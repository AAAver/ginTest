package tests.utils;


import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners extends BaseTest implements ITestListener {

    Logger log = LogManager.getLogger(Listeners.class.getName());

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onTestStart(ITestResult result) {


    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).getDriver();

        // ДЕФОЛТНЫЙ СКРИНШОТ И ЛОГИРОВАНИЕ
        log.debug("TEST " + result.getMethod().getMethodName() + " SUCCEEDED.");

        // EXTENT ОТЧЁТ И СКРИНШОТ
        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(LogStatus.PASS, result.getMethod().getDescription(),
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).getDriver();

        // ДЕФОЛТНЫЙ СКРИНШОТ И ЛОГИРОВАНИЕ
        log.fatal("TEST " + result.getMethod().getMethodName() + " FAILED. Trace follows: ", result.getThrowable());
//        takeScreenshotFail(result.getMethod().getMethodName(), webDriver);

        // EXTENT ОТЧЁТ И СКРИНШОТ
        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                getScreenshotAs(OutputType.BASE64);
        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getDescription(),
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(LogStatus.SKIP, result.getMethod().getDescription());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        // ДЕФОЛТНЫЙ СКРИНШОТ И ЛОГИРОВАНИЕ

//        log.error("TEST " + result.getMethod().getMethodName() + " FAILED. Trace follows: ", result.getThrowable());

        // EXTENT ОТЧЁТ И СКРИНШОТ
        Object testClass = result.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).getDriver();
        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                getScreenshotAs(OutputType.BASE64);
        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getDescription(),
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
        takeScreenshotFail(result.getMethod().getMethodName(), webDriver);

    }

    @Override
    public void onStart(ITestContext context) {
        context.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

}


