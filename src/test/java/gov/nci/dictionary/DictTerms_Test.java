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

public class DictTerms_Test extends BaseClass {

	private String TESTDATA_PATH;

	@BeforeClass(alwaysRun = true )
	@Parameters({"browser"})
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());
		driver = BrowserManager.startBrowser(browser, "about:blank");
		// TESTDATA_PATH = config.getProperty("AZListData");
		// System.out.println("    Test data path: " + TESTDATA_PATH);
	}

/*  ***************************** Test Methods ****************************************** */
	// The page options container is visible on most pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void TitleVisible(String url) {
		HeaderElements page;

		System.out.println("\nTesting Glossary TitleVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);


		try {
			page = new HeaderElements(driver);
			boolean titleVisible = page.TitleVisible();
			Assert.assertTrue(titleVisible,
			                  "*** Error: Term Dictionary Header Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in TitleVisible() ***");
		}
	}

	//
	// -----------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void WidgetLinkVisible(String url) {
		HeaderElements dict;

		System.out.println("\nTesting Term Dictionary WidgetLinkVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);


		try {
			dict = new HeaderElements(driver);
			boolean linkVisible = dict.LinkVisible();
			Assert.assertTrue(linkVisible,
			                  "*** Error: Term Dictionary Widget Link Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in WidgetLinkVisible() ***");
		}
	}

	// Finding the link for the Glossary Widget and testing that the widget page
	// exists.
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void ClickWidgetLink(String url) {
		HeaderElements dict;
		String language = "EN";

		System.out.println("\nTesting Term Dictionary ClickWidgetLink()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean foundWidgetPage = dict.LinksToWidgetPage(driver, language);
			Assert.assertTrue(foundWidgetPage,
			                  "*** Error: Term Dictionary Click Widget Page Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in ClickWidgetLink() ***");
		}
	}

	//
	// Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void RadioVisible(String url) {
		HeaderElements dict;
		String language = "EN";

		System.out.println("\nTesting Term Dictionary RadioVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.RadioBtnVisible();
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary Radio Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in RadioVisible() ***");
		}
	}

	//
	// Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "Glossary", groups = { "dictionary" })
	public void RadioStartsWithSelected(String url) {
		HeaderElements dict;
		String language = "EN";

		System.out.println("\nTesting Term Dictionary RadioStartsWithSelected()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioStartsWith = dict.RadioDefault();
			Assert.assertTrue(radioStartsWith,
			                  "*** Error: Term Dictionary Radio Button Default Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in RadioStartsWithSelected() ***");
		}
	}



/*  ***************************** Data Provider *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "Glossary")
	public Iterator<Object[]> loadAZList() {
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		String url = AddHostname("/publications/dictionaries/cancer-terms");
		Object ob[] = { url };
		myObjects.add(ob);

		return myObjects.iterator();
	}
}