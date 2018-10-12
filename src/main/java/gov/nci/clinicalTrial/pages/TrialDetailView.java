package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import gov.nci.Utilities.ClickUtil;
import gov.nci.Utilities.ScrollUtil;
import gov.nci.clinicalTrial.common.Delighter;
import gov.nci.framework.AutoSuggestHelper;
import gov.nci.framework.ElementChange;

public class TrialDetailView extends ClinicalTrialPageObjectBase {

	public static final String SEL_START_OVER = ".cts-start-over a";
	
	WebDriver driver;
	Actions action;

	/*************** Basic Search Page UI Elements **********************/	
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]")
	private WebElement text_HeaderText;
	@FindBy(how = How.CSS, using = ".cts-results-info a")
	private WebElement lnk_backToResults;
	@FindBy(how = How.CSS, using = SEL_START_OVER)
	private WebElement lnk_startOver;
	@FindBy(how = How.CSS, using = ".accordion-controls .open-all")
	private WebElement btn_openAll;
	@FindBy(how = How.CSS, using = ".accordion-controls .close-all")
	private WebElement btn_closeAll;
	@FindBy(how = How.CSS, using = ".cts-share a.print")
	private WebElement lnk_print;
	@FindBy(how = How.CSS, using = ".cts-share a.email")
	private WebElement lnk_email;
	
	// Constructor - Initializing the Page objects
	public TrialDetailView(WebDriver driver, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException {
		super(driver, decorator);
		this.driver = driver;
		this.action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickOpenAll() {
		btn_openAll.click();
	}

	public void clickCloseAll() {
		btn_closeAll.click();
	}

	public void clickBackToResults() {
		lnk_backToResults.click();
	}

	public void clickStartOver() {
		lnk_startOver.click();
	}

	public void clickStartOverNoNav() {
		ElementChange.removeHref(driver, SEL_START_OVER);
		clickStartOver();
	}
	
	public WebElement getHeaderText() {
		return text_HeaderText;
	}

	public WebElement getSection(String sectionId) {
		WebElement section = driver.findElement(By.cssSelector("#trial-" + sectionId + " h2"));
		return section;
	}	

	public void clickSection(String sectionId) {
		WebElement section = getSection(sectionId);
		ScrollUtil.scrollIntoview(this.driver, section);
		section.click();
	}
	
	public void clickComponent(String selector) {
		WebElement element = driver.findElement(By.cssSelector(selector));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	public void clickPrintLink() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.print=function(){};");
		ClickUtil.forceClick(driver, lnk_print);
	}

	public void clickEmailLink() {
		ElementChange.removeHref(driver, "a.email");
		ClickUtil.forceClick(driver, lnk_email);
	}

}