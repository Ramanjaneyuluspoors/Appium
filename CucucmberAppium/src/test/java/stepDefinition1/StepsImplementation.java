package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import Actions.CustomerPageActions;
import Actions.HomepageAction;
import Actions.MobileActionGesture;
import Actions.ReceiptActions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.CommonUtils;
import utils.FormAdvanceSettings;
import utils.Forms;
import utils.RoutePlan;
import utils.Work;

public class StepsImplementation {
 
	@Given("Navigate to Home page")
	public void navigate_to_Home_page() throws InterruptedException {
	    HomepageAction.NavigationVerfication(); 
	}

	@When("user click on signin")
	public void user_click_on_signin() throws MalformedURLException, InterruptedException, ParseException {
		HomepageAction.signInAction(); // need to optimize taking 37s time to signin using location if sign-in form
//    	Forms.createEntity();										// does'nt exist
	}

	@Then("verify signin validation")
	public void verify_signin_validation() throws InterruptedException {
		ReceiptActions.PageVerification();
	}
	
	@Given("^Scroll to customer card and click$")
	public void scroll_to_customer_card_and_click(String customers) throws MalformedURLException {
		CustomerPageActions.verifyCustomerInHomePage("" + customers + "");
	}

	@When("^user search for customer with$")
	public void user_search_for_customer_with(String cus) throws MalformedURLException {
		CustomerPageActions.customerSearch(cus);
	}

	@Then("verify customer exist or not {string}")
	public void verify_customer_exist_or_not(String cus) throws MalformedURLException, InterruptedException {
		CustomerPageActions.verifyCusExistOrNot(cus);	
	}

	@And("verfiy customer checkin")
	public void verfiy_customer_checkin() throws MalformedURLException, InterruptedException {
		CustomerPageActions.veirfyCusCheckin();
		CustomerPageActions.goToActivityScreen();
	}

	@And("^Do customer activity and checkout$")
	public void Do_customer_activity_and_checkout(String formName) throws MalformedURLException, InterruptedException, ParseException {
		CustomerPageActions.clickActivity(formName);
		Forms.verifyFormPagesAndFill();
		CustomerPageActions.checkout_in_customer_screen();
	}

	@Given("^Scroll to route plan and click$")
	public void scroll_to_route_plan_and_click(String Routeplans) throws MalformedURLException {
		CustomerPageActions.verifyCustomerInHomePage(Routeplans);
		CommonUtils.waitForElementVisibility("//*[@text='Route plans']");
	}

	@When("^user search for route$")
	public void user_search_for_route(String routeName) {
		RoutePlan.verifyRoute(routeName);
	}

	@Then("verify customer route checkin validation")
	public void verify_customer_route_checkin_validation() throws MalformedURLException, InterruptedException {
		RoutePlan.routeCusCheckin();
	}

	@And("perform routeactivity")
	public void perform_routeactivity() throws MalformedURLException, InterruptedException, ParseException {
		RoutePlan.performRouteActivity();
		Forms.verifyFormPagesAndFill();
	}

	@Then("complete client visit do customer checkout")
	public void complete_client_visit_do_customer_checkout() throws MalformedURLException, InterruptedException {
		RoutePlan.routeCusCheckout();
	}

	@Given("^Scroll to Day planner$")
	public void scroll_to_Day_planner(String Dayplanner) throws MalformedURLException {
		CustomerPageActions.verifyCustomerInHomePage(Dayplanner);
		CommonUtils.waitForElementVisibility("//*[@text='Day planner']");
	}

	@When("user click on currentday in Day plan calendar")
	public void user_click_on_currentday_in_Day_plan_calendar() throws InterruptedException {
		RoutePlan.calendarClick();
	}

	@Then("verify day plan exist or not")
	public void verify_day_plan_exist_or_not() throws MalformedURLException, InterruptedException {
		RoutePlan.day_Plan_Page();  
	}

	@And("^Do day plan activity and checkout$")
	public void Do_day_plan_activity_and_checkout(String formName) throws MalformedURLException, InterruptedException, ParseException {
		Do_customer_activity_and_checkout(formName);
	}

