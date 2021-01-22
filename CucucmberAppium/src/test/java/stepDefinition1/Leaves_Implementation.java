package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import Actions.MobileActionGesture;
import cucumber.api.java.en.*;
import modules_test.Leaves;


public class Leaves_Implementation {
	@Given("fling to leaves card")
	public static void fling_to_leaves_card() throws MalformedURLException, InterruptedException, ParseException {
		MobileActionGesture.scrollTospecifiedElement("Leaves");
	}
	
	@When("user want to apply leave with leave type {string}")
	public static void user_want_to_apply_leave_with_leave_type(String leaveType) throws MalformedURLException, InterruptedException, ParseException {
		Leaves.leave_apply(leaveType);
	}
	
	@Then("user should successfully apply leave")
	public static void user_should_successfully_apply_leave() {
		Leaves.leave_Status();
		Leaves.leave_cancel();
	}
}
