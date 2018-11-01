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


    //* Testing if the H1 header element exists on the page
    // ---------------------------------------------------
    public boolean headerVisible() {
        return txt_header.isDisplayed();
    }

    //* Getting the H1 header text
    // ---------------------------------------
    public String getHeaderText() {
        return txt_header.getText();
    }

    //* Testing if the radio button for the StartsWith/Contains selection is displayed
    // ------------------------------------------------------------------------------
    public boolean radioBtnVisible() {
        return contains_toggle.isDisplayed();
    }

    //* Testing if the "Starts with" radio button is selected by default
    // -----------------------------------------------------------------
    public boolean radioDefault() {
        return btn_startswith.isSelected();
    }

    //* Testing if the search input field is visible
    // ---------------------------------------------
    public boolean searchInputVisible() {
        return search_input.isDisplayed();
    }

    //* Testing if the search button is visible
    // ----------------------------------------
    public boolean searchBtnVisible() {
        return search_btn.isDisplayed();
    }

    //* Testing if the A-Z List is visible
    // -----------------------------------
    public boolean azListVisible() {
        return az_list.isDisplayed();
    }

    //* Testing if the A-Z List home page displays the results for letter "A"
    // ----------------------------------------------------------------------
    public boolean azListHomeIsA() {
        String resultHome = results_home.getText();
        String[] resultTokens = resultHome.trim().split("\\s+");
        Boolean isNumber = StringUtils.isNumeric(resultTokens[0]);

        if (isNumber && resultTokens[4].equals("A")) {
            return true;
        }
        return false;
    }

    //* Testing if the A-Z List home page URL is correct
    // ----------------------------------------------------------------------
    public boolean azListHomeUrl(String dictionary) {
        String pagePath = this.getPageUrl().getPath();
        String dictPath = "/publications/dictionaries/";
        String dictPathEs = "/espanol/publicaciones/";

        // Setting the URL based on the dictionary input
        // ---------------------------------------------
        if (dictionary.equals("drug")) {
            dictPath += "cancer-drug";
        } else if (dictionary.equals("genetics")) {
            dictPath += "genetics-dictionary";
        } else if (dictionary.equals("glossary")) {
            dictPath += "cancer-terms";
        } else if (dictionary.equals("glossary-es")) {
            dictPathEs += "diccionario";
        }

        if (pagePath.equals(dictPath)) {
            return true;
        }
        return false;
    }

    //* Testing if all of the elements of the A-Z list can be clicked and
    // returning a non-zero result  J9 O14 Q16 Y24 #26
    // -----------------------------------------------------------------
    public boolean azListSelect(WebDriver driver, String dictionary, String language) {
        int countLetters = az_list_letters.size();
        int curCount = 0;
        for (int i = curCount; i < countLetters; i++) {
            List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
            System.out.println(letter.get(i).getText());
            letter.get(i).click();
            try {
                java.util.concurrent.TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            List<WebElement> letterResults = driver.findElements(By.cssSelector("dt dfn"));
            // System.out.println(letterResults.size());

            // Only want to check that we're getting results. Don't need to check for exact numbers
            // since these numbers differ between environments
            if (letterResults.size() == 0) {
                System.out.println(letter.get(i).getText() + " letter is empty!!!");
                return false;
            }
            // driver.navigate().back();
        }

        return true;
    }

    //* Testing if the right URL is listed of the A-Z list when element is clicked and
    // returning a non-zero result  J9 O14 Q16 Y24 #26
    // -----------------------------------------------------------------
    public boolean azListSelectUrl(WebDriver driver, String dictionary, String language) {
        int curCount = 0;
        String[] urlParts;
        String urlExpected;

        // Loop through the alphabet and test the URL is the correct letter
        // ----------------------------------------------------------------
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
            System.out.println(letter.get(curCount).getText());
            letter.get(curCount).click();
            urlParts = driver.getCurrentUrl().split("[?]");  // '?' is special character
            urlExpected = "expand=" + alphabet;

            // If the URL doesn't match the expected value return false
            // --------------------------------------------------------
            if (!urlParts[1].equals(urlExpected)){
                return false;
            }

            curCount++;
        }

        String[] extra = new String[2];
        extra[0] = "%23";  // Hash tag
        extra[1] = "All";

        // All dictionaries also have an entry for '#' and some include 'All'
        // ------------------------------------------------------------------
        for (int i = 0; i < 2; i++) {
            if (i == 1 && dictionary == "glossary" || dictionary == "glossaryES") {
                continue;
            }
            List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
            System.out.println(letter.get(curCount).getText());
            letter.get(curCount).click();
            urlParts = driver.getCurrentUrl().split("[?]");  // '?' is special character
            urlExpected = "expand=" + extra[i];

            // If the URL doesn't match the expected value return false
            // --------------------------------------------------------
            if (!urlParts[1].equals(urlExpected)){
                return false;
            }
            curCount++;
        }
        return true;
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

    public boolean ButtonVisible(String buttonSelector) {
        List<WebElement> elementExists = getButtonControl(buttonSelector);

        if (elementExists.size() > 0) {
            WebElement pocButton = elementExists.get(0);
            return pocButton.isDisplayed();
        }
        return false;
    }

    // Test Content
    // ---------------------------------------------------------------------
    public boolean submitSearchTerm(WebDriver driver) {
	String term = "becatecarin";
	String searchTerm = term.substring(0, 5);

        selectStartsWith();
        search_input.sendKeys(searchTerm);
        hitEnter(driver);
        System.out.println("DADA");
        System.out.println(defHeader.getText());

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    // Test URL
    // ---------------------------------------------------------------------
    public boolean submitSearchTermUrl(WebDriver driver) {
	String term = "becatecarin";
	String searchTerm = term.substring(0, 5);
        String[] urlParts;
	String[] path = { "https:", "", "www-qa.cancer.gov", "publications", "dictionaries", 
		          "cancer-drug", "def", term};

        selectStartsWith();
        search_input.sendKeys(searchTerm);
        hitEnter(driver);

        // System.out.println(driver.getCurrentUrl());
        urlParts = driver.getCurrentUrl().split("[/]");  // '/' is special character
	// System.out.println(urlParts.length);
	// System.out.println(path.length);
        System.out.println("LiLi");

	for (int i = 3; i < path.length; i++) {
	    System.out.println(urlParts[i] + " = " + path[i]);
	    if (!urlParts[i].equals(path[i])) {
		    System.out.println(urlParts[i]);
		    System.out.println(path[i]);
		    return false;
	    }
	}

        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        // System.out.println(az_list_letters.size());
        // System.out.println(this.getPageUrl().getPath());

        return true;
    }

//    private void defPath(String term) {
//	System.out.println("In defPath");
//	path = new String[10];
//
//	path[0] = "https:";
//	path[1] = "server";
//	path[2] = "publications";
//	path[3] = "dictionaries";
//	path[4] = "cancer-drug";
//	path[5] = "def";
//	path[6] = term;
 //   }

    public boolean submitSearchTerm2() {
		String term = "bevacizumab";
		String searchTerm = term.substring(0, 8);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
        // List<WebElement> searchField = getElementControl(inputSelector);

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean submitSearchTerm3() {
		String term = "lenapenem";
		String searchTerm = term.substring(2, 8);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
        // List<WebElement> searchField = getElementControl(inputSelector);

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean submitSearchTerm4() {
		String term = "bevacizumab";
		String searchTerm = term.substring(2, 7);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
        // List<WebElement> searchField = getElementControl(inputSelector);

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean submitSearchTerm5() {
		String term = "bevacizumab";
		String searchTerm = term.substring(2, 6);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
        // List<WebElement> searchField = getElementControl(inputSelector);
        this.selectContains();

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean submitSearchTerm6() {
		String term = "bevacizumab";
		String searchTerm = term.substring(0, 4);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
        // List<WebElement> searchField = getElementControl(inputSelector);
        this.selectContains();

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean submitSearchTerm7() {
		String term = "alectinib";
		String searchTerm = term.substring(1, 7);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/")
        // List<WebElement> searchField = getElementControl(inputSelector);
        this.selectContains();

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

        			// Click a link on the results page
			// dict.ClickElement("dt dfn a", term, driver);

        if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean submitSearchTerm8() {
		String term = "bevacizumab";
		String searchTerm = term.substring(1, 5);
        // ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
        // List<WebElement> searchField = getElementControl(inputSelector);
        this.selectContains();

        search_input.sendKeys(searchTerm);
        search_input.sendKeys(Keys.RETURN);
        // List<WebElement> letter = driver.findElements(By.cssSelector("div.az-list ul li a"));
        System.out.println(az_list_letters.size());

			// Click a link on the results page
			// dict.ClickElement("dt dfn a", term, driver);

            if (az_list_letters.size() == 0) {
            return false;
        }
        return true;
    }

    public void SearchTermPressButton(String inputSelector, String searchTerm) {
        List<WebElement> searchField = getElementControl(inputSelector);
        searchField.get(0).sendKeys(searchTerm);
        List<WebElement> searchBtn = getElementControl("#btnSearch");
        searchBtn.get(0).click();
    }

    // Click the Search button to submit a search
    // ------------------------------------------
    public void pressButton() {
        List<WebElement> searchBtn = getElementControl("#btnSearch");
        searchBtn.get(0).click();
    }

    // Hit ENTER to submit a search
    // ----------------------------
    public void hitEnter(WebDriver driver) {
        search_input.sendKeys(Keys.RETURN);
        System.out.println(driver.getCurrentUrl());

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

    private List<WebElement> getHeader() {
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

    private List<WebElement> getElementControl(String selector) {
            List<WebElement> elementControls = getBrowser().findElements(By.cssSelector(selector));
            return elementControls;
    }

    public void selectStartsWith() {
            List<WebElement> searchType = getBrowser().findElements(By.cssSelector("#lblStartsWith"));
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
