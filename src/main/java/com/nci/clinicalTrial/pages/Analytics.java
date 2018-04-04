package com.nci.clinicalTrial.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Analytics {
	
	public static final String S_CODE_NAME = "Find NCI-Supported Clinical Trials";
	public static final String S_ACCOUNT = "s_account";
	public static final String NCI_FUNCTIONS_NAME = "Clinical Trials Search Results";

	WebDriver driver;

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.XPATH, using = "//*[contains(text(), 's_account')]")
	WebElement SAccount;
	
	// Constructor - Initializing the Page objects
	public Analytics(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Get inner text of element containing s_account
	public String getSAccountText() {
		return SAccount.getText();
	}
	
	// Click mega menu
	public void clickMegaMenu() {
		//megaMenuLink.click();
	}

}
