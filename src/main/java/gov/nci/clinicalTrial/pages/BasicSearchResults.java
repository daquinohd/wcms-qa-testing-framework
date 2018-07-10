package gov.nci.clinicalTrial.pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BasicSearchResults {
	
	WebDriver driver; 
	public BasicSearchResults(WebDriver driver) 
		{
			this.driver = driver;
			PageFactory.initElements(driver, this);
			System.out.println("PageFactory initiated");
		}
	
	//@FindBy(how=How.XPATH, using=".//input[@id='NCI-2014-01340' and @type='checkbox']") WebElement check_SearchTrials;
	
	
	@FindBy(how=How.XPATH, using=".//div[@class='cts-checkbox checkbox']") List <WebElement> check_SearchTrials;
	@FindBy(how=How.XPATH, using=".//input[@type='submit']") List <WebElement> btn_PrintSelected;
	
	public void selectCheckBox(){
		
		if (!((WebElement)check_SearchTrials.get(1)).isSelected()) 
		{
			((WebElement)check_SearchTrials.get(1)).click();
		}
		
		System.out.println("Checkbox selected");
	}

	public void clickPrint(){
		
		((WebElement) btn_PrintSelected.get(0)).click();
	}
}
