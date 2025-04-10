package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.testng.Assert;

public class HomePage {
    private Driver driver;
    By loginAndSignUpLink = By.xpath("//a[@href='/login']");
    By logoutLink = By.xpath("//a[@href='/logout']");
    By deleteAccount = By.xpath("//a[@href=\"/delete_account\"]");
    By loginLink = By.xpath("//a[@data-qa=\"continue-button\"]");
    By productLink = By.xpath("//a[@href=\"/products\"]");
    By cartLink = By.xpath("//a[@href=\"/view_cart\"]");

    public HomePage(Driver driver) {
        this.driver = driver;
    }

    /************************** Assertions *********************/
    public HomePage checkUserNavifatedToHomePage() {
        Assert.assertTrue(driver.browser().getCurrentURL().contains("https://www.automationexercise.com"));
        return this;
    }


    public HomePage checkLogoutLink() {
        Assert.assertTrue(driver.element().isDisplayed(logoutLink));
        return this;
    }

    public HomePage checkDeleteAccount() {
        Assert.assertTrue(driver.element().isDisplayed(deleteAccount));
        return this;
    }


    /************************** Actions *********************/
    public LoginPage clickOnLogin() {
        driver.element().click(loginAndSignUpLink);
        return new LoginPage(driver);
    }

    public HomePage clickOnContinue() {
        driver.element().click(loginLink);
        return this;
    }


    public LoginPage clickLogoutLink() {
        driver.element().click(logoutLink);
        return new LoginPage(driver);
    }

    public DeleteAccountSuccessPage clickDeleteAccountLink() {
        driver.element().click(deleteAccount);
        return new DeleteAccountSuccessPage(driver);
    }

    public ProductsPage clickProductsLink() {
        driver.element().click(productLink);
        return new ProductsPage(driver);
    }

    public CartPage clickCartLink() {
        driver.element().click(cartLink);
        return new CartPage(driver);
    }
}
