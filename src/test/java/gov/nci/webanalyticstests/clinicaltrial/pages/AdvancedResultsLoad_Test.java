package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class AdvancedResultsLoad_Test extends AnalyticsTestLoadBase {

	/**
	 * The CTS Results Page for Advanced search is covered by this test class.
	 */		

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String PARAMS_ALL_TRIALS = "?t=&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_CANCERTYPE = "?t=C3167&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_SUBTYPE = "?t=C4911&st=C147996&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_STAGE = "?t=C35850&st=C150207&stg=C6009&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_ATTRIBUTES = "?t=C9106&st=C27819&stg=C7508&fin=C39877&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_AGE = "?t=&a=99&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_KEYWORD = "?t=&a=&q=paget+disease&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_LOC_VA = "?t=&a=&q=&va=1&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_LOC_NIH = "?t=&a=&q=&loc=4&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_LOC_ZIP = "?t=&a=&q=&loc=1&z=99501&zp=200&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_LOC_CITY = "?t=&a=&q=&loc=2&lcnty=United+States&lst=NM&lcty=Las+Cruces&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_LOC_HOS = "?t=&a=&q=&loc=3&hos=Seattle+Cancer+Care+Alliance&tt=&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_TRIAL_TYPE = "?t=&a=&q=&loc=0&tt=prevention&tt=screening&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_DRUG = "?t=&a=&q=&loc=0&tt=&d=C2654&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_TREATMENT = "?t=&a=&q=&loc=0&tt=&i=C65008&tp=&tid=&in=&lo=&rl=2";
	private final String PARAMS_PHASE = "?t=&a=&q=&loc=0&tt=&tp=IV&tid=&in=&lo=&rl=2";
	private final String PARAMS_TRIAL_ID = "?t=&a=&q=&loc=0&tt=&tp=&tid=NCI-2014-01340&in=&lo=&rl=2";
	private final String PARAMS_INVESTIGATOR = "?t=&a=&q=&loc=0&tt=&tp=&tid=&in=Steven+A.+Rosenberg&lo=&rl=2";
	private final String PARAMS_ORG = "?t=&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=Johns+Hopkins+University+%2F+Sidney+Kimmel+Cancer+Center&rl=2";	
	private final String KITCHEN_SINK = "?t=C4872&st=C8287&stg=C94774&fin=C3036&a=100&q=tumor&loc=2&lcnty=Canada&lcty=Toronto&tt=treatment&d=C287&i=C15541&tp=IV&tid=NCI-0001&in=Edwin+Choy&lo=Alliance+for+Clinical+Trials+in+Oncology&rl=2";
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvAllResultsLoad() {
		System.out.println("Advanced CTS results for all trials: ");
		getAdvResultsLoadBeacon(PARAMS_ALL_TRIALS);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "none");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS - all trials load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvCancerTypeResultsLoad() {
		System.out.println("Advanced CTS results for Cancer Type: ");
		getAdvResultsLoadBeacon(PARAMS_CANCERTYPE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t");
		Assert.assertEquals(beacon.props.get(17), "c3167|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Cancer Type load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsAdvSubTypeResultsLoad() {
		System.out.println("Advanced CTS results for Cancer Subtype: ");
		getAdvResultsLoadBeacon(PARAMS_SUBTYPE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t:st");
		Assert.assertEquals(beacon.props.get(17), "c4911|c147996|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Cancer Subtype load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsAdvStageResultsLoad() {
		System.out.println("Advanced CTS results for Cancer Stage: ");
		getAdvResultsLoadBeacon(PARAMS_STAGE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t:st:stg");
		Assert.assertEquals(beacon.props.get(17), "c35850|c150207|c6009|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Cancer Stage load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvAttributesResultsLoad() {
		System.out.println("Advanced CTS results for Cancer Attributes: ");
		getAdvResultsLoadBeacon(PARAMS_ATTRIBUTES);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t:st:stg:fin");
		Assert.assertEquals(beacon.props.get(17), "c9106|c27819|c7508|c39877|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Cancer Attributes load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvAgeResultsLoad() {
		System.out.println("Advanced CTS results for Age: ");
		getAdvResultsLoadBeacon(PARAMS_AGE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "a");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|99|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Age load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvKeywordResultsLoad() {
		System.out.println("Advanced CTS results for Keyword: ");
		getAdvResultsLoadBeacon(PARAMS_KEYWORD);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "q");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|paget disease");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Keyword load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvLocVALoad() {
		System.out.println("Advanced CTS results for VA locations: ");
		getAdvResultsLoadBeacon(PARAMS_LOC_VA);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "loc:va");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all|va-only");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS VA location load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvLocNIHLoad() {
		System.out.println("Advanced CTS results for NIH locations: ");
		getAdvResultsLoadBeacon(PARAMS_LOC_NIH);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "loc");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "at nih");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS NIH location load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvLocZipLoad() {
		System.out.println("Advanced CTS results for locations by zip code: ");
		getAdvResultsLoadBeacon(PARAMS_LOC_ZIP);
		doCommonClassAssertions(beacon, analyticsPageLoad);		
		Assert.assertEquals(beacon.props.get(15), "loc:z:zp");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "zip|99501|200");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS zipcode location load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvLocCityLoad() {
		System.out.println("Advanced CTS results for locations by city/state: ");
		getAdvResultsLoadBeacon(PARAMS_LOC_CITY);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "loc:lcnty:lst:lcty");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "csc|united states|nm|las cruces");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS city/state location load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvLocHospitalLoad() {
		System.out.println("Advanced CTS results for locations by hospital: ");
		getAdvResultsLoadBeacon(PARAMS_LOC_HOS);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "loc:hos");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "hi|seattle cancer care alliance");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS hospital location load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvTrialTypeResultsLoad() {
		System.out.println("Advanced CTS results for Trial Type: ");
		getAdvResultsLoadBeacon(PARAMS_TRIAL_TYPE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "tt");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "pre,scr|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS Trial Type load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvDrugResultsLoad() {
		System.out.println("Advanced CTS results for Drug Type: ");
		getAdvResultsLoadBeacon(PARAMS_DRUG);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "d");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|c2654|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS drug load values are correct.");
	}
	
	@Test(groups = { "Analytics" })
	public void testCtsAdvTreatmentResultsLoad() {
		System.out.println("Advanced CTS results for treatment: ");
		getAdvResultsLoadBeacon(PARAMS_TREATMENT);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "i");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|c65008");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, "CTS treatment load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsAdvPhaseResultsLoad() {
		System.out.println("Advanced CTS results for phase: ");
		getAdvResultsLoadBeacon(PARAMS_PHASE);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "tp");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "iv|none|none|none");
		logger.log(LogStatus.PASS, "CTS Phase load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsAdvTrialIDResultsLoad() {
		System.out.println("Advanced CTS results for trial ID: ");
		getAdvResultsLoadBeacon(PARAMS_TRIAL_ID);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "tid");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|single:nci-2014-01340|none|none");
		logger.log(LogStatus.PASS, "CTS trial ID load values are correct.");
	}

	
	@Test(groups = { "Analytics" })
	public void testCtsAdvInvestigatorResultsLoad() {
		System.out.println("Advanced CTS results for investigator: ");
		getAdvResultsLoadBeacon(PARAMS_INVESTIGATOR);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "in");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|steven a. rosenberg|none");
		logger.log(LogStatus.PASS, "CTS Investigator load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsAdvOrgResultsLoad() {
		System.out.println("Advanced CTS results for organization: ");
		getAdvResultsLoadBeacon(PARAMS_ORG);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "lo");
		Assert.assertEquals(beacon.props.get(17), "all|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|johns hopkins university / sidney kimmel cancer center");
		logger.log(LogStatus.PASS, "CTS organization load values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testCtsAdvAllParamsLoad() {
		System.out.println("Advanced CTS results for all parameters selected: ");
		getAdvResultsLoadBeacon(KITCHEN_SINK);
		doCommonClassAssertions(beacon, analyticsPageLoad);
		Assert.assertEquals(beacon.props.get(15), "t:st:stg:fin:a:q:loc:lcnty:lcty:tt:d:i:tp:tid:in:lo");
		Assert.assertEquals(beacon.props.get(17), "c4872|c8287|c94774|c3036|100|tumor");
		Assert.assertEquals(beacon.props.get(18), "csc|canada|none|toronto");
		Assert.assertEquals(beacon.props.get(19), "tre|c287|c15541");
		Assert.assertEquals(beacon.props.get(20), "iv|single:nci-0001|edwin choy|alliance for clinical trials in oncology");
		logger.log(LogStatus.PASS, "CTS all parameter load values are correct.");
	}

	
	/**
	 * Go to the results page and retrieve the beacon request object.
	 * @param queryParams
	 */
	private void getAdvResultsLoadBeacon(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
		} catch (Exception ex) {
			Assert.fail("Error loading Advanced CTS results url: " + PATH + queryParams);
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
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_advanced");
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Advanced");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(15), beacon.props.get(15));
		Assert.assertEquals(beacon.eVars.get(17), beacon.props.get(17));
		Assert.assertEquals(beacon.eVars.get(18), beacon.props.get(18));
		Assert.assertEquals(beacon.eVars.get(19), beacon.props.get(19));
		Assert.assertEquals(beacon.eVars.get(20), beacon.props.get(20));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
	
}