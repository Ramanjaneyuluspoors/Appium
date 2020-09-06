package Actions;

import java.net.MalformedURLException;
import java.util.List;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.CommonUtils;
import utils.Forms;
import utils.MediaPermission;

public class CustomerPageActions {
	public static String randomstringCusName;

	// search for customer
	public static void customerSearch(String customerName) throws MalformedURLException {
		CommonUtils.waitForElementVisibility("//*[@text='Customers']");
		CommonUtils.getdriver().findElement(MobileBy.id("action_search")).click();
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(customerName);
		CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	// if customer not exist then create
	public static void verifyCusExistOrNot(String customerName) throws MalformedURLException, InterruptedException {
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + customerName + "']"))
					.isDisplayed()) {
				System.out.println("Customer found!!");
			}
		} catch (Exception e) {
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
		captureImage();
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
		MobileActionGesture.horizontalSwipeByPercentage(0.8, 0.2, 0.5);
		customerSearch(randomstringCusName);
		veirfyCusCheckin();
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
		goToActivityScreen();
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
			try {
				CommonUtils.getdriver().hideKeyboard();
			} catch (Exception e) {
			}
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SUBMIT']")).click();
			CommonUtils.waitForElementVisibility("//*[@text='Customers']");
			customerSearch(randomstringCusName); // two ways for search after checkout anyway
			veirfyCusCheckin();
		}
		goToActivityScreen();
	}

	// moving to customer activity screen
	public static void goToActivityScreen() throws MalformedURLException, InterruptedException {
		try {
			Thread.sleep(3000);
			CommonUtils.getdriver().findElement(MobileBy.id("View_all_details")).click();
			MobileElement addActivity = CommonUtils.getdriver().findElement(MobileBy.id("addTask"));
			MobileActionGesture.tapByElement(addActivity);
			CommonUtils.waitForElementVisibility("//*[contains(@text,'ACTIVITIES')]");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// click on form
	public static void clickActivity(String formName) throws MalformedURLException, InterruptedException {
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + formName + "']")).isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + formName + "']")).click();
				System.out.println("form with name is displayed and clicked");
			} else {
				System.out.println("form with name is not displayed, clicking on first form");
				CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/saveForm']");
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
				Forms.verifySectionToClickAdd();
				formfill();
			}
		} else {
			System.out.println("pagination not exists");
			Forms.verifySectionToClickAdd();
			formfill();
		}
		Forms.formSaveButton();
	}
	

	// form fill
	public static void formfill() throws InterruptedException, MalformedURLException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();
		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
						CommonUtils.alertContentXpath();
						Forms.dateScriptInForms();
						Thread.sleep(500);
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
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
						isCustomerType = true;
					}
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
						MobileElement terriory = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(terriory);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
						isTerritory = true;
					}
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
										+ "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(country);
						MobileActionGesture.scrollTospecifiedElement("Australia");
						isCountry = true;
					}
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[1]"))
								.click();
						CommonUtils.alertContentXpath();
						Forms.dateScriptInForms();
						Thread.sleep(500);
						isDateTime = true;
					}
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
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
						Forms.TimeScriptInForms();
						Thread.sleep(500);
						isTime = true;
					}
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
						isPickList = true;
					}
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
								+ "')]/parent::*/following-sibling::*/android.widget.Button")).click();
						Thread.sleep(5000);
						CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
						Thread.sleep(500);
						isLocation = true;
					}
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
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
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
						MobileElement yesno = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
								+ fieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(yesno);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						isYesNo = true;
					}
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
								+ "')]/parent::*/parent::*/android.widget.Button")).isEnabled()) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
									+ "')]/parent::*/parent::*/android.widget.Button")).click();
							CommonUtils.waitForElementVisibility("//*[@text='Customers']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
							} else {
								customerFab();
								createCustomer();
								customerSearch(randomstringCusName);
								CommonUtils.getdriver()
										.findElement(MobileBy.xpath("//*[@text='" + randomstringCusName + "']"))
										.click();
							}
							System.out.println("Now customer is picked");
						} else {
							System.out.println("Customer is already selected!!");
						}
						Thread.sleep(500);
						isCustomer = true;
					}
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
						isCustomEntity = true;
					}
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
						} catch (Exception e) {
							System.out.println(e);
						}
						Thread.sleep(500);
						isForm = true;
					}
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
							customerFab();
							createCustomer();
							customerSearch(randomstringCusName);
							CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[@text='" + randomstringCusName
											+ "']/parent::*/parent::*/parent::*/parent::*/android.widget.CheckBox"))
									.click();
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Select']")).click();
						}
						Thread.sleep(500);
						isMultiPickCustomer = true;
					}
				}
			default:
				break;
			}
//				// if element is not visible scroll to element
//				try {
//					if (CommonUtils.getdriver()
//							.findElements(MobileBy.xpath(
//									"//*[starts-with(@text,'" + fieldsText + "')]"))
//							.size() > 0) {
//						MobileActionGesture.scrollUsingText(fieldsText);
//					}
//				} catch (Exception e) {
//					System.out.println(e);
//				}
		}
	}

	// checkout customer in homepage
	public static void HomepageCusCheckout() throws MalformedURLException, InterruptedException {
		CommonUtils.goBackward();
		CommonUtils.openMenu();
		CommonUtils.clickHomeInMenubar();
		try {
			CustomerPageActions.cusCheckoutInHomepage();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// checkout customer in home page
	public static void cusCheckoutInHomepage() throws MalformedURLException, InterruptedException {
		boolean isFound = false;
		CommonUtils.getdriver().findElement(MobileBy.id("customerMenuItem")).click();
		CommonUtils.waitForElementVisibility(
				"//*[@class='android.widget.FrameLayout' and ./*[@class='android.widget.ListView']]");
		List<MobileElement> checkout = CommonUtils.getdriver().findElements(MobileBy.id("title"));
		for (int i = 0; i < checkout.size(); i++) {
			if (checkout.get(i).getText().contains("Check out")) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Check out']")).click();
				isFound = true;
				break;
			}
		}
		checkOutOrCheckOutAnyway();
	}

	//checkout anyway alert
	public static void checkOutOrCheckOutAnyway() throws MalformedURLException, InterruptedException {
		CommonUtils.alertContentXpath();
		MobileElement checkOut = CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"));
		if (checkOut.getText().contains("CHECK OUT")) {
			MobileActionGesture.tapByElement(checkOut);
		} else if (checkOut.getText().contains("CHECK-OUT ANYWAY")) {
			MobileActionGesture.tapByElement(checkOut);
			String randomstring = RandomStringUtils.randomAlphabetic(5).toLowerCase();
			CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).sendKeys(randomstring);
			CommonUtils.keyboardHide();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SUBMIT']")).click();
			Thread.sleep(2000);
		}
	}
	
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

}
