package com.nci.clinicalTrial.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AdvanceSearchResults {
	public static final String SEARCH_RESULT_PAGE_TITLE = "Clinical Trials Search Results";
	public static final String BREAD_CRUMB = "Home\nAbout Cancer\nCancer Treatment\nClinical Trials Information\nFind NCI-Supported Clinical Trials";
	public static final String H1_TITLE = "Clinical Trials Search Results";

	WebDriver driver;

	/*************** Advance Search Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement H1Title;
	@FindBy(how = How.XPATH, using = ".//*[@class='ctscb']")
	WebElement box_SearchCriteria;
	@FindBy(how = How.LINK_TEXT, using = "Start Over")
	WebElement lnk_StartOver;
	@FindBy(how = How.XPATH, using = "//input[@name='printButton']")
	WebElement btn_Print;
	@FindBy(how = How.XPATH, using = "//label[@for='checkAllTop']")
	WebElement cbox_CheckAllTop;
	// input[@id='checkAllTop']
	@FindBy(how = How.XPATH, using = "//div[@class='cts-results-container']")
	WebElement ctn_ResultsContainer;

	// @FindBy(how = How.XPATH, using = "//input[@type='checkbox']")
	// List<WebElement> checkBoxes;

	@FindBy(how = How.XPATH, using = "//input[@id='NCI-2016-01041']")
	List<WebElement> checkBoxes;
	// div[@class='cts-checkbox checkbox']
	@FindBy(how = How.XPATH, using = "//input[@type='checkbox']")
	List<WebElement> checkBoxes1;

	// Initializing the Page Objects
	public AdvanceSearchResults(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void getAdvanceSearchResultsPageTitle() {
		driver.getTitle();
	}

	public WebElement getSearchCriteriaBox() {
		return box_SearchCriteria;
	}

	public void clickOnSelectAllCheckBox() {
		cbox_CheckAllTop.click();
	}

	public void clickPrintButton() {
		btn_Print.click();
	}

	public List<WebElement> getCheckBoxes() {
		return checkBoxes;
	}

	public List<WebElement> getCheckBoxes1() {
		return checkBoxes1;
	}

	public int getCheckBoxesNumber() {
		System.out.println("Number of checkboxes: " + checkBoxes.size());
		return checkBoxes.size();

	}

	public void clickOnCheckBox() {
		System.out.println("Checkboxes: " + checkBoxes.get(0));
		checkBoxes.get(0).click();
	}

}
