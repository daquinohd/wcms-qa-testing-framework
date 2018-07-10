package gov.nci.Resources4Researchers;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.ExtentTest;

public class Resources4ResearchersHome {

	WebDriver driver;
	ExtentTest logger;

	public static final String R4R_PAGE_TITLE = "Resources for Researchers";
	public static final String R4R_RESULT_PAGE_TITLE = "Resources for Researchers Search Results";
	public static final String R4R_LEARN_MORE_PAGE_URL = "https://www.cancer.gov/research/about-r4r";
	public final String BREAD_CRUMB = "Home\nResearch";
	public static final List<String> TOOL_TYPE_OPTIONS = Lists.newArrayList("Analysis Tools (98)",
			"Datasets & Databases (58)", "Lab Tools (29)", "Terminology (6)", "Networks/Consortiums (4)",
			"Clinical Research Tools (2)", "Community Research Tools (1)");

	/*************** Resources for Researchers Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement r4rPageTitle;
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
	//List<WebElement> toolTypeItems;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']/h4")
	WebElement lbl_BrowseByResearchArea;
	@FindBy(how = How.XPATH, using = "//div[@class='home-nav__section  r4r-DEFAULT'][@aria-label='Browse by Research Area']/div/a")
	List<WebElement> researchAreaItems;

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

	public boolean compareToolTypesOptions() {
		List<WebElement> toolTypeOptions = getToolTypeOptions();
		List<String> actualToolTypeOptions = new ArrayList<>();
		for (WebElement ele : toolTypeOptions) {
			System.out.println("Name + Number===>" + ele.getText());
			actualToolTypeOptions.add(ele.getText());

			String s = ele.getText();
			s = s.substring(0, s.indexOf("("));

			//s = s.substring(s., s.indexOf("("));
			//s = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
			//System.out.println("Number==>" + s);
			System.out.println("Name==>" + s);
		}

		boolean areTooltipsAsExpected = TOOL_TYPE_OPTIONS.equals(actualToolTypeOptions);
		return areTooltipsAsExpected;
		//Assert.assertTrue(areTooltipsAsExpected);

	}

	public List<WebElement> getResearchAreaOptions() {
		return researchAreaItems;
	}

}
