package gov.nci.webanalyticstests.load;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
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
		// TODO: Beef up these assertions:
		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertTrue(beacon.channels.length() > 0);

		// TODO: Start replacing assertTrue() with assertEqual()
		Assert.assertEquals(beacon.props.get(1), driver.getCurrentUrl());
		Assert.assertEquals(beacon.props.get(3), path);
		Assert.assertEquals(beacon.props.get(6), analyticsPageLoad.getMetaTitle());
		Assert.assertEquals(beacon.props.get(8), analyticsPageLoad.getLanguageName());
		Assert.assertEquals(beacon.props.get(10), analyticsPageLoad.getPageTitle());
		Assert.assertEquals(beacon.props.get(44), analyticsPageLoad.getMetaIsPartOf());
		Assert.assertEquals(beacon.eVars.get(2), analyticsPageLoad.getLanguageName());
		Assert.assertEquals(beacon.eVars.get(44), analyticsPageLoad.getMetaIsPartOf());
		
		// TODO: For anything that can't be assertEqual (like dates), build out regex logic for assertTrue()		
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
	}
	
	/**
	 * Get an iterator data object with path and content type Strings.
	 * @param testDataFilePath
	 * @param sheetName
	 * @return Iterator<Object[]> myObjects
	 */
	public Iterator<Object[]> getPathContentTypeData(String testDataFilePath, String sheetName) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String path = excelReader.getCellData(sheetName, "Path", rowNum);
			String contentType = excelReader.getCellData(sheetName, "ContentType", rowNum);
			Object ob[] = { path, contentType };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

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
	
}
