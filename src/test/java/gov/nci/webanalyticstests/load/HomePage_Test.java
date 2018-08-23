package gov.nci.webanalyticstests.load;

import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class HomePage_Test extends AnalyticsTestBase {

	// TODO: add other values, heir, channel, suite
	// TODO: verify has() logic in AnalyticsRequest
	// TODO: tests for engagement & event47
	// TODO: regexes for dynamic values
	private Beacon beacon;
	
	/**
	 * The following page / content types are covered by this test class:
	 * - Site homepage
	 * - Spanish homepage
	 * - Microsite homepage
	 */

	/// Home pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testSiteHomeLoad() {
		try {
			System.out.println("Home page load event:");
			driver.get(config.goHome());
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));		
			logger.log(LogStatus.PASS, "Home page nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Home page.");
			e.printStackTrace();
		}
	}
		
	/// Home-and-back pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testHomeAndBack() throws MalformedURLException {
		try {
			System.out.println("Home page and back load event:");
			driver.get(config.goHome());
			driver.get(config.getPageURL("SpanishPage"));
			driver.get(config.goHome());
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "Home-and-back nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Home-page-and-back.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testSpanishHomeLoad() {
		try {
			System.out.println("Spanish home page load event:");
			driver.get(config.getPageURL("SpanishPage"));
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/espanol"));
			// "\u00e1" is lower-case 'a' with acute accent.
			// "\u00f1" is lower-case 'n' with tilde.
			Assert.assertTrue(beacon.hasProp(6, "C\u00e1ncer en espa\u00f1ol"));
			Assert.assertTrue(beacon.hasProp(8, "spanish"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Home - Spanish"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/espanol"));
			Assert.assertTrue(beacon.haseVar(2, "spanish"));
			Assert.assertTrue(beacon.haseVar(5));
			Assert.assertTrue(beacon.haseVar(44, "NCI Home - Spanish"));
			logger.log(LogStatus.PASS, "Spanish home page nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Spanish home page.");
			e.printStackTrace();
		}
	}	
	
	@Test(groups = { "Analytics" })
	public void testMicrositeHomeLoad() {
		try {
			System.out.println("Microsite home load event:");
			driver.get(config.getPageURL("MicroSite"));
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/sites/nano"));
			Assert.assertTrue(beacon.hasProp(6, "Nanodelivery Systems and Devices Branch"));
			Assert.assertTrue(beacon.hasProp(8, "english"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/sites/nano"));
			Assert.assertTrue(beacon.haseVar(2, "english"));
			Assert.assertTrue(beacon.haseVar(5));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "Microsite home nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading microsite home page.");
			e.printStackTrace();
		}
	}
	
	// Common assertions
	private void AssertCommon() {
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(1, driver.getCurrentUrl()));
	}
	
}