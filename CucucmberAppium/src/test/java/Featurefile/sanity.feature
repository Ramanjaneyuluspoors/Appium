Feature: Performing sanity checklist 

@signin 
Scenario: Signin Functionality 
	Given Navigate to Home page 
	When user click on signin 
	Then verify signin validation 
	
@CustomerActivity 
Scenario: Customer Activity 
	Given Scroll to customer card and click 
		|Customers|    
	When user search for customer with 
		|beens|
	Then verify customer exist or not "beens" 
	And  verfiy customer checkin 
	And Do customer activity and checkout 
		|formNormal fields|
		
@Routeplan 
Scenario: Route Activity 
	Given Scroll to route plan and click 
		|Route plans|
	When user search for route 
		|Check-out Anyway|
	Then verify customer route checkin validation 
	And perform routeactivity 
	Then complete client visit do customer checkout 
	
@Dayplan 
Scenario: Day plan Activity 
	Given Scroll to Day planner 
		|Day planner|
	When user click on currentday in Day plan calendar 
	Then verify day plan exist or not 
	And Do day plan activity and checkout 
		|formNormal fields|
		
@Workcompletion 
Scenario: Work Creation 
	Given Scroll to work "Sanity" 
	When user search for work 
		|Sanity|
	Then verify work exist or not 
		|Sanity|
	And perform workaction and move to homepage 
	
@Form 
Scenario: Form completion with allFields 
	Given Scroll to form and click 
		|AllFieldsForm new|
	Then fill form to complete 
	
@signout
Scenario: Signout
	Given user wants to signout	

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