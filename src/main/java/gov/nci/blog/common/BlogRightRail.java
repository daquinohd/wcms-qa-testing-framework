package gov.nci.blog.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ClickUtil;
import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.PageObjectBase;

public class BlogRightRail extends PageObjectBase {

	WebDriver driver;

	public BlogRightRail(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**************** Blog Right Rail Elements *****************************/
	@FindBy(id = "nvcgSlListBlogRTRail")
	WebElement div_rightRail;

	@FindBy(css = "#blog-archive-accordion")
	WebElement div_archiveAccordion;

	@FindBy(css = "h3#archive[aria-expanded='true']")	
	WebElement hdr_archive_expanded;
	
	@FindBy(css = "h3#archive[aria-expanded='false']")
	WebElement hdr_archive_collapsed;

	@FindBy(css = "#Featured\\+Posts + ul li a")
	List<WebElement> list_featured;

	@FindBy(css = "#Categories + ul li a")
	List<WebElement> list_categories;

	@FindBy(css = "li.month a")
	List<WebElement> list_months;

	public String getFeaturedItemText(int index) {
		return list_featured.get(index).getText();
	}

	public String getCategoryItemText(int index) {
		return list_categories.get(index).getText();
	}

	/**
	 * Get a 'Year' header WebElement.
	 * 
	 * @param year
	 * @return
	 */
	public WebElement getArchiveYear(String year) {
		WebElement element = driver.findElement(By.xpath("//h4[contains(text(), '" + year + "')]"));
		return element;
	}

	/**
	 * Get a 'Month' list WebElement.
	 * 
	 * @param month
	 * @return
	 */
	public WebElement getArchiveMonth(String month, String year) {
		String cssSelector = "year=" + year + "&month=" + month;
		WebElement element = driver.findElement(By.cssSelector("a[href*='" + cssSelector + "']"));
		return element;
	}

	/**************** Blog Post Page Actions *****************************/

	/**
	 * Click on featured item at given position.
	 */
	public void clickFeaturedItem(int index) {
		WebElement element = list_featured.get(index);
		ScrollUtil.scrollIntoview(driver, element);
		element.click();
	}

	/**
	 * Click on a category item at given position.
	 */
	public void clickCategoryItem(int index) {
		WebElement element = list_categories.get(index);
		ScrollUtil.scrollIntoview(driver, element);
		element.click();
	}

	/**
	 * Expand on archive header.
	 */
	public void expandArchiveHeader() {
		ClickUtil.forceClick(driver, hdr_archive_collapsed);
	}

	/**
	 * Collapse on archive header.
	 */
	public void collapseArchiveHeader() {
		ClickUtil.forceClick(driver, hdr_archive_expanded);
	}

	/**
	 * Click on an archive year header.
	 * 
	 * @param year
	 */
	public void clickArchiveYear(String year) {
		WebElement yearElement = getArchiveYear(year);
		ClickUtil.forceClick(driver, yearElement);
	}

	/**
	 * Click on an archive month link.
	 * 
	 * @param month
	 * @param year
	 */
	public void clickArchiveMonth(String month, String year) {
		WebElement monthElement = getArchiveMonth(month, year);
		ClickUtil.stall(driver);
		ClickUtil.forceClick(driver, monthElement);
	}

}