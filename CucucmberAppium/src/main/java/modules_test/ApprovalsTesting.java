package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;

import org.openqa.selenium.By;

import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import utils.CommonUtils;

public class ApprovalsTesting {

	// going to approval card
	public static void goingToApprovalsCard() {
		MobileActionGesture.scrollTospecifiedElement("My approvals");
		CommonUtils.waitForElementVisibility("//*[contains(@text,'APPROVALS')]");
	}

	// fill approval process activity
	public static void fill_approval_process_activity(String formApprovalName)
			throws MalformedURLException, InterruptedException, ParseException {
		CommonUtils.homeFabClick();
		MobileActionGesture.scrollTospecifiedElement(formApprovalName);
		CommonUtils.waitForElementVisibility("//*[@text='" + formApprovalName + "']");
		Forms_basic.verifyFormPagesAndFill();
		clickSaveAndSubmitForApproval();
		CommonUtils.waitForElementVisibility("//*[contains(@text,'APPROVALS')]");
	}

	// click on save and submit for approval
	public static void clickSaveAndSubmitForApproval() {
		AndroidLocators.resourceId("in.spoors.effortplus:id/saveForm").click();
		CommonUtils.alertContentXpath();
		CommonUtils.handling_alert("formSaveWorkflowButton", "formSaveWorkflowButton",
				"in.spoors.effortplus:id/formSaveWorkflowButton", "in.spoors.effortplus:id/formSaveWorkflowButton",
				"//*[@text='SAVE & SUBMIT FOR APPROVAL']", "//*[@text='SAVE & SUBMIT FOR APPROVAL']");
	}

	// going to approval/reject/re-submit/cancel page
	public static void clickOnApprovalForm() {
		AndroidLocators.clickElementusingXPath(
				"(//*[@text='WAITING FOR MY APPROVAL']/parent::*/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout//*[@resource-id='in.spoors.effortplus:id/form_spec_title_text_view'])[1]");
		CommonUtils.waitForElementVisibility("//*[@text='Workflow details']");
		CommonUtils.handling_alert("viewFormButton", "viewFormButton", "in.spoors.effortplus:id/viewFormButton",
				"in.spoors.effortplus:id/viewFormButton", "//*[@text='VIEW FORM']", "//*[@text='VIEW FORM']");
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/toolbar']");
	}

	// reject ApprovalProcess
	public static void reject_approvalProcess() {
		clickOnApprovalForm();
		CommonUtils.handling_alert("rejectButton", "rejectButton", "in.spoors.effortplus:id/rejectButton",
				"in.spoors.effortplus:id/rejectButton", "//*[@text='REJECT']", "//*[@text='REJECT']");
		remarksForApproveCancelResubmitAndCancel();
		CommonUtils.waitForElementVisibility("//*[@text='Workflow details']");
		CommonUtils.goBackward();
	}

	// approve ApprovalProcess
	public static void approve_approvalProcess() {
		clickOnApprovalForm();
		CommonUtils.handling_alert("approveButton", "approveButton", "in.spoors.effortplus:id/approveButton",
				"in.spoors.effortplus:id/approveButton", "//*[@text='APPROVE']", "//*[@text='APPROVE']");
		remarksForApproveCancelResubmitAndCancel();
		CommonUtils.waitForElementVisibility("//*[@text='Workflow details']");
		CommonUtils.goBackward();
	}

	// re-submit ApprovalProcess
	public static void resubmit_approvalProcess() {
		clickOnApprovalForm();
		CommonUtils.handling_alert("resubmitButton", "resubmitButton", "in.spoors.effortplus:id/resubmitButton",
				"in.spoors.effortplus:id/resubmitButton", "//*[@text='RE-SUBMIT']", "//*[@text='RE-SUBMIT']");
		remarksForApproveCancelResubmitAndCancel();
		CommonUtils.waitForElementVisibility("//*[@text='Workflow details']");
		CommonUtils.goBackward();
	}

	// cancel ApprovaPprocess
	public static void cancel_approvalProcess() throws MalformedURLException, InterruptedException {
		clickOnApprovalForm();
		CommonUtils.handling_alert("cancelButton", "cancelButton", "in.spoors.effortplus:id/cancelButton",
				"in.spoors.effortplus:id/cancelButton", "//*[@text='  CANCEL  ']", "//*[@text='  CANCEL  ']");
		remarksForApproveCancelResubmitAndCancel();
		CommonUtils.waitForElementVisibility("//*[@text='Workflow details']");
	}
	
	//move to home page
	public static void moveToHomeScreen() throws MalformedURLException, InterruptedException {
		CommonUtils.goBackward();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}

	// remarks input
	public static void remarksForApproveCancelResubmitAndCancel() {
		if (AndroidLocators.resourceId("new UiSelector().resourceId(\"android:id/alertTitle\")")
				.isDisplayed()) {
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.keyboardHide();
			CommonUtils.handling_alert("button1", "button1", "android:id/button1", "android:id/button1",
					"//*[@text='SUBMIT']", "//*[@text='SUBMIT']");
		} else if (AndroidLocators.returnUsingId("alertTitle").isDisplayed()) {
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.keyboardHide();
			CommonUtils.handling_alert("button1", "button1", "android:id/button1", "android:id/button1",
					"//*[@text='SUBMIT']", "//*[@text='SUBMIT']");
		} else {
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.keyboardHide();
			CommonUtils.handling_alert("button1", "button1", "android:id/button1", "android:id/button1",
					"//*[@text='SUBMIT']", "//*[@text='SUBMIT']");
		}
	}
}
