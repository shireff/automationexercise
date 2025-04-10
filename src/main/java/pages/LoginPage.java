package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import io.qameta.allure.Step;

public class LoginPage {
    private Driver driver;
    By signUpFromHeader = By.xpath("//div[@class=\"signup-form\"]/h2");
    String testAssert = "New User Signup!";

    By nameField = By.xpath("//input[@data-qa=\"signup-name\"]");
    By emailField = By.xpath("//input[@data-qa=\"signup-email\"]");
    By signUpButton = By.xpath("//button[@data-qa=\"signup-button\"]");


    By loginHeader = By.xpath("//div[@class='login-form']/h2");
    By loginEmailField = By.xpath("//input[@data-qa='login-email']");
    By loginPasswordField = By.xpath("//input[@data-qa='login-password']");
    By loginButton = By.xpath("//button[@data-qa='login-button']");
    By incorrectLogin = By.xpath("//form[@action=\"/login\"]/p");
    By existEmailLogin = By.xpath("//form[@action=\"/signup\"]/p");
    String incorrectLoginText = "Your email or password is incorrect!";
    String existEmailLoginText = "Email Address already exist!";


    public LoginPage(Driver driver) {
        this.driver = driver;
    }

    @Step("Check that the signup page title")
    public LoginPage checkTitle() {
        Assert.assertEquals(driver.element().getTextOf(signUpFromHeader), testAssert);
        return this;
    }

    @Step("Fill signup fields with name: {name} and email: {email}")
    public LoginPage fillSignUpField(String name, String email) {
        driver.element().type(nameField, name);
        driver.element().type(emailField, email);
        return this;
    }

    @Step("Fill login fields with email: {email} and password: {password}")
    public LoginPage fillLoginFields(String email, String password) {
        driver.element().type(loginEmailField, email);
        driver.element().type(loginPasswordField, password);
        return this;
    }

    @Step("Click the login button to submit login form")
    public HomePage clickLoginBtn() {
        driver.element().click(loginButton);
        return new HomePage(driver);
    }

    @Step("Click the signup button to go to registration page")
    public RegistrationPage clickOnSignUpBtn() {
        driver.element().click(signUpButton);
        return new RegistrationPage(driver);
    }

    @Step("Verify that error message 'Email Address already exist!' is displayed")
    public LoginPage checkThatErrorExistEmail() {
        Assert.assertTrue(driver.element().isDisplayed(existEmailLogin));
        Assert.assertEquals(driver.element().getTextOf(existEmailLogin), existEmailLoginText);
        return this;
    }

    @Step("Verify that error message 'Your email or password is incorrect!' is displayed")
    public LoginPage checkThatErrorIncorrectPassword() {
        Assert.assertTrue(driver.element().isDisplayed(incorrectLogin));
        Assert.assertEquals(driver.element().getTextOf(incorrectLogin), incorrectLoginText);
        return this;
    }
}
