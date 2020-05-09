package tests.utils;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import pagerepository.utilities.Props;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    public WebDriver driver;
    public Faker fake = new Faker(new Locale("ru"));
    public Logger log = LogManager.getLogger("ginLogger");


    public void setUpDriver(){
        System.setProperty(Props.CHROME_DRIVER, Props.CHROME_DRIVER_PATH);

        DesiredCapabilities dcap = new DesiredCapabilities();
        dcap.setCapability("pageLoadStrategy", "eager");

        ChromeOptions opt = new ChromeOptions();
        opt.merge(dcap);

        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
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
//    public void takeScreenshotFail(String methodName) {
//        File screenShotFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
//        try {
//            FileHandler.copy(screenShotFile, new File("./logs/screenshots_fail/"+methodName+"_"+time+"_.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setUpExtentReport(String description){
        ExtentTestManager.startTest(getClass().getName(), description);
    }

}
