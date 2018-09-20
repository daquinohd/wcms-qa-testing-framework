package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.OnThisPage;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class OnThisPageClick_Test extends AnalyticsTestClickBase {

	private OnThisPage otp;
	private Beacon beacon;

	private final String PATH_ARTICLE = "/about-cancer/understanding/what-is-cancer";
	private final String PATH_FACTSHEET = "/about-cancer/diagnosis-staging/diagnosis/pathology-reports-fact-sheet";
	private final String PATH_PDQ = "/about-cancer/screening/patient-screening-overview-pdq#section/all";

	@BeforeMethod(groups = { "Analytics" })
	public void setupOtpTest() {
		try {
			this.otp = new OnThisPage(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testArticleOtpClick() {
		try {
			System.out.println("Test Article On This Page link click: ");
			driver.get(config.goHome() + PATH_ARTICLE);
			String currentUrl = driver.getCurrentUrl();
			String linkText = otp.getOnThisPageLinkText(0);
			String linkHref = otp.getOnThisPageHref(0);
			otp.clickOnThisPageLink(0);
			beacon = getBeacon();

			doCommonClassAssertions(currentUrl, linkText, linkHref);
		} catch (Exception e) {
			Assert.fail("Error clicking Article On This Page link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void testFactsheetOtpClick() {
		try {
			System.out.println("Test Factsheet On This Page link click: ");
			driver.get(config.goHome() + PATH_FACTSHEET);
			String currentUrl = driver.getCurrentUrl();
			String linkText = otp.getOnThisPageLinkText(1);
			String linkHref = otp.getOnThisPageHref(1);
			otp.clickOnThisPageLink(1);
			beacon = getBeacon();

			doCommonClassAssertions(currentUrl, linkText, linkHref);
		} catch (Exception e) {
			Assert.fail("Error clicking Factsheet On This Page link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void tesPdqOtpClick() {
		try {
			System.out.println("Test PDQ On This Page link click: ");
			driver.get(config.goHome() + PATH_PDQ);
			String currentUrl = driver.getCurrentUrl();
			String linkText = otp.getOnThisPageLinkText(2);
			String linkHref = otp.getOnThisPageHref(2);
			otp.clickOnThisPageLink(2);
			beacon = getBeacon();

			doCommonClassAssertions(currentUrl, linkText, linkHref);
		} catch (Exception e) {
			Assert.fail("Error clicking PDQ On This Page link.");
			e.printStackTrace();
		}
	}

	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(String currentUrl, String linkText, String linkHref) {

		String testPath = beacon.props.get(67);
		Assert.assertTrue(beacon.hasEvent(29));
		Assert.assertEquals(beacon.linkName, "OnThisPageClick");
		Assert.assertEquals(beacon.props.get(4), linkHref);
		Assert.assertEquals(beacon.props.get(66), "OnThisPage_" + linkText);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
