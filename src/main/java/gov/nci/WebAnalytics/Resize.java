package gov.nci.WebAnalytics;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Resize extends AnalyticsBase {
	
	public static Dimension small = new Dimension(300, 800);
	public static Dimension med = new Dimension(700, 800);
	public static Dimension large = new Dimension(1100, 800);
	public static Dimension xlarge = new Dimension(1600, 800);	
	
	public Resize() {		
	}
	
	public Resize(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("Resize PageFactory initialized");
	}	
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */	
	/*** Resize browser ***/
	public void resizeToSmall() throws RuntimeException {
		driver.manage().window().setSize(small);
	}

	public void resizeToMed() throws RuntimeException {
		driver.manage().window().setSize(med);
	}

	public void resizeToLarge() throws RuntimeException {
		driver.manage().window().setSize(large);		
	}

	public void resizeToXlarge() throws RuntimeException {
		driver.manage().window().setSize(xlarge);
	}

	public void resizeBrowser() throws RuntimeException {
		System.out.println("Begin Resize actions");		
		driver.navigate().to(homePage);
		resizeToXlarge();
		resizeToLarge();
		resizeToMed();
		resizeToSmall();
		driver.manage().window().maximize();
		System.out.println("Done Resize actions");
	}
	
}
