package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CardClick_Test extends AnalyticsTestClickBase {

	private Card card;
	private String currentUrl;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCommonData");
	}

	/**
	 * Navigate to the test URL and create a new card object.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			currentUrl = driver.getCurrentUrl();
			card = new Card(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	// ==================== Test methods ==================== //

	/// Test Home Page Card click events
	@Test(dataProvider = "HomeCardClickData", groups = { "Analytics" })
	public void testHomePageCardClick(String path, String titleSel, String linkSel, String cardTypePos) {
		System.out.println("Path: " + path + ", Card type/position: " + cardTypePos);
		setupTestMethod(path);

		try {
			int index = Integer.parseInt(cardTypePos.split(":")[1]) - 1;
			String titleText = card.getCardText(titleSel, index);
			String linkText = card.getCardText(linkSel, index);
			card.clickCardLink(linkSel, index);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, titleText, linkText, cardTypePos);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Landing Page Card click events
	@Test(dataProvider = "LandingCardClickData", groups = { "Analytics" })
	public void testLandingPageCardClick(String path, String titleSel, String linkSel, String cardTypePos) {
		System.out.println("Path: " + path + ", Card type/position: " + cardTypePos);
		setupTestMethod(path);

		try {
			int index = Integer.parseInt(cardTypePos.split(":")[1]) - 1;
			String titleText = card.getCardText(titleSel, index);
			String linkText = card.getCardText(linkSel, index);
			card.clickCardLink(linkSel, index);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, titleText, linkText, cardTypePos);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Topic Page Card click events
	@Test(dataProvider = "TopicCardClickData", groups = { "Analytics" })
	public void testTopicPageCardClick(String path, String titleSel, String linkSel, String cardTypePos) {
		System.out.println("Path: " + path + ", Card type/position: " + cardTypePos);
		setupTestMethod(path);

		try {
			int index = Integer.parseInt(cardTypePos.split(":")[1]) - 1;
			String titleText = card.getCardText(titleSel, index);
			String linkText = card.getCardText(linkSel, index);
			card.clickCardLink(linkSel, index);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, titleText, linkText, cardTypePos);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "HomeCardClickData")
	private Iterator<Object[]> getHomeCardClickData() {
		return getCardClickData("HomePageCards");
	}

	@DataProvider(name = "LandingCardClickData")
	private Iterator<Object[]> getLandingCardClickData() {
		return getCardClickData("LandingPageCards");
	}

	@DataProvider(name = "TopicCardClickData")
	private Iterator<Object[]> getTopicCardClickData() {
		return getCardClickData("TopicPageCards");
	}

	/**
	 * Get an iterator data object with path, types, selectors, and position for
	 * Card objects, filtered by content type.
	 * 
	 * @param dataSheet
	 * @return collection of card info
	 */
	private Iterator<Object[]> getCardClickData(String dataSheet) {
		String[] columnsToReturn = { "Path", "TitleSelector", "LinkSelector", "CardTypePosition" };
		return getSpreadsheetData(testDataFilePath, dataSheet, columnsToReturn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for CardClick_Test
	 * 
	 * @param beacon
	 * @param titleText
	 * @param linkText
	 * @param typePosition
	 *            [Card Type]:[position number]
	 */
	private void doCommonClassAssertions(Beacon beacon, String titleText, String linkText, String typePosition) {
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27), "Missing event27");
		Assert.assertEquals(beacon.linkName, "CardClick");
		Assert.assertEquals(beacon.props.get(57), titleText.trim());
		Assert.assertEquals(beacon.props.get(58), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))), "prop60 incorrect");
	}

}
