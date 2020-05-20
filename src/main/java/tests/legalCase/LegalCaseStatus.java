package tests.legalCase;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pagerepository.common.LoginPage;
import pagerepository.legalcase.LegalCase;
import pagerepository.legalcase.LegalCase;
import pagerepository.utilities.Catalog;

import pagerepository.utilities.Props;
import tests.utils.BaseTest;



public class LegalCaseStatus extends BaseTest {

    SoftAssert softAssert;

    @BeforeTest
    void setDriver(){
        setUpExtentReport("Проверка переходов состояний карточки СД по Взысканию");
        setUpDriver();
    }
    @BeforeMethod
    void setSoftAssert(){
        softAssert = new SoftAssert();
    }

    LoginPage l;
    LegalCase lc;

    @Test(description = "Инициализация страниц")
    public void initialization(){
        l = new LoginPage(driver);
        lc = new LegalCase(driver);
    }

    @Test(dependsOnMethods = "initialization", description = "Авторизация")
    public void login(){
        driver.get(Props.getProperty("legalCaseUrl"));
        l.loginAs("ХайбуллинРР");
    }

    @Test(dependsOnMethods = "login", description = "Первичная валидация")
    public void starterValidation(){
        softAssert.assertTrue(lc.isStatusEquals(Catalog.legalCase.status.PREPARE_CASE));
//        softAssert.assertTrue(lc.isAppealClaimsEquals(""));
    }

    @Test(dependsOnMethods = "starterValidation", description = "Отправка искового заявления")
    public void firstStep(){

    }
}
