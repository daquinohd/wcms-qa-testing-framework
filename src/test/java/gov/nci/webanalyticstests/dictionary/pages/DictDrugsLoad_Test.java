package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.dictionary.DictionaryLoadBase;

public class DictDrugsLoad_Test extends DictionaryLoadBase {

	private final String TESTDATA_SHEET_NAME = "TermsPageDrugs";

	private AnalyticsMetaData analyticsMetaData;

	// ==================== Test methods ==================== //

	/// Test Drug Dictionary Page load event
	@Test(dataProvider = "DictionaryDrugsLoad", groups = { "Analytics" })
	public void testDrugDictionaryPageLoad(String path, String contentType) {
		System.out.println("Path: " + path + ",  Type: " + contentType);
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			String dictPath = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsMetaData, dictPath);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DictionaryDrugsLoad")
	private Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
