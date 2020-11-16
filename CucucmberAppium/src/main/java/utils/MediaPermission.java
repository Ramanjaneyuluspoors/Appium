package utils;


import io.appium.java_client.MobileBy;


public class MediaPermission {

	// verifying media permission & location
	public static void signinMediaPermission() throws InterruptedException {
		try {
			if (CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_message']"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Allow']")).click();
				System.out.println("media permission is allowed");
			} else {
				System.out.println("media permission is not found");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		Thread.sleep(800);
	}

	// media permission
	public static void mediaPermission() {
		//AndroidUIAutomator("new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_allow_button\")")
		////*[@text='Allow EFFORT Plus to take pictures and record video?']
		try {
			if (CommonUtils.getdriver()
					.findElements(MobileBy.xpath("//*[@text='Allow EFFORT Plus to take pictures and record video?']"))
					.size() > 0) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Allow']")).click();
				System.out.println("media permission is allowed");
			}
		} catch (Exception e) {
			System.out.println("media permission alert is not found");
		}
	}
	

	//image capture
	public static void formImageCapture(String imageClick, String selectClickedImage) throws InterruptedException {
		signinMediaPermission();
		CommonUtils.getdriver().findElement(MobileBy.id(imageClick)).click();
		CommonUtils.getdriver().findElement(MobileBy.xpath(selectClickedImage));
		CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
	}
	//video capture
	public static void formVideoCapture(String startVideoXpath, String stopVideoXpath, String selectVideoXpath) throws InterruptedException { ////*[@content-desc='Start video']  ////*[@content-desc='Stop video']
		signinMediaPermission();
		CommonUtils.getdriver().findElement(MobileBy.xpath(startVideoXpath)).click();   ////*[@resource-id='com.google.android.GoogleCamera:id/shutter_button']
		Thread.sleep(3000); 
		CommonUtils.getdriver().findElement(MobileBy.xpath(stopVideoXpath)).click();
		CommonUtils.getdriver()
				.findElementByXPath(selectVideoXpath).click();
		CommonUtils.waitForElementVisibility("//*[@text='PLAY']");
	}
	
	
}
