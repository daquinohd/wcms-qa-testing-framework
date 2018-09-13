package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.ElementChange;

public class AdvanceSearchResults extends ClinicalTrialPageObjectBase {
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

	@FindBy(how = How.XPATH, using = "//div[@class='cts-results-container']")
	WebElement ctn_ResultsContainer;

	@FindBy(how = How.XPATH, using = "//input[@type='checkbox']")
	List<WebElement> checkBoxes;

	@FindBy(how = How.XPATH, using = "//a[@class='protocol-abstract-link']")
	List<WebElement> lnk_resultLink;

	@FindBy(how = How.CSS, using = ".cts-checkbox.checkbox label")
	List<WebElement> checkBoxes3;

	/********** Constructor: Initializing the Page Objects *****************/
	public AdvanceSearchResults(WebDriver driver, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException{
		super(driver, decorator);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/********** Methods: Performing actions on the page *****************/
	public void getAdvanceSearchResultsPageTitle() {
		driver.getTitle();
	}

	public WebElement getSearchCriteriaBox() {
		return box_SearchCriteria;
	}

	public void clickStartOverNoNav() {
		ElementChange.removeHref(driver, ".cts-start-over a");
		lnk_StartOver.click();
	}

	public void clickStartOver() {
		lnk_StartOver.click();
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

	public int getCheckBoxesNumber() {
		System.out.println("Number of checkboxes: " + checkBoxes.size());
		return checkBoxes.size();

	}

	public void clickOnCheckBox() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", checkBoxes.get(0));
		System.out.println("Checkboxes: " + checkBoxes.get(1));
	}

	public void selectCheckboxByIndex(int index) {
		try {
			System.out.println("Selected list item #" + index);
			checkBoxes.get(index).click();
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Invalid arraylist index: " + index);
		}
	}
	
	public void clearCheckBoxes() {
		ElementChange.uncheckAll(driver);
	}
	
	public List<WebElement> getResultsLinks() {
		System.out.println("First result link: " + lnk_resultLink.get(0).getText());
		System.out.println("Number of result links: " + lnk_resultLink.size());
		return lnk_resultLink;
	}

	public void clickResultLinkByIndex(int index) {
		try {
			lnk_resultLink.get(index).click();
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Invalid arraylist index: " + index);
		}
	}
	
}
