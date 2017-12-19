package com.nci.testcases;

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

import com.nci.Utilities.BrowserFactory;
import com.nci.Utilities.ConfigReader;
import com.nci.clinicalTrial.pages.BasicSearch;
import com.relevantcodes.extentreports.LogStatus;

public class BasicSearch_Test extends BaseClass{
	
	//WebDriver driver;
	BasicSearch basicSearch;
	String resultPageUrl;
	String advSearchPageUrl;
	//ConfigReader config = new ConfigReader();
	
	@BeforeClass(groups={"Smoke"})
	@Parameters({"browser"})
	public void setup(String browser){
		logger = report.startTest(this.getClass().getSimpleName());
		//String url="https://www.cancer.gov/about-cancer/treatment/clinical-trials/search";
		pageURL= config.getPageURL("BasicClinicalTrialSearchURL");
		System.out.println("PageURL: "+pageURL);
		driver= BrowserFactory.startBrowser(browser, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//basicSearch = PageFactory.initElements(driver, BasicSearch.class);
		basicSearch = new BasicSearch(driver);
		System.out.println("Basic search setup done");
		}
	
	@Test (groups={"Smoke"})
	public void searchDefault(){
		basicSearch.searchDefault();
		if(driver.findElement(By.name("printButton")).isDisplayed()){
			System.out.println("All search results page should be displayed");
			resultPageUrl=driver.getCurrentUrl();
			System.out.println("Result page url: "+resultPageUrl);}
		else 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Checking the Page Title
		String pageTitle=driver.getTitle();
		System.out.println("Actual Page Title:"+pageTitle);
		Assert.assertTrue(driver.findElement(By.className("cts-results-label")).getText().contains("all trials"));
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the default search "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Default Search on Basic CTS");
		
	}
	
	
	@Test (groups={"Smoke"})
	public void searchCancerType() throws InterruptedException{
		String cancerType="Breast Cancer";
		
		//Performing the search using Cancer type parameter
		basicSearch.searchCancerType(cancerType);
		//driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//System.out.println("driver wait done");
		
		if(driver.findElement(By.name("printButton")).isDisplayed()){
			System.out.println("search results page should be displayed");
			resultPageUrl=driver.getCurrentUrl();
			System.out.println("Result page url: "+resultPageUrl);}
		else 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Checking the Page Title
		String pageTitle=driver.getTitle();
		System.out.println("Actual Page Title:"+pageTitle);
		Assert.assertEquals(pageTitle, "Clinical Trials Search Results - National Cancer Institute");
		
		//Checking the Search Results form Title
		String resultsTitle=driver.findElement(By.xpath(".//*[@id='main']/article/div[2]/h1/span")).getText();
		System.out.println("Results Title:"+resultsTitle);
		Assert.assertTrue(resultsTitle.contains("Clinical Trials Search Results"));
		
		//Checking the Results page URL for the parameters passed in order to validate correct search results are displayed
		String cancerTypeWithoutSpace = cancerType.replace(" ", "+");
		System.out.println("New String: " +cancerTypeWithoutSpace);
		Assert.assertTrue(resultPageUrl.contains(cancerTypeWithoutSpace), "Keyword not found "+cancerTypeWithoutSpace);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the cancer type search "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Search for Cancer Type on Basic CTS");
		
	}
	
	
	@Test (groups={"Smoke"})
	public void searchAge(){
		int age=65;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Performing the search using Age parameter
		basicSearch.searchAge(age);
		
		if(driver.findElement(By.name("printButton")).isDisplayed()){
			System.out.println("search results page should be displayed");
			resultPageUrl=driver.getCurrentUrl();
			System.out.println("Result page url: "+resultPageUrl);}
		else 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Checking the Results page URL for the parameters passed in order to validate correct search results are displayed
		Assert.assertTrue(resultPageUrl.contains(Integer.toString(age)), "Keyword not found "+age);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the age search "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Search for Age on Basic CTS");
			}
	
	 @Test (groups={"Smoke"})
	 public void searchZip(){
		int zip=20105;
		
		//Performing the search using Zipcode parameter
		basicSearch.searchZip(zip);
		
		//Checking the Results page URL for the parameters passed in order to validate correct search results are displayed
		resultPageUrl=driver.getCurrentUrl();
		Assert.assertTrue(resultPageUrl.contains(Integer.toString(zip)), "Keyword not found "+zip);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the zip search "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Search for ZipCode on Basic CTS");
	}
	 
	 @Test (groups={"Smoke"})
	 public void searchAgeZip(){
		int zip=20105;
		int age=65;
		
		//Performing the search using Age and Zipcode parameter
		basicSearch.searchAgeZip(age,zip);
		
		//Checking the Results page URL for the parameters passed in order to validate correct search results are displayed
		resultPageUrl=driver.getCurrentUrl();
		Assert.assertTrue(resultPageUrl.contains(Integer.toString(zip)), "Keyword not found "+zip);
		Assert.assertTrue(resultPageUrl.contains(Integer.toString(age)), "Keyword not found "+age);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the age search "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Search for Age and ZipCode on Basic CTS");
	}
	 
	 @Test (groups={"Smoke"})
	 public void verifyDelighterLiveHelp(){
		 String expectedPageUrl= config.getPageURL("DelighterLiveHelpURL");
		//Verifying the LiveHelp Delighter
		basicSearch.clickDelighterLiveHelp();
		
		//Checking the end page URL
		resultPageUrl=driver.getCurrentUrl();
		Assert.assertTrue(resultPageUrl.equals(expectedPageUrl), "Actual URL is not as expected "+resultPageUrl);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Live Help Delighter on Basic CTS");
	}
	 
	 @Test (groups={"Smoke"})
	 public void verifyDelighterWhat(){
		 String expectedPageUrl= config.getPageURL("DelighterWhatURL");
		//Verifying the LiveHelp Delighter
		basicSearch.clickDelighterWhat();
		
		//Checking the end page URL
		resultPageUrl=driver.getCurrentUrl();
		Assert.assertTrue(resultPageUrl.equals(expectedPageUrl), "Actual URL is not as expected "+resultPageUrl);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify What are Clinical Trials Delighter on Basic CTS");
	}
	 
	 @Test (groups={"Smoke"})
	 public void verifyDelighterWhich(){
		 String expectedPageUrl= config.getPageURL("DelighterWhichURL");
		//Verifying the LiveHelp Delighter
		basicSearch.clickDelighterWhich();
		
		//Checking the end page URL
		resultPageUrl=driver.getCurrentUrl();
		Assert.assertTrue(resultPageUrl.equals(expectedPageUrl), "Actual URL is not as expected "+resultPageUrl);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Page URL after the delighter check "+driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Pass => " + "Verify Which Trials are Best for You Delighter on Basic CTS");
	}
	 
	 @Test (groups={"Smoke"})
	 public void clickAdvSearch(){
						
			//Performing the search using Zipcode parameter
			basicSearch.clickAdvSearch();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//Checking the Results page URL for the parameters passed in order to validate correct search results are displayed
			advSearchPageUrl=driver.getCurrentUrl();
			System.out.println("Advanced Search "+advSearchPageUrl);
			Assert.assertTrue(advSearchPageUrl.contains("advanced"));
			driver.navigate().back();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("Page URL after the coming back to basic search "+driver.getCurrentUrl());
			logger.log(LogStatus.PASS, "Pass => " + "Verify navigation to Advanced CTS on Basic CTS");
		}
	 
	/*@AfterTest (groups={"Smoke"})
	public void endSession(){
		driver.quit();
	}*/
	
}

