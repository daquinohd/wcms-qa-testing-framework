package gov.nci.webanalyticstests.error.common;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.error.pages.PageNotFound;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;
import gov.nci.Utilities.ExcelManager;

public class ErrorClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "ErrorPageSearch";

	private PageNotFound pageNotFound;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsErrorPgData");
	}

	// ==================== Test methods ==================== //

	// Test Error Page English sitewide search click event
	@Test(dataProvider = "SearchTerms", groups = { "Analytics" })
	public void testErrorPageSearchEn(String searchTerm) {
		System.out.println("Test Error Page English sitewide search click event (" + searchTerm + "):");
		driver.get(config.goHome() + "/PageNotFound.html");

		try {
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectEnglish();
			pageNotFound.clickSearchButton();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);

			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch");
			logger.log(LogStatus.PASS,
					"Test Error Page English sitewide search click event (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// Test Error Page Spanish sitewide search click event
	@Test(dataProvider = "SearchTerms", groups = { "Analytics" })
	public void testErrorPageSearchEs(String searchTerm) {
		System.out.println("Test Error Page Spanish sitewide search click event (" + searchTerm + "):");
		driver.get(config.goHome() + "/hsinaps-dilavni");

		try {
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectSpanish();
			pageNotFound.clickSearchButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch_spanish");
			logger.log(LogStatus.PASS,
					"Test Error Page Spanish sitewide search click event (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterable collection of test objects given a data sheet name and column
	 * 
	 * @param sheetName
	 * @param filter
	 * @return
	 */
	@DataProvider(name = "SearchTerms")
	private Iterator<Object[]> getErrorPagedSearchTerms() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String searchTerm = excelReader.getCellData(TESTDATA_SHEET_NAME, "SearchTerm", rowNum);
			Object ob[] = { searchTerm };
			myObjects.add(ob);
		}
		return myObjects.iterator();

	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param searchTerm
	 */
	private void doCommonClassAssertions(Beacon beacon, String searchTerm) {
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.linkName, "PageNotFound");
		Assert.assertEquals(beacon.channels, "Error Pages");
		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
	}

}
