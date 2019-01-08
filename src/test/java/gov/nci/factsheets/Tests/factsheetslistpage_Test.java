package gov.nci.factsheets.Tests;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.factsheets.FactSheetsListPage;
import gov.nci.framework.ParsedURL;



public class factsheetslistpage_Test extends BaseClass {

	public final String FACTSHEETLISTPAGE_Browser_TITLE = "Fact Sheets - National Cancer Institute";
    public static final String FACTSHEETLISTPAGE_PAGE_TITLE = "NCI Fact Sheets";
	public static final String FACTSHEETSLISTPAGE_INTRO_TEXT = "The NCI fact sheet collection addresses a variety of cancer topics. Fact sheets are updated and revised based on the latest cancer research.";
    public final String BREAD_CRUMB = "Home\nPublications";
    public static final String FACTSHEETLISTPAGE_URL = "/publications/fact-sheets";
    public static final List<String> EXP_FACTSHEETLISTS_ON_FACTSHEETSLISTPAGE= Lists.newArrayList("Cancer Therapy","Cancer Types",
			"Detection and Diagnosis", "Diet and Nutrition", "Prevention", "Risk Factors and Possible Causes", "Support, Coping, and Resources",
			"Tobacco and Smoking Cessation", "En Español");


	FactSheetsListPage fslp;
    BreadCrumb crumb;
	Banner banner;


	@BeforeClass
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("FactSheetsListPageURL");
	    driver = BrowserManager.startBrowser(browser, config, pageURL);
	    System.out.println("FactSheetsList Page setup done");
	}

	@Test
	public void verifyFSlistPageBrowserTitle() {
		try {
		fslp = new FactSheetsListPage(driver, logger);
		Assert.assertEquals(driver.getTitle(), FACTSHEETLISTPAGE_Browser_TITLE);
		logger.log(LogStatus.PASS, "Verify that Browser Title of the page is *Fact Sheets - National Cancer Institute* | Actual Result: "
 				+ driver.getTitle());
	        }catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error displaying Fact Sheet List Page Browser Title.");
	        }
	}


	@Test
    public void verifyFSlistPageH1Title() {
		try {
    	fslp = new FactSheetsListPage(driver, logger);
    	Assert.assertEquals(fslp.getPageH1Title().getText(),FACTSHEETLISTPAGE_PAGE_TITLE);
    	logger.log(LogStatus.PASS, "Verify that H1 Title of the page is *NCI Fact Sheets* | Actual Result: "
				+ fslp.getPageH1Title());
		    }catch (MalformedURLException | UnsupportedEncodingException e) {
			Assert.fail("Error displaying Fact Sheet List Page H1 title.");
	        }
    }

    @Test
	public void verifyfactsheetDesciptionText() {
		try {
		fslp = new FactSheetsListPage(driver, logger);
		Assert.assertTrue(fslp.getPageIntroText().getText().contains(FACTSHEETSLISTPAGE_INTRO_TEXT));
		logger.log(LogStatus.PASS, "Verify that intro text is displayed below the title of the page");
		    }catch (MalformedURLException | UnsupportedEncodingException e) {
				Assert.fail("Error displaying Fact Sheet List Page Description Text.");
		   }
  }

	@Test
    public void verifyBreadCrumb() {
		driver.get(pageURL);
		crumb = new BreadCrumb(driver);
		Assert.assertEquals(crumb.getBreadCrumbText(), BREAD_CRUMB);
		logger.log(LogStatus.PASS, "Pass => " + "Verifying the Breadcrumb of the page");
	}

    @Test
	public void verifyBanner() {
		driver.get(pageURL);
		banner = new Banner(driver);
		Assert.assertTrue(banner.isDisplayed());
		Assert.assertEquals(banner.getAltText(), "National Cancer Institute");
		logger.log(LogStatus.PASS, "Verifying the Banner of the page");
	}

    @Test
    public void verifyFSlistPageURL() {
    	try {
    	fslp = new FactSheetsListPage(driver, logger);
    	ParsedURL url = fslp.getPageUrl();
		Assert.assertEquals(url.getPath(), FACTSHEETLISTPAGE_URL,
				"Unexpected URL path.");
	        }catch (MalformedURLException | UnsupportedEncodingException e) {
	        	Assert.fail("Error loading Fact Sheet List Page.");
	        }
  }
}

