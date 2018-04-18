package com.nci.testcases;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
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

	AnalyticsLoadEvents loadEvents;
	AnalyticsClickEvents clickEvents;
    BrowserMobProxy proxy = new BrowserMobProxyServer();
	
	// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring tools to export collected data. 
	// BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture HTTP content, 
    // and export performance data as a HAR file object.
	Har har;
	List<String> harList = new ArrayList<String>();

	
	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	// TODO: tear down selenium proxy when done	
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
		loadEvents = new AnalyticsLoadEvents(driver);
		clickEvents = new AnalyticsClickEvents(driver);
		
		// Add entries to the HAR log
		populateHar();
		setHar();
		
		
		System.out.println("Analytics setup done");
	}	
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	private void populateHar() throws RuntimeException {
		//TODO: refactor this
		navigateSite();
		resizeBrowser();
		//doSiteWideSearch();
		//doAdvancedCTSearch();
		//doBasicCTSearch();
		//useDictionary();
		//navigateError();
		//navigateRATs();		
	}
	
	/**
	 * Configure BrowserMob Proxy for Selenium.<br>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	// TODO: move this into base analytics class
	private void setHar() throws RuntimeException {
		
	    // Get the HAR data and print to console for now
	    // TODO: Break this out into actual tests
	    // TODO: Start tracking click events
	    har = proxy.getHar();
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Total HAR entries: " + entries.size());
    	
	    for (HarEntry entry : entries) {
	    	if(entry.getRequest().getUrl().contains(AnalyticsBase.TRACKING_SERVER))
			{
	    		String result = entry.getRequest().getUrl();
	    		try {
					result = URLDecoder.decode(result, "UTF-8");
					if(result.contains("pageName=" + AnalyticsBase.PAGE_NAME)) {
						harList.add(result);
					}
				} catch (Exception e) {
					result = "bleah";
				} 
				//System.out.println(result);
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


	/*** REGION ACTIONS TO POPULATE HAR ***/
	
	/// Click around pages
	public void navigateSite() {
				
		// Click on a feature card
		clickEvents.clickFeatureCard();
		driver.navigate().back();
		
		// Click on the MegaMenu
		clickEvents.clickMegaMenu();		
		driver.navigate().back();
		
	}

	// Resize browser
	public void resizeBrowser() {
		Dimension small = new Dimension(300, 800);
		Dimension med = new Dimension(700, 800);
		Dimension large = new Dimension(1100, 800);
		Dimension xlarge = new Dimension(1600, 800);
				
		driver.manage().window().setSize(xlarge);
		driver.manage().window().setSize(large);		
		driver.manage().window().setSize(med);
		driver.manage().window().setSize(small);
		driver.manage().window().maximize();
	}

	/*** END REGION ACTIONS TO POPULATE HAR ***/

	
	/*** REGION TESTS ***/

	// TODO: Set expected load values for different pages
	// TODO: Work out what we need to fire off on click/resize/other events
	// 		- Do we need to create a new HAR with each call? 
	//		- How do we differentiate between load and click calls?	
	// TODO: add "analytics" group
	// TODO: what are we writing to with "logger"? 
	// TODO: make a new group (not 'Smoke')
	
	/// "NCIAnalytics" elements are present in HTML
	@Test(groups = { "Smoke" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = loadEvents.getSitewideSearchWAFunction();
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoadEvents.NCI_FUNCTIONS_NAME));
		logger.log(LogStatus.PASS, "NCIAnalytics attribute is present on search form.");
	}	
	
	/// The HAR list is populated 
	@Test(groups = { "Smoke" })
	public void verifyHarLoad() {
		// Update the har object
		Assert.assertTrue(harList.size() > 0);
		logger.log(LogStatus.PASS, "Load events are being captured.");
	}	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Smoke" })
	public void testClickEvents() {

		// Debug
		System.out.println("=== Start debug testEvents() ===");		
		System.out.println("Total requests to tracking server : " + harList.size());
		
		for(String har : harList) {
			System.out.println(har);
			
			Assert.assertTrue(har.contains("nci"));
			logger.log(LogStatus.PASS, "Pass => " + "Verify 'nci' value...");
			
			//TODO: make our har an analyticsBeacon(?) object and compare that way...
			if(har.contains("pev2=FeatureCardClick")) {
				Assert.assertTrue(har.contains("events=event27"));				
			}

			if(har.contains("pev2=MegaMenuClick")) {
				Assert.assertTrue(har.contains("events=event26"));			
			}
		}
		System.out.println("=== End debug testEvents() ===");
		
	}	
	
	/// Resize events match with their descriptors
	@Test(groups = { "Smoke" })
	public void testResizeEvents() {
		List<String> localHar = harList;
		
		for(String har : localHar) {
			if(har.contains("pev2=ResizedToMobile")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}
			if(har.contains("pev2=ResizedToTablet")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}
			if(har.contains("pev2=ResizedToDesktop")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}
			if(har.contains("pev2=ResizedToExtra wide")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}			
		}
		
	}
	
	/*** END REGION TESTS ***/
	
}
