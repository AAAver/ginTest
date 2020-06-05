package tests.training;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagerepository.main.LoginPage;
import pagerepository.legalcase.DgiLegalCase;
import tests.utils.BaseTest;

import java.util.ArrayList;
import java.util.List;


public class TestFake3 extends BaseTest {

	List<Integer> l = new ArrayList<>();
	@BeforeClass
	void setup(){
		setUpDriver();
	}
	@Test
	public void interd() throws InterruptedException {
		driver.get("http://192.168.4.117/626/DgiLegalCase");
		LoginPage l = new LoginPage(driver);
		l.loginAs("3");
		DgiLegalCase dlc = new DgiLegalCase(driver);
		dlc.findLegalCaseForUbs("Махмутова 49");
	}


}
