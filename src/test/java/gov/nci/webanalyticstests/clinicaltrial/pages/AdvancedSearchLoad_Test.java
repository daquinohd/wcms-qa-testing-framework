package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class AdvancedSearchLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/advanced-search";
	private final String SEARCH_TYPE = "Clinical Trials: Advanced";

	// ==================== Test methods ==================== //
	
	/// Test CTS Advanced Search page load
	@Test(groups = { "Analytics" })
	public void testCTSAdvancedSearchPageLoad() {
		System.out.println("Test " + SEARCH_TYPE + " page load:");
		driver.get(config.goHome() + PATH);

		try {
			AnalyticsMetaData analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsMetaData, PATH);
			Assert.assertEquals(beacon.props.get(62), SEARCH_TYPE);
			Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
			logger.log(LogStatus.PASS, SEARCH_TYPE + " page load values passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

}