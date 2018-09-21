package gov.nci.pdq.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;
import gov.nci.pdq.common.PdqRightNav;
import gov.nci.commonobjects.OnThisPage;

public class PdqPage extends PageObjectBase {

	private WebDriver driver;
	private PdqRightNav rightNav;
	private OnThisPage onThisPage;

	/**
	 * @param driver
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public PdqPage(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		this.rightNav = new PdqRightNav(driver);
		PageFactory.initElements(driver, this);
	}

	public PdqRightNav getRightNav() {
		return rightNav;
	}

	public OnThisPage getOnThisPage() {
		return onThisPage;
	}

}
