package com.zibby.pages;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerShopPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(CustomerShopPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By whereToUseZibbyLabel = By.xpath("//div[@class='zby-shop-header']");
	private By settingsTab = By.xpath("//a[contains(text(),'Settings')]");

	private By logo = By.xpath("//img[@class='logo-main']");

	public void verifyPage() throws InterruptedException {
		waitForPageLoad();
		Thread.sleep(2);
		Assert.assertNotNull(waitForElement(whereToUseZibbyLabel));
	}

  public void navigateSettings() throws InterruptedException {
    waitForPageLoad();
    Thread.sleep(2);
    waitForElement(settingsTab).click();
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
