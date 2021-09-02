package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import io.appium.java_client.android.AndroidDriver;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.gen5.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;
import utils.MediaPermission;

public class Custom_Entity {
	public static String entityName;
	public static AndroidDriver<MobileElement> driver;

	// go to entity screen
	public static void goToCustomEntityScreen(String entityName) {
		MobileActionGesture.scrollTospecifiedElement(entityName);
		AndroidLocators.clickElementusingID("customEntitiesFab");
		CommonUtils.waitForElementVisibility("//*[contains(@text,'Create')]");
	}

	// create entity item
	public static void createEntity() throws MalformedURLException, InterruptedException, ParseException {
		AndroidLocators.clickElementusingID("customEntitiesFab");
		CommonUtils.waitForElementVisibility("//*[contains(@text,'Create')]");
		// insert entity data
		Custom_Entity.fillEntity();
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
		System.out.println("**** Last element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		labelViews.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		labelViews.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// retrieve count of fields displaying in first screen
		countOfAllFields = labelViews.size();
		System.out.println(" ---- Before swiping, entity fields count ---- : " + countOfAllFields);

		// scroll and add list fields(labelView) till last text element find
		while (!labelViews.isEmpty() && labelViews != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// retrieving count of fields displaying until last element display
			countOfAllFields = labelViews.size();
			System.out.println(".... After swiping, entity fields count .... : " + countOfAllFields);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {
				// printing the elements in the list
				System.out.println("===== Entity fields text ===== : " + labelViews.get(i).getText());

				// if list matches with last element the loop will break
				if (labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out
							.println("----- Entity fields text inside elements ----- : " + labelViews.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		MobileActionGesture.flingToBegining_Android();
		
		//inserting entity fields
		insertingEntityFields(labelViews, countOfAllFields);
	}

	// inserting entity fields
	public static void insertingEntityFields(List<MobileElement> labelViews, int countOfAllFields)
			throws InterruptedException, MalformedURLException, ParseException {
		
		// iterate the list fields
		for (int i = 0; i < countOfAllFields; i++) {		
						
			new WebDriverWait(CommonUtils.getdriver(), 10).until(ExpectedConditions.visibilityOfAllElements(labelViews.get(i)));
			
			String allFieldsText = labelViews.get(i).getText();
			allFieldsText = labelViews.get(i).getText().split("\\(")[0].replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("==== Before removing special character ==== : " + allFieldsText
					+ "\n**** After removing the special chararcter **** : " + allFieldsText);

			switch (allFieldsText) {
			case "Location":
				MobileActionGesture.scrollUsingText(allFieldsText);
				AndroidLocators.clickElementusingID("pick_location_button");
				CommonUtils.wait(8);
				CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
				AndroidLocators.clickElementusingXPath("//*[@text='MARK MY LOCATION']");
				AndroidLocators.clickElementusingXPath("//*[@text='USE MARKED LOCATION']");
				CommonUtils
						.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
				CommonUtils.wait(5);
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK A CUSTOMER");
				if (AndroidLocators
						.xpath("//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button")
						.isEnabled()) {
					AndroidLocators.clickElementusingXPath(
							"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
					CommonUtils.waitForElementVisibility("//*[@text='Customers']");
					if (AndroidLocators.findElements_With_Id("item_id").size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
					} else {
						CustomerPageActions.customerFab();
						CustomerPageActions.createCustomer();
						CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
						AndroidLocators
								.clickElementusingXPath("//*[@text='" + CustomerPageActions.randomstringCusName + "']");
					}
					System.out.println("Now customer is picked");
				} else {
					System.out.println("Customer is already selected!!");
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "Employee":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK A EMPLOYEE");
				AndroidLocators.clickElementusingXPath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				if (AndroidLocators.findElements_With_ClassName("android.widget.CheckBox").size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckBox")).get(0).click();
					AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
				} else if (AndroidLocators.findElements_With_Id("employeeNameTextView").size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(3);
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK A TIME");
				AndroidLocators.clickElementusingXPath(
						"//*[starts-with(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				Forms_basic.TimeScriptInForms(1, 1);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "DateTime":
				MobileActionGesture.scrollUsingText(allFieldsText);
				AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + allFieldsText
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[1]");
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
				if (AndroidLocators
						.findElements_With_Xpath("//*[starts-with(@text,'" + allFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]")
						.size() > 0) {
					AndroidLocators
							.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					Work.workEndTime(1, 5);
				} else {
					System.out.println("DateTime is already picked");
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK A DATE");
				AndroidLocators.clickElementusingXPath(
						"//*[starts-with(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				Forms_basic.dateScriptInForms(1);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("Pick customer type");
				MobileElement customerType = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(customerType);
				if (AndroidLocators.findElements_With_ClassName("android.widget.CheckedTextView").size() > 0) {
					CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
							.click();
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(3);
				break;
			case "Form":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK A FORM");
				MobileElement form = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				MobileActionGesture.tapByElement(form);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				try {
					if (AndroidLocators.findElements_With_Id("form_id_text_view").size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
					} else {
						CommonUtils.getdriver().findElementById("load_more_button").click();
						CommonUtils.waitForElementVisibility(
								"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
						if (AndroidLocators.findElements_With_Id("form_id_text_view").size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
						} else {
							AndroidLocators.clickElementusingID("fab");
							CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
							Forms_basic.verifyFormPagesAndFill();
							Forms_basic.formSaveButton();
							CommonUtils.waitForElementVisibility(
									"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
							if (AndroidLocators.findElements_With_Id("form_id_text_view").size() > 0) {
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
				MobileActionGesture.scrollUsingText("CAPTURE SIGNATURE");
				MobileElement signature = AndroidLocators.xpath("//*[contains(@text,'" + allFieldsText
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button");
				MobileActionGesture.tapByElement(signature);
				MediaPermission.mediaPermission();
				CommonUtils.waitForElementVisibility("//*[@text='CAPTURE']");
				MobileElement signatureCapture = AndroidLocators.returnUsingId("saveButton");
				MobileActionGesture.singleLongPress(signatureCapture);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Country":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("Pick a country");
				MobileElement country = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(country);
				MobileActionGesture.scrollTospecifiedElement("India");
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "YesNo":
			case "YesOrNo":
			case "Yes/No":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileElement yesno = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + ")]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(yesno);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("Pick territory type");
				MobileElement territory = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(territory);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("Pick a value");
				MobileElement dropdown = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Spinner");
				MobileActionGesture.singleLongPress(dropdown);
				CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1)
						.click();
				break;
			case "Multi Select Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK MULTI SELECT DROPDOWN");
				MobileElement multidropdown = AndroidLocators.xpath(
						"//*[contains(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				MobileActionGesture.tapByElement(multidropdown);
				CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
				List<MobileElement> pickValues = AndroidLocators
						.findElements_With_ClassName("android.widget.CheckedTextView");
				if (pickValues.get(0).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickValues.get(0));
				}
				if (pickValues.get(1).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickValues.get(1));
				}
				AndroidLocators.clickElementusingXPath("//*[@text='OK']");
				CommonUtils.wait(5);
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK PICK LIST");
				AndroidLocators.clickElementusingXPath(
						"//*[starts-with(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
					CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
				} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
					CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
				}
				CommonUtils.wait(5);
				break;
			case "Multi Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK MULTI PICK LIST");
				MobileElement multipicklist = AndroidLocators.xpath(
						"//*[starts-with(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				MobileActionGesture.tapByElement(multipicklist);
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				List<MobileElement> pickMultiPickList = AndroidLocators
						.findElements_With_ClassName("android.widget.CheckBox");
				if (pickMultiPickList.get(0).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickMultiPickList.get(0));
				}
				if (pickMultiPickList.get(0).isDisplayed()) {
					MobileActionGesture.singleLongPress(pickMultiPickList.get(1));
				}
				AndroidLocators.clickElementusingXPath("//*[@text='OK']");
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Multi Pick Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				MobileActionGesture.scrollUsingText("PICK MULTI PICK CUSTOMER");
				MobileElement multiPickCustomer = AndroidLocators.xpath(
						"//*[starts-with(@text,'" + allFieldsText + "')]/parent::*/parent::*/android.widget.Button");
				MobileActionGesture.tapByElement(multiPickCustomer);
				CommonUtils.waitForElementVisibility("//*[@text='Customers']");
				if (CommonUtils.getdriver().findElements(MobileBy.id("pickCustomerCheck")).size() > 0) {
					List<MobileElement> selectCheckbox = AndroidLocators.findElements_With_Id("pickCustomerCheck");
					if (selectCheckbox.get(0).isDisplayed()) {
						MobileActionGesture.singleLongPress(selectCheckbox.get(0));
					}
					if (selectCheckbox.get(1).isDisplayed()) {
						MobileActionGesture.singleLongPress(selectCheckbox.get(1));
					}
					AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
				} else {
					CustomerPageActions.customerFab();
					CustomerPageActions.createCustomer();
					CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
					AndroidLocators.clickElementusingXPath("//*[@text='" + CustomerPageActions.randomstringCusName
							+ "']/parent::*/parent::*/parent::*/parent::*/android.widget.CheckBox");
					AndroidLocators.clickElementusingXPath("//*[@content-desc='Select']");
				}
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + allFieldsText + "')]");
				CommonUtils.wait(5);
				break;
			case "Entity Name":
				entityName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.enterTextusingXpath("//*[contains(@text,'" + allFieldsText + "')]", entityName);
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.enterTextusingXpath("//*[contains(@text,'" + allFieldsText + "')]", entityName);
				}
				entityName = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"(//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']//android.widget.EditText)[1]"))
						.getText();
				System.out.println("---- Retrieve the entity name ---- :" + entityName);
				break;
			case "Text":
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Currency":
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Number":
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Phone":
			case "Phone Number":
			case "PhoneOptional":
				if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + allFieldsText + "')]");
				}
				break;
			case "URL":
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.sendUrlInputusing_XPath("//*[contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendUrlInputusing_XPath("//*[contains(@text,'" + allFieldsText + "')]");
				}
				break;
			case "Email":
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.sendEmailInputusing_XPath("//*[contains(@text,'" + allFieldsText + "')]");
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.sendEmailInputusing_XPath("//*[contains(@text,'" + allFieldsText + "')]");
				}
			}
		}
	}

	// entity checkin
	public static void customEntityCheckin() throws MalformedURLException, InterruptedException, ParseException {
		MobileElement entityCheckin = AndroidLocators.findElements_With_Id("checkinoutButton").get(0);
		if (entityCheckin.getText().contains("OFF")) {
			System.out.println(".... User is going to checkin to entity .... ");
			entityCheckin.click();
			checkinAlert();
			forceCheck_In();
			addActivity();
		} else if (entityCheckin.getText().contains("ON")) {
			addActivity();
		}
	}

	// entity checkout
	public static void customEntityCheckout() throws MalformedURLException, InterruptedException {
		if (AndroidLocators.findElements_With_Id("checkinoutButton").get(0).getText().contains("ON")) {
			AndroidLocators.findElements_With_Id("checkinoutButton").get(0).click();
			CustomerPageActions.checkOutOrCheckOutAnyway();
			forceCheck_In();
		}
	}

	public static void checkinAlert() throws MalformedURLException, InterruptedException {
		MobileElement checkin = AndroidLocators.resourceId("android:id/button1");
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
//			CustomerPageActions.customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.getdriver().hideKeyboard();
			AndroidLocators.clickElementusingXPath("//*[@text='SUBMIT']");
		}
		CommonUtils.wait(5);
	}

	// goto home screen
	public static void moveToHomeScreen() throws MalformedURLException, InterruptedException {
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}

	// fill entity activity
	public static void clickAndFillActivity() throws MalformedURLException, InterruptedException, ParseException {
		AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/titleTextView").get(0).click();
		Forms_basic.verifyFormPagesAndFill();
		Forms_basic.formSaveButton();
		CommonUtils.goBackward();
	}

	// click on allow force checkin/checkout
	public static void forceCheck_In() {
		if (AndroidLocators.findElements_With_Id("alertTitle").size() > 0) {
			AndroidLocators.clickElementusingID("button1");
			System.out.println("Force check-in is done successfully");
		} else if (AndroidLocators
				.findElements_With_ResourceId("new UiSelector().resourceId(\"in.spoors.effortplusbeta:id/alertTitle\")")
				.size() > 0) {
			AndroidLocators.clickElementusingResourceId("android:id/button1");
			System.out.println("Force check-in is done successfully");
		}
	}

	// click add button
	public static void addActivity() {
		if (AndroidLocators.findElements_With_Id("addButton").get(0).isDisplayed()) {
			AndroidLocators.clickElementusingID("addButton");
		} else if (AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/addButton").get(0)
				.isDisplayed()) {
			AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/addButton");
		}
		CommonUtils.waitForElementVisibility("//*[@text='ACTIVITIES']");
	}

	// regular expression testing
	public static void regularExpression(String regExp, String dataType) {
		AndroidLocators
				.sendInputusing_XPath("(//android.widget.LinearLayout/*/*/*/*/*[@class='android.widget.EditText'])[1]");

		MobileActionGesture.scrollUsingText(dataType);

		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(dataType);

		for (int i = 0; i < 3; i++) {

			String unMatchRegExp = FormAdvanceSettings.unMatch_regExp(regExp);
			String matchRegExp = FormAdvanceSettings.match_regExp(regExp);

			// inserting unmatching regular expression
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, unMatchRegExp);

			// save entity
			CustomerPageActions.saveMethod();

			// inserting matching regular expression
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
					"" + matchRegExp.charAt(i));

			// save entity
			CustomerPageActions.saveMethod();
		}
	}

	// entity min and max validations
	public static void entityFields_min_max_validations(int min, int max) throws MalformedURLException {
		// label list fields
		List<MobileElement> labelViews = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// retrieve count of entity fields
		int countOfAllFields = labelViews.size();
		System.out.println("----- Count Of all entity fields ----- : " + countOfAllFields);

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
		System.out.println("**** Last element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		labelViews.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		labelViews.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// retrieve count of fields displaying in first screen
		countOfAllFields = labelViews.size();
		System.out.println(" ---- Before swiping, entity fields count ---- : " + countOfAllFields);

		// scroll and add list fields(labelView) till last text element find
		while (!labelViews.isEmpty() && labelViews != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// retrieving count of fields displaying until last element display
			countOfAllFields = labelViews.size();
			System.out.println(".... After swiping, entity fields count .... : " + countOfAllFields);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {
				// printing the elements in the list
				System.out.println("===== Entity fields text ===== : " + labelViews.get(i).getText());

				// if list matches with last element the loop will break
				if (labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out
							.println("----- Entity fields text inside elements ----- : " + labelViews.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		MobileActionGesture.flingToBegining_Android();

		// iterate the list fields
		for (int i = 0; i < countOfAllFields; i++) {

			String allFieldsTextOriginal = labelViews.get(i).getText();
			String allFieldsText = labelViews.get(i).getText().split("\\(")[0].replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("==== Before removing special character ==== : " + allFieldsTextOriginal
					+ "\n**** After removing the special chararcter **** : " + allFieldsText);

			switch (allFieldsText) {
			case "Entity Name":
				String entityName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					AndroidLocators.enterTextusingXpath("//*[contains(@text,'" + allFieldsText + "')]", entityName);
				} else {
					MobileActionGesture.scrollUsingText(allFieldsText);
					AndroidLocators.enterTextusingXpath("//*[contains(@text,'" + allFieldsText + "')]", entityName);
				}
				String GetentityName = CommonUtils.getdriver().findElement(MobileBy.xpath(
						"(//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']//android.widget.EditText)[1]"))
						.getText();
				System.out.println("---- Retrieve the entity name ---- :" + GetentityName);
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(allFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					entityTextFieldMinMaxValue(allFieldsText, min, max);
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(allFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					currencyMinMax(allFieldsText, min, max);
				}
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(allFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + allFieldsText + "')]")
						.size() > 0) {
					currencyMinMax(allFieldsText, min, max);
				}
				break;
			}
		}
	}

	// inserting text input min max
	public static void entityTextFieldMinMaxValue(String allFieldsText, int min, int max) {
		// assigning min max values
		int min_value = min, max_value = max;

		// get above/below element of main element
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(allFieldsText);

		// get random data
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

		String textMinInput = null;
		String textMinInput1 = null;
		String textMaxInput = null;
		String textMaxInput1 = null;

		// inserting min input value
		for (int n = 0; n < 2; n++) {

			textMinInput = textGenerator.generate(min_value);
			textMinInput1 = textGenerator.generate(min);
			System.out.println("*** Given minimum length is *** : " + textMinInput1);
			System.out.println("*** Validation minimum length is *** : " + textMinInput);

			// insert min text input value
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textMinInput);

			// click on save button
			CustomerPageActions.saveMethod();

			// validating min values
			if (textMinInput.length() < textMinInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("---- Expected toast message for min input is ---- : " + text);
				Assertions.assertFalse(
						text.contains("" + allFieldsText + " Smaller than the minimum allowed length" + (textMinInput1)
								+ "."),
						"" + allFieldsText + " Smaller than the minimum allowed length " + (textMinInput1) + ".");
			}

			// decreasing the min value
			min_value = min_value - 1;
		}

		// inserting max input values
		for (int p = 0; p < 2; p++) {

			textMaxInput = textGenerator.generate(max_value);
			textMaxInput1 = textGenerator.generate(max);
			System.out.println("*** Given maximum length is *** : " + textMaxInput1);
			System.out.println("*** Validation maximum length is *** : " + textMaxInput);

			// insert max text input value
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textMaxInput);

			// click on save button
			CustomerPageActions.saveMethod();

			// validating max values
			if (textMaxInput.length() > textMaxInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max input is " + text);
				Assertions.assertFalse(
						text.contains("" + allFieldsText + " larger than the maximum allowed length " + (textMaxInput1)
								+ "."),
						"" + allFieldsText + " larger than the maximum allowed length " + (textMaxInput1) + ".");
			}

			// increasing max value
			max_value = max_value + 1;
		}
		// based previous element inputing the main element
		Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
				textGenerator.generate(max));
	}

	// entity currency field min max validation
	public static void currencyMinMax(String allFieldsText, int min, int max) {
		// assigning min max values
		int min_value = min, max_value = max;

		// get above/below element of main element
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(allFieldsText);

		for (int i = 0; i < 2; i++) {

			System.out.println("*** Minimum value *** : " + min_value);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
					String.valueOf(min_value));

			// click on save button
			CustomerPageActions.saveMethod();

			if (min < min_value) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for min value is :" + text);
				Assertions.assertFalse(text.contains("" + allFieldsText + " cannot be less than " + min + "."),
						"" + allFieldsText + " cannot be less than " + min + ".");
			}

			// clear the input
			Work_advanceSettings.clearTheInputBasedOnAboveOrBelowElement(getAboveOrBelowOfMainElement);

			// decrease the value
			min_value = min_value - 1;
		}

		// inserting max input value
		for (int p = 0; p < 2; p++) {

			System.out.println("*** Maximum value is *** : " + max_value);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
					String.valueOf(max_value));

			// click on save button
			CustomerPageActions.saveMethod();

			// validating max values
			if (max > max_value) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max value is :" + text);
				Assertions.assertFalse(text.contains("" + allFieldsText + " cannot be greater than " + max + "."),
						"" + allFieldsText + " cannot be greater than " + max + ".");
			}

			// clear the input
			Work_advanceSettings.clearTheInputBasedOnAboveOrBelowElement(getAboveOrBelowOfMainElement);

			// increase the value
			max_value = max_value + 1;
		}
		
		// based previous element inputing the main element
		Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(max));
	}

