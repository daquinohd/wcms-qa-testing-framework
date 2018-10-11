package gov.nci.webanalyticstests.sitewidesearch.common;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;
import gov.nci.Utilities.ExcelManager;

public class SwsFormClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME_EN = "SitewideSearchEn";
	private final String TESTDATA_SHEET_NAME_ES = "SitewideSearchEs";
	private final String PATH_HOME_EN = "/";
	private final String PATH_HOME_ES = "/espanol";
	private final String PATH_INNER = "/research/nci-role/extramural";
	private final String PATH_MICROSITE = "/sites/nano/research";

	private SitewideSearchForm swSearchForm;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsSwsData");
	}

	/**
	 * 
	 * @param path
	 * @param searchTerm
	 */
	private void setupTestMethod(String path, String searchTerm) {
		driver.get(config.goHome() + path);
		try {
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(searchTerm);
		} catch (Exception e) {
			Assert.fail("Error building Related Resources page object: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	// Test Sitewide Search from Home Page
	@Test(dataProvider = "CancerTermsEn", groups = { "Analytics" })
	public void testSitewideSearchFromHome(String searchTerm) {
		System.out.println("Test Sitewide Search from Home Page (" + searchTerm + "):");
		setupTestMethod(PATH_HOME_EN, searchTerm);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(8), "english");
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test Sitewide Search from Spanish Home Page
	@Test(dataProvider = "CancerTermsEs", groups = { "Analytics" })
	public void testSitewideSearchFromHomeSpanish(String searchTerm) {
		System.out.println("Test Sitewide Search from Spanish Home Page (" + searchTerm + "):");
		setupTestMethod(PATH_HOME_ES, searchTerm);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(8), "spanish");
			Assert.assertEquals(beacon.props.get(11), "sitewide_spanish");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test Sitewide Search from Inner Page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testSitewideSearchFromInner(String searchTerm) {
		System.out.println("Test Sitewide Search from Inner Page (" + searchTerm + "):");
		setupTestMethod(PATH_INNER, searchTerm);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(8), "english");
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test Sitewide Search for unmatched term
	@Test(dataProvider = "NoMatchTerms", groups = { "Analytics" })
	public void testSitewideSearchNoMatch(String searchTerm) {
		System.out.println("Test Sitewide Search for unmatched term (" + searchTerm + "):");
		setupTestMethod(PATH_HOME_EN, searchTerm);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Verify analytics click values for microsite-wide search
	@Test(dataProvider = "BestBetTerms", groups = { "Analytics" })
	public void testMicroSitewideSearch(String searchTerm) {
		System.out.println("Test Microsite Search for term (" + searchTerm + "):");
		setupTestMethod(PATH_MICROSITE, searchTerm);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "CancerTermsEn")
	private Iterator<Object[]> getCancerTermsEn() {
		String[] columnsToReturn = { "SearchTerm" };
		String[] filterInfo = { "ResultType", "Generic" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME_EN, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "CancerTermsEs")
	private Iterator<Object[]> getCancerTermsEs() {
		String[] columnsToReturn = { "SearchTerm" };
		String[] filterInfo = { "ResultType", "Generic" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME_ES, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "BestBetTerms")
	private Iterator<Object[]> getBestBetTerms() {
		String[] columnsToReturn = { "SearchTerm" };
		String[] filterInfo = { "ResultType", "BestBet" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME_EN, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "DefinitionTerms")
	private Iterator<Object[]> getDefinitionTerms() {
		String[] columnsToReturn = { "SearchTerm" };
		String[] filterInfo = { "ResultType", "Definition" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME_EN, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "NoMatchTerms")
	private Iterator<Object[]> getNoMatchTerms() {
		String[] columnsToReturn = { "SearchTerm" };
		String[] filterInfo = { "ResultType", "NoMatch" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME_EN, columnsToReturn, filterInfo);
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
		Assert.assertEquals(beacon.linkName, "SiteWideSearch");
		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"), "eVar13 incorrect");
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
	}

}
