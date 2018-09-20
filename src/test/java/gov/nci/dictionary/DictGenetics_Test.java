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

import gov.nci.clinicaltrials.BaseClass;

public class DictGenetics_Test extends BaseClass {

	private String language = "EN";

/*  ***************************** Test Methods ****************************************** */
	// Testing to confirm the page title "NCI Dictionary of Cancer Terms" is displayed
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void TitleVisible(String url) {
		DictObjectBase dict;
		String dictTitle = "NCI Dictionary of Genetics Terms";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing dictionary title: " + dictTitle);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean titleVisible = dict.TitleVisible();
			WebElement titleText = dict.getTitleText();

			Assert.assertTrue(titleVisible,
							  "*** Error: Genetics Distionary Header Not Found ***");
			Assert.assertTrue(titleText.getText().contains(dictTitle),
							                   "*** Error: Title text mismatch ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void RadioVisible(String url) {
		DictObjectBase dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.RadioBtnVisible();
			Assert.assertTrue(radioVisible,
			                  "*** Error: Genetics Dictionary Radio Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void RadioStartsWithSelected(String url) {
		DictObjectBase dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioStartsWith = dict.RadioDefault();
			Assert.assertTrue(radioStartsWith,
			                  "*** Error: Genetics Dictionary Radio Button Default Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the search input field is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void InputFieldVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Genetics Dictionary Input Field Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the Search button is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void SearchBtnVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "input.button";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean searchBtnVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(searchBtnVisible,
			                  "*** Error: Genetics Dictionary SearchButton Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the A-Z list is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void AZListVisible(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Genetics Dictionary A-Z List Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming a letter from the  A-Z list can be selected and shows results
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void AZListSelect(String url) {
		DictObjectBase dict;
		String cssSelector = "div.az-list ul li a";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);

			Integer numDefs = NumberOfDefinitions("genetics", language);
			boolean displayOK = dict.AZListSelect(driver, cssSelector, language, numDefs);
			Assert.assertTrue(displayOK,
			                  "*** Error: Genetics List for specified letter incorrect ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	/*
	 * Enter specific text ("beva") in search field with options "Starts with"
	 * selected.  Confirm the URL is a definition page for this term.
	 * -------------------------------------------------------------------------
	 */
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void KeyWordStartsWith(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String drug = "pedigree";
		String drug4 = drug.substring(0, 4);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(cssSelector, drug4);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/genetics-dictionary/def/" + drug)
			);

			String pageNotFound = "Page for drug " + drug + " not found";
			Assert.assertTrue(pageFound, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Enter specific text ("beva") in search field with options "Contains"
	// selected.  Confirm the URL is a search page.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void KeyWordContains(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "epigenetics";
		String term4 = term.substring(0, 4);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing contains: " + term4);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, term4);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/genetics-dictionary/search")
			);

			String pageNotFound = "Page for term " + term + " not found";
			Assert.assertTrue(pageFound, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Enter specific text ("beva") in search field with options "Contains"
	// selected.  Confirm header of the results page displayes: "5 results found for: beva".
	// -------------------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void KeyWordContainsReturnHeader(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "epigenetics";
		String term4 = term.substring(0, 6);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing contains result header: " + url);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, term4);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean searchResult = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "3 results found for: epigen")
				ExpectedConditions.urlContains("/publications/dictionaries/genetics-dictionary/search")
			);

			WebElement searchHeader = dict.SearchResultHeader("div.dictionary-search-results-header");

			String searchHeaderExpected = "3 results found for: epigen";
			String pageNotFound = "List of terms containing " + term + " not found";
			Assert.assertEquals(searchHeader.getText(), searchHeaderExpected, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Enter specific text ("beva") in search field with options "Contains"
	// selected.  Confirm the number of results displayed is 5.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GeneticsDictionary", groups = { "dictionary" })
	public void KeyWordContainsReturnList(String url) throws InterruptedException {
		DictObjectBase dict;
		String cssSelector = "input.dictionary-search-input";
		String term = "epigenetics";
		String term4 = term.substring(0, 6);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing contains result list: " + term4);

		driver.get(url);

		try {
			dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, term4);

			WebDriverWait wait = new WebDriverWait(driver, 2);
			boolean searchResult = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "5 results found for: beva")
				ExpectedConditions.urlContains("/publications/dictionaries/genetics-dictionary/search")
			);

			List<WebElement> resultList = dict.SearchResultList("dt > dfn");

			String pageNotFound = "List of drugs containing " + term + " not found";
			Assert.assertEquals(resultList.size(), 3, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


	/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "GeneticsDictionary")
	public Iterator<Object[]> loadGeneticsDictionary() {
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		String url = AddHostname("/publications/dictionaries/genetics-dictionary");
		Object ob[] = { url };
		myObjects.add(ob);

		return myObjects.iterator();
	}
}