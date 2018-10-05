package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.clinicalTrial.pages.TrialDetailView;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class TrialDetailViewClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "TrialDetailView";

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
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			trialView = new TrialDetailView(driver, chatPrompt);

			driver.navigate().refresh();
			Actions action = new Actions(driver);
			action.pause(500).perform();
		} catch (Exception ex) {
			Assert.fail("Error loading CTS Trial Detail View: " + path);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Trial View - start over click event
	@Test(dataProvider = "AdvancedViewFullPath", groups = { "Analytics" })
	public void testTrialViewStartOverClick(String fullPath) {
		System.out.println("Test Trial View - start over click event: ");
		setupTestMethod(fullPath);

		try {
			trialView.clickStartOverNoNav();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(49));
			Assert.assertEquals(beacon.linkName, "CTStartOverClick");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start over");
			logger.log(LogStatus.PASS, "Test Trial View - start over click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Trial View - open all sections click event
	@Test(dataProvider = "AdvancedViewFullPath", groups = { "Analytics" })
	public void testTrialViewOpenAllClick(String fullPath) {
		System.out.println("Test Trial View - open all sections click event:");
		setupTestMethod(fullPath);

		try {
			trialView.clickOpenAll();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|none|none|open-all");
			logger.log(LogStatus.PASS, "Test Trial View - open all sections click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Trial View - close all sections click event
	@Test(dataProvider = "AdvancedViewFullPath", groups = { "Analytics" })
	public void testTrialViewCloseAllClick(String fullPath) {
		System.out.println("Test Trial View - close all sections click event: ");
		setupTestMethod(fullPath);

		try {
			trialView.clickCloseAll();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|none|none|close-all");
			logger.log(LogStatus.PASS, "Test Trial View - close all sections click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Trial View - expand section click event
	@Test(dataProvider = "BasicViewFullPath", groups = { "Analytics" })
	public void testTrialViewSectionExpandClick(String fullPath) {
		System.out.println("Test Trial View - expand section click event: ");
		setupTestMethod(fullPath);

		try {
			trialView.clickComponent("#trial-ids h2");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|trial-ids|Trial IDs|expand");
			logger.log(LogStatus.PASS, "Test Trial View - expand section click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Trial View - collapse section click event
	@Test(dataProvider = "BasicViewFullPath", groups = { "Analytics" })
	public void testTrialViewSectionCollapseClick(String fullPath) {
		System.out.println("Test Trial View - collapse section click event:");
		setupTestMethod(fullPath);

		try {
			trialView.clickSection("description");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.props.get(41), "clinical_trial|trial-description|Description|collapse");
			logger.log(LogStatus.PASS, "Test Trial View - collapse section click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Trial View - 'Print' click event
	@Test(dataProvider = "CustomViewFullPath", groups = { "Analytics" })
	public void testTrialViewPrintClick(String fullPath) {
		System.out.println("Test Trial View - 'Print' click event:");
		setupTestMethod(fullPath);

		try {
			trialView.clickPrintLink();
			Actions action = new Actions(driver);
			action.pause(500).perform();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "CTSLink");
			Assert.assertEquals(beacon.props.get(5), "print|" + beacon.pageName);
			logger.log(LogStatus.PASS, "Test Trial View - 'Print' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Trial View - 'email' click event
	@Test(dataProvider = "CustomViewFullPath", groups = { "Analytics" })
	public void testTrialViewEmailClick(String fullPath) {
		System.out.println("Test Trial View - 'email' click event:");
		setupTestMethod(fullPath);

		try {
			trialView.clickEmailLink();
			Actions action = new Actions(driver);
			action.pause(500).perform();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "CTSLink");
			Assert.assertEquals(beacon.props.get(5), "email|" + beacon.pageName);
			logger.log(LogStatus.PASS, "Test Trial View - 'email' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "AdvancedViewFullPath")
	public Iterator<Object[]> getAdvancedViewFullPath() {
		return getTrialViewClickData("Advanced");
	}

	@DataProvider(name = "BasicViewFullPath")
	public Iterator<Object[]> getBasicViewFullPath() {
		return getTrialViewClickData("Basic");
	}

	@DataProvider(name = "CustomViewFullPath")
	public Iterator<Object[]> getCustomViewFullPath() {
		return getTrialViewClickData("Custom");
	}

	/**
	 * Get a URL data object, filtered by search type.
	 * 
	 * @param myType
	 * @return
	 */
	private Iterator<Object[]> getTrialViewClickData(String myType) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		String path = "/about-cancer/treatment/clinical-trials/search/v";

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String parms = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String type = excelReader.getCellData(TESTDATA_SHEET_NAME, "ContentType", rowNum);
			if (type.equalsIgnoreCase(myType)) {
				Object ob[] = { path + parms };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
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

		Assert.assertTrue(beacon.hasSuite("nciclinicaltrials", currentUrl));
		Assert.assertTrue(beacon.suites.length > 1);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
