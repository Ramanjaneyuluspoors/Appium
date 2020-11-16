package Actions;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;
import utils.Forms;
import utils.MediaPermission;

public class HomepageAction {
	public static pagefactory.HomePage HomePage;

	public HomepageAction() {
		HomePage = new pagefactory.HomePage();
		PageFactory.initElements(CommonUtils.getdriver(), HomePage);
	}

	// verify user sign-in and perform accordingly
	public static void signInAction() throws MalformedURLException, InterruptedException {
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/startStopWorkSwitch']");
		CommonUtils.SwitchStatus("startStopWorkSwitch");
		if (CommonUtils.SwitchStatus("startStopWorkSwitch").contains("OFF")) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@class='android.widget.Switch'][@text='OFF']"))
					.click();
			Thread.sleep(1000);
			sign_in_Options();
		} // closing if block
	}

	//signin cases
	public static void sign_in_Options() throws InterruptedException, MalformedURLException {
		// sign-in form(if sign-in form exist then capturing location page will not
		// display)
		CommonUtils.interruptSyncAndLetmeWork();
		if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
			Forms.verifyFormPagesAndFill();
		} else if (CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).click();
			if (CommonUtils.getdriver().findElement(By.id("heading")).isDisplayed()) {
				CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
			}
			CommonUtils.interruptSyncAndLetmeWork();
			if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
				Forms.verifyFormPagesAndFill();
			}
		} else if (CommonUtils.getdriver().findElement(By.id("heading")).isDisplayed()) {
			CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
		} else if (CommonUtils.getdriver().findElement(MobileBy.id("button1")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.id("button1")).click();
		} else if (CommonUtils.getdriver()
				.findElement(
						MobileBy.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_message']"))
				.isDisplayed()) {
			MediaPermission.signinMediaPermission();
			CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).click();
		} else {
			System.out.println("Signin activity is not found");
		}
	}
	
	// verifying user home page navigation
	public static void NavigationVerfication() throws InterruptedException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@text='Home']");
		MobileElement name = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Home']"));
		if (name.getText().contains("Home")) {
			System.out.println("---- User navigation is done ---- ");
		} else {
			System.out.println("*** App is taking more time to load *** ");
		}
	}

	// signout
	public static void signOutAction() throws MalformedURLException, InterruptedException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch']");
		CommonUtils.SwitchStatus("startStopWorkSwitch");
		if (CommonUtils.SwitchStatus("startStopWorkSwitch").contains("ON")) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@class='android.widget.Switch'][@text='ON']"))
					.click();
			// verify if sign-out form exist then fill if not signout from map
			signout_cases();
		}
	}
	
	// signout cases
	public static void signout_cases() throws InterruptedException, MalformedURLException {
		CommonUtils.interruptSyncAndLetmeWork();
		if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
			Forms.verifyFormPagesAndFill();
		} else if (CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).click();
			// capture reason
			if (CommonUtils.getdriver().findElement(By.id("heading")).isDisplayed()) {
				CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
			}
			if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
				Forms.verifyFormPagesAndFill();
			}
		} else if (CommonUtils.getdriver().findElement(MobileBy.id("button1")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("button1")).click();
		} else {
			System.out.println("Location not found");
		}
	}

	//form alert signout
	public static void form_SignIn_SignOut() {
		if (CommonUtils.getdriver().findElementsByXPath("//*[@text='SIGN-IN']").size() > 0) {
			CommonUtils.getdriver().findElementByXPath("//*[@text='SIGN-IN']").click();
		} else if (CommonUtils.getdriver().findElementsByXPath("//*[@text='SIGN-OUT']").size() > 0) {
			CommonUtils.getdriver().findElementByXPath("//*[@text='SIGN-OUT']").click();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
