package com.nci.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserManager;
import com.nci.Utilities.ConfigReader;
import com.nci.clinicalTrial.pages.BasicSearchResults;
import com.relevantcodes.extentreports.LogStatus;

public class BasicSearchResults_Test extends BaseClass{
	BasicSearchResults basicSearchResults;
	
	
	@BeforeClass(groups={"Smoke"})
	@Parameters({"browser"})
	public void setup(String browser){
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL= config.getPageURL("BasicSearchResultsURL");
		System.out.println("PageURL: "+pageURL);
		driver= BrowserManager.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		basicSearchResults = new BasicSearchResults(driver);
		System.out.println("Basic search results setup done");
		}
	
	@Test (groups={"Smoke"})
	public void clickPrint(){
		String fileName= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		basicSearchResults.selectCheckBox();
		basicSearchResults.clickPrint();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Checking the Page Title
		String pageTitle=driver.getTitle();
		System.out.println("Actual Page Title:"+pageTitle);
		Assert.assertTrue(driver.getCurrentUrl().contains("CTS.Print"));
		Assert.assertTrue(driver.findElement(By.className("printIntroText")).getText().contains(fileName));
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
		logger.log(LogStatus.PASS, "Pass => " + "Verify Print page on Basic Search on Basic CTS");
		
	}
	

}
