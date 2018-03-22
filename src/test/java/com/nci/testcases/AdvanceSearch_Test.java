package com.nci.testcases;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserManager;
import com.nci.Utilities.ExcelManager;
import com.nci.clinicalTrial.pages.AdvanceSearch;
import com.nci.commonobjects.ApiReference;
import com.nci.commonobjects.Banner;
import com.nci.commonobjects.BreadCrumb;
import com.nci.commonobjects.Delighters;
import com.relevantcodes.extentreports.LogStatus;

public class AdvanceSearch_Test extends BaseClass {

	public static final String TESTDATA_SHEET_NAME = "AdvanceSearch";

	AdvanceSearch advanceSearch;
	Delighters delighter;
	BreadCrumb crumb;
	Banner banner;
	ApiReference api;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("AdvanceSearchPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		advanceSearch = new AdvanceSearch(driver);
		System.out.println("Advance Search setup done");
		delighter = new Delighters(driver);
		crumb = new BreadCrumb(driver);
		banner = new Banner(driver);
		api = new ApiReference(driver);
		testDataFilePath = config.getProperty("TestData");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verifyPageTitle() {
		Assert.assertEquals(advanceSearch.getAdvanceSearchPageTitle(), AdvanceSearch.ADVANCE_SEARCH_PAGE_TITLE);
		logger.log(LogStatus.PASS,
				"Verifying the Title of the page is *Find NCI-Supported Clinical Trials* | Actual Result: "
						+ advanceSearch.getAdvanceSearchPageTitle());
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verifyBanner() {
		banner.getBanner();
		logger.log(LogStatus.PASS, "Verifying the Banner of the page");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verify_bread_crumb() {
		crumb.getBreadCrumb(AdvanceSearch.BREAD_CRUMB);
		logger.log(LogStatus.PASS, "Verifying the Breadcrumb of the page");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyApiReference() {
		api.getApiReference();
		api.verifyApiReferenceLink();
		System.out.println("**********API function executed***********");
		logger.log(LogStatus.PASS, "Verifying the API Reference section on the page");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyDelighterLiveHelp() {
		// Verifying the LiveHelp Delighter
		delighter.verifyDelighterLiveHelp();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check " + driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Live Help Delighter on Advance CTS");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyDelighterWhat() {
		// Verifying the LiveHelp Delighter
		delighter.verifyDelighterWhat();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check " + driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify What are Clinical Trials Delighter on Advance CTS");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyDelighterWhich() {
		// Verifying the LiveHelp Delighter
		delighter.verifyDelighterWhich();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check " + driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Which Trials are Best for You Delighter on Advance CTS");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void defaultSearchTest() {

		advanceSearch.defaultSearch();
		System.out.println("defaultSearchTest: default Search function executed");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("defaultSearchTest: Advance Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Results"));

		/*
		 * Verify that search result summary text contains the "all trials" Ex-
		 * Results 1-10 of 5384 for your search for: "all trials"
		 */

		String searchStatus = driver.findElement(By.xpath(".//*[@class='cts-results-label']/strong")).getText();
		System.out.println("defaultSearchTest: SR Status Body Text: " + searchStatus);
		Assert.assertTrue(searchStatus.contains("all trials"));

		driver.navigate().back();
		logger.log(LogStatus.PASS, "Verify Default Search on Advanced CTS");
	}

	@Test(dataProvider = "CancerType_SubType", groups = { "Smoke" }, priority = 2)
	public void advancesearch_CancerType_SubType(String cancerTypeId, String cancerType, String cancerSubTypeId,
			String cancerSubType) throws InterruptedException {
		Object[][] data;
		// Thread.sleep(500);
		advanceSearch.advSearch_CancerType_SubType(cancerType, cancerSubType);
		// Thread.sleep(500);

		// Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("advancesearch_CancerType_SubType: Advance Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Results"));

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_CancerType_SubType: Current Page URL: " + pageURL);
		Assert.assertTrue(pageURL.contains("t=" + cancerTypeId + "&st=" + cancerSubTypeId));
		System.out.println(
				"advancesearch_CancerType_SubType: Cancer Type ID: t=" + cancerTypeId + "&st=" + cancerSubTypeId);

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], cancerType, "CancerType not matched");
		Assert.assertEquals(data[2][1], cancerSubType, "CancerSubType not matched");

		System.out.println("data[1][1]==== " + data[1][1]);
		System.out.println("cancerType==== " + cancerType);
		System.out.println("data[2][1]==== " + data[2][1]);
		System.out.println("cancerSubType==== " + cancerSubType);

		// driver.navigate().to(pageURL);
		driver.findElement(By.linkText("Start Over")).click();
		// driver.navigate().back();
		logger.log(LogStatus.PASS, "Verify Search by Cancer Type and SubType on Advanced CTS");
	}

	@Test(dataProvider = "CancerType_SubType_Stage", groups = { "Smoke" }, priority = 2)
	public void advancesearch_CancerType_SubType_Stage(String cancerTypeId, String cancerType, String cancerSubTypeId,
			String cancerSubType, String cancerStageId, String cancerStage) throws InterruptedException {
		Object[][] data;
		// Thread.sleep(200);
		advanceSearch.advSearch_CancerType_SubType_Stage(cancerType, cancerSubType, cancerStage);
		// Thread.sleep(200);
		// advanceSearch.defaultSearch();

		// Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("advancesearch_CancerType_SubType_Stage: Advance Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result"));

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_CancerType_SubType_Stage: Current Page URL: " + pageURL);
		Assert.assertTrue(pageURL.contains("t=" + cancerTypeId + "&st=" + cancerSubTypeId + "&stg=" + cancerStageId));
		System.out.println("advancesearch_CancerType_SubType_Stage: Cancer Type ID: t=" + cancerTypeId + "&st="
				+ cancerSubTypeId + "&stg=" + cancerStageId);

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], cancerType, "CancerType not matched");
		Assert.assertEquals(data[2][1], cancerSubType, "CancerSubType not matched");
		Assert.assertEquals(data[3][1], cancerStage, "CancerStage not matched");

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by Cancer Type, SubType and Stage on Advanced CTS");
	}

