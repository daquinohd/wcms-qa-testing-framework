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
import gov.nci.framework.ParsedURL;

public class NewTermsES_Test extends NewDictionaryCommon {

    private String language = "ES";
    private String dictionary = "glossary-es";

/*  ***************************** Test Methods ****************************************** */
    // Testing to confirm the page header (H1 element) is visible
    // ----------------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void HeaderVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary header element is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean headerVisible = dict.isHeaderVisible();
            String headerNotVisibleTxt = "*** Error: Cancer Term Dictionary-ES Header"
                                       + " Not Displayed ***";
            Assert.assertTrue(headerVisible, headerNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Testing to confirm the correct page header is displayed
    // ------------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void HeaderDisplay(String url) {
        DictionarySearch dict;
        String dictHeaderTxt = "Diccionario de cáncer";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if correct dictionary header is displayed: "
                   + dictHeaderTxt);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String headerTxt = dict.getHeaderText();
            String incorrectTitle = "*** Error: Glossary (ES) Dictionary Header "
                                  + "text mismatch ***";
            Assert.assertEquals(headerTxt, dictHeaderTxt, incorrectTitle);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with"/"Contains" radio buttons are displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void RadioButtonVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if radio buttons are visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean radioVisible = dict.isRadioBtnVisible();
            String radioNotVisibleTxt = "*** Glossary (ES): Drug Dictionary Radio Button "
                                      + "Not Displayed ***";
            Assert.assertTrue(radioVisible, radioNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirm the link to the glossary widget in the header paragraph is
    // visible.
    // -----------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void WidgetLinkVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if widget link is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean linkVisible = dict.isWidgetLinkVisible();
            String linkNotVisibleTxt = "*** Error: Term Dictionary (ES) Widget "
                                     + "Link Not Found ***";
            Assert.assertTrue(linkVisible, linkNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Finding the link for the Glossary Widget in the header paragraph and
    // test that the widget page exists by checking the resulting page header.
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void ClickWidgetLink(String url) {
        DictionarySearch dict;
        // ResultPage widgetPage;
        String pageHeader;
        String language = "ES";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing link to glossary widget goes to correct page");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            ResultPage widgetPage = dict.clickWidgetLink();
            // String widgetHeader = widgetPage.pageHeader();
            String widgetPageHeaderTxt = widgetPage.getHeaderText();

            switch (language) {
                case "EN":
                    pageHeader = "NCI Dictionary of Cancer Terms Widget";
                    break;
                case "ES":
                    pageHeader = "Widget del Diccionario de cáncer del NCI";
                    break;
                default:
                    pageHeader = "Not Found";
            }

            String wrongPageHeaderTxt = "*** Error: Glossary (ES) Click Widget Page "
                                      + "Link Not Found ***";

            Assert.assertEquals(pageHeader, widgetPageHeaderTxt, wrongPageHeaderTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with" radio button is selected by default
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void RadioStartsWithSelected(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.log(LogStatus.INFO, "Testing if radio button StartsWith is selected");
        driver.get(url);
        try {
            dict = new DictionarySearch(driver);
            boolean radioStartsWith = dict.isStartsWithSelected();
            String radioErrorTxt = "*** Error: Glossary (ES) Dictionary Radio Button "
                                 + "Default Not Found ***";
            Assert.assertTrue(radioStartsWith, radioErrorTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }



    // Confirming the search input field is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void SearchInputVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if search entry field is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean searchVisible = dict.isSearchInputVisible();
            String searchErrorTxt = "*** Error: Glossary (ES) Search Input "
                                  + "Field Not Displayed ***";
            Assert.assertTrue(searchVisible, searchErrorTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the Search button is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void SearchBtnVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if Search button is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean searchBtnVisible = dict.isSearchBtnVisible();
            String searchBtnNotVisibleTxt = "*** Error: Glossary (ES) Dictionary "
                                          + "search button Not Displayed ***";
            Assert.assertTrue(searchBtnVisible, searchBtnNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the A-Z list is displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void AZListVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if A-Z list is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean azListVisible = dict.isAzListVisible();
            String azListNotVisibleTxt = "*** Error: Glossary (ES) A-Z List "
                                       + "Not Displayed ***";
            Assert.assertTrue(azListVisible, azListNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the A-Z list home page displays results for letter "A"
    // -------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void AZListHomeIsA(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing A-Z List home page is 'A'");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean azHome = dict.isAZListHomeLetterA();
            String azNotHomeTxt = "*** Error: Glossary (ES) A-Z Home Page Not Found ***";
            Assert.assertTrue(azHome, azNotHomeTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming each letter from the A-Z list can be selected and shows results
    // --------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
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
                logger.log(LogStatus.INFO, alphaList.get(i).getText() + " ");
                ResultPage azListPage = dict.clickAZListLetter(i);

                // Check to see if there are results
                // ---------------------------------
                if (azListPage.getDefinitionCount() == 0) {
                    logger.log(LogStatus.INFO, alphaList.get(i).getText()
                                       + " returns no results!!!");
                    AZLetterResultOK = false;
                }
            }

            String AZLetterResultNotOkTxt = "*** Error: Glossary (ES) Result for "
                                          + "specified letter incorrect ***";
            Assert.assertTrue(AZLetterResultOK, AZLetterResultNotOkTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming each result page from the A-Z list has the right URL
    // --------------------------------------------------------------------------
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
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
    @Test(dataProvider = "GlossaryES", groups = { "dictionary" })
    public void TitleDisplay(String url) {
        DictionarySearch dict;
        String dictTitle = "Diccionario de cáncer - National Cancer Institute";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary title is visible: " + dictTitle);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String titleText = dict.getPageTitle();
            String wrongTitleMsg = "*** Error: Glossary (ES) Title text mismatch ***";
            Assert.assertEquals(titleText, dictTitle, wrongTitleMsg);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


/*  ***************************** Data Provider ***************************************** */

    // DataProvider to read the Excel spreadsheet with data containing URLs to be checked
    // Using worksheet indicating URLs with Page Options that are visible.
    // ----------------------------------------------------------------------------------
    @DataProvider(name = "GlossaryES")
    public Iterator<Object[]> loadAZList() {
        ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

        String url = AddHostname("/espanol/publicaciones/diccionario");
        Object ob[] = { url };
        myObjects.add(ob);

        return myObjects.iterator();
    }
}
