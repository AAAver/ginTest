package training;


import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.LoginPage;
import utilities.Props;
import unauthBuilding.UbsScratch;

public class TestFake2 {

	public static void main(String[] args) throws IOException, InterruptedException{
//		// TODO Auto-generated method stub
		
//				
//		System.setProperty("webdriver.chrome.driver", "E:\\Eclipse_workspace\\drivers\\chromedriver.exe");
//		DesiredCapabilities dcap = new DesiredCapabilities();
//        dcap.setCapability("pageLoadStrategy", "eager");
//        ChromeOptions opt = new ChromeOptions();
//        opt.merge(dcap);
//		WebDriver driver = new ChromeDriver(opt);
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		String baseUrl = "http://10.127.48.19/GinHotfix20518/Controls/EditInsp/842442";
//		String ultPassword = "password123";
//		String ultLogin = "3";
//		String fakeAddress = fake.address().streetAddress();
//		
//		driver.get(baseUrl);
//		Login l = new Login(driver);
//		
//		l.loginAs(ultLogin, ultPassword);
//		
//		Inspection izu = new Inspection(driver);
//		izu.objectTab().click();
//		InspObj obj =new InspObj(driver);
//		
//		obj.setDistrict("Якиманка");
//		obj.fieldAddressKIM().clear();
//		obj.fieldAddressKIM().sendKeys(fakeAddress);
//		obj.setObjType("НФ");		
//		
//		FakeValuesService fvs = new FakeValuesService(
//	      new Locale("ru-RU"), new RandomService());
//		
//		String s = fvs.regexify("[4-9{2}]");
//		
//		System.out.println(s);
//		for(int i = 0; i < 15; i++) {
//		System.out.println(fake.number().numberBetween(40, 50)+":"+fake.number().digits(2)+":"+fake.number().digits(7)+":"+fake.number().digits(3)
//				);
//		
//		}
		WebDriver driver = Props.initChromeDriver();
		driver.get("http://10.127.48.19/GinRelease598/UnauthorizedBuilding/Edit/30472");
		LoginPage l = new LoginPage(driver);
		l.loginAs("3", "password123");
		UbsScratch ubs = new UbsScratch(driver);
		ubs.verify();
		 
	}

}
