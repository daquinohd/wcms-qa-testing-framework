package gov.nci.webanalyticstests.r4r.pages;

import java.util.Iterator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Resources4Researchers.Resources4ResearchersHome;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RHomePageClick_Test extends R4RClickBase {

	private final String TESTDATA_SHEET_NAME = "R4RHomeLoad";

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
			System.out.println("Path: " + path);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test the click event that fires off after the page has loaded
	@Test(dataProvider = "R4RHomePage", groups = { "Analytics" })
	public void testHomeLoadClickEvent(String path) {
		setupTestMethod(path);

		try {
			r4rHome.waitForAnalytics(50);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(37), "event37");
			Assert.assertEquals(beacon.linkName, "R4R Data Load", "linkName");
			Assert.assertEquals(beacon.props.get(39), "r4r_home|view", "prop39");
			Assert.assertEquals(beacon.props.get(40), "Home View", "prop40");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Tool Type link click event
	@Test(dataProvider = "R4RHomePage", groups = { "Analytics" })
	public void testHomeToolTypeClick(String path) {
		setupTestMethod(path);

		try {
			r4rHome.clickToolTypeLink();
			String label = r4rHome.getToolTypeFilter();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "R4R Click Event", "linkName");
			Assert.assertTrue(beacon.hasEvent(2), "event2");
			Assert.assertTrue(beacon.hasEvent(39), "event39");
			Assert.assertEquals(beacon.props.get(11), "r4r_home_filteredsearch", "prop11");
			Assert.assertEquals(beacon.props.get(14), "tooltypes:" + label, "prop14");
			Assert.assertEquals(beacon.props.get(39), "r4r_home|filteredsearch", "prop39");
			Assert.assertEquals(beacon.props.get(40), "Home View", "prop40");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "R4RHomePage")
	private Iterator<Object[]> getR4RHomeLoadData() {
		String[] rtnColumn = { "Path" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, rtnColumn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
		Assert.assertEquals(beacon.channels, "Research", "channel");
	}

}
