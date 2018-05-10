package com.nci.testcases;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.CaptureType;

import com.nci.Utilities.BrowserManager;
import com.relevantcodes.extentreports.LogStatus;
import gov.nci.WebAnalytics.AnalyticsClickEvents;
import gov.nci.WebAnalytics.AnalyticsLoadEvents;
import gov.nci.WebAnalytics.AnalyticsBase;

public class Analytics_Test extends AnalyticsTestBase {

	AnalyticsLoadEvents loadEvents;
	AnalyticsClickEvents clickEvents;
    BrowserMobProxy proxy = new BrowserMobProxyServer();	
	List<String> harList = new ArrayList<String>();
	List<AnalyticsBase> beacons = new ArrayList<AnalyticsBase>();
	
	@BeforeClass(groups = { "Analytics" })
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
		AnalyticsBase.setHar(proxy, harList);
		//setHar();
		
		
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
	
	/// "NCIAnalytics" elements are present in HTML
	@Test(groups = { "Analytics" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = loadEvents.getSitewideSearchWAFunction();
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoadEvents.NCI_FUNCTIONS_NAME));
		logger.log(LogStatus.PASS, "NCIAnalytics attribute is present on search form.");
	}	
	
	/// The HAR list is populated 
	@Test(groups = { "Analytics" })
	public void verifyHarLoad() {
		// Update the har object
		Assert.assertTrue(harList.size() > 0);
		logger.log(LogStatus.PASS, "Load events are being captured.");
	}	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testClickEvents() {

		// Debug
		System.out.println("=== Start debug testEvents() ===");		
		System.out.println("Total requests to tracking server : " + harList.size());
		
		//TODO: build out requestBeacon(?) object and compare that way...
		for(AnalyticsBase beacon : beacons) {
			// do something
		}		
		
		for(String har : harList) {
			System.out.println(har);
			
			Assert.assertTrue(har.contains("nci"));
			logger.log(LogStatus.PASS, "Pass => " + "Verify 'nci' value...");
			
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
	@Test(groups = { "Analytics" })
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
	
	/// Temporary method to test beacon object
	@Test(groups = { "Analytics" })
	public void testObject() throws MalformedURLException {
		List<String> localHars = harList;
		List<AnalyticsBase> myBeacons = new ArrayList<>();

		for(String har : harList)
		{
			myBeacons.add(new AnalyticsBase(har));
		}

		// Check that we have more than one beacon
		Assert.assertTrue(myBeacons.size() > 1);
				
		// For debugging purposes only...
		String firstHar = localHars.get(0);
		AnalyticsBase firstBeacon = new AnalyticsBase(firstHar);		

		// for each beacon ... logic goes here
		Assert.assertTrue(firstBeacon.channel.equals("NCI Homepage"));
		Assert.assertFalse(firstBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstBeacon.events[0].contains("1"));

	}
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void trueFlag() {
		int j = 1;
		Assert.assertTrue(j + 1 == 2);
	}	
	

	/// TODO: Add LinkXxx properties in AnalyticsClickEvents only
	/// TODO: Build negative tests - also 
	/// TODO: Build test for test	
	/// TODO: Clean clean clean		

	/*** END REGION TESTS ***/
	
}
