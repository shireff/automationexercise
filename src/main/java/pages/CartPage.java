package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.List;

public class CartPage {
    private Driver driver;

    private By cartItems = By.cssSelector(".cart_product");
    private By cartTotalPrice = By.cssSelector(".cart_total_price");
    private By checkoutButton = By.cssSelector(".btn.btn-default.check_out");
    private By removeItemButtons = By.cssSelector(".cart_quantity_delete");

    public CartPage(Driver driver) {
        this.driver = driver;
    }

    /************************** Actions **********************/

    public CartPage removeItemFromCart(int itemIndex) {
        List<WebElement> removeButtons = driver.get().findElements(removeItemButtons);
        if (itemIndex >= 0 && itemIndex < removeButtons.size()) {
            removeButtons.get(itemIndex).click();
        } else {
            throw new IndexOutOfBoundsException("Item index is out of bounds: " + itemIndex);
        }
        return this;
    }

    public CheckoutPage proceedToCheckout() {
        driver.element().click(checkoutButton);
        return new CheckoutPage(driver);
    }


    /************************** Assertions **********************/

    public CartPage verifyCartNotEmpty() {
        List<WebElement> items = driver.get().findElements(cartItems);
        Assert.assertTrue(items.size() > 0, "Cart is empty");
        return this;
    }

    public CartPage verifyCartTotalPrice(String expectedTotalPrice) {
        String actualTotalPrice = driver.element().getTextOf(cartTotalPrice) ;
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice, "Cart total price does not match the expected value.");
        return this;
    }

}
