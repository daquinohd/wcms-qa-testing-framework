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

	/**
	 * The following page / content types are covered by this test class: 
	 * - "Page Not Found"
	 * - "Thank You" 
	 * - "We're sorry but there seems to be a problem"
	 */
	
	private String testDataFilePath;

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}

	@Test(dataProvider = "ErrorPageLoad", groups = { "Analytics" })
	public void testErrorPageLoad(String path, String contentType) {
		System.out.println("Test error page ('" + contentType + "'):");
		driver.get(config.goHome() + path);
		try {
			String currUrl = driver.getCurrentUrl();
			Beacon beacon = getBeacon();

			// Error pages do not share the rest of the common pageload valules
			Assert.assertTrue(beacon.hasSuite("nciglobal", currUrl), "Incorrect suite value.");
			Assert.assertTrue(beacon.hasEvent(47), "event47 is missing.");
			Assert.assertEquals(beacon.channels, "Error Pages");
			Assert.assertEquals(beacon.getPageType(), "errorPage");
			Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "ErrorPageLoad")
	public Iterator<Object[]> getErrorPageLoadData() {
		return getPathContentTypeData(testDataFilePath, "ErrorPage");
	}

}