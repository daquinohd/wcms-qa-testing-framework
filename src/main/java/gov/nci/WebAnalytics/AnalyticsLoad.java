package gov.nci.WebAnalytics;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsLoad {
	
	public static final String S_CODE_NAME = "Find NCI-Supported Clinical Trials";
	public static final String S_ACCOUNT = "s_account";
	public static final String NCI_FUNCTIONS_NAME = "Clinical Trials Search Results";

	WebDriver driver;

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.XPATH, using = "//*[contains(text(), 's_account')]")
	WebElement SAccount;
	
	// Constructor - Initializing the Page objects
	public AnalyticsLoad(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Get inner text of element containing s_account
	public String getSAccountText() {		
		System.out.println("== start debug ==");
		System.out.println("s_account element: " + SAccount.getText());
		System.out.println("== end debug ==");				
		return SAccount.getText();
	}
	
	// Click mega menu
	public void clickMegaMenu() {
		//megaMenuLink.click();
	}

}
