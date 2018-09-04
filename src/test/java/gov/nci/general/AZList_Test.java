package gov.nci.general;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.AZList;

public class AZList_Test extends BaseClass {

	private String TESTDATA_PATH;

	@BeforeClass(alwaysRun = true )
	@Parameters({"browser"})
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());
		driver = BrowserManager.startBrowser(browser, config, "about:blank");
		TESTDATA_PATH = config.getProperty("AZListData");
		System.out.println("    Test data path: " + TESTDATA_PATH);
	}

/*  ***************************** Test Methods ****************************************** */
	// The page options container is visible on most pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadAZList", groups = { "AZList" })
	public void IsVisible(String url) {

		System.out.println("\nTesting AZList IsVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		AZList page;

		try {
			page = new AZList(driver);
			boolean isVisible = page.IsVisible();
			Assert.assertTrue(isVisible, "*** Error: Page Options Box Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in IsVisible() ***");
		}
	}


/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadAZList")
	public Iterator<Object[]> loadAZList() {
		// String TESTDATA_SHEET_NAME = "Visible Page Options";
		String TESTDATA_SHEET_NAME = "AZ-List";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsHost   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"Hostname", rowNum);
			String pageOptionsPath   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"AZ Link", rowNum);
			String pageOptionsType   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Type", rowNum);
			String url = pageOptionsHost + pageOptionsPath;

			if (!pageOptionsType.equals("none")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
}