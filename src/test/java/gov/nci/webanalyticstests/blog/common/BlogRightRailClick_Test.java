package gov.nci.webanalyticstests.blog.common;

import com.relevantcodes.extentreports.LogStatus;
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
		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Blog Series Rail Featured click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailFeaturedClick() {
		System.out.println("Test Blog Series Rail Featured click: ");
		setupTestMethod(BLOG_SERIES_EN);

		try {
			String featuredText = rightRail.getFeaturedItemText(0);
			rightRail.clickFeaturedItem(0);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54), "Missing event54");
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_FeaturedPosts:1");
			logger.log(LogStatus.PASS, "Test Blog Series Rail Featured click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Featured click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailFeaturedClick() {
		System.out.println("Test Blog Post Rail Featured click:");
		setupTestMethod(BLOG_POST_EN);

		try {
			String featuredText = rightRail.getFeaturedItemText(0);
			rightRail.clickFeaturedItem(0);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54), "Missing event54");
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_FeaturedPosts:1");
			logger.log(LogStatus.PASS, "Test Blog Post Rail Featured click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Rail Category click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailCategoryClick() {
		System.out.println("Test Blog Series Rail Category click:");
		setupTestMethod(BLOG_SERIES_EN);

		try {
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Category:2");
			logger.log(LogStatus.PASS, "Test Blog Series Rail Category click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Category click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailCategoryClick() {
		System.out.println("Test Blog Post Rail Category click:");
		setupTestMethod(BLOG_POST_EN);

		try {
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Category:2");
			logger.log(LogStatus.PASS, "Test Blog Post Rail Category click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Rail Archive Expand click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailArchiveExpand() {
		System.out.println("Test Blog Series Rail Archive Expand click:");
		setupTestMethod(BLOG_SERIES_ES);

		try {
			rightRail.clickArchiveHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Series_Expand:Archive");
			logger.log(LogStatus.PASS, "Test Blog Series Rail Archive Expand click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Archive expand click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailArchiveExpand() {
		System.out.println("Test Blog Post Rail Archive expand click: ");
		setupTestMethod(BLOG_POST_ES);

		try {
			rightRail.clickArchiveHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrentsEsp_Post_Expand:Archive");
			logger.log(LogStatus.PASS, "Test Blog Post Rail Archive expand click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Series Rail month click
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailMonthClick() {
		System.out.println("Test Blog Series Rail month click:");
		setupTestMethod(BLOG_SERIES_EN);

		try {
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("5", "2017");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), "2017:5");
			logger.log(LogStatus.PASS, "Test Blog Series Rail month click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Month click
	@Test(groups = { "Analytics" })
	public void testBlogPostRailMonthClick() {
		System.out.println("Test Blog Post Rail Month click:");
		setupTestMethod(BLOG_POST_EN);

		try {
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("5", "2017");

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55), "Missing event55");
			Assert.assertEquals(beacon.props.get(50), "2017:5");
			logger.log(LogStatus.PASS, "Test Blog Post Rail Month click passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Rail Archive collapse
	@Test(groups = { "Analytics" })
	public void testBlogPostRailArchiveCollapse() {
		System.out.println("Test Blog Post Rail Archive collapse:");
		setupTestMethod(BLOG_POST_EN);

		try {
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveHeader();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Collapse:Archive");
			logger.log(LogStatus.PASS, "Test Blog Post Rail Archive collapse passed.");
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
