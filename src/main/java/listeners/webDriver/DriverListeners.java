package listeners.webDriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.shoppy.com.utils.PropertiesManager.webConfig;

public class DriverListeners implements WebDriverListener {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(DriverListeners.class);

    public DriverListeners(WebDriver driver) {
        this.driver = driver;
    }

    /************************************* Browser Actions Listeners ******************************************/

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.info("🖥️ Getting to \"" + url + "\".");

    }

    @Override
    public void afterGetTitle(WebDriver driver, String result) {
        logger.info("📄 Current Page Title is: \"" + result + "\".");

    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        logger.info("🌐 Navigating to url \"" + url + "\".");

    }

    @Override
    public void afterBack(WebDriver.Navigation navigation) {
        logger.info("🔙 Navigating back.");

    }

    @Override
    public void afterForward(WebDriver.Navigation navigation) {
        logger.info("🔜 Navigating forward.");

    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        logger.info("🔄 Refreshing current page...");

    }

    @Override
    public void afterGetPageSource(WebDriver driver, String result) {
        logger.info("📜 Getting Page source: " + result);

    }

    @Override
    public void beforeDeleteCookie(WebDriver.Options options, Cookie cookie) {
        logger.info("🍪 Deleting Cookie: " + cookie + " ......");

    }

    @Override
    public void beforeDeleteAllCookies(WebDriver.Options options) {
        logger.info("🍪 Deleting All Cookies...");
    }

    /************************************* Element Actions Listeners ******************************************/

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        try {
            new FluentWait<>(driver).withTimeout(Duration.ofSeconds(
                            Long.parseLong(webConfig.getProperty("elementIdentificationTimeout"))))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException exception) {
            logger.error("⏳ Timeout: " + exception.getMessage() + " || " + exception.getCause().getMessage().substring(0, exception.getCause().getMessage().indexOf("\n")));
            throw exception;
        }


    }

    @Override
    public void afterClose(WebDriver driver) {
        logger.info("🚪 Successfully Closed Driver.");

    }

    @Override
    public void afterQuit(WebDriver driver) {
        logger.info("🚪 Successfully Quit Driver.");
    }

    @Override
    public void beforeClick(WebElement element) {
        logger.info("🔘 Wait for " + getElementName(element) + " to be clickable");


        try {
            new WebDriverWait(this.driver,
                    Duration.ofSeconds(Long.parseLong(webConfig.getProperty("actionTimeout"))))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (org.openqa.selenium.TimeoutException timeoutException) {
            logger.error("⏳ Timeout on click: " + timeoutException);
            throw timeoutException;
        }

        try {
            logger.info("🖱️ Click on " + getElementName(element) + ".");

        } catch (Exception throwable) {
            logger.info("🖱️ Click.");

        }
    }

    @Override
    public void beforeSubmit(WebElement element) {
        try {
            logger.info("📤 Submit " + getElementName(element) + ".");

        } catch (Exception throwable) {
            logger.info("📤 Submit.");
        }
    }


    @Override
    public void beforeClear(WebElement element) {
        logger.info("❌ Clear " + getElementName(element) + ".");

    }

    @Override
    public void afterGetAttribute(WebElement element, String name, String result) {
        try {
            logger.info("📝 Get Attribute \"" + name + "\" from " + getElementName(element) + ", value is \"" + result + "\".");

        } catch (Exception throwable) {
            logger.info("📝 Get Attribute \"" + name + "\", value is \"" + result + "\".");

        }
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        try {
            logger.info("📝 Get Text from " + getElementName(element) + ", text is \"" + result + "\".");

        } catch (Exception throwable) {
            logger.info("📝 Get Text, text is :\"" + result + "\".");

        }
    }


    // Alert
    @Override
    public void beforeSendKeys(Alert alert, String text) {
        logger.info("⌨️ Type \"" + text + "\" into Alert.");

    }

    private String getElementName(WebElement element) {
        String accessibleName = element.getAccessibleName();
        if ("".equals(accessibleName)) {
            return "element";
        } else {
            return "\"" + accessibleName + "\"";
        }
    }


}