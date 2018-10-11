package gov.nci.webanalyticstests;

import java.util.ArrayList;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;

import gov.nci.webanalytics.Beacon;

public class AnalyticsTestClickBase extends AnalyticsTestBase {

	// ==================== Required get() methods ==================== //

	/**
	 * Get the 'click' beacon for testing. If no index is specified, get the last
	 * item in the list.
	 * 
	 * @return Beacon
	 */
	protected Beacon getBeacon(int... index) {
		List<String> harList = getHarUrlList(proxy);
		List<Beacon> beaconList = getBeaconList(harList);

		// Optional index value... if index is present, get the beacon at that index.
		// Otherwise, get the last item in the list.
		int i = index.length > 0 ? index[0] : (beaconList.size() - 1);
		Beacon beacon = getBeaconAtIndex(beaconList, i);

		if (debug) {
			System.out.println("Click beacon to test: ");
			System.out.println(beacon.url + "\n");
		}
		
		return beacon;
	}

	/**
	 * Get a Beacon object at a given index.
	 * 
	 * @param requests
	 *            list of request objects
	 * @param index
	 * @return analytics request object at that position
	 */
	private Beacon getBeaconAtIndex(List<Beacon> requests, int index) {
		try {
			return requests.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Create a list of click Beacon objects. clickBeacons -> a list of analytics
	 * request URLs fired off by an analytics load event, ie s.t()
	 * 
	 * @param urlList
	 * @return collection of Beacon objects
	 */
	protected List<Beacon> getBeaconList(List<String> urlList) {

		List<Beacon> clickBeacons = new ArrayList<Beacon>();
		int loadBeacons = 0;

		// For each server URL, check if it is an analytics click
		// or load event, then add it to the correct list
		for (String url : urlList) {
			// Populate the beacon lists
			Beacon beacon = new Beacon(url);
			if (beacon.isClickTypeEvent()) {
				clickBeacons.add(beacon);
			} else {
				++loadBeacons;
			}
		}

		// Debug analytics beacon counts
		if (debug) {
			System.out.println("Total analytics requests: " + urlList.size() + " (load: " + loadBeacons + ", click: "
					+ clickBeacons.size() + ")");
		}
		
		return clickBeacons;
	}

	// ==================== Common assertions and exceptions ==================== //

	/**
	 * Shared Assert() calls for all click tracking beacons.
	 * 
	 * @param beacon
	 */
	protected void doCommonClickAssertions(Beacon beacon) {

		// Suites
		String currUrl = driver.getCurrentUrl();
		Assert.assertTrue(beacon.hasSuite("nciglobal", currUrl), "Common missing global suite");

		// Props
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");

		// Evars
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
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

		System.out.println("Exception: " + msg + "\n");
		logger.log(LogStatus.FAIL, "Failure: " + msg);
		Assert.fail("Click event exception in " + testMethod + "(): " + msg);
	}

}