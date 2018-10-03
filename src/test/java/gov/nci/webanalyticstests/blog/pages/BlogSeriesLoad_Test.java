package gov.nci.webanalyticstests.blog.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class BlogSeriesLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "BlogSeriesPage";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsBlogData");
	}

	// ==================== Test methods ==================== //

	/// Test Blog Series Page load event
	@Test(dataProvider = "BlogSeriesPageLoad", groups = { "Analytics" })
	public void testBlogSeriesPageLoad(String path, String contentType) {
		System.out.println("Test Blog Series Page load event:");
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsMetaData, path);
			logger.log(LogStatus.PASS, "Test Blog Series Page load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "BlogSeriesPageLoad")
	public Iterator<Object[]> getBlogSeriesPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
