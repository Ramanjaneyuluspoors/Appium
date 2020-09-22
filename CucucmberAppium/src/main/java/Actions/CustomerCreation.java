package Actions;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;
import utils.Forms;
import utils.MediaPermission;

public class CustomerCreation {

	public static void createCustomerWithAllFields() throws MalformedURLException, InterruptedException {
		// get labelview elements
		List<MobileElement> labelElements = CommonUtils.getdriver()
				.findElements(MobileBy.xpath("//android.widget.TextView[@id ='label_for_view']"));
		// get input text elements
		List<MobileElement> inputTextElements = CommonUtils.getdriver()
				.findElements(MobileBy.className("android.widget.EditText"));
		// merging both lists
		List<MobileElement> newList = labelElements;
		newList.addAll(inputTextElements);
//		labelElements.addAll(inputTextElements);
		// get customer fields count
		int customerFieldsCount = newList.size();
		System.out.println("fields count: " + customerFieldsCount);

		newList.clear();

		// find the last element to stop continuos scrolling
		String customerLastListElement = null;
		// scroll to bottom and add customerlist fields
		MobileActionGesture.flingVerticalToBottom_Android();
		// add customer fields to get last element
		labelElements
				.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath("//android.widget.TextView[@text and @id ='label_for_view']")));
		inputTextElements.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		// merge both list to get last element
		newList = labelElements;
		newList.addAll(inputTextElements);
