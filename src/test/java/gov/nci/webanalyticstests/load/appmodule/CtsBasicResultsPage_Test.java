package gov.nci.webanalyticstests.load.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class CtsBasicResultsPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The CTS Results Page for Basic search is covered by this test class.
	 */

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String PARAMS_ALL_TRIALS = "?q=&t=&a=&z=&rl=1";
	private final String PARAMS_CANCERTYPE = "?t=C9087&a=&z=&rl=1";
	private final String PARAMS_KEYWORD = "?q=medulla&t=&a=&z=&rl=1";
	private final String PARAMS_AGE = "?q=&t=&a=80&z=&rl=1";
	private final String PARAMS_ZIP = "?q=&t=&a=&z=20772&rl=1";
	private final String PARAMS_AGE_CT = "?t=C3869&a=85&z=&rl=1";
	private final String PARAMS_ZIP_KW = "?q=androgen&t=&a=&z=25063&rl=1";
	private final String PARAMS_AGE_ZIP_CT = "?t=C3242&a=101&z=96795&rl=1";
	private final String PARAMS_AGE_ZIP_KW = "?q=plasma&t=&a=70&z=60044&rl=1";
			
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	
	@Test(groups = { "Analytics" })
	public void testCtsBasicNoParamsLoad() {
		System.out.println("Basic CTS results for all trials (no params): ");
		getBasicResultsLoadBeacon("");
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "none");
		Assert.assertEquals(beacon.props.get(17), "none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		logger.log(LogStatus.PASS, "Basic CTS - all trials load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsBasicAllResultsLoad() {
		System.out.println("Basic CTS results for all trials: ");
		getBasicResultsLoadBeacon(PARAMS_ALL_TRIALS);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "none");
		Assert.assertEquals(beacon.props.get(17), "none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		logger.log(LogStatus.PASS, "Basic CTS - all trials load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsBasicCancerTypeResultsLoad() {
		System.out.println("Basic CTS results for Cancer Type: ");
		getBasicResultsLoadBeacon(PARAMS_CANCERTYPE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t");
		Assert.assertEquals(beacon.props.get(17), "typecondition|c9087|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		logger.log(LogStatus.PASS, "Basic CTS Cancer Type load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsBasicKeywordResultsLoad() {
		System.out.println("Basic CTS results for Keyword: ");
		getBasicResultsLoadBeacon(PARAMS_KEYWORD);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "q");
		Assert.assertEquals(beacon.props.get(17), "keyword|medulla|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		logger.log(LogStatus.PASS, "Basic CTS Keyword load values are correct.");
	}	
	
	@Test(groups = { "Analytics" })
	public void testCtsBasicAgeResultsLoad() {
		System.out.println("Basic CTS results for Age: ");
		getBasicResultsLoadBeacon(PARAMS_AGE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "a");
		Assert.assertEquals(beacon.props.get(17), "none|80");
		Assert.assertEquals(beacon.props.get(18), "all");
		logger.log(LogStatus.PASS, "Basic CTS Age load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsBasicZipResultsLoad() {
		System.out.println("Basic CTS results for zip code: ");
		getBasicResultsLoadBeacon(PARAMS_ZIP);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "loc:z");
		Assert.assertEquals(beacon.props.get(17), "none|none");
		Assert.assertEquals(beacon.props.get(18), "zip|20772|none");
		logger.log(LogStatus.PASS, "Basic CTS zip load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsBasicAgeTypeResultsLoad() {
		System.out.println("Basic CTS results for age/cancer type: ");
		getBasicResultsLoadBeacon(PARAMS_AGE_CT);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t:a");
		Assert.assertEquals(beacon.props.get(17), "typecondition|c3869|85");
		Assert.assertEquals(beacon.props.get(18), "all");
		logger.log(LogStatus.PASS, "Basic CTS age/cancer type load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsBasicZipKwResultsLoad() {
		System.out.println("Basic CTS results for age/keyword: ");
		getBasicResultsLoadBeacon(PARAMS_ZIP_KW);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "q:loc:z");
		Assert.assertEquals(beacon.props.get(17), "keyword|androgen|none");
		Assert.assertEquals(beacon.props.get(18), "zip|25063|none");
		logger.log(LogStatus.PASS, "Basic CTS age/keyword load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsBasicAllParamsCtResultsLoad() {
		System.out.println("Basic CTS results for all parameters: ");
		getBasicResultsLoadBeacon(PARAMS_AGE_ZIP_CT);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t:a:loc:z");
		Assert.assertEquals(beacon.props.get(17), "typecondition|c3242|101");
		Assert.assertEquals(beacon.props.get(18), "zip|96795|none");
		logger.log(LogStatus.PASS, "Basic CTS all parameters load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsBasicAllParamsKwResultsLoad() {
		System.out.println("Basic CTS results for all parameters: ");
		getBasicResultsLoadBeacon(PARAMS_AGE_ZIP_KW);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "a:q:loc:z");
		Assert.assertEquals(beacon.props.get(17), "keyword|plasma|70");
		Assert.assertEquals(beacon.props.get(18), "zip|60044|none");
		logger.log(LogStatus.PASS, "Basic CTS all parameters load values are correct.");
	}

	
	/**
	 * Go to the results page and retrieve the beacon request object.
	 * @param queryParams
	 */
	private void getBasicResultsLoadBeacon(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
		} catch (Exception ex) {
			Assert.fail("Error loading Basic CTS results url: " + PATH + queryParams);
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
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_basic");
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Basic");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(15), beacon.props.get(15));
		Assert.assertEquals(beacon.eVars.get(17), beacon.props.get(17));
		Assert.assertEquals(beacon.eVars.get(18), beacon.props.get(18));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
	
}