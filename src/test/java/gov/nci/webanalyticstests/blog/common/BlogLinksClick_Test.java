package gov.nci.webanalyticstests.blog.common;

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
		setupTestMethod(BLOG_SERIES_EN);

		try {
			blogLinks.clickSubscribeNoNav();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Subscribe");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Subscribe click
	@Test(groups = { "Analytics" })
	public void testBlogPostSubscribeClick() {
		setupTestMethod(BLOG_POST_EN);

		try {
			blogLinks.clickSubscribeNoNav();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Subscribe");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Newer click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesNewerClick() {
		setupTestMethod(BLOG_SERIES_ES + "?page=2");

		try {
			blogLinks.clickBlogCommon(".blog-pager a.newer");
			
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Series_Newer");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Older click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesOlderClick() {
		setupTestMethod(BLOG_SERIES_EN);

		try {
			blogLinks.clickBlogCommon(".blog-pager a.older");
			
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Older");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Newer click
	@Test(groups = { "Analytics" })
	public void testBlogPostNewerClick() {
		setupTestMethod(BLOG_POST_ES);

		try {
			blogLinks.clickNewerPost();
			
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Post_Newer");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Older click
	@Test(groups = { "Analytics" })
	public void testBlogPostOlderClick() {
		setupTestMethod(BLOG_POST_ES);

		try {
			blogLinks.clickOlderPost();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Post_Older");
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
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)), "prop67 incorrect");
	}

}
