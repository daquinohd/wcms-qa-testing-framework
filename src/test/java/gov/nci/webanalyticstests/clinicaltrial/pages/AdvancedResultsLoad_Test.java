package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class AdvancedResultsLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String TESTDATA_SHEET_NAME = "AdvancedResultsLoad";

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
			Assert.fail("Error loading Advanced CTS results url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test CTS Advanced Results page load event
	@Test(dataProvider = "AdvancedResultsLoadData", groups = { "Analytics" })
	public void testCtsAdvancedResultsPageLoad(String params, String searchType, String fieldsUsed, String cancerInfo,
			String location, String treatment, String custom) {
		System.out.println("Test CTS Advanced Results page load event (" + searchType + "): ");
		setupTestMethod(params);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, analyticsMetaData);
			Assert.assertEquals(beacon.props.get(15), fieldsUsed);
			Assert.assertEquals(beacon.props.get(17), cancerInfo);
			Assert.assertEquals(beacon.props.get(18), location);
			Assert.assertEquals(beacon.props.get(19), treatment);
			Assert.assertEquals(beacon.props.get(20), custom);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data Providers ==================== //

	/**
	 * Get an iterator data object with path and content type Strings, filtered by a
	 * given value and column.
	 * 
	 * @return expected strings
	 */
	@DataProvider(name = "AdvancedResultsLoadData")
	private Iterator<Object[]> getAdvancedResultsPageLoadData() {
		String[] columnsToReturn = { "Params", "SearchType", "CtsFieldsUsed", "CtsCancerInfo", "CtsLocation",
				"CtsDrugTreatment", "CtsTrialCustom" };
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
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_advanced");
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Advanced");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(15), beacon.props.get(15));
		Assert.assertEquals(beacon.eVars.get(17), beacon.props.get(17));
		Assert.assertEquals(beacon.eVars.get(18), beacon.props.get(18));
		Assert.assertEquals(beacon.eVars.get(19), beacon.props.get(19));
		Assert.assertEquals(beacon.eVars.get(20), beacon.props.get(20));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}