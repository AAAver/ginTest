package miscelaneous;

import java.io.*;
import java.util.Properties;

public class Props {
    private static final String PROP_PATH = "./cfg.properties";
    public static final String BASE_URL = getProperty("baseUrl");
    public static final String DISPOSAL_URL_NF = getProperty("disposalUrlNf");
    public static final String DISPOSAL_URL_ZU_1 = getProperty("disposalUrlZu");
    public static final String DISPOSAL_URL_ZU_2 = getProperty("disposalUrlZu");
    public static final String UBS_LIST_URL = BASE_URL + "/UnauthorizedBuilding";

    public static final String ULT_LOGIN = "3";
    public static final String DEFAULT_PASSWORD = decidePassword();

    public static final String CHROME_DRIVER = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "./driver/chromedriver.exe";
    public static final String PHOTO_PATH_A = "images/ubsA.jpg";
    public static final String PHOTO_PATH_U = "images/ubsU.png";
    public static final String PHOTO_PATH_T = "images/ubsT.png";
    public static final String PHOTO_PATH_O = "images/ubsO.jpg";

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

    private static String trimBaseUrl() {
        String untrimmedDisposalZu = Props.getProperty("disposalUrlZu");
        String untrimmedDisposalNf = Props.getProperty("disposalUrlNf");

        String[] parts;
        String baseUrl = "";
        if (untrimmedDisposalNf != null) {
            parts = untrimmedDisposalNf.split("/");
            for (int i = 0; i < 4; i++) {
                baseUrl += parts[i] + "/";
            }
        } else {
            parts = untrimmedDisposalZu.split("/");
            for (int i = 0; i < 4; i++) {
                baseUrl += parts[i] + "/";
            }
        }
        return baseUrl;
    }

    private static String decidePassword() {
        if (BASE_URL.contains("10.127.48.19")) {
            return "password123";
        } else {
            return "password";
        }
    }

}
