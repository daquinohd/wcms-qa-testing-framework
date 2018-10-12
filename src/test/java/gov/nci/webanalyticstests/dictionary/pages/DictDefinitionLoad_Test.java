package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;

public class DictDefinitionLoad_Test extends DictionaryLoadBase {

	private final String TESTDATA_SHEET_NAME = "Definitions";

	private AnalyticsMetaData analyticsMetaData;

	// ==================== Test methods ==================== //

	/// Test Definition Page load event
	@Test(dataProvider = "DefinitionData", groups = { "Analytics" })
	public void testDictionaryDefinitionLoad(String path, String contentType, String cdrData) {
		System.out.println("Path: " + path + ",  Type: " + contentType);
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			String dictPath = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsMetaData, dictPath);
			Assert.assertEquals(beacon.props.get(16), cdrData);
			Assert.assertEquals(beacon.eVars.get(16), beacon.props.get(16));
			logger.log(LogStatus.PASS, "Test Definition Page load event at " + path + " passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path, section, and page detail values.
	 * 
	 * @return path, type, and expected CDR data for testing
	 */
	@DataProvider(name = "DefinitionData")
	private Iterator<Object[]> getDefinitionLoadData() {
		String[] columnsToReturn = { "Path", "DictionaryType", "CDRData" };
		return getSpreadsheetData(testDataFilePath, TESTDATA_SHEET_NAME, columnsToReturn);
	}
}
