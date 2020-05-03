package tests.ubs;

import java.io.File;
import java.util.Locale;

import inspection.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import common.Upload;
import inspection.InspectionPage;
import misc.Catalog;
import misc.Props;
import unauthBuilding.UbsScratch;
import unauthBuilding.UnauthBldList;

public class Pp2inspNum2 {
	private Faker fake = new Faker(new Locale("ru"));

	private String ubsListUrl = Props.UBS_LIST_URL;
	private String ultLogin = Props.ULT_LOGIN;
	private String ultPassword = Props.ULT_PASSWORD;
	private String disposalUrlZu1 = Props.DISPOSAL_URL_ZU_1;
	private String disposalUrlZu2 = Props.DISPOSAL_URL_ZU_2;
	//==== РАСПОЛОЖЕНИЕ ====//
	private String address = fake.address().streetAddress();
	private String ao = Catalog.area.ao.DEFAULT_AO;
	//==== ОСС РАССМАТРИВАЕТСЯ В РАМКАХ ====//
	private String ubsResolution = misc.Catalog.ubs.resolution.PP_819;
	//==== СХД ====//
	private String shd = Catalog.shd.DEFAULT_SHD;
	//==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
	private String inspTheme1 = Catalog.inspection.theme.UBS_819_IDENT;
	private String inspResult1 = Catalog.inspection.result.PRIL_2;
	private String docCategory1 = Catalog.docs.category.ACT_PRIL_2;
	private String docPath1 = (new File(Catalog.docs.path.ACT_PRIL_2)).getAbsolutePath();
	//==== ВТОРАЯ ПРОВЕРКА (МОНИТОРИНГ ЗАКРЕПЛЕННЫХ ТЕРРИТОРИЙ) ====//	
	private String inspTheme2 = Catalog.inspection.theme.TERRITORY_MONITORING;
	private String inspResult2 = Catalog.inspection.result.VIOLS_IDENT;
	
	@Test
	public void addUbs819pp3() throws InterruptedException {
		WebDriver driver = Props.initChromeDriver();		
				
		driver.get(ubsListUrl);
		LoginPage l = new LoginPage(driver);
		l.loginAs(ultLogin, ultPassword);

		// Добавляем ОСС по 819
		UnauthBldList ubsList = new UnauthBldList(driver);
		ubsList.addUnauthBld();
		Thread.sleep(1000);

		UbsScratch ubs = new UbsScratch(driver);
		ubs.generateUBS(address, ao, ubsResolution);
		Thread.sleep(1000);
		String objSquare = ubs.getObjSquare();
		
		//==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
		driver.get(disposalUrlZu1);
		DisposalPage d = new DisposalPage(driver);
		d.btnAddInspection().click();
		InspectionPage insp = new InspectionPage(driver);
		insp.setInspectionTheme(inspTheme1);
		insp.setInspectionResult(inspResult1);
		insp.populateCommonInformation();
		InspectionMainTab main = new InspectionMainTab(driver);
		main.connectUbs(address);
		Upload.file(driver, docCategory1, docPath1);
		insp.objectTabSwitch();
		InspectionObjectTab obj = new InspectionObjectTab(driver);
		obj.setAddress(address);
		obj.setAo(ao);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		InspSubj subj = new InspSubj(driver);
		insp.subjectTabSwitch();
		subj.peekShd(shd);
		Save.saveThis(driver);
		insp.violTabSwitch();		
		InspViol viol = new InspViol(driver);
		viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
		Save.saveThis(driver);
		insp.violTabSwitch();
		viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
		insp.violTabSwitch();
		viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");	

		//==== ПЕРВАЯ ПРОВЕРКА (МОНИТОРИНГ) ====//
		driver.get(disposalUrlZu2);
		d.btnAddInspection().click();
		insp.setInspectionTheme(inspTheme2);
		insp.setInspectionResult(inspResult2);
		insp.populateCommonInformation();
		main.connectUbs(address);
		insp.objectTabSwitch();
		obj.setAddress(address);
		obj.setAo(ao);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		Thread.sleep(300);
		insp.subjectTabSwitch();
		subj.peekShd(shd);
		Save.saveThis(driver);
		insp.violTabSwitch();			
		viol.addViolation("Воспрепятствование проведения проверки должностному лицу");
		Save.saveThis(driver);
		insp.violTabSwitch();
		viol.addProtocol("Воспрепятствование проведения проверки должностному лицу");
		insp.violTabSwitch();
		viol.addPrescription("Воспрепятствование проведения проверки должностному лицу");
	}
}
