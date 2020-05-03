package common;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;
    Logger log = LogManager.getLogger(LoginPage.class.getName());

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By loginField = By.id("Login");
    private By passwordField = By.id("Password");
    private By loginBtn = By.cssSelector("button.btn.btn-primary.btn-block");

    public void loginAs(String login, String password) {
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginField));
        driver.findElement(loginField).sendKeys(login);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();
        log.debug("Authorized as " + login);
    }

    public void loginAdmin() {
        String pw;
        if (driver.getCurrentUrl().contains("10.127.48.19")) {
            pw = "password123";
        } else {
            pw = "password";
        }
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginField));
        driver.findElement(loginField).sendKeys("3");
        driver.findElement(passwordField).sendKeys(pw);
        driver.findElement(loginBtn).click();

    }


}
