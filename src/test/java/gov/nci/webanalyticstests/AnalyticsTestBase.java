package gov.nci.webanalyticstests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ConfigReader;
import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.Beacon;

public abstract class AnalyticsTestBase {

	protected static WebDriver driver;
	protected static BrowserMobProxy proxy;
	protected static ExtentReports report;
	protected static ExtentTest logger;
	protected static String trackingServer;

	protected ConfigReader config;
	protected boolean debug;
	
	// ==================== TextNG Befores & After methods ==================== //
	
	/**
	* Configuration information for a TestNG class (http://testng.org/doc/documentation-main.html):
	* @BeforeSuite: The annotated method will be run before all tests in this suite have run.
	* @AfterSuite: The annotated method will be run after all tests in this suite have run.
	* @BeforeTest: The annotated method will be run before any test method belonging to the classes inside the <test> tag is run.
	* @AfterTest: The annotated method will be run after all the test methods belonging to the classes inside the <test> tag have run.
	* @BeforeGroups: The list of groups that this configuration method will run before.
	* 				 This method is guaranteed to run shortly before the first test method that belongs to any of these groups is invoked.
	* @AfterGroups: The list of groups that this configuration method will run after.
	* 				This method is guaranteed to run shortly after the last test method that belongs to any of these groups is invoked.
	* @BeforeClass: The annotated method will be run before the first test method in the current class is invoked.
	* @AfterClass: The annotated method will be run after all the test methods in the current class have been run.
	* @BeforeMethod: The annotated method will be run before each test method.
	* @AfterMethod: The annotated method will be run after each test method.
	**/

	@BeforeTest(groups = { "Analytics" })
	@Parameters({ "browser", "environment" })
	public void beforeTest(String browser, String environment) throws MalformedURLException {
		config = new ConfigReader(environment);
		
		// Start the BrowserMob proxy on the site homepage
		System.out.println("=== Starting BrowserMobProxy ===");
		String initUrl = config.goHome();
		initializeProxy(initUrl);

		// Initialize driver and open browser
		System.out.println("=== Starting Driver ===");
		driver = BrowserManager.startProxyBrowser(browser, config, initUrl, proxy);
		trackingServer = config.getProperty("AdobeTrackingServer");
		System.out.println("~~ Analytics test group setup done.~~");		
		System.out.println("  Testing requests to " + trackingServer + "\r\n  Starting from " + initUrl);

		// Get path and configure extent reports
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("  Logger Path:" + extentReportPath);
		report = new ExtentReports(extentReportPath + config.getProperty("Environment") + "-" + fileName + ".html");
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}

	@BeforeClass(groups = { "Analytics" })
	@Parameters({ "environment", "debug" })
	public void beforeClass(String environment, boolean debug) {
		config = new ConfigReader(environment);
		this.debug = debug;
		
		String testClass = this.getClass().getSimpleName();
		System.out.println("\n~~ " + testClass + " ~~\n");
		logger = report.startTest(testClass);
	}

	@BeforeMethod(groups = { "Analytics" })
	public void beforeMethod(Method method) throws RuntimeException {
		String message = method.getName() + "(): ";
		System.out.println(message);

		// Reset our browser to full screen before each method
		driver.manage().window().maximize();
	}

