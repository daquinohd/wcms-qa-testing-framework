package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.openqa.selenium.WebDriver;

import com.nci.Utilities.ConfigReader;

public class AnalyticsBase {
	
	// Constants
	public static final String S_CODE_NAME = "s_code.js";
	public static final String S_ACCOUNT = "s_account";
	public static final String NCI_FUNCTIONS_NAME = "NCIAnalytics";
	public static final String PAGE_NAME = "www.cancer.gov";
	public static final String TRACKING_SERVER = "nci.122.2o7.net";

	// Get our page navigation URLs
	public ConfigReader config = new ConfigReader();
	public String homePage = config.getPageURL("HomePage");
	public String blogPostPage = config.getPageURL("BlogPostPage");
	public String blogSeriesPage = config.getPageURL("BlogSeriesPage");
	public String cthpPatient = config.getPageURL("CTHPPatient");
	public String cthpHP = config.getPageURL("CTHPHP");
	public String innerPage = config.getPageURL("InnerPage");
	public String landingPage = config.getPageURL("LandingPage");
	public String pdqPage = config.getPageURL("PDQPage");
	public String topicPage = config.getPageURL("TopicPage");
	public String spanishPage = config.getPageURL("SpanishPage");
	public String appModulePage = config.getPageURL("AppModulePage");
	
	// Beacon properties
	public URI uri;
	public String[] suites;	
	public List<NameValuePair> params;
	public String channel;
	public String[] events;
	public List<NameValuePair> props; 
	public List<NameValuePair> eVars; 
	public List<NameValuePair> hiers;
	public String linkType;
	public String linkName;
	public String linkUrl;	
	
	/** No-arg constructor - init all variables */
	public AnalyticsBase() {
		uri = null;
		suites = new String[0];		
		params = new ArrayList<>();
		channel = "";
		events = new String[0];
		props = new ArrayList<>();
		eVars = new ArrayList<>();
		hiers = new ArrayList<>();
		linkType = "";
		linkName = "";
		linkUrl = "";
	}
	
	/**
	 * Constructor
	 * @param beaconUrl
	 * TODO: Handle MalformedURLExceptions
	 */
	public AnalyticsBase(String beaconUrl) {
		uri = URI.create(beaconUrl);
		suites = getSuites(uri);
		params = buildParamsList(uri);
		channel = getChannel(params);
		events = getEvents(params);
		props = getProps(params);
		eVars = getEvars(params);
		hiers = getHiers(params);
		linkType = getLinkType(params);
		linkName = getLinkName(params);
		linkUrl = getLinkUrl(params);
	}
	
	/**
	 * Get the reporting suites (s_account) value
	 * as an array of strings
	 * @param uri
	 * @return
	 */
	protected String[] getSuites(URI uri) {
		String[] path = uri.getPath().split("/");
		String[] rtnSuites = path[3].split(",");
		return rtnSuites;
	}
	
	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 * TODO: replace deprecated parse() method
	 */
	public List<NameValuePair> buildParamsList(URI uri) {
		List<NameValuePair> rtnParams = new ArrayList<>();
		rtnParams = URLEncodedUtils.parse(uri, "UTF-8");
		return rtnParams;
	}
	
