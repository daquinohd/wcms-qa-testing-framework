package com.nci.Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.commons.lang3.SystemUtils;

public class BrowserFactory {

	static WebDriver driver;
	
	private static String getDriverPath(ConfigReader config, String driver ) {
		//Get the base path for the OS
		String driverPath = config.getDriverBasePath();
		
		//Get the driver name itself
		driverPath += "/" + config.getDriverName(driver);
		
		if (SystemUtils.IS_OS_WINDOWS) {
			driverPath += ".exe";
		}
		
		return driverPath;
	}

	public static WebDriver startBrowser(String browserName, String url){
		
		ConfigReader config = new ConfigReader();
		
		if(browserName.equalsIgnoreCase("Chrome")){
			System.out.println("");
			
			String driverFullPath = getDriverPath(config, "ChromeDriver");

			System.setProperty("webdriver.chrome.driver", driverFullPath);
			System.out.println("Chrome Driver Path: " + driverFullPath);
			driver=new ChromeDriver();
			
			driver.manage().window().maximize();
			driver.get(url);
		}
		
		if(browserName.equalsIgnoreCase("Firefox")){
			String driverFullPath = getDriverPath(config, "FirefoxDriver");
			
			System.setProperty("webdriver.gecko.driver", driverFullPath);
			System.out.println("Firefox Driver Path: " + driverFullPath);
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			driver.get(url);
		}
		/*Headless Chrome */
		if(browserName.equalsIgnoreCase("ChromeHeadless")){
			System.out.println("chrome headless");
			
			String driverFullPath = getDriverPath(config, "ChromeDriver");

			System.setProperty("webdriver.chrome.driver", driverFullPath);
			System.out.println("Chrome Driver Path: " + driverFullPath);
			ChromeOptions options=new ChromeOptions();
			options.addArguments("headless");
			driver=new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.get(url);
		}
	
		/*Headless Firefox*/
		if(browserName.equalsIgnoreCase("FirefoxHeadless")){
			System.out.println("Firefox headless");
			 FirefoxBinary firefoxBinary = new FirefoxBinary(); 
			 firefoxBinary.addCommandLineOptions("--headless"); 
			 String driverFullPath = getDriverPath(config, "FirefoxDriver"); 
			 System.setProperty("webdriver.gecko.driver", driverFullPath);
			System.out.println("Firefox Driver Path: " + driverFullPath);
			 FirefoxOptions firefoxOptions = new FirefoxOptions(); 
			 firefoxOptions.setBinary(firefoxBinary); 
			 driver = new FirefoxDriver(firefoxOptions);
			 						
			driver.manage().window().maximize();
			driver.get(url);
		}

		return driver;
	}
}
