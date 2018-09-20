package gov.nci.webanalyticstests.cthp.common;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CthpCardClick_Test extends AnalyticsTestClickBase {

	private Card card;
	private Beacon beacon;
	private Actions action;
	private String currentUrl;

	@BeforeMethod(groups = { "Analytics" })
	public void setupClickTest() {
		try {
			card = new Card(driver);
			action = new Actions(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testCthpPatientOverview() {
		System.out.println("Test CTHP Patient Overview click: ");
		driver.get(config.getPageURL("CTHPPatient"));
		String cardTitle = "Overview";
		String linkText = "Bladder Cancer Symptoms, Tests, Prognosis, and Stages";
		getCardClickBeacon(linkText);

		doCommonClassAssertions(cardTitle, linkText, "CTHP:1");
	}

	@Test(groups = { "Analytics" })
	public void testCthpPatientScreening() {
		System.out.println("Test CTHP Patient Screening Card click: ");
		driver.get(config.getPageURL("CTHPPatient"));
		String cardTitle = "Screening";
		String linkText = "Bladder and Other Urothelial Cancers Screening";
		getCardClickBeacon(linkText);

		doCommonClassAssertions(cardTitle, linkText, "CTHP:4");
	}

	@Test(groups = { "Analytics" })
	public void testCthpHpTreatment() {
		System.out.println("Test CTHP HP Treatment Card click: ");
		driver.get(config.getPageURL("CTHPHP"));
		String cardTitle = "Treatment";
		String linkText = "Male Breast Cancer Treatment";
		getCardClickBeacon(linkText);

		doCommonClassAssertions(cardTitle, linkText, "CTHP:1");
	}

	@Test(groups = { "Analytics" })
	public void testCthpHpSupport() {
		System.out.println("Test CTHP HP Supportive Care Card click: ");
		driver.get(config.getPageURL("CTHPHP"));
		String cardTitle = "Supportive & Palliative Care";
		String linkText = "Nausea and Vomiting";
		getCardClickBeacon(linkText);

		doCommonClassAssertions(cardTitle, linkText, "CTHP:6");
	}

	/**
	 * Do the card click action and get the beacon object, as well as the current
	 * URL.
	 * 
	 * @param text - text string to find for the link.
	 */
	private void getCardClickBeacon(String text) {
		try {
			this.currentUrl = driver.getCurrentUrl();
			action.pause(1000).perform();
			card.clickCardText(text);
			beacon = getBeacon();
		} catch (Exception e) {
			Assert.fail("Error clicking CTHP card link");
			e.printStackTrace();
		}
	}

	/**
	 * Shared Assert() calls for CardClick_Test
	 * 
	 * @param cardTitle
	 * @param linkText
	 * @param typePosition formatted as cardType:positionNumber
	 */
	private void doCommonClassAssertions(String cardTitle, String linkText, String typePosition) {
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27));
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57).trim(), cardTitle.trim());
		Assert.assertEquals(beacon.props.get(58).trim(), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
	}

}
