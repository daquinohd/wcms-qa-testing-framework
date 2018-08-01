package gov.nci.Resources4Researchers;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class Resources4ResearchersResourceDetailPage {

	WebDriver driver;
	ExtentTest logger;

	/*
	 * Constants
	 */

	// public static final String R4R_H1_PAGE_TITLE = "Resources for Researchers
	// Search Results";
	public final String BREAD_CRUMB = "Home\nResearch";
	public static final String R4R_HOME_PAGE_URL = "https://www-qa.cancer.gov/research/about-r4r";

	/*
	 * Resources for Researchers Resource Detail Page WebElements
	 * 
	 */
	@FindBy(how = How.XPATH, using = "//*[@id='r4r-root']/main/div[2]/header/h1")
	WebElement r4rResourceDetailH1PageTitle;
	@FindBy(how = How.LINK_TEXT, using = "< Back to results")
	WebElement lnk_BacktoSearchResults;
	@FindBy(how = How.LINK_TEXT, using = "Resources for Researchers Home")
	WebElement lnk_Resources4ResearchersHome;
	@FindBy(how = How.XPATH, using = "//div[@class='resource__link--external  r4r-DEFAULT']/a")
	WebElement btn_VisitResource;
	@FindBy(how = How.XPATH, using = "//div[@class='r4r-svg-container  r4r-DEFAULT']")
	WebElement resourceAccessImage;
	@FindBy(how = How.XPATH, using = "//div[@class='resource__access  r4r-DEFAULT']/h4")
	WebElement resourceAccessText;
	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'Contact Information')]")
	WebElement contactInfoTitle;
	@FindBy(how = How.XPATH, using = "//address[@class='contact-information  r4r-DEFAULT']")
	WebElement contactInfoDetail;

	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'NCI Affiliation')]")
	WebElement nciAffiliationTitle;
	@FindBy(how = How.XPATH, using = "//article[@class='resource__docs  r4r-DEFAULT']")
	WebElement nciAffiliationDetail;

	@FindBy(how = How.XPATH, using = "//form[@class='searchbar__container  cancer-gov']")
	WebElement box_Search;
	@FindBy(how = How.XPATH, using = "//input[@type='text'][@placeholder='Search resources']")
	WebElement txt_Search;
	@FindBy(how = How.XPATH, using = "//button[@class='searchbar__button--submit  button']")
	WebElement btn_Search;
	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'Find Related Resources')]")
	WebElement findRelatedResourcesTitle;
	@FindBy(how = How.XPATH, using = "//div[@class='similar-resource__container  r4r-DEFAULT']/a")
	List<WebElement> relatedResourcesItems;
	@FindBy(how = How.XPATH, using = "//li[@class='page-options--email']")
	WebElement lnk_ShareEmail;
	@FindBy(how = How.XPATH, using = "//li[@class='social-share social-share--twitter']")
	WebElement lnk_ShareTwitter;

	/*
	 * Initializing the Page Objects
	 */

	public Resources4ResearchersResourceDetailPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
	}

	/*
	 * Get Page title of Search Results page
	 */
	// public String getPageTitle() {
	// return R4R_PAGE_TITLE;
	// }

	public WebElement getPageH1Title() {
		return r4rResourceDetailH1PageTitle;
	}

	/*
	 * Get the Resources For researchers Home link
	 */
	public WebElement getResources4ResearchersHome() {
		return lnk_Resources4ResearchersHome;
	}

	/*
	 * Click on the Resources For researchers Home link
	 */
	public void clickResources4ResearchersHome() {
		lnk_Resources4ResearchersHome.click();
	}

	/*
	 * Get Back to Results link
	 */
	public WebElement getBacktoResult() {
		return lnk_BacktoSearchResults;
	}

	/*
	 * Click on Back to Results link
	 */
	public void clickBacktoResult() {
		lnk_BacktoSearchResults.click();
	}

	/*
	 * Click on Visit Resource button
	 */
	public WebElement getVisitResource() {
		return btn_VisitResource;
	}

	/*
	 * Click on Visit Resource button
	 */
	public void clickVisitResource() {
		btn_VisitResource.click();
	}

	/*
	 * Get Resource Access Image
	 */
	public WebElement getResourceAccessImage() {
		return resourceAccessImage;
	}

	/*
	 * Get Resource Access Text
	 */
	public WebElement getResourceAccessText() {
		return resourceAccessText;
	}

	/*
	 * Get Contact Info Title
	 */
	public WebElement getContactInfoTitle() {
		return contactInfoTitle;
	}

	/*
	 * Get Contact Info Detail
	 */
	public WebElement getContactInfoDetail() {
		return contactInfoDetail;
	}

	/*
	 * Get NCI Affiliation Title
	 */
	public WebElement getNCIAffliationTitle() {
		return nciAffiliationTitle;
	}

	/*
	 * Get NCI Affiliation Title
	 */
	public WebElement getNCIAffliationDetail() {
		return nciAffiliationDetail;
	}

	/*
	 * Get Element Search Box
	 */
	public WebElement getSearchBox() {
		return box_Search;
	}

	/*
	 * Get placeholder text of the Search Box
	 */
	public String getSearchBoxInnerText() {
		return box_Search.getAttribute("placeholder");
	}

	/*
	 * Get Element Search Button
	 */
	public WebElement getSearchButton() {
		return btn_Search;
	}

	/*
	 * Search Resources for Researchers based on any keyword
	 */
	public void search(String keyword) {
		getSearchBox().click();
		getSearchBox().sendKeys(keyword);
		getSearchButton().click();
	}

	/*
	 * Search Resources for Researchers based on any keyword
	 */
	public List<WebElement> getRelatedResources() {
		return relatedResourcesItems;
	}
}
