package com.nci.Utilities;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
	ConfigReader config = new ConfigReader();

	public static String captureScreenshot(WebDriver driver, String screenShotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			String dest = "./test-output/ScreenShotFailure/" + screenShotName + ".png";

			File destination = new File(dest);

			FileUtils.copyFile(source, destination);

			return dest;

		} catch (Exception e) {
			System.out.println("Exception while taking a screen shot " + e.getMessage());

			return e.getMessage();
		}
	}

}

// String dest =
// "C:\\nci_workspace\\nci\\test-output\\Screenshots\\" +
// screenShotName + ".png";