package gov.nci.webanalyticstests.commonobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CardClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "HomeLandingTopicCards";

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
	public void testHomePageCardClick(String path, String cardType, String titleSel, String linkSel, int index) {
		System.out.println("Test Home Page " + cardType + " Card click:");
		setupTestMethod(path);

		try {
			String titleText = card.getCardText(titleSel, index);
			String linkText = card.getCardText(linkSel, index);
			String cardPos = cardType + ":" + (index + 1);
			card.clickCardLink(linkSel, index);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, titleText, linkText, cardPos);
			logger.log(LogStatus.PASS, "Test Home Page " + cardType + " Card click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Landing Page Card click events
	@Test(dataProvider = "LandingCardClickData", groups = { "Analytics" })
	public void testLandingPageCardClick(String path, String cardType, String titleSel, String linkSel, int index) {
		System.out.println("Test Landing Page " + cardType + " Card click:");
		setupTestMethod(path);

		try {
			String titleText = card.getCardText(titleSel, index);
			String linkText = card.getCardText(linkSel, index);
			String cardPos = cardType + ":" + (index + 1);
			card.clickCardLink(linkSel, index);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, titleText, linkText, cardPos);
			logger.log(LogStatus.PASS, "Test Landing Page " + cardType + " Card click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Topic Page Card click events
	@Test(dataProvider = "TopicCardClickData", groups = { "Analytics" })
	public void testTopicPageCardClick(String path, String cardType, String titleSel, String linkSel, int index) {
		System.out.println("Test Topic Page " + cardType + " Card click:");
		setupTestMethod(path);

		try {
			String titleText = card.getCardText(titleSel, index);
			String linkText = card.getCardText(linkSel, index);
			String cardPos = cardType + ":" + (index + 1);
			card.clickCardLink(linkSel, index);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, titleText, linkText, cardPos);
			logger.log(LogStatus.PASS, "Test Topic Page " + cardType + " Card click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "HomeCardClickData")
	private Iterator<Object[]> getHomeCardClickData() {
		return getCardClickData("Home");
	}

	@DataProvider(name = "LandingCardClickData")
	private Iterator<Object[]> getLandingCardClickData() {
		return getCardClickData("Landing");
	}

	@DataProvider(name = "TopicCardClickData")
	private Iterator<Object[]> getTopicCardClickData() {
		return getCardClickData("Topic");
	}

	/**
	 * Get an iterator data object with path, types, selectors, and position for
	 * Card objects, filtered by content type.
	 * 
	 * @param filter
	 * @return collection of card info
	 */
	private Iterator<Object[]> getCardClickData(String filter) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String contentType = excelReader.getCellData(TESTDATA_SHEET_NAME, "ContentType", rowNum);

			if (contentType.equalsIgnoreCase(filter)) {
				String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
				String cardType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CardType", rowNum);
				String titleSel = excelReader.getCellData(TESTDATA_SHEET_NAME, "TitleSelector", rowNum);
				String linkSel = excelReader.getCellData(TESTDATA_SHEET_NAME, "LinkSelector", rowNum);
				int index = excelReader.getCellIntegerData(TESTDATA_SHEET_NAME, "CardPos", rowNum);
				Object ob[] = { path, cardType, titleSel, linkSel, index };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
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
		Assert.assertTrue(beacon.hasEvent(27));
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57), titleText.trim());
		Assert.assertEquals(beacon.props.get(58), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
	}

}
