package automationexercise;

import driverFactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.RegistrationSuccessPage;

public class TestClass {
    HomePage homePage;
    LoginPage login;
    RegistrationPage register;
    RegistrationSuccessPage registrationSuccessPage;
    private String email;
    private String name;

    String password = "password123";


    public ThreadLocal<Driver> driver;

    @BeforeClass

    public void setUp() {
        driver = new ThreadLocal<>();
        driver.set(new Driver());
        homePage = new HomePage(driver.get());
        login = new LoginPage(driver.get());
        register = new RegistrationPage(driver.get());
        registrationSuccessPage = new RegistrationSuccessPage(driver.get());

        email = "test" + Math.random() + "@gmail.com";
        name = "test" + Math.random();

    }


    @Test(priority = 1)
    public void demoRegistrationForm() {
        homePage.clickOnLogin()
                .checkTitle().fillSignUpField(name, email)
                .clickOnSignUpBtn()
                .checkPageTitle()
                .fillRegistrationForm("Mr", password,
                        "1", "January", "1990", "Shireff", "Nady", "123 Main St", "New York",
                        "New York", "10001", "1234567890", "United States")
                .clickCreateAccount()
                .verifyAccountCreationSuccess()
                .checkThatSuccessMessageShouldBeDisplayed();

    }

//    @Test(priority = 2, dependsOnMethods = "demoRegistrationForm")
//    public void demoLogout() {
//        homePage.checkUserNavifatedToHomePage().clickOnContinue().checkLogoutLink().checkDeleteAccount().clickLogoutLink().checkTitle();
//    }
//
//    @Test(priority = 3, dependsOnMethods = "demoRegistrationForm")
//    public void demoUserLogin() {
//        login.checkTitle().fillLoginFields(email, password).clickLoginBtn().checkUserNavifatedToHomePage().checkLogoutLink().checkDeleteAccount();
//    }
//
//    @Test(priority = 4, dependsOnMethods = "demoUserLogin")
//    public void demoUserDeleteAccount() {
//        homePage.clickDeleteAccountLink().verifyAccountDeletedMessage().clickContinueButton().checkUserNavifatedToHomePage();
//    }

//    @AfterMethod
//    public void takeScreenshot(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//
//            ScreenshotManager.takeScreenshot(driver.get(), result.getName());
//
//        }
//    }

    @AfterClass
    public void tearDown() {
        driver.get().quit();
    }
}
