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

public class DictDrugs_Test extends DictBaseClass {

	private String language = "EN";

/*  ***************************** Test Methods ****************************************** */
	// Testing to confirm the correct page title is displayed
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void TitleVisible(String url) {
		DictObjectBase dict;
		String dictTitle = "NCI Drug Dictionary";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if dictionary title is visible: " + dictTitle);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean titleVisible = dict.TitleVisible();
			WebElement titleText = dict.getTitleText();
			String titleNotVisible = "*** Error: Drug Distionary Header Not Found ***";
			String incorrectTitle = "*** Error: Drug Dictionary Title text mismatch ***";

			Assert.assertTrue(titleVisible, titleNotVisible);
			Assert.assertTrue(titleText.getText().contains(dictTitle), incorrectTitle);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void RadioVisible(String url) {
		DictObjectBase dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if radio buttons are visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.RadioBtnVisible();
			String radioNotVisible =  "*** Error: Drug Dictionary Radio Button Not Found ***";

			Assert.assertTrue(radioVisible, radioNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void RadioStartsWithSelected(String url) {
		DictObjectBase dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if radio button StartsWith is selected");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioStartsWith = dict.RadioDefault();
			String radioError = "*** Error: Drug Dictionary Radio Button Default Not Found ***";

			Assert.assertTrue(radioStartsWith, radioError);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the search input field is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void InputFieldVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if search entry field is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			String radioError = "*** Error: Drug Dictionary Input Field Not Found ***";

			Assert.assertTrue(radioVisible, radioError);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the Search button is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchBtnVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "input.button";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if Search button is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean searchBtnVisible = dict.FieldVisible(cssSelector);
			String searchBtnNotVisible = "*** Error: Drug Dictionary SearchButton Not Found ***";

			Assert.assertTrue(searchBtnVisible, searchBtnNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming the A-Z list is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if A-Z list is visible");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			String radioNotVisible = "*** Error: Drug Dictionary A-Z List Not Found ***";

			Assert.assertTrue(radioVisible, radioNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming a letter from the  A-Z list can be selected and shows results
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListSelectLetter(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list ul li a";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z list. Selecting letter B");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			// Integer numDefs = NumberOfDefinitions("drug", language);
			// Entries for the drug dictionary are displayed at 200 per page
			// numDefs = Math.min(numDefs, 200);
			boolean displayOK = dict.AZListSelect(driver, cssSelector, language);
			String displayNotOk = "*** Error: Drug Dictionary Result for specified letter incorrect ***";

			Assert.assertTrue(displayOK, displayNotOk);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirming a letter from the  A-Z list can be selected and shows correct URL
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListLetterUrl(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list ul li a";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z list. Check URL when letter B selected");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);

			boolean displayOK = dict.AZListSelect(driver, cssSelector, language);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug?expand=")
			);
			String pageNotFound = "*** Error: Drug Dictionary URL for specified letter incorrect ***";
			String displayNotOk = "*** Error: Drug Dictionary Can't select letter for A-Z List ***";

			Assert.assertTrue(pageFound, pageNotFound);
			Assert.assertTrue(displayOK, displayNotOk);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Submit using ENTER key. Confirm the URL is a definition page for this term.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SubmitSearchStartsWith(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "becatecarin";
		String termSubstr = term.substring(0, 5);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for drug " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Submit using ENTER key. Confirm a term result page is returend.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SubmitSearchStartsWithMulti(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "bevacizumab";
		String termSubstr = term.substring(0, 8);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for multiple drugs: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for drug " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Click the "Search" button. Confirm the URL is a definition page for this term.
	//  -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchButtonStartsWith(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "becatecarin";
		String termSubstr = term.substring(0, 4);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search button using Startswith for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SearchTermPressButton(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for drug " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm the URL for a single term is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchEnterContains(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "lenapenem";
		String termSubstr = term.substring(2, 8);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm the URL for multiple terms is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchEnterContainsMulti(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "bevacizumab";
		String termSubstr = term.substring(2, 7);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains for multiple drugs: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using "Search" button. Confirm the URL for a single term is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchButtonContains(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "lenapenem";
		String termSubstr = term.substring(2, 8);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains with Search button");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SearchTermPressButton(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm results page displays a "header" row
	// -------------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchReturnsHeader(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "bevacizumab";
		String termSubstr = term.substring(2, 6);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to inspect result header of search: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean searchResult = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "results found for: beva");
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
			);
			Assert.assertTrue(searchResult, "*** Error: Drug Dictionary URL for search page not found");

			// The results page returns a header "12 results found for: term-string"
			// This header is in a div with class "dictionary-search-results-header"
			// It's a success when we find this class.
			Boolean searchHeader = dict.SearchResultHeaderVisible("div.dictionary-search-results-header");
			String searchHeaderExpected = "*** Error: Drug Dictionary No Search Result Header Found";
			Assert.assertTrue(searchHeader, searchHeaderExpected);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm a results page is returned.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchReturnsList(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "bevacizumab";
		String termSubstr = term.substring(0, 4);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search contains a result list: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			// Selecting "Contains" radio button
			dict.selectContains();
			// Submit search term and enter return
			dict.SubmitSearchTerm(cssSelector, termSubstr);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "5 results found for: beva")
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/search")
			);
			Assert.assertTrue(pageFound, "*** Error: Drug Dictionary URL for search page not found");

			List<WebElement> resultList = dict.SearchResultList("dt dfn");
			boolean searchResults = false;
			if (resultList.size() > 0) {
				searchResults = true;
			}

			String pageNotFound = "*** Error: Drug Dictionary List of drugs containing " + termSubstr + " not found";
			Assert.assertTrue(searchResults, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key and click an element of the results page.
	// Confirm the URL displays a definition page.
	// -------------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchAndClick(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "alectinib";
		String termSubstr = term.substring(1, 7);
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

			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text ("beva") in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm a definition page is returned.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchReturnsDefinition(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "bevacizumab";
		String termSubstr = term.substring(1, 5);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to select a drug from search result");

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			// Select the Contains radio button
			dict.selectContains();
			// Enter drug name in search field and submit
			dict.SubmitSearchTerm(cssSelector, termSubstr);
			// Click a link on the results page
			dict.ClickElement("dt dfn a", term, driver);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-drug/def/" + term)
			);

			String pageNotFound = "*** Error: Drug Dictionary Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "DrugDictionary")
	public Iterator<Object[]> getDictUrl() {
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

        String url = AddHostname("/publications/dictionaries/cancer-drug");
        Object ob[] = { url };
        myObjects.add(ob);

		return myObjects.iterator();
	}
}