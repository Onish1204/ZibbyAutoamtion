package com.zibby.pages;

import java.text.ParseException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import com.zibby.enums.ui.DayDifferenceMap;
import com.zibby.testdata.DateUtil;

public class AdminSummaryPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(OTPEntryPage.class);
	WebDriver driver = WebDriverUtil.driver();
	

	private By logo = By.xpath("//div[@class='zibby-logo']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}
	
	private By payFrequency = By.xpath("(//div[@class='grid-content text-right tile-row-value ' and @ng-bind='value | noData'])[6]");
	private By lastPaymentDate = By.xpath("(//div[@class='grid-content text-right tile-row-value ' and @ng-bind='value | noData'])[7]");
	private By nextPaymentDate = By.xpath("(//div[@class='grid-content text-right tile-row-value ' and @ng-bind='value | noData'])[8]");
	private By directDeposit = By.xpath("(//div[@class='grid-content text-right tile-row-value ' and @ng-bind='value | noData'])[9]");

	public void validatePayFrequency(String expectedPayFrequency) {
		Assert.assertEquals(expectedPayFrequency, waitForElement(payFrequency).getText());
	}
	
	public void validateLastPaymentDate(String payFrequency, String dayDifference) throws ParseException {
		String expectedLastPaymentDate = DateUtil.dateGenerator(payFrequency, DayDifferenceMap.returnDayDiff().get(dayDifference)).get("lastPayDate");
		expectedLastPaymentDate = DateUtil.addDaysToDate(expectedLastPaymentDate, 0);
		String actualLastPaymentDate = waitForElement(lastPaymentDate).getText();
		actualLastPaymentDate = DateUtil.formatDate(actualLastPaymentDate);
		Assert.assertEquals(expectedLastPaymentDate, actualLastPaymentDate);
	
	}
	
	public void validateNextPaymentDate(String payFrequency, String dayDifference) throws ParseException {
		String expectedLastPaymentDate = DateUtil.dateGenerator(payFrequency, DayDifferenceMap.returnDayDiff().get(dayDifference)).get("nextPayDate");
		expectedLastPaymentDate  = DateUtil.addDaysToDate(expectedLastPaymentDate, 0);
		String actualNextPaymentDate = waitForElement(nextPaymentDate).getText();
		actualNextPaymentDate = DateUtil.formatDate(actualNextPaymentDate);
		Assert.assertEquals(expectedLastPaymentDate, actualNextPaymentDate);
	
	}
	
	public void validateDirectDeposit(String expectedDirectDeposit) {
		if (expectedDirectDeposit.equals("Yes")) {
			expectedDirectDeposit = "true";
		} else {
			expectedDirectDeposit = "Info not available";
		}
		Assert.assertEquals(expectedDirectDeposit, waitForElement(directDeposit).getText());
	}
}
