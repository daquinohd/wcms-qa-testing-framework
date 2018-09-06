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
import gov.nci.clinicalTrial.pages.AdvanceSearch;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.clinicaltrials.BaseClass;

public class Resources4Researchers_Test extends BaseClass {

	public static final String TESTDATA_SHEET_NAME = "R4R";

	Resources4ResearchersHome r4rHome;
	Resources4ResearchersSearchResult r4rSearchResult;
	AdvanceSearch advanceSearch;
	BreadCrumb crumb;
	Banner banner;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("Resources4ResearchersHomeURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, config, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		r4rHome = new Resources4ResearchersHome(driver, logger);
		System.out.println("Advance Search setup done");
		crumb = new BreadCrumb(driver);
		banner = new Banner(driver);
		r4rSearchResult = new Resources4ResearchersSearchResult(driver, logger);
		testDataFilePath = config.getProperty("TestData");
	}

	@Test(groups = { "Smoke" })
	public void verifyBanner() {
		Assert.assertTrue(banner.isDisplayed());
		Assert.assertEquals(banner.getAltText(), "National Cancer Institute");
		logger.log(LogStatus.PASS, "Verifying the Banner of the page");
	}

	@Test(groups = { "Smoke" })
	public void verify_bread_crumb() {
		Assert.assertEquals(crumb.getBreadCrumbText(), r4rHome.BREAD_CRUMB);
		System.out.println("Breadcrumb is displaying correctly");
		logger.log(LogStatus.PASS, "Pass => " + "Verifying the Breadcrumb of the page");

	}

	@Test(groups = { "Smoke" })
	public void verifyr4rHomeH1Title() {
		Assert.assertEquals(r4rHome.getPageH1Title().getText(), r4rHome.R4R_PAGE_TITLE);
		logger.log(LogStatus.PASS, "Verify that H1 Title of the page is *Resources for Researchers* | Actual Result: "
				+ r4rHome.getPageH1Title());
	}

	@Test(groups = { "Smoke" })
	public void verifyLearnMoreAboutR4RLink() {
		r4rHome.clickLearnMoreAboutR4R();
		Assert.assertTrue(FunctionLibrary.waitURLToBe(driver, r4rHome.R4R_LEARN_MORE_PAGE_URL));
		driver.navigate().back();
		logger.log(LogStatus.PASS,
				"Verify that 'about r4r' page is displayed on clicking the link 'Learn more about Resources for Researchers.' | Actual Result: "
						+ driver.getCurrentUrl());
	}

	@Test(groups = { "Smoke" })
	public void verifySearchForResourcesSection() {
		Assert.assertTrue(r4rHome.getSearchForResourcesIcon().isDisplayed(),
				"Search For Resources icon is not displayed");
		Assert.assertTrue(r4rHome.getSearchForResourcesLabel().isDisplayed(),
				"Search For Resources h2 title is not displayed");
		Assert.assertEquals(r4rHome.getSearchForResourcesLabel().getText(), "Search For Resources");
		Assert.assertTrue(r4rHome.getSearchForResourcesText().isDisplayed(),
				"Search For Resources text is not displayed");
		logger.log(LogStatus.PASS, "Verify that Search For Resources icon, h2 title and text are displayed");
	}

	@Test(groups = { "Smoke" })
	public void verifyBrowseResourcesSection() {
		Assert.assertTrue(r4rHome.getBrowseResourcesIcon().isDisplayed(), "Browse Resources icon is not displayed");
		Assert.assertTrue(r4rHome.getBrowseResourcesLabel().isDisplayed(),
				"Browse Resources h2 title is not displayed");
		Assert.assertEquals(r4rHome.getBrowseResourcesLabel().getText(), "Browse Resources");
		Assert.assertTrue(r4rHome.getBrowseResourcesText().isDisplayed(), "Browse Resources text is not displayed");
		logger.log(LogStatus.PASS, "Verify that Search For Resources icon, h2 title and text are displayed");
	}

	@Test(groups = { "Smoke" })
	public void verifySearchBar() {
		Assert.assertTrue(r4rHome.getSearchBox().isDisplayed(), "Search Bar is not displayed");
		Assert.assertTrue(r4rHome.getSearchBoxInnerText().contains("Search Resources for Researchers"),
				"Search Box placeholder is not displayed/matching");
		Assert.assertTrue(r4rHome.getSearchButton().isDisplayed(), "Search Button is not displayed");
		logger.log(LogStatus.PASS,
				"Verify that Search Bar is displayed and contains placeholder text as Search Resources for Researchers");
	}

	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySearch(String keyword) {

		r4rHome.search(keyword);
		//Verify Search Results Page H1 Title
		System.out.println("Search Result Page Title: " + driver.getTitle());
		verifySearchResultsPageTitle();

		//Verify the URL to contain q=keyword
		Assert.assertTrue(driver.getCurrentUrl().endsWith("q=" + keyword));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		//Verify that the Search Box contains search keyword
		WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
		Assert.assertTrue(ele.getAttribute("value").equals(keyword));

		driver.navigate().back();
		logger.log(LogStatus.PASS,
				"Verify that when a keyword is searched, Search Result page is displayed with following validations: "
						+ "Page Title, URL contains q=keyword and Search bar displays the keyword");
	}

