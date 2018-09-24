package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class DictPopupLoad_Test extends DictionaryBase {

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "DictionaryPopup";

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	@Test(dataProvider = "DictionaryPopupLoad", groups = { "Analytics" })
	public void testDictionaryPopupLoad(String path, String contentType) {
		try {
			System.out.println("Test popup at " + path + ":");
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			Assert.assertTrue(beacon.hasEvent(1), "Expected event1");
			Assert.assertEquals(beacon.props.get(7), "patient");
			Assert.assertTrue(beacon.hasEvent(11), "Expected event11");
			Assert.assertTrue(beacon.hasEvent(47), "Expected event47=");
			Assert.assertEquals(beacon.props.get(8), analyticsPageLoad.getLanguageName());
			Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
			Assert.assertEquals(beacon.eVars.get(7), beacon.props.get(7));
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "DictionaryPopupLoad")
	public Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}