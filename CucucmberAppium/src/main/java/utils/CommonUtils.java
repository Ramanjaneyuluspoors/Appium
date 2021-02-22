package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.text.RandomStringGenerator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class CommonUtils {
	private static Properties prop = new Properties();
	protected static WebDriverWait wait;
	public static int EXPLICIT_WAIT_TIME;
	public static int IMPLICIT_WAIT_TIME;
	public static int DEFAULT_WAIT_TIME;
	public static String APPLICATION_NAME;
	public static String BASE_PKG;
	public static String APP_ACTIVITY;
	public static String APP_PASSWORD;
	private static String APPIUM_PORT;
	public static String AUTOMATION_INSTRUMENTATION;
	public static String BROWSER_NAME;
	public static String PLATFORM_NAME;
	public static String NEW_COMMAND_TIMEOUT;
	public static String PLATFORM_VERSION;
	public static String DEVICE_READY_TIMEOUT;
	public static String DEVICE_NAME;
	private static DesiredCapabilities capabilities = new DesiredCapabilities();
	private static URL serverUrl;
	public static AndroidDriver<MobileElement> driver;
	public static String scrShotDir;
	public static DateFormat dateFormat;
	public static String scrPath;
	public static String UDID;
	public static String NO_RESET;
	public static TouchAction T;
	static File scrShotDirPath = new java.io.File("./" + scrShotDir + "//");

	// loading properties
	public static void loadConfigProp(String propertyFileName) throws IOException {
		FileInputStream fis = new FileInputStream(propertyFileName);
		prop.load(fis);
		BASE_PKG = prop.getProperty("base.pkg");
		APP_ACTIVITY = prop.getProperty("application.activity");
		APPIUM_PORT = prop.getProperty("appium.server.port");
		AUTOMATION_INSTRUMENTATION = prop.getProperty("automation.instumentation");
		DEVICE_NAME = prop.getProperty("device.name");
		UDID = prop.getProperty("udid");
		DEVICE_READY_TIMEOUT = prop.getProperty("device.ready.timeout");
		PLATFORM_VERSION = prop.getProperty("platform.version");
		NO_RESET = prop.getProperty("no.reset");
		PLATFORM_NAME = prop.getProperty("platform.name");
	}

	// desired capabilities of phone
	public static void setCapabilitiesForAndoird() {
		capabilities.setCapability(MobileCapabilityType.UDID, UDID);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		capabilities.setCapability(MobileCapabilityType.VERSION, PLATFORM_VERSION);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_INSTRUMENTATION);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, BASE_PKG);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, DEVICE_READY_TIMEOUT);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
