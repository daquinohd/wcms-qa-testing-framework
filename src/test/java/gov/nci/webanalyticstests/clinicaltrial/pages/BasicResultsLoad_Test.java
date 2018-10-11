package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class BasicResultsLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String TESTDATA_SHEET_NAME = "BasicResultsLoad";

	private String testDataFilePath;
	private AnalyticsMetaData analyticsMetaData;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTSData");
	}

	/**
	 * Navigate to page and create new AnalyticsPageLoad object.
	 * 
	 * @param queryParams
	 */
	private void setupTestMethod(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			analyticsMetaData = new AnalyticsMetaData(driver);
		} catch (Exception ex) {
			Assert.fail("Error loading Basic CTS results url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test CTS Basic Results page load event
	@Test(dataProvider = "BasicResultsLoadData", groups = { "Analytics" })
	public void testCtsBasicResultsPageLoad(String params, String searchType, String fieldsUsed, String cancerInfo,
			String location) {
		System.out.println("Fields used: " + fieldsUsed);
		setupTestMethod(params);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, analyticsMetaData);
			Assert.assertEquals(beacon.props.get(15), fieldsUsed);
			Assert.assertEquals(beacon.props.get(17), cancerInfo);
			Assert.assertEquals(beacon.props.get(18), location);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path and content type Strings, filtered by a
	 * given value and column.
	 * 
	 * @return expected strings
	 */
	@DataProvider(name = "BasicResultsLoadData")
	public Iterator<Object[]> getBasicResultsLoadData() {
		String[] columnsToReturn = { "Params", "SearchType", "CtsFieldsUsed", "CtsCancerInfo", "CtsLocation" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param analyticsMetaData
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsMetaData analyticsMetaData) {
		doCommonLoadAssertions(beacon, analyticsMetaData, PATH);
		Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_basic");
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Basic");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(15), beacon.props.get(15));
		Assert.assertEquals(beacon.eVars.get(17), beacon.props.get(17));
		Assert.assertEquals(beacon.eVars.get(18), beacon.props.get(18));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}