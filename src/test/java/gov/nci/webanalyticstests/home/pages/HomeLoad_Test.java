package gov.nci.webanalyticstests.home.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class HomeLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "HomePage";
	private final String SITEHOME_PATH = "/";

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}

	// ==================== Test methods ==================== //

	/// Test Home Page load event
	@Test(dataProvider = "HomePageLoad", groups = { "Analytics" })
	public void testHomePageLoad(String path, String contentType) {
		System.out.println("Test Home Page load event (" + contentType + "):");
		driver.get(config.goHome() + path);

		try {
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			logger.log(LogStatus.PASS, "Test Home Page load event (" + contentType + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Home-refresh load event
	@Test(groups = { "Analytics" })
	public void testRefresh() {
		System.out.println("Test Home-refresh load event:");
		driver.get(config.goHome());
		driver.navigate().refresh();

		try {
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, SITEHOME_PATH);
			logger.log(LogStatus.PASS, "Test Home-refresh load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
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
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, SITEHOME_PATH);
			logger.log(LogStatus.PASS, "Test Home-and-back load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test engagement tracking on page load
	// @Test(groups = { "Analytics" })
	public void testEngagement() {
		System.out.println("Test engagement tracking on page load:");
		driver.get(config.goHome());

		try {
			// TODO
			// Click and scroll around page
			// Refresh
			Beacon beacon = getBeacon();
			doCommonLoadAssertions(beacon, analyticsPageLoad, SITEHOME_PATH);
			logger.log(LogStatus.PASS, "Test engagement tracking on page load passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "HomePageLoad")
	public Iterator<Object[]> getHomePageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}