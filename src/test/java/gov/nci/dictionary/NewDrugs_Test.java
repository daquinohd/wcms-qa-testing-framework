package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.framework.ParsedURL;

public class NewDrugs_Test extends NewDictionaryCommon {

    private String language = "EN";
    private String dictionary = "drug";
    private String url = "https://www-qa.cancer.gov/publications/dictionaries/cancer-drug";

/* ***************************** Test Methods ****************************************** */

    // Testing to confirm the page header (H1 element) is visible
    // ------------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void HeaderVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary header element is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean headerVisible = dict.isHeaderVisible();
            String headerNotVisibleTxt = "*** Error: Drug Dictionary Header "
                                       + "Not Displayed ***";
            Assert.assertTrue(headerVisible, headerNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Testing to confirm the correct page header is displayed
    // ------------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void HeaderDisplay(String url) {
        DictionarySearch dict;
        String dictHeaderTxt = "NCI Drug Dictionary";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if correct dictionary header is displayed: "
                   + dictHeaderTxt);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String headerTxt = dict.getHeaderText();
            String incorrectTitle = "*** Error: Drug Dictionary Header text mismatch ***";
            Assert.assertEquals(headerTxt, dictHeaderTxt, incorrectTitle);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with"/"Contains" radio buttons are displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void RadioButtonVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if radio buttons are visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean radioVisible = dict.isRadioBtnVisible();
            String radioNotVisible = "*** Error: Drug Dictionary Radio Button "
                                   + "Not Displayed ***";
            Assert.assertTrue(radioVisible, radioNotVisible);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with" radio button is selected by default
    // -------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void RadioStartsWithSelected(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if radio button StartsWith is selected");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean radioStartsWith = dict.isStartsWithSelected();
            String radioErrorTxt = "*** Error: Drug Dictionary Radio Button "
                                 + "Default Not Found ***";
            Assert.assertTrue(radioStartsWith, radioErrorTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the search input field is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void SearchInputVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if search entry field is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean searchVisible = dict.isSearchInputVisible();
            String searchErrorTxt = "*** Error: Drug Dictionary Search Input "
                                  + "Field Not Displayed ***";
            Assert.assertTrue(searchVisible, searchErrorTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the Search button is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void SearchBtnVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if Search button is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean searchBtnVisible = dict.isSearchBtnVisible();
            String searchBtnNotVisibleTxt = "*** Error: Drug Dictionary "
                                          + "Not Displayed ***";
            Assert.assertTrue(searchBtnVisible, searchBtnNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the A-Z list is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void AZListVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if A-Z list is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean azListVisible = dict.isAzListVisible();
            String azListNotVisibleTxt = "*** Error: Drug Dictionary A-Z List "
                                       + "Not Displayed ***";
            Assert.assertTrue(azListVisible, azListNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the A-Z list home page displays results for letter "A"
    // -------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void AZListHomeIsA(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing A-Z List home page is 'A'");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean azHome = dict.isAZListHomeLetterA();
            String azNotHomeTxt = "*** Error: Drug Dictionary A-Z Home Page"
                                + " Not Found ***";
            Assert.assertTrue(azHome, azNotHomeTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming each letter from the A-Z list can be selected and shows results
    // --------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void AZListSelectLetter(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing A-Z list. Selecting each letter");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            // Find the members of AZ List Header row to loop over
            List<WebElement> alphaList = dict.getAZList();

            int curCount = 0;
            int countLetters = alphaList.size();
            boolean AZLetterResultOK = true;

            // Loop over each of the letters of the alpha list
            // -----------------------------------------------
            for (int i = curCount; i < countLetters; i++) {
                System.out.print(alphaList.get(i).getText() + " ");
                ResultPage azListPage = dict.clickAZListLetter(i);

                // Check to see if there are results
                // ---------------------------------
                if (azListPage.getDefinitionCount() == 0) {
                    System.out.println(alphaList.get(i).getText()
                                       + " letter is empty!!!");
                    AZLetterResultOK = false;
                }
            }

            String AZLetterResultNotOkTxt = "*** Error: Drug Dictionary Result for "
                                          + "specified letter incorrect ***";
            Assert.assertTrue(AZLetterResultOK, AZLetterResultNotOkTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming each result page from the A-Z list has the right URL
    // --------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void AZListSelectLetterURL(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing A-Z list. Inspecting URL of result page");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            // Find the members of AZ List Header row to loop over
            List<WebElement> alphaList = dict.getAZList();

            int curCount = 0;
            int countLetters = alphaList.size();
            boolean AZLetterResultOK = true;

            // Loop over each of the letters of the alpha list
            // -----------------------------------------------
            for (int i = curCount; i < countLetters; i++) {
                String curLetter = alphaList.get(i).getText();
                logger.log(LogStatus.INFO, curLetter + " ");
                // Get the result page for the letter clicked
                ResultPage azListPage = dict.clickAZListLetter(i);
                // Get the URL for the result page
                ParsedURL letterURL = azListPage.getPageUrl();

                // Check if the URL of the result page is for this letter
                // ------------------------------------------------------
                if (!letterURL.getQuery().equals("expand=" + curLetter)) {
                    if (curLetter.equals("#") && letterURL.getQuery().equals("expand=%23")) {
                        AZLetterResultOK = true;
                    }
                    else {
                        logger.log(LogStatus.INFO, curLetter + " returns no results!!!");
                        AZLetterResultOK = false;
                    }
                }
            }

            String AZLetterResultNotOkTxt = "*** Error: Drug Dictionary URL for "
                                          + "specified letter incorrect ***";
            Assert.assertTrue(AZLetterResultOK, AZLetterResultNotOkTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Testing to confirm the correct HTML title text is displayed
    // ------------------------------------------------------------------------
    @Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
    public void TitleDisplay(String url) {
        DictionarySearch dict;
        String dictTitle = "NCI Drug Dictionary - National Cancer Institute";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary title is visible: " + dictTitle);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String titleText = dict.getPageTitle();
            String wrongTitleMsg = "*** Error: Drug Dictionary Title text mismatch ***";
            Assert.assertEquals(titleText, dictTitle, wrongTitleMsg);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }

/*  ***************************** Data Provider ***************************************** */

    // DataProvider to read the Excel spreadsheet with data containing URLs to be checked
    // Using worksheet indicating URLs with Page Options that are visible.
    // ----------------------------------------------------------------------------------

    @DataProvider(name = "DrugDictionary")
    public Iterator<Object[]> getDictUrl() {
        ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

        String url = AddHostname("/publications/dictionaries/cancer-drug");
        // System.out.println(url);
        Object ob[] = { url };
        myObjects.add(ob);

        return myObjects.iterator();
    }
}
