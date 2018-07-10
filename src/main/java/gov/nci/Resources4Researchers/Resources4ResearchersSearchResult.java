package gov.nci.Resources4Researchers;

import java.util.List;

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

	@FindBy(how = How.XPATH, using = "//article[@class='home__desc  r4r-DEFAULT']")
	WebElement text_HomeDesc;
	@FindBy(how = How.LINK_TEXT, using = "Learn more about Resources for Researchers.")
	WebElement lnk_Learn_more_about_R4R;
	@FindBy(how = How.XPATH, using = "//div[@class='home-tile__icon  r4r-DEFAULT'][1]")
	WebElement icon_SearchForResources;
	@FindBy(how = How.XPATH, using = "//div[@class='home-tile__icon  r4r-DEFAULT'][2]")
	WebElement icon_BrowseResources;
	@FindBy(how = How.XPATH, using = "//h2[@id='ui-id-2']")
	WebElement lbl_SearchForResources;
	@FindBy(how = How.XPATH, using = "//form[@class='searchbar__container  cancer-gov']")
	WebElement box_Search;
	@FindBy(how = How.XPATH, using = "//input[@type='text'][@placeholder='Search Resources for Researchers']")
	WebElement txt_Search;
	@FindBy(how = How.XPATH, using = "//button[@class='searchbar__button--submit  button']")
	WebElement btn_Search;
	@FindBy(how = How.XPATH, using = "//h2[@id='ui-id-3']")
	WebElement lbl_BrowseResources;
	@FindBy(how = How.XPATH, using = "//a[@class='view-all__link  arrow-link']")
	WebElement lnk_ViewAllResources;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Tool Type']")
	WebElement section_BrowseByToolType;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Tool Type']/h4")
	WebElement lbl_BrowseByToolType;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Tool Type']/div/a")
	List<WebElement> toolTypeItems;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']/h4")
	WebElement lbl_BrowseByResearchArea;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']/div/a")
	List<WebElement> researchAreaItems;

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

	public WebElement getPageHomeDescription() {
		return text_HomeDesc;
	}

	public WebElement getSearchBox() {
		return box_Search;
	}

	public String getSearchBoxInnerText() {
		return box_Search.getAttribute("placeholder");
	}

	public WebElement getSearchButton() {
		return btn_Search;
	}

	//Search Resources for Researchers based on any keyword
	public void search(String keyword) {
		getSearchBox().click();
		getSearchBox().sendKeys(keyword);
		getSearchButton().click();
	}

	public void clickViewAllResources() {
		lnk_ViewAllResources.click();
	}

	public List<WebElement> getToolTypeOptions() {
		return toolTypeItems;
	}

	public List<WebElement> getResearchAreaOptions() {
		return researchAreaItems;
	}

}
