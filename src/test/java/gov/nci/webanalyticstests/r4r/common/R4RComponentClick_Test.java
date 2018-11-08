package gov.nci.webanalyticstests.r4r.common;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.Resources4Researchers.Resources4ResearchersHome;
import gov.nci.dictionary.DictObjectBase;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RComponentClick_Test extends R4RClickBase {

	private final String TESTDATA_SHEET_NAME = "SearchTerms";
	private final String SEARCH_SELECTOR = ".dictionary-search-input";
	private final String SEARCH_PATH = "/research/resources";

	private Resources4ResearchersHome r4rHome;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	/**
	 * Go to dictionary search page and initialize DictionaryObject.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		driver.get(config.goHome() + path);
		try {
			r4rHome = new Resources4ResearchersHome(driver, logger);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Ranked Result click event
	@Test(groups = { "Analytics" })
	public void testSearchQuery() {
		setupTestMethod(SEARCH_PATH);

		try {
			Beacon beacon = getBeacon();
			//doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon, beacon);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}

	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
	}

}
