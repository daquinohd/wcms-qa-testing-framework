package gov.nci.WebAnalytics;

import org.openqa.selenium.WebDriver;

public class AnalyticsBase {
	
	public static final String S_CODE_NAME = "s_code.js";
	public static final String S_ACCOUNT = "s_account";
	public static final String NCI_FUNCTIONS_NAME = "NCIAnalytics";
	public static final String TRACKING_SERVER = "nci.122.2o7.net";

	public WebDriver driver;

	public AnalyticsBase() {
		// No arg contructor
	}

}