package com.zibby.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.PageUtil;
import com.zibby.auto.WebDriverUtil;

public class LoginPage extends AbstractIolsPage {

	WebDriver driver = WebDriverUtil.driver();
	
	
	private By retailerLogin = By.xpath("//*[@ui-sref='ext-login.retailer']");
	
	private By customerLogin = By.xpath("//*[@ui-sref = 'ext-login.customer.username']");
	
	private By userName = By.xpath("//*[@name='username']");

	private By password = By.xpath("//*[@name='password']");

	private By continueBtn = By.xpath("//button[@type='submit']");
	
	private By loginAsCustomer = By.xpath("//a[@class='login-as-other']");

	private By confirmCookie = By.xpath("//a[@id='hs-eu-confirmation-button']");

	private By logos = By.xpath("//*[@title='Zibby']");

	private By logo = By.xpath("//a[@title='Zibby']");

	private By mobilePhoneNumber = By.xpath("//input[@placeholder='123-456-7890']");

	private By smsPolicyChkBx = By.xpath("//input[@name='smsPolicy']");

	private By sixDigitCode = By.xpath("//input[@placeholder='123456']");

	private By last4DigitOfSSN = By.xpath("//input[@maxlength='4']");



	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	public void getIframe(final WebDriver driver) {
	    final List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
	    for (WebElement iframe : iframes) {
	     System.out.println(iframe);  
	    }
	}

	public void performRetailerLogin(String strUserName, String strPassword) throws InterruptedException {

		waitForElement(logo);
		if (isElementPresent(confirmCookie)) {
			waitForElement(confirmCookie).click();
		} 
		PageUtil.waitForPagetoLoad(2);
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@src='/ng/retailer/login.html#ext-login/select']")));
		
		waitForElement(retailerLogin).click();
		
		waitForElement(userName).sendKeys(strUserName);
		waitForElement(password).sendKeys(strPassword);
		
		waitForElement(continueBtn).click();

	}

	public void performCustomerLogin(String mobilePhoneNum) throws InterruptedException {

		waitForElement(logo);
		// driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@src='/ng/customer/login.html#ext-login/select']")));
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@src='/ng/retailer/login.html#ext-login/select']")));
		
		waitForElement(customerLogin).click();

		waitForElement(mobilePhoneNumber).sendKeys(mobilePhoneNum);

		waitForElement(smsPolicyChkBx).click();

		waitForElement(continueBtn).click();

		waitForElement(sixDigitCode).sendKeys("123456");

		waitForElement(last4DigitOfSSN).sendKeys(BasicInfoPage.ssn.substring(BasicInfoPage.ssn.length()-5));

		waitForElement(continueBtn).click();

	}
	
	@Override
	public void waitForPageLoad() {
		// TODO Auto-generated method stub
		super.waitForPageLoad();
	}


}