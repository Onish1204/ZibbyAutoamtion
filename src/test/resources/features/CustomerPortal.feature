Feature: Pay Frequency flows

   @CustomerPortal @Demo
  Scenario: Verify the customer portal functionality and navigation across screens
    Given a user login into retailer portal with a valid set of credentials username "HeyArzie" and password "test"
    Then user sees the welcome page enters customer mobile number.
    And user initiates a new application by entering phone number and verification code
    And enter demographic information with first name as "Bruce"
    Then enters last name as "Hamm"
    Then enters date of birth
    Then enters SSN or ITIN
    Then enters email ID
    Then enters billing address as "12911 Alton Sq"
    Then enter second address line as "Apt 202"
    Then enter city as "Herndon"
    Then selects state as "VA"
    Then enters zipcode as "20170" and clicks continue
    And Lands on Pay frequency
    And Enters Salary "65000" for the term  "Yearly"
    And Enters Pay Frequency as "Every other week" and dates with difference as "MoreThan5"
    And Selects Direct Deposit as "Yes"
    And hits Continue and lands on Preapproval Page
    When user adds item to cart
    And Verify the Payment due date next cycle on the payment array displays as the customer's due date and pay frequency as "Every other week"
    When retailer clicks on Proceed to Checkout and enters payment details.
    Then next due date on Pay page reflects the correct date based on the pay frequency "Every other week" and day difference "MoreThan5" .
    And pay frequency is shown as "Every other week" .
    And number of payments is shown as "26" .
    And the lease agreement generated will have the dates according to the "Every other week" pay frequency.
    Then retailer makes payment after reading the agreement and entering the signature code sent to the customer.
    When admin logs in to Lease Management System and Clicks on Applications tab
    And clicks on the ID for the order id associated with the originated lease
    And user reads buyout amount from admin portal
    And user login into customer portal
    And user verify the Dashboard page
    And user reads buyout amount from customer Dashboard
    And user navigates to Lease Details Page
    And user verify Lease Details page
    And user navigates to Payments
    And user verify Payments page
    And user navigates to Shop page
    And user verify shop page
    And user navigates to settings page
    And user verify settings page
    And user sign out of the customer portal
    When admin logs in to Lease Management System and Clicks on Applications tab
    And clicks on the ID for the order id associated with the originated lease
    And user reads buyout amount from admin portal
    Then user validates buyout amount

  @CustomerPortal @Demo
  Scenario: Verify the customer portal pre-approved scenarios

    Given a user login into retailer portal with a valid set of credentials username "HeyArzie" and password "test"
    Then user sees the welcome page enters customer mobile number.
    And user initiates a new application by entering phone number and verification code
    And enter demographic information with first name as "Bruce"
    Then enters last name as "Hamm"
    Then enters date of birth
    Then enters SSN or ITIN
    Then enters email ID
    Then enters billing address as "12911 Alton Sq"
    Then enter second address line as "Apt 202"
    Then enter city as "Herndon"
    Then selects state as "VA"
    Then enters zipcode as "20170" and clicks continue
    And Lands on Pay frequency
    And Enters Salary "65000" for the term  "Yearly"
    And Enters Pay Frequency as "Every other week" and dates with difference as "MoreThan5"
    And Selects Direct Deposit as "Yes"
    And hits Continue and lands on Preapproval Page
    And retail user logout
    And user login into customer portal
    And user validates preapproval amount
    And user validates preapproval messages

  @C1016
  Scenario: verify the payment due date for grace period more than 5
    Given a user login into retailer portal with a valid set of credentials username "HeyArzie" and password "test"
    Then user sees the welcome page enters customer mobile number.
    And user initiates a new application by entering phone number and verification code
    And enter demographic information with first name as "Bruce"
    Then enters last name as "Hamm"
    Then enters date of birth
    Then enters SSN or ITIN
    Then enters email ID
    Then enters billing address as "12911 Alton Sq"
    Then enter second address line as "Apt 202"
    Then enter city as "Herndon"
    Then selects state as "VA"
    Then enters zipcode as "20170" and clicks continue
    And Lands on Pay frequency
    And Enters Salary "65000" for the term  "Yearly"
    And Enters Pay Frequency as "Every other week" and dates with difference as "MoreThan5"
    And Selects Direct Deposit as "Yes"
    And hits Continue and lands on Preapproval Page
    When user adds item to cart
    And Verify the Payment due date next cycle on the payment array displays as the customer's due date and pay frequency as "Every other week"
    When retailer clicks on Proceed to Checkout and enters payment details.
    Then next due date on Pay page reflects the correct date based on the pay frequency "Every other week" and day difference "MoreThan5" .
    And pay frequency is shown as "Every other week" .
    And number of payments is shown as "26" .
    And the lease agreement generated will have the dates according to the "Every other week" pay frequency.
    Then retailer makes payment after reading the agreement and entering the signature code sent to the customer.
    When admin logs in to Lease Management System and Clicks on Applications tab
    And clicks on the ID for the order id associated with the originated lease
    And user reads next payment due date
    And retail user logout
    And user login into customer portal
    And user validate next payment due

  Scenario: verify the payment due date for grace period less than 5
    Given a user login into retailer portal with a valid set of credentials username "HeyArzie" and password "test"
    Then user sees the welcome page enters customer mobile number.
    And user initiates a new application by entering phone number and verification code
    And enter demographic information with first name as "Bruce"
    Then enters last name as "Hamm"
    Then enters date of birth
    Then enters SSN or ITIN
    Then enters email ID
    Then enters billing address as "12911 Alton Sq"
    Then enter second address line as "Apt 202"
    Then enter city as "Herndon"
    Then selects state as "VA"
    Then enters zipcode as "20170" and clicks continue
    And Lands on Pay frequency
    And Enters Salary "65000" for the term "Yearly"
    And Enters Pay Frequency as "Weekly" and dates with difference as "LessThan5"
    And Selects Direct Deposit as "No"
    And hits Continue and lands on Preapproval Page
    When user adds item to cart
    And Verify the Payment due date next cycle on the payment array displays as the customer's due date and pay frequency as "Every other week"
    When retailer clicks on Proceed to Checkout and enters payment details.
    Then next due date on Pay page reflects the correct date based on the pay frequency "Every other week" and day difference "MoreThan5" .
    And pay frequency is shown as "Every other week" .
    And number of payments is shown as "26" .
    And the lease agreement generated will have the dates according to the "Every other week" pay frequency.
    Then retailer makes payment after reading the agreement and entering the signature code sent to the customer.
    When admin logs in to Lease Management System and Clicks on Applications tab
    And clicks on the ID for the order id associated with the originated lease
    And user reads next payment due date
    And retail user logout
    And user login into customer portal
    And user validate next payment due
