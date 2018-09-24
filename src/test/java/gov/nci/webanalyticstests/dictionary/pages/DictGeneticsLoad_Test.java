package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;

public class DictGeneticsLoad_Test extends DictionaryBase {

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "TermsPageGenetics";

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	@Test(dataProvider = "DictionaryGeneticsLoad", groups = { "Analytics" })
	public void testGeneticsDictionaryPageLoad(String path, String contentType) {
		try {
			System.out.println("Test Genetics Term Dictionary at " + path + ":");
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			path = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "DictionaryGeneticsLoad")
	public Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}