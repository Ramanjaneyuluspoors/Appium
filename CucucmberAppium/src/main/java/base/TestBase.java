package base;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import utils.CommonUtils;
import utils.ServerService;

public class TestBase {

	
	public static ExtentHtmlReporter Reporter;
	public static ExtentReports reports;
	public static ExtentTest Scenario;
	public static ExtentTest Feature;
	
	public static AndroidDriver<MobileElement> driver;
	
		public void setUp() throws IOException, InterruptedException {
			if (driver == null) {
				ServerService.services("" , "");
				ServerService.stopService();
				ServerService.startService();
				CommonUtils.loadConfigProp("selendroidtestapp.properties");
				CommonUtils.setCapabilitiesForAndoird();
				driver = CommonUtils.getAppiumDriver();
			}

		}
		public void tearDown() throws IOException {

			driver.quit();
			ServerService.stopService();

		}
		
}
