package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CardClick_Test extends AnalyticsTestClickBase {

	private Card card;
	private Beacon beacon;
	private String titleText;
	private String linkText;
	private String currentUrl;

	private final String SELECTOR_PRIMARY_FEATURE = ".feature-primary .feature-card";
	private final String SELECTOR_SECONDARY_FEATURE = ".feature-secondary .feature-card";
	private final String SELECTOR_GUIDE = ".guide-card .card";
	private final String SELECTOR_MULTIMEDIA = ".multimedia div[class*=feature-card]";
	private final String SELECTOR_THUMB = ".card-thumbnail";
	private final String SELECTOR_INLINE = "#cgvBody .feature-card";
	private final String SELECTOR_TOPIC_FEATURE = ".topic-feature .feature-card";
	private final String PATH_CRCHD = "/about-nci/organization/crchd";

	@BeforeMethod(groups = { "Analytics" })
	public void setupClickTest() {
		try {
			this.card = new Card(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testHomePrimaryFeatureClick() {
		System.out.println("Test home Primary Feature Card click: ");
		driver.get(config.goHome());
		String linkAndTitle = SELECTOR_PRIMARY_FEATURE + " h3";
		getCardClickBeacon(linkAndTitle, linkAndTitle);

		doCommonClassAssertions("Feature:1");
	}

	@Test(groups = { "Analytics" })
	public void testHomeGuideCardClick() {
		System.out.println("Test home guide card click: ");
		driver.get(config.goHome());
		String title = SELECTOR_GUIDE + " h2";
		String link = title + " + ul li a";
		getCardClickBeacon(title, link);

		doCommonClassAssertions("Guide:1");
	}

	@Test(groups = { "Analytics" })
	public void testHomeMultimediaCardClick() {
		System.out.println("Test home multimedia card click: ");
		driver.get(config.goHome());
		String linkAndTitle = SELECTOR_MULTIMEDIA + " h3";
		getCardClickBeacon(linkAndTitle, linkAndTitle, 0);

		doCommonClassAssertions("Multimedia:1");
	}

	@Test(groups = { "Analytics" })
	public void testHomeThumbnailCardClick() {
		System.out.println("Test home Thumbnail card click: ");
		driver.get(config.goHome());
		String linkAndTitle = SELECTOR_THUMB + " h3 a";
		getCardClickBeacon(linkAndTitle, linkAndTitle);

		doCommonClassAssertions("Thumbnail:1");
	}

	@Test(groups = { "Analytics" })
	public void testLandingPrimaryFeatureClick() {
		System.out.println("Test Landing page primary feature card click: ");
		driver.get(config.getPageURL("LandingPage"));
		String linkAndTitle = SELECTOR_PRIMARY_FEATURE + " h3";
		getCardClickBeacon(linkAndTitle, linkAndTitle);

		doCommonClassAssertions("Feature:1");
	}

	@Test(groups = { "Analytics" })
	public void testLandingSecFeatureClick() {
		System.out.println("Test Landing page secondary feature card click: ");
		driver.get(config.getPageURL("LandingPage"));
		String linkAndTitle = SELECTOR_SECONDARY_FEATURE + " h3";
		getCardClickBeacon(linkAndTitle, linkAndTitle, 1);

		doCommonClassAssertions("SecondaryFeature:2");
	}

	@Test(groups = { "Analytics" })
	public void testTopicInlineFeatureCardClick() {
		System.out.println("Test Topic Page inline Feature card click: ");
		driver.get(config.getPageURL("TopicPage"));
		String linkAndTitle = SELECTOR_INLINE + " a h3";
		getCardClickBeacon(linkAndTitle, linkAndTitle);

		doCommonClassAssertions("InlineCard:1");
	}

	@Test(groups = { "Analytics" })
	public void testTopicSlottedFeatureCardClick() {
		System.out.println("Test Topic Page slotted Feature card click: ");
		driver.get(config.goHome() + PATH_CRCHD);
		String linkAndTitle = SELECTOR_TOPIC_FEATURE + " a h3";
		getCardClickBeacon(linkAndTitle, linkAndTitle, 1);

		doCommonClassAssertions("SlottedTopicCard:2");
	}

	@Test(groups = { "Analytics" })
	public void testTopicThumbnailCardClick() {
		System.out.println("Test Topic Page Thumbnail card click: ");
		driver.get(config.getPageURL("TopicPage"));
		String linkAndTitle = SELECTOR_THUMB + " h3 a";
		getCardClickBeacon(linkAndTitle, linkAndTitle, 2);

		doCommonClassAssertions("Thumbnail:3");
	}

	/**
	 * Go to the results page and retrieve the beacon request object.
	 * 
	 * @param titleSelector
	 * @param linkSelector
	 * @param index
	 */
	private void getCardClickBeacon(String titleSelector, String linkSelector, int index) {
		try {
			this.titleText = card.getCardText(titleSelector, index);
			this.linkText = card.getCardText(linkSelector, index);
			this.currentUrl = driver.getCurrentUrl();
			card.clickCardLink(linkSelector, index);
			this.beacon = getBeacon();
		} catch (Exception e) {
			Assert.fail("Error clicking card: " + titleText);
			e.printStackTrace();
		}
	}

	/**
	 * Go to the results page and retrieve the beacon request object.
	 * 
	 * @param titleSelector
	 * @param linkSelector
	 */
	private void getCardClickBeacon(String titleSelector, String linkSelector) {
		getCardClickBeacon(titleSelector, linkSelector, 0);
	}

	/**
	 * Shared Assert() calls for CardClick_Test
	 * 
	 * @param typePosition [Card Type]:[position number]
	 */
	private void doCommonClassAssertions(String typePosition) {
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27));
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57).trim(), titleText.trim());
		Assert.assertEquals(beacon.props.get(58).trim(), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
	}

}
