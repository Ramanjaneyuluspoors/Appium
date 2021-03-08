@forms_basic_ui 
Feature: Performing Form Basic Testing 

@formfill_and_modify_form 
Scenario Outline: fill form with all data types 
	Given scroll to form "<cardName>" 
	When user click on the specified form "<formname>" 
	Then user fill the form and save 
	And modify the form and save 
	And Navigate to homepage 
	Examples: 
		|cardName|formname|
		|Forms|formPagination|
		
@form_save_and_new_then_discard_form 		
Scenario Outline: fill the form and click save and new then discard 
	Given swipe to form "<cardName>" 
	When user click on the given formname "<formname>" 
	Then user fill form and 
	And click save and new  
	Then Discard the form and navigate back to home
	Examples: 
		|cardName|formname|
		|Forms|formSubmissionReport|
				
@form_save_as_draft 
Scenario Outline: fill the form then save as draft 
	Given scroll and click on forms "<cardName>" 
	When user click on the form available in the forms "<formname>" 
	Then user insert form data 
	And save form as draft 
	Examples: 
		|cardName|formname|
		|Forms|formNormal fields|

@Form_resticiton_to_employee_group		
Scenario Outline: Restrict access to employee group
	Given move to forms card "<cardName>"
	Then validate form display on employee group restriction "<formname>"
	Examples: 
		|cardName|formname|
		|Forms|formNormal fields|
	