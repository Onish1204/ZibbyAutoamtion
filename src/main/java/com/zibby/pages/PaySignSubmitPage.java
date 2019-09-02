package com.zibby.pages;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.JSUtil;
import com.zibby.auto.WebDriverUtil;
import com.zibby.database.PostgresDBConnection;
import com.zibby.enums.ui.DayDifferenceMap;
import com.zibby.testdata.DateUtil;
import com.zibby.util.ScreenShotUtil;

public class PaySignSubmitPage extends AbstractIolsPage {
	private static final Logger LOG = LogManager.getLogger(PaySignSubmitPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By cardNumber = By.xpath("//*[@type='tel' and @name='CardNumber']");
	private By cardExpiration = By.xpath("//*[@type='tel' and @name='CardExpiration']");
	private By cardCVV = By.xpath("//*[@type='tel' and @name='CardCvv']");

	private By CustPriceTagCheckBox = By.xpath("//*[@type='checkbox' and @name='Disclosure']");
	private By TestfasPicIDgCheckBox = By.xpath("//*[@type='checkbox' and @name='Merchant']");

	private By makePaymentBtn = By.xpath("//button[@button-text='Make payment' and @type='submit']");
	private By clickToReadAndSignLeaseAgreement = By.xpath("//span[@class='cta']");
	private By viewMoreDetailsBtn = By.xpath("//button[@type='submit']");
	private By btnSubmit = By.xpath("//button[@button-text='Submit']");
	private By customerSignatureCode = By.xpath("//input[@type='text']");

	@FindBy(xpath = "(//li[contains(text(),'plus tax')])[last()]")
	private WebElement lastElementInAgreement;

	// Validation Points:
	private By payFrequency = By.xpath("//div[@class='grid-content text-right tile-row-value small-6 ng-binding']");
	private By nextDueDate = By.xpath("(//div[@class='grid-content text-right tile-row-value '])[2]");
	private By numberOfPayments = By.xpath("(//div[@class='grid-content text-right tile-row-value '])[4]");

	private By section3LeaseAgreement = By
			.xpath("//strong[contains(text(),'3. Lease Term and Payment Schedule: This Agreement')]");

	private By logo = By.xpath("//img[@class='logo-main']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}

	public void enterCardNumber(String customerCardNumber) {
		waitForElement(cardNumber).sendKeys(customerCardNumber);
		LOG.info("Entering cardNumber.");
	}

	public void enterCardExpiration(String customerCardExpiration) {
		waitForElement(cardExpiration).sendKeys(customerCardExpiration);
		LOG.info("Entering cardExpiration.");
	}

	public void enterCardCVV(String customerCardCVV) {
		waitForElement(cardCVV).sendKeys(customerCardCVV);
		LOG.info("Entering cardCVV.");
	}

	public void checkCustPriceTagCheckBox() {
		waitForElement(CustPriceTagCheckBox).click();
		LOG.info("Entering CustPriceTagCheckBox.");
	}

	public void checkTestfasPicIDgCheckBox() {
		waitForElement(TestfasPicIDgCheckBox).click();
		LOG.info("Entering TestfasPicIDgCheckBox.");
	}

	public void clickSubmitBtn() throws InterruptedException {
		Thread.sleep(400);
		waitForElement(btnSubmit).click();
		LOG.info("Click Submit.");
	}

	public void clickMakePaymentBtn() throws InterruptedException {
		Thread.sleep(200);
		waitForElement(makePaymentBtn).click();
		LOG.info("Click makePaymentBtn.");
	}

	public void clickViewMoreDetailsBtn() {
		waitForElement(viewMoreDetailsBtn).click();
		LOG.info("Click viewMoreDetailsBtn.");
	}

	public void clickOnReadLease() throws InterruptedException {
		waitForElement(clickToReadAndSignLeaseAgreement).click();
		Thread.sleep(800);
		LOG.info("Clicking on Read and Sign Lease");
	}

	public void scrollToBottom() {
		JSUtil.getJSExecutor().executeScript("arguments[0].scrollIntoView(true);", lastElementInAgreement);
		LOG.info("Scrolling to the bottom of the lease agreement.");
	}

	public void enterCustomerSignatureCode() throws SQLException, InterruptedException {
		String code = PostgresDBConnection.selectQuery("code");
		Thread.sleep(300);
		waitForElement(customerSignatureCode).sendKeys(code);
		LOG.info("Entering Customer Signature Code.");
	}

	public void validatePayFrequency(String expectedPayFrequency) {
		// ScreenShotUtil.takeScreenshotScrolling();
		String actualPayFrequency = waitForElement(payFrequency).getText();
		Assert.assertEquals(expectedPayFrequency, actualPayFrequency);

	}

	public void validateNumberOfPayments(String expectedNumOfPayments) {
		String actualNumOfPayments = waitForElement(numberOfPayments).getText();
		Assert.assertEquals(expectedNumOfPayments, actualNumOfPayments);

	}

	public void validateNextDueDate(String expectedNextDueDate, String payFrequency, String dayDiff) {
		String actualDueDate = waitForElement(nextDueDate).getText();
		System.out.println("Expected Next Due Date" + expectedNextDueDate);
		int days = DayDifferenceMap.returnDaysPayFreq().get(payFrequency);
		if (dayDiff.equals("MoreThan5")) {
			if (payFrequency.equals("Monthly")) {
				days = 1;
			} else if (payFrequency.equals("Other")) {
				days = 4;
			} else {
				days = 0;
			}
		} else if (dayDiff.equals("EqualTo5")) {
			if (payFrequency.equals("Monthly")) {
				days = 2;
			} else if (payFrequency.equals("Other")) {
				days = -10;
			} else if (payFrequency.equals("Twice a month")) {
				days = 1;
			}
		}

		String expectedDueDate = DateUtil.addDaysToDate(expectedNextDueDate, days);
		Assert.assertEquals(expectedDueDate, actualDueDate);
	}

	public void validateSection3(String payFrequency) {
		String section3 = waitForElement(section3LeaseAgreement).getText();
		String[] dates = getDatesFromString(section3);
		int dayDifference = DayDifferenceMap.returnDaysPayFreq().get(payFrequency);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf.parse(dates[0]);
			d2 = sdf.parse(dates[1]);
		} catch (ParseException e) {
			LOG.error("Unable to parse dates.");
		}
		int actualDiff = DateUtil.getDifferenceDays(d1, d2);
		Assert.assertEquals(dayDifference, actualDiff);

	}

	// Utility Method to fetch dates from Section 3 of the lease agreement.
	public static String[] getDatesFromString(String desc) {
		int count = 0;
		String[] allMatches = new String[2];
		Matcher m = Pattern.compile("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d").matcher(desc);
		while (m.find()) {
			allMatches[count] = m.group();
			count++;
		}
		return allMatches;
	}

}
