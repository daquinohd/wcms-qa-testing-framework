package gov.nci.webanalyticstests.click;

import java.util.ArrayList;
import java.util.List;

import gov.nci.webanalytics.adobe.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class AnalyticsTestClickBase extends AnalyticsTestBase {

	/**
	 * Get the 'click' beacon for testing.
	 * @return Beacon
	 */
	protected Beacon getBeacon() {
		List<String> harList = getHarUrlList(proxy);
		List<Beacon> beaconList = getBeaconList(harList);
		Beacon beacon = getLastReq(beaconList);
		System.out.println("Click beacon to test: ");
		System.out.println(beacon.url  + "\n");
		return beacon;
	}
	
	/**
	 * Create a list of click Beacon objects. 
	 * clickBeacons -> a list of analytics request URLs fired off by an analytics load event, ie s.t()
	 * @param urlList
	 */
	protected List<Beacon> getBeaconList(List<String> urlList) {
		
		List<Beacon> clickBeacons = new ArrayList<Beacon>();
		int loadBeacons = 0;
		
		// For each server URL, check if it is an analytics click
		// or load event, then add it to the correct list
		for(String url : urlList)
		{
			// Populate the beacon lists
			Beacon beacon = new Beacon(url);
			if(beacon.isClickTypeEvent()) {
				clickBeacons.add(beacon);
			}
			else {
				++loadBeacons;
			}
		}

	    // Debug analytics beacon counts
		System.out.println("Total analytics requests: " + urlList.size()
			+ " (load: " + loadBeacons
			+ ", click: " + clickBeacons.size() + ")"
		);
		
		return clickBeacons;
	}
	
}