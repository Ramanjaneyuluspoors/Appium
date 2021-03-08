package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import common_Steps.AndroidLocators;
import cucumber.api.java.en.*;
import modules_test.Work;
import modules_test.Work_advanceSettings;
import utils.CommonUtils;

public class Works_Implementation {
                                           /* Work creation and modification */
	@Given("verify work exist or not {string}")
	public void verify_work_exist_or_not(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}

	@When("user create the work")
	public void user_create_the_work() throws MalformedURLException, InterruptedException, ParseException {
		Work.work_Creation();
		Work.saveWork();
	}

	@Then("user search for created work {string}")
	public void user_search_for_created_work(String workName) throws MalformedURLException, InterruptedException, ParseException {
		Work.goingToWorkScreen(workName);
		Work.clickOnEditWorkSymbol();
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
	
	                              /* Testing Work advance settings */
	
	                              /* Minimum and maximum validations */
	@Given("^Swipe to specified work$")
	public void Swipe_to_specified_work(String minMaxWork) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(minMaxWork);
	}
	
	@When("user enters Min value {int} and Max value {int} then perform validations")
	public void user_enters_Min_value_and_Max_value_then_perform_validations(int min, int max)
			throws MalformedURLException, InterruptedException {
		Work_advanceSettings.workFields_Min_Max_Testing(min, max);
	}
	
	                            /* Regular Expression */
	
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
	
	                                  /*  Validate restrict data from mobile setting  */
	
