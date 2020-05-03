package common;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class CommonPage {

    WebDriver driver;
    WebDriverWait wait;

    Faker fake = new Faker(new Locale("ru"));

    public CommonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public CommonPage(WebDriver driver, int waitTime) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }

    public void click(By elementLocation) {
        waitVisibility(elementLocation);
        driver.findElement(elementLocation).click();
    }

    public void click(WebElement element) {
        waitVisibility(element);
        element.click();
    }

    public void writeText(By elementLocation, String text) {
        waitVisibility(elementLocation);
        driver.findElement(elementLocation).sendKeys(text);
    }

    public List<WebElement> getElementList(By elementLocator) {
        waitVisibilityMultipleElements(elementLocator);
        return driver.findElements(elementLocator);
    }

    public String readText(By elementLocation) {
        waitVisibility(elementLocation);
        return driver.findElement(elementLocation).getText();
    }

    public void scrollIntoViewBy(By elementLocator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(elementLocator));
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

    }

    public void scrollIntoViewBy(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }

    public void waitVisibility(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitVisibilityMultipleElements(By by) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public String getAttributeValue (By by, String attribute){
        waitVisibility(by);
        return driver.findElement(by).getAttribute(attribute);
    }
    public String getAttributeValue (WebElement element, String attribute){
        waitVisibility(element);
        return element.getAttribute(attribute);
    }

    public void clearField (By by){
        driver.findElement(by).clear();
    }
    public void clearField (WebElement element){
        element.clear();
    }

}
