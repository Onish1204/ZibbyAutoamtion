package com.zibby.auto;

import static org.openqa.selenium.ie.InternetExplorerDriverService.createDefaultService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zibby.auto.common.BrowserTypes;
import com.zibby.auto.common.RunSafe;

//import javafx.util.Pair;

/**
 * Manages static access to a per-Thread {@link WebDriver}. Also contains a variety
 * of convenience methods for dealing with {@link WebElement}s.
 */
public final class WebDriverUtil {
	private static final Logger LOG = LoggerFactory.getLogger(WebDriverUtil.class);

	private static final ThreadLocalDrivers drivers = new ThreadLocalDrivers();

    private static final DriverPresence driverPresence = new DriverPresence();

	private static final ActiveDrivers activeDrivers = new ActiveDrivers();

	/** The default implicit wait, in seconds */
	public static final long DEFAULT_IMPLICIT_WAIT = 7;

	private WebDriverUtil() { /** static only */ }

	/**
	 * Different threads can have different {@link WebDriver} instances. Only register the shutdown once per thread
	 */
	private static final ThreadLocal<Boolean> hookPresence = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	/**
	 * Static access to the {@link WebDriver}. Each Thread invoking this method will
	 * get an independent driver (via {@link ThreadLocal}. The initial invocation by
	 * a calling thread will cause the WebDriver to be instantiated and configured.
	 *
	 * @return the driver specific to calling Thread
     */
	public static WebDriver driver() {
		return drivers.get().driver;
	}

	/**
	 * Indicates if a {@link WebDriver} has been initialized for the calling thread. Any
	 * call to {@link #driver()} will initialize the driver. Calling {@link #dispose()} will
	 * clear the driver.
	 *
	 * @return whether a driver is currently initialized
	 */
	public static boolean isDriverInitialized() {
		return driverPresence.get();
	}

	/**
	 * Closes and quits the {@link WebDriver} for the calling thread. Also cleans up
	 * the fallback shutdown hook.
     */
	public static void dispose() {
        if (!isDriverInitialized()) return;
		
		WebDriver webDriver = driver();
		try {
			webDriver.quit();
		}
		finally {
			activeDrivers.remove(webDriver);
			drivers.remove();
            driverPresence.remove();
		}
	}

    /**
     * Forces a {@link BrowserTypes#CHROME} WebDriver with mobile emulation turned on. The emulation
	 * will be configured by device name using the given deviceName. Any subsequent calls to {@link #driver()}
	 * by this thread will return the overridden driver.
	 * <p>
	 * The method first calls {@link #dispose()} to vacate any existing WebDriver.
	 *
	 * @param deviceName the name of the device (e.g. <code>"Google Nexus 7"</code>)
	 * @throws MalformedURLException if {@link Configuration#SELENIUM_GRID_URL} is malformed
     */
    public static void mobileDriver(String deviceName) throws MalformedURLException {
        dispose();
        WebDriver webDriver = buildMobile(mobileEmulation -> mobileEmulation.put("deviceName", deviceName));
        WebDriver configuredMobileDriver = configureDriver(webDriver, BrowserTypes.MOBILE_DEVICE);
        drivers.set(new WebDriverPacket(configuredMobileDriver, BrowserTypes.MOBILE_DEVICE));
    }

	public static BrowserTypes getBrowserType() {
		return drivers.get().browserType;
	}

