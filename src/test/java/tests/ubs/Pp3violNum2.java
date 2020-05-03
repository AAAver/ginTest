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
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;
import unauthBuilding.UbsScratch;
import unauthBuilding.UnauthBldList;

public class Pp3violNum2 {
	
	private Faker fake = new Faker(new Locale("ru"));

	private String ubsListUrl = Props.UBS_LIST_URL;
	private String ultLogin = Props.ULT_LOGIN;
	private String ultPassword = Props.ULT_PASSWORD;
	private String disposalUrlZu1 = Props.DISPOSAL_URL_ZU_1;

	//==== РАСПОЛОЖЕНИЕ ====//
	private String address = fake.address().streetAddress();
	private String ao = Catalog.area.ao.DEFAULT_AO;
	//==== ОСС РАССМАТРИВАЕТСЯ В РАМКАХ ====//
	private String ubsResolution = utilities.Catalog.ubs.resolution.PP_819;
	//==== СХД ====//
	private String shd = Catalog.shd.DEFAULT_SHD;
	//==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
	private String inspTheme1 = Catalog.inspection.theme.UBS_819_IDENT;
	private String inspResult1 = Catalog.inspection.result.PRIL_3;
	private String docCategory1 = Catalog.docs.category.ACT_PRIL_3;
	private String docPath1 = (new File(Catalog.docs.path.ACT_PRIL_3)).getAbsolutePath();
	
	
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
		String ubsUrl = driver.getCurrentUrl();
		
		//==== ПРОВЕРКА (819-ПП) ====//
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
		
		//==== НАРУШЕНИЯ ====//
		insp.violTabSwitch();		
		InspViol viol = new InspViol(driver);
		//==== 1-е ====//
		viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
		Save.saveThis(driver);
		insp.violTabSwitch();
		viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
		insp.violTabSwitch();
		viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");	
		//==== 2-е ====//
		insp.violTabSwitch();			
		viol.addViolation("Воспрепятствование проведения проверки должностному лицу");
		Save.saveThis(driver);
		insp.violTabSwitch();
		viol.addProtocol("Воспрепятствование проведения проверки должностному лицу");
		insp.violTabSwitch();
		viol.addPrescription("Воспрепятствование проведения проверки должностному лицу");	
		
		//==== СХД ====//
		InspSubj subj = new InspSubj(driver);
		insp.subjectTabSwitch();
		while(!subj.isShdPresented()) {			
			subj.peekShd(shd);
			Save.saveThis(driver);
			insp.subjectTabSwitch();
		}		
		
		
		
		driver.get(ubsUrl);
		ubs.zpo(true);
		ubs.setBuildingKadastr(Generator.fakeKadastr());
		ubs.setObjectTypeRandom();
		
	}

}
