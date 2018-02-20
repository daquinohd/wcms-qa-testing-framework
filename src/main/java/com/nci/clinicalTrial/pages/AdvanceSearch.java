package com.nci.clinicalTrial.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AdvanceSearch {

	public static final String ADVANCE_SEARCH_PAGE_TITLE = "Find NCI-Supported Clinical Trials";
	public static final String SEARCH_RESULT_PAGE_TITLE = "Clinical Trials Search Results";
	public static final String BREAD_CRUMB = "Home\nAbout Cancer\nCancer Treatment\nClinical Trials Information";

	WebDriver driver;

	/*************** Advance Search Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement AdvanceSearchPageTitle;
	@FindBy(how = How.CSS, using = "#select2-ct-select-container")
	WebElement box_primaryCancerType;
	@FindBy(how = How.CSS, using = "#select2-ct-select-results")
	WebElement lst_primaryCancerTypeResults;
	@FindBy(how = How.CSS, using = "#fieldset--organization")
	WebElement box_LeadOrganization;
	@FindBy(how = How.XPATH, using = ".//input[@class='submit button']")
	WebElement btn_Search;
	@FindBy(how = How.CSS, using = "#ct-select option")
	List<WebElement> dpd_cancerType;
	@FindBy(how = How.CSS, using = "#st-multiselect")
	WebElement dpd_subTypeBox;
	@FindBy(how = How.CSS, using = "#st-multiselect")
	List<WebElement> dpd_subType;
	@FindBy(how = How.XPATH, using = "//input[@id='a']")
	WebElement txt_age;
	@FindBy(how = How.XPATH, using = "//input[@id='q']")
	WebElement txt_keywords;
	@FindBy(how = How.XPATH, using = "//label[@id='fin-label']")
	WebElement lbl_side_effects;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--type']/legend['CANCER TYPE / CONDITION']")
	WebElement lgd_cancerType;

	/*************** Search Result Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement searchResultsPageTitle;
	@FindBy(how = How.NAME, using = "printButton")
	WebElement btn_Print;
	@FindBy(how = How.CSS, using = ".ctscb")
	WebElement showSearchCriteria;

	// Initializing the Page Objects
	public AdvanceSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getAdvanceSearchPageTitle() {
		return AdvanceSearchPageTitle.getText();
	}

	public WebElement getCancerTypeBox() {
		return box_primaryCancerType;
	}

	public WebElement getCancerSubTypeBox() {
		return dpd_subTypeBox;
	}

	public WebElement getAgeBox() {
		return txt_age;
	}

	public String getSearchResultPageTitle() {
		return searchResultsPageTitle.getText();
	}

	public WebElement getPrintButton() {
		return btn_Print;
	}

	public void defaultSearch() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", box_LeadOrganization);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		btn_Search.click();
	}

	public void getCancerType(String cancerType) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", AdvanceSearchPageTitle);
		getCancerTypeBox().click();
		WebElement txt_CancerTypeBox = driver.findElement(By.xpath(
				"//span[@class='select2-search select2-search--dropdown']/input[@class='select2-search__field']"));

		txt_CancerTypeBox.click();
		txt_CancerTypeBox.sendKeys(cancerType);
		txt_CancerTypeBox.sendKeys(Keys.ENTER);
		System.out.println("****Clicked on the CancerType drop down****");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// List<WebElement> cancerTypeList =
		// lst_primaryCancerTypeResults.findElements(By.tagName("li"));
		// System.out.println("****Total # of cancerTypes: ****" +
		// cancerTypeList.size());
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// cancerTypeList.get(cancerTypeIndex).click();
		Thread.sleep(200);
	}

	public void getCancerSubType(String cancerSubType) throws InterruptedException {
		WebElement dpd_subTypeBox1 = driver.findElement(By.xpath(
				".//ul[@class='select2-selection__rendered']/li[@class='select2-search select2-search--inline']/input[@class='select2-search__field'][@placeholder='Select a subtype']"));
		dpd_subTypeBox1.click();
		dpd_subTypeBox1.sendKeys(cancerSubType);
		dpd_subTypeBox1.sendKeys(Keys.ENTER);
		System.out.println("****Clicked on the CancerSubType drop down****");
		Thread.sleep(200);
	}

	public void getCancerStage(String cancerStage) {
		// Clicking on "Select a Stage" drop down
		WebElement dpd_cancerStage = driver
				.findElement(By.xpath("//input[@class='select2-search__field' and @placeholder='Select a stage']"));
		dpd_cancerStage.click();
		dpd_cancerStage.sendKeys(cancerStage);
		dpd_cancerStage.sendKeys(Keys.ENTER);
		System.out.println("Clicked on the cancer stage drop down");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void getAge(int age) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lbl_side_effects);
		txt_age.click();
		txt_age.clear();
		txt_age.sendKeys(String.valueOf(age));
		System.out.println("Typed the age in Age field");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
	}

	public void getKeywordPhrase(String keyword) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lbl_side_effects);
		txt_keywords.click();
		txt_keywords.clear();
		txt_keywords.sendKeys(keyword);
		System.out.println("Typed the keyword/phrase in KEYWORDS/PHRASES field");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(500);
	}

	/* Verify advance search for cancer type and cancer sub-type */
	public void advSearch_CancerType_SubType(String cancerType, String cancerSubType) throws InterruptedException {
		getCancerType(cancerType);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getCancerSubType(cancerSubType);
		defaultSearch();
	}

	/* Verify advance search for cancer type, cancer sub-type and stage */
	public void advSearch_CancerType_SubType_Stage(String cancerType, String cancerSubType, String cancerStage)
			throws InterruptedException {
		getCancerType(cancerType);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getCancerSubType(cancerSubType);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getCancerStage(cancerStage);
		defaultSearch();
	}

}
