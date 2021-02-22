package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.openqa.selenium.By;
import org.apache.commons.lang3.RandomStringUtils;

import Actions.HomepageAction;
import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;
import utils.MediaPermission;

public class Forms_basic {
	private static final List<MobileElement> MobileElements = null;

	// go to form page from home page
	public static void goToFormPage() throws MalformedURLException {
		CommonUtils.homeFabClick();
		HomepageAction.select_dialog_list("Form");
	}

	// if specified form is in homepage click on it else click on home fab '+' and
	// go to forms
	public static void verifyForminHomePage(String form) throws MalformedURLException {
		try {
		CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
						+ form + "\").instance(0))"));
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[@class='android.widget.LinearLayout' and ./*[@text='" + form + "']]"))
				.size() > 0) {
			CommonUtils.getdriver()
					.findElement(
							MobileBy.xpath("//*[@class='android.widget.LinearLayout' and ./*[@text='" + form + "']]"))
					.click();
			CommonUtils.waitForElementVisibility(
					"//*[@resource-id='in.spoors.effortplus:id/toolbar']/android.widget.TextView[@text='" + form
							+ "']");
			CommonUtils.homeFabClick();
		} 
		}catch(Exception e) {
			goToFormPage();
		}
	}

	// click on form save button and if i understood alert display then click on it
	public static void formSaveButton() throws InterruptedException {
		AndroidLocators.resourceId("in.spoors.effortplus:id/saveForm").click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver()
				.findElements(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy
					.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
					.click();
		} else if (CommonUtils.getdriver()
				.findElements(MobileBy.AndroidUIAutomator(
						"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")")).click();
		} else {
			HomepageAction.form_SignIn_SignOut();
		}
		CommonUtils.interruptSyncAndLetmeWork();
		// verify if popup with i understand message is display then click on it
		i_understand_alert();
	}
	
	//i understand alert
	public static void i_understand_alert() throws InterruptedException {
		try {
			if (CommonUtils.getdriver()
					.findElements(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
					.size() > 0) {
				AndroidLocators.resourceId("android:id/button1").click();
				System.out.println("I understand message is displayed");
			}
		} catch (Exception e) {
			System.out.println("I understand message is not displayed");
		}
		CommonUtils.wait(5);
	}

	// click on form save and new
	public static void form_Save_And_New() throws InterruptedException {
		AndroidLocators.resourceId("in.spoors.effortplus:id/saveForm").click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver().findElements(MobileBy
				.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveAndNewButton\")"))
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/formSaveAndNewButton").click();
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/saveForm']");
		}
	}

	// form save as draft
	public static void form_Save_As_Draft() throws InterruptedException {
		AndroidLocators.resourceId("in.spoors.effortplus:id/saveForm").click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver().findElements(MobileBy
				.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveDraftButton\")"))
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/formSaveDraftButton").click();
		} else if (CommonUtils.getdriver().findElementsById("formSaveDraftButton").size() > 0) {
			AndroidLocators.clickElementusingID("formSaveDraftButton");
			AndroidLocators.resourceId("in.spoors.effortplus:id/formSaveDraftButton").click();
		} else {
			AndroidLocators.clickElementusingXPath("//*[@text='SAVE AS DRAFT']");
			AndroidLocators.resourceId("in.spoors.effortplus:id/formSaveDraftButton").click();
		}
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/action_search']");
		verify_Draft_Form_Status();
	}

	// form draft status
	public static String verify_Draft_Form_Status() throws InterruptedException {
		click_menubar_when_visible();
		MobileActionGesture.flingToBegining_Android();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/draftsFormItems']");
		MobileElement saveDraftStatus = CommonUtils.getdriver().findElement(MobileBy
				.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/draftsFormItems\")"));
		String getFormDraftStatus = saveDraftStatus.getText();
		System.out.println("**** Form draft status ****: " + getFormDraftStatus);
		return getFormDraftStatus;
	}

	//click on menu bar when visible
	public static void click_menubar_when_visible() throws InterruptedException {
		if (CommonUtils.getdriver().findElements(By.xpath("//*[@content-desc='Open drawer']")).size() > 0) {
			CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Open drawer']")).click();
			CommonUtils.clickHomeInMenubar();
		} else if (CommonUtils.getdriver().findElements(By.xpath("//*[@contentDescription='Open drawer']"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@contentDescription='Open drawer']")).click();
			CommonUtils.clickHomeInMenubar();
		} else if (CommonUtils.getdriver()
				.findElements(By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']")).size() > 0) {
			CommonUtils.getdriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']"))
					.click();
			CommonUtils.clickHomeInMenubar();
		} else {
			System.out.println("*** Menubar is not displayed ***");
		}
	}
	
	// form discard
	public static void form_Discard() throws InterruptedException {
		AndroidLocators.resourceId("in.spoors.effortplus:id/saveForm").click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver().findElements(MobileBy
				.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formDiscardButton\")"))
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/formDiscardButton").click();
		} else if (CommonUtils.getdriver().findElementsById("formSaveDraftButton").size() > 0) {
			AndroidLocators.clickElementusingID("formSaveDraftButton");
		} else {
			AndroidLocators.clickElementusingXPath("//*[@text='SAVE AS DRAFT']");
		}
		// form delete pop-up
		click_delete();
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Edit']");
	}
	
	//click on form delete
	public static void click_delete() {
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver().findElements(MobileBy.id("alertTitle")).size() > 0) {
			AndroidLocators.xpath("//*[@text='DELETE']").click();
		} else if (CommonUtils.getdriver()
				.findElements(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplusbeta:id/alertTitle\")"))
				.size() > 0) {
			CommonUtils.getdriver()
					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
					.click();
		} else {
			AndroidLocators.xpath("//*[@text='DELETE']").click();
		}
	}

	// if form has section fields then click on Add
	public static void checkRepeatablefields() {
		try {
			if (CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[@class='android.widget.LinearLayout']/android.widget.Button[@text='ADD']"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='ADD']")).click();
			} else {
				System.out.println(" ---- Section fields form not found ---- ");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// click on add if section exist
	public static void verifySectionToClickAdd() throws InterruptedException, MalformedURLException {
		// check if section fields exist by scrolling and then click on it
		try {
			CommonUtils.getdriver().findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()).scrollIntoView(text(\"ADD\"));").click();
		} catch (Exception e) {
			System.out.println(" **** Section with Add button is not found **** ");
		}
	}

	// automating time in 24 hrs format(clicking on hours and AM,PM)
	public static void TimeScriptInForms(int hoursCount, int minsCount)
			throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();

		// retrieving time
		Date date = new Date();
		// time is 24hrs format
		SimpleDateFormat DateFor = new SimpleDateFormat("H:m a");
		String stringDate = DateFor.format(date);
		System.out.println("---- Present time is ---- : " + stringDate);

		// Splitting time
		String[] CurrentTimesplitValue = DateFor.format(date).split(":");

		// retrieving current time into hours and minutes
		String presentHours = CurrentTimesplitValue[0];
		System.out.println(" **** Current Hours ***** : " + presentHours);
		String CurrentMin = CurrentTimesplitValue[1];
		System.out.println(" ===== Current Minutes ===== : " + CurrentMin);

		// splitting AMPM from mins
		String[] splitAMPM = CurrentMin.split(" ");

		// retrieving mins and AM PM
		String getMins = splitAMPM[0];
		String getAmPm = splitAMPM[1];
		System.out.println(" ---- Current Minutes ---- :" + getMins);
		System.out.println(".... Before adding hours AmPm.... :" + getAmPm);

		// adding extra hours to the current hours and splitting into hrs and mins
		date = DateUtils.addHours(date, hoursCount);
		System.out.println(" **** After adding hours time is **** : " + DateFor.format(date));
		// splitting extended hours
		String[] splitValueExtendedHrs = DateFor.format(date).split(":");
		// retrieving extended hours
		String extendedHours = splitValueExtendedHrs[0];
		System.out.println(" ---- Added Hours ---- : " + extendedHours);

		// adding minutes
		date = DateUtils.addMinutes(date, minsCount);
		// modifying minute in date
		date = DateUtils.truncate(date, Calendar.MINUTE);
		// After adding hours splitting extended Minutes
		String[] splitMins = DateFor.format(date).split(":");
		// retrieving minutes
		String addedMins = splitMins[1];
		System.out.println(" **** After adding Mins **** : " + addedMins);

		splitAMPM = addedMins.split(" ");
		String plusMins = splitAMPM[0];
		System.out.println(" ==== Minutes ==== : " + plusMins);
		// retrieving mins and AM PM
		String getAmPm1 = splitAMPM[1];
		System.out.println(" **** After adding hours AmPm **** :" + getAmPm1);

		// get xpath of current hour and pass variable of current,added hours
		MobileElement sourceHour = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@content-desc='" + presentHours + "']"));
		MobileElement destinationHour = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@content-desc='" + extendedHours + "']"));
		// using longpress method moving element from source to destination(i.e current
		// hr to added hr)
		MobileActionGesture.Movetoelement(sourceHour, destinationHour);

		CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/minutes']");

//		//get xpath of current hour and pass variable of current,added hours
//		MobileElement currMins=CommonUtils.getdriver().findElementByXPath("//*[@content-desc='"+CurrentMin+"']");
//		MobileElement pluMins=CommonUtils.getdriver().findElementByXPath("//*[@content-desc='"+plusMins+"']");
//		//using longpress method moving element from source to destination(i.e current hr to added hr)
//		MobileActionGesture.Movetoelement(currMins, pluMins);

		CommonUtils.getdriver().findElementByXPath("//*[@text='OK']").click();
	}

	// automating date
	public static void dateScriptInForms(int addedDate)
			throws MalformedURLException, InterruptedException, ParseException {

		// get the date
		Date date = new Date();
		// date formatter eg-02 Aug 2020
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");
		// EEE-Day of month, MMM-month, dd-date, yyyy-year
		SimpleDateFormat New_Date_Format = new SimpleDateFormat("EEE, MMM dd yyyy");

		// get current date
		String todayDate = DateFor.format(date);

		System.out.println("**** Today date is **** : " + todayDate);
		// add date
		date = DateUtils.addDays(date, addedDate);

		// conversion of date
		String ExtendedDate = DateFor.format(date);
		// printing date
		System.out.println(" ---- Date Format ---- : " + ExtendedDate);
		// splitting date
		String[] splitDate = ExtendedDate.split(" ");
		// get year
		String splitYear = splitDate[2];
		// printing year
		System.out.println("... Selected Year ... : " + splitYear);

		// parse the given date
		Date givenDate = DateFor.parse(ExtendedDate);

		// click on displaying year on calendar
		FormAdvanceSettings.clickCalendarYear();

		// select year
		FormAdvanceSettings.clickElementByText(splitYear);

		// get year from calendar
		String year = AndroidLocators.resourceId("android:id/date_picker_header_year").getText();
		System.out.println("**** Display year is **** : " + year);

		// get date from calendar
		String displayDate = AndroidLocators.xpath("//*[@class='android.widget.LinearLayout' and ./*[@text='"
				+ splitYear + "']]/android.widget.TextView[2]").getText();
		System.out.println("**** Displaying calendar date **** : " + displayDate);

		// concantenating year and date using string builder class
		StringBuilder sb = new StringBuilder(displayDate);
		StringBuilder date_Year_Append = sb.append(" " + year);
		System.out.println("==== After appending displaying date and year from calendar ==== : " + date_Year_Append);

		// converting to string
		String date_Year_Append_to_String = date_Year_Append.toString();
		
		System.out.println(".... After converting the date to string .... : " + date_Year_Append_to_String);
    
		Date parse_display_date = null;
		
		try {
			// parsing display date
			parse_display_date = New_Date_Format.parse(date_Year_Append_to_String);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		System.out.println(".... After parsing the display date .... : " + parse_display_date);
		
		// formating pared date to calendar format
		String formatDisplayDate = DateFor.format(parse_display_date);
		System.out.println("---- Forrmat the new date ---- : " + formatDisplayDate);

		// get xpath of Date and pass variable of added date and verify date is
		// available
		// or not
		// get list of dates
		FormAdvanceSettings.selectDate(ExtendedDate);
		// given date is after display date then move forward
		if (givenDate.after(parse_display_date)) {
			while (!(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']"))
					.size() > 0)) {
				FormAdvanceSettings.goRight();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']"))
						.size() > 0) {
					break;
				}
			}
			FormAdvanceSettings.selectDate(ExtendedDate);
		} // given date is before display date then move backword
		else if (givenDate.before(parse_display_date)) {
			while (!(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']"))
					.size() > 0)) {
				FormAdvanceSettings.goLeft();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']"))
						.size() > 0) {
					break;
				}
			}
			FormAdvanceSettings.selectDate(ExtendedDate);
		}
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
		CommonUtils.wait(1);
	}

	// click on given date
	public static void getCalendarDates(String myGivendate) throws InterruptedException, ParseException {
		// date format eg-02 october 2020
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");
		
		// EEE-Day of month, MMM-month, dd-date, yyyy-year
		SimpleDateFormat New_Date_Format = new SimpleDateFormat("EEE, MMM dd yyyy");
		
		// split the date
		String[] splitDate = myGivendate.split(" ");
		
		// get year from date
		String splitYear = splitDate[2];
		
		// printing year
		System.out.println("... Selected Year ... : " + splitYear);

		// parse the given date
		Date givenDate = DateFor.parse(myGivendate);
		
		// printing parsed given date
		System.out.println(".... After parsing the given date .... : " + givenDate);

		// click on displaying year on calendar
		try {
			FormAdvanceSettings.clickCalendarYear();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// select user input year
		try {
			FormAdvanceSettings.clickElementByText(splitYear);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get year from calendar
		String year = AndroidLocators.resourceId("android:id/date_picker_header_year").getText();
		System.out.println("**** Display year is **** : " + year);

		// get date from calendar
		String displayDate = AndroidLocators.xpath("//*[@class='android.widget.LinearLayout' and ./*[@text='"
				+ splitYear + "']]/android.widget.TextView[2]").getText();
		System.out.println("**** Displaying calendar date **** : " + displayDate);

		// concantenating year and date using string builder class
		StringBuilder sb = new StringBuilder(displayDate);
		StringBuilder date_Year_Append = sb.append(" " + year);
		System.out.println("==== After appending displaying date and year from calendar ==== : " + date_Year_Append);

		// converting date to string
		String date_Year_Append_to_String = date_Year_Append.toString();

		// parsing the display date
		Date parse_display_date = New_Date_Format.parse(date_Year_Append_to_String);
		System.out.println(".... After parsing the display date .... : " + parse_display_date);

		// formating parsed date to calendar format
		String formatDisplayDate = DateFor.format(parse_display_date);
		System.out.println("---- Forrmat the new date ---- : " + formatDisplayDate);

		FormAdvanceSettings.selectDate(myGivendate);
		// given date is after display date then move forward
		if (givenDate.after(parse_display_date)) {
			while (!(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + myGivendate + "']"))
					.size() > 0)) {
				FormAdvanceSettings.goRight();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + myGivendate + "']"))
						.size() > 0) {
					break;
				}
			}
			FormAdvanceSettings.selectDate(myGivendate);
		} // given date is before display date then move backword
		else if (givenDate.before(parse_display_date)) {
			while (!(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + myGivendate + "']"))
					.size() > 0)) {
				FormAdvanceSettings.goLeft();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + myGivendate + "']"))
						.size() > 0) {
					break;
				}
			}
			FormAdvanceSettings.selectDate(myGivendate);
		}
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
		CommonUtils.wait(1);
	}

	// form fill by verifying pages
	public static void verifyFormPagesAndFill() throws MalformedURLException, InterruptedException, ParseException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));
		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println(" ----- pagination exists ----- ");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				System.out.println(".... Clicking on page .... : " + pagination.get(i));
				pagination.get(i).click();
				verifySectionToClickAdd();
				fill_Form_With_Pagination(i);
			}
		} else {
			System.out.println(" **** pagination not exists **** ");
			verifySectionToClickAdd();
			formfill();
		}
//		Forms.formSaveButton();
	}

	// form fill with pagination
	public static void fill_Form_With_Pagination(int i)
			throws MalformedURLException, InterruptedException, ParseException {
		int j = i + 1;
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='PAGE " + j
				+ "']/parent::*/following::*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		// get last element text
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='PAGE " + j
				+ "']/parent::*/following::*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='PAGE " + j
				+ "']/parent::*/following::*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println(" ==== Before swiping fields count is ==== : " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='PAGE " + j
					+ "']/parent::*/following::*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println(".... After swiping fields count .... : " + countOfFields);
			for (int k = 0; k < countOfFields; k++) {
				System.out.println("***** Print form fields elements text ***** : "
						+ formFields1.get(countOfFields - (k + 1)).getText());
				System.out.println("====== Form fields text ====== : " + formFields1.get(k).getText());
				if (formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					System.out
							.println("----- Form fields text inside elements ----- : " + formFields1.get(k).getText());
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		//insert form data
		form_data_insertion(formFields1, countOfFields);
	}
	
	// form fill with out pagination
	public static void formfill() throws InterruptedException, MalformedURLException, ParseException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		// get last element text
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("===== Get the last element text ===== : " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list present in first screen
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("----- Before swiping fields count is ----- : " + countOfFields);
		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println("***** After swiping fields count ***** : " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				if (formFields1.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		//insert form data
        form_data_insertion(formFields1, countOfFields);
	}
	
	// form data insertion
	public static void form_data_insertion(List<MobileElement> formFields1, int countOfFields)
			throws MalformedURLException, InterruptedException, ParseException {
		boolean isDate = false, isCustomerType = false, isText = false, isURL = false, isEmail = false,
				isTerritory = false, isCountry = false, isEmployee = false, isDateTime = false, isTime = false,
				isPickList = false, isMultiPickList = false, isMultiSelectDropdown = false, isLocation = false,
				isPhone = false, isCurrency = false, isNumber = false, isDropdown = false, isYesNo = false,
				isCustomer = false, isMultiPickCustomer = false, isForm = false, isCustomEntity = false,
				isSignature = false, isRichTextFormat = false;

		// iterate and fill the form
		for (int k = 0; k < countOfFields; k++) {
			String OriginalfieldsText = formFields1.get(k).getText();
			String fieldsText = formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("==== Before removeing special character ==== : " + OriginalfieldsText
					+ "\n----- after removing regexp ----- : " + fieldsText);

			switch (fieldsText) {
			case "Rich Text Format":
			case "G-Rich Text Format":
			case "S-Rich Text Format":
				if (!isRichTextFormat) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// rich text format
						rich_text_format(fieldsText);
					}
					isRichTextFormat = true;
				}
				break;
			case "Date":
			case "G-Date":
			case "S-Date":
				if (!isDate) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// picking date
						formDate(fieldsText);
					}
					isDate = true;
				}
				break;
			case "Customer Type":
			case "G-Customer Type":
			case "S-Customer Type:":
				if (!isCustomerType) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// selecting customer type
						customerType(fieldsText);
					}
					isCustomerType = true;
				}
				break;
			case "Text":
			case "G-Text":
			case "S-Text":
				if (!isText) {
				MobileActionGesture.scrollUsingText(fieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]"))
						.size() > 0) {
					// inputting text
					text(fieldsText);
				}
					isText = true;
				}
				break;
			case "URL":
			case "G-URL":
			case "S-URL":
				if (!isURL) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						url(fieldsText);
					}
					isURL = true;
				}
				break;
			case "Email":
			case "G-Email":
			case "S-Email":
				if (!isEmail) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						email(fieldsText);
					}
					isEmail = true;
				}
				break;
			case "Territory":
			case "G-Territory":
			case "S-Territory":
				if (!isTerritory) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						selecting_Territory(fieldsText);
					}
					isTerritory = true;
				}
				break;
			case "Country":
			case "G-Country":
			case "S-Country":
				if (!isCountry) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						selecting_Country(fieldsText);
					}
					isCountry = true;
				}
				break;
			case "Employee":
			case "G-Employee":
			case "S-Employee":
				if (!isEmployee) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						pickEmployee(fieldsText);
					}
					isEmployee = true;
				}
				break;
			case "DateTime":
			case "G-DateTime":
			case "S-DateTime":
				if (!isDateTime) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						pickDateTime(fieldsText);
					}
					isDateTime = true;
				}
				break;
			case "Time":
			case "G-Time":
			case "S-Time":
				if (!isTime) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						timePicker(fieldsText);
					}
					isTime = true;
				}
				break;
			case "Pick List":
			case "G-Pick List":
			case "S-Pick List":
				if (!isPickList) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						pickList_Picker(fieldsText);
					}
					isPickList = true;
				}
				break;
			case "Multi Pick List":
			case "G-Multi Pick List":
			case "S-Multi Pick List":
				if (!isMultiPickList) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						picking_Multi_Pick_List(fieldsText);
					}
					isMultiPickList = true;
				}
				break;
			case "Multi Select Dropdown":
			case "G-Multi Select Dropdown":
			case "S-Multi Select Dropdown":
				if (!isMultiSelectDropdown) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						picking_Multi_Select_Dropdown(fieldsText);
					}
					isMultiSelectDropdown = true;
				}
				break;
			case "Location":
			case "G-Location":
			case "S-Location":
				if (!isLocation) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						location_picking(fieldsText);
					}
					isLocation = true;
				}
				break;
			case "Phone":
			case "Phone Number":
			case "G-Phone":
			case "S-Phone":
			case "G-Phone Number":
			case "S-Phone Number":
				if (!isPhone) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						phoneNumber(fieldsText);
					}
					isPhone = true;
				}
				break;
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				if (!isCurrency) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						currency_And_Number_Input(fieldsText);
					}
					isCurrency = true;
				}
				break;
			case "Number":
			case "G-Number":
			case "S-Number":
				if (!isNumber) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						currency_And_Number_Input(fieldsText);
					}
					isNumber = true;
				}
				break;
			case "Dropdown":
			case "G-Dropdown":
			case "S-Dropdown":
				if (!isDropdown) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						selecting_Dropdown(fieldsText);
					}
					isDropdown = true;
				}
				break;
			case "YesNo":
			case "YesOrNo":
			case "G-YesNo":
			case "S-YesNo":
			case "G-YesOrNo":
			case "S-YesOrNo":
				if (!isYesNo) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						selecting_YesNo(fieldsText);
					}
					isYesNo = true;
				}
				break;
			case "Customer":
			case "G-Customer":
			case "S-Customer":
				if (!isCustomer) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						picking_Customer(fieldsText);
					}
					isCustomer = true;
				}
				break;
			case "Custom Entity":
			case "G-Custom Entity":
			case "S-Custom Entity":
				if (!isCustomEntity) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						picking_CustomEntity(fieldsText);
					}
					isCustomEntity = true;
				}
				break;
			case "Form":
			case "G-Form":
			case "S-Form":
				if (!isForm) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						picking_Form(fieldsText);
					}
					isForm = true;
				}
				break;
			case "Signature":
			case "G-Signature":
			case "S-Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						capturing_Signature(fieldsText);
						// CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.CAMERA));
					}
					isSignature = true;
				}
				break;
			case "Multi Pick Customer":
			case "G-Multi Pick Customer":
			case "S-Multi Pick Customer":
				if (!isMultiPickCustomer) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						pick_multiPickCustomer(fieldsText);
					}
					isMultiPickCustomer = true;
				}
				break;
			}
		}
	}

	// inputting rich text format
	public static void rich_text_format(String fieldsText) {
		if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText")).size() > 0) {
			AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText");
		}
	}
	
	// picking formdate
	public static void formDate(String fieldsText) throws MalformedURLException, InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]")).size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]")).click();
			CommonUtils.alertContentXpath();
			try {
				Forms_basic.dateScriptInForms(1);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println(".... Date is already picked .... ");
		}
	}

	// selecting customer type
	public static void customerType(String fieldsText) throws MalformedURLException {
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
				.size() > 0) {
			MobileElement cusType = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
			MobileActionGesture.singleLongPress(cusType);
			if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
			}
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("---- Customer type is already selected ---- ");
		}
	}

	// inputting text
	public static void text(String fieldsText) {
		AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText");
	}

	// inputting url
	public static void url(String fieldsText) {
		AndroidLocators.sendUrlInputusing_XPath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText");
	}

	// inputting email
	public static void email(String fieldsText) {
		AndroidLocators.sendEmailInputusing_XPath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText");
	}

	// selecting territory
	public static void selecting_Territory(String fieldsText) throws MalformedURLException {
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
				.size() > 0) {
			MobileElement terriory = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
					+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
			MobileActionGesture.singleLongPress(terriory);
			if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
			}
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("==== Territory is already selected ==== ");
		}
	}

	// selecting country
	public static void selecting_Country(String fieldsText) throws MalformedURLException {
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
				.size() > 0) {
			MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
					+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
			MobileActionGesture.singleLongPress(country);
			MobileActionGesture.scrollTospecifiedElement("Australia");
		} else {
			System.out.println("***** Country is already selected ***** ");
		}
		CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
	}

	// pick employee
	public static void pickEmployee(String fieldsText) throws InterruptedException {
		CommonUtils.getdriver()
				.findElement(MobileBy
						.xpath("//*[contains(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button"))
				.click();
		if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
				.size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
					.click();
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
		} else if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
		}
		CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
	}

	// pick dateTime
	public static void pickDateTime(String fieldsText) throws MalformedURLException, InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK A DATE')]"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK A DATE')]"))
					.click();
			CommonUtils.alertContentXpath();
			try {
				Forms_basic.dateScriptInForms(1);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
			if (CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
					.size() > 0) {
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@text='" + fieldsText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
						.click();
				CommonUtils.alertContentXpath();
				Forms_basic.TimeScriptInForms(1, 5);
				CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
			}
		} else {
			System.out.println("----- DateTime is picked ----- ");
		}
	}

	// pick time
	public static void timePicker(String fieldsText) throws MalformedURLException, InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.Button[@text='PICK A TIME']")).size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.Button[@text='PICK A TIME']")).click();
			Forms_basic.TimeScriptInForms(1, 1);
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("**** Time already picked **** ");
		}
	}

	// picklist picker
	public static void pickList_Picker(String fieldsText) throws InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
					.click();
			CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
			if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).size() > 1) {
				CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(1).click();
			} else if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).size() > 0) {
				CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
			}
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		}
	}

	// picking multi_pick_list
	public static void picking_Multi_Pick_List(String fieldsText) throws MalformedURLException, InterruptedException {
		MobileElement multipicklist = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
				+ fieldsText + "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
		MobileActionGesture.tapByElement(multipicklist);
		CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
		if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).size() > 1) {
			List<MobileElement> pickMultiPickList = CommonUtils.getdriver()
					.findElements(MobileBy.className("android.widget.CheckBox"));
			if (pickMultiPickList.get(0).isDisplayed()) {
				MobileActionGesture.singleLongPress(pickMultiPickList.get(0));
			}
			if (pickMultiPickList.get(0).isDisplayed()) {
				MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
			}
		} else if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0).click();
		}
		if (CommonUtils.getdriver().findElement(By.xpath("//*[@text='OK']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@text='OK']");
		} else if (CommonUtils.getdriver().findElement(By.id("useTheseValuesButton")).isDisplayed()) {
			AndroidLocators.clickElementusingID("useTheseValuesButton");
		} else {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/useTheseValuesButton");
		}
		CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
	}

	// picking multiselect dropdown
	public static void picking_Multi_Select_Dropdown(String fieldsText)
			throws MalformedURLException, InterruptedException {
		MobileElement multiSelectDropdown = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
				+ fieldsText + "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
		MobileActionGesture.tapByElement(multiSelectDropdown);
		CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
		if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 1) {
			List<MobileElement> pickValues = CommonUtils.getdriver()
					.findElements(MobileBy.className("android.widget.CheckedTextView"));
			if (pickValues.get(0).isDisplayed()) {
				MobileActionGesture.singleLongPress(pickValues.get(0));
			}
			if (pickValues.get(1).isDisplayed()) {
				MobileActionGesture.singleLongPress(pickValues.get(1));
			}
		} else if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
				.size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(0).click();
		}
		if (CommonUtils.getdriver().findElement(By.xpath("//*[@text='OK']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@text='OK']");
		} else if (CommonUtils.getdriver().findElement(By.id("button1")).isDisplayed()) {
			AndroidLocators.clickElementusingID("button1");
		} else {
			AndroidLocators.clickElementusingResourceId("android:id/button1");
		}
		CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
	}

	// Location picking
	public static void location_picking(String fieldsText) throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")).click();
		CommonUtils.wait(10);
		CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
	}

	// inputting phoneNumber
	public static void phoneNumber(String fieldsText) {
		AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText");
	}

	// inputting currency
	public static void currency_And_Number_Input(String fieldsText) throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText")).clear();
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("// *[@text='" + fieldsText
						+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton"))
				.size() > 0) {
			CommonUtils.getdriver()
					.findElement(MobileBy.xpath("// *[contains(@text,'" + fieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton"))
					.click();
			CommonUtils.waitForElementVisibility("//*[@content-desc='Done']");
			MobileElement currencyClick = CommonUtils.getdriver().findElement(MobileBy.id("incrementButton"));
			MobileActionGesture.multiTouchByElement(currencyClick);
			CommonUtils.getdriver().findElement(MobileBy.id("done")).click();
			Thread.sleep(100);
		} else {
			AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText");
		}
	}

	// selecting dropdown
	public static void selecting_Dropdown(String fieldsText) throws MalformedURLException {
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
				.size() > 0) {
			MobileElement dropdown = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
			MobileActionGesture.singleLongPress(dropdown);
			CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1).click();
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("**** Dropdown is already selected **** ");
		}
	}

	// selecting YesNo
	public static void selecting_YesNo(String fieldsText) throws MalformedURLException {
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
				.size() > 0) {
			MobileElement yesno = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
			MobileActionGesture.singleLongPress(yesno);
			CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1).click();
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("==== YesNo is already selected ==== ");
		}
	}

	// picking customer
	public static void picking_Customer(String fieldsText) throws MalformedURLException, InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/*[contains(@text,'PICK A CUSTOMER')]")).size() > 0) {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/*[contains(@text,'PICK A CUSTOMER')]")).isEnabled()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/*[contains(@text,'PICK A CUSTOMER')]")).click();
				CommonUtils.waitForElementVisibility("//*[@text='Customers']");
				if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
				} else {
					CustomerPageActions.customerFab();
					CustomerPageActions.createCustomer();
					CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[@text='" + CustomerPageActions.randomstringCusName + "']"))
							.click();
				}
				CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
				System.out.println("Now customer is picked");
			} else {
				System.out.println(".... Customer is already selected .... !!");
			}
			Thread.sleep(300);
		}
	}

	// picking customEntity
	public static void picking_CustomEntity(String fieldsText)
			throws MalformedURLException, InterruptedException, ParseException {
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
						+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOM ENTITY')]"))
				.size() > 0) {
			MobileElement customEntity = CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOM ENTITY')]"));
			MobileActionGesture.tapByElement(customEntity);
			CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
			if (CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).size() > 0) {
				CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
			} else if (CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).size() > 0) {
				CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(0).click();
			} else {
				// write entity item creation method
				Forms_basic.createEntity();
				AndroidLocators.clickElementusingXPath("//*[@content-desc='Save']");
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
			}
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("==== Custom entity is not present ==== ");
		}
	}

	// picking form
	public static void picking_Form(String fieldsText) throws MalformedURLException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button"))
				.size() > 0) {
			MobileElement form = CommonUtils.getdriver().findElement(MobileBy.xpath(
					"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button"));
			MobileActionGesture.tapByElement(form);
			CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
			try {
				if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
				} else {
					CommonUtils.getdriver().findElementById("load_more_button").click();
					CommonUtils
							.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
					if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
					} else {
						CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
						CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
						verifyFormPagesAndFill();
						CommonUtils.waitForElementVisibility(
								"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
						}
					}
				}
				CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("**** Form is already picked **** ");
		}
	}

	// capturing signature
	public static void capturing_Signature(String fieldsText) throws MalformedURLException, InterruptedException {
		// deleting the existing signature if present
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + fieldsText
				+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'DELETE')]"))
				.size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@text='" + fieldsText
					+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'DELETE')]");
			CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button");
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")).size() > 0) {
			MobileElement signature = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText + "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
			MobileActionGesture.tapByElement(signature);
			MediaPermission.mediaPermission();
			CommonUtils.waitForElementVisibility("//*[@text='Signature']");
			MobileElement signatureCapture = CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
			MobileActionGesture.singleLongPress(signatureCapture);
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
		} else {
			System.out.println("---- Signature is not present ---- ");
		}
	}

	// picking multiPickCustomer
	public static void pick_multiPickCustomer(String fieldsText) throws MalformedURLException, InterruptedException {
		MobileElement multiPickCustomer = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
				+ fieldsText + "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
		MobileActionGesture.tapByElement(multiPickCustomer);
		CommonUtils.waitForElementVisibility("//*[@text='Customers']");
		if (CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).size() > 1) {
			List<MobileElement> selectCheckbox = CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck"));
			if (selectCheckbox.get(0).isDisplayed()) {
				MobileActionGesture.singleLongPress(selectCheckbox.get(0));
			}
			if (selectCheckbox.get(1).isDisplayed()) {
				MobileActionGesture.singleLongPress(selectCheckbox.get(1));
			}
		} else if (CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).size() > 0) {
			CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).get(0).click();
		} else {
			CustomerPageActions.customerFab();
			CustomerPageActions.createCustomer();
			CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + CustomerPageActions.randomstringCusName
					+ "']/parent::*/parent::*/parent::*/parent::*/android.widget.CheckBox")).click();
		}
		if (CommonUtils.getdriver().findElement(By.id("selectCustomers")).isDisplayed()) {
			AndroidLocators.clickElementusingID("selectCustomers");
		} else if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Select']"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
		} else {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/selectCustomers");
		}
		CommonUtils.waitForElementVisibility("//*[contains(@text,'" + fieldsText + "')]");
	}

	// create entity item
	public static void createEntity() throws MalformedURLException, InterruptedException, ParseException {
//		MobileActionGesture.scrollTospecifiedElement("Abc");
		CommonUtils.getdriver().findElement(MobileBy.id("customEntitiesFab")).click();
		CommonUtils.waitForElementVisibility("//*[contains(@text,'Create')]");
		// insert entity data
		fillEntity();
	}

	
	// fill entity
	public static void fillEntity() throws MalformedURLException, InterruptedException, ParseException {
		// label list fields
		List<MobileElement> labelViews = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// retrieve count of entity fields
		int countOfAllFields = labelViews.size();
		System.out.println("----- count Of All entity Fields ----- : " + countOfAllFields);

		// clear entity fields
		labelViews.clear();

		// initializing string
		String lastEntityTextElement = null;

		// scroll to bottom and add list fields
		MobileActionGesture.flingVerticalToBottom_Android();

		// add list fields
		labelViews.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// get last element in this list and store to "lastEntityTextElement"
		lastEntityTextElement = labelViews.get(labelViews.size() - 1).getText();
		System.out.println("**** Last Entity element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		labelViews.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		labelViews.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// retrieve count of fields displaying in first screen
		countOfAllFields = labelViews.size();
		System.out.println(" ---- Before swiping entity fields inputText count ---- : " + countOfAllFields);

		// scroll and add list fields(labelView) till last text element find
		while (!labelViews.isEmpty() && labelViews != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// retrieving count of fields displaying until last element display
			countOfAllFields = labelViews.size();
			System.out.println(".... After swiping entity fields count .... : " + countOfAllFields);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ labelViews.get(countOfAllFields - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + labelViews.get(i).getText());

				// if list matches with last element the loop will break
				if (labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + labelViews.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		// iterate the list fields
		for (int i = 0; i < countOfAllFields; i++) {
			String allFieldsTextOriginal = labelViews.get(i).getText();
			String allFieldsText = labelViews.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("==== Before removing specia character ==== : " + allFieldsTextOriginal
					+ "\n**** After removing the special chararcter **** : " + allFieldsText);

			switch (allFieldsText) {
			case "Location":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.id("pick_location_button")).click();
				CommonUtils.wait(10);
				CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
				CommonUtils.wait(5);
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'"
						+ allFieldsText + "')]/parent::*/android.widget.Button")).isEnabled()) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'"
							+ allFieldsText + "')]/parent::*/android.widget.Button")).click();
					CommonUtils.waitForElementVisibility("//*[@text='Customers']");
					if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
					} else {
						CustomerPageActions.customerFab();
						CustomerPageActions.createCustomer();
						CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
						CommonUtils.getdriver()
								.findElement(
										MobileBy.xpath("//*[@text='" + CustomerPageActions.randomstringCusName + "']"))
								.click();
					}
					System.out.println("Now customer is picked");
				} else {
					System.out.println("Customer is already selected!!");
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Employee":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'"
						+ allFieldsText + "')]/parent::*/android.widget.Button")).click();
				if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
						.size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
							.click();
					AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
				} else if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'"
						+ allFieldsText + "')]/parent::*/android.widget.Button")).click();
				TimeScriptInForms(1, 1);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "DateTime":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils
						.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'"
								+ allFieldsText + "')]/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
						.click();
				CommonUtils.alertContentXpath();
				try {
					Forms_basic.dateScriptInForms(1);
				} catch (MalformedURLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'"
						+ allFieldsText + "')]/parent::*/android.widget.Button")).click();
				CommonUtils.alertContentXpath();
				Forms_basic.dateScriptInForms(1);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement customerType = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(customerType);
				if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
						.size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
							.click();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Form":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement form = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Button"));
				MobileActionGesture.tapByElement(form);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				try {
					if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
					} else {
						CommonUtils.getdriver().findElementById("load_more_button").click();
						CommonUtils.waitForElementVisibility(
								"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
						} else {
							CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
							CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
							verifyFormPagesAndFill();
							CommonUtils.waitForElementVisibility(
									"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Signature":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement signature = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"));
				MobileActionGesture.tapByElement(signature);
				MediaPermission.mediaPermission();
				CommonUtils.waitForElementVisibility("//*[@text='CAPTURE']");
				MobileElement signatureCapture = CommonUtils.getdriver().findElement(MobileBy.id("saveButton"));
				MobileActionGesture.singleLongPress(signatureCapture);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Country":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement country = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(country);
				MobileActionGesture.scrollTospecifiedElement("India");
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "YesNo":
			case "YesOrNo":
			case "Yes/No":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement yesno = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ ")]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(yesno);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement territory = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(territory);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement dropdown = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(dropdown);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Multi Select Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multidropdown = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and contains(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Button"));
				MobileActionGesture.tapByElement(multidropdown);
				CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
				List<MobileElement> pickValues = CommonUtils.getdriver()
						.findElements(MobileBy.className("android.widget.CheckedTextView"));
				if (pickValues.get(0).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickValues.get(0));
				}
				if (pickValues.get(1).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickValues.get(1));
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@id='label_for_view' and starts-with(@text,'"
						+ allFieldsText + "')]/parent::*/android.widget.Button")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
					CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
				} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
					CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Multi Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multipicklist = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and starts-with(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Button"));
				MobileActionGesture.tapByElement(multipicklist);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				List<MobileElement> pickMultiPickList = CommonUtils.getdriver()
						.findElements(MobileBy.className("android.widget.CheckBox"));
				if (pickMultiPickList.get(0).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickMultiPickList.get(0));
				}
				if (pickMultiPickList.get(0).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Multi Pick Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multiPickCustomer = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@id='label_for_view' and starts-with(@text,'" + allFieldsText
								+ "')]/parent::*/android.widget.Button"));
				MobileActionGesture.tapByElement(multiPickCustomer);
				CommonUtils.waitForElementVisibility("//*[@text='Customers']");
				if (CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).size() > 0) {
					List<MobileElement> selectCheckbox = CommonUtils.getdriver()
							.findElements(MobileBy.id("pickCustomerCheck"));
					if (selectCheckbox.get(0).isDisplayed()) {
						MobileActionGesture.singleLongPress(selectCheckbox.get(0));
					}
					if (selectCheckbox.get(1).isDisplayed()) {
						MobileActionGesture.singleLongPress(selectCheckbox.get(1));
					}
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Select']")).click();
				} else {
					CustomerPageActions.customerFab();
					CustomerPageActions.createCustomer();
					CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[@text='" + CustomerPageActions.randomstringCusName
									+ "']/parent::*/parent::*/parent::*/parent::*/android.widget.CheckBox"))
							.click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Select']")).click();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Entity Name":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]"))
						.size() > 0) {
					AndroidLocators.sendInputusing_XPath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendInputusing_XPath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Text":
				if(CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]")).size()>0) {
					AndroidLocators.sendInputusing_XPath("//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Currency":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]"))
						.size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Number":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]"))
						.size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Phone":
			case "Phone Number":
			case "PhoneOptional":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and starts-with(@text,'" + allFieldsText + "')]"))
						.size() > 0) {
					AndroidLocators.sendPhoneNumberInputUsing_xpath(
							"//*[@class='android.widget.EditText' and starts-with(@text,'" + allFieldsText + "')]");

				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendPhoneNumberInputUsing_xpath(
							"//*[@class='android.widget.EditText' and starts-with(@text,'" + allFieldsText + "')]");
				}
				break;
			case "URL":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]"))
						.size() > 0) {
					AndroidLocators.sendUrlInputusing_XPath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendUrlInputusing_XPath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Email":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]"))
						.size() > 0) {
					AndroidLocators.sendEmailInputusing_XPath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendEmailInputusing_XPath(
							"//*[@class='android.widget.EditText' and contains(@text,'" + allFieldsText + "')]");
				}
			}
		}
	}

	// form modification
	public static void modify_form() throws MalformedURLException, InterruptedException, ParseException {
		if (CommonUtils.getdriver().findElements(By.id("editForm")).size() > 0) {
			AndroidLocators.clickElementusingID("editForm");
		} else if (CommonUtils.getdriver().findElementsByXPath("//*[@content-desc='Edit']").size() > 0) {
			CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Edit']")).click();
		} else {
			System.out.println("=== edit symbol is not displayed ===");
		}
		CommonUtils.interruptSyncAndLetmeWork();
		//modify form
		form_modification();
	}

	// modify the form
	public static void form_modification() throws MalformedURLException, InterruptedException, ParseException {
		// get pages
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));

		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println(" ----- pagination exists ----- ");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				System.out.println(".... Clicking on page .... : " + pagination.get(i));
				// click on pages
				pagination.get(i).click();

				// click pages
				verifySectionToClickAdd();

				// modify form in pages
				modify_form_in_pages(i);
			}
		} else {
			System.out.println(" **** pagination not exists **** ");
			// check section exist or not
			verifySectionToClickAdd();

			// modify without pages form
			modify_form_without_pagination();
		}
	}

	// form modification in pages
	public static void modify_form_in_pages(int i) throws MalformedURLException, InterruptedException, ParseException {
		int j = i + 1;
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='PAGE " + j
				+ "']/parent::*/parent::android.widget.LinearLayout/following::android.widget.LinearLayout//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		//retrieve count of form fields
		int countOfFields = formFields1.size();
		formFields1.clear();

		// get last element text
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='PAGE " + j
				+ "']/parent::*/parent::android.widget.LinearLayout/following::android.widget.LinearLayout//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text in modify form: " + lastTxtElement);
		
		//removing elements from list
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='PAGE " + j
				+ "']/parent::*/parent::android.widget.LinearLayout/following::android.widget.LinearLayout//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println(" ==== Before swiping fields count is ==== : " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='Page " + j
					+ "']/parent::*/parent::android.widget.LinearLayout/following::android.widget.LinearLayout//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			
			//retrieve count of formfields
			countOfFields = formFields1.size();
			System.out.println(".... After swiping fields count .... : " + countOfFields);
			
			// if work last element matches with newList then break the for loop
			for (int k = 0; k < countOfFields; k++) {
				
				// printing elements from last to first
				System.out.println("***** Print form fields elements text ***** : "
						+ formFields1.get(countOfFields - (k + 1)).getText());
				
				// printing the elements in the list
				System.out.println("====== Form fields text ====== : " + formFields1.get(k).getText());
				
				// if list matches with last element the loop will break
				if (formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					System.out
							.println("----- Form fields text inside elements ----- : " + formFields1.get(k).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		
		//insert data
		modify_form_data(formFields1, countOfFields);
	}
	
	//form modify without pages
	public static void modify_form_without_pagination()
			throws MalformedURLException, InterruptedException, ParseException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		// get last element text
		String form_modify_lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		
		//add elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		
		//retrieving last element from list
		form_modify_lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("===== Get the last element text in form modification ===== : " + form_modify_lastTxtElement);
		
		//clear elements in list
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list present in first screen
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		
		//retrieving count of fields displaying in first screen
		countOfFields = formFields1.size();
		System.out.println("----- Form modification before swiping fields count is ----- : " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty() && formFields1 != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			//add elements to list
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			
			//retrieving count of fields
			countOfFields = formFields1.size();
			System.out.println("***** In form modification after swiping fields count ***** : " + countOfFields);
			
			// if last element matches with List then break the for loop
			for (int j = 0; j < countOfFields; j++) {
				
				// printing elements from last to first
				System.out.println("***** Print form fields elements text ***** : "
						+ formFields1.get(countOfFields - (j + 1)).getText());
				
				// printing the elements in the list
				System.out.println("====== Form modification fields text ====== : " + formFields1.get(j).getText());
				
				// if list matches with last element the loop will break
				if (formFields1.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(form_modify_lastTxtElement)) {
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		}
		//scroll to top
		MobileActionGesture.flingToBegining_Android();
		//modifying form data
		modify_form_data(formFields1, countOfFields);
	}
	
	//modify the form
	public static void modify_form_data(List<MobileElement> formFields1, int countOfFields)
			throws InterruptedException, MalformedURLException, ParseException {
		boolean isDate = false, isCustomerType = false, isText = false, isURL = false, isEmail = false,
				isTerritory = false, isCountry = false, isEmployee = false, isDateTime = false, isTime = false,
				isPickList = false, isMultiPickList = false, isMultiSelectDropdown = false, isLocation = false,
				isPhone = false, isCurrency = false, isNumber = false, isDropdown = false, isYesNo = false,
				isCustomer = false, isMultiPickCustomer = false, isForm = false, isCustomEntity = false,
				isSignature = false, isRichTextFormat = false;
		
		// iterate and fill the form
		for (int k = 0; k < countOfFields; k++) {
			String OriginalfieldsText = formFields1.get(k).getText();
			String fieldsText = formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("==== Before removing special character in form form modification ==== : " + OriginalfieldsText
					+ "\n----- after removing regexp in form modification ----- : " + fieldsText);

			switch (fieldsText) {
			case "Rich Text Format":
			case "G-Rich Text Format":
			case "S-Rich Text Format":
				if (!isRichTextFormat) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// rich text format
						rich_text_format(fieldsText);
					}
					isRichTextFormat = true;
				}
			case "Date":
			case "G-Date":
			case "S-Date":
				if (!isDate) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// picking date
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button")).click();
							CommonUtils.alertContentXpath();
							Forms_basic.dateScriptInForms(1);
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
						} else {
							System.out.println(".... Date is already picked .... ");
						}
					}
					isDate = true;
				}
				break;
			case "Customer Type":
			case "G-Customer Type":
			case "S-Customer Type:":
				if (!isCustomerType) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// selecting customer type
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"))
								.size() > 0) {
							MobileElement cusType = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
									+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"));
							MobileActionGesture.singleLongPress(cusType);
							if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
										.click();
							}
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
						} else {
							System.out.println("---- Customer type is already selected ---- ");
						}
					}
					isCustomerType = true;
				}
				break;
			case "Text":
			case "G-Text":
			case "S-Text":
				if (!isText) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// inputting text
						text(fieldsText);
					}
					isText = true;
				}
				break;
			case "URL":
			case "G-URL":
			case "S-URL":
				if (!isURL) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// inputting url
						url(fieldsText);
					}
					isURL = true;
				}
				break;
			case "Email":
			case "G-Email":
			case "S-Email":
				if (!isEmail) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// inputting email
						email(fieldsText);
					}
					isEmail = true;
				}
				break;
			case "Territory":
			case "G-Territory":
			case "S-Territory":
				if (!isTerritory) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// selectting territory
						if (CommonUtils.getdriver().findElements(MobileBy.xpath(
								"//*[contains(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"))
								.size() > 0) {
							MobileElement terriory = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"));
							MobileActionGesture.singleLongPress(terriory);
							if (CommonUtils.getdriver()
									.findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
								CommonUtils.getdriver()
										.findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
										.click();
							}
						} else {
							System.out.println("==== Territory is already selected ==== ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isTerritory = true;
				}
				break;
			case "Country":
			case "G-Country":
			case "S-Country":
				if (!isCountry) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// selecting country
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"))
								.size() > 0) {
							MobileElement country = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"));
							MobileActionGesture.singleLongPress(country);
							MobileActionGesture.scrollTospecifiedElement("India");
						} else {
							System.out.println("***** Country is already selected ***** ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isCountry = true;
				}
				break;
			case "Employee":
			case "G-Employee":
			case "S-Employee":
				if (!isEmployee) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// pick employee
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button"))
								.click();
						if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 1) {
							CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(1).click();
						} else if(CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isEmployee = true;
				}
				break;
			case "DateTime":
			case "G-DateTime":
			case "S-DateTime":
				if (!isDateTime) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// pick dateTime
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms_basic.dateScriptInForms(1);
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
							if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
									+ fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.size() > 0) {
								CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
										+ fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
										.click();
								CommonUtils.alertContentXpath();
								Forms_basic.TimeScriptInForms(1, 5);
							}
						} else {
							System.out.println("----- DateTime is picked ----- ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isDateTime = true;
				}
				break;
			case "Time":
			case "G-Time":
			case "S-Time":
				if (!isTime) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						// pivck time
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button")).click();
							Forms_basic.TimeScriptInForms(1, 1);
						} else {
							System.out.println("**** Time already picked **** ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isTime = true;
				}
				break;
			case "Pick List":
			case "G-Pick List":
			case "S-Pick List":
				if (!isPickList) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// picking picklist value
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
									.click();
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).size() > 1) {
								CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(1).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
							}
						} else {
							System.out.println("..... Pick List is already picked ..... ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isPickList = true;
				}
				break;
//			case "Multi Pick List":
//			case "G-Multi Pick List":
//			case "S-Multi Pick List":
//				if (!isMultiPickList) {
//					MobileActionGesture.scrollUsingText(fieldsText);
//					if (CommonUtils.getdriver()
//							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
//						MobileActionGesture.scrollUsingText(fieldsText);
//						// picking multi pick list
//						MobileElement multipicklist = CommonUtils.getdriver()
//								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
//										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
//						MobileActionGesture.tapByElement(multipicklist);
//						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
//						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
//								.size() > 1) {
//							List<MobileElement> pickMultiPickList = CommonUtils.getdriver()
//									.findElements(MobileBy.className("android.widget.CheckBox"));
//							if (pickMultiPickList.get(1).isDisplayed()) {
//							MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
//							}
//						} else if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
//								.size() > 0) {
//							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
//									.click();
//						}
//						if (CommonUtils.getdriver().findElement(By.xpath("//*[@text='OK']")).isDisplayed()) {
//							AndroidLocators.clickElementusingXPath("//*[@text='OK']");
//						} else if (CommonUtils.getdriver().findElement(By.id("useTheseValuesButton")).isDisplayed()) {
//							AndroidLocators.clickElementusingID("useTheseValuesButton");
//						} else {
//							AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/useTheseValuesButton");
//						}
//						if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Navigate up']")).isDisplayed()){
//							CommonUtils.goBackward();
//						}
//						Thread.sleep(500);
//						
//					}
//					isMultiPickList = true;
//				}
//				break;
			case "Multi Select Dropdown":
			case "G-Multi Select Dropdown":
			case "S-Multi Select Dropdown":
				if (!isMultiSelectDropdown) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						//pick multi select dropdown
						MobileElement multiSelectDropdown = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
								+ fieldsText + "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
						MobileActionGesture.tapByElement(multiSelectDropdown);
						CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 1) {
							List<MobileElement> pickValues = CommonUtils.getdriver()
									.findElements(MobileBy.className("android.widget.CheckedTextView"));
							if (pickValues.get(1).isDisplayed()) {
								MobileActionGesture.singleLongPress(pickValues.get(1));
							}
						} else if (CommonUtils.getdriver()
								.findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(0).click();
						}
						if (CommonUtils.getdriver().findElement(By.xpath("//*[@text='OK']")).isDisplayed()) {
							AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						} else if (CommonUtils.getdriver().findElement(By.id("button1")).isDisplayed()) {
							AndroidLocators.clickElementusingID("button1");
						} else {
							AndroidLocators.clickElementusingResourceId("android:id/button1");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isMultiSelectDropdown = true;
				}
				break;
			case "Location":
			case "G-Location":
			case "S-Location":
				if (!isLocation) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// inputting location
						location_picking(fieldsText);
					}
					isLocation = true;
				}
				break;
			case "Phone":
			case "Phone Number":
			case "G-Phone":
			case "S-Phone":
			case "G-Phone Number":
			case "S-Phone Number":
				if (!isPhone) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// inputting phone number
						phoneNumber(fieldsText);
					}
					isPhone = true;
				}
				break;
			case "Currency":
			case "G-Currency":
			case "S-Currency":
			case "Number":
			case "G-Number":
			case "S-Number":
				if (!isCurrency) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// inputting currency
						currency_And_Number_Input(fieldsText);
					}
					isCurrency = true;
				}
				break;
			case "Dropdown":
			case "G-Dropdown":
			case "S-Dropdown":
				if (!isDropdown) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// selecting dropdown
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"))
								.size() > 0) {
							MobileElement dropdown = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"));
							MobileActionGesture.singleLongPress(dropdown);
							if (CommonUtils.getdriver()
									.findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 1) {
								CommonUtils.getdriver()
										.findElements(MobileBy.className("android.widget.CheckedTextView")).get(2).click();
							} else {
								CommonUtils.getdriver()
										.findElements(MobileBy.className("android.widget.CheckedTextView")).get(0)
										.click();
							}
						} else {
							System.out.println("**** Dropdown is already selected **** ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isDropdown = true;
				}
				break;
			case "YesNo":
			case "YesOrNo":
			case "G-YesNo":
			case "S-YesNo":
			case "G-YesOrNo":
			case "S-YesOrNo":
				if (!isYesNo) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// selecting yesno value
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"))
								.size() > 0) {
							MobileElement yesno = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView"));
							MobileActionGesture.singleLongPress(yesno);
							if (CommonUtils.getdriver()
									.findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 1) {
								CommonUtils.getdriver()
										.findElements(MobileBy.className("android.widget.CheckedTextView")).get(2)
										.click();
							}
						} else {
							System.out.println("==== YesNo is already selected ==== ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isYesNo = true;
				}
				break;
			case "Customer":
			case "G-Customer":
			case "S-Customer":
				if (!isCustomer) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// pickinf customer
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
							if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
									+ fieldsText + "')]/parent::*/parent::*/android.widget.Button")).isEnabled()) {
								CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
										+ fieldsText + "')]/parent::*/parent::*/android.widget.Button")).click();
								CommonUtils.waitForElementVisibility("//*[@text='Customers']");
								if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 1) {
									CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
								} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
								} else {
									CustomerPageActions.customerFab();
									CustomerPageActions.createCustomer();
									CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
									CommonUtils.getdriver()
											.findElement(MobileBy.xpath(
													"//*[@text='" + CustomerPageActions.randomstringCusName + "']"))
											.click();
								}
								System.out.println("Now customer is picked");
							} else {
								System.out.println(".... Customer is already selected .... !!");
							}
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
						}
					}
					isCustomer = true;
				}
				break;
			case "Custom Entity":
			case "G-Custom Entity":
			case "S-Custom Entity":
				if (!isCustomEntity) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// picking custom entity
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
							MobileElement customEntity = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Button"));
							MobileActionGesture.tapByElement(customEntity);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card"))
									.size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(1).click();
							} else {
								// write entity item creation method
								Forms_basic.createEntity();
								AndroidLocators.clickElementusingXPath("//*[@content-desc='Save']");
								CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
								CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
							}
						} else {
							System.out.println("==== Custom entity is not present ==== ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
					}
					isCustomEntity = true;
				}
				break;
			case "Form":
			case "G-Form":
			case "S-Form":
				if (!isForm) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// pick form
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
							MobileElement form = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Button"));
							MobileActionGesture.tapByElement(form);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							try {
								if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									CommonUtils.getdriver().findElementById("load_more_button").click();
									CommonUtils.waitForElementVisibility(
											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
									if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
											.size() > 0) {
										CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
												.click();
									} else {
										CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
										CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
										verifyFormPagesAndFill();
										CommonUtils.waitForElementVisibility(
												"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
										if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
												.size() > 0) {
											CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
													.get(0).click();
										}
									}
								}
								CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
							} catch (Exception e) {
								System.out.println(e);
							}
						} else {
							System.out.println("**** Form is already picked **** ");
						}
					}
					isForm = true;
				}
				break;
			case "Signature":
			case "G-Signature":
			case "S-Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// deleting the existing signature
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'DELETE')]"))
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'DELETE')]");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button");
						// capturing signature
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'CAPTURE')]"))
								.size() > 0) {
							MobileElement signature = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'CAPTURE')]"));
							MobileActionGesture.tapByElement(signature);
							MediaPermission.mediaPermission();
							CommonUtils.waitForElementVisibility("//*[@text='Signature']");
							MobileElement signatureCapture = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
							MobileActionGesture.singleLongPress(signatureCapture);
						} else {
							System.out.println("---- Signature is not present ---- ");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
						// CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.CAMERA));
					}
					isSignature = true;
				}
				break;
