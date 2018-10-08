package gov.nci.webanalyticstests.commonobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.commonobjects.MegaMenu;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class MegaMenuClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "MegaMenuInfo";

	private MegaMenu megaMenu;
	private String testDataFilePath;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCommonData");
	}

	/**
	 * Navigate to a page and create a new MegaMenu object.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			currentUrl = driver.getCurrentUrl();
			megaMenu = new MegaMenu(driver, "");
		} catch (Exception e) {
			Assert.fail("Error creating MegaMenu object at path: " + path);
			e.printStackTrace();
		}
	}

	/**
	 * No-arg overload for setupTestMethod(); just go to the homepage.
	 */
	private void setupTestMethod() {
		setupTestMethod("");
	}

	// ==================== Test methods ==================== //

	/// Test MegaMenu Nav Group click event
	@Test(dataProvider = "NavGroupData", groups = { "Analytics" })
	public void testMegaMenuNavGroup(String path, String navGroup, String lang) {
		System.out.println("Test MegaMenu Nav Group click event at \"" + path + "\" :");
		setupTestMethod(path);

		try {
			megaMenu.clickMegaMenuBar(navGroup);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), navGroup);
			Assert.assertEquals(beacon.props.get(55), navGroup);
			Assert.assertTrue(currentUrl.contains(beacon.props.get(56)));
			logger.log(LogStatus.PASS, "Test MegaMenu Nav Group click event at \"" + path + "\" passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test MegaMenu SubNav Group click event
	@Test(dataProvider = "SubNavGroupData", groups = { "Analytics" })
	public void testMegaMenuSubNavGroup(String path, String navGroup, String subNavGroup, String lang) {
		System.out.println("Test MegaMenu SubNav Group click event at \"" + path + "\" :");
		setupTestMethod(path);

		try {
			megaMenu.clickMegaMenuSubNavHeader();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), subNavGroup);
			Assert.assertEquals(beacon.props.get(55), subNavGroup);
			Assert.assertTrue(currentUrl.contains(beacon.props.get(56)));
			logger.log(LogStatus.PASS, "Test MegaMenu SubNav Group click event at \"" + path + "\" passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test MegaMenu SubNav Link click event
	@Test(dataProvider = "SubNavLinkData", groups = { "Analytics" })
	public void testMegaMenuSubNavLink(String path, String navGroup, String subNavGroup, String subNavLink,
			String lang) {
		System.out.println("Test MegaMenu SubNav Link click event at \"" + path + "\" :");
		setupTestMethod(path);

		try {
			megaMenu.clickMegaMenuListItem(subNavLink);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), subNavGroup);
			Assert.assertEquals(beacon.props.get(55), subNavLink);
			Assert.assertTrue(currentUrl.contains(beacon.props.get(56)));
			logger.log(LogStatus.PASS, "Test MegaMenu SubNav Link click event at \"" + path + "\" passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Mega Menu Desktop Reveal
	@Test(dataProvider = "PathData", groups = { "Analytics" })
	public void testMegaMenuDesktopReveal(String path, String lang) {
		System.out.println("Test MM desktop reveal event at \"" + path + "\" :");
		setupTestMethod(path);

		try {
			megaMenu.revealMegaMenuDesktop();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(28));
			Assert.assertEquals(beacon.linkName, "MegaMenuDesktopReveal");
			Assert.assertEquals(beacon.eVars.get(43), "Mega Menu");
			logger.log(LogStatus.PASS, "Test MM desktop reveal event at \"" + path + "\" :");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error hovering on component in " + currMethod + "()");
		}
	}

	/// Test MegaMenu Mobile Reveal
	@Test(dataProvider = "PathData", groups = { "Analytics" })
	public void testMegaMenuMobileReveal(String path, String lang) {
		System.out.println("Test Hamburger click at \"" + path + "\" :");
		setupTestMethod(path);

		try {
			megaMenu.revealMegaMenuMobile();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(28));
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileReveal");
			Assert.assertEquals(beacon.eVars.get(43), "Hamburger Menu");
			logger.log(LogStatus.PASS, "Test Hamburger click at \"" + path + "\" passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test MegaMenu Mobile Expand
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileExpand() {
		System.out.println("Test MM mobile expand:");
		setupTestMethod();

		try {
			megaMenu.revealMegaMenuMobile();
			megaMenu.clickMegaMenuMobileButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(34));
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileAccordionClick");
			Assert.assertTrue(beacon.props.get(73).contains("Expand"), "'Expand' value missing on evemt.");
			logger.log(LogStatus.PASS, "Test MM mobile expand passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test MegaMenu Mobile Collapse
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileCollapse() {
		System.out.println("Test MM mobile collapse:");
		setupTestMethod();

		try {
			megaMenu.revealMegaMenuMobile();
			megaMenu.clickMegaMenuMobileButton();
			megaMenu.clickMegaMenuMobileButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(35));
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileAccordionClick");
			Assert.assertTrue(beacon.props.get(73).contains("Collapse"), "'Collapse' value missing on evemt.");
			logger.log(LogStatus.PASS, "Test MM mobile collapse passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}
	
	// ==================== Data providers ==================== //

	@DataProvider(name = "PathData")
	private Iterator<Object[]> getMMPathData() {
		return getMegaMenuData("Path");
	}

	@DataProvider(name = "NavGroupData")
	private Iterator<Object[]> getMMNavGroupData() {
		return getMegaMenuData("NavGroup");
	}

	@DataProvider(name = "SubNavGroupData")
	private Iterator<Object[]> getMMSubNavGroupData() {
		return getMegaMenuData("SubNavGroup");
	}

	@DataProvider(name = "SubNavLinkData")
	private Iterator<Object[]> getMMSubNavLinkData() {
		return getMegaMenuData("");
	}

	/**
	 * Get an iterator data object with path, types, selectors, and position for
	 * Card objects, filtered by content type.
	 * 
	 * @param returnValues
	 *            specifies which collection of values we need for testing.
	 * @return
	 */
	private Iterator<Object[]> getMegaMenuData(String returnValues) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {

			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String navGroup = excelReader.getCellData(TESTDATA_SHEET_NAME, "NavGroup", rowNum);
			String subNavGroup = excelReader.getCellData(TESTDATA_SHEET_NAME, "SubNavGroup", rowNum);
			String listItem = excelReader.getCellData(TESTDATA_SHEET_NAME, "ListItem", rowNum);
			String language = excelReader.getCellData(TESTDATA_SHEET_NAME, "Language", rowNum);

			if (returnValues == "Path") {
				Object obj[] = { path, language };
				myObjects.add(obj);
			} else if (returnValues == "NavGroup") {
				Object obj[] = { path, navGroup, language };
				myObjects.add(obj);
			} else if (returnValues == "SubNavGroup") {
				Object obj[] = { path, navGroup, subNavGroup, language };
				myObjects.add(obj);
			} else {
				Object obj[] = { path, navGroup, subNavGroup, listItem, language };
				myObjects.add(obj);
			}

		}
		return myObjects.iterator();

	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
		Assert.assertEquals(beacon.eVars.get(53), beacon.props.get(53));
	}

}
