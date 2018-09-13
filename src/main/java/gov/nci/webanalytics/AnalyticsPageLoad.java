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
	//protected ConfigReader config = new ConfigReader();	

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
	@FindBy(how = How.XPATH, using = "//span[@data-basiccts-searchparam='n']")
	WebElement txt_dlp_total;
	
	// Constructor to initialize the page object	
	public AnalyticsPageLoad(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
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

	public String getDynamiListingTotal() {
		return txt_dlp_total.getText();
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
	
}