	// field dependency based on value in other fields
	public static void entity_field_dependency_basedOn_value_in_otherfields(String baseCondition, String fieldType,
			String fieldInput) throws MalformedURLException, InterruptedException {

		// initializing and assigning
		String entityBaseCondition = baseCondition;
		String entityFieldType = fieldType;
		String entityFieldInput = fieldInput;

		// label list fields
		List<MobileElement> labelViews = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// customer specified fields
		List<MobileElement> entity_Dependencyfield = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
						+ fieldType + "')]");

		// retrieve count of entity fields
		int countOfDependentField = entity_Dependencyfield.size();
		System.out.println("----- Count Of entity fields ----- : " + countOfDependentField);

		// clear entity fields
		entity_Dependencyfield.clear();

		// initializing string
		String lastEntityTextElement = null;

		// scroll to bottom and add list fields
		MobileActionGesture.flingVerticalToBottom_Android();

		// add list fields
		entity_Dependencyfield.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// get last element in this list and store to "lastEntityTextElement"
		lastEntityTextElement = entity_Dependencyfield.get(entity_Dependencyfield.size() - 1).getText();
		System.out.println("**** Last element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		entity_Dependencyfield.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		entity_Dependencyfield.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
						+ fieldType + "')]"));

		// retrieve count of fields displaying in first screen
		countOfDependentField = entity_Dependencyfield.size();
		System.out.println(" ---- Before swiping, entity fields count ---- : " + countOfDependentField);

		// scroll and add list fields(labelView) till last text element find
		while (entity_Dependencyfield.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			entity_Dependencyfield.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
							+ fieldType + "')]"));

			// retrieving count of fields displaying until last element display
			countOfDependentField = entity_Dependencyfield.size();
			System.out.println(".... After swiping, entity fields count .... : " + countOfDependentField);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {
				// printing the elements in the list
				System.out.println("===== Entity fields text ===== : " + labelViews.get(i).getText());

				// if list matches with last element the loop will break
				if (entity_Dependencyfield.size() > 0 || labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out
							.println("----- Entity fields text inside elements ----- : " + labelViews.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		MobileActionGesture.flingToBegining_Android();

		// iterate the list fields
		for (int i = 0; i < countOfDependentField; i++) {

			String allFieldsTextOriginal = entity_Dependencyfield.get(i).getText();
			String allFieldsText = entity_Dependencyfield.get(i).getText().split("\\(")[0]
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("==== Before removing special character ==== : " + allFieldsTextOriginal
					+ "\n**** After removing the special chararcter **** : " + allFieldsText);

			switch (allFieldsText) {
			case "Text":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// text method
				Customer_Advancesetting.customerTextFieldDependency(entityBaseCondition, entityFieldType,
						entityFieldInput);
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// currency method
				Customer_Advancesetting.testingDependencyFieldsBasedOnCurrency(entityBaseCondition, entityFieldType,
						entityFieldInput);
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// currency method
				Customer_Advancesetting.testingDependencyFieldsBasedOnCurrency(entityBaseCondition, entityFieldType,
						entityFieldInput);
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// customer method
				Work_advanceSettings.customerPicker(entityBaseCondition, entityFieldType, entityFieldInput);
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// picklist method
				Work_advanceSettings.workPickList(entityBaseCondition, entityFieldType, entityFieldInput);
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// dropdown method
				Work_advanceSettings.dropdownSelection(entityBaseCondition, entityFieldType, entityFieldInput);
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(allFieldsText);
				// Date method
				Work_advanceSettings.datePicking(entityBaseCondition, entityFieldType, entityFieldInput);
				break;
			}
			
		}
	}

	//error and warning message validation
	public static void entityFieldsValidation_basedon_value_inother_fields(String errorCondition,
			String criteriaCondition, String inputValue) throws MalformedURLException, InterruptedException, ParseException {
		
		// splitting and assigning input to variable
		String[] inputArray = inputValue.split(",");
		String currencyInput = inputArray[0];
		String dateInput = inputArray[1];
		
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
		System.out.println("**** Last element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		labelViews.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		labelViews.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

		// retrieve count of fields displaying in first screen
		countOfAllFields = labelViews.size();
		System.out.println(" ---- Before swiping, entity fields count ---- : " + countOfAllFields);

		// scroll and add list fields(labelView) till last text element find
		while (!labelViews.isEmpty() && labelViews != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// retrieving count of fields displaying until last element display
			countOfAllFields = labelViews.size();
			System.out.println(".... After swiping, entity fields count .... : " + countOfAllFields);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {
				// printing the elements in the list
				System.out.println("===== Entity fields text ===== : " + labelViews.get(i).getText());

				// if list matches with last element the loop will break
				if (labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out
							.println("----- Entity fields text inside elements ----- : " + labelViews.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		MobileActionGesture.flingToBegining_Android();

		// providing input for customer fields by iterating the customer list
		for (int i = 0; i < countOfAllFields; i++) {

			String originalText = labelViews.get(i).getText();
			String entityFieldsText = labelViews.get(i).getText().split("\\(")[0].replaceAll("[!@#$%&*(),.?\":{}|<>]",
					"");
			System.out.println("---- Before removing special character ---- : " + originalText
					+ "\n.... After removing special character .... : " + entityFieldsText);
			switch (entityFieldsText) {
			case "Entity Name":
				MobileActionGesture.scrollUsingText(entityFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + entityFieldsText + "')]")
						.size() > 0) {
					entityName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					AndroidLocators.enterTextusingXpath("//*[contains(@text,'" + entityFieldsText + "')]", entityName);
					entityName = CommonUtils.getdriver().findElement(MobileBy.xpath(
							"(//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']//android.widget.EditText)[1]"))
							.getText();
					System.out.println("---- Retrieve the entity name ---- :" + entityName);
				}
				break;
			case "Currency":
			case "G-Currency":
				MobileActionGesture.scrollUsingText(entityFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[@text='" + entityFieldsText + "']")
						.size() > 0) {
					// currency or number input
//					error_And_warnmessage_for_currency_and_number(errorCondition, criteriaCondition, currencyInput,
//							entityFieldsText);
					insertCurrency_and_Number_fieldValidation_alternateway(entityFieldsText, currencyInput);
				} else {
					insertCurrency_and_Number_fieldValidation_alternateway(entityFieldsText, currencyInput);
				}
				break;
			case "Number":
			case "G-Number":
				MobileActionGesture.scrollUsingText(entityFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[@text='" + entityFieldsText + "']")
						.size() > 0) {
					insertCurrency_and_Number_fieldValidation_alternateway(entityFieldsText, currencyInput);
				}else{
				MobileActionGesture.scrollUsingText(entityFieldsText);
				insertCurrency_and_Number_fieldValidation_alternateway(entityFieldsText, currencyInput);
			}
				break;
			case "Date":
			case "G-Date":
				MobileActionGesture.scrollUsingDirectText(entityFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[@text='" + entityFieldsText + "']").size() > 0) {
					dateErrorAndWarnMessageValidation(entityFieldsText, dateInput, errorCondition);
				}
				break;
			}
		}
	}
	
	
	// alternateway for currency and number error and warn message
	public static void insertCurrency_and_Number_fieldValidation_alternateway(String entityFieldsText,
			String currencyInput) throws InterruptedException {
		int currencyErrorInput = Integer.parseInt(currencyInput);

		// assigning inputdata to variable
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(entityFieldsText);
		
		for (int i = 0; i < 3; i++) {
			
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			
			currencyErrorInput = currencyErrorInput - 1;
			System.out.println("------- Currency value ------ :" + currencyErrorInput);
			
			if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + entityFieldsText + "')]")
					.size() > 0) {
				AndroidLocators.enterTextusingXpath("//*[starts-with(@text,'" + entityFieldsText + "')]",
						String.valueOf(currencyErrorInput));
			} else {
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));
			}
			//click on save button
			CustomerPageActions.saveMethod();
			
			//if alert display then handling alert
			FormAdvanceSettings.handlingWarningAlert();
			
			// increasing the currency value
			currencyErrorInput = currencyErrorInput + 2;
			System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
		}
	}

	// currency or number validating field based on value
	public static int error_And_warnmessage_for_currency_and_number(String errorCondition, String criteriaCondition,
			String currencyInput, String entityFieldsText) throws MalformedURLException, InterruptedException, ParseException {
		// initializing assigning
		int currencyErrorInput = Integer.parseInt(currencyInput);

		// assigning inputdata to variable
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(entityFieldsText);
		
		if (errorCondition.equalsIgnoreCase("Show Error when") && criteriaCondition.equalsIgnoreCase("Equals To")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);

				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 1;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Error when")
				&& criteriaCondition.equalsIgnoreCase("Not Equals To")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

				currencyErrorInput = currencyErrorInput - 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);

				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Error when")
				&& criteriaCondition.equalsIgnoreCase("Greater Than")) {

			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

				currencyErrorInput = currencyErrorInput + 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);

				// increasing the currency value
				currencyErrorInput = currencyErrorInput - 2;
				System.out.println(".... After decreasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Error when")
				&& criteriaCondition.equalsIgnoreCase("Less Than")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

				currencyErrorInput = currencyErrorInput - 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);

				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Error when")
				&& criteriaCondition.equalsIgnoreCase("Greater Than Or Equals To")) {
			for (int i = 0; i < 3; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

				currencyErrorInput = currencyErrorInput + 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);

				// decreasing the currency value
				currencyErrorInput = currencyErrorInput - 2;
				System.out.println(".... After decreasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Error when")
				&& criteriaCondition.equalsIgnoreCase("Less Than Or Equals To")) {
			for (int i = 0; i < 3; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

				currencyErrorInput = currencyErrorInput - 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);

				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Warning when")
				&& criteriaCondition.equalsIgnoreCase("Equals To")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					fillentity_mandatory_fields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 1;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Warning when")
				&& criteriaCondition.equalsIgnoreCase("Not Equals To")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				currencyErrorInput = currencyErrorInput - 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					fillentity_mandatory_fields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 1;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Warning when")
				&& criteriaCondition.equalsIgnoreCase("Greater Than")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				currencyErrorInput = currencyErrorInput + 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					fillentity_mandatory_fields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
				// increasing the currency value
				currencyErrorInput = currencyErrorInput - 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Warning when")
				&& criteriaCondition.equalsIgnoreCase("Less Than")) {
			for (int i = 0; i < 2; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				currencyErrorInput = currencyErrorInput - 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					fillentity_mandatory_fields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Warning when")
				&& criteriaCondition.equalsIgnoreCase("Greater Than Or Equals To")) {
			for (int i = 0; i < 3; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				currencyErrorInput = currencyErrorInput + 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					fillentity_mandatory_fields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
				// increasing the currency value
				currencyErrorInput = currencyErrorInput - 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		} else if (errorCondition.equalsIgnoreCase("Show Warning when")
				&& criteriaCondition.equalsIgnoreCase("Less Than Or Equals To")) {
			for (int i = 0; i < 3; i++) {
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
				currencyErrorInput = currencyErrorInput - 1;
				System.out.println("------- Currency value ------ :" + currencyErrorInput);
				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					fillentity_mandatory_fields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
				// increasing the currency value
				currencyErrorInput = currencyErrorInput + 2;
				System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
			}
		}
		return currencyErrorInput;
	}
	
	

	// fill entity mandatory fields
	public static void fillentity_mandatory_fields()
			throws MalformedURLException, InterruptedException, ParseException {
		// label list fields
		List<MobileElement> labelViews = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]");

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
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]"));

		// get last element in this list and store to "lastEntityTextElement"
		lastEntityTextElement = labelViews.get(labelViews.size() - 1).getText();
		System.out.println("**** Last element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		labelViews.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		labelViews.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]"));

		// retrieve count of fields displaying in first screen
		countOfAllFields = labelViews.size();
		System.out.println(" ---- Before swiping, entity fields count ---- : " + countOfAllFields);

		// scroll and add list fields(labelView) till last text element find
		while (!labelViews.isEmpty() && labelViews != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'*')]"));

			// retrieving count of fields displaying until last element display
			countOfAllFields = labelViews.size();
			System.out.println(".... After swiping, entity fields count .... : " + countOfAllFields);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < labelViews.size(); i++) {
				// printing the elements in the list
				System.out.println("===== Entity fields text ===== : " + labelViews.get(i).getText());

				// if list matches with last element the loop will break
				if (labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out
							.println("----- Entity fields text inside elements ----- : " + labelViews.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		MobileActionGesture.flingToBegining_Android();

		// inserting entity fields
		insertingEntityFields(labelViews, countOfAllFields);
	}

	//highlighting background color based on field value
	public static void highlighting_background_color_based_on_fieldValue(String inputValue, String dependencyField)
			throws MalformedURLException {

		String colorFieldType = dependencyField;
		int fieldValue = Integer.parseInt(inputValue);

		// label list fields
		List<MobileElement> labelViews = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]");

		// get all formfields elements of text
		List<MobileElement> entitySpecifiedField = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'"
						+ colorFieldType + "')]");

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
		System.out.println("**** Last element text **** : " + lastEntityTextElement);

		// removing entity fields from list
		labelViews.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding both list fields of first screen of mobile
		entitySpecifiedField.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'"
						+ colorFieldType + "')]"));
		// retrieve count of fields displaying in first screen
		countOfAllFields = entitySpecifiedField.size();
		System.out.println(" ---- Before swiping, entity fields count ---- : " + countOfAllFields);

		// scroll and add list fields(labelView) till last text element find
		while (entitySpecifiedField.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			entitySpecifiedField.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'"
							+ colorFieldType + "')]"));

			labelViews.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text')]"));

			// retrieving count of fields displaying until last element display
			countOfAllFields = entitySpecifiedField.size();
			System.out.println(".... After swiping, entity fields count .... : " + countOfAllFields);

			// verify last text element if finds then exit for loop
			for (int i = 0; i < countOfAllFields; i++) {
				// printing the elements in the list
				System.out.println("===== Entity fields text ===== : " + entitySpecifiedField.get(i).getText());

				// if list matches with last element the loop will break
				if (entitySpecifiedField.size() > 0 || labelViews.get(i).getText().equals(lastEntityTextElement)) {
					System.out.println("----- Entity fields text inside elements ----- : "
							+ entitySpecifiedField.get(i).getText());
					flag = true;
				}
			}
			// exit while loop
			if (flag == true)
				break;
		} // while loop close

		MobileActionGesture.flingToBegining_Android();

		// insert field value and hightlighting background value
		Customer_Advancesetting.insertValueForHighlightingBackgroungValue(entitySpecifiedField, countOfAllFields,
				colorFieldType, fieldValue);
	}
	
	// date error and warn meessage validation
	public static void dateErrorAndWarnMessageValidation(String workFieldsText, String dateInput, String errorCondition)
			throws ParseException, InterruptedException, MalformedURLException {

		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(workFieldsText);

		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

		java.util.Date date = new java.util.Date();
		// formating current date using date formatter
		String toDaydate = DateFor.format(date);
		// printing current date
		System.out.println("**** Todays date is **** : " + toDaydate);

		// parse the today date
		Date presentDate = DateFor.parse(toDaydate);
		// print parsing today date
		System.out.println(".... After parsing today date is ... : " + presentDate);

		// parsing given date from string
		date = DateFor.parse(dateInput);
		// formating input date into our date formatter
		String formatGivenDate = DateFor.format(date);
		// printing given date
		System.out.println("---- Given date is ---- : " + formatGivenDate);

		for (int p = 0; p < 3; p++) {
			// Number of Days to add
			date = DateUtils.addDays(date, -1);
			// conversion of date
			String inputDate = DateFor.format(date);

			// Printing customized date
			System.out.println(" **** My given date is **** : " + inputDate);
			
			// swipe to specified field
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

			if (CommonUtils.getdriver()
					.findElements(MobileBy
							.xpath("//*[@text='" + workFieldsText + "']/parent::*/parent::*/android.widget.Button"))
					.size() > 0) {
				AndroidLocators.clickElementusingXPath(
						"//*[@text='" + workFieldsText + "']/parent::*/parent::*/android.widget.Button");

				// providing the given input
				Forms_basic.getCalendarDates(inputDate);
			} else {
				Work_advanceSettings.selectInputBasedOnAboveOrBelowElement(getAboveOrBelowOfMainElement);
				// providing the given input
				Forms_basic.getCalendarDates(inputDate);
			}

			// validating date based on codition
			if (errorCondition.equals("Show Error when")) {
				// click on save button
				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);
			} else if (errorCondition.equals("Show Warning when")) {
				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					CustomerPageActions.fillCustomerMandatoryFields();
					// customer save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
			}

			// adding new date
			date = DateUtils.addDays(date, 2);
			// format the increased date
			String newIncreasedDate = DateFor.format(date);
			// printing the increased date
			System.out.println("**** After increasing the date **** : " + newIncreasedDate);
		}
	}
}
