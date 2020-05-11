package tests.runnertest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.common.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.inspection.*;
import pagerepository.ubs.UbsScratch;
import pagerepository.ubs.UnauthBldList;
import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;
import tests.utils.BaseTest;

@Listeners(tests.utils.Listeners.class)
public class Ubs234PPSignsConfirmed extends BaseTest {

	private String ubsResol = Catalog.ubs.resolution.PP_234;
	private String ubsState = Catalog.ubs.state.UBS_SIGNS_CONFIRMED;
	private String ao = Catalog.area.ao.DEFAULT_AO;
	private String disposalUrl = Props.DISPOSAL_URL_ZU_1;
	private String inspTheme = Catalog.inspection.theme.UBS_819_IDENT;
	private String inspResult = Catalog.inspection.result.FED_PROPERTY;
	private String shd = Catalog.shd.DEFAULT_SHD;

	String ubsUrl;
	String objSquare;

	@BeforeClass
	void setDriver() {
		setUpDriver();
		setUpExtentReport("Генерация ОСС 234-ПП Признаки СС подтверждены.");
	}

//	@AfterClass
//	void tearDown() {
//		driver.quit();
//	}

	LoginPage l;
	DisposalPage d;
	InspectionPage insp;
	InspectionMainTab main;
	InspectionObjectTab obj;
	InspectionActNF act;
	InspectionViolationTab viol;
	InspectionSubjectTab subj;
	UnauthBldList ubsList;
	UbsScratch ubs;

	@Test(description = "Инициализация страниц(сервисный шаг)")
	public void initialization() {
		log.info("Initializing pages");
		l = new LoginPage(driver);
		d = new DisposalPage(driver);
		insp = new InspectionPage(driver);
		main = new InspectionMainTab(driver);
		obj = new InspectionObjectTab(driver);
		act = new InspectionActNF(driver);
		viol = new InspectionViolationTab(driver);
		subj = new InspectionSubjectTab(driver);
		ubsList = new UnauthBldList(driver);
		ubs = new UbsScratch(driver);
		log.info("Pages initialized");
	}


	@Test(dependsOnMethods = "initialization", description = "Авторизация и создание ОСС")
	public void authorization() {
		driver.get(ubsListUrl);
		l.loginAs(ultLogin, ultPassword);
		ubsList.addUnauthBld();
	}

	@Test(dependsOnMethods = "authorization", description = "Первичное заполнение ОСС")
	public void populateUbs() throws InterruptedException {
		ubs.generateUBS(fakeAddress, ao, ubsResol);
		objSquare = ubs.getObjSquare();
		ubsUrl = driver.getCurrentUrl();
	}

	@Test(dependsOnMethods = "populateUbs", description = "Добавляем проверку")
	public void addControl() {
		driver.get(disposalUrl);
		d.addInspection();
	}

	@Test(dependsOnMethods = "addControl", description = "Тематика и результат")
	public void controlThemeAndResult() {
		main.setInspectionTheme(inspTheme);
		main.setInspectionResult(inspResult);
	}

	@Test(dependsOnMethods = "controlThemeAndResult", description = "Связка с ОСС")
	public void connectUbs() {
		main.populateCommonInformation();
		main.connectUbs(fakeAddress);
	}

	@Test(dependsOnMethods = "connectUbs", description = "Информация об объекте")
	public void settingObjectInfo() throws InterruptedException {
		obj.objectTabSwitch();
		obj.setAddress(fakeAddress);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		obj.objectTabSwitch();
	}

	@Test(dependsOnMethods = "settingObjectInfo", description = "Информация о субъекте")
	public void settingSubjectInfo() throws InterruptedException {
		subj.subjectTabSwitch();
		while (!subj.isShdPresented()) {
			subj.peekShd(shd);
			Save.saveThis(driver);
			subj.subjectTabSwitch();
		}
	}

	@Test(dependsOnMethods = "settingSubjectInfo", description = "Дозаполнение ОСС")
	public void updatingUbs() throws InterruptedException {
		driver.get(ubsUrl);
		ubs.isManualCorrection(true);
		ubs.setUbsState(ubsState);
		ubs.setBuildingKadastr(Generator.fakeKadastr());
		ubs.uploadFile(driver, dgiPack, dgiPackPath);
		Save.saveThis(driver);
	}

	@Test(dependsOnMethods = "updatingUbs", description = "Верификация")
	public void verification() throws InterruptedException {
		ubs.verify();
		String ubs234PPSignsConfirmedId = ubs.getUrlTail();
		Props.setProperty("ubs234PPIncluded", ubs234PPSignsConfirmedId);
		log.warn("Ubs 234 PP Signs Confirmed ID: " + ubs234PPSignsConfirmedId);
	}

}