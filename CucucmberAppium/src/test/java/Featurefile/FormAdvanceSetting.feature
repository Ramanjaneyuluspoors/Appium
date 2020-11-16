Feature: Performing Form Regression Test 

@formMinMax 
Scenario: Form MinMax Validations 
	Given Swipe to MinMax form 
		|MInMaxForm|
	When user enters Min value 5 and Max value 9 
	
@FieldDependencyBasedOnOtherFields 
Scenario: Form Field Dependency Based on Values in Other Fields 
	Given scroll to specified form 
		|formPagination|
	When user gives the basecondition "Hide when" and dependencyfield "Text" and input is "ramu" 
	
@RegularExpression 
Scenario: Regular Expression Validations 
	Given move to specified form 
		|Form-5|
	When user enters the Regular Expression "[0-9A-Za-z]" for datatype "Text" 
	
@Highlighting_Background_Field_Based_on_FieldValue 
Scenario: Highlighting Background Field Based on FieldValue 
	Given scroll to specified color form 
		|High lighting background color| 
	When user enters the field value 10 for datatype "Number"  

@Validate_Based_on_Values_in_Other_Fields 
Scenario: Validate Based on Values in Other Fields 
	Given swipe to specified form 
		|Error and warn message|
	When user provide the condition "Show Error when" and gives input as "10,9 Apr 2021"