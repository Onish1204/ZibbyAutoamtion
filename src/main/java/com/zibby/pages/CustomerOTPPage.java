package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class CustomerOTPPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(BasicInfoPage.class);
	WebDriver driver = WebDriverUtil.driver();
	
	private By mobileNumber = By.xpath("");
}
