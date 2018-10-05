package gov.nci.webanalyticstests.dictionary.pages;

import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class DictionaryLoadBase extends AnalyticsTestLoadBase {
	
	/**
	 * Clean up paths for expected dictionary analytics values
	 * @param path
	 * @return
	 */
	protected String getDictionaryPath(String path) {
		path = path.split("\\?")[0];
		if(path.contains("/def/")) {
			return path.split("/def/")[0];
		}
		else if(path.endsWith("/search")) {
			return path.split("/search")[0];
		}
		else if(path.endsWith("/buscar")) {
			return path.split("/buscar")[0];
		}
		else {
			return path;
		}
	}
	
}