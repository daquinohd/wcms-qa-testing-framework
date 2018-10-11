package gov.nci.webanalyticstests.sitewidesearch.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class SwsResultsClick_Test extends AnalyticsTestClickBase {

	private final String RESULTS_PATH_EN = "/search/results";
	private final String SEARCH_TERM = "Tumor";
	private final String REGEX_RESULT_RANK = "^\\+\\d{1,2}$";

	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;

	// ==================== Test methods ==================== //

	/// Test Best Bets click event for a search term
	@Test(groups = { "Analytics" })
	public void testBestBetsClick() {
		System.out.println("Search term: " + SEARCH_TERM);
		driver.get(config.goHome());

		try {
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(SEARCH_TERM);
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.clickBestBets();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "");
			Assert.assertEquals(beacon.props.get(12), "best_bets");
			Assert.assertEquals(beacon.props.get(13), "1");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test result item click event for a search term
	@Test(groups = { "Analytics" })
	public void testResultItemClick() {
		System.out.println("Search term: " + SEARCH_TERM);
		driver.get(config.goHome());

		try {
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(SEARCH_TERM);
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.clickResultLinkNoNav(".sitewide-results .sitewide-list li a");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "");
			Assert.assertEquals(beacon.props.get(12), "generic");
			Assert.assertEquals(beacon.props.get(13), "1");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test "Search within results" click event
	@Test(groups = { "Analytics" })
	public void testSearchWithinResults() {
		System.out.println("Search term: " + SEARCH_TERM);
		driver.get(config.getPageURL("SitewideResultsPage"));

		try {
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.selectWithinResults();
			swSearchResults.setSitewideSearchKeyword(SEARCH_TERM);
			swSearchResults.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "Search");
			Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_withinresults");
			Assert.assertEquals(beacon.props.get(14), SEARCH_TERM.toLowerCase());
			Assert.assertTrue(beacon.eVars.get(13).matches(REGEX_RESULT_RANK), "eVar13 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test "Search within results" click event
	@Test(groups = { "Analytics" })
	public void testSearchNewFromResults() {
		System.out.println("Search term: " + SEARCH_TERM);
		driver.get(config.goHome() + RESULTS_PATH_EN);

		try {
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.setSitewideSearchKeyword(SEARCH_TERM);
			swSearchResults.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "Search");
			Assert.assertTrue(beacon.hasEvent(2), "Missing event2");
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_new");
			Assert.assertEquals(beacon.props.get(14), SEARCH_TERM.toLowerCase());
			Assert.assertTrue(beacon.eVars.get(13).matches(REGEX_RESULT_RANK), "eVar13 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param action
	 */
	private void doCommonClassAssertions(Beacon beacon, String action) {
		doCommonClickAssertions(beacon);

		Assert.assertEquals(beacon.channels, "Search");
		Assert.assertEquals(beacon.linkName, "SiteWideSearchResults" + action);
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(12), beacon.props.get(12));
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
	}
}