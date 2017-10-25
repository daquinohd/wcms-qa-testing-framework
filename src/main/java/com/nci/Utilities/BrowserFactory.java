package com.nci.Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

	static WebDriver driver;
	
	public static WebDriver startBrowser(String browserName, String url){
		
		ConfigReader config = new ConfigReader();
		
		if(browserName.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", config.getDriverPath("ChromeDriver"));
			System.out.println("Chrome Driver Path: "+config.getDriverPath("ChromeDriver"));
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);
		}
		
		if(browserName.equalsIgnoreCase("Firefox")){
			System.setProperty("webdriver.gecko.driver", config.getDriverPath("FirefoxDriver"));
			System.out.println("Firefox Driver Path: "+config.getDriverPath("FirefoxDriver"));
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			driver.get(url);
		}
		
		return driver;
	}
}
