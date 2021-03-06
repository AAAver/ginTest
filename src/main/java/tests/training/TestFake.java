package tests.training;


import pagerepository.common.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagerepository.inspection.*;

import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;
import tests.utils.BaseTest;

import java.io.File;


public class TestFake extends BaseTest {

    @BeforeClass
    void setDriver() {
        setUpDriver();
        setUpExtentReport("Новый тест");
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }


    private final String baseUrl = Props.BASE_URL;
    private final String disposalUrl = Props.DISPOSAL_URL_NF;
    private final String ultLogin = Props.ULT_LOGIN;
    private final String ultPassword = Props.ULT_PASSWORD;

    private final String address = fake.address().streetAddress();
    private final String companyName = "Альянс Девелопмент";
    private final String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
    //==== ТЕМАТИКА/РЕЗУЛЬТАТ/ДОГОВОР ====//
    private final String inspTheme = Catalog.inspection.theme.ONF;
    private final String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
    private final String rightType = Catalog.useRight.RENT;
    private final String docCategory = Catalog.docs.category.RAPORT;
    private final String docPath = (new File(Catalog.docs.path.RAPORT)).getAbsolutePath();


    @Test(priority = 1, description = "Один из методов")
    public void missAct() throws InterruptedException {
        driver.get(baseUrl);

        LoginPage l = new LoginPage(driver);
        l.loginAs(ultLogin, ultPassword);

        driver.get(disposalUrl);
        DisposalPage d = new DisposalPage(driver);
        d.addInspection();

        InspectionMainTab main = new InspectionMainTab(driver);
        InspectionObjectTab obj = new InspectionObjectTab(driver);
        InspectionSubjectTab subj = new InspectionSubjectTab(driver);
        InspectionActNF act = new InspectionActNF(driver);
        InspectionViolationTab viol = new InspectionViolationTab(driver);

        //==== ТЕМАТИКА/РЕЗУЛЬТАТ ====//
        main.setInspectionTheme(inspTheme);
        main.setInspectionResult(inspResult);

        //==== ОБЩАЯ ИНФА ====//
        main.populateCommonInformation();

        act.populateCommonInformation();

        //==== АКТ НФ (НАРУШЕНИЯ) ====//
        act.isActualUsage(false);
        act.isReplanned(true);
        act.isPremicyUsed(false);
        act.isThirdPartyUsesBuilding(false);
        act.previousViolations(true);
        act.previousReplanViolation(true, false);
        act.previousThirdPartyViolation(false, false);
        act.previousNonPurposeUsage(true, true);
        //==================//
        Upload.file(driver, docCategory, docPath);
        //==== ДАННЫЕ ОБЪЕКТА ====//
        obj.objectTabSwitch();
        obj.setAddress(address);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        //==== ДАННЫЕ СУБЪЕКТА ====//
        subj.subjectTabSwitch();
        subj.peekShd(companyName);
        Save.saveThis(driver);
        subj.subjectTabSwitch();

        //==== ИНФОРМАЦИЯ О ЗДАНИИ ====//
        obj.objectTabSwitch();
        obj.createEgrnTable();
        obj.createRoomInfoTable();
        Save.saveThis(driver);
        //==== ДОГОВОР НА ПОМЕЩЕНИЕ ====//
        obj.objectTabSwitch();
        obj.createContractTable(rightType);
        //==== НАРУШЕНИЯ ====//
        viol.violTabSwitch();
        viol.addWarning();
        //==== ВЕРИФИКАЦИЯ ====//
        main.verify();

        //==== ЗАПИСЫВАЕМ ИНФУ ====//
        String inspId = main.getUrlTail();
        Props.setProperty("inspIdMissAct", inspId);

    }


}








