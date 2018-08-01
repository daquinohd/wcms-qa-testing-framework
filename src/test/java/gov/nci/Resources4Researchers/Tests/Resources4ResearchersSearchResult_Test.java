package gov.nci.Resources4Researchers.Tests;

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

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Resources4Researchers.Resources4ResearchersHome;
import gov.nci.Resources4Researchers.Resources4ResearchersSearchResult;
import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.Utilities.FunctionLibrary;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.testcases.BaseClass;

public class Resources4ResearchersSearchResult_Test extends BaseClass {

	public static final String TESTDATA_SHEET_NAME = "R4R";

	Resources4ResearchersHome r4rHome;
	Resources4ResearchersSearchResult r4rSearchResult;
	BreadCrumb crumb;
	Banner banner;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("Resources4ResearchersSearchResultURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		r4rSearchResult = new Resources4ResearchersSearchResult(driver, logger);
		r4rHome = new Resources4ResearchersHome(driver, logger);
		crumb = new BreadCrumb(driver);
		banner = new Banner(driver);
		testDataFilePath = config.getProperty("TestData");
	}

	@Test(groups = { "Smoke" })
	public void verifyBanner() {
		Assert.assertTrue(banner.getBanner().isDisplayed());
		Assert.assertEquals(banner.getBanner().getAttribute("alt"), "National Cancer Institute");
		logger.log(LogStatus.PASS, "Verify the Banner of the page");
	}

	@Test(groups = { "Smoke" })
	public void verify_bread_crumb() {
		crumb.getBreadCrumb();
		Assert.assertEquals(crumb.getBreadCrumb(), r4rSearchResult.BREAD_CRUMB);
		logger.log(LogStatus.PASS, "Verify the Breadcrumb of the page");

	}

	@Test(groups = { "Smoke" })
	public void verifyr4rHomeH1Title() {
		Assert.assertEquals(r4rSearchResult.getPageH1Title().getText(),
				r4rSearchResult.R4R_SEARCH_RESULT_H1_PAGE_TITLE);
		logger.log(LogStatus.PASS, "Verify that H1 Title of the page is *Resources for Researchers Search Results*");
	}

	@Test(groups = { "Smoke" })
	public void verifyResources4ResearchersHomeLink() {
		r4rSearchResult.clickResources4ResearchersHome();
		Assert.assertTrue(FunctionLibrary.waitURLToBe(driver, r4rSearchResult.R4R_HOME_PAGE_URL));
		driver.navigate().back();
		logger.log(LogStatus.PASS,
				"Verify that 'Resources For Researchers Home' page is displayed on clicking the link 'Resources for Researchers Home'");
	}

	@Test(groups = { "Smoke" })
	public void verifySearchBar() {
		Assert.assertTrue(r4rSearchResult.getSearchBox().isDisplayed(), "Search Bar is not displayed");
		Assert.assertTrue(r4rSearchResult.getSearchButton().isDisplayed(), "Search Button is not displayed");
		logger.log(LogStatus.PASS, "Verify that Search Bar and button are displayed");
	}

	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySearch(String keyword) {

		System.out.println("Search Keyword: " + keyword);
		r4rSearchResult.search(keyword);

		//Verify Search Results Page H1 Title
		verifySearchResultsPageTitle();

		//Verify the URL to contain q=keyword
		Assert.assertTrue(driver.getCurrentUrl().endsWith("q=" + keyword));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		//Verify that the Search Box contains search keyword
		WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
		Assert.assertTrue(ele.getAttribute("value").equals(keyword));

		//Verify the message on the screen when search is done for keyword having no result Ex-Terminology 
		if (keyword.equalsIgnoreCase("Terminology")) {
			WebElement resultText = driver.findElement(By.xpath("//div[@class='results__noresults  ']"));
			System.out.println("No Results Text: " + resultText.getText());
			Assert.assertTrue(
					resultText.getText()
							.equals("No results were found for your search. Please try another search or view all resources."),
					"No Result text is displayed/matched");
		}
		ele.clear();
		driver.navigate().to(pageURL);
		logger.log(LogStatus.PASS,
				"Verify that when a keyword is searched, Search Result page is displayed with following validations: "
						+ "Page Title, URL containing q=keyword and Search bar displays the keyword. If no result for a keyword, no result message is displayed");
	}

	@Test(groups = { "Smoke" })
	public void verifyToolTypesOptions() {
		Assert.assertTrue(r4rSearchResult.getToolTypeBox().isDisplayed(), "Tool Types Box is not displayed");
		Assert.assertEquals(r4rSearchResult.getToolTypeLabel().getText(), "Tool Types");
		List<String> toolTypeNameAsString = r4rSearchResult.getToolTypesOptionsName();
		Assert.assertTrue(r4rSearchResult.EXP_TOOL_TYPE_OPTIONS.equals(toolTypeNameAsString));
		logger.log(LogStatus.PASS,
				"Verify that Tool Type box is present, Title of the box is Tool Type and names of Tool Types as displayed in Tool Types Box");
	}

