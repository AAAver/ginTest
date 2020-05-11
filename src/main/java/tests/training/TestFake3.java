package tests.training;


import pagerepository.common.LoginPage;
import pagerepository.inspection.InspectionMainTab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.utilities.Props;
import tests.utils.BaseTest;
import tests.utils.ExtentTestManager;



public class TestFake3 extends BaseTest {
	@Test
	private void trimBaseUrl(){
		String untrimmedDisposalZu = Props.getProperty("disposalUrlZu");
		String untrimmedDisposalNf = Props.getProperty("disposalUrlNf");

		String[] parts;
		String baseUrl = "";
		if(untrimmedDisposalNf != null){
			parts = untrimmedDisposalNf.split("/");
			for(int i = 0; i < 4; i++){
				baseUrl += parts[i]+"/";
			}
		}
		else{
			parts = untrimmedDisposalZu.split("/");
			for(int i = 0; i < 4; i++){
				baseUrl += parts[i]+"/";
			}
		}
		System.out.println(baseUrl);

	}
}
