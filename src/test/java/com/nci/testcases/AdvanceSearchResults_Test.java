package com.nci.testcases;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserFactory;
import com.nci.clinicalTrial.pages.AdvanceSearchResults;
import com.nci.commonobjects.Banner;
import com.nci.commonobjects.BreadCrumb;
import com.relevantcodes.extentreports.LogStatus;

public class AdvanceSearchResults_Test extends BaseClass {

	AdvanceSearchResults advanceSearchResults;
	BreadCrumb crumb;
	Banner banner;

	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("AdvanceSearchResultsPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserFactory.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// basicSearch = PageFactory.initElements(driver, BasicSearch.class);
		advanceSearchResults = new AdvanceSearchResults(driver);
		System.out.println("Advance Search Results setup done");
		crumb = new BreadCrumb(driver);
		banner = new Banner(driver);
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verifyBanner() {
		banner.getBanner();
		logger.log(LogStatus.PASS, "Verifying the Banner of the page");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verify_bread_crumb() {
		crumb.getBreadCrumb(AdvanceSearchResults.BREAD_CRUMB);
		logger.log(LogStatus.PASS, "Verifying the Breadcrumb of the page");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void verify_PrintWithoutTrialSelection() {
		advanceSearchResults.clickPrintButton();
		// To see if the warning pop up presents
		WebElement dialog = driver.findElement(
				By.xpath("//div[@class='ui-dialog ui-corner-all ui-widget ui-widget-content ui-front cts-dialog']"));
		Assert.assertTrue(dialog.isDisplayed(), "Error dialog is not displayed");
		WebElement dialogContent = driver.findElement(By.xpath("//div[@id='modal-none']"));
		Assert.assertTrue(dialogContent.getText()
				.contains("You have not selected any trials. Please select at least one trial to print."));
		System.out.println("Attribute: " + dialogContent.getText());
		WebElement CloseButton = driver.findElement(By.xpath(
				"//button[@class='ui-dialog-close ui-button ui-corner-all ui-widget ui-button-icon-only btn-close-top']"));
		CloseButton.click();
		logger.log(LogStatus.PASS,
				"Verifying the warning popup which is displayed when Print Button is clicked without selecting any trial");
	}

	@Test(groups = { "Smoke" }, priority = 2)
	public void verify_PrintWithTrialSelection() throws InterruptedException {
		Thread.sleep(500);
		advanceSearchResults.clickOnSelectAllCheckBox();
		// advanceSearchResults.clickOnCheckBox();
		advanceSearchResults.clickPrintButton();
		Thread.sleep(1000);
		String currentPageURL = driver.getCurrentUrl();
		System.out.println("Current Page URL: " + currentPageURL);
		Assert.assertTrue(currentPageURL.contains("https://www.cancer.gov/CTS.Print/Display?printid="));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='clinical-trials-print']")).isDisplayed());
		logger.log(LogStatus.PASS,
				"Verifying that Print page is displayed when Print Selected button is clicked after selecting all trials");
	}

	// @Test(groups = { "Smoke" }, priority = 2)
	public void verify_PrintWithOneTrialSelection() throws InterruptedException {
		Thread.sleep(500);
		advanceSearchResults.getCheckBoxesNumber();
		advanceSearchResults.clickOnCheckBox();
		Thread.sleep(1000);
		// advanceSearchResults.clickOnCheckBox();
		advanceSearchResults.clickPrintButton();
		Thread.sleep(1000);
		String currentPageURL = driver.getCurrentUrl();
		System.out.println("Current Page URL: " + currentPageURL);
		Assert.assertTrue(currentPageURL.contains("https://www.cancer.gov/CTS.Print/Display?printid="));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='clinical-trials-print']")).isDisplayed());
		logger.log(LogStatus.PASS,
				"Verifying that Print page is displayed when Print Selected button is clicked after selecting all trials");
	}

}
