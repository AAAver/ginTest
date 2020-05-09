package tests.training;


import pagerepository.common.LoginPage;
import pagerepository.inspection.InspectionMainTab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.utils.BaseTest;
import tests.utils.ExtentTestManager;


@Listeners(tests.utils.Listeners.class)
public class TestFake3 extends BaseTest {
	private Logger log = LogManager.getLogger(TestFake3.class.getName());

	@BeforeClass
	void setDriver(){
		setUpDriver();
		ExtentTestManager.startTest(getClass().getName(), "Тестовый сценарий");
	}
//
//
//	@AfterClass
//	void tearDown(){
//		driver.quit();
//	}

	@Test(priority = 1, description = "1-й метод")
	public void screenShotTest() throws NoSuchFieldException, IllegalAccessException {
	driver.get("http://10.127.48.19/GinRelease586/Controls/EditInsp/843015");
		LoginPage l = new LoginPage(driver);
		l.loginAs("3","password123");

		InspectionMainTab m = new InspectionMainTab(driver);
		var x = m.getActualValuesFromField(m.getByByName("factUsage"));
		for(String i : x){
			System.out.println(i);
		}
		System.out.println(x.contains("навесы - укрытия"));
		System.out.println(x.contains("навесы"));

	}







}
