package tests.spdDgi;

import java.io.File;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pagerepository.common.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;
import pagerepository.inspection.*;

import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;
import tests.utils.BaseTest;

@Listeners(tests.utils.Listeners.class)
public class ActWrongRightLast extends BaseTest {
	
	private String baseUrl = Props.BASE_URL;
	private String disposalUrl = Props.DISPOSAL_URL_NF;
	private String ultLogin = Props.ULT_LOGIN;
	private String ultPassword = Props.ULT_PASSWORD;
	
	private String address = fake.address().streetAddress();
	private String companyName = "Альянс Девелопмент";	
	private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
	private String inspTheme = Catalog.inspection.theme.ONF;
	private String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
	private String wrongRightType = Catalog.useRight.RIGHT_OF_USE;
	private String correctRightType = Catalog.useRight.RENT;
	private String docCategory = Catalog.docs.category.ACT_NF;
	private String docPath = (new File(Catalog.docs.path.ACT_NF)).getAbsolutePath();

	@BeforeClass
	void setDriver() {
		setUpDriver();
		setUpExtentReport("Генерация акта НФ c первым заключенным договором аренды. is_done = 1");
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
		log.info("Pages initialized");
	}

	@Test(dependsOnMethods = "initialization", description = "Авторизация и создание проверки")
	public void authorization() {
		driver.get(baseUrl);
		l.loginAs(ultLogin, ultPassword);
		driver.get(disposalUrl);
		d.addInspection();
	}

	@Test(dependsOnMethods = "authorization", description = "Выбор тематики и результата")
	public void settingThemeAndResult() {
		main.setInspectionTheme(inspTheme);
		main.setInspectionResult(inspResult);
	}

	@Test(dependsOnMethods = "settingThemeAndResult", description = "Установка нарушений акта НФ. 4 нарушения без ранее выявленных")
	public void setUpViolations() {
		main.populateCommonInformation();
		act.populateCommonInformation();
		act.previousViolations(false);
		act.isActualUsage(false);
		act.isReplanned(true);
		act.isPremicyUsed(false);
		act.isThirdPartyUsesBuilding(true);
	}

	@Test(dependsOnMethods = "setUpViolations", description = "Загрузка документа(Акт НФ)")
	public void uploadDocuments() {
		Upload.file(driver, docCategory, docPath);
		act.scrollToBottom();
	}

	@Test(dependsOnMethods = "uploadDocuments", description = "Заполнение данных на вкладке объект")
	public void setObjectInformation() {
		obj.objectTabSwitch();
		obj.setAddress(address);
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

	@Test(dependsOnMethods = "setEgrnInfo", description = "Первый договор на помещение. Не аренда. Дата позже")
	void setBuildingContractOne() throws InterruptedException {
		Save.saveThis(driver);
		obj.objectTabSwitch();
		obj.createContractTable(wrongRightType,"16052016");
		obj.objectTabSwitch();
	}
	@Test(dependsOnMethods = "setBuildingContractOne", description = "Второй договор на помещение. Аренда. Дата раньше")
	void setBuildingContractTwo() throws InterruptedException {
		Save.saveThis(driver);
		obj.objectTabSwitch();
		obj.createContractTable(correctRightType,"16052015");
		obj.objectTabSwitch();
	}

	@Test(dependsOnMethods = "setBuildingContractTwo", description = "Добавление предупреждения")
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
		log.info("Wrong right contract later. is_done= 1. ID = " + inspId);
	}
}
