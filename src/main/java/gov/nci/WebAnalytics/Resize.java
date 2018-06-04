package gov.nci.WebAnalytics;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Resize extends AnalyticsBase {
	
	// Local driver object
	public WebDriver driver;
	
	// Constructor to initialize the page object
	public Resize(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("Resize PageFactory initialized");
	}
	
	/**
	* Browser view breakpoints
	*/
	public static Dimension small = new Dimension(300, 800);
	public static Dimension med = new Dimension(700, 800);
	public static Dimension large = new Dimension(1100, 800);
	public static Dimension xlarge = new Dimension(1600, 800);	
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */	
	public void toSmall() throws RuntimeException {
		driver.manage().window().setSize(small);
	}

	public void toMed() throws RuntimeException {
		driver.manage().window().setSize(med);
	}

	public void toLarge() throws RuntimeException {
		driver.manage().window().setSize(large);
	}

	public void toXlarge() throws RuntimeException {
		driver.manage().window().setSize(xlarge);
	}

	public void maximize() throws RuntimeException {
		driver.manage().window().maximize();
	}

	public void doAllResizes() throws RuntimeException {
		toXlarge();
		toLarge();
		toMed();
		toSmall();
		maximize();
	}
	
}
