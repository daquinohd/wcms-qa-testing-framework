package gov.nci.WebAnalytics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MegaMenu extends AnalyticsClick {

	public MegaMenu(){		
	}
	
	// Constructor to initialize the Page objects
	public MegaMenu(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}	
	
	/*** MegaMenu web elements ***/
	@FindBy(how = How.CSS, using = "#mega-nav a")
	WebElement mega_menu_link;	
	
	
	/*** Browser actions ***/
	private void clickMegaMenu() {
		System.out.println("Clicked clickMegaMenu");
		mega_menu_link.click();
	}

	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	public void doMegaMenuActions() throws RuntimeException {
		clickMegaMenu();
	}
	
}
