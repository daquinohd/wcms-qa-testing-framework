package gov.nci.webanalyticstests.clinicaltrial.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class DynamicListingLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "DynamicListingPage";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTSData");
	}

	/**
	 * Go to the listing page and create a new create a page object.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			analyticsMetaData = new AnalyticsMetaData(driver);
		} catch (Exception ex) {
			Assert.fail("Error loading listing page path: " + path);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Dynamic Listing Page load event
	@Test(dataProvider = "DiseaseListingPage", groups = { "Analytics" })
	public void testDiseaseListingPageLoad(String path, String listingType, String filterInfo) {
		System.out.println("Test '" + listingType + "' Dynamic Listing Page load event:");
		setupTestMethod(path);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, path, filterInfo);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Dynamic Listing Page load event
	@Test(dataProvider = "InterventionListingPage", groups = { "Analytics" })
	public void testInterventionListingPageLoad(String path, String listingType, String filterInfo) {
		System.out.println("Test '" + listingType + "' Dynamic Listing Page load event:");
		setupTestMethod(path);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, path, filterInfo);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Manual Trial Listing Page load event
	@Test(dataProvider = "ManualListingPage", groups = { "Analytics" })
	public void testManualListingPageLoad(String path, String listingType, String filterInfo) {
		System.out.println("Test '" + listingType + "' Trial Listing Page load event:");
		setupTestMethod(path);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, path, filterInfo);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DiseaseListingPage")
	private Iterator<Object[]> getDynamicListingDiseaseData() {
		String[] columnsToReturn = { "Path", "ListingType", "ExpectedFilterInfo" };
		String[] filterInfo = { "ListingType", "Disease" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "InterventionListingPage")
	private Iterator<Object[]> getDynamicListingInterventionData() {
		String[] columnsToReturn = { "Path", "ListingType", "ExpectedFilterInfo" };
		String[] filterInfo = { "ListingType", "Intervention" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "ManualListingPage")
	private Iterator<Object[]> getManualListingData() {
		String[] columnsToReturn = { "Path", "ListingType", "ExpectedFilterInfo" };
		String[] filterInfo = { "ListingType", "Manual" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn, filterInfo);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for DynamicListPage_Test
	 * 
	 * @param beacon
	 * @param path
	 * @param filter
	 */
	private void doCommonClassAssertions(Beacon beacon, String path, String filter) {
		String itemsPerPageRegex = "^\\d{1,4}$";
		String itemCountXpath = "//span[@data-basiccts-searchparam='n']";
		String itemsTotal = analyticsMetaData.getElementTextFromXpath(itemCountXpath);

		doCommonLoadAssertions(beacon, analyticsMetaData, path);
		Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_custom");
		Assert.assertEquals(beacon.props.get(20), filter + itemsTotal);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
		Assert.assertTrue(beacon.eVars.get(10).matches(itemsPerPageRegex), "eVar10 incorrect");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(20), beacon.props.get(20));
		Assert.assertEquals(beacon.eVars.get(47), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}