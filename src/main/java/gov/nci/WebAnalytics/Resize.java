package gov.nci.WebAnalytics;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Resize {
	
	// Local driver object
	public WebDriver driver;
	
	// Constructor to initialize the page object
	public Resize(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.print("Resize pagefactory initialized: ");		
	}
	
	/**
	* Browser view breakpoints
	*/
	public static Dimension small = new Dimension(600, 800);
	public static Dimension med = new Dimension(700, 800);
	public static Dimension large = new Dimension(1100, 800);
	public static Dimension xlarge = new Dimension(1450, 800);	
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */	
	public void toSmall() throws RuntimeException {
		System.out.println("Resize browser to mobile");
		driver.manage().window().setSize(xlarge);
		driver.manage().window().setSize(small);
	}

	public void toMed() throws RuntimeException {
		System.out.println("Resize browser to tablet");
		driver.manage().window().setSize(xlarge);
		driver.manage().window().setSize(med);
	}

	public void toLarge() throws RuntimeException {
		System.out.println("Resize browser to desktop/large");
		driver.manage().window().setSize(small);
		driver.manage().window().setSize(large);
	}

	public void toXlarge() throws RuntimeException {
		System.out.println("Resize browser to extra large");
		driver.manage().window().setSize(small);
		driver.manage().window().setSize(xlarge);
	}

	public void maximize() throws RuntimeException {
		System.out.println("Maximize browser window");		
		driver.manage().window().setSize(small);
		driver.manage().window().maximize();
	}

	public void doAllResizes() throws RuntimeException {
		System.out.println("Cycle through all browser size breakpoints");
		toXlarge();
		toLarge();
		toMed();
		toSmall();
		maximize();
	}
	
}
