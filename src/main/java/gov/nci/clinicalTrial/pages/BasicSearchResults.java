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

	private Pager pagerControl;
	private Checkbox topSelectAllControl;
	private Checkbox bottomSelectAllControl;

	public BasicSearchResults(WebDriver browser) throws MalformedURLException, UnsupportedEncodingException {
		this(browser, null);
	}

	public BasicSearchResults(WebDriver browser, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException {
		super(browser, decorator);
		PageFactory.initElements(browser, this);

		pagerControl = new Pager(browser);
		topSelectAllControl = new Checkbox(browser, By.cssSelector("#checkAllTop"));
		bottomSelectAllControl = new Checkbox(browser, By.cssSelector("#checkAllLower"));
	}

	public String getResultsCountText() {
		return lbl_ResultsCount.getText();
	}

	public boolean getResultsCountVisible() {
		return lbl_ResultsCount.isDisplayed();
	}

	public Pager getPager() {
		return pagerControl;
	}

	public Checkbox getUpperSelectAll(){
		return topSelectAllControl;
	}

	public Checkbox getLowerSelectAll() {
		return bottomSelectAllControl;
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
