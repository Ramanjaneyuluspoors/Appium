package utils;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.gen5.api.Assertions;

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
		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1 = CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
							+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1 = CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + j
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
				//inserting min input value
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
				//inserting max input value
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
				//inserting min input value
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
				//inserting max input value
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
				//inserting min input value
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
				//inserting max input value
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
				//inserting min input value
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
				//inserting max input value
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
		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			minMaxFields.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = minMaxFields.get(minMaxFields.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]",
					"");
			System.out.println("Get the last element text: " + lastTxtElement);
			minMaxFields.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		minMaxFields.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		minMaxFieldsCount = minMaxFields.size();
		System.out.println("Before swiping fields count is: " + minMaxFieldsCount);

		// scroll and add elements to list until the lastelement
		while (!minMaxFields.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
			System.out.println("pagination exists");
			// click on pagination link
			for (i = 0; i < pagination.size(); i++) {
				pagination.get(i).click();
				// field dependency value based on other fields in pages
				validatingFieldDependencyOfOtherFields(basecondition, valueOf, inputData, i);
			}
		} else {
			System.out.println("pagination not exists");
			// field dependency value based on other fields
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
		int countOfFields = formFields1.size();
		formFields1.clear();
		String formFieldsLabel = valueOf;
		String formFieldsLabelInput = inputData;
		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText();
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
						+ valueOf + "')]")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);
		// scroll and add elements to list until the lastelement
		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
							+ valueOf + "')]")));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				if (formFields1.get(j).getText().equals(lastTxtElement)) {
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
		int countOfFields = formFields1.size();
		formFields1.clear();
		String formFieldsLabel = valueOf;
		String formFieldsLabelInput = inputData;
		boolean searchElement = true;
		String lastElementText = null;
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastElementText = formFields1.get(formFields1.size() - 1).getText();
			System.out.println("Get the element text :" + lastElementText);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add elements to list of formfields displaying in first screen
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
				+ valueOf + "')]")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping count: " + countOfFields);

		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + i1
							+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[starts-with(@text,'"
							+ valueOf + "')]")));
			countOfFields = formFields1.size();
			System.out.println("After swiping fields count: " + countOfFields);
			for (int j = 0; j < countOfFields; j++) {
				if (formFields1.get(j).getText().equals(lastElementText)) {
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
						//text method
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
		for (int k = 0; k < 4; k++) {
			if (k == 0) {
				textInputData = formFieldsLabelInput;
			}
			if (k == 1) {
				textInputData = formFieldsLabelInput + "extraWords";
			}
			if (k == 2) {
				textInputData = "extraWords" + formFieldsLabelInput;
			}
			if (k == 3) {
				textInputData = "extraWords";
			}
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
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
								.click();
						System.out.println("Picklist found!!");
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
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel
						+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button")).click();
				CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
				try {
					if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
							.isDisplayed()) {
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + pickListArray[j] + "']"))
								.click();
						Thread.sleep(300);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
								.click();
						System.out.println("PickList found!!");
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
		System.out.println("Given date is: " + DateFor.format(c.getTime()));

		for (int j = 0; j < 3; j++) {
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, -1);
			// conversion of date
			String newDate = DateFor.format(c.getTime());
			// Date Printing
			System.out.println("My Date is : " + newDate);

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
			c.add(Calendar.DAY_OF_MONTH, 2);
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					pagination.get(k).click();
				}
			}
		}
	}

	// check mandatory error is displaying or not if base condition is mandatory
	public static void verify_mandatory_error() throws InterruptedException {
		CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/saveForm\")"))
				.click();
		CommonUtils.alertContentXpath();
		if (CommonUtils.getdriver()
				.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
				.isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy
					.AndroidUIAutomator("new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveButton\")"))
					.click();
		} else if (CommonUtils.getdriver()
				.findElement(MobileBy.AndroidUIAutomator(
						"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")"))
				.isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiSelector().resourceId(\"in.spoors.effortplus:id/formSaveWorkflowButton\")")).click();
			Thread.sleep(300);
		}
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
		boolean searchElement = true;
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText()
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFieldsLists.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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

		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
			// here code for elements should not visible without pages
//			MobileElement elementNotVisible = CommonUtils.getdriver().findElement(
//					MobileBy.xpath("//android.widget.TextView[starts-with(@text,'" + formFieldsText + "')]"));
//			elementNotVisible = null;
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
		boolean searchElement = true;
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText()
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFieldsLists.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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

		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
		boolean searchElement = true;
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText()
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFieldsLists.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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

		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
		boolean searchElement = true;
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText()
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFieldsLists.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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

		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
					.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy
				.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
		boolean searchElement = true;
		// scroll and get last element
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
					+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFieldsLists.get(formFieldsLists.size() - 1).getText()
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFieldsLists.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFieldsLists.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'PAGE " + k
				+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
				+ formFieldLabel + "')]")));
		countOfFields = formFieldsLists.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (formFieldsLists.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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
			default:
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

		// get last element text
		boolean searchElement = true;
		String lastTxtElement = null;

		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastTxtElement = formFields1.get(formFields1.size() - 1).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Get the last element text: " + lastTxtElement);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add the elements to list
		formFields1.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[contains(@text,'"
						+ formFieldLabel + "')]")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping fields count is: " + countOfFields);

		// scroll and add elements to list until the lastelement
		while (formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
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

}
