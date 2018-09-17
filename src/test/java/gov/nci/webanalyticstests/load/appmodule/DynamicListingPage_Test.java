package gov.nci.webanalyticstests.load.appmodule;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class DynamicListingPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Disease Dynamic Listing Page
	 * - Intervention Dynamic Listing Page
	 * - Manually-created Dynamic Listing Page
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "DynamicListingPage";
	private final String REGEX_ITEMS_PER_PAGE = "^\\d{1,4}$";
	private final String XPATH_ITEM_TOTAL = "//span[@data-basiccts-searchparam='n']";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	@Test(dataProvider = "DiseaseListingPage", groups = { "Analytics" })
	public void testDiseaseListingPageLoad(String path, String contentType, String filterInfo) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
		
		doCommonClassAssertions(path, filterInfo);
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}

	@Test(dataProvider = "InterventionListingPage", groups = { "Analytics" })
	public void testInterventionListingPageLoad(String path, String contentType, String filterInfo) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
		
		doCommonClassAssertions(path, filterInfo);
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}
	
	@Test(dataProvider = "ManualListingPage", groups = { "Analytics" })
	public void testManualListingPageLoad(String path, String contentType, String filterInfo) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
		
		doCommonClassAssertions(path, filterInfo);
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}

	/******************** Data Providers ********************/	
	@DataProvider(name = "DiseaseListingPage")
	public Iterator<Object[]> getDiseaseListingPageLoadData() {
		return getFilteredDataForDlp("ContentType", "Disease");
	}

	@DataProvider(name = "InterventionListingPage")
	public Iterator<Object[]> getInterventionListingPageLoadData() {
		return getFilteredDataForDlp("ContentType", "Intervention");
	}

	@DataProvider(name = "ManualListingPage")
	public Iterator<Object[]> getManualListingPageLoadData() {
		return getFilteredDataForDlp("ContentType", "Manual");
	}

	/**
	 * Get an iterator data object with path and content type Strings, filtered by a given value and column.
	 * @param testDataFilePath
	 * @param sheetName
	 * @param filterColumn
	 * @param myFilter
	 * @return
	 */
	public Iterator<Object[]> getFilteredDataForDlp(String filterColumn, String myFilter) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String contentType = excelReader.getCellData(TESTDATA_SHEET_NAME, "ContentType", rowNum);
			String expectedFilterInfo = excelReader.getCellData(TESTDATA_SHEET_NAME, "ExpectedFilterInfo", rowNum);
			String filter = excelReader.getCellData(TESTDATA_SHEET_NAME, filterColumn, rowNum);
			if(filter.equalsIgnoreCase(myFilter))
			{
				Object ob[] = { path, contentType, expectedFilterInfo };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
	
	
	/**
	 * Go to the listing page and retrieve the beacon request object.
	 * @param path
	 */
	private void getDynamicListingLoadBeacon(String path) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
		} catch (Exception ex) {
			Assert.fail("Error loading listing page path: " + path);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for DynamicListPage_Test
	 * @param path
	 */
	private void doCommonClassAssertions(String path, String filterInfo) {
		String total = analyticsPageLoad.getElementTextFromXpath(XPATH_ITEM_TOTAL);
		
		doCommonLoadAssertions(beacon, analyticsPageLoad, path);
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_custom");
		Assert.assertEquals(beacon.props.get(20), filterInfo + total);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
		Assert.assertTrue(beacon.eVars.get(10).matches(REGEX_ITEMS_PER_PAGE));
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(20), beacon.props.get(20));
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_custom");
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
	
}