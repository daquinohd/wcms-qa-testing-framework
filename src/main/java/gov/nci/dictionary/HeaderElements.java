package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
// import org.openqa.selenium.remote.server.handler.GetAllWindowHandles;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;

public class HeaderElements extends PageObjectBase {

    public HeaderElements (WebDriver browser) throws MalformedURLException, UnsupportedEncodingException {
        super(browser);
        // pageOptionsControl = null;
        PageFactory.initElements(browser, this);
    }


    // Testing if the H1 title element exists on the page
    // --------------------------------------------------
    public boolean TitleVisible() {
        List<WebElement> elementExists = getTitle();

        if (elementExists.size() > 0) {
            WebElement title = elementExists.get(0);
            return title.isDisplayed();
        }
        return false;
    }

    // Testing if the anchor link for the glossary widget exists on the page
    // ---------------------------------------------------------------------
    public boolean LinkVisible() {
        List<WebElement> elementExists = getWidgetLink();

        if (elementExists.size() > 0) {
            WebElement title = elementExists.get(0);
            return title.isDisplayed();
        }
        return false;
    }

    // Find the anchor link for the dictionary widget and click the link
    // Ensure we're seeing the correct page
    // -----------------------------------------------------------------
    public boolean LinksToWidgetPage(WebDriver driver, String lang) {
        List<WebElement> followLink = getWidgetLink();
        String widgetTitle;

        if (followLink.size() > 0) {
            WebElement widgetLink = followLink.get(0);

            widgetLink.click();

            // System.out.println(driver.getWindowHandles());
            // String winHandleBefore = driver.getWindowHandle();

            // Note:  This is looking for the HTML title element, NOT for
            //        the H1 title element
            if (lang.equals("ES")){
                widgetTitle = "Widget del Diccionario del NCI - National Cancer Institute";
            } else {
                 widgetTitle = "NCI Dictionary Widget - National Cancer Institute";
            }

            if ( driver.getTitle().equals(widgetTitle) ) {
                System.out.println("    Widget " + lang + " Found");
                return true;
            }
            System.out.println("*** Error: Widget Page NOT Found");

            // for (String winHandle : driver.getWindowHandles()){
                // driver.switchTo().window(winHandle);
            // }
            // System.out.println(driver.getWindowHandles());
            // Perform action on new window
            // driver.close();

            // driver.switchTo().window(winHandleBefore);
        }
        return false;
    }

    public boolean RadioBtnVisible() {
        List<WebElement> radioExists = getRadioControl();

        if (radioExists.size() > 0) {
            WebElement radioButton = radioExists.get(0);
            return radioButton.isDisplayed();
        }
        return false;
    }

    public boolean RadioDefault() {
        List<WebElement> elementExists = getRadioDefault();

        if (elementExists.size() > 0) {
            WebElement pocButton = elementExists.get(0);
            return pocButton.isSelected();
        }
        return false;
    }

    public boolean ButtonVisible(String buttonSelector) {
        List<WebElement> elementExists = getButtonControl(buttonSelector);

        if (elementExists.size() > 0) {
            WebElement pocButton = elementExists.get(0);
            return pocButton.isDisplayed();
        }
        return false;
    }

    public boolean FieldVisible(String elementSelector) {
        List<WebElement> elementExists = getElementControl(elementSelector);

        if (elementExists.size() > 0) {
            WebElement element = elementExists.get(0);
            return element.isDisplayed();
        }
        return false;
    }

    private List<WebElement> getTitle() {
            List<WebElement> pageTitle = getBrowser().findElements(By.cssSelector("h1"));
            return pageTitle;
    }

    private List<WebElement> getWidgetLink() {
            List<WebElement> widgetLink = getBrowser().findElements(
                                           By.cssSelector("div.last-SI p > a"));
            return widgetLink;
    }

    private List<WebElement> getButtonControl(String buttonSelector) {
            List<WebElement> buttonControls = getBrowser().findElements(By.cssSelector("li." +
                                                                                        buttonSelector));
            return buttonControls;
    }

    private List<WebElement> getRadioControl() {
            List<WebElement> radioControls = getBrowser().findElements(By.cssSelector("div span.radio" ));
            return radioControls;
    }

    private List<WebElement> getRadioDefault() {
            List<WebElement> radioControls = getBrowser().findElements(By.cssSelector("#radioStarts" ));
            return radioControls;
    }

    private List<WebElement> getElementControl(String selector) {
            List<WebElement> elementControls = getBrowser().findElements(By.cssSelector(selector));
            return elementControls;
    }
}