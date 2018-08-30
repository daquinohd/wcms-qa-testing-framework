package gov.nci.general;

import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ExcelManager;
import gov.nci.Utilities.ScreenShot;
import gov.nci.commonobjects.AZList;

public class AZList_Test {

	private String TESTDATA_PATH;

	protected static ExtentReports report;
	protected static ExtentTest logger;
	protected WebDriver driver;
	protected ConfigReader config = new ConfigReader();

	@BeforeSuite(groups = { "AZList", "current" })
	public void suiteHeader() {
		System.out.println("\n********************* START ***********************\n");
	}

	@BeforeTest(groups = { "AZList", "current" })
	@Parameters
	public void beforeTest() {
		// log.info("Starting a new test");
		System.out.println("*** Running " + this.getClass().getSimpleName() + "***");
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("   Logger Path: " + extentReportPath);
		// report = new ExtentReports(extentReportPath + ".html");
		report = new ExtentReports(extentReportPath + config.getProperty("Environment")
													+ "-" + fileName + ".html");
		System.out.println("   Report Path: " + report + "\n");
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}

	@BeforeClass(groups = { "AZList", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());
		driver = BrowserManager.startBrowser(browser, "about:blank");
		TESTDATA_PATH = config.getProperty("AZListData");
	}

	@AfterMethod(groups = { "AZList", "current" })
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

	@AfterClass(groups = { "AZList", "current" })
	public void afterClass() {
		driver.quit();
		report.endTest(logger);
	}

	@AfterTest(groups = { "AZList", "current" })
	public void afterTest() {
		report.flush();
		System.out.println("*** End AZList Tests ***\n");
	}

	@AfterSuite(groups = { "AZList", "current" })
	public void suiteFooter() {
		System.out.println("\n***************** END **********************\n");
	}

/*  ***************************** Test Methods ****************************************** */
	// The page options container is visible on most pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadAZList", groups = { "AZList" })
	public void IsVisible(String url) {

		System.out.println("\nTesting AZList IsVisible()");
		System.out.println("   " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		AZList page;

		try {
			page = new AZList(driver);
			boolean isVisible = page.IsVisible();
			Assert.assertTrue(isVisible, "*** Error: Page Options Box Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in IsVisible() ***");
		}
	}


	// The page options container is not visible on some pages.  Testing that
	// it's true for those pages as indicated on the worksheet.
	// ------------------------------------------------------------------------------
	// @Test(dataProvider = "loadAZList", groups = { "AZList" })
	// public void NotVisible(String url) {

	// 	System.out.println("\nTesting PO NotVisible()");
	// 	System.out.println("   " + url);
	// 	logger.log(LogStatus.INFO, "Testing url: " + url);
	// 	// logger.log(LogStatus.PASS, url);

	// 	driver.get(url);

	// 	PageOptions page;

	// 	try {
	// 		page = new PageOptions(driver);
	// 		boolean isVisible = page.IsVisible();
	// 		Assert.assertFalse(isVisible, "*** Error: Found Page Options Box ***");
	// 	} catch (MalformedURLException | UnsupportedEncodingException e) {
	// 		Assert.fail("*** Error loading page in MissingOk() *** ");
	// 	}
	// }


/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadAZList")
	public Iterator<Object[]> loadAZList() {
		// String TESTDATA_SHEET_NAME = "Visible Page Options";
		String TESTDATA_SHEET_NAME = "AZ-List";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"Hostname", rowNum);
			String pageOptionsPath   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"AZ Link", rowNum);
			String pageOptionsType   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Type", rowNum);
			String url = pageOptionsHost + pageOptionsPath;

			if (!pageOptionsType.equals("none")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
}