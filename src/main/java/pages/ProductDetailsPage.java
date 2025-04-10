package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProductDetailsPage {
    private Driver driver;
    private WebDriverWait wait;

    private By productName = By.cssSelector(".product-information h2");
    private By productCategory = By.cssSelector(".product-information p");
    private By productPrice = By.cssSelector(".product-information span span");
    private By quantityBox = By.xpath("//input[@id='quantity']");
    private By addToCartButton = By.xpath("//button[@class=\"btn btn-default cart\"]");
    private By continueShoppingButton = By.cssSelector(".btn.btn-success.close-modal");

    public ProductDetailsPage(Driver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
    }

    /************************** Assertions **********************/

    public ProductDetailsPage verifyProductDetailsDisplayed() {
        Assert.assertTrue(driver.element().isDisplayed(productName), "Product name is not visible");
        Assert.assertTrue(driver.element().isDisplayed(productCategory), "Product category is not visible");
        Assert.assertTrue(driver.element().isDisplayed(productPrice), "Product price is not visible");
        return this;
    }

    public ProductDetailsPage verifyProductName(String expectedName) {
        Assert.assertEquals(driver.element().getTextOf(productName), expectedName, "Product name does not match");
        return this;
    }

    public ProductDetailsPage verifyProductPrice(String expectedPrice) {
        Assert.assertEquals(driver.element().getTextOf(productPrice), expectedPrice, "Product price does not match");
        return this;
    }

    /************************** Actions **********************/

    public ProductDetailsPage setProductQuantity(int quantity) {
        driver.element().type(quantityBox, String.valueOf(quantity));
        return this;
    }

    public ProductDetailsPage addProductToCart() {
        driver.element().click(addToCartButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueShoppingButton));
        driver.element().click(continueShoppingButton);
        return this;
    }

}
