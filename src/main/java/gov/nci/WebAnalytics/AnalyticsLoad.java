package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsLoad extends AnalyticsBase {	

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.ID_OR_NAME, using = "siteSearchForm")
	WebElement siteWideSearch;
	
	public AnalyticsLoad() {}
	
	// Constructor to extend AnalyticsBase
	public AnalyticsLoad(String beaconUrl) {
		super(beaconUrl);
	}	
	
	// Constructor - Initializing the Page objects
	public AnalyticsLoad(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Get inner text of element containing s_account
	public String getSitewideSearchWAFunction() {
		String onSubmit = siteWideSearch.getAttribute("onsubmit");
		return onSubmit;
	}

	
	/**
	 * Get a list of beacon URLs fired off for load events
	 * @param urlList
	 * @return
	 * @throws MalformedURLException
	 */
	public static List<AnalyticsLoad> getLoadBeacons(List<String> urlList) {
				
		List<AnalyticsLoad> loadBeacons = new ArrayList<AnalyticsLoad>();		
		AnalyticsBase analytics = new AnalyticsBase();

		for(String url : urlList)
		{
			// If this doesn't have the "Link Type" param ('pe'), add to list of load beacons
			List<NameValuePair> params = analytics.buildParamsList(URI.create(url));
			if(!analytics.hasParam(params, "pe")) {
				loadBeacons.add(new AnalyticsLoad(url));
			}
		}

		System.out.println("Total load beacons: " + loadBeacons.size());		
		return loadBeacons;
	}	
}
