package gov.nci.WebAnalytics.Tests;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.AnalyticsClick;
import gov.nci.WebAnalytics.AnalyticsLoad;
import gov.nci.WebAnalytics.MegaMenu;

public class MegaMenu_Test extends AnalyticsTestBase {
	
	// Analytics objects
	public MegaMenu megaMenu;

	/// Load and click events have been captured
	@Test(groups = { "Analytics" })
	public void verifyHar() {
		megaMenu = new MegaMenu(driver);		
		megaMenu.doMegaMenuActions();
		List<String> harList = getHarUrlList(proxy);
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

		List<String> harList = getHarUrlList(proxy);
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
	
}
