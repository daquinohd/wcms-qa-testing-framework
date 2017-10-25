package com.nci.clinicalTrial.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BasicSearch {

	WebDriver driver; 
	public BasicSearch(WebDriver driver) 
		{
			this.driver = driver;
		}
	
	@FindBy(how=How.XPATH, using=".//*[@id='cgvBody']/div[1]/p/a[3]") WebElement lnk_AdvSearch;
	@FindBy(how=How.XPATH, using=".//input[@id='q']") WebElement txt_CancerType;
	@FindBy(how=How.XPATH, using=".//input[@id='z']") WebElement txt_Zipcode;
	@FindBy(how=How.XPATH, using=".//input[@id='a']") WebElement txt_Age;
	@FindBy(how=How.XPATH, using=".//div[@class='btn-group']/input[@value='Search']") WebElement btn_Search;
	//@FindBy(how=How.XPATH, using=".//input[@type='submit'][@class='submit button'] [@value='Search']") WebElement btn_Search;
	@FindBy(how=How.CSS, using=".delighter.cts-livehelp") WebElement delighter_LiveHelp;
	@FindBy(how=How.CSS, using=".delighter.cts-what") WebElement delighter_What;
	@FindBy(how=How.CSS, using=".delighter.cts-which") WebElement delighter_Which;
	@FindBy(how=How.CSS, using=".delighter.cts-feedback") WebElement delighter_Feedback;
	@FindBy(how=How.CSS, using=".delighter.cts-feedback>h4") WebElement sendUsYourFeedback;
	@FindBy(how=How.CSS, using=".ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.cts-feedback-dialog") WebElement delighter_FeedbackPopup;
	@FindBy(how=How.CSS, using="#cts-feedback-cancel") WebElement delighter_FeedbackPopupCancel;
	
	//Default Search
	public void searchDefault(){
		btn_Search.click();		
	}
	
	
	//Search based on cancer type
	public void searchCancerType(String cancerType){
		txt_CancerType.sendKeys(cancerType);
		/*System.out.println("send keys complete");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, 250);"); 
		//jse.executeScript("arguments[0].scrollIntoView(true);",btn_Search); 
				btn_Search.click();
		System.out.println("click method done");*/
		txt_CancerType.sendKeys(Keys.RETURN); 

	}
	
	public void searchAge(int age){
		txt_Age.sendKeys(Integer.toString(age));
		btn_Search.click();
		//txt_CancerType.sendKeys(Keys.RETURN);
	}
	
	public void searchZip(int zipCode){
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();	
	}	
	
	public void searchCancerTypeAge(String cancerType, int age){
		txt_CancerType.sendKeys(cancerType);
		txt_Age.sendKeys(Integer.toString(age));
		btn_Search.click();	
	}
	
	public void searchCancerTypeZip(String cancerType, int zipCode){
		txt_CancerType.sendKeys(cancerType);
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();	
	}
	
	public void searchAgeZip(int age, int zipCode){
		txt_Age.sendKeys(Integer.toString(age));
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();	
	}
	
	public void searchCancerTypeAgeZip(String cancerType, int age, int zipCode){
		txt_CancerType.sendKeys(cancerType);
		txt_Age.sendKeys(Integer.toString(age));
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();	
	}
	
	public void clickDelighterLiveHelp(){
		delighter_LiveHelp.click();
	}
	
	public void clickDelighterWhat(){
		delighter_What.click();
	}
	
	public void clickDelighterWhich(){
		delighter_Which.click();
	}
	
	public WebElement getDelighterWhich(){
		return delighter_Which;
	}
	
	public void clickDelighterFeedback(){
		
		delighter_Feedback.click();
	}
	
	public WebElement getDelighterFeedback(){
		return delighter_Feedback;
	}
	
	public WebElement delighterFeedbackPopup(){
		return delighter_FeedbackPopup;
	}
	
	public void clickDelighterFeedbackPopupCancel(){
		delighter_FeedbackPopupCancel.click();
	}
	
	public void clickAdvSearch(){
		lnk_AdvSearch.click();
	}
}
