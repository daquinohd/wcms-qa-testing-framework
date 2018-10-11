package gov.nci.webanalyticstests.blog.pages;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogPostClick_Test extends AnalyticsTestClickBase {

	private final String CANCER_CURRENTS_POST = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";

	private BlogPost blogPost;

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			this.blogPost = new BlogPost(driver);
		} catch (Exception e) {
			Assert.fail("Error building BlogPost page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Blog Post Body Link Click
	@Test(groups = { "Analytics" })
	public void testBlogPostBodyLinkClick() {
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String firstLinkText = blogPost.getBodyLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickBodyLink();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, currUrl, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56), "Missing event56");
			Assert.assertEquals(beacon.props.get(50), firstLinkText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyLink");
			Assert.assertTrue(currUrl.contains(beacon.props.get(67)), "prop67 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Definition Link Click
	@Test(groups = { "Analytics" })
	public void testBlogPostDefinitionLinkClick() {
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String firstLinkText = blogPost.getDefinitionLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickDefinitionNoPopup();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, currUrl, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56), "Missing event56");
			Assert.assertEquals(beacon.props.get(50), firstLinkText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyGlossifiedTerm");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Blog Post Recommended Link Click
	@Test(groups = { "Analytics" })
	public void testBlogPostRecommendedClick() {
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String recommended = blogPost.getRecommendedLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickRecommended();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, currUrl, "BlogFeatureCardClick");
			Assert.assertTrue(beacon.hasEvent(54), "Missing event56");
			Assert.assertEquals(beacon.props.get(50), recommended);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BlogCard:1");
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
	 * @param currentUrl
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon, String currentUrl, String linkName) {
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)), "prop67 incorrect");
	}

}