//		driver.setLogLevel(Level.INFO);
//		capabilities.setCapability("unlockType", "pin");
//		capabilities.setCapability("unlockKey", "6644");
	}

	// connection appium server using port number
	public static AndroidDriver<MobileElement> getDriver() throws MalformedURLException {
		serverUrl = new URL("http://localhost:" + APPIUM_PORT + "/wd/hub");
		driver = new AndroidDriver<MobileElement>(serverUrl, capabilities);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}

	// AndroidDriver method
	public static AndroidDriver<MobileElement> getdriver() {
		return driver;
	}

	public static void implicitWait() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	// screenshot method
	public static String takeScreenShot() {
		scrShotDir = "screenshots";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// Create folder under project with name "screenshots" if doesn't exist
		new File(scrShotDir).mkdirs();
		// set file name using current date time
		String destFile = dateFormat.format(new Date()) + ".png";
		try {
			// copy paste file at destination folder location
			FileUtils.copyFile(scrFile, new File(scrShotDir + "/" + destFile));
		} catch (IOException e) {
			System.out.println("Image not transfered to screencshot folder");
			e.printStackTrace();
		}
		return destFile;
	}

	// retrieving toast message using OCR
	public static String OCR() {
		String imgName = takeScreenShot();
		String result = null;
		File imageFile = new File(System.getProperty("user.home") + "\\git\\Appium\\CucucmberAppium\\screenshots",
				imgName);
		System.out.println("Image name is :" + imageFile.toString());
		ITesseract instance = new Tesseract1();

		File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Extracts Tessdata folder
		instance.setDatapath(tessDataFolder.getAbsolutePath()); // sets tessData path
		try {
			result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public static String verify() {
		String result = null;
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		ITesseract instance = new Tesseract();
		try {
			result = instance.doOCR(srcFile);
		} catch (TesseractException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public static String image() {
		File targetFile = null;
		try {
			File srcFile = driver.getScreenshotAs(OutputType.FILE);
			String filename = UUID.randomUUID().toString();
			targetFile = new File("./screenshots/" + filename + ".png");
			FileUtils.copyFile(srcFile, targetFile);
			System.out.println(targetFile.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
		return targetFile.toString();
	}

	// keyboard events
	public static void pressKeycode(KeyEvent keyEvent) {
		driver.pressKey(keyEvent);
	}

	// long press using keyboard
	public static void LongPressHardware(KeyEvent keyEvent) {
		driver.longPressKey(keyEvent);
	}

	// switch element using id
	public static String SwitchStatus(String id) {
		MobileElement ele = driver.findElement(By.id(id));
		String Status = ele.getAttribute("text");
		return Status;
	}

	// wait for element using xpath
	public static void waitForElementVisibility(String waitelexpath) {
		wait = new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitelexpath)));
	}
	
	// wait for element using xpath
		public static void longWaitElementVisibility(String waitelexpath) {
			wait = new WebDriverWait(driver, 6000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitelexpath)));
		}

	// click on sync button in homepage
	public static void waitForSyncButton() throws InterruptedException {
		if (driver.findElement(MobileBy.id("syncButton")).isDisplayed()) {
			MobileElement sync = driver.findElement(MobileBy.id("syncButton"));
			WebDriverWait waitforSync = new WebDriverWait(driver, 30);
			waitforSync.until(ExpectedConditions.visibilityOf(sync));
			if (sync.isDisplayed()) {
				sync.click();
				CommonUtils.wait(15);
			} else {
				CommonUtils.wait(5);
			}
		}
	}

	// sync progress
	public static void sync_in_progress() throws InterruptedException {
		if (CommonUtils.getdriver().findElements(By.id("syncHeading")).size() > 0) {
			CommonUtils.wait(5);
		} else {
			CommonUtils.wait(5);
		}
	}

	// click on menu bar
	public static void openMenu() throws MalformedURLException, InterruptedException {
		if (driver.findElements(By.xpath("//*[@content-desc='Open drawer']")).size() > 0) {
			driver.findElement(By.xpath("//*[@content-desc='Open drawer']")).click();
		} else if (driver.findElements(By.xpath("//*[@contentDescription='Open drawer']")).size() > 0) {
			driver.findElement(MobileBy.xpath("//*[@contentDescription='Open drawer']")).click();
		} else if (driver.findElements(By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']"))
				.size() > 0) {
			driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']")).click();
		} else {
			System.out.println("*** Menubar is not displayed ***");
		}
		CommonUtils.wait(1);
	}

	// javascript executor using id
	public static void javaScriptUsingId(String eleId) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('" + eleId + "').click();");
	}

	// click on home "+" image
	public static void homeFabClick() throws MalformedURLException {
		waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/fab']");
		MobileElement homeFab = driver.findElement(MobileBy.id("fab"));
		MobileActionGesture.tapByElement(homeFab);
		alertContentXpath();
	}

	// clicks on back arrow symbol to move backward
	public static void goBackward() {
		do {
			driver.navigate().back();
		} while (driver.findElements(By.xpath("//*[@content-desc='Navigate up']")).size() > 0);
	}

	// clicks on Home in menu bar to move homepage
	public static void clickHomeInMenubar() throws InterruptedException {
		if (driver.findElements(MobileBy.xpath("//*[@text='Home']")).size() > 0) {
			driver.findElement(MobileBy.xpath("//*[@text='Home']")).click();
			Thread.sleep(500);
			MobileActionGesture.scrollTospecifiedElement("Home");
			waitForElementVisibility("//*[@text='Home']");
		} else {
			System.out.println("--- Home button is not displayed ---");
		}
	}

	// common alert like check-in,check-out,time,calendar
	public static void alertContentXpath() {
		waitForElementVisibility("//*[@resource-id='android:id/content']");
	}

	// click on text
	public static void OkButton(String text) throws InterruptedException {
		MobileElement clickOk = driver
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"));
		if (clickOk.getText().contains(text))
			clickOk.click();
		Thread.sleep(1000);
	}

	// click on interrupt sync popup
	public static void interruptSyncAndLetmeWork() throws InterruptedException {
//		try {
		if (driver.findElements(By.id("button2")).size() > 0) {
			AndroidLocators.clickElementusingID("button2");
		} else if (driver
				.findElements(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
				.size() > 0) {
			AndroidLocators.clickElementusingResourceId("android:id/button2");
		} else if (driver.findElements(By.xpath("//android.widget.Button[@text='Interrupt Sync & Let me work']"))
				.size() > 0) {
			AndroidLocators.clickElementusingXPath("//android.widget.Button[@text='Interrupt Sync & Let me work']");
		} else {
			System.out.println("Interrupt sync pop-up is not displayed");
		}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
	}

	// hide keyboard
	public static void keyboardHide() {
		CommonUtils.getdriver().hideKeyboard();
	
	}

	// allow bluetooth 
	public static void allow_bluetooth() throws InterruptedException {
		try {
			if (CommonUtils.getdriver().findElements(By.id("message")).size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.id("button1")).click();
			} else if (CommonUtils.getdriver()
					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/message\")"))
					.isDisplayed()) {
				AndroidLocators.resourceId("android:id/button1").click();
			} else if (CommonUtils.getdriver().findElement(By.xpath("//*[@resource-id='android:id/message']"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(By.xpath("//*[@text='Allow']")).click();
			} else if (CommonUtils.getdriver()
					.findElement(
							By.xpath("//*[@class='android.widget.LinearLayout']/*[@resource-id='android:id/message']"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(By.xpath("//*[@text='Allow']")).click();
			} else if (CommonUtils.getdriver().findElement(By.xpath("[@text='EFFORT Plus wants to turn on Bluetooth']"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(By.xpath("//*[@text='Allow']")).click();
			}
		} catch (Exception e) {
			System.out.println("allow bluetooth alert is not dislayed");
		}
	}

	// wait method in seconds
	public static void wait(final int sec) throws InterruptedException {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// click on send debug info
	public static void click_on_send_debug_info() {
		if (driver.findElements(By.id("sendClientReportToServerButton")).size() > 0) {
			driver.findElement(By.id("sendClientReportToServerButton")).click();
		} else if (CommonUtils.getdriver()
				.findElements(MobileBy.AndroidUIAutomator(
						"new UiSelector().resourceId(\"in.spoors.effortplus:id/sendClientReportToServerButton\")"))
				.size() > 0) {
			CommonUtils.getdriver()
					.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().resourceId(\"in.spoors.effortplus:id/sendClientReportToServerButton\")"))
					.click();
		} else {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SEND DEBUG INFO']")).click();
		}
		try {
			CommonUtils.wait(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// send debug info remarks
	public static void send_debug_info_details() throws InterruptedException {
		alertContentXpath();
		RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String email = emailGenerator.generate(5);
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String text = textGenerator.generate(5);
		driver.findElement(By.xpath("//*[@class='android.widget.EditText'][contains(@text,'Please enter the email')]"))
				.sendKeys(email + "@gmail.com");
		driver.findElement(By
				.xpath("//*[@class='android.widget.EditText'][contains(@text,'Please provide reason for debug info')]"))
				.sendKeys(text);
		keyboardHide();
		if (driver.findElements(By.id("button1")).size() > 0) {
			driver.findElement(By.id("button1")).click();
		} else if (driver
				.findElements(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
				.size() > 0) {
			driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button1\")"))
					.click();
		} else {
			driver.findElement(MobileBy.xpath("SEND")).click();
		}
		wait(5);
	}

	// initiate full sync
	public static void initiate_full_sync() throws InterruptedException {
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Button'][@text='INITIATE FULL SYNC']");
		if (driver.findElements(By.id("initiateFirstSyncButton")).size() > 0) {
			AndroidLocators.clickElementusingID("initiateFirstSyncButton");
		} else if (driver
				.findElements(MobileBy.AndroidUIAutomator(
						"new UiSelector().resourceId(\"in.spoors.effortplus:id/initiateFirstSyncButton\")"))
				.size() > 0) {
			driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiSelector().resourceId(\"in.spoors.effortplus:id/initiateFirstSyncButton\")")).click();
		} else if (driver.findElements(By.xpath("//*[@class='android.widget.Button'][@text='INITIATE FULL SYNC']"))
				.size() > 0) {
			driver.findElement(By.xpath("//*[@class='android.widget.Button'][@text='INITIATE FULL SYNC']")).click();
		} else {
			driver.findElement(By.xpath("[@text='INITIATE FULL SYNC']")).click();
		}
		Thread.sleep(15000);
	}

	// click on search icon
	public void clickonSearchBox(String searchBox) {
		driver.findElementById(searchBox).click();
		try {
			CommonUtils.wait(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// verify element exist or not
	public boolean isElementExists(String uname) {
		List<MobileElement> ele = driver.findElementsByXPath(uname);
		if (ele.size() > 0)
			return true;
		else
			return false;
	}


	// handling alert
	public static void handling_alert(String idlocators, String sub_id_locator, String resourceId_Locator,
			String sub_resourceId_Locator, String xpath_locator, String sub_xpath) {
		if (CommonUtils.getdriver().findElements(By.id("" + idlocators + "")).size() > 0) {
			AndroidLocators.clickElementusingID("" + sub_id_locator + "");
		} else if (CommonUtils.getdriver()
				.findElements(
						MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceId_Locator + "\")"))
				.size() > 0) {
			AndroidLocators.clickElementusingResourceId("" + sub_resourceId_Locator + "");
		} else if (CommonUtils.getdriver().findElements(By.xpath("" + xpath_locator + "")).size() > 0) {
			AndroidLocators.clickElementusingXPath("" + sub_xpath + "");
		}
	}
	
	
	// get element text using xpath and scroll to that text
	public static void getTextAndScrollToElement(String locator) {
		if (driver.findElement(By.xpath(locator)).isDisplayed()) {
			String scrollUsingXpathElement = driver.findElement(By.xpath(locator)).getText();
			MobileActionGesture.scrollTospecifiedElement(scrollUsingXpathElement);
		} 
	}

}
