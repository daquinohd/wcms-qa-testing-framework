package gov.nci.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;

/*To read the Property file*/
public class ConfigReader {
	Properties properties;

	public ConfigReader() {
		try {
			File file = new File("./configuration/ConfigPROD.property");
			FileInputStream fis = new FileInputStream(file);
			properties = new Properties();
			properties.load(fis);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}

	}

	public String getPageURL(String pageURL) {
		return properties.getProperty(pageURL);
	}

	// Whey didn't you just go home? 
	// That's your home. 
	// Are you too good for your home? Answer me!
	public String goHome() {
		return getPageURL("HomePage");
	}

	public String getDriverPath(String driverPath) {
		return properties.getProperty(driverPath);
	}

	/**
	 * Gets the base driver path from the configuration properties file
	 * 
	 * @return The base path for the drivers
	 */
	public String getDriverBasePath() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return properties.getProperty("DriverPath_Win");
		} else if (SystemUtils.IS_OS_MAC_OSX) {
			return properties.getProperty("DriverPath_Mac");
		} else if (SystemUtils.IS_OS_LINUX) {
			return properties.getProperty("DriverPath_Linux");
		} else {
			throw new RuntimeException("Could not get base driver path, unknown OS");
		}
	}

	/**
	 * Gets the driver name (geckodriver) based on the selected driver
	 * (FirefoxDriver)
	 * 
	 * @param driver
	 * @return
	 */
	public String getDriverName(String driver) {
		return properties.getProperty("DriverName_" + driver);
	}

	public String getExtentReportPath() {
		return properties.getProperty("ExtentReports");
	}

	public String getScreenShotsPath() {
		return properties.getProperty("ScreenShots");
	}

	public String getProperty(String property) {
		return properties.getProperty(property);
	}

}
