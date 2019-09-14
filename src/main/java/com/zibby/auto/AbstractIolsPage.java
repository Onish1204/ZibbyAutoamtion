package com.zibby.auto;

import static com.zibby.auto.WebDriverUtil.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractIolsPage extends AbstractPage {
    
    public static final int DEFAULT_IMPLICIT_WAIT_SECONDS =20;
    private static final int WAIT_IN_SECONDS = 40;
    
    @FindBy(css = "#oss-lp-path-tiles > div:nth-child(1) > div > div > div > div.front > div")
    protected WebElement helpMeFindAPlanTile;
    
    @Override
    public void waitForPageLoad() {
        JSUtil.waitForStableDOM(6000);
        super.waitForPageLoad();
        JSUtil.waitForStableDOM(1500);
    }
    
    public void clickElementWithJavascript(WebElement element) {
        PageUtil.clickElementWithJavascript(element);
    }
    
    //FIXME possibly change this to "arguments[0].getInnerText()" so it works on more than just input tags.
    public String getElementTextWithJavascript(WebElement element) {
        String value = "";
        if (driver() instanceof JavascriptExecutor) {
            value = (String) ((JavascriptExecutor) driver()).executeScript("return arguments[0].value", element);
        }
        return value;
    }
    
    public WebElement waitForElementClickable(WebElement element) {
        return PageWaitUtil.waitForElementClickable(element);
    }
    
    public WebElement waitForElementClickable(By by) {
        return PageWaitUtil.waitForElementClickable(by);
    }
    
    /*public List<WebElement> waitForElementsBy(final By by) {
        WebDriverWait wait = new WebDriverWait(driver(), WAIT_IN_SECONDS);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.until((Function<? super WebDriver, V>) t -> t.findElements(by).size() > 0);
        return driver().findElements(by);
    }*/
    
    public void waitForElementNotStale(final By by) {
        PageWaitUtil.waitForElementNotStale(by);
    }
    
    public WebElement waitForElement(WebElement element) {
        return PageWaitUtil.waitForElementVisible(element);
    }
    
    public WebElement waitForElement(By elementBy) {
        return PageWaitUtil.waitForElementVisible(elementBy);
    }
    
    public boolean isElementPresent(By by) {
        return PageUtil.hasElement(by);
    }
    
    public void selectItemByIndex(WebElement parent, int index) {
        waitForElement(parent);
        Select select = new Select(parent);
        select.selectByIndex(index);
        waitForSeconds(2);
    }
    
    public void selectItemByValue(WebElement parent, String value) {
        waitForElement(parent);
        Select select = new Select(parent);
        select.selectByValue(value);
    }
    
    public void clickElementUsingAction(WebElement element) {
        waitForElement(element);
        Actions action = new Actions(driver());
        action.moveToElement(element).click().perform();
    }
    
    public void scrollPageUsingAction(WebElement element) {
        waitForElement(element);
        Actions action = new Actions(driver());
        action.sendKeys(Keys.DOWN).build().perform();
    }
    
    public String getTitle() {
        return PageUtil.getTitle().toLowerCase();
    }
    
    public static void waitForSeconds(int waitForSeconds) {
        PageUtil.handledSleep(waitForSeconds * 1000);
    }
    
    public void switchToLastTab() {
        PageUtil.switchToLastTab();
    }
    
    public WebElement waitForElementPresent(By by) {
        return PageWaitUtil.waitForElementPresent(by);
    }
    
    public void waitForElementNotPresent(By by) {
        waitForElementNotPresent(by, DEFAULT_IMPLICIT_WAIT_SECONDS);
    }
    
    public void waitForElementNotPresent(By by, int waitInSeconds) {
        WebDriverUtil.runWithTempTimeout(0, TimeUnit.SECONDS, () -> PageWaitUtil.waitForFindToFail(by, waitInSeconds));
    }
    
    public void waitForElementHiddenOrNotPresent(By by) {
        waitForElementHiddenOrNotPresent(by, DEFAULT_IMPLICIT_WAIT_SECONDS);
    }
    
    public void waitForElementHiddenOrNotPresent(By by, int waitInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver(), waitInSeconds);
        wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        
        if (!wait.until(ExpectedConditions.invisibilityOfElementLocated(by))) {
            throw new TimeoutException(String
                .format("Timed out after %s seconds waiting for element to be hidden or not present.  %s",
                    waitInSeconds, by));
        }
    }
    
    public void switchToFirstTab() {
        List<String> browserTabs = new ArrayList<>(driver().getWindowHandles());
        driver().switchTo().window(browserTabs.get(0));
    }
    
    public WebElement injectJavascript(WebElement ee, String ss, WebDriver driver) {
        try {
            getJavaScriptExecutor().executeScript(ss, ee);
        } catch (Exception ee2) {
            System.out.println("Inject Error" + ee2);
        }
        return ee;
    }
    
    public JavascriptExecutor getJavaScriptExecutor() {
        return JSUtil.getJSExecutor();
    }
    
    public String getValueWithJSExecutor(String id) {
        return getJavaScriptExecutor().executeScript("return document.getElementById('" + id + "').value;").toString();
    }
    
    public void navigateBrowserBack() {
        WebDriverUtil.driver().navigate().back();
    }
    
    public WebElement scrollToElement(WebElement element) {
        waitForElement(element);
        getJavaScriptExecutor().executeScript("arguments[0].scrollIntoView(true)", element);
        return element;
    }
    
    public void clickButton(String text) {
        waitForElementClickable(By.xpath(String.format("//button[contains(.,'%s')]", text))).click();
    }
    
    protected void waitForModalOverlayToClose() {
        waitForElementNotPresent(By.xpath("//div[@role='dialog' and @aria-hidden='false']"));
    }
    
    protected void waitForSpinnerOverlayToClose() {
        waitForElementHiddenOrNotPresent(By.id("data-uxd-ajax-loader-cmp"));
    }
   /* public int getColumnIndexFromTable(String tableXpath,String columnName) {
    	List<WebElement> elements=waitForElementsBy(By.xpath(tableXpath+"//th"));
		int index = 0;
		boolean isColumnDisplayed = false;

		for (WebElement webElement : elements) {
			index = index + 1;
			if (webElement.getText().equals(columnName)) {
				isColumnDisplayed = true;
				break;
			}
		}
		
		if(isColumnDisplayed)			
		return index;
		
		return 0;
	}*/
    
    @Override
    protected By defineUniqueElement() {
        // using Bento box profile icon, as it is global item throughout application
        return By.id("oss-top-logo-link__image");
    }
}
