package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsClick extends AnalyticsBase {	

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.CSS, using = "#mega-nav a")
	WebElement mega_menu_link;
	@FindBy(how = How.CSS, using = ".feature-card")
	WebElement feature_card;

	public String linkType;
	public String linkName;
	public String linkUrl;	
	
	public AnalyticsClick() {
		super();
		linkType = "";
		linkName = "";
		linkUrl = "";
	}
	
	// Constructor to extend AnalyticsBase
	public AnalyticsClick(String beaconUrl) throws MalformedURLException {
		super(beaconUrl);
		linkType = getLinkType(params);
		linkName = getLinkName(params);
		linkUrl = getLinkUrl(params);		
	}
	
	/**
	 * Get "Link Type" value (pe)(
	 * @param parms
	 * @return
	 */
	public String getLinkType(List<NameValuePair> parms) {
		String rtn = "";		
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("pe")) {
				rtn = param.getValue();
				break;
			}
		}
		return rtn;
	}	

	/**
	 * Get "Link Name" value (pev2)(
	 * @param parms
	 * @return
	 */	
	public String getLinkName(List<NameValuePair> parms) {
		String rtn = "";		
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("pev2")) {
				rtn = param.getValue();
				break;
			}
		}
		return rtn;
	}	

	/**
	 * Get "Link URL" value (pev1)(
	 * @param parms
	 * @return
	 */		
	public String getLinkUrl(List<NameValuePair> parms) {
		String rtn = "";		
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("pev1")) {
				rtn = param.getValue();
				break;
			}
		}
		return rtn;
	}	
	
	// Constructor to initialize the Page objects
	public AnalyticsClick(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}	
	
	public void clickFeatureCard() {
		feature_card.click();
	}	
	
	// Click mega menu
	public void clickMegaMenu() {
		mega_menu_link.click();
	}
	
}
