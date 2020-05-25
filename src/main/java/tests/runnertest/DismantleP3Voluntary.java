package tests.runnertest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pagerepository.common.LoginPage;
import pagerepository.common.MainPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;
import pagerepository.dismantle.DismantleList;
import pagerepository.dismantle.DismantlePage;
import pagerepository.inspection.*;
import pagerepository.inspectionTask.InspectionTaskList;
import pagerepository.inspectionTask.RaidPlanTask;
import pagerepository.ubs.UbsScratch;
import pagerepository.ubs.UnauthBldList;
import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import tests.utils.BaseTest;

import java.io.File;

public class DismantleP3Voluntary extends BaseTest {
    //==== РАСПОЛОЖЕНИЕ ====//
    private String ao = Catalog.area.ao.DEFAULT_AO;
    //==== ОСС РАССМАТРИВАЕТСЯ В РАМКАХ ====//
    private String ubsResolution = Catalog.ubs.resolution.PP_819;
    //==== СХД ====//
    private String shd = Catalog.shd.DEFAULT_SHD;
    //==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
    private String inspTheme1 = Catalog.inspection.theme.UBS_819_IDENT;
    private String inspResult1 = Catalog.inspection.result.PRIL_3;
    private String docCategory1 = Catalog.docs.category.ACT_PRIL_3;
    private String docPath1 = (new File(Catalog.docs.path.ACT_PRIL_3)).getAbsolutePath();
    private SoftAssert softAssert;

    @BeforeClass
    void initDriver() {
        setUpDriver();
        setUpExtentReport("Демонтаж по прил.3 (добровольный)");
    }

    @BeforeMethod
    void initSoftAssertion() {
        softAssert = new SoftAssert();
    }

    private LoginPage l;
    private DisposalsListPage dlp;
    private UnauthBldList ubsList;
    private UbsScratch ubs;
    private DisposalPage d;
    private InspectionMainTab main;
    private InspectionObjectTab obj;
    private InspectionSubjectTab subj;
    private InspectionViolationTab viol;
    private MainPage mp;
    private DismantleList dl;
    private DismantlePage dis;
    private RaidPlanTask raid;
    private InspectionTaskList itl;

    private String ubsUrl;
    private String objSquare;


    @Test(description = "Page initialization")
    void initPages() {
        l = new LoginPage(driver);
        mp = new MainPage(driver);
        dlp = new DisposalsListPage(driver);
        ubsList = new UnauthBldList(driver);
        ubs = new UbsScratch(driver);
        d = new DisposalPage(driver);
        main = new InspectionMainTab(driver);
        obj = new InspectionObjectTab(driver);
        subj = new InspectionSubjectTab(driver);
        viol = new InspectionViolationTab(driver);
        dl = new DismantleList(driver);
        dis = new DismantlePage(driver);
        raid = new RaidPlanTask(driver);
        itl = new InspectionTaskList(driver);
    }


    @Test(dependsOnMethods = "initPages", description = "Добавляем ОСС")
    void addUbs819pp2() throws InterruptedException {
        l.loginAs(ultLogin);
        mp.toUbsList();
        ubsList.addUnauthBld();
        ubs.generateUBS(fakeAddress, ao, ubsResolution);
        ubsUrl = driver.getCurrentUrl();
        objSquare = ubs.getObjSquare();
    }

    @Test(dependsOnMethods = "addUbs819pp2", description = "Добавляем проверку")
    void addInspection() throws InterruptedException {
        driver.get(baseUrl);
        mp.toDisposals();
        dlp.toInspectionZuDisposal();
        d.addInspection();
        main.setInspectionTheme(inspTheme1);
        main.setInspectionResult(inspResult1);
        main.populateCommonInformation();
        main.connectUbs(fakeAddress);
        Upload.file(driver, docCategory1, docPath1);
        obj.objectTabSwitch();
        obj.setAddress(fakeAddress);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        subj.subjectTabSwitch();
        subj.peekShd(shd);
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
        viol.violTabSwitch();
        viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");
    }

    @Test(dependsOnMethods = "addInspection", description = "Корректировка и верификация ОСС")
    void verifyUbs() throws InterruptedException {
        driver.get(ubsUrl);
        ubs.zpo(false);
        ubs.setBuildingKadastr(Generator.fakeKadastr());
        ubs.isManualCorrection(true);
        ubs.setUbsState(Catalog.ubs.state.INCLUDED);
        Save.saveThis(driver);
        ubs.verify();
        log.info(ubs.getUrlTail() + " Ubs ID");
    }

    @Test(dependsOnMethods = "verifyUbs", description = "Работа с карточкой демонтажа")
    void dismantle() throws InterruptedException {
        driver.get(baseUrl);
        mp.toDismantle();
        dl.filterAndOpen(fakeAddress);
        softAssert.assertTrue(dis.getStatus().contains("Требуется обследование территории"));

        dis.stageGbuInitial();
        softAssert.assertTrue(dis.getStatus().contains("Ожидается добровольный демонтаж"));

        dis.stageVoluntaryDismantle(true);
    }

    @Test(dependsOnMethods = "dismantle", description = "Создание задачи на осмотр")
    void inspectionTask(){
        driver.get(baseUrl);
        mp.toInspectionTaskList();
        itl.toRaidList();
        raid.createRaidTask();
    }
}