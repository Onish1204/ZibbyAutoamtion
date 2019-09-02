package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class SuccessPage extends AbstractIolsPage {
	private static final Logger LOG = LogManager.getLogger(PaySignSubmitPage.class);
	WebDriver driver = WebDriverUtil.driver();
	
	private By successMessage = By.xpath("//h1[@class='window-header-h1 ng-binding']");
	private By btnClose = By.xpath("//button[@class='button soft window-header-button']");
	private By btnBackToDashboard = By.xpath("//button[@class='button vibrant']");
	private By btnViewApplications = By.xpath("//a[@class='applications']");
	private By btnViewReports = By.xpath("//a[@class='applications']");
	private By paymentEstimator = By.xpath("//a[contains(@class,'link-payment-estimator')]");
	private By dashBoard = By.xpath("//a[contains(@class,'dashboard')]");
	private By btnRetailerUserName = By.xpath("(//button[@type='button' and @data-toggle='dropdown'])[2]");
	private By btnLogout = By.xpath("//a[contains(text(),'Logout')]");
	
	
	private By logo = By.xpath("//img[@class='logo-main']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	public void clickCloseButton() {
		waitForElement(btnClose).click();
		LOG.info("Clicking close button.");
	}
	
	public void clickBackToDashBoard() {
		waitForElement(btnBackToDashboard).click();
		LOG.info("Clicking back to dashboard button.");
	}
	
	public void clickViewApplications() {
		waitForElement(btnViewApplications).click();
		LOG.info("Clicking View Apllications button.");
	}
	
	public void clickViewReports() {
		waitForElement(btnViewReports).click();
		LOG.info("Clicking View Reports.");
	}
	
	public void clickPaymentEstimator() {
		waitForElement(paymentEstimator).click();
		LOG.info("Clicking on PaymentEstimator.");
	}
	
	public void clickingDashboard() {
		waitForElement(dashBoard).click();
	}
	
	public void validateSuccessMessage() {
		Assert.assertEquals("Success!", waitForElement(successMessage).getText());
	}
	
	public void clickOnLogOut() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForElement(btnRetailerUserName).click();
		waitForElement(btnLogout).click();
	}
	

}
