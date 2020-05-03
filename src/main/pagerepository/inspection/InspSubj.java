package inspection;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import misc.Generator;

public class InspSubj {

	WebDriver driver;
	WebDriverWait wait;	
	public InspSubj(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}
	
	Faker fake = new Faker(new Locale("ru"));

	String hPostTitle = "Руководитель высшей категории";
	String hLastName = fake.name().lastName();
	String hFirstName = fake.name().firstName();
	String addrType = "Местонахождения";
	String emplLastDefault = fake.name().lastName();
	String emplFirstDefault = fake.name().firstName();

	// Все(ну почти все) справочники в дропдаунах
	By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");

	// Вкладка [Субъект]
	By addShdBtn = By.id("shd-catalog-btn");

	// Таблица с СХД
	By shdTable = By.xpath("//div[@id='shd-calatogs-search-result']/table");
	By shdNames = By.xpath("//*[@id='shd-calatogs-search-result']/table/tbody/tr/td[4]");
	By addButtons = By.xpath("//*[@id='shd-calatogs-search-result']/table/tbody/tr/td[8] //a");

	By accept = By.xpath(
			"//*[@aria-describedby='modalDialog6'] //div[@class='ui-dialog-buttonpane ui-widget-content ui-helper-clearfix'] //button[@class='btn btn-xs btn-success']");
	
	By searchField = By.id("FullName");
	By searchBtn = By.xpath("//div[@id='searchFormDiv'] //button[text()='Поиск']");

	// Вкладки СХД
	By mainTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Основные')]");
	By addressTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Адреса')]");
	By employeeTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Данные сотрудника')]");
	By bankTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Банковские')]");

	// Вкладка [Основные]
	By headPostTitle = By.id("HeadPostTitle");
	By headLastName = By.id("HeadLastName");
	By headFirstName = By.id("HeadFirstName");

	// Вкладка [Адреса]
	By shdAddressTypes = By.xpath("//*[@id='address-table'] //td[8]");
	By shdAddressChkbox = By.xpath("//*[@id='address-table'] //td[9] //input[@type='checkbox']");

	// Вкладка [Данные сотрудника]
	By emplLastName = By.id("ShdEmployeeM_Surname");
	By emplFirstName = By.id("ShdEmployeeM_FirstName");
	By emplPosition = By.id("s2id_ShdEmployeeM_PostCtId");
	
	By snapshot = By.xpath("//*[@id='shd-snapshot-info-container'] //label[@for='ShortName']");

	public void peekShd(String companyName) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addShdBtn));		
		Thread.sleep(1000);
		driver.findElement(addShdBtn).click();		
		driver.findElement(searchField).sendKeys(companyName);
		driver.findElement(searchBtn).click();	
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(shdNames));
		List<WebElement> shdNamesList = driver.findElements(shdNames);
		for (int i = 0; i < shdNamesList.size(); i++) {
			String shdName = shdNamesList.get(i).getText();
			if (shdName.contains(companyName)) {
				driver.findElements(addButtons).get(i).click();				
				break;
			}
		}
		driver.findElement(headPostTitle).clear();
		driver.findElement(headPostTitle).sendKeys(hPostTitle);
		driver.findElement(headLastName).clear();
		driver.findElement(headLastName).sendKeys(hLastName);
		driver.findElement(headFirstName).clear();
		driver.findElement(headFirstName).sendKeys(hFirstName);
		
		driver.findElement(addressTab).click();
		List<WebElement> types = driver.findElements(shdAddressTypes);
		for (int i = 0; i < types.size(); i++) {
			String type = types.get(i).getText();
			if (type.contains(addrType)) {
				WebElement chkbox = driver.findElements(shdAddressChkbox).get(i);
				if (!chkbox.isSelected()) {
					chkbox.click();
				}
			}
		}

		driver.findElement(employeeTab).click();
		driver.findElement(emplLastName).sendKeys(emplLastDefault);
		driver.findElement(emplFirstName).sendKeys(emplFirstDefault);
		driver.findElement(emplPosition).click();
		List<WebElement> positions = driver.findElements(select2drop);		
		positions.get(Generator.getRandomUpTo(positions.size())).click();
		driver.findElement(accept).click();
	}
	
	public boolean isShdPresented() {
		List<WebElement> sh = driver.findElements(snapshot);
		if(sh.size() == 0) {
			return false;
		}
		else {
		return true;
		}
	}

}
