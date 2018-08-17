package gov.nci.sitewidesearch.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;

public class SitewideSearchResults extends PageObjectBase {

	/**
	 * Represents SitewideSearchResults page
	 * 
	 * Example search terms:
	 * "tumor" = best bet & definition
	 * "live help" = best bet only
	 * "ipilimumab" = definition only
	 * "bovine" = no best bet or definition
	 * 
	 * @param driver
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public SitewideSearchResults(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		PageFactory.initElements(driver, this);
	}	

	/**************** Sitewide Search Results Page Elements *****************************/
	@FindBy(css = ".featured.sitewide-results")
	WebElement txt_bestbets;
	@FindBy(css = "#best-bet-definition")
	WebElement txt_result_definition;
	@FindBy(css = ".sitewide-results .term span")
	WebElement lbl_search_term;
	@FindBy(css = ".sitewide-results .results-num")
	WebElement lbl_results_count;
	@FindBy(css = ".sitewide-results .results-range")	
	WebElement lbl_results_range;
	@FindBy(css = ".results-pager span.ui-selectmenu-button")
	WebElement btn_results_per;
	@FindBy(css = ".results-pager .pagination")
	WebElement txt_pagination;
	@FindBy(css = "form.sitewide-search-results")
	WebElement form_sw_res_search;
	// TODO: key off something other than these IDs
	@FindBy(css = "form.sitewide-search-results input#ctl34_rblSWRSearchType_0")
	WebElement btn_search_new;
	@FindBy(css = "form.sitewide-search-results input#ctl34_rblSWRSearchType_1")
	WebElement btn_search_within;
	@FindBy(css = "#siteSearchForm input#swKeyword")
	WebElement input_sw_res_search;
	@FindBy(css = "#siteSearchForm button#sitesearch")
	WebElement btn_sw_res_search;
	
	public WebElement getTxtBestbets() {
		return txt_bestbets;
	}

	public WebElement getTxtResultDefinition() {
		return txt_result_definition;
	}
	
	public WebElement getLblSearchTerm() {
		return lbl_search_term;
	}

	public WebElement getLblResultsCount() {
		return lbl_results_count;
	}
	
	public WebElement getLblResultsRange() {
		return lbl_results_range;
	}
	
	public WebElement getBtnResultsPer() {
		return btn_results_per;
	}
	
	public WebElement getTxtPagination() {
		return txt_pagination;
	}
	
	public WebElement getFormSwResSearch() {
		return form_sw_res_search;
	}
	
	public WebElement getBtnSearchNew() {
		return btn_search_new;
	}
	
	public WebElement getBtnSearchWithin() {
		return btn_search_within;
	}
	
	public WebElement getInputSwResSearch() {
		return input_sw_res_search;
	}
	
	public WebElement getBtnSwResSearch() {
		return btn_sw_res_search;		
	}
	
	/**
	 * Places text in the siteside results search input field.
	 * @param searchText the value to insert in the keyword field.
	 */
	public void setSitewideSearchKeyword(String searchText) {
		input_sw_res_search.sendKeys(searchText);
	}
	
	/**
	 * Clicks the form's search button.
	 */
	public void clickSearchButton() throws MalformedURLException, UnsupportedEncodingException {
 		expectUrlChange(() ->{
			btn_sw_res_search.click();
		});
 	}			
	
}
