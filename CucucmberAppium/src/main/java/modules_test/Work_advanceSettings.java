package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.gen5.api.Assertions;
import org.openqa.selenium.By;

import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class Work_advanceSettings {

	// save work
	public static void saveWork() throws InterruptedException {
		AndroidLocators.clickElementusingID("saveWork");
		CommonUtils.alertContentXpath();
		MobileElement saveButton = CommonUtils.getdriver().findElement(MobileBy.id("workSaveButton"));
		if (saveButton.isDisplayed()) {
			saveButton.click();
			try {
				if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@resource-id='android:id/alertTitle']"))
						.isDisplayed()) {
					AndroidLocators.clickElementusingXPath("//*[@resource-id='android:id/button1']");
					CommonUtils.wait(2);
					System.out.println(" ---- work is saved successfully!! ----");
				}
			} catch (Exception e) {
				System.out.println(" **** work time is not override **** ");
			}
		}
	}

	// work min max validations
	public static void workFields_Min_Max_Testing(int min, int max) throws MalformedURLException, InterruptedException {

		// Declaring the workLabelElements list
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]");

		// retrieving the list count
		int workFieldsCount = workFields.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		workFields.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workFields.isEmpty() && workFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workFields.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]")));

			// get the count of work fields
			workFieldsCount = workFields.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ workFields.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + workFields.get(i).getText());

				// if list matches with last element the loop will break
				if (workFields.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + workFields.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();

		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = workFields.get(j).getText();
			String workFieldsText = workFields.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			workFieldsText = workFieldsText.split("\\(")[0];
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);

			switch (workFieldsText) {
			case "Work Name":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					workNameMinMaxChecking(workFieldsText, min, max);
				}
				break;
			case "Ends":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
					CommonUtils.alertContentXpath();
					try {
						Forms_basic.dateScriptInForms(1);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CommonUtils.wait(3);
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					CommonUtils.alertContentXpath();
					Work.workEndTime(1, 5);
					CommonUtils.wait(1);
				}
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					textMinMaxInput(workFieldsText, min, max);
				}
				break;
