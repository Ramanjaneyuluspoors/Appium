package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;

import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class Leaves {

	// clicking on + icon in leaves screens
	public static void applyLeave() {
		if (CommonUtils.getdriver().findElement(By.id("applyForLeave")).isDisplayed()) {
			AndroidLocators.clickElementusingID("applyForLeave");
		} else if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Apply for leave']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Apply for leave']");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/applyForLeave\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId(
					"new UiSelector().resourceId(\"in.spoors.effortplus:id/applyForLeave\")");
		}
		CommonUtils.waitForElementVisibility("//*[@text='Leave']");
	}

	// select dropdown value
	public static void select_dropable_value(String dropdown_value) {
		if (CommonUtils.getdriver().findElement(By.className("android.widget.Spinner")).isDisplayed()) {
			// click elelment using classname
			AndroidLocators.clickElementusingClassName("android.widget.Spinner");
			// scroll to specified element and click
			MobileActionGesture.scrollTospecifiedElement(dropdown_value);
		}
	}

	// leave inputs
	public static void applying_leave_inputs() throws MalformedURLException, InterruptedException, ParseException {
		// selecting leave start date
		if (CommonUtils.getdriver().findElement(By.id("startDateButton")).isDisplayed()) {
			AndroidLocators.clickElementusingID("startDateButton");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/startDateButton\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/startDateButton");
		} else if (CommonUtils.getdriver()
				.findElement(
						By.xpath("//android.widget.Button[@resource-id='in.spoors.effortplus:id/startDateButton']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath(
					"//android.widget.Button[@resource-id='in.spoors.effortplus:id/startDateButton']");
		}
		// picking start date input
		Forms_basic.dateScriptInForms(1);

		// selecting leave start time
		if (CommonUtils.getdriver().findElement(By.id("startTimeButton")).isDisplayed()) {
			AndroidLocators.clickElementusingID("startTimeButton");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/startTimeButton\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/startTimeButton");
		} else if (CommonUtils.getdriver()
				.findElement(
						By.xpath("//android.widget.Button[@resource-id='in.spoors.effortplus:id/startTimeButton']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath(
					"//android.widget.Button[@resource-id='in.spoors.effortplus:id/startTimeButton']");
		}
		// picking start time
		Work.workEndTime(1, 1);

		// selecting leave end date
		if (CommonUtils.getdriver().findElement(By.id("endDateButton")).isDisplayed()) {
			AndroidLocators.clickElementusingID("endDateButton");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/endDateButton\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/endDateButton");
		} else if (CommonUtils.getdriver()
				.findElement(By.xpath("//android.widget.Button[@resource-id='in.spoors.effortplus:id/endDateButton']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath(
					"//android.widget.Button[@resource-id='in.spoors.effortplus:id/endDateButton']");
		}
		// picking end date
		Forms_basic.dateScriptInForms(1);

		// selecting leave end time
		if (CommonUtils.getdriver().findElement(By.id("endTimeButton")).isDisplayed()) {
			AndroidLocators.clickElementusingID("endTimeButton");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/endTimeButton\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/endTimeButton");
		} else if (CommonUtils.getdriver()
				.findElement(By.xpath("//android.widget.Button[@resource-id='in.spoors.effortplus:id/endTimeButton']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath(
					"//android.widget.Button[@resource-id='in.spoors.effortplus:id/endTimeButton']");
		}
		// picking end time
		Work.workEndTime(1, 1);

		// inputting remarks
		if (CommonUtils.getdriver().findElement(By.id("remarksEditText")).isDisplayed()) {
			AndroidLocators.sendInputusing_Id("remarksEditText");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/remarksEditText\")"))
				.isDisplayed()) {
			AndroidLocators.sendInputusing_ResourceId("in.spoors.effortplus:id/remarksEditText");
		} else if (CommonUtils.getdriver()
				.findElement(By.xpath("//android.widget.Button[@resource-id='in.spoors.effortplus:id/endTimeButton']"))
				.isDisplayed()) {
			AndroidLocators.sendInputusing_XPath(
					"//android.widget.Button[@resource-id='in.spoors.effortplus:id/endTimeButton']");
		}
	}

	// saving leave
	public static void clicking_on_Leave_savebutton() {
		if (CommonUtils.getdriver().findElement(By.id("saveLeave")).isDisplayed()) {
			AndroidLocators.clickElementusingID("saveLeave");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/saveLeave\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/saveLeave");
		} else if (CommonUtils.getdriver()
				.findElement(By.xpath("//android.widget.Button[@resource-id='in.spoors.effortplus:id/endTimeButton']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Save']");
		}
		CommonUtils.handling_alert("alertTitle", "button1", "android:id/alertTitle", "android:id/button1",
				"//android.widget.TextView[@resource-id='android:id/alertTitle']", "//*[@text='OK']");
		CommonUtils.waitForElementVisibility("//*[@text='Leaves']");
	}

	// leave apply
	public static void leave_apply(String leaveType)
			throws MalformedURLException, InterruptedException, ParseException {
		applyLeave();
		select_dropable_value(leaveType);
		applying_leave_inputs();
		clicking_on_Leave_savebutton();
	}

	// leave status
	public static void leave_Status() {
		List<MobileElement> leaveStatus = CommonUtils.getdriver().findElements(By.id("statusTextView"));
		boolean isFound = false;
		for (int i = 0; i < leaveStatus.size(); i++) {
			String leaves_status_names = leaveStatus.get(i).getText();
			MobileActionGesture.scrollTospecifiedElement(leaves_status_names);
			if (leaves_status_names.contains("Applied for leave")) {
				System.out.println("---- Leave applied successfully ----");
			} else if (leaves_status_names.contains("Leave Approved")) {
				System.out.println("---- Leave approved successfully ----");
			} else if (leaves_status_names.contains("Leave Cancelled")) {
				System.out.println("---- Leave cancelled successfully ----");
			} else if (leaves_status_names.contains("Leave Rejected")) {
				System.out.println("---- Leave rejected successfully ----");
			}
			if (isFound = true)
				break;
		}
	}

	// cancel applied leave
	public static void leave_cancel() {
		MobileActionGesture.scrollTospecifiedElement("Applied for leave");
		if (CommonUtils.getdriver().findElement(By.xpath("//*[@text='Applied for leave']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@text='Applied for leave']");
			CommonUtils.waitForElementVisibility("//*[@text='Leave']");
			cancel_leave();
			leave_Status();
		}
	}

	// cancel leave
	public static void cancel_leave() {
		if (CommonUtils.getdriver().findElement(By.id("cancelLeave")).isDisplayed()) {
			AndroidLocators.clickElementusingID("cancelLeave");
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/cancelLeave\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/cancelLeave");
		} else if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Cancel leave']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Cancel leave']");
		}
		CommonUtils.handling_alert("alertTitle", "button1", "android:id/alertTitle", "android:id/button1",
				"//*[@text='Leave cancellation']", "//*[@text='YES']");
		CommonUtils.waitForElementVisibility("//*[@text='Leaves']");
	}

	
	// leave approval
	public static void leave_Approval() {
		if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Leaves']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Leaves']");
			if (CommonUtils.getdriver().findElement(By.id("pendingLeavesLinearLayout")).isDisplayed()) {
				AndroidLocators.clickElementusingID("pendingLeavesLinearLayout");
				MobileActionGesture.scrollTospecifiedElement("Applied for leave");
				CommonUtils.waitForElementVisibility("//*[@text='Leave']");
				CommonUtils.handling_alert("leaveApproveButton", "leaveApproveButton",
						"in.spoors.effortplus:id/leaveApproveButton", "in.spoors.effortplus:id/leaveApproveButton",
						"//*[@text='APPROVE']", "//*[@text='APPROVE']");
				CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/parentPanel']");
				AndroidLocators.sendInputusing_Classname("android.widget.EditText");
				CommonUtils.keyboardHide();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SAVE']")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Leaves']");
				leave_Status();
			}
		}
	}

	// leave reject
	public static void leave_reject() {
		if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Leaves']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Leaves']");
			if (CommonUtils.getdriver().findElement(By.id("pendingLeavesLinearLayout")).isDisplayed()) {
				AndroidLocators.clickElementusingID("pendingLeavesLinearLayout");
				MobileActionGesture.scrollTospecifiedElement("Applied for leave");
				CommonUtils.waitForElementVisibility("//*[@text='Leave']");
				CommonUtils.handling_alert("leaveRejectButton", "leaveRejectButton",
						"in.spoors.effortplus:id/leaveRejectButton", "in.spoors.effortplus:id/leaveRejectButton",
						"//*[@text='REJECT']", "//*[@text='REJECT']");
				CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/parentPanel']");
				AndroidLocators.sendInputusing_Classname("android.widget.EditText");
				CommonUtils.keyboardHide();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SAVE']")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Leaves']");
				leave_Status();
			}
		}
	}
}