	@Test(dataProvider = "Age", groups = { "Smoke" }, priority = 3)
	public void advancesearch_AgeTest(int age) throws InterruptedException {
		System.out.println("Age from Data Provider: " + age);
		Object[][] data;
		// Thread.sleep(300);
		advanceSearch.getAge(age);

		if (age < 1 || age > 120) {
			WebElement error_Msg = driver.findElement(By.xpath("//div[@class='error-msg']"));
			error_Msg.getText();
			Assert.assertTrue(error_Msg.getText().contains("Please enter a number between 1 and 120."));
			System.out.println("Error message for age: " + error_Msg.getText());
			logger.log(LogStatus.PASS, "Verify Search by Age on Advanced CTS. Age = " + age);
		}

		else {
			// Verify page title
			String pageTitle = driver.getTitle();
			System.out.println("advancesearch_CancerType_SubType: Advance Search Results Page Title: " + pageTitle);
			Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result"));

			// Verify search criteria in URL
			String pageURL = driver.getCurrentUrl();
			System.out.println("advancesearch_Age: Current Page URL: " + pageURL);
			Assert.assertTrue(pageURL.contains("a=" + age));
			System.out.println("advancesearch_Age: Age: a=" + age);

			// Verify that show search criteria table contains the selected
			// search criteria
			data = verifySearchCriteriaTable();

			Assert.assertEquals(data[1][1], String.valueOf(age), "Age not matched");
			System.out.println("data[1][1]==== " + data[1][1]);

			driver.findElement(By.linkText("Start Over")).click();
			logger.log(LogStatus.PASS, "Verify Search by Age on Advanced CTS. Age = " + age);

		}
	}

	@Test(dataProvider = "Keyword", groups = { "Smoke" }, priority = 3)
	public void advancesearch_Keywords_PhraseTest(String keyword) throws InterruptedException {
		System.out.println("Keyword from Data Provider: " + keyword);
		Object[][] data;
		// Thread.sleep(300);
		advanceSearch.getKeywordPhrase(keyword);

		// Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("Page title for keyword search :" + pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Results"), "Page Title not matched");

		// Verify search criteria in URL
		if (keyword.contains("\"")) {
			// Replace space with +
			String phraseWithoutSpace = keyword.replace(" ", "+");
			// Replace " with %22
			phraseWithoutSpace = phraseWithoutSpace.replace("\"", "%22");
			String pageURL = driver.getCurrentUrl();
			System.out.println("Phrase without space and double quote replaced by %22 :" + phraseWithoutSpace);
			Assert.assertTrue(pageURL.contains("q=" + phraseWithoutSpace), "Phrase not found " + phraseWithoutSpace);
			driver.findElement(By.linkText("Start Over")).click();

		} else {
			// Replace space with +
			String phraseWithoutSpace = keyword.replace(" ", "+");
			String pageURL = driver.getCurrentUrl();
			System.out.println("Keyword/Phrase with space replaced by + :" + phraseWithoutSpace);
			Assert.assertTrue(pageURL.contains("q=" + phraseWithoutSpace),
					"Keyword/Phrase not found " + phraseWithoutSpace);
			driver.findElement(By.linkText("Start Over")).click();

		}
	}

