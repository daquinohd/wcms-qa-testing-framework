package gov.nci.webanalyticstests.blog.pages;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.pages.BlogSeries;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogSeriesClick_Test extends AnalyticsTestClickBase {
	
	private BlogSeries blogSeries;

	private Beacon beacon;
	private Actions action;
	
	// TODO: Spanish series
	private final String CANCER_CURRENTS_EN = "/news-events/cancer-currents-blog";

	@BeforeMethod(groups = { "Analytics" }) 
	public void setupBlogPost() {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + CANCER_CURRENTS_EN);
			this.blogSeries = new BlogSeries(driver);
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
