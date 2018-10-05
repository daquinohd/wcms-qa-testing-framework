package gov.nci.webanalyticstests.commonobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.Resize;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class ResizeClick_Test extends AnalyticsTestClickBase {

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		driver.get(config.goHome());
	}

	// ==================== Test methods ==================== //

	/// Test resize to small
	@Test(groups = { "Analytics" })
	public void testResizeToMobile() {
		System.out.println("Test resize to small:");

		try {
			Resize resize = new Resize(driver);
			resize.toXlarge();
			resize.toSmall();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "Mobile");
			logger.log(LogStatus.PASS, "'Test resize to small' passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error resizing component in " + currMethod + "()");
		}
	}

	/// Test resize to medium
	@Test(groups = { "Analytics" })
	public void testResizeToMedium() {
		System.out.println("Test resize to medium:");

		try {
			Resize resize = new Resize(driver);
			resize.toXlarge();
			resize.toMed();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "Tablet");
			logger.log(LogStatus.PASS, "'Test resize to medium' passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error resizing component in " + currMethod + "()");
		}
	}

	//// Test resize to large
	@Test(groups = { "Analytics" })
	public void testResizeToLarge() {
		System.out.println("Test resize to large:");

		try {
			Resize resize = new Resize(driver);
			resize.toSmall();
			resize.toLarge();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "Desktop");
			logger.log(LogStatus.PASS, "'Test resize to large' passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error resizing component in " + currMethod + "()");
		}
	}

	/// Test resize to x-large
	@Test(groups = { "Analytics" })
	public void testResizeToExtraWide() {
		System.out.println("Test resize to x-large:");

		try {
			Resize resize = new Resize(driver);
			resize.toSmall();
			resize.toXlarge();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "Extra wide");
			logger.log(LogStatus.PASS, "'Test resize to x-large' passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error resizing component in " + currMethod + "()");
		}
	}

	/// Test maximize
	@Test(groups = { "Analytics" })
	public void testMaximize() {
		System.out.println("Test maximize:");

		try {
			Resize resize = new Resize(driver);
			resize.toSmall();
			resize.maximize();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertFalse(beacon.hasEvent(1));
			logger.log(LogStatus.PASS, "'Test maximize' passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error resizing component in " + currMethod + "()");
		}
	}

	// ==================== Common assertions ==================== //

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
