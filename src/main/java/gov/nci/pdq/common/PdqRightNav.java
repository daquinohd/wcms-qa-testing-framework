package gov.nci.pdq.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.ElementChange;
import gov.nci.framework.PageObjectBase;

public class PdqRightNav extends PageObjectBase {

	private WebDriver driver;

	public PdqRightNav(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Verify the Cards exist on page and are visible.
	 * 
	 * @param selector
	 * @return
	 */
	public boolean IsVisible(String selector) {
		try {
			getPdqRightNav();
			return true;
		} catch (NoSuchElementException ex) {
			System.out.println(this.getClass().getSimpleName() + " element not found.");
			return false;
		}
	}

	/**
	 * Get a RightNav web element.
	 * 
	 * @return right nav Webelement
	 */
	private WebElement getPdqRightNav() {
		WebElement pdqRightNav = driver.findElement(By.id("pdq-toptoc"));
		return pdqRightNav;
	}

	/**
	 * Return a section element based on the contained text with Xpath.
	 * 
	 * @param text to find
	 * @return
	 */
	private WebElement getSection(String text) {
		WebElement section = driver.findElement(By.xpath("//*[contains(text(), '" + text + "')]"));
		return section;
	}

	/**
	 * Click on a given section baed on text value.
	 * 
	 * @param text
	 */
	public void clickSection(String text) {
		WebElement section = getSection(text);
		ScrollUtil.scrollIntoview(driver, section);
		section.click();
	}

	/**
	 * Click on a given section baed on text value.
	 * 
	 * @param text
	 */
	public void clickSectionNoNav(String text) {
		WebElement section = getSection(text);
		ScrollUtil.scrollIntoview(driver, section);
		
		ElementChange.removeHref(driver, ".pdq-toptoc a");
		
		section.click();
	}
	
}
