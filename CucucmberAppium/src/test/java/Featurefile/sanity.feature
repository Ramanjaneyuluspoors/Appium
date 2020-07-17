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
		|Task|
	Then verify work exist or not 
		|Task|
	And perform workaction and move to homepage 
	
@Form 
Scenario: Form completion with allFields 
	Given Scroll to form and click 
		|formPagination|
	Then fill form to complete 
	
@signout
Scenario: Signout
	Given user wants to signout	

	