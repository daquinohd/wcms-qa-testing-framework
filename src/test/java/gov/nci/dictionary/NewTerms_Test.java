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

public class NewTerms_Test extends NewDictionaryCommon {

    private String language = "EN";
    private String dictionary = "glossary";

/*  ***************************** Test Methods ****************************************** */
    // Testing to confirm the page header (H1 element) is visible
    // ----------------------------------------------------------------------------------
    @Test(dataProvider = "Glossary", groups = { "dictionary" })
    public void HeaderVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if dictionary header element is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean headerVisible = dict.isHeaderVisible();
            String headerNotVisibleTxt = "*** Error: Cancer Term Dictionary Header"
                                       + " Not Displayed ***";
            Assert.assertTrue(headerVisible, headerNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Testing to confirm the correct page header is displayed
    // ------------------------------------------------------------------------------
    @Test(dataProvider = "Glossary", groups = { "dictionary" })
    public void HeaderDisplay(String url) {
        DictionarySearch dict;
        String dictHeaderTxt = "NCI Dictionary of Cancer Terms";
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if correct dictionary header is displayed: "
                   + dictHeaderTxt);
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            String headerTxt = dict.getHeaderText();
            String incorrectTitle = "*** Error: Glossary Dictionary Header text mismatch ***";
            Assert.assertEquals(headerTxt, dictHeaderTxt, incorrectTitle);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


    // Confirming the "Starts with"/"Contains" radio buttons are displayed
    // -------------------------------------------------------------------------
    @Test(dataProvider = "Glossary", groups = { "dictionary" })
    public void RadioButtonVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if radio buttons are visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean radioVisible = dict.isRadioBtnVisible();
            String radioNotVisibleTxt = "*** Error: Glossary Dictionary Radio Button "
                                      + "Not Displayed ***";
            Assert.assertTrue(radioVisible, radioNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }

    // Confirm the link to the glossary widget in the header paragraph is
    // visible.
    // -----------------------------------------------------------------------
    @Test(dataProvider = "Glossary", groups = { "dictionary" })
    public void WidgetLinkVisible(String url) {
        DictionarySearch dict;
        String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

        logger.log(LogStatus.INFO, "Testing if widget link is visible");
        driver.get(url);

        try {
            dict = new DictionarySearch(driver);
            boolean linkVisible = dict.isWidgetLinkVisible();
            String linkNotVisibleTxt = "*** Error: Term Dictionary Widget "
                                     + "Link Not Found ***";
            Assert.assertTrue(linkVisible, linkNotVisibleTxt);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Assert.fail("*** Error loading page in " + curMethod + " ***");
        }
    }


/*  ***************************** Data Provider ***************************************** */

    // DataProvider to read the Excel spreadsheet with data containing URLs to be checked
    // Using worksheet indicating URLs with Page Options that are visible.
    // ----------------------------------------------------------------------------------
    @DataProvider(name = "Glossary")
    public Iterator<Object[]> DictionaryLink() {
        ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

        String url = AddHostname("/publications/dictionaries/cancer-terms");
        Object ob[] = { url };
        myObjects.add(ob);

        return myObjects.iterator();
    }
}