	/**
	 * Get channel value 
	 * @param parms
	 * @return
	 */
	public String getChannel(List<NameValuePair> parms) {
		String rtnChannel = "";		
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("ch")) {
				rtnChannel = param.getValue();
				break;
			}
		}
		return rtnChannel.trim();
	}
	
	/**
	 * Get array of event values
	 * @param parms
	 * @return
	 */
	public String[] getEvents(List<NameValuePair> parms) {
		String rtnEvents = "";		
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("events")) {
				rtnEvents = param.getValue();
				break;
			}
		}
		return rtnEvents.split(",");
	}
	
	/**
	 * Get array of props ('c' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getProps(List<NameValuePair> parms) {
		List<NameValuePair> rtnProps = new ArrayList<>();		
		for (NameValuePair param : parms) {
			// Regex: "c" followed by 1 or more digits, starting with 1-9 only
			if(param.getName().matches("^[Cc][1-9]\\d*$")) {
				String propName = param.getName().replaceAll("[Cc]", "prop");
				String propValue = param.getValue();
				rtnProps.add(new BasicNameValuePair(propName, propValue));
			}
		}
		return rtnProps;
	}
	
	/**
	 * Get array of eVars ('v' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getEvars(List<NameValuePair> parms) {
		List<NameValuePair> rtnEvars = new ArrayList<>();		
		for (NameValuePair param : parms) {
			// Regex: "v" followed by 1 or more digits, starting with 1-9 only			
			if(param.getName().matches("^[Vv][1-9]\\d*$")) {
				String eVarName = param.getName().replaceAll("[Vv]", "eVar");
				String eVarValue = param.getValue();
				rtnEvars.add(new BasicNameValuePair(eVarName, eVarValue));
			}
		}
		return rtnEvars;
	}
	
	/**
	 * Get array of hierarchy values ("hiers" or "h" values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getHiers(List<NameValuePair> parms) {
		List<NameValuePair> rtnHiers = new ArrayList<>();		
		for (NameValuePair param : parms) {
			// Regex: "h" followed by 1 or more digits, starting with 1-9 only			
			if(param.getName().matches("^[Hh][1-9]\\d*$")) {
				String hierName = param.getName().replaceAll("[Hh]", "hier");
				String hierValue = param.getValue();
				rtnHiers.add(new BasicNameValuePair(hierName, hierValue));
			}
		}
		return rtnHiers;
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
		return rtn.trim();
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
		return rtn.trim();
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
		return rtn.trim();
	}	
	
	/**
	 * Check query params to see if this is a link tracking event
	 * @param parms
	 * @return
	 */
	public boolean hasParam(List<NameValuePair> paramList, String myParam) {
		for (NameValuePair param : paramList) {
			if (param.getName().toLowerCase().equals(myParam)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check for parameters to verify that this is a link event
	 * @param paramList
	 * @return
	 */
	public static boolean isLinkEvent(List<NameValuePair> paramList) {
		for (NameValuePair param : paramList) {
			if (param.getName().equalsIgnoreCase("pe")) {
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Get a list of beacon URLs fired off for load events
	 * @param urlList
	 * @return
	 */
	public static List<AnalyticsBase> getLoadBeacons(List<String> urlList) {
				
		List<AnalyticsBase> loadBeacons = new ArrayList<AnalyticsBase>();		
		AnalyticsBase analytics = new AnalyticsBase();

		for(String url : urlList)
		{
			// If this doesn't have the "Link Type" param ('pe'), add to list of load beacons
			List<NameValuePair> params = analytics.buildParamsList(URI.create(url));
			if(!isLinkEvent(params)) {
				loadBeacons.add(new AnalyticsBase(url));
			}
		}

		System.out.println("Total load beacons: " + loadBeacons.size());
		System.out.println("Total click beacons: " + (urlList.size() - loadBeacons.size()));
		return loadBeacons;
	}		
	
	/**
	 * Get a list of beacon URLs fired off for click events
	 * @param urlList
	 * @return
	 */
	public static List<AnalyticsBase> getClickBeacons(List<String> urlList) {
				
		List<AnalyticsBase> clickBeacons = new ArrayList<AnalyticsBase>();		
		AnalyticsBase analytics = new AnalyticsBase();

		for(String url : urlList)
		{
			// If this has the "Link Type" param ('pe'), add to list of click beacons
			List<NameValuePair> params = analytics.buildParamsList(URI.create(url));
			if(isLinkEvent(params)) {
				clickBeacons.add(new AnalyticsBase(url));
			}
		}
		
		System.out.println("Total click beacons: " + clickBeacons.size());
		System.out.println("Total load beacons: " + (urlList.size() - clickBeacons.size()));
		return clickBeacons;
	}	
	
	/**
	 * Temporary util method for troubleshooting
	 * TODO: remove this once explicit wait is working 
	 * @param sec
	 */
	protected static void nap(int sec) {
		long ms = new Long(sec*1000);		
		try { 
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			System.out.println("goSleepy() failed");
		}
	}
	protected static void nap() {
		nap(10);
	}

}
