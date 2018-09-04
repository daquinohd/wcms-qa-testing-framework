package gov.nci.webanalyticstests.click;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsRequest;
import gov.nci.commonobjects.MegaMenu;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class MegaMenu_Test extends AnalyticsTestBase {
	
	private MegaMenu megaMenu;
	private AnalyticsRequest beacon;

	@BeforeMethod(groups = { "Analytics" }) 
	@Parameters({ "environment" })
	public void setupMegaMenu(String environment) throws UnsupportedEncodingException, MalformedURLException {
		megaMenu = new MegaMenu(driver, environment);
	}
	
	/// Megamenu click returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testMMGeneral() {
		megaMenu.clickMMBarEn();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasProp(4, "d=pev1"));
		Assert.assertTrue(beacon.hasProp(67, "D=pageName"));
		Assert.assertTrue(beacon.haseVar(2, "English"));
		logger.log(LogStatus.PASS, "MegaMenu gen value test passed.");
	}	
	
	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu.clickMMBarEn();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasLinkName("MegaMenuClick"));
		Assert.assertTrue(beacon.hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		megaMenu.clickMMBarEs();
		beacon = getClickBeacon();		
		Assert.assertTrue(beacon.hasLinkName("MegaMenuClick"));
		Assert.assertTrue(beacon.hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	

	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {		
		megaMenu.clickMMSubnavHeader();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasLinkName("MegaMenuClick"));
		Assert.assertTrue(beacon.hasEvent(26));
		logger.log(LogStatus.PASS, "Subnav header click passed.");
	}
	
	/// MegaMenu subnav link click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavLiClick() {
		megaMenu.clickMMSubnavLi();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasLinkName("MegaMenuClick"));
		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertTrue(beacon.haseVar(53, "About Cancer"));
		Assert.assertTrue(beacon.hasProp(53, "About Cancer"));
		Assert.assertTrue(beacon.hasProp(54, "Understanding cancer"));
		Assert.assertTrue(beacon.hasProp(55, "What is Cancer"));
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}
	
	/// Expanding the mobile megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		megaMenu.revealMegaMenuMobile();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasLinkName("MegamenuMobileReveal"));
		Assert.assertTrue(beacon.hasEvent(28));
		logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
	}
	
	/// Expanding the desktop megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuDesktopReveal() {
		megaMenu.revealMegaMenuDesktop();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasLinkName("MegamenuDesktopReveal"));
		Assert.assertTrue(beacon.hasEvent(28));
		Assert.assertFalse(beacon.hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
	}
	
}
