package com.nci.commonobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BreadCrumb {
	WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//div[@id='cgvSlBreadcrumb']")
	WebElement breadCrumb;

	public BreadCrumb(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getBreadCrumb() {
		// Assert.assertTrue(breadCrumb.isDisplayed());
		String Breadcrumb = breadCrumb.getText();
		// Assert.assertEquals(breadCrumb.getText(), crumb);
		return Breadcrumb;
	}

}
