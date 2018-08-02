package gov.nci.clinicaltrials;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import com.relevantcodes.extentreports.LogStatus;

public class AdvanceSearchResults_Test extends BaseClass {

	AdvanceSearchResults advanceSearchResults;
	BreadCrumb crumb;
	

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("ResultsPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);

		try {
			SuppressChatPromptPageObject chatBlock = new SuppressChatPromptPageObject(driver, null);
			advanceSearchResults = new AdvanceSearchResults(driver, chatBlock);
		} catch (Exception e) {
			advanceSearchResults = null;
			logger.log(LogStatus.ERROR, "Error creating Advanced Search page.");
		}

		System.out.println("Advance Search Results setup done");
		crumb = new BreadCrumb(driver);
		
	}

	// TODO: Move all page banner verifications to a single test class.
	@Test(groups = { "Smoke", "current" })
	public void verifyBanner() {

		Banner banner = new Banner(driver);

		Assert.assertTrue(banner.isDisplayed(), "Banner is not visible.");
		Assert.assertEquals(banner.getAltText(), "National Cancer Institute", "Banner alt-text is mismatched.");

		logger.log(LogStatus.PASS, "Verifying the Banner of the page");
	}

	@Test(groups = { "Smoke", "current" })
	public void verify_bread_crumb() {
		crumb.getBreadCrumbText();
		Assert.assertEquals(crumb.getBreadCrumbText(), AdvanceSearchResults.BREAD_CRUMB);
		System.out.println("Breadcrumb is displaying correctly");
		logger.log(LogStatus.PASS, "Pass => " + "Verifying the Breadcrumb of the page");
	}

	//@Test(groups = { "Smoke" })
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

	//@Test(groups = { "Smoke" })
	public void verify_PrintWithTrialSelection() throws InterruptedException {
		System.out.println("**************Executing Print with Trial Selection");
		Thread.sleep(500);
		advanceSearchResults.clickOnSelectAllCheckBox();
		// advanceSearchResults.clickOnCheckBox();
		advanceSearchResults.clickPrintButton();
		Thread.sleep(2000);
		String currentPageURL = driver.getCurrentUrl();
		System.out.println("Current Page URL: " + currentPageURL);
		Assert.assertTrue(currentPageURL.contains("/CTS.Print/Display?printid="));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='clinical-trials-print']")).isDisplayed());
		Thread.sleep(500);

		logger.log(LogStatus.PASS,
				"Verifying that Print page is displayed when Print Selected button is clicked after selecting all trials");
	}

	//@Test(groups = { "Smoke" })
	public void verify_PrintWithOneTrialSelection() throws InterruptedException {
		System.out.println("**************Executing Print with One Trial Selection");
		driver.get(pageURL);
		Thread.sleep(500);
		advanceSearchResults.getCheckBoxesNumber();
		Thread.sleep(1000);
		advanceSearchResults.clickOnCheckBox();
		String resultLinkText = advanceSearchResults.getResultsLinks().get(0).getText();
		System.out.println(
				"Result text on Search Result Page: " + advanceSearchResults.getResultsLinks().get(0).getText());
		Thread.sleep(1000);
		// advanceSearchResults.clickOnCheckBox();
		advanceSearchResults.clickPrintButton();
		Thread.sleep(2000);
		String currentPageURL = driver.getCurrentUrl();
		System.out.println("Current Page URL: " + currentPageURL);
		Assert.assertTrue(currentPageURL.contains("/CTS.Print/Display?printid="));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='clinical-trials-print']")).isDisplayed());

		String resultTestOnPrintPage = driver.findElement(By.xpath("//h2")).getText();
		System.out.println("Result text on Print Page: " + resultTestOnPrintPage);
		Assert.assertTrue(resultTestOnPrintPage.contains(resultLinkText), "Result test not matching on print page");
		logger.log(LogStatus.PASS,
				"Verifying that Print page is displayed when Print Selected button is clicked after selecting all trials");
	}

}
