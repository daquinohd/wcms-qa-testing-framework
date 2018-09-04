package gov.nci.commonobjects;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gov.nci.framework.PageObjectBase;

public class MegaMenu extends PageObjectBase{
	
	// Local driver object and actions
	private WebDriver driver;	
	private Actions action;
	private WebDriverWait wait;

	private Nav Nav;
	
	// Constructor to initialize the page object
	public MegaMenu(WebDriver driver, String environment) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 5);
		
		Nav = new Nav(environment);

		PageFactory.initElements(driver, this);
		System.out.print("MegaMenu PageFactory initialized: ");
	}

	/** Web elements
	* These are the elements that make up our page object
	*/
	// TODO: Figure out why FindBy(linkText) is so finnicky in Firefox
	// TODO: Helper function for wait.until(ExpectedConditions.visibilityOf())
	@FindBy(css = "#mega-nav .nav-item-title a")
	WebElement mm_bar_link;
	@FindBy(css = "#mega-nav .sub-nav-group a")
	WebElement mm_subnav_header;	
	@FindBy(css = "#mega-nav .sub-nav-group ul li a")
	WebElement mm_subnav_li;
	@FindBy(linkText = "What Is Cancer")
	WebElement mm_subnav_li_text;
	@FindBy(css = ".mobile-menu-bar button.menu-btn")
	WebElement mm_reveal_mobile;
	@FindBy(css = "#mega-nav .mega-menu-scroll.open")
	WebElement mm_reveal_desktop;	
	
	/** Browser actions
	* All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	* to fire off analytics events. These actions will populate our list of har objects, which will
	* then be tested.
	*/
	public void clickMMBarEn() {
		System.out.println("Click megamenu bar (English)");
		mm_bar_link.click();
	}

	public void clickMMBarEs() {
		System.out.println("Click megamenu bar (Spanish)");
		mm_bar_link.click();
	}
	
	public void clickMMSubnavHeader() {
		System.out.println("Click megamenu subnav header");
		action.moveToElement(mm_bar_link).perform();
		wait.until(ExpectedConditions.visibilityOf(mm_subnav_header));
		mm_subnav_header.click();
	}
	
	public void clickMMSubnavLi() {
		System.out.println("Click megamenu subnav list item");		
		action.moveToElement(mm_bar_link).perform();
		wait.until(ExpectedConditions.visibilityOf(mm_subnav_li_text));
		mm_subnav_li_text.click();
	}

	public void revealMegaMenuDesktop() {
		System.out.println("Expand megamenu on desktop");
		action.moveToElement(mm_bar_link).perform();
		action.pause(2000).perform();
		wait.until(ExpectedConditions.visibilityOf(mm_subnav_li_text));
	}
	
	public void revealMegaMenuMobile() {
		System.out.println("Expand megamenu on mobile");		
		driver.manage().window().setSize(Resize.small);
		mm_reveal_mobile.click();
	}
	
}
