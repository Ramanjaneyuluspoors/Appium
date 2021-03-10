Feature: Testing customer module

@Customer_creation
Scenario: Customer creation
	Given verify customer card exist or not
	Then Create a customer
	