	@Test(groups = { "Smoke" })
	public void searchByToolTypesOptions() {
		List<WebElement> toolTypeOptions = r4rSearchResult.getToolTypeOptions();
		List<String> toolTypeNameAsString = r4rSearchResult.getToolTypesOptionsName();
		List<String> toolTypeNumberAsString = r4rSearchResult.getToolTypesOptionsNumber();

		for (int i = 1; i <= toolTypeOptions.size(); i++) {

			//Clicking on tool type option 
			System.out.println("Tool Type Option=============" + toolTypeOptions.get(i - 1).getText());
			String toolTypeOption = toolTypeOptions.get(i - 1).getText();
			toolTypeOptions.get(i - 1).click();

			//Verify Search Results Page Title
			verifySearchResultsPageTitle();

			//Verify the URL to contain toolTypes=<tool type option clicked>
			//Assert.assertTrue(driver.getCurrentUrl().endsWith("toolTypes=" + toolTypeNameAsString.get(i - 1)));
			//System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

			//Verify that the Search Box is empty
			WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
			Assert.assertTrue(ele.getAttribute("value").equals(""));

			//Verify that Your Selection field displays the tool type clicked
			WebElement toolType = driver.findElement(By.cssSelector(".selected-filters__filter>p"));
			System.out.println("Your Selection Filter: " + toolType.getText());
			Assert.assertTrue(toolType.getText().equals(toolTypeNameAsString.get(i - 1)));

			//Verify that Result Count text contains the same number as present in the Tool Type Option
			String resultCount = driver.findElement(By.xpath("//div[@class='r4r-pager__count  r4r-DEFAULT']"))
					.getText();
			System.out.println("Text in Result Count: " + resultCount);
			Assert.assertTrue(resultCount.contains(toolTypeNumberAsString.get(i - 1)), "Result count not matching");

			//Verify that tool type box displays the tool type filter
			WebElement toolTypeFilter = driver
					.findElement(By.xpath("(//span[@class='filter__label  r4r-DEFAULT'])[1]"));
			System.out.println("Selected Tool Type Filter=======: " + toolTypeFilter.getText());
			Assert.assertTrue(toolTypeFilter.getText().equals(toolTypeOption));

			//Verify the number of results in results container after applying the tool type filter
			if (Integer.parseInt(toolTypeNumberAsString.get(i - 1)) <= 20) {

				List<WebElement> searchResultItems = driver
						.findElements(By.xpath("//section[@class='results-container  r4r-DEFAULT']/article/h2"));
				System.out.println("Search Result H2=======: " + searchResultItems.size());
				Assert.assertEquals(searchResultItems.size(), Integer.parseInt(toolTypeNumberAsString.get(i - 1)));
			}

			driver.navigate().back();
			logger.log(LogStatus.PASS, "Verify that Search Results can be filtered based on Tool Type Option: "
					+ toolTypeNameAsString.get(i - 1));
		}

	}

	@Test(groups = { "Smoke" })
	public void verifyResearchAreaOptions() {
		Assert.assertTrue(r4rSearchResult.getResearchAreaBox().isDisplayed(), "Research Area Box is not displayed");
		Assert.assertEquals(r4rSearchResult.getResearchAreaLabel().getText(), "Research Areas");
		List<String> researchAreaNameAsString = r4rSearchResult.getResearchAreaOptionsName();
		Assert.assertTrue(r4rSearchResult.EXP_RESEARCH_AREA_OPTIONS.equals(researchAreaNameAsString));
		logger.log(LogStatus.PASS,
				"Verify that Research Area box is present, Title of the box is Research Area and names of Research Areas as displayed in Research Areas Box");
	}

