package gov.nci.webanalyticstests.r4r.pages;

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RLoadBase;

public class R4RHomePageLoad_Test extends R4RLoadBase {

	private final String TESTDATA_SHEET_NAME = "R4RHomeLoad";

	// ==================== Test methods ==================== //

	/// Test R4R Home Page Load event
	@Test(dataProvider = "R4RHomePageLoad", groups = { "Analytics" })
	public void testR4RHomePageLoad(String path, String type) {
		System.out.println("Path: " + path + ", page type: " + type);
		driver.get(config.goHome() + path);

		try {
			Beacon beacon = getBeacon();
			Assert.assertTrue(beacon.hasSuite("nciglobal", driver.getCurrentUrl()), "Global suite");
			Assert.assertEquals(beacon.channels, "Research", "channel");
			Assert.assertTrue(beacon.hasEvent(1), "event1");
			Assert.assertTrue(beacon.hasEvent(47), "event47");
			Assert.assertEquals(beacon.props.get(6), "Resources for Researchers", "prop6");
			Assert.assertEquals(beacon.props.get(10), "Resources for Researchers - National Cancer Institute",
					"prop10");
			Assert.assertEquals(beacon.props.get(44), "RandD Resources", "prop44");
			Assert.assertEquals(beacon.eVars.get(44), beacon.props.get(44), "eVar44");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "R4RHomePageLoad")
	private Iterator<Object[]> getR4RHomeLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
