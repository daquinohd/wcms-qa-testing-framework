package gov.nci.webanalyticstests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;

public class AnalyticsTestLoadBase extends AnalyticsTestBase {

	private final String REGEX_BROWSER_SIZE = "^(Extra wide|Desktop|Tablet|Mobile)$";
	private final String REGEX_MMDDYY = "^(0[1-9]|1[012])[- \\/.](0[1-9]|[12][0-9]|3[01])[- \\/.](19|20)\\d\\d$"; // 01/01/2018
	private final String REGEX_PAGELOAD_TIME = "^\\d{1,4}$";
	private final String REGEX_TIME_PARTING = "^\\d{1,2}:\\d{2} (AM|PM)\\|[a-zA-z]+day$"; // 2:35 PM|Tuesday
	private final String REGEX_TIMESTAMP_PIPE = "^\\d{4}\\|\\d{1,2}\\|\\d{1,2}\\|\\d{1,2}$"; // 05/06/2018

	// ==================== Required get() methods ==================== //

	/**
	 * Get the 'load' beacon for testing.
	 * 
	 * @return Beacon object
	 */
	protected Beacon getBeacon(int... index) {
		List<String> harList = getHarUrlList(proxy);
		List<Beacon> beaconList = getBeaconList(harList);
		Beacon beacon = beaconList.get(beaconList.size() - 1);

		if (debug) {
			System.out.println("Load beacon to test: ");
			System.out.println(beacon.url + "\n");
		}

		return beacon;
	}

	/**
	 * Create a list of load Beacon objects. loadBeacons -> a list of analytics
	 * request URLs fired off by an analytics load event, ie s.tl()
	 * 
	 * @param urlList
	 * @return collection of Beacon objects
	 */
	protected List<Beacon> getBeaconList(List<String> urlList) {

		List<Beacon> loadBeacons = new ArrayList<Beacon>();
		int clickBeacons = 0;

		// For each server URL, check if it is an analytics click
		// or load event, then add it to the correct list
		for (String url : urlList) {
			// Populate the beacon lists
			Beacon beacon = new Beacon(url);
			if (!beacon.isClickTypeEvent()) {
				loadBeacons.add(beacon);
			} else {
				++clickBeacons;
			}
		}

		// Debug analytics beacon counts
		if (debug) {
			System.out.println("Total analytics requests: " + urlList.size() + " (load: " + loadBeacons.size()
					+ ", click: " + clickBeacons + ")");
		}

		return loadBeacons;
	}

	// ==================== Data methods ==================== //

	/**
	 * Get an iterator data object with path and content type Strings.
	 * 
	 * @param testDataFilePath
	 * @param sheetName
	 * @return Iterator<Object[]> myObjects
	 */
	public Iterator<Object[]> getPathContentTypeData(String testDataFilePath, String sheetName) {
		String[] columnsToReturn = { "Path", "ContentType" };
		return getSpreadsheetData(testDataFilePath, sheetName, columnsToReturn);
	}

	// ==================== Utility methods ==================== //

	/**
	 * Recreate the "Hierarchy 1" variable from a URL.
	 * 
	 * @param myUrl
	 * @return formatted hier1 string
	 */
	private String buildHier1(String myUrl) {
		try {
			URL url = new URL(myUrl);
			String hier = url.getHost() + url.getPath();
			if (hier.charAt(hier.length() - 1) == '/') {
				hier = hier.substring(0, hier.length() - 1);
			}
			return hier.replaceAll("/", "|");
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			return "";
		}
	}

	// ==================== Common assertions and exceptions ==================== //

	/**
	 * Shared Assert() calls for all pageLoad tracking beacons.
	 * 
	 * @param beacon
	 * @param analyticsMetaData
	 * @param path
	 */
	protected void doCommonLoadAssertions(Beacon beacon, AnalyticsMetaData analyticsMetaData, String path) {

		String currUrl = driver.getCurrentUrl();
		String currUrlMax = currUrl.substring(0, Math.min(currUrl.length(), 100));

		// Common Suites
		Assert.assertTrue(beacon.hasSuite("nciglobal", currUrl), "Common missing Global Suite");

		// Common events
		Assert.assertTrue(beacon.hasEvent(1), "Common missing event1");
		Assert.assertTrue(beacon.hasEvent(47), "Common missing event47");

		// Props
		Assert.assertEquals(beacon.props.get(1), currUrlMax);
		Assert.assertEquals(beacon.props.get(3), path.toLowerCase());
		Assert.assertEquals(beacon.props.get(6), analyticsMetaData.getMetaTitle());
		Assert.assertEquals(beacon.props.get(8), analyticsMetaData.getLanguageName());
		Assert.assertEquals(beacon.props.get(10), analyticsMetaData.getPageTitle());
		Assert.assertTrue(beacon.props.get(25).matches(REGEX_MMDDYY), "Common invalid date");
		Assert.assertTrue(beacon.props.get(26).matches(REGEX_TIMESTAMP_PIPE), "Common invalid timestamp");
		Assert.assertTrue(beacon.props.get(29).matches(REGEX_TIME_PARTING), "Common invalid time parting value");
		Assert.assertEquals(beacon.props.get(42), "Normal");
		Assert.assertEquals(beacon.props.get(44), analyticsMetaData.getMetaIsPartOf());
		Assert.assertTrue(beacon.props.get(65).matches(REGEX_PAGELOAD_TIME), "Common invalid pageload time value");

		// Evars
		Assert.assertTrue(beacon.eVars.get(1).contains("www.cancer.gov"), "Common pageName error");
		Assert.assertEquals(beacon.eVars.get(2), analyticsMetaData.getLanguageName());
		Assert.assertTrue(beacon.eVars.get(5).matches(REGEX_BROWSER_SIZE), "Common invalid browser width");
		Assert.assertEquals(beacon.eVars.get(44), analyticsMetaData.getMetaIsPartOf());

		// HIer
		Assert.assertEquals(beacon.hiers.get(1), buildHier1(currUrl));
	}

	/**
	 * Common method to log any non-assert exceptions in test methods.
	 * 
	 * @param Object
	 *            representing the test class
	 * @param Exception
	 */
	protected void handleTestErrors(Object obj, Exception ex) {
		String testMethod = obj.getClass().getEnclosingMethod().getName();
		String msg = ex.toString();
		msg = (msg.length() > 255) ? msg.substring(0, 255) : msg;

		System.out.println(" Exception: " + msg);
		logger.log(LogStatus.FAIL, "Exception => " + msg);
		Assert.fail("Load event exception in " + testMethod + "(): " + msg);
	}

}