	@Test(groups = { "Smoke" })
	public void verifyViewAllResources() {
		String viewAllResourcesNumber = r4rHome.getViewAllResourcesNumber();
		r4rHome.clickViewAllResources();

		//Verify Search Results Page Title
		System.out.println("Search Result Page Title: " + driver.getTitle());
		verifySearchResultsPageTitle();

		//Verify the URL to contain from=0
		Assert.assertTrue(driver.getCurrentUrl().endsWith("search?from=0"));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		//Verify that the Search Box is empty
		WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
		Assert.assertTrue(ele.getAttribute("value").equals(""));

		//Verify that Result Count text contains the same number as present in the View All Resources Link
		String resultCount = driver.findElement(By.xpath("//div[@class='r4r-pager__count  r4r-DEFAULT']")).getText();
		System.out.println("Text in Result Count: " + resultCount);
		Assert.assertTrue(resultCount.contains(viewAllResourcesNumber), "Result count not matching");
		driver.navigate().back();
		logger.log(LogStatus.PASS,
				"Verify that on clicking the View All Resources link displays Search Result page with following validations: "
						+ "Page Title, " + "URL contains from=0, " + "Search bar is empty, "
						+ "Result count contains the same number as present in the link ");
	}

	@Test(groups = { "Smoke" })
	public void verifyToolTypesOptions() {
		Assert.assertTrue(r4rHome.getBrowseByToolTypeSection().isDisplayed(), "Tool Types Box is not displayed");
		Assert.assertEquals(r4rHome.getBrowseByToolTypeLabel().getText(), "Tool Type");
		List<String> toolTypeNameAsString = r4rHome.getToolTypesOptionsName();
		Assert.assertTrue(r4rHome.EXP_TOOL_TYPE_OPTIONS.equals(toolTypeNameAsString));
		logger.log(LogStatus.PASS,
				"Verify that Tool Type box is present, Title of the box is Tool Type and names of Tool Types as displayed in Tool Types Box | Actual Result: "
						+ toolTypeNameAsString);
	}

	@Test(groups = { "Smoke" })
	public void searchByToolTypesOptions() {
		List<WebElement> toolTypeOptions = r4rHome.getToolTypeOptions();
		List<String> toolTypeNameAsString = r4rHome.getToolTypesOptionsName();
		List<String> toolTypeNumberAsString = r4rHome.getToolTypesOptionsNumber();

		for (int i = 1; i <= toolTypeOptions.size(); i++) {

			//Clicking on tool type option 
			System.out.println("Tool Type Option Size=============" + toolTypeOptions.get(i - 1).getText());
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
			System.out.println("Selected Filter: " + toolType.getText());
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

			driver.navigate().back();
		}

	}

	@Test(groups = { "Smoke" })
	public void searchByResearchAreaOptions() {
		List<WebElement> researchAreaOptions = r4rHome.getResearchAreaOptions();
		List<String> researchAreaNameAsString = r4rHome.getResearchAreaOptionsName();
		List<String> researchAreaNumberAsString = r4rHome.getResearchAreaOptionsNumber();

		for (int i = 1; i <= researchAreaOptions.size(); i++) {

			//Clicking on Research Area option 
			String researchAreaOption = researchAreaOptions.get(i - 1).getText();
			System.out.println("RESEARCH AREA OPTION: " + researchAreaOption);
			researchAreaOptions.get(i - 1).click();

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
			System.out.println("Selected Filter: " + researchArea.getText());
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
			driver.navigate().back();
		}
	}

	@Test(groups = { "Smoke" })
	public void verifyResearchAreaOptions() {
		Assert.assertTrue(r4rHome.getBrowseByResearchAreaBox().isDisplayed(), "Research Area Box is not displayed");
		Assert.assertEquals(r4rHome.getBrowseByResearchAreaLabel().getText(), "Research Area");
		List<String> researchAreaNameAsString = r4rHome.getResearchAreaOptionsName();
		Assert.assertTrue(r4rHome.EXP_RESEARCH_AREA_OPTIONS.equals(researchAreaNameAsString));
		logger.log(LogStatus.PASS,
				"Verify that Research Area box is present, Title of the box is Research Area and names of Research Areas as displayed in Tool Types Box are:"
						+ r4rHome.EXP_RESEARCH_AREA_OPTIONS + "| Actual Result: " + researchAreaNameAsString);
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

	/********************** Common Functions **********************/
	// Verify Search Results page title
	public void verifySearchResultsPageTitle() {
		String pageTitle = driver.getTitle();
		Assert.assertTrue(pageTitle.contains(r4rSearchResult.getPageTitle()));
	}

}
