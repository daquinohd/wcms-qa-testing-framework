package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class BasicSearchLoad_Test extends AnalyticsTestLoadBase {

	/**
	 * This test class covers Clinical Trial Basic Search pages
	 */
	
	private final String BASIC_PATH = "/about-cancer/treatment/clinical-trials/search";
	private final String BASIC_CONTENT_TYPE = "Clinical Trials: Basic";

	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;
	
	@Test(groups = { "Analytics" })
	public void testCTSBasicSearchPageLoad() {
		try {
			driver.get(config.goHome() + BASIC_PATH);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(BASIC_CONTENT_TYPE + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			doCommonLoadAssertions(beacon, analyticsPageLoad, BASIC_PATH);
			Assert.assertEquals(beacon.props.get(62), BASIC_CONTENT_TYPE);
			Assert.assertEquals(beacon.eVars.get(62), BASIC_CONTENT_TYPE);
			logger.log(LogStatus.PASS, BASIC_CONTENT_TYPE + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + BASIC_CONTENT_TYPE);
			e.printStackTrace();
		}
	}
	
}