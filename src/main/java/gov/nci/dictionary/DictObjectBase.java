package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.PageObjectBase;

public class DictObjectBase extends PageObjectBase {
    static String tier = "DEV";


    public DictObjectBase (WebDriver browser) throws MalformedURLException, UnsupportedEncodingException {
        super(browser);
        PageFactory.initElements(browser, this);
    }


    // Testing if the H1 title element exists on the page
    // --------------------------------------------------
    public boolean TitleVisible() {
        List<WebElement> elementExists = getTitle();

        if (elementExists.size() > 0) {
            WebElement title = elementExists.get(0);
            return title.isDisplayed();
        }
        return false;
    }

    // Testing if the H1 title text is correct
    // ---------------------------------------
    public WebElement getTitleText() {
        List<WebElement> elementExists = getTitle();
        WebElement text_TitleText = elementExists.get(0);

        return text_TitleText;
    }

    public boolean FieldVisible(String elementSelector) {
        List<WebElement> elementExists = getElementControl(elementSelector);

        if (elementExists.size() > 0) {
            WebElement element = elementExists.get(0);
            return element.isDisplayed();
        }
        return false;
    }

    // Testing if the input field is correct
    // ---------------------------------------
    public Boolean SearchResultHeaderVisible(String selector) {
        List<WebElement> elementExists = getBrowser().findElements(By.cssSelector(selector));

        if (elementExists.size() > 0) {
            // WebElement text_TitleText = elementExists.get(0);
            return true;
        }

        return false;
    }

    // Testing if the input field is correct
    // ---------------------------------------
    public List<WebElement> SearchResultList(String selector) {
        List<WebElement> resultList = getBrowser().findElements(By.cssSelector(selector));

        return resultList;
    }

    // Testing if the anchor link for the glossary widget exists on the page
    // ---------------------------------------------------------------------
    public boolean WidgetLinkVisible() {
        List<WebElement> elementExists = getWidgetLink();

        if (elementExists.size() > 0) {
            WebElement title = elementExists.get(0);
            return title.isDisplayed();
        }
        return false;
    }

    // Find the anchor link for the dictionary widget and click the link
    // Ensure we're seeing the correct page
    // -----------------------------------------------------------------
    public boolean LinksToWidgetPage(WebDriver driver, String lang) {
        List<WebElement> followLink = getWidgetLink();
        String widgetTitle;

        if (followLink.size() > 0) {
            WebElement widgetLink = followLink.get(0);
            widgetLink.click();

            if (lang.equals("ES")){
                widgetTitle = "Widget del Diccionario del NCI - National Cancer Institute";
            } else {
                widgetTitle = "NCI Dictionary Widget - National Cancer Institute";
            }

            if ( driver.getTitle().equals(widgetTitle) ) {
                System.out.println("    Widget " + lang + " Found");
                return true;
            }
            System.out.println("*** Error: Widget Page NOT Found");
        }
        return false;
    }

    // Find the anchor link for the dictionary widget and click the link
    // Ensure we're seeing the correct page
    // -----------------------------------------------------------------
    public boolean AZListSelect(WebDriver driver, String elementSelector, String language) {
        List<WebElement> followLink = getElementControl(elementSelector);

        if (followLink.size() > 0) {
            WebElement widgetLink = followLink.get(1);
            widgetLink.click();

            List<WebElement> letterResults = driver.findElements(By.cssSelector("dt dfn"));

            // Only want to check that we're getting results. Don't need to check for exact numbers
            // since these numbers differ between environments
            // if (letterResults.size() == numberOfEntries) {
            if (letterResults.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean RadioBtnVisible() {
        List<WebElement> radioExists = getRadioControl();

        if (radioExists.size() > 0) {
            WebElement radioButton = radioExists.get(0);
            return radioButton.isDisplayed();
        }
        return false;
    }

    public boolean RadioDefault() {
        List<WebElement> elementExists = getRadioDefault();

        if (elementExists.size() > 0) {
            WebElement pocButton = elementExists.get(0);
            return pocButton.isSelected();
        }
        return false;
    }

    public boolean ButtonVisible(String buttonSelector) {
        List<WebElement> elementExists = getButtonControl(buttonSelector);

        if (elementExists.size() > 0) {
            WebElement pocButton = elementExists.get(0);
            return pocButton.isDisplayed();
        }
        return false;
    }

    public void SubmitSearchTerm(String inputSelector, String searchTerm) {
        List<WebElement> searchField = getElementControl(inputSelector);
        searchField.get(0).sendKeys(searchTerm);
        searchField.get(0).sendKeys(Keys.RETURN);
    }

    public void SearchTermPressButton(String inputSelector, String searchTerm) {
        List<WebElement> searchField = getElementControl(inputSelector);
        searchField.get(0).sendKeys(searchTerm);
        List<WebElement> searchBtn = getElementControl("#btnSearch");
        searchBtn.get(0).click();
    }

    public void ClickElement(String inputSelector, String term, WebDriver driver) {
        List<WebElement> searchField = getElementControl(inputSelector);
        // Walk through the list of elements found and click the term provided
        //
        // I wasn't able to click the element inside the for-loop.  This resulted
        // in a StaleElementReferenceException but clicking the element outside
        // the loop works as expected.
        Integer item = 0;
        for (WebElement e : searchField) {
            if (e.getText().equals(term)){
                // e.click();
                ScrollUtil.scrollIntoview(driver, e);
                break;
            }
            item++;
        }
        searchField.get(item).click();
    }

    private List<WebElement> getTitle() {
            List<WebElement> pageTitle = getBrowser().findElements(By.cssSelector("h1"));
            return pageTitle;
    }

    private List<WebElement> getWidgetLink() {
            List<WebElement> widgetLink = getBrowser().findElements(
                                           By.cssSelector("div.last-SI p > a"));
            return widgetLink;
    }

    private List<WebElement> getButtonControl(String buttonSelector) {
            List<WebElement> buttonControls = getBrowser().findElements(By.cssSelector("li." +
                                                                                        buttonSelector));
            return buttonControls;
    }

    private List<WebElement> getRadioControl() {
            List<WebElement> radioControls = getBrowser().findElements(By.cssSelector("div span.radio" ));
            return radioControls;
    }

    private List<WebElement> getRadioDefault() {
            List<WebElement> radioControls = getBrowser().findElements(By.cssSelector("#radioStarts" ));
            return radioControls;
    }

    private List<WebElement> getElementControl(String selector) {
            List<WebElement> elementControls = getBrowser().findElements(By.cssSelector(selector));
            return elementControls;
    }

    public void selectStartsWith() {
            List<WebElement> searchType = getBrowser().findElements(By.cssSelector("#lblStarts"));
            searchType.get(0).click();
    }

    public void selectContains() {
            List<WebElement> searchType = getBrowser().findElements(By.cssSelector("#lblContains"));
            searchType.get(0).click();
    }

	public void clickSearchResult(String selector, int index) {
		WebElement result = SearchResultList(selector).get(index);
		ScrollUtil.scrollIntoview(getBrowser(), result);
		result.click();
	}

	public void clickSearchResult(String selector) {
		clickSearchResult(selector, 0);
	}

}