	@Test(groups = { "Smoke" })
	public void searchByResearchAreaOptions() {
		List<WebElement> researchAreaOptions = r4rSearchResult.getResearchAreaOptions();
		List<String> researchAreaNameAsString = r4rSearchResult.getResearchAreaOptionsName();
		List<String> researchAreaNumberAsString = r4rSearchResult.getResearchAreaOptionsNumber();

		for (int i = 1; i <= researchAreaOptions.size(); i++) {

			//Clicking on Research Area option 
			String researchAreaOption = researchAreaOptions.get(i - 1).getText();
			System.out.println("RESEARCH AREA OPTION: " + researchAreaOption);

			FunctionLibrary.scrollIntoview(driver, r4rSearchResult.getToolTypeBox());

			researchAreaOptions.get(i - 1).click();
			//researchAreaOptionsCheckboxes.get(i - 1).click();
			System.out.println("RESEARCH AREA CLICKED: " + researchAreaOptions.get(i - 1).getText());

			//Verify Search Results Page Title
			verifySearchResultsPageTitle();

			//Verify the URL to contain researchArea=<research area option>. This validation is complex as URL text is not same as link text
			//Assert.assertTrue(driver.getCurrentUrl().endsWith("researchArea=" + researchAreaNameAsString.get(i - 1)));
			//System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

			//Verify that the Search Box is empty
			WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
			Assert.assertTrue(ele.getAttribute("value").equals(""));

			//Verify that Your Selection field displays the research area clicked
			WebElement researchArea = driver.findElement(By.cssSelector(".selected-filters__filter>p"));
			System.out.println("Your Selection Filter: " + researchArea.getText());
			Assert.assertTrue(researchArea.getText().equals(researchAreaNameAsString.get(i - 1)));

			//Verify that Result Count text contains the same number as present in the Tool Type Option
			String resultCount = driver.findElement(By.xpath("//div[@class='r4r-pager__count  r4r-DEFAULT']"))
					.getText();
			System.out.println("Text in Result Count: " + resultCount);
			Assert.assertTrue(resultCount.contains(researchAreaNumberAsString.get(i - 1)), "Result count not matching");

			//Verify that Research Area box displays the Research Area filter as checked
			WebElement researchAreaFilter = driver.findElement(By
					.xpath("(//h4[@class='facet__title  r4r-DEFAULT' and contains(text(),'Research Areas')]/following-sibling::label[@class='facet__filter  r4r-DEFAULT'])["
							+ i + "]"));

			researchAreaFilter.getAttribute("aria-checked").equals("true");
			System.out.println("Selected Research Area Filter=======: " + researchAreaFilter.getText());
			Assert.assertTrue(researchAreaFilter.getText().equals(researchAreaOption));

			//Verify the number of results in results container after applying the Research Area filter
			if (Integer.parseInt(researchAreaNumberAsString.get(i - 1)) <= 20) {

				List<WebElement> searchResultItems = driver
						.findElements(By.xpath("//section[@class='results-container  r4r-DEFAULT']/article/h2"));
				System.out.println("Search Result H2=======: " + searchResultItems.size());
				Assert.assertEquals(searchResultItems.size(), Integer.parseInt(researchAreaNumberAsString.get(i - 1)));
			}

			driver.navigate().back();
			logger.log(LogStatus.PASS, "Verify that Search Results can be filtered based on Research Area: "
					+ researchAreaNameAsString.get(i - 1));
		}
	}

	@Test(groups = { "Smoke" })
	public void verifyResearchTypesOptions() {
		Assert.assertTrue(r4rSearchResult.getResearchTypeBox().isDisplayed(), "Research Type Box is not displayed");
		Assert.assertEquals(r4rSearchResult.getResearchTypesLabel().getText(), "Research Types");
		List<String> researchTypeNameAsString = r4rSearchResult.getResearchTypeOptionsName();
		Assert.assertTrue(r4rSearchResult.EXP_RESEARCH_TYPE_OPTIONS.equals(researchTypeNameAsString));
		logger.log(LogStatus.PASS,
				"Verify that Research Types box is present, Title of the box is Research Types and names of Research Types are as displayed in Research Types Box");
	}

	@Test(groups = { "Smoke" })
	public void searchByResearchTypeOptions() {
		List<WebElement> researchTypeOptions = r4rSearchResult.getResearchTypeOptions();
		List<String> researchTypeNameAsString = r4rSearchResult.getResearchTypeOptionsName();
		List<String> researchTypeNumberAsString = r4rSearchResult.getResearchTypeOptionsNumber();

		for (int i = 1; i <= researchTypeOptions.size(); i++) {

			//Clicking on Research Area option 
			String researchTypeOption = researchTypeOptions.get(i - 1).getText();
			System.out.println("RESEARCH TYPE OPTION: " + researchTypeOption);

			FunctionLibrary.scrollIntoview(driver, r4rSearchResult.getResearchAreaBox());
			researchTypeOptions.get(i - 1).click();
			System.out.println("RESEARCH TYPE CLICKED: " + researchTypeOptions.get(i - 1).getText());

			//Verify Search Results Page Title
			verifySearchResultsPageTitle();

			//Verify the URL to contain researchArea=<research area option>. This validation is complex as URL text is not same as link text
			//Assert.assertTrue(driver.getCurrentUrl().endsWith("researchArea=" + researchAreaNameAsString.get(i - 1)));
			//System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

			//Verify that the Search Box is empty
			WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
			Assert.assertTrue(ele.getAttribute("value").equals(""));

			//Verify that Your Selection field displays the research area clicked
			WebElement researchType = driver.findElement(By.cssSelector(".selected-filters__filter>p"));
			System.out.println("Your Selection Filter: " + researchType.getText());
			Assert.assertTrue(researchType.getText().equals(researchTypeNameAsString.get(i - 1)));

			//Verify that Result Count text contains the same number as present in the Tool Type Option
			String resultCount = driver.findElement(By.xpath("//div[@class='r4r-pager__count  r4r-DEFAULT']"))
					.getText();
			System.out.println("Text in Result Count: " + resultCount);
			Assert.assertTrue(resultCount.contains(researchTypeNumberAsString.get(i - 1)), "Result count not matching");

			driver.navigate().back();

			logger.log(LogStatus.PASS, "Verify that Search Results can be filtered based on Research Type: "
					+ researchTypeNameAsString.get(i - 1));
		}
	}

