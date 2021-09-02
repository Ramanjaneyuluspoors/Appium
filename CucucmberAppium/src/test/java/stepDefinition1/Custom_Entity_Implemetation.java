package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import Actions.MobileActionGesture;
import cucumber.api.java.en.*;
import modules_test.Custom_Entity;

public class Custom_Entity_Implemetation {

	@Given("Scroll to custom entity {string}")
	public void Scroll_to_custom_entity(String entityName) {
		Custom_Entity.goToCustomEntityScreen(entityName);
	}
	
	@Then("create a custom enity")
	public void create_a_custom_enity() throws MalformedURLException, InterruptedException, ParseException {
		Custom_Entity.fillEntity();
	}
	
	@Given("Swipe to custom entity {string}")
	public void Swipe_to_custom_entity(String entityName) {
		MobileActionGesture.scrollTospecifiedElement(entityName);
	}
	
	@When("user perform checkin and fill entity activity")
	public void user_perform_checkin_and_fill_entity_activity() throws MalformedURLException, InterruptedException, ParseException {
		Custom_Entity.customEntityCheckin();
		Custom_Entity.clickAndFillActivity();
	}
	
	@Then("perform checkout")
	public void perform_checkout() throws MalformedURLException, InterruptedException {
		Custom_Entity.customEntityCheckout();
		Custom_Entity.moveToHomeScreen();
	}
	
	                          /**** Regular expression ****/
	@Given("Move to entity {string}")
	public void Move_to_entity(String entityName) {
		Custom_Entity.goToCustomEntityScreen(entityName);
	}
	
	@When("user enter expression {string} for the datatype {string} then validate it")
	public void user_enter_expression_for_the_datatype_then_validate_it(String expression, String dataType) {
		Custom_Entity.regularExpression(expression, dataType);
	}
	
	                         /**** Minimum and Maximum value validation ****/
	@Given("Swipe to specified entity name {string}")
	public void Swipe_to_specified_entity_name(String entityName) {
		Custom_Entity.goToCustomEntityScreen(entityName);
	}
	
	@When("user enter min value {int} and max value {int} then validate fields")
	public void user_enter_min_value_and_max_value_then_validate_fields(int min, int max) throws MalformedURLException {
		Custom_Entity.entityFields_min_max_validations(min, max);
	}
	
							/**** Field Dependency Based On Value In Other Fields ****/
	
	@Given("fling to entity {string}")
	public void fling_to_entity(String entityName) {
		Custom_Entity.goToCustomEntityScreen(entityName);
	}
	
	@When("user select the basecondition {string} for the dependencyfield {string} the input is {string}")
	public void user_select_the_basecondition_for_the_dependencyfield_the_input_is(String baseCondition,
			String fieldType, String fieldInput) throws MalformedURLException, InterruptedException {
		Custom_Entity.entity_field_dependency_basedOn_value_in_otherfields(baseCondition, fieldType, fieldInput);
	}
	                       /*** Error and Warn message validation ***/
	@Given("Scroll to entity {string}")
	public void Scroll_to_entity(String entityName) {
		Custom_Entity.goToCustomEntityScreen(entityName);
	}
	
	@When("user gives the condition as {string} and provides criteria condition {string} for the input {string}")
	public void user_gives_the_condition_as_and_provides_criteria_condition_for_the_input(String errorCondition,
			String criteriaCondition, String inputValue)
			throws MalformedURLException, InterruptedException, ParseException {
		Custom_Entity.entityFieldsValidation_basedon_value_inother_fields(errorCondition, criteriaCondition,
				inputValue);
	}
                             
							/***** highlighting background color based on field value *****/
	
	@Given("Swipe to specified entity card {string}")
	public void Swipe_to_specified_entity_card(String entityName) {
		Custom_Entity.goToCustomEntityScreen(entityName);
	}
	
	@When("user enters the value {string} for the dependent field {string}")
	public void user_enters_the_value_for_the_dependent_field(String fieldValue, String fieldType) throws MalformedURLException {
		Custom_Entity.highlighting_background_color_based_on_fieldValue(fieldValue, fieldType);
	}
}
