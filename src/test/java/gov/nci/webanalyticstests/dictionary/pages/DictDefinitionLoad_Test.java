package gov.nci.webanalyticstests.dictionary.pages;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;

public class DictDefinitionLoad_Test extends DictionaryLoadBase {

	private final String TESTDATA_SHEET_NAME = "Definitions";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	// ==================== Test methods ==================== //

	/// Test Definition Page load event
	@Test(dataProvider = "DefinitionData", groups = { "Analytics" })
	public void testDictionaryDefinitionLoad(String path, String contentType, String cdrData) {
		System.out.println("Test Definition Page load event at " + path + ":");
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
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path, section, and page detail values.
	 * 
	 * @return path, type, and expected CDR data for testing
	 */
	@DataProvider(name = "DefinitionData")
	public Iterator<Object[]> getDefinitionLoadData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String type = excelReader.getCellData(TESTDATA_SHEET_NAME, "DictionaryType", rowNum);
			String cdrData = excelReader.getCellData(TESTDATA_SHEET_NAME, "CDRData", rowNum);
			Object ob[] = { path, type, cdrData };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}
}
