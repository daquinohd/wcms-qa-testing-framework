package gov.nci.clinicalTrial.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ConfigReader;

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
