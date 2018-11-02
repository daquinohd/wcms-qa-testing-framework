package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

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
			System.out.println("Path: " + path);
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
		System.out.println("Group: " + navGroup);
		setupTestMethod(path);

		try {
			megaMenu.clickMegaMenuBar(navGroup);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26), "Missing event26");
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), navGroup);
			Assert.assertEquals(beacon.props.get(55), navGroup);
			Assert.assertTrue(currentUrl.contains(beacon.props.get(56)), "prop56 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test MegaMenu SubNav Group click event
	@Test(dataProvider = "SubNavGroupData", groups = { "Analytics" })
	public void testMegaMenuSubNavGroup(String path, String navGroup, String subNavGroup, String lang) {
		System.out.println("Group: " + navGroup + ", subnav group: " + subNavGroup);
		setupTestMethod(path);

		try {
			megaMenu.clickMegaMenuSubNavHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26), "Missing event26");
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), subNavGroup);
			Assert.assertEquals(beacon.props.get(55), subNavGroup);
			Assert.assertTrue(currentUrl.contains(beacon.props.get(56)), "prop56 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test MegaMenu SubNav Link click event
	@Test(dataProvider = "SubNavLinkData", groups = { "Analytics" })
	public void testMegaMenuSubNavLink(String path, String navGroup, String subNavGroup, String subNavLink,
			String lang) {
		System.out.println("Group: " + navGroup + ", subnav group: " + subNavGroup + ", subnav link: " + subNavLink);
		setupTestMethod(path);

		try {
			megaMenu.clickMegaMenuListItem(subNavLink);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26), "Missing event26");
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), subNavGroup);
			Assert.assertEquals(beacon.props.get(55), subNavLink);
			Assert.assertTrue(currentUrl.contains(beacon.props.get(56)), "prop56 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Mega Menu Desktop Reveal
	@Test(dataProvider = "PathData", groups = { "Analytics" })
	public void testMegaMenuDesktopReveal(String path, String lang) {
		setupTestMethod(path);

		try {
			megaMenu.revealMegaMenuDesktop();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(28), "Missing event28");
			Assert.assertEquals(beacon.linkName, "MegaMenuDesktopReveal");
			Assert.assertEquals(beacon.eVars.get(43), "Mega Menu");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test MegaMenu Mobile Reveal
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		setupTestMethod();

		try {
			megaMenu.revealMegaMenuMobile();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(28), "Missing event28");
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileReveal");
			Assert.assertEquals(beacon.eVars.get(43), "Hamburger Menu");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test MegaMenu Mobile Expand
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileExpand() {
		setupTestMethod();

		try {
			megaMenu.expandMegaMenuMobileButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(34), "Missing event34");
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileAccordionClick");
			Assert.assertTrue(beacon.props.get(73).startsWith("Expand"), "prop73 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test MegaMenu Mobile Collapse
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileCollapse() {
		setupTestMethod();

		try {
			megaMenu.collapseMegaMenuMobileButton();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(35), "Missing event35");
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileAccordionClick");
			Assert.assertTrue(beacon.props.get(73).startsWith("Collapse"), "prop73 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "PathData")
	private Iterator<Object[]> getMMPathData() {
		String[] columnsToReturn = { "Path", "Language" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	@DataProvider(name = "NavGroupData")
	private Iterator<Object[]> getMMNavGroupData() {
		String[] columnsToReturn = { "Path", "NavGroup", "Language" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	@DataProvider(name = "SubNavGroupData")
	private Iterator<Object[]> getMMSubNavGroupData() {
		String[] columnsToReturn = { "Path", "NavGroup", "SubNavGroup", "Language" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}

	@DataProvider(name = "SubNavLinkData")
	private Iterator<Object[]> getMMSubNavLinkData() {
		String[] columnsToReturn = { "Path", "NavGroup", "SubNavGroup", "ListItem", "Language" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
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
