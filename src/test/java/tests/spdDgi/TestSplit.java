package tests.spdDgi;

import com.github.javafaker.Faker;
import common.DisposalPage;
import common.LoginPage;
import common.Save;
import common.Upload;
import inspection.*;
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;
import org.testng.annotations.*;
import tests.BaseTest;
import utils.ExtentTestManager;

import java.io.File;
import java.util.Locale;

@Listeners(listeners.Listeners.class)
public class TestSplit extends BaseTest {

    private Faker fake = new Faker(new Locale("ru"));

    private String baseUrl = Props.BASE_URL;
    private String disposalUrl = Props.DISPOSAL_URL_NF;
    private String ultLogin = Props.ULT_LOGIN;
    private String ultPassword = Props.ULT_PASSWORD;

    private String address = fake.address().streetAddress();
    private String companyName = "Альянс Девелопмент";
    private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
    //==== ТЕМАТИКА/РЕЗУЛЬТАТ/ДОГОВОР ====//
    private String inspTheme = Catalog.inspection.theme.ONF;
    private String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
    private String rightType = Catalog.useRight.RENT;
    private String docCategory = Catalog.docs.category.RAPORT;
    private String docPath = (new File(Catalog.docs.path.RAPORT)).getAbsolutePath();

    @BeforeClass
    void setDriver() {
        setUpDriver();
        ExtentTestManager.startTest(getClass().getName(), "Создание проверки НФ без прикрепенного документа категории Акт осмотра НФ. Отправка в СПД не происходит");

    }


    @AfterClass
    void tearDown() {
        driver.quit();
    }

    InspectionPage i;
    InspectionActNF act;
    InspectionObjectTab obj;
    InspSubj subj;
    InspViol viol;
    LoginPage l;
    DisposalPage d;

    @Test(description = "Инициализация страниц")
    public void initializePages() {
       l = new LoginPage(driver);
       d = new DisposalPage(driver);
       i = new InspectionPage(driver);
       act = new InspectionActNF(driver);
       obj = new InspectionObjectTab(driver);
       subj = new InspSubj(driver);
       viol = new InspViol(driver);
    }

    @Test (dependsOnMethods = "initializePages", description = "Логин")
    public void loginAndAddInspection() {

        driver.get(baseUrl);
        l.loginAs(ultLogin, ultPassword);

        driver.get(disposalUrl);
        d.addInspection();
    }

    @Test (dependsOnMethods = "loginAndAddInspection", description = "Указание тематики и результата")
    public void setThemeAndResult() throws InterruptedException {
        i.setInspectionTheme(inspTheme);
        i.setInspectionResult(inspResult);
    }

    @Test (dependsOnMethods = "setThemeAndResult", description = "Заполнение незначительной информации")
    public void fillCommonInfo() throws InterruptedException {
        i.populateCommonInformation();
        i.isRepresentativeRefusedToSign(false);
        act.populateCommonInformation();
    }

    @Test (dependsOnMethods = "fillCommonInfo", description = "Указание нарушений в Акте НФ")
    public void setViolations() {
        act.isActualUsage(false);
        act.isReplanned(true);
        act.isPremicyUsed(false);
        act.isThirdParty(false);
        act.previousViolations(false);
    }

    @Test (dependsOnMethods = "setViolations", description = "Указание информации об объекте")
    public void addObjectInfo() throws InterruptedException {
        Upload.file(driver, docCategory, docPath);
        //==== ДАННЫЕ ОБЪЕКТА ====//
        i.objectTabSwitch();
        obj.setAddress(address);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
    }

    //==== ДАННЫЕ СУБЪЕКТА ====//
    @Test (dependsOnMethods = "addObjectInfo", description = "Прикрепление субъекта")
    public void addSubjectInfo() throws InterruptedException {
        i.subjectTabSwitch();
        while (!subj.isShdPresented()) {
            subj.peekShd(companyName);
            Save.saveThis(driver);
            i.subjectTabSwitch();
        }
    }

    @Test (dependsOnMethods = "addSubjectInfo")
    public void fillRestInfo() throws InterruptedException {
        //==== ИНФОРМАЦИЯ О ЗДАНИИ ====//
        i.objectTabSwitch();
        obj.addEgrnTable();
        obj.addRoomTable();
        Save.saveThis(driver);
        //==== ДОГОВОР НА ПОМЕЩЕНИЕ ====//
        i.objectTabSwitch();
        obj.addContractTable(rightType);
        //==== НАРУШЕНИЯ ====//
        i.violTabSwitch();
        viol.addWarning();
    }

    @Test (dependsOnMethods = "fillRestInfo")
    public void verifyAndSaveInfo() throws InterruptedException {
        //==== ВЕРИФИКАЦИЯ ====//
        i.verify();
        //==== ЗАПИСЫВАЕМ ИНФУ ====//
        String inspId = i.getInspectionId();
        Props.setProperty("inspIdMissAct", inspId);
    }
}

