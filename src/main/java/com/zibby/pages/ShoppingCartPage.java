package com.zibby.pages;

import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.zibby.auto.AbstractIolsPage;
import com.zibby.auto.WebDriverUtil;

public class ShoppingCartPage extends AbstractIolsPage {

	private static final Logger LOG = LogManager.getLogger(ShoppingCartPage.class);
	WebDriver driver = WebDriverUtil.driver();

	private By salesRepresentativeName = By.xpath("//*[@name='salesrep']");
	private By orderNumber = By.xpath("//*[@name='orderId']");
	private By deliveryMethodDelivery = By.xpath("//*[@type='radio' and @value='delivery']");
	private By deliveryMethodInstore = By.xpath("//*[@type='radio' and @value='pickup']");
	private By itemType = By.xpath("//select[@ng-model]");
	private By quantity = By.xpath("//*[@type='number' and @name='quantity']");
	private By cashPrice = By.xpath("//*[@name='retailer_price']");
	private By makeModelNumber = By.xpath("//*[@name='item_code']");
	private By itemDescription = By.xpath("//*[@name='item_name']");
	private By addWarranty = By.xpath("//*[@type='checkbox']");
	private By warrantyPrice = By.xpath("//*[@type='text' and @name='warrantyPrice']");
	private By warrantyName = By.xpath("//*[@type='text' and @name='warrantyName']");
	private By btnAddItem = By.xpath("//button[@button-text='Add item']");
	private By btnProceedToCheckout = By.xpath("//button[@button-text='Proceed to checkout']");

	// validation Points
	private By nextDueDateValue = By.xpath("(//div[@class='grid-content text-right tile-row-value '])[2]");
	private By calculatedPayFrequency = By
			.xpath("//div[@class='grid-content text-right tile-row-value small-6 ng-binding']");

	private By logo = By.xpath("//img[@class='logo-main']");

	@Override
	protected By defineUniqueElement() {
		return logo;
	}

	public void enterSalesRepName(String name) {
		waitForElement(salesRepresentativeName).sendKeys(name);
		LOG.info("Entering Sales representative name");
	}

	public void enterOrderNumber(String orderNum) {
		waitForElement(orderNumber).sendKeys(orderNum);
		LOG.info("Entering order number");
	}
	
	// Helper Method for generating random and unique OrderID.
	protected static String getRandomOrderID() {
		String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder ID = new StringBuilder();
		Random rnd = new Random();
		while (ID.length() < 5) {
			int index = (int) (rnd.nextFloat() * validChars.length());
			ID.append(validChars.charAt(index));
		}
		String orderID = ID.toString();
		return orderID;
	}

	public void selectDeliveryMethod(String method) {
		if (method.equals("Delivery")) {
			waitForElement(deliveryMethodDelivery).click();
			LOG.info("Selecting delivery method as Delivery");
		} else {
			waitForElement(deliveryMethodInstore).click();
			LOG.info("Selecting delivery method as In-store Pickup");
		}
	}

	public void selectItemType(String item) {
		Select drpItemType = new Select(driver.findElement(itemType));
		drpItemType.selectByValue(item);
		LOG.info("Selecting Item type as " + item);
	}

	public void enterQuantity(String q) {
		waitForElement(quantity).sendKeys(q);
		LOG.info("Entering quantity.");
	}

	public void enterCashPrice(String amount) {
		waitForElement(cashPrice).sendKeys(amount);
		LOG.info("Entering cashPrice.");
	}

	public void enterModelNumber(String modelNum) {
		waitForElement(makeModelNumber).sendKeys(modelNum);
		LOG.info("Entering model number");
	}

	public void enterItemDescription(String description) {
		waitForElement(itemDescription).sendKeys(description);
		LOG.info("Entering item Description.");
	}

	public void clickAddItem() {
		waitForElement(btnAddItem).click();
		LOG.info("Adding item to cart.");
	}

	public void clickProceedToCheckout() {
		waitForElementClickable(btnProceedToCheckout).click();
		LOG.info("Proceeding to checkout.");
	}

	// Validating payment due date and pay frequency.
	public void validateNextPaymentDueDate(String payFrequency) {
		/*
		 * String actualDueDate = waitForElement(nextDueDateValue).getText();
		 * System.out.println("ACTUAL DUE DATE" + actualDueDate); String expectedDueDate
		 * = DateUtil.dateGen(payFrequency).get("nextPayDate");
		 * Assert.assertEquals(expectedDueDate, actualDueDate);
		 */

		String actualPayFrequency = waitForElement(calculatedPayFrequency).getText();
		String expectedPayFrequency = payFrequency;
		Assert.assertEquals(expectedPayFrequency, actualPayFrequency);

	}
}
