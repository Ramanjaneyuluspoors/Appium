package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
//		CommonUtils.getdriver().findElement(MobileBy.id("saveWork")).click();
		CommonUtils.alertContentXpath();
		MobileElement saveButton = CommonUtils.getdriver().findElement(MobileBy.id("workSaveButton"));
		if (saveButton.isDisplayed()) {
			saveButton.click();
			try {
				if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@resource-id='android:id/button1']"))
						.isDisplayed()) {
					CommonUtils.OkButton("CONTINUE");
					System.out.println(" ---- work is saved successfully!! ----");
				}
			} catch (Exception e) {
				System.out.println(" **** work time is not override **** ");
			}
		}
	}

	// work min max validations
	public static void workFields_Min_Max_Testing(int min, int max) throws MalformedURLException, InterruptedException {
		// Declaring the workInputElements list
		List<MobileElement> workInputElements = AndroidLocators.findElements_With_ClassName("android.widget.EditText");

		// Declaring the workLabelElements list
		List<MobileElement> workLabelElements = AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");

		// merging the both list
		List<MobileElement> newList = workLabelElements;
		newList.addAll(workInputElements);

		// retrieving the list count
		int workFieldsCount = newList.size();
		System.out.println(" ===== Work Fields Count ===== : " + workFieldsCount);

		// clear the elements from list
		newList.clear();
		workLabelElements.clear();
		workInputElements.clear();

		// Initializing the string to retrieve last element
		String workLastElement = null;

		// scroll to bottom and add work fields to list
		MobileActionGesture.flingVerticalToBottom_Android();
		workInputElements.addAll(AndroidLocators.findElements_With_ClassName("android.widget.EditText"));
		workLabelElements.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));

		// merge the list
		// newList = ListUtils.union(workLabelElements, workInputElements);
		workLabelElements.addAll(workInputElements);
		newList = workLabelElements;

		// Store work last element into 'workLastElement string'
		workLastElement = newList.get(newList.size() - 1).getText();
		System.out.println(" ***** Work Last Element is ***** : " + workLastElement);

		// remove the elements from the list
		newList.clear();
		workLabelElements.clear();
		workInputElements.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the work fields present in the first screen
		workInputElements.addAll(AndroidLocators.findElements_With_ClassName("android.widget.EditText"));
		workLabelElements.addAll(AndroidLocators.findElements_With_Xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));

		// merge the list
		newList = workLabelElements;
		newList.addAll(workInputElements);

		// get the count of work fields present in the first screen
		workFieldsCount = newList.size();
		System.out.println(" ---- Before swiping the device screen fields count are ---- : " + workFieldsCount);

		// swipe and retrieve the work fields until the last element found
		while (!newList.isEmpty() && newList != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			workInputElements.addAll(AndroidLocators.findElements_With_ClassName("android.widget.EditText"));
			workLabelElements.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));

			// merge the list
