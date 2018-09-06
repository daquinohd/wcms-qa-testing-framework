package gov.nci.clinicaltrials;

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

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicalTrial.pages.AdvanceSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.clinicalTrial.common.ApiReference;
import gov.nci.clinicalTrial.common.Delighters;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import com.relevantcodes.extentreports.LogStatus;

public class AdvanceSearch_Test extends BaseClass {

	public static final String TESTDATA_SHEET_NAME = "AdvanceSearch";
	public static final String API_REFERENCE_H3 = "The Clinical Trials API: Use our data to power your own clinical trial search";

	AdvanceSearch advanceSearch;
	Delighters delighter;
	BreadCrumb crumb;
	ApiReference api;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser", "environment" })
	public void setup(String browser, String environment) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("AdvanceSearchPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, config, pageURL);

		try {
			SuppressChatPromptPageObject chatBlock = new SuppressChatPromptPageObject(driver, null);
			advanceSearch = new AdvanceSearch(driver, chatBlock);
		} catch (Exception e) {
			advanceSearch = null;
			logger.log(LogStatus.ERROR, "Error creating Advanced Search page.");
		}

		System.out.println("Advance Search setup done");
		delighter = new Delighters(driver);
		crumb = new BreadCrumb(driver);
		api = new ApiReference(driver);
		testDataFilePath = config.getProperty("ClinicalTrialData");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verifyAdvanceSearchPageTitle() {
		Assert.assertEquals(advanceSearch.getAdvanceSearchPageTitle(), AdvanceSearch.ADVANCE_SEARCH_PAGE_TITLE);
		logger.log(LogStatus.PASS,
				"Verifying the Title of the page is *Find NCI-Supported Clinical Trials* | Actual Result: "
						+ advanceSearch.getAdvanceSearchPageTitle());
	}

	// TODO: Move all page banner verifications to a single test class.
	@Test(groups = { "Smoke" }, priority = 1)
	public void verifyBanner() {

		Banner banner = new Banner(driver);

		Assert.assertTrue(banner.isDisplayed(), "Banner is not visible.");
		Assert.assertEquals(banner.getAltText(), "National Cancer Institute", "Banner alt-text is mismatched.");

		logger.log(LogStatus.PASS, "Verifying the Banner of the page");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verify_bread_crumb() {
		crumb.getBreadCrumbText();
		Assert.assertEquals(crumb.getBreadCrumbText(), advanceSearch.BREAD_CRUMB);
		System.out.println("Breadcrumb is displaying correctly");
		logger.log(LogStatus.PASS, "Pass => " + "Verifying the Breadcrumb of the page");

	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyApiReference() {
		api.getApiReference();
		Assert.assertTrue(api.getApiReference().isDisplayed());
		Assert.assertEquals(api.getApiReference().getText(), API_REFERENCE_H3);
		Assert.assertTrue(api.getApiReferenceText().isDisplayed());
		api.verifyApiReferenceLink();
		Assert.assertTrue(driver.getCurrentUrl().contains("/syndication/api"));
		driver.navigate().back();
		System.out.println("**********API function executed***********");
		logger.log(LogStatus.PASS, "Verifying the API Reference section on the page");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyDelighterLiveHelp() {
		Assert.assertTrue(delighter.getLiveHelpDelighter().isDisplayed());
		String expectedPageUrl = config.getProperty("CTSLiveHelpDelighterPath");
		// Verifying the LiveHelp Delighter
		delighter.verifyDelighterLiveHelp();
		// Checking the end page URL
		String resultPageUrl = driver.getCurrentUrl();
		System.out.println("Page URL of the delighter " + resultPageUrl);
		Assert.assertTrue(resultPageUrl.equals(expectedPageUrl), "Actual URL is not as expected " + resultPageUrl);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check " + driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Live Help Delighter on Advance CTS");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyDelighterWhat() {
		Assert.assertTrue(delighter.getWhatAreDelighter().isDisplayed());
		String expectedPageUrl = config.getProperty("CTSWhatAreTrialsDelighterPath");
		// Verifying the LiveHelp Delighter
		delighter.verifyDelighterWhat();
		// Checking the end page URL
		String resultPageUrl = driver.getCurrentUrl();
		System.out.println("Page URL of the delighter " + resultPageUrl);
		Assert.assertTrue(resultPageUrl.equals(expectedPageUrl), "Actual URL is not as expected " + resultPageUrl);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check " + driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify What are Clinical Trials Delighter on Advance CTS");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verifyDelighterWhich() {
		Assert.assertTrue(delighter.getWhichTrialDelighter().isDisplayed());
		String expectedPageUrl = config.getProperty("CTSWhichTrialsDelighterPath");
		// Verifying the LiveHelp Delighter
		delighter.verifyDelighterWhich();
		// Checking the end page URL
		String resultPageUrl = driver.getCurrentUrl();
		System.out.println("Page URL of the delighter " + resultPageUrl);
		Assert.assertTrue(resultPageUrl.equals(expectedPageUrl), "Actual URL is not as expected " + resultPageUrl);
		driver.navigate().back();
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
		verifyAdvanceSearchResultsPageTitle();

		// Verify that search result summary text contains the "all trials"

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
		advanceSearch.advSearch_CancerType_SubType(cancerType, cancerSubType);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

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
		logger.log(LogStatus.PASS, "Verify Search by Cancer Type and SubType on Advanced CTS. CancerType = "
				+ cancerType + " , CancerSubType = " + cancerSubType);
	}

	@Test(dataProvider = "CancerType_SubType_Stage", groups = { "Smoke" }, priority = 2)
	public void advancesearch_CancerType_SubType_Stage(String cancerTypeId, String cancerType, String cancerSubTypeId,
			String cancerSubType, String cancerStageId, String cancerStage) throws InterruptedException {
		Object[][] data;
		Thread.sleep(50);

		advanceSearch.advSearch_CancerType_SubType_Stage(cancerType, cancerSubType, cancerStage);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

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
		logger.log(LogStatus.PASS, "Verify Search by Cancer Type, SubType and Stage on Advanced CTS. CancerType = "
				+ cancerType + " , CancerSubType = " + cancerSubType + " , CancerStage = " + cancerStage);

	}

	@Test(dataProvider = "Age", groups = { "Smoke" }, priority = 3)
	public void advancesearch_AgeTest(int age) throws InterruptedException {
		System.out.println("Age from Data Provider: " + age);
		Object[][] data;

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
			verifyAdvanceSearchResultsPageTitle();

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

		advanceSearch.getKeywordPhrase(keyword);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		if (keyword.contains("\"")) {
			// Replace space with +
			String phraseWithoutSpace = keyword.replace(" ", "+");
			// Replace '\"' with %22
			phraseWithoutSpace = phraseWithoutSpace.replace("\"", "%22");
			String pageURL = driver.getCurrentUrl();
			System.out.println("Phrase without space and double quote replaced by %22 :" + phraseWithoutSpace);
			Assert.assertTrue(pageURL.contains("q=" + phraseWithoutSpace), "Phrase not found " + phraseWithoutSpace);
			driver.findElement(By.linkText("Start Over")).click();
			logger.log(LogStatus.PASS, "Verify Search by Keyword on Advanced CTS. Keyword = " + keyword);

		} else {
			// Replace space with +
			String phraseWithoutSpace = keyword.replace(" ", "+");
			String pageURL = driver.getCurrentUrl();
			System.out.println("Keyword/Phrase with space replaced by + :" + phraseWithoutSpace);
			Assert.assertTrue(pageURL.contains("q=" + phraseWithoutSpace),
					"Keyword/Phrase not found " + phraseWithoutSpace);
			driver.findElement(By.linkText("Start Over")).click();
			logger.log(LogStatus.PASS, "Verify Search by Keyword on Advanced CTS. Keyword = " + keyword);

		}
	}

	@Test(dataProvider = "Zipcode", groups = { "Smoke" }, priority = 4)
	public void advancesearch_Zipcode(String zip) throws InterruptedException {
		System.out.println("Zipcode from Data Provider: " + zip);
		// zip = zip.toString();
		// System.out.println("Zipcode after converting to String: " + zip);
		Object[][] data;
		Thread.sleep(300);
		advanceSearch.advSearch_Zipcode(zip);

		// Verify that show search criteria table contains the selected search
		// criteria
		if (zip.equals("99999")) {
			System.out.println("**********zip=99999***********");
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
			verifyAdvanceSearchResultsPageTitle();

			// Verify search criteria in URL
			String pageURL = driver.getCurrentUrl();
			System.out.println("advancesearch_Zipcode: Current Page URL: " + pageURL);
			Assert.assertTrue(pageURL.contains("loc=1"));
			Assert.assertTrue(pageURL.contains("z=" + zip));
			System.out.println("advancesearch_Zipcode: Zipcode: z=" + zip);

			data = verifySearchCriteriaTable();

			Assert.assertTrue(data[1][1].toString().contains(zip), "zip not matched" + zip);
			System.out.println("data[1][1]==== " + data[1][1]);

			driver.findElement(By.linkText("Start Over")).click();
			logger.log(LogStatus.PASS, "Verify Search by zipcode on Advanced CTS. Zipcode = " + zip);

		}
	}

	@Test(dataProvider = "CountryStateCity", groups = { "Smoke" }, priority = 4)
	public void advancesearch_CountryStateCity(String country, String state, String statecode, String city)
			throws InterruptedException {
		System.out.println("Country from Data Provider: " + country + " ,State from Data Provider: " + state
				+ " ,City from Data Provider: " + city);

		Object[][] data;
		Thread.sleep(300);
		advanceSearch.advSearch_CountryStateCity(country, state, city);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_CountryStateCity: Current Page URL: " + pageURL);
		String countrywithoutspace = country.replace(" ", "+");
		String citywithoutspace = city.replace(" ", "+");
		Assert.assertTrue(pageURL.contains("loc=2"));
		Assert.assertTrue(pageURL.contains("lcnty=" + countrywithoutspace));
		System.out.println("advancesearch_CountryStateCity: Country: lcnty=" + country);
		Assert.assertTrue(pageURL.contains("&lst=" + statecode + "&lcty=" + citywithoutspace));
		System.out.println("advancesearch_CountryStateCity: Country: lcnty=" + country + " and State: &lst=" + state
				+ "and City:&lcty=" + city);

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], country, "Country not matched");
		Assert.assertEquals(data[2][1], state, "State not matched");
		Assert.assertEquals(data[3][1], city, "City not matched");

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by Country, State and City on Advanced CTS. Country = " + country
				+ " , State = " + state + " , City = " + city);

	}

	@Test(dataProvider = "HospitalInstitution", groups = { "Smoke" }, priority = 4)
	public void advancesearch_Hospital(String hospital) throws InterruptedException {
		System.out.println("Hospital from Data Provider: " + hospital);

		Object[][] data;
		Thread.sleep(300);
		advanceSearch.advSearch_Hospital(hospital);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_Hospital: Current Page URL: " + pageURL);
		String hospitalwithoutspace = hospital.replace(" ", "+");
		Assert.assertTrue(pageURL.contains("loc=3"));
		Assert.assertTrue(pageURL.contains("hos=" + hospitalwithoutspace));
		System.out.println("advancesearch_Hospital: Hospital: hos=" + hospital);

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], hospital, "Hospital not matched");

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by Hospital on Advanced CTS. Hospital = " + hospital);
	}

	@Test(groups = { "Smoke" }, priority = 4)
	public void advancesearch_AtNIH() throws InterruptedException {

		Object[][] data;
		Thread.sleep(300);
		advanceSearch.advSearch_AtNIH();

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_AtNIH: Current Page URL: " + pageURL);
		Assert.assertTrue(pageURL.contains("loc=4"));
		System.out.println("advancesearch_AtNIH: loc=4");

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], "Only show trials at the NIH Clinical Center (Bethesda, MD)",
				"NIH not matched");
		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS,
				"Verify Search to only show trials at the NIH Clinical Center (Bethesda, MD) location on Advanced CTS.");

	}

	@Test(dataProvider = "TrialType", groups = { "Smoke" }, priority = 4)
	public void advancesearch_TrialType(String trialType) throws InterruptedException {
		Object[][] data;
		Thread.sleep(200);
		advanceSearch.advSearch_TrialType(trialType);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_TrialType: Current Page URL: " + pageURL);
		String trialTypewithoutspace = trialType.replace(" ", "_");
		trialTypewithoutspace = trialTypewithoutspace.toLowerCase();
		System.out.println("advancesearch_TrialType: tt= " + trialTypewithoutspace);
		Assert.assertTrue(pageURL.contains("tt=" + trialTypewithoutspace));

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], trialType, "TrialType not matched");
		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by Trial Type on Advanced CTS. Trial Type = " + trialType);

	}

	@Test(dataProvider = "Drug", groups = { "Smoke" }, priority = 4)
	public void advancesearch_Drug(String drug, String drugId) throws InterruptedException {
		Object[][] data;
		Thread.sleep(200);
		advanceSearch.advSearch_Drug(drug);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_Drug: Current Page URL: " + pageURL);
		Assert.assertTrue(pageURL.contains("d=" + drugId));
		System.out.println("advancesearch_Drug: d= " + drug);

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], drug, "Drug not matched");

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by Drug on Advanced CTS");

	}

	@Test(dataProvider = "Treatment", groups = { "Smoke" }, priority = 4)
	public void advancesearch_Treatment(String treatment, String treatmentId) throws InterruptedException {
		Object[][] data;
		Thread.sleep(200);
		advanceSearch.advSearch_Treatment(treatment);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("advancesearch_Treatment: Advance Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result"));

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_Treatment: Current Page URL: " + pageURL);
		Assert.assertTrue(pageURL.contains("i=" + treatmentId));
		System.out.println("advancesearch_Treatment: i=" + treatment);

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], treatment, "Treatment not matched");
		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by Treatment on Advanced CTS");

	}

	@Test(dataProvider = "TrialPhase", groups = { "Smoke" }, priority = 4)
	public void advancesearch_TrialPhase(String trialphase) throws InterruptedException {
		Object[][] data;
		Thread.sleep(200);
		advanceSearch.advSearch_TrialPhase(trialphase);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		int phaselength = trialphase.length();
		String phaseno = trialphase.substring(6, phaselength);
		System.out.println("advancesearch_TrialPhase: Current Page URL: " + pageURL);
		System.out.println("advancesearch_TrialPhase: tp=" + phaseno);
		Assert.assertTrue(pageURL.contains("tp=" + phaseno));

		// Verify that show search criteria table contains the selected search
		// criteria
		data = verifySearchCriteriaTable();

		Assert.assertEquals(data[1][1], trialphase, "TrialPhase not matched");
		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS, "Verify Search by TrialPhase on Advanced CTS. Trial Phase = " + trialphase);

	}

	@Test(dataProvider = "TrialID", groups = { "Smoke" }, priority = 4)
	public void advancesearch_TrialID(String trialId) throws InterruptedException {
		Object[][] data;
		// Thread.sleep(200);
		advanceSearch.advSearch_TrialID(trialId);
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_TrialID: Current Page URL: " + pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		if (trialId.contains(";")) {
			// Replace ; with %3B
			String trialIdWithSemicolon = trialId.replace(";", "%3B");
			System.out.println("Trial Id with semicolon replaced by %3B :" + trialIdWithSemicolon);
			Assert.assertTrue(pageURL.contains("tid=" + trialIdWithSemicolon),
					"Phrase not found " + trialIdWithSemicolon);

			// Verify that show search criteria table contains the selected
			// search criteria
			data = verifySearchCriteriaTable();
			String trialIds = trialId.replace(";", ", ");
			Assert.assertEquals(data[1][1], trialIds, "Drug not matched");

		} else if (trialId.contains(",")) {
			// Replace , with %2C
			String trialIdWithComma = trialId.replace(",", "%2C");
			System.out.println("Trial Id with comma replaced by %2C :" + trialIdWithComma);
			Assert.assertTrue(pageURL.contains("tid=" + trialIdWithComma), "Phrase not found " + trialIdWithComma);

			// Verify that show search criteria table contains the selected
			// search criteria
			data = verifySearchCriteriaTable();
			String trialIds = trialId.replace(",", ", ");
			Assert.assertEquals(data[1][1], trialIds, "Drug not matched");

		} else {
			Assert.assertTrue(pageURL.contains("tid=" + trialId));
			System.out.println("advancesearch_TrialId: tid= " + trialId);

			// Verify that show search criteria table contains the selected
			// search criteria
			data = verifySearchCriteriaTable();
			Assert.assertEquals(data[1][1], trialId, "Drug not matched");
		}

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS,
				"Verify Search by TrialId or Trial Ids (separated by ; or ,) on Advanced CTS. Trial Id = " + trialId);
	}

	@Test(dataProvider = "TrialInvestigator", groups = { "Smoke", "current" }, priority = 4)
	public void advancesearch_TrialInvestigator(String trialInvestigator) throws InterruptedException {
		Object[][] data;
		advanceSearch.advSearch_TrialInvestigator(trialInvestigator);
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_TrialInvestigator: Current Page URL: " + pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();
		// Verify search criteria in URL
		Assert.assertTrue(pageURL.contains("in=" + trialInvestigator));
		System.out.println("advancesearch_TrialInvestigator: in= " + trialInvestigator);

		// Verify that show search criteria table contains the selected
		// search criteria
		data = verifySearchCriteriaTable();
		Assert.assertEquals(data[1][1], trialInvestigator, "Investigator not matched");

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS,
				"Verify Search by Trial Investigator on Advanced CTS. Trial Id = " + trialInvestigator);
	}

	@Test(dataProvider = "LeadOrganization", groups = { "Smoke" }, priority = 4)
	public void advancesearch_LeadOrganization(String leadOrganization) throws InterruptedException {
		Object[][] data;
		advanceSearch.advSearch_LeadOrganization(leadOrganization);
		String pageURL = driver.getCurrentUrl();
		System.out.println("advancesearch_LeadOrganization: Current Page URL: " + pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Verify page title
		verifyAdvanceSearchResultsPageTitle();

		// Verify search criteria in URL
		String leadOrganizationWithoutSpace = leadOrganization.replace(" ", "+");
		Assert.assertTrue(pageURL.contains("lo=" + leadOrganizationWithoutSpace));
		System.out.println("advancesearch_TrialInvestigator: in= " + leadOrganizationWithoutSpace);

		// Verify that show search criteria table contains the selected
		// search criteria
		data = verifySearchCriteriaTable();
		Assert.assertEquals(data[1][1], leadOrganization, "Lead Organization not matched");

		driver.findElement(By.linkText("Start Over")).click();
		logger.log(LogStatus.PASS,
				"Verify Search by Lead Organization on Advanced CTS. Lead Organization = " + leadOrganization);
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

	@DataProvider(name = "CountryStateCity")
	public Iterator<Object[]> readCountry_State_City_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String country = excelReader.getCellData(TESTDATA_SHEET_NAME, "Country", rowNum);
			String state = excelReader.getCellData(TESTDATA_SHEET_NAME, "State", rowNum);
			String statecode = excelReader.getCellData(TESTDATA_SHEET_NAME, "StateCode", rowNum);
			String city = excelReader.getCellData(TESTDATA_SHEET_NAME, "City", rowNum);
			Object ob[] = { country, state, statecode, city };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "HospitalInstitution")
	public Iterator<Object[]> readHospitalInstitution_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String hospital = excelReader.getCellData(TESTDATA_SHEET_NAME, "Hospital", rowNum);
			Object ob[] = { hospital };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "Drug")
	public Iterator<Object[]> readDrug_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String drug = excelReader.getCellData(TESTDATA_SHEET_NAME, "Drug", rowNum);
			String drugId = excelReader.getCellData(TESTDATA_SHEET_NAME, "DrugId", rowNum);
			Object ob[] = { drug, drugId };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "Treatment")
	public Iterator<Object[]> readTreatment_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String treatment = excelReader.getCellData(TESTDATA_SHEET_NAME, "Treatment", rowNum);
			String treatmentId = excelReader.getCellData(TESTDATA_SHEET_NAME, "TreatmentId", rowNum);
			Object ob[] = { treatment, treatmentId };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "TrialPhase")
	public Iterator<Object[]> readTrialPhase_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String trialphase = excelReader.getCellData(TESTDATA_SHEET_NAME, "TrialPhase", rowNum);
			// String treatmentId = excelReader.getCellData(TESTDATA_SHEET_NAME,
			// "TreatmentId", rowNum);
			Object ob[] = { trialphase };

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

	@DataProvider(name = "TrialType")
	public Iterator<Object[]> readTrialType_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String trialType = excelReader.getCellData(TESTDATA_SHEET_NAME, "TrialType", rowNum);
			System.out.println("Trial Type in data provider ======= " + trialType);
			Object ob[] = { trialType };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "TrialID")
	public Iterator<Object[]> readTrialId_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String trialId = excelReader.getCellData(TESTDATA_SHEET_NAME, "TrialId", rowNum);
			System.out.println("Trial Id in data provider ======= " + trialId);
			Object ob[] = { trialId };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "TrialInvestigator")
	public Iterator<Object[]> readTrialInvestigator_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String TrialInvestigator = excelReader.getCellData(TESTDATA_SHEET_NAME, "TrialInvestigator", rowNum);
			System.out.println("Trial Investigator in data provider ======= " + TrialInvestigator);
			Object ob[] = { TrialInvestigator };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "LeadOrganization")
	public Iterator<Object[]> readLeadOrganization_Data() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String leadOrganization = excelReader.getCellData(TESTDATA_SHEET_NAME, "LeadOrganization", rowNum);
			System.out.println("Lead Organization in data provider ======= " + leadOrganization);
			Object ob[] = { leadOrganization };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	/********************** Common Functions **********************/
	// Verify page title
	public void verifyAdvanceSearchResultsPageTitle() {
		String pageTitle = driver.getTitle();
		System.out.println("Advance Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result"));
	}

	// Verify that show search criteria table contains the selected search
	// criteria
	public Object[][] verifySearchCriteriaTable() {
		Object[][] data;

		driver.findElement(By.xpath(".//*[@class='ctscb']")).click();

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
