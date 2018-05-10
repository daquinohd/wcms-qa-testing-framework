package com.nci.Utilities;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.client.ClientUtil;

public class BrowserManager {

	static WebDriver driver;
	
	/**
	 * @param config
	 * @param driver
	 * @return String driverPath
	 */
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

	/**
	 * Create a new web driver for given browser and set that browser's options
	 * @param browserName name of the browser
	 * @param url URL to open
	 * @return WebDriver driver
	 * TODO: set up drivers for IE/Edge and Safari?
	 */
	public static WebDriver startBrowser(String browserName, String url){
		
		ConfigReader config = new ConfigReader();
		ChromeOptions chromeOptions = new ChromeOptions();
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		
		/* Chrome browser & settings */
		if(browserName.equalsIgnoreCase("Chrome")){
			System.out.println("Chrome browser");
			String driverFullPath = getDriverPath(config, "ChromeDriver");
			System.setProperty("webdriver.chrome.driver", driverFullPath);
			System.out.println("Chrome Driver Path: " + driverFullPath);
			
			driver = new ChromeDriver(chromeOptions);
			driver.manage().window().maximize();
			driver.get(url);
		}
		
		/* Firefox browser & settings */
		if(browserName.equalsIgnoreCase("Firefox")){
			System.out.println("Firefox browser");
			String driverFullPath = getDriverPath(config, "FirefoxDriver");
			System.setProperty("webdriver.gecko.driver", driverFullPath);
			System.out.println("Firefox Driver Path: " + driverFullPath);
			
			driver = new FirefoxDriver(firefoxOptions);
			driver.manage().window().maximize();
			driver.get(url);
		}
		
		/* Headless Chrome */
		if(browserName.equalsIgnoreCase("ChromeHeadless")){
			System.out.println("chrome headless");			
			String driverFullPath = getDriverPath(config, "ChromeDriver");
			System.setProperty("webdriver.chrome.driver", driverFullPath);
			System.out.println("Chrome Driver Path: " + driverFullPath);
			
			chromeOptions.addArguments("headless");
			driver = new ChromeDriver(chromeOptions);
			driver.manage().window().maximize();
			driver.get(url);
		}
	
		/* Headless Firefox */
		if(browserName.equalsIgnoreCase("FirefoxHeadless")){
			System.out.println("Firefox headless");
			String driverFullPath = getDriverPath(config, "FirefoxDriver"); 
			System.setProperty("webdriver.gecko.driver", driverFullPath);
			System.out.println("Firefox Driver Path: " + driverFullPath);
			
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless"); 
			firefoxOptions.setBinary(firefoxBinary); 
			driver = new FirefoxDriver(firefoxOptions); 						
			driver.manage().window().maximize();
			driver.get(url);
		}

		return driver;
	}
	

	/**
	 * Create a proxy web driver for a given browser and URL.<br/>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @param browserName name of the browser
	 * @param url URL to open
	 * @return WebDriver driver
	 * TODO: create headless drivers
	 * TODO: create Firefox/IE drivers
	 */
	public static WebDriver startProxyBrowser(String browserName, String url, BrowserMobProxy myProxy) {
		
		ConfigReader config = new ConfigReader();
		
	    // Get the Selenium proxy object and configure it as a desired capability
		System.out.println("=== Starting Driver ===");		
	    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(myProxy);
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
		
		// Chrome browser & settings (this is the only browser supported for now)
		if(browserName.equalsIgnoreCase("Chrome")) {
			System.out.println("Chrome browser");
			String driverFullPath = getDriverPath(config, "ChromeDriver");
			System.setProperty("webdriver.chrome.driver", driverFullPath);
			System.out.println("Chrome Driver Path: " + driverFullPath);
			
			driver = new ChromeDriver(capabilities);
			driver.manage().window().maximize();
			driver.get(url); // open proxy page
		}
		else {
			throw new IllegalArgumentException("Invalid browser type; check configuration settings.");
		}
		
		return driver;
	}
	
}
