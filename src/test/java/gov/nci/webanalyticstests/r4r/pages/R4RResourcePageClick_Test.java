package gov.nci.webanalyticstests.r4r.pages;

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RResourcePageClick_Test extends R4RClickBase {

	private final String TESTDATA_SHEET_NAME = "R4RResourceLoad";

	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsR4RData");
	}

	// ==================== Test methods ==================== //

	/// Test R4R Pages Load event
	@Test(groups = { "Analytics" })
	public void testR4RPageViewClick() {
		driver.get(config.goHome());

		try {
			Beacon beacon = getBeacon();
			Assert.assertEquals(beacon, getBeacon());
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "R4RPageLoad")
	private Iterator<Object[]> getR4RPageLoadData() {
		String[] columnsToReturn = { "Path", "ContentType", "CustomText" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param customText
	 */
	private void doCommonClassAssertions(Beacon beacon, String customText) {
		Assert.assertTrue(beacon.hasSuite("nciglobal", driver.getCurrentUrl()), "Common missing Global Suite");
		Assert.assertEquals(beacon.channels, "Research", "Channel");
		Assert.assertTrue(beacon.hasEvent(1), "Missing event1");
		Assert.assertTrue(beacon.hasEvent(47), "Missing event47");
		Assert.assertEquals(beacon.props.get(6), "Resources for Researchers", "prop6");
		Assert.assertEquals(beacon.props.get(10), "Resources for Researchers: " + customText, "prop10");
		Assert.assertEquals(beacon.props.get(44), "RandD Resources", "prop44");
		Assert.assertEquals(beacon.eVars.get(44), beacon.props.get(44), "eVar44");
	}
}
