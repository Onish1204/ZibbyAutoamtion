package com.zibby.pages;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.PageUtil;
import com.zibby.auto.WebDriverUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerDashboardPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(CustomerDashboardPage.class);
	WebDriver driver = WebDriverUtil.driver();
	
	static String buyoutAmount;

	private By webcomebackLabel = By.xpath("//div[starts-with(text(),'Welcome back')]");
	private By leaseDetailsLabel = By.xpath("//span[contains(text(),'Lease details')]");
	private By paymentsLabel = By.xpath("//span[@class='tile-header-text'][contains(text(),'Payments')]");
	private By spendingLimitLabel = By.xpath("//span[contains(text(),'Spending limit')]");
	private By settingsLabel = By.xpath("//span[contains(text(),'Settings')]");
	private By didYouKnowLabel = By.xpath("//span[contains(text(),'Did you know?')]");
	private By dashboardTab = By.xpath("//a[@class='top-navbar-link top-navbar-link-selected'][text()='Dashboard']");
	private By exploreBtn = By.xpath("//a[@class='button-default'][@href='/account/details'][text()='Explore']");
	private By makeAPaymentBtn =
      By.xpath(
          "//a[@class='button-default'][contains(text(),'Make a payment')][@href='/account/payment']");
	private By shopBtn =
			By.xpath(
					"//a[@class='button-default'][contains(text(),'Shop')][@href ='/account/shop']");
	private By viewBtn =
			By.xpath(
					"//a[@class='button-default'][contains(text(),'View')][@href='/account/settings']");

	private By nextStepsMessage =
			By.xpath(
					"(//div[contains(text(),'Get the items you want from')])[1]");

	private By comeBackAndSeeUsMssg =
			By.xpath(
					"//div[contains(text(), 'done shopping with ')]");

	private By nextPaymentDue =
			By.xpath(
					"//div[@class='top-details-text-large'][contains(text(),'/')]");

	private By buyoutAmt =
			By.xpath(
					"(//div[@class= 'helvetica-neue-medium'])[3]");

	private By readyToShopMessage = By.xpath("//div[contains(text(), 'ready to shop')]");

	private By leaseDetailsTab = By.xpath("//a[contains(text(),'Lease details')]");

	private By preApprovalAmt = By.xpath("//div[@class='top-details-text-large']");

	private By logo = By.xpath("//div[@class='logo-zibby-full-white']");

	public void verifyPage() throws InterruptedException {
		Thread.sleep(4);
		waitForPageLoad();
		Thread.sleep(2);
    	Assert.assertNotNull(waitForElement(webcomebackLabel));
		Assert.assertNotNull(waitForElement(leaseDetailsLabel));
		Assert.assertNotNull(waitForElement(paymentsLabel));
		Assert.assertNotNull(waitForElement(spendingLimitLabel));
		Assert.assertNotNull(waitForElement(settingsLabel));
		Assert.assertNotNull(waitForElement(didYouKnowLabel));
		Assert.assertNotNull(waitForElement(dashboardTab));
		Assert.assertNotNull(waitForElement(exploreBtn));
		Assert.assertNotNull(waitForElement(makeAPaymentBtn));
		Assert.assertNotNull(waitForElement(shopBtn));
		Assert.assertNotNull(waitForElement(viewBtn));
	}

  public void navigateLeaseDetails() throws InterruptedException {
    waitForPageLoad();
    Thread.sleep(2);
    waitForElement(leaseDetailsTab).click();
	}

	public void readbuyoutAmount() throws InterruptedException {
		waitForPageLoad();
		Thread.sleep(2);
		buyoutAmount = waitForElement(buyoutAmt).getText();
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

	public void validatePreApprovalAmount() {
		PageUtil.waitForPagetoLoad(5);
		String preApprovalAmount = waitForElement(preApprovalAmt).getText();
		Assert.assertEquals(PreapprovalPage.preApprovalAmount, preApprovalAmount);
	}

	public void validatePreApprovalMessages() {
		waitForPageLoad();
		String readyToShop = waitForElement(readyToShopMessage).getText();
		String expectedPreApprovedMessage = "You're ready to shop, " + BasicInfoPage.custFirstName +"!";
		Assert.assertEquals(expectedPreApprovedMessage, readyToShop);

		String nextStepsMssg = waitForElement(nextStepsMessage).getText();
		String expectedNextStepsMssg = "Get the items you want from "+ BasicInfoPage.custLastName;
		Assert.assertNotNull(nextStepsMssg.contains(expectedNextStepsMssg));

		String comeBackAndSeeUsMessage = waitForElement(comeBackAndSeeUsMssg).getText();
		String expectedComeBackAndSeeUsMessage = "done shopping with "+ BasicInfoPage.custLastName;
		Assert.assertNotNull(comeBackAndSeeUsMessage.contains(expectedComeBackAndSeeUsMessage));
	}

	public void validateNextPaymentDue() {
		waitForPageLoad();
		String nextPaymentDueDate = waitForElement(nextPaymentDue).getText();
		Assert.assertEquals(AdminApplicationPage.nextDuePaymentDate, nextPaymentDueDate);
	}
}
