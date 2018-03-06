package com.nci.clinicalTrial.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BasicSearch {

	WebDriver driver;

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.LINK_TEXT, using = "advanced search")
	WebElement lnk_AdvSearch;
	@FindBy(how = How.XPATH, using = ".//input[@id='q']")
	WebElement txt_CancerType;
	@FindBy(how = How.XPATH, using = ".//input[@id='z']")
	WebElement txt_Zipcode;
	@FindBy(how = How.XPATH, using = ".//input[@id='a']")
	WebElement txt_Age;
	// @FindBy(how=How.XPATH, using=".//*[@id='form--cts-basic']/div[2]/input")
	// WebElement btn_Search;
	// @FindBy(how=How.XPATH,
	// using=".//div[@class='btn-group']/input[@value='Search']") WebElement
	// btn_Search;
	@FindBy(how = How.XPATH, using = ".//input[@type='submit'][@class='submit button'] [@value='Search']")
	WebElement btn_Search;

	@FindBy(how = How.CSS, using = ".delighter.cts-feedback")
	WebElement delighter_Feedback;
	@FindBy(how = How.CSS, using = ".delighter.cts-feedback>h4")
	WebElement sendUsYourFeedback;
	@FindBy(how = How.CSS, using = ".ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.cts-feedback-dialog")
	WebElement delighter_FeedbackPopup;
	@FindBy(how = How.CSS, using = "#cts-feedback-cancel")
	WebElement delighter_FeedbackPopupCancel;
	@FindBy(how = How.XPATH, using = ".//*[@id='cgvBody']/div[2]/div/div[2]")
	WebElement module_CTSApi;
	// @FindBy(how=How.XPATH,
	// using=".//*[@id='cgvBody']/div[2]/div/div[2]/div/p/a") WebElement
	// lnk_CTSApi;
	@FindBy(how = How.CSS, using = ".api-reference-content > p:nth-child(1) > a:nth-child(1)")
	WebElement lnk_CTSApi;

	// Constructor - Initializing the Page objects
	public BasicSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Default Search
	public void searchDefault() {
		btn_Search.click();
	}

	// Search based on cancer type
	public void searchCancerType(String cancerType) {
		txt_CancerType.sendKeys(cancerType);
		txt_CancerType.sendKeys(Keys.RETURN);

	}

	// Search based on Age
	public void searchAge(int age) {
		txt_Age.sendKeys(Integer.toString(age));
		btn_Search.click();
	}

	public void searchZip(int zipCode) {
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();
	}

	public void searchCancerTypeAge(String cancerType, int age) {
		txt_CancerType.sendKeys(cancerType);
		txt_Age.sendKeys(Integer.toString(age));
		btn_Search.click();
		// txt_CancerType.sendKeys(Keys.RETURN);
	}

	public void searchCancerTypeZip(String cancerType, int zipCode) {
		txt_CancerType.sendKeys(cancerType);
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();
		// txt_CancerType.sendKeys(Keys.RETURN);
	}

	public void searchAgeZip(int age, int zipCode) {
		txt_Age.sendKeys(Integer.toString(age));
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();
	}

	public void searchCancerTypeAgeZip(String cancerType, int age, int zipCode) {
		txt_CancerType.sendKeys(cancerType);
		txt_Age.sendKeys(Integer.toString(age));
		txt_Zipcode.sendKeys(Integer.toString(zipCode));
		btn_Search.click();
		// txt_CancerType.sendKeys(Keys.RETURN);
	}

	/*
	 * public void clickDelighterWhat() { delighter_What.click(); }
	 * 
	 * public void clickDelighterWhich() { delighter_Which.click(); }
	 * 
	 * public WebElement getDelighterWhich() { return delighter_Which; }
	 */
	public void clickDelighterFeedback() {

		delighter_Feedback.click();
	}

	public WebElement getDelighterFeedback() {
		return delighter_Feedback;
	}

	public WebElement delighterFeedbackPopup() {
		return delighter_FeedbackPopup;
	}

	public void clickDelighterFeedbackPopupCancel() {
		delighter_FeedbackPopupCancel.click();
	}

	public void clickAdvSearch() {
		lnk_AdvSearch.click();
	}
}
