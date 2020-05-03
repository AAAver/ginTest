package training;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.ExtentTestManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners(listeners.Listeners.class)
public class TestFake3 extends BaseTest {
	private Logger log = LogManager.getLogger(TestFake3.class.getName());

	@BeforeClass
	void setDriver(){
		setUpDriver();
		ExtentTestManager.startTest(getClass().getName(), "Тестовый сценарий");
	}


	@AfterClass
	void tearDown(){
		driver.quit();
	}

	@Test(priority = 1, description = "1-й метод")
	public void screenShotTest() {
		String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
		System.out.println(time);
		driver.get("https://yandex.ru");
		Assert.assertTrue(true);
		log.info("HELLO");
	}

	@Test(priority = 1, description = "2-й метод")
	public void screenShotTest2() {
		String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
		System.out.println(time);
		driver.get("https://yandex.ru");
		Assert.assertTrue(true);
		log.info("HELLO");
	}







}
