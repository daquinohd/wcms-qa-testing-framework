package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ParsedURL;

public class BasicSearchResults {

	WebDriver driver;
	public BasicSearchResults(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}


	@FindBy(how=How.XPATH, using=".//div[@class='cts-checkbox checkbox']")
	List <WebElement> check_SearchTrials;

	@FindBy(how=How.XPATH, using=".//input[@type='submit']")
	List <WebElement> btn_PrintSelected;

	/**
	 * Gets a URL object representing the current page's URL.
	 * @return 
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	public ParsedURL getPageUrl() throws MalformedURLException, UnsupportedEncodingException {
		ParsedURL url = new ParsedURL(this.driver.getCurrentUrl());
		return url;
	}

	/**
	 * Returns the current page's title.
	 */
	public String getPageTitle() {
		return this.driver.getTitle().trim();
	}


	public void selectCheckBox(){

		if (!((WebElement)check_SearchTrials.get(1)).isSelected())
		{
			((WebElement)check_SearchTrials.get(1)).click();
		}

		System.out.println("Checkbox selected");
	}

	public void clickPrint(){

		((WebElement) btn_PrintSelected.get(0)).click();
	}
}
