package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.nci.Utilities.BrowserManager;
import com.nci.Utilities.ConfigReader;
import com.nci.Utilities.ScreenShot;
import com.nci.testcases.BaseClass;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.WebAnalytics.AnalyticsRequest;
import gov.nci.WebAnalytics.AnalyticsParams;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;

public class AnalyticsTestBase extends BaseClass {

	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver;
    public static BrowserMobProxy proxy = new BrowserMobProxyServer();	
	public ConfigReader config = new ConfigReader();
	public String pageURL;

	// TODO: Create 'catch-all' Contains() method
	// TODO: Build test for test
	// TODO: Check false positives for events 	
	// TODO: Clean up
	protected static List<String> harList;
	protected static AnalyticsRequest beacon;
	protected static List<AnalyticsRequest> loadBeacons;
	protected static List<AnalyticsRequest> clickBeacons;
	
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
	@Parameters	
	public void beforeTest() {
		// log.info("Starting a new test");
		System.out.println(this.getClass().getSimpleName());
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);
		report = new ExtentReports(extentReportPath + config.getProperty("Environment") + "-" + fileName + ".html");
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}
	
	@AfterTest(groups = { "Analytics" })
	public void afterTest() {
		report.flush();
	}	

	@BeforeGroups(groups = { "Analytics" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("HomePage");
		System.out.println("PageURL: " + pageURL);
						
		// setupProxy(driver);
		this.initializeProxy(pageURL);
		
		// Initialize driver and open browser
		driver = BrowserManager.startProxyBrowser(browser, pageURL, proxy);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		
		// Add entries to the HAR log		
		System.out.println("Analytics setup done");
	}	
	

	/**
	 * Start and configure BrowserMob Proxy for Selenium.<br/>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	protected void initializeProxy(String url) throws RuntimeException {

		if(proxy.isStarted()) {
			proxy.stop();
		}
		
		// Start the proxy
		System.out.println("=== Starting BrowserMobProxy ===");		
	    proxy.start();

	    // Enable more detailed HAR capture, if desired (see CaptureType for the complete list)
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

	    // Create a new HAR with a label matching the hostname
	    proxy.newHar(url);	    
		System.out.println("=== Started BrowserMobProxy successfully ===");
	}
	
	/**
	 * Configure BrowserMob Proxy for Selenium.<br>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	// TODO: trace our data type - don't need to be shuffling between String, URL, String...
	protected static List<String> getHarUrlList(BrowserMobProxy proxy) throws RuntimeException, IllegalArgumentException {		

		// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring tools to export collected data. 
		// BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture HTTP content, 
	    // and export performance data as a HAR file object.
	    Har har = proxy.getHar();
	    List<String> harList = new ArrayList<String>();
	    
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Requests to " + AnalyticsRequest.TRACKING_SERVER + ":");
    	
	    for (HarEntry entry : entries) {
	    	// Build a list of requests to the analytics tracking server from the HAR
	    	String result = entry.getRequest().getUrl();
	    	if(result.contains(AnalyticsRequest.TRACKING_SERVER))
	    	{
	    		harList.add(result);
	    		System.out.println(result);
	    	}
	    }
	    
	    // Debug size of har list
    	System.out.println("Total HAR entries: " + entries.size());
		System.out.println("Total analytics entries: " + harList.size());

		// harList cleanup logic here		
		har.getLog().getEntries().clear();
		
		return harList;
	}	
		
	@AfterGroups(groups = { "Analytics" })
	public void afterClass() {
		System.out.println("=== Quitting Driver ===");
		driver.quit();
		report.endTest(logger);
		System.out.println("=== Stopping BrowserMobProxy ===");
		proxy.stop();
	}	
	
	@BeforeClass(groups = { "Analytics" })
	public void beforeClass() {
		logger = report.startTest(this.getClass().getSimpleName());
	}

	@BeforeMethod(groups = { "Analytics" })
	public void preMaximize() throws RuntimeException {
		// Reset our browser to full screen before each method
		driver.manage().window().maximize();
	}
	
	@AfterMethod(groups = { "Analytics" })
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);
			// logger.addScreenCapture("./test-output/ExtentReport/");
			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
			driver.get(pageURL);
		}

		if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
			driver.get(pageURL);
		}
	}
	
	/**
	 * Utility function to check for a given suite name
	 * @return
	 */
	public boolean hasSuite() {
		// TODO: fill this out
		return false;
	}

	/**
	 * Utility function to check for a given channel name
	 * @return
	 */
	public boolean hasChannel() {
		// TODO: fill this out
		return false;
	}
	
	/**
	 * Utility function to check for a link name value within a click beacon.
	 * @param name
	 * @return
	 */
	public boolean hasLinkName(String name) {
		if(beacon.linkName.equalsIgnoreCase(name)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Utility function to check for an event value within a click beacon.
	 * @param evt
	 * TODO: fix hardcoded values
	 */
	public boolean hasEvent(int eventNumber) {
		String evt = "event" + Integer.toString(eventNumber);
		for(String event : beacon.events) {
			if(evt.equalsIgnoreCase("event47")) {
				if(event.matches("^event47=\\d+")) {
					return true;
				}
			} 
			else if(event.equalsIgnoreCase(evt)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Utility function to check for a given prop and value
	 * @param num
	 * @param val
	 * @return
	 */
	public boolean hasProp(int num, String val) {
		String blob = beacon.props.toString();
		if(blob.toLowerCase().contains("prop" + Integer.toString(num) + "=" + val.toLowerCase())) {
			return true;
		}
		return false;
	}	
	
	/**
	 * Utility function to check for a given eVar and value
	 * @param num
	 * @param val
	 * @return
	 */
	public boolean haseVar(int num, String val) {
		String blob = beacon.eVars.toString();
		if(blob.toLowerCase().contains("evar" + Integer.toString(num) + "=" + val.toLowerCase())) {
			return true;
		}
		return false;
	}	

	/**
	 * Utility function to check for a given heirarchy and value
	 * @param num
	 * @param val
	 * @return
	 */
	public boolean hasHier(int num, String val) {
		// TODO: fill this out
		return false;
	}
		
	/**
	 * Utility function to get the last element in a list of AnalyticsRequest objects
	 * @param requests
	 * @return the last AnalyticsRequest object
	 */
	private static AnalyticsRequest getLast(List<AnalyticsRequest> requests) {
		AnalyticsRequest request = requests.get(requests.size() - 1);
		return request;
	}
	
	/**
	 * Set the global loadBeacons and beacon variables
	 */
	protected static void setClickBeacon() {
		clickBeacons = getBeacons(getHarUrlList(proxy), true);
		beacon = getLast(clickBeacons);
	}
	
	/**
	 * Set the global clickBeacons and beacon variables
	 */
	protected static void setLoadBeacon() {
		loadBeacons = getBeacons(getHarUrlList(proxy), false);
		beacon = getLast(loadBeacons);
	}
	
	/**
	 * Get a list of beacon URLs fired off for load events
	 * @param urlList
	 * @param isClick
	 * @return list of AnalyticsRequest objects
	 */
	protected static List<AnalyticsRequest> getBeacons(List<String> urlList, boolean isClick) {
				
		List<AnalyticsRequest> beacons = new ArrayList<AnalyticsRequest>();
		AnalyticsRequest req = new AnalyticsRequest();

		for(String url : urlList)
		{
			// If this doesn't have the "Link Type" param ('pe'), add to list of load beacons
			List<NameValuePair> params = new AnalyticsParams(req.createURI(url)).getAll();
			
			if(isClick) {
				if(req.hasLinkType(params)) {
					beacons.add(new AnalyticsRequest(url));
				}
			}
			else {
				beacons.add(new AnalyticsRequest(url));
			}
		}

		System.out.println("Total load beacons: " + beacons.size());
		System.out.println("Total click beacons: " + (urlList.size() - beacons.size()));
		return beacons;
	}
	
	/**
	 * Override for getBeacons
	 * @param urlList
	 * @return list of AnalyticsRequest objects
	 */
	protected static List<AnalyticsRequest> getBeacons(List<String> urlList) {
		return getBeacons(urlList, true);
	}
	
}
