package tests.runnertest;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.common.MainPage;
import pagerepository.inspection.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;
import pagerepository.inspection.*;
import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;
import tests.utils.BaseTest;

@Listeners(tests.utils.Listeners.class)
public class BakeControlNf extends BaseTest {

    private String companyName = "Альянс Девелопмент";
    private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
    private String inspTheme = Catalog.inspection.theme.ONF;
    private String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
    private String rightType = Catalog.useRight.RENT;

    @BeforeClass
    void setDriver() {
        setUpDriver();
        setUpExtentReport("Генерация акта НФ c 4-мя нарушениями без ранее выявленных. 1010 = 1, 1011 = 4, is_done = 1");
    }

    LoginPage l;
    DisposalPage d;
    InspectionPage insp;
    InspectionMainTab main;
    InspectionObjectTab obj;
    InspectionActNF act;
    InspectionViolationTab viol;
    InspectionSubjectTab subj;
    MainPage mp;
    DisposalsListPage dlp;

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
        mp = new MainPage(driver);
        dlp = new DisposalsListPage(driver);
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
    void finalization() {
        String inspId = insp.getUrlTail();
        Props.setProperty("inspIdMissAct", inspId);
        log.info("4 violations whit no previous 1010+1011x4_Is_done=1. ID = " + inspId);
    }
}