package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.OnThisPage;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class OnThisPageClick_Test extends AnalyticsTestClickBase {

	private final String PATH_ARTICLE = "/about-cancer/understanding/what-is-cancer";
	private final String PATH_FACTSHEET = "/about-cancer/diagnosis-staging/diagnosis/pathology-reports-fact-sheet";
	private final String PATH_PDQ = "/about-cancer/screening/patient-screening-overview-pdq#section/all";

	private OnThisPage otp;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			otp = new OnThisPage(driver);
			driver.get(config.goHome() + path);
			currentUrl = driver.getCurrentUrl();
		} catch (Exception e) {
			Assert.fail("Error loading OnThisPage object at " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Article On This Page link click
	@Test(groups = { "Analytics" })
	public void testArticleOtpClick() {
		System.out.println("Test Article On This Page link click: ");
		setupTestMethod(PATH_ARTICLE);

		try {
			String linkText = otp.getOnThisPageLinkText(0);
			String linkHref = otp.getOnThisPageHref(0);
			otp.clickOnThisPageLink(0);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, linkText, linkHref);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test Factsheet On This Page link click
	@Test(groups = { "Analytics" })
	public void testFactsheetOtpClick() {
		System.out.println("Test Factsheet On This Page link click: ");
		setupTestMethod(PATH_FACTSHEET);

		try {
			String linkText = otp.getOnThisPageLinkText(1);
			String linkHref = otp.getOnThisPageHref(1);
			otp.clickOnThisPageLink(1);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, linkText, linkHref);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	/// Test PDQ On This Page link click
	@Test(groups = { "Analytics" })
	public void tesPdqOtpClick() {
		System.out.println("Test PDQ On This Page link click: ");
		setupTestMethod(PATH_PDQ);

		try {
			String linkText = otp.getOnThisPageLinkText(2);
			String linkHref = otp.getOnThisPageHref(2);
			otp.clickOnThisPageLink(2);

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, linkText, linkHref);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon, String linkText, String linkHref) {
		String testPath = beacon.props.get(67);
		
		Assert.assertTrue(beacon.hasEvent(29), "Missing event29");
		Assert.assertEquals(beacon.linkName, "OnThisPageClick");
		Assert.assertEquals(beacon.props.get(4), linkHref);
		Assert.assertEquals(beacon.props.get(66), "OnThisPage_" + linkText);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))), "prop67 incorrect");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
