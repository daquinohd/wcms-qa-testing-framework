package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class AdvancedResultsClick_Test extends AnalyticsTestClickBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String ADVANCED_PAGE_1ST = "?rl=2";
	private final String ADVANCED_PAGE_2ND = "?loc=0&rl=2&pn=2&ni=10";

	private AdvanceSearchResults searchResults;

	// ==================== Setup methods ==================== //

	/**
	 * Navigate, suppress chat, create advancedSearch object for each test.
	 * 
	 * @param queryParams
	 */
	private void setupTestMethod(String queryParams) {
		driver.get(config.goHome() + PATH + queryParams);

		try {
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			searchResults = new AdvanceSearchResults(driver, chatPrompt);
			System.out.println("Path: " + PATH + queryParams);
		} catch (Exception e) {
			Assert.fail("Error loading Advanced CTS results url: " + PATH + queryParams);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Advanced 'Start Over' click event
	@Test(groups = { "Analytics" })
	public void testAdvStartOverClick() {
		setupTestMethod(ADVANCED_PAGE_1ST);

		try {
			searchResults.clickStartOverNoNav();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(49), "Missing event49");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start over");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced ranked result click event
	@Test(groups = { "Analytics" })
	public void testAdvLinkRanking() {
		setupTestMethod(ADVANCED_PAGE_2ND);

		try {
			searchResults.clickResultLinkByIndex(1);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(42), "Missing event42");
			Assert.assertEquals(beacon.props.get(12), "clinicaltrials_advanced");
			Assert.assertEquals(beacon.props.get(13), "2|page 2");
			Assert.assertEquals(beacon.props.get(12), beacon.eVars.get(12));
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for AdvancedResultsClick_Test
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
