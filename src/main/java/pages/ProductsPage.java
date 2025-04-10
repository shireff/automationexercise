package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class ProductsPage {
    private Driver driver;

    private By productsTitle = By.cssSelector(".title.text-center");
    private By searchBox = By.xpath("//input[@id=\"search_product\"]");
    private By searchButton = By.xpath("//button[@id=\"submit_search\"]");
    private By productItems = By.cssSelector(".features_items .product-image-wrapper");
    private By viewProductButtons = By.cssSelector(".choose a");
    private By addToCartButtons = By.cssSelector(".add-to-cart");
    private By categoryProducts = By.xpath("//div[@id=\"accordian\"]");
    private By brandProducts = By.cssSelector(".brands_products");
    private By cartLink = By.cssSelector(".nav.navbar-nav li:nth-child(3) a");

    public ProductsPage(Driver driver) {
        this.driver = driver;
    }

    /************************** Assertions **********************/

    public ProductsPage checkUserNavifatedToProductsPage() {
        Assert.assertTrue(driver.get().getCurrentUrl().contains("https://www.automationexercise.com/products"));
        Assert.assertTrue(driver.get().findElement(productsTitle).isDisplayed());
        Assert.assertEquals(driver.get().findElement(productsTitle).getText(), "ALL PRODUCTS");
        return this;
    }

    public ProductsPage verifyProductsVisible() {
        List<WebElement> products = driver.get().findElements(productItems);
        Assert.assertTrue(products.size() > 0, "Products are not visible on the page");
        return this;
    }

    public ProductsPage verifySearchResults(String searchTerm) {
        List<WebElement> products = driver.get().findElements(productItems);
        Assert.assertTrue(products.size() > 0, "No products found for the search term: " + searchTerm);
        return this;
    }

    public ProductsPage verifyCategoriesVisible() {
        Assert.assertTrue(driver.get().findElement(categoryProducts).isDisplayed(), "Categories are not visible");
        return this;
    }

    public ProductsPage verifyBrandsVisible() {
        Assert.assertTrue(driver.get().findElement(brandProducts).isDisplayed(), "Brands are not visible");
        return this;
    }

    /************************** Actions **********************/

    public ProductsPage searchProduct(String productName) {
        driver.get().findElement(searchBox).clear();
        driver.get().findElement(searchBox).sendKeys(productName);
        driver.get().findElement(searchButton).click();
        return this;
    }

    public ProductDetailsPage viewProductAtIndex(int index) {
        List<WebElement> viewButtons = driver.get().findElements(viewProductButtons);
        if (index >= 0 && index < viewButtons.size()) {
            driver.element().click(viewProductButtons);
            return new ProductDetailsPage(driver);
        } else {
            throw new IndexOutOfBoundsException("Product index is out of bounds: " + index);
        }
    }


    public ProductsPage selectCategory(String categoryName, String subCategoryName) {
        By categoryLocator = By.xpath("//a[@href='#" + categoryName + "']");
        driver.element().click(categoryLocator);
        By subCategoryLocator = By.xpath("//div[@id='" + categoryName + "']//a[contains(text(), '" + subCategoryName + "')]");
        driver.element().click(subCategoryLocator);
        return this;
    }
    public ProductsPage selectBrand(String brandName) {
        By brandLocator = By.xpath("//a[@href='/brand_products/" + brandName + "']");
        driver.element().click(brandLocator);
        return this;
    }


    public CartPage navigateToCart() {
        driver.element().click(cartLink);
        return new CartPage(driver);
    }

    public HomePage navigateToHomePage() {
        By homeLink = By.cssSelector(".nav.navbar-nav li:nth-child(1) a");
        driver.element().click(homeLink);
        return new HomePage(driver);
    }
}