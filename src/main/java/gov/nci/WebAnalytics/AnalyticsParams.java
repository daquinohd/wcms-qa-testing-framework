package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

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
	
	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 */
	public static List<NameValuePair> getParamList(URI uri) {
		List<NameValuePair> rtnParams = new ArrayList<NameValuePair>();
		
		try {
			String queries = getRawQueryString(uri);
			for(String parm : queries.split("&")) {
				String[] pair = parm.split("=");
				String Name = URLDecoder.decode(pair[0], "UTF-8");
				String value = "";
				if(pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8"); 
				}
				rtnParams.add(new BasicNameValuePair(Name, value));				
			}
		} 
		catch (UnsupportedEncodingException ex) {
			System.out.println("Error decoding URI in WaParams:buildParamsList()");
		}		
		return rtnParams;
	}	
	
	/**
	 * Get the clean query string from a given URI.
	 * @param uri
	 * @return URL String
	 */	
	public static String getRawQueryString(URI uri) {
		return uri.getRawQuery();
	}	
	 
	/**
	 * Get an encoded query string from a given URI.
	 * @param uri
	 * @return URL String
	 */	
	public static String getQueryString(URI uri) {
		return uri.getQuery();
	}
	
	/**
	 * Check that a given query parameter (name) exists within a list of params
	 * @param paramList
	 * @param name
	 * @return bool
	 */
	public static boolean containsName(List<NameValuePair> paramList, String name) {
		for (NameValuePair param : paramList) {
			if (param.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check that a given parameter value exists within a list of params
	 * @param paramList
	 * @param value
	 * @return bool
	 */
	public static boolean containsValue(List<NameValuePair> paramList, String value) {
		for (NameValuePair param : paramList) {
			if (param.getValue().equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
	
}