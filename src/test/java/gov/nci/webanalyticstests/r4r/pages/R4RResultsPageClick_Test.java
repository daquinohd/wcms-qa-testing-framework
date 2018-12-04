package gov.nci.webanalyticstests.r4r.pages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import org.testng.Assert;

import gov.nci.Resources4Researchers.Resources4ResearchersSearchResult;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RResultsPageClick_Test extends R4RClickBase {

	private final String TESTDATA_SHEET_NAME = "R4RResultsLoad";

	private Resources4ResearchersSearchResult r4rResult;

	// ==================== Setup methods ==================== //

	/**
	 * Go to dictionary search page and initialize DictionaryObject.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		driver.get(config.goHome() + path);
		try {
			r4rResult = new Resources4ResearchersSearchResult(driver, logger);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test R4R Pages Load event
	@Test(dataProvider = "R4RResultsPage", groups = { "Analytics" })
	public void testR4RResultsViewClick(String path, String type, String action, String filters) {
		System.out.println("Path: " + path + ", search type: " + type);
		setupTestMethod(path);

		try {
			String total = r4rResult.getResultsCount();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(65), "event65");
			Assert.assertEquals(beacon.props.get(39), action + total, "prop39");
			Assert.assertEquals(beacon.props.get(40), filters, "prop40");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "R4RResultsPage")
	private Iterator<Object[]> getR4RResultsPageData() {
		String[] columnsToReturn = { "Path", "ContentType", "ActionStatus", "Filters" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
	}

}
