package gov.nci.sitewideSearch.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gov.nci.framework.PageObjectBase;

public class SiteWideSearchForm extends PageObjectBase {
	
	// Local driver object and actions
	private Actions action;
	private WebDriverWait wait;
	
	// Constructor to initialize the page object
	public SiteWideSearchForm(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 5);		
		PageFactory.initElements(driver, this);
	}

	/**************** Sitewide Search Form Elements *****************************/
	@FindBy(css = "#siteSearchForm")
	WebElement sw_search_form;
	@FindBy(css = "#siteSearchForm label")
	WebElement sw_search_form_label;
	@FindBy(css = "#siteSearchForm input#swKeyword")
	WebElement sw_search_form_input;
	@FindBy(how = How.XPATH, using = ".//input[@id='swKeyword']")
	private WebElement txt_sws;
	
	public WebElement getSitewideSearchForm() {
		return sw_search_form;
	}

	public WebElement getSitewideSearchFormLabel() {
		return sw_search_form_label;
	}

	public WebElement getSitewideSearchFormInput() {
		return sw_search_form_input;
	}

	public WebElement getSitewideSearchFormInputXPath() {
		return txt_sws;
	}
	
	/**
	 * Places text in the "Cancer type or keyword" field, but doesn't select anything
	 * from autocmplete.
	 *
	 * @param searchText the value to insert in the keyword field.
	 */
	public void setSitewideSearchKeyword(String searchText) {
		txt_sws.sendKeys(searchText);
	}
	
}
