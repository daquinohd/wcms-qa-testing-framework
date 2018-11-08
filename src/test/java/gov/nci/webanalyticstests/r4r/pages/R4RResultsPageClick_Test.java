package gov.nci.webanalyticstests.r4r.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Resources4Researchers.Resources4ResearchersSearchResult;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RResultsPageClick_Test extends R4RClickBase {

	// TODO: data method and more test cases
	// TODO: detail tests

	private final String ROOT_PATH = "/research/resources";
	private final String SEARCH_ALL = "/search";
	private final String SEARCH_TOOLS = "/search?from=0&toolTypes=analysis_tools";
	private final String SEARCH_AREAS = "/search?from=0&researchAreas=cancer_omics";
	private final String SEARCH_FILTERED = "/search?from=20&toolSubtypes=modeling&toolSubtypes=r_software&toolTypes=analysis_tools";

	private Resources4ResearchersSearchResult r4rResult;

	// ==================== Setup methods ==================== //

	/**
	 * Go to dictionary search page and initialize DictionaryObject.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		driver.get(config.goHome() + path);
		try {
			r4rResult = new Resources4ResearchersSearchResult(driver, logger);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //
	
	/// Test R4R Pages Load event
	@Test(groups = { "Analytics" })
	public void testR4RResultsViewClick() {
		driver.get(config.goHome());

		try {
			Beacon beacon = getBeacon();
			Assert.assertEquals(beacon, getBeacon());
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
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
	}

}
