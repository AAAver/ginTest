package tests.ubs;

import java.io.File;

import pagerepository.main.MainPage;
import pagerepository.inspections.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pagerepository.inspections.Disposal;
import pagerepository.main.LoginPage;
import pagerepository.utilities.Save;
import pagerepository.utilities.Upload;

import tests.utils.BaseTest;
import miscelaneous.Catalog;
import miscelaneous.Generator;
import pagerepository.ubs.UnauthorizedBuilding;
import pagerepository.ubs.UnauthorizedBuildingList;

@Listeners(tests.utils.Listeners.class)
public class CreateUBS819Pril2 extends BaseTest {

    //==== РАСПОЛОЖЕНИЕ ====//
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
    String ubsUrl;
    String objSquare;

    @BeforeClass
    void initDriver() {
        setUpDriver();
        setUpExtentReport("ОСС по прил.2 с проверкой и нарушениями");
    }


//    @AfterClass
//    void tearDown() {
//        driver.quit();
//    }

    LoginPage l;
    Disposal d;
    InspectionPage insp;
    InspectionMainTab main;
    InspectionObjectTab obj;
    InspectionActNF act;
    InspectionViolationTab viol;
    InspectionSubjectTab subj;
    UnauthorizedBuildingList ubsList;
    UnauthorizedBuilding ubs;
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
        ubsList = new UnauthorizedBuildingList(driver);
        ubs = new UnauthorizedBuilding(driver);
        mp = new MainPage(driver);
        dlp = new DisposalsList(driver);
        log.info("Pages initialized");
    }

    @Test(dependsOnMethods = "initialization", description = "Авторизация и создание карточки ОСС")
    public void authorization() throws InterruptedException {
        l.loginAs(ultLogin);
        mp.toUbsList();
        ubsList.addUnauthBld();
    }

    @Test(dependsOnMethods = "authorization", description = "Заполнение карточки ОСС")
    public void populateUBS() throws InterruptedException {
        ubs.generateUBS(fakeAddress, ao, ubsResolution);
        ubsUrl = driver.getCurrentUrl();
        objSquare = ubs.getObjSquare();
    }

    @Test(dependsOnMethods = "populateUBS", description = "Создание проверки. Тематика и результат")
    public void setUpInspectionThemeAndResultOne() {
        mp.toMainPage();
        mp.toDisposals();
        dlp.toInspectionNfDisposal();
        d.addInspection();
        main.populateCommonInformation();
        main.setInspectionTheme(inspTheme1);
        main.setInspectionResult(inspResult1);
    }

    @Test(dependsOnMethods = "setUpInspectionThemeAndResultOne", description = "Связка с ОСС")
    public void connectUbsOne() {
        main.connectUbs(fakeAddress);
        Upload.file(driver, docCategory1, docPath1);
    }

    @Test(dependsOnMethods = "connectUbsOne", description = "Заполнение вкладки объект")
    public void settingObjectInfoOne() throws InterruptedException {
        obj.objectTabSwitch();
        obj.setAddress(fakeAddress);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        obj.objectTabSwitch();
    }

    @Test(dependsOnMethods = "settingObjectInfoOne", description = "Заполнение вкладки субъект")
    public void settingSubjectInfoOne() throws InterruptedException {
        subj.subjectTabSwitch();
        while (!subj.isShdPresented()) {
            subj.peekShd(shd);
            Save.saveThis(driver);
            subj.subjectTabSwitch();
        }
    }

    @Test(dependsOnMethods = "settingSubjectInfoOne", description = "Добавление нарушений")
    public void setViolationOne() throws InterruptedException {
        viol.violTabSwitch();
        viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
        viol.violTabSwitch();
        viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");
    }


    @Test(dependsOnMethods = "setViolationOne", description = "Создание проверки. Тематика и результат")
    public void setUpInspectionThemeAndResultTwo() {
        mp.toMainPage();
        mp.toDisposals();
        dlp.toInspectionZuDisposal();
        d.addInspection();
        main.populateCommonInformation();
        main.setInspectionTheme(inspTheme2);
        main.setInspectionResult(inspResult2);
    }

    @Test(dependsOnMethods = "setUpInspectionThemeAndResultTwo", description = "Связка с ОСС")
    public void connectUbsTwo() {
        main.connectUbs(fakeAddress);
    }

    @Test(dependsOnMethods = "connectUbsTwo", description = "Заполнение вкладки объект")
    public void settingObjectInfoTwo() throws InterruptedException {
        obj.objectTabSwitch();
        obj.setAddress(fakeAddress);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        obj.objectTabSwitch();
    }

    @Test(dependsOnMethods = "settingObjectInfoTwo", description = "Заполнение вкладки объект")
    public void settingSubjectInfoTwo() throws InterruptedException {
        subj.subjectTabSwitch();
        while (!subj.isShdPresented()) {
            subj.peekShd(shd);
            Save.saveThis(driver);
            subj.subjectTabSwitch();
        }
    }

    @Test(dependsOnMethods = "settingSubjectInfoTwo", description = "Добавление нарушений")
    public void setViolationTwo() throws InterruptedException {
        viol.violTabSwitch();
        viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
        viol.violTabSwitch();
        viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");
    }

    @Test(dependsOnMethods = "setViolationTwo", description = "Заполнение ЗПО")
    public void populateUbsZpo() {
        driver.get(ubsUrl);
        ubs.addActualization(2);
        ubs.zpo(false);
        ubs.setBuildingKadastr(Generator.fakeKadastr());
        ubs.uploadFile(Catalog.docs.category.DGI_PACK, Catalog.docs.path.DGI_PACK);
        System.out.println(ubs.getUrlTail());
    }
}
