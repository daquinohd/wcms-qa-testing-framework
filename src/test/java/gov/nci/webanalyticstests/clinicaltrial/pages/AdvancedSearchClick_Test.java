package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class AdvancedSearchClick_Test extends AnalyticsTestClickBase {

	private AdvanceSearch advancedSearch;
	private Beacon beacon;
	private Actions action;
	
	// TODO: Multi_select abandon
	// TODO: Capture complete_Scrolling
	// TODO: error on submit	
	@BeforeMethod(groups = { "Analytics" }) 
	// Run before each test method in this class
	public void setup() {
		driver.get(config.getPageURL("AdvanceSearchPageURL"));
		try {
			// Create search page with chat prompt suppressed.					
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			advancedSearch = new AdvanceSearch(driver, chatPrompt);
			action = new Actions(driver);
		} catch (Exception e) {
			advancedSearch = null;
			logger.log(LogStatus.ERROR, "Error creating Advanced Search page.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testAdvancedDisplay() {
		try {
			/* Do browser actions and get our beacon object **/
			System.out.println("CTS \"Display\" click event:");
			beacon = getBeacon();
	
			/* Do assertions and log result */ 
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(37));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|display");
			logger.log(LogStatus.PASS, "CTS Advanced 'display' value test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error displaying advanced CTS form.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedStart() {
		try {
			System.out.println("CTS \"Start\" click event:");
			advancedSearch.getCancerType("lung");
			action.pause(1000).perform();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(38));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start");
			logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error starting advanced CTS form.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedComplete() {
		try {
			System.out.println("CTS \"Complete\" click event:");
			advancedSearch.getAge(55);
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertFalse(beacon.hasEvent(46));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
			logger.log(LogStatus.PASS, "CTS Basic 'complete' value test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error completing advanced CTS form.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedSubmit() {
		try {
			System.out.println("CTS \"Complete\" click event:");
			advancedSearch.getAge(55);
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertFalse(beacon.hasEvent(46));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
			logger.log(LogStatus.PASS, "CTS Basic 'complete' value test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error completing advanced CTS form.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedCtSelect() {
		try {
			System.out.println("CTS 'ct_select' click event:");
			advancedSearch.getCancerType("lung");
			action.pause(1000).perform();
			driver.get(config.goHome());
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|ct-select");
			logger.log(LogStatus.PASS, "CTS 'ct_select' click value test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error firing off CTS 'ct_select' click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedMultiSelect() {
		try {
			System.out.println("CTS 'multi_select' click event:");
			advancedSearch.getCancerType("Lung Cancer");
			advancedSearch.getCancerSubType("Adenosquamous Lung Cancer");
			driver.get(config.goHome());
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|st-multiselect");
			logger.log(LogStatus.PASS, "CTS 'multi_select' click value test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error firing off CTS 'multi_select' click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedAbandonAge() {
		try {
			System.out.println("CTS \"Abandon\" click event for age:");
			advancedSearch.setAge(55);
			action.pause(1000).perform();
			driver.get(config.goHome());
			beacon = getBeacon();
	
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|a");
			logger.log(LogStatus.PASS, "CTS \"Abandon\" click event for age passed.");
		}
		catch (Exception e) {
			Assert.fail("Error abandoning advanced CTS form.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedAbandonOrg() {
		try {
			System.out.println("CTS \"Abandon\" click event for lead org:");
			advancedSearch.setLeadOrganization("mayo");
			action.pause(1000).perform();
			driver.get(config.goHome());
			beacon = getBeacon();
	
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|lo");
			logger.log(LogStatus.PASS, "CTS \"Abandon\" click event for lead org passed.");
		}
		catch (Exception e) {
			Assert.fail("Error abandoning advanced CTS form.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvancedErrorAge() {
		try {
			System.out.println("CTS form age error:");
			advancedSearch.setAge(999);
			advancedSearch.setKeywordPhrase("foo");
			beacon = getBeacon();
	
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|error");
			Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
			logger.log(LogStatus.PASS, "CTS form error test passed.");
		}
		catch (Exception e) {
			Assert.fail("Error getting CTS form error event.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertTrue(beacon.channels.length() > 0);
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), "english");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_advanced");
	}
	
}

