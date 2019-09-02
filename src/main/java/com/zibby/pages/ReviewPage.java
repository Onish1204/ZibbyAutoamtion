package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class ReviewPage extends AbstractIolsPage{

	private static final Logger LOG = LogManager.getLogger(ReviewPage.class);
	WebDriver driver = WebDriverUtil.driver();
	
	private By basicInformation = By.xpath("//div[@class='review-header']/h2[contains(text(),'Basic Information')]");
	private By submitBtn = By.xpath("//button[@button-text='Submit']");
	

	private By logo = By.xpath("//img[@class='logo-main']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	public void clickSubmitBtn() {
		waitForElement(submitBtn).click();
		LOG.info("Clicking submit.");
	}
	
}
