package gov.nci.webanalyticstests.clinicaltrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.common.Checkbox;
import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.framework.ParsedURL;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class TrialPrintLoad_Test extends AnalyticsTestLoadBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String CHECKBOX_ITEM_SELECTOR = ".cts-results-container input";

	private AdvanceSearchResults searchResults;

	// ==================== Setup methods ==================== //

	/**
	 * Navigate to page, create new SearchResults object, and clear any checkboxes.
	 */
	private void setupTestMethod() {
		driver.get(config.goHome() + PATH);

		try {
			searchResults = new AdvanceSearchResults(driver, null);
			Checkbox.uncheckAll(driver);
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
		} catch (Exception ex) {
			Assert.fail("Error loading CTS print page url: " + PATH);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test CTS Trial(s) print page load
	@Test(groups = { "Analytics" })
	public void testCtsPrintPageLoad() {
		System.out.println("Test CTS Trial(s) print page load:");
		setupTestMethod();

		try {
			String checkboxId = searchResults.getSelectedFieldAttribute(CHECKBOX_ITEM_SELECTOR, "id");
			Checkbox checkbox = new Checkbox(driver, CHECKBOX_ITEM_SELECTOR);
			checkbox.checkCheckbox(checkboxId);
			searchResults.clickPrintButton();

			// Give the print page time to load, then refresh
			Actions action = new Actions(driver);
			action.pause(2000).perform();
			driver.navigate().refresh();

			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, driver.getCurrentUrl());
			logger.log(LogStatus.PASS, "Test CTS Trial(s) print page load passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Utility methods ==================== //

	/**
	 * Get the print ID from the print page URL.
	 * 
	 * @param url
	 * @return
	 */
	private String getPrintId(String url) {
		ParsedURL parsedUrl;
		try {
			parsedUrl = new ParsedURL(url);
			return parsedUrl.getQueryParam("printid");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Tried to retrieve print ID from invalid URL.");
			return null;
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param url
	 */
	private void doCommonClassAssertions(Beacon beacon, String url) {

		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertEquals(beacon.channels, "Clinical Trials Print Results Page");
		Assert.assertEquals(beacon.props.get(3), "/");
		Assert.assertEquals(beacon.props.get(6), "Comprehensive Cancer Information");
		Assert.assertEquals(beacon.props.get(15), getPrintId(url));
		Assert.assertEquals(beacon.props.get(44), "Clinical Trials");
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Print Results Page");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
		Assert.assertEquals(beacon.eVars.get(15), beacon.props.get(15));
		Assert.assertEquals(beacon.eVars.get(44), beacon.props.get(44));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}

}
