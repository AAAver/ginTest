package tests.ubs;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pagerepository.main.MainPage;
import pagerepository.inspections.*;
import pagerepository.main.LoginPage;
import pagerepository.utilities.Save;
import pagerepository.utilities.Upload;

import tests.utils.BaseTest;
import miscelaneous.Catalog;
import miscelaneous.Generator;
import pagerepository.ubs.UnauthorizedBuilding;
import pagerepository.ubs.UnauthorizedBuildingList;

@Listeners(tests.utils.Listeners.class)
public class CreateUBS819Pril3With2Violations extends BaseTest {

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

    @BeforeClass
    void initDriver(){
        setUpDriver();
        setUpExtentReport("Создание ОСС по прил.3 с проверкой в которой 2 нарушения");
    }

//    @AfterClass
//    void tearDown(){
//        driver.quit();
//    }

    @Test(priority = 1, description = "Новый тест")
    public void addUbs819pp3() throws InterruptedException {


        LoginPage l = new LoginPage(driver);
        l.loginAs(ultLogin);
        MainPage mp = new MainPage(driver);
        mp.toUbsList();

        // Добавляем ОСС по 819
        UnauthorizedBuildingList ubsList = new UnauthorizedBuildingList(driver);
        ubsList.addUnauthBld();

        UnauthorizedBuilding ubs = new UnauthorizedBuilding(driver);
        ubs.generateUBS(fakeAddress, ao, ubsResolution);
        String ubsUrl = driver.getCurrentUrl();
        String objSquare = ubs.getObjSquare();

        //==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
        mp.toMainPage();
        mp.toDisposals();
        DisposalsList dlp = new DisposalsList(driver);
        dlp.toInspectionZuDisposal();
        Disposal d = new Disposal(driver);
        d.addInspection();
        InspectionMainTab main = new InspectionMainTab(driver);
        main.setInspectionTheme(inspTheme1);
        main.setInspectionResult(inspResult1);
        main.populateCommonInformation();
        main.connectUbs(fakeAddress);
        Upload.file(driver, docCategory1, docPath1);
        InspectionObjectTab obj = new InspectionObjectTab(driver);
        obj.objectTabSwitch();
        obj.setAddress(fakeAddress);
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

        log.info(ubs.getUrlTail() + " Ubs ID");

    }
}
