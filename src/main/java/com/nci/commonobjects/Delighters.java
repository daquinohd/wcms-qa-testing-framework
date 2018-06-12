package com.nci.commonobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.nci.Utilities.ConfigReader;

public class Delighters {
	WebDriver driver;
	ConfigReader config = new ConfigReader();

	@FindBy(how = How.CSS, using = ".delighter.cts-livehelp")
	WebElement delighter_LiveHelp;
	@FindBy(how = How.CSS, using = ".delighter.cts-what")
	WebElement delighter_What;
	@FindBy(how = How.CSS, using = ".delighter.cts-which")
	WebElement delighter_Which;

	String resultPageUrl;

	public Delighters(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getDelighterLiveHelp() {

		return delighter_LiveHelp;

	}

	public WebElement getDelighterWhat() {

		return delighter_What;

	}

	public WebElement getDelighterWhich() {

		return delighter_Which;

	}

	public void verifyDelighterLiveHelp() {
		// Clicking the LiveHelp Delighter
		delighter_LiveHelp.click();
	}

	public void verifyDelighterWhat() {
		// Clicking What Delighter
		delighter_What.click();

	}

	public void verifyDelighterWhich() {
		// Clicking Which Delighter
		delighter_Which.click();

	}

}
/*
 * public void verifyDelighterLiveHelp() { String expectedPageUrl =
 * config.getProperty("DelighterLiveHelpURL"); // Clicking the LiveHelp
 * Delighter delighter_LiveHelp.click(); // Checking the end page URL
 * //resultPageUrl = driver.getCurrentUrl();
 * //Assert.assertTrue(resultPageUrl.equals(expectedPageUrl),
 * "Actual URL is not as expected " + resultPageUrl);
 * //driver.navigate().back(); }
 * 
 * public void verifyDelighterWhat() { String expectedPageUrl =
 * config.getProperty("DelighterWhatURL"); // Clicking What Delighter
 * delighter_What.click(); // Checking the end page URL resultPageUrl =
 * driver.getCurrentUrl();
 * Assert.assertTrue(resultPageUrl.equals(expectedPageUrl),
 * "Actual URL is not as expected " + resultPageUrl); driver.navigate().back();
 * }
 * 
 * public void verifyDelighterWhich() { String expectedPageUrl =
 * config.getProperty("DelighterWhichURL"); // Clicking Which Delighter
 * delighter_Which.click(); // Checking the end page URL resultPageUrl =
 * driver.getCurrentUrl();
 * Assert.assertTrue(resultPageUrl.equals(expectedPageUrl),
 * "Actual URL is not as expected " + resultPageUrl); driver.navigate().back();
 * }
 */
