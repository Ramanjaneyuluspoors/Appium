package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.CommonUtils;
import utils.MediaPermission;

public class CustomerPageActions {
	public static String randomstringCusName;

	// customer in home page
	public static void verifyCustomerInHomePage(String customer) {
		try {
			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
							+ customer + "\").instance(0))"));
			if (AndroidLocators.returnUsingId("//*[contains(@text,'" + customer + "')]").isDisplayed()) {
				AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + customer + "')]");
			}
		} catch (Exception e) {
			try {
				CommonUtils.homeFabClick();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			AndroidLocators.clickElementusingXPath("//*[@text='" + customer + "']");
		}
	}

	// search for customer
	public static void customerSearch(String customerName) throws MalformedURLException {
		CommonUtils.waitForElementVisibility("//*[@text='Customers']");
		AndroidLocators.clickElementusingID("action_search");
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(customerName);
		AndroidLocators.pressEnterKeyInAndroid();
	}

	// if customer not exist then create
	public static void verifyCusExistOrNot(String customerName) throws MalformedURLException, InterruptedException {
		if (AndroidLocators.findElements_With_Xpath("//*[@text='" + customerName + "']").size() > 0) {
			System.out.println("Customer found!!");
		} else {
			System.out.println("customer not found create customer!!");
			customerFab();
			createCustomer();
			searchForCreatedCustomer();
		}
	}

	// click on '+' icon in customer to create new customer
	public static void customerFab() throws MalformedURLException {
		MobileElement cusFab = AndroidLocators.returnUsingId("customersFab");
		MobileActionGesture.tapByElement(cusFab);
		CommonUtils.waitForElementVisibility("//*[@text='Customer']");
	}

	// inserting data for customer creation
	public static String createCustomer() throws MalformedURLException, InterruptedException {
		MobileElement customerName = AndroidLocators.returnUsingId("nameEditText");
		randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
		customerName.sendKeys(randomstringCusName);
		MobileElement customerType = AndroidLocators.returnUsingId("typeSpinner");
		customerType.click();
		CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView")).get(1).click();
//		captureImage();
//		captureVideo();
//		captureAudio();
		saveMethod();
		CommonUtils.waitForElementVisibility("//*[@text='Customers']");
		System.out.println("customer is created ");
		return randomstringCusName;

	}

	// image capture
	public static void captureImage() throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("Image");
		MobileElement imageCapture = AndroidLocators
				.xpath("//*[starts-with(@text,'Image')]/parent::*//*[@class='android.widget.Button']");
		MobileActionGesture.tapByElement(imageCapture);
		MediaPermission.mediaPermission();
		CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.CAMERA));
