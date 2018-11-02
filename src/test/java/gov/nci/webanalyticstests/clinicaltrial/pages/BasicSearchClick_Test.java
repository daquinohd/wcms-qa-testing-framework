package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BasicSearchClick_Test extends AnalyticsTestClickBase {

	private BasicSearch basicSearch;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	/**
	 * Navigate, suppress chat, create advancedSearch object for each test.
	 */
	private void setupTestMethod() {
		driver.get(config.getPageURL("BasicClinicalTrialSearchURL"));
		currentUrl = driver.getCurrentUrl();
		System.out.println("Path: " + currentUrl);

		try {
			// Create search page with chat prompt suppressed.
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			basicSearch = new BasicSearch(driver, chatPrompt);
		} catch (Exception e) {
			Assert.fail("Error loading Basic CTS results url: " + currentUrl);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Basic CTS 'Display' click event
	@Test(groups = { "Analytics" })
	public void testBasicDisplay() {
		setupTestMethod();

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(37), "event37");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|display", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Start' click event
	@Test(groups = { "Analytics" })
	public void testBasicStart() {
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("canc");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(38), "event38");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Complete' click event
	@Test(groups = { "Analytics" })
	public void testBasicComplete() {
		setupTestMethod();

		try {
			basicSearch.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "event39");
			Assert.assertFalse(beacon.hasEvent(46), "unexpected event46");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Complete' click event (keyword)
	@Test(groups = { "Analytics" })
	public void testBasicKeywordMatch() {
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("Ampulla of Vater Cancer");
			basicSearch.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "event39");
			Assert.assertTrue(beacon.hasEvent(46), "event46");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Abandon' click event (keyword)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonKeyword() {
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("Liver");
			basicSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|q", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Abandon' click event (age)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonAge() {
		setupTestMethod();

		try {
			basicSearch.setSearchAge("55");
			basicSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|a", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Abandon' click event (zip)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonZip() {
		setupTestMethod();

		try {
			basicSearch.setSearchZip("20001");
			basicSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|z", "prop74");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS error message click event (age)
	@Test(groups = { "Analytics" })
	public void testBasicErrorAgeClick() {
		setupTestMethod();

		try {
			basicSearch.setSearchAge("abc");
			basicSearch.setSearchZip("35401");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error", "prop74");
			Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.", "prop75");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS error message click event (zip):
	@Test(groups = { "Analytics" })
	public void testBasicErrorZipClick() {
		setupTestMethod();

		try {
			basicSearch.setSearchZip("abcde");
			basicSearch.setSearchAge("55");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error", "prop74");
			Assert.assertEquals(beacon.props.get(75), "z|Please enter a valid 5 digit ZIP code.", "prop75");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS error message submit click event
	@Test(groups = { "Analytics" })
	public void testBasicErrorZipComplete() {
		setupTestMethod();

		try {
			basicSearch.setSearchZip("abcde");
			basicSearch.setSearchAge("55");
			basicSearch.pressEnterOnField("input.submit.button");
			basicSearch.pressEnterOnField("input.submit.button"); // Second call to set 'submit' error

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error", "prop74");
			Assert.assertEquals(beacon.props.get(75), "submit|attempted form submit with errors", "prop75");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);

		Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", currentUrl), "'nciclinicaltrials' suite");
		Assert.assertEquals(beacon.channels, "About Cancer", "'About Cancer' channel");
		Assert.assertEquals(beacon.props.get(8), "english", "prop8");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic", "eVar74");
	}

}
