package automationexercise;

import driverFactory.Driver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.RegistrationSuccessPage;

public class LoginSignupTest {
    //  private WebDriver driver;
    private Driver WebDriver;
    HomePage homePage;
    LoginPage login;
    RegistrationPage register;
    RegistrationSuccessPage registrationSuccessPage;

    @BeforeMethod
    public void setUp() {
        WebDriver = new Driver("chrome");
        // driver = new ChromeDriver();
        WebDriver.get().navigate().to("https://www.automationexercise.com/");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        homePage = new HomePage(WebDriver);
        login = new LoginPage(WebDriver);
        register = new RegistrationPage(WebDriver);
    }

    @Test
    public void registerWithExistEmail() {
        homePage
                .clickOnLogin()
                .checkTitle()
                .fillSignUpField("test", "test@test.com")
                .clickOnSignUpBtn();
        login.checkThatErrorExistEmail();
    }

//    @Test
//    public void loginWithIncorrectPassword() {
//        homePage
//                .clickOnLogin()
//                .checkTitle()
//                .fillLoginFields("test@test.com", "IncorrectPassword")
//                .clickLoginBtn();
//        login.checkThatErrorIncorrectPassword();
//    }


    @AfterMethod
    public void tearDown() {
        WebDriver.quit();
    }
}
