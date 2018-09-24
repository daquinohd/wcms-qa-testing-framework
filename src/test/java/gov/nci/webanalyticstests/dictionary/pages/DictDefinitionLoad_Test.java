package gov.nci.webanalyticstests.dictionary.pages;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;

public class DictDefinitionLoad_Test extends DictionaryBase {

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	@Test(dataProvider = "DefinitionData", groups = { "Analytics" })
	public void testDictionaryDefinitionLoad(String path, String contentType, String dat) {
		try {
			System.out.println("Test definition at " + path + ":");
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			path = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			Assert.assertEquals(beacon.props.get(16), dat);
			Assert.assertEquals(beacon.eVars.get(16), beacon.props.get(16));
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	/**
	 * Get an iterator data object with path, section, and page detail values.
	 * 
	 * @return path, type, and expected CDR data for testing
	 */
	@DataProvider(name = "DefinitionData")
	public Iterator<Object[]> getRightNavData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount("Definitions"); rowNum++) {
			String path = excelReader.getCellData("Definitions", "Path", rowNum);
			String type = excelReader.getCellData("Definitions", "DictionaryType", rowNum);
			String cdr = excelReader.getCellData("Definitions", "CDRData", rowNum);
			Object ob[] = { path, type, cdr };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

}