package effortSteps;

import java.net.MalformedURLException;

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
	public void user_click_on_signin() throws MalformedURLException, InterruptedException {
		HomepageAction.signInAction(); // need to optimize taking 37s time to signin using location if sign-in forn
//    	Forms.createEntity();										// does'nt exist
	}

	@Then("verify signin validation")
	public void verify_signin_validation() throws InterruptedException {
		ReceiptActions.PageVerification();
	}
	
	@Given("^Scroll to customer card and click$")
	public void scroll_to_customer_card_and_click(String customers) throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("" + customers + "");
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
	}

	@And("^Do customer activity and checkout$")
	public void Do_customer_activity_and_checkout(String formName) throws MalformedURLException, InterruptedException {
		CustomerPageActions.clickActivity(formName);
		CustomerPageActions.verifyFormPagesAndFill();
		CustomerPageActions.HomepageCusCheckout();
	}

	@Given("^Scroll to route plan and click$")
	public void scroll_to_route_plan_and_click(String Routeplans) throws MalformedURLException {
		RoutePlan.GoingToRoutes(Routeplans);
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
	public void perform_routeactivity() throws MalformedURLException, InterruptedException {
		RoutePlan.performRouteActivity();
		CustomerPageActions.verifyFormPagesAndFill();
	}

	@Then("complete client visit do customer checkout")
	public void complete_client_visit_do_customer_checkout() throws MalformedURLException, InterruptedException {
		RoutePlan.routeCusCheckout();
	}

	@Given("^Scroll to Day planner$")
	public void scroll_to_Day_planner(String Dayplanner) throws MalformedURLException {
		RoutePlan.goToDayPlan(Dayplanner);
	}

	@When("user click on currentday in Day plan calendar")
	public void user_click_on_currentday_in_Day_plan_calendar() throws InterruptedException {
		RoutePlan.calendarClick();
	}

	@Then("verify day plan exist or not")
	public void verify_day_plan_exist_or_not() throws MalformedURLException, InterruptedException {
		RoutePlan.verifyDayPlanInCustomer();
	}

	@And("^Do day plan activity and checkout$")
	public void Do_day_plan_activity_and_checkout(String formName) throws MalformedURLException, InterruptedException {
		Do_customer_activity_and_checkout(formName);
	}

	@Given("Scroll to work {string}")
	public void scroll_to_work(String workName) throws MalformedURLException, InterruptedException {
		utils.Work.goToWorkPage(workName);
	}

	@When("^user search for work$")
	public void user_search_for_work(String workname) throws MalformedURLException, InterruptedException {
		Work.workSearch(workname);
	}

	@Then("^verify work exist or not$")
	public void verify_work_exist_or_not(String workname) throws MalformedURLException, InterruptedException {
		Work.verifyWorkExistOrNot(workname);
	}

	@And("perform workaction and move to homepage")
	public void perform_workaction_and_move_to_homepage() throws MalformedURLException, InterruptedException {
		// PerformAction();
		Work.WorkAction();
//		workSearch(workName);
//		workStatus(workName);
		Work.moveToHomepageFromWork();
	}

	@Given("^Scroll to form and click$")
	public void scroll_to_form_and_click(String form) throws InterruptedException, MalformedURLException {
		Forms.verifyForminHomePage(form);
	}

	@Then("fill form to complete")
	public void fill_form_to_complete() throws MalformedURLException, InterruptedException {
		CustomerPageActions.verifyFormPagesAndFill();
		CommonUtils.goBackward();
	}

	@Given("user wants to signout")
	public void user_wants_to_signout() throws MalformedURLException, InterruptedException {
		HomepageAction.signOutAction();
	}
	
	@Given("^Swipe to MinMax form$")
	public void Swipe_to_MinMax_form(String MinMaxform) throws InterruptedException, MalformedURLException {
		Forms.goToFormPage(MinMaxform);
	}
	
	@Then("user enters Min value {int} and Max value {int}")
	public static void user_enters_Min_value_and_Max_value(int min, int max) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.minMaxTesting(min, max);
	}
	
	@Given("^scroll to specified form$")
	public static void scroll_to_specified_form(String fieldDepency_form) throws MalformedURLException {
		Forms.goToFormPage(fieldDepency_form);
	}
	
	@Then("user gives dependencyfield {string} and input is {string}")
	public static void user_gives_dependencyfield_and_input_is(String formFieldLabel, String formFieldInput) throws MalformedURLException, InterruptedException {
		FormAdvanceSettings.clickSectionInPages();
		FormAdvanceSettings.fieldDependencyValueOtherFields(formFieldLabel, formFieldInput);
	}
}
