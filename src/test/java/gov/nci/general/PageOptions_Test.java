package gov.nci.general;

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
import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.PageOptions;

public class PageOptions_Test extends BaseClass {

	private String TESTDATA_PATH;

	@BeforeClass(alwaysRun = true )
	@Parameters({"browser"})
	public void setup(String browser) {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = "about:blank";
		driver = BrowserManager.startBrowser(browser, config, pageURL);
		// System.out.println(this.getClass().getSimpleName());
		// int uLine = this.getClass().getSimpleName().lastIndexOf("_");
		// System.out.println(this.getClass().getSimpleName().substring(0, uLine));
		TESTDATA_PATH = config.getProperty("PageOptionsData");
		System.out.println("    Test data path: " + TESTDATA_PATH);
	}

/*  ***************************** Test Methods *********************************************** */
	// The page options container is visible on most pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadVisiblePageOptionsList", groups = { "PageOptions" })
	public void IsVisible(String url) {

		System.out.println("\nTesting PO IsVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.IsVisible();
			Assert.assertTrue(isVisible, "*** Error: Page Options Box Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in IsVisible() ***");
		}
	}


	// The page options container is not visible on some pages.  Testing that
	// it's true for those pages as indicated on the worksheet.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNotVisiblePageOptionsList", groups = { "PageOptions" })
	public void NotVisible(String url) {

		System.out.println("\nTesting PO NotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);
		// logger.log(LogStatus.PASS, url);

		driver.get(url);

		PageOptions page;

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.IsVisible();
			Assert.assertFalse(isVisible, "*** Error: Found Page Options Box ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in MissingOk() *** ");
		}
	}


	// Twitter and email button are visible on all pages.  Testing that this is true.
	// There's no need checking these buttons aren't showing up where they shouldn't.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadButtonOnEachPageList", groups = { "PageOptions-button" })
	public void EmailVisible(String url) {
		System.out.println("\nTesting EmailVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--email";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: Email Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in EmailVisible() ***");
		}
	}


	// Twitter and email button are visible on all pages.  Testing that this is true.
	// There's no need checking these buttons aren't showing up where they shouldn't.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNotVisiblePageOptionsList", groups = { "PageOptions-button" })
	public void EmailNotVisible(String url) {
		System.out.println("\nTesting EmailNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--email";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: Email Button Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in EmailNotVisible() ***");
		}
	}


	// Twitter and email button are visible on all pages.  Testing that this is true.
	// There's no need checking these buttons aren't showing up where they shouldn't.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadButtonOnEachPageList", groups = { "PageOptions-button" })
	public void TwitterVisible(String url) {
		System.out.println("\nTesting TwitterVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);
		// logger.log(LogStatus.FAIL, url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--twitter";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: Twitter Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in TwitterVisible() ***");
		}
	}


	// Twitter and email button are visible on all pages.  Testing that this is true.
	// There's no need checking these buttons aren't showing up where they shouldn't.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNotVisiblePageOptionsList", groups = { "PageOptions-button" })
	public void TwitterNotVisible(String url) {
		System.out.println("\nTesting TwitterNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);
		// logger.log(LogStatus.FAIL, url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--twitter";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: Twitter Button  Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in TwitterNotVisible() ***");
		}
	}


	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadFontResizeList", groups = { "PageOptions-button" })
	public void FontVisible(String url) {
		System.out.println("\nTesting FontVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--resize";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: Font Resize Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in FontVisible() ***");
		}
	}

	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNoFontResizeList", groups = { "PageOptions-button" })
	public void FontNotVisible(String url) {
		System.out.println("\nTesting FontNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--resize";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: Font Resize Button Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in FontNotVisible() ***");
		}
	}


	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadDefaultButtonsList", groups = { "PageOptions-button" })
	public void PrintVisible(String url) {
		System.out.println("\nTesting PrintVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--print";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: Print Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in PrintVisible() ***");
		}
	}

	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNoDefaultButtonsList", groups = { "PageOptions-button" })
	public void PrintNotVisible(String url) {
		System.out.println("\nTesting PrintNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "page-options--print";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: Print Button Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in PrintNotVisible() ***");
		}
	}


	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadDefaultButtonsList", groups = { "PageOptions-button" })
	public void FacebookVisible(String url) {
		System.out.println("\nTesting FacebookVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--facebook";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: Facebook Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in FacebookVisible() ***");
		}
	}

	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNoDefaultButtonsList", groups = { "PageOptions-button" })
	public void FacebookNotVisible(String url) {
		System.out.println("\nTesting FacebookNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--facebook";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: Facebook Button Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in FacebookNotVisible() ***");
		}
	}


	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadDefaultButtonsList", groups = { "PageOptions-button" })
	public void GoogleplusVisible(String url) {
		System.out.println("\nTesting GoogleVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--googleplus";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: GooglePlus Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in GoogleplusVisible() ***");
		}
	}

	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNoDefaultButtonsList", groups = { "PageOptions-button" })
	public void GoogleplusNotVisible(String url) {
		System.out.println("\nTesting GoogleNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--googleplus";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: GooglePlus Button Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in GoogleplusNotVisible() ***");
		}
	}


	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadDefaultButtonsList", groups = { "PageOptions-button" })
	public void PinterestVisible(String url) {
		System.out.println("\nTesting PinterestVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--pinterest";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertTrue(isVisible, "*** Error: Pinterest Button Not Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in PinterestVisible() ***");
		}
	}

	// The font resize button is only visible on content type pages.  Testing that
	// it's shown on those pages.
	// ------------------------------------------------------------------------------
	@Test(dataProvider = "loadNoDefaultButtonsList", groups = { "PageOptions-button" })
	public void PinterestNotVisible(String url) {
		System.out.println("\nTesting PinterestNotVisible()");
		System.out.println("    " + url);
		logger.log(LogStatus.INFO, "Testing url: " + url);

		driver.get(url);

		PageOptions page;
		String cssSelector = "social-share--pinterest";

		try {
			page = new PageOptions(driver);
			boolean isVisible = page.ButtonVisible(cssSelector);
			Assert.assertFalse(isVisible, "*** Error: Pinterest Button Found ***");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("*** Error loading page in PinterestNotVisible() ***");
		}
	}

	// Method to create URL for data provider to be tested
	// ---------------------------------------------------
	// private String AddHostname(String path) {
	// 	String tier = "QA";         // Hard-code string for environment/host used
	// 	String host;

	// 	switch (tier.toUpperCase()) {
	// 		case "PROD": host = "www.cancer.gov";
	// 				     break;
	// 		case "DEV":  host = "www-blue-dev.cancer.gov";
	// 				     break;
	// 		case "QA":   host = "www-qa.cancer.gov";
	// 				     break;
	// 		case "DT":   host = "www-dt-qa.cancer.gov";
	// 				     break;
    //         default:     host = "www-qa.cancer.gov";
	// 	}
	// 	return "https://" + host + path;
	// }


