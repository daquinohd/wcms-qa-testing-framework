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
import com.relevantcodes.extentreports.LogStatus;
import gov.nci.WebAnalytics.AnalyticsLoad;

public class Analytics_Test extends BaseClass {

	// WebDriver driver;
	AnalyticsLoad analytics;

	// ConfigReader config = new ConfigReader();

	@BeforeClass(groups = { "Smoke" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("HomePage");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		analytics = new AnalyticsLoad(driver);
		System.out.println("Analytics setup done");
	}

	@Test(groups = { "Smoke" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = analytics.getSAccountText();
		//System.out.println("s_account element: " + sAccountBlob);
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoad.S_ACCOUNT));
		logger.log(LogStatus.INFO, "s_account element: " + sAccountBlob);
		logger.log(LogStatus.PASS, "The page contains the s_account variable.");		
	}

}
