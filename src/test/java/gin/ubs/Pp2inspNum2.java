package gin.ubs;

import java.io.File;
import java.util.Locale;

import inspection.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import common.Upload;

import utils.BaseTest;
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;
import ubs.UbsScratch;
import ubs.UnauthBldList;
import utils.ExtentTestManager;

@Listeners(utils.Listeners.class)
public class Pp2inspNum2 extends BaseTest {
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
	private String ubsResolution = Catalog.ubs.resolution.PP_819;
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

	@BeforeClass
	void initDriver(){
		setUpDriver();
		ExtentTestManager.startTest(getClass().getName(), "Тестовый сценарий");
	}

//
//	@AfterClass
//	void tearDown(){
//		driver.quit();
//	}


	
	@Test(priority = 1, description = "Новый тест")
	public void addUbs819pp2() throws InterruptedException {

		driver.get(ubsListUrl);
		LoginPage l = new LoginPage(driver);
		l.loginAs(ultLogin, ultPassword);

		// Добавляем ОСС по 819
		UnauthBldList ubsList = new UnauthBldList(driver);
		ubsList.addUnauthBld();

		UbsScratch ubs = new UbsScratch(driver);
		ubs.generateUBS(address, ao, ubsResolution);
		String ubsUrl = driver.getCurrentUrl();
		String objSquare = ubs.getObjSquare();
		
		//==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
		driver.get(disposalUrlZu1);
		DisposalPage d = new DisposalPage(driver);
		d.addInspection();
		InspectionMainTab main = new InspectionMainTab(driver);
		main.setInspectionTheme(inspTheme1);
		main.setInspectionResult(inspResult1);
		main.populateCommonInformation();
		main.connectUbs(address);
		Upload.file(driver, docCategory1, docPath1);
		InspectionObjectTab obj = new InspectionObjectTab(driver);
		obj.objectTabSwitch();
		obj.setAddress(address);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		InspectionSubjectTab subj = new InspectionSubjectTab(driver);
		subj.subjectTabSwitch();
		subj.peekShd(shd);
		Save.saveThis(driver);
		InspectionViolationTab viol = new InspectionViolationTab(driver);
		viol.violTabSwitch();
		viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
		Save.saveThis(driver);
		viol.violTabSwitch();
		viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
		viol.violTabSwitch();
		viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");	

		//==== ПЕРВАЯ ПРОВЕРКА (МОНИТОРИНГ) ====//
		driver.get(disposalUrlZu2);
		d.addInspection();
		main.setInspectionTheme(inspTheme2);
		main.setInspectionResult(inspResult2);
		main.populateCommonInformation();
		main.connectUbs(address);
		obj.objectTabSwitch();
		obj.setAddress(address);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		subj.subjectTabSwitch();
		subj.peekShd(shd);
		Save.saveThis(driver);
		viol.violTabSwitch();
		viol.addViolation("Воспрепятствование проведения проверки должностному лицу");
		Save.saveThis(driver);
		viol.violTabSwitch();
		viol.addProtocol("Воспрепятствование проведения проверки должностному лицу");
		viol.violTabSwitch();
		viol.addPrescription("Воспрепятствование проведения проверки должностному лицу");

		driver.get(ubsUrl);

		ubs.zpo(true);
		ubs.setBuildingKadastr(Generator.fakeKadastr());
		System.out.println(ubs.getUrlTail());
	}
}