	@Test(dataProvider = "ToolSubType", groups = { "Smoke" })
	public void verifyToolSubTypesOptions(String toolType) {
		System.out.println("Tool Type ====================:" + toolType);

		String toolTypeXPath = "//label[@class='facet__filter  r4r-DEFAULT']/input[@value='" + toolType
				+ "']/ancestor::label";
		//FunctionLibrary.scrollIntoview(driver, r4rSearchResult.getSearchBox());
		WebElement toolType1 = driver.findElement(By.xpath(toolTypeXPath));
		toolType1.click();
		//Verify that Tool Sub-Type box is displayed 
		WebElement box_SubToolType = driver
				.findElement(By.xpath("//div[@class='facet__box facet__box--tool-subtypes r4r-DEFAULT']"));
		Assert.assertTrue(box_SubToolType.isDisplayed());

		//Verify that Tool Sub-Type box label is displayed
		WebElement lbl_ToolSubType = driver
				.findElement(By.xpath("//div[@class='facet__box facet__box--tool-subtypes r4r-DEFAULT']/h4"));
		Assert.assertEquals(lbl_ToolSubType.getText(), "Tool Sub-Types");

		//Verify that expected Tool Sub-Type box items are displayed
		List<WebElement> toolSubTypeItems = driver
				.findElements(By.xpath("//div[@class='facet__box facet__box--tool-subtypes r4r-DEFAULT']/label"));
		Assert.assertFalse(toolSubTypeItems.isEmpty(), "Tool Sub-Types box is empty");

		List<String> toolSubTypeNameAsString = new ArrayList<>();
		for (WebElement ele : toolSubTypeItems) {
			System.out.println("Tool Sub Type Name + Number===>" + ele.getText());
			String toolSubTypeNameWithNumber = ele.getText();
			String toolSubTypeName = toolSubTypeNameWithNumber.substring(0, toolSubTypeNameWithNumber.indexOf("("));
			System.out.println("Tool Sub Type Name==>" + toolSubTypeName);
			toolSubTypeNameAsString.add(toolSubTypeName.trim());
		}

		switch (toolType) {
		case "Analysis Tools":
			Assert.assertEquals(toolSubTypeNameAsString, r4rSearchResult.EXP_RESEARCH_SUBTYPE_OPTIONS_ANALYSIS_TOOLS);
			break;
		case "Datasets & Databases":
			Assert.assertEquals(toolSubTypeNameAsString, r4rSearchResult.EXP_RESEARCH_SUBTYPE_OPTIONS_DATASETS);
			break;
		case "Lab Tools":
			Assert.assertEquals(toolSubTypeNameAsString, r4rSearchResult.EXP_RESEARCH_SUBTYPE_OPTIONS_LAB);
			break;
		case "Clinical Research Tools":
			Assert.assertEquals(toolSubTypeNameAsString,
					r4rSearchResult.EXP_RESEARCH_SUBTYPE_OPTIONS_CLINICAL_RESEARCH);
			break;
		case "Community Research Tools":
			Assert.assertEquals(toolSubTypeNameAsString,
					r4rSearchResult.EXP_RESEARCH_SUBTYPE_OPTIONS_COMMUNITY_RESEARCH);
			break;

		}

		driver.navigate().back();
		logger.log(LogStatus.PASS,
				"Verify that Tool Sub-Types box is present, Title of the box is Tool Sub-Types and names of Tool Sub-Types are as displayed in Tool Sub-Types Box ");
	}

	/********************** Data Providers **********************/

	@DataProvider(name = "Search")
	public Iterator<Object[]> readSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "Keyword", rowNum);
			Object ob[] = { cancerType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "ToolSubType")
	public Iterator<Object[]> readToolSubTypeData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String toolSubType = excelReader.getCellData(TESTDATA_SHEET_NAME, "ToolType", rowNum);
			Object ob[] = { toolSubType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	/********************** Common Functions **********************/
	// Verify Search Results page title
	public void verifySearchResultsPageTitle() {
		String pageTitle = driver.getTitle();
		Assert.assertTrue(pageTitle.contains(r4rSearchResult.getPageTitle()));
	}

}
