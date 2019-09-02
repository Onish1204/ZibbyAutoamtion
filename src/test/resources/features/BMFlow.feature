Feature: Brick and Mortar Flow 

@C44 @BMFlow @CartFlow 
Scenario Outline: 
	BM Cart Flow
	Given a user with a valid set of credentials username "<userName>" and password "<password>" 
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
	And Enters Pay Frequency as "<payFrequency>" 
	And Selects Direct Deposit as "<directDeposit>" 
	And hits Continue and lands on Preapproval Page 
	When user adds item to cart 
	And Verify the Payment due date next cycle on the payment array displays as the customer's due date and pay frequency as "<payFrequency>" 
	When user Navigates to Checkout 
	
	Examples: 
		|userName|password|firstName|lastName|billingAddress1|billingAddress2|city|state|zipCode|salary|payTerm|payFrequency|directDeposit|
		|HeyArzie|test|Bruce|Borges|12911 Alton Sq|Apt 202|Herndon|VA|20170|65000|Yearly|Weekly|Yes|
		
	Scenario Outline: 
		BM Flow - User enters and invalid Verfication Code when initiating an application
		Given a user with a valid set of credentials username "<userName>" and password "<password>" 
		Then user sees the welcome page enters customer mobile number. 
		And user initiates a new application by entering phone number and verification code 
		Then user sees and error message "<errorMessage>" on the Verification code entry page. 
		Examples: 
		|userName|password|errorMessage|
		|HeyArzie|test|errorMessage|
