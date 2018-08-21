package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;

import gov.nci.framework.PageObjectBase;
import gov.nci.Utilities.ConfigReader;

public class AnalyticsPageLoad extends PageObjectBase {

	public WebDriver driver;
	protected ConfigReader config = new ConfigReader();	
	
	// Constructor to initialize the page object	
	public AnalyticsPageLoad(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
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
		driver.navigate().to(config.getPageURL("HomePage"));
		driver.navigate().to(config.getPageURL("LandingPage"));
		driver.navigate().to(config.getPageURL("CTHPPatient"));
		driver.navigate().to(config.getPageURL("BlogPostPage"));
	}

	public void gotoHomePage() {
		System.out.println("Load home page.");		
		driver.navigate().to(config.getPageURL("HomePage"));
	}
	
	public void gotoBlogPostPage() {
		System.out.println("Load blog post page.");
		driver.navigate().to(config.getPageURL("BlogPostPage"));
	}
	
	public void gotoBlogSeriesPage() {
		System.out.println("Load blog series page.");
		driver.navigate().to(config.getPageURL("BlogSeriesPage"));
	}	
	
	public void gotoCTHPPatient() {
		System.out.println("Load CTHP patient page.");
		driver.navigate().to(config.getPageURL("CTHPPatient"));
	}
	
	public void gotoCTHPHP() {
		System.out.println("Load CTHP HP page.");
		driver.navigate().to(config.getPageURL("CTHPHP"));
	}
	
	public void gotoInnerPage() {
		System.out.println("Load home page.");
		driver.navigate().to(config.getPageURL("InnerPage"));
	}
	
	public void gotoLandingPage() {
		System.out.println("Load Landing page.");
		driver.navigate().to(config.getPageURL("LandingPage"));
	}
	
	public void gotoPDQPage() {
		System.out.println("Load PDQ page.");
		driver.navigate().to(config.getPageURL("PDQPage"));
	}
	
	public void gotoTopicPage() {
		System.out.println("Load topic page.");
		driver.navigate().to(config.getPageURL("TopicPage"));
	}
	
	public void gotoSpanishPage() {
		System.out.println("Load Spanish page.");
		driver.navigate().to(config.getPageURL("SpanishPage"));
	}	
	
	public void gotoAppModulePage() {
		System.out.println("Load appmodule (dictionary) page.");
		driver.navigate().to(config.getPageURL("AppModulePage"));
	}
		
	public void gotoBasicSearchPage() {
		System.out.println("Load CTS basic search page.");
		driver.navigate().to(config.getPageURL("BasicClinicalTrialSearchURL"));
	}
	
	public void gotoAdvSearchPage() {
		System.out.println("Load CTS advanced search page.");
		driver.navigate().to(config.getPageURL("AdvanceSearchPageURL"));
	}
	
	public void gotoResultsPage() {	
		System.out.println("Load CTS results page.");
		driver.navigate().to(config.getPageURL("ResultsPageURL"));
	}
	
	public void goHomeAndBack() {
		System.out.println("Go away from home page then back.");
		driver.navigate().to(config.getPageURL("HomePage"));
		driver.navigate().to(config.getPageURL("SpanishPage"));
		driver.navigate().back();
		driver.navigate().refresh();
	}
	
}