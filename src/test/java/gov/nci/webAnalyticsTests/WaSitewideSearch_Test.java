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
	
	private SitewideSearchForm swSearchForm;
	String testDataFilePath;
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("SitewideSearchData");
	}
	
	// TODO: do we need to split this further
	// TODO: separate page / component object creation
	// TODO: Spanish sws
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
	
	// Test for search click events
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" }, priority = 1)
	public void testSitewideSearchButtonClick(String searchTerm) {
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
	
	// Test for unmatched search click
	@Test(dataProvider = "NoMatchTerms", groups = { "Analytics" }, priority = 2)
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
	
	// Test for searchresults load events
	@Test(dataProvider = "OtherTerms", groups = { "Analytics" }, priority = 3)
	public void testSitewideSearchResultsLoad(String searchTerm) {
		try {
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    setLoadBeacon();
			Assert.assertTrue(hasEvent(1), "Event value incorrect.");
			Assert.assertTrue(hasEvent(47), "Event value incorrect.");
			Assert.assertFalse(hasEvent(2), "Unexpected event value.");
			Assert.assertTrue(hasProp(44, "Global search"), "Search type value incorrect");
			// Assert.assertTrue(hasHier()); // TODO: fix hier check
		} catch (Exception e) {
			Assert.fail("Error loading sitewide search results page.");
			e.printStackTrace();
		}
	}
	
	// Test for searchresults load events
	@Test(groups = { "Analytics" }, priority = 4)
	public void testNoResultsOnce() {
		try {
		    swSearchForm.setSitewideSearchKeyword("wingardium leviosa");
		    swSearchForm.clickSearchButton();
		    setLoadBeacon();
			Assert.assertTrue(hasEvent(1), "Event value incorrect.");
			Assert.assertTrue(hasEvent(47), "Event value incorrect.");
			Assert.assertFalse(hasEvent(2), "Unexpected event value.");
			Assert.assertTrue(hasProp(44, "Global search"), "Search type value incorrect");
			// Assert.assertTrue(hasHier()); // TODO: fix hier check
		} catch (Exception e) {
			Assert.fail("Error loading sitewide search results page.");
			e.printStackTrace();
		}
	}	
	
	// Test for searchresults load events
	@Test(groups = { "Analytics" }, priority = 5)
	public void testAllSitewideSearchResultsLoad() {
		try {
		    swSearchForm.setSitewideSearchKeyword("");
		    swSearchForm.clickSearchButton();
		    setLoadBeacon();
			Assert.assertTrue(hasEvent(1), "Event value incorrect.");
			Assert.assertTrue(hasEvent(47), "Event value incorrect.");
			Assert.assertFalse(hasEvent(2), "Unexpected event value.");
			Assert.assertTrue(hasProp(44, "Global search"), "Search type value incorrect");
		} catch (Exception e) {
			Assert.fail("Error loading sitewide search results page.");
			e.printStackTrace();
		}
	}	
	
	// Test for search click events
	/**
	* TODO: fix NoSuchSessionException()
	* This only seems to break when the data provider is used AND
	* when it's the final test on the list
	* The driver may be quitting before expected
	*/	
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" }, priority = 6)
	public void itBreaksHere(String searchTerm) {
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
	
	/******************** Data Providers ****************/		

	@DataProvider(name = "CancerTerms")
	public Iterator<Object[]> readCancerTerm_Data() {
		return getDataIteratorObject("CancerTerm");
	}

	@DataProvider(name = "BestBetTerms")
	public Iterator<Object[]> readBestBetTerm_Data() {
		return getDataIteratorObject("BestBet");
	}
	
	@DataProvider(name = "DefinitionTerms")
	public Iterator<Object[]> readDefinitionTerm_Data() {
		return getDataIteratorObject("Definition");
	}
	
	@DataProvider(name = "OtherTerms")
	public Iterator<Object[]> readOtherTerm_Data() {
		return getDataIteratorObject("Other");
	}
	
	@DataProvider(name = "NoMatchTerms")
	public Iterator<Object[]> readNoMatchTerm_Data() {
		return getDataIteratorObject("NoMatch");
	}
	
	private Iterator<Object[]> getDataIteratorObject(String columnName) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String searchTerm = excelReader.getCellData(TESTDATA_SHEET_NAME, columnName, rowNum);
			if(!searchTerm.isEmpty()) {
				System.out.println("Sitewide search term ======= " + searchTerm);
				Object ob[] = { searchTerm };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
}
