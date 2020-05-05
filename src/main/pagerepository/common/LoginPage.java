package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.CorePage;


public class LoginPage extends CorePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    By loginField = By.id("Login");
    By passwordField = By.id("Password");
    By loginBtn = By.cssSelector("button.btn.btn-primary.btn-block");

    public void loginAs(String login, String password) {
        writeText(loginField, login);
        writeText(passwordField, password);
        click(loginBtn);
    }
}
