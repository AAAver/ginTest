//package ubs;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.Assert;
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.Test;
//
//import common.Login;
//import common.Save;
//import misc.Compare;
//import misc.Props;
//import unauthBuilding.UbsScratch;
//import unauthBuilding.UnauthBldList;
//
//public class UbsFlk {
//
//	@Test
//	public void rawUbsFlk() throws InterruptedException {
//		WebDriver driver = Props.initChromeDriver();
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//		List<String> firstErrors = Arrays.asList(
//				"Введите район", "Введите адрес либо адресный ориентир ЗУ", "Введите адрес объекта", "Введите тип объекта", "Укажите Площадь/длина", "Укажите Площадь объекта");
//
//		List<String> secondErrors = Arrays.asList("Введите материал стен", "Введите конструктив");
//
//
//
//		// Авторизация
//		driver.get(Props.UBS_LIST_URL);
//		Login l = new Login(driver);
//		l.loginAs("ГоликовАА", Props.ULT_PASSWORD);
//
//		// Добавляем ОСС по 819
//		UnauthBldList ubsList = new UnauthBldList(driver);
//		ubsList.addUnauthBld();
//		Thread.sleep(1000);
//		Save.saveThis(driver);
//
//		UbsScratch scr = new UbsScratch(driver);
//		List<String> errors = scr.validationErrors();
//		Assert.assertTrue(Compare.equalLists(firstErrors, errors));
//
//		scr.setDistrictRandom();
//		scr.fieldAddr().sendKeys("dsfsdf");
//		scr.fieldAddrZU().sendKeys("sdfsdf");
//		scr.setObjectTypeRandom();
//		scr.setObjectSqr("500");
//		scr.setUbsSqr("400");
//
//		Save.saveThis(driver);
//
//		errors = scr.validationErrors();
//		Assert.assertTrue(Compare.equalLists(secondErrors, errors));
//	}
//}
