package Actions;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.Dimension;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utils.CommonUtils;

public class MobileActionGesture {

	// scroll to specified element using text
	public static void scrollTospecifiedElement(String eleText) {

//			MobileElement eleScroll=CommonUtils.getdriver().findElementByAndroidUIAutomator(
//					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+eleText+"\").instance(0))");
//			MobileElement eleScroll = CommonUtils.getdriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(textContains(\""+eleText+"\"));");
		MobileElement eleScroll = CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ eleText + "\").instance(0))"));
		if (eleScroll.isDisplayed()) {
			try {
				MobileActionGesture.tapByElement(eleScroll);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			System.out.println("Successfully scrolled to specified element and click");
		} else {
			System.out.println("Element unable to found");
		}
	}
	
	public static void scrollUsingText(String eleText) {
		MobileElement eleScroll = CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ eleText + "\").instance(0))"));
		System.out.println("scrolled element is " + eleScroll);
	}

	// Tap to an element for 50 milliseconds
	public static void tapByElement(MobileElement mobileElement) throws MalformedURLException {
		new TouchAction(CommonUtils.getdriver()).tap(tapOptions().withElement(element(mobileElement)))
				.waitAction(waitOptions(Duration.ofMillis(50))).perform();
	}

	// Horizontal Swipe by percentages
	public static void horizontalSwipeByPercentage(double startPercentage, double endPercentage,
			double anchorPercentage) throws MalformedURLException {
		Dimension size = CommonUtils.getdriver().manage().window().getSize();
		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * endPercentage);

		new TouchAction(CommonUtils.getdriver()).press(point(startPoint, anchor)).waitAction(waitOptions(ofMillis(25)))
				.moveTo(point(endPoint, anchor)).release().perform();
	}

	// Vertical Swipe by percentages
	public static void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage)
			throws MalformedURLException {
		Dimension size = CommonUtils.getdriver().manage().window().getSize();
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * endPercentage);

		new TouchAction(CommonUtils.getdriver()).press(point(anchor, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofNanos(10)))
				.moveTo(point(anchor, endPoint)).release().perform();
	}

	// longpress with single element
	public static void singleLongPress(MobileElement elelongpress) throws MalformedURLException {
		new TouchAction(CommonUtils.getdriver())
				.longPress(longPressOptions().withElement(element(elelongpress)).withDuration(ofMillis(5))).release()
				.perform();
	}

	// longpress from source element to destination element
	public static void Movetoelement(MobileElement first, MobileElement second) throws MalformedURLException {
		new TouchAction(CommonUtils.getdriver())
				.longPress(longPressOptions().withElement(element(first)).withDuration(ofMillis(5)))
				.moveTo(element(second)).release().perform();
	}

	// longpress move element to destination element
	public static void Movetoelement1(MobileElement moveEle) throws MalformedURLException {
		new TouchAction(CommonUtils.getdriver()).longPress(longPressOptions().withDuration(ofSeconds(1)))
				.moveTo(element(moveEle)).release().perform();
	}

	public static void scrollToBottom(double start, double end) {
		int x = CommonUtils.getdriver().manage().window().getSize().width / 2;
		int start_y = (int) (CommonUtils.getdriver().manage().window().getSize().height * start);
		int end_y = (int) (CommonUtils.getdriver().manage().window().getSize().height * end);
		TouchAction dragNDrop = new TouchAction(CommonUtils.getdriver()).press(PointOption.point(x, start_y))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(50))).moveTo(PointOption.point(x, end_y))
				.release();
		dragNDrop.perform();
	}

	// Swipe by elements
	public static void swipeByElements(MobileElement startElement, MobileElement endElement) {
		try {
			int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
			int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);
			int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
			int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);
//		int endX = CommonUtils.getdriver().manage().window().getSize().width / 2;
//		int endY = (int) (CommonUtils.getdriver().manage().window().getSize().height * 0.52);
			new TouchAction(CommonUtils.getdriver()).press(point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofNanos(5))).moveTo(point(endX, endY)).release()
					.perform();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void flingVerticalToBottom_Android() {
		try {
//	            CommonUtils.getdriver().findElements(MobileBy.AndroidUIAutomator(
//	                    "new UiScrollable(new UiSelector()).setAsVerticalList().flingToEnd()"));
			    CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).flingForward();"));
//			    CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
//				    	"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(1)"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void flingToBegining_Android() {
		try {
//	            CommonUtils.getdriver().findElements(MobileBy.AndroidUIAutomator(
//	                    "new UiScrollable(new UiSelector()).setAsVerticalList().flingToEnd()"));
			    CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).flingBackward();"));
//			    CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
//				    	"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(1)"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void scrollDown() {
//        Dimension dimension = CommonUtils.getdriver().manage().window().getSize();
//        int scrollStart = (int) (dimension.getHeight() * 0.7);
//        int scrollEnd = (int) (dimension.getHeight() * 0.2);
//
//        new TouchAction(CommonUtils.getdriver())
//                .press(PointOption.point(0, scrollStart))
//                .waitAction(WaitOptions.waitOptions(Duration.ofNanos(10)))
//                .moveTo(PointOption.point(0, scrollEnd))
//                .release().perform();

		// Get the dimensions of the device under test
		Dimension dimensions = CommonUtils.getdriver().manage().window().getSize();
		// Get the width
		int screenWidth = dimensions.getWidth();
		// Get the Height
		int screenHeight = dimensions.getHeight();

		// Change the width and height to where the first press will be
		screenWidth = screenWidth / 2;
		int scrollDistance = screenHeight / 2;
		screenHeight = screenHeight - 40;

		TouchAction actions = new TouchAction(CommonUtils.getdriver());
		actions.press(PointOption.point(screenWidth, screenHeight))   // point to press
				.waitAction(WaitOptions.waitOptions(Duration.ofNanos(5)))  // wait for specified seconds
				.moveTo(PointOption.point(screenWidth, scrollDistance))  // down.moveTo(PointOption.point(screenWidth,scrollDistance)) 
				.release() // release
				.perform(); // preform the touch action
	}

	// Generic function for Scroll
	public static void scrollUsingTouchActions_ByElements(MobileElement startElement, MobileElement endElement) {
		new TouchAction(CommonUtils.getdriver()).press(element(startElement)).waitAction(waitOptions(ofMillis(50)))
				.moveTo(element(endElement)).release().perform();
	}

}
