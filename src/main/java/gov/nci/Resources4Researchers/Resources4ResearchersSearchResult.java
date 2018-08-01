package gov.nci.Resources4Researchers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class Resources4ResearchersSearchResult {

	WebDriver driver;
	ExtentTest logger;

	public static final String R4R_PAGE_TITLE = "Resources for Researchers: Search Results - National Cancer Institute";
	public static final String R4R_H1_PAGE_TITLE = "Resources for Researchers Search Results";
	public final String BREAD_CRUMB = "Home\nResearch";

	/*************** Resources for Researchers Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement r4rSearchResultH1PageTitle;
	@FindBy(how = How.LINK_TEXT, using = "Resources for Researchers Home")
	WebElement lnk_Resources4ResearchersHome;
	@FindBy(how = How.XPATH, using = "form[@class='searchbar__container  cancer-gov']/input")
	WebElement txt_SearchBar;
	@FindBy(how = How.XPATH, using = "div[@class='r4r-pager__count  r4r-DEFAULT']")
	WebElement lbl_ResultCount;

	// Initializing the Page Objects
	public Resources4ResearchersSearchResult(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
	}

	public String getPageTitle() {
		return R4R_PAGE_TITLE;
	}

	public WebElement getPageH1Title() {
		return r4rSearchResultH1PageTitle;
	}

	public void clickResources4ResearchersHome() {
		lnk_Resources4ResearchersHome.click();
	}

}
