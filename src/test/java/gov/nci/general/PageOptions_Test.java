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
import org.openqa.selenium.WebDriver;
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

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ExcelManager;
import gov.nci.Utilities.ScreenShot;
import gov.nci.commonobjects.PageOptions;

public class PageOptions_Test {

		private String TESTDATA_PATH;

		protected static ExtentReports report;
		protected static ExtentTest logger;
		protected WebDriver driver;
		protected ConfigReader config = new ConfigReader();

		@BeforeTest(groups = { "Smoke-VE", "current" })
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

		@BeforeClass(groups = { "Smoke-VE", "current" })
		public void beforeClass() {
			logger = report.startTest(this.getClass().getSimpleName());
		}


		@AfterMethod(groups = { "Smoke-VE", "current" })
		public void tearDown(ITestResult result) {
			if (result.getStatus() == ITestResult.FAILURE) {
				String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
				String image = logger.addScreenCapture(screenshotPath);

				logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
			} else if (result.getStatus() == ITestResult.SKIP) {
				logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
			}
		}

		@AfterClass(groups = { "Smoke-VE", "current" })
		public void afterClass() {
			driver.quit();
			report.endTest(logger);
		}

		@AfterTest(groups = { "Smoke-VE", "current" })
		public void afterTest() {
			report.flush();

			System.out.println("*** End PageOptions Test ***");
			System.out.println(" ");
		}


	@BeforeClass(groups = { "Smoke-VE", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());

		driver = BrowserManager.startBrowser(browser, "about:blank");

		System.out.println("Site Wide Search setup done");
		System.out.println("");
		TESTDATA_PATH = config.getProperty("PageOptionsData");
	}

	@Test(dataProvider = "loadVisiblePageOptionsList", groups = { "Smoke-VE" })
	public void IsVisible(String url) {

		System.out.println(" ");
		System.out.println("Testing IsVisible()");

		// Assigning and printing the values from the spreadsheet
		// String url     = pageOptionsHost + pageOptionsUrl;
		// String type    = pageOptionsType;
		// String comment = pageOptionsComment;
		// System.out.println("   " + comment);
		System.out.println("   " + url);

		driver.get(url);

		PageOptions page;

		// IsVisible() fails if the selector cannot be found.  Use the information from the
		// spreadsheet (type="none") to skip calling IsVisible()
		// if (!type.equals("none")) {
			try {
				page = new PageOptions(driver);
				boolean isVisible = page.IsVisible();
				Assert.assertTrue(isVisible, "*** Error: Page Options Box Not Found ***");
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				Assert.fail("*** Error loading page in IsVisible() ***");
			}
		// } else if (type.equals("none")) {
		// 	try {
		// 		page = new PageOptions(driver);
		// 		boolean isVisible = page.IsVisible();
		// 		Assert.assertFalse(isVisible, "*** Error: Found Page Options Box ***");
		// 	} catch (MalformedURLException | UnsupportedEncodingException e) {
		// 		Assert.fail("*** Error loading page in MissingOk() *** ");
		// 	}
		// }
	}

	@Test(dataProvider = "loadAllPageOptionsList", groups = { "Smoke-VE" })
	public void FontVisible(String url) {

		System.out.println(" ");
		System.out.println("Testing FontVisible()");
		System.out.println("   " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--resize";

		// FontVisible() fails if the selector cannot be found.  Use the information from the
		// spreadsheet (type="none") to skip calling IsVisible()
			try {
				page = new PageOptions(driver);
				boolean isVisible = page.ButtonVisible(cssSelector);
				Assert.assertTrue(isVisible, "*** Error: Font Resize Button Not Found ***");
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				Assert.fail("*** Error loading page in FontVisible() ***");
			}
	}


	@Test(dataProvider = "loadAllPageOptionsList", groups = { "Smoke-VE" })
	public void TwitterVisible(String url) {

		System.out.println(" ");
		System.out.println("Testing TwitterVisible()");
		System.out.println("   " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--twitter";

		// FontVisible() fails if the selector cannot be found.  Use the information from the
		// spreadsheet (type="none") to skip calling IsVisible()
			try {
				page = new PageOptions(driver);
				boolean isVisible = page.ButtonVisible(cssSelector);
				Assert.assertTrue(isVisible, "*** Error: Twitter Button Not Found ***");
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				Assert.fail("*** Error loading page in TwitterVisible() ***");
			}
	}


	@Test(dataProvider = "loadNotVisiblePageOptionsList", groups = { "Smoke-VE" })
	public void NotVisible(String url) {

		System.out.println("\nTesting NotVisible()");
		System.out.println("   " + url);

		driver.get(url);

		PageOptions page;

		// IsVisible() fails if the selector cannot be found.  Use the information from the
		// spreadsheet (type="none") to skip calling IsVisible()
			try {
				page = new PageOptions(driver);
				boolean isVisible = page.IsVisible();
				Assert.assertFalse(isVisible, "*** Error: Found Page Options Box ***");
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				Assert.fail("*** Error loading page in MissingOk() *** ");
			}
		// }
	}



/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadVisibleOptionsList")
	public Iterator<Object[]> loadVisiblePageOptionsList() {
		String TESTDATA_SHEET_NAME = "Visible Page Options";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"Hostname", rowNum);
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			// String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"PageOptions Type", rowNum);
			// String pageOptionsComment = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"Comment", rowNum);
			String url = pageOptionsHost + pageOptionsPath;
			Object ob[] = { url };

			myObjects.add(ob);
		}
		return myObjects.iterator();
	}


	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs without Page Options
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadNotVisiblePageOptionsList")
	public Iterator<Object[]> loadNotVisiblePageOptionsList() {
		String TESTDATA_SHEET_NAME = "Not Visible Page Options";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"Hostname", rowNum);
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			// String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"PageOptions Type", rowNum);
			// String pageOptionsComment = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"Comment", rowNum);
			String url = pageOptionsHost + pageOptionsPath;
			Object ob[] = { url };

			myObjects.add(ob);
		}
		return myObjects.iterator();
	}


	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs without Page Options
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadDefaultPageOptionsList")
	public Iterator<Object[]> loadDefaultPageOptionsList() {
		String TESTDATA_SHEET_NAME = "Visible Page Options";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"Hostname", rowNum);
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
																"PageOptions Type", rowNum);
			// String pageOptionsComment = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"Comment", rowNum);
			String url = pageOptionsHost + pageOptionsPath;

			if (pageOptionsType.equals("default")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadAllPageOptionsList")
	public Iterator<Object[]> loadAllPageOptionsList() {
		String TESTDATA_SHEET_NAME = "Visible Page Options";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"Hostname", rowNum);
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Type", rowNum);
			// String pageOptionsComment = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"Comment", rowNum);
			String url = pageOptionsHost + pageOptionsPath;

			if (pageOptionsType.equals("all")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadR4RPageOptionsList")
	public Iterator<Object[]> loadR4RPageOptionsList() {
		String TESTDATA_SHEET_NAME = "Visible Page Options";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"Hostname", rowNum);
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
																"PageOptions Type", rowNum);
			// String pageOptionsComment = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// 													"Comment", rowNum);
			String url = pageOptionsHost + pageOptionsPath;

			if (pageOptionsType.equals("r4r")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
}