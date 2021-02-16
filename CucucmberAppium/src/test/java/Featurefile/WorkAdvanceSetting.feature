Feature: Performing workfields advance settings regression testing 

@Work_MinMax_validations 
Scenario: Work MinMax Validations 
	Given Swipe to specified work 
		|MY-TEST|	
	When user enters Min value 5 and Max value 9 then perform validations 
	
@Work_Regular_expression 
Scenario: Worjk Regular Expression 
	Given scroll to specified work 
		|RAMUEXP|
	When user enters regular expression "[a-z0-9A-Z]" then perform validation 
	
@WorkFieldDependencyBasedOnOtherFields 
Scenario Outline: Work Field Dependency Based on Values in Other Fields 
	Given fling to work "<workname>" 
	When user enters the basecondition "<ConditionName>" for the dependencyfield "<fieldType>" the input is "<inputValue>" 
	Examples: 
		|workname|ConditionName|fieldType|inputValue|
		|CHECK ADVANCE SETTINGS|Mandatory when|Date|2021-02-16|
		
@Work_Validate_Based_on_Values_in_Other_Fields 
Scenario Outline: Work Validate_Based_on_Values_in_Other_Fields 
	Given move to specified work "<workname>" 
	When user gives the condition "<errorCondition>" and provides the input as "<specifiedValue>" 
	Examples: 
		|workname|errorCondition|specifiedValue|
		|work-Error and warn message|Show Error when|10,9 Apr 2021|