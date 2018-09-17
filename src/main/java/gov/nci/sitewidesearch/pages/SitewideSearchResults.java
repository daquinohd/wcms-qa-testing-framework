package gov.nci.sitewidesearch.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;
import gov.nci.Utilities.ScrollUtil;

public class SitewideSearchResults extends PageObjectBase {

	/**
	 * Example search terms:
	 * "tumor" = best bet & definition
	 * "live help" = best bet only
	 * "ipilimumab" = definition only
	 * "bovine" = no best bet or definition
	 * 
	 */
	WebDriver driver;	
	
	/**
	 * @param driver
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */	
	public SitewideSearchResults(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
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
	@FindBy(css = ".sitewide-list ul")
	WebElement list_results;
	@FindBy(css = ".sitewide-list ul li a")
	List<WebElement> lnk_list_item;
	@FindBy(css = ".results-pager span.ui-selectmenu-button")
	WebElement btn_results_per;
	@FindBy(css = ".results-pager .pagination")
	WebElement txt_pagination;
	@FindBy(css = "form.sitewide-search-results")
	WebElement form_sw_res_search;
	// TODO: key off something other than these IDs if possible
	@FindBy(how = How.XPATH, using = "//label[@for='ctl34_rblSWRSearchType_0']")
	WebElement btn_search_new;
	@FindBy(how = How.XPATH, using = "//label[@for='ctl34_rblSWRSearchType_1']")
	WebElement btn_search_within;
	@FindBy(css = "form.sitewide-search-results input#ctl34_txtSWRKeyword")
	WebElement input_sw_res_search;
	@FindBy(css = "form.sitewide-search-results input#ctl34_btnSWRTxtSearch")
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

	public String getResultsCount() {
		return getLblResultsCount().getText();
	}
	
	/**************** Sitewide Search Results Page Actions *****************************/
	/**
	 * Clicks the Best Bets link.
	 */
	public void clickBestBets() {
		WebElement bbLink = txt_bestbets.findElement(By.cssSelector(".managed .title-and-desc a"));
		bbLink.click();
 	}

	/**
	 * Clicks the link at a given position.
	 */
	public void clickListItem(int position) {
		lnk_list_item.get(position - 1).click();
 	}

	/**
	 * Click the first result link.
	 */
	public void clickListItem() {
		clickListItem(0);
 	}

	
	/**
	 * Select 'New Search' radio button
	 */
	public void doNewSearch() {
		ScrollUtil.scrollIntoview(driver, input_sw_res_search);
		btn_search_new.click();
	}

	/**
	 * Select 'Search Within Results' radio button
	 */	
	public void selectWithinResults() {
		ScrollUtil.scrollIntoview(driver, input_sw_res_search);
		btn_search_within.click();
	}
	
	/**
	 * Places text in the siteside results search input field.
	 * @param searchText the value to insert in the keyword field.
	 */
	public void setSitewideSearchKeyword(String searchText) {
		ScrollUtil.scrollIntoview(driver, input_sw_res_search);
		input_sw_res_search.sendKeys(searchText);
	}
	
	/**
	 * Clicks the form's search button.
	 */
	public void clickSearchButton() throws MalformedURLException, UnsupportedEncodingException {
		ScrollUtil.scrollIntoview(driver, input_sw_res_search);
		btn_sw_res_search.click();
 	}
	
}
