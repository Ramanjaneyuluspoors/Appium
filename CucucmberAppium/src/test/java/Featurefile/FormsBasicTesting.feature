Feature: Performing Form Basic Testing

@formfill
Scenario: fill form with all data types
	Given scroll to form
	When user fill the form and save
	Then modify the form and save