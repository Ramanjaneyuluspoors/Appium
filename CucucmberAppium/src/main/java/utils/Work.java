package utils;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import Actions.CustomerPageActions;
import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Work {
	private static String generateWorkName;

	// go to work page from home fab icon '+'
	public static void goToWorkPage(String workName) throws MalformedURLException {

		CommonUtils.homeFabClick();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work']")).click();
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.FrameLayout' and ./*[@id='content']]");
		MobileActionGesture.scrollTospecifiedElement("" + workName + "");
		CommonUtils.waitForElementVisibility("//*[contains(@text,'" + workName + "')]");
	}

	// scrolling to specified work name
	public static void scrollToSpecifiedWork(String workName) throws MalformedURLException, InterruptedException {
		MobileActionGesture.scrollTospecifiedElement("" + workName + "");
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@text='" + workName + "']");
	}

	// work search
	public static void workSearch(String workName) throws MalformedURLException, InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("action_search")).click();
		CommonUtils.getdriver().findElement(MobileBy.id("search_src_text")).sendKeys(workName);
		CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.ENTER));
		CommonUtils.interruptSyncAndLetmeWork();
		try {
			CommonUtils.getdriver().hideKeyboard();
		} catch (Exception e) {
		}
	}

	// verify work exist or not
	public static void verifyWorkExistOrNot(String workName) throws MalformedURLException, InterruptedException {
		try {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']"))
					.isDisplayed()) {
				System.out.println("Work with this name exist!!");
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Work Name: " + workName + "']")).click();
				CommonUtils.interruptSyncAndLetmeWork();
				CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/button1']");
			}
		} catch (Exception e) {
			System.out.println("Going to create work!!");
			workFab();
			createWork();
			workSearch(generateWorkName);
			verifyWorkExistOrNot(generateWorkName);
		}
	}

	// clicking on '+' in work
	public static String workFab() throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
		return "pass";
	}

	// work creation
	public static void createWork() throws MalformedURLException, InterruptedException {
		// insertig working name randon name using 'RandomStringUtils' library
		generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//android.widget.EditText[contains(@text,'Work Name')]"))
				.sendKeys(generateWorkName);
		workEndTime();
		saveWork();
	}

	// work end time added as '2' hours extra because End time should be greater
	public static void workEndTime() throws MalformedURLException, InterruptedException {

		// retrieving time
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("h:mm a");
		String stringDate = DateFor.format(date);
		System.out.println("Hours Format : " + stringDate);

		// Splitting time
		String[] CurrentTimesplitValue = DateFor.format(date).split(":");

		// retrieving current time into hours and minutes
		String presentHours = CurrentTimesplitValue[0];
		System.out.println("CurrentHours: " + presentHours);
		String CurrentMin = CurrentTimesplitValue[1];
		System.out.println("CurrentMins: " + CurrentMin);

		// splitting AMPM from mins
		String[] splitAMPM = CurrentMin.split(" ");
		// retrieving mins and AM PM
		String getAmPm = splitAMPM[1];
		String getMins = splitAMPM[0];
		System.out.println("AmPm :" + getAmPm);
		System.out.println("CurrMinutes :" + getMins);

		// adding extra hours to the current hours and splitting into hrs and mins
		date = DateUtils.addHours(date, 2);
		String workAddhrs = DateFor.format(date);
		System.out.println("After adding hours time is : " + workAddhrs);
		String[] splitValueExtendedHrs = workAddhrs.split(":");
		String extendedHours = splitValueExtendedHrs[0];
		System.out.println("addedHours: " + extendedHours);
//        String AddedMin = splitValueExtendedHrs[1];
//        System.out.println("addedMins: "+AddedMin);
		String[] splitAmPm = workAddhrs.split(" ");
		String getAddhrsOfAmPm = splitAmPm[1];
		System.out.println("After added hrs AmPm is: " + getAddhrsOfAmPm);

		// click on end time
		CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[contains(@text,'Ends')]/parent::*/descendant::*[@text='PICK TIME']"))
				.click();
		CommonUtils.alertContentXpath();
		// get xpath of current hour and pass variable of current,added hours
		MobileElement sourceHour = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@content-desc='" + presentHours + "']"));
		MobileElement destinationHour = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@content-desc='" + extendedHours + "']"));
		// using longpress method moving element from source to destination(i.e current
		// hr to added hr)
		MobileActionGesture.Movetoelement(sourceHour, destinationHour);
		CommonUtils.waitForElementVisibility("//*[@resource-id='android:id/minutes']");
//		MobileElement minsClick = CommonUtils.getdriver().findElementByXPath("//*[@text='"+getMins+"']");
//		MobileActionGesture.singleLongPress(minsClick);
		MobileElement clickAmPm = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@text='" + getAddhrsOfAmPm + "']"));
		MobileActionGesture.singleLongPress(clickAmPm);
		CommonUtils.OkButton("OK");
		Thread.sleep(1000);
	}

	// while saving the work creation verify save or continue button is displaying
	// then perform accordingly
	public static void saveWork() throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("saveWork")).click();
		CommonUtils.alertContentXpath();
		MobileElement saveButton = CommonUtils.getdriver().findElement(MobileBy.id("workSaveButton"));
		if (saveButton.isDisplayed()) {
			saveButton.click();
			try {
				if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@resource-id='android:id/button1']"))
						.isDisplayed()) {
					CommonUtils.OkButton("CONTINUE");
				}
			} catch (Exception e) {
				System.out.println("work time is not override");
			}
		}
		CommonUtils.interruptSyncAndLetmeWork();
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/action_search']");
	}

	// click on created work and perform action
	public static void WorkAction() throws InterruptedException, MalformedURLException {
		do {
			CommonUtils.getdriver().findElement(MobileBy.id("button1")).click();
			Thread.sleep(1000);
			if (CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.className("android.widget.Button")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
				// perform action until next action displayed
				CustomerPageActions.verifyFormPagesAndFill();
				workActionSaveButton();
			}
		} while (CommonUtils.getdriver().findElementsById("button1").size() > 0);
	}

	// save workaction
	public static void workActionSaveButton() throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.id("saveForm")).click();
		CommonUtils.alertContentXpath();
		CommonUtils.getdriver().findElement(MobileBy.id("formSaveButton")).click();
		CommonUtils.implicitWait();
	}

	// Retrieving work status (i.e Completed,Yet to Start, in progrsss) based on the
	// text you passed
	public static void workStatus(String workName) throws MalformedURLException, InterruptedException {
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/workStatusText']");
		MobileElement getWorkStatus = CommonUtils.getdriver().findElement(MobileBy.xpath(
				"//*[@id='workStatusText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Work Name: "
						+ workName + "']]]"));
		String workStatus = getWorkStatus.getText();
		System.out.println("status of work is " + workStatus);
	}

	// move to homepage from work
	public static void moveToHomepageFromWork() throws InterruptedException, MalformedURLException {
		if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Collapse']")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Collapse']")).click();
			CommonUtils.openMenu();
			CommonUtils.clickHomeInMenubar();
		} else {
			System.out.println("collapse symbol not found");
		}
	}
}
