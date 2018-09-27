package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class TrialDetailViewLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/v";
	private final String TESTDATA_SHEET_NAME = "TrialDetailView";

	private AnalyticsPageLoad analyticsPageLoad;
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
			analyticsPageLoad = new AnalyticsPageLoad(driver);
		} catch (Exception ex) {
			Assert.fail("Error loading CTS Trial Detail View url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test CTS Trial Detail View page load event
	@Test(dataProvider = "TrialViewLoad", groups = { "Analytics" })
	public void testCTSTrialDetailViewPageLoad(String params, String type) {
		System.out.println("Test CTS Trial Detail View page load event (" + type + "): ");
		setupTestMethod(params);

		try {
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, analyticsPageLoad, type);
			logger.log(LogStatus.PASS, "Test CTS Trial Detail View page load event (" + type + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
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
	private String getNCTID(AnalyticsPageLoad pageLoad) {
		String[] titleId = pageLoad.getMetaTitle().split("-");
		return titleId[titleId.length - 1].trim();
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param type
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad, String type) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, PATH);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(16), getNCTID(analyticsPageLoad));
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: " + type);
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}