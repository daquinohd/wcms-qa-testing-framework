package gov.nci.webanalyticstests.dictionary.common;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.dictionary.DictObjectBase;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class DictSearchClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "SearchTerms";
	private final String SEARCH_SELECTOR = ".dictionary-search-input";

	private DictObjectBase dict;
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
			dict = new DictObjectBase(driver);
			System.out.println("Path: " + path);
		} catch (Exception e) {
			Assert.fail("Error loading Dictionary Search URL at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Term Search "Starts With" click event
	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermSearchStartsWithClick(String path, String term, String searchType, String linkName) {
		System.out.println("Search term: " + term + ", Search type: " + searchType);
		setupTestMethod(path);

		try {
			dict.SubmitSearchTerm(SEARCH_SELECTOR, term);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, linkName);
			Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
			Assert.assertEquals(beacon.props.get(11), searchType);
			Assert.assertEquals(beacon.props.get(22), term);
			Assert.assertEquals(beacon.props.get(24), "starts with");
			Assert.assertTrue(beacon.eVars.get(13).matches("^[+]\\d$"), "eVar13 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}

	}

	/// Test Term Search "Contains" click event
	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermSearchContainsClick(String path, String term, String searchType, String linkName) {
		System.out.println("Search term: " + term + ", Search type: " + searchType);
		setupTestMethod(path);

		try {
			dict.selectContains();
			dict.SubmitSearchTerm(SEARCH_SELECTOR, term);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, linkName);
			Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
			Assert.assertEquals(beacon.props.get(11), searchType);
			Assert.assertEquals(beacon.props.get(22), term);
			Assert.assertEquals(beacon.props.get(24), "contains");
			Assert.assertTrue(beacon.eVars.get(13).matches("^[+]\\d$"), "eVar13 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}

	}

	/// Test Ranked Result click event
	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermRankedResultsClick(String path, String term, String searchType, String linkName) {
		System.out.println("Search term: " + term + ", Search type: " + searchType);
		setupTestMethod(path);

		try {
			dict.clickSearchResult("dfn a", 2);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "DictionaryResults");
			Assert.assertEquals(beacon.props.get(13), "3");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}

	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path, term, and expected test values.
	 * 
	 * @return path, search term, search type, linkname
	 */
	@DataProvider(name = "SearchClickData")
	private Iterator<Object[]> getSearchClickData() {
		String[] columnsToReturn = { "Path", "SearchTerm", "SearchType", "LinkName" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon, String linkName) {
		doCommonClickAssertions(beacon);

		Assert.assertTrue(beacon.channels.contains("Publications"), "Incorrect channel");
		Assert.assertTrue(beacon.linkName.contains(linkName), "Incorrect linkName");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(26), beacon.props.get(24));
	}

}
