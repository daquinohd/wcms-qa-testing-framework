package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import gov.nci.framework.PageObjectBase;

/**
 * This is the base class for all all Clinical Trial page objects. This class
 * implements the Decorator pattern (https://en.wikipedia.org/wiki/Decorator_pattern)
 * to separate behaviors (e.g. suppress vs. allowing the pro-active chat prompt) into
 * behavior-specific classes, separate from the page objects.
 */
public class ClinicalTrialPageObjectBase extends PageObjectBase {

    // Reference to the decorator object.
    private ClinicalTrialPageObjectBase decorator;

    public ClinicalTrialPageObjectBase(WebDriver browser, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException {
        super(browser);

        this.decorator = decorator;
    }

}