/*  ***************************** Test Methods *********************************************** */

	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs with Page Options that are visible.
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadVisibleOptionsList")
	public Iterator<Object[]> loadVisiblePageOptionsList() {
		// String TESTDATA_SHEET_NAME = "Visible Page Options";
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			String pageOptionsType   = excelReader.getCellData(TESTDATA_SHEET_NAME,
																"PageOptions Type", rowNum);

			String url = AddHostname(pageOptionsPath);

			if (!pageOptionsType.equals("none")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	// DataProvider to read the Excel spreadsheet with data containing URLs to be checked
	// Using worksheet indicating URLs without Page Options
	// ----------------------------------------------------------------------------------
	@DataProvider(name = "loadNotVisiblePageOptionsList")
	public Iterator<Object[]> loadNotVisiblePageOptionsList() {
		// String TESTDATA_SHEET_NAME = "Not Visible Page Options";
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			String pageOptionsType   = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Type", rowNum);
			String url = AddHostname(pageOptionsPath);

			if (pageOptionsType.equals("none")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadButtonOnEachPageList")
	public Iterator<Object[]> loadAllPageOptionsList() {
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Type", rowNum);
			String url = AddHostname(pageOptionsPath);

			if (pageOptionsType.equals("all") || pageOptionsType.equals("default")
											  || pageOptionsType.equals("r4r")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadDefaultButtonsList")
	public Iterator<Object[]> loadDefaultButtonsList() {
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Type", rowNum);
			String url = AddHostname(pageOptionsPath);

			if (pageOptionsType.equals("all") || pageOptionsType.equals("default")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadNoDefaultButtonsList")
	public Iterator<Object[]> loadNoDefaultButtonsList() {
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Type", rowNum);
			String url = AddHostname(pageOptionsPath);

			if (pageOptionsType.equals("r4r") || pageOptionsType.equals("none")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadFontResizeList")
	public Iterator<Object[]> loadFontResizeList() {
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Type", rowNum);
			String url = AddHostname(pageOptionsPath);

			if (pageOptionsType.equals("all")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadNoFontResizeList")
	public Iterator<Object[]> loadNoFontResizeList() {
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			"PageOptions Type", rowNum);

			String url = AddHostname(pageOptionsPath);

			if (!pageOptionsType.equals("all")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}


	@DataProvider(name = "loadR4RButtonList")
	public Iterator<Object[]> loadR4RButtonList() {
		String TESTDATA_SHEET_NAME = "All Pages";
		ExcelManager excelReader = new ExcelManager(TESTDATA_PATH);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String pageOptionsPath    = excelReader.getCellData(TESTDATA_SHEET_NAME,
			                  									"PageOptions Link", rowNum);
			String pageOptionsType    = excelReader.getCellData(TESTDATA_SHEET_NAME,
																"PageOptions Type", rowNum);
			String url = AddHostname(pageOptionsPath);

			if (pageOptionsType.equals("r4r")) {
				Object ob[] = { url };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
}