package com.nci.clinicalTrial.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Analytics {

	WebDriver driver;

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.LINK_TEXT, using = "advanced search")
	WebElement lnk_AdvSearch;

	@FindBy(how = How.CSS, using = "#accessible-megamenu-1522852087022-1")
	WebElement megaMenuLink;

	// Constructor - Initializing the Page objects
	public Analytics(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}

	// Click mega menu
	public void clickMegaMenu() {
		megaMenuLink.click();
	}


}
