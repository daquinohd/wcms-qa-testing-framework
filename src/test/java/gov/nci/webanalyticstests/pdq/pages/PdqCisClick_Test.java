package gov.nci.webanalyticstests.pdq.pages;

import java.util.Iterator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import gov.nci.pdq.pages.PdqPage;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class PdqCisClick_Test extends AnalyticsTestClickBase {

	private PdqPage pdqPage;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	// Go to the page, build the page and nav objects, then pause to load
	// everything.
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			pdqPage = new PdqPage(driver);
			System.out.println("Path: " + path);
		} catch (Exception e) {
			Assert.fail("Error building PDQ page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //


	// Test Toggle Link details click
	@Test(groups = { "Analytics" }, dataProvider = "ToggleData")
	public void testToggleLinkDetail(String path, String linkName) {
		System.out.println("Link name: " + linkName);
		setupTestMethod(path);

		try {
			pdqPage.clickPatientHPToggle();

			Beacon beacon = getBeacon();
			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "GlobalLinkTrack");
			Assert.assertEquals(beacon.props.get(28), "D=pageName");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "RightNavData")
	private Iterator<Object[]> getRightNavData() {
		return getPdqPageData("RightNav");
	}

	@DataProvider(name = "ToggleData")
	private Iterator<Object[]> getTogglevData() {
		return getPdqPageData("PatientHPToggle");
	}

	/**
	 * Get an iterator data object with path and link text values.
	 * 
	 * @param sheet
	 *            to use
	 * @return path and link text strings
	 */
	private Iterator<Object[]> getPdqPageData(String dataSheet) {
		String[] columnsToReturn = { "Path", "LinkText" };
		return getSpreadsheetData(testDataFilePath, dataSheet, columnsToReturn);
	}

}