//			case "Multi Pick Customer":
//			case "G-Multi Pick Customer":
//			case "S-Multi Pick Customer":
//				if (!isMultiPickCustomer) {
//					MobileActionGesture.scrollUsingText(fieldsText);
//					if (CommonUtils.getdriver()
//							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
//						MobileActionGesture.scrollUsingText(fieldsText);
//						// picking multi pick customer
//						MobileElement multiPickCustomer = CommonUtils.getdriver()
//								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
//										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
//						MobileActionGesture.tapByElement(multiPickCustomer);
//						CommonUtils.waitForElementVisibility("//*[@text='Customers']");	
//						if (CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).size() > 1) {
//							List<MobileElement> selectCheckbox = CommonUtils.getdriver()
//									.findElements(MobileBy.id("pickCustomerCheck"));
//							if (selectCheckbox.get(1).isDisplayed()) {
//								MobileActionGesture.singleLongPress(selectCheckbox.get(1));
//							}
//						} else if (CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).size() > 0) {
//							CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).get(0).click();
//						} else {
//							CustomerPageActions.customerFab();
//							CustomerPageActions.createCustomer();
//							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
//							CommonUtils.getdriver()
//									.findElement(MobileBy.xpath("//*[@text='" + CustomerPageActions.randomstringCusName
//											+ "']/parent::*/parent::*/parent::*/parent::*/android.widget.CheckBox"))
//									.click();
//						}
//						if (CommonUtils.getdriver().findElement(By.id("selectCustomers")).isDisplayed()) {
//							AndroidLocators.clickElementusingID("selectCustomers");
//						} else if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Select']"))
//								.isDisplayed()) {
//							AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
//						} else {
//							AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/selectCustomers");
//						}
//						if (CommonUtils.getdriver().findElement(By.xpath("//*[@content-desc='Navigate up']"))
//								.isDisplayed()) {
//							CommonUtils.goBackward();
//						} else {
//							Thread.sleep(500);
//						}
//						Thread.sleep(500);
//					}
//					isMultiPickCustomer = true;
//				}	
//				break;
			}
		}
	}
}