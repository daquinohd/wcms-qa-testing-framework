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

public class ResultPage extends PageObjectBase {

    public ResultPage (WebDriver browser) throws MalformedURLException,
                                                 UnsupportedEncodingException {
        super(browser);
        PageFactory.initElements(browser, this);
    }

    /**************** Sitewide Search Results Page Elements *****************************/
    @FindBy(css = "h1")
    WebElement txt_header;
    @FindBy(css = "#search" )
    WebElement search_input;
    @FindBy(css = "button.button" )
    WebElement search_btn;
    @FindBy(css = "div.last-SI p > a")
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
}
