package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.openqa.selenium.By;

import Actions.HomepageAction;
import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;
import utils.MediaPermission;

public class Work {
	public static String generateWorkName;

	// go to work page from home fab icon '+' then swipe and click on the specified
	// work
	public static void goToWorkPage(String workName) throws MalformedURLException {
		CommonUtils.homeFabClick();
		HomepageAction.select_dialog_list("Work");
		//converting to uppercase where in homefab for all workname are displaying in upper case
		workName = workName.toUpperCase();
		// swipe and click on the specified work
		MobileActionGesture.scrollTospecifiedElement("" + workName + "");
		try {
			CommonUtils.interruptSyncAndLetmeWork();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonUtils.waitForElementVisibility("//*[contains(@text,'Create')]");
	}

	// scrolling to specified work name
	public static void scrollToSpecifiedWork(String workName) throws MalformedURLException, InterruptedException {
		MobileActionGesture.scrollTospecifiedElement("" + workName + "");
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@text='" + workName + "']");
	}

	// verify work in home screen if work not exist then click on home fab to create
	// a work
	public static void checkWorkExistInHomePageorNot(String workName)
			throws InterruptedException, MalformedURLException {
		CommonUtils.wait(3);
		try {
			CommonUtils.getdriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
					+ "new UiSelector().text(\"" + workName + "\"));");
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workName + "')]"))
					.size() > 0) {
				AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workName + "')]");
				CommonUtils.interruptSyncAndLetmeWork();
				workFab();
			} else {
				goToWorkPage(workName);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// work search
	public static void workSearch(String workName) throws MalformedURLException, InterruptedException {
		AndroidLocators.clickElementusingID("action_search");
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(workName);
		AndroidLocators.pressEnterKeyInAndroid();
//		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.getdriver().hideKeyboard();
	}

	// verify work exist or not
	public static void verifyWorkExistOrNotInWorkScreen(String workName)
			throws MalformedURLException, InterruptedException, ParseException {
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']"))
					.isDisplayed()) {
				System.out.println("*** Work with this name exist *** !!");
				AndroidLocators.clickElementusingXPath("//*[@text='Work Name: " + workName + "']");
				CommonUtils.interruptSyncAndLetmeWork();
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/button1']");
			}
		} catch (Exception e) {
			System.out.println("--- Going to create work --- !!");
			workFab();
			try {
				work_Creation();
			} catch (MalformedURLException | InterruptedException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			saveWork();
//			fill_Work_MandatoryFields();
			workSearch(generateWorkName);
			verifyWorkExistOrNotInWorkScreen(generateWorkName);
		}
	}

	// verify the created work
	public static void goingToWorkScreen(String workName)
			throws InterruptedException, MalformedURLException, ParseException {
		CommonUtils.wait(3);
		if (CommonUtils.getdriver().findElements(By.id("action_search")).size() > 0) {
			workSearch(generateWorkName);
			CommonUtils.interruptSyncAndLetmeWork();
			verifyWorkExistOrNotInWorkScreen(generateWorkName);
		} else {
			workName = workName.toUpperCase();
			CommonUtils.waitForElementVisibility("//*[@text='Home']");
			MobileActionGesture.scrollUsingText("Home");
			String clickonwork = CommonUtils.getdriver()
					.findElementByXPath("//*[contains(@text,'" + workName + " WAITING FOR YOUR ACTION')]").getText();
			AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + clickonwork + "')]");
			CommonUtils.interruptSyncAndLetmeWork();
			workSearch(generateWorkName);
			CommonUtils.interruptSyncAndLetmeWork();
			verifyWorkExistOrNotInWorkScreen(generateWorkName);
		}
	}

	// clicking on '+' in work
	public static void workFab() throws InterruptedException {
		if (AndroidLocators.findElements_With_Id("fab").size() > 0) {
			AndroidLocators.clickElementusingID("fab");
		} else if (AndroidLocators.resourceId("in.spoors.effortplus:id/fab").isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/fab");
		} else {
			AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/fab']");
		}
		CommonUtils.interruptSyncAndLetmeWork();
	}

	// work creation
	public static void createWork() throws MalformedURLException, InterruptedException {
		// insertig working name randon name using 'RandomStringUtils' library
		generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//android.widget.EditText[contains(@text,'Work Name')]"))
				.sendKeys(generateWorkName);
		// click on end time
		AndroidLocators.clickElementusingXPath(
				"//*[contains(@text,'Ends')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
		CommonUtils.alertContentXpath();
		workEndTime(1, 5);
	}

	// work end time added as given hours extra because End time should be greater
	public static void workEndTime(int timeCount, int minsCount) throws MalformedURLException, InterruptedException {
		// retrieving time
		Date date = new Date();
		// time 12hrs format
		SimpleDateFormat DateFor = new SimpleDateFormat("h:mm a");
		String stringDate = DateFor.format(date);
		System.out.println("Hours Format : " + stringDate);

		// Splitting time
		String[] CurrentTimesplitValue = DateFor.format(date).split(":");

		// retrieving current time into hours and minutes
		String presentHours = CurrentTimesplitValue[0];
		System.out.println("CurrentHours: " + presentHours);
		String CurrentMin = CurrentTimesplitValue[1];
		System.out.println("CurrentMins: " + CurrentMin);

		// splitting AMPM from mins
		String[] splitAMPM = CurrentMin.split(" ");
		// retrieving mins and AM PM
		String getAmPm = splitAMPM[1];
		String getMins = splitAMPM[0];
		System.out.println("AmPm :" + getAmPm);
		System.out.println(" .... CurrMinutes .... :" + getMins);

		// adding extra hours to the current hours and splitting into hrs and mins
		date = DateUtils.addHours(date, timeCount);
		String workAddhrs = DateFor.format(date);
		System.out.println(" ===== After adding hours time is ===== : " + workAddhrs);
		String[] splitValueExtendedHrs = workAddhrs.split(":");
		String extendedHours = splitValueExtendedHrs[0];
		System.out.println(" **** addedHours **** : " + extendedHours);

		// adding minutes
		date = DateUtils.addMinutes(date, minsCount);

		date = DateUtils.truncate(date, Calendar.MINUTE);
		// splitting extended Minutes
		String[] splitMins = DateFor.format(date).split(":");
		// retrieving minutes
		String addedMins = splitMins[1];
		System.out.println("----- After adding Mins ------ : " + addedMins);

		// retrieving AM & PM after adding hours and mins
		String[] splitAmPm = workAddhrs.split(" ");
		String getAddhrsOfAmPm = splitAmPm[1];
		System.out.println("===== After added hrs AmPm is ===== : " + getAddhrsOfAmPm);

		// get xpath of current hour and pass variable of current,added hours
		MobileElement sourceHour = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@content-desc='" + presentHours + "']"));
		MobileElement destinationHour = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@content-desc='" + extendedHours + "']"));
		// using longpress method moving element from source to destination(i.e current
		// hr to added hr)
		MobileActionGesture.Movetoelement(sourceHour, destinationHour);
		CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/minutes']");
