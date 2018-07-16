package gov.nci.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Breaks a URL string into its constituent components (path, query parameters,
 * etc.). This is a wrapper for the java.net.URL class, adding a dictionary of
 * query parameters.
 */
public class ParsedURL {
    private URL innerUrl;
    private Map<String, String> queryPairs = new LinkedHashMap<String, String>();

    /**
     * Creates a ParsedURL object from its String representation.
     * 
     * @param url - the String to parse as a URL.
     * @throws UnsupportedEncodingException
     */
    public ParsedURL (String url )
        throws MalformedURLException, UnsupportedEncodingException {
        innerUrl = new URL(url);

        String query = innerUrl.getQuery();
        String[] pairs = query.split("&");
        for(String pair : pairs ) {
            int idx = pair.indexOf("=");

            String parameter = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            queryPairs.put(parameter, value);
        }
    }

    /**
     * Gets the path part of this URL.
     * 
     * @return the path part of thsi URL, or an empty string if one does not exist.
     */
    public String getPath() {
        return innerUrl.getPath();
    }

    /**
     * Get the query portion of the URL.
     * 
     * @return String containing the query portion of the URL.
     * @see getQueryParam for individual parameters.
     */
    public String getQuery() {
        return innerUrl.getQuery();
    }

    /**
     * Gets the value of a single query parameter.
     * @param paramName The name of the query parameter to retrieve.
     * @return A String containing the parameter's value. If the parameter was present, but with no value, an empty string
     * is return. If the parameter was not present, NULL is returned.
     */
    public String getQueryParam(String paramName) {
        if(queryPairs.containsKey(paramName))
            return queryPairs.get(paramName);
        else
            return  null;
    }
}