	@AfterMethod(groups = { "Analytics" })
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "FAILED => " + result.getName() + ": " + result.getThrowable().getMessage());
			System.out.println("  Failed: " + result.getThrowable().getMessage() + ".\n");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "SKIPPED => " + result.getName());
			System.out.println("  Skipped.\n");
		} else {
			logger.log(LogStatus.PASS, "PASSED => " + result.getName());
			System.out.println("  Passed!\n");
		}
	}

	@AfterClass(groups = { "Analytics" })
	public void afterClass() {
		report.endTest(logger);
	}

	@AfterTest(groups = { "Analytics" })
	public void afterTest() {
		System.out.println("=== Quitting Driver ===");
		report.flush();
		driver.quit();
		
		System.out.println("=== Stopping BrowserMobProxy ===");
		proxy.stop();
	}

	// ==================== BMP and request beacon Initialization methods ==================== //
	
	/**
	 * Start and configure BrowserMob Proxy for Selenium.<br/>
	 * Modified from
	 * https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * 
	 * @param starting URL 
	 * @throws RuntimeException
	 */
	protected void initializeProxy(String url) throws RuntimeException {

		// New BrowserMobProxy instance - this is needed to create the HAR (HTTP
		// archive) object
		proxy = new BrowserMobProxyServer();
		proxy.start(0);

		// Selenium proxy object, capabilities, and WebDriver instantiation are handled
		// in BrowserManager:startProxyBrowser()
		// Enable more detailed HAR capture, if desired (see CaptureType for the
		// complete list)
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

		// Create a new HAR with a label matching the hostname
		proxy.newHar(url);
		System.out.println("== Started BrowserMobProxy successfully ==");
	}

	/**
	 * Build the list of HAR (HTTP archive) request URLs. Modified from
	 * https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * 
	 * @param proxy (BrowserMobProxy object)
	 * @return list of URLs
	 * @throws RuntimeException
	 * @throws IllegalArgumentException
	 */
	protected List<String> getHarUrlList(BrowserMobProxy proxy) throws RuntimeException, IllegalArgumentException {

		// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring
		// tools to export collected data. BrowserMob Proxy allows us to manipulate HTTP
		// requests and responses, capture HTTP content, and export performance data as
		// a HAR file object.
		Har har = proxy.getHar();
		List<HarEntry> entries = har.getLog().getEntries();

		// Reset HAR URL list
		List<String> harUrlList = new ArrayList<String>();

		// Build a list of requests to the analytics tracking server from the HAR
		for (HarEntry entry : entries) {
			String result = entry.getRequest().getUrl();
			if (result.contains(trackingServer)) {
				harUrlList.add(result);
			}
		}

		// Debug size of HAR list
		if (debug) {
			System.out.println("Total HAR entries: " + entries.size());
		}
		
		// The HAR list has been created; clear the log for next pass
		har.getLog().getEntries().clear();
		// For further reading, see https://en.wiktionary.org/wiki/hardy_har_har

		return harUrlList;
	}

	// ==================== Excel data retrieval methods ==================== //

	/**
	 * Get a collection of spreadsheet data from a given sheet and column(s). 
	 *
	 * @param File path for the data
	 * @param Spreadsheet name
	 * @param Column values to return
	 * @return a collection of test data
	 */
	protected Iterator<Object[]> getSpreadsheetData(String testDataFilePath, String sheetName, String[] columns) {
		return getSpreadsheetData(testDataFilePath, sheetName, columns, null);
	}

	/**
	 * Get a filtered collection of spreadsheet data from a given sheet and
	 * column(s).
	 * 
	 * @param File path for the data
	 * @param Spreadsheet name
	 * @param Column values to return
	 * @param FilterParams - pair of values representing the column to filter and the expected value.
	 * @return a collection of test data
	 */
	protected Iterator<Object[]> getFilteredSpreadsheetData(String testDataFilePath, String sheetName, String[] columns,
			String[] filterParams) {
		return getSpreadsheetData(testDataFilePath, sheetName, columns, filterParams);
	}
	
	/**
	 * Get a collection of spreadsheet data from given sheet, column, and filter values.
	 * 
	 * @param testDataFilePath
	 * @param sheetName
	 * @param columns
	 * @return collection of spreadsheet data rows from a given sheet and columns.
	 */
	private Iterator<Object[]> getSpreadsheetData(String testDataFilePath, String sheetName, String[] columns,
			String[] filterParams) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		int rowCount = excelReader.getRowCount(sheetName);
		int colCount = columns.length;

		// Stop if the search column array is more than ten items
		if (colCount > 10) {
			System.out.println(
					"Trying to retrieve too many columns in data sheet; check test data and data provider method.");
			return null;
		}

		// Stop if our test data is more than 300 cells
		if (colCount * rowCount > 300) {
			System.out.println(
					"Number of data rows and columns may be getting out of control. Split out test data as needed.");
			return null;
		}

		// IF we have a manageable amount of data, dynamically generate an array (rows)
		// of arrays (columns).
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			if (meetsFilterCriteria(excelReader, sheetName, filterParams, rowNum) == true) {
				ArrayList<String> tempList = getCellDataList(excelReader, sheetName, columns, rowNum);
				myObjects.add(tempList.toArray());
			}
		}

		return myObjects.iterator();
	}

	/**
	 * Get a list of column values for a single row.
	 * 
	 * @param excelReader
	 * @param sheetName
	 * @param cols
	 * @param rowNum
	 * @return List of column values.
	 */
	private ArrayList<String> getCellDataList(ExcelManager excelReader, String sheetName, String[] cols, int rowNum) {
		ArrayList<String> cdl = new ArrayList<String>();

		for (int i = 0; i <= cols.length - 1; i++) {
			String myItem = excelReader.getCellData(sheetName, cols[i], rowNum);
			cdl.add(myItem);
		}
		return cdl;
	}

	/**
	 * Check if 1) filter parameters have been passed in or 2) the cell value
	 * matches our filter value. If so, return true.
	 * 
	 * @param ExcelManager object
	 * @param Sheet name
	 * @param filterParams
	 * @param rowNum
	 * @return
	 */
	private boolean meetsFilterCriteria(ExcelManager excelReader, String sheetName, String[] filterParams, int rowNum) {
		if (filterParams == null) {
			return true;
		}

		try {
			String filterColumn = filterParams[0];
			String filterValue = filterParams[1];

			if (filterColumn.isEmpty() || filterValue.isEmpty()) {
				return true;
			} else if (excelReader.getCellData(sheetName, filterColumn, rowNum).contains(filterValue)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println("Invalid filter parameters; filter will not be applied.");
			return true;
		}
	}

	// ==================== Abstract methods ==================== //

	/**
	 * Get a single Beacon object for testing.
	 * 
	 * @param index - optional int value
	 * @return beacon (Beacon)
	 */
	protected abstract Beacon getBeacon(int... index);

	/**
	 * Get a list of Beacon objects.
	 * 
	 * @param urlList (List<String>)
	 * @return List of Beacon objects
	 */
	protected abstract List<Beacon> getBeaconList(List<String> urlList);

}