	@Given("Scroll to work {string}")
	public void scroll_to_work(String workName) throws MalformedURLException, InterruptedException {
		Work.checkWorkExistInHomePageorNot(workName);
	}

	@When("^user create work$")
	public void user_create_work() throws MalformedURLException, InterruptedException {
		Work.createWork();
		Work.save_And_StartAction();
	}

	@Then("perform workaction")
	public void perform_workaction() throws MalformedURLException, InterruptedException, ParseException {
		Work.WorkAction();
	}

	@Given("^Scroll to form and click$")
	public void scroll_to_form_and_click(String form) throws InterruptedException, MalformedURLException {
		Forms.verifyForminHomePage(form);
	}

	@Then("fill form to complete")
	public void fill_form_to_complete() throws MalformedURLException, InterruptedException, ParseException {
		Forms.verifyFormPagesAndFill();
		CommonUtils.goBackward();
	}

	@Given("Swipe to settings")
	public void Swipe_to_settings() throws MalformedURLException, InterruptedException {
		CommonUtils.openMenu();
		MobileActionGesture.scrollTospecifiedElement("Settings");
		CommonUtils.allow_bluetooth();
	}
	
	@When("user click on send debug info")
	public void user_click_on_send_debug_info() {
		CommonUtils.click_on_send_debug_info();
	}
	
	@Then("send debug information with remarks")
	public void send_debug_information_with_remarks() throws InterruptedException {
		CommonUtils.send_debug_info_details();
		CommonUtils.initiate_full_sync();
	}
	
	@Given("user wants to signout")
	public void user_wants_to_signout() throws MalformedURLException, InterruptedException, ParseException {
		HomepageAction.signOutAction();
	}
	
	@Given("^Swipe to MinMax form$")
	public void Swipe_to_MinMax_form(String MinMaxform) throws InterruptedException, MalformedURLException {
		Forms.verifyForminHomePage(MinMaxform);
	}
	
	@Then("user enters Min value {int} and Max value {int}")
	public static void user_enters_Min_value_and_Max_value(int min, int max) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.minMaxTesting(min, max);
	}
	
	@Given("^scroll to specified form$")
	public static void scroll_to_specified_form(String fieldDepency_form) throws MalformedURLException {
		Forms.verifyForminHomePage(fieldDepency_form);
	}
	
	@Then("user gives the basecondition {string} and dependencyfield {string} and input is {string}")
	public static void user_gives_the_basecondition_and_dependencyfield_and_input_is(String baseCondition, String formFieldLabel, String formFieldInput) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.fieldDependencyValueOtherFields(baseCondition, formFieldLabel, formFieldInput);
	}
	
	@Given("move to specified form")
	public void move_to_specified_form(String regularExpression_form) throws MalformedURLException {
		Forms.verifyForminHomePage(regularExpression_form);
	}

	@When("user enters the Regular Expression {string} for datatype {string}")
	public void user_enters_the_Regular_Expression_for_datatype(String regExp, String formFieldLabel) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.regularExpressionTesting(regExp, formFieldLabel);
	}
	
	@Given("^scroll to specified color form$")
	public static void scroll_to_specified_color_form(String colorForm) throws MalformedURLException {
		Forms.verifyForminHomePage(colorForm);
	}
	
	@When("user enters the field value {int} for datatype {string}")
	public static void user_enters_the_field_value_for_datatype(int fieldValue, String fieldLabel) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.testing_highlighting_background_field_basedOn_fieldValue(fieldValue, fieldLabel);
	}
	 
	@Given("^swipe to specified form$")
	public static void swipe_to_specified_form(String errorAndWarnMessageForm) throws MalformedURLException {
		Forms.verifyForminHomePage(errorAndWarnMessageForm);
	}
	
	@When("user provide the condition {string} and gives input as {string}")
	public static void user_provide_the_condition_and_gives_input_as(String errorCondition, String inputValue) throws MalformedURLException, InterruptedException, ParseException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.Validate_Based_on_Values_in_Other_Fields(errorCondition, inputValue);
	}
}
