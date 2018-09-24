package gov.nci.webanalyticstests.dictionary.common;

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

	private String testDataFilePath;
	private final String SEARCH_SELECTOR = ".dictionary-search-input";

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermSearchStartsWithClick(String path, String term, String searchType, String linkName) {
		String curMethod = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println(linkName + " starts with '" + term + "':");
		driver.get(config.goHome() + path);

		try {
			DictObjectBase dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(SEARCH_SELECTOR, term);
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.linkName, linkName);
			Assert.assertEquals(beacon.props.get(11), searchType);
			Assert.assertEquals(beacon.props.get(22), term);
			Assert.assertEquals(beacon.props.get(24), "starts with");
			Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
			Assert.assertTrue(beacon.eVars.get(13).matches("^[+]\\d$"), "eVar13 regex mismatch");
			Assert.assertEquals(beacon.eVars.get(26), beacon.props.get(24));
		} catch (Exception ex) {
			Assert.fail("Error clicking element in " + curMethod + "()");
		}

	}

	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermSearchContainsClick(String path, String term, String searchType, String linkName) {
		String curMethod = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println(linkName + " contains '" + term + "':");
		driver.get(config.goHome() + path);

		try {
			DictObjectBase dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SubmitSearchTerm(SEARCH_SELECTOR, term);
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.linkName, linkName);
			Assert.assertEquals(beacon.props.get(11), searchType);
			Assert.assertEquals(beacon.props.get(22), term);
			Assert.assertEquals(beacon.props.get(24), "contains");
			Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
			Assert.assertTrue(beacon.eVars.get(13).matches("^[+]\\d$"), "eVar13 regex mismatch");
			Assert.assertEquals(beacon.eVars.get(26), beacon.props.get(24));
		} catch (Exception ex) {
			Assert.fail("Error clicking element in " + curMethod + "()");
		}

	}

	@Test(dataProvider = "SearchClickData", groups = { "Analytics" })
	public void testTermRankedResultsClick(String path, String term, String searchType, String linkName) {
		String curMethod = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test " + path + " results click: ");
		driver.get(config.goHome() + path);

		try {
			DictObjectBase dict = new DictObjectBase(driver);
			dict.clickSearchResult("dfn a", 2);
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.linkName.contains("DictionaryResults"));
			Assert.assertEquals(beacon.props.get(13), "3");
		} catch (Exception ex) {
			Assert.fail("Error clicking element in " + curMethod + "()");
		}

	}

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

}
