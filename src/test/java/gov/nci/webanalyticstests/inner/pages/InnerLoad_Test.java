package gov.nci.webanalyticstests.inner.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class InnerLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "InnerPage";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}

	// ==================== Test methods ==================== //

	/// Test Inner Page load event
	@Test(dataProvider = "InnerPageLoad", groups = { "Analytics" })
	public void testInnerPageLoad(String path, String contentType) {
		System.out.println("Path: " + path + ", Type: " + contentType);
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

	@DataProvider(name = "InnerPageLoad")
	private Iterator<Object[]> getInnerPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}