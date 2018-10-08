package gov.nci.webanalyticstests.error.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class ErrorLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "ErrorPage";

	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsErrorPgData");
	}

	// ==================== Test methods ==================== //

	/// Test Error Page load event
	@Test(dataProvider = "ErrorPageLoad", groups = { "Analytics" })
	public void testErrorPageLoad(String path, String contentType) {
		System.out.println("Test Error Page load event (" + contentType + "):");
		driver.get(config.goHome() + path);

		try {
			Beacon beacon = getBeacon();

			// Error pages do not share the rest of the common pageload valules
			Assert.assertTrue(beacon.hasSuite("nciglobal", driver.getCurrentUrl()), "Incorrect suite value.");
			Assert.assertTrue(beacon.hasEvent(47), "event47 is missing.");
			Assert.assertEquals(beacon.channels, "Error Pages");
			Assert.assertEquals(beacon.getPageType(), "errorPage");
			Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
			logger.log(LogStatus.PASS, "Test Error Page load event (" + contentType + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "ErrorPageLoad")
	public Iterator<Object[]> getTopicPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}