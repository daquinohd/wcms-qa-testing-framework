package gov.nci.webanalyticstests.sitewidesearch.common;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
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
			logger.log(LogStatus.PASS, "Test Sitewide Search from Home Page (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			logger.log(LogStatus.PASS, "Test Sitewide Search from Spanish Home Page (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			logger.log(LogStatus.PASS, "Test Sitewide Search from Inner Page (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			logger.log(LogStatus.PASS, "Test Sitewide Search unmatched term (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			logger.log(LogStatus.PASS, "Test Microsite Search for term (" + searchTerm + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "CancerTermsEn")
	public Iterator<Object[]> getCancerTermsEn() {
		return getFilteredSearchTerms(TESTDATA_SHEET_NAME_EN, "Generic");
	}

	@DataProvider(name = "CancerTermsEs")
	public Iterator<Object[]> getCancerTermsEs() {
		return getFilteredSearchTerms(TESTDATA_SHEET_NAME_ES, "Generic");
	}

	@DataProvider(name = "BestBetTerms")
	public Iterator<Object[]> getBestBetTerms() {
		return getFilteredSearchTerms(TESTDATA_SHEET_NAME_EN, "BestBet");
	}

	@DataProvider(name = "DefinitionTerms")
	public Iterator<Object[]> getDefinitionTerms() {
		return getFilteredSearchTerms(TESTDATA_SHEET_NAME_EN, "BestBet");
	}

	@DataProvider(name = "NoMatchTerms")
	public Iterator<Object[]> getNoMatchTerms() {
		return getFilteredSearchTerms(TESTDATA_SHEET_NAME_EN, "NoMatch");
	}

	/**
	 * Get an iterable collection of test objects given a data sheet name and column
	 * 
	 * @param sheetName
	 * @param filter
	 * @return
	 */
	private Iterator<Object[]> getFilteredSearchTerms(String sheetName, String filter) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String searchTerm = excelReader.getCellData(sheetName, "SearchTerm", rowNum);
			String resultType = excelReader.getCellData(sheetName, "ResultType", rowNum);
			if (resultType.equalsIgnoreCase(filter)) {
				Object ob[] = { searchTerm };
				myObjects.add(ob);
			}
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
		Assert.assertEquals(beacon.linkName, "SiteWideSearch");

		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
	}

}
