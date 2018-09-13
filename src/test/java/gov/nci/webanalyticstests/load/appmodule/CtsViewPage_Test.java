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

public class CtsViewPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Clinical Trial detailed view Page
	 */

	// TODO: Handle broken common variables	
	// View details page 
	//prop16: NCTID
	//prop62: Clinical Trials: <searchtype>
	//eVar62: Clinical Trials: <searchtype>
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "CTSViewPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// CTSView page loads return expected values
	@Test(dataProvider = "CTSAdvancedViewPage", groups = { "Analytics" })
	public void testCTSAdvancedViewPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			String[] pathNoQuery = path.split("\\?");
			doCommonLoadAssertions(beacon, analyticsPageLoad, pathNoQuery[0]);
			Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Advanced");
			Assert.assertEquals(beacon.eVars.get(62), "Clinical Trials: Advanced");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	/// CTSView page loads return expected values
	@Test(dataProvider = "CTSBasicViewPage", groups = { "Analytics" })
	public void testCTSBasicViewPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			String[] pathNoQuery = path.split("\\?");
			doCommonLoadAssertions(beacon, analyticsPageLoad, pathNoQuery[0]);
			Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Basic");
			Assert.assertEquals(beacon.eVars.get(62), "Clinical Trials: Basic");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}
	
	/// CTSView page loads return expected values
	@Test(dataProvider = "CTSCustomViewPage", groups = { "Analytics" })
	public void testCTSCustomViewPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			String[] pathNoQuery = path.split("\\?");
			doCommonLoadAssertions(beacon, analyticsPageLoad, pathNoQuery[0]);
			Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
			Assert.assertEquals(beacon.eVars.get(62), "Clinical Trials: Custom");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}
	
	@DataProvider(name = "CTSAdvancedViewPage")
	public Iterator<Object[]> getAdvancedViewPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "SearchType", "Advanced");
	}
	
	@DataProvider(name = "CTSBasicViewPage")
	public Iterator<Object[]> getBasicViewPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "SearchType", "Basic");
	}

	@DataProvider(name = "CTSCustomViewPage")
	public Iterator<Object[]> getCustomViewPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "SearchType", "Custom");
	}
	
}