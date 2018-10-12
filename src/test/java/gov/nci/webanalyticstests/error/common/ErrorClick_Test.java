package gov.nci.webanalyticstests.error.common;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.error.pages.PageNotFound;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

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
		System.out.println("Search term: " + searchTerm);
		driver.get(config.goHome() + "/PageNotFound.html");

		try {
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectEnglish();
			pageNotFound.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test Error Page Spanish sitewide search click event
	@Test(dataProvider = "SearchTerms", groups = { "Analytics" })
	public void testErrorPageSearchEs(String searchTerm) {
		System.out.println("Search term: " + searchTerm);
		driver.get(config.goHome() + "/hsinaps-dilavni");

		try {
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectSpanish();
			pageNotFound.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch_spanish");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get a list of search terms from the error data spreadsheet.
	 * 
	 * @return
	 */
	@DataProvider(name = "SearchTerms")
	private Iterator<Object[]> getErrorPagedSearchTerms() {
		String[] columnsToReturn = { "SearchTerm" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
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
		Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
		Assert.assertEquals(beacon.linkName, "PageNotFound");
		Assert.assertEquals(beacon.channels, "Error Pages");
		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"), "eVar13 incorrect");
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
	}

}
