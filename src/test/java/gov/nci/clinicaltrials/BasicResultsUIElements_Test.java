package gov.nci.clinicaltrials;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicalTrial.pages.BasicSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;

/**
 * Test for the presence of UI elements.
 * 
 * **NOTE** These tests asssume that the "BasicSearchResultsURL" page URL contains a
 * link to a basic search results page with no search criteria.
 */
public class BasicResultsUIElements_Test extends BaseClass {

    String testDataFilePath;

    @BeforeClass(groups = { "Smoke" })
    @Parameters({ "browser" })
    public void setup(String browser) {
        logger = report.startTest(this.getClass().getSimpleName());
        pageURL = config.getPageURL("BasicSearchResultsURL");
        System.out.println("PageURL: " + pageURL);
        driver = BrowserManager.startBrowser(browser, pageURL);

        System.out.println("Basic search results setup done");
        testDataFilePath = config.getProperty("ClinicalTrialData");
    }

    /**
     * Common routine for retriving the basic search results page.
     */
    private BasicSearchResults getResultsPage(String pageUrl) throws MalformedURLException, UnsupportedEncodingException{
        // Create search page with chat prompt suppressed.
        driver.get(pageURL);
        SuppressChatPromptPageObject chatBlock = new SuppressChatPromptPageObject(driver, null);
        BasicSearchResults results = new BasicSearchResults(driver, chatBlock);
        return results;
    }

    /**
     * Verify the result count is present and (for an empty search) contains at least 10 results.
     */
    @Test(groups = { "Smoke" })
    public void uiVerifyResultCount() {

        try {

            // Load results for empty search critera.
            BasicSearchResults page = getResultsPage(pageURL);

            Assert.assertTrue(page.getResultsCountVisible(), "Results count not visible.");
            Assert.assertTrue(page.getResultsCountText().startsWith("Results 1-10 of"), "Result text not as expected");
            Assert.assertTrue(page.getResultsCountText().contains("all trials"), "'all trials' text missing.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("Error creating Basic Search Results page.");
            logger.log(LogStatus.ERROR, "Error creating Basic Search Results page.");
		}
    }

    /**
     * Verify page title text
     */
    @Test(groups = { "Smoke" })
    public void verifyPageTitle(){

        try {

            // Load results for empty search critera.
            BasicSearchResults page = getResultsPage(pageURL);

            Assert.assertEquals("Clinical Trials Search Results - National Cancer Institute", page.getPageTitle(), "Page title mismatch");

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("Error creating Basic Search Results page.");
            logger.log(LogStatus.ERROR, "Error creating Basic Search Results page.");
        }
    }

    /**
     * Verify the pager is present.
     */
    @Test(groups = { "Smoke" })
    public void verifyPagerIsPresent() {

        try {

            // Load results for empty search critera.
            BasicSearchResults page = getResultsPage(pageURL);

            Assert.assertTrue(page.getPager().IsVisible(), "Pager not displayed!");

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("Error creating Basic Search Results page.");
            logger.log(LogStatus.ERROR, "Error creating Basic Search Results page.");
        }
    }

    /**
     * Verify a results list is present.
     */
    @Test(groups = { "Smoke" })
    public void verifyResultListIsPresent() {

        try {
            // Load results for empty search critera.
            BasicSearchResults page = getResultsPage(pageURL);

            Assert.assertTrue(page.getResultsListIsVisible(), "Result list not displayed!");

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("Error creating Basic Search Results page.");
            logger.log(LogStatus.ERROR, "Error creating Basic Search Results page.");
        }
    }


    /**
     * TODO: Enable the tests for whether upper and lower "Select All"
     * buttons are present.
     */


    /**
     * Verify the upper "Select All" button is present.
     */
    //@Test(groups = { "Smoke" })
    public void verifyUpperSelectAllIsPresent() {

        try {
            // Load results for empty search critera.
            BasicSearchResults page = getResultsPage(pageURL);

            Assert.assertTrue(page.getUpperSelectAll().IsVisible(), "Top Select All not displayed!");

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("Error creating Basic Search Results page.");
            logger.log(LogStatus.ERROR, "Error creating Basic Search Results page.");
        }
    }

    /**
     * Verify a results list is present.
     */
    //@Test(groups = { "Smoke" })
    public void verifyLowerSelectAllIsPresent() {

        try {

            // Load results for empty search critera.
            BasicSearchResults page = getResultsPage(pageURL);

            Assert.assertTrue(page.getLowerSelectAll().IsVisible(), "Lower Select All not displayed!");

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("Error creating Basic Search Results page.");
            logger.log(LogStatus.ERROR, "Error creating Basic Search Results page.");
        }
    }

}