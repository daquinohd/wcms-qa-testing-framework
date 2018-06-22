package com.nci.clinicalTrial.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.nci.Utilities.FunctionLibrary;

public class AdvanceSearch {

	public static final String ADVANCE_SEARCH_PAGE_TITLE = "Find NCI-Supported Clinical Trials";
	public static final String SEARCH_RESULT_PAGE_TITLE = "Clinical Trials Search Results";
	public final String BREAD_CRUMB = "Home\nAbout Cancer\nCancer Treatment\nClinical Trials Information";

	WebDriver driver;

	/*************** Advance Search Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement AdvanceSearchPageTitle;
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]")
	WebElement text_AdvanceDefinition;
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]/p/a[2]")
	WebElement lnk_Steps;
	@FindBy(how = How.XPATH, using = ".//*[@id='cgvBody']/div[1]/div")
	WebElement txt_SearchTipAdv;
	@FindBy(how = How.LINK_TEXT, using = "basic search")
	WebElement lnk_BasicSearch;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--type']/legend['CANCER TYPE/CONDITION']")
	WebElement lgd_cancerType;
	@FindBy(how = How.XPATH, using = "#fieldset--type > a")
	WebElement lnk_CancerTypeHelp;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--type']/div[1]")
	WebElement text_CancerTypeText;
	@FindBy(how = How.CSS, using = "#ct-label")
	WebElement lbl_PrimaryCancerType;
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
	WebElement box_subType;
	@FindBy(how = How.CSS, using = "#st-label")
	WebElement lbl_SubType;
	@FindBy(how = How.CSS, using = "#st-multiselect")
	List<WebElement> dpd_subType;
	@FindBy(how = How.CSS, using = "#stg-label")
	WebElement lbl_Stage;
	@FindBy(how = How.XPATH, using = "//input[@id='a']")
	WebElement txt_age;
	@FindBy(how = How.XPATH, using = "//input[@id='q']")
	WebElement txt_keywords;
	@FindBy(how = How.XPATH, using = "//label[@id='fin-label']")
	WebElement lbl_side_effects;

	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--location']/legend['LOCATION']")
	WebElement lgd_location;
	@FindBy(how = How.XPATH, using = "//label[@for='zip-code']")
	WebElement rbtn_zipcode;
	@FindBy(how = How.CSS, using = "#section--zip-code > div.radio > label")
	WebElement lbl_zipcode;
	@FindBy(how = How.XPATH, using = "//input[@id='z']")
	WebElement txt_zipcode;
	@FindBy(how = How.XPATH, using = "//*[@id='section--ccs']/div[1]/label")
	WebElement rbtn_countryStateCity;
	@FindBy(how = How.XPATH, using = "//*[@id='section--ccs']/div[3]/div[1]/span/span[1]/span/ul/li/input")
	WebElement txt_state;
	@FindBy(how = How.XPATH, using = "//*[@id='lcty']")
	WebElement txt_city;
	@FindBy(how = How.XPATH, using = "//*[@id='section--hospital']/div[1]/label")
	WebElement rbtn_hospital;
	@FindBy(how = How.XPATH, using = "//*[@id='hos']")
	WebElement txt_hospital;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--nih']/div/label")
	WebElement rbtn_atNIH;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--trialtype']")
	WebElement lgd_trialtype;
	@FindBy(how = How.XPATH, using = "//input[@id='hv']")
	WebElement cb_healthy_volunteers;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_all']")
	WebElement cb_trialtype_all;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_prevention']")
	WebElement cb_trialtype_prevention;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_treatment']")
	WebElement cb_trialtype_treatment;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_health_services_research']")
	WebElement cb_trialtype_health_services_research;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_supportive_care']")
	WebElement cb_trialtype_supportivecare;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_screening']")
	WebElement cb_trialtype_screening;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_diagnostic']")
	WebElement cb_trialtype_diagnostic;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_other']")
	WebElement cb_trialtype_other;
	@FindBy(how = How.XPATH, using = "//label[@for='tt_basic_science']")
	WebElement cb_trialtype_basic_science;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--trialtreatment']")
	WebElement lgd_drugtreatment;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--trialtreatment']/legend['DRUG/TREATMENT']")
	WebElement lgd_drugtreatment1;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--trialtreatment']/div[2]/span/span[1]/span/ul/li/input")
	WebElement txt_drug;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--trialtreatment']/div[3]/span/span[1]/span/ul/li/input")
	WebElement txt_treatment;
	@FindBy(how = How.XPATH, using = "//*[@id='fieldset--trialstatus']")
	WebElement lgd_trialphase;
	@FindBy(how = How.XPATH, using = "//div[@id='tp']//label[@for='tp_all']")
	WebElement cb_trialphaseAll;
	@FindBy(how = How.XPATH, using = "//div[@id='tp']//label[@for='tp_1']")
	WebElement cb_trialphase1;
	@FindBy(how = How.XPATH, using = "//div[@id='tp']//label[@for='tp_2']")
	WebElement cb_trialphase2;
	@FindBy(how = How.XPATH, using = "//div[@id='tp']//label[@for='tp_3']")
	WebElement cb_trialphase3;
	@FindBy(how = How.XPATH, using = "//div[@id='tp']//label[@for='tp_4']")
	WebElement cb_trialphase4;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--trialstatus']/legend['TRIAL PHASE']")
	WebElement lgd_trialPhase;
	@FindBy(how = How.XPATH, using = "//*[@id='tid']")
	WebElement txt_trialId;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--trialid']/legend['TRIAL ID']")
	WebElement lgd_trialId;
	@FindBy(how = How.XPATH, using = "//*[@id='in']")
	WebElement txt_trialInvestigator;
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--investigator']/legend['TRIAL INVESTIGATORS']")
	WebElement lgd_trialInvestigator;
	@FindBy(how = How.XPATH, using = "//input[@id='lo']")
	WebElement txt_leadOrganization;

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

	// UI components
	// public WebElement[] verifyUI(){
	// return new WebElement[] {text_AdvanceDefinition,
	// lbl_CancerType,lnk_CancerTypeHelp, lbl_Age, lnk_AgeHelp, lbl_Zipcode,
	// lnk_ZipcodeHelp, btn_Search, msg_CancerType, text_Age, text_Zipcode};

	// }

	public String getAdvanceSearchPageTitle() {
		return AdvanceSearchPageTitle.getText();
	}

	public WebElement getCancerTypeBox() {
		return box_primaryCancerType;
	}

	public WebElement getCancerSubTypeBox() {
		return box_subType;
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
		FunctionLibrary.scrollIntoview(driver, box_LeadOrganization);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		btn_Search.click();
	}

	public void getCancerType(String cancerType) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, AdvanceSearchPageTitle);
		getCancerTypeBox().click();
		WebElement txt_CancerTypeBox = driver.findElement(By.xpath(
				"//span[@class='select2-search select2-search--dropdown']/input[@class='select2-search__field']"));

		txt_CancerTypeBox.click();
		txt_CancerTypeBox.sendKeys(cancerType);
		txt_CancerTypeBox.sendKeys(Keys.ENTER);
		System.out.println("****Clicked on the CancerType drop down****");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void getAge(int age) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lbl_side_effects);
		txt_age.click();
		txt_age.clear();
		txt_age.sendKeys(String.valueOf(age));
		System.out.println("Typed the age in Age field");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
	}

	public void getKeywordPhrase(String keyword) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lbl_side_effects);
		txt_keywords.click();
		txt_keywords.clear();
		txt_keywords.sendKeys(keyword);
		System.out.println("Typed the keyword/phrase in KEYWORDS/PHRASES field: " + keyword);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		// Thread.sleep(500);
	}

	public void advSearch_Zipcode(String zip) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_location);
		rbtn_zipcode.click();
		txt_zipcode.click();
		txt_zipcode.clear();
		txt_zipcode.sendKeys(zip);
		System.out.println("Typed the zipcode");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(500);
	}

	public void getState(String state) {
		// Clicking on "Select a State" drop down
		WebElement dpd_state = driver.findElement(By.xpath(
				"//*[@id='section--ccs']/div[3]/div[1]/span/span[1]/span[@class='select2-selection select2-selection--multiple']"));
		// dpd_state.click();
		dpd_state.sendKeys(state);
		dpd_state.sendKeys(Keys.ENTER);
		System.out.println("Clicked on the state drop down");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void getHospital(String hospital) {
		// Clicking on "Select a State" drop down
		WebElement dpd_hospital = driver.findElement(By.cssSelector("#hos"));
		// dpd_hospital.click();
		dpd_hospital.sendKeys(hospital);
		// dpd_hospital.sendKeys(Keys.ENTER);
		System.out.println("Clicked on the hospital drop down");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void advSearch_CountryStateCity(String country, String state, String city) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_location);
		rbtn_countryStateCity.click();
		getState(state);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		txt_city.click();
		txt_city.clear();
		txt_city.sendKeys(city);
		System.out.println("Typed the State and City");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(500);
	}

	public void advSearch_Hospital(String hospital) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, rbtn_countryStateCity);
		rbtn_hospital.click();
		getHospital(hospital);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(500);
	}

	public void advSearch_AtNIH() throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, rbtn_countryStateCity);
		rbtn_atNIH.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(500);
	}

	public void advSearch_TrialType(String trialType) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_trialtype);
		switch (trialType) {
		case ("Prevention"):
			cb_trialtype_prevention.click();
			break;
		case ("Treatment"):
			cb_trialtype_treatment.click();
			break;
		case ("Health Services Research"):
			cb_trialtype_health_services_research.click();
			break;
		case ("Supportive Care"):
			cb_trialtype_supportivecare.click();
			break;
		case ("Screening"):
			cb_trialtype_screening.click();
			break;
		case ("Diagnostic"):
			cb_trialtype_diagnostic.click();
			break;
		case ("Other"):
			cb_trialtype_other.click();
			break;
		case ("Basic Science"):
			cb_trialtype_basic_science.click();
			break;
		default:
			cb_trialtype_all.click();
		}
		System.out.println("Trial Type is selected");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
	}

	public void getDrug(String drug) {
		txt_drug.click();
		txt_drug.sendKeys(drug);
		txt_drug.sendKeys(Keys.ENTER);
		System.out.println("Clicked on the drug drop down");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void getTreatment(String treatment) {

		txt_treatment.click();
		txt_treatment.sendKeys(treatment);
		txt_treatment.sendKeys(Keys.ENTER);
		System.out.println("Clicked on the treatment drop down");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void getTrialId(String trialId) {
		txt_trialId.click();
		txt_trialId.sendKeys(trialId);
		// txt_trialId.sendKeys(Keys.ENTER);
		System.out.println("Trial Id entered");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void getTrialInvestigators(String investigator) {
		txt_trialInvestigator.click();
		txt_trialInvestigator.sendKeys(investigator);
		// txt_trialId.sendKeys(Keys.ENTER);
		System.out.println("Trial Investigator entered");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void getLeadOrganization(String leadOrganization) {
		txt_leadOrganization.click();
		txt_leadOrganization.sendKeys(leadOrganization);
		// txt_trialId.sendKeys(Keys.ENTER);
		System.out.println("Lead Organization entered");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void advSearch_Drug(String drug) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_drugtreatment1);
		System.out.println("Drug box is displayed");
		getDrug(drug);
		System.out.println("Drug is entered");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
	}

	public void advSearch_Treatment(String treatment) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_drugtreatment1);
		System.out.println("Drug box is displayed");
		getTreatment(treatment);
		System.out.println("Treatment is entered");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
	}

	public void advSearch_TrialPhase(String trialphase) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_drugtreatment1);
		switch (trialphase) {
		case ("Phase I"):
			cb_trialphase1.click();
			break;
		case ("Phase II"):
			cb_trialphase2.click();
			break;
		case ("Phase III"):
			cb_trialphase3.click();
			break;
		case ("Phase IV"):
			cb_trialphase4.click();
			break;
		default:
			cb_trialphaseAll.click();
		}
		System.out.println("Trial phase is selected");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(500);
	}

	/* Verify advance search for Trial Id */
	public void advSearch_TrialID(String trialId) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_trialPhase);
		System.out.println("Trial Phase box is displayed");
		getTrialId(trialId);
		System.out.println("Trial Id is entered");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
	}

	/* Verify advance search for Trial Investigator */
	public void advSearch_TrialInvestigator(String investigator) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_trialId);
		System.out.println("Trial Investigator box is displayed");
		getTrialInvestigators(investigator);
		System.out.println("Trial Investigator is entered");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
	}

	/* Verify advance search for Lead Organization */
	public void advSearch_LeadOrganization(String leadOrganization) throws InterruptedException {
		FunctionLibrary.scrollIntoview(driver, lgd_trialInvestigator);
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lgd_trialInvestigator);
		System.out.println("Lead Organization box is displayed");
		getLeadOrganization(leadOrganization);
		System.out.println("Lead Organization is entered");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
		Thread.sleep(200);
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		defaultSearch();
	}

}
