package com.nci.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.nci.Utilities.ConfigReader;
import com.nci.Utilities.ScreenShot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	// private static Logger log=
	// LogManager.getLogger(BaseClass.class.getName());
	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver;
	public String pageURL;
	ConfigReader config = new ConfigReader();

	@BeforeTest(groups = { "Smoke", "current" })
	@Parameters
	public void beforeTest() {
		// log.info("Starting a new test");
		System.out.println(this.getClass().getSimpleName());
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);
		report = new ExtentReports(extentReportPath + config.getProperty("Environment") + "-" + fileName + ".html");
		System.out.println("Report Path: ");
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}

	@BeforeClass(groups = { "Smoke", "current" })
	public void beforeClass() {
		System.out.println("************BEFORE CLASS EXECUTED************** ");
		logger = report.startTest(this.getClass().getSimpleName());
	}

	// handles the CTS Chat popup and closes it for executing scripts
	@BeforeMethod(groups = { "Smoke", "current" })
	public void beforeMethod() {

		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			// To see if the pop up presents
			driver.findElement(By.xpath("//*[@id='ProactiveLiveHelpForCTSPrompt']"));
			Thread.sleep(500);
			driver.findElement(By.cssSelector("#ProactiveLiveHelpForCTSPrompt > a")).click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}

		((JavascriptExecutor) driver).executeScript("scroll(0, -100);");
	}

	@AfterMethod(groups = { "Smoke", "current" })
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);
			// logger.addScreenCapture("./test-output/ExtentReport/");
			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
			driver.get(pageURL);
		}

		if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
			driver.get(pageURL);
		}
	}

	@AfterClass(groups = { "Smoke", "current" })
	public void afterClass() {
		System.out.println("***********Quiting Driver*********");
		driver.quit();
		report.endTest(logger);
	}

	@AfterTest(groups = { "Smoke", "current" })
	public void afterTest() {
		report.flush();
		//report.close();
		//log.info("Test ends here");
	}

}
