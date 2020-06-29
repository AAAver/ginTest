package tests.runnertest;

import miscelaneous.Catalog;
import miscelaneous.Generator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pagerepository.inspections.Disposal;
import pagerepository.main.DocumentSigning;
import pagerepository.main.LoginPage;
import pagerepository.main.MainPage;
import pagerepository.plan.InspectionTask;
import pagerepository.plan.InspectionTaskAddress;
import pagerepository.plan.InspectionTaskList;
import pagerepository.plan.RaidPlanTask;
import tests.utils.BaseTest;

import java.security.Signature;
import java.util.List;

@Listeners(tests.utils.Listeners.class)
public class TaskZU extends BaseTest {


    private String companyName = "Альянс Девелопмент";
    private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
    private String inspTheme = Catalog.inspection.theme.TERRITORY_MONITORING;
    private String inspResult = Catalog.inspection.result.PRIL_2;
    private String departmentAo = Catalog.area.ao.CAO;


    @BeforeClass
    void setup(){
        setUpDriver();
        setUpExtentReport("Проверка ЗУ");
    }

    LoginPage loginPage;
    MainPage mainPage;
    Disposal disposal;
    InspectionTaskList inspectionTaskList;
    InspectionTask inspectionTask;
    InspectionTaskAddress inspectionTaskAddress;
    RaidPlanTask raid;
    private DocumentSigning documentSigning;

    @Test
    void createTaskZu() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginAs(ultLogin);

        mainPage = new MainPage(driver);
        mainPage.toInspectionTaskList();

        inspectionTaskList = new InspectionTaskList(driver);
        inspectionTaskList.addTaskZu();

        inspectionTask = new InspectionTask(driver);
        inspectionTask.setTaskSource("Поручение");
        inspectionTask.setInspectionTheme(inspTheme);
        inspectionTask.setInspectionDepartment(departmentAo);
        inspectionTask.setEndDate(Generator.getCurrentDatePlus5());
        inspectionTask.save();

        inspectionTaskAddress = inspectionTask.addAddress();
        inspectionTaskAddress.create(fakeAddress, departmentAo, 2);

        String itaUrl = driver.getCurrentUrl();
        List<String> inspectors = inspectionTaskAddress.getInspectorsList();
        String inspName = inspectors.get(Generator.getRandomUpTo(inspectors.size()));
        String responsibleInspector = trimLoginName(inspName);

        mainPage.toMainPage();
        mainPage.toInspectionTaskList();
        inspectionTaskList.toRaidList();

        raid = new RaidPlanTask(driver);
        raid.createRaidTask("Горбунов");
        String raidNumber = raid.getRaidNumber();
        raid.toInspectionTaskList();

        inspectionTaskList.addTaskToRaid(fakeAddress, raidNumber);
        inspectionTaskList.toRaidList();
        raid.open(raidNumber);
        raid.sendForSign();

        loginPage.logout();
        loginPage.loginAs("ГорбуновАЕ");
        mainPage.toDocumentsForSigning();

        documentSigning = new DocumentSigning(driver);
        documentSigning.sign(raidNumber);

        loginPage.logout();
        loginPage.loginAs(ultLogin);
        mainPage.toInspectionTaskList();
        inspectionTaskList.openInspectionTaskAddress(fakeAddress);
        disposal = inspectionTaskAddress.addDisposal();
        disposal.setIssueDate();
        disposal.selectReason("Инициативно");
        disposal.save();
        disposal.addInspection();
    }
}
