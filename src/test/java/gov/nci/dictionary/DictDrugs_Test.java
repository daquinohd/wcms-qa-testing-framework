package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.BrowserManager;
// import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicaltrials.BaseClass;

public class DictDrugs_Test extends BaseClass {

	private String TESTDATA_PATH;

	// @BeforeClass(alwaysRun = true )
	// @Parameters({"browser"})
	// public void setup(String browser) {
	// 	logger = report.startTest(this.getClass().getSimpleName());
	// 	driver = BrowserManager.startBrowser(browser, config, "about:blank");
	// 	// TESTDATA_PATH = config.getProperty("AZListData");
	// 	// System.out.println("    Test data path: " + TESTDATA_PATH);
	// }

/*  ***************************** Test Methods ****************************************** */
	// The page options container is visible on most pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "DrugDictionary", groups = { "dictionary" })
	public void TitleVisible(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Drug Dictionary TitleVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean titleVisible = dict.TitleVisible();
			Assert.assertTrue(titleVisible,
			                  "*** Error: Drug Distionary Header Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}


/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "DrugDictionary")
	public Iterator<Object[]> loadAZList() {
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

        String url = AddHostname("/publications/dictionaries/cancer-drug");
        Object ob[] = { url };
        myObjects.add(ob);

		return myObjects.iterator();
	}
}