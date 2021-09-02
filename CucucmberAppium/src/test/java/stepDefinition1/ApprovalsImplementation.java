package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import cucumber.api.java.en.*;
import modules_test.ApprovalsTesting;

public class ApprovalsImplementation {

	@Given("swipe to my approvals card")
	public void swipe_to_my_approvals_card() throws MalformedURLException, InterruptedException, ParseException {
		ApprovalsTesting.goingToApprovalsCard();
	}
	
	@Then("fill an activity using approval process name {string}")
	public void fill_an_activity_using_approval_process_name(String formApprovalName) throws MalformedURLException, InterruptedException, ParseException {
	    ApprovalsTesting.fill_approval_process_activity(formApprovalName);
	}

	@Given("fling to approval card")
	public void fling_to_approval_card() {
		ApprovalsTesting.goingToApprovalsCard();
	}
	
	@When("user approves the approval activity")
	public void user_approves_the_approval_activity() {
		ApprovalsTesting.approve_approvalProcess();
	}
	
	@Then("Back to home screen")
	public void Back_to_home_screen() throws MalformedURLException, InterruptedException {
		ApprovalsTesting.moveToHomeScreen();
	}
	
	@Given("scroll to approval card")
	public void scroll_to_approval_card() {
		ApprovalsTesting.goingToApprovalsCard();
	}
	
	@When("user rejects the approval process")
	public void user_rejects_the_approval_process() {
		ApprovalsTesting.reject_approvalProcess();
	}
	
	@And("go back to home page")
	public void go_back_to_home_page() throws MalformedURLException, InterruptedException {
		ApprovalsTesting.moveToHomeScreen();
	}
	
	@Given("go to my approval card")
	public void go_to_my_approval_card() {
		ApprovalsTesting.goingToApprovalsCard();
	}
	
	@When("user re-submit the approval form")
	public void user_resubmit_the_approval_form(){
		ApprovalsTesting.resubmit_approvalProcess();
	}
	
	@Then("move back to home page")
	public void move_back_to_home_page() throws MalformedURLException, InterruptedException {
		ApprovalsTesting.moveToHomeScreen();
	}
	
	@Given("going to my approval card")
	public void going_to_my_approval_card() {
		ApprovalsTesting.goingToApprovalsCard();
	}
	
	@When("user cancels the approval form")
	public void user_cancels_the_approval_form() throws MalformedURLException, InterruptedException {
		ApprovalsTesting.cancel_approvalProcess();
	}
	
	@And("move to home page")
	public void move_to_home_page() throws MalformedURLException, InterruptedException {
		ApprovalsTesting.moveToHomeScreen();
	}
	
}
