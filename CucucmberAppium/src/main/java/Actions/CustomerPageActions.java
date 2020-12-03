package Actions;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.AndroidLocators;
import utils.CommonUtils;
import utils.Forms;
import utils.MediaPermission;

public class CustomerPageActions {
	public static String randomstringCusName;
	
	//customer in home page
	public static void verifyCustomerInHomePage(String customer) {
		try {
			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
							+ customer + "\").instance(0))"));
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + customer + "')]"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + customer + "')]")).click();
			}
		} catch (Exception e) {
			try {
				CommonUtils.homeFabClick();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + customer + "']")).click();
		}
	}

	// search for customer
	public static void customerSearch(String customerName) throws MalformedURLException {
		CommonUtils.waitForElementVisibility("//*[@text='Customers']");
		CommonUtils.getdriver().findElement(MobileBy.id("action_search")).click();
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(customerName);
		CommonUtils.pressEnterKeyInAndroid();
	}

	// if customer not exist then create
	public static void verifyCusExistOrNot(String customerName) throws MalformedURLException, InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + customerName + "']")).size() > 0) {
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
		MobileElement cusFab = CommonUtils.getdriver().findElement(MobileBy.id("customersFab"));
		MobileActionGesture.tapByElement(cusFab);
		CommonUtils.waitForElementVisibility("//*[@text='Customer']");
	}

	// inserting data for customer creation
	public static String createCustomer() throws MalformedURLException, InterruptedException {
		MobileElement customerName = CommonUtils.getdriver().findElement(MobileBy.id("nameEditText"));
		randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
		customerName.sendKeys(randomstringCusName);
		MobileElement customerType = CommonUtils.getdriver().findElement(MobileBy.id("typeSpinner"));
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
		MobileElement imageCapture = CommonUtils.getdriver()
				.findElementByXPath("//*[starts-with(@text,'Image')]/parent::*//*[@class='android.widget.Button']");
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
		MobileElement videoCapture = CommonUtils.getdriver()
				.findElementByXPath("//*[starts-with(@text,'Video')]/parent::*//*[@class='android.widget.Button']");
		MobileActionGesture.tapByElement(videoCapture);
		MediaPermission.mediaPermission();
		CommonUtils.getdriver().findElementByXPath("//*[@content-desc='Start video']").click();
		Thread.sleep(3000);
		CommonUtils.getdriver().findElementByXPath("//*[@content-desc='Stop video']").click();
		CommonUtils.getdriver()
				.findElementByXPath("//*[@resource-id='com.google.android.GoogleCamera:id/shutter_button']").click();
		CommonUtils.waitForElementVisibility("//*[@text='PLAY']");
	}

	// audio capture
	public static void captureAudio() throws MalformedURLException {
		MobileActionGesture.scrollTospecifiedElement("Audio");
		MobileElement audioCapture = CommonUtils.getdriver()
				.findElementByXPath("//*[starts-with(@text,'Audio')]/parent::*//*[@class='android.widget.Button']");
		MobileActionGesture.tapByElement(audioCapture);
		MediaPermission.mediaPermission();
		CommonUtils.waitForElementVisibility("//*[@text='Audio recorder']");
		MobileElement startRecording = CommonUtils.getdriver().findElementByXPath("//*[@text='START RECORDING']");
		MobileActionGesture.singleLongPress(startRecording);
		CommonUtils.waitForElementVisibility("//*[@text='STOP RECORDING']");
		MobileElement stopRecording = CommonUtils.getdriver().findElementByXPath("//*[@text='STOP RECORDING']");
		MobileActionGesture.singleLongPress(stopRecording);
		CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
	}

	// customer save button
	public static void saveMethod() {
		MobileElement customerSaveButton = CommonUtils.getdriver().findElement(MobileBy.id("saveCustomer"));
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
		MobileElement cusCheckin = CommonUtils.getdriver().findElement(MobileBy.id("checkinoutButton"));
		if (cusCheckin.getText().contains("OFF")) {
			System.out.println("user is going to checkin to customer");
			cusCheckin.click();
			checkInOrCheckOutAnyway();
		} else {
			System.out.println("User already checked into customer, perform customer activity");
			CommonUtils.getdriver().findElement(MobileBy.id("summaryBtn")).click();
		}
	}
 
	// check-in or checkout anyway alert
	public static void checkInOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkin = CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"));
		if (checkin.getText().contains("CHECK IN")) {
			MobileActionGesture.tapByElement(checkin);
			customerCheckInReason();
		} else if (checkin.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkin);
			String randomstring = RandomStringUtils.randomAlphabetic(5).toLowerCase();
			CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).sendKeys(randomstring);
			CommonUtils.keyboardHide();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SUBMIT']")).click();
			CommonUtils.waitForElementVisibility("//*[@text='Customers']");
			customerSearch(randomstringCusName); // two ways for search after checkout anyway
			veirfyCusCheckin();
		}
	}

	// moving to customer activity screen
	public static void goToActivityScreen() throws MalformedURLException, InterruptedException {
		try {
			CommonUtils.wait(2);
			if (CommonUtils.getdriver().findElements(MobileBy.id("View_all_details")).size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.id("View_all_details")).click();
			} else {
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/View_all_details']");
				CommonUtils.getdriver().findElement(MobileBy.id("View_all_details")).click();
			}
			if (CommonUtils.getdriver().findElements(MobileBy.id("addTask")).size() > 0) {
				MobileElement addActivity = CommonUtils.getdriver().findElement(MobileBy.id("addTask"));
				MobileActionGesture.tapByElement(addActivity);
				CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
			} else {
				CommonUtils.waitForElementVisibility("in.spoors.effortplus:id/addTask");
				CommonUtils.getdriver().findElement(MobileBy.id("addTask")).click();
				CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// click on '+' to move activity screen from customer screen
	public static void performAcitivity_using_add_symbol() {
		CommonUtils.goBackward();
		if (CommonUtils.getdriver().findElements(MobileBy.id("addButton")).size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.id("addButton")).click();
			CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
		} else {
			CommonUtils.waitForElementVisibility("in.spoors.effortplus:id/addButton");
			CommonUtils.getdriver().findElement(MobileBy.id("addButton")).click();
			CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
		}
		//// *[@text='C1']/parent::*/parent::*/ancestor::android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[@id='addLayout']//*[@id='addButton']
	}
	
	//moving to activity screen from home page 
	public static void perform_Activity_from_homeScreen() {
		if (CommonUtils.getdriver().findElements(MobileBy.id("checkInCustomerName")).size() > 0) {
			MobileElement getChecked_in_Customer = CommonUtils.getdriver().findElement(MobileBy.id("checkInCustomerName"));
			System.out.println(".... customer check-in .... :"+getChecked_in_Customer.getText());
			CommonUtils.getdriver().findElement(MobileBy.id("customerMenuItem")).click();
			if(AndroidLocators.xpath("//*[@text='Add activity']").isDisplayed()) {
				AndroidLocators.xpath("//*[@text='Add activity']").click();
				CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
			}
		}
	}
	
	// click on specified form to peform customer activity
	public static void clickActivity(String formName) throws MalformedURLException, InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + formName + "')]"))
				.size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + formName + "')]")).click();
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
	
	//checkout in customer screen
	public static void checkout_in_customer_screen() throws MalformedURLException, InterruptedException {
		CommonUtils.goBackward();
		customer_Screen_checkout();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
	}
	
	

	// checkout customer in home page
	public static void cusCheckoutInHomepage() throws MalformedURLException, InterruptedException {
		CommonUtils.wait(2);
		boolean isFound = false;
		if (CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[@resource-id='in.spoors.effortplus:id/customerMenuItem']"))
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/customerMenuItem").click();
			CommonUtils.waitForElementVisibility(
					"//*[@class='android.widget.FrameLayout' and ./*[@class='android.widget.ListView']]");
			List<MobileElement> checkout = CommonUtils.getdriver().findElements(MobileBy.id("title"));
			for (int i = 0; i < checkout.size(); i++) {
				if (checkout.get(i).getText().contains("Check out")) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Check out']")).click();
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
		MobileElement checkOut = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@resource-id='android:id/button1']"));
		if (checkOut.getText().contains("CHECK OUT")) {
			MobileActionGesture.tapByElement(checkOut);
		} else if (checkOut.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkOut);
			String randomstring = RandomStringUtils.randomAlphabetic(5).toLowerCase();
			CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).sendKeys(randomstring);
			CommonUtils.keyboardHide();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SUBMIT']")).click();
		} 
		Thread.sleep(500);
	}

	//handling customer check-in alert
	public static void customerCheckInReason() throws InterruptedException {
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='Select check in reason']")).size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Meeting']")).click();
		} else if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Enter check in reason']"))
				.isDisplayed()) {
			String randomstring = RandomStringUtils.randomAlphabetic(5).toLowerCase();
			CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).sendKeys(randomstring);
			CommonUtils.keyboardHide();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
		}
		Thread.sleep(2000);
	}

	// customer checkout in customer screen
	public static void customer_Screen_checkout() throws MalformedURLException, InterruptedException {
		CommonUtils.SwitchStatus("checkinoutButton");
		if (CommonUtils.SwitchStatus("checkinoutButton").contains("ON")) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@class='android.widget.Switch'][@text='ON']"))
					.click();
			checkOutOrCheckOutAnyway();
		}
	}
	
	public static void customerCreation() throws MalformedURLException, InterruptedException, ParseException {
		List<MobileElement> customer_Labelfields = CommonUtils.getdriver().findElements(
				MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/label_for_view\")"));
		List<MobileElement> customer_input_fields = CommonUtils.getdriver()
				.findElements(MobileBy.className("android.widget.EditText"));
		List<MobileElement> mergeList = customer_Labelfields;
		mergeList.addAll(customer_input_fields);
		int custFieldsCount = mergeList.size();
		customer_Labelfields.clear();
		customer_input_fields.clear();
		mergeList.clear();
		
		
		String custLastElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		customer_Labelfields.addAll(CommonUtils.getdriver().findElements(MobileBy
				.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/label_for_view\")")));
		customer_input_fields
				.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		mergeList = customer_Labelfields;
		mergeList.addAll(customer_input_fields);
		custFieldsCount = mergeList.size();
		custLastElement = mergeList.get(mergeList.size() - 1).getText();
		System.out.println("==== customer last element ==== : "+custLastElement);
		mergeList.clear();
		customer_Labelfields.clear();
		customer_input_fields.clear();
		
		MobileActionGesture.flingToBegining_Android();
		//first screen
		customer_Labelfields.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//*[@resource-id='in.spoors.effortplus:id/nameEditLayout']/parent::android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView")));
		customer_input_fields
				.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		mergeList = customer_Labelfields;
		mergeList.addAll(customer_input_fields);
		custFieldsCount = mergeList.size();
		System.out.println("*** customer first screen fields count *** : "+custFieldsCount);
		
		
		while (!mergeList.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			customer_Labelfields.addAll(CommonUtils.getdriver().findElements(MobileBy
					.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/label_for_view\")")));
			customer_input_fields
					.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
			mergeList = customer_Labelfields;
			mergeList.addAll(customer_input_fields);
			custFieldsCount = mergeList.size();
			System.out.println("... After swiping customer fields count is ... : " + custFieldsCount);
			for (int l = 0; l < custFieldsCount; l++) {
				System.out.println("*** Fields Inside loop *** : " + mergeList.get(l).getText());
				if (mergeList.get(l).getText().contains(custLastElement)) {
					System.out.println("---- customer inside elements ---- : " + mergeList.get(l).getText());
					flag = true;
				}
			}
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
		
		//providing input for customer fields by iterating the customer list
		for (int j = 0; j < custFieldsCount; j++) {
			String originalText = mergeList.get(j).getText();
			String cusFieldsText = mergeList.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("---- Before removing special character ---- : " + originalText
					+ "\n .... After removing special character .... : " + cusFieldsText);
			
			switch (cusFieldsText) {
			case "Type":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + cusFieldsText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					MobileElement type = CommonUtils.getdriver().findElement(
							MobileBy.xpath("//*[@text='" + originalText + "']/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(type);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						CommonUtils.wait(5);
					}
				}
				break;
			case "Name":
				if (!isName) {
					MobileActionGesture.scrollUsingText(cusFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[contains(@text,'" + cusFieldsText + "')]")).size() > 0) {
						MobileActionGesture.scrollUsingText(cusFieldsText);
						String randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("nameEditText")).sendKeys(randomstringCusName);
					}
					isName = true;
				}
				break;
			case "Territory Type":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					MobileElement territoryType = CommonUtils.getdriver().findElement(
							MobileBy.xpath("//*[@text='" + originalText + "']/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(territoryType);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						Thread.sleep(100);
					}
				}
				break;
			case "Phone number":
				if (!isPhoneNumber) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusPhoneNum = RandomStringUtils.randomNumeric(10);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(cusPhoneNum);
					}
					isPhoneNumber = true;
				}
				break;
			case "Street":
				if (!isStreet) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusAddressStreet = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("streetEditText")).sendKeys(cusAddressStreet);
					}
					isStreet = true;
				}
				break;
			case "Area":
				if (!isArea) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusArea = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("areaEditText")).sendKeys(cusArea);
					}
					isArea = true;
				}
				break;
			case "Landmark":
				if (!isLandmark) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusLandmark = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("landmarkEditText")).sendKeys(cusLandmark);
					}
					isLandmark = true;
				}
				break;
			case "City":
				if (!isCity) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusCity = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("cityEditText")).sendKeys(cusCity);
					}
					isCity = true;
				}
				break;
			case "State":
				if (!isState) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusState = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("stateEditText")).sendKeys(cusState);
					}
					isState = true;
				}
				break;
			case "District":
				if (!isDistrict) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusDistrict = RandomStringUtils.randomAlphabetic(5).toLowerCase();
						CommonUtils.getdriver().findElement(MobileBy.id("districtEditText")).sendKeys(cusDistrict);
					}
					isDistrict = true;
				}
				break;
			case "PIN code":
				if (!isPincode) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String cusPincode = RandomStringUtils.randomNumeric(6);
						CommonUtils.getdriver().findElement(MobileBy.id("pinCodeEditText")).sendKeys(cusPincode);
					}
					isPincode = true;
				}
				break;
			case "Phone(Optional)":
				if (isPhone) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String phoneNum = RandomStringUtils.randomNumeric(10);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(phoneNum);
					}
					isPhone = true;
				}
				break;
			case "Employee":
				if (!isEmployee) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A EMPLOYEE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A EMPLOYEE')]"))
									.click();
							if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0)
										.click();
								CommonUtils.wait(3);
							}
						} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
								+ originalText + "')]/parent::*/parent::*/android.widget.Button")).size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button")).click();
							if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
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
						CommonUtils.wait(3);
					}
					isMultiPickList = true;
				}
				break;
			case "Pick List":
				if (!isPickList) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
									.click();
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"))
								.size() > 0) {
							MobileElement form = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
											+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"));
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
										Forms.verifyFormPagesAndFill();
										CommonUtils.waitForElementVisibility(
												"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
										if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
												.size() > 0) {
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						CommonUtils.getdriver().findElement(MobileBy.id("pick_location_button")).click();
						Thread.sleep(5000);
						CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
						CommonUtils.wait(3);
					}
					isLocation = true;
				}
				break;
			case "YesNo":
			case "G-YesNo":
				if (!isYesNo) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement cusYesNo = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(cusYesNo);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					isYesNo = true;
				}
				break;
			case "URL(Optional)":
			case "G-URL(Optional)":
				if (!isURL) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						RandomStringGenerator urlGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String url = urlGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys("www." + url + ".com");
					}
					isURL = true;
				}
				break;
			case "Email(Optional)":
			case "G-Email(Optional)":
				if (!isEmail) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String cusEmail = emailGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(cusEmail + "@gmail.com");
					}
					isEmail = true;
				}
				break;
			case "Text":
			case "G-Text":
				if (!isText) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String cusText = textGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + originalText + "')]"))
								.sendKeys(cusText);
					}
					isText = true;
				}
				break;
			case "Country":
			case "G-Country":
				if (!isCountry) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Spinner//*[contains(@text,'Pick a country')]"))
								.size() > 0) {
							MobileElement country = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + originalText
											+ "')]/parent::*/parent::*/android.widget.Spinner//*[contains(@text,'Pick a country')]"));
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
									.click();
							Forms.TimeScriptInForms(2, 1);
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
									.click();
							CommonUtils.alertContentXpath();
							try {
								Forms.dateScriptInForms(2);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}
							CommonUtils.wait(3);
						}
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + originalText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms.TimeScriptInForms(2, 5);
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
									.click();
							CommonUtils.alertContentXpath();
							try {
								Forms.dateScriptInForms(2);
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String number1 = RandomStringUtils.randomNumeric(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(number1);
					}
					isNumber = true;
				}
			case "Customer":
			case "G-Cutomer":
				if (isCustomer) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).isEnabled()) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).click();
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"))
								.size() > 0) {
							MobileElement customEntity = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
											+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"));
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement signature = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button[1]"));
						MobileActionGesture.tapByElement(signature);
						MediaPermission.mediaPermission();
						CommonUtils.waitForElementVisibility("//*[@text='Signature']");
						MobileElement signatureCapture = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String currency = RandomStringUtils.randomNumeric(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(currency);
					}
					isCurrency = true;
				}
				break;
			case "Dropdown":
			case "G-Dropdown":
				if (!isDropdown) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if(CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]")).size() > 0) {
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						}else {
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
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Button"));
						MobileActionGesture.tapByElement(multiSelectDropdown);
						CommonUtils.waitForElementVisibility("//*[@text='Pick values']");
						List<MobileElement> pickValues = CommonUtils.getdriver()
								.findElements(MobileBy.className("android.widget.CheckedTextView"));
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
						CommonUtils.wait(3);
					}
					isMultiSelectDropdown = true;
				}
				break;
			}
		}
	}
	
}
