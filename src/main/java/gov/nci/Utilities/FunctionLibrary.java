package gov.nci.Utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionLibrary {

	public static void scrollIntoview(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);" + "window.scrollBy(0,-100);",
				element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void waitForElement(WebDriver driver, WebElement elementToBeLoaded) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(elementToBeLoaded));
	}

	public static boolean waitForTitle(WebDriver driver, String title) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.titleContains(title));
	}

	public static boolean waitElementToContainText(WebDriver driver, WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	public static boolean waitURLToBe(WebDriver driver, String url) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.urlToBe(url));
	}

}
