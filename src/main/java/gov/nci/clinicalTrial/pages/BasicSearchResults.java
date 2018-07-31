package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.clinicalTrial.common.Checkbox;
import gov.nci.commonobjects.Pager;

public class BasicSearchResults extends ClinicalTrialPageObjectBase {

	// UI Elements
	@FindBy(how = How.CSS, using = ".cts-results-label")
	WebElement lbl_ResultsCount;

	// Select all checkboxes
	@FindBy(how = How.CSS, using = "#checkAllTop")
	WebElement topSelectAll;
	@FindBy(how = How.CSS, using = "checkAllLower")
	WebElement bottomSelectAll;


	// Results List display area
	@FindBy(how = How.CSS, using = ".cts-results-container")
	WebElement resultsArea;

	private Pager pagerControl;

	public BasicSearchResults(WebDriver browser) throws MalformedURLException, UnsupportedEncodingException {
		this(browser, null);
	}

	public BasicSearchResults(WebDriver browser, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException {
		super(browser, decorator);
		PageFactory.initElements(browser, this);

		pagerControl = new Pager(browser);
	}

	public String getResultsCountText() {
		return lbl_ResultsCount.getText();
	}

	public boolean getResultsCountVisible() {
		return lbl_ResultsCount.isDisplayed();
	}

	public boolean getResultsListIsVisible() {
		return resultsArea.isDisplayed();
	}

	public Pager getPager() {
		return pagerControl;
	}

	public Checkbox getUpperSelectAll(){
		throw new RuntimeException("Not Implmented");
	}

	public Checkbox getLowerSelectAll() {
		throw new RuntimeException("Not Implmented");
	}


	@FindBy(how=How.XPATH, using=".//div[@class='cts-checkbox checkbox']")
	List <WebElement> check_SearchTrials;

	@FindBy(how=How.XPATH, using=".//input[@type='submit']")
	List <WebElement> btn_PrintSelected;

	public void selectCheckBox(){

		if (!((WebElement)check_SearchTrials.get(1)).isSelected())
		{
			((WebElement)check_SearchTrials.get(1)).click();
		}

		System.out.println("Checkbox selected");
	}

	public void clickPrint(){

		((WebElement) btn_PrintSelected.get(0)).click();
	}
}
