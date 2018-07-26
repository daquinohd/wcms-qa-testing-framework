package gov.nci.WebAnalytics.Tests;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.MegaMenu;

public class MegaMenu_Test extends AnalyticsTestBase {
	
	private MegaMenu megaMenu;

	@BeforeMethod(groups = { "Analytics" }) 
	public void setupMegaMenu() throws UnsupportedEncodingException, MalformedURLException {
		megaMenu = new MegaMenu(driver);
	}
	
	/// Megamenu click returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testMMGeneral() {
		megaMenu.clickMMBarEn();
		setClickBeacon();
		Assert.assertTrue(hasProp(4, "d=pev1"));
		Assert.assertTrue(hasProp(67, "D=pageName"));
		Assert.assertTrue(haseVar(2, "English"));
		logger.log(LogStatus.PASS, "MegaMenu gen value test passed.");
	}	
	
	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu.clickMMBarEn();
		setClickBeacon();
		Assert.assertTrue(hasLinkName("MegaMenuClick"));
		Assert.assertTrue(hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		megaMenu.clickMMBarEs();
		setClickBeacon();		
		Assert.assertTrue(hasLinkName("MegaMenuClick"));
		Assert.assertTrue(hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	

	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {		
		megaMenu.clickMMSubnavHeader();
		setClickBeacon();
		Assert.assertTrue(hasLinkName("MegaMenuClick"));
		Assert.assertTrue(hasEvent(26));
		logger.log(LogStatus.PASS, "Subnav header click passed.");
	}
	
	/// MegaMenu subnav link click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavLiClick() {
		megaMenu.clickMMSubnavLi();
		setClickBeacon();
		Assert.assertTrue(hasLinkName("MegaMenuClick"));
		Assert.assertTrue(hasEvent(26));
		Assert.assertTrue(haseVar(53, "About Cancer"));
		Assert.assertTrue(hasProp(53, "About Cancer"));
		Assert.assertTrue(hasProp(54, "Understanding cancer"));
		Assert.assertTrue(hasProp(55, "What is Cancer"));
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}
	
	/// Expanding the mobile megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		megaMenu.revealMegaMenuMobile();
		setClickBeacon();
		Assert.assertTrue(hasLinkName("MegamenuMobileReveal"));
		Assert.assertTrue(hasEvent(28));
		logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
	}
	
	/// Expanding the desktop megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuDesktopReveal() {
		megaMenu.revealMegaMenuDesktop();
		setClickBeacon();
		Assert.assertTrue(hasLinkName("MegamenuDesktopReveal"));
		Assert.assertTrue(hasEvent(28));
		Assert.assertFalse(hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
	}
	
}
