package gin.spdDgi;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import inspection.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.BaseTest;
import ubs.UbsScratch;
import ubs.UnauthBldList;
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;

@Listeners({utils.Listeners.class})
public class Ubs819Pril2TakenIntoAccount extends BaseTest {

	private String login = Props.ULT_LOGIN;
	private String password = Props.ULT_PASSWORD;
	private String ubsListUrl = Props.UBS_LIST_URL;
	private String ubsResol = Catalog.ubs.resolution.PP_819;
	private String ubsState = Catalog.ubs.state.TAKEN_INTO_ACCOUNT;
	private String inspTheme = Catalog.inspection.theme.UBS_819_IDENT;
	private String inspResult = Catalog.inspection.result.PRIL_2;
	private String[] dgiDocs = Catalog.docs.category.DGI_PACK;
	private String[] dgiDocsPath = Catalog.docs.path.DGI_PACK;
	private String address = fake.address().streetAddress();
	private String ao = Catalog.area.ao.DEFAULT_AO;
	private String disposalUrl = Props.DISPOSAL_URL_ZU_1;
	private String shd = Catalog.shd.DEFAULT_SHD;
	String ubsUrl;
	String objSquare;

	@BeforeClass
	void setDriver() {
		setUpDriver();
		setUpExtentReport("Генерация ОСС 819-ПП прил.2 Учтен.");
	}

	@AfterClass
	void tearDown() {
		driver.quit();
	}

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
		l.loginAs(login, password);
		ubsList.addUnauthBld();
	}

	@Test(dependsOnMethods = "authorization", description = "Первичное заполнение ОСС")
	public void populateUbs() throws InterruptedException {
		ubs.generateUBS(address, ao, ubsResol);
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
		main.connectUbs(address);
	}

	@Test(dependsOnMethods = "connectUbs", description = "Информация об объекте")
	public void settingObjectInfo() throws InterruptedException {
		obj.objectTabSwitch();
		obj.setAddress(address);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		obj.objectTabSwitch();
	}

	@Test(dependsOnMethods = "settingObjectInfo", description = "Информация о субъекте")
	public void settingSubjectInfo() throws InterruptedException {
		subj.subjectTabSwitch();
		subj.peekShd(shd);
		Save.saveThis(driver);
		subj.subjectTabSwitch();
	}

	@Test(dependsOnMethods = "settingSubjectInfo", description = "Дозаполнение ОСС")
	public void updatingUbs() throws InterruptedException {
		driver.get(ubsUrl);
		ubs.isManualCorrection(true);
		ubs.setUbsState(ubsState);
		ubs.setBuildingKadastr(Generator.fakeKadastr());
		ubs.uploadFile(driver, dgiDocs, dgiDocsPath);
		Save.saveThis(driver);
	}

	@Test(dependsOnMethods = "updatingUbs", description = "Верификация")
	public void verification() throws InterruptedException {
		ubs.verify();
		String ubs819TakenIntoAccountId = ubs.getUrlTail();
		Props.setProperty("ubs819TakenIntoAccountId", ubs819TakenIntoAccountId);
		log.info("ubs 819 pril2 Taken into account ID: " + ubs819TakenIntoAccountId);
	}

}
