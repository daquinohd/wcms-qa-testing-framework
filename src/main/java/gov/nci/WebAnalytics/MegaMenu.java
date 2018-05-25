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
	WebElement mm_subnav_header;	
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group ul li a")
	WebElement mm_subnav_li;	
	@FindBy(how = How.LINK_TEXT , using = "Cancer Disparities")
	WebElement mm_subnav_li_text;
	@FindBy(how = How.CSS, using = ".mobile-menu-bar button.menu-btn")
	WebElement mm_reveal_mobile;
  //@FindBy(how = How.CSS, using = "#mega-nav a.open")
	@FindBy(how = How.CSS, using = "#mega-nav .mega-menu-scroll.open")
	WebElement mm_reveal_desktop;
	
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
	public void clickMMBarEn() {
		driver.navigate().to(homePage);
		mm_bar_link.click();
	}

	public void clickMMBarEs() {
		driver.navigate().to(spanishPage);
		mm_bar_link.click();
	}
	
	public void clickMMSubnavHeader() {
		driver.navigate().to(homePage);
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 5);		
		action.moveToElement(mm_bar_link);
		action.perform();
		wait.until(ExpectedConditions.visibilityOf(mm_reveal_desktop));
		mm_subnav_header.click();
	}
	
	public void clickMMSubnavLi() {
		driver.navigate().to(homePage);
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 5);		
		action.moveToElement(mm_bar_link);
		action.perform();
		wait.until(ExpectedConditions.visibilityOf(mm_reveal_desktop));
		mm_subnav_li_text.click();
	}	

	public void revealMegaMenuDesktop() {
		System.out.println("-- Begin debugging hover/expand megamenu actions --");
		driver.navigate().to(homePage);
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 5);		
		action.moveToElement(mm_bar_link);
		action.perform();
		AnalyticsBase.goSleepy(5);
		driver.navigate().refresh();
		System.out.println("-- End debugging hover/expand megamenu actions --");		
	}
	
	public void revealMegaMenuMobile() {
		Resize resize = new Resize(driver);
		resize.toSmall();
		mm_reveal_mobile.click();
		driver.manage().window().maximize();
	}
	
}
