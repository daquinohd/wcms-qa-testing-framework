package gov.nci.WebAnalytics.Tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.MegaMenu;

public class MegaMenu_Test extends AnalyticsTestBase {
	
	private MegaMenu megaMenu;

	@BeforeMethod(groups = { "Analytics" }) 
	public void beforeMethod() {
		megaMenu = new MegaMenu(driver);
	}
	
	/// Megamenu click returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testMMGeneral() {
		megaMenu.clickMMBarEn();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasProp(clickBeacons, 4, "d=pev1"));
		Assert.assertTrue(hasProp(clickBeacons, 67, "D=pageName"));
		Assert.assertTrue(haseVar(clickBeacons, 2, "English"));
		logger.log(LogStatus.PASS, "MegaMenu gen value test passed.");
	}	
	
	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu.clickMMBarEn();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		megaMenu.clickMMBarEs();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));		
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	

	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {		
		megaMenu.clickMMSubnavHeader();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "Subnav header click passed.");
	}
	
	/// MegaMenu subnav link click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavLiClick() {
		megaMenu.clickMMSubnavLi();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		Assert.assertTrue(haseVar(clickBeacons, 53, "About Cancer"));
		Assert.assertTrue(hasProp(clickBeacons, 53, "About Cancer"));
		Assert.assertTrue(hasProp(clickBeacons, 54, "Understanding cancer"));
		Assert.assertTrue(hasProp(clickBeacons, 55, "What is Cancer"));
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}
	
	/// Expanding the mobile megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		megaMenu.revealMegaMenuMobile();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegamenuMobileReveal"));
		Assert.assertTrue(hasEvent(clickBeacons, "event28"));
		logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
	}
	
	/// Expanding the desktop megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuDesktopReveal() {
		megaMenu.revealMegaMenuDesktop();
		clickBeacons = megaMenu.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegamenuDesktopReveal"));
		Assert.assertTrue(hasEvent(clickBeacons, "event28"));
		Assert.assertFalse(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
	}
	
}
