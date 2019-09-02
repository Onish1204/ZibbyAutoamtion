package com.zibby.util;

import com.zibby.auto.WebDriverUtil;
//import com.zibby.maps.DataMaps;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.zibby.auto.WebDriverUtil.driver;

/**
 * This class was created to be able to nagivate between Google Chrome tabs when
 * one clicks on a link that opens a new tab. 
 */
public class WebDriverNavigation {
    
    public static void navigateToSecondTab() {
        
        Set<String> handles = driver().getWindowHandles();
        Iterator<String> it = handles.iterator();
        // iterate through your windows
        while (it.hasNext()) {
            String parent = it.next();
            String newwin = it.next();
            driver().switchTo().window(newwin);
            
        }
    }
    
    public static void navigateToFirstTab() {
        
        Set<String> handles = driver().getWindowHandles();
        Iterator<String> it = handles.iterator();
        // iterate through your windows
        while (it.hasNext()) {
            try {
                String parent = it.next();
                String newwin = it.next();
                driver().switchTo().window(newwin);
                driver().close();
                driver().switchTo().window(parent);
                
            } catch (NoSuchElementException e) {
                
                // do nothing, just let it continue
            }
        }
        
    }
    
    public static void navigateToLastTab() {
        
        List<String> browserTabs = new ArrayList<String>(driver()
                .getWindowHandles());
        driver().switchTo().window(browserTabs.get(browserTabs.size() - 1));
    }
    
    public static void navigateToURL(String mapURL) {
        
        driver().navigate().to(mapURL);
        
    }
    
    public static void navigateToPortalClearingCookies(String portal) {
        WebDriverUtil.driver().manage().deleteAllCookies();
        WebDriverUtil.driver().manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        WebDriverNavigation.navigateToURL(portal);
    }
    
    public static void switchToFirstWindow() {
        Set<String> handles = driver().getWindowHandles();
        for (int i = handles.size(); i > 1; i--) {
            driver().switchTo().window(
                    handles.toArray(new String[handles.size()])[i - 1]);
            driver().close();
        }
        driver().switchTo().window(
                handles.toArray(new String[handles.size()])[0]);
    }
    
    public static void closeTab() {
        driver().close();
        List<String> browserTabs = new ArrayList<>(driver().getWindowHandles());
        driver().switchTo().window(browserTabs.get(browserTabs.size() - 1));
    }
    
    public static void closeCurrentTab() {
        Set<String> handles = driver().getWindowHandles();
        Iterator it = handles.iterator();
        driver().close();
        driver().switchTo().window((String) it.next());
        
    }
    
    public static void switchToParenttab() {
        // Get the list of window handles
        Set<String> handles = driver().getWindowHandles();
        Iterator iterator = handles.iterator();
        driver().switchTo().window((String) iterator.next());
    }
}


