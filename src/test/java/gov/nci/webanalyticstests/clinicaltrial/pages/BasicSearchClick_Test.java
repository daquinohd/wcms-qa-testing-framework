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
		System.out.println("Basic CTS 'Display' click event:");
		setupTestMethod();

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(37), "Missing event37");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|display");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Start' click event
	@Test(groups = { "Analytics" })
	public void testBasicStart() {
		System.out.println("Basic CTS 'Start' click event:");
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("canc");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(38), "Missing event38");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Complete' click event
	@Test(groups = { "Analytics" })
	public void testBasicComplete() {
		System.out.println("Basic CTS 'Complete' click event:");
		setupTestMethod();

		try {
			basicSearch.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "Missing event39");
			Assert.assertFalse(beacon.hasEvent(46), "Unexpected event");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Complete' click event (keyword)
	@Test(groups = { "Analytics" })
	public void testBasicKeywordMatch() {
		System.out.println("Basic CTS 'Complete' click event (keyword):");
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("Ampulla of Vater Cancer");
			basicSearch.clickSearchButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(39), "Missing event39");
			Assert.assertTrue(beacon.hasEvent(46), "Missing event46");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Abandon' click event (keyword)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonKeyword() {
		System.out.println("Basic CTS 'Abandon' click event (keyword):");
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("Liver");
			basicSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "Missing event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|q");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Abandon' click event (age)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonAge() {
		System.out.println("Basic CTS 'Abandon' click event (age):");
		setupTestMethod();

		try {
			basicSearch.setSearchAge("55");
			basicSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "Missing event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|a");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS 'Abandon' click event (zip)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonZip() {
		System.out.println("Basic CTS 'Abandon' click event (zip):");
		setupTestMethod();

		try {
			basicSearch.setSearchZip("20001");
			basicSearch.abandon(config.goHome());

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40), "Missing event40");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|z");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS error message click event (age)
	@Test(groups = { "Analytics" })
	public void testBasicErrorAge() {
		System.out.println("Test Basic CTS error message click event (age):");
		setupTestMethod();

		try {
			basicSearch.setSearchAge("abc");
			basicSearch.setSearchKeyword("");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "Missing event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS error message click event (zip):
	@Test(groups = { "Analytics" })
	public void testBasicErrorZip() {
		System.out.println("Test Basic CTS error message click event (zip):");
		setupTestMethod();

		try {
			basicSearch.setSearchZip("abcde");
			basicSearch.setSearchKeyword("");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "Missing event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "z|Please enter a valid 5 digit ZIP code.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Basic CTS error message submit click event
	@Test(groups = { "Analytics" })
	public void testBasicErrorSubmit() {
		System.out.println("Test Basic CTS error message submit click event:");
		setupTestMethod();

		try {
			basicSearch.setSearchZip("abcde");
			basicSearch.clickSelectedField(".btn-group input.submit");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41), "Missing event41");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "submit|attempted form submit with errors");
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

		Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", currentUrl), "Missing NCT suite");
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
