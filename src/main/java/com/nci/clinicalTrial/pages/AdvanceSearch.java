package com.nci.clinicalTrial.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AdvanceSearch {
	
	public static final String ADVANCE_SEARCH_PAGE_TITLE = "Find NCI-Supported Clinical Trials";
	public static final String SEARCH_RESULT_PAGE_TITLE = "Clinical Trials Search Results";
	
	WebDriver driver;
	
	/***************Advance Search Page WebElements**********************/
	@FindBy(how=How.XPATH, using=".//h1") WebElement AdvanceSearchPageTitle;
	@FindBy(how=How.CSS, using="#select2-ct-select-container") WebElement box_primaryCancerType;
	@FindBy(how=How.CSS, using="#select2-ct-select-results") WebElement lst_primaryCancerTypeResults;
	
	
	//@FindBy(how=How.CSS, using="#fieldset--type") WebElement box_cancerType;
	@FindBy(how=How.CSS, using="#fieldset--organization") WebElement box_LeadOrganization;
	@FindBy(how=How.XPATH, using=".//input[@class='submit button']") WebElement btn_Search;
	@FindBy(how=How.CSS, using="#ct-select option") List <WebElement> dpd_cancerType;
	@FindBy(how=How.CSS, using="#st-multiselect") WebElement dpd_subTypeBox;
	@FindBy(how=How.CSS, using="#st-multiselect") List <WebElement> dpd_subType;
	
	
	
	/***************Search Result Page WebElements**********************/
	@FindBy(how=How.XPATH, using=".//h1") WebElement searchResultsPageTitle; 
	@FindBy(how=How.NAME, using="printButton") WebElement btn_Print; 
	@FindBy(how=How.CSS, using=".ctscb") WebElement showSearchCriteria; 
	
	public AdvanceSearch (WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getAdvanceSearchPageTitle() {
		return AdvanceSearchPageTitle.getText();
	}
	
	public List <WebElement> getCancerType() {
		return dpd_cancerType;
	}
	
	public List <WebElement> getSubType() {
		return dpd_subType;
	}
	
	public WebElement getSubTypeBox() {
		return dpd_subTypeBox;
	}
	
	public String getSearchResultPageTitle() {
		return searchResultsPageTitle.getText();
	}
	
	public WebElement getPrintButton() {
		return btn_Print;
	}
	
	public WebElement getCancerTypeBox() {
		return box_primaryCancerType;
	}
	
	public void defaultSearch() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", box_LeadOrganization);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		btn_Search.click();
		
	}
	
	public void advSearch_CancerType_SubType(int cancerTypeIndex, int cancerSubTypeIndex) {
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		box_primaryCancerType.click();
		System.out.println("Clicked on the CancerType drop down");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List <WebElement> cancerTypeList = lst_primaryCancerTypeResults.findElements(By.tagName("li"));
		System.out.println("Total # of cancerTypes: "+cancerTypeList.size());
		cancerTypeList.get(cancerTypeIndex).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector(".select2-selection__rendered [placeholder='Select a subtype']")).click();
		System.out.println("Clicked on the SubType drop down");
		// Get the list of elements under the select tag
		Select multi = new Select(driver.findElement(By.id("st-multiselect")));
		System.out.println("Put the elements of the second drop down in the 'Select' class");
		// Get an element at specified index
		multi.selectByIndex(cancerSubTypeIndex);
		System.out.print("Selected an item from the cancer subtype drop down: ");
		System.out.println(driver.findElement(By.cssSelector(".select2-selection__choice")).getText());
	}	
	
	public void advSearch_CancerType_SubType_Stage(int cancerTypeIndex, int cancerSubTypeIndex, int cancerStageIndex) {
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		box_primaryCancerType.click();
		System.out.println("Clicked on the CancerType drop down");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List <WebElement> cancerTypeList = lst_primaryCancerTypeResults.findElements(By.tagName("li"));
		cancerTypeList.get(cancerTypeIndex).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector(".select2-selection__rendered [placeholder='Select a subtype']")).click();
		System.out.println("Clicked on the SubType drop down");
		// Get the list of elements under the select tag
		Select multi = new Select(driver.findElement(By.id("st-multiselect")));
		System.out.println("Put the elements of the second drop down in the 'Select' class");
		// Get an element at specified index
		multi.selectByIndex(cancerSubTypeIndex);
		System.out.print("Selected an item from the cancer subtype drop down: ");
		System.out.println(driver.findElement(By.cssSelector(".select2-selection__choice")).getText());

		// Clicking on "Select a Stage" drop down
		driver.findElement(By.cssSelector(".select2-selection__rendered [placeholder='Select a stage']")).click();
		System.out.println("Clicked on the cancer stage drop down");
				
		WebElement stageListBox = driver.findElement(By.id("select2-stg-multiselect-results"));
		System.out.println("Found the box that contains the list of the 'Stage' items");
				
		// Find the elements with "li" tag inside the box
		List <WebElement> stageList = stageListBox.findElements(By.tagName("li"));
		System.out.println("Got all the 'li' tag name items");
				
		// Choose the item at specified index (The zero's is "All")
		stageList.get(cancerStageIndex).click();
		System.out.println("Selected one of stage items");
		
	}		

}
