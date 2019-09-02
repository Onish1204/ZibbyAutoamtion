package com.zibby.auto;

import com.zibby.auto.PageWaitUtil;
import com.zibby.auto.WebDriverUnavailableException;
import com.zibby.auto.AccessibilityAuditReport;
import com.zibby.auto.AccessibilityScanner;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.zibby.auto.ElementUtil.by;
import static com.zibby.auto.PageUtil.handledSleep;
import static com.zibby.auto.WebDriverUtil.addAutomationCookie;
import static com.zibby.auto.WebDriverUtil.driver;
import static com.zibby.auto.Configuration.isMockEnvironment;
import static com.zibby.auto.common.RunSafe.runSafe;

/**
 * Abstract superclass of all Page Objects.
 */
public abstract class AbstractPage {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractPage.class);

    private static final long DEFAULT_DURATION = 800;
    private volatile long throttleDuration = 0;

    /**
     * Default constructor that provides a single place to init the find by annotation, via
     * {@link PageFactory#initElements(WebDriver, Object)}. Do NOT call initElements from subclass constructors
     * as it will be redundant. Please note that due to timing reasons, initElements is actually called
     * by {@link #install(Class)}.
     * <p>
     * Do NOT attempt to instantiate a page object directly as this circumvents the {@link #waitForPageLoad()}
     * process. Instead call {@link #install(Class)} or {@link #installMulti(Class[])} to instantiate the
     * page object properly.
     */
    public AbstractPage() {
        throwIfNotBeingInstalled();
        throttleDuration = LoggerFactory.getLogger(getClass()).isDebugEnabled() ? DEFAULT_DURATION : 0;
    }

    /**
     * Use reflection to scan the page object for internal classes and have {@link PageFactory} look
     * for FindBy annotations within them as well.
     */
    void initElementsWithinInternalClasses(WebDriver driver) {
        Class<? extends AbstractPage> clazz = getClass();
        String pageClazzName = clazz.getName();
        for(Field field : clazz.getDeclaredFields()) {
            String internalClazzName = field.getType().getName();
            LOG.trace("Considering internal class: " + internalClazzName);
            if(internalClazzName.startsWith(pageClazzName)  && !internalClazzName.equals(pageClazzName)) {
                String fieldName = field.getName();
                LOG.debug("Initializing potential elements within: " + fieldName);
                try {
                    field.setAccessible(true);
                    Object internalClazzInstance = field.get(this);
                    if (internalClazzInstance != null) {
                        PageFactory.initElements(driver, internalClazzInstance);
                    } else {
                        LOG.warn("Unable to initialize " + fieldName + " because the object is null");
                    }
                } catch (Exception e) {
                    LOG.error("Failed to initElements", e);
                }
            }
        }
    }

    /**
     * In order to enforce the pattern of waiting for the page to load, it must be instantiated indirectly
     * by calling {@link #install(Class)}. Otherwise a {@link UnsupportedOperationException} is thrown.
     */
    private static void throwIfNotBeingInstalled() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        Predicate<StackTraceElement> rule = e -> e.getClassName().equals(AbstractPage.class.getName()) && e.getMethodName().equals("instantiateOrThrow");
        int attempt = 1;
        for(StackTraceElement element : stack) {
            if(rule.test(element)) {
                return;
            }
            // only look as far as expected for the instantiateOrThrow call
            if(++attempt > 20) {
                LOG.warn("Giving up looking for instantiateOrThrow after looking " + attempt + " up the call stack");
                break;
            }
        }
        throw new UnsupportedOperationException("AbstractPage must not be instantiated directly. Instead instantiate by passing the Page's Class object to AbstractPage.install(Class<P>)");
    }

    private static <P extends AbstractPage> P instantiateOrThrow(Class<P> pageClazz) {
        LOG.trace("Instantiating: " + pageClazz);
        try {
            return pageClazz.newInstance();
        } catch (WebDriverUnavailableException wdue) {
            throw wdue;
        } catch (Exception e) {
            throw new PageObjectInstantiationException(pageClazz, e);
        }
    }

    /**
     * Constructs an instance of the Page via its Class definition passed in and
     * then calls {@link AbstractPage#waitForPageLoad()} before returning the Page instance.
     *
     * @param pageClazz the given class
     * @param <P> the generic type that must extend {@link AbstractPage}
     * @param postActions list of additional actions to take when creating the page object after instantiation and before waitForPageLoad()
     * @return an instance of the Page that is considered to be loaded
     */
    public static <P extends AbstractPage> P install(Class<P> pageClazz, List<Consumer<AbstractPage>> postActions) {
        P page = instantiateOrThrow(pageClazz);
        if(isMockEnvironment()) {
            LOG.info("PageFactory.initElements() skipped because in Mock Environment for testing");
        } else {
            WebDriver driver = driver();
            PageFactory.initElements(driver, page);
            try {
                page.initElementsWithinInternalClasses(driver);
            } catch (Exception e) {
                LOG.error("Unexpected error scanning internal classes", e);
            }
        }
        postActions.forEach(a -> a.accept(page));
        if(isMockEnvironment()) {
            LOG.info("page.waitForPageLoad() skipped because in Mock Environment for testing");
        } else {
            addAutomationCookie();
            page.waitForPageLoad();
        }
        return page;
    }

    /**
     * Constructs an instance of the Page via its Class definition passed in and
     * then calls {@link AbstractPage#waitForPageLoad()} before returning the Page instance.
     *
     * @param pageClazz the given class
     * @param <P> the generic type that must extend {@link AbstractPage}
     * @return an instance of the Page that is considered to be loaded
     */
    public static <P extends AbstractPage> P install(Class<P> pageClazz){
        return install(pageClazz, Collections.emptyList());
    }

    /**
     * Constructs an instance for each of the page classes passed in and obtains their
     * {@link #defineUniqueElement()}. This list of {@link By} locators is then passed to
     * {@link PageWaitUtil#waitForElementVisibleMulti(By...)} to find the first locator that
     * is displayed. The page owning that locator is then returned.
     *
     * @param pageClazzes the given class(es)
     * @return an instance of the first page with a visible element
     */
    public static AbstractPage installMulti(Class<? extends AbstractPage>... pageClazzes) {
        List<AbstractPage> pages = new LinkedList<>();
        for(Class<? extends AbstractPage> pageClazz : pageClazzes) {
            pages.add(instantiateOrThrow(pageClazz));
        }

        By[] locators = new By[pages.size()];
        for(int i = 0; i < locators.length; i++) {
            locators[i] = pages.get(i).defineUniqueElement();
        }

        PageWaitUtil.IndexedWebElement result = PageWaitUtil.waitForElementVisibleMulti(locators);
        if(null != result)
            return pages.get(result.index);
        return null;
    }

    /**
     * Legacy method for installing a page object.
     *
     * @param pageClazz the given class
     * @param <P> the generic type that must extend {@link AbstractPage}
     * @return an instance of the Page that is considered to be loaded
     *
     * @deprecated call {@link #install(Class)} instead.
     */
    @Deprecated
    public static <P extends AbstractPage> P peek(Class<P> pageClazz) {
        return install(pageClazz);
    }

    public String peekUrl(){
        throw new UnsupportedOperationException("URL not defined");
    }

    /**
     * Legacy method for installing the first available of multiple page object.
     *
     * @param pageClazzes the given class(es)
     * @return an instance of the first page with a visible element
     *
     * @deprecated call {@link #installMulti(Class[])} instead.
     */
    @Deprecated
    public static AbstractPage peekMulti(Class<? extends AbstractPage>... pageClazzes) {
        return installMulti(pageClazzes);
    }

    /**
     * Subclass may override to specify a different number of seconds to wait in {@link #waitForPageLoad()}.
     * The default value is {@link PageWaitUtil#WAIT_IN_SECONDS}.
     *
     * @return number of seconds to wait
     */
    protected int getWaitForPageLoad() {
        return PageWaitUtil.WAIT_IN_SECONDS;
    }

    /**
     * Waits for the Page to load and then calls {@link PageFactory#initElements(WebDriver, Object)} to initialize
     * the Page's fields marked with the {@link FindBy} annotation. The Page is considered to be loaded when
     * {@link AbstractPage#defineUniqueElement()} is present.
     */
    public void waitForPageLoad() {
        LOG.trace("Waiting for " + getClass().getName() + " to load...");
        long time = System.currentTimeMillis();
        By locator = defineUniqueElement();
        LOG.debug(getClass().getName() + " defined unique element locator: " + locator);
        // wait for it to appear
        try {
            WebElement element = locator instanceof InvisibleLocator ?
                    PageWaitUtil.waitForElementPresent(locator, getWaitForPageLoad()) :
                    PageWaitUtil.waitForElementVisible(locator, getWaitForPageLoad());
            LOG.debug("Page has loaded: " + getClass().getName() + " after element found: " + by(element));
            LOG.trace("Page loaded in " + (System.currentTimeMillis() - time) + "ms");
        } catch(Exception e) {
            LOG.error(getClass().getName() + " could not find unique element: " + locator);
            String message = "Unique element for Page " + getClass().getName() + " was not available";
            // this stack trace is huge, so only log it if in DEBUG
            LOG.debug(message, e);
            throw new RuntimeException(message);
        }
    }

    /**
     * A concrete subclass must implement this method to help locate an element unique to the page. This presence of
     * this element is used to determine when the page has loaded.
     *
     * @return the mechanism to find the unique element
     */
    protected abstract By defineUniqueElement();

    /**
     * A Page's toString is the concrete subclass' full class name.
     *
     * @return the full class name
     */
    public final String toString() {
        return getClass().getName();
    }

    /**
     * Retrieves the By associated with a WebElement that uses the FindBy annotation.
     *
     * @param fieldName the given field by name
     * @return the corresponding locator
     * @throws NoSuchFieldException if the given named field is not found
     */
    public By getByFromFindBy(String fieldName) throws NoSuchFieldException {
        return new Annotations(this.getClass().getDeclaredField(fieldName)).buildBy();
    }

    /**
     * Sets the sleep duration for calls to {@link #throttle()}. Setting a duration
     * that is &lt;= 0 will cause throttle() to no-op.
     *
     * @param durationInMillis the number of millis to sleep
     */
    public final void setThrottle(long durationInMillis) {
        throttleDuration = durationInMillis;
    }

    public final boolean throttle() {
        return throttle(Optional.empty());
    }

    public final boolean throttle(@Nonnull Runnable openingAct) {
       return throttle(Optional.of(openingAct));
    }

    /**
     * Performs a handled sleep if the Throttle Duration is &gt; 0 millis.
     *
     * @param openingAct an optional runnable to invoke before the sleep
     * @return whether throttle Duration &gt; 0
     */
    public final boolean throttle(Optional<Runnable> openingAct) {
        // volatile
        long duration = throttleDuration;

        // don't incur string building unless necessary
        if(LOG.isTraceEnabled())
            LOG.trace("Throttling for " + duration + "ms");

        // sleeps?
        if(duration > 0) {
            runSafe(openingAct);
            handledSleep(duration);
            return true;
        }
        return false;
    }

    /**
     * Invokes {@link AccessibilityScanner#runAccessibilityAudit()} on this page using
     * {@link #defineAccessibilityParent()} as the parent selector to run the audit from.
     *
     * @return whether the audit contains any errors
     *
     * @throws IOException if the {@link AccessibilityScanner#runAccessibilityAudit()} throws
     */
    public final boolean hasAccessibilityErrors() throws IOException {
        Optional<String> parentSelector = defineAccessibilityParent();
        LOG.debug("Page: " + this + " defines Accessibility parent: " + parentSelector);
        AccessibilityScanner scanner = new AccessibilityScanner(parentSelector);
        scanner.runAccessibilityAudit();
        return AccessibilityAuditReport.containsErrors();
    }

    /**
     * Convenience method that calls {@link #hasAccessibilityErrors()} and wraps the result in an
     * {@link Assert#assertFalse(boolean)}.
     *
     * @throws IOException if the {@link AccessibilityScanner#runAccessibilityAudit()} throws
     */
    public final void assertAccessible() throws IOException {
        Assert.assertFalse("No accessibility errors expected for page: " + this, hasAccessibilityErrors());
    }

    /**
     * The subclass should override this method to provide a suitable CSS Selector to use as the parent element for
     * the Accessibility audit. By default a Page doesn't define a parent and {@link #hasAccessibilityErrors()} will cause the
     * {@link AccessibilityScanner} to scan from the root element of the DOM.
     *
     * @return the desired selector
     */
    protected Optional<String> defineAccessibilityParent() {
        return Optional.empty();
    }

	public static class PageObjectInstantiationException extends RuntimeException {
		private PageObjectInstantiationException(Class<?> pageClazz, Throwable cause) {
			super("Failed to instantiate page: " + pageClazz, cause);
		}
	}
}
