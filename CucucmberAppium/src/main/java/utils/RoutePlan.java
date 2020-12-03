package utils;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import Actions.CustomerPageActions;
import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;

public class RoutePlan {

	// go to route plan
	public static void GoingToRoutes(String Routeplans) throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("" + Routeplans + "");
		CommonUtils.waitForElementVisibility("//*[@text='Route plans']");
	}

	// verify today route with name
	public static void verifyRoute(String routeName) {
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='TODAYS']")).click();
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + routeName + "']")).isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + routeName + "']")).click();
			} else if (CommonUtils.getdriver().findElement(MobileBy.id("loadMoreButton")).isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.id("loadMoreButton")).click();
				CommonUtils.waitForElementVisibility("//*[@text='" + routeName + "']");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + routeName + "']")).click();
			}
		} catch (Exception e) {
			CommonUtils.getdriver().findElements(MobileBy.id("card_view")).get(0).click();
		}
		CommonUtils.waitForElementVisibility("//*[@content-desc='Navigate up']");
	}

	// verify route customer check-in
	public static void routeCusCheckin() throws MalformedURLException, InterruptedException {
		List<MobileElement> routeCusCheckin = CommonUtils.getdriver().findElements(MobileBy.id("checkinoutButton"));
		System.out.println("route plan cus count:" + routeCusCheckin);
		if (routeCusCheckin.get(0).getText().contains("OFF")) {
			routeCusCheckin.get(0).click();
			routeCheckInOrCheckOutAnyway();
		} else {
			System.out.println("User already checked into customer");
			CommonUtils.getdriver().findElement(MobileBy.id("summaryBtn")).click();
		}
		CustomerPageActions.goToActivityScreen();
	}

	// check-in or checkout anyway alert
	public static void routeCheckInOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkin = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@resource-id='android:id/button1']"));
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			CustomerPageActions.customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			String randomstring = RandomStringUtils.randomAlphabetic(5).toLowerCase();
			CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).sendKeys(randomstring);
			CommonUtils.keyboardHide();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SUBMIT']")).click();
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/completeRoutePlan']");
			CommonUtils.implicitWait();
			routeCusCheckin();
		}
		CustomerPageActions.goToActivityScreen();
	}

	// perform route Customer activity
	public static void performRouteActivity() throws InterruptedException, MalformedURLException {
		if (CommonUtils.getdriver().findElements(MobileBy.id("activityItem1")).size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.id("activityItem1")).get(0).click();
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/saveForm']");
		}
	}
 
	// click complete route visit 
	public static void completeClientVisit() throws InterruptedException {
		try {
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/completeClientVisit']");
			if (CommonUtils.getdriver().findElement(MobileBy.id("completeClientVisit")).isEnabled()) {
				CommonUtils.getdriver().findElement(MobileBy.id("completeClientVisit")).click();
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
		while (!(CommonUtils.getdriver().findElements(MobileBy.id("completeRoutePlan")).size() > 0)) {
			CommonUtils.getdriver().navigate().back();
			if (CommonUtils.getdriver().findElements(MobileBy.id("completeRoutePlan")).size() > 0) {
				break;
			}
		}
		if (CommonUtils.getdriver().findElement(MobileBy.id("completeRoutePlan")).isEnabled()) {
			CommonUtils.getdriver().findElement(MobileBy.id("completeRoutePlan")).click();
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

	// Go to dayplan by swiping to dayplan
	public static void goToDayPlan(String Dayplanner) throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("" + Dayplanner + "");
		CommonUtils.waitForElementVisibility("//*[@text='Day planner']");
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
			if (CommonUtils.getdriver().findElement(MobileBy.id("textview")).isDisplayed()) {
				System.out.println("---- Select Customer/Custom Entity popup is displayed ----");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Customers']")).click();
				CommonUtils.waitForElementVisibility("//*[@text='Customers']");
			}
		} catch (Exception e) {
			System.out.println("**** Select Customer/Custom Entity popup is not displayed ****");
		}
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Customers']")).isDisplayed()) {
				System.out.println(".... Day plan with customer are not exist, lets pick customers ....");
				List<MobileElement> selectCheckbox = CommonUtils.getdriver()
						.findElements(MobileBy.id("pickCustomerCheck"));
				MobileActionGesture.singleLongPress(selectCheckbox.get(0));
				MobileActionGesture.singleLongPress(selectCheckbox.get(1));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Select']")).click();
				CommonUtils.waitForElementVisibility("//*[@text='Day planner']");
				calendarClick();
				veirfyDayPlanCusCheckin();
			} else {
				System.out.println("**** Day plan with customers are exist ****");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'Dayplan progress')]"))
						.isDisplayed();
				veirfyDayPlanCusCheckin();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// verify day plan with customer check-in
	public static void veirfyDayPlanCusCheckin() throws MalformedURLException, InterruptedException {
		List<MobileElement> daypalnCusCheckin = CommonUtils.getdriver().findElements(By.id("checkinoutButton"));
		System.out.println("---- Dayplan customer count ---- :" + daypalnCusCheckin.size());
		if (daypalnCusCheckin.get(0).getText().contains("OFF")) {
			System.out.println("---- User going to checkin to customer ----");
			daypalnCusCheckin.get(0).click();
			dayPlancheckInOrCheckOutAnyway();
		} else {
			System.out.println("**** User already checked into customer, perform dayplan customer activity ****");
			CommonUtils.getdriver().findElement(MobileBy.id("summaryBtn")).click();
		}
		CustomerPageActions.goToActivityScreen();
	}

	// check-in or checkout anyway alert
	public static void dayPlancheckInOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkin = CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"));
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			CustomerPageActions.customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			String randomstring = RandomStringUtils.randomAlphabetic(5).toLowerCase();
			CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).sendKeys(randomstring);
			CommonUtils.keyboardHide();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SUBMIT']")).click();
			CommonUtils.waitForElementVisibility("//*[@text='Day plan']");
			veirfyDayPlanCusCheckin();
		}
		CustomerPageActions.goToActivityScreen();
	}

}
