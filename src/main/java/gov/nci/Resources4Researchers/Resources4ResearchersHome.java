package gov.nci.Resources4Researchers;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.ExtentTest;

public class Resources4ResearchersHome {

	WebDriver driver;
	ExtentTest logger;

	public static final String R4R_PAGE_TITLE = "Resources for Researchers";
	public static final String R4R_RESULT_PAGE_TITLE = "Resources for Researchers Search Results";
	public static final String R4R_LEARN_MORE_PAGE_URL = "https://www.cancer.gov/research/resources/about";
	public static final String R4R_INTRO_TEXT = "Resources for Researchers is a directory of NCI-supported tools and services for cancer researchers. Most resources are free of cost and available to anyone.";
	public final String BREAD_CRUMB = "Home\nResearch";
	public static final List<String> EXP_TOOL_TYPE_OPTIONS = Lists.newArrayList("Analysis Tools",
			"Datasets & Databases", "Lab Tools", "Community Research Tools", "Terminology", "Networks/Consortiums",
			"Clinical Research Tools", "Training Resources");
	public static final List<String> EXP_RESEARCH_AREA_OPTIONS = Lists.newArrayList("Cancer Biology", "Cancer Omics",
			"Cancer Treatment", "Screening & Detection", "Causes of Cancer", "Cancer Health Disparities",
			"Cancer & Public Health", "Cancer Diagnosis", "Cancer Statistics", "Cancer Prevention",
			"Cancer Survivorship", "Bioinformatics");

