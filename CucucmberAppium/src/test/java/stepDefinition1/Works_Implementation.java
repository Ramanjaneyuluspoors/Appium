package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import cucumber.api.java.en.*;
import modules_test.Work;
import modules_test.Work_advanceSettings;

public class Works_Implementation {

	@Given("verify work exist or not {string}")
	public void verify_work_exist_or_not(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
//		Work.goToWorkPage(workName);
	}

	@When("user create the work")
	public void user_create_the_work() throws MalformedURLException, InterruptedException, ParseException {
		Work.work_Creation();
		Work.saveWork();
	}

	@Then("user search for created work {string}")
	public void user_search_for_created_work(String workName) throws MalformedURLException, InterruptedException, ParseException {
		Work.goingToWorkModifyScreen(workName);
	}
	
	@And("modify the work")
	public void modify_the_work() throws MalformedURLException, InterruptedException, ParseException {
		Work.workModification();
		Work.saveWork();
	}
	
	@And("complete the work")
	public void complete_the_work() throws MalformedURLException, InterruptedException, ParseException {
		Work.workSearch(Work.generateWorkName);;
		Work.WorkAction();
	}
	
	                           /* Work advance settings */
	
	/* min max validations */
	@Given("^Swipe to specified work$")
	public void Swipe_to_specified_work(String minMaxWork) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(minMaxWork);
	}
	
	@When("user enters Min value {int} and Max value {int} then perform validations")
	public void user_enters_Min_value_and_Max_value_then_perform_validations(int min, int max)
			throws MalformedURLException, InterruptedException {
		Work_advanceSettings.workFields_Min_Max_Testing(min, max);
	}
	
	/* regular expression */
	@Given("^scroll to specified work$")
	public void scroll_to_specified_work(String regExpression) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(regExpression);
	}
	
	@When("user enters regular expression {string} then perform validation")
	public void user_enters_regular_expression_then_perform_validation(String regExp) throws InterruptedException, MalformedURLException {
		Work_advanceSettings.reuglarExpression(regExp);
	}
	
	/* Field Dependency based on value in other fields */
	@Given("fling to work {string}")
	public void fling_to_work(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@When("user enters the basecondition {string} for the dependencyfield {string} the input is {string}")
	public void user_enters_the_basecondition_for_the_dependencyfield_the_input_is(String conditionName, String dependentFieldType,
			String inputData) throws MalformedURLException, InterruptedException {
		Work_advanceSettings.fieldDependencyBasedOnValueInOtherFieldsTesing(conditionName, dependentFieldType, inputData);
	}
	
	/* Validate Based on Values in Other Fields(error and warn message) */
	@Given("move to specified work {string}")
	public void move_to_specified_work(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
//		Work.goToWorkPage(workName);
	}
	
	@When("user gives the condition {string} and provides the input as {string}")
	public void user_gives_the_condition_and_provide_the_input_as(String errorCondition, String specifiedInput)
			throws MalformedURLException, InterruptedException, ParseException {
		Work_advanceSettings.workErrorAndWarnMeassage(errorCondition, specifiedInput);
	}
	
	/*Validate restrict data from mobile setting*/
	@Given("go to specified work {string}")
	public void go_to_specified_work(String Workname) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(Workname);
	}
	
	@When("user create a work and view the work then validate")
	public void user_create_a_work_and_view_the_work_then_validate() throws MalformedURLException, InterruptedException, ParseException {
		Work.work_Creation();
		Work_advanceSettings.validateWorkfields();
	}
}
