package gov.nci.webanalyticstests.pdq.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class PdqCisLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "PDQCisPage";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	// ==================== Test methods ==================== //

	/// Test PDQ Cancer Info Summary Page load event
	@Test(dataProvider = "PDQPageLoad", groups = { "Analytics" })
	public void testPdqCisPageLoad(String path, String contentType) {
		System.out.println("Path: " + path + ", Type: " + contentType);
		driver.get(config.goHome() + path);
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			String[] pathNoId = path.split("#");
			doCommonLoadAssertions(beacon, analyticsMetaData, pathNoId[0]);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "PDQPageLoad")
	private Iterator<Object[]> getPDQPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}