package utils;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import Actions.CustomerPageActions;
import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class Forms {
	private static final List<MobileElement> MobileElements = null;
	static String formName = "Form-2";

	// go to form page from home page
	public static void goToFormPage(String formName) throws MalformedURLException {
		CommonUtils.homeFabClick();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Form']")).click();
//		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/select_dialog_listview']");
		MobileActionGesture.scrollTospecifiedElement("" + formName + "");
		CommonUtils.waitForElementVisibility("//*[@text='" + formName + "']");
	}

	public static void verifyForminHomePage(String form) throws MalformedURLException {
//		try {
//			MobileActionGesture.scrollTospecifiedElement("" + form + "");
//			MobileElement homeFab = CommonUtils.getdriver().findElementById("fab");
//			MobileActionGesture.tapByElement(homeFab);
//			CommonUtils.interruptSyncAndLetmeWork();
//		}catch(Exception e) {
//			goToFormPage(form);
//		}
		goToFormPage(form);
	}

	// click on form save button
	public static void formSaveButton() throws InterruptedException {

		CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/saveForm\")"))
				.click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
				.isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy
					.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
					.click();
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator(
						"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")"))
				.isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")")).click();
		}
		CommonUtils.interruptSyncAndLetmeWork();
		// verify if popup with i understand message is display then click on it
		try {
			if (CommonUtils.getdriver()
					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
					.isDisplayed()) {
				CommonUtils.getdriver()
						.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
						.click();
				System.out.println("I understand message is displayed");
			}
		} catch (Exception e) {
			System.out.println("I understand message is not displayed");
		}
		CommonUtils.waitForElementVisibility("//*[@content-desc='Edit']");
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
				System.out.println("Section fields form not found");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	//click on add if section exist
	public static void verifySectionToClickAdd() throws InterruptedException, MalformedURLException {
		// check if section fields exist by scrolling and then click on it
		try {
			CommonUtils.getdriver().findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()).scrollIntoView(text(\"ADD\"));").click();
		} catch (Exception e) {
			System.out.println("Section with Add button is not found");
		}

	}

	// automating time in 12 hrs format(clicking on hours and AM,PM)
	public static void TimeScriptInForms(int hoursCount, int minsCount) throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();

		// retrieving time
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("H:m a");
		String stringDate = DateFor.format(date);
		System.out.println("Before adding hours time is : " + stringDate);

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
		String getMins = splitAMPM[0];
		String getAmPm = splitAMPM[1];
		System.out.println("CurrMinutes :" + getMins);
		System.out.println("AmPm :" + getAmPm);

		// adding extra hours to the current hours and splitting into hrs and mins
		date = DateUtils.addHours(date, hoursCount);
		System.out.println("After adding hours time is : " + DateFor.format(date));
		// splitting extended hours
		String[] splitValueExtendedHrs = DateFor.format(date).split(":");
		// retrieving extended hours
		String extendedHours = splitValueExtendedHrs[0];
		System.out.println("addedHours: " + extendedHours);

		// adding minutes
		date = DateUtils.addMinutes(date, minsCount);

		date = DateUtils.truncate(date, Calendar.MINUTE);
		// splitting extended Minutes
		String[] splitMins = DateFor.format(date).split(":");
		// retrieving minutes
		String addedMins = splitMins[1];
		System.out.println("After adding Mins: " + addedMins);

		splitAMPM = addedMins.split(" ");
		String plusMins = splitAMPM[0];
		System.out.println("Minutes : " + plusMins);
		// retrieving mins and AM PM
		String getAmPm1 = splitAMPM[1];
		System.out.println("AmPm :" + getAmPm1);

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
//		MobileElement currMins=CommonUtils.getdriver().findElementByXPath("//*[@content-desc='"+getMins+"']");
//		MobileElement pluMins=CommonUtils.getdriver().findElementByXPath("//*[@content-desc='"+plusMins+"']");
//		//using longpress method moving element from source to destination(i.e current hr to added hr)
//		MobileActionGesture.Movetoelement(currMins, pluMins);

		CommonUtils.getdriver().findElementByXPath("//*[@text='OK']").click();
	}

	// automating date
	public static void dateScriptInForms(int addedDate) throws MalformedURLException, InterruptedException {
		// get the date
		Date date = new Date();
		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

		date = DateUtils.addDays(date, addedDate);
		
		// conversion of date
		String ExtendedDate = DateFor.format(date);
		// printing date
		System.out.println("Date Format : " + ExtendedDate);
		
		// get xpath of Date and pass variable of added date and verify date is available
		// or not
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']")).click();
		} else {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Next month']")).click();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + ExtendedDate + "']")).click();
		}
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
	}
	

	
	// form fill by verifying pages
	public static void verifyFormPagesAndFill() throws MalformedURLException, InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));
		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println("pagination exists");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				pagination.get(i).click();
				verifySectionToClickAdd();
				fill_Form_With_Pagination(i);
			}
		} else {
			System.out.println("pagination not exists");
			Forms.verifySectionToClickAdd();
			formfill();
		}
		Forms.formSaveButton();
	}


	// form fill with pagination
	public static void fill_Form_With_Pagination(int i) throws MalformedURLException, InterruptedException {
		int j = i + 1;
		List<MobileElement> formFields1 = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();
		// get last element text
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int k = 0; k < countOfFields; k++) {
				if (formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		boolean isDate = false, isCustomerType = false, isText = false, isURL = false, isEmail = false,
				isTerritory = false, isCountry = false, isEmployee = false, isDateTime = false, isTime = false,
				isPickList = false, isMultiPickList = false, isMultiSelectDropdown = false, isLocation = false,
				isPhone = false, isCurrency = false, isNumber = false, isDropdown = false, isYesNo = false,
				isCustomer = false, isMultiPickCustomer = false, isForm = false, isCustomEntity = false,
				isSignature = false;

		// iterate and fill the form
		for (int k = 0; k < countOfFields; k++) {
			String OriginalfieldsText = formFields1.get(k).getText();
			String fieldsText = formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Before removeing special character: " + OriginalfieldsText + "\nafter removing regexp: "
					+ fieldsText);

			switch (fieldsText) {
			case "Date":
			case "G-Date":
			case "S-Date":
				if (!isDate) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]")).click();
						CommonUtils.alertContentXpath();
						Forms.dateScriptInForms(2);
						Thread.sleep(500);
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
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
						MobileActionGesture.scrollUsingText(fieldsText);
						RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String text = textGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(text);
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
						MobileActionGesture.scrollUsingText(fieldsText);
						RandomStringGenerator urlGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String url = urlGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys("www." + url + ".com");
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
						MobileActionGesture.scrollUsingText(fieldsText);
						RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String email = emailGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(email + "@gmail.com");
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement terriory = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(terriory);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
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
						MobileElement country = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(country);
						MobileActionGesture.scrollTospecifiedElement("Australia");
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
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
						if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
						}
						Thread.sleep(500);	
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.click();
						CommonUtils.alertContentXpath();
						Forms.dateScriptInForms(2);
						Thread.sleep(500);
					}
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[@text='" + fieldsText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + fieldsText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.click();
						CommonUtils.alertContentXpath();
						Forms.TimeScriptInForms(2, 5);
						Thread.sleep(500);
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
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]")).click();
						Forms.TimeScriptInForms(2, 1);
						Thread.sleep(500);
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
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
								.click();
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
						} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
						}
						Thread.sleep(500);
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK A LOCATION')]"))
								.click();
						Thread.sleep(5000);
						CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
						Thread.sleep(500);
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
						String phoneNum = RandomStringUtils.randomNumeric(10);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(phoneNum);
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
						MobileActionGesture.scrollUsingText(fieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("// *[@text='" + fieldsText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("// *[@text='" + fieldsText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton"))
									.click();
							CommonUtils.waitForElementVisibility("//*[@content-desc='Done']");
							MobileElement currencyClick = CommonUtils.getdriver()
									.findElement(MobileBy.id("incrementButton"));
							MobileActionGesture.multiTouchByElement(currencyClick);
							CommonUtils.getdriver().findElement(MobileBy.id("done")).click();
							Thread.sleep(100);
						} else {
							String currency1 = RandomStringUtils.randomNumeric(5);
							CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
									.sendKeys(currency1);
						}
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
						MobileActionGesture.scrollUsingText(fieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("// *[@text='" + fieldsText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("// *[@text='" + fieldsText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton"))
									.click();
							CommonUtils.waitForElementVisibility("//*[@content-desc='Done']");
							MobileElement currencyClick = CommonUtils.getdriver()
									.findElement(MobileBy.id("incrementButton"));
							MobileActionGesture.multiTouchByElement(currencyClick);
							CommonUtils.getdriver().findElement(MobileBy.id("done")).click();
							Thread.sleep(100);
						} else {
							String number1 = RandomStringUtils.randomNumeric(5);
							CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
									.sendKeys(number1);
						}
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
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
						MobileElement yesno = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
								+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(yesno);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
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
						if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).isEnabled()) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).click();
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
							Thread.sleep(500);
							System.out.println("Now customer is picked");
						} else {
							System.out.println("Customer is already selected!!");
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
						MobileElement customEntity = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
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
						MobileElement form = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
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
									verifyFormPagesAndFill();
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
						MobileElement signature = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
						MobileActionGesture.tapByElement(signature);
						MediaPermission.mediaPermission();
						CommonUtils.waitForElementVisibility("//*[@text='Signature']");
						MobileElement signatureCapture = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
						MobileActionGesture.singleLongPress(signatureCapture);
						CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
						Thread.sleep(500);
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement multiPickCustomer = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
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
						Thread.sleep(500);
					}
					isMultiPickCustomer = true;
				}
			default:
				break;
			}
		}
	}
	
	// form fill with out pagination
	public static void formfill() throws InterruptedException, MalformedURLException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();
		// get last element text
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();
	

		// add the elements to list present in first screen
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);
		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				if (formFields1.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		boolean isDate = false, isCustomerType = false, isText = false, isURL = false, isEmail = false,
				isTerritory = false, isCountry = false, isEmployee = false, isDateTime = false, isTime = false,
				isPickList = false, isMultiPickList = false, isMultiSelectDropdown = false, isLocation = false,
				isPhone = false, isCurrency = false, isNumber = false, isDropdown = false, isYesNo = false,
				isCustomer = false, isMultiPickCustomer = false, isForm = false, isCustomEntity = false,
				isSignature = false;

		// iterate and fill the form
		for (int i = 0; i < countOfFields; i++) {
			String OriginalfieldsText = formFields1.get(i).getText();
			String fieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Before removeing special character: " + OriginalfieldsText + "\nafter removing regexp: "
					+ fieldsText);

			switch (fieldsText) {
			case "Date":
			case "G-Date":
			case "S-Date":
				if (!isDate) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms.dateScriptInForms(2);
							Thread.sleep(500);
						} else {
							System.out.println("Date is already picked");
						}
						isDate = true;
					}
				}
				break;
			case "Customer Type":
			case "G-Customer Type":
			case "S-Customer Type:":
				if (!isCustomerType) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement cusType = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
							MobileActionGesture.singleLongPress(cusType);
							if (CommonUtils.getdriver()
									.findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
								CommonUtils.getdriver()
										.findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
										.click();
							}
						} else {
							System.out.println("Customer type is already selected");
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
						MobileActionGesture.scrollUsingText(fieldsText);
						RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String text = textGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(text);
						isText = true;
					}
				}
				break;
			case "URL":
			case "G-URL":
			case "S-URL":
				if (!isURL) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						RandomStringGenerator urlGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String url = urlGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys("www." + url + ".com");
						isURL = true;
					}
				}
				break;
			case "Email":
			case "G-Email":
			case "S-Email":
				if (!isEmail) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String email = emailGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(email + "@gmail.com");
						isEmail = true;
					}
				}
				break;
			case "Territory":
			case "G-Territory":
			case "S-Territory":
				if (!isTerritory) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement terriory = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
							MobileActionGesture.singleLongPress(terriory);
							if (CommonUtils.getdriver()
									.findElements(MobileBy.className("android.widget.CheckedTextView")).size() > 0) {
								CommonUtils.getdriver()
										.findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
										.click();
							}
						} else {
							System.out.println("Territory is already selected");
						}
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement country = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
							MobileActionGesture.singleLongPress(country);
							MobileActionGesture.scrollTospecifiedElement("Australia");
						} else {
							System.out.println("Country is already selected");
						}
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
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::android.widget.Button")).click();
						if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
						}
						Thread.sleep(500);
						isEmployee = true;
					}
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms.dateScriptInForms(2);
							Thread.sleep(500);
							if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + fieldsText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.size() > 0) {
								CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + fieldsText
										+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
										.click();
								CommonUtils.alertContentXpath();
								Forms.TimeScriptInForms(2, 5);
								Thread.sleep(500);
							}
						} else {
							System.out.println("DateTime is picked");
						}
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
						MobileActionGesture.scrollUsingText(fieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
									.click();
							Forms.TimeScriptInForms(2, 1);
							Thread.sleep(500);
						} else {
							System.out.println("Time already picked");
						}
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK LIST')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK LIST')]"))
									.click();
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0)
									.isDisplayed()) {
								CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1)
									.isDisplayed()) {
								CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
							}
							Thread.sleep(500);
						} else {
							System.out.println("Pick List is already picked");
						}
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
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
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
						isMultiPickList = true;
					}
				}
				break;
			case "Multi Select Dropdown":
			case "G-Multi Select Dropdown":
			case "S-Multi Select Dropdown":
				if (!isMultiSelectDropdown) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
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
						isMultiSelectDropdown = true;
					}
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
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
									.click();
							Thread.sleep(5000);
							CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']"))
									.click();
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']"))
									.click();
							Thread.sleep(500);
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
						String phoneNum = RandomStringUtils.randomNumeric(10);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(phoneNum);
						isPhone = true;
					}
				}
				break;
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				if (!isCurrency) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						String currency1 = RandomStringUtils.randomNumeric(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(currency1);
						isCurrency = true;
					}
				}
				break;
			case "Number":
			case "G-Number":
			case "S-Number":
				if (!isNumber) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						String number1 = RandomStringUtils.randomNumeric(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(number1);
						isNumber = true;
					}
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement dropdown = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
							MobileActionGesture.singleLongPress(dropdown);
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						} else {
							System.out.println("Dropdown is already selected");
						}
						isDropdown = true;
					}
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement yesno = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
							MobileActionGesture.singleLongPress(yesno);
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						} else {
							System.out.println("YesNo is already selected");
						}
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
						if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).isEnabled()) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).click();
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
							System.out.println("Now customer is picked");
						} else {
							System.out.println("Customer is already selected!!");
						}
						Thread.sleep(500);
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOM ENTITY')]"))
								.size() > 0) {
							MobileElement customEntity = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOM ENTITY')]"));
							MobileActionGesture.tapByElement(customEntity);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card"))
									.size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(0).click();
							} else {
								// write entity item creation method
								Forms.createEntity();
							}
							Thread.sleep(500);
						} else {
							System.out.println("Custom entity is not present");
						}
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
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK FORM')]"))
								.size() > 0) {
							MobileElement form = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK FORM')]"));
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
								Thread.sleep(500);
							} catch (Exception e) {
								System.out.println(e);
							}
						} else {
							System.out.println("Form is already picked");
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
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"))
								.size() > 0) {
							MobileElement signature = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
											+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
							MobileActionGesture.tapByElement(signature);
							MediaPermission.mediaPermission();
							CommonUtils.waitForElementVisibility("//*[@text='Signature']");
							MobileElement signatureCapture = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
							MobileActionGesture.singleLongPress(signatureCapture);
							CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
							Thread.sleep(500);
						} else {
							System.out.println("signature is not present");
						}
						isSignature = true;
						// CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.CAMERA));
					}
				}
				break;
			case "Multi Pick Customer":
			case "G-Multi Pick Customer":
			case "S-Multi Pick Customer":
				if (!isMultiPickCustomer) {
					MobileActionGesture.scrollUsingText(fieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(fieldsText);
						MobileElement multiPickCustomer = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button"));
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
						Thread.sleep(500);
						isMultiPickCustomer = true;
					}
				}
				break;
			default:
				break;
			}
		}
	}
	
	// create entity item
	public static void createEntity() throws MalformedURLException, InterruptedException {
		MobileActionGesture.scrollTospecifiedElement("Abc");
		CommonUtils.getdriver().findElement(MobileBy.id("customEntitiesFab")).click();
		CommonUtils.waitForElementVisibility("//*[contains(@text,'Create')]");
		// insert entity data
		fillEntity();
		CustomerPageActions.saveMethod();
	}

	
	// fill entity
	public static void fillEntity() throws MalformedURLException, InterruptedException {
		//label list fields 
		List<MobileElement> labelViews = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"));
//		labelViews.clear();
		//text list fields
		List<MobileElement> inputTexts = CommonUtils.getdriver()
				.findElements(MobileBy.className("android.widget.EditText"));
//		inputTexts.clear();
		//merging two lists
		labelViews.addAll(inputTexts);
		labelViews.clear();
		int countOfAllFields = labelViews.size();
		System.out.println("countOfAllFields " + countOfAllFields);
		
		// get last element text to stop continuous scrolling from get list of elements
		String lastEntityTextElement = null;
		String inputTextlastElement = null;
		// scroll to bottom and add list fields
		MobileActionGesture.flingVerticalToBottom_Android();
		// add list fields
		labelViews.addAll(CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
		// get last element in this list "lastEntityTextElement"
		lastEntityTextElement = labelViews.get(labelViews.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get Last Entity Label element text: " + lastEntityTextElement);
		labelViews.clear();
		// add list fields
		inputTexts.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		// get last element in this list and store to "lastEntityTextElement"
		inputTextlastElement = inputTexts.get(inputTexts.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get Last Entity Input element text: " + inputTextlastElement);
		inputTexts.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();
		
		//adding both list fields of first screen of mobile
		labelViews.addAll(CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
		inputTexts.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		//merging both list(labelView, inputText) 
		labelViews.addAll(inputTexts);
		countOfAllFields = labelViews.size();
		System.out.println("before swiping entity fields inputText count: " + countOfAllFields);

		// scroll and add list fields(labelView, inputText) till last text element find
		while (!labelViews.isEmpty() && labelViews != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			labelViews.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
			inputTexts.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
			//merging both list(labelView, inputText)
//			labelViews.clear();
			labelViews.addAll(inputTexts);
			countOfAllFields = labelViews.size();
			System.out.println("After swiping entity fields count: " + countOfAllFields);
			//verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {
				if (labelViews.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastEntityTextElement)) {
					flag = true;
				} 
					else if (inputTexts.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "")
						.equals(inputTextlastElement)) {
					flag = true;
				}
			} 
			//exit while loop
			if (flag == true)
				break;
		}		
		//iterate the list fields
		for (int i = 0; i < countOfAllFields; i++) {
			String allFieldsTextOriginal = labelViews.get(i).getText();
			String allFieldsText = labelViews.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Before removing specia character: " + allFieldsTextOriginal
					+ "\nAfter removing the special chararcter: " + allFieldsText);

			switch (allFieldsText) {
			case "Location":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.id("pick_location_button")).click();
				Thread.sleep(5000);
				CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				Thread.sleep(500);
				break;
			case "Last Activity Time":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@text='"+allFieldsText+"']/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
						.click();
				CommonUtils.alertContentXpath();
				Forms.dateScriptInForms(2);
				Thread.sleep(500);
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				if (CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"))
						.isEnabled()) {
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"))
							.click();
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
				Thread.sleep(500);
				break;
			case "Employee":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"))
						.click();
				if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
				}
				Thread.sleep(500);
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath(
								"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"))
						.click();
				TimeScriptInForms(2, 1);
				Thread.sleep(500);
				break;
			case "DateTime":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
						.click();
				CommonUtils.alertContentXpath();
				Forms.dateScriptInForms(2);
				Thread.sleep(500);
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath(
								"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"))
						.click();
				CommonUtils.alertContentXpath();
				Forms.dateScriptInForms(2);
				Thread.sleep(500);
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement customerType = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(customerType);
				if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
						.size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
							.click();
				}
				break;
			case "Form":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement form = CommonUtils.getdriver().findElement(MobileBy
						.xpath("//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"));
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
				Thread.sleep(500);
				break;
			case "Signature":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement signature = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"));
				MobileActionGesture.tapByElement(signature);
				MediaPermission.mediaPermission();
				CommonUtils.waitForElementVisibility("//*[@text='CAPTURE']");
				MobileElement signatureCapture = CommonUtils.getdriver().findElement(MobileBy.id("saveButton"));
				MobileActionGesture.singleLongPress(signatureCapture);
				CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
				Thread.sleep(500);
				break;
			case "Country":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(country);
				MobileActionGesture.scrollTospecifiedElement("India");
				break;
			case "YesNo":
			case "YesOrNo":
			case "Yes/No":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement yesno = CommonUtils.getdriver().findElement(MobileBy
						.xpath("//*[@id='label_for_view' and contains(@text,'"+allFieldsText+")]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(yesno);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement territory = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(territory);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement dropdown = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(dropdown);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Multi Select Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multidropdown = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"));
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
				Thread.sleep(500);
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and starts-with(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"))
						.click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
					CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
				} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
					CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
				}
				break;
			case "Multi Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multipicklist = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and starts-with(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"));
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
				break;
			case "Multi Pick Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multiPickCustomer = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and starts-with(@text,'"+allFieldsText+"')]/parent::*/android.widget.Button"));
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

				Thread.sleep(500);
				break;
			case "Entity Name":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String EntityName = RandomStringUtils.randomAlphabetic(6);
				CommonUtils.getdriver()
						.findElement(MobileBy
								.xpath("//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys(EntityName);
				break;
			case "Last Activity Name":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String LastActivtyName = RandomStringUtils.randomAlphabetic(6);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys(LastActivtyName);
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String Text = RandomStringUtils.randomAlphabetic(5);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys(Text);
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String currency = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys(currency);
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String number = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys(number);
				break;
			case "Phone":
			case "Phone Number":
			case "PhoneOptional":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String phonenumber = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and starts-with(@text,'"+allFieldsText+"')]"))
						.sendKeys(phonenumber);
				break;
			case "URL":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String url = RandomStringUtils.randomAlphabetic(5);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys("www." + url + ".com");
				break;
			case "Email":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String email = RandomStringUtils.randomAlphabetic(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'"+allFieldsText+"')]"))
						.sendKeys(email + "@gmail.com");
			default:
				break;
			}
		}
	}
}
