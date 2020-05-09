package tests.ubs;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pagerepository.common.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;

import pagerepository.inspection.InspectionMainTab;
import pagerepository.inspection.InspectionObjectTab;
import pagerepository.inspection.InspectionSubjectTab;
import pagerepository.inspection.InspectionViolationTab;
import tests.utils.BaseTest;
import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;
import pagerepository.ubs.UbsScratch;
import pagerepository.ubs.UnauthBldList;

@Listeners(tests.utils.Listeners.class)
public class CreateUBS819Pril3With2Violations extends BaseTest {

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
    private String inspResult1 = Catalog.inspection.result.PRIL_3;
    private String docCategory1 = Catalog.docs.category.ACT_PRIL_3;
    private String docPath1 = (new File(Catalog.docs.path.ACT_PRIL_3)).getAbsolutePath();

    @BeforeClass
    void initDriver(){
        setUpDriver();
        setUpExtentReport("Создание ОСС по прил.3 с проверкой в которой 2 нарушения");
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

    @Test(priority = 1, description = "Новый тест")
    public void addUbs819pp3() throws InterruptedException {

        driver.get(ubsListUrl);
        LoginPage l = new LoginPage(driver);
        l.loginAs(ultLogin, ultPassword);

        // Добавляем ОСС по 819
        UnauthBldList ubsList = new UnauthBldList(driver);
        ubsList.addUnauthBld();

        UbsScratch ubs = new UbsScratch(driver);
        ubs.generateUBS(address, ao, ubsResolution);
        String ubsUrl = driver.getCurrentUrl();
        String objSquare = ubs.getObjSquare();

        //==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
        driver.get(disposalUrlZu1);
        DisposalPage d = new DisposalPage(driver);
        d.addInspection();
        InspectionMainTab main = new InspectionMainTab(driver);
        main.setInspectionTheme(inspTheme1);
        main.setInspectionResult(inspResult1);
        main.populateCommonInformation();
        main.connectUbs(address);
        Upload.file(driver, docCategory1, docPath1);
        InspectionObjectTab obj = new InspectionObjectTab(driver);
        obj.objectTabSwitch();
        obj.setAddress(address);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        InspectionSubjectTab subj = new InspectionSubjectTab(driver);
        subj.subjectTabSwitch();
        subj.peekShd(shd);
        Save.saveThis(driver);
        InspectionViolationTab viol = new InspectionViolationTab(driver);
        viol.violTabSwitch();
        viol.addViolation("Самовольное занятие земельного участка под строительство нежилых объектов");
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addProtocol("Самовольное занятие земельного участка под строительство нежилых объектов");
        viol.violTabSwitch();
        viol.addPrescription("Самовольное занятие земельного участка под строительство нежилых объектов");

        viol.violTabSwitch();
        viol.addViolation("Воспрепятствование проведения проверки должностному лицу");
        Save.saveThis(driver);
        viol.violTabSwitch();
        viol.addProtocol("Воспрепятствование проведения проверки должностному лицу");
        viol.violTabSwitch();
        viol.addPrescription("Воспрепятствование проведения проверки должностному лицу");

        driver.get(ubsUrl);

        ubs.zpo(true);
        ubs.setBuildingKadastr(Generator.fakeKadastr());
        Save.saveThis(driver);
        ubs.verify();
        log.info(ubs.getUrlTail() + " Ubs ID");

    }
}
