package gov.nci.clinicaltrials;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicalTrial.common.Delighter;
import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;


public class BasicSearchDelighters_Test extends BaseClass {

    // The search page.  All
    BasicSearch basicSearch;
    String testDataFilePath;

    @BeforeClass(groups = { "Smoke", "current" })
    @Parameters({ "browser" })
    public void setup(String browser) {
        logger = report.startTest(this.getClass().getSimpleName());

        // TODO: Make pageURL a local instance variable.
        pageURL = config.getPageURL("BasicClinicalTrialSearchURL");
        this.driver = BrowserManager.startBrowser(browser, config, pageURL);

		try {
            // Create search page with chat prompt suppressed.
            SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
            basicSearch = new BasicSearch(driver, chatPrompt);
        } catch (Exception e) {
            basicSearch = null;
            logger.log(LogStatus.ERROR, "Error creating Basic Search page.");
        }
        System.out.println("Basic search setup done");
        testDataFilePath = config.getProperty("ClinicalTrialData");
    }

    /**
     * Utility method for loading the CTS basic search page.
     */
    private BasicSearch LoadSearchPage() {

        BasicSearch page = null;

        try {
            // Create search page with chat prompt suppressed.
            SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
            page = new BasicSearch(driver, chatPrompt);
        } catch (Exception e) {
            basicSearch = null;
            logger.log(LogStatus.ERROR, "Error creating Basic Search page.");
        }

        return page;
    }

    /**
     * Presence and link for the Live Help delighter.
     */
    @Test(groups = { "Smoke" })
	public void verifyDelighterLiveHelp() {
        driver.get(pageURL);
        BasicSearch page = LoadSearchPage();

        Delighter delighter = page.getLiveHelpDelighter();
        String expectedUrl = config.getProperty("CTSLiveHelpDelighterPath");

        Assert.assertTrue(delighter.isDisplayed(), "Live Help delighter is not displayed.");
        Assert.assertEquals(delighter.getLinkPath(), expectedUrl, "Link URL mismatch.");
    }

    /**
     * Presence and link for the What Are Clinical Trials delighter.
     */
    @Test(groups = { "Smoke" })
	public void verifyDelighterWhatAre() {
        driver.get(pageURL);
        BasicSearch page = LoadSearchPage();

        Delighter delighter = page.getWhatAreDelighter();
        String expectedUrl = config.getProperty("CTSWhatAreTrialsDelighterPath");

        Assert.assertTrue(delighter.isDisplayed(), "What Are Clinical Trials delighter is not displayed.");
        Assert.assertEquals(delighter.getLinkPath(), expectedUrl, "Link URL mismatch.");
    }

    /**
     * Presence and link for the Which Clinical Trials are right for you delighter.
     */
    @Test(groups = { "Smoke" })
    public void verifyDelighterWhich() {
        driver.get(pageURL);
        BasicSearch page = LoadSearchPage();

        Delighter delighter = page.getWhichTrialDelighter();
        String expectedUrl = config.getProperty("CTSWhichTrialsDelighterPath");

        Assert.assertTrue(delighter.isDisplayed(), "What Are Clinical Trials delighter is not displayed.");
        Assert.assertEquals(delighter.getLinkPath(), expectedUrl, "Link URL mismatch.");
    }

}