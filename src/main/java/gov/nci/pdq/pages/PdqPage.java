package gov.nci.pdq.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;
import gov.nci.Utilities.ClickUtil;
import gov.nci.Utilities.ScrollUtil;
import gov.nci.commonobjects.OnThisPage;

public class PdqPage extends PageObjectBase {

	private WebDriver driver;
	private OnThisPage onThisPage;

	/**
	 * @param driver
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public PdqPage(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public OnThisPage getOnThisPage() {
		return onThisPage;
	}

	private WebElement getPatientHPToggle() {
		return driver.findElement(By.cssSelector(".pdq-hp-patient-toggle a"));
	}
	
	public void clickPatientHPToggle() {
		WebElement toggle = getPatientHPToggle();
		ScrollUtil.scrollIntoview(driver, toggle);
		expectUrlChange(() -> {
			toggle.click();
		});
	}

}
