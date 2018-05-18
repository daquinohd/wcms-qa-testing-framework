package gov.nci.WebAnalytics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PageLoad extends AnalyticsLoad {

	public PageLoad () {		
	}
	
	public PageLoad(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("pageload() PageFactory initiated.");
	}
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	private void loadPageTypes() {
		// Home page
		driver.navigate().to("https://www.cancer.gov");		
		// Landing page
		driver.navigate().to("https://www.cancer.gov/research");		
		// CTHP Patient
		driver.navigate().to("https://www.cancer.gov/types/bladder");
		// CTHP HP
		driver.navigate().to("https://www.cancer.gov/types/breast/hp");
		// Appmodule
		driver.navigate().to("https://www.cancer.gov/publications/dictionaries/cancer-terms");		
		// Blog series
		driver.navigate().to("https://www.cancer.gov/news-events/cancer-currents-blog");		
	}

	public void goHomeAndBack() {
		// Home page
		driver.navigate().to("https://www.cancer.gov");
		driver.navigate().to("https://www.cancer.gov/about-nci");
		driver.navigate().back();
		driver.navigate().refresh();		
	}
	
	
	/// Click around pages
	public void doPageLoadActions() throws RuntimeException {
		loadPageTypes();
		goHomeAndBack();		
	}
	
}
