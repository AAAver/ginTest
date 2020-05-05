package training;


import common.LoginPage;
import inspection.InspectionActNF;
import inspection.InspectionMainTab;
import inspection.InspectionPage;
import inspection.Prescription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.BaseTest;
import unauthBuilding.UbsScratch;
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
