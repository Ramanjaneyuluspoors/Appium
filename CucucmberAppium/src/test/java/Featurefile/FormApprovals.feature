@Formapprovals 
Feature: Automating Form approvals 

@my_approvals 
Scenario: Testing form approvals 
	Given swipe to my approvals card 
	Then fill an activity using approval process name "Every one Hierarchy" 
	
@Approve_approvalForm 
Scenario: Approving the approval form 
	Given fling to approval card 
	When user approves the approval activity 
	Then Back to home screen 
	
@Reject_approvalProcess 
Scenario: Rejecting the approval form 
	Given scroll to approval card 
	When user rejects the approval process 
	And go back to home page 
	
@Re-submitting_approval_form 
Scenario: Re-submitting the approval form 
	Given go to my approval card 
	When user resubmit the approval form 
	Then move back to home page 
	
@Cancelling_the_approval_form 
Scenario: Cancelling the approval form 
	Given going to my approval card 
	When user cancels the approval form 
	And move to home page 
