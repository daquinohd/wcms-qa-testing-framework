package gov.nci.webanalyticstests.commonobjects;

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
		try {
			Resize resize = new Resize(driver);
			resize.toXlarge();
			resize.toSmall();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "Mobile");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test resize to medium
	@Test(groups = { "Analytics" })
	public void testResizeToMedium() {
		try {
			Resize resize = new Resize(driver);
			resize.toXlarge();
			resize.toMed();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "Tablet");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	//// Test resize to large
	@Test(groups = { "Analytics" })
	public void testResizeToLarge() {
		try {
			Resize resize = new Resize(driver);
			resize.toSmall();
			resize.toLarge();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "Desktop");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test resize to x-large
	@Test(groups = { "Analytics" })
	public void testResizeToExtraWide() {
		try {
			Resize resize = new Resize(driver);
			resize.toSmall();
			resize.toXlarge();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "Extra wide");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test maximize
	@Test(groups = { "Analytics" })
	public void testMaximize() {
		try {
			Resize resize = new Resize(driver);
			resize.toSmall();
			resize.maximize();

			Beacon beacon = getBeacon();
			doCommonClickAssertions(beacon);
			Assert.assertFalse(beacon.hasEvent(1));
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
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
		Assert.assertTrue(beacon.hasEvent(7), "Missing event7");
		Assert.assertEquals(beacon.linkName, "ResizedTo" + linkName);
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(5), linkName);
	}

}
