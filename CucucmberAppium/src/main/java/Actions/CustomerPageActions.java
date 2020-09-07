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

	// checkout anyway alert
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
