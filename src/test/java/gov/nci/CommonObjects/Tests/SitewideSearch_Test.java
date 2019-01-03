package gov.nci.CommonObjects.Tests;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.SitewideSearch;

public class SitewideSearch_Test extends BaseClass {

	public static final String SITEWIDE_SEARCH_SHEET_NAME = "SitewideSearch";
	public static final String BESTBET_SEARCH_SHEET_NAME = "BestBet";

	SitewideSearch search;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("HomePage");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, config, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		search = new SitewideSearch(driver, logger);
		testDataFilePath = config.getProperty("SiteWideSearchData");
	}

	// Testing if Search Text Box is visible
	@Test(groups = { "Smoke" })
	public void verifySearchTextBoxUI() {
		Assert.assertTrue(search.getSearchBox().isDisplayed(), "Search text box is not displayed");
		logger.log(LogStatus.PASS, "Verify that Search text box is displayed");
	}

	// Testing if Search Button is visible
	@Test(groups = { "Smoke" })
	public void verifySearchButtonUI() {
		Assert.assertTrue(search.getSearchButton().isDisplayed(), "Search Button is not displayed");
		logger.log(LogStatus.PASS, "Verify that Search button are displayed");
	}

	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySitewideSearch(String keyword) {

		System.out.println("Search Keyword: " + keyword);
		search.search(keyword);

		// Verify Search Results page common validation
		verifySearchResultsPage();

		// Verify that the Result Text contains search keyword
		WebElement ele = driver.findElement(By.xpath("//h3[@id='ui-id-2']"));
		System.out.println("Element Value ***********: " + ele.getText());
		Assert.assertTrue(ele.getText().equals("Results for: " + keyword));

		// Verify that the Top Search Result Text contains search keyword
		WebElement topResultText = driver.findElement(By.xpath("(//h4)[1]"));
		System.out.println("Top Result Text Value ***********: " + topResultText.getText());
		Assert.assertTrue(topResultText.getText().contains("Results 1\u201310 of"));
		Assert.assertTrue(topResultText.getText().contains("for: " + keyword));

		// Verify that the bottom Search Result Text contains search keyword
		WebElement bottomResultText = driver.findElement(By.xpath("(//h4)[2]"));
		System.out.println("Bottom Result Text Value ***********: " + bottomResultText.getText());
		Assert.assertTrue(bottomResultText.getText().contains("results found for: " + keyword));

		// Verify best bet box is displayed
		Assert.assertTrue(search.getBestBetBox().isDisplayed());

		logger.log(LogStatus.PASS,
				"Verify that when a keyword is searched, Search Result page is displayed with following validations: "
						+ "Page Title, H1 Title, URL ending with 'results', results text");
	}

	@Test(groups = { "Smoke" })
	public void verifyEmptySitewideSearch() {

		String keyword = "";

		System.out.println("Search Keyword: Empty");
		search.search(keyword);

		// Verify Search Results page common validation
		verifySearchResultsPage();

		// Verify that the Result Text contains search keyword
		WebElement ele = driver.findElement(By.xpath("//h3[@id='ui-id-2']"));
		System.out.println("Element Value ***********: " + ele.getText());
		Assert.assertTrue(ele.getText().equals("Results for:"));

		// Verify that the Top Search Result Text contains search keyword
		WebElement topResultText = driver.findElement(By.xpath("(//h4)[1]"));
		System.out.println("Top Result Text Value ***********: " + topResultText.getText());
		Assert.assertTrue(topResultText.getText().contains("Results 0\u20130 of 0 for:"));

		// Verify that the error message is displayed for empty search
		WebElement errorMsg = driver.findElement(By.xpath("(//h4)[1]/following-sibling::div[1]"));
		System.out.println("Error Message for empty search ***********: " + errorMsg.getText());
		Assert.assertTrue(errorMsg.getText().contains("Please enter a search phrase."));

		// Verify that the bottom Search Result Text contains search keyword
		WebElement bottomResultText = driver.findElement(By.xpath("(//h4)[2]"));
		System.out.println("Bottom Result Text Value ***********: " + bottomResultText.getText());
		Assert.assertTrue(bottomResultText.getText().contains("0 results found for:"));

		logger.log(LogStatus.PASS,
				"Verify that when a search is performed with empty keyword, Search Result page is displayed with following validations: "
						+ "Page Title, H1 Title, URL ending with 'results', error message");
	}

	@Test(dataProvider = "SearchWithinSearch", groups = { "Smoke" })
	public void verifySearchWithinSearch(String keyword1, String keyword2) {

		// Perform Search
		search.SearchWithinSearch(keyword1, keyword2);
		System.out.println("SearchWithinSearch function executed");

		// Verify Search Results page common validation
		verifySearchResultsPage();

		// Verify that the Result Text contains search keywords
		WebElement ele = driver.findElement(By.xpath("//h3[@id='ui-id-2']"));
		System.out.println("Element Value ***********: " + ele.getText());
		Assert.assertTrue(ele.getText().equals("Results for: " + keyword1 + " : " + keyword2));

		// Verify that the Top Search Result Text contains search keywords
		WebElement topResultText = driver.findElement(By.xpath("(//h4)[1]"));
		System.out.println("Top Result Text Value ***********: " + topResultText.getText());
		// "\u2013" is an endash
		Assert.assertTrue(topResultText.getText().contains("Results 1\u201310 of"));
		Assert.assertTrue(topResultText.getText().contains("for: " + keyword1 + " : " + keyword2));

		// Verify that the bottom Search Result Text contains search keyword
		WebElement bottomResultText = driver.findElement(By.xpath("(//h4)[2]"));
		System.out.println("Bottom Result Text Value ***********: " + bottomResultText.getText());
		Assert.assertTrue(bottomResultText.getText().contains("results found for: " + keyword1 + " : " + keyword2));

		logger.log(LogStatus.PASS,
				"Verify that when a two keywords are searched via Search Within Search, Search Result page is displayed with following validations: "
						+ "Page Title, H1 Title, URL ending with 'results', results text");
	}

	// Perform Site-wide search for words which display Best Bet Box on the
	// Search Result Page and validate the results
	// -------------------------------------------------------------------------
	@Test(dataProvider = "BestBets", groups = { "Smoke" })
	public void verifyBestBetSearch(String keyword) {

		System.out.println("Search Keyword: " + keyword);
		search.search(keyword);

		// Verify Search Results page common validation
		verifySearchResultsPage();

		// Verify Best Bet box, label and text are displayed
		Assert.assertTrue(search.getBestBetBox().isDisplayed());
		Assert.assertTrue(search.getBestBetLabel().isDisplayed());
		Assert.assertTrue(search.getBestBetLabel().getText().contains("Best Bets"));

		logger.log(LogStatus.PASS,
				"Verify that when a keyword having Best Bet is searched, Search Result page is displayed with Best Bet Box");
	}

	/********************** Data Providers **********************/

	@DataProvider(name = "Search")
	public Iterator<Object[]> readSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(SITEWIDE_SEARCH_SHEET_NAME); rowNum++) {
			String cancerType = excelReader.getCellData(SITEWIDE_SEARCH_SHEET_NAME, "Keyword1", rowNum);
			Object ob[] = { cancerType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "SearchWithinSearch")
	public Iterator<Object[]> readSearchWithinSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(SITEWIDE_SEARCH_SHEET_NAME); rowNum++) {
			String keyword1 = excelReader.getCellData(SITEWIDE_SEARCH_SHEET_NAME, "Keyword1", rowNum);
			String keyword2 = excelReader.getCellData(SITEWIDE_SEARCH_SHEET_NAME, "Keyword2", rowNum);
			Object ob[] = { keyword1, keyword2 };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "BestBets")
	public Iterator<Object[]> readBestBetsSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(BESTBET_SEARCH_SHEET_NAME); rowNum++) {
			String bestBetKeyword = excelReader.getCellData(BESTBET_SEARCH_SHEET_NAME, "BestBetKeywords", rowNum);
			Object ob[] = { bestBetKeyword };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	/********************** Common Functions **********************/
	// Verify Search Results page common validation
	public void verifySearchResultsPage() {
		// Verify Search Results Page Title
		String pageTitle = search.getSearchResultsPageTitle();
		Assert.assertTrue(pageTitle.contains(search.SEARCH_RESULT_PAGE_TITLE));

		// Verify Search Results Page H1 Title
		String h1Title = search.getSearchResultsH1Title();
		Assert.assertTrue(h1Title.contains(search.getSearchResultsH1Title()));

		// Verify the Search result page URL to contain "result"
		Assert.assertTrue(driver.getCurrentUrl().endsWith("results"));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		// Verify the Search Within Search Box
		Assert.assertTrue(search.getSearchWithinSearchBox().isDisplayed(), "Search Within Search Box is not displayed");
		Assert.assertTrue(search.getSearchWithinSearchButton().isDisplayed(),
				"Search Within Search Button is not displayed");
	}

}
