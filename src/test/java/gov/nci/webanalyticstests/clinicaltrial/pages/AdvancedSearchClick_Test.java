package gov.nci.webanalyticstests.clinicaltrial.pages;

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
			System.out.println("Path: " + currentUrl);
		} catch (Exception e) {
			Assert.fail("Error loading Advanced CTS results url: " + currentUrl);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Advanced CTS 'Display' click event
	@Test(groups = { "Analytics" })
	public void testAdvancedDisplay() {
		setupTestMethod();

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(37), "Missing event37");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|display");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Start' click event
	@Test(groups = { "Analytics" })
	public void testAdvancedStart() {
		setupTestMethod();

		try {
			advancedSearch.getCancerType("lung");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(38), "Missing event38");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Complete' click event (age)
	@Test(groups = { "Analytics" })
	public void testAdvancedCompleteAge() {
		setupTestMethod();

		try {
			advancedSearch.getAge(55);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "Missing event39");
			Assert.assertFalse(beacon.hasEvent(46), "Unexpected event");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS zip submit click event
	@Test(groups = { "Analytics" })
	public void testAdvancedZipComplete() {
		setupTestMethod();

		try {
			advancedSearch.advSearch_Zipcode("21043");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "Missing event39");
			Assert.assertEquals(beacon.linkName, "formAnalysis|clinicaltrials_advanced|complete");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Complete' click event (investigator)
	@Test(groups = { "Analytics" })
	public void testAdvancedCompleteInv() {
		setupTestMethod();

		try {
			advancedSearch.setSelectedField("#in", "joey jo jo junior");
			advancedSearch.clickSelectedField("input.submit.button");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "Missing event39");
			Assert.assertFalse(beacon.hasEvent(46), "Unexpected event");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Complete' click event (subtype)
	@Test(groups = { "Analytics" })
	public void testAdvancedMultiSelectSubmit() {
		setupTestMethod();

		try {
			advancedSearch.getCancerType("Lung Cancer");
			advancedSearch.getCancerSubType("Adenosquamous Lung Cancer");
			advancedSearch.defaultSearch();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "Missing event39");
			Assert.assertEquals(beacon.linkName, "formAnalysis|clinicaltrials_advanced|complete");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|complete");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Abandon' click event (age)
	@Test(groups = { "Analytics" })
	public void testAdvancedAbandonAge() {
		setupTestMethod();

		try {
			advancedSearch.setAge(55);
			advancedSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "Missing event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|a");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Abandon' click event (organization)
	@Test(groups = { "Analytics" })
	public void testAdvancedAbandonOrg() {
		setupTestMethod();

		try {
			advancedSearch.setLeadOrganization("mayo clinic cancer center lao");
			advancedSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "Missing event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|lo");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS 'Abandon' click event (subtype)
	@Test(groups = { "Analytics" })
	public void testAdvancedMultiSelectAbandon() {
		setupTestMethod();

		try {
			advancedSearch.getCancerType("Lung Cancer");
			advancedSearch.getCancerSubType("Adenosquamous Lung Cancer");
			advancedSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "Missing event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|abandon|st-multiselect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS age error message click event
	@Test(groups = { "Analytics" })
	public void testAdvancedErrorAge() {
		setupTestMethod();

		try {
			advancedSearch.setAge(999);
			advancedSearch.setKeywordPhrase("foo");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "Missing event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|error");
			Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Advanced CTS Error Submit click event (zip):
	@Test(groups = { "Analytics" })
	public void testAdvancedZipError() {
		setupTestMethod();

		try {
			advancedSearch.enterZipCode("blahh");
			advancedSearch.pressEnterOnField("input.submit.button");
			advancedSearch.pressEnterOnField("input.submit.button"); // Second call to set 'submit' error

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "Missing event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|error");
			Assert.assertEquals(beacon.props.get(75), "submit|attempted form submit with errors");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
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

		Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", currentUrl), "Missing NCT suite");
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_advanced");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
