package tests.training;


import org.openqa.selenium.By;
import pagerepository.main.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pagerepository.dismantle.Dismantle;
import miscelaneous.Catalog;
import miscelaneous.Generator;
import miscelaneous.Props;
import tests.utils.BaseTest;

import java.io.File;


public class TestFake extends BaseTest {

    @BeforeClass
    void setDriver() {
        setUpDriver();
        setUpExtentReport("Новый тест");
    }

//    @AfterClass
//    void tearDown() {
//        driver.quit();
//    }


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


    @Test(priority = 1, description = "Один из методов")
    public void missAct() throws InterruptedException {
        driver.get("http://192.168.4.117/626/UnauthBuildingDismantle/Edit/2011#");
        LoginPage l = new LoginPage(driver);
        l.loginAs("3");
        Dismantle dis = new Dismantle(driver);
        var s = dis.getActualValueFromDrop(By.xpath("//*[@id='s2id_ContractorId']"));
        System.out.println("result is: " + s);

    }


}








