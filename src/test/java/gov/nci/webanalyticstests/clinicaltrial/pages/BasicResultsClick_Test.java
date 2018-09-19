package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BasicResultsClick_Test extends AnalyticsTestClickBase {
	
	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String BASIC_PAGE_1ST = "?rl=1";
	private final String BASIC_PAGE_2ND = "?loc=0&rl=1&pn=2&ni=10";	
	
	// Don't see an existing BasicSearchResultsPage, so I'm reusing advanced for all instances
	private AdvanceSearchResults searchResults;
	private Beacon beacon;
		
	@Test(groups = { "Analytics" })
	public void testBasicStartOverClick() {
		try {
			System.out.println("Basic 'Start Over' click event:");
			setSearchResults(BASIC_PAGE_1ST);
			// TODO: build utility function to stop nav
			searchResults.clickStartOverNoNav();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(49));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start over");
			logger.log(LogStatus.PASS, "Basic 'Start Over' click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic 'Start Over' click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 1)
	public void testPrintError() {
		try {
			System.out.println("Basic print error click event:");
			setSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "printselected|noneselected");
			logger.log(LogStatus.PASS,"Basic print error click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print error click event.");
			e.printStackTrace();
		}
	}
	
	//@Test(groups = { "Analytics" }, priority = 2)
	public void testPrintOneItem() {
		try {
			System.out.println("Basic print one item click event:");
			setSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.selectCheckboxByIndex(2);
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS,"Basic print one item click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print one item click event.");
			e.printStackTrace();
		}
	}
	
	//@Test(groups = { "Analytics" }, priority = 3)
	public void testPrintMultiItems() {
		try {
			System.out.println("Basic print one item click event:");
			setSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS,"Basic print one item click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print one item click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 4)
	public void testPrintAllItems() {
		try {
			System.out.println("Basic print all items click event:");
			setSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS,"Basic print all items click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print all items click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testBasicLinkRanking() {
		try {
			System.out.println("Basic ranked result click event:");
			setSearchResults(BASIC_PAGE_2ND);
			searchResults.clickResultLinkByIndex(0);
			beacon = getBeacon();
						
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(42));
			Assert.assertEquals(beacon.props.get(12), "clinicaltrials_basic");
			Assert.assertEquals(beacon.props.get(13), "1|page 2");
			Assert.assertEquals(beacon.props.get(12), beacon.eVars.get(12));
			logger.log(LogStatus.PASS,"Basic ranked result click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic ranked result click event.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Go to the results page and create a new searchresults object.
	 * @param queryParams
	 */
	private void setSearchResults(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			searchResults = new AdvanceSearchResults(driver, chatPrompt);
		} catch (Exception ex) {
			Assert.fail("Error loading Advanced CTS results url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for BasicResultsClick_Test
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}
	
}

