package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class RetailerLogin extends AbstractIolsPage{

	
	private static final Logger LOG = LogManager.getLogger(RetailerLogin.class);
	WebDriver driver=WebDriverUtil.driver();
	
	private By userName = By.xpath("//*[contains(@name,'username')]");
	private By password = By.xpath("//*[@type='password' and @name='password']");
	private By continueBtn = By.xpath("//button[@type='submit']");
	private By logInAsCustomer = By.xpath("//*[@class='login-as-other']");
	
	
	
	
	
}
