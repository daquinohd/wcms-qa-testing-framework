package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;

public class PageLoad extends PageObjectBase {

	// Local driver object
	public WebDriver driver;
	
	// Constructor to initialize the page object	
	public PageLoad(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.print("PageFactory initialized for load events: ");
	}
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	public void gotoMultiplePages() {
		System.out.println("Load multiple different page types.");		
		driver.navigate().to(Nav.homePage);
		driver.navigate().to(Nav.landingPage);
		driver.navigate().to(Nav.cthpPatient);
		driver.navigate().to(Nav.cthpHP);
		driver.navigate().to(Nav.topicPage);
		driver.navigate().to(Nav.appModulePage);
		driver.navigate().to(Nav.blogSeriesPage);
	}

	public void gotoHomePage() {
		System.out.println("Load home page.");		
		driver.navigate().to(Nav.homePage);
	}
	
	public void gotoBlogPostPage() {
		System.out.println("Load blog post page.");
		driver.navigate().to(Nav.blogPostPage);
	}
	
	public void gotoBlogSeriesPage() {
		System.out.println("Load blog series page.");
		driver.navigate().to(Nav.blogSeriesPage);
	}
	
	public void gotoCTHPPatient() {
		System.out.println("Load CTHP patient page.");
		driver.navigate().to(Nav.cthpPatient);
	}
	
	public void gotoCTHPHP() {
		System.out.println("Load CTHP HP page.");
		driver.navigate().to(Nav.cthpHP);	
	}
	
	public void gotoInnerPage() {
		System.out.println("Load home page.");
		driver.navigate().to(Nav.innerPage);
	}
	
	public void gotoLandingPage() {
		System.out.println("Load Landing page.");
		driver.navigate().to(Nav.landingPage);
	}
	
	public void gotoPDQPage() {
		System.out.println("Load PDQ page.");
		driver.navigate().to(Nav.pdqPage);
	}
	
	public void gotoTopicPage() {
		System.out.println("Load topic page.");
		driver.navigate().to(Nav.topicPage);
	}
	
	public void gotoSpanishPage() {
		System.out.println("Load Spanish page.");
		driver.navigate().to(Nav.spanishPage);
	}
	
	public void gotoAppModulePage() {
		System.out.println("Load appmodule (dictionary) page.");
		driver.navigate().to(Nav.appModulePage);
	}
	
	public void gotoBasicSearchPage() {
		System.out.println("Load CTS basic search page.");
		driver.navigate().to(Nav.basicSearchPage);
	}
	
	public void gotoAdvSearchPage() {
		System.out.println("Load CTS advanced search page.");
		driver.navigate().to(Nav.advSearchPage);
	}
	
	public void gotoResultsPage() {	
		System.out.println("Load CTS results page.");
		driver.navigate().to(Nav.resultsPage);
	}
	
	public void goHomeAndBack() {
		System.out.println("Go away from home page then back.");
		driver.navigate().to(Nav.homePage);
		driver.navigate().to(Nav.spanishPage);
		driver.navigate().back();
		driver.navigate().refresh();
	}
	
}
