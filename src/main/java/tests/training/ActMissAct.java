package tests.training;

import com.relevantcodes.extentreports.LogStatus;
import pagerepository.inspection.DisposalPage;
import pagerepository.common.LoginPage;
import pagerepository.common.Save;
import pagerepository.common.Upload;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.inspection.*;

import pagerepository.utilities.Catalog;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;
import tests.utils.BaseTest;
import tests.utils.ExtentTestManager;


import java.io.File;

@Listeners(tests.utils.Listeners.class)
public class ActMissAct extends BaseTest {

    private final String baseUrl = Props.BASE_URL;
    private final String disposalUrl = Props.DISPOSAL_URL_NF;
    private final String ultLogin = Props.ULT_LOGIN;
    private final String ultPassword = Props.DEFAULT_PASSWORD;

    private final String address = fake.address().streetAddress();
    private final String companyName = "Альянс Девелопмент";
    private final String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
    //==== ТЕМАТИКА/РЕЗУЛЬТАТ/ДОГОВОР ====//
    private final String inspTheme = Catalog.inspection.theme.ONF;
    private final String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
    private final String rightType = Catalog.useRight.RENT;
    private final String docCategory = Catalog.docs.category.RAPORT;
    private final String docPath = (new File(Catalog.docs.path.RAPORT)).getAbsolutePath();
    String inspId;

    @BeforeClass
    void setDriver() {
        setUpDriver();
        ExtentTestManager.startTest(getClass().getName(), "Тестовый сценарий");
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

    @Test(description = "Инициализация страниц(сервисный шаг)")
    public void initialization() {
        l = new LoginPage(driver);
        d = new DisposalPage(driver);
        insp = new InspectionPage(driver);
        main = new InspectionMainTab(driver);
        obj = new InspectionObjectTab(driver);
        act = new InspectionActNF(driver);
        viol = new InspectionViolationTab(driver);
        subj = new InspectionSubjectTab(driver);
    }

    @Test(dependsOnMethods = "initialization", description = "Авторизация и создание проверки")
    public void authorization() {
        driver.get(baseUrl);
        l.loginAs(ultLogin, ultPassword);
        driver.get(disposalUrl);
        d.addInspection();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор тематики и результата")
    public void settingThemeAndResult() throws NoSuchFieldException, IllegalAccessException {
        main.setInspectionTheme(inspTheme);
        main.setInspectionResult(inspResult);
        main.populateCommonInformation();
        act.populateCommonInformation();
        main.scrollIntoViewBy(insp.getByByName("inspectionTheme"));
    }

    @Test(dependsOnMethods = "settingThemeAndResult", description = "Установка нарушений акта НФ")
    public void setUpViolations() {
        act.previousViolations(false);
        act.isReplanned(true);
        act.isPremicyUsed(false);
        act.isThirdPartyUsesBuilding(false);
        act.isActualUsage(false);
    }

    @Test(dependsOnMethods = "setUpViolations", description = "Загрузка документа(не Акт НФ)")
    public void uploadDocuments() {
        Upload.file(driver, docCategory, docPath);
        act.scrollToBottom();
    }

    @Test(dependsOnMethods = "uploadDocuments", description = "Заполнение данных на вкладке объект")
    public void setObjectInformation() throws InterruptedException {
        obj.objectTabSwitch();
        obj.setAddress(address);
        obj.setObjSquare(objSquare);
        obj.pickKadNumExist(true);
        Save.saveThis(driver);
        inspId = insp.getUrlTail();
        ExtentTestManager.getTest().log(LogStatus.INFO, "Id проверки: " + inspId);
        obj.objectTabSwitch();
    }

    @Test(dependsOnMethods = "setObjectInformation", description = "Прикрепление СХД")
    public void setShd() throws InterruptedException {
        subj.subjectTabSwitch();
        subj.peekShd(companyName);
        Save.saveThis(driver);
        subj.subjectTabSwitch();
    }

    @Test(dependsOnMethods = "setShd", description = "Информация о здании")
    void setEgrnInfo() {
        obj.objectTabSwitch();
        obj.createRoomInfoTable();
        obj.createEgrnTable();
    }

    @Test(dependsOnMethods = "setEgrnInfo", description = "Прикрепление договора")
    void setBuildingContract() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        Save.saveThis(driver);
        obj.objectTabSwitch();
        obj.createContractTable(rightType);
        obj.objectTabSwitch();
        obj.scrollIntoViewBy(obj.getByByName("BtnContract"));
    }

    @Test(dependsOnMethods = "setBuildingContract", description = "Добавление предупреждения")
    void addWarningCard() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        viol.violTabSwitch();
        viol.addWarning();
        viol.violTabSwitch();
        viol.scrollIntoViewBy(viol.getByByName("warningBtn"));
    }

    @Test(dependsOnMethods = "addWarningCard", description = "Верификация карточки")
    void verification() throws InterruptedException {
        insp.verify();
        Props.setProperty("inspIdMissAct", inspId);
    }


}
