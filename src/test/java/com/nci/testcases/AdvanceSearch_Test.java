package com.nci.testcases;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserFactory;
import com.nci.clinicalTrial.pages.AdvanceSearch;
import com.nci.commonobjects.ApiReference;
import com.nci.commonobjects.Banner;
import com.nci.commonobjects.BreadCrumb;
import com.relevantcodes.extentreports.LogStatus;

public class AdvanceSearch_Test extends BaseClass {

	// WebDriver driver;
	AdvanceSearch advanceSearch;
	BreadCrumb crumb;
	Banner banner;
	ApiReference api;

	// ConfigReader config = new ConfigReader();

	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("AdvanceSearchPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserFactory.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		advanceSearch = new AdvanceSearch(driver);
		System.out.println("Advance Search setup done");
		crumb = new BreadCrumb(driver);
		banner = new Banner(driver);
		api = new ApiReference(driver);
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

	// @Test(dataProvider = "AdvanceSearch", groups = { "Smoke" }, priority = 2)
	public void advancesearch_CancerType_SubType(int cancerTypeIndex, String cancerTypeId, String cancerType,
			int cancerSubTypeIndex, String cancerSubTypeId, String cancerSubType) throws InterruptedException {
		Object[][] data;
		Thread.sleep(500);
		advanceSearch.advSearch_CancerType_SubType(cancerType, cancerSubType);
		Thread.sleep(500);
		// advanceSearch.defaultSearch();

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

	@Test(dataProvider = "AdvanceSearch", groups = { "Smoke" }, priority = 2)
	public void advancesearch_CancerType_SubType(String cancerTypeId, String cancerType, String cancerSubTypeId,
			String cancerSubType) throws InterruptedException {
		Object[][] data;
		Thread.sleep(500);
		advanceSearch.advSearch_CancerType_SubType(cancerType, cancerSubType);
		Thread.sleep(500);
		// advanceSearch.defaultSearch();

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

	// @Test(dataProvider = "AdvanceSearch1", groups = { "Smoke" }, priority =2)
	public void advancesearch_CancerType_SubType_Stage(int cancerTypeIndex, String cancerTypeId, String cancerType,
			int cancerSubTypeIndex, String cancerSubTypeId, String cancerSubType, int cancerStageIndex,
			String cancerStageId, String cancerStage) throws InterruptedException {
		Object[][] data;
		Thread.sleep(500);
		advanceSearch.advSearch_CancerType_SubType_Stage(cancerType, cancerSubType, cancerStage);
		Thread.sleep(500);
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

	@Test(dataProvider = "AdvanceSearch1", groups = { "Smoke" }, priority = 2)
	public void advancesearch_CancerType_SubType_Stage(String cancerTypeId, String cancerType, String cancerSubTypeId,
			String cancerSubType, String cancerStageId, String cancerStage) throws InterruptedException {
		Object[][] data;
		Thread.sleep(500);
		advanceSearch.advSearch_CancerType_SubType_Stage(cancerType, cancerSubType, cancerStage);
		Thread.sleep(500);
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
		Object[][] data;
		Thread.sleep(300);
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

	@Test(dataProvider = "keywords", groups = { "Smoke" }, priority = 3)
	public void advancesearch_Keywords_PhraseTest(String keyword) throws InterruptedException {
		Object[][] data;
		Thread.sleep(300);
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

		} else {
			// Replace space with +
			String phraseWithoutSpace = keyword.replace(" ", "+");
			String pageURL = driver.getCurrentUrl();
			System.out.println("Keyword/Phrase with space replaced by + :" + phraseWithoutSpace);
			Assert.assertTrue(pageURL.contains("q=" + phraseWithoutSpace),
					"Keyword/Phrase not found " + phraseWithoutSpace);

		}
	}

	@Test(dataProvider = "zipcode", groups = { "Smoke" }, priority = 4)
	public void advancesearch_Zipcode(String zip) throws InterruptedException {
		Object[][] data;
		Thread.sleep(300);
		advanceSearch.searchZipcode(zip);

		// Verify that show search criteria table contains the selected
		// search criteria
		if (zip == "99999") {
			System.out.println("**********zip=99999***********");
			System.out.println(driver.getPageSource());
			Assert.assertTrue(driver.getPageSource().contains(
					"Sorry, you seem to have entered invalid criteria.  Please check the following, and try your search again:"));

			;
			driver.findElement(By.linkText("Try a new search")).click();
			logger.log(LogStatus.PASS, "Verify Search by invalid zipcode on Advanced CTS. Zipcode = " + zip);
		} else if (zip == "abc") {

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
	@DataProvider(name = "AdvanceSearch")
	public Object[][] readAdvanceSearchData() {
		return new Object[][] {
				// {cancerTypeIndex, cancerTypeID, cancerTypeName,
				// cancerSubTypeIndex, cancerSubTypeID, cancerSubTypeName,
				// cancerStageIndex, cancerStageId, cancerStageName}
				{ "C2991", "Other Disease", "C34783", "Alcoholic Liver Disease" },
				{ "C9312", "Bone Sarcoma", "C53707", "Bone Osteosarcoma" },
				{ "C4872", "Breast Cancer", "C5214", "Breast Adenocarcinoma" } };
	}

	@DataProvider(name = "AdvanceSearch1")
	public Object[][] readAdvanceSearchData1() {
		return new Object[][] {
				// {cancerTypeIndex, cancerTypeID, cancerTypeName,
				// cancerSubTypeIndex, cancerSubTypeID, cancerSubTypeName,
				// cancerStageIndex, cancerStageId, cancerStageName}
				{ "C9312", "Bone Sarcoma", "C53707", "Bone Osteosarcoma", "C6468", "Stage III Bone Sarcoma" },
				{ "C4872", "Breast Cancer", "C5214", "Breast Adenocarcinoma", "C36301",
						"Breast Carcinoma Metastatic in the Brain" } };
	}

	@DataProvider(name = "Age")
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
