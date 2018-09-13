package gov.nci.webanalyticstests.click;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.Resize;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class Resize_Test extends AnalyticsTestClickBase {
	
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
		beacon = getBeacon();

		Assert.assertFalse(beacon.hasEvent(1));
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), "english");
		logger.log(LogStatus.PASS, "Resize gen value test passed.");		
	}
	
	/// Correct linkName captured on small resize
	@Test(groups = { "Analytics" }, priority = 2)
	public void testResizeToMobile() {
		resize.toSmall();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToMobile");	
		logger.log(LogStatus.PASS, "'Resize to mobile' values are correct.");
	}

	/// Correct linkName captured on medium resize
	@Test(groups = { "Analytics" }, priority = 3)
	public void testResizeToTablet() {
		resize.toMed();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToTablet");
		logger.log(LogStatus.PASS, "'Resize to tablet' values are correct.");
	}

	/// Correct linkName captured on large resize
	@Test(groups = { "Analytics" }, priority = 4)
	public void testResizeToDesktop() {
		resize.toLarge();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToDesktop");
		logger.log(LogStatus.PASS, "'Resize to desktop' values are correct.");
	}

	/// Correct linkName captured on XL resize
	@Test(groups = { "Analytics" }, priority = 5)
	public void testResizeToExtraWide() {
		resize.toXlarge();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToExtra wide");
		logger.log(LogStatus.PASS, "'Resize to extra wide' values are correct.");
	}

	/// Correct event on maximize
	@Test(groups = { "Analytics" }, priority = 6)
	public void testMaximize() {
		resize.maximize();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(7));
		logger.log(LogStatus.PASS, "Maximize values are correct.");
	}	
	
}
