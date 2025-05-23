package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class RegistrationSuccessPage {
    public Driver driver;
    private WebDriverWait wait;
    By successMessage = By.xpath("//h2 [@data-qa=\"account-created\"]");
    By continueButton = By.xpath("//a[@data-qa=\"continue-button\"]");

    public RegistrationSuccessPage(Driver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver.get(), Duration.ofSeconds(10));

    }

    public RegistrationSuccessPage checkThatSuccessMessageShouldBeDisplayed() {
       wait.until(ExpectedConditions.urlContains("/account_created"));
        wait.until(ExpectedConditions.visibilityOf(driver.get().findElement(successMessage)));
        Assert.assertTrue(driver.browser().getCurrentURL().contains("/account_created"));
        Assert.assertTrue(driver.element().isDisplayed(successMessage));
        Assert.assertEquals(driver.element().getTextOf(successMessage), "ACCOUNT CREATED!");
        return this;
    }

    public HomePage clickContinueButton() {
        driver.element().click(continueButton);
        return new HomePage(driver);
    }

}

