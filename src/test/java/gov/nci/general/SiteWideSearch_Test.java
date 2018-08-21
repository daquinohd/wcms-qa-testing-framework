package gov.nci.general;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.framework.ParsedURL;
import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ExcelManager;
import gov.nci.Utilities.ScreenShot;
import gov.nci.commonobjects.PageOptions;

public class SiteWideSearch_Test {
		private final String TESTDATA_SHEET_NAME = "Sheet1";

		protected static ExtentReports report;
		protected static ExtentTest logger;
		protected WebDriver driver;
		protected ConfigReader config = new ConfigReader();
		private String testDataFilePath;

		@BeforeTest(groups = { "Smoke", "current" })
		@Parameters
		public void beforeTest() {
			// log.info("Starting a new test");
			System.out.println(this.getClass().getSimpleName());
			String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
			String extentReportPath = config.getExtentReportPath();
			System.out.println("Logger Path:" + extentReportPath);
			// report = new ExtentReports(extentReportPath + ".html");
			report = new ExtentReports(extentReportPath + config.getProperty("Environment")
														+ "-" + fileName + ".html");
			System.out.println("Report Path: " + report);
			report.addSystemInfo("Environment", config.getProperty("Environment"));
		}

		@BeforeClass(groups = { "Smoke", "current" })
		public void beforeClass() {
			logger = report.startTest(this.getClass().getSimpleName());
		}


		@AfterMethod(groups = { "Smoke", "current" })
		public void tearDown(ITestResult result) {
			if (result.getStatus() == ITestResult.FAILURE) {
				String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
				String image = logger.addScreenCapture(screenshotPath);

				logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
			} else if (result.getStatus() == ITestResult.SKIP) {
				logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
			}

		}

		@AfterClass(groups = { "Smoke", "current" })
		public void afterClass() {
			driver.quit();
			report.endTest(logger);
		}

		@AfterTest(groups = { "Smoke", "current" })
		public void afterTest() {
			report.flush();

			System.out.println("*** End SiteWideSearch Test ***");
			System.out.println(" ");
		}


	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());

		driver = BrowserManager.startBrowser(browser, "about:blank");

		System.out.println("Page Options setup done");
		System.out.println("");
		testDataFilePath = config.getProperty("SiteWideSearchData");
	}

	@Test(dataProvider = "SiteWideSearchData", groups = { "Smoke" })
	public void SearchIsVisible(String siteWideSearchUrl, String siteWideSearchType,
														  String siteWideSearchComment) {

		System.out.println(" ");
		System.out.println("Testing SearchIsVisible()");
		String url = "https://www-qa.cancer.gov/publications";

		// Assigning and printing the values from the spreadsheet
		// String url     = siteWideSearchUrl;
		// String type    = pageOptionsType;
		String comment = siteWideSearchComment;
		System.out.println("   " + comment);
		System.out.println("   " + url);

		driver.get(url);

		PageOptions page;

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.IsVisible();
			System.out.println(isVisible);
			Assert.assertTrue(isVisible, "*** Error: Search Form Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Assert.fail("*** Error loading page in SearchIsVisible() ***");
		}
	}

	// DataProvider to read the Excel spreadsheet data containin URLs to be checked
	// ----------------------------------------------------------------------------
	@DataProvider(name = "SiteWideSearchData")
	public Iterator<Object[]> readKeywordText() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsUrl    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
																"PageOptions Type", rowNum);
			String pageOptionsComment = excelReader.getCellData(TESTDATA_SHEET_NAME,
																"Comment", rowNum);
			Object ob[] = { pageOptionsUrl, pageOptionsType, pageOptionsComment};

			myObjects.add(ob);
		}
		return myObjects.iterator();
	}
}