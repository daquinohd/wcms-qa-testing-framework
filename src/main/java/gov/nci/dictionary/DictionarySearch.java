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


    public DictionarySearch (WebDriver browser) throws MalformedURLException,
                                                       UnsupportedEncodingException {
        super(browser);
        PageFactory.initElements(browser, this);
    }

    /**************** Sitewide Search Results Page Elements *****************************/
    @FindBy(css = "h1")
    WebElement txt_header;
    @FindBy(xpath = "//head/title")
    WebElement txt_title;
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
    // @FindBy(css = "div.last-SI p > a")
    @FindBy(css = "#cgvBody p > a")
    WebElement widgetLink;
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


    // Testing if the search button is visible
    // ---------------------------------------------------
    public boolean isSearchBtnVisible() {
        return search_btn.isDisplayed();
    }


    // Testing if the A-Z List is visible
    // ---------------------------------------------------
    public boolean isAzListVisible() {
        return az_list.isDisplayed();
    }


    // Testing if all of the elements of the A-Z list can be clicked and
    // returning a non-zero result  J9 O14 Q16 Y24 #26
    // -----------------------------------------------------------------
    public ResultPage clickAZListLetter(int position) throws MalformedURLException,
                                                 UnsupportedEncodingException {
        az_list_letters.get(position).click();

        return new ResultPage(getBrowser());
    }


    // Return the list of the AZ Header row
    // -----------------------------------------------------------------
    public List<WebElement> getAZList() {
        return az_list_letters;
    }


    // Getting the HTML document title
    // Note: The title is not displayed on the page,
    //       therefore txt_title.getText() doesn't work.
    // ---------------------------------------------------
    public String getPageTitle() {
        return txt_title.getAttribute("textContent").trim();
    }


    // Testing if the anchor link to the glossary widget exists on the page
    // ------------------------------------------------------------------------------
    public boolean isWidgetLinkVisible() {
        return widgetLink.isDisplayed();
    }


    // Find the anchor link for the dictionary widget and click the link
    // Returning a page object for the result page
    // -----------------------------------------------------------------
    public ResultPage clickWidgetLink() throws MalformedURLException,
                                               UnsupportedEncodingException {
        widgetLink.click();

        return new ResultPage(getBrowser());
    }

}
