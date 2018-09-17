package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class SwsResults_Test extends AnalyticsTestClickBase {
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	private Beacon beacon;
	private final String SEARCH_TERM = "Tumor";	

	@Test(groups = { "Analytics" })
	public void testBestBetsClick() {
		try {
			System.out.println("Test Best Bets click event for \"" + SEARCH_TERM + "\": ");
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(SEARCH_TERM);
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.clickBestBets();
		    beacon = getBeacon();
		    
		    Assert.assertEquals(beacon.props.get(12), "best_bets");
		    Assert.assertEquals(beacon.props.get(13), "1");
		    Assert.assertEquals(beacon.eVars.get(12), beacon.props.get(12));
			logger.log(LogStatus.PASS, "Best Bet click values are correct.");
		} catch (Exception e) {
			Assert.fail("Error getting Best Bet click values.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void testResultItemClick() {
		try {
			System.out.println("Test result item click event for \"" + SEARCH_TERM + "\": ");
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(SEARCH_TERM);
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.clickListItem(2);
		    beacon = getBeacon();
		    
		    Assert.assertEquals(beacon.props.get(12), "generic");
		    Assert.assertEquals(beacon.props.get(13), "2");
		    Assert.assertEquals(beacon.eVars.get(12), beacon.props.get(12));
			logger.log(LogStatus.PASS, "Result item click values are correct.");
		} catch (Exception e) {
			Assert.fail("Error getting result item click values.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void testSearchWithinResults() {
		try {
			System.out.println("Test refined results search for \"" + SEARCH_TERM + "\": ");
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.selectWithinResults();
			swSearchResults.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchResults.clickSearchButton();
		    beacon = getBeacon();
		    
			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "SiteWideSearchResultsSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_withinresults");
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.props.get(14), SEARCH_TERM.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));		
			Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
			Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search from results.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testSearchNewFromResults() {
		try {
			System.out.println("Test new results page search for \"" + SEARCH_TERM + "\": ");
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);			
			swSearchResults.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchResults.clickSearchButton();
		    beacon = getBeacon();
		    
			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "SiteWideSearchResultsSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_new");
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.props.get(14), SEARCH_TERM.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));		
			Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
			Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search from results.");
			e.printStackTrace();
		}
	}	
	
	
}