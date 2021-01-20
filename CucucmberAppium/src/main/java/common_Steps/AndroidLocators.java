package common_Steps;

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

	// resource id using uiselector
	public static MobileElement resourceId(String resourceIdLocator) {
		return CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"));
	}

	// xpath using MobileBy
	public static MobileElement xpath(String xpathLocator) {
		return CommonUtils.getdriver().findElement(MobileBy.xpath("" + xpathLocator + ""));
	}

	// click using findElementByClassName
	public static void clickElementusingClassName(String className) {
		CommonUtils.getdriver().findElementByClassName(className).click();
	}

	// click element using id
	public static void clickElementusingID(String ID) {
		CommonUtils.getdriver().findElementById(ID).click();
	}

	// click element using xpath
	public static void clickElementusingXPath(String continueButton) {
		CommonUtils.getdriver().findElementByXPath(continueButton).click();
	}

	// click element using resource-id
	public static void clickElementusingResourceId(String resourceIdLocator) {
		CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"))
				.click();
	}

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
	
	//sending input using id
	public static void sendInputusing_Id(String idLocator) {
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String IdInput = textGenerator.generate(5);
		CommonUtils.getdriver().findElement(MobileBy.id(idLocator)).clear();
		CommonUtils.getdriver().findElement(MobileBy.id(idLocator)).sendKeys(IdInput);
	}

	// sending input using xpath
	public static void sendInputusing_XPath(String xpathLocator) {
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String xpath = textGenerator.generate(5);
		CommonUtils.getdriver().findElementByXPath(xpathLocator).clear();
		CommonUtils.getdriver().findElementByXPath(xpathLocator).sendKeys(xpath);
	}
	
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

	// send text using id
	public static void enterTextusingID(String locator, String sText) {
		CommonUtils.getdriver().findElementById(locator).clear();
		CommonUtils.getdriver().findElementById(locator).sendKeys(sText);
		CommonUtils.getdriver().hideKeyboard();
	}

	// get text using xpath
	public String getTextUsingXpath(String locator) {
		return CommonUtils.getdriver().findElementByXPath(locator).getText();
	}

}
