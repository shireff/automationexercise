package elementactions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementActions {

    private static final Logger logger = LoggerFactory.getLogger(ElementActions.class); // Create logger instance
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";
    private static final String BOLD = "\u001B[1m";


    private WebDriver driver;
    private JavascriptExecutor js;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) this.driver;
    }

    public ElementActions click(By locator) {
        logger.info(BLUE + "🖱️ Click on: " + locator.toString() + RESET);
        try {
            isClickable(locator);
            driver.findElement(locator).click();
        } catch (ElementClickInterceptedException | NoSuchElementException | StaleElementReferenceException |
                 TimeoutException exception) {
            logger.error(RED + BOLD + "❌ ERROR From click method: " + exception.getMessage() + RESET);
            scrollToElement(locator);
            clickUsingJs(locator);
        }
        return this;
    }

    public ElementActions type(By locator, String text) {
        clearField(locator);
        logger.info(GREEN + "⌨️ Fill field: " + locator.toString() + " with: " + text + RESET);
        driver.findElement(locator).sendKeys(text);
        return this;
    }

    public ElementActions clearField(By locator) {
        logger.info(YELLOW + "❌ Clear field with locator: " + locator.toString() + RESET);
        driver.findElement(locator).clear();
        return this;
    }

    public ElementActions scrollToElement(By locator) {
        logger.info(CYAN + "🔽 Scrolling to element: " + locator.toString() + RESET);
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        return this;
    }

    public ElementActions selectByIndex(By locator, int index) {
        logger.info(BLUE + "🔘 Selecting Option " + index + " from dropdown: " + locator.toString() + RESET);
        new Select(driver.findElement(locator)).selectByIndex(index);
        return this;
    }

    public ElementActions selectByValue(By locator, String value) {
        logger.info(BLUE + "🔘 Selecting Value: " + value + " from dropdown: " + locator.toString() + RESET);
        new Select(driver.findElement(locator)).selectByValue(value);
        return this;
    }

    public ElementActions selectByVisibleText(By locator, String value) {
        logger.info(BLUE + "🔘 Selecting Value: " + value + " from dropdown: " + locator.toString() + RESET);
        new Select(driver.findElement(locator)).selectByVisibleText(value);
        return this;
    }

    public String getTextOf(By locator) {
        logger.info(GREEN + "📝 Getting text from locator: " + locator.toString() + RESET);
        return driver.findElement(locator).getText();
    }

    public Boolean isDisplayed(By locator) {
        logger.info(GREEN + "👀 Checking " + locator.toString().split(":", 2)[1] + " if Displayed" + RESET);
        return driver.findElement(locator).isDisplayed();
    }

    public Boolean isSelected(By locator) {
        logger.info(GREEN + "✔️ Checking " + locator.toString().split(":", 2)[1] + " if Selected" + RESET);
        return driver.findElement(locator).isSelected();
    }

    public Boolean isClickable(By locator) {
        logger.info(GREEN + "👆 Checking " + locator.toString().split(":", 2)[1] + " if Clickable" + RESET);
        return driver.findElement(locator).isEnabled();
    }

    public ElementActions clickUsingJs(By locator) {
        logger.info(BLUE + "🖱️ Click on: " + locator.toString() + RESET);
        try {
            isClickable(locator);
            js.executeScript("arguments[0].click();", driver.findElement(locator));
        } catch (ElementClickInterceptedException | NoSuchElementException | StaleElementReferenceException |
                 TimeoutException exception) {
            logger.error(RED + BOLD + "❌ ERROR From click method: " + exception.getMessage() + RESET);
            scrollToElement(locator);
            js.executeScript("arguments[0].click();", driver.findElement(locator));
        }
        return this;
    }
}