//			labelElements.addAll(inputTextElements);
		// get customer last textelement
		customerLastListElement = newList.get(newList.size() - 1).getText();
		System.out.println("Customer last element: " + customerLastListElement);
		// remove the elements from the list
		labelElements.clear();
		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// adding the customer fields present in the first screen
		labelElements.addAll(CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/parent::*/parent::*/android.widget.LinearLayout/android.widget.TextView")));
		inputTextElements.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
		// merging both list
		newList = labelElements;
		newList.addAll(inputTextElements);
//		labelElements.addAll(inputTextElements);
		// get customer fields count present in the first screen
		customerFieldsCount = newList.size();
		System.out.println("Before swiping customer fields count: " + customerFieldsCount);

		// scroll and add elelemnts to list until last element found
		while (!newList.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			inputTextElements.addAll(CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText")));
			labelElements.addAll(
					CommonUtils.getdriver().findElements(MobileBy.xpath("//android.widget.TextView[@text and @id ='label_for_view']")));
			// merging both list
			newList = labelElements;
			newList.addAll(inputTextElements);
//			labelElements.addAll(inputTextElements);
			// get the count of all added elements
			customerFieldsCount = newList.size();
			System.out.println("After swiping fields count: " + customerFieldsCount);
			for (int i = 0; i < customerFieldsCount; i++) {
				if (newList.get(i).getText().equals(customerLastListElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		MobileActionGesture.flingToBegining_Android();
		boolean isDate = false, isText = false, isURL = false, isEmail = false, isCountry = false, isEmployee = false,
				isDateTime = false, isTime = false, isPickList = false, isMultiPickList = false, isPhone = false,
				isMultiSelectDropdown = false, isLocation = false, isCurrency = false, isNumber = false,
				isDropdown = false, isYesNo = false, isCustomer = false, isForm = false, isCustomEntity = false,
				isSignature = false;

		for (int j = 0; j < customerFieldsCount; j++) {
			String originalText = newList.get(j).getText();
			String cusFieldsText = newList.get(j).getText().replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Before removing special character: " + originalText
					+ "\nAfter removing special character: " + cusFieldsText);

			switch (cusFieldsText) {
			case "Type":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					MobileElement type = CommonUtils.getdriver().findElement(
							MobileBy.xpath("//*[@text='" + originalText + "']/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(type);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
					}
				}
				break;
			case "Name":
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + cusFieldsText + "')]"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(cusFieldsText);
					String randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("nameEditText")).sendKeys(randomstringCusName);
				}
				break;
			case "Territory Type":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					MobileElement territoryType = CommonUtils.getdriver().findElement(
							MobileBy.xpath("//*[@text='" + originalText + "']/parent::*/android.widget.Spinner"));
					MobileActionGesture.singleLongPress(territoryType);
					if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
							.size() > 0) {
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
					}
				}
				break;
			case "Phone number":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver()
						.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]")).size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusPhoneNum = RandomStringUtils.randomNumeric(10);
					CommonUtils.getdriver().findElement(MobileBy.id("phoneEditText")).sendKeys(cusPhoneNum);
				}
				break;
			case "Street":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusAddressStreet = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("streetEditText")).sendKeys(cusAddressStreet);
				}
				break;
			case "Area":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusArea = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("areaEditText")).sendKeys(cusArea);
				}
				break;
			case "Landmark":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusLandmark = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("landmarkEditText")).sendKeys(cusLandmark);
				}
				break;
			case "City":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusCity = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("cityEditText")).sendKeys(cusCity);
				}
				break;
			case "State":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusState = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("stateEditText")).sendKeys(cusState);
				}
				break;
			case "District":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusDistrict = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("districtEditText")).sendKeys(cusDistrict);
				}
				break;
			case "PIN code":
				MobileActionGesture.scrollUsingText(originalText);
				if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText + "']"))
						.size() > 0) {
					MobileActionGesture.scrollUsingText(originalText);
					String cusPincode = RandomStringUtils.randomNumeric(6);
					CommonUtils.getdriver().findElement(MobileBy.id("pinCodeEditText")).sendKeys(cusPincode);
				}
				break;
			case "Phone(Optional)":
				if (isPhone) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String phoneNum = RandomStringUtils.randomNumeric(10);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(phoneNum);
					}
					isPhone = true;
				}
				break;
			case "Employee":
				if (!isEmployee) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button")).click();
						if (CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.id("employeeNameTextView")).get(0).click();
							Thread.sleep(500);
						}
					}
					isEmployee = true;
				}
				break;
			case "Multi Pick List":
				if (!isMultiPickList) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement multipicklist = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
						Thread.sleep(500);
					}
					isMultiPickList = true;
				}
				break;
			case "Pick List":
				if (!isPickList) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK LIST')]"))
									.click();
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0)
									.isDisplayed()) {
								CommonUtils.getdriver().findElements(MobileBy.id("titleTextView")).get(0).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1)
									.isDisplayed()) {
								CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(1).click();
							}
							Thread.sleep(500);
						} else {
							System.out.println("Pick List is already picked");
						}
					}
					isPickList = true;
				}
				break;
			case "Form":
				if (!isForm) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"))
								.size() > 0) {
							MobileElement form = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
											+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A FORM')]"));
							MobileActionGesture.tapByElement(form);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							try {
								if (CommonUtils.getdriver().findElementsById("form_id_text_view").size() > 0) {
									CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
											.click();
								} else {
									CommonUtils.getdriver().findElementById("load_more_button").click();
									CommonUtils.waitForElementVisibility(
											"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
									if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
											.size() > 0) {
										CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view")).get(0)
												.click();
									} else {
										CommonUtils.getdriver().findElement(MobileBy.id("fab")).click();
										CommonUtils.waitForElementVisibility("//*[@content-desc='Save']");
										Forms.verifyFormPagesAndFill();
										CommonUtils.waitForElementVisibility(
												"//*[@resource-id='in.spoors.effortplus:id/form_id_text_view']");
										if (CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
												.size() > 0) {
											CommonUtils.getdriver().findElements(MobileBy.id("form_id_text_view"))
													.get(0).click();
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
					isForm = true;
				}
				break;
			case "Location":
			case "G-Location":
				if (!isLocation) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						CommonUtils.getdriver().findElement(MobileBy.id("pick_location_button")).click();
						Thread.sleep(5000);
						CommonUtils.waitForElementVisibility("//*[@text='MARK MY LOCATION']");
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='MARK MY LOCATION']")).click();
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='USE MARKED LOCATION']")).click();
						Thread.sleep(500);
					}
					isLocation = true;
				}
				break;
			case "YesNo":
			case "G-YesNo":
				if (!isYesNo) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement cusYesNo = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')/parent::*/parent::*/android.widget.Spinner"));
						MobileActionGesture.singleLongPress(cusYesNo);
						if (CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.size() > 0) {
							CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
									.get(1).click();
						}
					}
					isYesNo = true;
				}
				break;
			case "URL(Optional)":
			case "G-URL(Optional)":
				if (!isURL) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						RandomStringGenerator urlGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String url = urlGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys("www." + url + ".com");
					}
					isURL = true;
				}
				break;
			case "Email(Optional)":
			case "G-Email(Optional)":
				if (!isEmail) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						RandomStringGenerator emailGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String cusEmail = emailGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(cusEmail + "@gmail.com");
					}
					isEmail = true;
				}
				break;
			case "Text":
			case "G-Text":
				if (!isText) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
								.build();
						String cusText = textGenerator.generate(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[contains(@text,'" + originalText + "')]"))
								.sendKeys(cusText);
					}
					isText = true;
				}
				break;
			case "Country":
			case "G-Country":
				if (!isCountry) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[contains(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Spinner//*[contains(@text,'Pick a country')]"))
								.size() > 0) {
							MobileElement country = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[contains(@text,'" + originalText
											+ "')]/parent::*/parent::*/android.widget.Spinner//*[contains(@text,'Pick a country')]"));
							MobileActionGesture.singleLongPress(country);
							MobileActionGesture.scrollTospecifiedElement("Angola");
						} else {
							System.out.println("Country is already selected");
						}
					}
					isCountry = true;
				}
				break;
			case "Time":
			case "G-Time":
				if (!isTime) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A TIME')]"))
									.click();
							Forms.TimeScriptInForms(2, 1);
							Thread.sleep(500);
						} else {
							System.out.println("Time is already picked");
						}
					}
					isTime = true;
				}
				break;
			case "DateTime":
			case "G-DateTime":
				if (!isDateTime) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[contains(@text,'PICK DATE')]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms.dateScriptInForms(2);
							Thread.sleep(500);
						}
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[@text='" + originalText
								+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='" + originalText
									+ "']/parent::*/parent::*/android.widget.LinearLayout/android.widget.Button[2]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms.TimeScriptInForms(2, 5);
							Thread.sleep(500);
						} else {
							System.out.println("DateTime is already picked");
						}
					}
					isDateTime = true;
				}
				break;
			case "Date":
			case "G-Date":
				if (!isDate) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
								.size() > 0) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A DATE')]"))
									.click();
							CommonUtils.alertContentXpath();
							Forms.dateScriptInForms(2);
							Thread.sleep(500);
						} else {
							System.out.println("Date is already picked");
						}
					}
					isDate = true;
				}
			case "Number":
			case "G-Number":
				if (isNumber) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String number1 = RandomStringUtils.randomNumeric(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(number1);
					}
					isNumber = true;
				}
			case "Customer":
			case "G-Cutomer":
				if (isCustomer) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).isEnabled()) {
							CommonUtils.getdriver().findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
									+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'PICK A CUSTOMER')]")).click();
							CommonUtils.waitForElementVisibility("//*[@text='Customers']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("item_id")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("item_id")).get(0).click();
							} else {
								CustomerPageActions.customerFab();
								CustomerPageActions.createCustomer();
								CustomerPageActions.customerSearch(CustomerPageActions.randomstringCusName);
								CommonUtils.getdriver()
										.findElement(MobileBy
												.xpath("//*[@text='" + CustomerPageActions.randomstringCusName + "']"))
										.click();
							}
							Thread.sleep(500);
							System.out.println("Now customer is picked");
						} else {
							System.out.println("Customer is already selected!!");
						}
					}
					isCustomer = true;
				}
				break;
			case "Custom Entity":
			case "G-Custom Entity":
				if (isCustomEntity) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if (CommonUtils.getdriver().findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
								+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"))
								.size() > 0) {
							MobileElement customEntity = CommonUtils.getdriver()
									.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
											+ "')]/parent::*/parent::*/android.widget.Button[contains(@text,'ENTITY')]"));
							MobileActionGesture.tapByElement(customEntity);
							CommonUtils.waitForElementVisibility("//*[@content-desc='Search']");
							if (CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("entityTitle")).get(0).click();
							} else if (CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card"))
									.size() > 0) {
								CommonUtils.getdriver().findElements(MobileBy.id("custom_entity_card")).get(0).click();
							} else {
								// write entity item creation method
								Forms.createEntity();
							}
							Thread.sleep(500);
						} else {
							System.out.println("Custom entity is already picked");
						}
					}
					isCustomEntity = true;
				}
				break;
			case "Signature":
			case "G-Signature":
				if (isSignature) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement signature = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button[1]"));
						MobileActionGesture.tapByElement(signature);
						MediaPermission.mediaPermission();
						CommonUtils.waitForElementVisibility("//*[@text='Signature']");
						MobileElement signatureCapture = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[@text='CAPTURE']")); // id("saveButton")
						MobileActionGesture.singleLongPress(signatureCapture);
						CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
						Thread.sleep(500);
						// CommonUtils.getdriver().pressKey(new KeyEvent(AndroidKey.CAMERA));
					}
					isSignature = true;
				}
				break;
			case "Currency":
			case "G-Currency":
				if (isCurrency) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						String currency = RandomStringUtils.randomNumeric(5);
						CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
								.sendKeys(currency);
					}
					isCurrency = true;
				}
				break;
			case "Dropdown":
			case "G-Dropdown":
				if (!isDropdown) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						if(CommonUtils.getdriver()
								.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]")).size() > 0) {
						MobileElement dropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
										+ "')]/parent::*/parent::*/android.widget.Spinner/*[contains(@text,'Pick a value')]"));
						MobileActionGesture.singleLongPress(dropdown);
						CommonUtils.getdriver().findElements(MobileBy.className("android.widget.CheckedTextView"))
								.get(1).click();
						}else {
							System.out.println("Dropdown is already selected");
						}
					}
					isDropdown = true;
				}
				break;
			case "Multi Select Dropdown":
			case "G-Multi Select Dropdown":
				if (!isMultiSelectDropdown) {
					MobileActionGesture.scrollUsingText(originalText);
					if (CommonUtils.getdriver()
							.findElements(MobileBy.xpath("//*[starts-with(@text,'" + originalText + "')]"))
							.size() > 0) {
						MobileActionGesture.scrollUsingText(originalText);
						MobileElement multiSelectDropdown = CommonUtils.getdriver()
								.findElement(MobileBy.xpath("//*[starts-with(@text,'" + originalText
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
						CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='OK']")).click();
						Thread.sleep(500);
					}
					isMultiSelectDropdown = true;
				}
				break;
			} // switch close
		} // for loop close
	} // method close
	
	
	
	
	
	
}