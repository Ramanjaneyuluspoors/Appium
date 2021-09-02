Feature: Testing custom enity module 

@Custom_Entity_Creation
Scenario: Custom Entity Creation
	Given Scroll to custom entity "Abc"
	Then create a custom enity

@Custom_Entity_checkin_checkout_operation	
Scenario: Custom Entity Checkin Checkout
	Given Swipe to custom entity "Abc"
	When user perform checkin and fill entity activity
	Then perform checkout

@Regular_Expression_Testing
Scenario: Regular Expression
	Given Move to entity "Abc"
	When user enter expression "[A-Za-z0-9]" for the datatype "Text" then validate it 	
	
@Validating_Min_Max_values	
Scenario: Minium and Maximum value validation
	Given Swipe to specified entity name "Abc"
	When user enter min value 5 and max value 9 then validate fields
	
@EntityFieldDependencyBasedOnOtherFields 
Scenario Outline: Entity Field Dependency Based on Values in Other Fields 
	Given fling to entity "<entity>" 
	When user select the basecondition "<conditionName>" for the dependencyfield "<fieldType>" the input is "<inputValue>" 
	Examples: 
		|entity|conditionName|fieldType|inputValue|
		|Abc|Hide when|Customer|charan,Area2|

@EntityFields_Validation_Based_on_Values_in_Other_Fields 
Scenario Outline: Customer Validate Based on Values in OtherFields 
	Given Scroll to entity "<entity>" 
	When user gives the condition as "<errorCondition>" and provides criteria condition "<criteriaCondition>" for the input "<inputValue>" 
	Examples: 
		|entity|errorCondition|criteriaCondition|inputValue|
		|Abc|Show Error when|Equals to|10,15 March 2021|
		
@Highlighting_Background_Fields_Based_on_Dependent_value 
Scenario Outline: Entity Highlighting Background of a Field Based on Dependent value 
	Given Swipe to specified entity card "<entity>" 
	When user enters the value "<fieldvalue>" for the dependent field "<fieldType>" 
	Examples: 
		|entity|fieldvalue|fieldType|
		|Abc|10|Currency|		
	