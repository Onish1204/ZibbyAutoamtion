Feature: Pay Frequency flows

  @PayFrequency @Demo
  Scenario Outline:
  Verify that the payment due date has moved to the next payment array and displays correctly across
  Cart, Contract, Customer Portal and LMS when the time difference between lease origination date and the next pay date is less than 5 business days.

    Given a user login into retailer portal with a valid set of credentials username "<userName>" and password "<password>"
    Then user sees the welcome page enters customer mobile number.
    And user initiates a new application by entering phone number and verification code
    And enter demographic information with first name as "<firstName>"
    Then enters last name as "<lastName>"
    Then enters date of birth
    Then enters SSN or ITIN
    Then enters email ID
    Then enters billing address as "<billingAddress1>"
    Then enter second address line as "<billingAddress2>"
    Then enter city as "<city>"
    Then selects state as "<state>"
    Then enters zipcode as "<zipCode>" and clicks continue
    And Lands on Pay frequency
    And Enters Salary "<salary>" for the term  "<payTerm>"
    And Enters Pay Frequency as "<payFrequency>" and dates with difference as "<dayDifference>"
    And Selects Direct Deposit as "<directDeposit>"
    And hits Continue and lands on Preapproval Page
    When user adds item to cart
    And Verify the Payment due date next cycle on the payment array displays as the customer's due date and pay frequency as "<payFrequency>"
    When retailer clicks on Proceed to Checkout and enters payment details.
    Then next due date on Pay page reflects the correct date based on the pay frequency "<payFrequency>" and day difference "<dayDifference>" .
    And pay frequency is shown as "<payFrequency>" .
    And number of payments is shown as "<numberOfPayments>" .
    And the lease agreement generated will have the dates according to the "<payFrequency>" pay frequency.
    Then retailer makes payment after reading the agreement and entering the signature code sent to the customer.
    When admin logs in to Lease Management System and Clicks on Applications tab
    And clicks on the ID for the order id associated with the originated lease
    Then the Payment Frequency in the Summary Page reflects the correct due date based on next pay date "<dayDifference>" days more than today and direct deposit as "<directDeposit>" and pay frequency as "<payFrequency>"  .
    Examples:
      | userName | password | firstName | lastName | billingAddress1 | billingAddress2 | city    | state | zipCode | salary | payTerm | dayDifference | directDeposit | numberOfPayments | payFrequency     |
		#More Than 5
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | Yes           | 26               | Every other week |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | Yes           | 24               | Twice a month    |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | Yes           | 12               | Monthly          |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | Yes           | 26               | Other            |
#		#
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | No            | 26               | Every other week |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | No            | 24               | Twice a month    |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | No            | 12               | Monthly          |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | MoreThan5     | No            | 26               | Other            |
#		#
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | Yes           | 26               | Every other week |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | Yes           | 24               | Twice a month    |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | Yes           | 12               | Monthly          |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | Yes           | 26               | Other            |
		#
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | No            | 26               | Every other week |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | No            | 24               | Twice a month    |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | No            | 12               | Monthly          |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | EqualTo5      | No            | 26               | Other            |
##		#
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | None          | Yes           | 52               | Weekly           |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | None          | No            | 52               | Weekly           |
#		#
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | Yes           | 26               | Every other week |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | Yes           | 24               | Twice a month    |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | Yes           | 12               | Monthly          |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | Yes           | 26               | Other            |
#		#
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | No            | 26               | Every other week |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | No            | 24               | Twice a month    |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | No            | 12               | Monthly          |
      | HeyArzie | test     | Bruce     | Hamm     | 12911 Alton Sq  | Apt 202         | Herndon | VA    | 20170   | 65000  | Yearly  | LessThan5     | No            | 26               | Other            |