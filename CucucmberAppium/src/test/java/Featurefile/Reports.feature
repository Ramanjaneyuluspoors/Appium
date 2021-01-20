@Reports 
Feature: Validating Reports 

@Reports_validation
Scenario: verifying reports 
	Given Fling to reports card 
	When user click on specified report name and validate data

@employee_specifying_reports
Scenario: verifying day plan report
          Given scroll to day plan report with specified employee "Nelampati Ramu" and validate report data
          