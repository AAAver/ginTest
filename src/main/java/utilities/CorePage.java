package utilities;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CorePage {

    public WebDriver driver;
    public WebDriverWait wait;

    public CorePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public static Faker fake = new Faker(new Locale("ru"));
    public static Random random = new Random();
    public static Logger log = LogManager.getLogger(CorePage.class.getName());

    //===== Все или почти все справочники =====//
    public static final By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li/div");


    public void click(By by) {
        waitVisibility(by);
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).click();
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    public void click(WebElement element) {
        waitVisibility(element);
        try {
            element.click();
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
            element.click();
        }
    }

    public void writeText(By elementLocation, String text) {
        waitVisibility(elementLocation);
        driver.findElement(elementLocation).sendKeys(text);
    }

    public List<WebElement> getElementList(By elementLocator) {
        waitVisibilityMultipleElements(elementLocator);
        return driver.findElements(elementLocator);
    }

    public String getText(By by) {
        waitVisibility(by);
        return driver.findElement(by).getText();
    }

    public String getText(WebElement element) {
        waitVisibility(element);
        return element.getText();
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
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception TimeoutException) {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
        }
    }

    public void waitVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));


    }

    public void waitVisibilityMultipleElements(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } catch (Exception TimeoutException) {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
        }
    }

    public String getAttribute(By by, String attribute) {
        waitVisibility(by);
        return driver.findElement(by).getAttribute(attribute);
    }

    public String getAttribute(WebElement element, String attribute) {
        waitVisibility(element);
        return element.getAttribute(attribute);
    }

    public void clearField(By by) {
        driver.findElement(by).clear();
    }

    public void clearField(@NotNull WebElement element) {
        element.clear();
    }

    public boolean isDisplayed(By by) {
        return driver.findElement(by).isDisplayed();
    }

    public WebElement castToWebElement(By by) {
        return driver.findElement(by);
    }

    public String getUrlTail() {
        String[] urlParts = driver.getCurrentUrl().split("/");
        String tail = urlParts[urlParts.length - 1];
        return tail;
    }

    public void setDate(By by, String date) {
        waitVisibility(by);
        driver.findElement(by).sendKeys(date);
        driver.findElement(by).sendKeys(Keys.chord(Keys.ENTER));
    }

    public String getActualValueFromDrop(By by) {

            String xpath = "";
            String[] p = by.toString().split(" ");
            for (int i = 1; i < p.length; i++) {
                if (i != p.length - 1) {
                    xpath += p[i] + " ";
                } else {
                    xpath += p[i];
                }
            }
            String correctedXPath = xpath + " //span[contains(@class, 'chosen')]";
            By correctedBy = By.xpath(correctedXPath);
            return getAttribute(correctedBy, "innerHTML");
    }

    public List<String> getActualValuesFromField(By by) {
        String xpath = "";
        String[] p = by.toString().split(" ");
        for (int i = 1; i < p.length; i++) {
            if (i != p.length - 1) {
                xpath += p[i] + " ";
            } else {
                xpath += p[i];
            }
        }
        String correctedXPath = xpath + "/ul/li/div";
        By correctedBy = By.xpath(correctedXPath);
        List<String> values = new ArrayList<>();
        for(WebElement i : getElementList(correctedBy)){
            values.add(i.getText());
        }
        return values;

    }
}
