package gov.nci.webanalyticstests.load;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class AnalyticsTestLoadBase extends AnalyticsTestBase {
	
	// TODO: refactor has..() methods
	// TODO: fix hasEvent() arg
	// TODO: create generic param test
	
	/**
	 * Get the 'load' beacon for testing.
	 * @return Beacon
	 */
	protected Beacon getBeacon() {
		List<String> harList = getHarUrlList(proxy);
		List<Beacon> beaconList = getBeaconList(harList);
		Beacon beacon = getLastReq(beaconList);
		System.out.println("Load beacon to test: ");
		System.out.println(beacon.url  + "\n");
		return beacon;
	}
	
	/**
	 * Create a list of load Beacon objects. 
	 * loadBeacons -> a list of analytics request URLs fired off by an analytics load event, ie s.tl()
	 * @param urlList
	 */
	protected List<Beacon> getBeaconList(List<String> urlList) {
		
		List<Beacon> loadBeacons = new ArrayList<Beacon>();
		int clickBeacons = 0;
		
		// For each server URL, check if it is an analytics click
		// or load event, then add it to the correct list
		for(String url : urlList)
		{
			// Populate the beacon lists
			Beacon beacon = new Beacon(url);
			if(!beacon.isClickTypeEvent()) {
				loadBeacons.add(beacon);
			}
			else {
				++clickBeacons;
			}
		}

	    // Debug analytics beacon counts
		System.out.println("Total analytics requests: " + urlList.size()
			+ " (load: " + loadBeacons.size() 
			+ ", click: " + clickBeacons + ")"
		);
		
		return loadBeacons;		
	}
	
	/**
	 * Shared Assert() calls for all pageLoad tracking beacons.
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param path
	 */
	protected void DoCommonLoadAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad, String path) {
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(1, driver.getCurrentUrl()));
		Assert.assertTrue(beacon.hasProp(3, path));
		Assert.assertTrue(beacon.hasProp(6, analyticsPageLoad.getMetaTitle()));
		Assert.assertTrue(beacon.hasProp(8, analyticsPageLoad.getLanguageName()));
		Assert.assertTrue(beacon.hasProp(10, analyticsPageLoad.getPageTitle()));
		Assert.assertTrue(beacon.hasProp(44, analyticsPageLoad.getMetaIsPartOf()));
		Assert.assertTrue(beacon.haseVar(2, analyticsPageLoad.getLanguageName()));
		Assert.assertTrue(beacon.haseVar(44, analyticsPageLoad.getMetaIsPartOf()));
	}
	

	/// Load events have been captured
