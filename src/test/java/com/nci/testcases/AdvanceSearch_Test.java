package com.nci.testcases;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserFactory;
import com.nci.Utilities.ConfigReader;
import com.nci.clinicalTrial.pages.AdvanceSearch;
import com.nci.clinicalTrial.pages.BasicSearch;

public class AdvanceSearch_Test {

	WebDriver driver;
	AdvanceSearch advanceSearch;
	ConfigReader config = new ConfigReader();
	
	@BeforeTest (groups={"Smoke"})
	@Parameters({"browser"})
	public void setup(String browser) throws MalformedURLException{
		//String url="https://www.cancer.gov/about-cancer/treatment/clinical-trials/search";
		String pageUrl= config.getPageURL("AdvanceSearchPageURL");
		System.out.println("PageURL: "+pageUrl);
		driver= BrowserFactory.startBrowser(browser, pageUrl);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//basicSearch = PageFactory.initElements(driver, BasicSearch.class);
		//System.out.println("PageFactory initiated");
		advanceSearch = new AdvanceSearch(driver);
		}
	
	@Test (groups = {"Smoke"}, priority = 1)
	public void verifyPageTitle()
	{
		Assert.assertEquals(advanceSearch.getAdvanceSearchPageTitle(), AdvanceSearch.ADVANCE_SEARCH_PAGE_TITLE);
		//logger.log(LogStatus.PASS, "Verifying the Title of the page is *Find NCI-Supported Clinical Trials* | Actual Result: "+advanceSearch.getAdvanceSearchPageTitle());
	}
	
	@Test (groups = {"Smoke"}, priority = 2)
	public void defaultSearchTest() {
		
		advanceSearch.defaultSearch();
		System.out.println("default Search function executed");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		//Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("Advance Search Results Page Title: "+pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result" ));
				
		/*Verify that search result summary text contains the "all trials" 
		Ex- Results 1-10 of 5384 for your search for: "all trials"  */ 
		
		String searchStatus = driver.findElement(By.xpath(".//*[@class='cts-results-label']/strong")).getText();
		System.out.println("SR Status Body Text: "+searchStatus);
		Assert.assertTrue(searchStatus.contains("all trials"));
		
		driver.navigate().back();
	}
	
	@Test(dataProvider="AdvanceSearch", groups = {"Smoke"}, priority = 2)
	public void advancesearch_CancerType_SubType(int cancerTypeIndex, String cancerTypeId, String cancerType, 
										int cancerSubTypeIndex, String cancerSubTypeId, String cancerSubType) throws InterruptedException {
		Object[][] data;
		Thread.sleep(500);
		advanceSearch.advSearch_CancerType_SubType(cancerTypeIndex, cancerSubTypeIndex);
		Thread.sleep(500);
		advanceSearch.defaultSearch();
		
		//Verify page title
		String pageTitle = driver.getTitle();
		System.out.println("Advance Search Results Page Title: "+pageTitle);
		Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result" ));
		
		//Verify search criteria in URL
		String pageURL = driver.getCurrentUrl();
		System.out.println("Current Page URL: "+pageURL);
		//Assert.assertTrue(pageURL.contains("t=C9312&st=C53707" ));
		Assert.assertTrue(pageURL.contains("t="+cancerTypeId+"&st="+cancerSubTypeId));
		System.out.println("Cancer Type ID: t="+cancerTypeId+"&st="+cancerSubTypeId);
				
		/*Verify that show search criteria table contains the selected search criteria */ 
		driver.findElement(By.xpath(".//*[@class='ctscb']")).click();
		Thread.sleep(500);
		
		WebElement table_element = driver.findElement(By.xpath(".//table[@class='table no-auto-enlarge']"));
		//List<WebElement> tr_collection=table_element.findElements(By.xpath(".//table[@class='table no-auto-enlarge']/tbody/tr"));
		//WebElement cancerType=table_element.findElement(By.xpath(".//table[@class='table no-auto-enlarge']//tbody//tr[1]//td[2]"));
		//WebElement cancerType=table_element.findElement(By.className("table no-auto-enlarge"));
		
		List<WebElement> tr_collection=table_element.findElements(By.tagName("tr"));
        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
        data = new Object[tr_collection.size()][2];
        int row_num,col_num;
        row_num=0;
       
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            System.out.println("NUMBER OF COLUMNS="+td_collection.size());
            
            col_num=0;
            for(WebElement tdElement : td_collection)
            {
                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
                data[row_num][col_num]=tdElement.getText();
                System.out.println("DATA="+data[row_num][col_num]);
                col_num++;
            }
            row_num++;
        } 
        
