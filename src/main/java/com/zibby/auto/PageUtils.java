package com.zibby.auto;



import com.zibby.auto.PageUtil;
import com.zibby.auto.PageWaitUtil;
import com.zibby.auto.WebDriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PageUtils {
    public static void waitForLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(WebDriverUtil.driver(), 30);
        wait.until(pageLoadCondition);
    }

//    public static void scrollElementIntoView(WebElement element){
//        ((JavascriptExecutor) WebDriverUtil.driver()).executeScript("arguments[0].scrollIntoView(true);", element);
//        PageUtil.handledSleep(500);
//    }

    public static void scrollIntoView(WebElement el) {
        Point p = el.getLocation();
        int windowHieght = WebDriverUtil.driver().manage().window().getSize().getHeight();
        int elementY = p.getY();
        ((JavascriptExecutor) WebDriverUtil.driver()).executeScript("window.scrollTo(0, " + (elementY - (windowHieght/2)) + ")");
    }

    public static WebElement getElementInView(WebElement webElement) {
        scrollIntoView(webElement);
        PageUtil.handledSleep(500);
        return webElement;
    }

    public void examples() {
        //need at Webdriver or other driver related settings
        WebDriverUtil.driver();  //get the driver and make your own custom waits
        //need at some already written waits
        PageWaitUtil.waitForElementClickable(new By.ById("yourelement"));
        //PageUtil has some useful methods
        PageUtil.executeScript("blah");
        //screenshots
        //ScreenshotUtil.takeScreenshot();
    }

    public static void waitForSeconds(int waitForSeconds) {
        Calendar expectedEndtime = Calendar.getInstance();
        expectedEndtime.add(Calendar.SECOND, waitForSeconds);

        Calendar currentTime;
        while (true) {
            currentTime = Calendar.getInstance();
            if (currentTime.compareTo(expectedEndtime) > 0)
                break;
        }
    }

    public static void verifypopulatedData(Map<String, String> dataMap, String fileName) throws Exception {
        Properties property = new Properties();
        Set<String> keySet = dataMap.keySet();
        for (String key : keySet) {
            property.setProperty(key, dataMap.get(key));
        }
        File propertyFile = new File("InputFiles\\" + fileName + ".properties");
        FileOutputStream os = new FileOutputStream(propertyFile);
        property.store(os, "Yay!!");
    }
}
