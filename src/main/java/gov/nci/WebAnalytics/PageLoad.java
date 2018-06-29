package gov.nci.WebAnalytics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageLoad extends AnalyticsBase {

	// Local driver object
	public WebDriver driver;
	
	// Constructor to initialize the page object	
	public PageLoad(WebDriver driver) {
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
		driver.navigate().to(WANav.homePage);
		driver.navigate().to(WANav.landingPage);
		driver.navigate().to(WANav.cthpPatient);
		driver.navigate().to(WANav.cthpHP);
		driver.navigate().to(WANav.topicPage);
		driver.navigate().to(WANav.appModulePage);
		driver.navigate().to(WANav.blogSeriesPage);
	}

	public void gotoHomePage() {
		System.out.println("Load home page.");		
		driver.navigate().to(WANav.homePage	);
	}
	
	public void gotoBlogPostPage() {
		System.out.println("Load blog post page.");
		driver.navigate().to(WANav.blogPostPage);
	}
	
	public void gotoBlogSeriesPage() {
		System.out.println("Load blog series page.");
		driver.navigate().to(WANav.blogSeriesPage);
	}
	
	public void gotoCTHPPatient() {
		System.out.println("Load CTHP patient page.");
		driver.navigate().to(WANav.cthpPatient);
	}
	
	public void gotoCTHPHP() {
		System.out.println("Load CTHP HP page.");
		driver.navigate().to(WANav.cthpHP);	
	}
	
	public void gotoInnerPage() {
		System.out.println("Load home page.");
		driver.navigate().to(WANav.innerPage);
	}
	
	public void gotoLandingPage() {
		System.out.println("Load Landing page.");
		driver.navigate().to(WANav.landingPage);
	}
	
	public void gotoPDQPage() {
		System.out.println("Load PDQ page.");
		driver.navigate().to(WANav.pdqPage);
	}
	
	public void gotoTopicPage() {
		System.out.println("Load topic page.");
		driver.navigate().to(WANav.topicPage);
	}
	
	public void gotoSpanishPage() {
		System.out.println("Load Spanish page.");
		driver.navigate().to(WANav.spanishPage);
	}
	
	public void gotoAppModulePage() {
		System.out.println("Load appmodule (dictionary) page.");
		driver.navigate().to(WANav.appModulePage);
	}
	
	public void gotoBasicSearchPage() {
		System.out.println("Load CTS basic search page.");
		driver.navigate().to(WANav.basicSearchPage);
	}
	
	public void gotoAdvSearchPage() {
		System.out.println("Load CTS advanced search page.");
		driver.navigate().to(WANav.advSearchPage);
	}
	
	public void gotoResultsPage() {	
		System.out.println("Load CTS results page.");
		driver.navigate().to(WANav.resultsPage);
	}
	
	public void goHomeAndBack() {
		System.out.println("Go away from home page then back.");
		driver.navigate().to(WANav.homePage);
		driver.navigate().to(WANav.spanishPage);
		driver.navigate().back();
		driver.navigate().refresh();
	}
	
}
