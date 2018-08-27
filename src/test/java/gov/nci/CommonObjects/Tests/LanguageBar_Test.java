package gov.nci.CommonObjects.Tests;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.commonobjects.LanguageBar;
import gov.nci.clinicaltrials.BaseClass;

public class LanguageBar_Test extends BaseClass {

	LanguageBar languageBar;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("HomePageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		languageBar = new LanguageBar(driver, logger);
	}

	@Test(groups = { "Smoke" })
	public void verifyLanguageBar() {
		Assert.assertTrue(languageBar.getLanguageBar().isDisplayed());
		logger.log(LogStatus.PASS, "Verify the Language Bar of the page");
	}

	@Test(groups = { "Smoke" })
	public void verifyLanguageToggle() {
		languageBar.clickLanguageToggle();
		String espanolPageUrl = driver.getCurrentUrl();
		String exp_PageUrl = pageURL + languageBar.ESPANOL_PAGE_URL;
		Assert.assertEquals(espanolPageUrl, exp_PageUrl);

		WebElement languageToggle = driver.findElement(By.xpath("//div[@id='LangList1']/ul/li/a"));
		Assert.assertEquals(languageToggle.getText(), "English");

		languageToggle.click();
		String englishPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(englishPageUrl, pageURL);
		logger.log(LogStatus.PASS, "Verify the Language Toggle of the page");
	}

}
