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

public class DictTermsES_Test extends BaseClass {

	private String TESTDATA_PATH;
	private String language = "ES";

	// @BeforeClass(alwaysRun = true )
	// @Parameters({"browser"})
	// public void setup(String browser) {
	// 	// logger = report.startTest(this.getClass().getSimpleName());
	// 	driver = BrowserManager.startBrowser(browser, config, "about:blank");
	// 	// TESTDATA_PATH = config.getProperty("AZListData");
	// 	// System.out.println("    Test data path: " + TESTDATA_PATH);
	// }

/*  ***************************** Test Methods ****************************************** */
	// The page options container is visible on most pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void TitleVisibleES(String url) {
		HeaderElements page;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Glossary ES TitleVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			page = new HeaderElements(driver);
			boolean titleVisible = page.TitleVisible();
			Assert.assertTrue(titleVisible,
			                  "*** Error: Term (ES) Distionary Header Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// --------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void WidgetLinkVisibleES(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Glossary ES WidgetLinkESVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean linkVisible = dict.WidgetLinkVisible();
			Assert.assertTrue(linkVisible,
			                  "*** Error: Term Distionary ES Widget Link Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	// Finding the link for the Glossary Widget and testing that the page
	// exists.
	// ------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void ClickWidgetLinkES(String url) {
		HeaderElements dict;
		String language = "ES";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Glossary ES ClickWidgetLinkES()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);
		// System.out.println("    WindowHandle");
		// System.out.println("    " + driver.getWindowHandle());

		try {
			dict = new HeaderElements(driver);
			boolean foundWidgetPage = dict.LinksToWidgetPage(driver, language);
			Assert.assertTrue(foundWidgetPage,
			                  "*** Error: Terms Distionary ES Widget Page Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the "Starts with"/"Contains" radio buttons are displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void RadioVisibleES(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Term Dictionary RadioVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.RadioBtnVisible();
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary ES Radio Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the "Starts with" radio button is selected by default
	// ----------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void RadioStartsWithSelectedES(String url) {
		HeaderElements dict;

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Term Dictionary RadioStartsWithSelected()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioStartsWith = dict.RadioDefault();
			Assert.assertTrue(radioStartsWith,
			                  "*** Error: Term Dictionary ES Radio Button Default Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the search input field is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void InputFieldVisibleES(String url) {
		HeaderElements dict;
		String cssSelector = "input.dictionary-search-input";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Term Dictionary " + curMethod);
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary ES Input Field Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the Search button is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void SearchBtnVisibleES(String url) {
		HeaderElements dict;
		String cssSelector = "input.button";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Term Dictionary " + curMethod);

		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary SearchButton Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

	//
	// Confirming the A-Z list is displayed
	// -------------------------------------------------------------------------
	@Test(dataProvider = "GlossaryES", groups = { "dictionary" })
	public void AZListVisibleES(String url) {
		HeaderElements dict;
		String cssSelector = "div.az-list";

		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();
		// System.out.println("\nTesting Term Dictionary " + curMethod);
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		try {
			dict = new HeaderElements(driver);
			boolean radioVisible = dict.FieldVisible(cssSelector);
			Assert.assertTrue(radioVisible,
			                  "*** Error: Term Dictionary A-Z List Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in " + curMethod + " ***");
		}
	}

/*  ***************************** Data Provider *********************************************** */
	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "GlossaryES")
	public Iterator<Object[]> loadAZList() {
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		String url = AddHostname("/espanol/publicaciones/diccionario");
		Object ob[] = { url };
		myObjects.add(ob);

		return myObjects.iterator();
	}
}