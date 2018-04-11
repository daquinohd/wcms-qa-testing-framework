package com.nci.testcases;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

import com.nci.Utilities.BrowserManager;
import com.relevantcodes.extentreports.LogStatus;
import gov.nci.WebAnalytics.AnalyticsLoad;

public class Analytics_Test extends BaseClass {

	// WebDriver driver;
	AnalyticsLoad analytics;

	// HAR data object
	/** A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring 
	 * tools to export collected data. 
	 * BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture 
	 * HTTP content, and export performance data as a HAR file. */
	Har har;

	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("HomePage");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		analytics = new AnalyticsLoad(driver);
		setupProxy(driver);
		System.out.println("Analytics setup done");
	}

	/**
	 * Configure BrowserMob Proxy for Selenium
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	public void setupProxy(WebDriver driver) throws RuntimeException {
	    // start the proxy
	    BrowserMobProxy proxy = new BrowserMobProxyServer();
	    proxy.start(0);

	    // get the Selenium proxy object
	    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

	    // configure it as a desired capability
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

	    // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

	    // create a new HAR with the label "cancer.gov"
	    proxy.newHar("cancer.gov");

	    // Open proxy page
	    driver.get(config.getPageURL("HomePage"));

	    // get the HAR data
	    har = proxy.getHar();
		System.out.println("BMP proxy setup done");	    
	}
	
	/// Check for NCIAnalytics in HTML
	@Test(groups = { "Smoke" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = analytics.getSitewideSearchWAFunction();
		//System.out.println("onsubmit attribute: " + sAccountBlob);
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoad.NCI_FUNCTIONS_NAME));
		logger.log(LogStatus.PASS, "NCIAnalytics attribute is present on search form.");
	}

}
