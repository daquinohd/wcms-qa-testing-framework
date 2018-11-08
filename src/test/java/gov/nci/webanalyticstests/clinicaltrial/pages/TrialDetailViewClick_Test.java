package gov.nci.webanalyticstests.clinicaltrial.pages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.clinicalTrial.pages.TrialDetailView;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class TrialDetailViewClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "TrialDetailView";
	private final String VIEW_TRIAL_PATH = "/about-cancer/treatment/clinical-trials/search/v";

	private TrialDetailView trialView;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTSData");
	}

	/**
	 * Go to the trial view page and create a new TrialDetailView object..
	 * 
	 * @param queryParams
	 */
	private void setupTestMethod(String params) {
		try {
			driver.get(config.goHome() + VIEW_TRIAL_PATH + params);
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			trialView = new TrialDetailView(driver, chatPrompt);
			driver.navigate().refresh();
			System.out.println("Path: " + VIEW_TRIAL_PATH + params);
		} catch (Exception ex) {
			Assert.fail("Error loading CTS Trial Detail View: " + VIEW_TRIAL_PATH + params);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Trial View - start over click event
	@Test(dataProvider = "AdvancedViewFullParams", groups = { "Analytics" })
	public void testTrialViewStartOverClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickStartOverNoNav();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(49), "Missing event49");
			Assert.assertEquals(beacon.linkName, "CTStartOverClick");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start over");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Trial View - open all sections click event
	@Test(dataProvider = "AdvancedViewFullParams", groups = { "Analytics" })
	public void testTrialViewOpenAllClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickOpenAll();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|none|none|open-all");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Trial View - close all sections click event
	@Test(dataProvider = "AdvancedViewFullParams", groups = { "Analytics" })
	public void testTrialViewCloseAllClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickCloseAll();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|none|none|close-all");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Trial View - expand section click event
	@Test(dataProvider = "BasicViewFullParams", groups = { "Analytics" })
	public void testTrialViewSectionExpandClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickComponent("#trial-ids h2");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|trial-ids|Trial IDs|expand");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Trial View - collapse section click event
	@Test(dataProvider = "BasicViewFullParams", groups = { "Analytics" })
	public void testTrialViewSectionCollapseClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickSection("description");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|trial-description|Description|collapse");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Trial View - 'Print' click event
	@Test(dataProvider = "CustomViewFullParams", groups = { "Analytics" })
	public void testTrialViewPrintClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickPrintLink();

			Beacon beacon = getBeacon(0);
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "PrintLink");
			Assert.assertTrue(beacon.hasEvent(17), "Missing event17");
			Assert.assertEquals(beacon.props.get(43), "Print");
			Assert.assertEquals(beacon.props.get(66), "print");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Trial View - 'email' click event
	@Test(dataProvider = "CustomViewFullParams", groups = { "Analytics" })
	public void testTrialViewEmailClick(String params) {
		setupTestMethod(params);

		try {
			trialView.clickEmailLink();

			Beacon beacon = getBeacon(0);
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "eMailLink");
			Assert.assertTrue(beacon.hasEvent(17), "Missing event17");
			Assert.assertEquals(beacon.props.get(43), "Email");
			Assert.assertEquals(beacon.props.get(66), "email");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "AdvancedViewFullParams")
	private Iterator<Object[]> getAdvancedViewParams() {
		String[] columnsToReturn = { "Path" };
		String[] filterInfo = { "ContentType", "Advanced" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "BasicViewFullParams")
	private Iterator<Object[]> getBasicViewParams() {
		String[] columnsToReturn = { "Path" };
		String[] filterInfo = { "ContentType", "Basic" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn, filterInfo);
	}

	@DataProvider(name = "CustomViewFullParams")
	private Iterator<Object[]> getCustomViewParams() {
		String[] columnsToReturn = { "Path" };
		String[] filterInfo = { "ContentType", "Custom" };
		return getFilteredSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn, filterInfo);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for CtsAdvBasicView_Test
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		String currentUrl = driver.getCurrentUrl();
		doCommonClickAssertions(beacon);

		Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", currentUrl), "Missing CT suite");
		Assert.assertTrue(beacon.suites.length > 1, "Missing suite");
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
