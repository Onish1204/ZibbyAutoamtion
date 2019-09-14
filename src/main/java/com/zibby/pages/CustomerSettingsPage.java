package com.zibby.pages;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerSettingsPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(CustomerSettingsPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By basicInformationLabel = By.xpath("//div[contains(text(),'Basic information')]");
	private By firstNameLabel = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'First name:')]");
	private By lastNameLabel = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Last name:')]");
	private By billingAddress = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Billing address:')]");
	private By billingAddress2 = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Billing address 2:')]");
	private By city = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'City:')]");
	private By state = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'State:')]");
	private By zipCode = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Zip code:')]");
	private By mobileNumber = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Mobile number:')]");
	private By email = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Email:')]");
	private By dateOfBirth = By.xpath("//div[@id='zby-account-settings-container']//label[contains(text(),'Date of birth:')]");

	private By signOutLink = By.xpath("//a[contains(text(),'Sign out')]");

	private By logo = By.xpath("//a[@class='zby-logo']//img");

	public void verifyPage() throws InterruptedException {
		waitForPageLoad();
		Thread.sleep(2);
		Assert.assertNotNull(waitForElement(basicInformationLabel));
		Assert.assertNotNull(waitForElement(firstNameLabel));
		Assert.assertNotNull(waitForElement(lastNameLabel));
		Assert.assertNotNull(waitForElement(billingAddress));
		Assert.assertNotNull(waitForElement(billingAddress2));
		Assert.assertNotNull(waitForElement(city));
		Assert.assertNotNull(waitForElement(state));
		Assert.assertNotNull(waitForElement(zipCode));
		Assert.assertNotNull(waitForElement(mobileNumber));
		Assert.assertNotNull(waitForElement(email));
		Assert.assertNotNull(waitForElement(dateOfBirth));
	}

  public void signOut() throws InterruptedException {
    waitForPageLoad();
    Thread.sleep(2);
    waitForElement(signOutLink).click();
	}


	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	@Override
	public void waitForPageLoad() {
		// TODO Auto-generated method stub
		super.waitForPageLoad();
	}
	


}
