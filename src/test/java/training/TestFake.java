package training;


import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


import tests.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExtentTestManager;

@Listeners(listeners.Listeners.class)
public class TestFake extends BaseTest {
	private Logger log = LogManager.getLogger(TestFake.class.getName());

	@BeforeClass
	void setDriver(){
		setUpDriver();
		ExtentTestManager.startTest(getClass().getName(), "Тестовый сценарий");
	}


	@AfterClass
	void tearDown(){
		driver.quit();
	}

	@Test(priority = 1, description = "Один из методов")
	public void screenShotTest() throws IOException {
		String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
		System.out.println(time);
		driver.get("https://yandex.ru");
		Assert.assertTrue(true);
		log.info("HELLO");
	}







}
