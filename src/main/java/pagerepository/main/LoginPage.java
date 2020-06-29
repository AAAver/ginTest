package pagerepository.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;


public class LoginPage extends CorePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    By login = By.id("Login");
    By password = By.id("Password");
    By loginBtn = By.cssSelector("button.btn.btn-primary.btn-block");

    By currentPassword = By.xpath("//*[@id='CurrentPassword']");
    By newPasswordConfirmation = By.xpath("//*[@id='PasswordConfirm']");
    By confirmNewPasswordButton = By.xpath("//*[@type='submit']");

    By exit = By.xpath("//*[contains(@href, 'Logout')]");
    By validationError = By.xpath("//*[contains(@class, 'validation')]/ul/li");

    public void loginAs(String loginName, String password) {
        writeText(this.login, loginName);
        writeText(this.password, password);
        click(loginBtn);

    }

    public void loginAs(String loginName) {
        writeText(this.login, loginName);
        writeText(password, "password");
        clickJS(loginBtn);

        if(isDisplayed(currentPassword)){
            setNewPassword();
            writeText(password,"password123");
            clickJS(loginBtn);
        }

        if(isDisplayed(validationError)){
            writeText(password, "password123");
            clickJS(loginBtn);
        }

        if(driver.getCurrentUrl().contains("Login")){
            log.error("Login unsuccessful");
        } else {
            log.info("Login successful as: " +loginName);
        }

    }

    public void setNewPassword() {
        writeText((currentPassword), "password");
        writeText(password,"password123");
        writeText(newPasswordConfirmation,"password123");
        clickJS(confirmNewPasswordButton);
        log.info("Password changed for \"password123\"");
    }

    public void logout(){
        clickJS(exit);
    }
}
