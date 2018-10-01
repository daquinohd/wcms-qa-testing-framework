package gov.nci.webanalyticstests.blog.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class BlogPostLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "BlogPostPage";

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsBlogData");
	}

	// ==================== Test methods ==================== //

	/// Test Blog Post Page load event
	@Test(dataProvider = "BlogPostPageLoad", groups = { "Analytics" })
	public void testBlogPostPageLoad(String path, String contentType) {
		System.out.println("Test Blog Post Page load event:");
		driver.get(config.goHome() + path);

		try {
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			Assert.assertEquals(beacon.eVars.get(48), analyticsPageLoad.getMetaIsPartOf() + " Viewer");
			logger.log(LogStatus.PASS, "Test Blog Post Page load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "BlogPostPageLoad")
	public Iterator<Object[]> getBlogPostPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
