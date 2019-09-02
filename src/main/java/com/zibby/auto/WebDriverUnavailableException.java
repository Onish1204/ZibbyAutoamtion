package com.zibby.auto;

import com.zibby.auto.common.BrowserTypes;

/**
 * Exception thrown when {@link WebDriverUtil} is unable to create a WebDriver.
 */
public class WebDriverUnavailableException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	WebDriverUnavailableException(BrowserTypes browserType, Exception e) {
        super("Failed to establish WebDriver for: " + browserType, e);
    }
}
