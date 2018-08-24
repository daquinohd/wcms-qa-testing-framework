package gov.nci.clinicaltrials;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicalTrial.common.CriteriaList;
import gov.nci.clinicalTrial.pages.SearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;

import com.relevantcodes.extentreports.LogStatus;

/**
 * Tests for the Clinical Trial Search results page.
 * NOTE: Basic and advanced search use the same results page, so there's only one class.
 */
public class SearchResults_Test extends BaseClass {


	SearchResults basicSearchResults;
	String testDataFilePath;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "browser" })
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = "about:blank";
		driver = BrowserManager.startBrowser(browser, config, pageURL);

		try {
			// Create search page with chat prompt suppressed.
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			basicSearchResults = new SearchResults(driver, chatPrompt);
		} catch (Exception e) {
			basicSearchResults = null;
			logger.log(LogStatus.ERROR, "Error creating Basic Search results page.");
		}
		System.out.println("Basic search results setup done");
		testDataFilePath = config.getProperty("ClinicalTrialData");
	}

	/**
	 * This is the test to use for verifying that a single criterion is displayed on
	 * the search results page. Items to be tested appear on the Criteria Display of
	 * the clinical-trials.xlsx data file.
	 */
	@Test(dataProvider = "LoadCriterionTestData")
	public void VerifyCriteriaDisplay(String url, String label, String criterion, String description) {

		driver.get(url);
		try {
			SearchResults page = new SearchResults(driver);
			page.ShowSearchCriteria();
			CriteriaList criteria = page.GetSearchCriteria();

			System.out.println("Contains '" + label + "': " + criteria.ContainsCriterion(label));
			
			Assert.assertTrue(criteria.ContainsCriterion(label),
				String.format("No criteria entry for label '%s' in test '%s'.", label, description));

			CriteriaList.Criterion entry = criteria.GetCriterion(label);
			Assert.assertEquals(entry.getLabel(), label, 
				String.format("Label text mismatch in test '%s'.", description));
			Assert.assertEquals(entry.getText(), criterion, 
				String.format("Criterion text mismatch in test '%s'.", description));

		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail(String.format("Error parsing page URL '%s'.", url));
		}
	}

	/**
	 * Retrieve data for testing the criteria display of on the search results page.
	 * 
	 * All columns are required to be present:
	 * 
	 * result URL - link to the search results page to be tested.
	 * expected label - how the criterion is expected to be labeled.
	 * expected text - the expected text of the criterion.
	 * description - A description of the test.
	 * 
	 */
	@DataProvider(name = "LoadCriterionTestData")
	public Iterator<Object[]> LoadCriterionTestData(){


		String TESTDATA_SHEET_NAME = "Criteria Display";

		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++)  {

			String url = excelReader.getCellData(TESTDATA_SHEET_NAME, "result URL", rowNum);
			String label = excelReader.getCellData(TESTDATA_SHEET_NAME, "expected label", rowNum);
			String criterion = excelReader.getCellData(TESTDATA_SHEET_NAME, "expected text", rowNum);
			String description = excelReader.getCellData(TESTDATA_SHEET_NAME, "description", rowNum);

			Object obj[] = { url, label, criterion, description };

			myObjects.add(obj);
		}

		return myObjects.iterator();
	}



	//@Test(groups = { "Smoke" })
	// TODO: Reinstate as discrete tests.
	public void clickPrint() {
		String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		basicSearchResults.selectCheckBox();
		basicSearchResults.clickPrint();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Checking the Page Title
		String pageTitle = driver.getTitle();
		System.out.println("Actual Page Title:" + pageTitle);
		Assert.assertTrue(driver.getCurrentUrl().contains("/CTS.Print/Display?printid="));
		Assert.assertTrue(driver.findElement(By.className("printIntroText")).getText().contains(fileName));
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logger.log(LogStatus.PASS, "Pass => " + "Verify Print page on Basic Search on Basic CTS");

	}

}
