package tests.runnertest;

import miscelaneous.Api;
import miscelaneous.Catalog;
import miscelaneous.Generator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pagerepository.dismantle.Dismantle;
import pagerepository.dismantle.DismantleList;
import pagerepository.inspections.*;
import pagerepository.main.DocumentSigning;
import pagerepository.main.LoginPage;
import pagerepository.main.MainPage;
import pagerepository.plan.InspectionTask;
import pagerepository.plan.InspectionTaskAddress;
import pagerepository.plan.InspectionTaskList;
import pagerepository.plan.RaidPlanTask;
import pagerepository.ubs.UnauthorizedBuilding;
import pagerepository.ubs.UnauthorizedBuildingList;
import pagerepository.utilities.Save;
import pagerepository.utilities.Upload;
import tests.utils.BaseTest;

import java.io.File;
import java.io.IOException;

@Listeners(tests.utils.Listeners.class)
public class FromScratchToDismantlePril3Easy extends BaseTest {

    /*
    Initial Settings
     */
    private String departmentAO = Catalog.area.ao.CAO;
    private String ubsResolution = Catalog.ubs.resolution.PP_819;
    private String controlSHD = Catalog.shd.DEFAULT_SHD;
    private String initialControlTheme = Catalog.inspection.theme.UBS_819_IDENT;
    private String initialControlResult = Catalog.inspection.result.PRIL_3;
    private String docCategory1 = Catalog.docs.category.ACT_PRIL_3;
    private String docPath1 = (new File(Catalog.docs.path.ACT_PRIL_3)).getAbsolutePath();

    /*
    Used Variables
     */
    private String raidNumber1;
    private String raidNumber2;
    private String confirmDismantleDisposalNumber;
    private String disposalUrl;
    private String ubsUrl;
    private String dismantleUrl;
    private String objectSquare;
    private SoftAssert softAssert;

    /*
    Used Pages
     */
    private RaidPlanTask raid;
    private InspectionTaskList inspectionTaskList;
    private InspectionTaskAddress inspectionTaskAddress;
    private InspectionTask inspectionTask;
    private LoginPage loginPage;
    private UnauthorizedBuildingList ubList;
    private UnauthorizedBuilding ub;
    private Disposal disposal;
    private InspectionMainTab main;
    private InspectionObjectTab obj;
    private InspectionSubjectTab subj;
    private InspectionViolationTab viol;
    private MainPage mainPage;
    private DismantleList dismantleList;
    private Dismantle dismantle;

    /*
    Driver setup
    Extent report setup
     */
    @BeforeClass
    void initDriver() throws IOException, InterruptedException {
        setUpDriver();
        setUpExtentReport("Демонтаж по прил.3 (простой)");
        Api.featureControllerDisable("SetlUnauthBldPolygon");
    }

    @BeforeMethod
    void initSoftAssertion() {
        softAssert = new SoftAssert();
    }

    /*
    EXECUTION
     */
    @Test(description = "Авторизация")
    void firstLogin() {
        loginPage = new LoginPage(driver);
        loginPage.loginAs(ultLogin);
        mainPage = new MainPage(driver);
    }

    @Test(dependsOnMethods = "firstLogin", description = "Добавляем ОСС")
    void createUnauthorizedBuilding() throws InterruptedException {
        mainPage.toUbsList();
        ubList = new UnauthorizedBuildingList(driver);
        ubList.addUnauthBld();
        ub = new UnauthorizedBuilding(driver);
        ub.generateUBS(fakeAddress, departmentAO, ubsResolution);

        ubsUrl = driver.getCurrentUrl();
        objectSquare = ub.getObjSquare();
    }

    @Test(dependsOnMethods = "createUnauthorizedBuilding", description = "Добавляем задачу, адрес задачи, ПРЗ и подписываем ПРЗ ")
    void createInspectionTask() throws InterruptedException {
        mainPage.toMainPage();

        inspectionTaskList = mainPage.toInspectionTaskList();
        inspectionTask = inspectionTaskList.addTaskZu();

        inspectionTask.create("Поручение", initialControlTheme, departmentAO);
        inspectionTaskAddress = inspectionTask.addAddress();

        inspectionTaskAddress.create(fakeAddress, departmentAO, 2);

        mainPage.toMainPage();
        mainPage.toInspectionTaskList();

        raid = inspectionTaskList.toRaidList();
        raid.createRaidTask("Горбунов");
        raidNumber1 = raid.getRaidNumber();
        raid.toInspectionTaskList();

        inspectionTaskList.addTaskToRaid(fakeAddress, raidNumber1);
        inspectionTaskList.toRaidList();
        raid.sign(raidNumber1,"ГорбуновАЕ");
    }

