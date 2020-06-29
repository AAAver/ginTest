package tests.runnertest;

import miscelaneous.Api;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.main.LoginPage;
import pagerepository.main.MainPage;
import pagerepository.utilities.Save;
import pagerepository.utilities.Upload;
import pagerepository.inspections.*;
import pagerepository.legalcase.DgiLegalCase;
import pagerepository.ubs.UnauthorizedBuilding;
import pagerepository.ubs.UnauthorizedBuildingList;
import miscelaneous.Catalog;
import miscelaneous.Generator;
import tests.utils.BaseTest;

import java.io.File;
import java.io.IOException;

@Listeners(tests.utils.Listeners.class)
public class BakeUbsPril2 extends BaseTest {
    //==== РАСПОЛОЖЕНИЕ ====//
    private String ao = Catalog.area.ao.CAO;
    //==== ОСС РАССМАТРИВАЕТСЯ В РАМКАХ ====//
    private String ubsResolution = Catalog.ubs.resolution.PP_819;
    //==== СХД ====//
    private String shd = Catalog.shd.DEFAULT_SHD;
    //==== ПЕРВАЯ ПРОВЕРКА (819-ПП) ====//
    private String inspTheme1 = Catalog.inspection.theme.UBS_819_IDENT;
    private String inspResult1 = Catalog.inspection.result.PRIL_2;
    private String docCategory1 = Catalog.docs.category.ACT_PRIL_2;
    private String docPath1 = (new File(Catalog.docs.path.ACT_PRIL_2)).getAbsolutePath();
    private String ubsState = Catalog.ubs.state.INCLUDED;
    private String[] dgiDocPack = Catalog.docs.category.DGI_PACK;
    private String[] dgiDocPackPath = Catalog.docs.path.DGI_PACK;

    @BeforeClass
    void initDriver() {
        setUpDriver();
        setUpExtentReport("Bake Unauth Building Pril.2");
    }

    private LoginPage lp;
    private MainPage mp;
    private DisposalsList dlp;
    private Disposal dp;
    private UnauthorizedBuildingList ubsList;
    private UnauthorizedBuilding ubs;
    private InspectionMainTab main;
    private InspectionObjectTab obj;
    private InspectionSubjectTab subj;
    private InspectionViolationTab viol;
    private DgiLegalCase dlc;

    private String ubsUrl;
    private String objSquare;


    @Test(description = "Page initialization")
    void initPages() throws IOException, InterruptedException {
        lp = new LoginPage(driver);
        mp = new MainPage(driver);
        dlp = new DisposalsList(driver);
        dp = new Disposal(driver);
        ubsList = new UnauthorizedBuildingList(driver);
        ubs = new UnauthorizedBuilding(driver);
        main = new InspectionMainTab(driver);
        obj = new InspectionObjectTab(driver);
        subj = new InspectionSubjectTab(driver);
        viol = new InspectionViolationTab(driver);
        dlc = new DgiLegalCase(driver);
        Api.featureControllerDisable("SetlUnauthBldPolygon");
    }


    @Test(dependsOnMethods = "initPages", description = "Добавляем ОСС")
    void addUbs819pp3() throws InterruptedException {
        lp.loginAs(ultLogin);
        mp.toUbsList();
        ubsList.addUnauthBld();
        ubs.generateUBS(fakeAddress, ao, ubsResolution);
        ubsUrl = driver.getCurrentUrl();
        objSquare = ubs.getObjSquare();
    }

    @Test(dependsOnMethods = "addUbs819pp3", description = "Добавляем проверку")
    void addInspection() throws InterruptedException {
        driver.get(baseUrl);
        mp.toDisposals();
        dlp.toInspectionZuDisposal();
        dp.addInspection();
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
        while (!subj.isShdPresented()) {
            subj.peekShd(shd);
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


    @Test(dependsOnMethods = "addInspection", description = "Корректировка и верификация ОСС")
    void correctUBS() throws InterruptedException {
        driver.get(ubsUrl);
        ubs.isManualCorrection(true);
        ubs.setUbsState(ubsState);
        ubs.zpo(false);
        ubs.setBuildingKadastr(Generator.fakeKadastr());
        ubs.courtDecisionCorrection();

        Save.saveThis(driver);
        ubs.addActualization(2);
        ubs.uploadFile(dgiDocPack, dgiDocPackPath);

        Save.saveThis(driver);
    }

    @Test(dependsOnMethods = "correctUBS", description = "Ищем судебное дело ДГИ")
    void caseDgiSearch() throws InterruptedException {
        mp.toMainPage();
        mp.toDgiLegalCase();
        dlc.findLegalCaseForUbs(fakeAddress);
        dlc.setLegalForceDate();
        dlc.save();
        driver.get(ubsUrl);

        log.info(ubs.getUrlTail() + " Ubs ID. Выпечка завершена");
    }
}