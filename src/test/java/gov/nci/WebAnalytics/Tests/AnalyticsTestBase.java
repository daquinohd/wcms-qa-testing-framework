package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.NameValuePair;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserManager;
import com.nci.Utilities.ConfigReader;
import com.nci.Utilities.ScreenShot;
import com.nci.testcases.BaseClass;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.WebAnalytics.AnalyticsBase;
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

	// TODO: build a 'beacon params' object 
	// TODO: Build negative tests
	// TODO: Build test for test
	// TODO: Check false positives for events 	
	// TODO: Create reusable utility methods
	protected static List<String> harList;
	protected static List<AnalyticsBase> loadBeacons;
	protected static List<AnalyticsBase> clickBeacons;
	    
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
		// report.close();
		// log.info("Test ends here");
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
	// TODO: remove duplicates (?)
	// TODO: trace our data type - don't need to be shuffling between String, URL, String...
	protected static List<String> getHarUrlList(BrowserMobProxy proxy) throws RuntimeException, IllegalArgumentException {		

		// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring tools to export collected data. 
		// BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture HTTP content, 
	    // and export performance data as a HAR file object.
	    Har har = proxy.getHar();
	    List<String> harList = new ArrayList<String>();
	    
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Requests to " + AnalyticsBase.TRACKING_SERVER + ":");
    	
	    for (HarEntry entry : entries) {
	    	// Build a list of requests to the analytics tracking server from the HAR
	    	String result = entry.getRequest().getUrl();
	    	if(result.contains(AnalyticsBase.TRACKING_SERVER))
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
	 * Utility function to check for a link name value within a click beacon.
	 * @param clickBeacons
	 * @param name
	 * @return
	 */
	public boolean hasLinkName(List<AnalyticsBase> clickBeacons, String name) {
		for(AnalyticsBase beacon : clickBeacons) {
			if(beacon.linkName.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Utility function to check for an event value within a click beacon.
	 * @param clickBeacons
	 * @param evt
	 * @return
	 */
	public boolean hasEvent(List<AnalyticsBase> clickBeacons, String evt) {
		for(AnalyticsBase beacon : clickBeacons) {
			String events = Arrays.toString(beacon.events);
			if(events.toLowerCase().contains(evt)) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Utility function to check for a given prop and value
	 * @param clickBeacons
	 * @param num
	 * @param val
	 * @return
	 */
	public boolean hasProp(List<AnalyticsBase> clickBeacons, int num, String val) {
		for(AnalyticsBase beacon : clickBeacons) {
			String blob = beacon.props.toString();
			if(blob.toLowerCase().contains("prop" + Integer.toString(num) + "=" + val.toLowerCase())) {
				return true;
			}
		}
		return false;
	}	

	/**
	 * Utility function to check for a given eVar and value
	 * @param clickBeacons
	 * @param num
	 * @param val
	 * @return
	 */
	public boolean haseVar(List<AnalyticsBase> clickBeacons, int num, String val) {
		for(AnalyticsBase beacon : clickBeacons) {
			String blob = beacon.eVars.toString();
			if(blob.toLowerCase().contains("evar" + Integer.toString(num) + "=" + val.toLowerCase())) {
				return true;
			}
		}
		return false;
	}	
	
}
