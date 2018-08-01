package gov.nci.webAnalyticsTests;

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.sitewideSearch.common.SitewideSearchForm;
import gov.nci.sitewideSearch.pages.SitewideSearchResults;
import junit.framework.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WaSitewideSearch_Test extends AnalyticsTestBase {
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	
	@BeforeMethod(groups = { "Analytics" }) 
	// Run before each test method in this class	
	public void setup() {
		driver.get(config.goHome());
		try {
			swSearchForm = new SitewideSearchForm(driver);
			swSearchResults = new SitewideSearchResults();
		} catch (Exception ex) {
			System.out.println("feh");
		}
	}
		
	// Do the thing
	@Test(groups = { "Analytics" })
	public void doTest1() {
	    swSearchForm.setSitewideSearchKeyword("ipilimumab");
	    setClickBeacon();
		Assert.assertTrue(1 == 1);
		logger.log(LogStatus.PASS, "Test 1 passed");
	}
	
	/// Test test
	@Test(groups = { "Analytics" })
	public void doTest2() {
		Assert.assertTrue(2 == 2);
		logger.log(LogStatus.PASS, "Test2 passed. Jeah jeah jeah.");
	}

}
