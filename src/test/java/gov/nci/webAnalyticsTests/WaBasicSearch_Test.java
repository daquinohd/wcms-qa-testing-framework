package gov.nci.webAnalyticsTests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;

public class WaBasicSearch_Test extends AnalyticsTestBase {

	// TODO: add wait() to capture begin / abandon events
	private BasicSearch basicSearch;

	@BeforeMethod(groups = { "Analytics" }) 
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
	public void testBasicBegin() {
		/* Do browser actions  **/ 
		basicSearch.setSearchKeyword("stomatitis");
		
		/* Get our beacon object **/ 
		setClickBeacon();
		
		/* Do assertions and log result */ 
		Assert.assertTrue(hasEvent(38));
		Assert.assertFalse(hasEvent(40));
		Assert.assertTrue(hasProp(74, "clinicaltrials_basic|start"));
		Assert.assertTrue(haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void abandonKeyword() {
		/* Enter a keyword field, then abandon the form by navigating away  **/ 
		basicSearch.setSearchKeyword("liver");
		driver.navigate().to(config.getPageURL("HomePage"));
		setClickBeacon();

		/* Verify that the expected values are tracked */
		Assert.assertTrue(hasEvent(40));
		Assert.assertTrue(hasProp(74, "clinicaltrials_basic|abandon|q"));
		Assert.assertTrue(haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void abandonAge() {
		/* Enter an age field, then abandon the form by navigating away  **/ 
		basicSearch.setSearchAge("55");
		driver.navigate().to(config.getPageURL("HomePage"));
		setClickBeacon();

		/* Verify that the expected values are tracked */
		Assert.assertTrue(hasEvent(40));
		Assert.assertTrue(hasProp(74, "clinicaltrials_basic|abandon|a"));
		Assert.assertTrue(haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
}

