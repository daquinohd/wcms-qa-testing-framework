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

public class DictTerms_Test extends BaseClass {

	private String TESTDATA_PATH;

/*  ***************************** Test Methods ****************************************** */
	// Testing to confirm the page title "NCI Dictionary of Cancer Terms" is displayed
	// --------------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void TitleVisible(String url) {
		HeaderElements dict;
		String dictTitle = "NCI Dictionary of Cancer Terms";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing dictionary title: " + dictTitle);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean titleVisible = dict.TitleVisible();
			WebElement titleText = dict.getTitleText();

			Assert.assertTrue(titleVisible,
			                            "*** Error: Term Dictionary Header Not Found ***");
			Assert.assertTrue(titleText.getText().contains(dictTitle),
			                                         "*** Error: Title text mismatch ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Confirm the link to the glossary widget in the header paragraph is
	// visible.
	// -----------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void WidgetLinkVisible(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean linkVisible = dict.WidgetLinkVisible();
			Assert.assertTrue(linkVisible,
			                  "*** Error: Term Dictionary Widget Link Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Finding the link for the Glossary Widget in the header paragraph and
	// test that the widget page exists by checking the resulting page title.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void ClickWidgetLink(String url) {
		HeaderElements dict;
		String language = "EN";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean foundWidgetPage = dict.LinksToWidgetPage(driver, language);
			Assert.assertTrue(foundWidgetPage,
			                  "*** Error: Term Dictionary Click Widget Page Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void RadioVisible(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.RadioBtnVisible();
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary Radio Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void RadioStartsWithSelected(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioStartsWith = dict.RadioDefault();
			Assert.assertTrue(radioStartsWith,
			                  "*** Error: Term Dictionary Radio Button Default Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the search input field is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void InputFieldVisible(String url) {
		HeaderElements dict;
		String cssSelector = "input.dictionary-search-input";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary Input Field Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the Search button is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void SearchBtnVisible(String url) {
		HeaderElements dict;
		String cssSelector = "input.button";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary SearchButton Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the A-Z list is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void AZListVisible(String url) {
		HeaderElements dict;
		String cssSelector = "div.az-list";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary A-Z List Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming a letter from the  A-Z list can be selected and shows results
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void AZListSelect(String url) {
		HeaderElements dict;
		String cssSelector = "div.az-list ul li a";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean displayOK = dict.AZListSelect(driver, cssSelector);
			Assert.assertTrue(displayOK,
			                  "*** Error: Dictionary List for specified letter incorrect ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	/*
	 * Enter specific text ("beva") in search field with options "Starts with"
	 * selected.  Confirm the URL is a definition page for this term.
	 * -------------------------------------------------------------------------
	 */
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void KeyWordStartsWith(String url) throws InterruptedException {
		HeaderElements dict;
		String cssSelector = "input.dictionary-search-input";
		String drug = "bevacizumab";
		String drug4 = drug.substring(0, 4);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			dict.SubmitSearchTerm(cssSelector, drug4);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/def/" + drug)
			);

			// Thread.sleep(5000);

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
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void KeyWordContains(String url) throws InterruptedException {
		HeaderElements dict;
		String cssSelector = "input.dictionary-search-input";
		String drug = "bevacizumab";
		String drug4 = drug.substring(0, 4);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, drug4);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean pageFound = wait.until(
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);

			// Thread.sleep(2000);
			// WebElement dada = dict.getBrowser().findElement(By.cssSelector(cssSelector));

			String pageNotFound = "Page for drug " + drug + " not found";
			Assert.assertTrue(pageFound, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Enter specific text ("beva") in search field with options "Contains"
	// selected.  Confirm header of the results page displayes: "5 results found for: beva".
	// -------------------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void KeyWordContainsReturnHeader(String url) throws InterruptedException {
		HeaderElements dict;
		String cssSelector = "input.dictionary-search-input";
		String drug = "bevacizumab";
		String drug4 = drug.substring(0, 4);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
		    dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, drug4);

			WebDriverWait wait = new WebDriverWait(driver, 5);
			boolean searchResult = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "5 results found for: beva")
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);

			WebElement searchHeader = dict.SearchResultHeader("div.dictionary-search-results-header");

			String searchHeaderExpected = "5 results found for: beva";
			String pageNotFound = "List of drugs containing " + drug + " not found";
			Assert.assertEquals(searchHeader.getText(), searchHeaderExpected, pageNotFound);

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Enter specific text ("beva") in search field with options "Contains"
	// selected.  Confirm the number of results displayed is 5.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void KeyWordContainsReturnList(String url) throws InterruptedException {
		HeaderElements dict;
		String cssSelector = "input.dictionary-search-input";
		String drug = "bevacizumab";
		String drug4 = drug.substring(0, 4);

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			dict.selectContains();
			dict.SubmitSearchTerm(cssSelector, drug4);

			WebDriverWait wait = new WebDriverWait(driver, 2);
			boolean searchResult = wait.until(
				// ExpectedConditions.textToBePresentInElement(searchField, "5 results found for: beva")
				ExpectedConditions.urlContains("/publications/dictionaries/cancer-terms/search")
			);

			List<WebElement> resultList = dict.SearchResultList("dt > dfn");

			String pageNotFound = "List of drugs containing " + drug + " not found";
			Assert.assertEquals(resultList.size(), 5, pageNotFound);

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