package gov.nci.webanalyticstests.load.appmodule;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class DynamicListingPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Disease Dynamic Listing Page
	 * - Intervention Dynamic Listing Page
	 * - Manually-created Dynamic Listing Page
	 */
	
	/**
	 *  TODO: split into different listing types & add custom assertions
	 *  TODO: create another data provider method to filter test items
	**/
	//Disease Listing Page
	//prop62 -> Clinical Trials: Custom
	//evar10 -> results per page (regex)
	//evar11 -> clinicaltrials_custom
	//evar20 -> disease|<type>|treatment|<name>|total results (regex)
	//evar47 -> clinicaltrials_custom
	//evar62 -> Clinical Trials: Custom
	///about-cancer/treatment/clinical-trials/disease/breast-cancer
	///about-cancer/treatment/clinical-trials/disease/breast-cancer/treatment
	///about-cancer/treatment/clinical-trials/disease/breast-cancer/treatment/trastuzumab
	//
	//
	//Intervention Listing Page
	//prop62 -> Clinical Trials: Custom
	//evar10 -> results per page (regex)
	//evar11 -> clinicaltrials_custom
	//evar20 -> intervention|<intervention type>|??|total results (regex)
	//evar47 -> clinicaltrials_custom
	//evar62 -> Clinical Trials: Custom
	///about-cancer/treatment/clinical-trials/intervention/trastuzumab
	///about-cancer/treatment/clinical-trials/intervention/trastuzumab/treatment
	//
	//
	//Manual Listing Page
	//prop62 -> Clinical Trials: Custom
	//evar10 -> results per page
	//evar11 -> clinicaltrials_custom
	//evar20 -> manual parameters|total results (regex)
	//evar47 -> clinicaltrials_custom
	//evar62 -> Clinical Trials: Custom
	///about-cancer/treatment/clinical-trials/kidney-cancer (edited)
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "DynamicListingPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// DynamicListing page loads return expected values
	@Test(dataProvider = "DynamicListingPageLoad", groups = { "Analytics" })
	public void testDynamicListingPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			DoCommonLoadAssertions(beacon, analyticsPageLoad, path);
			Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
			Assert.assertEquals(beacon.eVars.get(11), "clinicaltrials_custom");
			Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_custom");
			Assert.assertEquals(beacon.eVars.get(62), "Clinical Trials: Custom");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "DynamicListingPageLoad")
	public Iterator<Object[]> getDynamicListingPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}
	
}