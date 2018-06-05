package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.PageLoad;

public class PageLoad_Test extends AnalyticsTestBase {

	// Analytics objects
	public PageLoad pageLoad;

	/// Load and click events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void verifyLoadHar() {
		pageLoad = new PageLoad(driver);
		pageLoad.doPageLoadActions();
		
		harList = getHarUrlList(proxy);
		loadBeacons = PageLoad.getLoadBeacons(harList);
				
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		
		logger.log(LogStatus.PASS, "Load and click events have been captured.");				
	}	

	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testLoadEvents() {
		pageLoad = new PageLoad(driver);
		pageLoad.doPageLoadActions();
		
		harList = getHarUrlList(proxy);
		loadBeacons = PageLoad.getLoadBeacons(harList);
		
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		
		for(AnalyticsBase beacon : loadBeacons) {
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
		harList = getHarUrlList(proxy);
		loadBeacons = PageLoad.getLoadBeacons(harList);		
		
		AnalyticsBase firstLoadBeacon = loadBeacons.get(0);

		// for each beacon ... logic goes here
		Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage") || firstLoadBeacon.channel.contains("Research"));
		Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstLoadBeacon.events[0].contains("1"));
	}
	
}
