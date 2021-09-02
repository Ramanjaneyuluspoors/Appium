package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import cucumber.api.java.en.*;
import modules_test.CustomerPageActions;
import modules_test.Customer_Advancesetting;

public class CustomerImplementation {

	@Given("verify customer card exist or not {string}")
	public void verify_customer_card_exist_or_not(String customer) throws MalformedURLException, InterruptedException, ParseException {
		CustomerPageActions.goToCustomerScreen(customer);
	}
	
	@When("user create a customer")
	public void user_create_a_customer() throws MalformedURLException, InterruptedException, ParseException {
		CustomerPageActions.createCustomer();   //customer creation with few fields  (need to check with all fields)
		CustomerPageActions.saveMethod();
	}
	
	@Then("validate customer is created or not")
	public void validate_customer_is_created_or_not() throws MalformedURLException, InterruptedException {
		CustomerPageActions.searchForCreatedCustomer();
	}
	
	@And("modify the customer")
	public void modify_the_customer() throws MalformedURLException, InterruptedException {
		CustomerPageActions.goToCustomerModifyScreen();
		CustomerPageActions.modifyCustomer();
		validate_customer_is_created_or_not();
	}
	
	                                /********* Customer Advance settings **********/
	
	                             /* Customer min max validation */
	
	@Given("Go to customer page {string}")
	public void Go_to_customer_page(String customer) {
		CustomerPageActions.goToCustomerScreen(customer);
	}
	
	@When("user enter min value {int} and max value {int} and check validations")
	public void user_enter_min_value_and_max_value_and_check_validations(int min , int max) throws MalformedURLException {
		Customer_Advancesetting.customer_Fields_min_max_testing(min, max);
	}
	
	                                      /* Customer regular expression testing */
	
	@Given("Move to customer page {string}")
	public void Move_to_customer_page(String customer) {
		CustomerPageActions.goToCustomerScreen(customer);
	}
	
	@When("user enter the regular expression {string} then validate customer fields")
	public void user_enter_the_regular_expression_then_validate_customer_fields(String regExp) {
		Customer_Advancesetting.regularExpression_Testing(regExp);
	}
	
	                                   /* Field Dependency based on value in other fields */
	@Given("fling to customer {string}")
	public void fling_to_customer(String customer) throws MalformedURLException, InterruptedException {
		CustomerPageActions.goToCustomerScreen(customer);
	}
	
	@When("user enters the basecondition {string} for the customer dependencyfield {string} the input is {string}")
	public void user_enters_the_basecondition_for_the_customer_dependencyfield_the_input_is(String conditionName, String dependentFieldType,
			String inputData) throws MalformedURLException, InterruptedException {
		Customer_Advancesetting.FieldDependencyBasedOnValueInOtherFieldsTesing(conditionName, dependentFieldType, inputData);
	}
	
	                                    /* Highlighting background field based on field value */
	
	@Given("Swipe to customer screen {string}")
	public void swipe_to_customer_screen(String customer) {
		CustomerPageActions.goToCustomerScreen(customer);
	}

	@When("user enters value {string} for the dependent field {string}")
	public void user_enters_value_for_the_dependent_field(String fieldValue, String fieldType) throws MalformedURLException {
	   Customer_Advancesetting.validating_BackgroundFields_Basedon_Dependent_Fieldvalue(fieldValue, fieldType);
	}
										/* Error and warn message validation based on field value */
	
	@Given("Scroll to customer screen {string}")
	public void scroll_to_customer_screen(String customer) {
		CustomerPageActions.goToCustomerScreen(customer);
	}

	@When("user gives the condition as {string} and provides the input as {string}")
	public void user_gives_the_condition_as_and_provides_the_input_as(String errorCondition, String inputValue)
			throws MalformedURLException, InterruptedException, ParseException {
		Customer_Advancesetting.fieldValidation_BasedOn_ValueIn_OtherField(errorCondition, inputValue);
	}
	
}
