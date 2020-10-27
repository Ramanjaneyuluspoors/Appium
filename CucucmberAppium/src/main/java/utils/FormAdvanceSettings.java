package utils;

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

import Actions.CustomerPageActions;
import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import nl.flotsam.xeger.Xeger;

public class FormAdvanceSettings {
	static String[] baseCondition = { "Hide when", "Disable when", "Mandatory when" };

	public static void minMaxTesting(int min, int max) throws MalformedURLException, InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));
		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println("pagination exists");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				pagination.get(i).click();
				fillMinMaxData(min, max, i);
			}
		} else {
			System.out.println("pagination not exists");
			Forms.verifySectionToClickAdd();
			min_max_withoutPages(min, max);
		}
		Forms.formSaveButton();
	}

	public static void fillMinMaxData(int min, int max, int i) throws MalformedURLException, InterruptedException {
		// get all formfields elements xpath
		int j = i + 1;
		List<MobileElement> formFields1 = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();
		String lastTxtElement = null;
		// swipe to bottom and get the last element from the list
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int k = 0; k < countOfFields; k++) {
				if (formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}

		// iterate and fill the form
		for (int m = 0; m < countOfFields; m++) {
			String originalText = formFields1.get(m).getText();
			String fieldsText = formFields1.get(m).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing special character: " + originalText + "\nafter removing regexp: " + fieldsText);

			int n = 0, p = 0;
			int min_test = min, max_text = max;

			switch (fieldsText) {
			case "Text":
			case "G-Text":
			case "S-Text":
				MobileActionGesture.scrollUsingText(fieldsText);
				RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					String textMinInput = textGenerator.generate(min_test);
					String textMinInput1 = textGenerator.generate(min);
					System.out.println("min input data: " + textMinInput);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(textMinInput);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (textMinInput1.length() < textMinInput.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max input is " + text);
						Assertions.assertFalse(
								text.contains(
										"" + fieldsText + " cannot be shorter than " + textMinInput1 + "characters."),
								"" + fieldsText + " cannot be shorter than " + textMinInput1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					String textMaxInput = textGenerator.generate(max_text);
					String textMaxInput1 = textGenerator.generate(max);
					System.out.println("Max input data is: " + textMaxInput);
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(textMaxInput);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (textMaxInput1.length() < textMaxInput.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max input is " + text);
						Assertions.assertFalse(
								text.contains(
										"" + fieldsText + " cannot be longer than " + textMaxInput1 + "characters."),
								"" + fieldsText + " cannot be longer than " + textMaxInput1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(textGenerator.generate(max));
				MobileActionGesture.scrollUsingText("Currency");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
				break;
			case "Phone":
			case "Phone Number":
			case "G-Phone":
			case "S-Phone":
			case "G-Phone Number":
			case "S-Phone NUmber":
				MobileActionGesture.scrollUsingText(fieldsText);
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					String phoneNum = RandomStringUtils.randomNumeric(min_test);
					String phoneNum1 = RandomStringUtils.randomNumeric(min);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(phoneNum);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (phoneNum1.length() < phoneNum.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for min input is " + text);
						Assertions.assertFalse(
								text.contains("" + fieldsText + " cannot be shorter than " + phoneNum1 + "characters."),
								"" + fieldsText + " cannot be shorter than " + phoneNum1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					String phoneNum = RandomStringUtils.randomNumeric(max_text);
					String phoneNum1 = RandomStringUtils.randomNumeric(max);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(phoneNum);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (phoneNum1.length() > phoneNum.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max input is " + text);
						Assertions.assertFalse(
								text.contains("" + fieldsText + " cannot be longer than " + phoneNum1 + "characters."),
								"" + fieldsText + " cannot be longer than " + phoneNum1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(RandomStringUtils.randomNumeric(max));
				MobileActionGesture.scrollUsingText("Currency");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
				break;
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				MobileActionGesture.scrollUsingText(fieldsText);
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					System.out.println("min input data: " + min_test);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(min_test));
					MobileActionGesture.scrollUsingText("Number");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (min < min_test) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for min value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be less than " + min + "."),
								"" + fieldsText + " cannot be less than " + min + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					MobileActionGesture.scrollUsingText(fieldsText);
					System.out.println("Max input data is: " + max_text);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(max_text));
					MobileActionGesture.scrollTospecifiedElement("Number");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (max > max_text) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be greater than " + max + "."),
								"" + fieldsText + " cannot be greater than " + max + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(String.valueOf(max));
				MobileActionGesture.scrollUsingText("Number");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
				break;
			case "Number":
			case "G-Number":
			case "S-Number":
				MobileActionGesture.scrollUsingText(fieldsText);
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					System.out.println("min input data: " + min_test);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(min_test));
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (min < min_test) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for min value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be less than " + min + "."),
								"" + fieldsText + " cannot be less than " + min + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					MobileActionGesture.scrollUsingText(fieldsText);
					System.out.println("Max input data is: " + max_text);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(max_text));
					MobileActionGesture.scrollTospecifiedElement("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (max > max_text) {
						String text = CommonUtils.OCR();
						System.out.println("Expected Toast message for max value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be greater than " + max + "."),
								"" + fieldsText + " cannot be greater than " + max + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(String.valueOf(max));
				MobileActionGesture.scrollUsingText("Currency");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
			default:
				break;
			}
		}
	}

	public static void min_max_withoutPages(int min, int max) throws MalformedURLException {
		List<MobileElement> minMaxFields = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int minMaxFieldsCount = minMaxFields.size();
		minMaxFields.clear();
		// swipe and get the last element from the list
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		minMaxFields.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = minMaxFields.get(minMaxFields.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		minMaxFields.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		minMaxFields.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		minMaxFieldsCount = minMaxFields.size();
		System.out.println("Before swiping fields count is: " + minMaxFieldsCount);

		// scroll and add elements to list until the lastelement
		while (!minMaxFields.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			minMaxFields.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			minMaxFieldsCount = minMaxFields.size();
			System.out.println("After swiping fields count: " + minMaxFieldsCount);
			for (int k = 0; k < minMaxFieldsCount; k++) {
				if (minMaxFields.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}

		// iterate and fill the form
		for (int m = 0; m < minMaxFieldsCount; m++) {
			String originalText = minMaxFields.get(m).getText();
			String fieldsText = minMaxFields.get(m).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing special character: " + originalText + "after removing regexp: " + fieldsText);

			int n = 0, p = 0;
			int min_test = min, max_text = max;

			switch (fieldsText) {
			case "Text":
			case "G-Text":
			case "S-Text":
				MobileActionGesture.scrollUsingText(fieldsText);
				RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					String textMinInput = textGenerator.generate(min_test);
					String textMinInput1 = textGenerator.generate(min);
					System.out.println("min input data: " + textMinInput);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(textMinInput);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (textMinInput1.length() < textMinInput.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max input is " + text);
						Assertions.assertFalse(
								text.contains(
										"" + fieldsText + " cannot be shorter than " + textMinInput1 + "characters."),
								"" + fieldsText + " cannot be shorter than " + textMinInput1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					String textMaxInput = textGenerator.generate(max_text);
					String textMaxInput1 = textGenerator.generate(max);
					System.out.println("Max input data is: " + textMaxInput);
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(textMaxInput);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (textMaxInput1.length() < textMaxInput.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max input is " + text);
						Assertions.assertFalse(
								text.contains(
										"" + fieldsText + " cannot be longer than " + textMaxInput1 + "characters."),
								"" + fieldsText + " cannot be longer than " + textMaxInput1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(textGenerator.generate(max));
				MobileActionGesture.scrollUsingText("Currency");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
				break;
			case "Phone":
			case "Phone Number":
			case "G-Phone":
			case "S-Phone":
			case "G-Phone Number":
			case "S-Phone NUmber":
				MobileActionGesture.scrollUsingText(fieldsText);
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					String phoneNum = RandomStringUtils.randomNumeric(min_test);
					String phoneNum1 = RandomStringUtils.randomNumeric(min);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(phoneNum);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (phoneNum1.length() < phoneNum.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for min input is " + text);
						Assertions.assertFalse(
								text.contains("" + fieldsText + " cannot be shorter than " + phoneNum1 + "characters."),
								"" + fieldsText + " cannot be shorter than " + phoneNum1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					String phoneNum = RandomStringUtils.randomNumeric(max_text);
					String phoneNum1 = RandomStringUtils.randomNumeric(max);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(phoneNum);
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (phoneNum1.length() > phoneNum.length()) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max input is " + text);
						Assertions.assertFalse(
								text.contains("" + fieldsText + " cannot be longer than " + phoneNum1 + "characters."),
								"" + fieldsText + " cannot be longer than " + phoneNum1 + "characters.");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(RandomStringUtils.randomNumeric(max));
				MobileActionGesture.scrollUsingText("Currency");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
				break;
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				MobileActionGesture.scrollUsingText(fieldsText);
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					System.out.println("min input data: " + min_test);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(min_test));
					MobileActionGesture.scrollUsingText("Number");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (min < min_test) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for min value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be less than " + min + "."),
								"" + fieldsText + " cannot be less than " + min + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					MobileActionGesture.scrollUsingText(fieldsText);
					System.out.println("Max input data is: " + max_text);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(max_text));
					MobileActionGesture.scrollTospecifiedElement("Number");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (max > max_text) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for max value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be greater than " + max + "."),
								"" + fieldsText + " cannot be greater than " + max + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(String.valueOf(max));
				MobileActionGesture.scrollUsingText("Number");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
				break;
			case "Number":
			case "G-Number":
			case "S-Number":
				MobileActionGesture.scrollUsingText(fieldsText);
				// inserting min input value
				for (n = 0; n < 3; n++) {
					min_test = min_test - 1;
					System.out.println("min input data: " + min_test);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(min_test));
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (min < min_test) {
						String text = CommonUtils.OCR();
						System.out.println("Expected toast message for min value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be less than " + min + "."),
								"" + fieldsText + " cannot be less than " + min + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					min_test = min_test + 2;
				}
				// inserting max input value
				for (p = 0; p < 3; p++) {
					max_text = max_text - 1;
					MobileActionGesture.scrollUsingText(fieldsText);
					System.out.println("Max input data is: " + max_text);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver().hideKeyboard();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(max_text));
					MobileActionGesture.scrollTospecifiedElement("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					CommonUtils.getdriver().hideKeyboard();
					if (max > max_text) {
						String text = CommonUtils.OCR();
						System.out.println("Expected Toast message for max value is :" + text);
						Assertions.assertFalse(text.contains("" + fieldsText + " cannot be greater than " + max + "."),
								"" + fieldsText + " cannot be greater than " + max + ".");
					}
					MobileActionGesture.scrollUsingText(fieldsText);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
					max_text = max_text + 2;
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver().hideKeyboard();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(String.valueOf(max));
				MobileActionGesture.scrollUsingText("Currency");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
			default:
				break;
			}
		}
	}

	// verify form with pagination and section tab exist or not
	public static void clickSectionInPages() throws MalformedURLException, InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		int pageCount = pagination.size();
		System.out.println("Total pages are: " + pageCount);

		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println("pagination exists");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				pagination.get(i).click();
				// verify section tab exist or not
				Forms.verifySectionToClickAdd();
				// scroll to top
				MobileActionGesture.flingToBegining_Android();
			}
		} else {
			// verify section tab exist or not
			Forms.verifySectionToClickAdd();
			// scroll to top
			MobileActionGesture.flingToBegining_Android();
		}
	}

	static boolean hasNextPage = false;

	// Validating Hide disable mandatory conditions in forms
	public static void fieldDependencyValueOtherFields(String basecondition, String valueOf, String inputData)
			throws MalformedURLException, InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		int pageCount = pagination.size();
		System.out.println("Total pages are: " + pageCount);
		int i = 0;

		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println("******** Pagination exists ********");

			// click on pagination link
			for (i = 0; i < pagination.size(); i++) {
				System.out.println("Current page is: " + pagination.get(i));
				if (!hasNextPage) {
					pagination.get(i).click();
					// field dependency value based on other fields in pages
					validatingFieldDependencyOfOtherFields(basecondition, valueOf, inputData, i);
				}
			}
		} else {
			System.out.println("******** Pagination not exists ********");
			// field dependency value based on other fields with-out pagination
			getFormFieldswhenPageNotExist(basecondition, valueOf, inputData, i);
		}
//		Forms.formSaveButton();
	}

	// get fields when form did not had pages
	public static void getFormFieldswhenPageNotExist(String basecondition, String valueOf, String inputData, int i)
			throws MalformedURLException, InterruptedException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
						+ valueOf + "')]"));
		List<MobileElement> formFields2 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();
		String formFieldsLabel = valueOf;
		String formFieldsLabelInput = inputData;

		// swipe and get the last element text from the list
		String lastTxtElement = null;
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText();
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();
		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
						+ valueOf + "')]")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);
		// scroll and add elements to list until the lastelement
		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
							+ valueOf + "')]")));
			formFields2.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				if (formFields1.size() > 0 || formFields2.get(j).getText().equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}

		boolean isDate = false, isText = false, isPickList = false, isCurrency = false, isNumber = false,
				isDropdown = false, isCustomer = false;
		// iterate and fill the form
		for (int k = 0; k < countOfFields; k++) {
			String OriginalText = formFields1.get(k).getText();
			String fieldsText = formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing regular expression: " + OriginalText + "\nAfter removing regexp: " + fieldsText);

			if (fieldsText.equals(formFieldsLabel)) {

				switch (fieldsText) {
				case "Text":
				case "G-Text":
				case "S-Text":
					if (!isText) {
						MobileActionGesture.scrollUsingText(fieldsText);
						textFieldDependencyInput(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isText = true;
					}
					break;
				case "Number":
				case "G-Number":
				case "S-Number":
				case "Currency":
				case "G-Currency":
				case "S-Currency":
					if (!isNumber || !isCurrency) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// currency or number method
						numberInput(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isNumber = true;
						isCurrency = true;
					}
					break;
				case "Pick List":
				case "G-Pick List":
				case "S-Pick List":
					if (!isPickList) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// pick list method
						pickPickList(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isPickList = true;
					}
					break;
				case "Customer":
				case "G-Customer":
				case "S-Customer":
					if (!isCustomer) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// customer method
						customerSelect(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isCustomer = true;
					}
					break;
				case "Dropdown":
				case "G-Dropdown":
				case "S-Dropdown":
					if (!isDropdown) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Dropdown method
						dropdownSelection(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isDropdown = true;
					}
					break;
				case "Date":
				case "G-Date":
				case "S-Date":
					if (!isDate) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Date method
						datePickerInForm(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isDate = true;
					}
				default:
					break;
				} // switch case close
			} // if stmt close
			else {
				System.out.println("specified element not visible");
			}
		} // for loop
	} // method close

	// enter input in form when pagination exists
	public static void validatingFieldDependencyOfOtherFields(String basecondition, String valueOf, String inputData,
			int i) throws MalformedURLException, InterruptedException {
		int i1 = i + 1;
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
						+ valueOf + "')]"));
		List<MobileElement> formFields2 = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		// remove fields from list
		formFields1.clear();
		String formFieldsLabel = valueOf;
		String formFieldsLabelInput = inputData;
		String lastElementText = null;

		// scroll and get last element from the list
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastElementText = formFields1.get(formFields1.size() - 1).getText();
		System.out.println("Get the element text :" + lastElementText);
		// remove fields from list
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add elements to list of formfields displaying in first screen
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
				+ valueOf + "')]")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping count: " + countOfFields);

		// if element is not exist scroll to specified element and add to list
		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
					+ valueOf + "')]")));
			formFields2.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int j = 0; j < formFields2.size(); j++) {
				// if specified element found break the for loop
				if (formFields1.size() > 0 || formFields2.get(j).getText().equals(lastElementText)) {
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
		for (int k = 0; k < countOfFields; k++) {
			String OriginalText = formFields1.get(k).getText();
			String fieldsText = formFields1.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing regular expression: " + OriginalText + "\nAfter removing regexp: " + fieldsText);
			if (fieldsText.equals(formFieldsLabel)) {
				hasNextPage = true;
				switch (fieldsText) {
				case "Text":
				case "G-Text":
				case "S-Text":
					if (!isText) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// text method
						textFieldDependencyInput(basecondition, OriginalText, fieldsText, formFieldsLabelInput, i);
						isText = true;
					}
					break;
				case "Number":
				case "G-Number":
				case "S-Number":
				case "Currency":
				case "G-Currency":
				case "S-Currency":
					if (!isNumber || !isCurrency) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// currency or number method
						numberInput(basecondition, OriginalText, fieldsText, formFieldsLabelInput, i);
						isNumber = true;
						isCurrency = true;
					}
					break;
				case "Pick List":
				case "G-Pick List":
				case "S-Pick List":
					if (!isPickList) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// pick list method
						pickPickList(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isPickList = true;
					}
					break;
				case "Customer":
				case "G-Customer":
				case "S-Customer":
					if (!isCustomer) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// customer method
						customerSelect(basecondition, OriginalText, fieldsText, formFieldsLabelInput, i);
						isCustomer = true;
					}
					break;
				case "Dropdown":
				case "G-Dropdown":
				case "S-Dropdown":
					if (!isDropdown) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Dropdown method
						dropdownSelection(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isDropdown = true;
					}
					break;
				case "Date":
				case "G-Date":
				case "S-Date":
					if (!isDate) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Date method
						datePickerInForm(basecondition, OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isDate = true;
					}
				default:
					break;
				} // switch case close
			} // if stmt close
			else {
				System.out.println("specified element not visible");
			}
		} // for loop
	} // method close

	// text input in form
	public static void textFieldDependencyInput(String basecondition, String OriginalText, String formFieldsLabel,
			String formFieldsLabelInput, int i) throws InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String textInputData = null;
		String[] criteriaCondition = baseCondition;
		String[] selectCondition = { "Equals", "Contain", "Does not contain", "Starts with", "Ends with" };
		String[] myInput = { formFieldsLabelInput, formFieldsLabelInput + "extraWords",
				"extraWords" + formFieldsLabelInput, "extraWords" };
		for (int k = 0; k < 4; k++) {
			textInputData = myInput[k];
			if (pagination.size() > 0) {
				CommonUtils.waitForElementVisibility("//*[contains(@text,'PAGE " + i + "')]");
				pagination.get(i).click();
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(textInputData);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + formFieldsLabel + "')]");
				if (basecondition.equals("Hide when") || basecondition.equals("Disable when")) {
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]")).click();
				} else if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			} else {
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(textInputData);
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + formFieldsLabel + "')]");
				if (basecondition.equals("Hide when") || basecondition.equals("Disable when")) {
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]")).click();
				} else if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			}
			if (pagination.size() > 0) {
				for (int j = 0; j < pagination.size(); j++) {
					Thread.sleep(100);
					pagination.get(j).click();
				}
			}
		}
	}

	// number input in form
	public static void numberInput(String basecondition, String OriginalText, String formFieldsLabel,
			String formFieldsLabelInput, int i) throws InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		int currencyInput = 0;
		currencyInput = Integer.parseInt(formFieldsLabelInput);
		String[] selectCondition = { "Equal to", "Less Than or Equal to", "Greater Than or Equal to", "Not equal to",
				"Greater Than", "Less Than" };
		for (int j = 0; j < 3; j++) {
			currencyInput = currencyInput - 1;
			if (pagination.size() > 0) {
				pagination.get(i).click();
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(String.valueOf(currencyInput));
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + formFieldsLabel + "')]");
				if (basecondition.equals("Hide when") || basecondition.equals("Disable when")) {
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]")).click();
				} else if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
				currencyInput = currencyInput + 2;
			} else {
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
						.sendKeys(String.valueOf(currencyInput));
				CommonUtils.waitForElementVisibility("//*[starts-with(@text,'" + formFieldsLabel + "')]");
				if (basecondition.equals("Hide when") || basecondition.equals("Disable when")) {
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]")).click();
				} else if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
				currencyInput = currencyInput + 2;
			}
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					Thread.sleep(100);
					pagination.get(k).click();
				}
			}
		}
	}

	// customer selection form
	public static void customerSelect(String basecondition, String OriginalText, String formFieldsLabel,
			String formFieldsLabelInput, int i) throws MalformedURLException, InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String customer = null;
		customer = formFieldsLabelInput;
		String[] selectCondition = { "In", "Not In" };
		String[] cusArray = customer.split(",");
		for (int j = 0; j < 2; j++) {
			if (pagination.size() > 0) {
				pagination.get(i).click();
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/android.widget.Button"))
						.click();
				CustomerPageActions.customerSearch(cusArray[j]);
				try {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + cusArray[j] + "']"))
							.isDisplayed()) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + cusArray[j] + "']")).click();
						Thread.sleep(500);
						System.out.println("Customer found!!");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			} else {
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/android.widget.Button"))
						.click();
				CustomerPageActions.customerSearch(cusArray[j]);
				try {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + cusArray[j] + "']"))
							.isDisplayed()) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + cusArray[j] + "']")).click();
						Thread.sleep(300);
						System.out.println("Customer found !!");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			}
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					pagination.get(k).click();
					// code of elements not visible in pages
				}
			}
		}
	}

	// pick-list selection in form
	public static void pickPickList(String basecondition, String OriginalText, String formFieldsLabel,
			String formFieldsLabelInput, int i) throws InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String[] selectCondition = { "In", "Not In" };
		String pickList = null;
		pickList = formFieldsLabelInput;
		String[] pickListArray = pickList.split(",");
		for (int j = 0; j < 2; j++) {
			if (pagination.size() > 0) {
				pagination.get(i).click();
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				try {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
							.isDisplayed()) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
								.click();
						Thread.sleep(300);
						System.out.println("Picklist found!!");
						if (basecondition.equals("Mandatory when")) {
							verify_mandatory_error();
						} else if (basecondition.equals("Hide when") || basecondition.equals("Disable when")) {
							CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
									.click();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				try {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
							.isDisplayed()) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
								.click();
						Thread.sleep(300);
						System.out.println("Picklist found!!");
						if (basecondition.equals("Mandatory when")) {
							verify_mandatory_error();
						} else if (basecondition.equals("Hide when") || basecondition.equals("Disable when")) {
							CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
									.click();
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					Thread.sleep(500);
					pagination.get(k).click();
					// code of elements are not visible with pages
				}
			}
		}
	}

	// select dropdown value in form
	public static void dropdownSelection(String basecondition, String OriginalText, String formFieldsLabel,
			String formFieldsLabelInput, int i) throws MalformedURLException, InterruptedException {
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String[] selectCondition = { "In", "Not In" };
		String dropDown = null;
		dropDown = formFieldsLabelInput;
		String[] dropDownArray = dropDown.split(",");
		for (int j = 0; j < 2; j++) {
			if (pagination.size() > 0) {
				Thread.sleep(100);
				pagination.get(i).click();
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				if (CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/android.widget.Spinner"))
						.isDisplayed()) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
							+ "')]/parent::*/parent::*/android.widget.Spinner")).click();
					CommonUtils.getdriver()
							.findElement(MobileBy
									.xpath("//android.widget.CheckedTextView[@text='" + dropDownArray[j] + "']"))
							.click();
					Thread.sleep(200);
				} else {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + dropDownArray[j] + "']"))
							.click();
				}
				if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			} else {
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				if (CommonUtils.getdriver().findElements(MobileBy.xpath(
						"//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/android.widget.Spinner"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
							+ "')]/parent::*/parent::*/android.widget.Spinner")).click();
					CommonUtils.getdriver()
							.findElement(MobileBy
									.xpath("//android.widget.CheckedTextView[@text='" + dropDownArray[j] + "']"))
							.click();
					Thread.sleep(200);
				} else {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + dropDownArray[j] + "']"))
							.click();
				}
				if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			}
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					Thread.sleep(100);
					pagination.get(k).click();
					// code of elements are not visible with pages
				}
			}
		}
	}

	// select datepicker in form
	public static void datePickerInForm(String basecondition, String OriginalText, String formFieldsLabel,
			String formFieldsLabelInput, int i) throws InterruptedException {
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String[] selectCondition = { "After", "Before", "In between", "On", "Not on" };
		String dateString = null;
		dateString = formFieldsLabelInput;
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

			if (pagination.size() > 0) {
				pagination.get(i).click();
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/android.widget.Button"))
						.click();
				CommonUtils.alertContentXpath();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).click();
				} else {
					do {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Next month']")).click();
					} while (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).size() > 0);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).click();
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
				Thread.sleep(200);
				if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			} else {
				CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
								+ OriginalText + "\").instance(0))"));
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/android.widget.Button"))
						.click();
				CommonUtils.alertContentXpath();
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']"))
						.size() > 0) {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).click();
				} else {
					do {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Next month']")).click();
					} while (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).size() > 0);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).click();
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
				Thread.sleep(200);
				if (basecondition.equals("Mandatory when")) {
					verify_mandatory_error();
				}
			}
			// adding the date
			c.add(Calendar.DAY_OF_MONTH, 2);

			System.out.println("---- After increasing the date ---- :" + DateFor.format(c.getTime()));
			// click on pages for validation
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					pagination.get(k).click();
				}
			}
		}
	}

	// click on form save or save & submit for approval
	public static void verify_mandatory_error() throws InterruptedException {
		AndroidLocators.resourceId("in.spoors.effortplus:id/saveForm").click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver()
				.findElements(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"'in.spoors.effortplus:id/formSaveButton\")"))
				.size() > 0) {
			AndroidLocators.resourceId("'in.spoors.effortplus:id/formSaveButton").click();
		} else if (CommonUtils.getdriver()
				.findElements(MobileBy.AndroidUIAutomator(
						"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")"))
				.size() > 0) {
			AndroidLocators.resourceId("in.spoors.effortplus:id/formSaveWorkflowButton").click();
		}
		Thread.sleep(300);
	}

	// validating formfields are hidden in form with pagination
	public static void formFields_should_hidden(String formFieldsLabel) throws MalformedURLException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		if (pagination.size() > 0) {
			for (int j = 0; j < pagination.size(); j++) {
				pagination.get(j).click();
				// code of elements are not visible with pages
				validate_formFields_are_hidden_inPages(formFieldsLabel, j);
			}
		} else {
			// code of elements are not visible without pages
			formFields_are_not_visible_without_Pages(formFieldsLabel);
		}
	}

	// validating formfields are hidden in form with pagination
	public static void validate_formFields_are_hidden_inPages(String formFieldsLabel, int j)
			throws MalformedURLException {
		int k = j + 1;
		// get all formfields elements xpath
		List<MobileElement> formFieldsLists = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFieldsLists.size();
		formFieldsLists.clear();
		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFieldsLists.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFieldsLists.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int i = 0; i < countOfFields; i++) {
				if (formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		for (int i = 0; i < formFieldsLists.size(); i++) {
			String fieldsLabelText = formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Elements text " + fieldsLabelText);
			if (fieldsLabelText.equals(formFieldsLabel)) {
				continue;
			}
			// formFields should not visible in pages

		}
	}

	// validating formfields are hidden which didn't has pagination
	public static void formFields_are_not_visible_without_Pages(String formFieldsLabel) throws MalformedURLException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
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
		for (int i = 0; i < countOfFields; i++) {
			String formFieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("field element text " + formFieldsText);
			if (formFieldsText.equals(formFieldsLabel)) {
				continue;
			}
//			// here code for elements should not visible without pages
//			List<MobileElement> elementNotVisible = CommonUtils.getdriver().findElements(
//					MobileBy.xpath("//*[starts-with(@text,'" + formFieldsText + "')]"));

		}
	}

	// verifying formFields are visible
	public static void checking_formFields_should_visible(String formFieldsLabel) throws MalformedURLException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		if (pagination.size() > 0) {
			for (int j = 0; j < pagination.size(); j++) {
				pagination.get(j).click();
				// code for formfields of elements should visible in form with pages
				validate_formFields_are_visible_inPages(formFieldsLabel, j);
			}
		} else {
			// code for formfields of elements should visible in form without pages
			formFields_are_visible_without_pages(formFieldsLabel);
		}
	}

	// valiadting formfields are visible which has pagination
	public static void validate_formFields_are_visible_inPages(String formFieldsLabel, int j)
			throws MalformedURLException {
		int k = j + 1;
		// get all formfields elements xpath
		List<MobileElement> formFieldsLists = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFieldsLists.size();

		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFieldsLists.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFieldsLists.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int i = 0; i < countOfFields; i++) {
				if (formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		for (int i = 0; i < formFieldsLists.size(); i++) {
			String originalFields = formFieldsLists.get(i).getText();
			String fieldsLabelText = formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Original text: " + originalFields + "\nElements text: " + fieldsLabelText);
			// here code for elements should visible with pages
			MobileActionGesture.scrollUsingText(lastTxtElement);
			CommonUtils.getdriver()
					.findElement(
							MobileBy.xpath("//android.widget.TextView[starts-with(@text,'" + fieldsLabelText + "')]"))
					.isDisplayed();
		}
	}

	// validating formfields are visible which didn't has pagination
	public static void formFields_are_visible_without_pages(String formFieldsLabel) throws MalformedURLException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
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
		for (int i = 0; i < countOfFields; i++) {
			String formFieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("field element text " + formFieldsText);

			// here code for elements should be visible without pages
			MobileActionGesture.scrollUsingText(lastTxtElement);
			CommonUtils.getdriver()
					.findElement(
							MobileBy.xpath("//android.widget.TextView[starts-with(@text,'" + formFieldsText + "')]"))
					.isDisplayed();
		}
	}

	// validate form fields disable
	public static void formFields_Disable(String formFieldsLabel) throws MalformedURLException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		if (pagination.size() > 0) {
			for (int j = 0; j < pagination.size(); j++) {
				pagination.get(j).click();
				// validating formfields should disable in form with pages
				verifying_formFields_Disable_withPages(formFieldsLabel, j);
			}
		} else {
			// validating formfields should disable in form without pages
			formFields_are_disable_withOut_pages(formFieldsLabel);
		}
	}

	// checking formfields are disable which has pagination
	public static void verifying_formFields_Disable_withPages(String formFieldsLabel, int j)
			throws MalformedURLException {
		int k = j + 1;
		// get all formfields elements xpath
		List<MobileElement> formFieldsLists = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFieldsLists.size();
		formFieldsLists.clear();
		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFieldsLists.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFieldsLists.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int i = 0; i < countOfFields; i++) {
				if (formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		for (int i = 0; i < formFieldsLists.size(); i++) {
			String fieldsLabelText = formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Elements text " + fieldsLabelText);
			if (CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/child::*[2]"))
					.isEnabled()) {
				continue;
			}
			// formFields should be disable in pages
//			CommonUtils.getdriver()
//					.findElement(MobileBy
//							.xpath("//*[starts-with(@text,'" + fieldsLabelText + "')]/parent::*/parent::*/child::*[2]"))
//					.isEnabled();

		}

	}

	// validating formfields are disable which didn't has pagination
	public static void formFields_are_disable_withOut_pages(String formFieldsLabel) throws MalformedURLException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
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
		for (int i = 0; i < countOfFields; i++) {
			String originalFields = formFields1.get(i).getText();
			String formFieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("original Fields text: " + originalFields + "\nfield element text: " + formFieldsText);
			if (CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]/parent::*/parent::*/child::*[2]"))
					.isEnabled()) {
				continue;
			}
			// formFields should be disable without pages
//			CommonUtils.getdriver()
//					.findElement(MobileBy
//							.xpath("//*[starts-with(@text,'" + formFieldsText + "')]/parent::*/parent::*/child::*[2]"))
//					.isEnabled();

		}
	}

	// validate form fields enable
	public static void formFields_Enable(String formFieldsLabel) throws MalformedURLException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		if (pagination.size() > 0) {
			for (int j = 0; j < pagination.size(); j++) {
				pagination.get(j).click();
				// validating formfields should enable in form with pages
				verifying_formFields_Enable_withPages(formFieldsLabel, j);
			}
		} else {
			// validating formfields should enable in form without pages
			checking_formFields_should_enable_without_Pagination(formFieldsLabel);
		}
	}

	// validating formFields are enabled which has pagination
	public static void verifying_formFields_Enable_withPages(String formFieldsLabel, int j)
			throws MalformedURLException {
		int k = j + 1;
		// get all formfields elements xpath
		List<MobileElement> formFieldsLists = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFieldsLists.size();
		formFieldsLists.clear();
		String lastTxtElement = null;
		// swipe and get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFieldsLists.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFieldsLists.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int i = 0; i < countOfFields; i++) {
				if (formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		for (int i = 0; i < formFieldsLists.size(); i++) {
			String fieldsLabelText = formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Elements text " + fieldsLabelText);
			// formFields should be enable in pages
			MobileActionGesture.scrollUsingText(lastTxtElement);
			CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[starts-with(@text,'" + fieldsLabelText + "')]/parent::*/parent::*/child::*[2]"))
					.isEnabled();
		}
	}

	// formfields should be enable which didn't has pagination
	public static void checking_formFields_should_enable_without_Pagination(String formFieldsLabel)
			throws MalformedURLException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// get the last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
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
		for (int i = 0; i < countOfFields; i++) {
			String formFieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("field element text " + formFieldsText);
			// formFields should be disable without pages
			CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[starts-with(@text,'" + formFieldsText + "')]/parent::*/parent::*/child::*[2]"))
					.isEnabled();
		}
	}

	// Testing Regular Expression
	public static void regularExpressionTesting(String regExp, String formFieldLabel) throws MalformedURLException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		if (pagination.size() > 0) {
			for (int j = 0; j < pagination.size(); j++) {
				pagination.get(j).click();
				// validating regular expression in form with pages
				insertRegularExpInputInPages(regExp, formFieldLabel, j);
			}
		} else {
			// validating regular expression in form without pages
			regExpInputWithoutPages(regExp, formFieldLabel);
		}
	}

	// Creates a random string from regular expression
	public static String match_regExp(String matchPattern) {
		String regex = null;
		regex = matchPattern;
		Xeger generator = new Xeger(regex);
		String result = generator.generate();
		System.out.println("Match Regular Expression is: " + result);
		return result;
	}

	// Creates a special character from regular expression
	public static String unMatch_regExp(String unmatchPattern) {
		String extPattern = null;
		extPattern = unmatchPattern;
		String result = null;
		if (unmatchPattern.matches("^")) {
			extPattern = unmatchPattern.replace("^", ".");
			Xeger generator = new Xeger(extPattern);
			System.out.println("Match: " + generator.generate());
		} else if (!unmatchPattern.matches("^")) {
			extPattern = unmatchPattern.replace("[", "[^");
			Xeger generator = new Xeger(extPattern);
			result = generator.generate();
			System.out.println("Not Match: " + result);
		}
		return result;
	}

	// checking regular expression in forms which had pagination
	public static void insertRegularExpInputInPages(String regExp, String formFieldLabel, int j)
			throws MalformedURLException {

		int k = j + 1;
		// get all formfields elements of text
		List<MobileElement> formFieldsLists = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ formFieldLabel + "')]"));
		int countOfFields = formFieldsLists.size();
		formFieldsLists.clear();
		String lastTxtElement = null;

		// scroll and get last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFieldsLists.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
				+ formFieldLabel + "')]")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
					+ formFieldLabel + "')]")));
			countOfFields = formFieldsLists.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int i = 0; i < countOfFields; i++) {
				if (formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}

		MobileActionGesture.flingToBegining_Android();
		boolean isText = false;
		// iterate and fill the form
		for (int l = 0; l < countOfFields; l++) {
			String OriginalText = formFieldsLists.get(l).getText();
			String fieldsText = formFieldsLists.get(l).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing regular expression: " + OriginalText + "\nAfter removing regexp: " + fieldsText);

			String matchRegExp = match_regExp(regExp);
			String unMatchRegExp = unMatch_regExp(regExp);

			switch (fieldsText) {
			case "Text":
			case "G-Text":
			case "S-Text":
				if (!isText) {
					MobileActionGesture.scrollUsingText(fieldsText);
					// input method for regularexp
					List<MobileElement> pagination = CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
					if (pagination.size() > 0) {
						pagination.get(j).click();
						MobileActionGesture.scrollUsingText(OriginalText);

						/* inputting the matching regular expression */
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.click();
						CommonUtils.keyboardHide();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.sendKeys(matchRegExp);
						MobileActionGesture.scrollUsingText("Number");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Number')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.click();
						CommonUtils.keyboardHide();
						MobileActionGesture.scrollUsingText(OriginalText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.click();
						CommonUtils.keyboardHide();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.clear();

						/* inputting the unmatching regular expression(special charcter) */
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.sendKeys(unMatchRegExp);
						CommonUtils.keyboardHide();
						MobileActionGesture.scrollUsingText("Number");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Number')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.click();
						CommonUtils.keyboardHide();
						MobileActionGesture.scrollUsingText(OriginalText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.clear();
						isText = true;
					}
				}
				break;
			}
		}
	}

	// inputting the regular expression for text data type in form which didn't has
	// pagination
	public static void regExpInputWithoutPages(String regExp, String formFieldLabel) throws MalformedURLException {

		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ formFieldLabel + "')]"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;

		// get last element text
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ formFieldLabel + "')]")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
							+ formFieldLabel + "')]")));
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
		// inputting the regular expression for text data type
		for (int i = 0; i < countOfFields; i++) {
			String OriginalText = formFields1.get(i).getText();
			String fieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing regular expression: " + OriginalText + "\nAfter removing regexp: " + fieldsText);

			String matchRegExp = match_regExp(regExp);
			String unMatchRegExp = unMatch_regExp(regExp);
			switch (fieldsText) {
			case "Text":
			case "G-Text":
			case "S-Text":
				MobileActionGesture.scrollUsingText(fieldsText);
				/* inputting the matching regular expression */
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.click();
				CommonUtils.keyboardHide();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.sendKeys(matchRegExp);
				MobileActionGesture.scrollUsingText("Number");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Number')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.click();
				CommonUtils.keyboardHide();
				MobileActionGesture.scrollUsingText(OriginalText);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.click();
				CommonUtils.keyboardHide();
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.clear();

				/* inputting the unmatching regular expression(special charcter) */
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.sendKeys(unMatchRegExp);
				CommonUtils.keyboardHide();
				MobileActionGesture.scrollUsingText("Number");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Number')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.click();
				CommonUtils.keyboardHide();
				MobileActionGesture.scrollUsingText(OriginalText);
				CommonUtils.getdriver()
						.findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.clear();
			default:
				break;
			}
		}
	}

	// testing Highlighting Background of a Field Based on field value
	public static void testing_highlighting_background_field_basedOn_fieldValue(int colorInput, String fieldLabel)
			throws MalformedURLException, InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));
		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println(" **** pagination exists **** ");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				pagination.get(i).click();
				Validations_for_Highlighting_BackgroundField_Basedon_fieldValue_in_pagination(colorInput, fieldLabel,
						i);
			}
		} else {
			System.out.println(" ==== pagination not exists ====");
			Forms.verifySectionToClickAdd();
			highlighting_BackgroundField_Basedon_fieldValue_WithOut_Pages(colorInput, fieldLabel);
		}
