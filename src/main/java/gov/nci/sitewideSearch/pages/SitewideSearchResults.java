package gov.nci.sitewideSearch.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;

public class SitewideSearchResults extends PageObjectBase {

	public SitewideSearchResults(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		PageFactory.initElements(driver, this);
		System.out.println("SitewideSearchForm initialized.");
	}
	
}
