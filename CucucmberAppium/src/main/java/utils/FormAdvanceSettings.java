package utils;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.gen5.api.Assertions;
import org.openqa.selenium.By;

import Actions.CustomerPageActions;
import Actions.MobileActionGesture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

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
	public static void fieldDependencyValueOtherFields(String valueOf, String inputData)
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
				validatingFieldDependencyOfOtherFields(valueOf, inputData, i);
			}
		} else {
			System.out.println("pagination not exists");
			// field dependency value based on other fields
			getFormFieldswhenPageNotExist(valueOf, inputData, i);
		}
//		Forms.formSaveButton();
	}

	// get fields when form has not pages
	public static void getFormFieldswhenPageNotExist(String valueOf, String inputData, int i)
			throws MalformedURLException, InterruptedException {
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver().findElements(
				MobileBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
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
						textFieldDependencyInput(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
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
						numberInput(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
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
						pickPickList(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isPickList = true;
					}
					break;
				case "Customer":
				case "G-Customer":
				case "S-Customer":
					if (!isCustomer) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// customer method
						customerSelect(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isCustomer = true;
					}
					break;
				case "Dropdown":
				case "G-Dropdown":
				case "S-Dropdown":
					if (!isDropdown) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Dropdown method
						dropdownSelection(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isDropdown = true;
					}
					break;
				case "Date":
				case "G-Date":
				case "S-Date":
					if (!isDate) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Date method
						datePickerInForm(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
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
	public static void validatingFieldDependencyOfOtherFields(String valueOf, String inputData, int i)
			throws MalformedURLException, InterruptedException {
		int i1 = i + 1;
		// get all formfields elements xpath
		List<MobileElement> formFields1 = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//android.widget.TextView[contains(@text,'PAGE " + i1
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView"));
		int countOfFields = formFields1.size();
		formFields1.clear();
		String formFieldsLabel = valueOf;
		String formFieldsLabelInput = inputData;
		boolean searchElement = true;
		String lastElementText = null;
		if (searchElement) {
			MobileActionGesture.flingVerticalToBottom_Android();
			formFields1.addAll(CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//android.widget.TextView[contains(@text,'PAGE " + i1
							+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
			lastElementText = formFields1.get(formFields1.size() - 1).getText();
			System.out.println("Get the element text :" + lastElementText);
			formFields1.clear();
			MobileActionGesture.flingToBegining_Android();
		}

		// add elements to list of formfields displaying in first screen
		formFields1.addAll(CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//android.widget.TextView[contains(@text,'PAGE " + i1
						+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
		countOfFields = formFields1.size();
		System.out.println("Before swiping count: " + countOfFields);

		while (!formFields1.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			formFields1.addAll(CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//android.widget.TextView[contains(@text,'PAGE " + i1
							+ "')]/following::android.widget.LinearLayout//android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView")));
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
						textFieldDependencyInput(OriginalText, fieldsText, formFieldsLabelInput, i);
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
						numberInput(OriginalText, fieldsText, formFieldsLabelInput, i);
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
						pickPickList(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isPickList = true;
					}
					break;
				case "Customer":
				case "G-Customer":
				case "S-Customer":
					if (!isCustomer) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// customer method
						customerSelect(OriginalText, fieldsText, formFieldsLabelInput, i);
						isCustomer = true;
					}
					break;
				case "Dropdown":
				case "G-Dropdown":
				case "S-Dropdown":
					if (!isDropdown) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Dropdown method
						dropdownSelection(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
						isDropdown = true;
					}
					break;
				case "Date":
				case "G-Date":
				case "S-Date":
					if (!isDate) {
						MobileActionGesture.scrollUsingText(fieldsText);
						// Date method
						datePickerInForm(OriginalText, formFieldsLabel, formFieldsLabelInput, i);
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
	public static void textFieldDependencyInput(String OriginalText, String formFieldsLabel,
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
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
						.click();
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
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
						.click();
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
	public static void numberInput(String OriginalText, String formFieldsLabel, String formFieldsLabelInput, int i)
			throws InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		int currencyInput = 0;
		currencyInput = Integer.parseInt(formFieldsLabelInput);
		String[] selectCondition = { "Equal to", "Less Than or Equal to", "Greater Than or Equal to", "Not equal to", "Greater Than", "Less Than" };
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
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
						.click();
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
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
						.click();
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
	public static void customerSelect(String OriginalText, String formFieldsLabel, String formFieldsLabelInput, int i)
			throws MalformedURLException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String customer = null;
		customer = formFieldsLabelInput;
		String[] selectCondition = {"In","Not In"};
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
						Thread.sleep(500);
						System.out.println("Customer found !!");
					}
				} catch (Exception e) {
					System.out.println(e);
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
	public static void pickPickList(String OriginalText, String formFieldsLabel, String formFieldsLabelInput, int i)
			throws InterruptedException {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String[] selectCondition = {"In","Not In"};
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
	public static void dropdownSelection(String OriginalText, String formFieldsLabel, String formFieldsLabelInput,
			int i) throws MalformedURLException, InterruptedException {
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String[] selectCondition = {"In","Not In"};
		String dropDown = null;
		dropDown = formFieldsLabelInput;
		String[] dropDownArray = dropDown.split(",");
		for (int j = 0; j < 2; j++) {
			if (pagination.size() > 0) {
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
				} else {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + dropDownArray[j] + "']"))
							.click();
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
						.click();
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
				} else {
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + dropDownArray[j] + "']"))
							.click();
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + formFieldsLabel + "')]"))
						.click();
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

	public static void datePickerInForm(String OriginalText, String formFieldsLabel, String formFieldsLabelInput,
			int i) throws InterruptedException {
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		String[] selectCondition = {"After","Before","In between","On","Not on"};
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
		System.out.println("Given date is: "+ DateFor.format(c.getTime()));
		
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
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='Next month']")).click();
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).click();
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
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
					} while (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@content-desc='" + newDate + "']"))
							.size() > 0);
					CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@content-desc='" + newDate + "']")).click();
				}
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
				Thread.sleep(300);
			}
			c.add(Calendar.DAY_OF_MONTH, 2);
			if (pagination.size() > 0) {
				for (int k = 0; k < pagination.size(); k++) {
					Thread.sleep(200);
					pagination.get(k).click();
				}
			}
		}
	}
	
	// validating formfields are hidden in form with pagination
	public static void formFields_should_hidden(String formFieldsLabel) {
		// get pages
		List<MobileElement> pagination = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//*[contains(@text,'PAGE ')]"));
		if (pagination.size() > 0) {
			for (int j = 0; j < pagination.size(); j++) {
				pagination.get(j).click();
				// code of elements are not visible with pages
			}
		} else {
			// code of elements are not visible without pages
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

	// validating formfields are hidden in form without pagination
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

	// valiadting formfields are visible in form with pagination
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
			String fieldsLabelText = formFieldsLists.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Elements text " + fieldsLabelText);
			if (fieldsLabelText.equals(formFieldsLabel)) {
				continue;
			}
			// here code for elements should visible with pages
			CommonUtils.getdriver()
					.findElement(
							MobileBy.xpath("//android.widget.TextView[starts-with(@text,'" + fieldsLabelText + "')]"))
					.isDisplayed();
		}
	}

	// validating formfields are visible in form without pagination
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
			if (formFieldsText.equals(formFieldsLabel)) {
				continue;
			}
			// here code for elements should be visible without pages
			CommonUtils.getdriver()
					.findElement(
							MobileBy.xpath("//android.widget.TextView[starts-with(@text,'" + formFieldsText + "')]"))
					.isDisplayed();
		}
	}
	
	//validate form fields disable
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
	
	//checking formfields are disable in form with pagination
	public static void verifying_formFields_Disable_withPages(String formFieldsLabel, int j) throws MalformedURLException {
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
			// formFields should be disable in pages
//			CommonUtils.getdriver()
//					.findElement(MobileBy
//							.xpath("//*[starts-with(@text,'" + fieldsLabelText + "')]/parent::*/parent::*/child::*[2]"))
//					.isEnabled();
			
		}
		
	}
	
	// validating formfields are disable in form without pagination
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
			String formFieldsText = formFields1.get(i).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("field element text " + formFieldsText);
			if (formFieldsText.equals(formFieldsLabel)) {
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

		}
	}
	
	// validating formFields are enabled in form withpages
	public static void verifying_formFields_Enable_withPages(String formFieldsLabel, int j) throws MalformedURLException {
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
			// formFields should be enable in pages
			CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[starts-with(@text,'" + fieldsLabelText + "')]/parent::*/parent::*/child::*[2]"))
					.isEnabled();
			
		}
	}
	
	public static void checking_formFields_should_enable_without_Pagination(String formFieldsLabel) throws MalformedURLException {
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
			// formFields should be disable without pages
			CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[starts-with(@text,'" + formFieldsText + "')]/parent::*/parent::*/child::*[2]"))
					.isEnabled();
		}
	}
	
	
	
	

}
