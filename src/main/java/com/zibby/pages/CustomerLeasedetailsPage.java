package com.zibby.pages;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerLeasedetailsPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(CustomerLeasedetailsPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By aboutYourProductsLabel = By.xpath("//div[@class='columns small-4 zby-account-med-title']");
	private By yourActivityLabel = By.xpath("//div[contains(text(),'Your activity')]");
	private By paymentsDue = By.xpath("//div[contains(text(),'Payment due')]");
	private By nextPaymentLabel = By.xpath("//div[contains(text(),'Next payment')]");
	private By recurringPaymentLabel = By.xpath("//div[contains(text(),'Recurring payment')]");
	private By startDateLabel = By.xpath("//div[contains(text(),'Start date')]");
	private By orderNumberLabel = By.xpath("//div[contains(text(),'Order number')]");
	private By paymentsTab = By.xpath("//a[contains(text(),'Payments')]");




	private By logo = By.xpath("//img[@class='logo-main']");

	public void verifyPage() throws InterruptedException {
		waitForPageLoad();
		Thread.sleep(2);
    	Assert.assertNotNull(waitForElement(aboutYourProductsLabel));
		Assert.assertNotNull(waitForElement(yourActivityLabel));
		Assert.assertNotNull(waitForElement(paymentsDue));
		Assert.assertNotNull(waitForElement(nextPaymentLabel));
		Assert.assertNotNull(waitForElement(recurringPaymentLabel));
		Assert.assertNotNull(waitForElement(startDateLabel));
		Assert.assertNotNull(waitForElement(orderNumberLabel));

	}

  public void navigatePayments() throws InterruptedException {
    waitForPageLoad();
    Thread.sleep(2);
    waitForElement(paymentsTab).click();
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
