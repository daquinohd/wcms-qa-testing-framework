package gov.nci.webanalyticstests.commonobjects;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.commonobjects.MegaMenu;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class MegaMenuClick_Test extends AnalyticsTestClickBase {
	
	private MegaMenu megaMenu;
	private Beacon beacon;

	@BeforeMethod(groups = { "Analytics" }) 
	@Parameters({ "environment" })
	public void setupMegaMenu(String environment) throws UnsupportedEncodingException, MalformedURLException {
		megaMenu = new MegaMenu(driver, environment);
		driver.get(config.goHome());
	}
	
	/// Megamenu click returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testMMGeneral() {
		megaMenu.clickMMBarEn();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		logger.log(LogStatus.PASS, "MegaMenu gen value test passed.");
	}	
	
	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu.clickMMBarEn();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertEquals(beacon.props.get(8), "english");
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		driver.get(config.getPageURL("SpanishPage"));		
		megaMenu.clickMMBarEs();
		beacon = getBeacon();

		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertEquals(beacon.props.get(8), "spanish");
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	

	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {		
		megaMenu.clickMMSubnavHeader();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertEquals(beacon.linkName, "MegaMenuClick");
		logger.log(LogStatus.PASS, "Subnav header click passed.");
	}
	
	/// MegaMenu subnav link click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavLiClick() {
		megaMenu.clickMMSubnavLi();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertEquals(beacon.props.get(53), "About Cancer");
		Assert.assertEquals(beacon.props.get(54), "Understanding Cancer");
		Assert.assertEquals(beacon.props.get(55), "What Is Cancer");
		Assert.assertEquals(beacon.eVars.get(53), "About Cancer");
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}
	
	/// Expanding the mobile megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		megaMenu.revealMegaMenuMobile();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(28));
		logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
	}
	
	/// Expanding the desktop megamenu returns the expected values
	/// @Test(groups = { "Analytics" })
	public void testMegaMenuDesktopReveal() {
		megaMenu.revealMegaMenuDesktop();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(28));
		Assert.assertFalse(beacon.hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
	}
	
	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertTrue(beacon.channels.length() > 0);
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(8), beacon.eVars.get(2));
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		Assert.assertEquals(beacon.linkName, "MegaMenuClick");		
	}
	
}
