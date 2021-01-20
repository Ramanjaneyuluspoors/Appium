@leave 
Feature: Automating Leave 

@Testing_leaves_module 
Scenario: Applying leave 
	Given fling to leaves card 
	When user want to apply leave with leave type "planned" 
	Then user should successfully apply leave 