//		CommonUtils.getdriver()
//				.findElementByXPath("//*[@resource-id='com.google.android.GoogleCamera:id/shutter_button']").click();
//		CommonUtils.getdriver()
//				.findElement(MobileBy.xpath("//android.widget.ImageButton[@content-desc='Done']")).click();
//		CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
	}

	// video capture
	public static void captureVideo() throws MalformedURLException, InterruptedException {
		MobileActionGesture.scrollTospecifiedElement("Video");
		MobileElement videoCapture = AndroidLocators
				.xpath("//*[starts-with(@text,'Video')]/parent::*//*[@class='android.widget.Button']");
		MobileActionGesture.tapByElement(videoCapture);
		MediaPermission.mediaPermission();
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Start video']");
		Thread.sleep(3000);
		AndroidLocators.clickElementusingXPath("//*[@content-desc='Stop video']");
		AndroidLocators.clickElementusingXPath("//*[@resource-id='com.google.android.GoogleCamera:id/shutter_button']");
		CommonUtils.waitForElementVisibility("//*[@text='PLAY']");
	}

	// audio capture
	public static void captureAudio() throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("Audio");
		MobileElement audioCapture = AndroidLocators
				.xpath("//*[starts-with(@text,'Audio')]/parent::*//*[@class='android.widget.Button']");
		MobileActionGesture.tapByElement(audioCapture);
		MediaPermission.mediaPermission();
		CommonUtils.waitForElementVisibility("//*[@text='Audio recorder']");
		MobileElement startRecording = AndroidLocators.xpath("//*[@text='START RECORDING']");
		MobileActionGesture.singleLongPress(startRecording);
		CommonUtils.waitForElementVisibility("//*[@text='STOP RECORDING']");
		MobileElement stopRecording = AndroidLocators.xpath("//*[@text='STOP RECORDING']");
		MobileActionGesture.singleLongPress(stopRecording);
		CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
	}

	// customer save button
	public static void saveMethod() {
		MobileElement customerSaveButton = AndroidLocators.returnUsingId("saveCustomer");
		customerSaveButton.click();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Open drawer']");
	}

	// search for created customer
	public static void searchForCreatedCustomer() throws MalformedURLException, InterruptedException {
		CommonUtils.openMenu();
		CommonUtils.waitForSyncButton();
		CommonUtils.sync_in_progress();
		MobileActionGesture.horizontalSwipeByPercentage(0.7, 0.3, 0.5);
		customerSearch(randomstringCusName);
	}

	// verify customer check-in
	public static void veirfyCusCheckin() throws MalformedURLException, InterruptedException {
		MobileElement cusCheckin = AndroidLocators.returnUsingId("checkinoutButton");
		if (cusCheckin.getText().contains("OFF")) {
			System.out.println(".... User is going to checkin to customer .... ");
			cusCheckin.click();
			checkInOrCheckOutAnyway();
		} else {
			System.out.println("----- User already checked into customer, perform customer activity ----- ");
			AndroidLocators.clickElementusingID("summaryBtn");
		}
	}

	// check-in or checkout anyway alert
	public static void checkInOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkin = AndroidLocators.resourceId("android:id/button1");
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.keyboardHide();
			AndroidLocators.clickElementusingXPath("//*[@text='SUBMIT']");
			CommonUtils.waitForElementVisibility("//*[@text='Customers']");
			customerSearch(randomstringCusName); // two ways for search after checkout anyway
			veirfyCusCheckin();
		}
	}

	// moving to customer activity screen
	public static void goToActivityScreen() throws MalformedURLException, InterruptedException {
		try {
			CommonUtils.wait(2);
			if (AndroidLocators.findElements_With_Id("View_all_details").size() > 0) {
				AndroidLocators.clickElementusingID("View_all_details");
			} else {
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/View_all_details']");
				AndroidLocators.clickElementusingID("View_all_details");
			}
			if (AndroidLocators.findElements_With_Id("addTask").size() > 0) {
				MobileElement addActivity = AndroidLocators.returnUsingId("addTask");
				MobileActionGesture.tapByElement(addActivity);
				CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
			} else {
				CommonUtils.waitForElementVisibility("in.spoors.effortplus:id/addTask");
				AndroidLocators.clickElementusingID("addTask");
				CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// click on '+' to move activity screen from customer screen
	public static void performAcitivity_using_add_symbol() {
		CommonUtils.goBackward();
		if (AndroidLocators.findElements_With_Id("addButton").size() > 0) {
			AndroidLocators.clickElementusingID("addButton");
			CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
		} else {
			CommonUtils.waitForElementVisibility("in.spoors.effortplus:id/addButton");
			AndroidLocators.clickElementusingID("addButton");
			CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
		}
		//// *[@text='C1']/parent::*/parent::*/ancestor::android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[@id='addLayout']//*[@id='addButton']
	}

	// moving to activity screen from home page
	public static void perform_Activity_from_homeScreen() {
		if (AndroidLocators.findElements_With_Id("checkInCustomerName").size() > 0) {
			MobileElement getChecked_in_Customer = AndroidLocators.returnUsingId("checkInCustomerName");
			System.out.println(".... customer check-in .... :" + getChecked_in_Customer.getText());
			CommonUtils.getdriver().findElement(MobileBy.id("customerMenuItem")).click();
			if (AndroidLocators.xpath("//*[@text='Add activity']").isDisplayed()) {
				AndroidLocators.xpath("//*[@text='Add activity']").click();
				CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
			}
		}
	}

	// click on specified form to peform customer activity
	public static void clickActivity(String formName) throws MalformedURLException, InterruptedException {
		if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + formName + "')]").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + formName + "')]");
			System.out.println("form with name is displayed and clicked");
		} else {
			System.out.println("form name is not displayed, clicking on first activity");
			CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
		}
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/saveForm']");
	}

	// checkout customer in homepage
	public static void HomepageCusCheckout() throws MalformedURLException, InterruptedException {
		CommonUtils.goBackward();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
		cusCheckoutInHomepage();
	}

	// checkout in customer screen
	public static void checkout_in_customer_screen() throws MalformedURLException, InterruptedException {
		navigate_back();
		Thread.sleep(600);
		customer_Screen_checkout();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}

	// navigate back
	public static void navigate_back() {
		do {
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Navigate up']");
		} while (AndroidLocators.findElements_With_Xpath("//*[@content-desc='Navigate up']").size() > 0);
	}

	// checkout customer in home page
	public static void cusCheckoutInHomepage() throws MalformedURLException, InterruptedException {
		CommonUtils.wait(2);
		boolean isFound = false;
		if (AndroidLocators.findElements_With_Xpath("//*[@resource-id='in.spoors.effortplus:id/customerMenuItem']")
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/customerMenuItem").click();
			CommonUtils.waitForElementVisibility(
					"//*[@class='android.widget.FrameLayout' and ./*[@class='android.widget.ListView']]");
			List<MobileElement> checkout = CommonUtils.getdriver().findElements(MobileBy.id("title"));
			for (int i = 0; i < checkout.size(); i++) {
				if (checkout.get(i).getText().contains("Check out")) {
					AndroidLocators.clickElementusingXPath("//*[@text='Check out']");
					CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/parentPanel']");
					if (isFound = true)
						break;
				}
			}
			checkOutOrCheckOutAnyway();
		}
	}

	// checkout anyway alert
	public static void checkOutOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		MobileElement checkOut = AndroidLocators.returnUsingId("//*[@resource-id='android:id/button1']");
		if (checkOut.getText().contains("CHECK OUT")) {
			MobileActionGesture.tapByElement(checkOut);
		} else if (checkOut.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkOut);
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.keyboardHide();
			AndroidLocators.clickElementusingXPath("//*[@text='SUBMIT']");
		}
		CommonUtils.wait(5);
	}

	// handling customer check-in alert
	public static void customerCheckInReason() throws InterruptedException {
		if (AndroidLocators.findElements_With_Xpath("//*[@text='Select check in reason']").size() > 0) {
			AndroidLocators.clickElementusingXPath("//*[@text='Meeting']");
		} else if (AndroidLocators.findElements_With_Xpath("//*[@text='Enter check in reason']").size() > 0) {
			AndroidLocators.sendInputusing_Classname("android.widget.EditText");
			CommonUtils.keyboardHide();
			AndroidLocators.clickElementusingXPath("//*[@text='OK']");
		} else {
			System.out.println("No alert is displayed");
		}
		CommonUtils.wait(5);
	}

	// customer checkout in customer screen
	public static void customer_Screen_checkout() throws MalformedURLException, InterruptedException {
		if (AndroidLocators.findElements_With_Id("checkinoutButton").size() > 0) {
			if (CommonUtils.SwitchStatus("checkinoutButton").contains("ON")) {
				AndroidLocators.clickElementusingXPath("//*[@class='android.widget.Switch'][@text='ON']");
				checkOutOrCheckOutAnyway();
			}
		} else if (CommonUtils.getdriver().findElementsByAndroidUIAutomator(
				"new UiSelector().resourceId(\"in.spoors.effortplus:id/checkinoutButton\")").size() > 0) {
			if (AndroidLocators.resourceId("in.spoors.effortplus:id/checkinoutButton").getText().contains("ON")) {
				AndroidLocators.resourceId("in.spoors.effortplus:id/checkinoutButton").click();
				checkOutOrCheckOutAnyway();
			}
		} else {
			AndroidLocators.clickElementusingXPath("//*[@class='android.widget.Switch'][@text='ON']");
			checkOutOrCheckOutAnyway();
		}
	}

	// customer checkout in customer details screen
	public static void checkout_customer_details_screen() throws MalformedURLException, InterruptedException {
		if (AndroidLocators.findElements_With_Id("checkinCheckoutSwitch").size() > 0) {
			if (CommonUtils.SwitchStatus("checkinCheckoutSwitch").contains("ON")) {
				AndroidLocators.clickElementusingXPath("//*[@class='android.widget.Switch'][@text='ON']");
				checkOutOrCheckOutAnyway();
			}
		} else if (AndroidLocators.findElements_With_ResourceId("in.spoors.effortplus:id/checkinCheckoutSwitch")
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/checkinCheckoutSwitch").click();
			checkOutOrCheckOutAnyway();
		} else {
			AndroidLocators.clickElementusingXPath("//*[@class='android.widget.Switch'][@text='ON']");
			checkOutOrCheckOutAnyway();
		}
	}

	// customer creation
	public static void customerCreation() throws MalformedURLException, InterruptedException, ParseException {

		// customer fields
		List<MobileElement> customer_fields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]");

		// get size of list
		int custFieldsCount = customer_fields.size();
		System.out.println("**** Customer fields count **** :" + custFieldsCount);

		// removing elements from list
		customer_fields.clear();

		// find the last element to stop continuos scrolling
		String custLastElement = null;
		// scroll from top to bottom and add customerlist fields
		MobileActionGesture.flingVerticalToBottom_Android();

		// add customer fields to get last element of the customer fields
		customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		// get count of elements
		custFieldsCount = customer_fields.size();
		System.out.println("**** Customer fields in the last page are **** :" + custFieldsCount);

		// retrieving the last element of customer
		custLastElement = customer_fields.get(customer_fields.size() - 1).getText();
		System.out.println("==== customer last element ==== : " + custLastElement);

		// removing elelments from list(here we only need last element after finding
		// last element we are removing elements from list)
		customer_fields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// add first screen elements of customer to list
		customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/nameEditLayout']/parent::android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"));

		// get count of elements available in the first screen are
		custFieldsCount = customer_fields.size();
		System.out.println("*** customer first screen fields count *** : " + custFieldsCount);

		// scroll and add elelemnts to list until last element found
		while (!customer_fields.isEmpty() && customer_fields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/nameEditLayout']/parent::android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"));

			custFieldsCount = customer_fields.size();
			System.out.println("... After swiping customer fields count is ... : " + custFieldsCount);

			for (int l = 0; l < custFieldsCount; l++) {
				System.out.println("*** Fields Inside loop *** : " + customer_fields.get(l).getText());

				if (customer_fields.get(l).getText().contains(custLastElement)) {
					System.out.println("---- customer inside elements ---- : " + customer_fields.get(l).getText());
					flag = true;
				}
			}
			// break the loop once last element found
			if (flag == true) {
				break;
			}
		}

		MobileActionGesture.flingToBegining_Android();
		boolean isName = false, isStreet = false, isArea = false, isLandmark = false, isCity = false, isState = false,
				isDistrict = false, isPincode = false, isDate = false, isText = false, isURL = false, isEmail = false,
				isCountry = false, isEmployee = false, isPhoneNumber = false, isDateTime = false, isTime = false,
				isPickList = false, isMultiPickList = false, isPhone = false, isMultiSelectDropdown = false,
				isLocation = false, isCurrency = false, isNumber = false, isDropdown = false, isYesNo = false,
				isCustomer = false, isForm = false, isCustomEntity = false, isSignature = false;

		// providing input for customer fields by iterating the customer list
		for (int j = 0; j < custFieldsCount; j++) {
			String originalText = customer_fields.get(j).getText();
			String cusFieldsText = customer_fields.get(j).getText().replaceAll("[\\(!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("---- Before removing special character ---- : " + originalText
					+ "\n .... After removing special character .... : " + cusFieldsText);

			switch (cusFieldsText) {
			case "Type":
				MobileActionGesture.scrollUsingText(originalText);
				if (AndroidLocators.findElements_With_Xpath("//*[@text='" + cusFieldsText + "']").size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					MobileElement type = AndroidLocators
							.xpath("//*[@text='" + originalText + "']/parent::*/android.widget.Spinner");
					MobileActionGesture.singleLongPress(type);
					if (AndroidLocators.findElements_With_ClassName("android.widget.CheckedTextView").size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						CommonUtils.wait(3);
					}
				}
				break;
			case "Name":
				if (!isName) {
					MobileActionGesture.scrollUsingText(cusFieldsText);
					if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
							.size() > 0) {
						String randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("nameEditText")).sendKeys(randomstringCusName);
					}
					isName = true;
				}
				break;
			case "Territory Type":
				MobileActionGesture.scrollUsingText(originalText);
				if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
					MobileElement territoryType = AndroidLocators
							.xpath("//*[@text='" + originalText + "']/parent::*/android.widget.Spinner");
					MobileActionGesture.singleLongPress(territoryType);
					if (AndroidLocators.findElements_With_ClassName("android.widget.CheckedTextView").size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						CommonUtils.wait(3);
					}
				}
				break;
			case "Phone number":
				if (!isPhoneNumber) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators
								.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + originalText + "')]");
					}
					isPhoneNumber = true;
				}
				break;
			case "Street":
				if (!isStreet) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("streetEditText");
					}
					isStreet = true;
				}
				break;
			case "Area":
				if (!isArea) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("areaEditText");
					}
					isArea = true;
				}
				break;
			case "Landmark":
				if (!isLandmark) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("landmarkEditText");
					}
					isLandmark = true;
				}
				break;
			case "City":
				if (!isCity) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("cityEditText");
					}
					isCity = true;
				}
				break;
			case "State":
				if (!isState) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("stateEditText");
					}
					isState = true;
				}
				break;
			case "District":
				if (!isDistrict) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("districtEditText");
					}
					isDistrict = true;
				}
				break;
			case "PIN code":
				if (!isPincode) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[@text='" + originalText + "']").size() > 0) {
						AndroidLocators.sendInputusing_Id("pinCodeEditText");
					}
					isPincode = true;
				}
				break;
			case "Phone":
				if (isPhone) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators
								.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + originalText + "')]");
					}
					isPhone = true;
				}
				break;
			case "Employee":
				if (!isEmployee) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A EMPLOYEE')]")
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A EMPLOYEE')]");
							if (AndroidLocators.findElements_With_Id("employeeNameTextView").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0)
										.click();
								CommonUtils.wait(3);
							}
						} else if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button").size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button");
							if (AndroidLocators.findElements_With_Id("employeeNameTextView").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0)
										.click();
								CommonUtils.wait(3);
							}
						}
					}
					isEmployee = true;
				}
				break;
			case "Multi Pick List":
				if (!isMultiPickList) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						MobileElement multipicklist = AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button");
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
						CommonUtils.wait(3);
					}
					isMultiPickList = true;
				}
				break;
			case "Pick List":
				if (!isPickList) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators
								.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]")
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]");
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0)
									.isDisplayed()) {
								CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1)
									.isDisplayed()) {
								CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
							}
							CommonUtils.wait(3);
						} else {
							System.out.println("Pick List is already picked");
						}
					}
					isPickList = true;
				}
				break;
			case "Form":
				if (!isForm) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]")
								.size() > 0) {
							MobileElement form = AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]");
							MobileActionGesture.tapByElement(form);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							try {
								if (AndroidLocators.findElements_With_Id("form_id_text_view").size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									AndroidLocators.clickElementusingID("load_more_button");
									CommonUtils.waitForElementVisibility(
											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
									if (AndroidLocators.findElements_With_Id("form_id_text_view").size() > 0) {
										CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
												.click();
									} else {
										AndroidLocators.clickElementusingID("fab");
										CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
										Forms_basic.verifyFormPagesAndFill();
										Forms_basic.formSaveButton();
										CommonUtils.waitForElementVisibility(
												"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
										if (AndroidLocators.findElements_With_Id("form_id_text_view").size() > 0) {
											CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
													.get(0).click();
										}
									}
								}
								CommonUtils.wait(3);
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
			case "Location":
			case "G-Location":
				if (!isLocation) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators.clickElementusingID("pick_location_button");
						CommonUtils.wait(10);
						CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
						AndroidLocators.xpath("//*[@text='MARK MY LOCATION']").click();
						AndroidLocators.xpath("//*[@text='USE MARKED LOCATION']").click();
					} else {
						MobileActionGesture.scrollUsingResourceId("in.spoors.effortplus:id/pick_location_button");
						AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/pick_location_button");
						CommonUtils.wait(10);
						CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
						AndroidLocators.xpath("//*[@text='MARK MY LOCATION']").click();
						AndroidLocators.xpath("//*[@text='USE MARKED LOCATION']").click();
					}
					CommonUtils.waitForElementVisibility(
							"//*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					isLocation = true;
				}
				break;
			case "YesNo":
			case "G-YesNo":
				if (!isYesNo) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						MobileElement cusYesNo = AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
								+ "')/parent::*/parent::*/android.widget.Spinner");
						MobileActionGesture.singleLongPress(cusYesNo);
						if (AndroidLocators.findElements_With_ClassName("android.widget.CheckedTextView").size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					isYesNo = true;
				}
				break;
			case "URL":
			case "G-URL":
				if (!isURL) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators.sendUrlInputusing_XPath("//*[starts-with(@text,'" + originalText + "')]");
					}
					isURL = true;
				}
				break;
			case "Email":
			case "G-Email":
				if (!isEmail) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators.sendEmailInputusing_XPath("//*[starts-with(@text,'" + originalText + "')]");
					}
					isEmail = true;
				}
				break;
			case "Text":
			case "G-Text":
				if (!isText) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + originalText + "')]");
					}
					isText = true;
				}
				break;
			case "Country":
			case "G-Country":
				if (!isCountry) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Spinner//*[contains(@text,'Pick a country')]")
								.size() > 0) {
							MobileElement country = AndroidLocators.xpath("//*[contains(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Spinner//*[contains(@text,'Pick a country')]");
							MobileActionGesture.singleLongPress(country);
							MobileActionGesture.scrollTospecifiedElement("Angola");
						} else {
							System.out.println("Country is already selected");
						}
					}
					isCountry = true;
				}
				break;
			case "Time":
			case "G-Time":
				if (!isTime) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]")
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]");
							Forms_basic.TimeScriptInForms(1, 1);
							CommonUtils.wait(3);
						} else {
							System.out.println("... Time is already picked ...");
						}
					}
					isTime = true;
				}
				break;
			case "DateTime":
			case "G-DateTime":
				if (!isDateTime) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]")
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]");
							try {
								Forms_basic.dateScriptInForms(1);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}
							CommonUtils.wait(3);
						}
						if (AndroidLocators
								.findElements_With_Xpath("//*[@text='" + originalText
										+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]")
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[@text='" + originalText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]");
							Forms_basic.TimeScriptInForms(1, 5);
							CommonUtils.wait(3);
						} else {
							System.out.println("DateTime is already picked");
						}
					}
					isDateTime = true;
				}
				break;
			case "Date":
			case "G-Date":
				if (!isDate) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]")
								.size() > 0) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]");
							try {
								Forms_basic.dateScriptInForms(1);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}
							CommonUtils.wait(5);
						} else {
							System.out.println("Date is already picked");
						}
					}
					isDate = true;
				}
			case "Number":
			case "G-Number":
				if (isNumber) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators.sendNumberInputUsing_xpath("//*[starts-with(@text,'" + originalText + "')]");
					}
					isNumber = true;
				}
			case "Customer":
			case "G-Cutomer":
				if (isCustomer) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")
								.isEnabled()) {
							AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
							CommonUtils.waitForElementVisibility("//*[@text='Customers']");
							if (AndroidLocators.findElements_With_Id("item_id").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
							} else {
								CustomerPageActions.customerFab();
								CustomerPageActions.createCustomer();
								CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
								AndroidLocators.clickElementusingXPath(
										"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
							}
							CommonUtils.wait(3);
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
				if (isCustomEntity) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators
								.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]")
								.size() > 0) {
							MobileElement customEntity = AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]");
							MobileActionGesture.tapByElement(customEntity);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (AndroidLocators.findElements_With_Id("entityTitle").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
							} else if (AndroidLocators.findElements_With_Id("custom_entity_card").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(0).click();
							} else {
								// write entity item creation method
								Forms_basic.createEntity();
								AndroidLocators.clickElementusingXPath("//*[@content-desc='Save']");
								CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
								CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
							}
							CommonUtils.wait(3);
						} else {
							System.out.println("Custom entity is already picked");
						}
					}
					isCustomEntity = true;
				}
				break;
			case "Signature":
			case "G-Signature":
				if (isSignature) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						MobileElement signature = AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button[1]");
						MobileActionGesture.tapByElement(signature);
						MediaPermission.mediaPermission();
						CommonUtils.waitForElementVisibility("//*[@text='Signature']");
						MobileElement signatureCapture = AndroidLocators.xpath("//*[@text='CAPTURE']"); // id("saveButton")
						MobileActionGesture.singleLongPress(signatureCapture);
						CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
						CommonUtils.wait(3);
						// CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.CAMERA));
					}
					isSignature = true;
				}
				break;
			case "Currency":
			case "G-Currency":
				if (isCurrency) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						AndroidLocators.sendNumberInputUsing_xpath("//*[starts-with(@text,'" + originalText + "')]");
					}
					isCurrency = true;
				}
				break;
			case "Dropdown":
			case "G-Dropdown":
				if (!isDropdown) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]")
								.size() > 0) {
							MobileElement dropdown = AndroidLocators.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]");
							MobileActionGesture.singleLongPress(dropdown);
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						} else {
							System.out.println("Dropdown is already selected");
						}
					}
					isDropdown = true;
				}
				break;
			case "Multi Select Dropdown":
			case "G-Multi Select Dropdown":
				if (!isMultiSelectDropdown) {
					MobileActionGesture.scrollUsingText(originalText);
					if (AndroidLocators.findElements_With_Xpath("//*[starts-with(@text,'" + originalText + "')]")
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement multiSelectDropdown = AndroidLocators.xpath("//*[starts-with(@text,'"
								+ originalText + "')]/parent::*/parent::*/android.widget.Button");
						MobileActionGesture.tapByElement(multiSelectDropdown);
						CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
						List<MobileElement> pickValues = AndroidLocators
								.findElements_With_ClassName("android.widget.CheckedTextView");
						if (pickValues.get(0).isDisplayed()) {
							try {
								MobileActionGesture.singleLongPress(pickValues.get(0));
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (pickValues.get(1).isDisplayed()) {
							MobileActionGesture.singleLongPress(pickValues.get(1));
						}
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						CommonUtils.wait(3);
					}
					isMultiSelectDropdown = true;
				}
				break;
			}
		}
	}

}
