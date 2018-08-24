package gov.nci.clinicaltrials;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ScreenShot;

public class BaseClass {

	// private static Logger log=
	// LogManager.getLogger(BaseClass.class.getName());
	protected static ExtentReports report;
	protected static ExtentTest logger;
	protected WebDriver driver;
	protected String pageURL; // TODO: Get rid of pageURL from BaseClass.
	protected ConfigReader config;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void beforeClass(String environment) {

		config = new ConfigReader(environment);

		System.out.println(this.getClass().getSimpleName());
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);

		report = new ExtentReports(extentReportPath + environment + "-" + fileName + ".html");
		System.out.println("Report Path: " + report);
		report.addSystemInfo("Environment", environment);
		logger = report.startTest(this.getClass().getSimpleName());
	}


	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {

		System.out.println(String.format("Executing %s.%s()", method.getDeclaringClass().getName(), method.getName()));

		// Force the page to load fresh before each test.
		// TODO: Move this to those test classes where it makes sense.
		driver.get(pageURL);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);

			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
		}

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
		report.endTest(logger);
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		report.flush();
	}

}