//		Forms.formSaveButton();
	}

	// color validations with pagination
	public static int Validations_for_Highlighting_BackgroundField_Basedon_fieldValue_in_pagination(int fieldInput,
			String fieldLabel, int i) throws MalformedURLException, InterruptedException {
		int j = i + 1;
		// get all formfields elements of text
		List<MobileElement> formFieldsLists = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ fieldLabel + "')]"));
		int countOfFields = formFieldsLists.size();
		formFieldsLists.clear();
		String lastTxtElement = null;

		// scroll and get last element
		MobileActionGesture.flingVerticalToBottom_Android();
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
				"");
		System.out.println("Get the last element text: " + lastTxtElement);
		formFieldsLists.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
				+ fieldLabel + "')]")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
					+ fieldLabel + "')]")));
			countOfFields = formFieldsLists.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int k = 0; k < countOfFields; k++) {
				if (formFieldsLists.get(k).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));

		// iterate and fill the form
		for (int l = 0; l < countOfFields; l++) {
			String OriginalText = formFieldsLists.get(l).getText();
			String fieldsText = formFieldsLists.get(l).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(
					"Before removing regular expression: " + OriginalText + "\nAfter removing regexp: " + fieldsText);
			int colorInputValue = fieldInput;

			switch (OriginalText) {
			case "Number":
			case "G-Number":
			case "S-Number":
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				MobileActionGesture.scrollUsingText(OriginalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + OriginalText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(OriginalText);
					for (int n = 0; n < 3; n++) {
						if (pagination.size() > 0) {
							Thread.sleep(100);
							pagination.get(i).click();
							MobileActionGesture.scrollUsingText(OriginalText);
							colorInputValue = colorInputValue - 1;
							System.out.println("min input data: " + colorInputValue);
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
									.click();
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
									.clear();
							CommonUtils.getdriver().hideKeyboard();
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
									.sendKeys(String.valueOf(colorInputValue));
							MobileActionGesture.scrollUsingText("Currency");
							CommonUtils.getdriver().findElement(MobileBy.xpath(
									"//*[contains(@text,'Currency')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
									.click();
							CommonUtils.getdriver().hideKeyboard();
						}
						// checkif pagination link exists
						if (pagination.size() > 0) {
							for (int k = 0; k < pagination.size(); k++) {
								Thread.sleep(100);
								pagination.get(k).click();
							}
						}
						colorInputValue = colorInputValue + 2;
					}
				}
				break;
			}
		}
		return fieldInput;
	}

	// color field validation based on input for currency/number data type with out
	// using pagination
	public static int highlighting_BackgroundField_Basedon_fieldValue_WithOut_Pages(int fieldInput, String fieldLabel)
			throws MalformedURLException {
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ fieldLabel + "')]"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// get last element text
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println(" **** Get the last element text **** : " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ fieldLabel + "')]")));
		countOfFields = formFields1.size();
		System.out.println(" ***** Before swiping fields count is ***** : " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
							+ fieldLabel + "')]")));
			countOfFields = formFields1.size();
			System.out.println(" ..... After swiping fields count ..... : " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				System.out.println("***** Print form fields elements text ***** : "
						+ formFields1.get(countOfFields - (j + 1)).getText());
				System.out.println("====== Form fields text ====== : " + formFields1.get(j).getText());
				if (formFields1.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					System.out
							.println("----- Form fields text inside elements ----- : " + formFields1.get(j).getText());
					flag = true;
				}
			}
			if (flag == true)
				break;
		}

		MobileActionGesture.flingToBegining_Android();
		// inputting the color field for currency/number data type
		for (int i = 0; i < countOfFields; i++) {
			String OriginalText = formFields1.get(i).getText();
			String fieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(" ==== Before removing regular expression ==== : " + OriginalText
					+ "\n ---- After removing regexp ---- : " + fieldsText);

			int colorFieldInput = fieldInput;

			switch (OriginalText) {
			case "Number":
			case "G-Number":
			case "S-Number":
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				MobileActionGesture.scrollUsingText(OriginalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + OriginalText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(OriginalText);
					for (int n = 0; n < 3; n++) {
						MobileActionGesture.scrollUsingText(OriginalText);
						colorFieldInput = colorFieldInput - 1;
						System.out.println("***** Enter min input value ***** : " + colorFieldInput);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.click();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.clear();
						CommonUtils.getdriver().hideKeyboard();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + OriginalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.sendKeys(String.valueOf(colorFieldInput));
						MobileActionGesture.scrollUsingText("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
								.click();
						CommonUtils.getdriver().hideKeyboard();
						colorFieldInput = colorFieldInput + 2;
					}
				}
				break; //// *[@text='Number']/parent::*/parent::*/android.widget.LinearLayout/android.widget.ImageButton
						//// -'+'
			}
		}
		return fieldInput;
	}

	// validating Error and Warn conditon based on values in other fields
	public static void Validate_Based_on_Values_in_Other_Fields(String errorCondition, String inputValue)
			throws MalformedURLException, InterruptedException, ParseException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@content-desc,'Page ')]"));
		// checkif pagination link exists
		if (pagination.size() > 0) {
			System.out.println(" **** Pagination exists going to click on pages **** ");

			// click on pagination link
			for (int i = 0; i < pagination.size(); i++) {
				pagination.get(i).click();
				System.out.println("----- Clicked on page ----- : " + pagination.get(i));
				error_And_WarnMessage_in_pagination(errorCondition, inputValue, i);
			}
		} else {
			System.out.println(" ==== Pagination not exists!! ====");
			Forms.verifySectionToClickAdd();
			validating_errorAndWarn_Message_without_pagination(errorCondition, inputValue);
		}
