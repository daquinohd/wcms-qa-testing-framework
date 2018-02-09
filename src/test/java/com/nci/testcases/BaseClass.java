package com.nci.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.nci.Utilities.ConfigReader;
import com.nci.Utilities.ScreenShot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class BaseClass {

	private static Logger log= LogManager.getLogger(BaseClass.class.getName());
	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver;
	public String pageURL;
	ConfigReader config= new ConfigReader();
	
	@BeforeTest (groups={"Smoke"})
	@Parameters({"browser"})
	
	public void beforeTest(){
		log.info("Starting a new test");
		String fileName= new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);
		report= new ExtentReports(extentReportPath +config.getProperty("Environment")+"-"+fileName+".html");
		System.out.println("Report Path: ");
		
	}
	
	@AfterMethod (groups={"Smoke"})
	public void tearDown(ITestResult result){
		if (result.getStatus()== ITestResult.FAILURE)
			{
				String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
				String image = logger.addScreenCapture(screenshotPath);
				//logger.addScreenCapture("./test-output/ExtentReport/");
				logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
				driver.get(pageURL);
			} 
	
		if (result.getStatus()== ITestResult.SKIP)
			{
				logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
				driver.get(pageURL);
			} 
	}

	@AfterClass (groups={"Smoke"})
	public void afterClass(){
		driver.quit();
		report.endTest(logger);	
	}
	
	@AfterTest (groups={"Smoke"})
	public void afterTest(){
		report.flush();
		report.close();
		log.info("Test ends here");
	}


}
