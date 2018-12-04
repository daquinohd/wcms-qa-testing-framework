package gov.nci.webanalyticstests.r4r.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Resources4Researchers.Resources4ResearchersHome;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RHomePageClick_Test extends R4RClickBase {

	private final String ROOT_PATH = "/research/resources";

	private Resources4ResearchersHome r4rHome;

	// ==================== Setup methods ==================== //

	/**
	 * Go to R4R home and initialize Resources4ResearchersHome.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		driver.get(config.goHome() + path);
		try {
			r4rHome = new Resources4ResearchersHome(driver, logger);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test the click event that fires off after the page has loaded
	@Test(groups = { "Analytics" })
	public void testPageLoadClickEvent() {
		setupTestMethod(ROOT_PATH);

		try {
			r4rHome.waitForAnalytics(50);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.channels, "Research", "channel");
			Assert.assertTrue(beacon.hasEvent(37), "event37");
			Assert.assertEquals(beacon.linkName, "R4R Data Load", "linkName");
			Assert.assertEquals(beacon.props.get(39), "r4r_home|view", "prop39");
			Assert.assertEquals(beacon.props.get(40), "Home View", "prop40");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}

	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
	}

}
