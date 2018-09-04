package gov.nci.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for Element change events.
 */
public class ElementChange {

    static final int MAX_TRANSITION_DELAY = 15;

    /**
     * Pause for up to MAX_TRANSITION_DELAY seconds to allow the text of a page
     * element to change to a new value.
     * 
     * @param driver A WebDriver instance representing the browser, currently displaying
     * the page where the change will take place.
     * 
     * @param element The element to be watched.
     * 
     * @param text The desired text.
     */
    public static void WaitForText(WebDriver driver, WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, MAX_TRANSITION_DELAY);
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

}