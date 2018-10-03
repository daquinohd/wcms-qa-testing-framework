package gov.nci.webanalyticstests.clinicaltrial.pages;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
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
	@Test(dataProvider = "DynamicListingPage", groups = { "Analytics" })
	public void testInterventionListingPageLoad(String path, String listingType, String filterInfo) {
		System.out.println("Test '" + listingType + "' Dynamic Listing Page load event:");
		setupTestMethod(path);

		try {
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, path, filterInfo);
			logger.log(LogStatus.PASS, "Test '" + listingType + "' Dynamic Listing Page load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
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
			logger.log(LogStatus.PASS, "Test '" + listingType + "' Trial Listing Page load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DynamicListingPage")
	private Iterator<Object[]> getDynamicListingPageLoadData() {
		ArrayList<Object[]> dynamicObj = new ArrayList<Object[]>();
		dynamicObj.addAll(getFilteredDataForDlp("ListingType", "Disease"));
		dynamicObj.addAll(getFilteredDataForDlp("ListingType", "Intervention"));
		return dynamicObj.iterator();
	}

	@DataProvider(name = "ManualListingPage")
	private Iterator<Object[]> getManualListingPageLoadData() {
		ArrayList<Object[]> manualObj = getFilteredDataForDlp("ListingType", "Manual");
		return manualObj.iterator();
	}

	/**
	 * Get a data object with path and content type Strings, filtered by a given
	 * value and column.
	 * 
	 * @param filterColumn
	 * @param myFilter
	 * @return
	 */
	private ArrayList<Object[]> getFilteredDataForDlp(String filterColumn, String myFilter) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String type = excelReader.getCellData(TESTDATA_SHEET_NAME, "ListingType", rowNum);
			String expectedFilterInfo = excelReader.getCellData(TESTDATA_SHEET_NAME, "ExpectedFilterInfo", rowNum);
			String filter = excelReader.getCellData(TESTDATA_SHEET_NAME, filterColumn, rowNum);
			if (filter.equalsIgnoreCase(myFilter)) {
				Object ob[] = { path, type, expectedFilterInfo };
				myObjects.add(ob);
			}
		}
		return myObjects;
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
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_custom");
		Assert.assertEquals(beacon.props.get(20), filter + itemsTotal);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
		Assert.assertTrue(beacon.eVars.get(10).matches(itemsPerPageRegex));
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(20), beacon.props.get(20));
		Assert.assertEquals(beacon.eVars.get(47), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}