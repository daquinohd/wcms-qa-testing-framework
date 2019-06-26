package gov.nci.webanalyticstests.blog.common;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogRightRail;
import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogRightRailClick_Test extends AnalyticsTestClickBase {

	private final String BLOG_POST_EN = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";
	private final String BLOG_POST_ES = "/espanol/noticias/temas-y-relatos-blog/2018/fda-olaparib-seno-brca-mutaciones";
	private final String BLOG_SERIES_EN = "/news-events/cancer-currents-blog";
	private final String BLOG_SERIES_ES = "/espanol/noticias/temas-y-relatos-blog";

	private BlogRightRail rightRail;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			driver.navigate().refresh();

			BlogPost blogPost = new BlogPost(driver);
			rightRail = blogPost.getRightRail();
			currentUrl = driver.getCurrentUrl();
			System.out.println("Path: " + path);
		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Blog Series Rail Featured click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailFeaturedClick() {
		setupTestMethod(BLOG_SERIES_EN);

		try {
			String featuredText = rightRail.getFeaturedItemText(0);
			rightRail.clickFeaturedItem(0);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54), "Missing event54");
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_FeaturedPosts:1");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Featured click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailFeaturedClick() {
		setupTestMethod(BLOG_POST_EN);

		try {
			String featuredText = rightRail.getFeaturedItemText(0);
			rightRail.clickFeaturedItem(0);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54), "Missing event54");
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_FeaturedPosts:1");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Rail Category click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailCategoryClick() {
		setupTestMethod(BLOG_SERIES_EN);

		try {
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Category:2");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Category click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailCategoryClick() {
		setupTestMethod(BLOG_POST_EN);

		try {
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Category:2");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Rail Archive Expand click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailArchiveExpand() {
		setupTestMethod(BLOG_SERIES_ES);

		try {
			rightRail.expandArchiveHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Series_Expand:Archive");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Archive expand click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailArchiveExpand() {
		setupTestMethod(BLOG_POST_ES);

		try {
			rightRail.expandArchiveHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Post_Expand:Archive");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Rail month click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailMonthClick() {
		setupTestMethod(BLOG_SERIES_EN);

		try {
			rightRail.expandArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("5", "2017");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), "2017:5");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Month click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailMonthClick() {
		setupTestMethod(BLOG_POST_EN);

		try {
			rightRail.expandArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("5", "2017");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), "2017:5");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Archive collapse
	@Test(groups = { "Analytics" })
	public void testBlogPostRailArchiveCollapse() {
		setupTestMethod(BLOG_POST_EN);

		try {
			rightRail.expandArchiveHeader();
			rightRail.collapseArchiveHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Collapse:Archive");
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
		doCommonClickAssertions(beacon);
	}

}
