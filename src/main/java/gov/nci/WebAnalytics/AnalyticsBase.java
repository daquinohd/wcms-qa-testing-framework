package gov.nci.WebAnalytics;

import java.net.URLDecoder;
import java.util.List;

import org.openqa.selenium.WebDriver;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;

public class AnalyticsBase {
	
	public static final String S_CODE_NAME = "s_code.js";
	public static final String S_ACCOUNT = "s_account";
	public static final String NCI_FUNCTIONS_NAME = "NCIAnalytics";
	public static final String PAGE_NAME = "www.cancer.gov";
	public static final String TRACKING_SERVER = "nci.122.2o7.net";

	public WebDriver driver;

	// No arg constructor
	public AnalyticsBase() {}
	
	/**
	 * Configure BrowserMob Proxy for Selenium.<br>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	// TODO: clean this up - also should this have a return value? 
	// TODO: set up filters 
	// TODO: remove duplicates
	public static void setHar(BrowserMobProxy proxy, List<String> harList) throws RuntimeException {		

		// A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring tools to export collected data. 
		// BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture HTTP content, 
	    // and export performance data as a HAR file object.
	    Har har = proxy.getHar();
	    
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Total HAR entries: " + entries.size());
    	
	    for (HarEntry entry : entries) {
	    	if(entry.getRequest().getUrl().contains(AnalyticsBase.TRACKING_SERVER))
			{
	    		String result = entry.getRequest().getUrl();
	    		try {
					//result = URLDecoder.decode(result, "UTF-8");
					if(result.contains("pageName=" + AnalyticsBase.PAGE_NAME)) {
						harList.add(result);
					}
				} catch (Exception e) {
					result = "bleah";
				} 
				//System.out.println(result);
			}
	    }  
	    
		System.out.println("BMP proxy setup done");
	}		
}