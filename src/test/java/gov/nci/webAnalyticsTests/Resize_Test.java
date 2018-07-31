package gov.nci.webAnalyticsTests;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.WebAnalytics.Resize;

public class Resize_Test extends AnalyticsTestBase {
	
	private Resize resize;
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupResize() throws MalformedURLException, UnsupportedEncodingException {
		resize = new Resize(driver);
	}

	// TODO: Fix random failure errors (this may be doing the resizes too quickly - set timeout if needed)
	/// Correct click events have been captured on resize
	@Test(groups = { "Analytics" }, priority = 1)
	public void testResizeGeneral() {
		resize.doAllResizes();
		setClickBeacon();
		Assert.assertTrue(clickBeacons.size() > 1);
		Assert.assertTrue(hasProp(4, "d=pev1"));
		Assert.assertTrue(hasProp(67, "D=pageName"));
		Assert.assertTrue(haseVar(2, "English"));
		Assert.assertFalse(hasEvent(1));
		logger.log(LogStatus.PASS, "Resize gen value test passed.");		
	}
	
	/// Correct linkName captured on small resize
	@Test(groups = { "Analytics" }, priority = 2)
	public void testResizeToMobile() {
		resize.toSmall();
		setClickBeacon();
		Assert.assertTrue(hasEvent(7));
		Assert.assertTrue(hasLinkName("ResizedToMobile"));
		logger.log(LogStatus.PASS, "'Resize to mobile' values are correct.");
	}

	/// Correct linkName captured on medium resize
	@Test(groups = { "Analytics" }, priority = 3)
	public void testResizeToTablet() {
		resize.toMed();
		setClickBeacon();
		Assert.assertTrue(hasEvent(7));
		Assert.assertTrue(hasLinkName("ResizedToTablet"));
		logger.log(LogStatus.PASS, "'Resize to tablet' values are correct.");
	}

	/// Correct linkName captured on large resize
	@Test(groups = { "Analytics" }, priority = 4)
	public void testResizeToDesktop() {
		resize.toLarge();
		setClickBeacon();
		Assert.assertTrue(hasEvent(7));
		Assert.assertTrue(hasLinkName("ResizedToDesktop"));
		logger.log(LogStatus.PASS, "'Resize to desktop' values are correct.");
	}

	/// Correct linkName captured on XL resize
	@Test(groups = { "Analytics" }, priority = 5)
	public void testResizeToExtraWide() {
		resize.toXlarge();
		setClickBeacon();
		Assert.assertTrue(hasEvent(7));
		Assert.assertTrue(hasLinkName("ResizedToExtra wide"));
		logger.log(LogStatus.PASS, "'Resize to extra wide' values are correct.");
	}

	/// Correct event on maximize
	@Test(groups = { "Analytics" }, priority = 6)
	public void testMaximize() {
		resize.maximize();
		setClickBeacon();
		Assert.assertTrue(hasEvent(7));
		logger.log(LogStatus.PASS, "Maximize values are correct.");
	}	
	
}
