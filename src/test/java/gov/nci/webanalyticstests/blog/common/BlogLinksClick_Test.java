package gov.nci.webanalyticstests.blog.common;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogLinks;
import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogLinksClick_Test extends AnalyticsTestClickBase {

	private final String BLOG_POST_EN = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";
	private final String BLOG_POST_ES = "/espanol/noticias/temas-y-relatos-blog/2018/fda-olaparib-seno-brca-mutaciones";
	private final String BLOG_SERIES_EN = "/news-events/cancer-currents-blog";
	private final String BLOG_SERIES_ES = "/espanol/noticias/temas-y-relatos-blog";

	private BlogLinks blogLinks;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			driver.navigate().refresh();

			BlogPost blogPost = new BlogPost(driver);
			blogLinks = blogPost.getBlogLinks();
			currentUrl = driver.getCurrentUrl();
		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Blog Series Subscribe click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesSubscribeClick() {
		System.out.println("Test Blog Series Subscribe click:");
		setupTestMethod(BLOG_SERIES_EN);

		try {
			blogLinks.clickSubscribeNoNav();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Subscribe");
			logger.log(LogStatus.PASS, "Test Blog Series Subscribe click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Blog Post Subscribe click
	@Test(groups = { "Analytics" }, priority = 10)
	public void testBlogPostSubscribeClick() {
		System.out.println("Test Blog Post Subscribe click: ");
		setupTestMethod(BLOG_POST_EN);

		try {
			blogLinks.clickSubscribeNoNav();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Subscribe");
			logger.log(LogStatus.PASS, "Test Blog Post Subscribe click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Blog Series Newer click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesNewerClick() {
		System.out.println("Test Blog Series Newer click:");
		setupTestMethod(BLOG_SERIES_ES + "?page=2");

		try {
			blogLinks.clickBlogCommon(".blog-pager a.newer");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Series_Newer");
			logger.log(LogStatus.PASS, "Test Blog Series Older click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Blog Series Older click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesOlderClick() {
		System.out.println("Test Blog Series Older click:");
		setupTestMethod(BLOG_SERIES_EN);

		try {
			blogLinks.clickBlogCommon(".blog-pager a.older");
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Older");
			logger.log(LogStatus.PASS, "Test Blog Series Older click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Blog Post Newer click
	@Test(groups = { "Analytics" })
	public void testBlogPostNewerClick() {
		System.out.println("Test Blog Post Newer click:");
		setupTestMethod(BLOG_POST_ES);

		try {
			blogLinks.clickNewerPost();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Post_Newer");
			logger.log(LogStatus.PASS, "Test Blog Post Newer click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Blog Post Older click
	@Test(groups = { "Analytics" })
	public void testBlogPostOlderClick() {
		System.out.println("Test Blog Post Older click:");
		setupTestMethod(BLOG_POST_ES);

		try {
			blogLinks.clickOlderPost();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Post_Older");
			logger.log(LogStatus.PASS, "Test Blog Post Newer click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
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
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
	}

}
