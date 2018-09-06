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
		String currUrl = driver.getCurrentUrl();

		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertTrue(beacon.channels.length() > 0);
		Assert.assertEquals(beacon.props.get(1), currUrl.substring(0, Math.min(currUrl.length(), 100)));
		Assert.assertEquals(beacon.props.get(3), path);
		Assert.assertEquals(beacon.props.get(6), analyticsPageLoad.getMetaTitle());
		Assert.assertEquals(beacon.props.get(8), analyticsPageLoad.getLanguageName());
		Assert.assertEquals(beacon.props.get(10), analyticsPageLoad.getPageTitle());
		Assert.assertEquals(beacon.props.get(44), analyticsPageLoad.getMetaIsPartOf());
		Assert.assertEquals(beacon.eVars.get(2), analyticsPageLoad.getLanguageName());
		Assert.assertEquals(beacon.eVars.get(44), analyticsPageLoad.getMetaIsPartOf());
		
		// TODO: regex assertions
//		Assert.assertTrue(beacon.props.get(25).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(26).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(29).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(42).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(48).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(61).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(64).matches("some regex"));
//		Assert.assertTrue(beacon.props.get(65).matches("some regex"));
//		Assert.assertTrue(beacon.eVars.get(1).matches("some regex"));
//		Assert.assertTrue(beacon.eVars.get(5).matches("some regex"));
		
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
	
	/**
	 * Get an iterator data object with path and content type Strings, filtered by a given value.
	 * @param testDataFilePath
	 * @param sheetName
	 * @param filterColumn
	 * @param filter
	 * @return
	 */
	public Iterator<Object[]> getFilteredPathContentTypeData(String testDataFilePath, String sheetName, String filterColumn, String myFilter) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String path = excelReader.getCellData(sheetName, "Path", rowNum);
			String contentType = excelReader.getCellData(sheetName, "ContentType", rowNum);
			String filter = excelReader.getCellData(sheetName, filterColumn, rowNum);
			if(filter.equalsIgnoreCase(myFilter))
			{
				Object ob[] = { path, contentType };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}	
}
