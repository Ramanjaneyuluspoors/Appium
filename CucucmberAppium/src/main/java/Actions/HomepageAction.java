package Actions;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import common_Steps.AndroidLocators;
import io.appium.java_client.MobileElement;
import modules_test.Forms_basic;
import utils.CommonUtils;
import utils.MediaPermission;

public class HomepageAction {
	public static pagefactory.HomePage HomePage;

	public HomepageAction() {
		HomePage = new pagefactory.HomePage();
		PageFactory.initElements(CommonUtils.getdriver(), HomePage);
	}

	// verify user sign-in and perform accordingly
	public static void signInAction() throws MalformedURLException, InterruptedException, ParseException {
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/startStopWorkSwitch']");
		CommonUtils.SwitchStatus("startStopWorkSwitch");
		if (CommonUtils.SwitchStatus("startStopWorkSwitch").contains("OFF")) {
			AndroidLocators.clickElementusingXPath("//*[@class='android.widget.Switch'][@text='OFF']");
			Thread.sleep(1000);
			sign_in_Options();
		} // closing if block
	}

	// signin cases
	public static void sign_in_Options() throws InterruptedException, MalformedURLException, ParseException {
		// sign-in form(if sign-in form exist then capturing location page will not
		// display)
		CommonUtils.interruptSyncAndLetmeWork();
		if (AndroidLocators.findElements_With_Id("saveForm").size() > 0) {
			Forms_basic.verifyFormPagesAndFill();
			Forms_basic.formSaveButton();
		} else if (AndroidLocators.findElements_With_Id("signInButton").size() > 0) {
			AndroidLocators.clickElementusingID("signInButton");
			if (AndroidLocators.findElements_With_Id("heading").size() > 0) {
				CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
			}
			CommonUtils.interruptSyncAndLetmeWork();
			if (AndroidLocators.findElements_With_Id("saveForm").size() > 0) {
				Forms_basic.verifyFormPagesAndFill();
				Forms_basic.formSaveButton();
			}
		} else if (AndroidLocators.findElements_With_Id("heading").size() > 0) {
			CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
		} else if (AndroidLocators.findElements_With_Xpath("//*[@text='SIGN IN']").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@text='SIGN IN']");
		} else if (AndroidLocators.findElements_With_Xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_message']")
				.size() > 0) {
			MediaPermission.signinMediaPermission();
			AndroidLocators.clickElementusingID("signInButton");
		} else {
			System.out.println("Signin activity is not found");
		}
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch'][@text='ON']");
	}

	// verifying user home page navigation
	public static void NavigationVerfication() throws InterruptedException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@text='Home']");
		MobileElement name = AndroidLocators.xpath("//*[@text='Home']");
		if (name.getText().contains("Home")) {
			System.out.println("---- User navigation is done ---- ");
		} else {
			System.out.println("*** App is taking more time to load *** ");
		}
	}

	// signout
	public static void signOutAction() throws MalformedURLException, InterruptedException, ParseException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch']");
		CommonUtils.SwitchStatus("startStopWorkSwitch");
		if (CommonUtils.SwitchStatus("startStopWorkSwitch").contains("ON")) {
			AndroidLocators.clickElementusingXPath("//*[@class='android.widget.Switch'][@text='ON']");
			// verify if sign-out form exist then fill if not signout from map
			signout_cases();
		}
	}

	// signout cases
	public static void signout_cases() throws InterruptedException, MalformedURLException, ParseException {
		CommonUtils.interruptSyncAndLetmeWork();
		if (AndroidLocators.findElements_With_Id("saveForm").size() > 0) {
			Forms_basic.verifyFormPagesAndFill();
			Forms_basic.formSaveButton();
		} else if (AndroidLocators.findElements_With_Id("signInButton").size() > 0) {
			CommonUtils.getdriver().findElement(By.id("signInButton")).click();
			// capture reason
			if (AndroidLocators.findElements_With_Id("heading").size() > 0) {
				CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
			}
			if (AndroidLocators.findElements_With_Id("saveForm").size() > 0) {
				Forms_basic.verifyFormPagesAndFill();
				Forms_basic.formSaveButton();
			}
		} else if (AndroidLocators.findElements_With_Xpath("//*[@text='SIGN OUT']").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@text='SIGN OUT']");
		} else {
			System.out.println("Location not found");
		}
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch'][@text='OFF']");
	}

	// form alert signout
	public static void form_SignIn_SignOut() {
		if (AndroidLocators.findElements_With_Xpath("//*[@text='SIGN-IN']").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@text='SIGN-IN']");
		} else if (AndroidLocators.findElements_With_Xpath("//*[@text='SIGN-OUT']").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@text='SIGN-IN']");
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// scroll to settings and send debug information
	public static void perform_debug_action() throws MalformedURLException, InterruptedException {
		MobileActionGesture.scrollUsingText("Settings");
		if (AndroidLocators.findElements_With_Xpath("//*[@class='android.widget.LinearLayout']/*[@text='Settings']")
				.size() > 0) {
			AndroidLocators.xpath("//*[@class='android.widget.LinearLayout']/*[@text='Settings']").click();
		} else {
			CommonUtils.openMenu();
			MobileActionGesture.scrollUsingText("Settings");
			Thread.sleep(1000);
		}
		CommonUtils.allow_bluetooth();
		CommonUtils.waitForElementVisibility("//*[@text='Settings']");
	}

	// select work/form/customer/dayplan from home fab dialog
	public static void select_dialog_list(String selectValue) {
		List<MobileElement> selectList = AndroidLocators
				.findElements_With_ResourceId("new UiSelector().resourceId(\"android:id/text1\")");
		for (int i = 0; i < selectList.size(); i++) {
			String List_view_Text = selectList.get(i).getText();
			if (List_view_Text.contains(selectValue)) {
				AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + selectValue + "')]");
				break;
			}
		}
	}
}
