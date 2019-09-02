package com.zibby.pages;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class AdminApplicationPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(OTPEntryPage.class);
	WebDriver driver = WebDriverUtil.driver();
	public static String buyoutAmount;
	public static String nextDuePaymentDate;

	private By buyoutAmountTxtBx = By.xpath("(//h3[contains(@class,'stat-title ng-binding')])[4]");
	private By logo = By.xpath("//div[@class='zibby-logo']");
	private By nextDueDate = By.xpath("(//h3[contains(text(),'/')])[1]");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}

	public void readBuyoutAmount() throws InterruptedException {
		waitForPageLoad();
		Thread.sleep(2);
		buyoutAmount = waitForElement(buyoutAmountTxtBx).getText();
	}

	public void validateBuyoutAmount(){
    Assert.assertEquals(CustomerDashboardPage.buyoutAmount, buyoutAmount);
	}

	public void getNextDueDate(){
		waitForPageLoad();
		nextDuePaymentDate = waitForElement(nextDueDate).getText();
	}
}