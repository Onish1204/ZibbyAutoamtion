package com.zibby.pages;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerPaymentsPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(CustomerPaymentsPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By yourActivityLabel = By.xpath("//div[contains(text(),'Your activity')]");
	private By paymentsDue = By.xpath("//div[contains(text(),'Payment due')]");
	private By availableCreditLimit = By.xpath("//div[contains(text(),'Available credit limit')]");
	private By nextPaymentLabel = By.xpath("//div[contains(text(),'Next payment')]");
	private By shopTab = By.xpath("//a[contains(text(),'Shop')]");

	private By logo = By.xpath("//a[@class='zby-logo']//img");

	public void verifyPage() throws InterruptedException {
		waitForPageLoad();
		Thread.sleep(2);
		Assert.assertNotNull(waitForElement(yourActivityLabel));
		Assert.assertNotNull(waitForElement(paymentsDue));
		Assert.assertNotNull(waitForElement(availableCreditLimit));
		Assert.assertNotNull(waitForElement(nextPaymentLabel));
	}

  public void navigateShop() throws InterruptedException {
    waitForPageLoad();
    Thread.sleep(2);
    waitForElement(shopTab).click();
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
