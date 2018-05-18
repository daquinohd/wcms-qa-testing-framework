package com.nci.testcases;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.AnalyticsClick;
import gov.nci.WebAnalytics.AnalyticsLoad;
import gov.nci.WebAnalytics.MegaMenu;
import gov.nci.WebAnalytics.PageLoad;
import gov.nci.WebAnalytics.Resize;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.CaptureType;

public class AnalyticsTest extends BaseClass {

	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver;
	public String pageURL;
	public ConfigReader config = new ConfigReader();
    public static BrowserMobProxy proxy = new BrowserMobProxyServer();	

    // TODO: clean up (or remove) loadEvents / clickEvents objects
	// TODO: build a 'beacon params' object 
	// TODO: Work out what we need to fire off on click/resize/other events
	// 		- Do we need to create a new HAR with each call? 
	//		- How do we differentiate between load and click calls?	
	// TODO: get the logger to actually work
	// TODO: Determine where we want the tests to live
	// TODO: Build negative tests
	// TODO: Build test for test
	public AnalyticsLoad loadEvents;
	public AnalyticsClick clickEvents;
	
	// Analytics objects
	public PageLoad pageLoad;
	public MegaMenu megaMenu;
	public Resize resize;

    
	@BeforeTest(groups = { "Analytics" })
	@Parameters	
	public void beforeTest() {
		// log.info("Starting a new test");
		System.out.println(this.getClass().getSimpleName());
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);
		report = new ExtentReports(extentReportPath + config.getProperty("Environment") + "-" + fileName + ".html");
		System.out.println("Report Path: ");
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

		// Create our load and click analytics objects
		loadEvents = new AnalyticsLoad(driver);
		clickEvents = new AnalyticsClick(driver);
		
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
	

	

	

	/// Load and click events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void verifyLoadHar() {
		pageLoad = new PageLoad(driver);
		pageLoad.doPageLoadActions();
		
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);		
				
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		Assert.assertTrue(clickBeacons.size() == 0);
		
		System.out.println("=== Start debug testEvents() ===");
		for(String har : harList) {
			System.out.println(har);
		}
		System.out.println("=== End debug testEvents() ===");				
		
		logger.log(LogStatus.PASS, "Load and click events have been captured.");				
	}	

	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testLoadEvents() {
		pageLoad = new PageLoad(driver);
		pageLoad.doPageLoadActions();
		
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
			
		for(AnalyticsLoad beacon : loadBeacons) {
			Assert.assertTrue(beacon.events[0].contains("event1"));
			Assert.assertTrue(beacon.events[1].contains("event47"));
		}
		
		logger.log(LogStatus.PASS, "Load event values are correct.");				
	}
	
	/// Temporary method to test beacon object
	@Test(groups = { "Analytics" })
	public void testObject() throws MalformedURLException {
		pageLoad = new PageLoad(driver);
		pageLoad.goHomeAndBack();
		
		// For debugging purposes only...
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);		
		
		AnalyticsLoad firstLoadBeacon = loadBeacons.get(0);

		// for each beacon ... logic goes here
		Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage") || firstLoadBeacon.channel.contains("Research"));
		Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstLoadBeacon.events[0].contains("1"));

	}
	


	/// Load and click events have been captured
	@Test(groups = { "Analytics" })
	public void verifyResizeHar() {
		
		resize = new Resize(driver);
		resize.resizeBrowser();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);		
				
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(clickBeacons.size() > 0);
		
		System.out.println("=== Start debug testEvents() ===");
		for(String har : harList) {
			System.out.println(har);
		}
		System.out.println("=== End debug testEvents() ===");				
		
		logger.log(LogStatus.PASS, "Load and click events have been captured.");				
	}		
	
	/// Resize events match with their descriptors
	@Test(groups = { "Analytics" })
	public void testResizeEvents() {
		resize = new Resize(driver);
		resize.resizeBrowser();

		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);
		
		for(AnalyticsClick beacon : clickBeacons) {
			if(beacon.linkName.toLowerCase().contains("resize")) {
				Assert.assertTrue(beacon.events[0].contains("event7"));
			}
		}
		logger.log(LogStatus.PASS, "Resize values are correct.");
	}
	
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testRsString() {
		String str = "clickEvent";
		Assert.assertEquals("clickEvent", str);
	}
	//endregion tests	
	
	
	
	
	

	/// Load and click events have been captured
	@Test(groups = { "Analytics" })
	public void verifyHar() {
		megaMenu = new MegaMenu(driver);		
		megaMenu.doMegaMenuActions();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);		
				
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		Assert.assertTrue(clickBeacons.size() > 0);
		
		System.out.println("=== Start debug testEvents() ===");
		for(String har : harList) {
			System.out.println(har);
		}
		System.out.println("=== End debug testEvents() ===");				
		
		logger.log(LogStatus.PASS, "Load and click events have been captured.");				
	}	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testClickEvents() {
		megaMenu = new MegaMenu(driver);		
		megaMenu.doMegaMenuActions();

		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);
		
		for(AnalyticsClick beacon : clickBeacons) {
			if(beacon.linkName == "FeatureCardClick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
			}
			if(beacon.linkName == "MegaMenuClick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
			}
		}
		
		logger.log(LogStatus.PASS, "Click event values are correct.");		
	}		
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testString() {
		String str = "clickEvent";
		Assert.assertEquals("clickEvent", str);
	}	
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testInt() {
		int j = 1;
		Assert.assertTrue(j + 1 == 2);
	}
	
}
