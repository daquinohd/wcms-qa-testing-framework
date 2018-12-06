package gov.nci.webanalyticstests.r4r.pages;

import java.util.Iterator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.Resources4Researchers.Resources4ResearchersResourceDetailPage;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RResourcePageClick_Test extends R4RClickBase {

	private final String TESTDATA_SHEET_NAME = "R4RResourceLoad";

	private Resources4ResearchersResourceDetailPage r4rResourcePage;

	// ==================== Setup methods ==================== //

	/**
	 * Go to dictionary search page and initialize DictionaryObject.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		driver.get(config.goHome() + path);
		try {
			r4rResourcePage = new Resources4ResearchersResourceDetailPage(driver, logger);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test R4R resource detail page load event
	@Test(dataProvider = "R4RResourcePage", groups = { "Analytics" })
	public void testR4RResourceViewClick(String path, String type) {
		System.out.println("Path: " + path + ", search type: " + type);
		setupTestMethod(path);

		try {
			r4rResourcePage.clickResources4ResearchersHome();
			driver.navigate().back();
			String title = r4rResourcePage.getPageTitleText();
			
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "R4R Data Load", "linkName");
			Assert.assertTrue(beacon.hasEvent(66), "event66");
			Assert.assertEquals(beacon.props.get(39), "r4r_resource|view", "prop39");
			Assert.assertEquals(beacon.props.get(40), title, "prop40");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "R4RResourcePage")
	private Iterator<Object[]> getR4RResultsPageData() {
		String[] columnsToReturn = { "Path", "ContentType" };
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
