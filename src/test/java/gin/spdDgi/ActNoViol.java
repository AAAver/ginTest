package gin.spdDgi;

import java.io.File;

import inspection.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import common.Upload;
import inspection.InspectionPage;
import utils.BaseTest;
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;

@Listeners(utils.Listeners.class)
public class ActNoViol  extends BaseTest {

	
	private String baseUrl = Props.BASE_URL;
	private String disposalUrl = Props.DISPOSAL_URL_NF;
	private String ultLogin = Props.ULT_LOGIN;
	private String ultPassword = Props.ULT_PASSWORD;
	
	private String address = fake.address().streetAddress();
	private String companyName = "Альянс Девелопмент";	
	private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
	private String inspTheme = Catalog.inspection.theme.ONF;
	private String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
	private String rightType = Catalog.useRight.RENT;
	private String docCategory = Catalog.docs.category.ACT_NF;
	private String docPath = (new File(Catalog.docs.path.ACT_NF)).getAbsolutePath();

	@BeforeClass
	void setDriver() {
		setUpDriver();
		setUpExtentReport("Генерация акта НФ без нарушений и без ранее выявленных. 1010 = 1, no_violations, is_done = 1");
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

	@Test(dependsOnMethods = "settingThemeAndResult", description = "Установка нарушений акта НФ = нет нарушений")
	public void setUpViolations() {
		main.populateCommonInformation();
		act.populateCommonInformation();
		act.previousViolations(false);
		act.isActualUsage(true);
		act.isReplanned(false);
		act.isPremicyUsed(true);
		act.isThirdPartyUsesBuilding(false);
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

	@Test(dependsOnMethods = "setEgrnInfo", description = "Прикрепление договора")
	void setBuildingContract() throws InterruptedException {
		Save.saveThis(driver);
		obj.objectTabSwitch();
		obj.createContractTable(rightType);
		obj.objectTabSwitch();
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
		log.info("No violations whit no previous 1010_no_violations_Is_done=1. ID = " + inspId);
	}
}

