package utilities;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Props {
	private static final String PROP_PATH = "./cfg.properties";
	public static final String BASE_URL = "http://192.168.4.117/586";
	public static final String DISPOSAL_URL_NF = "http://10.127.48.19/GinRelease586/Disposals/Edit/818931";
	public static final String DISPOSAL_URL_ZU_1 = BASE_URL + "/Disposals/Edit/849541";
	public static final String DISPOSAL_URL_ZU_2 = BASE_URL + "/Disposals/Edit/849540";
	public static final String UBS_LIST_URL = BASE_URL + "/UnauthorizedBuilding";

	public static final String ULT_LOGIN = "3";
	public static final String ULT_PASSWORD = "password";

	public static final String CHROME_DRIVER = "webdriver.chrome.driver";
	public static final String CHROME_DRIVER_PATH = "./driver/chromedriver.exe";	
	public static final String PHOTO_PATH_A = "images/ubsA.jpg";
	public static final String PHOTO_PATH_U = "images/ubsU.png";
	public static final String PHOTO_PATH_T = "images/ubsT.png";
	public static final String PHOTO_PATH_O = "images/ubsO.jpg";
	
	public static WebDriver initChromeDriver() {
		System.setProperty(Props.CHROME_DRIVER, Props.CHROME_DRIVER_PATH);
		DesiredCapabilities dcap = new DesiredCapabilities();
		dcap.setCapability("pageLoadStrategy", "eager");
		ChromeOptions opt = new ChromeOptions();
		opt.merge(dcap);
		WebDriver driver = new ChromeDriver(opt);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return driver;
	}

	public static String getProperty(String key) {
		Properties prop = new Properties();		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(PROP_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = prop.getProperty(key);
		return property;
	
	}

	public static void setProperty(String key, String value) {
		Properties prop = new Properties();	
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(PROP_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos = new FileOutputStream(PROP_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop.put(key, value);
		try {
			prop.store(fos, "hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
