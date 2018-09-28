package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;

public class DictPopupLoad_Test extends DictionaryBase {

	private final String TESTDATA_SHEET_NAME = "DictionaryPopup";

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	// ==================== Test methods ==================== //

	/// Test Dictionary Popup Page load event
	@Test(dataProvider = "DictionaryPopupLoad", groups = { "Analytics" })
	public void testDictionaryPopupLoad(String path, String contentType) {
		System.out.println("Test Dictionary Popup Page load event:");
		driver.get(config.goHome() + path);

		try {
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			String dictPath = getDictionaryPath(path);
			Assert.assertTrue(beacon.hasEvent(1), "Expected event1");
			Assert.assertEquals(beacon.props.get(7), "patient");
			Assert.assertTrue(beacon.hasEvent(11), "Expected event11");
			Assert.assertTrue(beacon.hasEvent(47), "Expected event47=");
			Assert.assertEquals(beacon.props.get(8), analyticsPageLoad.getLanguageName());
			Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
			Assert.assertEquals(beacon.eVars.get(7), beacon.props.get(7));
			logger.log(LogStatus.PASS, "Test Dictionary Popup Page load event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DictionaryPopupLoad")
	private Iterator<Object[]> getDictionaryPopupLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
