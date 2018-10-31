package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class BasicSearchLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search";
	private final String SEARCH_TYPE = "Clinical Trials: Basic";

	// ==================== Test methods ==================== //

	/// Test CTS Advanced Search page load
	@Test(groups = { "Analytics" })
	public void testCTSAdvancedSearchPageLoad() {
		System.out.println("Path: " + PATH);
		driver.get(config.goHome() + PATH);

		try {
			AnalyticsMetaData analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			doCommonLoadAssertions(beacon, analyticsMetaData, PATH);
			Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", driver.getCurrentUrl()), "Missing NCT suite");
			Assert.assertEquals(beacon.props.get(62), SEARCH_TYPE);
			Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

}