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
	public void gotoPageTypes() {
		System.out.print("Navigate to several page types");		
		driver.navigate().to(WANav.homePage);
		driver.navigate().to(WANav.landingPage);
		driver.navigate().to(WANav.cthpPatient);
		driver.navigate().to(WANav.cthpHP);
		driver.navigate().to(WANav.appModulePage);
		driver.navigate().to(WANav.blogSeriesPage);
	}

	public void gotoHomePage() {
		driver.navigate().to(WANav.homePage	);
	}
	
	public void gotoBlogPostPage() {
		driver.navigate().to(WANav.blogPostPage);
	}
	
	public void gotoBlogSeriesPage() {
		driver.navigate().to(WANav.blogSeriesPage);
	}
	
	public void gotoCTHPPatient() {
		driver.navigate().to(WANav.cthpPatient);
	}
	
	public void gotoCTHPHP() {
		driver.navigate().to(WANav.cthpHP);	
	}
	
	public void gotoInnerPage() {
		driver.navigate().to(WANav.innerPage);
	}
	
	public void gotoLandingPage() {
		driver.navigate().to(WANav.landingPage);
	}
	
	public void gotoPDQPage() {
		driver.navigate().to(WANav.pdqPage);
	}
	
	public void gotoTopicPage() {
		driver.navigate().to(WANav.topicPage);
	}
	
	public void gotoSpanishPage() {
		driver.navigate().to(WANav.spanishPage);
	}
	
	public void gotoAppModulePage() {
		driver.navigate().to(WANav.appModulePage);
	}
	
	public void gotoBasicSearchPage() {
		driver.navigate().to(WANav.basicSearchPage);
	}
	
	public void gotoAdvSearchPage() {
		driver.navigate().to(WANav.advSearchPage);
	}
	
	public void gotoResultsPage() {	
		driver.navigate().to(WANav.resultsPage);
	}
	
	public void goHomeAndBack() {
		// Home page
		driver.navigate().to(WANav.homePage);
		driver.navigate().to(WANav.spanishPage);
		driver.navigate().back();
		driver.navigate().refresh();
	}	
	
	/// Click around pages
	public void doPageLoadActions() throws RuntimeException {
		System.out.println("Begin PageLoad actions");
		gotoPageTypes();
		goHomeAndBack();		
		System.out.println("End PageLoad actions");
	}
	
}
