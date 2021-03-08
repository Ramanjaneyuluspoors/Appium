package common_Steps;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.CommonUtils;

public class AndroidLocators {
	public static final int TYPE_TEXT = 1;
	public static final int TYPE_NUMBER = 2;
	public static final int TYPE_DATE = 3;
	public static final int TYPE_YES_OR_NO = 4;
	public static final int TYPE_DROPDOWN = 5;
	public static final int TYPE_MULTI_SELECT_DROPDOWN = 6;
	public static final int TYPE_CUSTOMER = 7;
	public static final int TYPE_EMAIL = 8;
	public static final int TYPE_PHONE = 9;
	public static final int TYPE_URL = 10;
	public static final int TYPE_TIME = 11;
	public static final int TYPE_IMAGE = 12;
	public static final int TYPE_SIGNATURE = 13;
	public static final int TYPE_PICK_LIST = 14;
	public static final int TYPE_EMPLOYEE = 15;
	public static final int TYPE_CURRENCY = 16;
	public static final int TYPE_MULTI_PICK_LIST = 17;
	public static final int TYPE_LOCATION = 18;
	public static final int TYPE_DATE_TIME = 19;
	public static final int TYPE_COUNTRY = 20;
	public static final int TYPE_AUDIO = 22;
	public static final int TYPE_NUMBERTOWORDS = 23;
	public static final int TYPE_CUSTOMERTYPE = 24;
	public static final int TYPE_FORM = 25;
	public static final int TYPE_TIME_SPAN = 26;
	public static final int TYPE_VIDEO = 27;

	// constant for auto generate ID
	public static final int TYPE_AUTO_GENERATE = 28;
	public static final int TYPE_LABEL = 29;
	public static final int TYPE_MULTI_CUSTOMER = 30;
	public static final int TYPE_TERRITORY = 31;
	public static final int TYPE_CUSTOM_ENTITY = 32;
	public static final int TYPE_DOCUMENT = 33;
	
	                                /* Return single element */
	// resource id using uiselector
	public static MobileElement resourceId(String resourceIdLocator) {
		return CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"));
	}

	// xpath using MobileBy
	public static MobileElement xpath(String xpathLocator) {
		return CommonUtils.getdriver().findElement(MobileBy.xpath("" + xpathLocator + ""));
	}
	
	// return id using uiselector
	public static MobileElement returnUsingId(String IdLocator) {
		return CommonUtils.getdriver().findElement(MobileBy.id("" + IdLocator + ""));
	}
	
	// resource id using uiselector
	public static MobileElement returnUsingClassName(String ClassName) {
		return CommonUtils.getdriver().findElement(MobileBy.className("" + ClassName + ""));
	}
	
	                             /* Return multiple elements */
	
	// findElements with classname
	public static List<MobileElement> findElements_With_ClassName(String classNameLocator) {
		return CommonUtils.getdriver().findElements(MobileBy.className(classNameLocator));
	}

	// find elements using xpath
	public static List<MobileElement> findElements_With_Xpath(String xpathLocator) {
		return CommonUtils.getdriver().findElements(MobileBy.xpath(xpathLocator));
	}
								
	// findElements with Id
	public static List<MobileElement> findElements_With_Id(String IdLocator) {
		return CommonUtils.getdriver().findElements(MobileBy.id(IdLocator));
	}

	// find elements with resource id locators
	public static List<MobileElement> findElements_With_ResourceId(String resourceIdLocator) {
		return CommonUtils.getdriver().findElements(
				MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"));
	}
								/* click element using different locators */
	// click element using id
	public static void clickElementusingID(String IdLocator) {
		CommonUtils.getdriver().findElement(MobileBy.id(IdLocator)).click();
	}
	
	// click using findElementByClassName
	public static void clickElementusingClassName(String classNameLocator) {
		CommonUtils.getdriver().findElement(MobileBy.className(classNameLocator)).click();
	}

	// click element using xpath
	public static void clickElementusingXPath(String xpathLocator) {
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).click();
	}

	// click element using resource-id
	public static void clickElementusingResourceId(String resourceIdLocator) {
		CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"))
				.click();
	}
										/* sending input using different locators */
	// sending input using resource-id
	public static void sendInputusing_ResourceId(String resourceIdLocator) {
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String resourceidInput = textGenerator.generate(5);
		CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"))
				.clear();
		CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"))
				.sendKeys(resourceidInput);
	}

	// sending input using classname
	public static void sendInputusing_Classname(String classLocator) {
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String classnameInput = textGenerator.generate(5);
		CommonUtils.getdriver().findElement(MobileBy.className(classLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.className(classLocator)).sendKeys(classnameInput);
	}

	// sending input using id
	public static void sendInputusing_Id(String idLocator) {
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String IdInput = textGenerator.generate(5);
		CommonUtils.getdriver().findElement(MobileBy.id(idLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.id(idLocator)).sendKeys(IdInput);
	}

	// sending input using xpath
	public static void sendInputusing_XPath(String xpathLocator) {
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String xpathInput = textGenerator.generate(5);
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).sendKeys(xpathInput);
	}

	// sending Email input using xpath
	public static void sendEmailInputusing_XPath(String xpathLocator) {
		RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String email = emailGenerator.generate(5);
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).sendKeys(email + "@gmail.com");
	}

	// sending URL input using xpath
	public static void sendUrlInputusing_XPath(String xpathLocator) {
		RandomStringGenerator urlGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String url = urlGenerator.generate(5);
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).sendKeys("www." + url + ".com");
	}

	// sending number input using xpath for pincode/number/currency
	public static void sendNumberInputUsing_xpath(String xpathLocator) {
		String pincodeInput = RandomStringUtils.randomNumeric(6);
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).sendKeys(pincodeInput);
	}

	// sending Phonenumber input using xpath
	public static void sendPhoneNumberInputUsing_xpath(String xpathLocator) {
		String PhoneNumber = RandomStringUtils.randomNumeric(10);
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath(xpathLocator)).sendKeys(PhoneNumber);
	}
	
	// send text using id
	public static void enterTextusingID(String locator, String sText) {
		CommonUtils.getdriver().findElement(MobileBy.id(locator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.id(locator)).sendKeys(sText);
	}

	// send text using xpath
	public static void enterTextusingXpath(String locator, String sText) {
		CommonUtils.getdriver().findElement(MobileBy.xpath(locator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath(locator)).click();
		CommonUtils.getdriver().findElement(MobileBy.xpath(locator)).sendKeys(sText);
		CommonUtils.getdriver().hideKeyboard();
	}
						/* press key events */
	// press enter
	public static void pressEnterKeyInAndroid() {
		CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	// move back
	public static void pressBackKeyInAndroid() {
		CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.BACK));
	}

	// moving home
	public static void pressHomeKeyInAndroid() {
		CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.HOME));
	}

							/* return text using different locators */
	// get text using xpath
	public String getTextUsingXpath(String locator) {
		return CommonUtils.getdriver().findElementByXPath(locator).getText();
	}

}
