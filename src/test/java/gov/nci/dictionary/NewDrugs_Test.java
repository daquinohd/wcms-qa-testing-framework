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

public class NewDrugs_Test extends NewDictionaryCommon {

	private String language = "EN";
	private String dictionary = "drug";

/*  ***************************** Test Methods ****************************************** */
	//* Testing to confirm the page header (H1 element) is visible
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void HeaderVisible(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if dictionary header element is visible");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean headerVisible = dict.headerVisible();
			String headerNotVisible = "*** Error: Drug Dictionary Header Not Found ***";
			Assert.assertTrue(headerVisible, headerNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Testing to confirm the correct page header is displayed
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void HeaderDisplay(String url) {
		DictionarySearch dict;
		String dictHeader = "NCI Drug Dictionary";
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if correct dictionary header is displayed: " + dictHeader);
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			String headerText = dict.getHeaderText();
			String incorrectTitle = "*** Error: Drug Dictionary Header text mismatch ***";
			Assert.assertEquals(headerText, dictHeader, incorrectTitle);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Testing to confirm the correct HTML title text is displayed
	// ------------------------------------------------------------------------------
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
			String incorrectTitle = "*** Error: Drug Dictionary Title text mismatch ***";
			Assert.assertEquals(titleText, dictTitle, incorrectTitle);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void RadioVisible(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if radio buttons are visible");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean radioVisible = dict.radioBtnVisible();
			String radioNotVisible =  "*** Error: Drug Dictionary Radio Button Not Found ***";
			Assert.assertTrue(radioVisible, radioNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void RadioStartsWithSelected(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if radio button StartsWith is selected");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean radioStartsWith = dict.radioDefault();
			String radioError = "*** Error: Drug Dictionary Radio Button Default Not Found ***";
			Assert.assertTrue(radioStartsWith, radioError);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the search input field is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchInputVisible(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if search entry field is visible");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean radioVisible = dict.searchInputVisible();
			String radioError = "*** Error: Drug Dictionary Input Field Not Found ***";
			Assert.assertTrue(radioVisible, radioError);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the Search button is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchBtnVisible(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if Search button is visible");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean searchBtnVisible = dict.searchBtnVisible();
			String searchBtnNotVisible = "*** Error: Drug Dictionary SearchButton Not Found ***";
			Assert.assertTrue(searchBtnVisible, searchBtnNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the A-Z list is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListVisible(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing if A-Z list is visible");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean radioVisible = dict.azListVisible();
			String radioNotVisible = "*** Error: Drug Dictionary A-Z List Not Found ***";
			Assert.assertTrue(radioVisible, radioNotVisible);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the A-Z list home page displays results for letter "A"
	// -----------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListHome(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z List home page results");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean azHome = dict.azListHomeIsA();
			String azNotHome = "*** Error: Drug Dictionary A-Z Home Not Found ***";
			Assert.assertTrue(azHome, azNotHome);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming the A-Z list home page displays results for letter "A"
	// -----------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListHomeUrl(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z List home page Url");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean azHomeUrl = dict.azListHomeUrl(dictionary);
			String azNotHome = "*** Error: Drug Dictionary A-Z Home URL incorrect ***";
			Assert.assertTrue(azHomeUrl, azNotHome);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming each letter from the  A-Z list can be selected and shows results
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListSelectLetter(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z list. Selecting each letter");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean displayOK = dict.azListSelect(driver, dictionary, language);
			String displayNotOk = "*** Error: Drug Dictionary Result for specified letter incorrect ***";
			Assert.assertTrue(displayOK, displayNotOk);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//* Confirming each letter from the  A-Z list can be selected and shows correct URL
	// -------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void AZListSelectLetterTestUrl(String url) {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing A-Z list. Selecting letter B");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean displayOK = dict.azListSelectUrl(driver, dictionary, language);
			String displayNotOk = "*** Error: Drug Dictionary URLs for letters incorrect ***";
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
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for single term");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean resultsOK = dict.submitSearchTerm(driver);
			String resultsNotOK = "*** Error: Drug Dictionary Page for drug term not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Submit using ENTER key. Confirm the URL is a definition page for this term.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SubmitSearchStartsWithUrl(String url) throws InterruptedException {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for single term");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean resultsOK = dict.submitSearchTermUrl(driver);
			String resultsNotOK = "*** Error: Drug Dictionary Page for drug term not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Submit using ENTER key. Confirm a term result page is returend.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SubmitSearchStartsWithMulti(String url) throws InterruptedException {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using StartsWith for multiple drugs ");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean resultsOK = dict.submitSearchTerm2();
			String resultsNotOK = "*** Error: Drug Dictionary Page for drug term  not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Starts with" selected.
	// Click the "Search" button. Confirm the URL is a definition page for this term.
	//  -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchButtonStartsWith(String url) throws InterruptedException {
		DictionarySearch dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "becatecarin";
		String termSubstr = term.substring(0, 4);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search button using Startswith for single drug: " + termSubstr);

		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
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
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains for single drug: ");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
		    dict.selectContains();
			boolean resultsOK = dict.submitSearchTerm3();
			String resultsNotOK = "*** Error: Drug Dictionary Page for term term not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm the URL for multiple terms is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchEnterContainsMulti(String url) throws InterruptedException {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains for multiple drugs: ");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
		    dict.selectContains();
			boolean resultsOK = dict.submitSearchTerm4();
			String resultsNotOK = "*** Error: Drug Dictionary Page for term term not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text in search field with options "Contains" selected.
	// Submit using "Search" button. Confirm the URL for a single term is displayed.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchButtonContains(String url) throws InterruptedException {
		DictionarySearch dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "lenapenem";
		String termSubstr = term.substring(2, 8);
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search using contains with Search button");

		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
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
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to inspect result header of search: ");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			boolean resultsOK = dict.submitSearchTerm5();

			Assert.assertTrue(resultsOK, "*** Error: Drug Dictionary URL for search page not found");

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
	public void DictionarySearch(String url) throws InterruptedException {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing search contains a result list: ");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			// Selecting "Contains" radio button
			// Submit search term and enter return
			boolean resultsOK = dict.submitSearchTerm6();
			Assert.assertTrue(resultsOK, "*** Error: Drug Dictionary URL for search page not found");

			List<WebElement> resultList = dict.SearchResultList("dt dfn");
			boolean searchResults = false;
			if (resultList.size() > 0) {
				searchResults = true;
			}

			String pageNotFound = "*** Error: Drug Dictionary List of drugs containing  termSubstr not found";
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
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to click member of result page: ");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			// Enter drug name in search field and submit
			boolean resultsOK = dict.submitSearchTerm7();
			String resultsNotOK = "*** Error: Drug Dictionary Page for term term not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Enter specific text ("beva") in search field with options "Contains" selected.
	// Submit using ENTER key. Confirm a definition page is returned.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void SearchReturnsDefinition(String url) throws InterruptedException {
		DictionarySearch dict;
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();

		logger.log(LogStatus.INFO, "Testing to select a drug from search result");
		driver.get(url);

		try {
			dict = new DictionarySearch(driver);
			// Enter drug name in search field and submit
			boolean resultsOK = dict.submitSearchTerm8();
			String resultsNotOK = "*** Error: Drug Dictionary Page for term term not found";
			Assert.assertTrue(resultsOK, resultsNotOK);
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