/*@Test
public void verifyDelighterFeedback() throws InterruptedException{
	 
	//Verifying the LiveHelp Delighter
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicSearch.getDelighterWhich());
	Thread.sleep(1000);
	basicSearch.clickDelighterFeedback();
	System.out.println("Delighter Feedback clicked");
	WebElement popUp=basicSearch.delighterFeedbackPopup();
	driver.switchTo().frame(popUp);
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	Assert.assertTrue(popUp.isDisplayed());
	System.out.println("Popup dispalyed? "+popUp.isDisplayed());
	if (popUp.isDisplayed()) {
		basicSearch.clickDelighterFeedbackPopupCancel();
	}
	System.out.println("Page URL after the delighter check "+driver.getCurrentUrl());
	
}*/

/*
//WebDriverWait wait = new WebDriverWait(driver, 10);
		//wait.until(driver.findElement(By.className("action button printSelected")).isDisplayed());
		//ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
	        //  public Boolean apply(WebDriver d) {
	           //return (d.findElement(By.className("action button printSelected")).isDisplayed());
	        	 //return (d.getCurrentUrl() != previousURL);
	         // }
	       // };
	   
	    //wait.until(e);
		//wait. (driver.findElement(By.className("action button printSelected")).isDisplayed());


//driver.findElement(By.xpath("//*[@id='cgvBody']/div[2]/div[1]/div[2]/label")).click();
		//System.out.println("Show Search Criteria is displayed");
		
		WebElement table_Element = driver.findElement(By.xpath(".//table[@class='table no-auto-enlarge']"));
		List<WebElement> tr_collection=table_Element.findElements(By.xpath(".//tbody/tr"));
		
		WebElement trElement;
		List<WebElement> td_collection=tr_collection.findElements(By.xpath("td"));
	
		System.out.println("No of rows in table: "+tr_collection);
		System.out.println("Cell value in table: "+tr_collection.get(0));
		
		int row_num, col_num;
		row_num=1;
		for(WebElement trElement : tr_collection)
		{
			List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
			System.out.println("No of Columns in table: "+td_collection.size());
			col_num=1;
			for(WebElement tdElement : td_collection)
			{
				System.out.println("Row # "+row_num+ ", Col # "+col_num+ "Text= " +tdElement.getText());
				col_num++;
			}
			row_num++;		
		}
				
		
		//WebElement yourSearch= driver.findElement(By.xpath(".//div[@class='clinicaltrials-results-criteria-display']//tbody/tr/td[2]"));
		
		//System.out.println("Your Search criteria: "+yourSearch.getText());
		//Assert.assertTrue(yourSearch.getText().contains(cancerType));
		//Assert.assertTrue(yourSearch.getText().contains(cancerType));*/
		