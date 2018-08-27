package gov.nci.Resources4Researchers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.ExtentTest;

public class Resources4ResearchersSearchResult {

	WebDriver driver;
	ExtentTest logger;

	public static final String R4R_SEARCH_RESULT_PAGE_TITLE = "Resources for Researchers: Search Results - National Cancer Institute";
	public static final String R4R_SEARCH_RESULT_H1_PAGE_TITLE = "Resources for Researchers Search Results";
	public static final String R4R_HOME_PAGE_URL = "https://www-blue-dev.cancer.gov/research/r4r/";
	public final String BREAD_CRUMB = "Home\nResearch";
	public static final List<String> EXP_TOOL_TYPE_OPTIONS = Lists.newArrayList("Analysis Tools",
			"Datasets & Databases", "Lab Tools", "Terminology", "Networks/Consortiums", "Clinical Research Tools",
			"Community Research Tools");
	public static final List<String> EXP_RESEARCH_AREA_OPTIONS = Lists.newArrayList("Cancer Treatment", "Cancer Omics",
			"Cancer Biology", "Screening & Detection", "Cancer Statistics", "Cancer Health Disparities",
			"Cancer & Public Health", "Cancer Diagnosis", "Causes of Cancer", "Cancer Survivorship",
			"Cancer Prevention");
	public static final List<String> EXP_RESEARCH_TYPE_OPTIONS = Lists.newArrayList("Basic", "Translational",
			"Clinical Trials", "Epidemiologic", "Clinical");
	public static final List<String> EXP_RESEARCH_SUBTYPE_OPTIONS_ANALYSIS_TOOLS = Lists.newArrayList(
			"Genomic Analysis", "Data Visualization", "Imaging Analysis", "R Software", "Modeling",
			"Statistical Software", "Natural Language Processing");
	public static final List<String> EXP_RESEARCH_SUBTYPE_OPTIONS_DATASETS = Lists.newArrayList("Clinical Data",
			"Imaging", "Genomic Datasets", "Epidemiologic Data", "Patient Registries", "Biological Networks");
	public static final List<String> EXP_RESEARCH_SUBTYPE_OPTIONS_LAB = Lists.newArrayList("Reagents", "Biospecimen",
			"Assays", "Cell Lines", "Protocols", "Animal Models", "Compounds", "Plant Samples", "Vectors");
	public static final List<String> EXP_RESEARCH_SUBTYPE_OPTIONS_CLINICAL_RESEARCH = Lists.newArrayList("CTCAE");
	public static final List<String> EXP_RESEARCH_SUBTYPE_OPTIONS_COMMUNITY_RESEARCH = Lists
			.newArrayList("Questionnaire");

	/*************** Resources for Researchers Search Results Page WebElements **********************/
	@FindBy(how = How.XPATH, using = ".//h1")
	WebElement r4rSearchResultH1PageTitle;
	@FindBy(how = How.LINK_TEXT, using = "Resources for Researchers Home")
	WebElement lnk_Resources4ResearchersHome;

	@FindBy(how = How.XPATH, using = "//form[@class='searchbar__container  cancer-gov']")
	WebElement box_Search;
	@FindBy(how = How.XPATH, using = "//form[@class='searchbar__container  cancer-gov']/input")
	WebElement txt_SearchBoxInput;
	@FindBy(how = How.XPATH, using = "//button[@class='searchbar__button--submit  button']")
	WebElement btn_Search;

	@FindBy(how = How.XPATH, using = "//div[@class='r4r-pager__count  r4r-DEFAULT']")
	WebElement lbl_ResultCount;

	@FindBy(how = How.XPATH, using = "//div[@class='facet__box facet__box--tool-types r4r-DEFAULT']")
	WebElement box_ToolType;
	@FindBy(how = How.XPATH, using = "//div[@class='facet__box facet__box--tool-types r4r-DEFAULT']/h4")
	WebElement lbl_ToolType;
	@FindBy(how = How.XPATH, using = "//div[@class='facet__box facet__box--tool-types r4r-DEFAULT']/label/span")
	List<WebElement> toolTypeItems;

	@FindBy(how = How.XPATH, using = "(//div[@class='facet__box  r4r-DEFAULT'])[1]")
	WebElement box_ResearchAreas;
	@FindBy(how = How.XPATH, using = "//div[@class='facet__box  r4r-DEFAULT']/h4 [contains(text(),'Research Areas')]")
	WebElement lbl_ResearchAreas;
	@FindBy(how = How.XPATH, using = "(//div[@class='facet__box  r4r-DEFAULT'])[1]/label/span")
	List<WebElement> researchAreaItems;

