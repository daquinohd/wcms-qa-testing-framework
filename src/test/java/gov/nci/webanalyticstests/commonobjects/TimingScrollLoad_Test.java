package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class TimingScrollLoad_Test extends AnalyticsTestLoadBase {

	private final String REGEX_PAGE_SCROLL = "^\\d{1,3}pct\\|\\d{1,3}pct\\|\\d{1,5}px\\|\\/.*$"; // 30pct|30pct|3215px|/research
	private final String REGEX_PERCENT_VIEWED = "^\\d{1,3}\\|\\d{1,3}$"; // 26|0
	private final String TESTDATA_SHEET_NAME = "TimingScroll";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCommonData");
	}

	// ==================== Test methods ==================== //

	/// Test Page Load event after no scrolling
	@Test(dataProvider = "TimingScrollLoad", groups = { "Analytics" })
	public void testNoScrollPageLoad(String path, String contentType) {
		System.out.println("Path: " + path);
		System.out.println("Content type: " + contentType);
		driver.get(config.goHome() + path);
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, analyticsMetaData, path);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Page Load event after scrolling to bottom
	@Test(groups = { "Analytics" })
	public void testFullScrollPageLoad() {
		System.out.println("Path: " + "/");
		driver.get(config.goHome());
		// scroll to bottom
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			// Beacon beacon = getBeacon();
			// doCommonClassAssertions(beacon, analyticsMetaData, "");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test engagement tracking on page load
	@Test(groups = { "Analytics" })
	public void testEngagement() {
		System.out.println("Path: " + "/");
		driver.get(config.goHome());

		try {
			// TODO
			// Click and scroll around page
			// Refresh

			// Beacon beacon = getBeacon();
			// doCommonClassAssertions(beacon, analyticsMetaData, "");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param analyticsMetaData
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsMetaData analyticsMetaData, String path) {
		doCommonLoadAssertions(beacon, analyticsMetaData, path);

		Assert.assertTrue(beacon.props.get(48).matches(REGEX_PAGE_SCROLL), "prop48 incorrect");
		Assert.assertTrue(beacon.props.get(64).matches(REGEX_PERCENT_VIEWED), "prop64 incorrect");
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "TimingScrollLoad")
	private Iterator<Object[]> getTimingAndScrollLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
