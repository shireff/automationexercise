package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private Driver driver;

    private By nameOnCardField = By.xpath("//input[@data-qa=\"name-on-card\"]");
    private By cardNumberField = By.xpath("//input[@data-qa=\"card-number\"]");
    private By cvcField = By.xpath("//input[@data-qa=\"cvc\"]"); // CVC
    private By expirationMonthField = By.xpath("//input[@data-qa=\"expiry-month\"]");
    private By expirationYearField = By.xpath("//input[@data-qa=\"expiry-year\"]");
    private By submitButton = By.xpath("//button[@id=\"submit\"]");
    private By placeOrder = By.xpath("//a[@href=\"/payment\"]");

    private By continueShoppingButton = By.xpath("//a[@data-qa=\"continue-button\"]");


    private By orderConfirmationMessage = By.xpath("//h2/b");

    public CheckoutPage(Driver driver) {
        this.driver = driver;
    }

    /************************** Actions **********************/


    public CheckoutPage placeOrder() {
        driver.element().click(placeOrder);
        return this;
    }

    public CheckoutPage fillPaymentInformation(String nameOnCard, String cardNumber, String cvc, String expMonth, String expYear) {
        driver.element().type(nameOnCardField, nameOnCard);
        driver.element().type(cardNumberField, cardNumber);
        driver.element().type(cvcField, cvc);
        driver.element().type(expirationMonthField, expMonth);
        driver.element().type(expirationYearField, expYear);
        return this;
    }


    public CheckoutPage clickSubmitBtn() {
        driver.element().click(submitButton);
        return this;
    }

    public HomePage continueShopping() {
        driver.element().click(continueShoppingButton);
        return new HomePage(driver);
    }


    /************************** Assertions **********************/

    public CheckoutPage verifyOrderConfirmation() {
        driver.element().isDisplayed(orderConfirmationMessage);
        return this;
    }
}
