package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import Actions.HomepageAction;
import Actions.MobileActionGesture;
import Actions.ReceiptActions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import modules_test.CustomerPageActions;
import modules_test.Forms_basic;
import modules_test.RoutePlan;
import modules_test.Work;
import utils.CommonUtils;

public class Sanity_implementation {
 
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
		Forms_basic.verifyFormPagesAndFill();
		Forms_basic.formSaveButton();
		CustomerPageActions.HomepageCusCheckout();
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
		Forms_basic.verifyFormPagesAndFill();
		Forms_basic.formSaveButton();
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
		Forms_basic.verifyForminHomePage(form);
	}

	@When("^user click on specified form$")
	public static void user_click_on_specified_form(String form) {
		MobileActionGesture.scrollTospecifiedElement("" + form + "");
		CommonUtils.waitForElementVisibility("//*[@text='" + form + "']");
	}
	
	@Then("fill the form to complete")
	public void fill_the_form_to_complete() throws MalformedURLException, InterruptedException, ParseException {
		Forms_basic.verifyFormPagesAndFill();
		Forms_basic.formSaveButton();
		CommonUtils.goBackward();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}

	@Given("Swipe to settings")
	public void Swipe_to_settings() throws MalformedURLException, InterruptedException {
		HomepageAction.perform_debug_action();
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
	
}
