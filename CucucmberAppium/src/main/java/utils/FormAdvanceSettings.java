package utils;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.http.util.Asserts;
import org.junit.gen5.api.Assertions;

import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class FormAdvanceSettings {

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
				Forms.verifySectionToClickAdd();
				fillMinMaxData(min, max);
			}
		} else {
			System.out.println("pagination not exists");
			Forms.verifySectionToClickAdd();
			fillMinMaxData(min, max);
		}
//		Forms.formSaveButton();
	}

	public static void fillMinMaxData(int min, int max) throws MalformedURLException, InterruptedException {
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

		boolean isText = false, isPhone = false, isCurrency = false, isNumber = false;

		// iterate and fill the form
		for (int i = 0; i < countOfFields; i++) {
			String fieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("after removing regexp:" + fieldsText);

			int j = 0, k = 0;
			int min_test = min, max_text = max;
			switch (fieldsText) {
			case "Text":
			case "G-Text":
			case "S-Text":
				if (!isText) {
					MobileActionGesture.scrollUsingText(fieldsText);
					RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
							.build();
					for (j = 0; j < 3; j++) {
						min_test = min_test - 1;
						String textMinInput = textGenerator.generate(min_test);
						String textMinInput1 = textGenerator.generate(min);
						System.out.println("min input data: " + textMinInput);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(textMinInput);
						MobileActionGesture.scrollUsingText("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
						if (textMinInput1.length() < textMinInput.length()) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max input is " + text);
							Assertions.assertFalse(
									text.contains("" + fieldsText + " cannot be shorter than " + textMinInput1
											+ "characters."),
									"" + fieldsText + " cannot be shorter than " + textMinInput1 + "characters.");
						}
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
						min_test = min_test + 2;
					}
					for (k = 0; k < 3; k++) {
						max_text = max_text - 1;
						MobileActionGesture.scrollUsingText(fieldsText);
						String textMaxInput = textGenerator.generate(max_text);
						String textMaxInput1 = textGenerator.generate(max);
						System.out.println("Max input data is: " + textMaxInput);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(textMaxInput);
						MobileActionGesture.scrollUsingText("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
						if (textMaxInput1.length() < textMaxInput.length()) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max input is " + text);
							Assertions.assertFalse(
									text.contains("" + fieldsText + " cannot be longer than " + textMaxInput1
											+ "characters."),
									"" + fieldsText + " cannot be longer than " + textMaxInput1 + "characters.");
						}
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
						max_text = max_text + 2;
					}
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(textGenerator.generate(max));
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					isText = true;
				}
				break;
			case "Phone":
			case "Phone Number":
			case "G-Phone":
			case "S-Phone":
			case "G-Phone Number":
			case "S-Phone NUmber":
				if (!isPhone) {
					MobileActionGesture.scrollUsingText(fieldsText);
					for (j = 0; j < 3; j++) {
						min_test = min_test - 1;
						String phoneNum = RandomStringUtils.randomNumeric(min_test);
						String phoneNum1 = RandomStringUtils.randomNumeric(min);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(phoneNum);
						MobileActionGesture.scrollUsingText("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
						if (phoneNum1.length() < phoneNum.length()) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for min input is " + text);
							Assertions.assertFalse(
									text.contains(
											"" + fieldsText + " cannot be shorter than " + phoneNum1 + "characters."),
									"" + fieldsText + " cannot be shorter than " + phoneNum1 + "characters.");
						}
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
						min_test = min_test + 2;
					}
					for (k = 0; k < 3; k++) {
						max_text = max_text - 1;
						String phoneNum = RandomStringUtils.randomNumeric(max_text);
						String phoneNum1 = RandomStringUtils.randomNumeric(max);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(phoneNum);
						MobileActionGesture.scrollUsingText("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
						if (phoneNum1.length() > phoneNum.length()) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max input is " + text);
							Assertions.assertFalse(
									text.contains(
											"" + fieldsText + " cannot be longer than " + phoneNum1 + "characters."),
									"" + fieldsText + " cannot be longer than " + phoneNum1 + "characters.");
						}
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
						max_text = max_text + 2;
					}
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(RandomStringUtils.randomNumeric(max));
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					isPhone = true;
				}
				break;
			case "Currency":
			case "G-Currency":
			case "S-Currency":
				if (!isCurrency) {
					MobileActionGesture.scrollUsingText(fieldsText);
					for (j = 0; j < 3; j++) {
						min_test = min_test - 1;
						System.out.println("min input data: " + min_test);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(String.valueOf(min_test));
						MobileActionGesture.scrollUsingText("Number");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
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
					for (k = 0; k < 3; k++) {
						max_text = max_text - 1;
						MobileActionGesture.scrollUsingText(fieldsText);
						System.out.println("Max input data is: " + max_text);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(String.valueOf(max_text));
						MobileActionGesture.scrollTospecifiedElement("Number");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
						if (max > max_text) {
							String text = CommonUtils.OCR();
							System.out.println("Expected toast message for max value is :" + text);
							Assertions.assertFalse(
									text.contains("" + fieldsText + " cannot be greater than " + max + "."),
									"" + fieldsText + " cannot be greater than " + max + ".");
						}
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
						max_text = max_text + 2;
					}
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(max));
					MobileActionGesture.scrollUsingText("Number");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Number')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					isCurrency = true;
				}
				break;
			case "Number":
			case "G-Number":
			case "S-Number":
				if (!isNumber) {
					MobileActionGesture.scrollUsingText(fieldsText);
					for (j = 0; j < 3; j++) {
						min_test = min_test - 1;
						System.out.println("min input data: " + min_test);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(String.valueOf(min_test));
						MobileActionGesture.scrollUsingText("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
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
					for (k = 0; k < 3; k++) {
						max_text = max_text - 1;
						MobileActionGesture.scrollUsingText(fieldsText);
						System.out.println("Max input data is: " + max_text);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
										+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.sendKeys(String.valueOf(max_text));
						MobileActionGesture.scrollTospecifiedElement("Currency");
						CommonUtils.getdriver().findElement(MobileBy.xpath(
								"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
								.click();
						if (max > max_text) {
							String text = CommonUtils.OCR();
							System.out.println("Expected Toast message for max value is :" + text);
							Assertions.assertFalse(
									text.contains("" + fieldsText + " cannot be greater than " + max + "."),
									"" + fieldsText + " cannot be greater than " + max + ".");
						}
						MobileActionGesture.scrollUsingText(fieldsText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
								+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).clear();
						max_text = max_text + 2;
					}
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
							+ "')]/parent::*/following-sibling::*/child::android.widget.EditText")).click();
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[contains(@text,'" + fieldsText
									+ "')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.sendKeys(String.valueOf(max));
					MobileActionGesture.scrollUsingText("Currency");
					CommonUtils.getdriver().findElement(MobileBy.xpath(
							"//*[contains(@text,'Currency')]/parent::*/following-sibling::*/child::android.widget.EditText"))
							.click();
					isNumber = true;
				}
			default:
				break;
			}
		}
	}
}
