package gov.nci.webanalytics;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import gov.nci.framework.PageObjectBase;
import gov.nci.Utilities.ConfigReader;

public class AnalyticsPageLoad extends PageObjectBase {

	public WebDriver driver;
	protected ConfigReader config = new ConfigReader();	

	/**************** Sitewide Search Results Page Elements *****************************/
	@FindBy(how = How.XPATH, using = "//meta[@property='og:title']")
	WebElement meta_title;
	@FindBy(how = How.XPATH, using = "//meta[@name='content-language']")
	WebElement meta_language;
	@FindBy(how = How.XPATH, using = "//meta[@name='dcterms.coverage']")
	WebElement meta_coverage;
	@FindBy(how = How.XPATH, using = "//meta[@name='dcterms.subject']")
	WebElement meta_subject;
	@FindBy(how = How.XPATH, using = "//meta[@name='dcterms.isPartOf']")
	WebElement meta_is_part_of;
	@FindBy(how = How.XPATH, using = "//meta[@name='dcterms.issued']")
	WebElement meta_issued;
	
	public String getMetaTitle() {
		return meta_title.getAttribute("content");
	}

	public String getMetaLanguage() {
		return meta_language.getAttribute("content");
	}
	
	public String getMetaCoverage() {
		return meta_coverage.getAttribute("content");
	}

	public String getMetaSubject() {
		return meta_subject.getAttribute("content");
	}

	public String getMetaIsPartOf() {
		return meta_is_part_of.getAttribute("content");
	}

	public String getMetaIssued() {
		return meta_issued.getAttribute("content");
	}

	
	
	// Constructor to initialize the page object	
	public AnalyticsPageLoad(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Get full name for content language.
	 * @return language (String)
	 */
	public String getLanguageName() {
		String lang = this.getMetaLanguage();
		switch(lang) {
			case "en" : return "english";
			case "es" : return "spanish";
			default: return "english";				
		}
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