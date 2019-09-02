package com.zibby.pages;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class AdminPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(OTPEntryPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By dashboard = By.xpath("//a[@name='dashboard']");
	private By admin = By.xpath("//a[contains(text(),'Admin')]");
	private By applications = By.xpath("//a[@name='applications']");
	private By reports = By.xpath("//a[contains(text(),'Reports')]");
	private By retailer = By.xpath("//a[@name='retailers']");
	private By partners = By.xpath("//a[@name='partners']");
	private By estimator = By.xpath("//a[@name='payment-estimator']");
	private By sweeps = By.xpath("//a[@name='sweeps']");


	private By dynamicTable = By.xpath("//table/tbody");
	private By firstOrderNum = By.xpath("//tr[2]//td[3]");
	private By uniqueOrderId = By.xpath("//tr[2]//td[2]");

	private By logo = By.xpath("//div[@class='zibby-logo']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}

	public void clickOnOrderID(String orderID) throws InterruptedException {
		List<WebElement> rows = waitForElement(dynamicTable).findElements(By.xpath("//tr[@class='ng-scope']"));
		int rowsCount = rows.size();
		System.out.println("Row Count :" + rowsCount);
		// Xpath for the cell with order number  //table/tbody/tr[2]/td[3]
		String xpath1 = "//table/tbody/tr[";
		String xpath2 = "]/td[3]";
		String idXpath = "]/td[2]/a";
		for (int i = 2; i <= rowsCount; i++) {
			String orderNumber = waitForElement(By.xpath(xpath1 + i + xpath2)).getText();
			if (orderNumber.equalsIgnoreCase(orderID)) {
				String xp = xpath1 + i + idXpath;
				System.out.println("XPATH : " + xp);
				waitForElement(By.xpath(xp)).click();
				System.out.println("IM HERE");
				break;
			}
		}
		LOG.info("Clicking on the order Id");
	}

}
