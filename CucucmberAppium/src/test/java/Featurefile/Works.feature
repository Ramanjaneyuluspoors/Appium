Feature: Testing work module


@Work_creation_modifcation_completion
Scenario: Creating the work
	Given verify work exist or not "SANITY" 
	When user create the work 
	Then user search for created work "SANITY"
	And modify the work
	And complete the work

