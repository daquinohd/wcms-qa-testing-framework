package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewGenetics_Test extends NewDictionaryCommon {

    private String language = "EN";
    private String dictionary = "genetics";

/*  ***************************** Test Methods ****************************************** */
    // Testing to confirm the page header (H1 element) is visible
    // ----------------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void HeaderVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary header element is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean headerVisible = dict.isHeaderVisible();
            String headerNotVisibleTxt = "*** Error: Genetics Dictionary Header"
                                       + " Not Displayed ***";
            Assert.assertTrue(headerVisible, headerNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Testing to confirm the correct page header is displayed
    // ------------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void HeaderDisplay(String url) {
        DictionarySearch dict;
        String dictHeaderTxt = "NCI Dictionary of Genetics Terms";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if correct dictionary header is displayed: "
                   + dictHeaderTxt);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String headerTxt = dict.getHeaderText();
            String incorrectTitle = "*** Error: Genetics Dictionary Header text mismatch ***";
            Assert.assertEquals(headerTxt, dictHeaderTxt, incorrectTitle);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with"/"Contains" radio buttons are displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void RadioButtonVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if radio buttons are visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean radioVisible = dict.isRadioBtnVisible();
            String radioNotVisibleTxt = "*** Error: Genetics Radio Button "
                                      + "Not Displayed ***";
            Assert.assertTrue(radioVisible, radioNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with" radio button is selected by default
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void RadioStartsWithSelected(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.log(LogStatus.INFO, "Testing if radio button StartsWith is selected");
        driver.get(url);
        try {
            dict = new DictionarySearch(driver);
            boolean radioStartsWith = dict.isStartsWithSelected();
            String radioErrorTxt = "*** Error: Genetics Radio Button "
                                 + "Default Not Found ***";
            Assert.assertTrue(radioStartsWith, radioErrorTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }

    // Confirming the search input field is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void SearchInputVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if search entry field is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean searchVisible = dict.isSearchInputVisible();
            String searchErrorTxt = "*** Error: Glossary Dictionary Search Input "
                                  + "Field Not Displayed ***";
            Assert.assertTrue(searchVisible, searchErrorTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }



    // Testing to confirm the correct HTML title text is displayed
    // ------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void TitleDisplay(String url) {
        DictionarySearch dict;
        String dictTitle = "NCI Dictionary of Genetics Terms - National Cancer Institute";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary title is visible: " + dictTitle);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String titleText = dict.getPageTitle();
            String wrongTitleMsg = "*** Error: Genetics Dictionary Title text mismatch ***";
            Assert.assertEquals(titleText, dictTitle, wrongTitleMsg);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the Search button is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
    public void SearchBtnVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if Search button is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean searchBtnVisible = dict.isSearchBtnVisible();
            String searchBtnNotVisibleTxt = "*** Error: Genetics Dictionary "
                                          + "search button Not Displayed ***";
            Assert.assertTrue(searchBtnVisible, searchBtnNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }
/*  ***************************** Data Provider ***************************************** */

    // DataProvider to read the Excel spreadsheet with data containing URLs to be checked
    // Using worksheet indicating URLs with Page Options that are visible.
    // ----------------------------------------------------------------------------------
    @DataProvider(name = "GeneticsDictionary")
    public Iterator<Object[]> loadGeneticsDictionary() {
        ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

        String url = AddHostname("/publications/dictionaries/genetics-dictionary");
        Object ob[] = { url };
        myObjects.add(ob);

        return myObjects.iterator();
    }
}
