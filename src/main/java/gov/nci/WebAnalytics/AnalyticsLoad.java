package gov.nci.WebAnalytics;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsLoad {
	
	public static final String S_CODE_NAME = "s_code.js";
	public static final String S_ACCOUNT = "s_account";
	public static final String NCI_FUNCTIONS_NAME = "NCIAnalytics";

	WebDriver driver;

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.ID_OR_NAME, using = "siteSearchForm")
	WebElement siteWideSearch;
	
	// Constructor - Initializing the Page objects
	public AnalyticsLoad(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Get inner text of element containing s_account
	public String getSitewideSearchWAFunction() {
		String onSubmit = siteWideSearch.getAttribute("onsubmit");
		System.out.println("== start debug ==");
		System.out.println("getSitewideSearchWAFunction() returns: " + onSubmit);
		System.out.println("== end debug ==");
		return onSubmit;
	}
	
	// Click mega menu
	public void clickMegaMenu() {
		//megaMenuLink.click();
	}

}
