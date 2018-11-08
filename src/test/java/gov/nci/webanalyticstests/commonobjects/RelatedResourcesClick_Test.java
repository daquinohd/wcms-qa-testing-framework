package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.RelatedResources;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class RelatedResourcesClick_Test extends AnalyticsTestClickBase {

	// TODO: add PDQ page test, if one exists
	private final String PATH_BLOG_POST = "/about-nci/organization/crchd/blog/2017/celebrating-cure";
	private final String PATH_ARTICLE = "/about-cancer/understanding/what-is-cancer";

	private RelatedResources relatedResources;

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			this.relatedResources = new RelatedResources(driver);
			driver.get(config.goHome() + path);
			System.out.println("Path: " + path);
		} catch (Exception e) {
			Assert.fail("Error creating RelatedResources object in RelatedResourceClick_Test.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	@Test(groups = { "Analytics" })
	public void testBlogPostRelatedResourcesClick() {
		setupTestMethod(PATH_BLOG_POST);

		try {
			String linkText = relatedResources.getRelatedResourcesLinkText(0);
			String currentUrl = driver.getCurrentUrl();
			relatedResources.clickRelatedResourcesLink(0);

			Beacon beacon = getBeacon();
			Assert.assertTrue(beacon.hasEvent(57), "Missing event57");
			Assert.assertEquals(beacon.linkName, "BlogRelatedLinkClick");
			Assert.assertEquals(beacon.props.get(50), linkText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CRCHDDialogueDisparities_Post_RelatedResource:1");
			Assert.assertTrue(currentUrl.contains(beacon.props.get(67)), "prop67 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	@Test(groups = { "Analytics" })
	public void testArticleRelatedResourcesClick() {
		setupTestMethod(PATH_ARTICLE);

		try {
			String linkText = relatedResources.getRelatedResourcesLinkText(1);
			String currentUrl = driver.getCurrentUrl();
			relatedResources.clickRelatedResourcesLink(1);

			Beacon beacon = getBeacon();
			Assert.assertTrue(beacon.hasEvent(59), "Missing event59");
			Assert.assertEquals(beacon.props.get(50), linkText);
			Assert.assertEquals(beacon.props.get(66), "Understanding Cancer_RelatedResource:2");
			Assert.assertTrue(currentUrl.contains(beacon.props.get(67)), "prop67 incorrect");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

}
