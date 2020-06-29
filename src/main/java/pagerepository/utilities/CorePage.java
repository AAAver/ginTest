package pagerepository.utilities;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.security.Key;
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
    public static Logger log = LogManager.getLogger("ginLogger");

    //===== Все или почти все справочники =====//
    public static final By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li/div");
    public static final By select2dropDiv = By.xpath("//div[@id='select2-drop']");
    public static final By select2dropMask = By.xpath("//div[@id='select2-drop-mask']");
    By toMainPage = By.xpath("//a[text()='Главная']");
    By instrumentalPanel = By.xpath("//*[text()='Инструментальная панель']");


    public void click(By by) {
        waitVisibility(by);
        try {
            driver.findElement(by).click();
        } catch (ElementClickInterceptedException e) {
            scrollXY(0,200);
            try {
                driver.findElement(by).click();
            } catch (ElementClickInterceptedException e2) {
                scrollXY(0,-400);
                driver.findElement(by).click();
            }
        }
    }

    public void click(WebElement element) {
        waitVisibility(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            scrollXY(0,200);
            try {
                element.click();
            } catch (ElementClickInterceptedException e2) {
                scrollXY(0,-400);
                element.click();
            }
        }
    }

    public void clickJS(By by){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(by));
    }

    public void clickJS(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(elementLocator));
    }

    public void scrollXY(int x, int y){
        String sx = Integer.toString(x);
        String yx = Integer.toString(y);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy("+x+","+y+")");
    }

    public void scrollIntoViewBy(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
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
        try {
            waitVisibility(by);
            return driver.findElement(by).getAttribute(attribute);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getAttribute(WebElement element, String attribute) {
        try {
            waitVisibility(element);
            return element.getAttribute(attribute);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void clearField(By by) {
        driver.findElement(by).clear();
    }

    public void clearField(@NotNull WebElement element) {
        element.clear();
    }

    public boolean isDisplayed(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        }
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
        ((JavascriptExecutor) driver).executeScript( "arguments[0].setAttribute('value','"+date+"')", driver.findElement(by));
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
        for (WebElement i : getElementList(correctedBy)) {
            values.add(i.getText());
        }
        return values;

    }

    public void chooseFromDropDown(By by, String option){
        click(by);
        if (!isDisplayed(select2dropDiv)){
            click(by);
        }
        List<WebElement> options = driver.findElements(select2drop);
        for (WebElement webElement : options) {
            if (webElement.getText().contains(option)) {
                click(webElement);
                break;
            }
        }
    }

    public void chooseFromDropDownRandom(By by){
        click(by);
        if (!isDisplayed(select2dropDiv)){
            click(by);
        }
        List<WebElement> options = driver.findElements(select2drop);
        click(options.get(random.nextInt(options.size())));
    }

    public void toMainPage (){
        while (!isDisplayed(instrumentalPanel)) {
            click(toMainPage);
        }
        log.info("On main page");
    }

    public boolean isStale(By by){
        try{
            driver.findElement(by).isEnabled();
            return false;
        }
        catch (StaleElementReferenceException e){
            return true;
        }
    }

    public List<String> getAllPossibleValues(By by){
        click(by);
        if (!isDisplayed(select2dropDiv)){
            click(by);
        }
        List<WebElement> elements = getElementList(select2drop);
        List<String> values = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            values.add(elements.get(i).getText());
        }
        click(select2dropMask);
        return values;
    }

    public boolean isOnMainPage(){
        return isDisplayed(instrumentalPanel);
    }


}
