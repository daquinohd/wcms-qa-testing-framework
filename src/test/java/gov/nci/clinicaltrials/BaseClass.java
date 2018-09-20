package gov.nci.clinicaltrials;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ScreenShot;
import gov.nci.Utilities.BrowserManager;

public class BaseClass {

	// private static Logger log=
	// LogManager.getLogger(BaseClass.class.getName());
	protected static ExtentReports report;
	protected static ExtentTest logger;
	protected WebDriver driver;
	protected String pageURL; // TODO: Get rid of pageURL from BaseClass.
	protected ConfigReader config;

	@BeforeSuite( alwaysRun = true )
	public void suiteHeader() {
		System.out.println("\n***************** START Suite **********************");
	}

	@BeforeTest( alwaysRun = true )
	@Parameters({ "environment", "browser" })
	public void beforeTest(String environment, String browser) {
		System.out.println("*** START Test");
		config = new ConfigReader(environment);

		System.out.println("\n    Environment: " + environment.toUpperCase());
		String dateTime = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		String fileName =  environment.toUpperCase() + "-" + dateTime + ".html";
		System.out.println("    Logger Path: " + extentReportPath + fileName);

		report = new ExtentReports(extentReportPath + fileName);
		System.out.println("    Report Path: " + report);
		report.addSystemInfo("Environment", environment);
	}

	// Setting up path and filename for the log file
	// Printing location of log file and environment variables
	// -------------------------------------------------------
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment", "browser" })
	public void beforeClass(String environment, String browser) {

		System.out.println("\n    ===========  ");
		System.out.println("    Class Name:  " + this.getClass().getSimpleName());
		System.out.println("    ===========  ");

		config = new ConfigReader(environment);
		driver = BrowserManager.startBrowser(browser, config, "about:blank");

		logger = report.startTest(this.getClass().getSimpleName());
		logger.assignAuthor("Volker");
	}

	// Printing out the name of the method before each is run
	// ------------------------------------------------------
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser" })
	public void beforeMethod(Method method, String browser) {

		System.out.println(String.format("\nExecuting %s.%s()",
											method.getDeclaringClass().getName(),
											method.getName()));

		// Force the page to load fresh before each test.
		// TODO: Move this to those test classes where it makes sense.
		// driver.get("about:blank");
	}

	// If a method failed log the result and take a screenshot of the page
	// -------------------------------------------------------------------
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);

			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
		} else {
			logger.log(LogStatus.PASS, "Passed => " + result.getName());
		}
		report.flush();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println("*** END Class");
		driver.quit();
		report.endTest(logger);
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		System.out.println("*** END Test");
	}

	@AfterSuite( alwaysRun = true )
	public void suiteFooter() {
		System.out.println("\n***************** END Suite **********************\n");
	}

	// Setting the tier as a class variable
	public static String tier = "QA";         // Hard-code string for environment/host used

	/*
	 *  Method to return
	 */
	public Integer NumberOfDefinitions(String dictionary, String language) {
		// These values are for the letter "B". Change the numbers if using a different letter
		Map<String, Integer> results = new HashMap<String, Integer>() {{
			put("b-prod-dict", 378);
			put("b-prod-dict-es", 232);
			put("b-prod-drug", 267);
			put("b-prod-genetics", 3);
			put("b-qa-dict", 374);
			put("b-qa-dict-es", 229);
			put("b-qa-drug", 259);
			put("b-qa-genetics", 3);
		}};

		// Need to add "-es" to the key value for the Spanish dictionary
		String lang = "";
		if (language.toLowerCase().equals("es")){
			lang = "-es";
		}

		String mapKey = "b-" + tier.toLowerCase() + "-" + dictionary + lang;
		// System.out.println(mapKey);

		return results.get(mapKey);
	}


	// Returns the URL for the host currently used for testing
	// -------------------------------------------------------
	public String AddHostname(String path) {
		String host;

		switch (tier.toUpperCase()) {
			case "PROD": host = "www.cancer.gov";
					     break;
			case "DEV":  host = "www-blue-dev.cancer.gov";
					     break;
			case "QA":   host = "www-qa.cancer.gov";
					     break;
			case "DT":   host = "www-dt-qa.cancer.gov";
					     break;
            default:     host = "www-qa.cancer.gov";
		}
		return "https://" + host + path;
	}

}
