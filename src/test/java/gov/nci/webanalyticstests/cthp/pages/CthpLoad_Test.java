package gov.nci.webanalyticstests.cthp.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class CthpLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "CTHPPage";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTHPData");
	}

	// ==================== Test methods ==================== //

	/// Test CTHP Page load event
	@Test(dataProvider = "CthpPageLoad", groups = { "Analytics" })
	public void testCthpPageLoad(String path, String contentType) {
		System.out.println("Test CTHP Page load event (" + contentType + "):");
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			doCommonLoadAssertions(beacon, analyticsMetaData, path);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "CthpPageLoad")
	private Iterator<Object[]> getCthpPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
