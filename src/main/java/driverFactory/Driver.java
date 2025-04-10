package driverFactory;

import browseractions.BrowserActions;
import elementactions.ElementActions;
import listeners.webDriver.DriverListeners;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import static utils.PropertiesManager.webConfig;

public class Driver {

    private ThreadLocal<WebDriver> driver;

    public Driver() {
        String browserType = webConfig.getProperty("BrowserType");
        WebDriver undecoratedDriver = getDriver(browserType).StartDriver();
        assert undecoratedDriver != null;


        driver = new ThreadLocal<>();
        driver.set(new EventFiringDecorator<>(WebDriver.class,
                new DriverListeners(undecoratedDriver)).decorate(undecoratedDriver));


        System.out.println("Start with driver: " + driver);
        driver.get().manage().window().maximize();
        String baseUrl = webConfig.getProperty("BaseURL");

        if (!baseUrl.isEmpty()) {
            driver.get().navigate().to(baseUrl);
        }

    }

    public Driver(String driverType) {
        WebDriver undecoratedDriver = getDriver(driverType).StartDriver();
        assert undecoratedDriver != null;


        driver = new ThreadLocal<>();
        driver.set(new EventFiringDecorator<>(WebDriver.class,
                new DriverListeners(undecoratedDriver)).decorate(undecoratedDriver));


        System.out.println("Start with driver: " + driver);
        driver.get().manage().window().maximize();
        String baseUrl = webConfig.getProperty("BaseURL");

        if (!baseUrl.isEmpty()) {
            driver.get().navigate().to(baseUrl);
        }

    }

    private DriverAbstract getDriver(String driver) {
        switch (driver) {
            case "chrome":
                return new ChromeDriverFactory();
            case "firefox":
                return new FirefoxDriverFactory();
            case "edge":
                return new EdgeDriverFactory();
            default:
                throw new IllegalStateException("Unexpected value: " + driver);
        }

    }

    public WebDriver get() {
        return driver.get();
    }

    public void quit() {
        driver.get().quit();
    }

    public ElementActions element() {
        return new ElementActions(driver.get());
    }

    public BrowserActions browser() {
        return new BrowserActions(driver.get());
    }

}