//			newList = ListUtils.union(workLabelElements, workInputElements);
			newList = workLabelElements;
			newList.addAll(workInputElements);

			// get the count of work fields
			workFieldsCount = newList.size();
			System.out.println(" .... After swiping the device screen fields count are .... : " + workFieldsCount);

			// if work last element matches with newList then break the for loop
			for (int i = 0; i < workFieldsCount; i++) {

				// printing elements from last to first
				System.out.println("***** Print work fields elements text ***** : "
						+ newList.get(workFieldsCount - (i + 1)).getText());

				// printing the elements in the list
				System.out.println("===== Work fields text ===== : " + newList.get(i).getText());

				// if list matches with last element the loop will break
				if (newList.get(i).getText().equals(workLastElement)) {
					System.out.println("----- Work fields text inside elements ----- : " + newList.get(i).getText());
					flag = true;
				}
			}
			// break the while loop if the work last element found
			if (flag == true)
				break;
		} // break the while loop

		MobileActionGesture.flingToBegining_Android();
		int n = 0, p = 0;
		int min_test = min, max_text = max;

		// providing input for work fields by iterating using the workList(newList)
		for (int j = 0; j < workFieldsCount; j++) {
			String workOriginalFields = newList.get(j).getText();
			String workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			workFieldsText = workFieldsText.split("\\(")[0];
			System.out.println("***** Before removing special character ***** : " + workOriginalFields
					+ "\n----- After removing special character ----- : " + workFieldsText);

			switch (workFieldsText) {
			case "Work Name":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
							.build();
					// inserting min input value
					for (n = 0; n < 2; n++) {
						min_test = min_test - 1;
						String textMinInput = textGenerator.generate(min_test);
						String textMinInput1 = textGenerator.generate(min);
						System.out.println("*** Given minimum length is *** : " + textMinInput1);
						System.out.println("*** Validation minimum length is *** : " + textMinInput);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
								.sendKeys(textMinInput);
						saveWork();
						// validating min values
						if (textMinInput.length() < textMinInput1.length()) {
							String text = CommonUtils.OCR();
							System.out.println("---- Expected toast message for min input is ---- : " + text);
							Assertions.assertFalse(
									text.contains("" + workFieldsText + " Smaller than the minimum allowed length"
											+ (textMinInput1) + "."),
									"" + workFieldsText + " Smaller than the minimum allowed length " + (textMinInput1)
											+ ".");
						}
						// Declaring the workInputElements list
						workInputElements = AndroidLocators.findElements_With_ClassName("android.widget.EditText");

						// Declaring the workLabelElements list
						workLabelElements = AndroidLocators.findElements_With_Xpath(
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");

						// merge the list
						newList = workLabelElements;
						newList.addAll(workInputElements);

						// get the count of work fields present in the first screen
						workFieldsCount = newList.size();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character inside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character inside loop ----- : "
								+ workFieldsText);

						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).clear();
						AndroidLocators.clickElementusingXPath("//*[contains(@text,'Description')]");
						CommonUtils.getdriver().hideKeyboard();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character outside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character outside loop ----- : "
								+ workFieldsText);

						min_test = min_test + 2;
					}
					// inserting max input values
					for (p = 0; p < 2; p++) {
						max_text = max_text - 1;
						String textMaxInput = textGenerator.generate(max_text);
						String textMaxInput1 = textGenerator.generate(max);
						System.out.println("*** Given maximum length is *** : " + textMaxInput1);
						System.out.println("*** Validation maximum length is *** : " + textMaxInput);
//						AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
//						CommonUtils.getdriver().hideKeyboard();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
								.sendKeys(textMaxInput);
						saveWork();
						// validating max values
						if (textMaxInput.length() > textMaxInput1.length()) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max input is " + text);
							Assertions.assertFalse(
									text.contains("" + workFieldsText + " larger than the maximum allowed length "
											+ (textMaxInput1) + "."),
									"" + workFieldsText + " larger than the maximum allowed length " + (textMaxInput1)
											+ ".");
						}
						// Declaring the workInputElements list
						workInputElements = AndroidLocators.findElements_With_ClassName("android.widget.EditText");

						// Declaring the workLabelElements list
						workLabelElements = AndroidLocators.findElements_With_Xpath(
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");

						// merge the list
						newList = workLabelElements;
						newList.addAll(workInputElements);

						// get the count of work fields present in the first screen
						workFieldsCount = newList.size();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character inside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character inside loop ----- : "
								+ workFieldsText);

						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).clear();
						AndroidLocators.clickElementusingXPath("//*[contains(@text,'Description')]");
						CommonUtils.getdriver().hideKeyboard();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character outside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character outside loop ----- : "
								+ workFieldsText);

						max_text = max_text + 2;
					}