	@FindBy(how = How.XPATH, using = "(//div[@class='facet__box  r4r-DEFAULT'])[2]")
	WebElement box_ResearchTypes;
	@FindBy(how = How.XPATH, using = "//div[@class='facet__box  r4r-DEFAULT']/h4 [contains(text(),'Research Types')]")
	WebElement lbl_ResearchTypes;
	@FindBy(how = How.XPATH, using = "(//div[@class='facet__box  r4r-DEFAULT'])[2]/label/span")
	List<WebElement> researchTypesItems;

	@FindBy(how = How.XPATH, using = "//section[@class='results-container  r4r-DEFAULT']/article/h2")
	List<WebElement> searchResultItems;

	// Initializing the Page Objects
	public Resources4ResearchersSearchResult(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
	}

	public String getPageTitle() {
		return R4R_SEARCH_RESULT_PAGE_TITLE;
	}

	public WebElement getPageH1Title() {
		return r4rSearchResultH1PageTitle;
	}

	public WebElement getr4rHomeLink() {
		return lnk_Resources4ResearchersHome;
	}

	public void clickResources4ResearchersHome() {
		lnk_Resources4ResearchersHome.click();
	}

	public WebElement getSearchBox() {
		return box_Search;
	}

	public WebElement getSearchBoxInput() {
		return txt_SearchBoxInput;
	}

	public WebElement getSearchButton() {
		return btn_Search;
	}

	//Search Resources for Researchers based on any keyword
	public void search(String keyword) {
		getSearchBox().click();
		getSearchBoxInput().sendKeys(keyword);
		getSearchButton().click();
	}

	public WebElement getResultCount() {
		return lbl_ResultCount;
	}

	public WebElement getToolTypeBox() {
		return box_ToolType;
	}

	public WebElement getToolTypeLabel() {
		return lbl_ToolType;
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

	public WebElement getResearchAreaBox() {
		return box_ResearchAreas;
	}

	public WebElement getResearchAreaLabel() {
		return lbl_ResearchAreas;
	}

	public List<WebElement> getResearchAreaOptions() {
		//int toolTypeLastItem = getToolTypeOptions().size();

		//FunctionLibrary.scrollIntoView(driver, -500);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
			String researchAreaNumber = researchAreaNameWithNumber
					.substring(researchAreaNameWithNumber.indexOf("(") + 1, researchAreaNameWithNumber.indexOf(")"));
			System.out.println("Research Area Number==>" + researchAreaNumber);
			researchAreaNumberAsString.add(researchAreaNumber.trim());
		}
		System.out.println("researchAreaNumberAsString===>" + researchAreaNumberAsString);
		return researchAreaNumberAsString;

	}

	public WebElement getResearchTypeBox() {
		return box_ResearchTypes;
	}

	public WebElement getResearchTypesLabel() {
		return lbl_ResearchTypes;
	}

	public List<WebElement> getResearchTypeOptions() {
		return researchTypesItems;
	}

	public List<String> getResearchTypeOptionsName() {
		List<WebElement> researchTypeOptions = getResearchTypeOptions();
		List<String> researchTypeNameAsString = new ArrayList<>();
		for (WebElement ele : researchTypeOptions) {
			System.out.println("Research Type Name + Number===>" + ele.getText());
			String researchTypeNameWithNumber = ele.getText();
			String researchTypeName = researchTypeNameWithNumber.substring(0, researchTypeNameWithNumber.indexOf("("));
			System.out.println("Research Type Name==>" + researchTypeName);
			researchTypeNameAsString.add(researchTypeName.trim());
		}
		System.out.println("researchTypeNameAsString===>" + researchTypeNameAsString);
		return researchTypeNameAsString;

	}

	public List<String> getResearchTypeOptionsNumber() {
		List<WebElement> researchTypeOptions = getResearchTypeOptions();
		List<String> researchTypeNumberAsString = new ArrayList<>();
		for (WebElement ele : researchTypeOptions) {
			System.out.println("Research Type Name + Number===>" + ele.getText());
			String researchTypeNameWithNumber = ele.getText();
			String researchTypeNumber = researchTypeNameWithNumber
					.substring(researchTypeNameWithNumber.indexOf("(") + 1, researchTypeNameWithNumber.indexOf(")"));
			System.out.println("Research Area Number==>" + researchTypeNumber);
			researchTypeNumberAsString.add(researchTypeNumber.trim());
		}
		System.out.println("researchTypeNumberAsString===>" + researchTypeNumberAsString);
		return researchTypeNumberAsString;

	}

}
