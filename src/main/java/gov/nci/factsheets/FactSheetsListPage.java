package gov.nci.factsheets;



import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import gov.nci.framework.PageObjectBase;


public class FactSheetsListPage extends PageObjectBase {

	WebDriver driver;
	ExtentTest logger;



/*************** FactSheets Page WebElements *****************************/
	@FindBy(how = How.CSS, using = "div.resize-content>h1>span")
	   WebElement factsheetslist_PageTitle;
	@FindBy(how = How.CSS, using = "#cgvBody>div.slot-item.first-SI>p")
	    WebElement text_FSListIntroText;
	@FindBy(how = How.CSS, using = "head#header>title")
		WebElement factsheetslist_BrowserTitle;
	@FindBy(how = How.CSS, using = "ul.generic-bullet>li>a")
	List<WebElement> subjectListItems ;



// Initializing the Page Objects
	    public FactSheetsListPage(WebDriver driver, ExtentTest logger) throws MalformedURLException, UnsupportedEncodingException  {
			super (driver);
	    	this.driver = driver;
			this.logger = logger;
			PageFactory.initElements(driver, this);
		}

// Testing if the H1 header element exists on the page
// ---------------------------------------------------
       public WebElement getPageH1Title() {
           return factsheetslist_PageTitle;
		}


// Verifying the Description Text
// ---------------------------------------------------
        public WebElement getPageIntroText() {
			return text_FSListIntroText;

		}

}