//					CommonUtils.getTextAndScrollToElement("//*[contains(@text,'" + workFieldsText + "')]");
//					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(textGenerator.generate(max));
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
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/*[@resource-id='in.spoors.effortplus:id/pick_time_buton']");
					CommonUtils.alertContentXpath();
					Work.workEndTime(2, 5);
					CommonUtils.wait(1);
				}
				break;
			case "Text":
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
							.build();
					// inserting min input value
					for (n = 0; n < 2; n++) {
						min_test = min_test - 1;
						String textMinInput = textGenerator.generate(min_test);
						String textMinInput1 = textGenerator.generate(min);
						System.out.println("*** Given minimum length is *** : " + textMinInput1);
						System.out.println("*** Validation minimum length is *** : " + textMinInput);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
								.sendKeys(textMinInput);
						saveWork();
						// validating min values
						if (textMinInput.length() < textMinInput1.length()) {
							String text = CommonUtils.OCR();
							System.out.println("---- Expected toast message for min input is ---- : " + text);
							Assertions.assertFalse(
									text.contains("" + workFieldsText + " Smaller than the minimum allowed length"
											+ (textMinInput1) + "."),
									"" + workFieldsText + " Smaller than the minimum allowed length " + (textMinInput1)
											+ ".");
						}
						// Declaring the workInputElements list
						workInputElements = AndroidLocators.findElements_With_ClassName("android.widget.EditText");

						// Declaring the workLabelElements list
						workLabelElements = AndroidLocators.findElements_With_Xpath(
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");

						// merge the list
						newList = workLabelElements;
						newList.addAll(workInputElements);

						// get the count of work fields present in the first screen
						workFieldsCount = newList.size();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character inside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character inside loop ----- : "
								+ workFieldsText);

						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).clear();
						MobileActionGesture.scrollTospecifiedElement("Description");
						CommonUtils.getdriver().hideKeyboard();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character outside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character outside loop ----- : "
								+ workFieldsText);

						min_test = min_test + 2;
					}
					// inserting max input values
					for (p = 0; p < 2; p++) {
						max_text = max_text - 1;
						String textMaxInput = textGenerator.generate(max_text);
						String textMaxInput1 = textGenerator.generate(max);
						System.out.println("*** Given maximum length is *** : " + textMaxInput1);
						System.out.println("*** Validation maximum length is *** : " + textMaxInput);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
								.sendKeys(textMaxInput);
						saveWork();
						// validating max values
						if (textMaxInput.length() > textMaxInput1.length()) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max input is " + text);
							Assertions.assertFalse(
									text.contains("" + workFieldsText + " larger than the maximum allowed length "
											+ (textMaxInput1) + "."),
									"" + workFieldsText + " larger than the maximum allowed length " + (textMaxInput1)
											+ ".");
						}
						// Declaring the workInputElements list
						workInputElements = AndroidLocators.findElements_With_ClassName("android.widget.EditText");

						// Declaring the workLabelElements list
						workLabelElements = AndroidLocators.findElements_With_Xpath(
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView");

						// merge the list
						newList = workLabelElements;
						newList.addAll(workInputElements);

						// get the count of work fields present in the first screen
						workFieldsCount = newList.size();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character inside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character inside loop ----- : "
								+ workFieldsText);

						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).clear();
						MobileActionGesture.scrollTospecifiedElement("Description");
						CommonUtils.getdriver().hideKeyboard();

						// get string
						workOriginalFields = newList.get(j).getText();
						workFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*,.?\":{}|<>]", "");
						workFieldsText = workFieldsText.split("\\(")[0];
						System.out.println("***** Before removing special character outside loop ***** : "
								+ workOriginalFields + "\n----- After removing special character outside loop ----- : "
								+ workFieldsText);

						max_text = max_text + 2;
					}
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(textGenerator.generate(max));
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
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(workFieldsText);
					// inserting min input value
					for (n = 0; n < 2; n++) {
						min_test = min_test - 1;
						System.out.println("*** Minimum value *** : " + min_test);
						AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
						CommonUtils.getdriver().hideKeyboard();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
								.sendKeys(String.valueOf(min_test));
						saveWork();
						if (min < min_test) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for min value is :" + text);
							Assertions.assertFalse(
									text.contains("" + workFieldsText + " cannot be less than " + min + "."),
									"" + workFieldsText + " cannot be less than " + min + ".");
						}
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).clear();
						min_test = min_test + 2;
					}

					// inserting max input value
					for (p = 0; p < 2; p++) {
						max_text = max_text - 1;
						System.out.println("*** Maximum value is *** : " + max_text);
						AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
						CommonUtils.getdriver().hideKeyboard();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
								.sendKeys(String.valueOf(max_text));
						saveWork();
						if (max > max_text) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max value is :" + text);
							Assertions.assertFalse(
									text.contains("" + workFieldsText + " cannot be greater than " + max + "."),
									"" + workFieldsText + " cannot be greater than " + max + ".");
						}
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]")).clear();
						max_text = max_text + 2;
					}
					AndroidLocators.clickElementusingXPath("//*[contains(@text,'" + workFieldsText + "')]");
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + workFieldsText + "')]"))
							.sendKeys(String.valueOf(max));
					saveWork();
				}
				break;
			}
		}
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
			getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "").split("\\(")[0];
			System.out.println("*** Edit Text label name above element is *** : " + getAboveOrBelowOfMainElement);
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + fieldsText
				+ "']/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
				.size() > 0) {
			getAboveOrBelowOfMainElement = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
					+ fieldsText
					+ "')]/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
					.getText();
			getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "").split("\\(")[0];
			System.out.println("*** Text View label name above element is *** : " + getAboveOrBelowOfMainElement);
		} else {
			if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
					.size() > 0) {
				getAboveOrBelowOfMainElement = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ fieldsText
						+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.EditText"))
						.getText();
				getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "")
						.split("\\(")[0];
				System.out.println("*** Edit Text label name below element is *** : " + getAboveOrBelowOfMainElement);
			} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText
					+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
					.size() > 0) {
				getAboveOrBelowOfMainElement = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'"
						+ fieldsText
						+ "')]//parent::*//parent::*//parent::*//parent::*/following-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
						.getText();
				getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.replaceAll("[!@#$%&*,.?\":{}|<>]", "")
						.split("\\(")[0];
				System.out.println("*** Text View label name below element is *** : " + getAboveOrBelowOfMainElement);
			}
		}

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
			if (CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
					.isDisplayed()) {
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(textInputData);
				} else if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(textInputData);
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
							.sendKeys(textInputData);
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
							.sendKeys(textInputData);
				}
			}

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

	//number field dependecy based on value in other field
	public static void currencyInput(String workBaseCondition, String fieldsText, String workFieldInput) {
		//initializing and assigning
		int currencyInput = 0;
		currencyInput = Integer.parseInt(workFieldInput);
	
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
		} else if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + fieldsText
				+ "']/parent::*/parent::*/parent::*/parent::*/preceding-sibling::android.widget.LinearLayout[1]//android.widget.TextView"))
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

		for (int j = 0; j < 3; j++) {
			currencyInput = currencyInput - 1;
			System.out.println("------- Currency value ------ :" + currencyInput);
			// based previous element inputing the main element
			if (CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement + "')]"))
					.isDisplayed()) {
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'"
							+ getAboveOrBelowOfMainElement
							+ "')]/parent::*/parent::*/parent::*/parent::*/following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(String.valueOf(currencyInput));
				} else if (CommonUtils.getdriver().findElements(By.xpath("//*[contains(@text,'"
						+ getAboveOrBelowOfMainElement
						+ "')]//parent::*//parent::*//following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.clear();
					CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + getAboveOrBelowOfMainElement
							+ "')]//parent::*//parent::*//following-sibling::android.widget.LinearLayout[1]/*/*//android.widget.EditText"))
							.sendKeys(String.valueOf(currencyInput));
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
							.sendKeys(String.valueOf(currencyInput));
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
							.sendKeys(String.valueOf(currencyInput));
				}
			}
 
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
	} //method close
	
	//customer field dependecy based on value in other field
	public static void customerPicker(String workBaseCondition, String fieldsText, String workFieldInput) throws MalformedURLException {
		
		//initializing and assigning
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

	//dropdown field dependency based on value in other fields
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
	
	//date field dependency based on value in other fields
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
	
	//work error and warn message 
	public static void workErrorAndWarnMeassage() {
		
	}
	
	
}
