package gov.nci.WebAnalytics;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsClickEvents extends AnalyticsBase {	

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.ID_OR_NAME, using = "mega-nav")
	WebElement megaMenu;
	
	// Constructor - Initializing the Page objects
	public AnalyticsClickEvents(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}
	
	// Click mega menu
	public void clickMegaMenu() {
		megaMenu.click();
	}

}
