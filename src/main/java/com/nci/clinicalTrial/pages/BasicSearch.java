package com.nci.clinicalTrial.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.nci.Utilities.FunctionLibrary;

public class BasicSearch {

	public final String BREAD_CRUMB = "Home\nAbout Cancer\nCancer Treatment\nClinical Trials Information";
	WebDriver driver;

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//*[@id='cgvBody']/div[1]/div")
	WebElement txt_SearchTip;
	//@FindBy(how = How.LINK_TEXT, using = "advanced search")
	@FindBy(how = How.XPATH, using = ".//*[@id='cgvBody']/div[1]/div/div/a")
	WebElement lnk_AdvSearch;
	@FindBy(how = How.XPATH, using = ".//input[@id='q']")
	WebElement txt_CancerType;
	@FindBy(how = How.CSS, using = "#fieldset--type > legend > span")
	WebElement lbl_CancerType;
	@FindBy(how = How.CSS, using = "#fieldset--type > a")
	WebElement lnk_CancerTypeHelp;
	@FindBy(how = How.CSS, using = "#fieldset--age > legend > span")
	WebElement lbl_Age;
	@FindBy(how = How.CSS, using = "#fieldset--age > a")
	WebElement lnk_AgeHelp;
	@FindBy(how = How.CSS, using = "#fieldset--zip > legend > span")
	WebElement lbl_Zipcode;
	@FindBy(how = How.CSS, using = "#fieldset--zip > a")
	WebElement lnk_ZipcodeHelp;
	@FindBy(how = How.XPATH, using = ".//input[@id='z']")
	WebElement txt_Zipcode;
	@FindBy(how = How.XPATH, using = ".//input[@id='a']")
	WebElement txt_Age;
	@FindBy(how = How.XPATH, using = ".//*[@id='q']")
	WebElement msg_CancerType;
	@FindBy(how = How.XPATH, using = ".//*[@id='fieldset--age']/div")
	WebElement text_Age;
	@FindBy(how = How.XPATH, using = ".//*[@id='fieldset--zip']/div")
	WebElement text_Zipcode;
	//@FindBy(how = How.XPATH, using = ".//input[@type='submit'][@class='submit button'] [@value='Search']")
	//WebElement btn_Search;
	@FindBy(how = How.XPATH, using = ".//input[@type='submit'][@class='submit button'] [@value='Find Trials']")
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
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]")
	WebElement text_BasicDefinition;
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]/p/a[2]")
	WebElement lnk_Steps;
	@FindBy(how = How.CSS, using = ".api-reference-content > p:nth-child(1) > a:nth-child(1)")
	WebElement lnk_CTSApi;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--type']/legend['Cancer Type/Keyword']")
	WebElement lgd_cancerType;

	// Constructor - Initializing the Page objects
	public BasicSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	//UI components
	public WebElement[] verifyUI() {
		return new WebElement[] { text_BasicDefinition, lbl_CancerType, lnk_CancerTypeHelp, lbl_Age, lnk_AgeHelp,
				lbl_Zipcode, lnk_ZipcodeHelp, btn_Search, msg_CancerType, text_Age, text_Zipcode, txt_SearchTip };

	}

	public void clickSteps() {
		lnk_Steps.click();
	}

	// Default Search
	public void searchDefault() {
		FunctionLibrary.scrollIntoview(driver, lgd_cancerType);
		btn_Search.click();
	}

	// Search based on cancer type
	public void searchCancerType(String cancerType) {
		txt_CancerType.sendKeys(cancerType);
		txt_CancerType.sendKeys(Keys.RETURN);

	}

	// Search based on Age
	public void searchAge(int age) {
		txt_Age.sendKeys(String.valueOf(age));
		btn_Search.click();
	}

	public void searchZip(String zipCode) {
		txt_Zipcode.sendKeys(zipCode);
		btn_Search.click();
	}

	public void searchCancerTypeAge(String cancerType, int age) {
		txt_CancerType.sendKeys(cancerType);
		txt_Age.sendKeys(String.valueOf(age));
		btn_Search.click();
		// txt_CancerType.sendKeys(Keys.RETURN);
	}

	public void searchCancerTypeZip(String cancerType, String zipCode) {
		txt_CancerType.sendKeys(cancerType);
		txt_Zipcode.sendKeys(zipCode);
		btn_Search.click();
		// txt_CancerType.sendKeys(Keys.RETURN);
	}

	public void searchAgeZip(int age, String zipCode) {
		txt_Age.sendKeys(String.valueOf(age));
		txt_Zipcode.sendKeys(zipCode);
		btn_Search.click();
	}

	public void searchCancerTypeAgeZip(String cancerType, int age, String zipCode) {
		txt_CancerType.sendKeys(cancerType);
		txt_Age.sendKeys(String.valueOf(age));
		txt_Zipcode.sendKeys(zipCode);
		btn_Search.click();
		// txt_CancerType.sendKeys(Keys.RETURN);
	}

	/*
	 * public void clickDelighterWhat() { delighter_What.click(); }
	 * 
	 * public void clickDelighterWhich() { delighter_Which.click(); }
	 * 
	 * public WebElement getDelighterWhich() { return delighter_Which; }
	 
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
	} */

	public void clickAdvSearch() {
		lnk_AdvSearch.click();
	}
}
