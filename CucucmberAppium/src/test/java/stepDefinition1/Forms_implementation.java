package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import Actions.MobileActionGesture;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import modules_test.CustomerPageActions;
import modules_test.FormAdvanceSettings;
import modules_test.Forms_basic;
import utils.CommonUtils;

public class Forms_implementation {
	@Given("scroll to form {string}")
	public void scroll_to_form(String form) throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.verifyForminHomePage(form);
	}
	
	@When("user click on the specified form {string}")
	public static void user_click_on_the_specified_form(String form) {
		MobileActionGesture.scrollTospecifiedElement("" + form + "");
		CommonUtils.waitForElementVisibility("//*[@text='" + form + "']");
	}
	
	@Then("user fill the form and save")
	public void user_fill_the_form_and_save() throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.verifyFormPagesAndFill();
		Forms_basic.formSaveButton();
	}
	
	@And("modify the form and save")
	public void modify_the_form_and_save() throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.modify_form();
		Forms_basic.formSaveButton();
	}
	
	@And("Navigate to homepage")
	public void Navigate_to_homepage() throws MalformedURLException, InterruptedException, ParseException {
		CommonUtils.goBackward();
	}
	
	@Given("swipe to form {string}")
	public void swipe_to_form(String form) throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.verifyForminHomePage(form);
	}

	@When("user click on the given formname {string}")
	public static void user_click_on_the_given_formname(String form) {
		user_click_on_the_specified_form(form);
	}
	
	@Then("user fill form and")
	public void user_fill_the_form_and() throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.verifyFormPagesAndFill();
	}
	
	@And("click save and new")
	public static void click_save_and_new() throws InterruptedException {
		Forms_basic.form_Save_And_New();
	}
	
	@Then("Discard the form and navigate back to home")
	public static void Discard_the_form_and_navigate_back_to_home() throws InterruptedException, MalformedURLException {
		Forms_basic.form_Discard();
		CommonUtils.goBackward();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}
	
	@Given("scroll and click on forms {string}")
	public static void scroll_and_click_on_forms(String form) throws MalformedURLException {
		Forms_basic.verifyForminHomePage(form);
	}
	
	@When("user click on the form available in the forms {string}")
	public static void user_click_on_the_form_available_in_the_forms(String form) {
		user_click_on_the_specified_form(form);
	}
	
	@Then("user insert form data")
	public static void user_insert_form_data() throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.verifyFormPagesAndFill();
	}
	
	@And("save form as draft")
	public static void save_form_as_draft() throws InterruptedException {
		Forms_basic.form_Save_As_Draft();
	}
	
	@Given("^Swipe to MinMax form$")
	public void Swipe_to_MinMax_form(String MinMaxform) throws InterruptedException, MalformedURLException {
		Forms_basic.verifyForminHomePage(MinMaxform);
	}
	
	@When("^user click on given form$")
	public static void user_click_on_given_form(String form) {
		user_click_on_the_specified_form(form);
	}

	@Then("user enters Min value {int} and Max value {int}")
	public static void user_enters_Min_value_and_Max_value(int min, int max) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.minMaxTesting(min, max);
	}
	
	@Given("^scroll to specified form$")
	public static void scroll_to_specified_form(String form) throws MalformedURLException {
		Forms_basic.verifyForminHomePage(form);
	}
	
	@When("^user click on given form name$")
	public static void user_click_on_given_form_name(String fieldDepency_form) {
		user_click_on_the_specified_form(fieldDepency_form);
	}
	
	@Then("user gives the basecondition {string} and dependencyfield {string} and input is {string}")
	public static void user_gives_the_basecondition_and_dependencyfield_and_input_is(String baseCondition, String formFieldLabel, String formFieldInput) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.fieldDependencyValueOtherFields(baseCondition, formFieldLabel, formFieldInput);
	}
	
	@Given("move to specified form")
	public void move_to_specified_form(String form) throws MalformedURLException {
		Forms_basic.verifyForminHomePage(form);
	}
	
	@When("^user click on the given form$")
	public static void user_click_on_the_given_form(String regularExpression_form) {
		user_click_on_the_specified_form(regularExpression_form);
	}

	@Then("user enters the Regular Expression {string} for datatype {string}")
	public void user_enters_the_Regular_Expression_for_datatype(String regExp, String formFieldLabel) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.regularExpressionTesting(regExp, formFieldLabel);
	}
	
	@Given("^scroll to specified color form$")
	public static void scroll_to_specified_color_form(String Form) throws MalformedURLException {
		Forms_basic.verifyForminHomePage(Form);
	}
	
	@When("^user click on the form name$")
	public static void user_click_on_the_form_name(String colorForm) {
		user_click_on_the_specified_form(colorForm);
	}
	
	@Then("user enters the field value {int} for datatype {string}")
	public static void user_enters_the_field_value_for_datatype(int fieldValue, String fieldLabel) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.testing_highlighting_background_field_basedOn_fieldValue(fieldValue, fieldLabel);
	}
	 
	@Given("^swipe to specified form$")
	public static void swipe_to_specified_form(String Form) throws MalformedURLException {
		Forms_basic.verifyForminHomePage(Form);
	}
	
	@When("^user click on the form given$")
	public static void user_click_on_the_form_given(String errorAndWarnMessageForm) {
		user_click_on_the_specified_form(errorAndWarnMessageForm);
	}
	
	@Then("user provide the condition {string} and gives input as {string}")
	public static void user_provide_the_condition_and_gives_input_as(String errorCondition, String inputValue) throws MalformedURLException, InterruptedException, ParseException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.Validate_Based_on_Values_in_Other_Fields(errorCondition, inputValue);
	}

}
