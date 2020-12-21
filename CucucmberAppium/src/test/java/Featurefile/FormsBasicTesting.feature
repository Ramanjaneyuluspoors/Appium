Feature: Performing Form Basic Testing

@formfill 
Scenario: fill form with all data types 
	Given scroll to form 
		|Forms|
	When user click on the specified form 
		|formSubmissionReport|
	Then user fill the form and save 
	And modify the form and save 
	And Navigate to homepage 
	
@form_save_and_new_then_discard_form 
Scenario: fill the form and click save and new then discard 
	Given swipe to form 
		|Forms|
	When user click on the given formname 
		|formSubmissionReport|
	Then user fill form and 
	And click save and new 
	Then Discard the form 
	
@form_save_as_draft 
Scenario: fill the form then save as draft 
	Given scroll and click on forms 
		|Forms|
	When user click on the form available in the forms 
		|formSubmissionReport|
	Then user insert form data 
	And save form as draft 
	
          