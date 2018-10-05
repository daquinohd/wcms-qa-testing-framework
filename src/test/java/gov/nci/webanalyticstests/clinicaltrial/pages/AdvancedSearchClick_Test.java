package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class AdvancedSearchClick_Test extends AnalyticsTestClickBase {

	private AdvanceSearch advancedSearch;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	/**
	 * Navigate, suppress chat, create advancedSearch object for each test.
	 */
	private void setupTestMethod() {
		driver.get(config.getPageURL("AdvanceSearchPageURL"));
		currentUrl = driver.getCurrentUrl();

		try {
			// Create search page with chat prompt suppressed.
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			advancedSearch = new AdvanceSearch(driver, chatPrompt);
		} catch (Exception e) {
			Assert.fail("Error loading Advanced CTS results url: " + currentUrl);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Advanced CTS 'Display' click event
	@Test(groups = { "Analytics" })
	public void testAdvancedDisplay() {
		System.out.println("Advanced CTS 'Display' click event:");
		setupTestMethod();

		try {
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(37));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|display");
			logger.log(LogStatus.PASS, "Advanced CTS 'Display' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Start' click event
	@Test(groups = { "Analytics" })
	public void testAdvancedStart() {
		System.out.println("Advanced CTS 'Start' click event:");
		setupTestMethod();

		try {
			advancedSearch.getCancerType("lung");
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(38));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start");
			logger.log(LogStatus.PASS, "Advanced CTS 'Start' click event.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Complete' click event (age)
	@Test(groups = { "Analytics" })
	public void testAdvancedCompleteAge() {
		System.out.println("Advanced CTS 'Complete' click event (age):");
		setupTestMethod();

		try {
			advancedSearch.getAge(55);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertFalse(beacon.hasEvent(46));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
			logger.log(LogStatus.PASS, "Advanced CTS 'Complete' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS zip submit click event
	@Test(groups = { "Analytics" })
	public void testAdvancedZipCompletet() {
		System.out.println("Advanced CTS zip submit click event:");
		setupTestMethod();

		try {
			System.out.println("CTS form zipcode error:");
			advancedSearch.advSearch_Zipcode("21043");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertEquals(beacon.linkName, "formAnalysis|clinicaltrials_advanced|complete");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
			logger.log(LogStatus.PASS, "Advanced CTS zip submit click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Complete' click event (investigator)
	@Test(groups = { "Analytics" })
	public void testAdvancedCompleteInv() {
		System.out.println("Advanced CTS 'Complete' click event (investigator):");
		setupTestMethod();

		try {
			advancedSearch.submitFromSelectedField("#in", "joey jo jo junior");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertFalse(beacon.hasEvent(46));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
			logger.log(LogStatus.PASS, "Advanced CTS 'Complete' click event (investigator) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Complete' click event (subtype)
	@Test(groups = { "Analytics" })
	public void testAdvancedMultiSelectSubmit() {
		System.out.println("Advanced CTS 'Complete' click event (subtype):");
		setupTestMethod();

		try {
			advancedSearch.getCancerType("Lung Cancer");
			advancedSearch.getCancerSubType("Adenosquamous Lung Cancer");
			advancedSearch.defaultSearch();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertEquals(beacon.linkName, "formAnalysis|clinicaltrials_advanced|complete");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
			logger.log(LogStatus.PASS, "Advanced CTS 'Complete' click event (subtype) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Abandon' click event (age)
	@Test(groups = { "Analytics" })
	public void testAdvancedAbandonAge() {
		System.out.println("Advanced CTS 'Abandon' click event (age):");
		setupTestMethod();

		try {
			advancedSearch.setAge(55);
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			driver.get(config.goHome());
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|a");
			logger.log(LogStatus.PASS, "Advanced CTS 'Abandon' click event (age) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Abandon' click event (organization)
	@Test(groups = { "Analytics" })
	public void testAdvancedAbandonOrg() {
		System.out.println("Advanced CTS 'Abandon' click event (organization):");
		setupTestMethod();

		try {
			advancedSearch.setLeadOrganization("mayo");
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			driver.get(config.goHome());
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|lo");
			logger.log(LogStatus.PASS, "Advanced CTS 'Abandon' click event (organization) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS 'Abandon' click event (subtype)
	@Test(groups = { "Analytics" })
	public void testAdvancedMultiSelectAbandon() {
		System.out.println("Advanced CTS 'Abandon' click event (subtype):");
		setupTestMethod();

		try {
			advancedSearch.getCancerType("Lung Cancer");
			advancedSearch.getCancerSubType("Adenosquamous Lung Cancer");
			driver.get(config.goHome());
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|st-multiselect");
			logger.log(LogStatus.PASS, "Test Advanced CTS 'Abandon' click event (subtype) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS age error message click event
	@Test(groups = { "Analytics" })
	public void testAdvancedErrorAge() {
		System.out.println("Advanced CTS age error message click event:");
		setupTestMethod();

		try {
			advancedSearch.setAge(999);
			advancedSearch.setKeywordPhrase("foo");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|error");
			Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
			logger.log(LogStatus.PASS, "Advanced CTS age error message click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Advanced CTS Error Submit click event (zip):
	@Test(groups = { "Analytics" })
	public void testAdvancedZipError() {
		System.out.println("Advanced CTS Error Submit click event (zip):");
		setupTestMethod();

		try {
			advancedSearch.enterZipCode("blahh");
			advancedSearch.clickSelectedField("#cts-submit-floater");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|error");
			Assert.assertEquals(beacon.props.get(75), "submit|attempted form submit with errors");
			logger.log(LogStatus.PASS, "Advanced CTS Error Submit click event (zip) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);

		Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", currentUrl));
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_advanced");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
