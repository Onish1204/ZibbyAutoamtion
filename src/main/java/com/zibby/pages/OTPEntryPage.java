package com.zibby.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;
import com.zibby.database.PostgresDBConnection;

public class OTPEntryPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(OTPEntryPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By sixDigitCode = By.xpath("//*[@type='tel']");
	private By resendBtn = By.xpath("//button[@class='button'][.//span[contains(text(), 'Re-send')]]");
	private By beginAppBtn = By.xpath("//button[@type='submit']");

	private By logo = By.xpath("//img[@class='logo-main']");

	PostgresDBConnection dbConn = new PostgresDBConnection();

	@Override
	protected By defineUniqueElement() {
		return logo;
	}

	public void enterSixDigitCode() {
		
		waitForElement(sixDigitCode).sendKeys("616805");
		/*try {
			String sixDgtCode = dbConn.selectQuery();
			System.out.println(":::::::::::: SixDigitCode" + sixDgtCode);
			waitForElement(sixDigitCode).sendKeys(sixDgtCode);

			LOG.info("Entering Six digit code sent to Mobile number, fetched from the database.");

		} catch (SQLException e) {
			LOG.error("Unable to fetch the code from DB");
		}*/

	}

	public void beginApplication() {
		waitForElement(By.xpath("//button[@type='submit']")).click();
		LOG.info("Clicked on Begin Application.");
	}

}
