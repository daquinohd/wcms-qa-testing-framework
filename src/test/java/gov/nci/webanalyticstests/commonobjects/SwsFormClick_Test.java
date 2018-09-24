package gov.nci.webanalyticstests.commonobjects;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.error.pages.PageNotFound;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;
import gov.nci.Utilities.ExcelManager;

public class SwsFormClick_Test extends AnalyticsTestClickBase {
	
	private SitewideSearchForm swSearchForm;
	private PageNotFound pageNotFound;
	private Beacon beacon;
	
	private final String TESTDATA_SHEET_NAME = "SitewideSearch";	
	private final String TESTDATA_SHEET_NAME_ES = "SitewideSearchEs";	
	private String testDataFilePath;
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("SitewideSearchData");
	}
	
	// Verify analytics click values for sitewide cancer term search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" })
	public void testSitewideSearch(String searchTerm) {
		try {
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(searchTerm);
			System.out.println("Sitewide search term: " + searchTerm);
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.linkName, "SiteWideSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values for sitewide Spanish term search
	@Test(dataProvider = "CancerTermsEs", groups = { "Analytics" })
	public void testSitewideSearchEspanol(String searchTerm) {
		try {
			driver.get(config.getPageURL("SpanishHome"));
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(searchTerm);
			System.out.println("Sitewide search term: " + searchTerm);
			beacon = getBeacon(); 
			
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.linkName, "SiteWideSearch");
			Assert.assertEquals(beacon.props.get(8), "spanish");
			Assert.assertEquals(beacon.props.get(11), "sitewide_spanish");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}

	// Verify analytics click values for sitewide search w/o results
	@Test(dataProvider = "NoMatchTerms", groups = { "Analytics" })
	public void testSitewideSearchNoMatch(String searchTerm) {
		try {
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(searchTerm);
			System.out.println("Sitewide search term: " + searchTerm);
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.linkName, "SiteWideSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values for microsite-wide search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" })
	public void testMicroSitewideSearch(String searchTerm) {
		try {
			driver.get(config.getPageURL("MicroSite"));
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(searchTerm);
			System.out.println("Sitewide search term: " + searchTerm);
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.linkName, "SiteWideSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from error page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testErrorPageSearch(String searchTerm) {
		try {
			driver.get(config.getPageURL("PageNotFound"));
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectEnglish();
			pageNotFound.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.linkName, "PageNotFound");
			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	/******************** Data Providers ****************/
	@DataProvider(name = "CancerTerms")
	public Iterator<Object[]> readCancerTerm_Data() {
		return getDataIteratorObject("CancerTerm", TESTDATA_SHEET_NAME);
	}

	@DataProvider(name = "CancerTermsEs")
	public Iterator<Object[]> readCancerTermEs_Data() {
		return getDataIteratorObject("CancerTerm", TESTDATA_SHEET_NAME_ES);
	}

	@DataProvider(name = "DefinitionTerms")
	public Iterator<Object[]> readDefinitionTerm_Data() {
		return getDataIteratorObject("Definition", TESTDATA_SHEET_NAME);
	}
	
	@DataProvider(name = "NoMatchTerms")
	public Iterator<Object[]> readNoMatchTerm_Data() {
		return getDataIteratorObject("NoMatch", TESTDATA_SHEET_NAME);
	}
	
	/**
	 * Get an iterable collection of test objects given a data sheet name and column
	 * @param columnName
	 * @param sheetName
	 */	
	private Iterator<Object[]> getDataIteratorObject(String columnName, String sheetName) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String searchTerm = excelReader.getCellData(sheetName, columnName, rowNum);
			if(!searchTerm.isEmpty()) {
				Object ob[] = { searchTerm };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
	
	/**
	 * Shared Assert() calls for SitewideSearch_Test
	 * @param searchTerm
	 */
	private void doCommonClassAssertions(Beacon beacon, String searchTerm) {
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));		
		Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
		
	}
	
}
