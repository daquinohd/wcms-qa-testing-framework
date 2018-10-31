package gov.nci.webanalyticstests.cthp.common;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Iterator;

import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CthpCardClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_HP = "CTHPHPCard";
	private final String TESTDATA_SHEET_PATIENT = "CTHPPatientCard";

	private String testDataFilePath;
	private Card card;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTHPData");
	}

	/**
	 * Create new card object and go to
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			currentUrl = driver.getCurrentUrl();
			card = new Card(driver);
		} catch (Exception e) {
			Assert.fail("Error loading CTHP Card object at: " + driver.getCurrentUrl());
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	// Test CTHP HP Card click event
	@Test(dataProvider = "CthpHpCard", groups = { "Analytics" })
	public void testCthpHpCardClick(String path, String cardTitle, String linkText, String cardPos) {
		System.out.println("Path: " + path + ", Card title: " + cardTitle);
		setupTestMethod(path);

		try {
			card.clickCardText(linkText);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, cardTitle, linkText, cardPos);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// Test CTHP Patient Card click event
	@Test(dataProvider = "CthpPatientCard", groups = { "Analytics" })
	public void testCthpPatientCardClick(String path, String cardTitle, String linkText, String cardPos) {
		System.out.println("Path: " + path + ", Card title: " + cardTitle);
		setupTestMethod(path);

		try {
			card.clickCardText(linkText);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, cardTitle, linkText, cardPos);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "CthpHpCard")
	private Iterator<Object[]> getCthpHpCardData() {
		return getCthpCardData(TESTDATA_SHEET_HP);
	}

	@DataProvider(name = "CthpPatientCard")
	private Iterator<Object[]> getCthpPatientCardData() {
		return getCthpCardData(TESTDATA_SHEET_PATIENT);
	}

	/**
	 * Get a testable data object based on audience sheet.
	 * 
	 * @param sheetName
	 * @return object containing path, card title, linktext, position data
	 */
	private Iterator<Object[]> getCthpCardData(String sheetName) {
		String[] columnsToReturn = { "Path", "CardTitle", "LinkText", "CardPos" };
		return getSpreadsheetData(testDataFilePath, sheetName, columnsToReturn);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param cardTitle
	 * @param linkText
	 * @param typePosition
	 *            formatted as cardType:positionNumber
	 */
	private void doCommonClassAssertions(Beacon beacon, String cardTitle, String linkText, String typePosition) {
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27), "Missing event27");
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57), cardTitle.trim());
		Assert.assertEquals(beacon.props.get(58), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))), "prop60 incorrect");
	}

}
