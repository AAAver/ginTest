package tests.utils;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import miscelaneous.Catalog;
import miscelaneous.Props;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class BaseTest {
    public WebDriver driver;
    public Faker fake = new Faker(new Locale("ru"));
    public Logger log = LogManager.getLogger("ginLogger");

    public String baseUrl = Props.BASE_URL;
    public String disposalUrlNf = Props.DISPOSAL_URL_NF;
    public String disposalUrlZu1 = Props.DISPOSAL_URL_ZU_1;
    public String disposalUrlZu2 = Props.DISPOSAL_URL_ZU_2;
    public String ubsListUrl = Props.UBS_LIST_URL;

    public String ultLogin = Props.ULT_LOGIN;
    public String ultPassword = Props.DEFAULT_PASSWORD;

    // Документы
    public String[] dgiPack = Catalog.docs.category.DGI_PACK;
    public String[] dgiPackPath = Catalog.docs.path.DGI_PACK;
    public String raport = Catalog.docs.category.RAPORT;
    public String raportPath = (new File(Catalog.docs.path.RAPORT)).getAbsolutePath();
    public String actNf = Catalog.docs.category.ACT_NF;
    public String actNfPath = (new File(Catalog.docs.path.ACT_NF)).getAbsolutePath();

    public String fakeAddress = fake.address().streetAddress();


    public void setUpDriver() {
        System.setProperty(Props.CHROME_DRIVER, Props.CHROME_DRIVER_PATH);

        DesiredCapabilities dcap = new DesiredCapabilities();
        dcap.setCapability("pageLoadStrategy", "eager");

        ChromeOptions opt = new ChromeOptions();
        opt.addExtensions(new File("./extensions/cades.crx"));
        opt.addArguments("start-maximized");
        opt.merge(dcap);

        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

//    public void takeScreenshotSuccess(String methodName) {
//       File screenShotFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//       String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
//        try {
//            FileHandler.copy(screenShotFile, new File("./logs/screenshots_success/"+methodName+"_"+time+"_.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
    public void takeScreenshotFail(String methodName, WebDriver webDriver) {
        File screenShotFile =  ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
        try {
            FileHandler.copy(screenShotFile, new File("./logs/screenshots_fail/"+methodName+"_"+time+"_.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setUpExtentReport(String description) {
        ExtentTestManager.startTest(getClass().getName(), description);
    }

    public String trimLoginName(String name){
        String[] parts1 = name.split(" ");
        String noSpace = "";
        for (int i = 0; i < parts1.length; i++) {
            noSpace += parts1[i];
        }
        String[] parts2 = noSpace.split("\\.");
        String trimmedLogin = "";
        for (int i = 0; i < parts2.length; i++) {
            trimmedLogin += parts2[i];
        }
        return trimmedLogin;
    }

}
