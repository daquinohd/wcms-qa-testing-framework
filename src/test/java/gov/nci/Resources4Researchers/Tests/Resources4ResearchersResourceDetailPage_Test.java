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
import gov.nci.Resources4Researchers.Resources4ResearchersResourceDetailPage;
import gov.nci.Resources4Researchers.Resources4ResearchersSearchResult;
import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.Utilities.FunctionLibrary;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;

public class Resources4ResearchersResourceDetailPage_Test extends BaseClass {

	public static final String TESTDATA_SHEET_NAME = "R4R";

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
		testDataFilePath = config.getProperty("TestDataR4R");
	}

	@Test(groups = { "Smoke" })
	public void verifyResourceDetailPageTitle() {
		Assert.assertTrue(r4rResourceDetail.getPageH1Title().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that Title of the Resource Detail page is displayed | Actual Result: "
				+ r4rResourceDetail.getPageH1Title());
	}

	@Test(groups = { "Smoke" })
	public void verifyResources4ResearchersHome() {
		Assert.assertTrue(r4rResourceDetail.getResources4ResearchersHome().isDisplayed());
		logger.log(LogStatus.PASS,
				"Verify that there is a link titled as 'Resources for Researchers Home' is displayed on the Resource Detail page ");
		r4rResourceDetail.clickResources4ResearchersHome();
		Assert.assertTrue(FunctionLibrary.waitURLToBe(driver, r4rResourceDetail.R4R_HOME_PAGE_URL));
		driver.navigate().to(pageURL);
		logger.log(LogStatus.PASS,
				"Verify that 'Resources for Researchers Home' link correctly redirects to R4R homepage");
	}

	@Test(groups = { "Smoke" })
	public void verifyBackToResult() {
		Assert.assertFalse(driver.getPageSource().contains("Back to results"));
		// Assert.assertNull(r4rResourceDetail.getBacktoResult());
		// Assert.assertTrue(FunctionLibrary.waitURLToBe(driver,
		// r4rHome.R4R_LEARN_MORE_PAGE_URL));
		logger.log(LogStatus.PASS,
				"Verify that there is Back to result link is not displayed on the Resource Detail page ");

	}

	@Test(groups = { "Smoke" })
	public void verifyBackToResultReturnfromSearchresult() {
		driver.navigate().to(config.getProperty("Resources4ResearchersSearchResultURL"));
		r4rSearchResult.getResultItems().get(0).click();
		Assert.assertTrue(driver.getPageSource().contains("Back to results"));
		logger.log(LogStatus.PASS,
				"Verify that there is Back to result link is displayed on the Resource Detail page ");
		r4rResourceDetail.clickBacktoResult();
		Assert.assertEquals(driver.getCurrentUrl(), config.getProperty("Resources4ResearchersSearchResultURL"));
		logger.log(LogStatus.PASS, "Verify that Back to result link displays the Search Results page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyVisitResource() {
		Assert.assertTrue(r4rResourceDetail.getVisitResource().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that there is Visit Resource link displayed on the Resource Detail page ");
		Assert.assertTrue(r4rResourceDetail.getVisitResource().isEnabled());
		logger.log(LogStatus.PASS, "Verify that Visit Resource link is clickable on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyResourceAccessImage() {
		Assert.assertTrue(r4rResourceDetail.getResourceAccessImage().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that there is Resource Access Image displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyResourceAccessText() {
		Assert.assertTrue(r4rResourceDetail.getResourceAccessText().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that there is Resource Access Text displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyContactInfoTitle() {
		Assert.assertTrue(r4rResourceDetail.getContactInfoTitle().isDisplayed());
		Assert.assertEquals(r4rResourceDetail.getContactInfoTitle().getText(), "Contact Information");
		logger.log(LogStatus.PASS, "Verify that there is Contact Info Title displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyContactInfoDetail() {
		Assert.assertTrue(r4rResourceDetail.getContactInfoDetail().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that there is Contact Info Details displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyNCIAffiliationTitle() {
		Assert.assertTrue(r4rResourceDetail.getNCIAffliationTitle().isDisplayed());
		Assert.assertEquals(r4rResourceDetail.getNCIAffliationTitle().getText(), "NCI Affiliation");
		logger.log(LogStatus.PASS, "Verify that there is NCI Affiliation Title displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyNCIAffiliationDetail() {
		Assert.assertTrue(r4rResourceDetail.getNCIAffliationDetail().isDisplayed());
		Assert.assertTrue(r4rResourceDetail.getNCIAffliationDetail().getText().contains("This resource is managed by"));
		logger.log(LogStatus.PASS, "Verify that there is NCI Affiliation text displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyEmailIcon() {
		Assert.assertTrue(r4rResourceDetail.getEmailIcon().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that there is Email share icon displayed on the Resource Detail page ");
	}

	@Test(groups = { "Smoke" })
	public void verifyTwitterIcon() {
		Assert.assertTrue(r4rResourceDetail.getTwitterIcon().isDisplayed());
		logger.log(LogStatus.PASS, "Verify that there is Twitter share icon displayed on the Resource Detail page ");
	}

	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySearchbyClick(String keyword) {

		System.out.println("Search Keyword: " + keyword);
		r4rResourceDetail.searchbyClick(keyword);

		// Verify Search Results Page H1 Title
		verifySearchResultsPageTitle();

		// Verify the URL to contain q=keyword
		Assert.assertTrue(driver.getCurrentUrl().endsWith("q=" + keyword));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		// Verify that the Search Box contains search keyword
		WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
		Assert.assertTrue(ele.getAttribute("value").equals(keyword));

		// Verify the message on the screen when search is done for keyword
		// having no result Eg-Terminology
		if (keyword.equalsIgnoreCase("Terminology")) {
			WebElement resultText = driver.findElement(By.xpath("//div[@class='results__main  r4r-DEFAULT']/div"));
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

	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySearchbyENTER(String keyword) {

		System.out.println("Search Keyword: " + keyword);
		r4rResourceDetail.searchbyEnter(keyword);

		// Verify Search Results Page H1 Title
		verifySearchResultsPageTitle();

		// Verify the URL to contain q=keyword
		Assert.assertTrue(driver.getCurrentUrl().endsWith("q=" + keyword));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		// Verify that the Search Box contains search keyword
		WebElement ele = driver.findElement(By.xpath("//form[@class='searchbar__container  cancer-gov']/input"));
		Assert.assertTrue(ele.getAttribute("value").equals(keyword));

		// Verify the message on the screen when search is done for keyword
		// having no result Eg-Terminology
		if (keyword.equalsIgnoreCase("Terminology")) {
			WebElement resultText = driver.findElement(By.xpath("//div[@class='results__main  r4r-DEFAULT']/div"));
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
	public void searchByRelatedResourcesItems() {
		List<WebElement> relatedResource = r4rResourceDetail.getRelatedResources();

		for (int i = 0; i <= relatedResource.size() - 1; i++) {

			// Clicking on Related Resource
			String researchArea = relatedResource.get(i).getText();
			System.out.println("RELATED RESOURCE DISPLAYED: " + researchArea);
			relatedResource.get(i).click();
			System.out.println("RELATED RESOURCE CLICKED: " + researchArea);

			// Verify Search Results Page Title
			verifySearchResultsPageTitle();
			// driver.navigate().to(pageURL);
		}

	}

	/********************** Data Providers **********************/

	@DataProvider(name = "Search")
	public Iterator<Object[]> readSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "SearchText", rowNum);
			Object ob[] = { cancerType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	/********************** Common Functions **********************/
	// Verify Search Results page title
	public void verifySearchResultsPageTitle() {
		String pageTitle = driver.getTitle();
		System.out.println("R4R Search Results Page Title: " + pageTitle);
		Assert.assertTrue(pageTitle.contains(r4rSearchResult.getPageTitle()));
	}
}
