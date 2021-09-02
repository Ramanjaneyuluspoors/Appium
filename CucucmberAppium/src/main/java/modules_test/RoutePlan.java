package modules_test;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;


import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import utils.CommonUtils;

public class RoutePlan {

	// go to route plan
	public static void GoingToRoutes(String Routeplans) throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("" + Routeplans + "");
		CommonUtils.waitForElementVisibility("//*[@text='Route plans']");
	}

	// verify today route with name
	public static void verifyRoute(String routeName) {
		AndroidLocators.clickElementusingXPath("//*[@text='TODAYS']");
		try {
			if (AndroidLocators.xpath("//*[@text='" + routeName + "']").isDisplayed()) {
				AndroidLocators.clickElementusingXPath("//*[@text='" + routeName + "']");
			} else if (AndroidLocators.returnUsingId("loadMoreButton").isDisplayed()) {
				AndroidLocators.clickElementusingID("loadMoreButton");
				CommonUtils.waitForElementVisibility("//*[@text='" + routeName + "']");
				AndroidLocators.clickElementusingXPath("//*[@text='" + routeName + "']");
			}
		} catch (Exception e) {
			CommonUtils.getdriver().findElements(MobileBy.id("card_view")).get(0).click();
		}
		CommonUtils.waitForElementVisibility("//*[@content-desc='Navigate up']");
	}

	// verify route customer check-in
	public static void routeCusCheckin() throws MalformedURLException, InterruptedException {
		List<MobileElement> routeCusCheckin = AndroidLocators.findElements_With_Id("checkinoutButton");
		System.out.println("route plan cus count:" + routeCusCheckin);
		if (routeCusCheckin.get(0).getText().contains("OFF")) {
			routeCusCheckin.get(0).click();
			routeCheckInOrCheckOutAnyway();
		} else {
			System.out.println("User already checked into customer");
			AndroidLocators.clickElementusingID("summaryBtn");
		}
		CustomerPageActions.goToActivityScreen();
	}

	// check-in or checkout anyway alert
	public static void routeCheckInOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkin = AndroidLocators.resourceId("android:id/button1");
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			CustomerPageActions.customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.getdriver().hideKeyboard();
			AndroidLocators.clickElementusingXPath("//*[@text='SUBMIT']");
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/completeRoutePlan']");
			CommonUtils.implicitWait();
			routeCusCheckin();
		}
		CustomerPageActions.goToActivityScreen();
	}

	// perform route Customer activity
	public static void performRouteActivity() throws InterruptedException, MalformedURLException {
		if (AndroidLocators.findElements_With_Id("activityItem1").size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.id("activityItem1")).get(0).click();
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/saveForm']");
		}
	}
 
	// click complete route visit 
	public static void completeClientVisit() throws InterruptedException {
		try {
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/completeClientVisit']");
			if (AndroidLocators.returnUsingId("completeClientVisit").isEnabled()) {
				AndroidLocators.clickElementusingID("completeClientVisit");
				CommonUtils.OkButton("OK"); 
				CommonUtils.implicitWait();
			} else {
				System.out.println("Route visit is already completed");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// complete route visit and check-out customer
	public static void routeCusCheckout() throws InterruptedException, MalformedURLException {
		completeClientVisit();
		while (!(AndroidLocators.findElements_With_Id("completeRoutePlan").size() > 0)) {
			CommonUtils.getdriver().navigate().back();
			if (AndroidLocators.findElements_With_Id("completeRoutePlan").size() > 0) {
				break;
			}
		}
		if (AndroidLocators.returnUsingId("completeRoutePlan").isEnabled()) {
			AndroidLocators.clickElementusingID("completeRoutePlan");
		} else {
			CommonUtils.getdriver().navigate().back();
			CommonUtils.waitForElementVisibility("//*[@content-desc='Open drawer']");
		}
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
		try {
			CustomerPageActions.cusCheckoutInHomepage();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	// click on current day in calendar by using date
	public static void calendarClick() throws InterruptedException {
		// retrieving date from LocalDate library in java
		LocalDate CurrentDate = LocalDate.now();
		System.out.println("Current Date: " + CurrentDate);
		// get day and month of current date
		int day = CurrentDate.getDayOfMonth();
		Month month = CurrentDate.getMonth();
		// printing current day and current month
		System.out.println("Month : " + month);
		System.out.println("Day : " + day);
		// getting day plan calender outview element
		AndroidElement calender = (AndroidElement) CommonUtils.getdriver().findElementById("cardGrid");
		// getting the element of calendar inside dates and passing the current day
		AndroidElement clicktDate = (AndroidElement) calender.findElementByXPath("//*[@text='" + day + "']");
		clicktDate.click();
	}

	//verify dayplan page
	public static void day_Plan_Page() throws MalformedURLException, InterruptedException {
		try {
			if (AndroidLocators.resourceId("in.spoors.effortplus:id/progressText").isDisplayed()) {
				veirfyDayPlanCusCheckin();
			}
		} catch (Exception e) {
			verifyDayPlanInCustomer();
		}
	}
	
	// verify customer with day plan exist or not
	public static void verifyDayPlanInCustomer() throws InterruptedException, MalformedURLException {
		CommonUtils.implicitWait();
		try {
			if (AndroidLocators.returnUsingId("textview").isDisplayed()) {
				System.out.println("---- Select Customer/Custom Entity popup is displayed ----");
				AndroidLocators.clickElementusingXPath("//*[@text='Customers']");
				CommonUtils.waitForElementVisibility("//*[@text='Customers']");
			}
		} catch (Exception e) {
			System.out.println("**** Select Customer/Custom Entity popup is not displayed ****");
		}
		try {
			if (AndroidLocators.xpath("//*[@text='Customers']").isDisplayed()) {
				System.out.println(".... Day plan with customer are not exist, lets pick customers ....");
				List<MobileElement> selectCheckbox = AndroidLocators.findElements_With_Id("pickCustomerCheck");
				MobileActionGesture.singleLongPress(selectCheckbox.get(0));
				MobileActionGesture.singleLongPress(selectCheckbox.get(1));
				AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
				CommonUtils.waitForElementVisibility("//*[@text='Day planner']");
				calendarClick();
				veirfyDayPlanCusCheckin();
			} else {
				System.out.println("**** Day plan with customers are exist ****");
				AndroidLocators.xpath("//*[contains(@text,'Dayplan progress')]").isDisplayed();
				veirfyDayPlanCusCheckin();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// verify day plan with customer check-in
	public static void veirfyDayPlanCusCheckin() throws MalformedURLException, InterruptedException {
		List<MobileElement> daypalnCusCheckin = AndroidLocators.findElements_With_Id("checkinoutButton");
		System.out.println("---- Dayplan customer count ---- :" + daypalnCusCheckin.size());
		if (daypalnCusCheckin.get(0).getText().contains("OFF")) {
			System.out.println("---- User going to checkin to customer ----");
			daypalnCusCheckin.get(0).click();
			dayPlancheckInOrCheckOutAnyway();
		} else {
			System.out.println("**** User already checked into customer, perform dayplan customer activity ****");
			AndroidLocators.clickElementusingID("summaryBtn");
		}
		CustomerPageActions.goToActivityScreen();
	}

	// check-in or checkout anyway alert
	public static void dayPlancheckInOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkin = AndroidLocators.resourceId("android:id/button1");
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			CustomerPageActions.customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.getdriver().hideKeyboard();
			AndroidLocators.clickElementusingXPath("//*[@text='SUBMIT']");
			CommonUtils.waitForElementVisibility("//*[@text='Day plan']");
			veirfyDayPlanCusCheckin();
		}
		CustomerPageActions.goToActivityScreen();
	}

}
