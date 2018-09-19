package gov.nci.webanalyticstests.click;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogLinks;
import gov.nci.blog.common.BlogRightRail;
import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;

public class BlogPost_Test extends AnalyticsTestClickBase {
	
	private BlogPost blogPost;
	private BlogRightRail rightRail;
	private BlogLinks blogLinks;

	private Beacon beacon;
	private Actions action;
	
	private final String CANCER_CURRENTS_POST = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";
	private final String CRCHD_POST = "	/about-nci/organization/crchd/blog/2017/celebrating-cure";

	@BeforeMethod(groups = { "Analytics" }) 
	public void setupBlogPost() {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + CANCER_CURRENTS_POST);
			this.blogPost = new BlogPost(driver);
			this.rightRail = blogPost.getRightRail();
			this.blogLinks = blogPost.getBlogLinks();
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
		
	/**************** Blog right rail tests *****************************/
	
	@Test(groups = { "Analytics" }, priority = 5)
	public void testBlogPostRailArchiveExpand() {
		try {
			System.out.println("Test expand archive click: ");
			rightRail.clickArchiveHeader();
			beacon = getBeacon();

			doCommonClassAssertions(driver.getCurrentUrl(), "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Expand:Archive");
		} catch (Exception e) {
			Assert.fail("Error expanding archive on rail.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 6)
	public void testBlogPostRailArchiveCollapse() {
		try {
			System.out.println("Test collapse archive click: ");
			action.pause(1000);
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveHeader();
			beacon = getBeacon();

			doCommonClassAssertions(driver.getCurrentUrl(), "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Collapse:Archive");
		} catch (Exception e) {
			Assert.fail("Error collapsing archive on rail.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 7)
	public void testBlogPostRailMonthClick() {
		try {
			System.out.println("Test month click: ");
			String currUrl = driver.getCurrentUrl();
			action.pause(1000);
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("May");
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), "2017:5");
		} catch (Exception e) {
			Assert.fail("Error clicking the month.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 8)
	public void testBlogPostRailFeaturedClick() {
		try {
			System.out.println("Test featured click: ");
			String currUrl = driver.getCurrentUrl();
			String featuredText = rightRail.getFeaturedItemText(0);			
			rightRail.clickFeaturedItem(0);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54));
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_FeaturedPosts:1");
		} catch (Exception e) {
			Assert.fail("Error clicking a featured blog link.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 9)
	public void testBlogPostRailCategoryClick() {
		try {
			System.out.println("Test category click: ");
			String currUrl = driver.getCurrentUrl();
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Category:2");
		} catch (Exception e) {
			Assert.fail("Error clicking a blog category link.");
			e.printStackTrace();
		}
	}
	
	/**************** Blog common link tests *****************************/
	
	@Test(groups = { "Analytics" }, priority = 10)
	public void testBlogPostSubscribeClick() {
		try {
			System.out.println("Test subscribe click: ");
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickSubscribeNoNav();
			beacon = getBeacon();
			
			doCommonClassAssertions(currUrl, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Subscribe");
		} catch (Exception e) {
			Assert.fail("Error clicking 'subscribe' link.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 11)
	public void testBlogPostNewerClick() {
		try {
			System.out.println("Test 'newer' click: ");
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickNewerPost();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Newer");
		} catch (Exception e) {
			Assert.fail("Error clicking 'newer' link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 11)
	public void testBlogPostOlderClick() {
		try {
			System.out.println("Test 'older' click: ");
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickOlderPost();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Older");
		} catch (Exception e) {
			Assert.fail("Error clicking 'older' link.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for BlogPost_Test class.
	 * @param currentUrl
	 * @param linkName
	 */
	private void doCommonClassAssertions(String currentUrl, String linkName) {
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
	}
	
}
