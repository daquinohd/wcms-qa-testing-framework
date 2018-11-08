package gov.nci.webanalyticstests.r4r.pages;

import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import gov.nci.Resources4Researchers.Resources4ResearchersHome;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RClickBase;

public class R4RHomePageClick_Test extends R4RClickBase {

	private final String ROOT_PATH = "/research/resources";

	private Resources4ResearchersHome r4rHome;

	// ==================== Setup methods ==================== //

	/**
	 * Go to dictionary search page and initialize DictionaryObject.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		driver.get(config.goHome() + path);
        new WebDriverWait(driver, 20).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
		driver.navigate().refresh();
		
		try {
			r4rHome = new Resources4ResearchersHome(driver, logger);
		} catch (Exception e) {
			Assert.fail("Error loading R4R page at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test the click event that fires off after the page has loaded
	@Test(groups = { "Analytics" })
	public void testPageLoadClickEvent() {
		setupTestMethod(ROOT_PATH);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.channels, "Research", "Channel");
			Assert.assertTrue(beacon.hasEvent(37), "Missing event37");
			Assert.assertEquals(beacon.linkName, "R4R Data Load", "Link name");
			Assert.assertEquals(beacon.props.get(39), "r4r_home|view");
			Assert.assertEquals(beacon.props.get(40), "Home View");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}

	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param linkName
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
	}

}
