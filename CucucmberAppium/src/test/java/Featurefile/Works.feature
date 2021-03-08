Feature: Testing work module


@Work_creation_modifcation_completion
Scenario: Creating the work
	Given verify work exist or not "Sanity" 
	When user create the work 
	Then user search for created work "Sanity"
	And modify the work
	And complete the work

@Work_settings_Work_check-in
Scenario: Work card settings
	Given move to workcard "WorkSettings"
	When user create a work
	Then check work checkin is exist or not "WorkSettings"
          And perform work action
          And Reject the work
          
          
@Restricting_user_to_add_new_work          
Scenario: Restricting the user to add new work          
          Given scrolling to specified work "WorkSettings"
          Then validate user not able to add new work "WorkSettings"
    
@work_restriction_access_to_emplyee_group          
Scenario: work restrict access to employee group    
	Given swipe to work and validate work is displaying or not "WorkSettings"      
	
@Validating_workPermissions_setting
Scenario: Validating work permissions	
	Given swipe to work and validate work "WorkSettings"
	Then validate workAdd and workDelete and workModify	

@Validating_work_to_form_and_form_to_form_autocopy          
Scenario: Verifying work_to_form and form_to_form autocopy
	Given swipe to given work "work to form Autocopy"
	Then validate work_to_form and form_to_form autocopy "work to form Autocopy"

@validating_form-to_work_autocopy	
Scenario:	Verifying form_to_work_autocopy
	Given fling to given work "Form to Work Autocopy"
	When user create the work and perform work action "Form to Work Autocopy"
	Then validate form_to_work autocopy
	
@Autocopy_subtask_from_parentwork_and_modifying_subtask	
Scenario: verifying Subtask Autocopy from parentwork
	Given swipe to parent work "Testing Subwork"
	When user create parent work "Testing Subwork"
	And Add Subtask and check autocopy 
	Then Modify subtask
	And Perform subtask action and complete subtask
	
@complete_subtask_parentWork
Scenario: completing subtask and parent work
	Given scroll to parentwork "Testing Subwork"
	When user create parentwork "Testing Subwork"
	Then Add subtask and complete subtask
	And complete parentwork 

@complete_subtask_then_perform_parent_workAction
Scenario: perform parent work when subtask is completed
	Given verify parent work exit or not "Testing Subwork"
	When User add parent work "Testing Subwork"
	And check subtask is exist or not
	Then verify parent work action is enabled or not dependent on subtask
	
	
	
	
	