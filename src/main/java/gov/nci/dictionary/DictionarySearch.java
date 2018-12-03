package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.PageObjectBase;

public class DictionarySearch extends PageObjectBase {
    // static String tier = "DT";


    public DictionarySearch (WebDriver browser) throws MalformedURLException, UnsupportedEncodingException {
        super(browser);
        PageFactory.initElements(browser, this);
    }

    /**************** Sitewide Search Results Page Elements *****************************/
    @FindBy(css = "h1")
    WebElement txt_header;
    @FindBy(css = "div span.radio")
    WebElement contains_toggle;
    @FindBy(css = "#radioStarts" )
    WebElement btn_startswith;
    @FindBy(css = "#radioContains" )
    WebElement btn_contains;
    @FindBy(css = "input.dictionary-search-input" )
    WebElement search_input;
    @FindBy(css = "input.button" )
    WebElement search_btn;
    @FindBy(css = "div.az-list" )
    WebElement az_list;
    @FindBy(css = "span.results-count" )
    WebElement results_home;
    @FindBy(css = "div.az-list ul li a")
    List<WebElement> az_list_letters;
    @FindBy(css = "div.results dfn span")
    WebElement defHeader;
    /**************** Sitewide Search Results Page Elements *****************************/


    // Testing if the H1 header element exists on the page
    // ---------------------------------------------------
    public boolean isHeaderVisible() {
        return txt_header.isDisplayed();
    }


    // Getting the H1 header text
    // ---------------------------------------------------
    public String getHeaderText() {
        return txt_header.getText();
    }


    // Testing if the radio button for the StartsWith/Contains selection is displayed
    // ------------------------------------------------------------------------------
    public boolean isRadioBtnVisible() {
        return contains_toggle.isDisplayed();
    }

    // Testing if the "Starts with" radio button is selected by default
    // -----------------------------------------------------------------
    public boolean isStartsWithSelected() {
        return btn_startswith.isSelected();
    }


    // Testing if the search input field is visible
    // ---------------------------------------------------
    public boolean isSearchInputVisible() {
        return search_input.isDisplayed();
    }


    //* Testing if the search button is visible
    // ---------------------------------------------------
    public boolean isSearchBtnVisible() {
        return search_btn.isDisplayed();
    }

}
