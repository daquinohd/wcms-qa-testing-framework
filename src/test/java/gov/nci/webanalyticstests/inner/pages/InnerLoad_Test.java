package gov.nci.webanalyticstests.inner.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class InnerLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "InnerPage";

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}

	// ==================== Test methods ==================== //

	/// Test Inner Page load event
	@Test(dataProvider = "InnerPageLoad", groups = { "Analytics" })
	public void testInnerPageLoad(String path, String contentType) {
		System.out.println("Test Inner Page load event (" + contentType + "):");
		driver.get(config.goHome() + path);

		try {
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			logger.log(LogStatus.PASS, "Test Inner Page load event (" + contentType + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "InnerPageLoad")
	public Iterator<Object[]> getInnerPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}