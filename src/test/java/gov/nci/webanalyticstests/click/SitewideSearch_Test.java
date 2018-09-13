package gov.nci.webanalyticstests.click;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.error.pages.PageNotFound;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.webanalytics.Beacon;
import gov.nci.Utilities.ExcelManager;

public class SitewideSearch_Test extends AnalyticsTestClickBase {
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	private PageNotFound pageNotFound;
	private Beacon beacon;
	
	private final String TESTDATA_SHEET_NAME = "SitewideSearch";	
	private final String TESTDATA_SHEET_NAME_ES = "SitewideSearchEs";	
	private String testDataFilePath;
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("SitewideSearchData");
	}
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupTestMethod() {
		/// TODO: get results count
		/// Do these before each test
	}

	// Verify analytics click values for sitewide cancer term search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" })
	public void testSitewideSearch(String searchTerm) {
		try {
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(searchTerm);
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
			driver.get(config.getPageURL("SpanishPage"));
			swSearchForm = new SitewideSearchForm(driver);			
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
		    beacon = getBeacon(); 
		    
			doCommonClassAssertions(searchTerm);
			Assert.assertEquals(beacon.props.get(8), "spanish");
			Assert.assertEquals(beacon.props.get(11), "sitewide_spanish");
			Assert.assertEquals(beacon.eVars.get(11), "sitewide_spanish");
			Assert.assertEquals(beacon.eVars.get(2), "spanish");
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
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(searchTerm);
			Assert.assertEquals(beacon.props.get(11), "sitewide");
			Assert.assertEquals(beacon.eVars.get(11), "sitewide");
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
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(searchTerm);
			Assert.assertEquals(beacon.props.get(11), "sitewide");
			Assert.assertEquals(beacon.eVars.get(11), "sitewide");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from sitewide search results page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testSearchWithinResults(String searchTerm) {
		try {
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.doWithinSearch();
			swSearchResults.setSitewideSearchKeyword(searchTerm);
		    swSearchResults.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(searchTerm);
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_withinresults");
			Assert.assertEquals(beacon.eVars.get(11), "sitewide_bottom_withinresults");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from sitewide search results page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testSearchNewFromResults(String searchTerm) {
		try {
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);			
			swSearchResults.setSitewideSearchKeyword(searchTerm);
		    swSearchResults.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(searchTerm);
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_new");
			Assert.assertEquals(beacon.eVars.get(11), "sitewide_bottom_new");
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
		    
			doCommonClassAssertions(searchTerm);
			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch");
			Assert.assertEquals(beacon.eVars.get(11), "pagenotfoundsearch");
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
	private void doCommonClassAssertions(String searchTerm) {
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		//Assert.assertEquals(beacon.eVars.get(13), "eVar13 value incorrect.");
		Assert.assertEquals(beacon.eVars.get(14), searchTerm.toLowerCase());
	}
	
}
