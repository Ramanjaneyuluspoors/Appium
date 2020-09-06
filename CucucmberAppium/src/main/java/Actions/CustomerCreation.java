package Actions;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class CustomerCreation {
	
	public static void createCustomerWithAllFields() throws MalformedURLException {
		// get labelview elements
		List<MobileElement> labelElements = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@id='label_for_view']"));
		// get input text elements
		List<MobileElement> inputTextElements = CommonUtils.getdriver()
				.findElements(MobileBy.className("android.widget.EditText"));
		// merging both lists
		List<MobileElement> newList = new LinkedList<MobileElement>(labelElements);
		newList.addAll(inputTextElements);
		// get customer fields count
		int customerFieldsCount = newList.size();
		System.out.println("fields count: " + customerFieldsCount);
		
		newList.clear();
		

		// find the last element to stop continuos scrolling
		boolean customerSearchElement = true;
		String customerLastListElement = null;
		if (customerSearchElement) {
			// scroll to bottom and add customerlist fields
			MobileActionGesture.flingVerticalToBottom_Android();
			// add customer last elements fields
			labelElements = CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@id='label_for_view']"));
			inputTextElements = CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText"));
			// merge both list to get last element
			newList = new LinkedList<MobileElement>(labelElements);
			newList.addAll(inputTextElements);
			// get customer last textelement
			customerLastListElement = newList.get(newList.size() - 1).getText();
			System.out.println("Customer last element: " + customerLastListElement);
			// remove the elements from the list
			newList.clear();
			// scroll to top
			MobileActionGesture.flingToBegining_Android();
		}
		// adding the both customer fields present in the first screen
		labelElements = CommonUtils.getdriver().findElements(MobileBy.xpath(
				"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@id='label_for_view']"));
		inputTextElements = CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText"));
		// merging both list
		newList = new LinkedList<MobileElement>(labelElements);
		newList.addAll(inputTextElements);
		// get customer fields count present in tne first screen
		customerFieldsCount = newList.size();
		System.out.println("Before swiping customer fields count: " + customerFieldsCount);

		while (!newList.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.7, 0.2, 0.5);
			labelElements = CommonUtils.getdriver().findElements(MobileBy.xpath(
					"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@id='label_for_view']"));
			inputTextElements = CommonUtils.getdriver().findElements(MobileBy.className("android.widget.EditText"));
			// merging both list
			newList = new LinkedList<MobileElement>(labelElements);
			newList.addAll(inputTextElements);
			//get the count of all added elements
			customerFieldsCount = newList.size();
			for (int i = 0; i < customerFieldsCount; i++) {
				if (labelElements.get(i).getText().equals(customerLastListElement)) {
					flag = true;
				}
			}
			if (flag == true)
				break;
		}
		
		for(int j = 0; j < customerFieldsCount; j++) {
			String originalText = labelElements.get(labelElements.size() - 1).getText();
			String cusFieldsText = labelElements.get(labelElements.size() - 1).getText()
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("Original Text: " + originalText + "After removing special character: " + cusFieldsText);
			switch(cusFieldsText) {
			
			
			}
		}
		

	}
	
	
}
