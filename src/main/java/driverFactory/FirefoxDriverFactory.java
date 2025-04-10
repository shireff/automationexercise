package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static utils.PropertiesManager.webConfig;

public class FirefoxDriverFactory extends DriverAbstract {

    @Override
    public WebDriver StartDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false); // Stop notifications
        options.addPreference("permissions.default.desktop-notification", 2);
        options.addPreference("media.navigator.permission.disabled", true);
        options.addPreference("media.navigator.streams.fake", true);
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        if (webConfig.getProperty("HeadlessMode").equalsIgnoreCase("true")) {
            options.addArguments("--headless");
        }
        driver = new FirefoxDriver(options);
        return driver;
    }
}
