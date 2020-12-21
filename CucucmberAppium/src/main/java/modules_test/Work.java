package utils;

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

import Actions.CustomerPageActions;
import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class Work {
	private static String generateWorkName;

	// go to work page from home fab icon '+' then swipe and click on the specified work
	public static void goToWorkPage(String workName) throws MalformedURLException {
		CommonUtils.homeFabClick();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work']")).click();
		//swipe and click on the specified work
		MobileActionGesture.scrollTospecifiedElement("" + workName + "");
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
		try {
			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
							+ workName + "\").instance(0))"));
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workName + "')]"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workName + "')]")).click();
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
		CommonUtils.getdriver().findElement(MobileBy.id("action_search")).click();
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(workName);
		CommonUtils.pressEnterKeyInAndroid();
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.keyboardHide();
	}

	// verify work exist or not
	public static void verifyWorkExistOrNot(String workName) throws MalformedURLException, InterruptedException {
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']"))
					.isDisplayed()) {
				System.out.println("Work with this name exist!!");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']")).click();
				CommonUtils.interruptSyncAndLetmeWork();
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/button1']");
			}
		} catch (Exception e) {
			System.out.println("Going to create work!!");
			workFab();
//			workCreation();
			createWork();
			workSearch(generateWorkName);
			verifyWorkExistOrNot(generateWorkName);
		}
	}

	// clicking on '+' in work
	public static String workFab() throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
		return "pass";
	}

	// work creation
	public static void createWork() throws MalformedURLException, InterruptedException {
		// insertig working name randon name using 'RandomStringUtils' library
		generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//android.widget.EditText[contains(@text,'Work Name')]"))
				.sendKeys(generateWorkName);
		// click on end time
		CommonUtils.getdriver().findElement(MobileBy.xpath(
				"//*[contains(@text,'Ends')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']"))
				.click();
		CommonUtils.alertContentXpath();
		workEndTime(1, 5);
	}

	// work end time added as given hours extra because End time should be greater
	public static void workEndTime(int timeCount, int minsCount) throws MalformedURLException, InterruptedException {
		// retrieving time
		Date date = new Date();
		//time 12hrs format 
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
		CommonUtils.getdriver().findElement(MobileBy.id("saveWork")).click();
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
	
	//worksave and startaction
	public static void save_And_StartAction() throws MalformedURLException, InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("saveWork")).click();
		CommonUtils.alertContentXpath();
		if(CommonUtils.getdriver().findElement(By.id("workSaveAndStartActionButton")).isDisplayed()) {
			CommonUtils.getdriver().findElement(By.id("workSaveAndStartActionButton")).click();
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

	//perform action
	public static void WorkAction() throws InterruptedException, MalformedURLException, ParseException {
		do {
			if (CommonUtils.getdriver().findElements(MobileBy.id("button1")).size() > 0) {
				if (CommonUtils.getdriver().findElement(MobileBy.id("button1")).isEnabled()) {
					CommonUtils.getdriver().findElement(MobileBy.id("button1")).click();
				}
			}
			CommonUtils.alertContentXpath();
			if (CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
				// perform action until next action displayed
				Forms.verifyFormPagesAndFill();
//				workActionSaveButton();
			}
		} while (CommonUtils.getdriver().findElementsById("button1").size() > 0);
	}

	// save workaction
	public static void workActionSaveButton() throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).click();
		CommonUtils.alertContentXpath();
		CommonUtils.getdriver().findElement(MobileBy.id("formSaveButton")).click();
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
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Collapse']")).click();
			CommonUtils.openMenu();
			CommonUtils.clickHomeInMenubar();
		} else {
			System.out.println("collapse symbol not found");
		}
	}

	public static String workCreation() throws MalformedURLException, InterruptedException, ParseException {
		//Declaring the workInputElements list
		List<MobileElement> workInputElements = CommonUtils.getdriver()
						.findElements(MobileBy.className("android.widget.EditText"));
		//Declaring the workLabelElements list
		List<MobileElement> workLabelElements = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']"));
		
		//merging the both list
		List<MobileElement> newList = workLabelElements;
		newList.addAll(workInputElements);
		//retrieving the list count 
		int workFieldsCount = newList.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);
		//clear the elements from list
		newList.clear();
		workLabelElements.clear();
		workInputElements.clear();

		String workLastElement = null;
		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workInputElements
				.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		workLabelElements.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']")));
		// merge the list
       //  newList = ListUtils.union(workLabelElements, workInputElements);
		workLabelElements.addAll(workInputElements);
		newList = workLabelElements;
		// get work last element
		workLastElement = newList.get(newList.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);
		// remove the elements from the list
		newList.clear();
		workLabelElements.clear();
		workInputElements.clear();
		// scroll to top
		MobileActionGesture.flingToBegining_Android();
		
		
		// adding the work fields present in the first screen
		workInputElements
				.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		workLabelElements.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']")));
		// merge the list
		newList = workLabelElements;
		newList.addAll(workInputElements);
		// get the count of work fields present in the first screen
		workFieldsCount = newList.size();
		System.out.println(" ---- Before swiping the device screen fields count is ---- : " + workFieldsCount);
		
		// swipe and retrieve the work fields until the last element found
		while (!newList.isEmpty() && newList != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workInputElements
					.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
			workLabelElements.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.TextView[@resource-id='in.spoors.effortplus:id/label_for_view']")));
			// merge the list
//			newList = ListUtils.union(workLabelElements, workInputElements);
			newList = workLabelElements;
			newList.addAll(workInputElements);
			
			// get the count of work fields
			workFieldsCount = newList.size();
			System.out.println(" .... After swiping the device screen fields count is .... : " + workFieldsCount);
			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {
				System.out.println("***** Print work fields elements text ***** : "
						+ newList.get(workFieldsCount - (i + 1)).getText());
				System.out.println("===== Work fields text ===== : " + newList.get(i).getText());
				if (newList.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + newList.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		}
		
		MobileActionGesture.flingToBegining_Android();
		boolean isMultipicklist = false, isMultiselectdropdown = false, isyesNo = false, isSignature = false, isPriority = false;

		//providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = newList.get(j).getText();
			String workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);

			switch (workFieldsText) {

			case "Work Name":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String workName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.sendKeys(workName);
				}
				break;
			case "Description":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String workDescription = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.sendKeys(workDescription);
				}
				break;
			case "Ends":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']"))
							.click();
					CommonUtils.alertContentXpath();
					try {
						Forms.dateScriptInForms(2);
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
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']"))
							.click();
					CommonUtils.alertContentXpath();
					workEndTime(2, 5);
					CommonUtils.wait(1);
				}
				break;
			case "Customer":
			case "Customer-SYS":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
						.size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
								.click();
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							CommonUtils.getdriver()
									.findElement(MobileBy
											.xpath("//*[@text='" + CustomerPageActions.randomstringCusName + "']"))
									.click();
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
								.click();
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							CommonUtils.getdriver()
									.findElement(MobileBy
											.xpath("//*[@text='" + CustomerPageActions.randomstringCusName + "']"))
									.click();
						}
						CommonUtils.wait(5);
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Employee":
			case "Employee-SYS":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button"))
							.click();
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
				}
				break;
			case "Priority":
				if(!isPriority) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						MobileActionGesture.scrollUsingText("Pick a value");
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
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement address_Same_As_customer = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(address_Same_As_customer);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(2).click();
					}
				}
				break;
			case "Phone Number(Optional)":
			case "Phone(Optional)":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String phoneNumber = RandomStringUtils.randomNumeric(10);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.sendKeys(phoneNumber);
				}
				break;
			case "Street":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String street = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(street);
				}
				break;
			case "Area":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String area = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(area);
				}
				break;
			case "City":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String city = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(city);
				}
				break;
			case "Landmark":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String landmark = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(landmark);
				}
				break;
			case "Country":
			case "Country-SYS":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a country')]"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a country')]"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				break;
			case "State":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String state = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(state);
				}
				break;
			case "Pincode":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String pincode = RandomStringUtils.randomNumeric(6);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(pincode);
				}
				break;
			case "Location":
			case "Location-SYS":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']"))
							.click();
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
					Thread.sleep(500);
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']"))
							.click();
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
					Thread.sleep(500);
				}
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String text = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(text);
				}
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String number = RandomStringUtils.randomNumeric(5);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(number);
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					String currency = RandomStringUtils.randomNumeric(5);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(currency);
				}
				break;
			case "Custom Entity":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
							Forms.createEntity();
						}
						Thread.sleep(500);
					} else {
						System.out.println("Custom entity is already picked");
					}
				}
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
							Forms.dateScriptInForms(2);
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + workFieldsText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + workFieldsText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
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
							Forms.dateScriptInForms(2);
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
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
								CommonUtils.waitForElementVisibility(
										"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
								if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
									CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
									Forms.verifyFormPagesAndFill();
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
					RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
							.build();
					String email = emailGenerator.generate(5);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(email + "@gmail.com");
				}
				break;
			case "URL(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					RandomStringGenerator urlGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
							.build();
					String url = urlGenerator.generate(5);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys("www." + url + ".com");
				}
				break;
			case "Multi Pick List":
				if (!isMultipicklist) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
						Thread.sleep(500);
					}
					isMultipicklist = true;
				}
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
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
						Forms.capturing_Signature(workFieldsText);
					} else {
						MobileActionGesture.scrollUsingText(workFieldsText);
						Forms.capturing_Signature(workFieldsText);
					}
					isSignature = true;
				}
				break;
			} //switch statement close
			
		}  //for loop close
		return workLastElement;
	}
}
