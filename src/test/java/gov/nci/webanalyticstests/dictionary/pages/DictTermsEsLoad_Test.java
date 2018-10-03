package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;

public class DictTermsEsLoad_Test extends DictionaryLoadBase {

	private final String TESTDATA_SHEET_NAME = "TermsPageSpanish";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	// ==================== Test methods ==================== //

	/// Test Spanish Dictionary Page load event
	@Test(dataProvider = "DictionarySpanishLoad", groups = { "Analytics" })
	public void testTermDictionaryEsPageLoad(String path, String contentType) {
		System.out.println("Test Spanish Dictionary Page load event at " + path + ":");
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			String dictPath = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsMetaData, dictPath);
			logger.log(LogStatus.PASS, "Test Spanish Dictionary Page load event at " + path + " passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DictionarySpanishLoad")
	private Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
