package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.RelatedResources;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class RelatedResourcesClick_Test extends AnalyticsTestClickBase {

	/**
	 * TODO:
	 * - PDQ pages? 
	 */
	
	private RelatedResources relatedResources;
	private Beacon beacon;

	private final String PATH_BLOG_POST = "/about-nci/organization/crchd/blog/2017/celebrating-cure";
	private final String PATH_ARTICLE = "/about-cancer/understanding/what-is-cancer";

	@BeforeMethod(groups = { "Analytics" })
	public void setupBlogPost() {
		try {
			this.relatedResources = new RelatedResources(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testBlogPostRelatedResourcesClick() {
		try {
			System.out.println("Test Blog Post Related Resources link click: ");
			driver.get(config.goHome() + PATH_BLOG_POST);
			String linkText = relatedResources.getRelatedResourcesLinkText(0);
			String currentUrl = driver.getCurrentUrl();
			relatedResources.clickRelatedResourcesLink(0);
			beacon = getBeacon();

			Assert.assertTrue(beacon.hasEvent(57));
			Assert.assertEquals(beacon.linkName, "BlogRelatedLinkClick");
			Assert.assertEquals(beacon.props.get(50), linkText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CRCHDDialogueDisparities_Post_RelatedResource:1");
			Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
		} catch (Exception e) {
			Assert.fail("Error clicking Related Resources link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void testArticleRelatedResourcesClick() {
		try {
			System.out.println("Test Article Related Resources link click: ");
			driver.get(config.goHome() + PATH_ARTICLE);
			String linkText = relatedResources.getRelatedResourcesLinkText(1);
			String currentUrl = driver.getCurrentUrl();
			relatedResources.clickRelatedResourcesLink(1);
			beacon = getBeacon();

			Assert.assertTrue(beacon.hasEvent(59));
			Assert.assertEquals(beacon.props.get(50), linkText);
			Assert.assertEquals(beacon.props.get(66), "Understanding Cancer_RelatedResource:2");
			Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
		} catch (Exception e) {
			Assert.fail("Error clicking Related Resources link.");
			e.printStackTrace();
		}
	}
	
}
