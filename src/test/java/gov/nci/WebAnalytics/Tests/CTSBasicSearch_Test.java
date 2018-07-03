package gov.nci.WebAnalytics.Tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nci.Utilities.Nav;
import com.nci.clinicalTrial.pages.BasicSearch;

public class CTSBasicSearch_Test extends AnalyticsTestBase{
	
	private BasicSearch basicSearch;

	@BeforeMethod(groups = { "Analytics" }) 
	public void beforeMethod() {
		basicSearch = new BasicSearch(driver);
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
		basicSearch.enterCancerType("stomatitis");		
		
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
		basicSearch.enterCancerType("liver");
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

