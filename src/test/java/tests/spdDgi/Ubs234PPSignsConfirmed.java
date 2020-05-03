package tests.spdDgi;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import inspection.InspectionPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import inspection.InspectionMainTab;
import inspection.InspectionObjectTab;
import inspection.InspSubj;
import misc.Catalog;
import misc.Generator;
import misc.Props;
import unauthBuilding.UbsScratch;
import unauthBuilding.UnauthBldList;

public class Ubs234PPSignsConfirmed {
	private Faker fake = new Faker(new Locale("ru"));
	
	private String ubsResol = Catalog.ubs.resolution.PP_234;
	private String ubsState = Catalog.ubs.state.UBS_SIGNS_CONFIRMED;
	private String address = fake.address().streetAddress();
	private String ao = Catalog.area.ao.DEFAULT_AO;
	private String disposalUrl = Props.DISPOSAL_URL_ZU_1;
	private String inspTheme = Catalog.inspection.theme.UBS_819_IDENT;
	private String inspResult = Catalog.inspection.result.FED_PROPERTY;
	private String shd = Catalog.shd.DEFAULT_SHD;
	private String[] dgiDocs = Catalog.docs.category.DGI_PACK;
	private String[] dgiDocsPath = Catalog.docs.path.DGI_PACK;
	
	@Test
	public void pp234SignsConfirmed() throws InterruptedException {
		WebDriver driver = Props.initChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		

		// Авторизация
		driver.get(Props.UBS_LIST_URL);
		LoginPage l = new LoginPage(driver);
		l.loginAs(Props.ULT_LOGIN, Props.ULT_PASSWORD);

		//==== ДОБАВЛЯЕМ ОСС 234-ПП ====//
		UnauthBldList ubsList = new UnauthBldList(driver);
		ubsList.addUnauthBld();
		Thread.sleep(1000);
		UbsScratch ubs = new UbsScratch(driver);
		ubs.generateUBS(address, ao, ubsResol);
		Thread.sleep(1000);
		String objSquare = ubs.getObjSquare();
		String ubsUrl = driver.getCurrentUrl();

		//==== ДОБАВЛЯЕМ ПРОВЕРКУ ====//
		driver.get(disposalUrl);
		DisposalPage d = new DisposalPage(driver);
		d.btnAddInspection().click();
		InspectionPage insp = new InspectionPage(driver);
		insp.setInspectionTheme(inspTheme);
		insp.setInspectionResult(inspResult);
		insp.populateCommonInformation();
		InspectionMainTab main = new InspectionMainTab(driver);
		main.connectUbs(address);	
		insp.objectTabSwitch();
		InspectionObjectTab obj = new InspectionObjectTab(driver);
		obj.setAddress(address);
		obj.setAo(ao);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		//==== ДОБАВЛЯЕМ СУБЪЕКТ ПРОВЕРКИ ====//
		InspSubj subj = new InspSubj(driver);
		insp.subjectTabSwitch();
		subj.peekShd(shd);
		Save.saveThis(driver);
		//==== ВОЗВРАЩАЕМСЯ В ОСС====//
		driver.get(ubsUrl);
		ubs.isManualCorrection(true);
		ubs.setUbsState(ubsState);
		ubs.setBuildingKadastr(Generator.fakeKadastr());
		ubs.uploadFile(driver, dgiDocs, dgiDocsPath);
		Save.saveThis(driver);
		ubs.verify();
		String ubs234PPSignsConfirmedId = ubs.getUbsId();
		Props.setProperty("ubs234PPSignsConfirmedId", ubs234PPSignsConfirmedId);
		driver.close();
		

	}

}
