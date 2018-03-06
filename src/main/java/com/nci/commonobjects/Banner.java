package com.nci.commonobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class Banner {
	WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//img[@src='/publishedcontent/images/images/design-elements/logos/nci-logo-full.__v1.svg']")

	WebElement banner;

	public Banner(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void getBanner() {
		Assert.assertTrue(banner.isDisplayed());
		System.out.println("Banner: " + banner.getAttribute("alt"));
		Assert.assertEquals(banner.getAttribute("alt"), "National Cancer Institute");

	}

}
