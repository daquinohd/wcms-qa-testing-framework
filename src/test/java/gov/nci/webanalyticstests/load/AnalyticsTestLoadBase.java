package gov.nci.webanalyticstests.load;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsPageLoad;
import gov.nci.WebAnalytics.AnalyticsRequest;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class AnalyticsTestLoadBase extends AnalyticsTestBase {

	private AnalyticsPageLoad analyticsPageLoad;
	private AnalyticsRequest beacon;
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupPageLoad() throws MalformedURLException, UnsupportedEncodingException {
		analyticsPageLoad = new AnalyticsPageLoad(driver);
	}	

	// TODO: refactor has..() methods
	// TODO: fix hasEvent() arg
	// TODO: create generic param test
	/// Load events have been captured
	@Test(groups = { "Analytics" })
	public void testHarAndBeacons() {
		analyticsPageLoad.gotoMultiplePages();
		beacon = getLoadBeacon();
		Assert.assertTrue(harUrlList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		Assert.assertTrue(beacon != null);
		logger.log(LogStatus.PASS, "Load events have been captured.");
	}
	
	/// Event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testLoadGeneral() {
		analyticsPageLoad.gotoMultiplePages();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(47));		
		for(AnalyticsRequest beacon : loadBeacons) {
			String[] evts = beacon.getEvents();	
			Assert.assertTrue(evts[0].contains("event1"));
			Assert.assertTrue(evts[1].contains("event47"));
		}		
		logger.log(LogStatus.PASS, "Load event values are correct.");				
	}
	
	/// Blog post pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testBlogPostLoad() {
		analyticsPageLoad.gotoBlogPostPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasEvent(53));
		Assert.assertTrue(beacon.hasProp(6, "Addressing Cancer Drug Costs and Value"));
		Assert.assertTrue(beacon.hasProp(44, "CancerCurrents"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/news-events/cancer-currents-blog/2018/presidents-cancer-panel-drug-prices"));
		// hasHier()
		logger.log(LogStatus.PASS, "Blog post pageload values are correct.");		
	}
	
	/// Blog series pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testBlogSeriesLoad() {
		analyticsPageLoad.gotoBlogSeriesPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(44, "CancerCurrents"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/news-events/cancer-currents-blog"));
		Assert.assertTrue(beacon.haseVar(44, "CancerCurrents"));
		logger.log(LogStatus.PASS, "Blog series pageload values are correct.");
	}
	
	/// CTHP patient pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testCTHPPatientLoad() {
		analyticsPageLoad.gotoCTHPPatient();
		beacon = getLoadBeacon(); 
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(8, "english"));
		Assert.assertTrue(beacon.hasProp(6, "Bladder cancer"));
		// "\u2014" is an emdash
		Assert.assertTrue(beacon.hasProp(10, "Bladder Cancer\u2014Patient Version - National Cancer Institute"));
		Assert.assertTrue(beacon.hasProp(44, "Cancer Types Landing Page"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/types/bladder"));
		Assert.assertTrue(beacon.haseVar(44, "Cancer Types Landing Page"));
		logger.log(LogStatus.PASS, "CTHP Patient pageload values are correct.");
	}
	
	/// CTHP HP pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testCTHPHPLoad() {
		analyticsPageLoad.gotoCTHPHP();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(6, "Breast cancer"));
		// "\u2014" is an emdash
		Assert.assertTrue(beacon.hasProp(10, "Breast Cancer\u2014Health Professional Version - National Cancer Institute"));
		Assert.assertTrue(beacon.hasProp(44, "Cancer Types Landing Page"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/types/breast/hp"));
		logger.log(LogStatus.PASS, "CTHP HP pageload values are correct.");
	}
	
	/// Innerpage load returns expected values
	@Test(groups = { "Analytics" })
	public void testInnerPageLoad() {
		analyticsPageLoad.gotoInnerPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(6, "Visitor information"));
		Assert.assertTrue(beacon.hasProp(10, "Visitor Information - National Cancer Institute"));
		Assert.assertTrue(beacon.hasProp(42, "Normal"));
		Assert.assertTrue(beacon.hasProp(44, "Visitor information"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/about-nci/visit"));
		Assert.assertTrue(beacon.haseVar(44, "Visitor information"));
		logger.log(LogStatus.PASS, "Inner page load values are correct.");
	}
	
	/// Landing pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testLandingPageLoad() {
		analyticsPageLoad.gotoLandingPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(3, "/research"));
		Assert.assertTrue(beacon.hasProp(6, "Cancer Research"));
		Assert.assertTrue(beacon.hasProp(10, "Cancer Research - National Cancer Institute"));
		Assert.assertTrue(beacon.hasProp(42, "Normal"));
		Assert.assertTrue(beacon.hasProp(44, "Research Landing Page"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/research"));
		Assert.assertTrue(beacon.haseVar(44, "Research Landing Page"));
		logger.log(LogStatus.PASS, "Landing page load values are correct.");
	}
	
	/// PDQ pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testPDQPageLoad() {
		analyticsPageLoad.gotoPDQPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(3, "/types/breast"));
		Assert.assertTrue(beacon.hasProp(6, "Breast Cancer Prevention"));
		Assert.assertTrue(beacon.hasProp(7, "patient"));
		// "\u2014" is an emdash
		// "\u00ae" is the registered mark.
		Assert.assertTrue(beacon.hasProp(10, "Breast Cancer Prevention (PDQ\u00ae)\u2014Patient Version - National Cancer Institute"));
		Assert.assertTrue(beacon.hasProp(44, "Cancer Types Landing Page"));
		//Assert.assertTrue(beacon.hasProp(65, "2"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/types/breast/patient/breast-prevention-pdq"));
		Assert.assertTrue(beacon.haseVar(44, "Cancer Types Landing Page"));
		logger.log(LogStatus.PASS, "PDQ page load values are correct.");
	}
	
	/// Topic page load returns expected values
	@Test(groups = { "Analytics" })
	public void testTopicPageLoad() {
		analyticsPageLoad.gotoTopicPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(44, "NCI Grants Process"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/grants-training/apply-grant"));
		Assert.assertTrue(beacon.haseVar(44, "NCI Grants Process"));
		logger.log(LogStatus.PASS, "Topioc page load values are correct.");
	}
	
	/// Spanish page load returns expected values
	@Test(groups = { "Analytics" })
	public void testSpanishPageLoad() {
		analyticsPageLoad.gotoSpanishPage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(3, "/espanol"));
		// "\u00e1" is lower-case 'a' with acute accent.
		// "\u00f1" is lower-case 'n' with tilde.
		Assert.assertTrue(beacon.hasProp(6, "C\u00e1ncer en espa\u00f1ol"));
		Assert.assertTrue(beacon.hasProp(8, "Spanish"));
		Assert.assertTrue(beacon.hasProp(44, "NCI Home - Spanish"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/espanol"));
		Assert.assertTrue(beacon.haseVar(2, "Spanish"));
		Assert.assertTrue(beacon.haseVar(44, "NCI Home - Spanish"));		
		logger.log(LogStatus.PASS, "Spanish home page load values are correct.");		
	}

	/// Appmodule pageload returns expected values
	//@Test(groups = { "Analytics" })
	public void testAppModulePageLoad1() {
		analyticsPageLoad.gotoAppModulePage();
		beacon = getLoadBeacon();
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(3, "/publications/dictionaries/cancer-terms"));
		Assert.assertTrue(beacon.hasProp(6, "NCI Dictionary of Cancer Terms"));
		Assert.assertTrue(beacon.hasProp(8, "English"));
		Assert.assertTrue(beacon.hasProp(44, "Dictionary of Cancer Terms"));
		Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/publications/dictionaries/cancer-terms"));
		Assert.assertTrue(beacon.haseVar(44, "Dictionary of Cancer Terms"));
		logger.log(LogStatus.PASS, "Appmodule (dictionary) page load values are correct.");		
	}
//
//	/// Appmodule pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testAppModulePageLoad2() {
//		pageLoad.gotoAppModulePage();
//		setLoadBeacons();
//	}		
//		
//	/// CTHP HP pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testAdvSearchPageLoad() {
//		pageLoad.gotoAdvSearchPage();
//		setLoadBeacons();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		/** more **/
//	}
//	
//	@Test(groups = { "Analytics" })
//	public void testBasicSearchPageLoad() {
//		pageLoad.gotoBasicSearchPage();
//		setLoadBeacons();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		/** more **/
//	}		
//	
//	@Test(groups = { "Analytics" })
//	public void testCTResultsPageLoad() {
//		pageLoad.gotoResultsPage();
//		setLoadBeacons();
//		Assert.assertTrue(beacon.hasEvent(1));
//		Assert.assertTrue(beacon.hasEvent(47));
//		/** more **/
//	}			
//
//	@Test(groups = { "Analytics" })
//	public void testCTViewPageLoad() {
//		Assert.assertTrue((2 + 2) == 4);
//		/** more **/
//	}
}
