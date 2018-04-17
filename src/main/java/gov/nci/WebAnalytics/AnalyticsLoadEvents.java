package gov.nci.WebAnalytics;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsLoadEvents extends AnalyticsBase {	

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.ID_OR_NAME, using = "siteSearchForm")
	WebElement siteWideSearch;
	
	// Constructor - Initializing the Page objects
	public AnalyticsLoadEvents(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Get inner text of element containing s_account
	public String getSitewideSearchWAFunction() {
		String onSubmit = siteWideSearch.getAttribute("onsubmit");
		return onSubmit;
	}

}
