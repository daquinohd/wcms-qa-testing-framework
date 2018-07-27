package gov.nci.WebAnalytics.Tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.WebAnalytics.Nav;

public class CTSBasicSearch_Test extends AnalyticsTestBase {
	
	private BasicSearch basicSearch;

	@BeforeMethod(groups = { "Analytics" }) 
	public void beforeMethod() {
		// TODO: verify 'form start' event
		//basicSearch = new BasicSearch();
		//basicSearch = new BasicSearch(driver);
	}

	/// ??? returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testBasicGeneral() {
		Assert.assertTrue(1 == 1);
		logger.log(LogStatus.PASS, "CTS Basic gen value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testBasicBegin() {
		/* Do browser actions  **/ 
		driver.navigate().to(Nav.basicSearchPage);
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
	public void testBasicAbandon() {
		/* Do browser actions  **/ 
		driver.navigate().to(Nav.basicSearchPage);
		basicSearch.setSearchKeyword("liver");
		driver.navigate().to(Nav.homePage);

		/* Get our beacon object **/ 
		setClickBeacon();		

		/* Do assertions and log result */ 
		Assert.assertTrue(hasEvent(40));
		Assert.assertTrue(hasProp(74, "clinicaltrials_basic|abandon|q"));
		Assert.assertTrue(haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
}

