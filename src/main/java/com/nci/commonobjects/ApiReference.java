package com.nci.commonobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ApiReference {

	public static final String API_REFERENCE_H3 = "The Clinical Trials API: Use our data to power your own clinical trial search";

	WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//*[@id='ui-id-4']")
	WebElement apiReferenceH3;
	@FindBy(how = How.XPATH, using = ".//div[@class='api-reference-content']")
	WebElement apiReferenceContent;
	@FindBy(how = How.XPATH, using = ".//a[@href='/syndication/api']")
	WebElement apiReferenceLink;
	@FindBy(how = How.XPATH, using = ".//input[@class='submit button']")
	WebElement btn_Search;

	public ApiReference(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void getApiReference() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn_Search);
		Assert.assertTrue(apiReferenceH3.isDisplayed());
		Assert.assertEquals(apiReferenceH3.getText(), API_REFERENCE_H3);
		Assert.assertTrue(apiReferenceContent.isDisplayed());

	}

	public void verifyApiReferenceLink() {
		apiReferenceLink.click();
		Assert.assertTrue(driver.getCurrentUrl().contains("/syndication/api"));
		driver.navigate().back();
	}

}