package gov.nci.webAnalyticsTests;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.sitewideSearch.common.SitewideSearchForm;
import gov.nci.sitewideSearch.pages.SitewideSearchResults;
import gov.nci.Utilities.ExcelManager;

public class WaSitewideSearch_Test extends AnalyticsTestBase {
	
	private final String TESTDATA_SHEET_NAME = "SitewideSearch";	
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	String testDataFilePath;
	
	// TODO: Add data providers / iterator
	// TODO: split beforeMethod() and beforeClass() methods
	@BeforeMethod(groups = { "Analytics" }) 
	// Run before each test method in this class	
	public void setup() {
		driver.get(config.goHome());
		try {
			swSearchForm = new SitewideSearchForm(driver);
			//swSearchResults = new SitewideSearchResults();
		} catch (Exception ex) {
			System.out.println("feh");
		}
	}
		
	@BeforeClass(groups = { "Analytics" }) 
	protected void preClass() {
		testDataFilePath = config.getProperty("SitewideSearchData");		
	}
	
	// Test for search click events
	@Test(groups = { "Analytics" })
	public void testSitewideSearchButtonClick() {		
		try {
		    swSearchForm.setSitewideSearchKeyword("ipilimumab");
		    swSearchForm.clickSearchButton();
		    setClickBeacon();
			Assert.assertTrue(hasEvent(2), "Event value incorrect.");
			Assert.assertTrue(hasProp(4, "d=pev1"), "Value of prop4 incorrect.");
			Assert.assertTrue(hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(hasProp(14, "ipilimumab"), "Search term value does not match.");
			Assert.assertTrue(hasProp(67, "D=pageName"), "Dynamic pageName value incorrect.");
			Assert.assertTrue(haseVar(13), "eVar13 value incorrect.");
			Assert.assertTrue(haseVar(14, "ipilimumab"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Test for searchresults load events
	@Test(groups = { "Analytics" })
	public void testSitewideSearchResultsLoad() {		
		try {
		    swSearchForm.setSitewideSearchKeyword("wingardium leviosa");
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
	
	
	// Start building data thingy
	@DataProvider(name = "SitewideSearch")
	public Iterator<Object[]> readSitewideSearch_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String searchTerm = excelReader.getCellData(TESTDATA_SHEET_NAME, "LeadOrganization", rowNum);
			System.out.println("Lead Organization in data provider ======= " + searchTerm);
			Object ob[] = { searchTerm };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

}
