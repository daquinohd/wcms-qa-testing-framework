package gov.nci.webanalyticstests.load.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class SwsResultsPage_Test extends AnalyticsTestLoadBase {
	
	private AnalyticsPageLoad analyticsPageLoad;	
	private SitewideSearchForm swSearchForm;
	private Beacon beacon;

	private final String RESULTS_PATH_EN = "/search/results";
	private final String RESULTS_PATH_ES = "/espanol/buscar/resultados";
	private final String SEARCH_TERM = "Tumor";	

	// Verify analytics load values for sitewide cancer term search results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageEn() {
		try {
			driver.get(config.goHome());
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchForm.clickSearchButton();
			System.out.println("Sitewide search term: " + SEARCH_TERM);
		    beacon = getBeacon();
		    
		    analyticsPageLoad.setPageTitle("NCI Search Results - National Cancer Institute");
			doCommonLoadAssertions(beacon, analyticsPageLoad, RESULTS_PATH_EN);
			logger.log(LogStatus.PASS, "English results load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics load values for Spanish sitewide cancer term search results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageEs() {
		try {
			driver.get(config.goHome() + "/espanol");
			analyticsPageLoad = new AnalyticsPageLoad(driver);			
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchForm.clickSearchButton();
			System.out.println("Sitewide search term: " + SEARCH_TERM);
		    beacon = getBeacon();
		    
		    analyticsPageLoad.setPageTitle("Resultados - National Cancer Institute");
			doCommonLoadAssertions(beacon, analyticsPageLoad, RESULTS_PATH_ES);
			logger.log(LogStatus.PASS, "English results load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}

	// Verify analytics load values for sitewide cancer term search results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageNoResults() {
		try {
			driver.get(config.goHome() + RESULTS_PATH_EN);
			System.out.println("Direct navigation to results page");
		    beacon = getBeacon();
		    
		    analyticsPageLoad.setPageTitle("NCI Search Results - National Cancer Institute");		    
			doCommonLoadAssertions(beacon, analyticsPageLoad, RESULTS_PATH_EN);
			logger.log(LogStatus.PASS, "English results load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error doing sitewide search.");
			e.printStackTrace();
		}
	}
	
}
