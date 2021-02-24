Feature: Testing work module


@Work_creation_modifcation_completion
Scenario: Creating the work
	Given verify work exist or not "Sanity" 
	When user create the work 
	Then user search for created work "Sanity"
	And modify the work
	And complete the work

@Work_setings_Work_check-in
Scenario: Work card settings
	Given move to workcard "WorkSettings"
	When user create a work
	Then check work checkin is exist or not "WorkSettings"
          And perform work action
          And Reject the work
          
          
