package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AnalyticsParams {
	
	// Parameter values from URL
	static final String CHANNEL = "ch";
	static final String EVENTS = "events";	
	static final String LINKTYPE = "pe";
	static final String LINKNAME = "pev2";
	static final String LINKURL = "pev1";
	
	// Partial parameter values. Each prop, eVar, and hier is its own query parameter. 
	// The getNumberedParams() method handles the logic of appending the number values
	// to each of these query parameter
	static final String PROP_PARTIAL = "c";
	static final String EVAR_PARTIAL = "v";
	static final String HIER_PARTIAL = "h";
	
	// Beacon properties
	private List<NameValuePair> all;
	
	public List<NameValuePair> getAll() {
		return all;
	}
	public void setAll(List<NameValuePair> all) {
		this.all = all;
	}

	public AnalyticsParams() 
	{
		// No arg constructor
	}
	
	/**
	 * Constructor
	 * @param beaconUrl
	 */
	public AnalyticsParams(URI uri) {
		setAll(buildParamsList(uri));
	}
	
	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 */
	public List<NameValuePair> buildParamsList(URI uri) {		
		List<NameValuePair> rtnParams = new ArrayList<NameValuePair>();
		
		try {
			String queries = getRawQueryString(uri);
			for(String parm : queries.split("&")) {
				String[] pair = parm.split("=");
				String key = URLDecoder.decode(pair[0], "UTF-8");
				String value = "";
				if(pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8"); 
				}
				rtnParams.add(new BasicNameValuePair(key, value));				
			}
		} 
		catch (UnsupportedEncodingException ex) {
			System.out.println("Error decoding URI in WaParams:buildParamsList()");
		}		
		return rtnParams;
	}
	
	/**
	 * Get an encoded query string from a given URI.
	 * @param uri
	 * @return
	 */	
	public static String getRawQueryString(URI uri) {
		return uri.getRawQuery();
	}
	
	/**
	 * Get the clean query string from a given URI.
	 * @param uri
	 * @return
	 */
	public static String getQueryString(URI uri) {
		return uri.getQuery();
	}
	
	/**
	 * Check query params to see if this is a link tracking event
	 * @param parms
	 * @return
	 */
	public static boolean hasParam(List<NameValuePair> paramList, String myParam) {
		for (NameValuePair param : paramList) {
			if (param.getName().toLowerCase().equals(myParam)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get a list of numbered parameters and their values (e.g. [prop1="www.cancer.gov", prop2="/home", prop3="NCI"])
	 * @param paramList
	 * @param parm
	 * @param replacement
	 * @return
	 */
	public static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm, String replacement) {
		List<NameValuePair> rtnList = new ArrayList<>();
		for (NameValuePair param : paramList) {
			// Regex: parameter name followed by 1 or more digits, starting with 1-9 only
			if(param.getName().matches("^" + parm + "[1-9]\\d*$")) {
				String rtnName = param.getName().replace(parm, replacement);
				String rtnValue = param.getValue();
				rtnList.add(new BasicNameValuePair(rtnName, rtnValue));
			}
		}
		return rtnList;
	}
	
	/**
	 * Overload for getNumberedParams
	 * @param paramList
	 * @param parm
	 * @return
	 */
	public static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm) {
		return getNumberedParams(paramList, parm, parm);
	}	
	
}
