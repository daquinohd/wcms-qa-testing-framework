package gov.nci.Resources4Researchers.Tests;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Resources4Researchers.Resources4ResearchersHome;
import gov.nci.Resources4Researchers.Resources4ResearchersResourceDetailPage;
import gov.nci.Resources4Researchers.Resources4ResearchersSearchResult;
import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.FunctionLibrary;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.testcases.BaseClass;

public class Resources4ResearchersResourceDetailPage_Test extends BaseClass {
	// public static final String TESTDATA_SHEET_NAME = "AdvanceSearch";
	// public static final String API_REFERENCE_H3 = "The Clinical Trials API:
	// Use our data to power your own clinical trial search";

	Resources4ResearchersHome r4rHome;
	Resources4ResearchersSearchResult r4rSearchResult;
	Resources4ResearchersResourceDetailPage r4rResourceDetail;
	BreadCrumb crumb;
	Banner banner;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("Resources4ResearchersResourceDetailPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		r4rResourceDetail = new Resources4ResearchersResourceDetailPage(driver, logger);
		System.out.println("R4R ResourceDetail Page setup done");
		crumb = new BreadCrumb(driver);
		banner = new Banner(driver);
		r4rSearchResult = new Resources4ResearchersSearchResult(driver, logger);
		testDataFilePath = config.getProperty("TestData");
	}

	// @Test(groups = { "Smoke" })
	/*
	 * public void verifyr4rHomeH1Title() {
	 * Assert.assertEquals(r4rHome.getPageH1Title().getText(),
	 * r4rHome.R4R_PAGE_TITLE); logger.log(LogStatus.PASS,
	 * "Verify that H1 Title of the page is *Resources for Researchers* | Actual Result: "
	 * + r4rHome.getPageH1Title()); }
	 */

	// @Test(groups = { "Smoke" })
	public void verifyResources4ResearchersHome() {
		Assert.assertTrue(r4rResourceDetail.getResources4ResearchersHome().isDisplayed());
		// Assert.assertTrue(FunctionLibrary.waitURLToBe(driver,
		// r4rHome.R4R_LEARN_MORE_PAGE_URL));
		logger.log(LogStatus.PASS,
				"Verify that there is a link titled as 'Resources for Researchers Home' is displayed on the Resource Detail page ");
		r4rResourceDetail.clickResources4ResearchersHome();
		Assert.assertTrue(FunctionLibrary.waitURLToBe(driver, r4rResourceDetail.R4R_HOME_PAGE_URL));
		logger.log(LogStatus.PASS,
				"Verify that 'Resources for Researchers Home' link correctly redirects to R4R homepage");
	}

	// @Test(groups = { "Smoke" })
	public void verifyBackToResult() {
		Assert.assertFalse(r4rResourceDetail.getBacktoResult().isDisplayed());
		// Assert.assertTrue(FunctionLibrary.waitURLToBe(driver,
		// r4rHome.R4R_LEARN_MORE_PAGE_URL));
		logger.log(LogStatus.PASS,
				"Verify that there is Back to result link is not displayed on the Resource Detail page ");
	}

	// @Test(groups = { "Smoke" })
	public void verifyVisitResource() {
		Assert.assertFalse(r4rResourceDetail.getVisitResource().isDisplayed());
		logger.log(LogStatus.PASS,
				"Verify that there is Back to result link is not displayed on the Resource Detail page ");
	}

