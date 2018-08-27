package gov.nci.commonobjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class SitewideSearch {
	WebDriver driver;
	ExtentTest logger;

	public final String SEARCH_RESULT_PAGE_TITLE = "NCI Search Results - National Cancer Institute";
	public static final String SEARCH_RESULT_H1_TITLE = "NCI Search Results";

	/*************** Site-wide Search Page WebElements **********************/

	@FindBy(how = How.XPATH, using = "//input[@id='swKeyword']")
	WebElement txt_SearchBox;
	@FindBy(how = How.XPATH, using = "//button[@id='sitesearch']")
	WebElement btn_Search;

	/*************** Site-wide Search Results Page WebElements **********************/
	@FindBy(how = How.XPATH, using = "//label[@for='ctl34_rblSWRSearchType_0']")
	WebElement rbtn_NewSearch;
	@FindBy(how = How.XPATH, using = "//label[@for='ctl34_rblSWRSearchType_1']")
	WebElement rbtn_SearchWithinSearch;
	@FindBy(how = How.XPATH, using = "//input[@id='ctl34_txtSWRKeyword']")
	WebElement txt_SearchWithinSearchBox;
	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	WebElement btn_SearchWithinSearch;
	@FindBy(how = How.XPATH, using = "//span[@id='ctl34_lblSearchWithinResultsFound']")
	WebElement lbl_SearchResult;
	@FindBy(how = How.XPATH, using = "//div[@id='best-bet-definition']")
	WebElement box_BestBet;

	// Initializing the Page Objects
	public SitewideSearch(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
	}

	public WebElement getSearchBox() {
		return txt_SearchBox;
	}

	public WebElement getSearchButton() {
		return btn_Search;
	}

	//Site-wide Search based on any keyword
	public void search(String keyword) {
		getSearchBox().click();
		getSearchBox().clear();
		getSearchBox().sendKeys(keyword);
		getSearchButton().click();
	}

	// Get Search Results page title
	public String getSearchResultsPageTitle() {
		return driver.getTitle();
	}

	// Get Search Results page h1 title
	public String getSearchResultsH1Title() {
		return SEARCH_RESULT_H1_TITLE;
	}

	// Get Search Results page best bet box
	public WebElement getBestBetBox() {
		return box_BestBet;
	}

	// Get Search Results page Search Within Search Box
	public WebElement getSearchWithinSearchBox() {
		return txt_SearchWithinSearchBox;
	}

	public WebElement getSearchWithinSearchButton() {
		return btn_SearchWithinSearch;
	}

	//Search Within Search based on any two keyword
	public void SearchWithinSearch(String keyword1, String keyword2) {

		search(keyword1);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("arguments[0].scrollIntoView(true);",lbl_Search_Result);
		jse.executeScript("window.scrollBy(0,1250)", "");
		rbtn_SearchWithinSearch.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getSearchWithinSearchBox().sendKeys(keyword2);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Text typed in second box: " + getSearchWithinSearchBox().getText());
		getSearchWithinSearchButton().click();
	}

}
