package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
		CommonUtils.wait(5);
		try {
			MobileActionGesture.scrollUsingText(workName);
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workName + "')]"))
					.size() > 0) {
				AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workName + "')]");
				CommonUtils.interruptSyncAndLetmeWork();
				CommonUtils.waitForElementVisibility("//*[contains(@text,'" + workName + "')]");
				workFab();
			}
		} catch (Exception e) {
			goToWorkPage(workName);
		}
	}

	// work search
	public static void workSearch(String workName) throws MalformedURLException, InterruptedException {
		AndroidLocators.clickElementusingID("action_search");
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(workName);
		AndroidLocators.pressEnterKeyInAndroid();
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.keyboardHide();
	}

	// verify work exist or not
	public static void verifyWorkExistOrNotInWorkScreen(String workName)
			throws MalformedURLException, InterruptedException, ParseException {
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']"))
					.isDisplayed()) {
				System.out.println("Work with this name exist!!");
				AndroidLocators.clickElementusingXPath("//*[@text='Work Name: " + workName + "']");
//				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']")).click();
				CommonUtils.interruptSyncAndLetmeWork();
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/button1']");
			}
		} catch (Exception e) {
			System.out.println("Going to create work!!");
			workFab();
//			workCreation(); 
			try {
				work_Creation();
			} catch (MalformedURLException | InterruptedException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			createWork();
			work_Creation();
			workSearch(generateWorkName);
			verifyWorkExistOrNotInWorkScreen(generateWorkName);
		}
	}

	// verify the created work
	public static void goingToWorkModifyScreen(String workName) throws InterruptedException, MalformedURLException, ParseException {
		Thread.sleep(1000);
		if (CommonUtils.getdriver().findElements(By.id("action_search")).size() > 0) {
			AndroidLocators.clickElementusingID("action_search");
			CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(generateWorkName);
			AndroidLocators.pressEnterKeyInAndroid();
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.keyboardHide();
			verifyWorkExistOrNotInWorkScreen(generateWorkName);
		} else {
			MobileActionGesture.scrollUsingText("Sign in for today?");
			String clickonwork = CommonUtils.getdriver()
					.findElementByXPath("//*[contains(@text,'" + workName + " WAITING FOR YOUR ACTION')]").getText();
			AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + clickonwork + "')]");
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
			AndroidLocators.clickElementusingID("action_search");
			CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(generateWorkName);
			AndroidLocators.pressEnterKeyInAndroid();
			CommonUtils.interruptSyncAndLetmeWork();
			CommonUtils.keyboardHide();
			verifyWorkExistOrNotInWorkScreen(generateWorkName);
		}
	}

	// clicking on '+' in work
	public static void workFab() throws InterruptedException {
		if (CommonUtils.getdriver().findElement(MobileBy.id("fab")).isDisplayed()) {
			AndroidLocators.clickElementusingID("fab");
		} else {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/fab");
		}
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
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
		Thread.sleep(500);
	}

	// while saving the work creation verify save or continue button is displaying
	// then perform accordingly
	public static void saveWork() throws InterruptedException {
		AndroidLocators.clickElementusingID("saveWork");
		CommonUtils.alertContentXpath();
		MobileElement saveButton = CommonUtils.getdriver().findElement(MobileBy.id("workSaveButton"));
		if (saveButton.isDisplayed()) {
			saveButton.click();
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
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/action_search']");
	}

	// worksave and startaction
	public static void save_And_StartAction() throws MalformedURLException, InterruptedException {
		AndroidLocators.clickElementusingID("saveWork");
		CommonUtils.alertContentXpath();
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

	// perform action
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
//						CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).click();
						CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
						// perform action until next action displayed
						Forms_basic.verifyFormPagesAndFill();
						Forms_basic.formSaveButton();
					} else {
						AndroidLocators.clickElementusingID("button1");
						CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
						// perform action until next action displayed
						Forms_basic.verifyFormPagesAndFill();
						Forms_basic.formSaveButton();
					}
				}
			}
