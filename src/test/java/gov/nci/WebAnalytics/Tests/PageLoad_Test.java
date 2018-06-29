package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.PageLoad;

public class PageLoad_Test extends AnalyticsTestBase {

	private PageLoad pageLoad;
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void beforeMethod() {
		pageLoad = new PageLoad(driver);
	}	

	// TODO: refactor has..() methods
	// TODO: fix hasEvent() arg
	// TODO: create generic param test
	/// Load events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void testHarAndBeacons() {
		pageLoad.gotoMultiplePages();
		harList = getHarUrlList(proxy);
		loadBeacons = pageLoad.getLoadBeacons(harList);
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		logger.log(LogStatus.PASS, "Load events have been captured.");
	}
	
	/// Event numbers match with their descriptors
	@Test(groups = { "Analytics" }, priority = 2)
	public void testLoadGeneral() {
		pageLoad.gotoMultiplePages();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));		
		for(AnalyticsBase beacon : loadBeacons) {
			Assert.assertTrue(beacon.events[0].contains("event1"));
			Assert.assertTrue(beacon.events[1].contains("event47"));
		}		
		logger.log(LogStatus.PASS, "Load event values are correct.");				
	}
	
	/// Home-and-back pageload returns expected values
	@Test(groups = { "Analytics" }, priority = 3)
	public void testHomeAndBack() throws MalformedURLException {
		// For debugging purposes only..
		pageLoad.goHomeAndBack();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));		
		AnalyticsBase firstLoadBeacon = loadBeacons.get(0);
		// Temporary / debugging tests
		Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage") || firstLoadBeacon.channel.contains("Research"));
		Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstLoadBeacon.events[0].contains("1"));
		logger.log(LogStatus.PASS, "Home-and-back nav values are correct.");	
	}
	
	/// Home pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testHomeLoad() {
		pageLoad.gotoHomePage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 3, "/"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Comprehensive Cancer Information"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "NCI Homepage"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "NCI Homepage"));		
		logger.log(LogStatus.PASS, "Home page nav values are correct.");
	}	
	
	/// Blog post pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testBlogPostLoad() {
		pageLoad.gotoBlogPostPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasEvent(loadBeacons, "event53"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Addressing Cancer Drug Costs and Value"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "CancerCurrents"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/news-events/cancer-currents-blog/2018/presidents-cancer-panel-drug-prices"));
		// hasHier()
		logger.log(LogStatus.PASS, "Blog post pageload values are correct.");		
	}
	
	/// Blog series pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testBlogSeriesLoad() {
		pageLoad.gotoBlogSeriesPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "CancerCurrents"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/news-events/cancer-currents-blog"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "CancerCurrents"));
		logger.log(LogStatus.PASS, "Blog series pageload values are correct.");
	}
	
	/// CTHP patient pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testCTHPPatientLoad() {
		pageLoad.gotoCTHPPatient();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 8, "english"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Bladder cancer"));
		Assert.assertTrue(hasProp(loadBeacons, 10, "Bladder Cancer—Patient Version - National Cancer Institute"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Cancer Types Landing Page"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/types/bladder"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "Cancer Types Landing Page"));
		logger.log(LogStatus.PASS, "CTHP Patient pageload values are correct.");
	}
	
	/// CTHP HP pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testCTHPHPLoad() {
		pageLoad.gotoCTHPHP();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Breast cancer"));
		Assert.assertTrue(hasProp(loadBeacons, 10, "Breast Cancer—Health Professional Version - National Cancer Institute"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Cancer Types Landing Page"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/types/breast/hp"));
		logger.log(LogStatus.PASS, "CTHP HP pageload values are correct.");
	}
	
	/// Innerpage load returns expected values
	@Test(groups = { "Analytics" })
	public void testInnerPageLoad() {
		pageLoad.gotoInnerPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Visitor information"));
		Assert.assertTrue(hasProp(loadBeacons, 10, "Visitor Information - National Cancer Institute"));
		Assert.assertTrue(hasProp(loadBeacons, 42, "Normal"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Visitor information"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/about-nci/visit"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "Visitor information"));
		logger.log(LogStatus.PASS, "Inner page load values are correct.");
	}
	
	/// Landing pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testLandingPageLoad() {
		pageLoad.gotoLandingPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 3, "/research"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Cancer Research"));
		Assert.assertTrue(hasProp(loadBeacons, 10, "Cancer Research - National Cancer Institute"));
		Assert.assertTrue(hasProp(loadBeacons, 42, "Normal"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Research Landing Page"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/research"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "Research Landing Page"));
		logger.log(LogStatus.PASS, "Landing page load values are correct.");
	}
	
	/// PDQ pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testPDQPageLoad() {
		pageLoad.gotoPDQPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 3, "/types/breast"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Breast Cancer Prevention"));
		Assert.assertTrue(hasProp(loadBeacons, 7, "patient"));
		Assert.assertTrue(hasProp(loadBeacons, 10, "Breast Cancer Prevention (PDQ®)—Patient Version - National Cancer Institute"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Cancer Types Landing Page"));
		//Assert.assertTrue(hasProp(loadBeacons, 65, "2"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/types/breast/patient/breast-prevention-pdq"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "Cancer Types Landing Page"));
		logger.log(LogStatus.PASS, "PDQ page load values are correct.");
	}
	
	/// Topic page load returns expected values
	@Test(groups = { "Analytics" })
	public void testTopicPageLoad() {
		pageLoad.gotoTopicPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "NCI Grants Process"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/grants-training/apply-grant"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "NCI Grants Process"));
		logger.log(LogStatus.PASS, "Topioc page load values are correct.");
	}
	
	/// Spanish page load returns expected values
	@Test(groups = { "Analytics" })
	public void testSpanishPageLoad() {
		pageLoad.gotoSpanishPage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 3, "/espanol"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Cáncer en español"));
		Assert.assertTrue(hasProp(loadBeacons, 8, "Spanish"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "NCI Home - Spanish"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/espanol"));
		Assert.assertTrue(haseVar(loadBeacons, 2, "Spanish"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "NCI Home - Spanish"));		
		logger.log(LogStatus.PASS, "Spanish home page load values are correct.");		
	}

	/// Appmodule pageload returns expected values
	//@Test(groups = { "Analytics" })
	public void testAppModulePageLoad1() {
		pageLoad.gotoAppModulePage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 3, "/publications/dictionaries/cancer-terms"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "NCI Dictionary of Cancer Terms"));
		Assert.assertTrue(hasProp(loadBeacons, 8, "English"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Dictionary of Cancer Terms"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/publications/dictionaries/cancer-terms"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "Dictionary of Cancer Terms"));
		logger.log(LogStatus.PASS, "Appmodule (dictionary) page load values are correct.");		
	}
//
//	/// Appmodule pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testAppModulePageLoad2() {
//		pageLoad.gotoAppModulePage();
//		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
//	}		
//		
//	/// CTHP HP pageload returns expected values
//	@Test(groups = { "Analytics" })
//	public void testAdvSearchPageLoad() {
//		pageLoad.gotoAdvSearchPage();
//		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
//		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
//		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
//		/** more **/
//	}
//	
//	@Test(groups = { "Analytics" })
//	public void testBasicSearchPageLoad() {
//		pageLoad.gotoBasicSearchPage();
//		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
//		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
//		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
//		/** more **/
//	}		
//	
//	@Test(groups = { "Analytics" })
//	public void testCTResultsPageLoad() {
//		pageLoad.gotoResultsPage();
//		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
//		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
//		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
//		/** more **/
//	}			
//
//	@Test(groups = { "Analytics" })
//	public void testCTViewPageLoad() {
//		Assert.assertTrue((2 + 2) == 4);
//		/** more **/
//	}
}
