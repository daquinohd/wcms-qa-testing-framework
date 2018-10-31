package gov.nci.webanalyticstests.blog.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.pages.BlogSeries;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogSeriesClick_Test extends AnalyticsTestClickBase {

	private final String CANCER_CURRENTS_EN = "/news-events/cancer-currents-blog";
	private final String CANCER_CURRENTS_ES = "/espanol/noticias/temas-y-relatos-blog";

	private BlogSeries blogSeries;

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			blogSeries = new BlogSeries(driver);
			System.out.println("Path: " + path);
		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Blog Series Title Link Click (English)
	@Test(groups = { "Analytics" })
	public void testBlogSeriestTitleLinkClickEn() {
		setupTestMethod(CANCER_CURRENTS_EN);

		try {
			blogSeries.clickBlogPostTitle();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "SearchResults");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Title Link Click (Spanish)
	@Test(groups = { "Analytics" })
	public void testBlogSeriestTitleLinkClickEs() {
		setupTestMethod(CANCER_CURRENTS_ES);

		try {
			blogSeries.clickBlogPostTitle();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "SearchResults");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon, String linkName) {
		doCommonClickAssertions(beacon);
		Assert.assertEquals(beacon.linkName, linkName);
	}

}
