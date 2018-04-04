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

import com.nci.Utilities.BrowserManager;
import com.nci.clinicalTrial.pages.Analytics;
import com.relevantcodes.extentreports.LogStatus;

public class Analytics_Test extends BaseClass {

	// WebDriver driver;
	Analytics analytics;

	// ConfigReader config = new ConfigReader();

	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("AdvanceSearchPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		analytics = new Analytics(driver);
		System.out.println("Analytics setup done");
	}

/*	@Test(groups = { "ClickEvents" }, priority = 1)
	public void verifyMegaMenuClick() {
		Assert.assertEquals(analytics.clickMegaMenu(), 
		advanceSearch.getAdvanceSearchPageTitle(), AdvanceSearch.ADVANCE_SEARCH_PAGE_TITLE);
		logger.log(LogStatus.PASS,
				"Verifying the Title of the page is *Find NCI-Supported Clinical Trials* | Actual Result: "
				+ advanceSearch.getAdvanceSearchPageTitle());
	}
*/

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