	/**
	 * Constructs the appropriate concrete {@link WebDriver} per the provided {@link BrowserTypes}.
	 * The driver is also configured with various capabilities.
	 * @param browserType the given browser type
	 * @return a web driver configured according to the given brwoser type and specified system properties
     */
	public static WebDriver establishWebDriver(BrowserTypes browserType) {
        LOG.info("Establishing WebDriver for browser: " + browserType);
        if (null == browserType)
            throw new NullPointerException("browserType is not allowed to be null");

        WebDriver webDriver;

        try {
            DesiredCapabilities capabilities;
            switch (browserType) {
                case IE:
                    System.setProperty("webdriver.ie.driver", Configuration.IE_WEBDRIVER);
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setPlatform(Platform.WINDOWS);
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
                    capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                    capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                    capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    webDriver = buildNonMobile(capabilities, () -> new InternetExplorerDriver(createDefaultService(), capabilities));
                    break;

                case FIREFOX:
					System.setProperty("webdriver.gecko.driver", Configuration.FIREFOX_WEBDRIVER);
                    ProfilesIni profilesIni = new ProfilesIni();
                    FirefoxProfile firefoxProfile = profilesIni.getProfile("default");
                    capabilities = DesiredCapabilities.firefox();
                    capabilities.setBrowserName("firefox");
                    capabilities.setPlatform(Platform.ANY);
					if(Configuration.REMOTE) {
						// TODO 
						capabilities.setCapability("marionette", false);
					}
                    webDriver = buildNonMobile(capabilities, () -> new FirefoxDriver((Capabilities) firefoxProfile));
                    break;

                case CHROME:
                    System.getenv();
                    System.setProperty("webdriver.chrome.driver", Configuration.CHROME_WEBDRIVER);
                    capabilities = DesiredCapabilities.chrome();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("test-type", "start-maximized", "no-default-browser-check", "--disable-extensions");

                    if (Configuration.IS_PDF_DOWNLOAD_WEBDRIVER) {

                        Map<String, Object> preferences = new Hashtable<String, Object>();

                        preferences.put("plugins.always_open_pdf_externally", true);
                        preferences.put("download.default_directory", System.getProperty("user.dir"));

                        options.setExperimentalOption("prefs", preferences);

                    }

                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    capabilities.setPlatform(Platform.ANY);
                    webDriver = buildNonMobile(capabilities, () -> new ChromeDriver(capabilities));
                    break;

                case SAFARI:
                    capabilities = new DesiredCapabilities();
                    capabilities.setPlatform(Platform.MAC);
                    webDriver = buildNonMobile(capabilities, () -> new SafariDriver(capabilities));
                    break;

                case MOBILE_DEVICE:
                    webDriver = buildMobile(mobileEmulation -> mobileEmulation.put("deviceName", Configuration.MOBILE_DEVICE));
                    break;

                case MOBILE_CONFIG:
                    webDriver = buildMobile(mobileEmulation -> {
                        Map<String, Object> deviceMetrics = new HashMap<>();
                        deviceMetrics.put("width", Configuration.MOBILE_WIDTH);
                        deviceMetrics.put("height", Configuration.MOBILE_HEIGHT);
                        deviceMetrics.put("pixelRatio", Configuration.MOBILE_PIXEL_RATIO);
                        mobileEmulation.put("deviceMetrics", deviceMetrics);
                    });
                    break;

                default:
                    throw new RuntimeException("Browser type not supported: " + browserType);
            }
        } catch (Exception e) {
            throw new WebDriverUnavailableException(browserType, e);
        }

        return configureDriver(webDriver, browserType);
    }

    private static WebDriver configureDriver(WebDriver webDriver, BrowserTypes browserType){
		WebDriver.Options options = webDriver.manage();
		setImplicitTimeout(webDriver, DEFAULT_IMPLICIT_WAIT);

		if (!browserType.isMobile) {
			options.window().maximize();
		}

		options.deleteAllCookies();

		activeDrivers.add(webDriver);
        driverPresence.set(true);
		return webDriver;

	}

	private static WebDriver buildNonMobile(DesiredCapabilities capabilities, Supplier<WebDriver> nonRemote) throws MalformedURLException {
		return Configuration.REMOTE ? new RemoteWebDriver(new URL(Configuration.SELENIUM_GRID_URL), capabilities) : nonRemote.get();
	}

	private static WebDriver buildMobile(Consumer<Map<String, Object>> emulationConfig) throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", Configuration.CHROME_WEBDRIVER);

		Map<String, Object> mobileEmulation = new HashMap<>();
		// populate the emulation map with the passed in config
		emulationConfig.accept(mobileEmulation);