//		MobileElement minsClick = CommonUtils.getdriver().findElementByXPath("//*[@text='"+addedMins+"']");
//		MobileActionGesture.singleLongPress(minsClick);
		MobileElement clickAmPm = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@text='" + getAddhrsOfAmPm + "']"));
		MobileActionGesture.singleLongPress(clickAmPm);
		CommonUtils.OkButton("OK");
		CommonUtils.wait(3);
	}

	// while saving the work creation verify save or continue button is displaying
	// then perform accordingly
	public static void saveWork() throws InterruptedException {
		CommonUtils.handling_alert("saveWork", "saveWork", "in.spoors.effortplus:id/saveWork",
				"in.spoors.effortplus:id/saveWork", "//*[@content-desc='Save']", "//*[@content-desc='Save']");
		//if save alert is displayed then click on save
		if (AndroidLocators.returnUsingId("workSaveButton").isDisplayed()) {
			AndroidLocators.clickElementusingID("workSaveButton");
		} else if (AndroidLocators.resourceId("in.spoors.effortplus:id/workSaveButton").isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/workSaveButton");
		} else if (AndroidLocators.xpath("//*[@text='SAVE']").isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@text='SAVE']");
		}
		try {    //if work override alert is display then click on continue
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@resource-id='android:id/button1']"))
					.isDisplayed()) {
				CommonUtils.OkButton("CONTINUE");
				System.out.println(" ---- work is saved successfully!! ----");
			}
		} catch (Exception e) {
			System.out.println(" **** work time is not override **** ");
		}
		CommonUtils.interruptSyncAndLetmeWork();
	}

	// worksave and startaction
	public static void save_And_StartAction() throws MalformedURLException, InterruptedException {
		AndroidLocators.clickElementusingID("saveWork");
		if (CommonUtils.getdriver().findElement(By.id("workSaveAndStartActionButton")).isDisplayed()) {
			AndroidLocators.clickElementusingID("workSaveAndStartActionButton");
			try {
				if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@resource-id='android:id/button1']"))
						.isDisplayed()) {
					CommonUtils.OkButton("CONTINUE");
					System.out.println(" ---- work is saved successfully!! ----");
				}
			} catch (Exception e) {
				System.out.println(" **** work time is not override **** ");
			}
		}
	}

	// perform work action and complete the work
	public static void WorkAction() throws InterruptedException, MalformedURLException, ParseException {
		do {
			if (CommonUtils.getdriver().findElements(MobileBy.id("button1")).size() > 0) {
				if (CommonUtils.getdriver().findElement(MobileBy.id("button1")).isEnabled()) {
					AndroidLocators.clickElementusingID("button1");
					// if pop is display to perform action then click and perform action else
					// directly perform action
					if (CommonUtils.getdriver()
							.findElements(By.xpath("//*[@resource-id='in.spoors.effortplus:id/heading']")).size() > 0) {
						AndroidLocators.clickElementusingClassName("android.widget.Button");
					} else {
						AndroidLocators.clickElementusingID("button1");
					}
					//if any alert display then handle and perform work action
					checkinToCus_PerformWorkAction();
					//wait until formsave element is displayed
					CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
					// perform action until next action displayed
					Forms_basic.verifyFormPagesAndFill();
					Forms_basic.formSaveButton();
				}
			} else {
				if (CommonUtils.getdriver()
						.findElement(MobileBy
								.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/button1\")"))
						.isEnabled()) {
					AndroidLocators.clickElementusingID("button1");
					// if pop is display to perform action then click and perform action else
					// directly perform action
					if (CommonUtils.getdriver()
							.findElements(By.xpath("//*[@resource-id='in.spoors.effortplus:id/heading']")).size() > 0) {
						AndroidLocators.clickElementusingClassName("android.widget.Button");
					} else {
						AndroidLocators.clickElementusingID("button1");
					}
					//if any alert display then handle and perform work action
					checkinToCus_PerformWorkAction();
					//wait until formsave element is displayed
					CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
					// perform action until next action displayed
					Forms_basic.verifyFormPagesAndFill();
					Forms_basic.formSaveButton();
				}
			}
		} while (CommonUtils.getdriver().findElementsById("button1").size() > 0);
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}

	// perform work single action
	public static void performSingleAction() throws MalformedURLException, InterruptedException, ParseException {
		if (CommonUtils.getdriver().findElements(MobileBy.id("button1")).size() > 0) {
			if (CommonUtils.getdriver().findElement(MobileBy.id("button1")).isEnabled()) {
				AndroidLocators.clickElementusingID("button1");
				// if pop is display to perform action then click and perform action else
				// directly perform action
				if (CommonUtils.getdriver()
						.findElements(By.xpath("//*[@resource-id='in.spoors.effortplus:id/heading']")).size() > 0) {
					AndroidLocators.clickElementusingClassName("android.widget.Button");
				} else {
					AndroidLocators.clickElementusingID("button1");
				}
				//if any alert display then handle and perform work action
				checkinToCus_PerformWorkAction();
				//wait until formsave element is displayed
				CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
				// perform action until next action displayed
				Forms_basic.verifyFormPagesAndFill();
				Forms_basic.formSaveButton();
			}
		} else {
			if (CommonUtils.getdriver()
					.findElement(MobileBy
							.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/button1\")"))
					.isEnabled()) {
				AndroidLocators.clickElementusingID("button1");
				// if pop is display to perform action then click and perform action else
				// directly perform action
				if (CommonUtils.getdriver()
						.findElements(By.xpath("//*[@resource-id='in.spoors.effortplus:id/heading']")).size() > 0) {
					AndroidLocators.clickElementusingClassName("android.widget.Button");
				} else {
					AndroidLocators.clickElementusingID("button1");
				}
				//if any alert display then handle and perform work action
				checkinToCus_PerformWorkAction();
				//wait until formsave element is displayed
				CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
				// perform action and save
				Forms_basic.verifyFormPagesAndFill();
				Forms_basic.formSaveButton();
			}
		}
	}
	
	
	// check-in to customer to perform workaction
	public static void checkinToCus_PerformWorkAction() throws InterruptedException {
		CommonUtils.handling_alert("message", "button1", "android:id/message", "android:id/button1",
				"//*[@resource-id='android:id/message']", "//*[@text='CHECK IN']");
		CustomerPageActions.customerCheckInReason();
	}


	// Retrieving work status (i.e Completed,Yet to Start, in progrsss) based on the
	// text you passed
	public static void workStatus(String workName) throws MalformedURLException, InterruptedException {
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/workStatusText']");
		MobileElement getWorkStatus = CommonUtils.getdriver().findElement(MobileBy.xpath(
				"//*[@id='workStatusText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Work Name: "
						+ workName + "']]]"));
		String workStatus = getWorkStatus.getText();
		System.out.println(" ----- status of work is ----- : " + workStatus);
	}

	// move to homepage from work
	public static void moveToHomepageFromWork() throws InterruptedException, MalformedURLException {
		if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Collapse']")).isDisplayed()) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Collapse']");
			CommonUtils.openMenu();
			CommonUtils.clickHomeInMenubar();
		} else {
			System.out.println("collapse symbol not found");
		}
	}


	// creating work with all fields
	public static List<MobileElement> work_Creation()
			throws MalformedURLException, InterruptedException, ParseException {
		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// retrieving the list count
		int workFieldsCount = workFields.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		workFields.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workFields.isEmpty() && workFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workFields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// get the count of work fields
			workFieldsCount = workFields.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ workFields.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + workFields.get(i).getText());

				// if list matches with last element the loop will break
				if (workFields.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + workFields.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();
		
		//inserting workfields data
		workFieldsDataInsert(workFields, workFieldsCount);
		
		return workFields;
	}
	
	//insert workfields data
	public static void workFieldsDataInsert(List<MobileElement> workFields, int workFieldsCount) throws MalformedURLException, InterruptedException, ParseException {
		
		boolean isMultipicklist = false, isMultiselectdropdown = false, isyesNo = false, isSignature = false, isDropdown = false,
				isPriority = false, isAddressSameAsCustomer = false;

		
		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = workFields.get(j).getText();
			System.out.println(workOriginalFields);
			String[] val = workOriginalFields.split("\\*");
			
			String workFieldsText = val[0];
		//	String workFieldsText = workFields.get(j).getText().replaceAll("\\s[!@#$%&*,.?\":{}|<>]", "");
			
			System.out.println();
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);
			
