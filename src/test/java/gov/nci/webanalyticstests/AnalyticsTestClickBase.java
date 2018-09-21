package gov.nci.webanalyticstests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import gov.nci.webanalytics.Beacon;

public class AnalyticsTestClickBase extends AnalyticsTestBase {

	/**
	 * Get the 'click' beacon for testing.
	 * 
	 * @return Beacon
	 */
	protected Beacon getBeacon() {
		List<String> harList = getHarUrlList(proxy);
		List<Beacon> beaconList = getBeaconList(harList);
		Beacon beacon = getLastReq(beaconList);
		System.out.println("Click beacon to test: ");
		System.out.println(beacon.url + "\n");
		return beacon;
	}

	/**
	 * Create a list of click Beacon objects. clickBeacons -> a list of analytics
	 * request URLs fired off by an analytics load event, ie s.t()
	 * 
	 * @param urlList
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
		System.out.println("Total analytics requests: " + urlList.size() + " (load: " + loadBeacons + ", click: "
				+ clickBeacons.size() + ")");

		return clickBeacons;
	}

	/**
	 * Shared Assert() calls for all click tracking beacons.
	 * 
	 * @param beacon
	 */
	protected void doCommonClickAssertions(Beacon beacon) {

		// Suites
		String currUrl = driver.getCurrentUrl();
		Assert.assertTrue(beacon.hasSuite("nciglobal", currUrl));

		// Props
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");

		// Evars
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}