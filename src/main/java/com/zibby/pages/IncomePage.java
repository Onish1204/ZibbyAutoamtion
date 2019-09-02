package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class IncomePage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(IncomePage.class);
	WebDriver driver = WebDriverUtil.driver();
	

	private By income = By.xpath("//*[@type='tel' and @name='income']");
	private By payFrequency = By.xpath("//select[@name='pay_frequency']");
	private By lastPayDate = By.xpath("//input[@name='last_pay_date']");
	private By nextPayDate = By.xpath("//input[@name='next_pay_date']");
	private By ddYes = By.xpath("//input[@type='radio' and @value='yes']");
	private By ddNo = By.xpath("//input[@type='radio' and @value='no']");
	private By continueBtn = By.xpath("//button[@type='submit']");
	private By backBtn = By.xpath("//button[contains(text(),'Back')]");

	private By logo = By.xpath("//img[@class='logo-main']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}

	public void enterIncome(String customerIncome) {
		waitForElement(income).sendKeys(customerIncome);
		LOG.info("Entering customer income.");
	}

	public void selectPayFrequency(String custPayFrequency) {
		Select drpPayFreq = new Select(driver.findElement(payFrequency));
		drpPayFreq.selectByVisibleText(custPayFrequency);
		LOG.info("Selecting customer pay frequency.");
	}

	public void selectLastPayDate(String previousPayDate) {
		waitForElement(lastPayDate).sendKeys(previousPayDate);
		
	}

	public void selectNextPayDate(String nextDate) {
		waitForElement(nextPayDate).sendKeys(nextDate);
	}

	public void selectDirectDeposit(String value) {
		if (value.equals("Yes")) {
			waitForElement(ddYes).click();
		} else {
			waitForElement(ddNo).click();
		}
	}

	public void continueBtn() {
		waitForElement(continueBtn).click();
	}
}
