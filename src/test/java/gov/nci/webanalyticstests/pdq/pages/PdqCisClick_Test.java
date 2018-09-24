package gov.nci.webanalyticstests.pdq.pages;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

	private PdqPage pdqPage;
	private PdqRightNav pdqRightNav;

	private Actions action;
	private String testDataFilePath;

	private final String REGEX_PAGE_SCROLL = "^\\d{1,3}pct\\|\\d{1,3}pct\\|\\d{1,5}px\\|\\/.*$"; // 30pct|30pct|3215px|/research

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	@BeforeMethod(groups = { "Analytics" })
	public void setupTestMethod() {
		try {
			action = new Actions(driver);
			pdqPage = new PdqPage(driver);
			pdqRightNav = pdqPage.getRightNav();
			action.pause(500).perform();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building PDQ page object.");
		}
	}

	@Test(groups = { "Analytics" }, dataProvider = "RightNavData")
	public void testRightNavCurrentClick(String path, String linkName, String pageDetail) {
		try {
			System.out.println(linkName + " click: ");
			driver.get(config.goHome() + path);
			pdqRightNav.clickSection(linkName);
			action.pause(500).perform(); // Wait until loaded, then click again
			pdqRightNav.clickSection(linkName);
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "RightNavLink-");
			Assert.assertEquals(beacon.props.get(27), linkName);
			Assert.assertEquals(beacon.props.get(66), "pdq_" + linkName.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(49),  beacon.props.get(27));
			logger.log(LogStatus.PASS, linkName + " click values are correct.");
		} catch (Exception e) {
			Assert.fail("Error clicking " + linkName);
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, dataProvider = "RightNavData")
	public void testRightNavDetail(String path, String linkName, String pageDetail) {
		try {
			System.out.println(linkName + " detail click: ");
			driver.get(config.goHome() + path);
			pdqRightNav.clickSection(linkName);
			action.pause(500).perform(); // Make sure the second click event has fired off
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName,  "GlobalLinkTrack");
			Assert.assertEquals(beacon.props.get(28), pageDetail);
			Assert.assertTrue(beacon.props.get(48).matches(REGEX_PAGE_SCROLL));

			logger.log(LogStatus.PASS, linkName + " click values are correct.");
		} catch (Exception e) {
			Assert.fail("Error clicking " + linkName);
			e.printStackTrace();
		}
	}

	/**
	 * Get an iterator data object with path, section, and page detail values.
	 * 
	 * @return a path and section string
	 */
	@DataProvider(name = "RightNavData")
	public Iterator<Object[]> getRightNavData() {
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