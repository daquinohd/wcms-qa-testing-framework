package gov.nci.WebAnalytics;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MegaMenu extends AnalyticsBase {
	/*** MegaMenu web elements ***/
	@FindBy(how = How.CSS, using = "#mega-nav .nav-item-title a")
	WebElement mm_bar_link;
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group a")
	WebElement mm_subnav_title_link;	
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group ul li a")
	WebElement mm_subnav_list_item;	
	@FindBy(how = How.CSS, using = "#mega-nav .mega-menu-scroll.open")
	WebElement mm_expanded_desktop;
	@FindBy(how = How.CSS, using = ".mobile-menu-bar button.menu-btn")
	WebElement mm_mobile_expand_button;
	
	public MegaMenu(){		
	}
	
	// Constructor to initialize the Page objects
	public MegaMenu(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("MegaMenu PageFactory initialized");
	}
	
	/** Browser actions
	* All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	* to fire off analytics events. These actions will populate our list of har objects, which will
	* then be tested.
	*/
	public void clickMegaMenuEn() {
		driver.navigate().to(homePage);
		mm_bar_link.click();
		//mm_subnav_title_link.click();
		//mm_subnav_list_item.click();
	}

	public void clickMegaMenuEs() {
		driver.navigate().to(spanishPage);
		mm_bar_link.click();
		//mm_subnav_title_link.click();
		//mm_subnav_list_item.click();
	}
	
	public void clickMegaMenuSubnav() {
		System.out.println("debug hover");
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 5);		
		action.moveToElement(mm_bar_link);
		action.perform();
		wait.until(ExpectedConditions.visibilityOf(mm_expanded_desktop));
		action.moveToElement(mm_subnav_title_link);
		action.perform();		
		mm_subnav_title_link.click();
		driver.navigate().to(homePage);
	}
	
	public void revealMegaMenuMobile() {
		Resize resize = new Resize(driver);
		resize.resizeToSmall();
		mm_mobile_expand_button.click();
		driver.manage().window().maximize();
	}
	
}
