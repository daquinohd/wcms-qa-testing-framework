package gov.nci.clinicaltrials;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import gov.nci.clinicalTrial.common.ApiReference;
import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.BasicSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.framework.ParsedURL;

public class BasicSearch_Test extends BaseClass {

	// WebDriver driver;
	private final String TESTDATA_SHEET_NAME = "BasicSearch";
	private final String BASIC_CANCERTYPE_SHEET_NAME = "Basic Cancer Types";
	private final String API_REFERENCE_H3 = "The Clinical Trials API: Use our data to power your own clinical trial search";

	private final String BREAD_CRUMB = "Home\nAbout Cancer\nCancer Treatment\nClinical Trials Information";

	private static final String KEYWORD_PARAM = "q";
	private static final String CANCERTYPE_PARAM = "t";
	private static final String AGE_PARAM = "a";
	private static final String ZIPCODE_PARAM = "z";
	private static final String RESULTS_LINK = "rl";

	BasicSearch basicSearch;
	String resultPageUrl;
	String advSearchPageUrl;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());

		pageURL = config.getPageURL("BasicClinicalTrialSearchURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);

		try {
			// Create search page with chat prompt suppressed.
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			basicSearch = new BasicSearch(driver, chatPrompt);
		} catch (Exception e) {
			basicSearch = null;
			logger.log(LogStatus.ERROR, "Error creating Basic Search page.");
		}

		System.out.println("Basic search setup done");
		testDataFilePath = config.getProperty("ClinicalTrialData");
	}

	// Verifying the UI components of Basic Search

	/**
	 * Verifies presence, visibility, and content of the search header text
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationHeaderText() {

		WebElement headerTextElement = basicSearch.getHeaderText();
		Assert.assertTrue(headerTextElement.isDisplayed(), "Basic CTS header text not displayed");
		Assert.assertTrue(headerTextElement.getText().contains("Steps to Find a Clinical Trial"),
				"header text mismatch");
	}

	/**
	 * Verifies presence, visibility, and content of the search help icon
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationSearchTip() {

		WebElement searchTipElememt = basicSearch.getSearchTipElement();
		Assert.assertTrue(searchTipElememt.isDisplayed(), "Search Tip not displayed");
		Assert.assertTrue(
				searchTipElememt.getText().contains("Search Tip: For more search options, use our advanced search"),
				"Search Tip text mismatched");
	}

	/**
	 * Verifies presence, visibility, and content of the cancer type input field.
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationCancerType() {

		WebElement cancerTypeLabel = basicSearch.getCancerTypeLabel();
		Assert.assertTrue(cancerTypeLabel.isDisplayed(), "Cancer Type label not displayed");

		WebElement cancerTypeHelpLink = basicSearch.getCancerTypeHelpLink();
		Assert.assertTrue(cancerTypeHelpLink.isDisplayed(), "Cancer Type help icon not displayed");

		WebElement cancerTypeMessageElement = basicSearch.getCancerTypeMessageElement();
		Assert.assertTrue(cancerTypeMessageElement.isDisplayed(), "Cancer Type Placeholder message not displayed");
		Assert.assertTrue(
				cancerTypeMessageElement.getAttribute("placeholder").contains("Start typing to select a cancer type"),
				"Cancer type message text mismatch");
	}

	/**
	 * Verifies presence, visibility, and content of the age input field.
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationAgeField() {

		WebElement ageLabel = basicSearch.getAgeLabel();
		Assert.assertTrue(ageLabel.isDisplayed(), "Age label not displayed");

		WebElement ageHelpLink = basicSearch.getAgeHelpLink();
		Assert.assertTrue(ageHelpLink.isDisplayed(), "Age help icon is displayed");

		WebElement ageHelpText = basicSearch.getTextAgeElement();
		Assert.assertTrue(ageHelpText.isDisplayed(), "Age help text not displayed");
		Assert.assertTrue(ageHelpText.getText().contains("Your age helps determine which trials are right for you."),
				"Age text mismatch");
	}

	/**
	 * Verifies presence, visibility, and content of the ZIP code input field.
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationZipCodeField() {

		WebElement zipCodeField = basicSearch.getZipCodeField();
		Assert.assertTrue(zipCodeField.isDisplayed(), "ZipCode Input field not displayed.");

		WebElement zipCodeLabel = basicSearch.getZipCodeLabel();
		Assert.assertTrue(zipCodeLabel.isDisplayed(), "ZipCode label not displayed.");

		WebElement zipCodeHelpLink = basicSearch.getZipCodeHelpLink();
		Assert.assertTrue(zipCodeHelpLink.isDisplayed(), "ZipCode help icon not displayed.");

		WebElement zipCodeTextElement = basicSearch.getZipCodeTextElement();
		Assert.assertTrue(zipCodeTextElement.isDisplayed(), "ZipCode help text not displayed.");
		Assert.assertTrue(zipCodeTextElement.getText().contains("Show trials near this U.S. ZIP code."),
				"Zip code help text mismatch.");
	}

	/**
	 * Verifies presence, visibility, and content of the search button
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationSearchButton() {

		WebElement searchButton = basicSearch.getSearchButton();
		Assert.assertTrue(searchButton.isDisplayed(), "Find Results button not displayed");
	}

	/**
	 * Verify link to the clinical trials "Next Steps" page.
	 */
	@Test(groups = { "Smoke" })
	public void uiVerificationNextSteps() {

		WebElement nextStepsLink = basicSearch.getNextStepsLink();
		Assert.assertTrue(nextStepsLink.isDisplayed(), "Next steps link not displayed");
		Assert.assertEquals(nextStepsLink.getTagName(), "a", "Next steps link not an 'a' tag.");

		String link = nextStepsLink.getAttribute("href");
		Assert.assertNotEquals(link, null, "Next steps link is missing");

		try {
			URL url = new URL(link);
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/trial-guide",
					"Next steps link path is mismatched.");
		} catch (MalformedURLException ex) {
			Assert.fail("Error parsing Next steps URL");
		}
	}

	/**
	 * Test search with all default vaules.
	 */
	@Test(groups = { "Smoke", "current" })
	public void searchDefault() {
		try {
			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error loading result page.");
			e.printStackTrace();
		}
	}

	/**
	 * Verify that pressing Enter while the Keyword field has focus will
	 * submit the form.
	 */
	@Test(groups = { "Smoke", "current" })
	public void pressEnterOnKeywordField() {
		try {
			BasicSearchResults result = basicSearch.pressEnterOnKeywordField();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error loading result page.");
			e.printStackTrace();
		}
	}

	/**
	 * Verify that pressing Enter while the Age field has focus will submit the
	 * form.
	 */
	@Test(groups = { "Smoke", "current" })
	public void pressEnterOnAgeField() {
		try {
			BasicSearchResults result = basicSearch.pressEnterOnAgeField();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error loading result page.");
			e.printStackTrace();
		}
	}

	/**
	 * Verify that pressing Enter while the ZIP Code field has focus will submit the
	 * form.
	 */
	@Test(groups = { "Smoke", "current" })
	public void pressEnterOnZipCodeField() {
		try {
			BasicSearchResults result = basicSearch.pressEnterOnZipCodeField();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error loading result page.");
			e.printStackTrace();
		}
	}

	/**
	 * Performs a series of keyword searches (using cancer type test data)
	 * and verifies that the search URL is created correctly.
	 */
	@Test(dataProvider = "CancerType", groups = { "Smoke" })
	public void searchByKeyword(String cancerType) {

		try {
			basicSearch.setSearchKeyword(cancerType);
			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), cancerType, "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Performs a series of tests, verifying that the selected cancer type is passed
	 * as the expected concept ID.
	 */
	@Test(dataProvider = "CancerTypeExactText", groups = { "Smoke" })
	public void searchCancerType(String cancerType, String ConceptID) throws InterruptedException {

		try {
			// Search for the exact cancer type
			basicSearch.setExactCancerType(cancerType);
			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), null, "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), ConceptID, "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Verifies handling of search by age for valid inputs.
	 */
	@Test(dataProvider = "ValidAges", groups = { "Smoke" })
	public void searchByAge(String age) {

		try {
			basicSearch.setSearchAge(age);
			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), age, "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Verifies that invalid age criteria are handled correctly.
	 */
	@Test(dataProvider = "InvalidAges", groups = { "Smoke" })
	public void searchByAgeInvalid(String age) {

		basicSearch.setSearchAge(age);
		basicSearch.setSearchKeyword(""); // Send focus to another field

		WebElement errMessage = basicSearch.getAgeInputError();
		Assert.assertTrue(errMessage.isDisplayed(), "Age input message not visible.");
		Assert.assertEquals(errMessage.getText(), "Please enter a number between 1 and 120.",
				"Age input message not set correctly.");
	}

	/**
	 * Verifies handling of search by ZIP code for valid inputs.
	 */
	@Test(dataProvider = "ValidZipCode", groups = { "Smoke" })
	public void searchByZip(String zipCode) {

		try {
			basicSearch.setSearchZip(zipCode);
			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), zipCode, "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Verifies handling of search by ZIP code for invalid inputs.
	 */
	@Test(dataProvider = "InvalidZipCode", groups = { "Smoke" })
	public void searchByZipInvalid(String zipCode) {

		basicSearch.setSearchZip(zipCode);
		basicSearch.setSearchKeyword(""); // Send focus to another field

		WebElement errMessage = basicSearch.getZipCodeInputError();
		Assert.assertTrue(errMessage.isDisplayed(), "Age input message not visible.");
		Assert.assertEquals(errMessage.getText(), "Please enter a valid 5 digit ZIP code.",
				"ZIP Code input message not set correctly.");
	}

	/**
	 * Verifies ZIP code only allows 5 digits.
	 */
	@Test(groups = { "Smoke" })
	public void enterLongZipCode() {

		String invalidZIPCode = "123456";
		basicSearch.setSearchZip(invalidZIPCode);

		String actualZip = basicSearch.getZipCodeField().getText();
		Assert.assertNotEquals(actualZip, invalidZIPCode, "Zip code field allowed more than 5 digits.");
	}

	/**
	 * Verify handling of search by ZIP code and age.
	 */
	@Test(dataProvider = "Age_ZipCode", groups = { "Smoke" })
	public void searchAgeZip(String age, String zipCode) {
		try {
			// Performing the search using Age and Zipcode parameter
			basicSearch.setSearchAge(age);
			basicSearch.setSearchZip(zipCode);

			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), "", "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), age, "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), zipCode, "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Test for the combination of keyword text and age.
	 * The list of cancer types is used as sample text.
	 */
	@Test(dataProvider = "CancerType_Age", groups = { "Smoke" })
	public void searchByKeywordAndAge(String keyword, String age) {

		try {
			// Performing the search using Age and keyword text
			basicSearch.setSearchAge(age);
			basicSearch.setSearchKeyword(keyword);

			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), keyword, "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), age, "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), "", "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Test for the combination of keyword text and ZIP code. The list of cancer types is
	 * used as sample text.
	 */
	@Test(dataProvider = "CancerType_ZipCode", groups = { "Smoke" })
	public void searchByKeywordAndZip(String keyword, String zipCode) {

		try {
			// Performing the search using Age and keyword text
			basicSearch.setSearchZip(zipCode);
			basicSearch.setSearchKeyword(keyword);

			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), keyword, "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), "", "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), zipCode, "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	/**
	 * Test for the combination of keyword text, age and ZIP code. The list of cancer
	 * types is used as keywords.
	 */
	@Test(dataProvider = "CancerType_Age_ZipCode", groups = { "Smoke" })
	public void searchKeywordAgeZip(String keyword, String age, String zipCode) {

		try {
			// Performing the search using Age and keyword text
			basicSearch.setSearchAge(age);
			basicSearch.setSearchZip(zipCode);
			basicSearch.setSearchKeyword(keyword);

			BasicSearchResults result = basicSearch.clickSearchButton();

			// Verify the search parameters were set correctly.
			ParsedURL url = result.getPageUrl();
			Assert.assertEquals(url.getPath(), "/about-cancer/treatment/clinical-trials/search/r",
					"Unexpected URL path.");

			Assert.assertEquals(url.getQueryParam(KEYWORD_PARAM), keyword, "Keyword parameter not matched.");
			Assert.assertEquals(url.getQueryParam(CANCERTYPE_PARAM), "", "Cancer Type parameter not matched.");
			Assert.assertEquals(url.getQueryParam(AGE_PARAM), age, "Age parameter not matched.");
			Assert.assertEquals(url.getQueryParam(ZIPCODE_PARAM), zipCode, "ZIP code parameter not matched.");
			Assert.assertEquals(url.getQueryParam(RESULTS_LINK), "1", "Results Link parameter not matched.");

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error parsing page URL.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Smoke" })
	public void verifyAdvancedSearchLink() {

		String link = basicSearch.getAdvancedSearchLinkUrl();
		String expected = config.getPageURL("AdvanceSearchPageURL");

		Assert.assertEquals(link, expected, "Link mismatch");
	}

	// TODO: Move all bread crumb verifications to a single test class.
	@Test(groups = { "Smoke" })
	public void verifyBreadCrumb() {

		BreadCrumb crumb = new BreadCrumb(driver);
		String text = crumb.getBreadCrumbText();

		// NOTE: getBreadCrumbText trims innerText whitespace.
		Assert.assertEquals(text, BREAD_CRUMB, "Bread crumbs don't match.");
	}

	// TODO: Move all page banner verifications to a single test class.
	@Test(groups = { "Smoke" })
	public void verifyBanner() {

		Banner banner = new Banner(driver);

		Assert.assertTrue(banner.isDisplayed(), "Banner is not visible.");
		Assert.assertEquals(banner.getAltText(), "National Cancer Institute", "Banner alt-text is mismatched.");
	}

	// TODO: Reinstate test or delete it.
	//@Test(groups = { "Smoke" })
	public void verifyApiReference() {

		ApiReference api = new ApiReference(driver);

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

	/******************** Data Providers ****************/

	@DataProvider(name = "CancerType")
	public Iterator<Object[]> readKeywordText() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {

			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
			Object ob[] = { cancerType };

			myObjects.add(ob);

		}
		return myObjects.iterator();
	}

	@DataProvider(name = "CancerTypeExactText")
	public Iterator<Object[]> readCancerType() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(BASIC_CANCERTYPE_SHEET_NAME); rowNum++) {

			String cancerTypeName = excelReader.getCellData(BASIC_CANCERTYPE_SHEET_NAME, "Cancer Type Name", rowNum);
			String conceptID = excelReader.getCellData(BASIC_CANCERTYPE_SHEET_NAME, "Concept ID", rowNum);
			Object ob[] = { cancerTypeName, conceptID };

			myObjects.add(ob);

		}
		return myObjects.iterator();
	}

	@DataProvider(name = "ValidAges")
	public Iterator<Object[]> readAge() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String age = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidAges", rowNum);
			Object ob[] = { age };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "InvalidAges")
	public Iterator<Object[]> readInvalidAge() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String age = excelReader.getCellData(TESTDATA_SHEET_NAME, "InvalidAges", rowNum);
			Object ob[] = { age };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "ValidZipCode")
	public Iterator<Object[]> readZipCode() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String zipcode = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidZipCode", rowNum);
			// skip empty rows
			if (!zipcode.trim().isEmpty()) {
				Object ob[] = { zipcode.trim() };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "InvalidZipCode")
	public Iterator<Object[]> readInvalidZipCode() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String zipcode = excelReader.getCellData(TESTDATA_SHEET_NAME, "InvalidZipCode", rowNum);
			// skip empty rows
			if (!zipcode.trim().isEmpty()) {
				Object ob[] = { zipcode.trim() };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "Age_ZipCode")
	public Iterator<Object[]> readAgeZipCode() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String age = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidAges", rowNum);
			String zipcode = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidZipCode", rowNum);

			age = age.trim();
			zipcode = zipcode.trim();

			// Skip blank entries.
			if (age.isEmpty() || zipcode.isEmpty())
				continue;

			Object ob[] = { age, zipcode };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "CancerType_Age")
	public Iterator<Object[]> readCancerTypeAge() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {

			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
			String age = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidAges", rowNum);

			cancerType = cancerType.trim();
			age = age.trim();
			if (cancerType.isEmpty() || age.isEmpty())
				continue;

			Object ob[] = { cancerType, age };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "CancerType_ZipCode")
	public Iterator<Object[]> readCancerTypeZip() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {

			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
			String zipcode = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidZipCode", rowNum);

			cancerType = cancerType.trim();
			zipcode = zipcode.trim();
			if (cancerType.isEmpty() || zipcode.isEmpty())
				continue;

			Object ob[] = { cancerType, zipcode };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	@DataProvider(name = "CancerType_Age_ZipCode")
	public Iterator<Object[]> readCancerTypeAgeZip() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {

			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
			String age = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidAges", rowNum);
			String zipcode = excelReader.getCellData(TESTDATA_SHEET_NAME, "ValidZipCode", rowNum);

			cancerType = cancerType.trim();
			age = age.trim();
			zipcode = zipcode.trim();
			if (cancerType.isEmpty() || age.isEmpty() || zipcode.isEmpty())
				continue;

			Object ob[] = { cancerType, age, zipcode };
			myObjects.add(ob);
		}
		return myObjects.iterator();
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
