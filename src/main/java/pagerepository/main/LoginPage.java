package pagerepository.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;


public class LoginPage extends CorePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    By loginField = By.id("Login");
    By passwordField = By.id("Password");
    By loginBtn = By.cssSelector("button.btn.btn-primary.btn-block");

    By validationError = By.xpath("//*[contains(@class, 'validation')]/ul/li");

    public void loginAs(String login, String password) {
        writeText(loginField, login);
        writeText(passwordField, password);
        click(loginBtn);
    }

    public void loginAs(String login) {
        writeText(loginField, login);
        writeText(passwordField, "password");
        click(loginBtn);
        if(isDisplayed(validationError)){
            writeText(passwordField, "password123");
            click(loginBtn);
        }
    }
}
