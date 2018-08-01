package gov.nci.webAnalyticsTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.sitewideSearch.common.SitewideSearchForm;
import gov.nci.sitewideSearch.pages.SitewideSearchResults;

public class WaSitewideSearch_Test extends AnalyticsTestBase {
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	
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
		    swSearchForm.setSitewideSearchKeyword("ipilimumab");
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

}
