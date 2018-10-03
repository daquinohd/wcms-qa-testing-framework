package gov.nci.webanalyticstests.pdq.pages;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.pdq.common.PdqRightNav;
import gov.nci.pdq.pages.PdqPage;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class PdqCisClick_Test extends AnalyticsTestClickBase {

	private final String REGEX_PAGE_SCROLL = "^\\d{1,3}pct\\|\\d{1,3}pct\\|\\d{1,5}px\\|\\/.*$"; // 30pct|30pct|3215px|/research

	private PdqPage pdqPage;
	private PdqRightNav pdqRightNav;
	private Actions action;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	// Go to the page, build the page and nav objects, then pause to load
	// everything.
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			pdqPage = new PdqPage(driver);
			pdqRightNav = pdqPage.getRightNav();

			action = new Actions(driver);
			action.pause(500).perform();
		} catch (Exception e) {
			Assert.fail("Error building PDQ page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	// Test Right Nav current section click
	@Test(groups = { "Analytics" }, dataProvider = "RightNavData")
	public void testRightNavCurrentClick(String path, String linkName, String pageDetail) {
		System.out.println("Test Right Nav current section click (" + linkName + "):");
		setupTestMethod(path);

		try {
			pdqRightNav.clickSection(linkName);
			action.pause(500).perform(); // Wait until loaded, then click again
			pdqRightNav.clickSection(linkName);
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "RightNavLink-");
			Assert.assertEquals(beacon.props.get(27), linkName);
			Assert.assertEquals(beacon.props.get(66), "pdq_" + linkName.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(49), beacon.props.get(27));
			logger.log(LogStatus.PASS, "Test Right Nav current section click (" + linkName + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}

	}

	// Test Right Nav section details info click
	@Test(groups = { "Analytics" }, dataProvider = "RightNavData")
	public void testRightNavDetail(String path, String linkName, String pageDetail) {
		System.out.println("Test Right Nav section details info click (" + linkName + "):");
		setupTestMethod(path);

		try {
			driver.get(config.goHome() + path);
			pdqRightNav.clickSection(linkName);
			action.pause(500).perform(); // Make sure the second click event has fired off
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "GlobalLinkTrack");
			Assert.assertEquals(beacon.props.get(28), pageDetail);
			Assert.assertTrue(beacon.props.get(48).matches(REGEX_PAGE_SCROLL));
			logger.log(LogStatus.PASS, "Test Right Nav section details info click (" + linkName + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path, section, and page detail values.	 * 
	 * @return a path and section string
	 */
	@DataProvider(name = "RightNavData")
	private Iterator<Object[]> getRightNavData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount("RightNav"); rowNum++) {
			String path = excelReader.getCellData("RightNav", "Path", rowNum);
			String sectionName = excelReader.getCellData("RightNav", "SectionName", rowNum);
			String pageDetail = excelReader.getCellData("RightNav", "PageDetail", rowNum);
			Object ob[] = { path, sectionName, pageDetail };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

}