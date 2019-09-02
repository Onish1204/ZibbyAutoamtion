package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.PageUtil;
import com.zibby.auto.WebDriverUtil;

public class OTPPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(OTPPage.class);
	WebDriver driver = WebDriverUtil.driver();
	public static String mobileNo;

	private By chckBox1 = By.xpath("//label[contains(text(),'The customer has read and agrees')]");
	private By chckBox2 = By.xpath("//label[contains(text(),'The customer consents to the')]//preceding-sibling::input[@type='checkbox']");
	private By continueBtn = By.xpath("//button[@type='submit']");
	private By mobileNumber = By.xpath("//input[@name='phone']");
	
	private By logo = By.xpath("//img[@class='logo-main']");


	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	@Override
	public void waitForPageLoad() {
		// TODO Auto-generated method stub
		super.waitForPageLoad();
	}
	
	public void enterCustomerMobileNumber() {

		mobileNo = String.valueOf(Math.random()*100000 + 9010000000L);
		PageUtil.waitForPagetoLoad(5);
		waitForElement(mobileNumber).sendKeys(mobileNo);
		LOG.info("Entering customer mobile Number " + mobileNo);
	}
	
	public void checksmsPolicy() {
		waitForElement(chckBox1).click();
		LOG.info("Selecting checkBox for Privacy Policy, Terms of Use and ESIGN disclosure");
	}
	
	public void checksmsPolicy2() {
		waitForElement(chckBox2).click();
		LOG.info("Selecting checkBox for Credit Report Disclosure consent.");
	}
	
	public void clickContinue() {
		waitForElement(continueBtn).click();
		LOG.info("Clicking on Continue");
	}

}