//			String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(workFieldsText);
			
			switch (workFieldsText) {
			case "Work Name":
				MobileActionGesture.scrollUsingText(workFieldsText);
				generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"(//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']//android.widget.EditText)[1]"))
							.sendKeys(generateWorkName);
				} 
				generateWorkName = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"(//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']//android.widget.EditText)[1]"))
						.getText();
				System.out.println("---- Retrieve the workname ---- :" + generateWorkName);
				break;
			case "Description":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[starts-with(@text,'" + workFieldsText + "')]");
				} 
				break;
			case "Ends":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
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
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					workEndTime(1, 5);
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A CUSTOMER");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					} else {
						System.out.println("Customer is already selected!!");
					}
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Customer-SYS":
				MobileActionGesture.scrollUsingDirectText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					} else {
						System.out.println("Customer is already selected!!");
					}
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Employee":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A EMPLOYEE");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
								.click();
						AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
					} else if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Employee-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
								.click();
						AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
					} else if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Priority":
				if (!isPriority) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("Pick a value");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement priority = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(priority);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isPriority = true;
				}
				break;
			case "Address same as customer":
				if (!isAddressSameAsCustomer) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("No");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement address_Same_As_customer = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(address_Same_As_customer);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 1) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(2).click();
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isAddressSameAsCustomer = true;
				}
				break;
			case "Phone Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Phone":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Street":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Area":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "City":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Landmark":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Country-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick a country");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Country":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick a country");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "State":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Pincode":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Location-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A LOCATION");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					CommonUtils.wait(10);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					CommonUtils.wait(10);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				}
				CommonUtils
						.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
				break;
			case "Location":
				MobileActionGesture.scrollUsingText(workOriginalFields);
				MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/pick_location_button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					CommonUtils.wait(10);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				} else {
					MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/pick_location_edittext");
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					CommonUtils.wait(10);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				}
				CommonUtils
						.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Custom Entity":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("ENTITY");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
						MobileElement customEntity = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
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
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Custom entity is already picked");
					}
				}
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick customer type");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick customer type')]"))
							.size() > 0) {
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick customer type')]"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					} else {
						MobileActionGesture.directScrollToView(workFieldsText);
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick customer type')]"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					System.out.println("Customer type is aready picked");
				}
				break;
			case "DateTime":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/pick_date_button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
							.size() > 0) {
						AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
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
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
								+ workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
							workEndTime(1, 5);	
						}
					} else {
						System.out.println("DateTime is already picked");
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
							.size() > 0) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]");
						workEndTime(1, 5);
					} else {
						System.out.println("Time already picked");
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.click();
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
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Date is already picked");
					}
				}
				break;
			case "Dropdown":
				if(!isDropdown) {
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick a value");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
							.size() > 0) {
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
					} else {
						System.out.println("Dropdown is already picked");
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				isDropdown = true;
			}
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK PICK LIST");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
							.size() > 0) {
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
								.click();
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
						} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Pick List is already picked");
					}
				}
				break;
			case "Form":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A FORM");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"))
							.size() > 0) {
						MobileElement form = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"));
						MobileActionGesture.tapByElement(form);
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						try {
							if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
							} else {
								CommonUtils.getdriver().findElementById("load_more_button").click();
//								CommonUtils.waitForElementVisibility(
//										"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
								CommonUtils.wait(5);
								if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
									CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
									Forms_basic.verifyFormPagesAndFill();
									Forms_basic.formSaveButton();
									CommonUtils.goBackward();
									CommonUtils.waitForElementVisibility(
											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
									if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
											.size() > 0) {
										CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
												.click();
									}
								}
							}
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						} catch (Exception e) {
							System.out.println(e);
						}
					} else {
						System.out.println("Form is already picked");
					}
				}
				break;
			case "Email":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendEmailInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "URL":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendUrlInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Multi Pick List":
				if (!isMultipicklist) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("PICK MULTI PICK LIST");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
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
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isMultipicklist = true;
				}
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick territory type");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick territory type')]"))
							.size() > 0) {
						MobileElement terriory = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick territory type')]"));
						MobileActionGesture.singleLongPress(terriory);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					} else {
						System.out.println("Territory is already selected");
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Multi Select Dropdown":
				if (!isMultiselectdropdown) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("PICK MULTI SELECT DROPDOWN(S)");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
						MobileActionGesture.tapByElement(multiSelectDropdown);
						CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
						List<MobileElement> pickValues = CommonUtils.getdriver()
								.findElements(MobileBy.className("android.widget.CheckedTextView"));
						if (pickValues.get(0).isDisplayed()) {
							MobileActionGesture.singleLongPress(pickValues.get(0));
						}
						if (pickValues.get(1).isDisplayed()) {
							MobileActionGesture.singleLongPress(pickValues.get(1));
						}
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isMultiselectdropdown = true;
				}
				break;
			case "YesNo":
				if (!isyesNo) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement yesno = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner"));
							MobileActionGesture.singleLongPress(yesno);
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						} else {
							System.out.println("YesNo is already selected");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isyesNo = true;
				}
				break;
			case "Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("CAPTURE SIGNATURE");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						work_Capturing_Signature(workFieldsText);
					} else {
						MobileActionGesture.scrollUsingText(workFieldsText);
						work_Capturing_Signature(workFieldsText);
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					isSignature = true;
				}
				break;
			case "Rich Text Format":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[@text='" + workFieldsText
							+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.EditText");
				}
				break;
			} // switch statement close
		}
	}

	// capturing signature
	public static void work_Capturing_Signature(String fieldsText) throws MalformedURLException, InterruptedException {
		// deleting the existing signature if present
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/delete_button']"))
				.size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/delete_button']");
			CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button");
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"))
				.size() > 0) {
			MobileElement signature = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"));
			MobileActionGesture.tapByElement(signature);
			MediaPermission.mediaPermission();
			CommonUtils.waitForElementVisibility("//*[@text='Signature']");
			MobileElement signatureCapture = CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
			MobileActionGesture.singleLongPress(signatureCapture);
		} else {
			System.out.println("---- Signature is not present ---- ");
		}
	}

	// click on the work which is displaying in the home screen and naviagte to
	// respective work
	public static void clickOnWorkDisplayInHomeScreenNotification(String workName) throws InterruptedException {
		MobileActionGesture.scrollUsingText("Home");
		AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workName + " WAITING FOR YOUR ACTION')]");
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
	}

	// search the work and click on it
	public static void searchAndClickOnWork(String workName)
			throws MalformedURLException, InterruptedException, ParseException {
		workSearch(workName);
		verifyWorkExistOrNotInWorkScreen(workName);
	}

	// click on edit work
	public static void clickOnEditWorkSymbol() {
		CommonUtils.handling_alert("(editWork", "editWork", "in.spoors.effortplus:id/editWork",
				"in.spoors.effortplus:id/editWork", "//*[@content-desc='Edit']", "//*[@content-desc='Edit']");
		CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
	}

	// modifying the work
	public static void workModification() throws MalformedURLException, InterruptedException, ParseException {
		// Declaring the workLabelElements list
		List<MobileElement> workLabelElements = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]");

		// retrieving the list count
		int workFieldsCount = workLabelElements.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		workLabelElements.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workLabelElements.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workLabelElements.get(workLabelElements.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workLabelElements.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workLabelElements.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workLabelElements.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workLabelElements.isEmpty() && workLabelElements != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workLabelElements.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

			// get the count of work fields
			workFieldsCount = workLabelElements.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ workLabelElements.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + workLabelElements.get(i).getText());

				// if list matches with last element the loop will break
				if (workLabelElements.get(i).getText().equals(workLastElement)) {
					System.out.println(
							"----- Work fields text inside elements ----- : " + workLabelElements.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();

		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = workLabelElements.get(j).getText();
			String workFieldsText = workLabelElements.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);

			boolean isMultipicklist = false, isMultiselectdropdown = false, isyesNo = false, isSignature = false,
					isDate = false, isDateTime = false, isPriority = false;

			switch (workFieldsText) {
			case "Ends":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
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
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					CommonUtils.alertContentXpath();
					workEndTime(1, 5);
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					} else {
						System.out.println("Customer is already selected!!");
					}
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Customer-SYS":
				MobileActionGesture.scrollUsingDirectText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.wait(5);
						System.out.println("Now customer is picked");
					} else {
						System.out.println("Customer is already selected!!");
					}
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Employee":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
								.click();
						AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
					} else if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Employee-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0)
								.click();
						AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
					} else if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Priority":
				if (!isPriority) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("Address same as customer");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement priority = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"));
						MobileActionGesture.singleLongPress(priority);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
						CommonUtils.wait(3);
					}
					isPriority = true;
				}
				break;
			case "Address same as customer":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement address_Same_As_customer = CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(address_Same_As_customer);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(2).click();
					}
					CommonUtils.wait(3);
				}
				break;
			case "Country-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				CommonUtils.wait(3);
				break;
			case "Country":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				CommonUtils.wait(3);
				break;
			case "Custom Entity":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
						MobileElement customEntity = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
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
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Custom entity is already picked");
					}
				}
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"))
							.size() > 0) {
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					} else {
						MobileActionGesture.directScrollToView(workFieldsText);
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					CommonUtils.wait(3);
					System.out.println("Customer type is aready picked");
				}
				break;
			case "DateTime":
				MobileActionGesture.directScrollToView(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button/*[@resource-id='in.spoors.effortplus:id/pick_date_button']"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button/*[@resource-id='in.spoors.effortplus:id/pick_date_button']"))
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
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
								+ workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
									+ workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.click();
							CommonUtils.alertContentXpath();
							workEndTime(1, 5);
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						}
					} else {
						System.out.println("DateTime is already picked");
					}
				}
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
						CommonUtils.alertContentXpath();
						workEndTime(1, 5);
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Time already picked");
					}
				}
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
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
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Date is already picked");
					}
				}
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"))
							.size() > 0) {
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[@resource-id='android:id/text1']"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
					} else {
						System.out.println("Dropdown is already picked");
					}
				}
				CommonUtils.wait(3);
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
						} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
						}
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					} else {
						System.out.println("Pick List is already picked");
					}
				}
				break;
			case "Form":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
						MobileElement form = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
						MobileActionGesture.tapByElement(form);
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						try {
							if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
							} else {
								CommonUtils.getdriver().findElementById("load_more_button").click();
//								CommonUtils.waitForElementVisibility(
//										"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
								CommonUtils.wait(5);
								if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
									CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
									Forms_basic.verifyFormPagesAndFill();
									Forms_basic.formSaveButton();
									CommonUtils.goBackward();
									CommonUtils.waitForElementVisibility(
											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
									if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
											.size() > 0) {
										CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
												.click();
									}
								}
							}
							CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
						} catch (Exception e) {
							System.out.println(e);
						}
					} else {
						System.out.println("Form is already picked");
					}
				}
				break;
			case "Multi Pick List":
				if (!isMultipicklist) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[2]");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[2]"));
						MobileActionGesture.tapByElement(multipicklist);
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						List<MobileElement> pickMultiPickList = CommonUtils.getdriver()
								.findElements(MobileBy.className("android.widget.CheckBox"));
						if (pickMultiPickList.get(0).isDisplayed()) {
							MobileActionGesture.singleLongPress(pickMultiPickList.get(0));
						} else {
							AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						}
//						if (pickMultiPickList.get(1).isDisplayed()) {
//							MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
//						}
//						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isMultipicklist = true;
				}
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
						+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick territory type')]"))
							.size() > 0) {
						MobileElement terriory = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick territory type')]"));
						MobileActionGesture.singleLongPress(terriory);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					} else {
						System.out.println("Territory is already selected");
					}
				}
				break;
			case "Multi Select Dropdown":
				if (!isMultiselectdropdown) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
						MobileActionGesture.tapByElement(multiSelectDropdown);
						CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
						List<MobileElement> pickValues = CommonUtils.getdriver()
								.findElements(MobileBy.className("android.widget.CheckedTextView"));
						if (pickValues.get(0).isDisplayed()) {
							MobileActionGesture.singleLongPress(pickValues.get(0));
						}
//						if (pickValues.get(1).isDisplayed()) {
//							MobileActionGesture.singleLongPress(pickValues.get(1));
//						}
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + workFieldsText + "')]");
					}
					isMultiselectdropdown = true;
				}
				break;
			case "YesNo":
				if (!isyesNo) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
							+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement yesno = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner"));
							MobileActionGesture.singleLongPress(yesno);
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						} else {
							System.out.println("YesNo is already selected");
						}
					}
					CommonUtils.wait(3);
					isyesNo = true;
				}
				break;
			case "Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						work_Capturing_Signature(workFieldsText);
					} else {
						MobileActionGesture.scrollUsingText(workFieldsText);
						work_Capturing_Signature(workFieldsText);
					}
					isSignature = true;
				}
				break;
			case "Rich Text Format":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[@text='" + workFieldsText
							+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.EditText");
				}
				break;
			}
		}
	}// work modification method close
	
	// work mandatory fields
	public static void fill_Work_MandatoryFields() throws MalformedURLException, InterruptedException, ParseException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]"))
				.size() > 0) {
			work_Mandatory_fields();
		}
	}

	// creating work with all fields
	public static List<MobileElement> work_Mandatory_fields()
			throws MalformedURLException, InterruptedException, ParseException {
		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// work all fields
		List<MobileElement> mandatoryFields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]");

		
		// retrieving the list count
		int workFieldsCount = mandatoryFields.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		mandatoryFields.clear();
		workFields.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		mandatoryFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = mandatoryFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!mandatoryFields.isEmpty() && mandatoryFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			
			workFields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// adding the work fields present in the first screen
			mandatoryFields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]"));

			// get the count of work fields
			workFieldsCount = mandatoryFields.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFields.size(); i++) {

				// if list matches with last element the loop will break
				if (workFields.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + workFields.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();
		
		//insert only work mandatory fields 
		workFieldsDataInsert(mandatoryFields, workFieldsCount);
		
		return workFields;
	}

	
	//inside work card perform work check-in
	public static void workCheck_In() throws MalformedURLException, InterruptedException, ParseException {
		MobileElement workCheckIn= AndroidLocators.returnUsingId("workCheckInCheckOut");
		if(workCheckIn.getText().contains("OFF")) {
			System.out.println(".... User is going to checkin to work .... ");
			workCheckIn.click();
			//handling checkin alert
			workCheckinAlert();
		}
	}
	
	// work check-in alert
	public static void workCheckinAlert() throws MalformedURLException, InterruptedException, ParseException {
		MobileElement checkin = CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"));
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			//handling interrupt sync
			CommonUtils.interruptSyncAndLetmeWork();
			if (CommonUtils.getdriver().findElements(MobileBy.id("saveForm")).size() > 0) {
				// fill form
				Forms_basic.verifyFormPagesAndFill();
				// save form
				saveWorkCheckinForm();
			} else if (CommonUtils.getdriver()
					.findElements(MobileBy
							.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/saveForm\")"))
					.size() > 0) {
				// fill work check-in form
				Forms_basic.verifyFormPagesAndFill();
				// save form
				saveWorkCheckinForm();
			}
			//waiting for work check-in check-out element
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/workCheckInCheckOut']");
		}
	}
	
	
	// save form for work check-in
	public static void saveWorkCheckinForm() {
		if (CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).isDisplayed()) {
			AndroidLocators.clickElementusingID("saveForm");
			//handling form save alert
			Forms_basic.formSaveAlert();
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/saveForm\")"))
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/saveForm");
			//handling form save alert
			Forms_basic.formSaveAlert();
		} else {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Save']");
			//handling form save alert
			Forms_basic.formSaveAlert();
		}
	}
	
	// work check-out
	public static void workCheck_Out() throws MalformedURLException {
		MobileElement workCheckIn = AndroidLocators.returnUsingId("workCheckInCheckOut");
		if (workCheckIn.getText().contains("ON")) {
			System.out.println(".... User is going to check-out work .... ");
			//handling checkout alert
			check_out_Alert();
		}
	}
	
	// work checkout
	public static void check_out_Alert() throws MalformedURLException {
		MobileElement checkOut = CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"));
		if (checkOut.getText().contains("CHECK OUT")) {
			MobileActionGesture.tapByElement(checkOut);
		}
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/workCheckInCheckOut']");
	}
	
	//work rejection
	public static void workRejection() throws MalformedURLException, InterruptedException, ParseException {
		boolean isElementFound = true;
		if (CommonUtils.getdriver().findElements(MobileBy.id("rejectWork")).size() > 0) {
			AndroidLocators.clickElementusingID("rejectWork");
			//handling work rejection alert
			workRejectionAlert();
		} else if (CommonUtils.getdriver()
				.findElements(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/rejectWork\")"))
				.size() > 0) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/rejectWork");
			//handling work rejection alert
			workRejectionAlert();
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='Reject']")).size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Reject']");
			//handling work rejection alert
			workRejectionAlert();
		} else {
			System.out.println("*** work rejection symbol is not displayed *** ");
		}
		
		//validating next action is enabled or not when work is rejected
		isElementFound = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@resource-id='in.spoors.effortplus:id/button1']")).isEnabled();
		if (isElementFound == false) {
			System.out.println("*** Next action is disabled due to work rejection  ***");
		} else {
			System.out.println("*** Next action is enabled because work was not rejected ***");
		}
	}
	
	//work rejection alert
	public static void workRejectionAlert() throws InterruptedException, MalformedURLException, ParseException {
		if (CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/message\")"))
				.isDisplayed()) {
			//retrieving the text reject work text
			String getTextOfRejectWork = CommonUtils.getdriver()
					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/message\")"))
					.getText();
			System.out.println(" ---- Work rejection text ---- :" + getTextOfRejectWork);
			//click on ok button to reject work
			CommonUtils.OkButton("OK");
			//handling interrupt sync 
			CommonUtils.interruptSyncAndLetmeWork();
			
			//if work rejection had activity then fill activity and reject else wait 1second
			if (CommonUtils.getdriver().findElements(MobileBy.id("saveForm")).size() > 0) {
				// fill form
				Forms_basic.verifyFormPagesAndFill();
				// save form
				saveWorkCheckinForm();
			} else if (CommonUtils.getdriver()
					.findElements(MobileBy
							.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/saveForm\")"))
					.size() > 0) {
				// fill work check-in form
				Forms_basic.verifyFormPagesAndFill();
				// save form
				saveWorkCheckinForm();
			} else {
				CommonUtils.wait(1);
			}
		}
	}
	
	//validate work '+' icon 
	public static void verifyingFabIcon(String workName) throws InterruptedException, MalformedURLException {
		//swipe to workcard
		MobileActionGesture.scrollUsingText(workName);
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workName + "')]"))
				.size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workName + "')]");
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.waitForElementVisibility("//*[contains(@text,'" + workName + "')]");
		} else {
			CommonUtils.homeFabClick();
			HomepageAction.select_dialog_list("Work");
		}
	}
	
	// validating work fab icon('+')
	public static void workFabIcon_DisplayingOrNot(String workName) {
		boolean isElementDisplay = false;
		if (CommonUtils.getdriver().findElements(MobileBy.id("action_search")).size() > 0) {
			isElementDisplay = CommonUtils.getdriver().findElement(MobileBy.id("fab")).isDisplayed();
			if (isElementDisplay == false) {
				System.out.println("**** Work fab icon is not displaying due to allow user to add new work setting is disabled **** ");
			} else {
				System.out.println(
						"**** Work fab icon is displaying due to allow user to add new work setting is enabled **** ");
			}
		} else {
			try {
				// swipe and click on the specified work
				MobileActionGesture.scrollUsingText("" + workName + "");
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + workName + "']")).size() > 0) {
					System.out.println("*** Workname is not displaying based on configuration ***");
				} else {
					System.out.println("*** Workname is displaying based on configuration ***");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// work can only access to emp group othan than emplyee group can't access
	public static void restrict_access_to_empGroup(String workName) {
		boolean isElementFound;
		try {
			MobileActionGesture.scrollUsingText(workName);
			if (AndroidLocators.xpath("//*[@starts-with(@text,'" + workName + "')]").isDisplayed()) {
				isElementFound = AndroidLocators.xpath("//*[@starts-with(@text,'" + workName + "')]").isDisplayed();
				System.out.println("**** work is visible to employee **** :" + workName);
			} else {
				System.out.println("---- work is not visible due to restriction ---- ");
			}
		} catch (Exception e) {

		}
	}
	
	// validating work delete icon is displaying based on permissions
	public static void verify_Work_Delete_Icon_status() {
		if (!(AndroidLocators.findElements_With_Id("discardWork").size() > 0)) {
			System.out.println("*** work delete symbol is not displaying *** ");
		} else if (!(AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/discardWork").size() > 0)) {
			System.out.println("*** work delete symbol is not displaying *** ");
		} else if (!(AndroidLocators.findElements_With_Xpath("//*[@content-desc='Discard']").size() > 0)) {
			System.out.println("*** work delete symbol is not displaying *** ");
		} else {
			System.out.println("*** work delete symbol is displaying *** ");
			deleteWork();
		}
	}
	
	// work delete
	public static void deleteWork() {
		CommonUtils.handling_alert("discardWork", "discardWork", "in.spoors.effortplus:id/discardWork",
				"in.spoors.effortplus:id/discardWork", "//*[@content-desc='Discard']", "//*[@content-desc='Discard']");
		CommonUtils.handling_alert("message", "button1", "android:id/message", "android:id/button1",
				"//*[@text='Are you sure you want to delete? ']", "//*[@text='OK']");
		System.out.println("----- work is deleted successfully ----- ");
		try {
			CommonUtils.wait(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// validating work edit icon is displaying based on permissions
	public static void verify_work_Edit_Icon_status() {
		if (!(AndroidLocators.findElements_With_Id("editWork").size() > 0)) {
			System.out.println("*** work edit symbol is not displaying *** ");
		} else if (!(AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/editWork").size() > 0)) {
			System.out.println("*** work edit symbol is not displaying *** ");
		} else if (!(AndroidLocators.findElements_With_Xpath("//*[@content-desc='Edit']").size() > 0)) {
			System.out.println("*** work edit symbol is not displaying *** ");
		} else {
			System.out.println("*** work edit symbol is displaying *** ");
			clickOnEditWorkSymbol();
			goBackAndDiscardWork();
		}
	}
	
	// from work edit gotback and click on discard in alert
	public static void goBackAndDiscardWork() {
		CommonUtils.goBackward();
		CommonUtils.handling_alert("alertTitle", "button2", "android:id/alertTitle", "android:id/button2",
				"//*[@text='Do you want to save your changes?']", "//*[@text='DISCARD']");
		System.out.println("----- Work is successfully discarded ------ ");
		try {
			CommonUtils.wait(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// validating work add icon is displaying or not based on the permissions
	public static void workAddIcon() throws InterruptedException {
		if (!(AndroidLocators.findElements_With_Id("fab").size() > 0)) {
			System.out.println("*** work fab symbol is not displaying *** ");
		} else if (!(AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/fab").size() > 0)) {
			System.out.println("*** work fab symbol is not displaying *** ");
		} else {
			System.out.println("*** work fab symbol is displaying *** ");
		}
	}
	
	//click on visible work
	public static void clickOnWorkCard(String workName) throws InterruptedException {
		MobileActionGesture.scrollTospecifiedElement(workName);
	}
	
	// click on work displaying inside the workcard
	public static void workClick() {
		if (AndroidLocators.findElements_With_Id("titleTextView").size() > 0) {
			System.out.println("---- Work is visible ---- ");
			CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
		} else if (AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/titleTextView").size() > 0) {
			System.out.println("---- Work is visible ---- ");
			CommonUtils.getdriver().findElements(MobileBy
					.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/titleTextView\")"))
					.get(0).click();
		} else {
			System.out.println("---- Work is not visible ---- ");
		}
	}
	
	//validating work_to_form autocopy
	public static void workToFormAutoCopy(String workName) throws MalformedURLException, InterruptedException, ParseException {
		work_Creation();
		Work_advanceSettings.saveWork();
		goingToWorkScreen(workName);
		WorkAction();
	}
	
	// form to work autocopy
	public static void formToWorkAutoCopy(String workName)
			throws MalformedURLException, InterruptedException, ParseException {
		fill_Work_MandatoryFields();
		Work_advanceSettings.saveWork();
		goingToWorkScreen(workName);
		performSingleAction();
		clickOnEditWorkSymbol();
	}
	
	// verify all workfields filled from form submission or not
	public static void validating_form_to_work_autocopy_by_swiping() throws MalformedURLException {
		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// retrieving the list count
		int workFieldsCount = workFields.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		workFields.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workFields.isEmpty() && workFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workFields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// get the count of work fields
			workFieldsCount = workFields.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ workFields.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + workFields.get(i).getText());

				// if list matches with last element the loop will break
				if (workFields.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + workFields.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop
		
		MobileActionGesture.flingToBegining_Android();
	
		int count = 0;
		
		System.out.println("*** Actual count of work fields is *** : "+workFieldsCount);
		
		for (int j = 0; j < workFieldsCount; j++ ) {
			String workOriginalFields = workFields.get(j).getText();
			String workFieldsText = workFields.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);
			
			MobileActionGesture.scrollUsingText(workOriginalFields);
				if (!AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + workOriginalFields
						+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']")
						.isEmpty()) {
					count++;
					continue;
				} else {    //location
					System.out.println("------ Work fields" + workOriginalFields + " data is empty ------- ");
				}
				if (!AndroidLocators
						.findElements_With_Xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")
						.isEmpty()) {
					count++;
					continue;
				} else {      //datetime,starts,ends
					System.out.println("------ Work fields" + workOriginalFields + " is Empty");
				}

				if (!AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + workOriginalFields + "')]")
						.isEmpty()) {
					count++;
					continue;
				} else {   //text,currency,number,email,url...etc
					System.out.println("------ Work fields" + workOriginalFields + " data is Empty");
				}
				if (!AndroidLocators.findElements_With_Xpath(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner")
						.isEmpty()) {
					count++;
					continue;
				} else {   //dropdowns data like country,priority..etc
					System.out.println("------ Work fields" + workOriginalFields + " data is Empty");
				}
				if (!AndroidLocators.findElements_With_Xpath(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button")
						.isEmpty()) {
					count++;
					continue;
				} else {   //pickers (like form,picklist...etc)
					System.out.println("------ Work fields" + workOriginalFields + " data is Empty");
				}

				if (!AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + workFieldsText
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.EditText")
						.isEmpty()) {
					count++;
					continue;
				} else {    //rich text format
					System.out.println("------ Work fields" + workOriginalFields + " data is Empty");
				}
				if (!AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + workFieldsText
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button")
						.isEmpty()) {
					count++;
					continue; 
				} else {      //signature
					System.out.println("------ Work fields" + workOriginalFields + " data is Empty");
				}
			System.out.println(" ==== fields count data empty is ==== :" + count + "\n   j-->" + j);
		}
		if (count != workFieldsCount - 1)
			System.out.println("******* Workfields data is empty ******** ");
	}

	// click on add subtask button
	public static void clickOn_Add_SubTask_Button_to_create_subtask() {
		if (AndroidLocators.returnUsingId("addsubtask").isEnabled()) {
			CommonUtils.handling_alert("addsubtask", "addsubtask", "in.spoors.effortplus:id/addsubtask",
					"in.spoors.effortplus:id/addsubtask", "//*[@text='ADD TASK']", "//*[@text='ADD TASK']");
			// click on subtask alert
			subtask_Alert();
		} else if (AndroidLocators.resourceId("in.spoors.effortplus:id/addsubtask").isEnabled()) {
			CommonUtils.handling_alert("addsubtask", "addsubtask", "in.spoors.effortplus:id/addsubtask",
					"in.spoors.effortplus:id/addsubtask", "//*[@text='ADD TASK']", "//*[@text='ADD TASK']");
			// click on subtask alert
			subtask_Alert();
		} else if(AndroidLocators.xpath("//*[@text='ADD TASK']").isEnabled()) {
			CommonUtils.handling_alert("addsubtask", "addsubtask", "in.spoors.effortplus:id/addsubtask",
					"in.spoors.effortplus:id/addsubtask", "//*[@text='ADD TASK']", "//*[@text='ADD TASK']");
			// click on subtask alert
			subtask_Alert();
		} else {
			System.out.println("==== Sub Task button is not enable ==== ");
		}
	}
	
	// click on subtask alert
	public static void subtask_Alert() {
		if (AndroidLocators.findElements_With_Id("heading").size() > 0) {
			AndroidLocators.clickElementusingClassName("android.widget.Button");
		} else if (AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/heading").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[contains(@text,'ADD SUB WORK')]");
		} else if (AndroidLocators.findElements_With_Xpath("//*[@text='Add Sub Task']").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[contains(@text,'ADD SUB WORK')]");
		}
		 // wait until formsave element is displayed
		CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
	}
	
	// swipe to subtask and validate subtask is added or not
	public static void scrollToSubTaskWork() {
		MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/subTasksTV");
		if (AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[2]")
				.size() > 0) {
			String subtask = AndroidLocators.xpath(
					"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[1]")
					.getText();
			System.out.println("*** Subtask is added successfully subtask name is *** :"  +subtask);
		} else {
			System.out.println("=== Subtask is not added. ===");
		}
	}
	
	// verifying subtask
	public static void verify_Subtask() throws InterruptedException {
		MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/subTasksTV");
		if (AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[2]")
				.size() > 0) {
			String subtask = AndroidLocators.xpath(
					"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[1]")
					.getText();
			System.out.println("*** Subtask is added successfully, subtask name is *** :" + subtask);
			AndroidLocators.clickElementusingXPath(
					"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[2]");
			CommonUtils.interruptSyncAndLetmeWork();
		} else {
			System.out.println("=== Subtask is not added ===");
			
			// click on add subtask button
			clickOn_Add_SubTask_Button_to_create_subtask();
		}
	}
	
	
	// complete subtask then perofrm parent work
	public static void perform_Parent_WorkAction_When_Subtask_is_Completed()
			throws InterruptedException, MalformedURLException, ParseException {
		while (!CommonUtils.getdriver().findElement(MobileBy.id("button1")).isEnabled()) {

			System.out.println(" ----- Parent work action is disable perform subtask action thn peform parent work action ----- ");

			// scroll to subtask
			MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/subTasksTV");
			AndroidLocators.clickElementusingXPath(
					"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[2]");
			CommonUtils.interruptSyncAndLetmeWork();
			
			//click on action
			AndroidLocators.clickElementusingID("button1");

			// if pop is display to perform action then click and perform action else
			// directly perform action
			if (CommonUtils.getdriver().findElements(By.xpath("//*[@resource-id='in.spoors.effortplus:id/heading']"))
					.size() > 0) {
				AndroidLocators.clickElementusingClassName("android.widget.Button");
			} else {
				AndroidLocators.clickElementusingID("button1");
			}

			// if any alert display then handle and perform work action
			checkinToCus_PerformWorkAction();

			// wait until formsave element is displayed
			CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");

			// perform action until next action displayed
			Forms_basic.verifyFormPagesAndFill();
			Forms_basic.formSaveButton();

			// click on back button
			if(!AndroidLocators.xpath("subTasksTV").isDisplayed()) {
				CommonUtils.getdriver().navigate().back();
			}
				
			// wait until work action is displayed
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/button1']");
		} // while loop close
		
		//perform parent work action
		performSingleAction();
	}
	
	//perform parent work action when subtask is rejected
	public static void perform_ParentWorkAction_when_Subtask_isRejected() throws InterruptedException, MalformedURLException, ParseException {
		
		while (!CommonUtils.getdriver().findElement(MobileBy.id("button1")).isEnabled()) {

			System.out.println(
					" ----- Parent work action is disable perform subtask action thn peform parent work action ----- ");

			// scroll to subtask
			MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/subTasksTV");
			AndroidLocators.clickElementusingXPath(
					"//*[@resource-id='in.spoors.effortplus:id/subTasksLayout']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//android.widget.TextView[2]");
			CommonUtils.interruptSyncAndLetmeWork();

			// reject work
			workRejection();
			
			System.out.println("----- subtask is rejected successfully ----- ");

			// click on back button
			CommonUtils.getdriver().navigate().back();

			// wait until work action is displayed
			CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/button1']");
		} // while loop close
		
		//perform parent work action
		performSingleAction();
	}
	
	
	
	
}
