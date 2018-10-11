package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class TrialDetailViewLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/v";
	private final String TESTDATA_SHEET_NAME = "TrialDetailView";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTSData");
	}

	/**
	 * Go to the view page and retrieve the beacon request object.
	 * 
	 * @param queryParams
	 */
	private void setupTestMethod(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			analyticsMetaData = new AnalyticsMetaData(driver);
		} catch (Exception ex) {
			Assert.fail("Error loading CTS Trial Detail View url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test CTS Trial Detail View page load event
	@Test(dataProvider = "TrialViewLoad", groups = { "Analytics" })
	public void testCTSTrialDetailViewPageLoad(String params, String type) {
		System.out.println("Trial search type: " + type);
		setupTestMethod(params);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, analyticsMetaData, type);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "TrialViewLoad")
	private Iterator<Object[]> getTrialViewLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

	// ==================== Utility methods ==================== //

	/**
	 * Retrieve the NCT ID for a given clinical trial view page.
	 * 
	 * @param pageLoad
	 * @return
	 */
	private String getNCTID(AnalyticsMetaData pageLoad) {
		String[] titleId = pageLoad.getMetaTitle().split("-");
		return titleId[titleId.length - 1].trim();
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param analyticsMetaData
	 * @param type
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsMetaData analyticsMetaData, String type) {
		doCommonLoadAssertions(beacon, analyticsMetaData, PATH);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(16), getNCTID(analyticsMetaData));
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: " + type);
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}