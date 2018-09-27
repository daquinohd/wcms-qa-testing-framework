package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class BasicResultsLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String TESTDATA_SHEET_NAME = "BasicResultsLoad";

	private String testDataFilePath;
	private AnalyticsPageLoad analyticsPageLoad;

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
			analyticsPageLoad = new AnalyticsPageLoad(driver);
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
		System.out.println("Test CTS Basic Results page load event (" + searchType + "): ");
		setupTestMethod(params);

		try {
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, analyticsPageLoad);
			Assert.assertEquals(beacon.props.get(15), fieldsUsed);
			Assert.assertEquals(beacon.props.get(17), cancerInfo);
			Assert.assertEquals(beacon.props.get(18), location);
			logger.log(LogStatus.PASS, "Test CTS Basic Results page load event (" + searchType + ") passed");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
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
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String params = excelReader.getCellData(TESTDATA_SHEET_NAME, "Params", rowNum);
			String searchType = excelReader.getCellData(TESTDATA_SHEET_NAME, "SearchType", rowNum);
			String fieldsUsed = excelReader.getCellData(TESTDATA_SHEET_NAME, "CtsFieldsUsed", rowNum);
			String cancerInfo = excelReader.getCellData(TESTDATA_SHEET_NAME, "CtsCancerInfo", rowNum);
			String location = excelReader.getCellData(TESTDATA_SHEET_NAME, "CtsLocation", rowNum);
			Object ob[] = { params, searchType, fieldsUsed, cancerInfo, location };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param analyticsPageLoad
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, PATH);
		Assert.assertTrue(beacon.hasEvent(2));
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