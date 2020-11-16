package utils;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

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

	//constant for auto generate ID
	public static final int TYPE_AUTO_GENERATE = 28;
	public static final int TYPE_LABEL = 29;
	public static final int TYPE_MULTI_CUSTOMER = 30;
	public static final int TYPE_TERRITORY = 31;
	public static final int TYPE_CUSTOM_ENTITY = 32;
	public static final int TYPE_DOCUMENT = 33;
	
	//resource id using uiselector
	public static MobileElement resourceId(String resourceIdLocator) {
		return CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"" + resourceIdLocator + "\")"));
	}
	
	// xpath using MobileBy
	public static MobileElement xpath(String xpathLocator) 
	{
		return CommonUtils.getdriver().findElement(MobileBy.xpath("" + xpathLocator + ""));
	}
	
	//click using findElementByClassName
	public static void clickElementusingClassName(String className) {
	        CommonUtils.getdriver().findElementByClassName(className).click();
	    }
	 
	// click element using id
	public static void clickElementusingID(String ID) {
			CommonUtils.getdriver().findElementById(ID).click();
		}
	
	//click element using xpath
	public static void clickElementusingXPath(String continueButton) {
		    CommonUtils.getdriver().findElementByXPath(continueButton).click();
	    }


}