        Assert.assertEquals(data[1][1], cancerType);
        Assert.assertEquals(data[2][1], cancerSubType);
          
		driver.navigate().back();
	}
	
	@Test(dataProvider="AdvanceSearch1", groups = {"Smoke"}, priority = 2)
		public void advancesearch_CancerType_SubType_Stage(int cancerTypeIndex, String cancerTypeId, String cancerType, 
											int cancerSubTypeIndex, String cancerSubTypeId, String cancerSubType, 
											int cancerStageIndex, String cancerStageId, String cancerStage ) throws InterruptedException {
			Object[][] data;
			Thread.sleep(500);
			advanceSearch.advSearch_CancerType_SubType_Stage(cancerTypeIndex, cancerSubTypeIndex, cancerStageIndex);
			Thread.sleep(500);
			advanceSearch.defaultSearch();
			
			//Verify page title
			String pageTitle = driver.getTitle();
			System.out.println("Advance Search Results Page Title: "+pageTitle);
			Assert.assertTrue(pageTitle.contains("Clinical Trials Search Result" ));
			
			//Verify search criteria in URL
			String pageURL = driver.getCurrentUrl();
			System.out.println("Current Page URL: "+pageURL);
			//Assert.assertTrue(pageURL.contains("t=C9312&st=C53707&stg=C6468" ));
			Assert.assertTrue(pageURL.contains("t="+cancerTypeId+"&st="+cancerSubTypeId+"&stg="+cancerStageId));
			System.out.println("Cancer Type ID: t="+cancerTypeId+"&st="+cancerSubTypeId+"&stg="+cancerStageId);
					
			/*Verify that show search criteria table contains the selected search criteria */ 
			driver.findElement(By.xpath(".//*[@class='ctscb']")).click();
			Thread.sleep(500);
			
			WebElement table_element = driver.findElement(By.xpath(".//table[@class='table no-auto-enlarge']"));
			//List<WebElement> tr_collection=table_element.findElements(By.xpath(".//table[@class='table no-auto-enlarge']/tbody/tr"));
			//WebElement cancerType=table_element.findElement(By.xpath(".//table[@class='table no-auto-enlarge']//tbody//tr[1]//td[2]"));
			//WebElement cancerType=table_element.findElement(By.className("table no-auto-enlarge"));
			
			List<WebElement> tr_collection=table_element.findElements(By.tagName("tr"));
	        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	        data = new Object[tr_collection.size()][2];
	        int row_num,col_num;
	        row_num=0;
	       
	        for(WebElement trElement : tr_collection)
	        {
	            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
	            System.out.println("NUMBER OF COLUMNS="+td_collection.size());
	            
	            col_num=0;
	            for(WebElement tdElement : td_collection)
	            {
	                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
	                data[row_num][col_num]=tdElement.getText();
	                System.out.println("DATA="+data[row_num][col_num]);
	                col_num++;
	            }
	            row_num++;
	        } 
	        
	        Assert.assertEquals(data[1][1], cancerType);
	        Assert.assertEquals(data[2][1], cancerSubType);
	        Assert.assertEquals(data[3][1], cancerStage);
	          
			driver.navigate().back();
		}	
	 
	@AfterTest (groups={"Smoke"})
	public void endSession(){
		driver.quit();
	}
	
	@DataProvider(name="AdvanceSearch")
	public Object[][] readAdvanceSearchData() {
		 return new Object[][] { 
		   //{cancerTypeIndex, cancerTypeID, cancerTypeName, cancerSubTypeIndex, cancerSubTypeID, cancerSubTypeName, cancerStageIndex, cancerStageId, cancerStageName}
			 {129, "C2991", "Other Disease", 10, "C34783", "Alcoholic Liver Disease"},
			 {12, "C9312", "Bone Sarcoma", 1, "C53707", "Bone Osteosarcoma"},
			 {14, "C4872", "Breast Cancer", 1, "C5214", "Breast Adenocarcinoma"} };	
	}
	
	@DataProvider(name="AdvanceSearch1")
	public Object[][] readAdvanceSearchData1() {
		 return new Object[][] { 
		   //{cancerTypeIndex, cancerTypeID, cancerTypeName, cancerSubTypeIndex, cancerSubTypeID, cancerSubTypeName, cancerStageIndex, cancerStageId, cancerStageName}
			 {12, "C9312", "Bone Sarcoma", 1, "C53707", "Bone Osteosarcoma", 1, "C6468", "Stage III Bone Sarcoma"},
			 {14, "C4872", "Breast Cancer", 1, "C5214", "Breast Adenocarcinoma", 1, "C36301", "Breast Carcinoma Metastatic in the Brain"}};	
	}
	
}
