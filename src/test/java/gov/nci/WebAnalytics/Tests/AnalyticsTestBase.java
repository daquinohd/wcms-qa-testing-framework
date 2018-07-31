package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.WebAnalytics.AnalyticsRequest;

public class AnalyticsTestBase extends BaseClass {

	// TODO: Create 'catch-all' Contains() method
	// TODO: Clean up & refactor 
	public static WebDriver driver;

	// BrowserMobProxy object - needed to create HAR
    public static BrowserMobProxy proxy = new BrowserMobProxyServer();
	
	// List of HAR (HTTP archive) request URLs 
	private List<String> harUrlList;
	public List<String> getHarUrlList() {
		return harUrlList;
	}
	
	// List of analytics request URLs fired off by 
	// an analytics load event, ie s.t() 
	private List<AnalyticsRequest> loadBeacons;
	public List<AnalyticsRequest> getLoadBeacons() {
		return loadBeacons;
	}

	// List of analytics request URLs fired off by an 
	// analytics load event, ie s.tl() 
	private List<AnalyticsRequest> clickBeacons;
	public List<AnalyticsRequest> getClickBeacons() {
		return clickBeacons;
	}
	
	// A single analytics request URL
	private AnalyticsRequest beacon;
	public AnalyticsRequest getBeacon() {
		return beacon;
	}
	public void setBeacon(AnalyticsRequest beacon) {
		this.beacon = beacon;
	}
	
	/**************************************
	 * Section: TextNG Befores & Afters *
	 **************************************/
	
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
		
		// Add entries to the HAR log		
		System.out.println("Analytics setup done");
	}	
			
	@AfterGroups(groups = { "Analytics" })
	public void afterGroups() {
		System.out.println("=== Quitting Driver ===");
		driver.quit();
		report.endTest(logger);
		System.out.println("=== Stopping BrowserMobProxy ===");
		proxy.stop();
	}

	@BeforeMethod(groups = { "Analytics" })
	public void beforeMethodMaximize() throws RuntimeException {
		// Reset our browser to full screen before each method
		driver.manage().window().maximize();
	}
	
	/******************************************************
	 * Section: Initialize BMP and request beacon objects *
	 ******************************************************/
	
	/**
	 * Start and configure BrowserMob Proxy for Selenium.<br/>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	protected void initializeProxy(String url) throws RuntimeException {

		// We should never run into this, but if so, "stop" the proxy by creating a BMP instance
		if(proxy.isStarted()) {
		    proxy = new BrowserMobProxyServer();
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
	 * @throws IllegalArgumentException
	 */
	protected void setHarUrlList(BrowserMobProxy proxy) throws RuntimeException, IllegalArgumentException {		

		// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring tools to export collected data. 
		// BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture HTTP content, 
	    // and export performance data as a HAR file object.
	    Har har = proxy.getHar();
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Requests to " + AnalyticsRequest.TRACKING_SERVER + ":");
    	
    	// Reset HAR URL list
    	harUrlList = new ArrayList<String>();

    	// Build a list of requests to the analytics tracking server from the HAR
	    for (HarEntry entry : entries) {
	    	String result = entry.getRequest().getUrl();
	    	if(result.contains(AnalyticsRequest.TRACKING_SERVER))
	    	{
	    		harUrlList.add(result);
	    		System.out.println(result);
	    	}
	    }
	    
	    // Debug size of HAR list
    	System.out.println("Total HAR entries: " + entries.size());
		System.out.println("Total analytics requests: " + harUrlList.size());
		
		// The HAR list has been created; clear the log for next pass
		har.getLog().getEntries().clear();		
	}
	
	/**
	 * Set create lists of AnalyticsRequest objects for load and click events
	 * @param urlList
	 */
	protected void setBeaconLists(List<String> urlList) {
		
		// Reset beacon lists
		loadBeacons = new ArrayList<AnalyticsRequest>();
		clickBeacons = new ArrayList<AnalyticsRequest>();		
		
		// For each server URL, check if it is an analytics click
		// or load event, then add it to the correct list
		for(String url : urlList)
		{  
			AnalyticsRequest request = new AnalyticsRequest(url);
			request.buildParamsList();
			
			// Populate the beacon lists
			if(request.isClickTypeEvent()) {
				clickBeacons.add(request);
			}
			else {
				loadBeacons.add(request);
			}
		}
		
		System.out.println("Total load beacons: " + loadBeacons.size());
		System.out.println("Total click beacons: " + clickBeacons.size());
	}	
	
	/**
	 * Set the global loadBeacons and beacon variables
	 */
	protected void setClickBeacon() {
		setHarUrlList(proxy);
		setBeaconLists(harUrlList);
		setBeacon(getLastReq(clickBeacons));
	}
	
	/**
	 * Set the global clickBeacons and beacon variables
	 */
	protected void setLoadBeacon() {
		setHarUrlList(proxy);
		setBeaconLists(harUrlList);
		setBeacon(getLastReq(loadBeacons));
	}
	
	/**
	 * Utility function to get the last element in a list of AnalyticsRequest objects
	 * @param requests
	 * @return the last AnalyticsRequest object
	 */
	private static AnalyticsRequest getLastReq(List<AnalyticsRequest> requests) {
		AnalyticsRequest request = requests.get(requests.size() - 1);
		return request;
	}
	
	
	/*********************************
	 * Section - Common test methods *
	 *********************************/
	
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
		if(beacon.getLinkName().equalsIgnoreCase(name)) {
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
		for(String event : beacon.getEvents()) {
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
		String blob = beacon.getProps().toString();
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
		String blob = beacon.getEvars().toString();
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
	 * Utility function to check for a user-specified analytics variable and value
	 * @param name
	 * @param value
	 * @return bool
	 */
	public boolean hasVariable(String name, String value) {
		// TODO: fill this out
		return false;
	}	
	
}
