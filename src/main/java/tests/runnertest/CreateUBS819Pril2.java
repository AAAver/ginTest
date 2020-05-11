package tests.runnertest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.common.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;
import pagerepository.inspection.*;
import pagerepository.ubs.UbsScratch;
import pagerepository.ubs.UnauthBldList;
import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import tests.utils.BaseTest;

import java.io.File;

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

    @Test(dependsOnMethods = "initialization", description = "Авторизация и создание карточки ОСС")
    public void authorization() throws InterruptedException {
        driver.get(ubsListUrl);
        l.loginAs(ultLogin, ultPassword);
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
        driver.get(disposalUrlZu1);
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
        driver.get(disposalUrlZu2);
        d.addInspection();
        main.populateCommonInformation();
        main.setInspectionTheme(inspTheme2);
        main.setInspectionResult(inspResult2);
    }

    @Test(dependsOnMethods = "setUpInspectionThemeAndResultTwo", description = "Связка с ОСС")
    public void connectUbsTwo() {
        main.connectUbs(fakeAddress);
        Upload.file(driver, docCategory1, docPath1);
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
        ubs.zpo(true);
        ubs.setBuildingKadastr(Generator.fakeKadastr());
        System.out.println(ubs.getUrlTail());
    }
}
