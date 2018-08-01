package gov.nci.clinicalTrial.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Exposes functionality related to a single checkbox
 */
public class Checkbox {

    private WebElement theCheckbox;

    public Checkbox(WebDriver browser, By selector) {
        this.theCheckbox = browser.findElement(selector);
    }

    public boolean IsVisible() {
        return theCheckbox.isDisplayed();
    }
}