	// @Test(groups = { "Smoke" })
	/*
	 * public void verifyViewAllResources() { r4rHome.clickViewAllResources();
	 * 
	 * // Verify Search Results Page H1 Title verifySearchResultsPageTitle();
	 * 
	 * // Verify the URL to contain from=0
	 * Assert.assertTrue(driver.getCurrentUrl().endsWith("search?from=0"));
	 * 
	 * r4rSearchResult.clickResources4ResearchersHome(); }
	 * 
	 * 
	 * @Test(groups = { "Smoke" }) public void verifyToolTypesOptions() {
	 * r4rHome.compareToolTypesOptions();
	 * 
	 * List<WebElement> allElements = r4rHome.getToolTypeOptions(); List<String>
	 * actualTooltips = new ArrayList<>(); for (WebElement ele : allElements) {
	 * System.out.println("Name + Number===>" + ele.getText());
	 * actualTooltips.add(ele.getText());
	 * 
	 * String s = ele.getText(); s = s.substring(0, s.indexOf("("));
	 * 
	 * // s = s.substring(s., s.indexOf("(")); // s = s.substring(s.indexOf("(")
	 * + 1, s.indexOf(")")); // System.out.println("Number==>" + s);
	 * System.out.println("Name==>" + s); }
	 * 
	 * boolean areTooltipsAsExpected =
	 * r4rHome.TOOL_TYPE_OPTIONS.equals(actualTooltips);
	 * Assert.assertTrue(areTooltipsAsExpected);
	 * 
	 * }
	 */

	/*
	 * @Test(dataProvider = "CancerType_SubType_Stage", groups = { "Smoke" },
	 * priority = 2) public void advancesearch_CancerType_SubType_Stage(String
	 * cancerTypeId, String cancerType, String cancerSubTypeId, String
	 * cancerSubType, String cancerStageId, String cancerStage) throws
	 * InterruptedException { Object[][] data; Thread.sleep(50);
	 * 
	 * advanceSearch.advSearch_CancerType_SubType_Stage(cancerType,
	 * cancerSubType, cancerStage);
	 * 
	 * // Verify page title verifyAdvanceSearchResultsPageTitle();
	 * 
	 * // Verify search criteria in URL String pageURL = driver.getCurrentUrl();
	 * System.out.
	 * println("advancesearch_CancerType_SubType_Stage: Current Page URL: " +
	 * pageURL); Assert.assertTrue(pageURL.contains("t=" + cancerTypeId + "&st="
	 * + cancerSubTypeId + "&stg=" + cancerStageId)); System.out.
	 * println("advancesearch_CancerType_SubType_Stage: Cancer Type ID: t=" +
	 * cancerTypeId + "&st=" + cancerSubTypeId + "&stg=" + cancerStageId);
	 * 
	 * // Verify that show search criteria table contains the selected search //
	 * criteria data = verifySearchCriteriaTable();
	 * 
	 * Assert.assertEquals(data[1][1], cancerType, "CancerType not matched");
	 * Assert.assertEquals(data[2][1], cancerSubType,
	 * "CancerSubType not matched"); Assert.assertEquals(data[3][1],
	 * cancerStage, "CancerStage not matched");
	 * 
	 * driver.findElement(By.linkText("Start Over")).click();
	 * logger.log(LogStatus.PASS,
	 * "Verify Search by Cancer Type, SubType and Stage on Advanced CTS. CancerType = "
	 * + cancerType + " , CancerSubType = " + cancerSubType +
	 * " , CancerStage = " + cancerStage);
	 * 
	 * }
	 */
	/********************** Data Providers **********************/

	/*
	 * @DataProvider(name = "CancerType_SubType") public Iterator<Object[]>
	 * readCancerType_SubType_Data() { ExcelManager excelReader = new
	 * ExcelManager(testDataFilePath);
	 * 
	 * ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
	 * 
	 * for (int rowNum = 2; rowNum <=
	 * excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) { String
	 * cancerTypeId = excelReader.getCellData(TESTDATA_SHEET_NAME,
	 * "CancerTypeId", rowNum); String cancerType =
	 * excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerType", rowNum);
	 * String cancerSubTypeId = excelReader.getCellData(TESTDATA_SHEET_NAME,
	 * "CancerSubTypeId", rowNum); String cancerSubType =
	 * excelReader.getCellData(TESTDATA_SHEET_NAME, "CancerSubType", rowNum);
	 * Object ob[] = { cancerTypeId, cancerType, cancerSubTypeId, cancerSubType
	 * };
	 * 
	 * myObjects.add(ob);
	 * 
	 * } return myObjects.iterator();
	 * 
	 * }
	 */
	/********************** Common Functions **********************/
	// Verify Search Results page title
	public void verifySearchResultsPageTitle() {
		String pageTitle = driver.getTitle();
		System.out.println("R4R Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains(r4rSearchResult.getPageTitle()));
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
