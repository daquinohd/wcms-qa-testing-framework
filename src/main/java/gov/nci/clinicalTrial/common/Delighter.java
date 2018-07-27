package gov.nci.clinicalTrial.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Exposes funtionality related to a single delighter.
 */
public class Delighter {
    
    private WebElement theDelighter;

    /**
     * Constructor
     * 
     * @param delighter A WebElement instance referencing a single delighter.
     */
    public Delighter(WebDriver browser, By selector) {
        this.theDelighter = browser.findElement(selector);
    }

    /**
     * Reports whether the delighter is displayed. Wrapper for WebElement.isDisplayed().
     * 
     * @return True if the delighter is displayed, false otherwise.
     */
    public boolean isDisplayed() {
        return theDelighter.isDisplayed();
    }

    public String getLinkUrl() {
        WebElement link = theDelighter.findElement(By.cssSelector("a"));
        return link.getAttribute("href");
    }

}