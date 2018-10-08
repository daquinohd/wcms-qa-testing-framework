package gov.nci.webanalyticstests.blog.pages;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogPostClick_Test extends AnalyticsTestClickBase {

	private BlogPost blogPost;

	private Beacon beacon;
	private Actions action;

	private final String CANCER_CURRENTS_POST = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";

	@BeforeMethod(groups = { "Analytics" })
	public void setupBlogPost() {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + CANCER_CURRENTS_POST);
			this.blogPost = new BlogPost(driver);
			action.pause(2000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building BlogPost page object.");
		}
	}

	/**************** Blog Post body tests *****************************/

	@Test(groups = { "Analytics" }, priority = 1)
	public void testBlogPostBodyLinkClick() {
		try {
			System.out.println("Test body link click: ");
			String firstLinkText = blogPost.getBodyLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickBodyLink();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56));
			Assert.assertEquals(beacon.props.get(50), firstLinkText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyLink");
			Assert.assertTrue(currUrl.contains(beacon.props.get(67)));
		} catch (Exception e) {
			Assert.fail("Error clicking link in body.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 2)
	public void testBlogPostDefinitionLinkClick() {
		try {
			System.out.println("Test definition link click: ");
			String firstLinkText = blogPost.getDefinitionLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickDefinitionNoPopup();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56));
			Assert.assertEquals(beacon.props.get(50), firstLinkText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyGlossifiedTerm");
		} catch (Exception e) {
			Assert.fail("Error clicking definition link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 3)
	public void testBlogPostRecommendedClick() {
		try {
			System.out.println("Test 'Recommended' card click: ");
			String recommended = blogPost.getRecommendedLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickRecommended();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogFeatureCardClick");
			Assert.assertTrue(beacon.hasEvent(54));
			Assert.assertEquals(beacon.props.get(50), recommended);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BlogCard:1");
		} catch (Exception e) {
			Assert.fail("Error navigating blost post page.");
			e.printStackTrace();
		}
	}

	/**
	 * Shared Assert() calls for BlogPost_Test class.
	 * 
	 * @param currentUrl
	 * @param linkName
	 */
	private void doCommonClassAssertions(String currentUrl, String linkName) {
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
	}

}
