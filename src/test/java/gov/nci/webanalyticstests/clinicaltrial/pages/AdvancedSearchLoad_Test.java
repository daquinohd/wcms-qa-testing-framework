package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class AdvancedSearchLoad_Test extends AnalyticsTestLoadBase {

	/**
	 * This test class covers Clinical Trial Advanced Search pages
	 */
	
	private final String ADV_PATH = "/about-cancer/treatment/clinical-trials/advanced-search";
	private final String ADV_CONTENT_TYPE = "Clinical Trials: Advanced";

	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;

	@Test(groups = { "Analytics" })
	public void testCTSAdvancedSearchPageLoad() {
		try {
			driver.get(config.goHome() + ADV_PATH);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(ADV_CONTENT_TYPE + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			doCommonLoadAssertions(beacon, analyticsPageLoad, ADV_PATH);
			Assert.assertEquals(beacon.props.get(62), ADV_CONTENT_TYPE);
			Assert.assertEquals(beacon.eVars.get(62), ADV_CONTENT_TYPE);
			logger.log(LogStatus.PASS, ADV_CONTENT_TYPE + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + ADV_CONTENT_TYPE);
			e.printStackTrace();
		}
	}
	
}