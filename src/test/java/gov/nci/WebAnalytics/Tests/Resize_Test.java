package gov.nci.WebAnalytics.Tests;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.AnalyticsClick;
import gov.nci.WebAnalytics.Resize;

public class Resize_Test extends AnalyticsTestBase {
	
	// Analytics objects
	public Resize resize;

	/// Load and click events have been captured
	@Test(groups = { "Analytics" })
	public void verifyResizeHar() {
		
		resize = new Resize(driver);
		resize.resizeBrowser();
		List<String> harList = getHarUrlList(proxy);
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

		List<String> harList = getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);
		
		for(AnalyticsClick beacon : clickBeacons) {
			if(beacon.linkName.toLowerCase().contains("resize")) {
				Assert.assertTrue(beacon.events[0].contains("event7"));
			}
		}
		logger.log(LogStatus.PASS, "Resize values are correct.");
	}
	
}