    @Test(dependsOnMethods = "createInspectionTask", description = "Добавляем поручение и првоерку")
    void createInspection() throws InterruptedException {
        loginPage.loginAs(ultLogin);
        mainPage.toInspectionTaskList();
        inspectionTaskList.openInspectionTaskAddress(fakeAddress);

        disposal = inspectionTaskAddress.addDisposal();
        disposal.populate();
        disposal.addInspection();

        main = new InspectionMainTab(driver);
        obj = new InspectionObjectTab(driver);
        subj = new InspectionSubjectTab(driver);
        viol = new InspectionViolationTab(driver);

        main.setInspectionTheme(initialControlTheme);
        main.setInspectionResult(initialControlResult);
        main.populateCommonInformation();
        main.connectUbs(fakeAddress);
        Upload.file(driver, docCategory1, docPath1);
        obj.objectTabSwitch();
        obj.setAddress(fakeAddress);
        obj.setObjSquare(objectSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        subj.subjectTabSwitch();
        while (!subj.isShdPresented()) {
            subj.peekShd(controlSHD);
            Save.saveThis(driver);
            subj.subjectTabSwitch();
        }
        viol.violTabSwitch();
        viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
        viol.violTabSwitch();
        viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");
    }

    @Test(dependsOnMethods = "createInspection", description = "Корректировка и верификация ОСС")
    void verifyUbs() throws InterruptedException {
        driver.get(ubsUrl);

        ub.zpo(true);
        ub.setBuildingKadastr(Generator.fakeKadastr());
        Save.saveThis(driver);
        ub.verify();
    }

    @Test(dependsOnMethods = "verifyUbs", description = "Работа с карточкой демонтажа")
    void dismantle() throws InterruptedException {
        mainPage.toMainPage();
        mainPage.toDismantle();

        dismantleList = new DismantleList(driver);
        dismantle = new Dismantle(driver);

        dismantleList.openDismantle(fakeAddress);
        dismantleUrl = driver.getCurrentUrl();

        softAssert.assertTrue(dismantle.getStatus().contains("Требуется обследование территории"));
        dismantle.stageGbuInitial();

        softAssert.assertTrue(dismantle.getStatus().contains("Ожидается добровольный демонтаж"));
        dismantle.stageVoluntaryDismantle(false);

        softAssert.assertTrue(dismantle.getStatus().contains("Определение сложности демонтажа"));
        dismantle.stageDismantleComplexity(false);

        softAssert.assertTrue(dismantle.getStatus().contains("Демонтаж"));
        dismantle.dismantleByContractor();

        softAssert.assertTrue(dismantle.getStatus().contains("Приёмка демонтажа (ГБУ)"));
        dismantle.stageGbuAcceptance(Catalog.docs.category.GBU_DISMANTLE_DOC_PACK, Catalog.docs.path.GBU_DISMANTLE_DOC_PACK);

    }

    @Test(dependsOnMethods = "dismantle", description = "Создание задачи на подтверждение демонтажа")
    void confirmDismantleInspectionTask() throws InterruptedException {
        dismantle.toMainPage();
        mainPage.toInspectionTaskList();
        inspectionTaskList.toRaidList();
        raid.createRaidTask("Горбунов");
        raidNumber2 = raid.getRaidNumber();

        raid.toInspectionTaskList();
        inspectionTaskList.openInspectionTaskAddress(fakeAddress);
        inspectionTaskAddress.setInspectorsRandom(2);
        inspectionTaskAddress.save();
        inspectionTaskAddress.finishAddressCorrection();
        inspectionTaskAddress.toInspectionTaskList();
        inspectionTaskList.addTaskToRaid(fakeAddress, raidNumber2);
        inspectionTaskList.toRaidList();
        raid.sign(raidNumber2, "ГорбуновАЕ");

        loginPage.loginAs(ultLogin);
        mainPage.toInspectionTaskList();
        inspectionTaskList.openInspectionTaskAddress(fakeAddress);

        inspectionTaskAddress.addDisposal();
        disposal.populate();
        disposalUrl = driver.getCurrentUrl();
        disposal.sign(disposalUrl, "ЕлшинЕБ");
    }

    @Test(dependsOnMethods = "confirmDismantleInspectionTask", description = "Создание проверки по подтверждению демонтажа")
    void confirmDismantleInspection() throws InterruptedException {
        loginPage.loginAs(ultLogin);
        driver.get(disposalUrl);
        disposal.addInspection();

        main.populateCommonInformation();
        main.setInspectionResult("Демонтаж подтвержден");
        main.connectUbs(fakeAddress);
        Upload.file(driver, "Акт о подтверждении демонтажа", docPath1);
        obj.objectTabSwitch();
        obj.setObjSquare(objectSquare);
        obj.saveInspection();
        obj.objectTabSwitch();
        obj.setContractZU(false);
        obj.saveInspection();
        main.verify();

        loginPage.logout();
    }

    @Test(dependsOnMethods = "confirmDismantleInspection", description = "Подтверждение демонтажа ФХУ ГИН")
    void confirmDismantleFHU(){
        loginPage.loginAs(ultLogin);
        driver.get(dismantleUrl);
    }
}