package gov.nci.dictionary;
import gov.nci.clinicaltrials.BaseClass;

import java.util.HashMap;
import java.util.Map;

/**
 *  Dictionary base class
 */
public class DictBaseClass extends BaseClass {

	/**
	 *  Method to return the expected number of dictionary definitions for letter "B" on PROD
	 *  and QA.
	 */
	public Integer NumberOfDefinitions(String dictionary, String language) {
		// These values are for the letter "B". Change the numbers if using a different letter
		Map<String, Integer> results = new HashMap<String, Integer>() {{
			put("b-prod-dict",    378);
			put("b-prod-dict-es", 232);
			put("b-prod-drug",    267);
			put("b-prod-genetics",  3);
			put("b-qa-dict",      374);
			put("b-qa-dict-es",   229);
			put("b-qa-drug",      259);
			put("b-qa-genetics",    3);
		}};

		// Need to add "-es" to the key value for the Spanish dictionary
		String lang = "";
		if (language.toLowerCase().equals("es")){
			lang = "-es";
		}

		String mapKey = "b-" + tier.toLowerCase() + "-" + dictionary + lang;
		// System.out.println(mapKey);

		return results.get(mapKey);
	}
}
