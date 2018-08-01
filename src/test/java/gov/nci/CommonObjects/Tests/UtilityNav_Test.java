package gov.nci.CommonObjects.Tests;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.commonobjects.UtilityNav;
import gov.nci.testcases.BaseClass;

public class UtilityNav_Test extends BaseClass {

	UtilityNav utilityNav;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("HomePageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		utilityNav = new UtilityNav(driver, logger);
	}

	@Test(groups = { "Smoke" })
	public void verifyUtilityNavBar() {
		Assert.assertTrue(utilityNav.getUtilityNavBar().isDisplayed());
		logger.log(LogStatus.PASS, "Verify the Utility Nav Bar of the page");
	}

	@Test(groups = { "Smoke" })
	public void verifyContact() {
		Assert.assertTrue(utilityNav.getContactLink().isDisplayed());
		Assert.assertTrue(utilityNav.getContactLink().getText().equals("1-800-4-CANCER"));

		utilityNav.clickContactLink();

		String actualPageUrl = driver.getCurrentUrl();
		String expected_PageUrl = pageURL + utilityNav.CONTACT_PAGE_URL;
		Assert.assertEquals(actualPageUrl, expected_PageUrl);

		driver.navigate().back();
		logger.log(LogStatus.PASS, "Verify the Contact link of the page");
	}

	@Test(groups = { "Smoke" })
	public void verifyPublications() {
		Assert.assertTrue(utilityNav.getPublications().isDisplayed());
		Assert.assertTrue(utilityNav.getPublications().getText().equals("Publications"));

		utilityNav.clickPublications();
		;

		String actualPageUrl = driver.getCurrentUrl();
		String expected_PageUrl = pageURL + utilityNav.PUBLICATIONS_PAGE_URL;
		Assert.assertEquals(actualPageUrl, expected_PageUrl);

		driver.navigate().back();
		logger.log(LogStatus.PASS, "Verify the Publications link of the page");
	}

	@Test(groups = { "Smoke" })
	public void verifyDictionary() {
		Assert.assertTrue(utilityNav.getUtilityDictionary().isDisplayed());
		Assert.assertTrue(utilityNav.getUtilityDictionary().getText().equals("Dictionary"));

		utilityNav.clickUtilityDictionary();

		String actualPageUrl = driver.getCurrentUrl();
		String expected_PageUrl = pageURL + utilityNav.DICTIONARY_PAGE_URL;
		Assert.assertEquals(actualPageUrl, expected_PageUrl);

		driver.navigate().back();
		logger.log(LogStatus.PASS, "Verify the Dictionary link of the page");
	}

	@Test(groups = { "Smoke" })
	public void verifyLiveChat() {
		Assert.assertTrue(utilityNav.getLiveChat().isDisplayed());
		Assert.assertTrue(utilityNav.getLiveChat().getText().equals("Live Chat"));

		String mainWindowHandle = driver.getWindowHandle();
		utilityNav.clickLiveChat();

		Set s = driver.getWindowHandles();
		Iterator ite = s.iterator();
		while (ite.hasNext()) {
			String popupHandle = ite.next().toString();
			if (!popupHandle.contains(mainWindowHandle)) {
				driver.switchTo().window(popupHandle);
				String actualPageUrl = driver.getCurrentUrl();
				String expected_PageUrl = utilityNav.LIVECHAT_PAGE_URL;
				System.out.println("Live CHat: " + actualPageUrl);
				Assert.assertEquals(actualPageUrl, expected_PageUrl);
				driver.switchTo().window(mainWindowHandle).getWindowHandle();
				//driver.close();
			}
		}

		//driver.navigate().back();
		logger.log(LogStatus.PASS, "Verify the Live Chat link of the page");
	}

}
