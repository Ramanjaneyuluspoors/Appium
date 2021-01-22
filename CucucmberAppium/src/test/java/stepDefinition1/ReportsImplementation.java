package stepDefinition1;

import java.net.MalformedURLException;
import java.text.ParseException;

import Actions.MobileActionGesture;
import cucumber.api.java.en.*;
import modules_test.Reportsverification;

public class ReportsImplementation {

	@Given("Fling to reports card")
	public void Fling_to_reports_card() throws MalformedURLException, InterruptedException, ParseException {
		Thread.sleep(4000);
		MobileActionGesture.scrollTospecifiedElement("Reports");
		Thread.sleep(4000);
	}

	@When("user click on specified report name and validate data")
	public void user_click_on_specified_report_name() throws InterruptedException {
		Reportsverification.employeeActivityWiseSummaryReport();
		Reportsverification.distanceTravelledReport();
		Reportsverification.employeeWiseActivitySummaryReport();
		Reportsverification.employeeSignInSignOutReport();
		
	}
	
	@Given("scroll to day plan report with specified employee {string} and validate report data")
	public void scroll_to_day_plan_report_with_specified_employee_and_validate_report_data(String employeeName) throws InterruptedException {
		Reportsverification.dayPlanReport(employeeName);
		Reportsverification.validatingDayPlanReportData();
		Reportsverification.employeeCoverageReport(employeeName);
	}
	
	
}
