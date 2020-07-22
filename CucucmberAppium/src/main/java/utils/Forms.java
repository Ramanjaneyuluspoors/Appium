package utils;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.text.RandomStringGenerator;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.collections.ListUtils;
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
		CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
				.click();
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

	// data inserting for customer activity
	public static void handleActivity() throws MalformedURLException, InterruptedException {

		List<MobileElement> formFields = CommonUtils.getdriver().findElementsByXPath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");
		int countResult = formFields.size();
		System.out.println("countLatestfields:" + countResult);

		for (int i = 0; i < countResult; i++) {
			String fieldsText = formFields.get(i).getText();
			fieldsText = fieldsText.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("LabelNames after regExp:" + fieldsText);
			if (fieldsText.contains("Customer Type")) {
				MobileElement cusType = CommonUtils.getdriver().findElement(MobileBy
						.xpath("//*[starts-with(@text,'Customer Type')]/parent::*/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(cusType);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
			} else if (fieldsText.contains("Territory")) {
				MobileElement terriory = CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'Territory')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(terriory);
				CommonUtils.getdriver().findElementsByClassName("android.widget.CheckedTextView").get(1).click();
			} else if (fieldsText.contains("Country")) {
				MobileElement country = CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'Country')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(country);
				MobileActionGesture.scrollTospecifiedElement("Australia");
			} else if (fieldsText.contains("YesNo")) {
				MobileActionGesture.scrollTospecifiedElement("YesNo");
				MobileElement yesno = CommonUtils.getdriver()
						.findElementByXPath("//*[contains(@text,'YesNo')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(yesno);
				CommonUtils.getdriver().findElementsByClassName("android.widget.CheckedTextView").get(1).click();
			} else if (fieldsText.contains("Dropdown")) {
				MobileElement dropdown = CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Dropdown')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(dropdown);
				CommonUtils.getdriver().findElementsByClassName("android.widget.CheckedTextView").get(1).click();
			} else if (fieldsText.contains("Text")) {
				RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
				String text = textGenerator.generate(5);
				Thread.sleep(1000);
				CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'Text')]/parent::*/following-sibling::*/child::android.widget.EditText")
						.sendKeys(text);
			} else if (fieldsText.contains("URL")) {
				MobileActionGesture.scrollTospecifiedElement("URL");
				RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
				String url = generator.generate(5);
				CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'URL')]/parent::*/following-sibling::*/child::android.widget.EditText")
						.sendKeys("www." + url + ".com");
			} else if (fieldsText.contains("Email")) {
				MobileActionGesture.scrollTospecifiedElement("Email");
				RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
				String email = generator.generate(5);
				CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'Email')]/parent::*/following-sibling::*/child::android.widget.EditText")
						.sendKeys(email + "@gmail.com");
			} else if (fieldsText.contains("Number")) {
				String number1 = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText")
						.sendKeys(number1);
			} else if (fieldsText.contains("Currency")) {
				String currency1 = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText")
						.sendKeys(currency1);
			} else if (fieldsText.contains("Phone")) {
				MobileActionGesture.scrollTospecifiedElement("Phone");
				String phoneNum = RandomStringUtils.randomNumeric(10);
				CommonUtils.getdriver().findElementByXPath(
						"//*[contains(@text,'Phone')]/parent::*/following-sibling::*/child::android.widget.EditText")
						.sendKeys(phoneNum);
				CommonUtils.interruptSyncAndLetmeWork();
			} else if (fieldsText.contains("Location")) {
				CommonUtils.getdriver()
						.findElementByXPath(
								"//*[contains(@text,'Location')]/parent::*/following-sibling::*/android.widget.Button")
						.click();
				Thread.sleep(5000);
				CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
				CommonUtils.getdriver().findElementByXPath("//*[@text='MARK MY LOCATION']").click();
				CommonUtils.getdriver().findElementByXPath("//*[@text='USE MARKED LOCATION']").click();
			} else if (fieldsText.contains("Multi Select Dropdown")) {
				MobileElement multiSelectDropdown = CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Multi Select Dropdown')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button");
				MobileActionGesture.tapByElement(multiSelectDropdown);
				CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
				List<MobileElement> pickValues = CommonUtils.getdriver()
						.findElementsByClassName("android.widget.CheckedTextView");
				MobileActionGesture.singleLongPress(pickValues.get(0));
				MobileActionGesture.singleLongPress(pickValues.get(1));
				CommonUtils.getdriver().findElementByXPath("//*[@text='OK']").click();
			} else if (fieldsText.contains("Pick List")) {
				CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Pick List')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")
						.click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				if (CommonUtils.getdriver().findElementsById("titleTextView").get(0).isDisplayed()) {
					CommonUtils.getdriver().findElementsById("titleTextView").get(0).click();
				} else if (CommonUtils.getdriver().findElementsById("item_id").get(1).isDisplayed()) {
					CommonUtils.getdriver().findElementsById("item_id").get(1).click();
				}
			} else if (fieldsText.contains("Multi Pick List")) {
				MobileElement multipicklist = CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Multi Pick List')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button");
				MobileActionGesture.tapByElement(multipicklist);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				List<MobileElement> pickMultiPickList = CommonUtils.getdriver()
						.findElementsByClassName("android.widget.CheckBox");
				MobileActionGesture.singleLongPress(pickMultiPickList.get(0));
				MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
				CommonUtils.getdriver().findElementByXPath("//*[@text='OK']").click();
			} else if (fieldsText.contains("Time")) {
				CommonUtils.getdriver()
						.findElementByXPath("//*[starts-with(@text,'Time')]/parent::*/parent::*/android.widget.Button")
						.click();
				TimeScriptInForms();
			} else if (fieldsText.contains("DateTime")) {
				CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'DateTime')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[1]")
						.click();
				CommonUtils.alertContentXpath();
				dateScriptInForms();
			} else if (fieldsText.contains("Date")) {
				CommonUtils.getdriver()
						.findElementByXPath("//*[starts-with(@text,'Date')]/parent::*/parent::*/android.widget.Button")
						.click();
				CommonUtils.alertContentXpath();
				dateScriptInForms();
			} else if (fieldsText.contains("Employee")) {
				CommonUtils.getdriver()
						.findElementByXPath(
								"//*[contains(@text,'Employee')]/parent::*/following-sibling::android.widget.Button")
						.click();
				CommonUtils.getdriver().findElementsById("employeeNameTextView").get(0).click();
			} else if (fieldsText.contains("Customer")) {
				if (CommonUtils.getdriver()
						.findElementByXPath(
								"//*[starts-with(@text,'Customer')]/parent::*/following-sibling::android.widget.Button")
						.isEnabled()) {
					CommonUtils.getdriver()
							.findElementByXPath(
									"//*[starts-with(@text,'Customer')]/parent::*/parent::*/android.widget.Button")
							.click();
					CommonUtils.waitForElementVisibility("//*[@text='Customers']");
					if (CommonUtils.getdriver().findElementsById("item_id").get(0).isDisplayed()) {
						CommonUtils.getdriver().findElementsById("item_id").get(0).click();
					} else {
						CustomerPageActions.customerFab();
						CustomerPageActions.createCustomer();
						CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
						CommonUtils.getdriver()
								.findElementByXPath("//*[@text='" + CustomerPageActions.randomstringCusName + "']")
								.click();
					}
					System.out.println("Now customer is picked");
				} else {
					System.out.println("Customer is already selected!!");
				}
			} else if (fieldsText.contains("Custom Entity")) {
				MobileElement customEntity = CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Custom Entity')]/parent::*/parent::*/android.widget.Button");
				MobileActionGesture.tapByElement(customEntity);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				try {
					if (CommonUtils.getdriver().findElementsById("entityTitle").get(0).isDisplayed()) {
						CommonUtils.getdriver().findElementsById("entityTitle").get(0).click();
					}
				} catch (Exception e) {
					// write entity item creation method
				}
			} else if (fieldsText.equalsIgnoreCase("Multi Pick Customer")) {
				MobileElement multiPickCustomer = CommonUtils.getdriver().findElementByXPath(
						"//*[starts-with(@text,'Multi Pick Customer')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button");
				MobileActionGesture.tapByElement(multiPickCustomer);
				CommonUtils.waitForElementVisibility("//*[@text='Customers']");
				if (CommonUtils.getdriver().findElementsById("pickCustomerCheck").size() > 0) {
					List<MobileElement> selectCheckbox = CommonUtils.getdriver().findElementsById("pickCustomerCheck");
					MobileActionGesture.singleLongPress(selectCheckbox.get(0));
					MobileActionGesture.singleLongPress(selectCheckbox.get(1));
					CommonUtils.getdriver().findElementByXPath("//*[@content-desc='Select']").click();
				} else {
					CustomerPageActions.customerFab();
					CustomerPageActions.createCustomer();
					CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
					CommonUtils.getdriver().findElementByXPath("//*[@text='" + CustomerPageActions.randomstringCusName
							+ "']/parent::*/parent::*/parent::*/parent::*/android.widget.CheckBox").click();
					CommonUtils.getdriver().findElementByXPath("//*[@content-desc='Select']").click();
				}
			} else if (fieldsText.contains("Form")) {
				MobileElement form = CommonUtils.getdriver()
						.findElementByXPath("//*[starts-with(@text,'Form')]/parent::*/parent::*/android.widget.Button");
				MobileActionGesture.tapByElement(form);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				if (CommonUtils.getdriver().findElementsById("form_id_text_view").get(0).isDisplayed()) {
					CommonUtils.getdriver().findElementsById("form_id_text_view").get(0).click();
				} else if (CommonUtils.getdriver().findElementsById("load_more_button").get(0).isDisplayed()) {
					CommonUtils.getdriver().findElementById("load_more_button").click();
					Thread.sleep(2000);
					CommonUtils.getdriver().findElementsById("form_id_text_view").get(0).click();
				} else {
					CommonUtils.getdriver().findElementById("fab").click();
					CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
					CustomerPageActions.formfill();
					Forms.formSaveButton();
					CommonUtils.getdriver().findElementsById("form_id_text_view").get(0).click();
				}
			}
			CommonUtils.waitForElementVisibility(
					"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");
			if (i == formFields.size() - 1) {
				MobileActionGesture.flingVerticalToBottom_Android();
				CommonUtils.waitForElementVisibility(
						"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");
				formFields.addAll(CommonUtils.getdriver().findElementsByXPath(
						"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
				countResult = formFields.size();
				System.out.println("after scroll added FieldsCount:" + countResult);
			}
//			if (formFields.isEmpty())
//				break;
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
				System.out.println("Section fields form not found");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void verifySectionToClickAdd() throws InterruptedException, MalformedURLException {
		// check if section fields exist by scrolling and then click on it
		try {
			MobileElement sectionAdd = CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"ADD\").instance(0))"));
			if (sectionAdd.isDisplayed()) {
				MobileActionGesture.tapByElement(sectionAdd);
				System.out.println("In Section Add button is clicked");
			}
		} catch (Exception e) {
			System.out.println("Section with Add button is not found");
		}
		
	}

	// automating time in 12 hrs format(clicking on hours and AM,PM)
	public static void TimeScriptInForms() throws MalformedURLException, InterruptedException {
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
		date = DateUtils.addHours(date, 2);
		System.out.println("After adding hours time is : " + DateFor.format(date));
		// splitting extended hours
		String[] splitValueExtendedHrs = DateFor.format(date).split(":");
		// retrieving extended hours
		String extendedHours = splitValueExtendedHrs[0];
		System.out.println("addedHours: " + extendedHours);

		// adding minutes
		date = DateUtils.addMinutes(date, 5);

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
	public static void dateScriptInForms() throws MalformedURLException, InterruptedException {
		// get the date
		Date date = new Date();
		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

		date = DateUtils.addDays(date, 2);

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
	@SuppressWarnings("unchecked")
	public static void fillEntity() throws MalformedURLException, InterruptedException {
		//label list fields 
		List<MobileElement> labelView = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"));
		labelView.clear();
		//text list fields
		List<MobileElement> inputText = CommonUtils.getdriver()
				.findElements(MobileBy.className("android.widget.EditText"));
		inputText.clear();
		//merging two lists
		List<MobileElement> LabelViewAndTextView = ListUtils.union(labelView, inputText);
		int countOfAllFields = LabelViewAndTextView.size();
		System.out.println("countOfAllFields " + countOfAllFields);

		// get last element text to stop continuous scrolling and get list of elements
		boolean searchElement = true;
		String lastEntityTextElement = null;
		if (searchElement) {
			//scroll to bottom and add list fields
			MobileActionGesture.flingVerticalToBottom_Android();

			labelView.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
			lastEntityTextElement = labelView.get(labelView.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
					"");
			System.out.println("Get Last Entity Label element text: " + lastEntityTextElement);
			labelView.clear();

			inputText.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
			lastEntityTextElement = inputText.get(inputText.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
					"");
			System.out.println("Get Last Entity Input element text: " + lastEntityTextElement);
			inputText.clear();
			
			//scroll to top
			MobileActionGesture.flingToBegining_Android();
		}
		
		//adding both list fields of first screen
		labelView.addAll(CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
		inputText.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		LabelViewAndTextView = ListUtils.union(labelView, inputText);
		countOfAllFields = LabelViewAndTextView.size();
		System.out.println("before swiping entity fields inputText count: " + countOfAllFields);

		//scroll and add list fields till last text element find
		while (!labelView.isEmpty() && labelView!= null && !inputText.isEmpty() && inputText!=null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			labelView.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
			inputText.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
			LabelViewAndTextView = ListUtils.union(labelView, inputText);
			countOfAllFields = LabelViewAndTextView.size();
			System.out.println("After swiping entity fields count: " + countOfAllFields);
			for (int i = 0; i < LabelViewAndTextView.size(); i++) {
				if (labelView.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastEntityTextElement)) {
					flag = true;
				} else if (inputText.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "")
						.equalsIgnoreCase(lastEntityTextElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}		
		//iterate the list fields
		for (int i = 0; i < countOfAllFields; i++) {
			String allFieldsText = LabelViewAndTextView.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("After remove Special character label is: " + allFieldsText);

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
						"//*[@text='Last Activity Time']/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
						.click();
				CommonUtils.alertContentXpath();
				Forms.dateScriptInForms();
				Thread.sleep(500);
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				if (CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'Customer')]/parent::*/android.widget.Button"))
						.isEnabled()) {
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[@id='label_for_view' and contains(@text,'Customer')]/parent::*/android.widget.Button"))
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
						"//*[@id='label_for_view' and contains(@text,'Employee')]/parent::*/android.widget.Button"))
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
								"//*[@id='label_for_view' and contains(@text,'Time')]/parent::*/android.widget.Button"))
						.click();
				TimeScriptInForms();
				Thread.sleep(500);
				break;
			case "DateTime":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'DateTime')]/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
						.click();
				CommonUtils.alertContentXpath();
				Forms.dateScriptInForms();
				Thread.sleep(500);
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(allFieldsText);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath(
								"//*[@id='label_for_view' and contains(@text,'Date')]/parent::*/android.widget.Button"))
						.click();
				CommonUtils.alertContentXpath();
				Forms.dateScriptInForms();
				Thread.sleep(500);
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement customerType = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'Customer Type')]/parent::*/android.widget.Spinner"));
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
						.xpath("//*[@id='label_for_view' and contains(@text,'Form')]/parent::*/android.widget.Button"));
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
							CustomerPageActions.verifyFormPagesAndFill();
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
						"//*[@id='label_for_view' and contains(@text,'Signature')]/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"));
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
						"//*[@id='label_for_view' and contains(@text,'Country')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(country);
				MobileActionGesture.scrollTospecifiedElement("India");
				break;
			case "YesNo":
			case "YesOrNo":
			case "Yes/No":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement yesno = CommonUtils.getdriver().findElement(MobileBy
						.xpath("//*[@id='label_for_view' and contains(@text,'Yes')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(yesno);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement territory = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'Territory')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(territory);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement dropdown = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'Dropdown')]/parent::*/android.widget.Spinner"));
				MobileActionGesture.singleLongPress(dropdown);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Multi Select Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement multidropdown = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[@id='label_for_view' and contains(@text,'Multi Select Dropdown')]/parent::*/android.widget.Button"));
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
						"//*[@id='label_for_view' and starts-with(@text,'Pick List')]/parent::*/android.widget.Button"))
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
						"//*[@id='label_for_view' and starts-with(@text,'Multi Pick List')]/parent::*/android.widget.Button"));
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
						"//*[@id='label_for_view' and starts-with(@text,'Multi Pick Customer')]/parent::*/android.widget.Button"));
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
								.xpath("//*[@class='android.widget.EditText' and contains(@text,'Entity Name')]"))
						.sendKeys(EntityName);
				break;
			case "Last Activity Name":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String LastActivtyName = RandomStringUtils.randomAlphabetic(6);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath(
								"//*[@class='android.widget.EditText' and contains(@text,'Last Activity Name')]"))
						.sendKeys(LastActivtyName);
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String Text = RandomStringUtils.randomAlphabetic(5);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'Text')]"))
						.sendKeys(Text);
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String currency = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'Currency')]"))
						.sendKeys(currency);
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String number = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'Number')]"))
						.sendKeys(number);
				break;
			case "Phone":
			case "Phone Number":
			case "PhoneOptional":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String phonenumber = RandomStringUtils.randomNumeric(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and starts-with(@text,'Phone')]"))
						.sendKeys(phonenumber);
				break;
			case "URL":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String url = RandomStringUtils.randomAlphabetic(5);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'URL')]"))
						.sendKeys("www." + url + ".com");
				break;
			case "Email":
				MobileActionGesture.scrollUsingText(allFieldsText);
				String email = RandomStringUtils.randomAlphabetic(5);
				CommonUtils.getdriver()
						.findElement(
								MobileBy.xpath("//*[@class='android.widget.EditText' and contains(@text,'Email')]"))
						.sendKeys(email + "@gmail.com");
			default:
				break;
			}
		}
	}	

	

}
