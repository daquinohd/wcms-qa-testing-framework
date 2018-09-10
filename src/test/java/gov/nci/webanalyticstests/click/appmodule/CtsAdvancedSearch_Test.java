package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class CtsAdvancedSearch_Test extends AnalyticsTestClickBase {

	private BasicSearch basicSearch;
	private Beacon beacon;
	
	@BeforeMethod(groups = { "Analytics" }) 
	// TODO: add wait() to capture begin / abandon events
	// Run before each test method in this class
	public void setup() {
		driver.get(config.getPageURL("BasicClinicalTrialSearchURL"));
		try {
			// Create search page with chat prompt suppressed.					
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			basicSearch = new BasicSearch(driver, chatPrompt);
		} catch (Exception e) {
			basicSearch = null;
			logger.log(LogStatus.ERROR, "Error creating Basic Search page.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testAdvancedBegin() {
		/* Do browser actions  **/ 
		basicSearch.setSearchKeyword("stomatitis");
		
		/* Get our beacon object **/ 
		// TODO: nullhandling for beacon
		//beacon = getBeacon();
		
		/* Do assertions and log result */ 
		Assert.assertNotEquals(1, 2);
		logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
	}
	
}

