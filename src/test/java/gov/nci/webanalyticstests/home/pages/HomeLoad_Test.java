package gov.nci.webanalyticstests.home.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class HomeLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "HomePage";
	private final String SITEHOME_PATH = "/";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}

	// ==================== Test methods ==================== //

	/// Test Home Page load event
	@Test(dataProvider = "HomePageLoad", groups = { "Analytics" })
	public void testHomePageLoad(String path, String contentType) {
		System.out.println("Test Home Page load event (" + contentType + "):");
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

	/// Test Home-refresh load event
	@Test(groups = { "Analytics" })
	public void testRefresh() {
		System.out.println("Test Home-refresh load event:");
		driver.get(config.goHome());
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			doCommonLoadAssertions(beacon, analyticsMetaData, SITEHOME_PATH);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Home-and-back load event
	@Test(groups = { "Analytics" })
	public void testHomeAndBack() {
		System.out.println("Test Home-and-back load event:");
		driver.get(config.goHome());
		driver.get(config.getPageURL("SpanishHome"));
		driver.navigate().back();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			doCommonLoadAssertions(beacon, analyticsMetaData, SITEHOME_PATH);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "HomePageLoad")
	private Iterator<Object[]> getHomePageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}