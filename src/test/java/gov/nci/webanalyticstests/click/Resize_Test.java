package gov.nci.webanalyticstests.click;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.Resize;
import gov.nci.webanalytics.Beacon;

public class Resize_Test extends AnalyticsTestClickBase {
	
	private Resize resize;
	private Beacon beacon;	
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setupResizeTestClass() {
		driver.get(config.goHome());
	}

	@BeforeMethod(groups = { "Analytics" }) 
	public void setupResizeTestMehod() throws MalformedURLException, UnsupportedEncodingException {
		resize = new Resize(driver);
	}
	
	@Test(groups = { "Analytics" })
	public void testResizeToMobile() {
		System.out.println("Test resize to small: ");
		resize.toXlarge();
		resize.toSmall();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon, "Mobile");
		logger.log(LogStatus.PASS, "'Resize to mobile' values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testResizeToTablet() {
		System.out.println("Test resize to medium: ");
		resize.toXlarge();
		resize.toMed();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon, "Tablet");
		logger.log(LogStatus.PASS, "'Resize to tablet' values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testResizeToDesktop() {
		System.out.println("Test resize to large: ");
		resize.toSmall();
		resize.toLarge();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon, "Desktop");
		logger.log(LogStatus.PASS, "'Resize to desktop' values are correct.");
	}

	@Test(groups = { "Analytics" })
	public void testResizeToExtraWide() {
		System.out.println("Test resize to x-large: ");
		resize.toSmall();
		resize.toXlarge();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon, "Extra wide");
		logger.log(LogStatus.PASS, "'Resize to extra wide' values are correct.");
	}

	/// Correct event on maximize
	@Test(groups = { "Analytics" })
	public void testMaximize() {
		System.out.println("Test maximize: ");
		resize.toSmall();
		resize.maximize();
		beacon = getBeacon();
		
		doCommonClickAssertions(beacon);
		Assert.assertFalse(beacon.hasEvent(1));
		logger.log(LogStatus.PASS, "Maximize values are correct.");
	}
	
	
	/**
	 * Shared Assert() calls for Resize_Test
	 * 
	 * @param beacon
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon, String linkName) {
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedTo" + linkName);
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(5), linkName);
	}
	
}
