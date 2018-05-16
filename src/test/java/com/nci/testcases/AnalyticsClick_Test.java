package com.nci.testcases;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import com.nci.Utilities.BrowserManager;
import gov.nci.WebAnalytics.AnalyticsClick;
import gov.nci.WebAnalytics.AnalyticsLoad;
import gov.nci.WebAnalytics.AnalyticsBase;

import com.relevantcodes.extentreports.LogStatus;
import net.lightbody.bmp.proxy.CaptureType;

import org.apache.http.NameValuePair;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AnalyticsClick_Test extends AnalyticsTestBase {


	
	//region browseractions
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	private void doBrowserActions() throws RuntimeException {
		navigateSite();
		resizeBrowser();
		//doSiteWideSearch();
		//doAdvancedCTSearch();
		//doBasicCTSearch();
		//useDictionary();
		//navigateError();
		//navigateRATs();		
		
		
	}
	
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
	//endregion browseractions
	
	//region tests

	/// Load and click events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void verifyHar() {
		doBrowserActions();
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
	public void testLoadEvents() {
		doBrowserActions();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
			
		for(AnalyticsLoad beacon : loadBeacons) {
			Assert.assertTrue(beacon.events[0].contains("event1"));
			Assert.assertTrue(beacon.events[1].contains("event47"));
		}
		
		logger.log(LogStatus.PASS, "Load event values are correct.");				
	}
	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testClickEvents() {
		navigateSite();
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
	
	/// Resize events match with their descriptors
	@Test(groups = { "Analytics" })
	public void testResizeEvents() {
		resizeBrowser();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);
		
		for(AnalyticsClick beacon : clickBeacons) {
			if(beacon.linkName.toLowerCase().contains("resize")) {
				Assert.assertTrue(beacon.events[0].contains("event7"));
			}
		}
		logger.log(LogStatus.PASS, "Resize values are correct.");
	}
	
	/// Temporary method to test beacon object
	@Test(groups = { "Analytics" })
	public void testObject() throws MalformedURLException {
		// For debugging purposes only...
		navigateSite();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);		
		
		AnalyticsLoad firstLoadBeacon = loadBeacons.get(0);

		// for each beacon ... logic goes here
		Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage") || firstLoadBeacon.channel.contains("Research"));
		Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstLoadBeacon.events[0].contains("1"));

	}
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testInt() {
		int j = 1;
		Assert.assertTrue(j + 1 == 2);
	}

	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testString() {
		String K = "potassium";
		Assert.assertEquals(K, "potassium");
	}
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void asdftestString() {
		String K = "potassium";
		Assert.assertEquals(K, "potassium");
	}
	
	//endregion tests
	
}
