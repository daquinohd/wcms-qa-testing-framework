package com.nci.clinicalTrial.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CancerGovBanner {

	WebDriver driver; 
	public CancerGovBanner(WebDriver driver) 
		{
			this.driver = driver;
			PageFactory.initElements(driver, this);
			System.out.println("PageFactory initiated");
		}
	
	@FindBy(how=How.XPATH, using=".//input[@id='q']") WebElement txt_CancerType;
}
