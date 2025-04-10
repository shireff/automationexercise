package automationexercise;

import driverFactory.Driver;
import org.testng.annotations.*;
import pages.*;

public class ProductsPageTest {
    //   private Driver driver;
    private HomePage homePage;
    LoginPage login;
    private ProductsPage productsPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    String email = "Shireff@123";
    String password = "Shireff@123";

    public ThreadLocal<Driver> driver;

    @BeforeClass
    @Parameters(value = {"browserName"})
    public void setUp(@Optional("chrome") String browserName) {
        driver = new ThreadLocal<>();
        driver.set(new Driver());
        driver.get().browser().navigateToURL("https://www.automationexercise.com/");
        homePage = new HomePage(driver.get());
        productsPage = new ProductsPage(driver.get());
        checkoutPage = new CheckoutPage(driver.get());
        login = new LoginPage(driver.get());

    }

    @Test(priority = 1)
    public void navigateToProductsPage() {
        homePage.clickProductsLink().checkUserNavifatedToProductsPage();
    }

    @Test(priority = 2, dependsOnMethods = "navigateToProductsPage")
    public void verifyProductsVisible() {
        productsPage.verifyProductsVisible().verifyCategoriesVisible().verifyBrandsVisible();
    }

    @Test(priority = 3, dependsOnMethods = "navigateToProductsPage")
    public void searchProduct() {
        productsPage.searchProduct("Men Tshirt").verifySearchResults("Men Tshirt");
    }

    @Test(priority = 4, dependsOnMethods = "navigateToProductsPage")
    public void viewProductDetails() {
        productDetailsPage = productsPage.viewProductAtIndex(0);
        productDetailsPage.verifyProductDetailsDisplayed();
    }

    @Test(priority = 5, dependsOnMethods = "viewProductDetails")
    public void verifyProductNameAndPrice() {
        productDetailsPage.verifyProductName("Men Tshirt")
                .verifyProductPrice("Rs. 400");
    }

    @Test(priority = 6, dependsOnMethods = "viewProductDetails")
    public void addProductToCartFromDetailsPage() {
        productDetailsPage.setProductQuantity(2).addProductToCart();
    }

    @Test(priority = 7, dependsOnMethods = "navigateToProductsPage")
    public void selectCategory() {
        productsPage.selectCategory("Women", "Dress");
    }

    @Test(priority = 8, dependsOnMethods = "navigateToProductsPage")
    public void selectBrand() {
        productsPage.selectBrand("Polo");
    }

    @Test(priority = 9, dependsOnMethods = "addProductToCartFromDetailsPage")
    public void navigateToCart() {
        cartPage = productsPage.navigateToCart().verifyCartNotEmpty();
    }

    @Test(priority = 10, dependsOnMethods = "navigateToCart")
    public void verifyCartTotalPrice() {
        cartPage.verifyCartTotalPrice("Rs. 800");
    }

//    @Test(priority = 11, dependsOnMethods = "navigateToCart")
//    public void removeItemFromCart() {
//        cartPage.removeItemFromCart(0).verifyCartNotEmpty();
//    }

    @Test(priority = 12, dependsOnMethods = "navigateToCart")
    public void proceedToCheckout() {
        cartPage.proceedToCheckout();
        homePage.clickOnLogin().checkTitle().fillLoginFields(email, password).clickLoginBtn().clickCartLink().proceedToCheckout()
                .placeOrder()
                .fillPaymentInformation("sheriff", "1234567812345678", "311", "12", "2025")
                .clickSubmitBtn()
                .verifyOrderConfirmation()
                .continueShopping();
    }


    @AfterClass
    public void tearDown() {
        driver.get().quit();
    }
}