//	@Test(groups = { "Analytics" })
//	public void testHarAndBeacons() {
//		analyticsPageLoad.gotoMultiplePages();
//		beacon = getLoadBeacon();
//		Assert.assertTrue(harUrlList.size() > 0);
//		Assert.assertTrue(loadBeacons.size() > 0);
//		Assert.assertTrue(beacon != null);
//		logger.log(LogStatus.PASS, "Load events have been captured.");
//	}
//	
//	
//	/// Blog post pageload returns expected values
//	public void testBlogPostLoad() {
//		analyticsPageLoad.gotoBlogPostPage();
//		beacon = getLoadBeacon();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		Assert.assertTrue(beacon.hasEvent(53));
//		Assert.assertTrue(beacon.hasProp(6, "Addressing Cancer Drug Costs and Value"));
//		Assert.assertTrue(beacon.hasProp(44, "CancerCurrents"));
//		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/news-events/cancer-currents-blog/2018/presidents-cancer-panel-drug-prices"));
//		// hasHier()
//		logger.log(LogStatus.PASS, "Blog post pageload values are correct.");		
//	}
//	
//	/// Blog series pageload returns expected values
//	public void testBlogSeriesLoad() {
//		analyticsPageLoad.gotoBlogSeriesPage();
//		beacon = getLoadBeacon();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		Assert.assertTrue(beacon.hasProp(44, "CancerCurrents"));
//		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/news-events/cancer-currents-blog"));
//		Assert.assertTrue(beacon.haseVar(44, "CancerCurrents"));
//		logger.log(LogStatus.PASS, "Blog series pageload values are correct.");
//	}
//	
//	/// CTHP patient pageload returns expected values
//	public void testCTHPPatientLoad() {
//		analyticsPageLoad.gotoCTHPPatient();
//		beacon = getLoadBeacon(); 
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		Assert.assertTrue(beacon.hasProp(8, "english"));
//		Assert.assertTrue(beacon.hasProp(6, "Bladder cancer"));
//		// "\u2014" is an emdash
//		Assert.assertTrue(beacon.hasProp(10, "Bladder Cancer\u2014Patient Version - National Cancer Institute"));
//		Assert.assertTrue(beacon.hasProp(44, "Cancer Types Landing Page"));
//		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/types/bladder"));
//		Assert.assertTrue(beacon.haseVar(44, "Cancer Types Landing Page"));
//		logger.log(LogStatus.PASS, "CTHP Patient pageload values are correct.");
//	}
//	
//	/// CTHP HP pageload returns expected values
//	public void testCTHPHPLoad() {
//		analyticsPageLoad.gotoCTHPHP();
//		beacon = getLoadBeacon();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		Assert.assertTrue(beacon.hasProp(6, "Breast cancer"));
//		// "\u2014" is an emdash
//		Assert.assertTrue(beacon.hasProp(10, "Breast Cancer\u2014Health Professional Version - National Cancer Institute"));
//		Assert.assertTrue(beacon.hasProp(44, "Cancer Types Landing Page"));
//		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/types/breast/hp"));
//		logger.log(LogStatus.PASS, "CTHP HP pageload values are correct.");
//	}
//	
//	
//	/// PDQ pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testPDQPageLoad() {
//		analyticsPageLoad.gotoPDQPage();
//		beacon = getLoadBeacon();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		Assert.assertTrue(beacon.hasProp(3, "/types/breast"));
//		Assert.assertTrue(beacon.hasProp(6, "Breast Cancer Prevention"));
//		Assert.assertTrue(beacon.hasProp(7, "patient"));
//		// "\u2014" is an emdash
//		// "\u00ae" is the registered mark.
//		Assert.assertTrue(beacon.hasProp(10, "Breast Cancer Prevention (PDQ\u00ae)\u2014Patient Version - National Cancer Institute"));
//		Assert.assertTrue(beacon.hasProp(44, "Cancer Types Landing Page"));
//		//Assert.assertTrue(beacon.hasProp(65, "2"));
//		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/types/breast/patient/breast-prevention-pdq"));
//		Assert.assertTrue(beacon.haseVar(44, "Cancer Types Landing Page"));
//		logger.log(LogStatus.PASS, "PDQ page load values are correct.");
//	}
//	
//
//	/// Appmodule pageload returns expected values
//	//@Test(groups = { "Analytics" })
//	public void testAppModulePageLoad1() {
//		analyticsPageLoad.gotoAppModulePage();
//		beacon = getLoadBeacon();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		Assert.assertTrue(beacon.hasProp(3, "/publications/dictionaries/cancer-terms"));
//		Assert.assertTrue(beacon.hasProp(6, "NCI Dictionary of Cancer Terms"));
//		Assert.assertTrue(beacon.hasProp(8, "English"));
//		Assert.assertTrue(beacon.hasProp(44, "Dictionary of Cancer Terms"));
//		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/publications/dictionaries/cancer-terms"));
//		Assert.assertTrue(beacon.haseVar(44, "Dictionary of Cancer Terms"));
//		logger.log(LogStatus.PASS, "Appmodule (dictionary) page load values are correct.");		
//	}
//
//	/// Appmodule pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testAppModulePageLoad2() {
//		pageLoad.gotoAppModulePage();
//		setLoadBeacons();
//	}		
//		
//	/// CTHP HP pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testAdvSearchPageLoad() {
//		pageLoad.gotoAdvSearchPage();
//		setLoadBeacons();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		/** more **/
//	}
//	
//	@Test(groups = { "Analytics" })
//	public void testBasicSearchPageLoad() {
//		pageLoad.gotoBasicSearchPage();
//		setLoadBeacons();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		/** more **/
//	}		
//	
//	@Test(groups = { "Analytics" })
//	public void testCTResultsPageLoad() {
//		pageLoad.gotoResultsPage();
//		setLoadBeacons();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		/** more **/
//	}			
//
//	@Test(groups = { "Analytics" })
//	public void testCTViewPageLoad() {
//		Assert.assertTrue((2 + 2) == 4);
//		/** more **/
//	}
	
}