//	Forms.formSaveButton();
	}

	// handling warning alert in form
	public static void handlingWarningAlert() {
		CommonUtils.alertContentXpath();
		if (AndroidLocators.resourceId("android:id/alertTitle").isDisplayed()) {
			MobileElement message = AndroidLocators.resourceId("android:id/message");
//					CommonUtils.getdriver()
//					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/message\")"));
			System.out.println(" **** Warning message is **** :" + message.getText());
			AndroidLocators.resourceId("android:id/button2").click();
//			CommonUtils.getdriver()
//					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/button2\")"))
//					.click();
		}
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/saveForm']");
	}

	// validating error and warn message in form pagination
	public static String error_And_WarnMessage_in_pagination(String errorCondition, String inputValue, int i)
			throws MalformedURLException, InterruptedException, ParseException {
		int j = i + 1;
		List<MobileElement> formFields1 = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// swipe to bottom and get the last element from the list
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText();
		System.out.println("---- Get the last element text ---- : " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list in screen 1
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("***** Before swiping fields count is ***** : " + countOfFields);

		// scroll and add elements to list until the lastelement found
		while (!formFields1.isEmpty() && formFields1 != null) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println(".... After swiping fields count .... : " + countOfFields);
			for (int k = 0; k < countOfFields; k++) {
				System.out.println("***** Print form fields elements text ***** : "
						+ formFields1.get(countOfFields - (k + 1)).getText());
				System.out.println("===== Form fields text ===== : " + formFields1.get(k).getText());
				// if last element found then break the for loop
				if (formFields1.get(k).getText().equals(lastTxtElement)) {
					System.out
							.println("----- Form fields text inside elements ----- : " + formFields1.get(k).getText());
					flag = true;
				}
			}
			// break the while loop after last last element found
			if (flag == true)
				break;
		}

		// swipe to top of rhe screen
		MobileActionGesture.flingToBegining_Android();

		// iterate and fill the form
		for (int m = 0; m < countOfFields; m++) {
			String originalText = formFields1.get(m).getText();
			String fieldsText = formFields1.get(m).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(" **** Before removing special character **** : " + originalText
					+ "\n---- After removing regexp ---- : " + fieldsText);
			String[] inputArray = inputValue.split(",");
			String currencyInput = inputArray[0];
			String dateInput = inputArray[1];
			int currencyErrorInput = 0;
			currencyErrorInput = Integer.parseInt(currencyInput);

			if (fieldsText.contains("Text") || fieldsText.contains("G-Text") || fieldsText.contains("S-Text")) {
				MobileActionGesture.scrollUsingText(fieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(fieldsText);
					Forms.text(fieldsText);
				}
			}
//			else if (fieldsText.contains("Currency") || fieldsText.contains("G-Currency")
//					|| fieldsText.contains("S-Currency") || fieldsText.contains("Number")
//					|| fieldsText.contains("G-Number") || fieldsText.contains("S-Number")) 
//			{
//				MobileActionGesture.scrollUsingText(fieldsText);
//				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]"))
//						.size() > 0) {
//					MobileActionGesture.scrollUsingText(fieldsText);
//					// method for validating currency using error and warning condition
//					currencyValidation_error_And_warn_message(errorCondition, currencyErrorInput, fieldsText);
//				}
//			} 
			else if (fieldsText.contains("Date") || fieldsText.contains("G-Date") || fieldsText.contains("S-Date")) {
				MobileActionGesture.scrollUsingText(fieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(fieldsText);
					// method for Date validation using error and warning condition
					dateValidationforError_and_warn_message(errorCondition, dateInput, fieldsText);
				}
			}
		} // closing for loop
		return errorCondition;
	}

	// validating error and warn essage without pagination
	public static String validating_errorAndWarn_Message_without_pagination(String errorCondition, String inputValue)
			throws MalformedURLException, InterruptedException, ParseException {
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();

		String lastTxtElement = null;
		// get last element text
		MobileActionGesture.flingVerticalToBottom_Android();
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println(" **** Get the last element text **** : " + lastTxtElement);
		formFields1.clear();
		MobileActionGesture.flingToBegining_Android();

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println(" ***** Before swiping fields count is ***** : " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			countOfFields = formFields1.size();
			System.out.println(" ..... After swiping fields count ..... : " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				System.out.println("***** Print form fields elements text ***** : "
						+ formFields1.get(countOfFields - (j + 1)).getText());
				System.out.println("====== Form fields text ====== : " + formFields1.get(j).getText());
				if (formFields1.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "").equals(lastTxtElement)) {
					System.out
							.println("----- Form fields text inside elements ----- : " + formFields1.get(j).getText());
					flag = true;
				}
			}
			if (flag == true)
				break;
		}

		// iterate and fill the form
		for (int m = 0; m < countOfFields; m++) {
			String originalText = formFields1.get(m).getText();
			String fieldsText = formFields1.get(m).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println(" **** Before removing special character **** : " + originalText
					+ "\n----- After removing regexp ---- : " + fieldsText);

			String[] inputArray = inputValue.split(",");
			String currencyInput = inputArray[0];
			String dateInput = null;
			dateInput = inputArray[1];
			int currencyErrorInput = 0;
			currencyErrorInput = Integer.parseInt(currencyInput);

			switch (fieldsText) {

			case "Currency":
			case "G-Currency":
			case "S-Currency":
			case "Number":
			case "G-Number":
			case "S-Number":
				MobileActionGesture.scrollUsingText(fieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(fieldsText);
					// method for validating currency using error and warning condition
					currencyValidation_error_And_warn_message(errorCondition, currencyErrorInput, fieldsText);
				}
				break;
			case "Date":
			case "G-Date":
			case "S-Date":
				MobileActionGesture.scrollUsingText(fieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + fieldsText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(fieldsText);
					// method for Date validation using error and warning condition
					dateValidationforError_and_warn_message(errorCondition, dateInput, fieldsText);
				}
				break;
			} // closing switch satement
		} // closing for loop

		return errorCondition;

	}

	// validating currency for error and warning condition
	public static int currencyValidation_error_And_warn_message(String errorCondition, int currencyErrorInput,
			String fieldsText) throws InterruptedException {
		for (int p = 0; p < 3; p++) {
			// decreasing currency input for boundary value testing
			currencyErrorInput = currencyErrorInput - 1;
			System.out.println(" **** Enter the currency input **** :" + currencyErrorInput);
			MobileActionGesture.scrollUsingText(fieldsText);
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText")).click();
			CommonUtils.getdriver().hideKeyboard();
			CommonUtils.getdriver()
					.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
					.sendKeys(String.valueOf(currencyErrorInput));
			// validating error and warning condition
			if (errorCondition.equals("Show Error when")) {
				MobileActionGesture.scrollUsingText("Text");
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[contains(@text,'Text')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText"))
						.click();
				CommonUtils.getdriver().hideKeyboard();
			} else if (errorCondition.equals("Show Warning when")) {
				verify_mandatory_error();
				handlingWarningAlert();
			}
			// retrieving error message using OCR
			String text = CommonUtils.OCR();
			System.out.println("---- Expected toast message is ---- : " + text);

			MobileActionGesture.scrollUsingText(fieldsText);
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
					+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.EditText")).clear();
			// increasing the currency value
			currencyErrorInput = currencyErrorInput + 2;
			System.out.println(".... After increaing the currency value .... : " + currencyErrorInput);
		}
		return currencyErrorInput;
	}

	// date validation for error and warning message
	public static String dateValidationforError_and_warn_message(String errorCondition, String dateInput,
			String fieldsText) throws InterruptedException, ParseException, MalformedURLException {
		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

//		SimpleDateFormat New_Date_Format = new SimpleDateFormat("EEE, MMM dd yyyy"); //EEE-Day of month, MMM-month, dd-date, yyyy-year
		
		java.util.Date date = new java.util.Date();
		// formating current date using date formatter
		String toDaydate = DateFor.format(date);
		// printing current date
		System.out.println("**** Todays date is **** : " + toDaydate);

		// parse the today date
		Date presentDate = DateFor.parse(toDaydate);
		// print parsing today date
		System.out.println("....After parsing today date ... : " + presentDate);

		// converting given date in string format to date format
		date = DateFor.parse(dateInput);
		// formating converted date into our date formatter
		String formatGivenDate = DateFor.format(date);
		// printing given date
		System.out.println("---- Given date is ---- : " + formatGivenDate);

		for (int p = 0; p < 3; p++) {
			// Number of Days to add
			date = DateUtils.addDays(date, -1);
			// conversion of date
			String inputDate = DateFor.format(date);
			
			// Printing customized date
			System.out.println(" **** My given date is **** : " + inputDate);

//			String[] splitDate = inputDate.split(" ");
//			
//			String splitYear = splitDate[2];
//			//get year
//			System.out.println("... Selected Year ... : "+splitYear);
//			
//			// parse the given date inside loop
//			Date givenDate = DateFor.parse(inputDate);
//			// printing parsed given date
//			System.out.println(".... After parsing the given date .... : " + givenDate);

			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
							+ fieldsText + "\").instance(0))"));
			if (CommonUtils.getdriver()
					.findElements(MobileBy.xpath(
							"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath(
						"//*[starts-with(@text,'" + fieldsText + "')]/parent::*/parent::*/android.widget.Button"))
						.click();
				CommonUtils.alertContentXpath();
				
				Forms.getCalendarDates(inputDate);
			}
			if (errorCondition.equals("Show Error when")) {
				Forms.formSaveButton();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);
			} else if (errorCondition.equals("Show Warning when")) {
//				verify_mandatory_error();
				Forms.formSaveButton();
				handlingWarningAlert();
			}
			// adding date
			date = DateUtils.addDays(date, 2);
			//format the increased date
			String newIncreasedDate = DateFor.format(date);
			//printing the increased date
			System.out.println("**** After increasing the date **** : " + newIncreasedDate);

		} // closing for loop
		return toDaydate;
	} // closing method

	//calendar click next('>' symbol)
	public static void goRight() {
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Next month']")).click();
	}
	
	//calendar click next('<' symbol)
	public static void goLeft() throws InterruptedException {
		CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Previous month']")).click();
		CommonUtils.wait(1);
	}
	
	//select specified year
	public static void clickElementByText(String txt) throws InterruptedException, MalformedURLException {
		CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@resource-id='android:id/text1'][@text='" + txt + "']")).click();
		CommonUtils.wait(1);
	}
	
	//click on year
	public static void clickCalendarYear() throws InterruptedException, MalformedURLException {
		MobileElement yearClick = AndroidLocators.resourceId("android:id/date_picker_header_year");
		MobileActionGesture.tapByElement(yearClick);
	}
	
	//select date from dates list
	public static void selectDate(String inputDate) throws InterruptedException {
		boolean flag = false;
		List<MobileElement> allDates = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//android.view.View/android.view.View"));
		for (int l = 0; l < allDates.size(); l++) {
			String myDateList = allDates.get(l).getAttribute("content-desc");
			if (myDateList.contains(inputDate)) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + inputDate + "']")).click();
				flag = true;
			}
			if(flag == true)
				break;
		}
		
	}
	
	//get year
	public static void checkYear() {
		final String year = AndroidLocators.resourceId("android:id/date_picker_header_year").getText();
		System.out.println("**** Display year is **** : "+year);
	}
	
	//calendar display date
	public static String dateSelection() {
		String displayDate = AndroidLocators.resourceId("android:id/date_picker_header_date").getText();
		System.out.println(".... Date displaying in calendar .... : " + displayDate);
		return displayDate;
	}
	 
	String monthCheck(String MMMM) throws ParseException {
		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

		SimpleDateFormat New_Date_Format = new SimpleDateFormat("EEE, MMM dd");

		String displayDate = AndroidLocators.resourceId("android:id/date_picker_header_date").getText();		
		System.out.println("**** Displaying calendar date **** : " + displayDate);
		Date parse_display_date = New_Date_Format.parse(displayDate);
		System.out.println(".... After parsing the display date .... : " +parse_display_date);
		String formatDate = DateFor.format(parse_display_date);
		System.out.println("---- Formating the parese date ---- : "+formatDate);
		
		String[] splitFormated_Date = formatDate.split(" ");
		String monthSplit = splitFormated_Date[1];
		System.out.println(".... Retrieving month from parsed date .... " +monthSplit);
		return MMMM;
		
	}
}