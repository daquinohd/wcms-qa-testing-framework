package gov.nci.webanalyticstests.r4r;

import org.testng.annotations.BeforeClass;

import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class R4RClickBase extends AnalyticsTestClickBase {

	protected String testDataFilePath;

	// ==================== Setup methods ==================== //

	// Set data file path for the R4R spreadsheet
	@BeforeClass(groups = { "Analytics" })
	protected void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsR4RData");
	}

}