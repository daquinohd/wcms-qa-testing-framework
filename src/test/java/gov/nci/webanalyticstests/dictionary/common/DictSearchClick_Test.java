package gov.nci.webanalyticstests.dictionary.common;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.dictionary.DictObjectBase;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class DictSearchClick_Test extends AnalyticsTestClickBase {

	private final String SEARCH_SELECTOR = ".dictionary-search-input";

	DictObjectBase dict;
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
		} catch (Exception e) {
			Assert.fail("Error loading Dictionary Search URL at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Term Search "Starts With" click event
	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermSearchStartsWithClick(String path, String term, String searchType, String linkName) {
		System.out.println("Test Term Search starts With \"" + term + "\" click event:");
		setupTestMethod(path);

		try {
			dict.SubmitSearchTerm(SEARCH_SELECTOR, term);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, linkName);
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.props.get(11), searchType);
			Assert.assertEquals(beacon.props.get(22), term);
			Assert.assertEquals(beacon.props.get(24), "starts with");
			Assert.assertTrue(beacon.eVars.get(13).matches("^[+]\\d$"), "eVar13 regex mismatch");
			logger.log(LogStatus.PASS, "Test Term Search starts with \"" + term + "\" click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}

	}

	/// Test Term Search "Contains" click event
	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermSearchContainsClick(String path, String term, String searchType, String linkName) {
		System.out.println("Test Term Search contains \"" + term + "\" click event:");
		setupTestMethod(path);

		try {
			dict.selectContains();
			dict.SubmitSearchTerm(SEARCH_SELECTOR, term);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, linkName);
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.props.get(11), searchType);
			Assert.assertEquals(beacon.props.get(22), term);
			Assert.assertEquals(beacon.props.get(24), "contains");
			Assert.assertTrue(beacon.eVars.get(13).matches("^[+]\\d$"), "eVar13 regex mismatch");
			logger.log(LogStatus.PASS, "Test Term Search contains \"" + term + "\" click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}

	}

	/// Test Ranked Result click event
	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermRankedResultsClick(String path, String term, String searchType, String linkName) {
		System.out.println("Test Ranked Result click event (" + term + "):");
		setupTestMethod(path);

		try {
			dict.clickSearchResult("dfn a", 2);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "DictionaryResults");
			Assert.assertEquals(beacon.props.get(13), "3");
			logger.log(LogStatus.PASS, "Test Ranked Result click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}

	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path, term, and expected test values.
	 * 
	 * @return path, search term, search type, linkname
	 */
	@DataProvider(name = "SearchClickData")
	public Iterator<Object[]> getSearchClickData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount("SearchTerms"); rowNum++) {
			String path = excelReader.getCellData("SearchTerms", "Path", rowNum);
			String term = excelReader.getCellData("SearchTerms", "SearchTerm", rowNum);
			String type = excelReader.getCellData("SearchTerms", "SearchType", rowNum);
			String name = excelReader.getCellData("SearchTerms", "LinkName", rowNum);
			Object ob[] = { path, term, type, name };
			myObjects.add(ob);
		}
		return myObjects.iterator();
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

		Assert.assertTrue(beacon.channels.contains("Publications"));
		Assert.assertTrue(beacon.linkName.contains(linkName));
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(26), beacon.props.get(24));
	}

}
