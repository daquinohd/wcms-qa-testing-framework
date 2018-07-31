package gov.nci.clinicaltrials;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ScreenShot;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	// private static Logger log=
	// LogManager.getLogger(BaseClass.class.getName());
	protected static ExtentReports report;
	protected static ExtentTest logger;
	protected WebDriver driver;
	protected String pageURL; // TODO: Get rid of pageURL from BaseClass.
	protected ConfigReader config = new ConfigReader();

	@BeforeTest(groups = { "Smoke", "Analytics", "current" })
	@Parameters
	public void beforeTest() {
		// log.info("Starting a new test");
		System.out.println(this.getClass().getSimpleName());
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);
		// report = new ExtentReports(extentReportPath + ".html");
		report = new ExtentReports(extentReportPath + config.getProperty("Environment") + "-" + fileName + ".html");
		System.out.println("Report Path: " + report);
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}

	@BeforeClass(groups = { "Smoke", "Analytics", "current" })
	public void beforeClass() {
		logger = report.startTest(this.getClass().getSimpleName());
	}

	@BeforeMethod(groups = { "Smoke", "Analytics", "current" })
	public void beforeMethod() {
		try {
			// Force the page to load fresh before each test.
			// TODO: explicitly load the page in each test.
			driver.get(pageURL);
			((JavascriptExecutor) driver).executeScript("scroll(0, -100);");
		}
		catch(NullPointerException ex) {
			logger.log(LogStatus.INFO, "No driver object found in beforeMethod()");
		}		

	}

	@AfterMethod(groups = { "Smoke", "Analytics", "current" })
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);

			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
		}
		else {
			logger.log(LogStatus.PASS, "Pass => "+ result.getName());
		}
	}

	@AfterClass(groups = { "Smoke", "Analytics", "current" })
	public void afterClass() {
		try {
			driver.quit();
		}
		catch(NullPointerException ex) {
			logger.log(LogStatus.INFO, "No driver object found in afterClass()");
		}
		finally {
			report.endTest(logger);
		}
	}

	@AfterTest(groups = { "Smoke", "Analytics", "current" })
	public void afterTest() {
		report.flush();
	}

}
