package training;


import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


import common.LoginPage;
import inspection.InspectionObjectTab;
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
	public void screenShotTest() throws InterruptedException {
		driver.get("http://10.127.48.19/GinRelease598/Controls/EditInsp/842993");
		LoginPage l = new LoginPage(driver);
		l.loginAs("3","password123");
		InspectionObjectTab obj = new InspectionObjectTab(driver);
		obj.objectTabSwitch();
		obj.setObjSquare("300");

	}







}
