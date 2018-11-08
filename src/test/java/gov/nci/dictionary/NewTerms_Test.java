package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewTerms_Test extends NewDictionaryCommon {

	private String language = "EN";

/*  ***************************** Test Methods ****************************************** */
	// Testing to confirm the correct page title is displayed
	// --------------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void TitleVisible(String url) {
		DictionarySearch dict;
		String dictTitle = "NCI Dictionary of Cancer Terms";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if dictionary title is visible: " + dictTitle);

		throw new RuntimeException("This test is disabled pending resolution of build errors.");

//		driver.get(url);
//
//		try {
//			dict = new DictionarySearch(driver);
//			boolean titleVisible = dict.TitleVisible();
//			WebElement titleText = dict.getTitleText();
//			String titleNotVisible = "*** Error: Term Dictionary Header Not Visible ***";
//			String wrongTitleText = "*** Error: Term Dictionary Title text mismatch ***";
//
//			Assert.assertTrue(titleVisible, titleNotVisible);
//			Assert.assertTrue(titleText.getText().contains(dictTitle), wrongTitleText);
//		} catch (MalformedURLException | UnsupportedEncodingException e) {
//			Assert.fail("*** Error loading page in " + curMethod + " ***");
//		}
	}

	// Confirm the link to the glossary widget in the header paragraph is
	// visible.
	// -----------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void WidgetLinkVisible(String url) {
		DictObjectBase dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if widget link is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean linkVisible = dict.WidgetLinkVisible();
			String linkNotVisible = "*** Error: Term Dictionary Widget Link Not Found ***";

			Assert.assertTrue(linkVisible, linkNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Finding the link for the Glossary Widget in the header paragraph and
	// test that the widget page exists by checking the resulting page title.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void ClickWidgetLink(String url) {
		DictObjectBase dict;
		String language = "EN";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing link to glossary widget goes to correct page");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean foundWidgetPage = dict.LinksToWidgetPage(driver, language);
			String noFoundWidgetPage = "*** Error: Term Dictionary Click Widget Page Not Found ***";

			Assert.assertTrue(foundWidgetPage, noFoundWidgetPage);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void RadioVisible(String url) {
		DictObjectBase dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if radio buttons are visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.RadioBtnVisible();
			String radioNotVisible = "*** Error: Term Dictionary Radio Button Not Found ***";

			Assert.assertTrue(radioVisible, radioNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void RadioStartsWithSelected(String url) {
		DictObjectBase dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if radio button StartsWith is selected");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioStartsWith = dict.RadioDefault();
			String radioError = "*** Error: Term Dictionary Radio Button Default Not Found ***";

			Assert.assertTrue(radioStartsWith, radioError);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the search input field is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void InputFieldVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if search entry field is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			String radioError = "*** Error: Term Dictionary Input Field Not Found ***";

			Assert.assertTrue(radioVisible, radioError);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the Search button is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchBtnVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "input.button";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing is Search button is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean searchBtnVisible = dict.FieldVisible(cssSelector);
			String searchBtnNotVisible = "*** Error: Term Dictionary SearchButton Not Found ***";

			Assert.assertTrue(searchBtnVisible, searchBtnNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the A-Z list is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void AZListVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing is A-Z list is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			String radioNotVisible = "*** Error: Term Dictionary A-Z List Not Found ***";

			Assert.assertTrue(radioVisible, radioNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming a letter from the  A-Z list can be selected and shows results
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void AZListSelectLetter(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list ul li a";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z. Selecting letter B");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean displayOK = dict.AZListSelect(driver, cssSelector, language);
			String displayNotOk = "*** Error: Term Dictionary List for specified letter incorrect ***";

			Assert.assertTrue(displayOK, displayNotOk);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming a letter from the A-Z list can be selected and shows correct URL
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void AZListLetterUrl(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list ul li a";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z list. Check URL when letter B selected");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);

			// Integer numDefs = NumberOfDefinitions("genetics", language);
			boolean displayOK = dict.AZListSelect(driver, cssSelector, language);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms?expand=")
			);
			String pageNotFound = "*** Error: Term Dictionary URL for specified letter incorrect ***";
			String displayNotOk = "*** Error: Term Dictionary Can't select letter for A-Z List ***";

			Assert.assertTrue(pageFound, pageNotFound);
			Assert.assertTrue(displayOK, displayNotOk);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Submit using ENTER key. Confirm the URL is a definition page for this term.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchEnterStartsWith(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "necitumumab";
		String termSubstr = term.substring(0, 4);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + term)
			);

			String pageNotFound = "*** Error: Term Dictionary Page for drug " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Submit using ENTER key. Confirm a term result page is returned.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchEnterStartsWithMulti(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "nanoparticle";
		String termSubstr = term.substring(0, 4);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for multiple drugs: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);

			String pageNotFound = "*** Error: Term Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Click the "Search" button. Confirm the URL is a definition page for this term.
	//  -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchButtonStartsWith(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "necitumumab";
		String termSubstr = term.substring(0, 4);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search button using Startswith for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SearchTermPressButton(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + term)
			);

			String pageNotFound = "*** Error: Term Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm the URL for a single term is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchEnterContains(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "necitumumab";
		String termSubstr = term.substring(2, 6);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + term)
			);

			String pageNotFound = "*** Error: Term Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm the URL for multiple terms is displayed.
	// -------------------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchEnterContainsMulti(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "nanoparticle";
		String termSubstr = term.substring(2, 7);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains for multiple drugs: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);

			String pageNotFound = "*** Error: Term Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using "Search" button. Confirm the URL for a single term is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchButtonContains(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "necitumumab";
		String termSubstr = term.substring(2, 6);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains with Search button");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SearchTermPressButton(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + term)
			);

			String pageNotFound = "*** Error: Term Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm results page displays a "header" row
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchReturnsHeader(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "sensitivity";
		String termSubstr = term.substring(1, 7);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to inspect result header of search: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean searchResult = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);
			Assert.assertTrue(searchResult, "*** Error: Term Dictionary URL for search page not found");

			Boolean searchHeader = dict.SearchResultHeaderVisible("div.dictionary-search-results-header");
			String searchHeaderExpected = "*** Error: Term Dictionary No Search Result Header Found";
			Assert.assertTrue(searchHeader, searchHeaderExpected);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm a results page is returned
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchReturnsList(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "sensitivity";
		String termSubstr = term.substring(1, 7);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search contains a result list: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 2);
			boolean searchResult = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);
			Assert.assertTrue(searchResult, "*** Error: Term Dictionary URL for search page not found");

			List<WebElement> searchResultList = dict.SearchResultList("dt dfn");
			Boolean searchResults = false;
			if (searchResultList.size() > 0) {
				searchResults = true;
			}
			String pageNotFound = "*** Error: Term Dictionary List of terms containing " + termSubstr + " not found";
			Assert.assertTrue(searchResults, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key and click an element of the results page.
	// Confirm the URL displayes a definition page.
	// -------------------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchAndClick(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "sensitivity";
		String termSubstr = term.substring(1, 9);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to click member of result page: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			// Select the Contains radio button
			dict.selectContains();
			// Enter drug name in search field and submit
			dict.SubmitSearchTerm(cssSelector, termSubstr);
			// Click a link on the results page
			dict.ClickElement("dt dfn a", term, driver);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + term)
			);

			String pageNotFound = "*** Error: Term Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


	// Enter specific text in search field with options "Contains"	selected.
	// Submit using ENTER key. Confirm a definition page is returned.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchReturnsDefinition(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "necitumumab";
		String termSubstr = term.substring(2, 6);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to select a drug from search result");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			// Selecting "Contains" radio button
			dict.selectContains();
			// Submit search term and enter return
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean searchResult = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "5 results found for: beva")
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + term)
			);
			Assert.assertTrue(searchResult, "*** Error: Term Dictionary URL not found");

			// The element includes a "More Information" header only seen on definition pages,
			// not search pages
			List<WebElement> resultList = dict.SearchResultList("h6");
			boolean moreInformation = false;
			if (resultList.size() > 0) {
				moreInformation = true;
			}

			String pageNotFound = "*** Error: Term Dictionary Page for " + term + " not found";
			Assert.assertTrue(moreInformation, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


	/*  ***************************** Data Provider *********************************************** */

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