	@Test(dataProvider = "Zipcode", groups = { "Smoke" }, priority = 4)
	public void advancesearch_Zipcode(String zip) throws InterruptedException {
		System.out.println("Zipcode from Data Provider: " + zip);
		// zip = zip.toString();
		// System.out.println("Zipcode after converting to String: " + zip);
		Object[][] data;
		Thread.sleep(300);
		advanceSearch.searchZipcode(zip);

		// Verify that show search criteria table contains the selected
		// search criteria
		if (zip.equals("99999")) {
			System.out.println("**********zip=99999***********");
			// System.out.println(driver.getPageSource());
			Assert.assertTrue(driver.getPageSource().contains(
					"Sorry, you seem to have entered invalid criteria.  Please check the following, and try your search again:"));

			;
			driver.findElement(By.linkText("Try a new search")).click();
			logger.log(LogStatus.PASS, "Verify Search by invalid zipcode on Advanced CTS. Zipcode = " + zip);
		} else if (zip.equals("abc")) {

			WebElement error_Msg = driver.findElement(By.xpath("//div[@class='error-msg']"));
			error_Msg.getText();
			Assert.assertTrue(error_Msg.getText().contains("Please enter a valid 5 digit ZIP code."));
			System.out.println("Error message for age: " + error_Msg.getText());
			logger.log(LogStatus.PASS, "Verify Search by Age on Advanced CTS. Age = " + zip);
		}

		else {
			// Verify page title
			String pageTitle = driver.getTitle();
			System.out.println("Page title for zipcode search :" + pageTitle);
			Assert.assertTrue(pageTitle.contains("Clinical Trials Search Results"), "Page Title not matched");

			// Verify search criteria in URL
			String pageURL = driver.getCurrentUrl();
			System.out.println("advancesearch_Zipcode: Current Page URL: " + pageURL);
			Assert.assertTrue(pageURL.contains("z=" + zip));
			System.out.println("advancesearch_Zipcode: Zipcode: z=" + zip);

			data = verifySearchCriteriaTable();

			Assert.assertTrue(data[1][1].toString().contains(zip), "zip not matched" + zip);
			System.out.println("data[1][1]==== " + data[1][1]);

			driver.findElement(By.linkText("Start Over")).click();
			logger.log(LogStatus.PASS, "Verify Search by zipcode on Advanced CTS. Zipcode = " + zip);

		}
	}

	/********************** Data Providers **********************/
	@DataProvider(name = "CancerType_SubType")
	public Iterator<Object[]> readCancerType_SubType_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String cancerTypeId = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerTypeId", rowNum);
			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
			String cancerSubTypeId = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerSubTypeId", rowNum);
			String cancerSubType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerSubType", rowNum);
			Object ob[] = { cancerTypeId, cancerType, cancerSubTypeId, cancerSubType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "CancerType_SubType_Stage")
	public Iterator<Object[]> readCancerType_SubType_Stage_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String cancerTypeId = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerTypeId", rowNum);
			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
			String cancerSubTypeId = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerSubTypeId", rowNum);
			String cancerSubType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerSubType", rowNum);
			String cancerStageId = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerStageId", rowNum);
			String cancerStage = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerStage", rowNum);
			Object ob[] = { cancerTypeId, cancerType, cancerSubTypeId, cancerSubType, cancerStageId, cancerStage };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "Age")
	public Iterator<Object[]> readAge_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String age = excelReader.getCellData(TESTDATA_SHEET_NAME, "Age", rowNum);
			int age1 = Integer.valueOf(age);
			Object ob[] = { age1 };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "Keyword")
	public Iterator<Object[]> readKeyword_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String keyword = excelReader.getCellData(TESTDATA_SHEET_NAME, "Keyword", rowNum);
			System.out.println("Keyword in data provider ======= " + keyword);
			Object ob[] = { keyword };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "Zipcode")
	public Iterator<Object[]> readZipcode_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String zipcode = excelReader.getCellData(TESTDATA_SHEET_NAME, "Zipcode", rowNum);
			String zipcode1 = String.valueOf(zipcode);
			Object ob[] = { zipcode1 };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	public Object[][] readAdvanceSearchAge() {
		return new Object[][] {
				// {age}
				{ -10 }, { 18 }, { 19 }, { 121 }, { 120 } };
	}

	@DataProvider(name = "keywords")
	public Object[][] readAdvanceSearchKeywords() {
		return new Object[][] {
				// {keywords, Phrase}
				{ "PSA" }, { "\"Paget Disease\"" }, { "Brain Cancer" } };
	}

	@DataProvider(name = "zipcode")
	public Object[][] readAdvanceSearchZipcode() {
		return new Object[][] {
				// {zipcode}
				{ "20105" }, { "99999" }, { "abc" } };
	}

	/********************** Common Functions **********************/
	// Verify that show search criteria table contains the selected search
	// criteria
	public Object[][] verifySearchCriteriaTable() {
		Object[][] data;

		driver.findElement(By.xpath(".//*[@class='ctscb']")).click();
		// Thread.sleep(300);

		WebElement table_element = driver.findElement(By.xpath(".//table[@class='table no-auto-enlarge']"));

		List<WebElement> tr_collection = table_element.findElements(By.tagName("tr"));
		System.out.println("Verify Table: NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());
		data = new Object[tr_collection.size()][2];
		int row_num, col_num;
		row_num = 0;

		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
			System.out.println("Verify Table: NUMBER OF COLUMNS=" + td_collection.size());

			col_num = 0;
			for (WebElement tdElement : td_collection) {
				System.out.println(
						"Verify Table: row # " + row_num + ", col # " + col_num + "text=" + tdElement.getText());
				data[row_num][col_num] = tdElement.getText();
				System.out.println("Verify Table: DATA=" + data[row_num][col_num]);
				col_num++;
			}
			row_num++;
		}

		return data;
	}

}
