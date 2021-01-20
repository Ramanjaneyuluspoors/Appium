package modules_test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;

import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class Reportsverification {

	// fling to Employee activity wise summary report
	public static void employeeActivityWiseSummaryReport() throws InterruptedException {
		CommonUtils.getdriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(textContains(\"Employee activity wise summary\"));");
		AndroidLocators.clickElementusingXPath("//*[@text='Employee activity wise summary report']");
		CommonUtils.waitForElementVisibility("//*[@text='Employee Activity Wise Summary Report']");
		inputting_data_for_EmployeeActivityWiseSummaryReport();
		clickOnLoadButton();
		CommonUtils.longWaitElementVisibility("//*[@class='android.widget.GridView']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@text='Employee Activity Wise Summary Report']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}

	// inserting data to Employee activity wise summary report
	public static void inputting_data_for_EmployeeActivityWiseSummaryReport() {
		AndroidLocators.clickElementusingClassName("android.widget.Spinner");
		CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/parentPanel']");
		List<MobileElement> selectActivities = CommonUtils.getdriver()
				.findElements(By.className("android.widget.CheckedTextView"));
		for (int i = 0; i < selectActivities.size(); i++) {
			selectActivities.get(i).click();
		}
		CommonUtils.handling_alert("button1", "button1", "android:id/button1", "android:id/button1",
				"//android.widget.Button[@text='OK']", "//android.widget.Button[@text='OK']");

		// picking date range for Employee activity wise summary report
		datePicker("fromDate", "toDate", "1");
	}

	// verifying distance travelled report
	public static void distanceTravelledReport() throws InterruptedException {
		MobileActionGesture.scrollUsingText("Distance Traveled Report");
		AndroidLocators.clickElementusingXPath("//*[@text='Distance Traveled Report']");
		CommonUtils.waitForElementVisibility("//*[@text='Distance Traveled Report']");
		// split date
		String[] currentDate = currentDate().split("-");
		// get date
		String toDate = currentDate[2];
		AndroidLocators.clickElementusingResourceId("fromDate");
		CommonUtils
				.waitForElementVisibility("//*[@class='android.view.View' and ./*[@class='android.widget.GridView']]");
		// picking from date
		AndroidLocators.clickElementusingXPath("//*[@text='" + toDate + "']");
		AndroidLocators.clickElementusingResourceId("toDate");
		CommonUtils
				.waitForElementVisibility("//*[@class='android.view.View' and ./*[@class='android.widget.GridView']]");
		// picking todate
		AndroidLocators.clickElementusingXPath("//*[@text='" + toDate + "']");
		clickOnLoadButton();
		CommonUtils.longWaitElementVisibility("//*[@text='Day Wise Distance Travelled Report']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@text='Distance Traveled Report']");
		clickOnBackArrow();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}

	// verifying employee wise activity summary report
	public static void employeeWiseActivitySummaryReport() throws InterruptedException {
		MobileActionGesture.scrollUsingText("Employee wise activity summary report");
		AndroidLocators.clickElementusingXPath("//*[@text='Employee wise activity summary report']");
		CommonUtils.waitForElementVisibility("//*[@text='Employee Wise Customer Visit Report']");
		// picking date range for Employee wise activity summary report
		datePicker("from", "to", "1");
		clickOnLoadButton();
		CommonUtils.longWaitElementVisibility("//*[@text='Send email as xls']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@text='Employee Wise Customer Visit Report']");
		clickOnBackArrow();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}

	// employee sign-in sign-out report
	public static void employeeSignInSignOutReport() throws InterruptedException {
		MobileActionGesture.scrollUsingText("Employee Sign-in Sign-out Report");
		AndroidLocators.clickElementusingXPath("//*[@text='Employee Sign-in Sign-out Report']");
		CommonUtils.longWaitElementVisibility("//*[@text='Employee Sign-In Sign-Out Report']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}
	
	// advanced activity summary report
	public static void advancedActivitySummaryReport() throws InterruptedException {
		MobileActionGesture.scrollTospecifiedElement("Advanced Activity Summary");
		CommonUtils.longWaitElementVisibility("//*[@text='Advanced Activity Summary Report']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}
	
	//Target Vs Achievement Report
	public static void targetVsAchievementReport(String EmpName) {
		MobileActionGesture.scrollTospecifiedElement("Target Vs Achievement");
		CommonUtils.longWaitElementVisibility("//*[@text='Target Vs Achievement Report']");
		AndroidLocators.clickElementusingResourceId("empIds");
		CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/parentPanel']");
		AndroidLocators.clickElementusingXPath("//*[@text='" + EmpName + "']");
		AndroidLocators.clickElementusingXPath("//*[@text='OK']");
		AndroidLocators.clickElementusingResourceId("entityId");
	}
	

	// verifying dayplan report
	public static void dayPlanReport(String EmployeeName) throws InterruptedException {
		MobileActionGesture.scrollUsingText("Day Plan Report");
		AndroidLocators.clickElementusingXPath("//*[@text='Day Plan Report']");
		CommonUtils.waitForElementVisibility("//*[@text='Day Plan Report']");
		pickEmployeeUsingSearch(EmployeeName);
		AndroidLocators.clickElementusingResourceId("fromDate");
		// split date
		String[] currentDate = currentDate().split("-");
		// get date
		String fromDate = currentDate[2];
		// picking from date in calendar
		AndroidLocators.clickElementusingXPath("//*[@text='" + fromDate + "']");
		CommonUtils.waitForElementVisibility("//*[@text='LOAD']");
		AndroidLocators.clickElementusingXPath("//*[@text='LOAD']");
		CommonUtils.longWaitElementVisibility("//*[@resource-id='rcj']");
	}

	//picking an employee using search
	public static void pickEmployeeUsingSearch(String EmployeeName) {
		AndroidLocators.clickElementusingXPath("//*[@text='pick']");
		CommonUtils.waitForElementVisibility("//*[@text='Employees']");
		AndroidLocators.clickElementusingID("action_search");
		AndroidLocators.enterTextusingID("search_src_text", "" + EmployeeName + "");
		AndroidLocators.clickElementusingID("employeeNameTextView");
		CommonUtils.waitForElementVisibility("//*[@text='" + EmployeeName + "']");
	}

	// validate dayplan report data
	public static void validatingDayPlanReportData() throws InterruptedException {
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@text='Day Plan Report']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}

	// employee coverage report
	public static void employeeCoverageReport(String EmployeeName) throws InterruptedException {
		MobileActionGesture.scrollTospecifiedElement("Employee Coverage");
		CommonUtils.waitForElementVisibility("//*[@text='Employee Coverage Report']");
		pickEmployeeUsingSearch(EmployeeName);
		datePicker("fromDate", "toDate", "1");
		AndroidLocators.clickElementusingXPath("//android.widget.RadioButton[1]");
		MobileActionGesture.scrollUsingText("LOAD");
		AndroidLocators.clickElementusingXPath("//*[@text='LOAD']");
		CommonUtils.longWaitElementVisibility("//*[@resource-id='rcj']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@text='Employee Coverage Report']");
		pickEmployeeUsingSearch(EmployeeName);
		datePicker("fromDate", "toDate", "1");
		AndroidLocators.clickElementusingXPath("//android.widget.RadioButton[2]");
		MobileActionGesture.scrollUsingText("LOAD");
		AndroidLocators.clickElementusingXPath("//*[@text='LOAD']");
		CommonUtils.longWaitElementVisibility("//*[@resource-id='rcj']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@text='Employee Coverage Report']");
		clickOnBackArrow();
		CommonUtils.longWaitElementVisibility("//*[@content-desc='Reload']");
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Reload']");
	}

	// click >> calendar
	public static void moveRight() {
		AndroidLocators.clickElementusingXPath("//android.widget.GridView/android.view.View[1]/android.view.View[1]");
	}

	// click << in calendar
	public static void moveLeft() {
		AndroidLocators.clickElementusingXPath("//android.widget.GridView/android.view.View[1]/android.view.View[3]");
	}

	// click on month year in calendar
	public static void clickOnMonthYear() {
		AndroidLocators.clickElementusingXPath("//android.widget.GridView/android.view.View[1]/android.view.View[2]");
		CommonUtils
				.waitForElementVisibility("//*[@class='android.view.View' and ./*[@class='android.widget.GridView']]");
	}

	// click on year
	public static void clickOnYear() {
		clickOnMonthYear();
	}

	// click on back arrow symbol
	public static void clickOnBackArrow() throws InterruptedException {
		AndroidLocators.clickElementusingXPath("(//android.view.View/android.widget.TextView)[1]");
		Thread.sleep(5000);
	}

	// click on load button
	public static void clickOnLoadButton() {
		AndroidLocators.clickElementusingXPath("//*[@text='Load']");
	}

	// automating datepicker in calendar
	public static void datePicker(String fromDateLocator, String toDateLocator, String fromdate) {
		AndroidLocators.clickElementusingResourceId("" + fromDateLocator + "");
		CommonUtils
				.waitForElementVisibility("//*[@class='android.view.View' and ./*[@class='android.widget.GridView']]");
		AndroidLocators.clickElementusingXPath("//*[@text='" + fromdate + "']");
		// split date
		String[] currentDate = currentDate().split("-");
		// get date
		String toDate = currentDate[2];
		AndroidLocators.clickElementusingResourceId("" + toDateLocator + "");
		CommonUtils
				.waitForElementVisibility("//*[@class='android.view.View' and ./*[@class='android.widget.GridView']]");
		AndroidLocators.clickElementusingXPath("//*[@text='" + toDate + "']");
	}

	public static String currentDate() {
		// get the date
		Date date = new Date();
		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
		// get current date
		String todayDate = DateFor.format(date);
		return todayDate;
	}
}