//			case "Phone Number(Optional)":
//			case "Phone(Optional)":
//				if (CommonUtils.getdriver()
//						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
//				MobileActionGesture.scrollUsingText(workFieldsText);
//				for (n = 0; n < 2; n++) {
//					min_test = min_test - 1;
//					String phoneNum = RandomStringUtils.randomNumeric(min_test);
//					String phoneNum1 = RandomStringUtils.randomNumeric(min);
//					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
//					CommonUtils.getdriver().hideKeyboard();
//					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
//							.sendKeys(phoneNum);
//					saveWork();
//					if (phoneNum1.length() < phoneNum.length()) {
//						String text = CommonUtils.OCR();
//						System.out.println("Expected toast message for min input is " + text);
//						Assertions.assertFalse(
//								text.contains(
//										"" + workFieldsText + " cannot be shorter than " + phoneNum1 + "characters."),
//								"" + workFieldsText + " cannot be shorter than " + phoneNum1 + "characters.");
//					}
//					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
//							.clear();
//					min_test = min_test + 2;
//				}
//
//				// inserting max input value
//				for (p = 0; p < 2; p++) {
//					max_text = max_text - 1;
//					String phoneNum = RandomStringUtils.randomNumeric(max_text);
//					String phoneNum1 = RandomStringUtils.randomNumeric(max);
//					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
//					CommonUtils.getdriver().hideKeyboard();
//					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
//							.sendKeys(phoneNum);
//					saveWork();
//					if (phoneNum1.length() > phoneNum.length()) {
//						String text = CommonUtils.OCR();
//						System.out.println("Expected toast message for max input is " + text);
//						Assertions.assertFalse(
//								text.contains(
//										"" + workFieldsText + " cannot be longer than " + phoneNum1 + "characters."),
//								"" + workFieldsText + " cannot be longer than " + phoneNum1 + "characters.");
//					}
//					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
//							.clear();
//					max_text = max_text + 2;
//				}
//				AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
//				CommonUtils.getdriver().hideKeyboard();
//				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
//						.sendKeys(RandomStringUtils.randomNumeric(max));
//				saveWork();
//			}
//				break;
			case "Pincode":
			case "Currency":
			case "Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					// based on previous element inputing the main element
					numberCurrencyInputMinMax(workFieldsText, min, max);
				}
				break;
			}
		}
	}

	// inserting Work/Text min max
	public static void workNameMinMaxChecking(String workFieldsText, int min, int max) throws InterruptedException {

		// assigning min max values
		int min_test = min, max_text = max;

		// get above/below element of main element
		String getAboveOrBelowOfMainElement = commonMethodForInput(workFieldsText);

		// get random data
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

		String textMinInput = null;
		String textMinInput1 = null;
		String textMaxInput = null;
		String textMaxInput1 = null;
		// inserting min input value
		for (int n = 0; n < 2; n++) {
			min_test = min_test - 1;
			textMinInput = textGenerator.generate(min_test);
			textMinInput1 = textGenerator.generate(min);
			System.out.println("*** Given minimum length is *** : " + textMinInput1);
			System.out.println("*** Validation minimum length is *** : " + textMinInput);

			// based previous element inputing the main element
			if (CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
					.isDisplayed()) {
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(textMinInput);
				} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(textMinInput);
				}
			}

			// work save
			saveWork();

			// validating min values
			if (textMinInput.length() < textMinInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("---- Expected toast message for min input is ---- : " + text);
				Assertions.assertFalse(
						text.contains("" + workFieldsText + " Smaller than the minimum allowed length" + (textMinInput1)
								+ "."),
						"" + workFieldsText + " Smaller than the minimum allowed length " + (textMinInput1) + ".");
			}
			min_test = min_test + 2;
		}

		// inserting max input values
		for (int p = 0; p < 2; p++) {

			max_text = max_text + 1;
			textMaxInput = textGenerator.generate(max_text);
			textMaxInput1 = textGenerator.generate(max);
			System.out.println("*** Given maximum length is *** : " + textMaxInput1);
			System.out.println("*** Validation maximum length is *** : " + textMaxInput);

			// based previous element inputing the main element
			if (CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
					.isDisplayed()) {
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath(
								"//android.widget.EditText[starts-with(@text,'" + getAboveOrBelowOfMainElement + "')]"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(textMaxInput);
				} else if (CommonUtils.getdriver()
						.findElements(MobileBy
								.xpath("//*[@resource-id='in.spoors.effortplus:id/label_for_view'][starts-with(@text,'"
										+ getAboveOrBelowOfMainElement + "')]"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(textMaxInput);
				}
			}

			// work save
			saveWork();

			// validating max values
			if (textMaxInput.length() > textMaxInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max input is " + text);
				Assertions.assertFalse(
						text.contains("" + workFieldsText + " larger than the maximum allowed length " + (textMaxInput1)
								+ "."),
						"" + workFieldsText + " larger than the maximum allowed length " + (textMaxInput1) + ".");
			}
			max_text = max_text - 1;
		}

		// based on previous element inputting to main element
		if (CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
				.isDisplayed()) {
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.sendKeys(textGenerator.generate(max));
			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.sendKeys(textGenerator.generate(max));
			}
		}
	}

	// text min max validations
	public static void textMinMaxInput(String workFieldsText, int min, int max) throws InterruptedException {

		// assigning min max values
		int min_test = min, max_text = max;

		// get above/below element of main element
		String getAboveOrBelowOfMainElement = commonMethodForInput(workFieldsText);

		// get random data
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

		// inserting min input value
		for (int n = 0; n < 2; n++) {
			min_test = min_test - 1;
			String textMinInput = textGenerator.generate(min_test);
			String textMinInput1 = textGenerator.generate(min);
			System.out.println("*** Given minimum length is *** : " + textMinInput1);
			System.out.println("*** Validation minimum length is *** : " + textMinInput);

			// based previous element inputing the main element
			insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textMinInput);

			// wprk save
			saveWork();

			// validating min values
			if (textMinInput.length() < textMinInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("---- Expected toast message for min input is ---- : " + text);
				Assertions.assertFalse(
						text.contains("" + workFieldsText + " Smaller than the minimum allowed length" + (textMinInput1)
								+ "."),
						"" + workFieldsText + " Smaller than the minimum allowed length " + (textMinInput1) + ".");
			}
			min_test = min_test + 2;
		}

		// inserting max input values
		for (int p = 0; p < 2; p++) {

			max_text = max_text + 1;
			String textMaxInput = textGenerator.generate(max_text);
			String textMaxInput1 = textGenerator.generate(max);
			System.out.println("*** Given maximum length is *** : " + textMaxInput1);
			System.out.println("*** Validation maximum length is *** : " + textMaxInput);

			// based previous element inputing the main element
			insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textMaxInput);

			// work save
			saveWork();

			// validating max values
			if (textMaxInput.length() > textMaxInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max input is " + text);
				Assertions.assertFalse(
						text.contains("" + workFieldsText + " larger than the maximum allowed length " + (textMaxInput1)
								+ "."),
						"" + workFieldsText + " larger than the maximum allowed length " + (textMaxInput1) + ".");
			}
			max_text = max_text - 1;
		}

		// based previous element inputing the main element
		insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textGenerator.generate(max));

	}

	// inserting number/currency/pincode min and max values
	public static void numberCurrencyInputMinMax(String workFieldsText, int min, int max) throws InterruptedException {

		int min_test = min, max_text = max;
		String getAboveOrBelowOfMainElement = commonMethodForInput(workFieldsText);

		// inserting min input value
		for (int n = 0; n < 2; n++) {
			min_test = min_test - 1;
			System.out.println("*** Minimum value *** : " + min_test);

			// based previous element inputing the main element
			insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(min_test));

			// work save
			saveWork();

			if (min < min_test) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for min value is :" + text);
				Assertions.assertFalse(text.contains("" + workFieldsText + " cannot be less than " + min + "."),
						"" + workFieldsText + " cannot be less than " + min + ".");
			}

			// clear the input
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
			} else if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();

			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
			}
			min_test = min_test + 2;
		}

		// inserting max input value
		for (int p = 0; p < 2; p++) {

			max_text = max_text + 1;
			System.out.println("*** Maximum value is *** : " + max_text);

			// based previous element inputing the main element
			insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(max_text));

			// work save
			saveWork();

			// validating max values
			if (max > max_text) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max value is :" + text);
				Assertions.assertFalse(text.contains("" + workFieldsText + " cannot be greater than " + max + "."),
						"" + workFieldsText + " cannot be greater than " + max + ".");
			}

			// clear the input
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
			} else if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();

			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
			}
			max_text = max_text - 1;
		}

		// based previous element inputing the main element
		insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(max));
	}

	/******* Testing regular expression *********/
	public static void reuglarExpression(String regExp) throws InterruptedException, MalformedURLException {
		// retrieving regular expression
		String matchRegExp = FormAdvanceSettings.match_regExp(regExp);
		String unMatchRegExp = FormAdvanceSettings.unMatch_regExp(regExp);

		MobileActionGesture.scrollUsingText("Ends");
		AndroidLocators.clickElementusingXPath(
				"//*[contains(@text,'Ends')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");

		CommonUtils.alertContentXpath();
		Work.workEndTime(1, 5);

		MobileActionGesture.scrollUsingText("Work Name");
		/* inputting the unmatching regular expression(special charcter) */

		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'Work Name')]")).size() > 0) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'Work Name')]")).clear();
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'Work Name')]"))
					.sendKeys(unMatchRegExp);
			saveWork();
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy
						.xpath("(//android.widget.LinearLayout/*/*/*/*/*[@class='android.widget.EditText'])[1]"))
				.isDisplayed()) {
			CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("(//android.widget.LinearLayout/*/*/*/*/*[@class='android.widget.EditText'])[1]"))
					.clear();
			CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("(//android.widget.LinearLayout/*/*/*/*/*[@class='android.widget.EditText'])[1]"))
					.sendKeys(matchRegExp);
			saveWork();
		}

		/* inputting the matching regular expression */
		CommonUtils.getdriver()
				.findElement(MobileBy
						.xpath("(//android.widget.LinearLayout/*/*/*/*/*[@class='android.widget.EditText'])[1]"))
				.clear();
		CommonUtils.getdriver()
				.findElement(MobileBy
						.xpath("(//android.widget.LinearLayout/*/*/*/*/*[@class='android.widget.EditText'])[1]"))
				.sendKeys(matchRegExp);
		saveWork();

		MobileActionGesture.scrollUsingText("Text");
		String getPreviousElementText = null;

		// retrieving the text of the input previous element
		if (CommonUtils.getdriver().findElement(MobileBy.xpath(
				"//*[starts-with(@text,'Text')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
				.isDisplayed()) {
			getPreviousElementText = CommonUtils.getdriver().findElement(MobileBy.xpath(
					"//*[starts-with(@text,'Text')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
					.getText();
			System.out.println("*** Edit Text Input is *** : " + getPreviousElementText);
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//*[@text='Text']/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView"))
				.size() > 0) {
			getPreviousElementText = CommonUtils.getdriver().findElement(MobileBy.xpath(
					"//*[@text='Text']/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView"))
					.getText();
			System.out.println("*** Text View Input is *** : " + getPreviousElementText);
		}
		// removing special characters from the given string
		getPreviousElementText = getPreviousElementText.replaceAll("\\s[!@#$%&*,.?\":{}|<>]", "");
		System.out.println("*** Inputting text of the previous element is *** :" + getPreviousElementText);

		/* inputting the unmatching regular expression(special charcter) */
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'Text')]")).clear();
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'Text')]")).sendKeys(unMatchRegExp);
		saveWork();

		MobileActionGesture.scrollUsingText(getPreviousElementText);

		/* inputting the matching regular expression */
		if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getPreviousElementText + "')]"))
				.isDisplayed()) {
			if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getPreviousElementText
					+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getPreviousElementText
						+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getPreviousElementText
						+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
						.sendKeys(matchRegExp);
				CommonUtils.getdriver().hideKeyboard();
			} else {
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getPreviousElementText
						+ "')]/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getPreviousElementText
						+ "')]/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
						.sendKeys(matchRegExp);
				CommonUtils.getdriver().hideKeyboard();
			}
			saveWork();
		}
	}

	// Field Dependency based on value in other fields
	public static void fieldDependencyBasedOnValueInOtherFieldsTesing(String conditionName, String dependentFieldType,
			String inputData) throws MalformedURLException, InterruptedException {

		// initializing and assigning
		String workBaseCondition = conditionName;
		String workFieldType = dependentFieldType;
		String workFieldInput = inputData;

		// dependent work field
		List<MobileElement> specifiedElement = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'"
						+ workFieldType + "')]");

		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]");

		// count of field
		int countOfDependentField = specifiedElement.size();
		System.out.println(" ===== Work Field Count is ===== : " + countOfDependentField);

		// remove fields from list
		specifiedElement.clear();

		// initializig the lastElement
		String workLastElementText = null;

		// scroll to end and get last element text from the list
		MobileActionGesture.flingVerticalToBottom_Android();
		specifiedElement.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));
		workLastElementText = specifiedElement.get(specifiedElement.size() - 1).getText();
		System.out.println("**** Work Last element text is **** :" + workLastElementText);

		// remove fields from list
		specifiedElement.clear();
		MobileActionGesture.flingToBegining_Android();

		// add elements to list of workfields displaying in first screen
		specifiedElement.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'"
						+ workFieldType + "')]"));
		countOfDependentField = specifiedElement.size();
		System.out.println("Before swiping count: " + countOfDependentField);

		// if element is not exist scroll to specified element and add to list
		while (specifiedElement.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			specifiedElement.addAll(AndroidLocators.findElements_With_Xpath(
					"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'"
							+ workFieldType + "')]"));
			workFields.addAll(AndroidLocators.findElements_With_Xpath(
					"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));
			countOfDependentField = specifiedElement.size();
			System.out.println("After swiping fields count: " + countOfDependentField);

			for (int j = 0; j < workFields.size(); j++) {
				// if specified element found break the for loop
				if (specifiedElement.size() > 0 || workFields.get(j).getText().equals(workLastElementText)) {
					flag = true;
				}
			}
			// break the while loop
			if (flag == true) {
				break;
			}
		}

		boolean isDate = false, isText = false, isPickList = false, isCurrency = false, isNumber = false,
				isDropdown = false, isCustomer = false;

		// iterate and fill the form
		for (int k = 0; k < countOfDependentField; k++) {
			String OriginalText = specifiedElement.get(k).getText();
			String fieldsText = specifiedElement.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("***** Before removing regular expression ***** : " + OriginalText
					+ "\n..... After removing regexp ..... : " + fieldsText);
			if (fieldsText.equals(workFieldType)) {
				switch (fieldsText) {
				case "Text":
					if (!isText) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// text method
						workTextFieldDependency(workBaseCondition, fieldsText, workFieldInput);
						isText = true;
					}
				case "Curreny":
				case "Number":
					if (!isCurrency || !isNumber) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// currency input
						currencyInput(workBaseCondition, fieldsText, workFieldInput);
						isCurrency = true;
						isNumber = true;
					}
				case "Customer":
					if (!isCustomer) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// customer picker
						customerPicker(workBaseCondition, fieldsText, workFieldInput);
						isCustomer = true;
					}
				case "Pick List":
					if (!isPickList) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// pick list picker
						workPickList(workBaseCondition, fieldsText, workFieldInput);
						isPickList = true;
					}
				case "Dropdown":
					if (isDate) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Date picker
						datePicking(workBaseCondition, fieldsText, workFieldInput);
						isPickList = true;
					}
				}
			}
		}
	}

	// work field text input
	public static void workTextFieldDependency(String workBaseCondition, String fieldsText, String workFieldInput)
			throws InterruptedException {

		String getAboveOrBelowOfMainElement = commonMethodForInput(fieldsText);

		// initializing string for inputdata
		String textInputData = null;

		// text input data
		String[] myInput = { workFieldInput, workFieldInput + "extraWords", "extraWords" + workFieldInput,
				"extraWords" };

		// iterating the given input
		for (int k = 0; k < myInput.length; k++) {
			textInputData = myInput[k];
			System.out.println("------- Text Input data ------ :" + textInputData);

			// based previous element inputing the main element
			insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textInputData);

			// validating the work fields
			if (workBaseCondition.equals("Hide when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				if (CommonUtils.getdriver().findElements(MobileBy.xpath(
						"//*[@resource-id='in.spoors.effortplus:id/refreshButton']/parent::*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.waitForElementVisibility("//*[@class='android.widget.EditText']");
					CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).clear();
					AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/refreshButton");
				}
				MobileActionGesture.scrollTospecifiedElement(getAboveOrBelowOfMainElement);
				CommonUtils.getdriver().hideKeyboard();
			} else if (workBaseCondition.equals("Disable when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			} else if (workBaseCondition.equals("Mandatory when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			}
		}
	}// method close

	// using in fields dependency based on value in other field for input
	public static String commonMethodForInput(String fieldsText) {

		// initializing the string
		String getAboveOrBelowOfMainElement = null;

		// retrieving the text of the input above/below element
		if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
				.size() > 0) {
			getAboveOrBelowOfMainElement = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText
					+ "')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
					.getText();
			getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "")
					.split("\\(")[0];
			System.out.println("*** Edit Text label name above element is *** : " + getAboveOrBelowOfMainElement);
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
				+ "')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
				.size() > 0) {
			getAboveOrBelowOfMainElement = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText
					+ "')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
					.getText();
			getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "")
					.split("\\(")[0];
			System.out.println("*** Text View label name above element is *** : " + getAboveOrBelowOfMainElement);
		} else {
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
					.size() > 0) {
				getAboveOrBelowOfMainElement = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
						.getText();
				getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "")
						.split("\\(")[0];
				System.out.println("*** Edit Text label name below element is *** : " + getAboveOrBelowOfMainElement);
			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
					.size() > 0) {
				getAboveOrBelowOfMainElement = CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
								+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
						.getText();
				getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "")
						.split("\\(")[0];
				System.out.println("*** Text View label name below element is *** : " + getAboveOrBelowOfMainElement);
			}
		}
		return getAboveOrBelowOfMainElement;
	}

	// insert input data based on based on above or below element using
	// commonMethodForInput(dependent method)
	public static void insertInputBasedOnAboveOrBelowEle(String getAboveOrBelowOfMainElement, String InputData) {

		// based previous element inputing the main element
		if (CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
				.isDisplayed()) {
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.sendKeys(String.valueOf(InputData));
			} else if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.sendKeys(String.valueOf(InputData));
			}
		} else {
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.sendKeys(String.valueOf(InputData));
			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
					+ getAboveOrBelowOfMainElement
					+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.sendKeys(String.valueOf(InputData));
			}
		}
	}

	// number field dependecy based on value in other field
	public static void currencyInput(String workBaseCondition, String fieldsText, String workFieldInput) {
		// initializing and assigning
		int currencyInput = 0;
		currencyInput = Integer.parseInt(workFieldInput);

		String getAboveOrBelowOfMainElement = commonMethodForInput(fieldsText);

		for (int j = 0; j < 3; j++) {
			currencyInput = currencyInput - 1;
			System.out.println("------- Currency value ------ :" + currencyInput);

			// based previous element inputing the main element
			insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(currencyInput));

			// validating the work fields based on condition
			if (workBaseCondition.equals("Hide when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				if (CommonUtils.getdriver().findElements(MobileBy.xpath(
						"//*[@resource-id='in.spoors.effortplus:id/refreshButton']/parent::*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.waitForElementVisibility("//*[@class='android.widget.EditText']");
					CommonUtils.getdriver().findElement(MobileBy.className("android.widget.EditText")).clear();
					AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/refreshButton");
				}
				MobileActionGesture.scrollTospecifiedElement(getAboveOrBelowOfMainElement);
				CommonUtils.getdriver().hideKeyboard();
			} else if (workBaseCondition.equals("Disable when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			} else if (workBaseCondition.equals("Mandatory when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			}
		}
	} // method close

	// customer field dependecy based on value in other field
	public static void customerPicker(String workBaseCondition, String fieldsText, String workFieldInput)
			throws MalformedURLException {

		// initializing and assigning
		String customer = null;
		customer = workFieldInput;
		String[] cusArray = customer.split(",");

		for (int j = 0; j < cusArray.length; j++) {
			MobileActionGesture.scrollUsingText(fieldsText);
			AndroidLocators.clickElementusingXPath(
					"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button");
			CustomerPageActions.customerSearch(cusArray[j]);
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + cusArray[j] + "']")).size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + cusArray[j] + "']")).click();
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
				System.out.println("---- Customer found ---- !!");
			}
		}
	}

	// picklist field dependency based on value in other fields
	public static void workPickList(String workBaseCondition, String fieldsText, String workFieldInput) {
		// initializing and assigning
		String pickList = null;
		pickList = workFieldInput;
		String[] pickListArray = pickList.split(",");

		// inputting the list data
		for (int j = 0; j < pickListArray.length; j++) {
			MobileActionGesture.scrollUsingText(fieldsText);
			CommonUtils.getTextAndScrollToElement(pickList);
			CommonUtils.getTextAndScrollToElement(
					"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button");
			AndroidLocators.clickElementusingXPath(
					"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button");
			CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
			AndroidLocators.clickElementusingXPath("//*[@content-desc='Search']");
			CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[@resource-id='in.spoors.effortplus:id/search_src_text']"))
					.sendKeys(pickListArray[j]);
			AndroidLocators.pressEnterKeyInAndroid();
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
					.size() > 0) {
				AndroidLocators.clickElementusingXPath("//*[@text='" + pickListArray[j] + "']");
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
				System.out.println("*** Picklist found and picked successfully *** !!");
			}
			// validating the conditions
			if (workBaseCondition.equals("Hide when") || workBaseCondition.equals("Disable when")
					|| workBaseCondition.equals("Mandatory when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
			}
		}
	}

	// dropdown field dependency based on value in other fields
	public static void dropdownSelection(String workBaseCondition, String fieldsText, String workFieldInput) {

		// initializing and assigning
		String dropDown = null;
		dropDown = workFieldInput;
		String[] dropDownArray = dropDown.split(",");

		for (int j = 0; j < dropDownArray.length; j++) {
			MobileActionGesture.scrollUsingText(fieldsText);
			CommonUtils.getTextAndScrollToElement("//*[starts-with(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.Spinner/android.widget.TextView");
			if (CommonUtils.getdriver()
					.findElement(MobileBy.xpath(
							"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Spinner"))
					.isDisplayed()) {
				AndroidLocators.clickElementusingXPath(
						"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Spinner");
				AndroidLocators
						.clickElementusingXPath("//android.widget.CheckedTextView[@text='" + dropDownArray[j] + "']");
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + fieldsText + "')]");
			} else {
				AndroidLocators
						.clickElementusingXPath("//android.widget.CheckedTextView[@text='" + dropDownArray[j] + "']");
			}

			// validating the conditions
			if (workBaseCondition.equals("Hide when") || workBaseCondition.equals("Disable when")
					|| workBaseCondition.equals("Mandatory when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
			}
		}
	}

	// date field dependency based on value in other fields
	public static void datePicking(String workBaseCondition, String fieldsText, String workFieldInput) {
		// initializing and assigning
		String dateString = null;
		dateString = workFieldInput;
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");
		Calendar c = Calendar.getInstance();

		try {
			// Setting the date to the given date
			c.setTime(DateFor.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("---- Given date is ---- : " + DateFor.format(c.getTime()));

		for (int j = 0; j < 3; j++) {
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, -1);
			// conversion of date
			String newDate = DateFor.format(c.getTime());
			// Date Printing
			System.out.println(" **** My Date is **** : " + newDate);

			MobileActionGesture.scrollUsingText(fieldsText);
			CommonUtils.getTextAndScrollToElement(
					"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button");
			AndroidLocators.clickElementusingXPath(
					"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button");
			CommonUtils.alertContentXpath();
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']"))
					.size() > 0) {
				AndroidLocators.clickElementusingXPath("//*[@content-desc='" + newDate + "']");
			} else {
				do {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Next month']")).click();
				} while (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']"))
						.size() > 0);
				AndroidLocators.clickElementusingXPath("//*[@content-desc='" + newDate + "']");
			}
			AndroidLocators.clickElementusingXPath("//*[@text='OK']");

			// adding the date
			c.add(Calendar.DAY_OF_MONTH, 2);

			System.out.println("---- After increasing the date ---- :" + DateFor.format(c.getTime()));
		}
	}

	// work error and warn message
	public static void workErrorAndWarnMeassage(String errorCondition, String inputValue)
			throws MalformedURLException, InterruptedException, ParseException {
		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]");

		// retrieving the list count
		int workFieldsCount = workFields.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		workFields.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workFields.isEmpty() && workFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workFields.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@class,'Text')]")));

			// get the count of work fields
			workFieldsCount = workFields.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ workFields.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + workFields.get(i).getText());

				// if list matches with last element the loop will break
				if (workFields.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + workFields.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();

		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = workFields.get(j).getText();
			String workFieldsText = workFields.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);

			// splitting and assigning input to variable
			String[] inputArray = inputValue.split(",");
			String currencyInput = inputArray[0];
			String dateInput = null;
			dateInput = inputArray[1];

			switch (workFieldsText) {
			case "Work Name":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					Work.generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.sendKeys(Work.generateWorkName);
				}
				break;
			case "Ends":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
					CommonUtils.alertContentXpath();
					try {
						Forms_basic.dateScriptInForms(1);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CommonUtils.wait(3);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					CommonUtils.alertContentXpath();
					Work.workEndTime(1, 5);
					CommonUtils.wait(1);
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					currencyErrorInput(workFieldsText, currencyInput, errorCondition);
				}
				break;

			case "Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					currencyErrorInput(workFieldsText, currencyInput, errorCondition);
				}
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					dateErrorAndWarnMessageValidation(workFieldsText, dateInput, errorCondition);
				}
				break;
			}
		}
	}

	// currency error and warn message input
	public static int currencyErrorInput(String workFieldsText, String currencyInput, String errorCondition)
			throws InterruptedException, MalformedURLException, ParseException {
		// initializing assigniong
		int currencyErrorInput = 0;
		currencyErrorInput = Integer.parseInt(currencyInput);

		// assigning inputdata to variable
		String getAboveOrBelowOfMainElement = commonMethodForInput(workFieldsText);

		for (int j = 0; j < 3; j++) {
			currencyErrorInput = currencyErrorInput - 1;
			System.out.println("------- Currency value ------ :" + currencyErrorInput);
			// based previous element inputing the main element
			if (CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
					.isDisplayed()) {
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]/parent::*/parent::*/parent::*/parent::*/following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(String.valueOf(currencyErrorInput));
				} else if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//following::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(String.valueOf(currencyErrorInput));
				}
			} else {
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(String.valueOf(currencyErrorInput));
				} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//preceding-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(String.valueOf(currencyErrorInput));
				}
			}

			// validating error and warning condition
			if (errorCondition.equals("Show Error when")) {
				saveWork();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);
			} else if (errorCondition.equals("Show Warning when")) {
				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]"))
						.isDisplayed()) {
					fillMandatoryFieldsForWarningValidations();
					saveWork();
					FormAdvanceSettings.handlingWarningAlert();
				}
			}
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

			// increasing the currency value
			currencyErrorInput = currencyErrorInput + 2;
			System.out.println(".... After increaing the currency value .... : " + currencyErrorInput);
		}
		return currencyErrorInput;
	}

	// date error and warn meessage validation
	public static void dateErrorAndWarnMessageValidation(String workFieldsText, String dateInput, String errorCondition)
			throws ParseException, InterruptedException, MalformedURLException {

		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

		java.util.Date date = new java.util.Date();
		// formating current date using date formatter
		String toDaydate = DateFor.format(date);
		// printing current date
		System.out.println("**** Todays date is **** : " + toDaydate);

		// parse the today date
		Date presentDate = DateFor.parse(toDaydate);
		// print parsing today date
		System.out.println("....After parsing today date is ... : " + presentDate);

		// parsing given date from string
		date = DateFor.parse(dateInput);
		// formating input date into our date formatter
		String formatGivenDate = DateFor.format(date);
		// printing given date
		System.out.println("---- Given date is ---- : " + formatGivenDate);

		for (int p = 0; p < 3; p++) {
			// swipe to specified field
			MobileActionGesture.scrollUsingText(workFieldsText);
			// Number of Days to add
			date = DateUtils.addDays(date, -1);
			// conversion of date
			String inputDate = DateFor.format(date);

			// Printing customized date
			System.out.println(" **** My given date is **** : " + inputDate);

			if (CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button"))
					.size() > 0) {

				AndroidLocators.clickElementusingXPath(
						"//*[starts-with(@text,'" + workFieldsText + "')]/parent::*/parent::*/android.widget.Button");

				CommonUtils.alertContentXpath();
				// providing the given input
				Forms_basic.getCalendarDates(inputDate);
			}

			// validating date based on codition
			if (errorCondition.equals("Show Error when")) {
				saveWork();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);
			} else if (errorCondition.equals("Show Warning when")) {
				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath(
						"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]"))
						.size() > 0) {
					fillMandatoryFieldsForWarningValidations();
					saveWork();
					FormAdvanceSettings.handlingWarningAlert();
				}
			}

			// adding new date
			date = DateUtils.addDays(date, 2);
			// format the increased date
			String newIncreasedDate = DateFor.format(date);
			// printing the increased date
			System.out.println("**** After increasing the date **** : " + newIncreasedDate);
		}
	}

	// warning validations fill mandatory fields
	public static void fillMandatoryFieldsForWarningValidations()
			throws MalformedURLException, InterruptedException, ParseException {
		// work all fields
		List<MobileElement> workFields = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]");

		// retrieving the list count
		int workFieldsCount = workFields.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		workFields.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]"));

		// Store work last element into 'workLastElement string'
		workLastElement = workFields.get(workFields.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		workFields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workFields.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]"));

		// get the count of work fields present in the first screen
		workFieldsCount = workFields.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!workFields.isEmpty() && workFields != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workFields.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]")));

			// get the count of work fields
			workFieldsCount = workFields.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ workFields.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + workFields.get(i).getText());

				// if list matches with last element the loop will break
				if (workFields.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + workFields.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();
		boolean isPriority = false, isWorkName = false, isEnds = false, isMultipicklist = false,
				isAddressSameAsCustomer = false, isMultiselectdropdown = false, isyesNo = false, isSignature = false;

		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workFieldsText = workFields.get(j).getText();
			workFieldsText = workFields.get(j).getText().replaceAll("[!@#$%&()*,.?\":{}|<>]", "");

			switch (workFieldsText) {
			case "Work Name":
				if (!isWorkName) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						Work.generateWorkName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
								.sendKeys(Work.generateWorkName);
					}
					isWorkName = true;
				}
				break;
			case "Description":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Ends":
				if (!isEnds) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_date_button']");
						CommonUtils.alertContentXpath();
						try {
							Forms_basic.dateScriptInForms(2);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						CommonUtils.wait(3);
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
						CommonUtils.alertContentXpath();
						Work.workEndTime(2, 5);
						CommonUtils.wait(1);
					}
					isEnds = true;
				}
				break;
			case "Customer":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.wait(5);
						System.out.println("Now customer is picked");
					} else {
						System.out.println("Customer is already selected!!");
					}
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.wait(5);
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Customer-SYS":
				MobileActionGesture.scrollUsingDirectText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.wait(5);
						System.out.println("Now customer is picked");
					} else {
						System.out.println("Customer is already selected!!");
					}
				} else {
					MobileActionGesture.directScrollToView(workFieldsText);
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text ,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]"))
							.isEnabled()) {
						AndroidLocators.clickElementusingXPath("//*[starts-with(@text ,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]");
						CommonUtils.waitForElementVisibility("//*[@text='Customers']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
						} else {
							CustomerPageActions.customerFab();
							CustomerPageActions.createCustomer();
							CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
							AndroidLocators.clickElementusingXPath(
									"//*[@text='" + CustomerPageActions.randomstringCusName + "']");
						}
						CommonUtils.wait(5);
						System.out.println("Now customer is picked");
					}
				}
				break;
			case "Employee":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A EMPLOYEE");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
				}
				break;
			case "Employee-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Button");
					if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
					}
					Thread.sleep(500);
				}
				break;
			case "Priority":
				if (!isPriority) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("Pick a value");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement priority = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(priority);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					isPriority = true;
				}
				break;
			case "Address same as customer":
				if (!isAddressSameAsCustomer) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement address_Same_As_customer = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(address_Same_As_customer);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(2).click();
						}
					}
					isAddressSameAsCustomer = true;
				}
				break;
			case "Phone Number(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Phone(Optional)":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendPhoneNumberInputUsing_xpath("//*[starts-with(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Street":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Area":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "City":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Landmark":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Country-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick a country");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				break;
			case "Country":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick a country");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileElement country = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ workFieldsText + "')]/parent::*/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(country);
					MobileActionGesture.scrollTospecifiedElement("Australia");
				}
				break;
			case "State":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Pincode":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Location-SYS":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A LOCATION");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				}
				Thread.sleep(500);
				break;
			case "Location":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A LOCATION");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				} else {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.clickElementusingXPath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/parent::*/parent::*/*[@resource-id='in.spoors.effortplus:id/pick_location_button']");
					Thread.sleep(5000);
					CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
				}
				Thread.sleep(500);
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendNumberInputUsing_xpath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Custom Entity":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("ENTITY");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"))
							.size() > 0) {
						MobileElement customEntity = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"));
						MobileActionGesture.tapByElement(customEntity);
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
						} else if (CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(0).click();
						} else {
							// write entity item creation method
							Forms_basic.createEntity();
						}
						Thread.sleep(500);
					} else {
						System.out.println("Custom entity is already picked");
					}
				}
				break;
			case "Customer Type":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick customer type");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick customer type')]"))
							.size() > 0) {
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick customer type')]"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					} else {
						MobileActionGesture.directScrollToView(workFieldsText);
						MobileElement cusType = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick customer type')]"));
						MobileActionGesture.singleLongPress(cusType);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					System.out.println("Customer type is aready picked");
				}
				break;
			case "DateTime":
				MobileActionGesture.directScrollToView(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
								.click();
						CommonUtils.alertContentXpath();
						try {
							Forms_basic.dateScriptInForms(2);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Thread.sleep(500);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'"
								+ workFieldsText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
									+ workFieldsText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.click();
							CommonUtils.alertContentXpath();
							Work.workEndTime(2, 5);
							Thread.sleep(100);
						}
					} else {
						System.out.println("DateTime is already picked");
					}
				}
				break;
			case "Time":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);

					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
								.click();
						CommonUtils.alertContentXpath();
						Work.workEndTime(2, 5);
						Thread.sleep(100);
					} else {
						System.out.println("Time already picked");
					}
				}
				break;
			case "Date":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
							.size() > 0) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.click();
						CommonUtils.alertContentXpath();
						try {
							Forms_basic.dateScriptInForms(2);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Thread.sleep(500);
					} else {
						System.out.println("Date is already picked");
					}
				}
				break;
			case "Dropdown":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick a value");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
							.size() > 0) {
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
					} else {
						System.out.println("Dropdown is already picked");
					}
				}
				break;
			case "Pick List":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK LIST");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
							.size() > 0) {
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
								.click();
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
						} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).isDisplayed()) {
							CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
						}
						Thread.sleep(500);
					} else {
						System.out.println("Pick List is already picked");
					}
				}
				break;
			case "Form":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("PICK A FORM");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"))
							.size() > 0) {
						MobileElement form = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"));
						MobileActionGesture.tapByElement(form);
						CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
						try {
							if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0).click();
							} else {
								CommonUtils.getdriver().findElementById("load_more_button").click();
//									CommonUtils.waitForElementVisibility(
//											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
								CommonUtils.wait(5);
								if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
									CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
									Forms_basic.verifyFormPagesAndFill();
									Forms_basic.formSaveButton();
									CommonUtils.goBackward();
									CommonUtils.waitForElementVisibility(
											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
									if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
											.size() > 0) {
										CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
												.click();
									}
								}
							}
							Thread.sleep(500);
						} catch (Exception e) {
							System.out.println(e);
						}
					} else {
						System.out.println("Form is already picked");
					}
				}
				break;
			case "Email(Optional)":
			case "Email":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendEmailInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "URL(Optional)":
			case "URL":
				MobileActionGesture.scrollUsingText(workFieldsText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					AndroidLocators.sendUrlInputusing_XPath("//*[contains(@text,'" + workFieldsText + "')]");
				}
				break;
			case "Multi Pick List":
				if (!isMultipicklist) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("PICK MULTI PICK LIST");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
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
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						Thread.sleep(500);
					}
					isMultipicklist = true;
				}
				break;
			case "Territory":
				MobileActionGesture.scrollUsingText(workFieldsText);
				MobileActionGesture.scrollUsingText("Pick territory type");
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]")).size() > 0) {
					if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick territory type')]"))
							.size() > 0) {
						MobileElement terriory = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick territory type')]"));
						MobileActionGesture.singleLongPress(terriory);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					} else {
						System.out.println("Territory is already selected");
					}
				}
				break;
			case "Multi Select Dropdown":
				if (!isMultiselectdropdown) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					MobileActionGesture.scrollUsingText("PICK MULTI SELECT DROPDOWN(S)");
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText
										+ "')]/parent::*/parent::*/android.widget.Button"));
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
						AndroidLocators.clickElementusingXPath("//*[@text='OK']");
						Thread.sleep(500);
					}
					isMultiselectdropdown = true;
				}
				break;
			case "YesNo":
				if (!isyesNo) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
								+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"))
								.size() > 0) {
							MobileElement yesno = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText
											+ "')]/parent::*/parent::*/android.widget.Spinner"));
							MobileActionGesture.singleLongPress(yesno);
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						} else {
							System.out.println("YesNo is already selected");
						}
					}
					isyesNo = true;
				}
				break;
			case "Signature":
				if (!isSignature) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + workFieldsText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(workFieldsText);
						Work.work_Capturing_Signature(workFieldsText);
					} else {
						MobileActionGesture.scrollUsingText(workFieldsText);
						Work.work_Capturing_Signature(workFieldsText);
					}
					isSignature = true;
				}
				break;
			} // switch statement close
		} // for loop close
	} // method close

	//validate work fields
	public static void validateWorkfields() throws InterruptedException, MalformedURLException {
		Work.saveWork();
		Work.workSearch(Work.generateWorkName);
		AndroidLocators.clickElementusingXPath("//*[@resource-id='in.spoors.effortplus:id/titleTextView']");	
		MobileActionGesture.flingVerticalToBottom_Android();
	}
}