//			CommonUtils.alertContentXpath();
//			if (CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).isDisplayed()) {
//				AndroidLocators.clickElementusingClassName("android.widget.Button");
////				CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).click();
//				CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
//				// perform action until next action displayed
//				Forms_basic.verifyFormPagesAndFill();
//				Forms_basic.formSaveButton();
//			}
		} while (CommonUtils.getdriver().findElementsById("button1").size() > 0);
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}

	// save workaction
	public static void workActionSaveButton() throws InterruptedException {
		AndroidLocators.clickElementusingID("saveForm");
		CommonUtils.alertContentXpath();
		AndroidLocators.clickElementusingID("formSaveButton");
		CommonUtils.implicitWait();
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

	// get workname
	public static void getWorkNameAndSearch() throws MalformedURLException, InterruptedException {
		generateWorkName = CommonUtils.getdriver()
				.findElement(By.xpath("(//android.widget.LinearLayout//android.widget.EditText)[1]")).getText();
		MobileActionGesture.scrollUsingText(generateWorkName);
		workSearch(generateWorkName);
		AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/titleTextView']");
	}

	// get workname
	public static void getWorkName() {
		generateWorkName = CommonUtils.getdriver()
				.findElement(By.xpath("(//android.widget.LinearLayout//android.widget.EditText)[1]")).getText();
		MobileActionGesture.scrollUsingText(generateWorkName);

	}

	// creating work with all fields
	public static List<MobileElement> work_Creation()
			throws MalformedURLException, InterruptedException, ParseException {
		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]");

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
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workFields.isEmpty() && workFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workFields.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]")));

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
		boolean isMultipicklist = false, isMultiselectdropdown = false, isyesNo = false, isSignature = false,
				isDate = false, isDateTime = false, isPriority = false;

		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = workFields.get(j).getText();
			String workFieldsText = workFields.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);

			switch (workFieldsText) {
			case "Work Name":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.sendKeys(generateWorkName);
//					AndroidLocators.sendInputusing_XPath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Description":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Ends":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
					CommonUtils.alertContentXpath();
					try {
						Forms_basic.dateScriptInForms(2);
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
					CommonUtils.wait(3);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					CommonUtils.alertContentXpath();
					workEndTime(2, 5);
					CommonUtils.wait(1);
				}
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(workFieldsText);
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
						CommonUtils.wait(5);
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
						CommonUtils.wait(5);
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
						CommonUtils.wait(5);
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
						CommonUtils.wait(5);
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
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
				}
				break;
			case "Employee-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
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
					}
					isPriority = true;
				}
				break;
			case "Address same as customer":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement address_Same_As_customer = CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(address_Same_As_customer);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(2).click();
					}
				}
				break;
			case "Phone Number(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Phone(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Street":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Area":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "City":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Landmark":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
				break;
			case "State":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Pincode":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Location-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A LOCATION");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				}
				Thread.sleep(500);
				break;
			case "Location":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A LOCATION");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				}
				Thread.sleep(500);
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Custom Entity":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("ENTITY");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"))
							.size() > 0) {
						MobileElement customEntity = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"));
						MobileActionGesture.tapByElement(customEntity);
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
						} else if (CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(0).click();
						} else {
							// write entity item creation method
							Forms_basic.createEntity();
						}
						Thread.sleep(500);
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
					System.out.println("Customer type is aready picked");
				}
				break;
			case "DateTime":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
								.click();
						CommonUtils.alertContentXpath();
						try {
							Forms_basic.dateScriptInForms(2);
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
						Thread.sleep(500);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
								+ workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
									+ workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.click();
							CommonUtils.alertContentXpath();
							workEndTime(2, 5);
							Thread.sleep(100);
						}
					} else {
						System.out.println("DateTime is already picked");
					}
				}
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);

					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
								.click();
						CommonUtils.alertContentXpath();
						workEndTime(2, 5);
						Thread.sleep(100);
					} else {
						System.out.println("Time already picked");
					}
				}
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.click();
						CommonUtils.alertContentXpath();
						try {
							Forms_basic.dateScriptInForms(2);
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
						Thread.sleep(500);
					} else {
						System.out.println("Date is already picked");
					}
				}
				break;
			case "Dropdown":
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
				}
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK LIST");
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
						Thread.sleep(500);
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
							Thread.sleep(500);
						} catch (Exception e) {
							System.out.println(e);
						}
					} else {
						System.out.println("Form is already picked");
					}
				}
				break;
			case "Email(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendEmailInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "URL(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
						Thread.sleep(500);
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
				}
				break;
			case "Multi Select Dropdown":
				if (!isMultiselectdropdown) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("PICK MULTI SELECT DROPDOWN(S)");
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
						if (pickValues.get(1).isDisplayed()) {
							MobileActionGesture.singleLongPress(pickValues.get(1));
						}
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						Thread.sleep(500);
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
					isyesNo = true;
				}
				break;
			case "Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(workFieldsText);					
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						work_Capturing_Signature(workFieldsText);
					} else {
						MobileActionGesture.scrollUsingText(workFieldsText);
						work_Capturing_Signature(workFieldsText);
					}
					isSignature = true;
				}
				break;
			} // switch statement close

		} // for loop close
		return workFields;
	}


	// capturing signature
	public static void work_Capturing_Signature(String fieldsText) throws MalformedURLException, InterruptedException {
		// deleting the existing signature if present
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/delete_button']"))
				.size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/delete_button']");
		}
		CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button");
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
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
			CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
			Thread.sleep(1000);
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
	public static void searchAndClickOnWork(String workName) throws MalformedURLException, InterruptedException, ParseException {
		workSearch(workName);
		verifyWorkExistOrNotInWorkScreen(workName);
	}

	// click on edit work
	public static void clickOnEditWorkSymbol(String workName) {
		CommonUtils.handling_alert("(editWork", "editWork", "in.spoors.effortplus:id/editWork",
				"in.spoors.effortplus:id/editWork", "//*[@content-desc='Edit']", "//*[@content-desc='Edit']");
		CommonUtils.waitForElementVisibility("//*[@text='Edit " + workName + "']");
	}

	// modifying the work
	public static void workModification() throws MalformedURLException, InterruptedException, ParseException {
		// Declaring the workLabelElements list
		List<MobileElement> workLabelElements = AndroidLocators.findElements_With_Xpath(
				"//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']");

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
				"//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']"));

		// Store work last element into 'workLastElement string'
		workLastElement = workLabelElements.get(workLabelElements.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workLabelElements.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workLabelElements.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']"));

		// get the count of work fields present in the first screen
		workFieldsCount = workLabelElements.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workLabelElements.isEmpty() && workLabelElements != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workLabelElements.addAll(CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//*[@resource-id='in.spoors.effortplus:id/label_for_view']")));

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
						Forms_basic.dateScriptInForms(2);
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
					CommonUtils.wait(3);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					CommonUtils.alertContentXpath();
					workEndTime(2, 5);
					CommonUtils.wait(1);
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
						CommonUtils.wait(5);
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
						CommonUtils.wait(5);
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
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
				}
				break;
			case "Employee-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				CommonUtils.getTextAndScrollToElement(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
				}
				break;
			case "Priority":
				if (!isPriority) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + workFieldsText
							+ "')]/android.widget.Spinner/*[@resource-id='android:id/text1']");
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
						}
						Thread.sleep(500);
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
							Forms_basic.dateScriptInForms(2);
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
						Thread.sleep(500);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
								+ workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
									+ workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.click();
							CommonUtils.alertContentXpath();
							workEndTime(2, 5);
							Thread.sleep(100);
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
						workEndTime(2, 5);
						Thread.sleep(100);
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
							Forms_basic.dateScriptInForms(2);
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
						Thread.sleep(500);
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
						Thread.sleep(500);
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
							Thread.sleep(500);
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
//						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
//						List<MobileElement> pickMultiPickList = CommonUtils.getdriver()
//								.findElements(MobileBy.className("android.widget.CheckBox"));
//						if (pickMultiPickList.get(0).isDisplayed()) {
//							MobileActionGesture.singleLongPress(pickMultiPickList.get(0));
//						}
//						if (pickMultiPickList.get(0).isDisplayed()) {
//							MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
//						}
//						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						Thread.sleep(500);
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
						Thread.sleep(500);
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
					isyesNo = true;
				}
				break;
			case "Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						work_Capturing_Signature(workFieldsText);
					} else {
						MobileActionGesture.scrollUsingText(workFieldsText);
						work_Capturing_Signature(workFieldsText);
					}
					isSignature = true;
				}
				break;
			}
		}
	}
	
	
}
