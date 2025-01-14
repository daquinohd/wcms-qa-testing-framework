package gov.nci.webanalyticstests.sitewidesearch.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class SwsResultsLoad_Test extends AnalyticsTestLoadBase {

	private final String RESULTS_PATH_EN = "/search/results";
	private final String RESULTS_PATH_ES = "/espanol/buscar/resultados";
	private final String SEARCH_TERM = "Tumor";

	private AnalyticsMetaData analyticsMetaData;
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;

	// ==================== Test methods ==================== //

	// Test Sitewide Search Results page (English) load event
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageEn() {
		System.out.println("Path: " + RESULTS_PATH_EN + " Search term: " + SEARCH_TERM);
		driver.get(config.goHome());

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.setSitewideSearchKeyword(SEARCH_TERM);
			swSearchForm.clickSearchButton();
			swSearchResults = new SitewideSearchResults(driver);
			analyticsMetaData.setPageTitle("NCI Search Results - National Cancer Institute");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, RESULTS_PATH_EN);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test Sitewide Search Results page (Spanish) load event
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageEs() {
		System.out.println("Path: " + RESULTS_PATH_ES + " Search term: " + SEARCH_TERM);
		driver.get(config.getPageURL("SpanishHome"));

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.setSitewideSearchKeyword(SEARCH_TERM);
			swSearchForm.clickSearchButton();
			swSearchResults = new SitewideSearchResults(driver);
			analyticsMetaData.setPageTitle("Resultados - National Cancer Institute");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, RESULTS_PATH_ES);
			Assert.assertEquals(beacon.props.get(8), "spanish");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test Sitewide Search Results page load event - no results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageNoResults() {
		System.out.println("Path: " + RESULTS_PATH_EN);
		driver.get(config.goHome() + RESULTS_PATH_EN);

		try {
			analyticsMetaData.setPageTitle("NCI Search Results - National Cancer Institute");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, RESULTS_PATH_EN);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param path
	 */
	private void doCommonClassAssertions(Beacon beacon, String path) {
		doCommonLoadAssertions(beacon, analyticsMetaData, path);
		Assert.assertEquals(beacon.eVars.get(10), swSearchResults.getResultsCount());
	}

}
