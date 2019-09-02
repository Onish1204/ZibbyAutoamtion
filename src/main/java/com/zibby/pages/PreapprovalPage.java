package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class PreapprovalPage extends AbstractIolsPage{
	
	private static final Logger LOG = LogManager.getLogger(PreapprovalPage.class);
	WebDriver driver = WebDriverUtil.driver();
	static String preApprovalAmount;

	private By buildTheirCart = By.xpath("//button[@button-text='Build their cart']");
	private By backToDashboard = By.xpath("//button[@class='button link']");
	private By preApprovalAmt = By.xpath("//strong[@class='ng-binding']");

	private By logo = By.xpath("//img[@class='logo-main']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	public void clickBuildTheirCart() {
		preApprovalAmount = waitForElement(preApprovalAmt).getText();
		waitForElement(buildTheirCart).click();
		LOG.info("Clicking on Build Their cart.");
	}
	
	
}
