package gov.nci.webanalyticstests.click;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogLinks;
import gov.nci.blog.common.BlogRightRail;
import gov.nci.blog.pages.BlogSeries;
import gov.nci.webanalytics.Beacon;

public class BlogSeries_Test extends AnalyticsTestClickBase {
	
	private BlogSeries blogSeries;
	private BlogRightRail rightRail;
	private BlogLinks blogLinks;

	private Beacon beacon;
	private Actions action;
	
	// TODO: Spanish series
	private final String CANCER_CURRENTS_EN = "/news-events/cancer-currents-blog";
	private final String CANCER_CURRENTS_ES = "/espanol/noticias/temas-y-relatos-blog";

	@BeforeMethod(groups = { "Analytics" }) 
	public void setupBlogPost() {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + CANCER_CURRENTS_EN);
			this.blogSeries = new BlogSeries(driver);
			this.rightRail = blogSeries.getRightRail();
			this.blogLinks = blogSeries.getBlogLinks();
			action.pause(5000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Blog Series page object.");
		}
	}

	/**************** Blog Series body tests *****************************/
	
	@Test(groups = { "Analytics" })
	public void testBlogSeriestTitleLinkClick() {
		try {
			System.out.println("Test body link click: ");
			blogSeries.clickBlogPostTitle();
			beacon = getBeacon();

			Assert.assertEquals(beacon.linkName, "SearchResults");
			doCommonClickAssertions(beacon);
		} catch (Exception e) {
			Assert.fail("Error clicking link in body.");
			e.printStackTrace();
		}
	}
	
	/**************** Blog Series right rail tests *****************************/
	
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailArchiveExpand() {
		try {
			System.out.println("Test expand archive click: ");
			rightRail.clickArchiveHeader();
			beacon = getBeacon();

			doCommonClassAssertions(driver.getCurrentUrl(), "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Expand:Archive");
		} catch (Exception e) {
			Assert.fail("Error expanding archive on rail.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailMonthClick() {
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

	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailFeaturedClick() {
		try {
			System.out.println("Test featured click: ");
			String currUrl = driver.getCurrentUrl();
			String featuredText = rightRail.getFeaturedItemText(0);			
			rightRail.clickFeaturedItem(0);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54));
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_FeaturedPosts:1");
		} catch (Exception e) {
			Assert.fail("Error clicking a featured blog link.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailCategoryClick() {
		try {
			System.out.println("Test category click: ");
			String currUrl = driver.getCurrentUrl();
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Category:2");
		} catch (Exception e) {
			Assert.fail("Error clicking a blog category link.");
			e.printStackTrace();
		}
	}
	
	/**************** Blog common link tests *****************************/
	
	@Test(groups = { "Analytics" })
	public void testBlogSeriesSubscribeClick() {
		try {
			System.out.println("Test subscribe click: ");
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickSubscribeNoNav();
			beacon = getBeacon();
			
			doCommonClassAssertions(currUrl, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Subscribe");
		} catch (Exception e) {
			Assert.fail("Error clicking 'subscribe' link.");
			e.printStackTrace();
		}
	}
	
	//@Test(groups = { "Analytics" })
	public void testBlogSeriesNewerClick() {
		try {
			System.out.println("Test 'newer' click: ");
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickOlderSeries();
			blogLinks.clickNewerSeries();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Newer");
		} catch (Exception e) {
			Assert.fail("Error clicking 'newer' link.");
			e.printStackTrace();
		}
	}

	//@Test(groups = { "Analytics" })
	public void testBlogSeriesOlderClick() {
		try {
			System.out.println("Test 'older' click: ");
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickOlderSeries();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Older");
		} catch (Exception e) {
			Assert.fail("Error clicking 'older' link.");
			e.printStackTrace();
		}
	}
	

	/**
	 * Shared Assert() calls for BlogSeries_Test class.
	 * @param currentUrl
	 * @param linkName
	 */
	private void doCommonClassAssertions(String currentUrl, String linkName) {
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
	}
	
}