	/***************
	 * Resources for Researchers Page WebElements
	 **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement r4rPageTitle;
	@FindBy(how = How.XPATH, using = "//article[@class='home__desc  r4r-DEFAULT']/p")
	WebElement text_HomeDesc;
	@FindBy(how = How.XPATH, using = "//a[@class='r4r__link--about  r4r-DEFAULT']")
	WebElement lnk_Learn_more_about_R4R;
	@FindBy(how = How.XPATH, using = "(//div[@class='home-tile__icon  r4r-DEFAULT'])[1]")
	WebElement icon_SearchForResources;
	@FindBy(how = How.XPATH, using = "(//*[@class='home-tile__text']/h2)[1]")
	WebElement lbl_SearchForResources;
	@FindBy(how = How.XPATH, using = "(//*[@class='home-tile__text']/p)[1]")
	WebElement lbl_SearchForResourcesText;
	@FindBy(how = How.XPATH, using = "(//div[@class='home-tile__icon  r4r-DEFAULT'])[2]")
	WebElement icon_BrowseResources;
	@FindBy(how = How.XPATH, using = "(//*[@class='home-tile__text']/h2)[2]")
	WebElement lbl_BrowseResources;
	@FindBy(how = How.XPATH, using = "(//*[@class='home-tile__text']/p)[2]")
	WebElement lbl_BrowseResourcesText;

	@FindBy(how = How.XPATH, using = "//form[@class='searchbar__container  cancer-gov']")
	WebElement box_Search;
	@FindBy(how = How.XPATH, using = "//input[@type='text'][@placeholder='Search Resources for Researchers']")
	WebElement txt_Search;
	@FindBy(how = How.XPATH, using = "//form[@class='searchbar__container  cancer-gov']/input")
	WebElement lbl_SearchPlaceholder;
	@FindBy(how = How.XPATH, using = "//button[@class='searchbar__button--submit  button']")
	WebElement btn_Search;

	@FindBy(how = How.XPATH, using = "//a[@class='view-all__link  arrow-link']")
	WebElement lnk_ViewAllResources;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Tool Type']")
	WebElement section_BrowseByToolType;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Tool Type']/h4")
	WebElement lbl_BrowseByToolType;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Tool Type']/div/a")
	List<WebElement> toolTypeItems;

	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']")
	WebElement section_BrowseByResearchArea;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']/h4")
	WebElement lbl_BrowseByResearchArea;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']/div/a")
	List<WebElement> researchAreaItems;
	@FindBy(how = How.XPATH, using = "//li[@class='page-options--email']")
	WebElement lnk_ShareEmail;
	@FindBy(how = How.XPATH, using = "//li[@class='social-share social-share--twitter']")
	WebElement lnk_ShareTwitter;

	// Initializing the Page Objects
	public Resources4ResearchersHome(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
	}

	public WebElement getPageH1Title() {
		return r4rPageTitle;
	}

	public WebElement getPageHomeDescription() {
		return text_HomeDesc;
	}

	public void clickLearnMoreAboutR4R() {
		lnk_Learn_more_about_R4R.click();
	}

	public WebElement getSearchForResourcesIcon() {
		return icon_SearchForResources;
	}

	public WebElement getSearchForResourcesLabel() {
		return lbl_SearchForResources;
	}

	public WebElement getSearchForResourcesText() {
		return lbl_SearchForResourcesText;
	}

	public WebElement getBrowseResourcesIcon() {
		return icon_BrowseResources;
	}

	public WebElement getBrowseResourcesLabel() {
		return lbl_BrowseResources;
	}

	public WebElement getBrowseResourcesText() {
		return lbl_BrowseResourcesText;
	}

	public WebElement getSearchBox() {
		return box_Search;
	}

	public String getSearchBoxInnerText() {
		System.out.println("Placeholder Text: " + txt_Search.getAttribute("placeholder"));
		return txt_Search.getAttribute("placeholder");
	}

	public WebElement getSearchInput() {
		return txt_Search;
	}

	public WebElement getSearchButton() {
		return btn_Search;
	}

	// Search Resources for Researchers based on any keyword
	public void search(String keyword) {
		getSearchInput().click();
		getSearchInput().sendKeys(keyword);
		getSearchButton().click();
	}

	// Search Resources for Researchers based on any keyword and hitting ENTER
	// KEY
	public void searchWithEnterKey(String keyword) {
		getSearchInput().click();
		getSearchInput().sendKeys(keyword);
		getSearchInput().sendKeys(Keys.RETURN);
	}

	public String getViewAllResourcesNumber() {
		System.out.println("View All Resources Link Text with Number: " + lnk_ViewAllResources.getText());
		String viewAllResourcesLinkText = lnk_ViewAllResources.getText();

		String viewAllResourcesNumber = viewAllResourcesLinkText.substring(viewAllResourcesLinkText.indexOf("(") + 1,
				viewAllResourcesLinkText.indexOf(")"));
		System.out.println("View All Resource Number ==>" + viewAllResourcesNumber);
		return viewAllResourcesNumber;
	}

	public void clickViewAllResources() {
		System.out.println("View All Resources Link Text with Number: " + lnk_ViewAllResources.getText());
		lnk_ViewAllResources.click();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.urlContains("research/resources/search"));
	}

	public WebElement getBrowseByToolTypeSection() {
		return section_BrowseByToolType;
	}

	public WebElement getBrowseByToolTypeLabel() {
		return lbl_BrowseByToolType;
	}

	public List<WebElement> getToolTypeOptions() {
		return toolTypeItems;
	}

	public List<String> getToolTypesOptionsName() {
		List<WebElement> toolTypeOptions = getToolTypeOptions();
		List<String> toolTypeNameAsString = new ArrayList<>();
		for (WebElement ele : toolTypeOptions) {
			System.out.println("Tool Type Name + Number===>" + ele.getText());
			String toolTypeNameWithNumber = ele.getText();
			String toolTypeName = toolTypeNameWithNumber.substring(0, toolTypeNameWithNumber.indexOf("("));
			System.out.println("Tool Type Name==>" + toolTypeName);
			toolTypeNameAsString.add(toolTypeName.trim());
		}
		System.out.println("toolTypeNameAsString===>" + toolTypeNameAsString);
		return toolTypeNameAsString;

	}

	public List<String> getToolTypesOptionsNumber() {
		List<WebElement> toolTypeOptions = getToolTypeOptions();
		List<String> toolTypeNumberAsString = new ArrayList<>();
		for (WebElement ele : toolTypeOptions) {
			System.out.println("Tool Type Name + Number===>" + ele.getText());
			String toolTypeNameWithNumber = ele.getText();
			String toolTypeNumber = toolTypeNameWithNumber.substring(toolTypeNameWithNumber.indexOf("(") + 1,
					toolTypeNameWithNumber.indexOf(")"));
			System.out.println("Tool Type Name==>" + toolTypeNumber);
			toolTypeNumberAsString.add(toolTypeNumber.trim());
		}
		System.out.println("toolTypeNumberAsString===>" + toolTypeNumberAsString);
		return toolTypeNumberAsString;

	}

	public WebElement getBrowseByResearchAreaBox() {
		return section_BrowseByResearchArea;
	}

	public WebElement getBrowseByResearchAreaLabel() {
		return lbl_BrowseByResearchArea;
	}

	public List<WebElement> getResearchAreaOptions() {
		return researchAreaItems;
	}

	public List<String> getResearchAreaOptionsName() {
		List<WebElement> researchAreaOptions = getResearchAreaOptions();
		List<String> researchAreaNameAsString = new ArrayList<>();
		for (WebElement ele : researchAreaOptions) {
			System.out.println("Research Area Name + Number===>" + ele.getText());
			String researchAreaNameWithNumber = ele.getText();
			String researchAreaName = researchAreaNameWithNumber.substring(0, researchAreaNameWithNumber.indexOf("("));
			System.out.println("Research Area Name==>" + researchAreaName);
			researchAreaNameAsString.add(researchAreaName.trim());
		}
		System.out.println("researchAreaNameAsString===>" + researchAreaNameAsString);
		return researchAreaNameAsString;

	}

	public List<String> getResearchAreaOptionsNumber() {
		List<WebElement> researchAreaOptions = getResearchAreaOptions();
		List<String> researchAreaNumberAsString = new ArrayList<>();
		for (WebElement ele : researchAreaOptions) {
			System.out.println("Research Area Name + Number===>" + ele.getText());
			String researchAreaNameWithNumber = ele.getText();
			String toolTypeNumber = researchAreaNameWithNumber.substring(researchAreaNameWithNumber.indexOf("(") + 1,
					researchAreaNameWithNumber.indexOf(")"));
			System.out.println("Research Area Number==>" + toolTypeNumber);
			researchAreaNumberAsString.add(toolTypeNumber.trim());
		}
		System.out.println("researchAreaNumberAsString===>" + researchAreaNumberAsString);
		return researchAreaNumberAsString;

	}

	/*
	 * Get Email icon
	 */
	public WebElement getEmailIcon() {
		return lnk_ShareEmail;
	}

	/*
	 * Get Twitter icon
	 */
	public WebElement getTwitterIcon() {
		return lnk_ShareTwitter;
	}

}
