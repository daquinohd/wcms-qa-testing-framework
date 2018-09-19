package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class TrialDetailViewLoad_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Clinical Trial detailed view Page
	 */

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/v";
	private final String PARAMS_ADVANCED_SEARCH = "?t=C4911&q=nivolumab&loc=0&tid=S1609&rl=2&id=NCI-2016-01041&pn=1&ni=10";
	private final String PARAMS_BASIC_SEARCH = "?q=ipilimumab&loc=1&z=20850&zp=100&rl=1&id=NCI-2016-01041&pn=1&ni=10";	
	private final String PARAMS_LISTING_PAGE = "?id=NCI-2016-01041&r=1";
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	
	@Test(groups = { "Analytics" })
	public void testCTSAdvancedViewPageLoad() {
		System.out.println("Trial detail view from Advanced Search results: ");
		getTrialDetailViewLoadBeacon(PARAMS_ADVANCED_SEARCH);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Advanced");
		logger.log(LogStatus.PASS, "Trial detail view from Advanced Search load values are correct.");		
	}

	@Test(groups = { "Analytics" })
	public void testCTSBasicViewPageLoad() {
		System.out.println("Trial detail view from Basic Search results: ");
		getTrialDetailViewLoadBeacon(PARAMS_BASIC_SEARCH);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Basic");
		logger.log(LogStatus.PASS, "Trial detail view from Basic Search load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCTSCustomViewPageLoad() {
		System.out.println("Trial detail view from Custom/Listing results: ");
		getTrialDetailViewLoadBeacon(PARAMS_LISTING_PAGE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
		logger.log(LogStatus.PASS, "Trial detail view from Custom/Listing load values are correct.");
	}
	
	/**
	 * Go to the view page and retrieve the beacon request object.
	 * @param queryParams
	 */
	private void getTrialDetailViewLoadBeacon(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
		} catch (Exception ex) {
			Assert.fail("Error loading CTS Trial View url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared assertions for all tests in this class.
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param path
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, PATH);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(16), getNCTID(analyticsPageLoad));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
	
	/**
	 * Retrieve the NCT ID for a give clinical trial view page.
	 * @param pageLoad
	 * @return
	 */
	private String getNCTID(AnalyticsPageLoad pageLoad) {
		String[] titleId = pageLoad.getMetaTitle().split("-");
		return titleId[titleId.length - 1].trim();
	}
	
}