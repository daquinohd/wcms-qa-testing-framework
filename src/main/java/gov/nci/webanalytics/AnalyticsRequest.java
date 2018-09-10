package gov.nci.webanalytics;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import gov.nci.framework.ParsedURL;

public class AnalyticsRequest {
	
	//	TODO: Handle null exceptions in has() methods
	//	TODO: Create 'catch-all' Contains() method		
	public String url;
	public List<NameValuePair> paramsList;
	
	public AnalyticsRequest(String url) {
		try {
			this.url = url;
			this.paramsList = ParsedURL.getParamArrayList(url);
		} catch (Exception e) {
			System.out.println("Error initializing AnalyticsRequest. Check the value of the URL being passed in.");
			e.printStackTrace();
		}
	}
	
	/******************** Utility functions ****************************************/
	/**
	 * Utility function to check for a user-specified variable and value
	 * @param name
	 * @param value
	 * @return bool
	 */
	protected boolean hasVariable(String name, String value) {
		// TODO: fill this out
		return false;
	}
	
	/**
	 * Get a list of numbered parameters and their values (e.g. [prop1="www.cancer.gov", prop2="/home", prop3="NCI"])
	 * @param paramList
	 * @param parm
	 * @param replacement
	 * @return
	 */
	protected static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm, String replacement) {
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
	 * Can be used for cases where the parameter name and analytics variable name match 
	 * @param paramList
	 * @param parm
	 * @return
	 */
	protected static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm) {
		return getNumberedParams(paramList, parm, parm);
	}	
	
}
