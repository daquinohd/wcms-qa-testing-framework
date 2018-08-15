package gov.nci.webAnalyticsTests;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.sitewideSearch.common.SitewideSearchForm;
import gov.nci.Utilities.ExcelManager;

public class WaSitewideSearch_Test extends AnalyticsTestBase {
	
	private final String TESTDATA_SHEET_NAME = "SitewideSearch";	
	private final String TESTDATA_SHEET_NAME_ES = "SitewideSearchEs";
	
	private SitewideSearchForm swSearchForm;
	String testDataFilePath;
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("SitewideSearchData");
	}
	
	// TODO: do we need to split this further
	// TODO: separate page / component object creation
	// TODO: Spanish sws
	// TODO: Subsite sws
	@BeforeMethod(groups = { "Analytics" }) 
	public void beforeEachTest() {
		driver.get(config.goHome());
		try {
			swSearchForm = new SitewideSearchForm(driver);
		} catch (Exception ex) {
			Assert.fail("Error initializing sitewide search.");
			ex.printStackTrace();
		}
	}
		
	// Verify analytics click values for sitewide cancer term search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" }, priority = 1)
	public void testSitewideSearch(String searchTerm) {
		try {
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    setClickBeacon();
			Assert.assertTrue(hasEvent(2), "Event value incorrect.");
			Assert.assertTrue(hasProp(4, "d=pev1"), "Value of prop4 incorrect.");
			Assert.assertTrue(hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(hasProp(14, searchTerm), "Search term value does not match.");
			Assert.assertTrue(hasProp(67, "D=pageName"), "Dynamic pageName value incorrect.");
			Assert.assertTrue(haseVar(13), "eVar13 value incorrect.");
			Assert.assertTrue(haseVar(14, searchTerm), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values for sitewide Spanish term search
	@Test(dataProvider = "CancerTermsEs", groups = { "Analytics" }, priority = 2)
	public void testSitewideSearchEspanol(String searchTerm) {
		try {
			driver.get(config.getPageURL("SpanishPage"));
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    setClickBeacon(); 
			Assert.assertTrue(hasEvent(2), "Event value incorrect.");
			Assert.assertTrue(hasProp(4, "d=pev1"), "Value of prop4 incorrect.");
			Assert.assertTrue(hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(hasProp(14, searchTerm), "Search term value does not match.");
			Assert.assertTrue(hasProp(67, "D=pageName"), "Dynamic pageName value incorrect.");
			Assert.assertTrue(haseVar(13), "eVar13 value incorrect.");
			Assert.assertTrue(haseVar(14, searchTerm), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}

	// Verify analytics click values for sitewide search w/o results
	@Test(dataProvider = "NoMatchTerms", groups = { "Analytics" }, priority = 3)
	public void testSitewideSearchNoMatch(String searchTerm) {
		try {
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    setClickBeacon();
			Assert.assertTrue(hasEvent(2), "Event value incorrect.");
			Assert.assertTrue(hasProp(4, "d=pev1"), "Value of prop4 incorrect.");
			Assert.assertTrue(hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(hasProp(14, searchTerm), "Search term value does not match.");
			Assert.assertTrue(hasProp(67, "D=pageName"), "Dynamic pageName value incorrect.");
			Assert.assertTrue(haseVar(13), "eVar13 value incorrect.");
			Assert.assertTrue(haseVar(14, searchTerm), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values for microsite-wide search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" }, priority = 5)
	public void testSitewideSearchButtonClick(String searchTerm) {
		try {
			driver.get(config.getPageURL("MicroSite"));			
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    setClickBeacon();
			Assert.assertTrue(hasEvent(2), "Event value incorrect.");
			Assert.assertTrue(hasProp(4, "d=pev1"), "Value of prop4 incorrect.");
			Assert.assertTrue(hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(hasProp(14, searchTerm), "Search term value does not match.");
			Assert.assertTrue(hasProp(67, "D=pageName"), "Dynamic pageName value incorrect.");
			Assert.assertTrue(haseVar(13), "eVar13 value incorrect.");
			Assert.assertTrue(haseVar(14, searchTerm), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
		
	/**** 
	 * TODO tests 
	 * Results page (separate test?)
	 * Form on results page
	 * 
	 ****/	
	
	/******************** Data Providers ****************/		

	@DataProvider(name = "CancerTerms")
	public Iterator<Object[]> readCancerTerm_Data() {
		return getDataIteratorObject("CancerTerm", TESTDATA_SHEET_NAME);
	}

	@DataProvider(name = "CancerTermsEs")
	public Iterator<Object[]> readCancerTermEs_Data() {
		return getDataIteratorObject("CancerTerm", TESTDATA_SHEET_NAME_ES);
	}
		
	@DataProvider(name = "NoMatchTerms")
	public Iterator<Object[]> readNoMatchTerm_Data() {
		return getDataIteratorObject("NoMatch", TESTDATA_SHEET_NAME);
	}
	
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
}
