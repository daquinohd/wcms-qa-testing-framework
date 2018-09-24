package gov.nci.webanalyticstests.pdq.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class PdqDrugLoad_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class: 
	 * - PDQ Drug Info Summary
	 */

	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	@Test(dataProvider = "PDQPageLoad", groups = { "Analytics" })
	public void testPdqPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			driver.navigate().refresh();
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, path.toLowerCase());
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "PDQPageLoad")
	public Iterator<Object[]> getPDQPageLoadData() {
		return getPathContentTypeData(testDataFilePath, "PDQDrugPage");
	}

}