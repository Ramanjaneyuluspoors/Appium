package Actions;

import java.net.MalformedURLException;

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
		try {
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/startStopWorkSwitch']");
			MobileElement SigninButton = CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[@text='OFF']"));
			if (SigninButton.getText().contains("OFF")) {
				SigninButton.click();
				Thread.sleep(2000);
				// sign-in form(if sign-in form exist then capturing location page will not
				// display )
				try {
					CommonUtils.interruptSyncAndLetmeWork();
					if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
						Forms.verifyFormPagesAndFill();
					}
				} catch (Exception e) {
					MediaPermission.signinMediaPermission();
				}
			}
		} catch (Exception e) {
			System.out.println("User already begin the day!!");
		}

	}

	// verifying user home page navigation
	public static void NavigationVerfication() throws InterruptedException {
		Thread.sleep(2000);
		CommonUtils.waitForElementVisibility("//*[@text='Home']");
		MobileElement name = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Home']"));
		if (name.getText().contains("Home")) {
			System.out.println("User navigation is done");
		} else {
			System.out.println("App is taking more time to load");
		}
	}

	// signout
	public static void signOutAction() throws MalformedURLException, InterruptedException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch']");
		MobileElement SigninButton = CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Switch"));
		if (SigninButton.getText().contains("ON")) {
			SigninButton.click();
			// verify if sign-out form exist then fill if not signout from map
			try {
				if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
					Forms.verifyFormPagesAndFill();
				}
			} catch (Exception e) {
				MediaPermission.signinMediaPermission();
			}
		}
	}

}
