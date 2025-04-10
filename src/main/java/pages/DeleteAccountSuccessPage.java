package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteAccountSuccessPage {
    private Driver driver;

    private By successMessage = By.xpath("//h2[@data-qa=\"account-deleted\"]");
    private By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public DeleteAccountSuccessPage(Driver driver) {
        this.driver = driver;
    }

    public DeleteAccountSuccessPage verifyAccountDeletedMessage() {
        driver.get().findElement(successMessage).isDisplayed();
        return this;
    }

    public HomePage clickContinueButton() {
        driver.get().findElement(continueButton).click();
        return new HomePage(driver);
    }

}