		Map<String, Object> chromeOptions = new HashMap<>();
		chromeOptions.put("mobileEmulation", mobileEmulation);

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        return Configuration.REMOTE ? new RemoteWebDriver(new URL(Configuration.SELENIUM_GRID_URL), capabilities) : new ChromeDriver(capabilities);
	}

	/**
	 * Adds a cookie "automation" with value "1" used to suppress behavior like ForeSee popups. <b>Note:</b> the cookie
	 * is applied to the <u>current page</u>. If the cookie needs to be added to the upcoming page,
	 * call {@link #addAutomationCookieOnNextPage()} instead.
	 */
	public static void addAutomationCookie() {
		addAutomationCookie(driver());
	}

	private static void addAutomationCookie(WebDriver driver) {
		URL url;
		try {
			url = new URL(driver.getCurrentUrl());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Failed to determine current URL");
		}

		Cookie cookie = new Cookie.Builder("automation", "1")
				.domain(url.getHost())
				.path("/")
				.isSecure(false)
				.build();
		WebDriver.Options options = driver.manage();
		if(options.getCookieNamed(cookie.getName()) == null) {
			options.addCookie(cookie);
			LOG.info("Added automation cookie to: " + url);
		} else {
			LOG.debug("Automation cookie already present: " + url);
		}
    }

	/**
	 * Gets the current URL from the driver and then waits up to {@link PageWaitUtil#WAIT_IN_SECONDS} seconds for a new URL.
	 * This should indicate a page change. Then calls {@link #addAutomationCookie()}. A new {@link Thread} is spun up
	 * to wait for the URL to change so this call does not block the calling thread.
	 */
	public static void addAutomationCookieOnNextPage() {
		/** Grab the driver for the calling thread so the same instance is used by the spun up thread */
		WebDriver driver = driver();
		new Thread(() -> {
			try {
				PageWaitUtil.waitForURLToChange(driver);
				addAutomationCookie(driver);
			} catch (TimeoutException te) {
				LOG.warn("Timed out waiting for URL to change before adding automation cookie");
			}
		}, "addAutomationCookieOnNextPage").start();
	}

	/**
	 * Sets the implicit timeout for the WebDriver assigned to the calling thread. Any future resets of the timeout
	 * will use the updated value.
	 *
	 * @param timeInSeconds the number of seconds to wait
     */
	public static void setImplicitTimeout(long timeInSeconds) {
		WebDriverPacket packet = drivers.get();
		packet.implicitWait = timeInSeconds;
		setImplicitTimeout(packet.driver, timeInSeconds);
	}

	private static void setImplicitTimeout(WebDriver driver, long timeInSeconds) {
		driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
	}

	/**
	 * Resets the WebDriver's implicit wait to the default value. The default is initially set to
	 * {@link #DEFAULT_IMPLICIT_WAIT} and may be updated by {@link #setImplicitTimeout(long)}.
	 */
	public static void resetImplicitTimeout() {
		WebDriverPacket packet = drivers.get();
		setImplicitTimeout(packet.driver, packet.implicitWait);
	}

	/**
	 * Runs the given logic using a temporary implicit timeout. After the logic completes the timeout is reset to the
	 * default value.
	 *
	 * @param time The amount of time to wait.
	 * @param unit The unit of measure for {@code time}.
	 * @param logic the logic to run under the temp timeout
     */
	public static void runWithTempTimeout(long time, TimeUnit unit, Runnable logic) {
		WebDriver.Timeouts timeouts = driver().manage().timeouts();
		timeouts.implicitlyWait(time, unit);
		RunSafe.runSafe(logic);
		resetImplicitTimeout();
	}

	/**
	 * Runs the given logic using a temporary implicit timeout and returns a value.
	 * After the logic completes the timeout is reset to the default value.
	 *
	 * @param time The amount of time to wait.
	 * @param unit The unit of measure for {@code time}.
	 * @param logic the logic to run under the temp timeout
	 * @param <T> the type of object returned by the logic
     * @return the return value provided by the logic
     */
	public static <T> T runWithTempTimeout(long time, TimeUnit unit, Supplier<T> logic) {
		WebDriver.Timeouts timeouts = driver().manage().timeouts();
		timeouts.implicitlyWait(time, unit);
		try {
			return logic.get();
		} finally {
			resetImplicitTimeout();
		}
	}

	/**
	 * Stores a {@link WebDriver} instance along with its associated {@link BrowserTypes} value
	 * and default implicit timeout value.
	 */
	private static final class WebDriverPacket {
		private final WebDriver driver;
		private final BrowserTypes browserType;
		private long implicitWait;

		WebDriverPacket(WebDriver driver, BrowserTypes browserType) {
			this.driver = driver;
			this.browserType = browserType;
			implicitWait = DEFAULT_IMPLICIT_WAIT;
		}
	}

	/**
	 * Handles instantiation and configuration per Thread of the {@link WebDriver} appropriate for
	 * the {@link BrowserTypes} provided by {@link Configuration#BROWSER}.
	 */
	private static class ThreadLocalDrivers extends ThreadLocal<WebDriverPacket> {
		/**
		 * This method builds profiles for the specific browser that is sent in. It
		 * builds the correct profile and launches that browser from the
		 * WebDriverUtil WebDriver.
		 *
		 * @return a {@link } containing the WebDriver for the associated {@link BrowserTypes}
		 */
		@Override
		protected WebDriverPacket initialValue() {
			BrowserTypes browserType = Configuration.BROWSER;
			WebDriver webDriver = establishWebDriver(browserType);
			LOG.info("Created WebDriver for " + browserType + " on: " + Thread.currentThread());
			return new WebDriverPacket(webDriver, browserType);
		}
	}

    private static class DriverPresence extends ThreadLocal<Boolean> {
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    }

	/**
	 * Gets the hyper text transfer protocol as either plain or secure based on {@link Configuration#USE_SSL}.
	 *
	 * @return "https://" if SSL is configured, else "http://"
     */
	public static String getProtocol() {
		return Configuration.USE_SSL ? "https://" : "http://";
	}

	/**
	 * Composes a full URL based on the given base and relative pieces passed in. The URL will
	 * begin with "https://" if {@link Configuration#USE_SSL} is true else begins with "http://".
	 *
	 * @param baseURL the given base URL
	 * @param relativeURL the given relative URL to append
     * @return the full URL
     */
	public static String assembleFullURL(String baseURL, String relativeURL) {
		StringBuilder protocol = new StringBuilder(getProtocol());
		protocol.append(baseURL).append(relativeURL);
		String url = protocol.toString();
		LOG.debug("Full URL: " + url);
		return url;
	}

	/**
	 * Removes any protocol from the beginning of the URL (if present). A protocol is recognized by
	 * the because it ends with ://.
	 *
	 * @param url the given URL
	 * @return the URL with the leading protocol stripped away
     */
	public static String stripProtocol(String url) {
		final String protocolPattern = "://";
		int at = url.indexOf(protocolPattern);
		if(at == -1)
			return url;
		return url.substring(at + protocolPattern.length());
	}

	/**
	 * Manages the active drivers and will quit them during a JVM shutdown hook
	 * established when a static instance is created by the containing class.
     */
	private static class ActiveDrivers {
		private final List<WebDriver> drivers = new ArrayList<>();

		ActiveDrivers() {
			LOG.debug("Establishing shutdown for Thread: " + Thread.currentThread());
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					LOG.trace("Shutdown hook activated");
					quit();
				}
			});
		}

		synchronized void add(WebDriver driver) {
			LOG.debug("Added active WebDriver: " + driver);
			drivers.add(driver);
		}

		synchronized void remove(WebDriver driver) {
			LOG.debug("Removed active WebDriver: " + driver);
			boolean removed = drivers.remove(driver);
			if(!removed)
				LOG.warn("Failed to remove WebDriver: " + driver);
		}

		synchronized void quit() {
			for(WebDriver driver : drivers) {
				try {
					LOG.debug("Quitting WebDriver: " + driver);
					driver.quit();
				} catch(Exception e) {
					LOG.error("Failed to quit WebDriver: " + driver);
				}
			}
		}
	}
}
