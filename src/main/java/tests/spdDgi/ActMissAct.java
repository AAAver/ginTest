package tests.spdDgi;

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
public class ActMissAct extends BaseTest {


    private final String companyName = "Альянс Девелопмент";
    private final String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
    //==== ТЕМАТИКА/РЕЗУЛЬТАТ/ДОГОВОР ====//
    private final String inspTheme = Catalog.inspection.theme.ONF;
    private final String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
    private final String rightType = Catalog.useRight.RENT;

    @BeforeClass
    void setDriver() {
        setUpDriver();
        setUpExtentReport("Генерация акта НФ без прикрепленного документа категории \"Акт осмотра НФ\". 1010 = 1, 1011 = 3, is_done = 0");
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
        driver.get(disposalUrlNf);
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
        act.isActualUsage(false);
        act.isReplanned(true);
        act.isPremicyUsed(false);
        act.isThirdPartyUsesBuilding(false);
    }

    @Test(dependsOnMethods = "setUpViolations", description = "Загрузка документа(не Акт НФ)")
    public void uploadDocuments() {
        Upload.file(driver, raport, raportPath);
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
        log.info("Inspection with no act nf document attached. ID = " + inspId);
    }
}
