package gov.nci.clinicaltrials;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ScreenShot;

public class BaseClass {

	// protected String TESTDATA_PATH;

	// private static Logger log=
	// LogManager.getLogger(BaseClass.class.getName());
	protected static ExtentReports report;
	protected static ExtentTest logger;
	protected WebDriver driver;
	protected String pageURL; // TODO: Get rid of pageURL from BaseClass.
	protected ConfigReader config = new ConfigReader();

	@BeforeSuite( alwaysRun = true)
	public void suiteHeader() {
		System.out.println("\n********************* START ***********************\n");
	}

	// @BeforeTest(groups = { "Smoke", "current" })
	@BeforeTest( alwaysRun = true )
	@Parameters
	public void beforeTest() {
		System.out.println("\n******** Running " + this.getClass().getSimpleName()
		                                         + " Tests ********");
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("    Logger Path: " + extentReportPath);
		// report = new ExtentReports(extentReportPath + ".html");
		report = new ExtentReports(extentReportPath + config.getProperty("Environment")
		                                            + "-" + fileName + ".html");
		System.out.println("    Report Path: " + report + "\n");
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}

	// @BeforeClass(groups = { "Smoke", "current" })
	// public void beforeClass() {
	// 	logger = report.startTest(this.getClass().getSimpleName());
	// }

	// @BeforeClass(alwaysRun = true )
	// @Parameters({"browser"})
	// public void setup(String browser) {
	// 	logger = report.startTest(this.getClass().getSimpleName());
	// 	driver = BrowserManager.startBrowser(browser, "about:blank");
	// 	TESTDATA_PATH = config.getProperty("AZListData");
	// }

	@BeforeMethod(groups = { "Smoke", "current" })
	public void beforeMethod() {

		// Force the page to load fresh before each test.
		// TODO: explicitly load the page in each test.
		// VE driver.get(pageURL);
		System.out.print(" ");

		//((JavascriptExecutor) driver).executeScript("scroll(0, -100);");
	}

	@AfterMethod( alwaysRun = true )
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, result.getName());
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);

			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
		} else {
			logger.log(LogStatus.PASS, result.getName());
		}
	}

	@AfterClass( alwaysRun = true )
	public void afterClass() {
		driver.quit();
		report.endTest(logger);
	}

	@AfterTest( alwaysRun = true )
	public void afterTest() {
		report.flush();
		System.out.println("\n******** End " + this.getClass().getSimpleName()
		                                     + " Tests ********");
	}

	@AfterSuite( alwaysRun = true )
	public void suiteFooter() {
		System.out.println("\n***************** END **********************\n");
	}
}
