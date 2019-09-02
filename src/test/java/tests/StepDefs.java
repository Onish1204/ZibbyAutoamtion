package tests;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import com.zibby.pages.*;
import cucumber.api.java.en.And;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.zibby.auto.AbstractPage;
import com.zibby.auto.Environment;
import com.zibby.auto.PageUtil;
import com.zibby.auto.WebDriverUtil;
import com.zibby.enums.ui.CartDetailsInputData;
import com.zibby.enums.ui.DayDifferenceMap;
import com.zibby.testdata.DateUtil;
import com.zibby.testdata.RandomDataUtil;
import com.zibby.util.ScreenShotUtil;
import com.zibby.util.WebDriverNavigation;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefs {

	public LoginPage loginPage;
	public OTPPage otpPage;
	public OTPEntryPage otpEntryPage;
	public BasicInfoPage basicInfoPage;
	public IncomePage incomePage;
	public ReviewPage reviewPage;
	public PreapprovalPage preapprovalPage;
	public ShoppingCartPage shoppingCartPage;
	public PaySignSubmitPage paySignSubmitPage;
	public SuccessPage successPage;
	public AdminPage adminPage;
	public AdminSummaryPage adminSummaryPage;
	public AdminApplicationPage adminApplicationPage;
	public CustomerDashboardPage customerDashboardPage;
	public CustomerLeasedetailsPage customerLeasedetailsPage;
	public CustomerPaymentsPage customerPaymentsPage;
	public CustomerShopPage customerShopPage;
	public CustomerSettingsPage customerSettingsPage;
	
	private Scenario scenario;

	WebDriver driver = WebDriverUtil.driver();
	private static final Logger LOG = LogManager.getLogger(StepDefs.class);
	HashMap<String, String> payDates = new HashMap<String, String>();
	String orderID = RandomDataUtil.getRandomOrderID();

	@Given("^a user login into retailer portal with a valid set of credentials username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void user_login_into_retailer_portal_with_a_valid_set_of_credentials_username_and_password(String username,
			String password) throws InterruptedException {
		// Thread.sleep(2000);
		WebDriverNavigation.navigateToURL(Environment.getBaseUrl());
		loginPage = AbstractPage.install(LoginPage.class);
		PageUtil.waitForPagetoLoad(5);
		loginPage.waitForPageLoad();
		loginPage.performRetailerLogin(username, password);
	}

	@Then("^user sees the welcome page enters customer mobile number\\.$")
	public void user_sees_the_welcome_page_enters_customer_mobile_number() throws InterruptedException {
		otpPage = AbstractPage.install(OTPPage.class);
		otpPage.waitForPageLoad();
		otpPage.enterCustomerMobileNumber();
		otpPage.checksmsPolicy();
		otpPage.checksmsPolicy2();
		otpPage.clickContinue();
	}

	@Then("^user initiates a new application by entering phone number and verification code$")
	public void user_initiates_a_new_application_by_entering_phone_number_and_verification_code() {
		otpEntryPage = AbstractPage.install(OTPEntryPage.class);
		otpEntryPage.waitForPageLoad();
		otpEntryPage.enterSixDigitCode();
		otpEntryPage.beginApplication();
	}

	@Then("^enter demographic information with first name as \"([^\"]*)\"$")
	public void enter_demographic_information_with_first_name_as(String firstName) {
		basicInfoPage = AbstractPage.install(BasicInfoPage.class);
		basicInfoPage.waitForPageLoad();
		basicInfoPage.enterFirstName(firstName);
	}

	@Then("^enters last name as \"([^\"]*)\"$")
	public void enters_last_name_as(String lastName) {
		basicInfoPage.enterLastName(lastName);
	}

	@Then("^enters date of birth$")
	public void enters_date_of_birth() {
		basicInfoPage.enterDateOfBirth();
	}

	@Then("^enters SSN or ITIN$")
	public void enters_SSN_or_ITIN() {
		basicInfoPage.enterSSN();
	}

	@Then("^enters email ID$")
	public void enters_email_ID() {
		basicInfoPage.enterEmail();
	}

	@Then("^enters billing address as \"([^\"]*)\"$")
	public void enters_billing_address_as(String addressLine1) {
		basicInfoPage.enterBillingAddress(addressLine1);
	}

	@Then("^enter second address line as \"([^\"]*)\"$")
	public void enter_second_address_line_as(String addressLine2) {
		basicInfoPage.enterBillingAddress2(addressLine2);
	}

	@Then("^enter city as \"([^\"]*)\"$")
	public void enter_city_as(String city) {
		basicInfoPage.enterCity(city);
	}

	@Then("^selects state as \"([^\"]*)\"$")
	public void selects_state_as(String state) {
		basicInfoPage.selectState(state);
	}

	@Then("^enters zipcode as \"([^\"]*)\" and clicks continue$")
	public void enters_zipcode_as_and_clicks_continue(String zipCode) {
		basicInfoPage.enterZipCode(zipCode);
		basicInfoPage.clickContinue();
	}

	@Then("^Lands on Pay frequency$")
	public void lands_on_Pay_frequency() {
		incomePage = AbstractPage.install(IncomePage.class);
		incomePage.waitForPageLoad();
	}

	@Then("^Enters Salary \"([^\"]*)\" for the term \"([^\"]*)\"$")
	public void enters_Salary_for_the_term(String salary, String payTerm) {
		incomePage.enterIncome(salary);
	}

	@Then("^Enters Pay Frequency as \"([^\"]*)\" and dates with difference as \"([^\"]*)\"$")
	public void enters_Pay_Frequency_as_and_dates_with_difference_as(String payFrequency, String dayDifference) {
		incomePage.selectPayFrequency(payFrequency);
		payDates = DateUtil.dateGenerator(payFrequency, DayDifferenceMap.returnDayDiff().get(dayDifference));
		incomePage.selectLastPayDate(payDates.get("lastPayDate"));
		incomePage.selectNextPayDate(payDates.get("nextPayDate"));
	}

	@Then("^Enters Pay Frequency as \"([^\"]*)\"$")
	public void enters_Pay_Frequency_as(String payFrequency) {
		incomePage.selectPayFrequency(payFrequency);
		payDates = DateUtil.dateGen(payFrequency);
		incomePage.selectLastPayDate(payDates.get("lastPayDate"));
		incomePage.selectNextPayDate(payDates.get("nextPayDate"));
	}

	@Then("^Selects Direct Deposit as \"([^\"]*)\"$")
	public void selects_Direct_Deposit_as(String directDeposit) {
		incomePage.selectDirectDeposit(directDeposit);
	}

	@Then("^hits Continue and lands on Preapproval Page$")
	public void hits_Continue_and_lands_on_Preapproval_Page() throws InterruptedException {

		incomePage.continueBtn();
		reviewPage = AbstractPage.install(ReviewPage.class);
		reviewPage.waitForPageLoad();
		reviewPage.clickSubmitBtn();
		preapprovalPage = AbstractPage.install(PreapprovalPage.class);
		preapprovalPage.waitForPageLoad();
		preapprovalPage.clickBuildTheirCart();
	}

	@When("^user adds item to cart$")
	public void user_adds_item_to_cart() throws Throwable {
		shoppingCartPage = AbstractPage.install(ShoppingCartPage.class);
		shoppingCartPage.waitForPageLoad();
		HashMap<String, String> cartDetails = CartDetailsInputData.Sample1.getSampleData();
		shoppingCartPage.enterSalesRepName(cartDetails.get("Sales Rep Name"));
		shoppingCartPage.enterOrderNumber(orderID);
		shoppingCartPage.selectDeliveryMethod(cartDetails.get("DeliveryMethod"));
		shoppingCartPage.selectItemType(cartDetails.get("ItemType"));

		shoppingCartPage.enterCashPrice(cartDetails.get("CashPrice"));
		shoppingCartPage.enterModelNumber(cartDetails.get("ModelNumber"));

		shoppingCartPage.enterItemDescription(cartDetails.get("ItemDescription"));
	}

	@When("^Verify the Payment due date next cycle on the payment array displays as the customer's due date and pay frequency as \"([^\"]*)\"$")
	public void verify_the_Payment_due_date_next_cycle_on_the_payment_array_displays_as_the_customer_s_due_date_and_pay_frequency_as(
			String payFrequency) throws InterruptedException {
		shoppingCartPage.clickAddItem();
		shoppingCartPage.validateNextPaymentDueDate(payFrequency);
		PageUtil.waitForPagetoLoad(2);
	}

	@When("^retailer clicks on Proceed to Checkout and enters payment details\\.$")
	public void retailer_clicks_on_Proceed_to_Checkout_and_enters_payment_details() throws InterruptedException {
		Thread.sleep(3000);
		shoppingCartPage.clickProceedToCheckout();
		paySignSubmitPage = AbstractPage.install(PaySignSubmitPage.class);
		paySignSubmitPage.waitForPageLoad();
		paySignSubmitPage.enterCardNumber("41111111111111111");
		paySignSubmitPage.enterCardExpiration("12/20");
		paySignSubmitPage.enterCardCVV("555");
		paySignSubmitPage.checkCustPriceTagCheckBox();
		paySignSubmitPage.checkTestfasPicIDgCheckBox();
	}

	@Then("^next due date on Pay page reflects the correct date based on the pay frequency \"([^\"]*)\" and day difference \"([^\"]*)\" \\.$")
	public void next_due_date_on_Pay_page_reflects_the_correct_date_based_on_the_pay_frequency_and_day_difference(
			String payFrequency, String dayDifference) {
		paySignSubmitPage.validateNextDueDate(payDates.get("nextPayDate"), payFrequency, dayDifference);
	}

	@Then("^pay frequency is shown as \"([^\"]*)\" \\.$")
	public void pay_frequency_is_shown_as(String payFrequency) {
		paySignSubmitPage.validatePayFrequency(payFrequency);
	}

	@Then("^number of payments is shown as \"([^\"]*)\" \\.$")
	public void number_of_payments_is_shown_as(String numberOfPayments) {
		paySignSubmitPage.validateNumberOfPayments(numberOfPayments);
	}

	@Then("^the lease agreement generated will have the dates according to the \"([^\"]*)\" pay frequency\\.$")
	public void the_lease_agreement_generated_will_have_the_dates_according_to_the_pay_frequency(String payFrequency) {
		try {
			paySignSubmitPage.clickOnReadLease();
		} catch (InterruptedException e) {
			LOG.error("Unable to Click on the lease agreement link.");
		}
		paySignSubmitPage.validateSection3(payFrequency);
	}

	@Then("^retailer makes payment after reading the agreement and entering the signature code sent to the customer\\.$")
	public void retailer_makes_payment_after_reading_the_agreement_and_entering_the_signature_code_sent_to_the_customer() {
		try {
			paySignSubmitPage.scrollToBottom();
			paySignSubmitPage.enterCustomerSignatureCode();
			paySignSubmitPage.clickSubmitBtn();
			Thread.sleep(2000);
			paySignSubmitPage.clickMakePaymentBtn();
			Thread.sleep(2000);
			successPage = AbstractPage.install(SuccessPage.class);
			successPage.waitForPageLoad();
			successPage.validateSuccessMessage();
			successPage.clickOnLogOut();

		} catch (InterruptedException | SQLException e) {
			LOG.error("Exception occured while scrolling the lease and entering the customer signature code.");
		}
	}

	@When("^admin logs in to Lease Management System and Clicks on Applications tab$")
	public void admin_logs_in_to_Lease_Management_System_and_Clicks_on_Applications_tab() {
		try {
			loginPage = AbstractPage.install(LoginPage.class);
			Thread.sleep(2000);
			loginPage.performRetailerLogin("nadim.cognical", "test");
		} catch (InterruptedException e) {
			LOG.error("Unable to login");
		}
		adminPage = AbstractPage.install(AdminPage.class);
		adminPage.waitForPageLoad();
	}

	@When("^clicks on the ID for the order id associated with the originated lease$")
	public void clicks_on_the_ID_for_the_order_id_associated_with_the_originated_lease() throws InterruptedException {
		adminPage.clickOnOrderID(orderID);
	}

	@Then("^the Payment Frequency in the Summary Page reflects the correct due date based on next pay date \"([^\"]*)\" days more than today and direct deposit as \"([^\"]*)\" and pay frequency as \"([^\"]*)\"  \\.$")
	public void the_Payment_Frequency_in_the_Summary_Page_reflects_the_correct_due_date_based_on_next_pay_date_days_more_than_today_and_direct_deposit_as_and_pay_frequency_as(
			String dayDifference, String directDeposit, String payFrequency) throws ParseException {
		adminSummaryPage = AbstractPage.install(AdminSummaryPage.class);
		adminSummaryPage.waitForPageLoad();
		adminSummaryPage.validatePayFrequency(payFrequency);
		adminSummaryPage.validateLastPaymentDate(payFrequency, dayDifference);
		adminSummaryPage.validateNextPaymentDate(payFrequency, dayDifference);
		adminSummaryPage.validateDirectDeposit(directDeposit);
	}

	/*
	 * @Before public void startup(Scenario scenario) { LOG.info(
	 * "Before cucumber scenario: ********** {} **********" +
	 * scenario.getName()); }
	 */

	/**
	 * Runs after every Cucumber test scenario.
	 *
	 * @param scenario
	 *            The test scenario that has run
	 */
	/*
	 * @After public void teardown(Scenario scenario) {
	 * ScreenShotUtil.embedScreenshot(scenario); LOG.info(
	 * "After cucumber scenario: ********** {} **********" +
	 * scenario.getName()); }
	 */
	@And("^user login into customer portal$")
	public void userLoginIntoCustomerPortal() throws InterruptedException {
		loginPage = AbstractPage.install(LoginPage.class);
		loginPage.waitForPageLoad();
		loginPage.performCustomerLogin(OTPPage.mobileNo);
	}

	@And("^user verify the Dashboard page$")
	public void userVerifyTheDashboardPage() throws InterruptedException {
		customerDashboardPage = AbstractPage.install(CustomerDashboardPage.class);
		customerDashboardPage.verifyPage();
	}

	@And("^user navigates to Lease Details Page$")
	public void userNavigatesToLeaseDetails() throws InterruptedException {
		customerDashboardPage.navigateLeaseDetails();
	}

	@And("^user verify Lease Details page$")
	public void userVerifyLeaseDetailsPage() throws InterruptedException {
		customerLeasedetailsPage = AbstractPage.install(CustomerLeasedetailsPage.class);
		customerLeasedetailsPage.verifyPage();
	}

	@And("^user navigates to Payments$")
	public void userNavigatesToPayments() throws InterruptedException {
		customerLeasedetailsPage.navigatePayments();
	}

	@And("^user verify Payments page$")
	public void userVerifyPaymentsPage() throws InterruptedException {
		customerPaymentsPage = AbstractPage.install(CustomerPaymentsPage.class);
		customerPaymentsPage.verifyPage();
	}

	@And("^user navigates to Shop page$")
	public void userNavigatesToShopPage() throws InterruptedException {
		customerPaymentsPage.navigateShop();
	}

	@And("^user verify shop page$")
	public void userVerifyShopPage() throws InterruptedException {
		customerShopPage = AbstractPage.install(CustomerShopPage.class);
		customerShopPage.verifyPage();
	}

	@And("^user navigates to settings page$")
	public void userNavigatesToSettingsPage() throws InterruptedException {
		customerShopPage.navigateSettings();
	}

	@And("^user verify settings page$")
	public void userVerifySettingsPage() throws InterruptedException {
		customerSettingsPage = AbstractPage.install(CustomerSettingsPage.class);
		customerSettingsPage.verifyPage();
	}

	@And("^user sign out of the customer portal$")
	public void userSignOutOfTheCustomerPortal() throws InterruptedException {
		customerSettingsPage.signOut();
	}

	@And("^user reads buyout amount from admin portal$")
	public void userReadsBuyoutAmountFromAdminPortal() throws InterruptedException {
		adminApplicationPage = AbstractPage.install(AdminApplicationPage.class);
		adminApplicationPage.readBuyoutAmount();
	}

	@And("^user reads buyout amount from customer Dashboard$")
	public void userReadsCustomerBuyoutAmount() throws InterruptedException {
		customerDashboardPage.readbuyoutAmount();
	}

	@Then("^user validates buyout amount$")
	public void userValidatesBuyoutAmount() {
		adminApplicationPage.validateBuyoutAmount();
	}

	@And("^retail user logout$")
	public void retailUserLogout() {
		basicInfoPage.logoutRetailUser();
	}

	@And("^user validates preapproval amount$")
	public void userValidatesThePreapprovalAmount() {
		customerDashboardPage = AbstractPage.install(CustomerDashboardPage.class);
		PageUtil.waitForPagetoLoad(10);
		customerDashboardPage.waitForPageLoad();		
		customerDashboardPage.validatePreApprovalAmount();
	}

	@And("^user validates preapproval messages$")
	public void userValidatesPreapprovalMessages() {
		customerDashboardPage.validatePreApprovalMessages();
	}

	@And("^user validate next payment due$")
	public void userValidateNextPaymentDue() {
		customerDashboardPage.validateNextPaymentDue();
	}

	@And("^user reads next payment due date$")
	public void userReadsNextPaymentDueDate() {
		adminApplicationPage.getNextDueDate();
	}
}
