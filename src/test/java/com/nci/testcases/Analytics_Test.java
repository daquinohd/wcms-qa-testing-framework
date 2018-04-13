package com.nci.testcases;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;

import com.nci.Utilities.BrowserManager;
import com.relevantcodes.extentreports.LogStatus;
import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.AnalyticsClickEvents;
import gov.nci.WebAnalytics.AnalyticsLoadEvents;

public class Analytics_Test extends BaseClass {

	AnalyticsLoadEvents analyticsLoad;
	AnalyticsClickEvents analyticsClick;
    BrowserMobProxy proxy = new BrowserMobProxyServer();
	
	// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring tools to export collected data. 
	// BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture HTTP content, 
    // and export performance data as a HAR file object.
	Har har;
	String clickHar = "";
	String loadHar = "";
	
	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("HomePage");
		System.out.println("PageURL: " + pageURL);
						
		// setupProxy(driver);
		initializeProxy(pageURL);
		
		// Initialize driver and open browser
		driver = BrowserManager.startProxyBrowser(browser, pageURL, proxy);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		

		// Create our load and click analytics objects
		analyticsLoad = new AnalyticsLoadEvents(driver);
		analyticsClick = new AnalyticsClickEvents(driver);
				
		getHarObject();
		System.out.println("Analytics setup done");
	}	
	
	/**
	 * Configure BrowserMob Proxy for Selenium.<br>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	private void getHarObject() throws RuntimeException {
		
	    // Get the HAR data and print to console for now
	    // TODO: Break this out into actual tests
	    // TODO: Start tracking click events
	    har = proxy.getHar();
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Entry count (debug): " + entries.size());
	    for (HarEntry entry : entries) {
	    	if(entry.getRequest().getUrl().contains(AnalyticsBase.TRACKING_SERVER))
			{
	    		String result = entry.getRequest().getUrl();
	    		try {
					result = URLDecoder.decode(result, "UTF-8");
					if(result.contains("=event1,")) {
						loadHar = result;
					}
				} catch (Exception e) {
					result = "bleah";
				} 
				System.out.println(result);
			}
	    }  
	    
		System.out.println("BMP proxy setup done");
	}	
	
	/**
	 * Start and configure BrowserMob Proxy for Selenium.<br/>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	private void initializeProxy(String url) throws RuntimeException {
	    // Start the proxy
	    proxy.start();

	    // Enable more detailed HAR capture, if desired (see CaptureType for the complete list)
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

	    // Create a new HAR with a label matching the hostname
	    proxy.newHar(url);	   
	}	

	
	/******** Begin testing section ********/		
	// TODO: Set expected load values for different pages
	// TODO: Work out what we need to fire off on click/resize/other events
	// 		- Do we need to create a new HAR with each call? 
	//		- How do we differentiate between load and click calls?	
	// TODO: add "analytics" group
	
	/// Check for NCIAnalytics in HTML
	@Test(groups = { "Smoke" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = analyticsLoad.getSitewideSearchWAFunction();
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoadEvents.NCI_FUNCTIONS_NAME));
		logger.log(LogStatus.PASS, "NCIAnalytics attribute is present on search form.");
	}
	
	/// Load event fired off
	@Test(groups = { "Smoke" })
	public void verifyHar() {
		Assert.assertTrue(loadHar.contains("event1,event47="));
		logger.log(LogStatus.PASS, "Load events are captured.");
	}

	/// 1 == 1
	@Test(groups = { "Smoke" })
	public void onePlusOne() {
		int h = 1;
		Assert.assertTrue(h == 1);
		logger.log(LogStatus.PASS, "One equals one");
	}
	
}
