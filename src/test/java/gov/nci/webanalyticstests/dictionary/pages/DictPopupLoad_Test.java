package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.dictionary.DictionaryLoadBase;

public class DictPopupLoad_Test extends DictionaryLoadBase {

	private final String TESTDATA_SHEET_NAME = "DictionaryPopup";

	private AnalyticsMetaData analyticsMetaData;

	// ==================== Test methods ==================== //

	/// Test Dictionary Popup Page load event
	@Test(dataProvider = "DictionaryPopupLoad", groups = { "Analytics" })
	public void testDictionaryPopupLoad(String path, String contentType) {
		System.out.println("Path: " + path + ",  Type: " + contentType);
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			Assert.assertTrue(beacon.hasEvent(1), "Missing event1");
			Assert.assertEquals(beacon.props.get(7), "patient");
			Assert.assertTrue(beacon.hasEvent(11), "Missing event11");
			Assert.assertTrue(beacon.hasEvent(47), "Missing event47");
			Assert.assertEquals(beacon.props.get(8), analyticsMetaData.getLanguageName());
			Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
			Assert.assertEquals(beacon.eVars.get(7), beacon.props.get(7));
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DictionaryPopupLoad")
	private Iterator<Object[]> getDictionaryPopupLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
