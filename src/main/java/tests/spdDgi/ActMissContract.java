package tests.spdDgi;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pagerepository.main.MainPage;
import pagerepository.inspections.Disposal;
import pagerepository.main.LoginPage;
import pagerepository.utilities.Save;
import pagerepository.utilities.Upload;
import pagerepository.inspections.*;

import miscelaneous.Catalog;
import miscelaneous.Generator;
import miscelaneous.Props;
import tests.utils.BaseTest;

@Listeners(tests.utils.Listeners.class)
public class ActMissContract extends BaseTest {

	private String companyName = "Альянс Девелопмент";	
	private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
	private String inspTheme = Catalog.inspection.theme.ONF;
	private String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;	


	@BeforeClass
	void setDriver() {
		setUpDriver();
		setUpExtentReport("Генерация акта НФ без договора (Таблица 2). 1010 = 1, 1011 = 2, is_done = 0");
	}

	@AfterClass
	void tearDown() {
		driver.quit();
	}

	LoginPage l;
	Disposal d;
	InspectionPage insp;
	InspectionMainTab main;
	InspectionObjectTab obj;
	InspectionActNF act;
	InspectionViolationTab viol;
	InspectionSubjectTab subj;
	MainPage mp;
	DisposalsList dlp;

	@Test(description = "Инициализация страниц(сервисный шаг)")
	public void initialization() {
		log.info("Initializing pages");
		l = new LoginPage(driver);
		d = new Disposal(driver);
		insp = new InspectionPage(driver);
		main = new InspectionMainTab(driver);
		obj = new InspectionObjectTab(driver);
		act = new InspectionActNF(driver);
		viol = new InspectionViolationTab(driver);
		subj = new InspectionSubjectTab(driver);
		mp = new MainPage(driver);
		dlp = new DisposalsList(driver);
		log.info("Pages initialized");
	}

	@Test(dependsOnMethods = "initialization", description = "Авторизация и создание проверки")
	public void authorization() {
		l.loginAs(ultLogin);
		mp.toDisposals();
		dlp.toInspectionNfDisposal();
		d.addInspection();
	}

	@Test(dependsOnMethods = "authorization", description = "Выбор тематики и результата")
	public void settingThemeAndResult() {
		main.setInspectionTheme(inspTheme);
		main.setInspectionResult(inspResult);
	}

	@Test(dependsOnMethods = "settingThemeAndResult", description = "Установка нарушений акта НФ")
	public void setUpViolations() {
		main.populateCommonInformation();
		act.populateCommonInformation();
		act.previousViolations(false);
		act.isActualUsage(true);
		act.isReplanned(true);
		act.isPremicyUsed(false);
		act.isThirdPartyUsesBuilding(false);
	}

	@Test(dependsOnMethods = "setUpViolations", description = "Загрузка документа(Акт НФ)")
	public void uploadDocuments() {
		Upload.file(driver, actNf, actNfPath);
		act.scrollToBottom();
	}

	@Test(dependsOnMethods = "uploadDocuments", description = "Заполнение данных на вкладке объект")
	public void setObjectInformation() {
		obj.objectTabSwitch();
		obj.setAddress(fakeAddress);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
	}

	@Test(dependsOnMethods = "setObjectInformation", description = "Прикрепление СХД")
	public void setShd() throws InterruptedException {
		Save.saveThis(driver);
		subj.subjectTabSwitch();
		while (!subj.isShdPresented()) {
			subj.peekShd(companyName);
			Save.saveThis(driver);
			subj.subjectTabSwitch();
		}
	}

	@Test(dependsOnMethods = "setShd", description = "Информация о здании")
	void setEgrnInfo() {
		obj.objectTabSwitch();
		obj.createRoomInfoTable();
		obj.createEgrnTable();
	}

	@Test(dependsOnMethods = "setEgrnInfo", description = "Договор не прикрепляется")
	void setBuildingContract() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
		Save.saveThis(driver);
		obj.objectTabSwitch();
		obj.scrollIntoViewBy(obj.getByByName("BtnContract"));
	}

	@Test(dependsOnMethods = "setBuildingContract", description = "Добавление предупреждения")
	void addWarningCard() throws InterruptedException {
		viol.violTabSwitch();
		viol.addWarning();
		viol.violTabSwitch();
	}

	@Test(dependsOnMethods = "addWarningCard", description = "Верификация карточки")
	void verification() throws InterruptedException {
		insp.verify();
		String inspId = insp.getUrlTail();
		Props.setProperty("inspIdMissAct",inspId);
		log.info("Inspection with NO CONTRACT created. ID = " + inspId);
	}
}
