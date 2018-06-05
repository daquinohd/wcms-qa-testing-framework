package gov.nci.WebAnalytics.Tests;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.Resize;

public class Resize_Test extends AnalyticsTestBase {
	
	private Resize resize;
	
	@BeforeMethod(groups = { "Analytics" }) 
	private void beforeMethod() {
		resize = new Resize(driver);
	}

	// TODO: Fix random failure errors (this may be doing the resizes too quickly - set timeout if needed)
	/// Correct click events have been captured on resize
	@Test(groups = { "Analytics" }, priority = 1)
	public void testResizeGeneral() {
		resize.doAllResizes();
		clickBeacons = Resize.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(clickBeacons.size() > 1);
		Assert.assertTrue(hasProp(clickBeacons, 4, "d=pev1"));
		Assert.assertTrue(hasProp(clickBeacons, 67, "D=pageName"));
		Assert.assertTrue(haseVar(clickBeacons, 2, "English"));
		Assert.assertFalse(hasEvent(clickBeacons, "event1"));
		logger.log(LogStatus.PASS, "Resize gen value test passed.");		
	}
	
	/// Correct linkName captured on small resize
	@Test(groups = { "Analytics" }, priority = 2)
	public void testResizeToMobile() {
		resize.toSmall();
		clickBeacons = Resize.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(clickBeacons, "event7"));
		Assert.assertTrue(hasLinkName(clickBeacons, "ResizedToMobile"));
		logger.log(LogStatus.PASS, "'Resize to mobile' values are correct.");
	}

	/// Correct linkName captured on medium resize
	@Test(groups = { "Analytics" }, priority = 3)
	public void testResizeToTablet() {
		resize.toMed();
		clickBeacons = Resize.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(clickBeacons, "event7"));
		Assert.assertTrue(hasLinkName(clickBeacons, "ResizedToTablet"));
		logger.log(LogStatus.PASS, "'Resize to tablet' values are correct.");
	}

	/// Correct linkName captured on large resize
	@Test(groups = { "Analytics" }, priority = 4)
	public void testResizeToDesktop() {
		resize.toLarge();
		clickBeacons = Resize.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(clickBeacons, "event7"));
		Assert.assertTrue(hasLinkName(clickBeacons, "ResizedToDesktop"));
		logger.log(LogStatus.PASS, "'Resize to desktop' values are correct.");
	}

	/// Correct linkName captured on XL resize
	@Test(groups = { "Analytics" }, priority = 5)
	public void testResizeToExtraWide() {
		resize.toXlarge();
		clickBeacons = Resize.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(clickBeacons, "event7"));
		Assert.assertTrue(hasLinkName(clickBeacons, "ResizedToExtra wide"));
		logger.log(LogStatus.PASS, "'Resize to extra wide' values are correct.");
	}

	/// Correct event on maximize
	@Test(groups = { "Analytics" }, priority = 6)
	public void testMaximize() {
		resize.toSmall();
		resize.maximize();
		clickBeacons = Resize.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(clickBeacons, "event7"));
		logger.log(LogStatus.PASS, "Maximize values are correct.");
	}	
	
}