	@Given("go to specified work {string}")
	public void go_to_specified_work(String Workname) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(Workname);
	}
	
	@When("user create a work and view the work then validate")
	public void user_create_a_work_and_view_the_work_then_validate() throws MalformedURLException, InterruptedException, ParseException {
		Work.work_Creation();
		Work_advanceSettings.validateWorkfields();
	}
	
	                               /* Work Settings */
	                               /* perofrm work check-in and work rejection*/
	
	@Given("move to workcard {string}")
	public void move_to_workcard(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@When("user create a work")
	public void user_create_a_work() throws MalformedURLException, InterruptedException, ParseException {
		Work.fill_Work_MandatoryFields();
		Work_advanceSettings.saveWork();
	}

	@Then("check work checkin is exist or not {string}")
	public void check_work_checkin_is_exist_or_not(String workName)
			throws MalformedURLException, InterruptedException, ParseException {
		Work.goingToWorkScreen(workName);
		AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/button1");
		Work.workCheck_In();
	}

	@And("perform work action")
	public void perform_work_action() throws MalformedURLException, InterruptedException, ParseException {
		Work.performSingleAction();
	}

	@And("Reject the work")
	public void reject_the_work() throws MalformedURLException, InterruptedException, ParseException {
		Work.workRejection();
	}
	
	                                      /* Restrict allow user to add new work */
	
	@Given("scrolling to specified work {string}")
	public void scrolling_to_specified_work(String workName) throws MalformedURLException, InterruptedException {
		Work.verifyingFabIcon(workName);
	}
	
	@Then("validate user not able to add new work {string}")
	public void validate_user_not_able_to_add_new_work(String workName) {
		Work.workFabIcon_DisplayingOrNot(workName);
	}
	
	                                    /* work restriction to employee group */
	@Given("swipe to work and validate work is displaying or not {string}")
	public void swipe_to_work_and_validate_work_is_displaying_or_not(String workName) {
		Work.restrict_access_to_empGroup(workName);
	}
	
                                       /* Work Permissions */ 
	
	@Given("swipe to work and validate work {string}")
	public void swipe_to_work_and_validate_work(String workName) throws InterruptedException {
		CommonUtils.wait(10);
		Work.clickOnWorkCard(workName);
	}

	@Then("validate workAdd and workDelete and workModify")
	public void validate_workAdd_and_workDelete_and_workModify() throws InterruptedException {
		Work.workAddIcon();
		Work.workClick();
		Work.verify_work_Edit_Icon_status();
		Work.workClick();
		Work.verify_Work_Delete_Icon_status();
	}
	                                       /* Work to form and form to form autocopy */
	
	@Given("swipe to given work {string}")
	public void swipe_to_given_work(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@Then("validate work_to_form and form_to_form autocopy {string}")
	public void validate_work_to_form_and_form_to_form_autocopy(String workName) throws MalformedURLException, InterruptedException, ParseException {
		Work.workToFormAutoCopy(workName);
	}
	
	                                         /* Form to work autocopy */
	
	@Given("fling to given work {string}")
	public void fling_to_given_work(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@When("user create the work and perform work action {string}")
	public void user_create_the_work_and_perform_work_action(String workName) throws MalformedURLException, InterruptedException, ParseException {
		Work.formToWorkAutoCopy(workName);
	}
	
	@Then("validate form_to_work autocopy")
	public void validate_form_to_work_autocopy() throws MalformedURLException, InterruptedException {
		Work.validating_form_to_work_autocopy_by_swiping();
		Work.saveWork();
	}
	                                /* autocopy subtask from parent work,add subtask and modifying subtask and complete subtask */
	
	@Given("swipe to parent work {string}")
	public void swipe_to_parent_work(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@When("user create parent work {string}")
	public void user_create_parent_work(String workName) throws MalformedURLException, InterruptedException, ParseException {
		Work.work_Creation();
		Work.saveWork();
		Work.goingToWorkScreen(workName);
	}
	
	@And("Add Subtask and check autocopy")
	public void Add_Subtask_and_check_autocopy() throws InterruptedException {
		Work.verify_Subtask();
		//save subwork
		Work.saveWork();
		
	}
	
	@Then("Modify subtask")
	public void Modify_subtask() throws MalformedURLException, InterruptedException, ParseException {
		//click on subtask
		Work.verify_Subtask();
		Work.clickOnEditWorkSymbol();
		Work.fill_Work_MandatoryFields();
		Work.saveWork();
	}
	
	@And("Perform subtask action and complete subtask")
	public void Perform_subtask_action_and_complete_subtask() throws MalformedURLException, InterruptedException, ParseException {
		Work.verify_Subtask();
		Work.WorkAction();
	}
	
	                              /*parentwork subtask completion*/
	
	@Given("scroll to parentwork {string}")
	public void scroll_to_parentwork(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@When("user create parentwork {string}")
	public void user_create_parentwork(String workName)
			throws MalformedURLException, InterruptedException, ParseException {
		Work.fill_Work_MandatoryFields();
		Work.saveWork();
		Work.goingToWorkScreen(workName);
	}

	@Then("Add subtask and complete subtask")
	public void Add_subtask_and_complete_subtask() throws InterruptedException, MalformedURLException, ParseException {
		Work.verify_Subtask();
		Work.fill_Work_MandatoryFields();
		Work.saveWork();
		//if subtask exist then click on it
		Work.verify_Subtask();
		Work.WorkAction();
	}

	@And("complete parentwork")
	public void complete_parentwork() throws MalformedURLException, InterruptedException, ParseException {
		//perform parent work action
		Work.WorkAction();
	}
	
                                           /* perform parent work action when subtask is completed */
	
	@Given("verify parent work exit or not {string}")
	public void verify_parent_work_exit_or_not(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}
	
	@When("User add parent work {string}")
	public void User_add_parent_work(String workName) throws MalformedURLException, InterruptedException, ParseException {
		Work.fill_Work_MandatoryFields();
		Work.saveWork();
		Work.goingToWorkScreen(workName);
	}
	
	@And("check subtask is exist or not")
	public void check_subtask_is_exist_or_not() throws InterruptedException, MalformedURLException, ParseException {
		Work.verify_Subtask();
		Work.fill_Work_MandatoryFields();
		Work.saveWork();
	}
	
	@Then("verify parent work action is enabled or not dependent on subtask")
	public void verify_parent_work_action_is_enabled_or_not_dependent_on_subtask() throws MalformedURLException, InterruptedException, ParseException {
		Work.perform_Parent_WorkAction_When_Subtask_is_Completed();
	}
	
	
}
