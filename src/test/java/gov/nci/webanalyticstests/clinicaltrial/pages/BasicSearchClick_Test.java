package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
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
			Assert.assertTrue(beacon.hasEvent(37));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|display");
			logger.log(LogStatus.PASS, "Basic CTS 'Display' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic CTS 'Start' click event
	@Test(groups = { "Analytics" })
	public void testBasicStart() {
		System.out.println("Basic CTS 'Start' click event:");
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("canc");
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(38));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start");
			logger.log(LogStatus.PASS, "Basic CTS 'Start' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertFalse(beacon.hasEvent(46));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete");
			logger.log(LogStatus.PASS, "Basic CTS 'Complete' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			Assert.assertTrue(beacon.hasEvent(39));
			Assert.assertTrue(beacon.hasEvent(46));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete");
			logger.log(LogStatus.PASS, "Basic CTS 'Complete' click event (keyword) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic CTS 'Abandon' click event (keyword)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonKeyword() {
		System.out.println("Basic CTS 'Abandon' click event (keyword):");
		setupTestMethod();

		try {
			basicSearch.setSearchKeyword("Liver");
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			driver.get(config.goHome());
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|q");
			logger.log(LogStatus.PASS, "Basic CTS 'Abandon' click event (keyword) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic CTS 'Abandon' click event (age)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonAge() {
		System.out.println("Basic CTS 'Abandon' click event (age):");
		setupTestMethod();

		try {
			basicSearch.setSearchAge("55");
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			driver.get(config.goHome());
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|a");
			logger.log(LogStatus.PASS, "Basic CTS 'Abandon' click event (age) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}

	}

	/// Test Basic CTS 'Abandon' click event (zip)
	@Test(groups = { "Analytics" })
	public void testBasicAbandonZip() {
		System.out.println("Basic CTS 'Abandon' click event (zip):");
		setupTestMethod();

		try {
			System.out.println("CTS \"Abandon\" click event for zip:");
			basicSearch.setSearchZip("20001");
			Actions action = new Actions(driver);
			action.pause(1000).perform();
			driver.get(config.goHome());
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(40));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|z");
			logger.log(LogStatus.PASS, "Basic CTS 'Abandon' click event (zip) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
			logger.log(LogStatus.PASS, "Basic CTS error message click event (age): passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "z|Please enter a valid 5 digit ZIP code.");
			logger.log(LogStatus.PASS, "Test Basic CTS error message click event (age) passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "submit|attempted form submit with errors");
			logger.log(LogStatus.PASS, "Test Basic CTS error message submit click event passed.");
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
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
