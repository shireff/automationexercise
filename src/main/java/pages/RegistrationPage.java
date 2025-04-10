package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.testng.Assert;

public class RegistrationPage {
    Driver driver;

    By registrationHeader = By.xpath("(//h2/b)[1]");
    String registrationHeaderTitle = "ENTER ACCOUNT INFORMATION";

    By mrTitleRadio = By.xpath("//input[@id='id_gender1']");
    By mrsTitleRadio = By.xpath("//input[@id='id_gender2']");
    By passField = By.xpath("//input[@data-qa='password']");
    By dayDropdown = By.xpath("//select[@data-qa='days']");
    By monthDropdown = By.xpath("//select[@data-qa='months']");
    By yearDropdown = By.xpath("//select[@data-qa='years']");

    By newsletterCheckbox = By.xpath("//input[@id='newsletter']");
    By optinCheckbox = By.xpath("//input[@id='optin']");

    By firstNameField = By.xpath("//input[@data-qa='first_name']");
    By lastNameField = By.xpath("//input[@data-qa='last_name']");
    By addressField = By.xpath("//input[@data-qa='address']");
    By cityField = By.xpath("//input[@data-qa='city']");
    By stateField = By.xpath("//input[@data-qa='state']");
    By zipcodeField = By.xpath("//input[@data-qa='zipcode']");
    By mobileNumberField = By.xpath("//input[@data-qa='mobile_number']");
    By countryDropdown = By.xpath("//select[@data-qa='country']");

    By createAccountButton = By.xpath("//button[@data-qa='create-account']");

    public RegistrationPage(Driver driver) {
        this.driver = driver;
    }


    public RegistrationPage fillRegistrationForm(String title, String password,
                                                 String day, String month, String year, String firstName,
                                                 String lastName, String address, String city, String state,
                                                 String zipcode, String mobileNumber, String country) {
        if (title.equalsIgnoreCase("Mr")) {
            driver.element().click(mrTitleRadio);
        } else {
            driver.element().click(mrsTitleRadio);
        }
        driver.element().type(passField, password);

        driver.element().selectByVisibleText(dayDropdown, day);
        driver.element().selectByVisibleText(monthDropdown, month);
        driver.element().selectByVisibleText(yearDropdown, year);


        driver.element().click(newsletterCheckbox);
        driver.element().click(optinCheckbox);
        driver.element().type(firstNameField, firstName);
        driver.element().type(lastNameField, lastName);
        driver.element().type(addressField, address);
        driver.element().type(cityField, city);
        driver.element().type(stateField, state);
        driver.element().type(zipcodeField, zipcode);
        driver.element().type(mobileNumberField, mobileNumber);

        driver.element().selectByVisibleText(countryDropdown, country);
        return this;
    }


    public RegistrationPage clickCreateAccount() {
        driver.element().click(createAccountButton);
        return this;
    }


    public RegistrationPage checkPageTitle() {
        String actualHeaderTitle = driver.element().getTextOf(registrationHeader);
        Assert.assertEquals(actualHeaderTitle, registrationHeaderTitle, "Page title does not match.");
        return this;
    }

    public RegistrationSuccessPage verifyAccountCreationSuccess() {
        return new RegistrationSuccessPage(driver);
    }


}
