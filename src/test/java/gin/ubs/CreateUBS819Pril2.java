package gin.ubs;

import java.io.File;
import java.util.Locale;

import inspection.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import common.Upload;

import utils.BaseTest;
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;
import ubs.UbsScratch;
import ubs.UnauthBldList;
import utils.ExtentTestManager;

@Listeners(utils.Listeners.class)
public class CreateUBS819Pril2 extends BaseTest {

    private String ubsListUrl = Props.UBS_LIST_URL;
    private String ultLogin = Props.ULT_LOGIN;
    private String ultPassword = Props.ULT_PASSWORD;
    private String disposalUrlZu1 = Props.DISPOSAL_URL_ZU_1;
    private String disposalUrlZu2 = Props.DISPOSAL_URL_ZU_2;
    //==== РАСПОЛОЖЕНИЕ ====//
    private String address = fake.address().streetAddress();
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
        ubs.generateUBS(address, ao, ubsResolution);
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
        main.connectUbs(address);
        Upload.file(driver, docCategory1, docPath1);
    }

    @Test(dependsOnMethods = "connectUbsOne", description = "Заполнение вкладки объект")
    public void settingObjectInfoOne() throws InterruptedException {
        obj.objectTabSwitch();
        obj.setAddress(address);
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
        main.connectUbs(address);
        Upload.file(driver, docCategory1, docPath1);
    }

    @Test(dependsOnMethods = "connectUbsTwo", description = "Заполнение вкладки объект")
    public void settingObjectInfoTwo() throws InterruptedException {
        obj.objectTabSwitch();
        obj.setAddress(address);
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
