package gov.nci.webanalyticstests.click;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.WebAnalytics.Beacon;
import gov.nci.WebAnalytics.Resize;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class Resize_Test extends AnalyticsTestBase {
	
	private Resize resize;
	private Beacon beacon;	
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupResize() throws MalformedURLException, UnsupportedEncodingException {
		resize = new Resize(driver);
	}

	// TODO: Fix random failure errors (this may be doing the resizes too quickly - set timeout if needed)
	/// Correct click events have been captured on resize
	@Test(groups = { "Analytics" }, priority = 1)
	public void testResizeGeneral() {
		resize.doAllResizes();
		beacon = getClickBeacon();
		Assert.assertTrue(clickBeacons.size() > 1);
		Assert.assertTrue(beacon.hasProp(4, "d=pev1"));
		Assert.assertTrue(beacon.hasProp(67, "D=pageName"));
		Assert.assertTrue(beacon.haseVar(2, "English"));
		Assert.assertFalse(beacon.hasEvent(1));
		logger.log(LogStatus.PASS, "Resize gen value test passed.");		
	}
	
	/// Correct linkName captured on small resize
	@Test(groups = { "Analytics" }, priority = 2)
	public void testResizeToMobile() {
		resize.toSmall();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertTrue(beacon.hasLinkName("ResizedToMobile"));
		logger.log(LogStatus.PASS, "'Resize to mobile' values are correct.");
	}

	/// Correct linkName captured on medium resize
	@Test(groups = { "Analytics" }, priority = 3)
	public void testResizeToTablet() {
		resize.toMed();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertTrue(beacon.hasLinkName("ResizedToTablet"));
		logger.log(LogStatus.PASS, "'Resize to tablet' values are correct.");
	}

	/// Correct linkName captured on large resize
	@Test(groups = { "Analytics" }, priority = 4)
	public void testResizeToDesktop() {
		resize.toLarge();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertTrue(beacon.hasLinkName("ResizedToDesktop"));
		logger.log(LogStatus.PASS, "'Resize to desktop' values are correct.");
	}

	/// Correct linkName captured on XL resize
	@Test(groups = { "Analytics" }, priority = 5)
	public void testResizeToExtraWide() {
		resize.toXlarge();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertTrue(beacon.hasLinkName("ResizedToExtra wide"));
		logger.log(LogStatus.PASS, "'Resize to extra wide' values are correct.");
	}

	/// Correct event on maximize
	@Test(groups = { "Analytics" }, priority = 6)
	public void testMaximize() {
		resize.maximize();
		beacon = getClickBeacon();
		Assert.assertTrue(beacon.hasEvent(7));
		logger.log(LogStatus.PASS, "Maximize values are correct.");
	}	
	
}
