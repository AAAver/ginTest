package inspection;

import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.javafaker.Faker;

import misc.Generator;

public class ContractBld {

	WebDriver driver;

	public ContractBld(WebDriver driver) {
		this.driver = driver;
	}

	Faker fake = new Faker(new Locale("ru"));
	// Кнопка добавить договор в проверке
	By addContractBtn = By.xpath("//div[@name='nf-info-container'] //a[@title='Добавить договор']");

	// Все справочники в дропдаунах
	By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");

	// Кнопка Проверка
	By toInspBtn = By.xpath("//a[@title='Проверка']");

	// Информация о договорах
	By bldAddress = By.id("s2id_BtiAddressStr");
	By btiAddrText = By.xpath("//*[@id='s2id_BtiAddressStr'] //span");
	By autogenInput = By.id("s2id_autogen5_search");
	By contractNum = By.id("Number");
	By contractDate = By.id("ContractDate");
	By subjectType = By.id("s2id_SubjectTypeCtId");
	By rightType = By.id("s2id_RightTypeCtId");
	By contractValidity = By.id("s2id_ContractValidityCtId");
	By startDate = By.id("StartDate");
	By endDate = By.id("EndDate");
	By isPerpetual = By.id("s2id_IsPerpetual");
	By subjectName = By.id("SubjectName");
	By legalForm = By.xpath("//input[@id='LegalForm']");
	By subjectInn = By.id("SubjectInn");
	By square = By.id("Square");
	By kadastrNumber = By.id("KadastrBld");
	By usePurpose = By.id("UsagePurposes");
	By contractSaveBtn = By.xpath("//a[@title='Сохранить']");

	public void fillIn(String contractRight) throws InterruptedException {		
		String contrDate = Generator.fakeDatePast();

		if (driver.findElement(btiAddrText).getAttribute("innerHTML").length() == 1) {
			driver.findElement(bldAddress).click();
			Thread.sleep(300);
			driver.findElement(autogenInput).sendKeys("Ста");
			Thread.sleep(3000);
			List<WebElement> results = driver.findElements(select2drop);
			results.get(1).click();
			Thread.sleep(3000);
			List<WebElement> newResults = driver.findElements(select2drop);
			newResults.get(1).click();
		}
		driver.findElement(contractNum).sendKeys(Generator.fakeContractNum());
		driver.findElement(contractDate).sendKeys(contrDate);
		driver.findElement(contractNum).click();
		driver.findElement(subjectType).click();
		List<WebElement> sbjs = driver.findElements(select2drop);		
		sbjs.get(Generator.getRandomUpTo(sbjs.size())).click();

		String subject = driver.findElement(subjectType).getText();

		driver.findElement(rightType).click();
		List<WebElement> rights = driver.findElements(select2drop);
		for (int i = 0; i < rights.size(); i++) {
			String right = rights.get(i).getText();
			if (right.contains(contractRight)) {
				rights.get(i).click();
				break;
			}
		}		

		driver.findElement(contractValidity).click();
		List<WebElement> vals = driver.findElements(select2drop);		
		vals.get(Generator.getRandomUpTo(vals.size())).click();

		driver.findElement(startDate).sendKeys(contrDate);
		driver.findElement(endDate).sendKeys(Generator.fakeDateFuture());
		driver.findElement(contractNum).click();
		driver.findElement(isPerpetual).click();
		List<WebElement> perps = driver.findElements(select2drop);		
		perps.get(Generator.getRandomUpTo(perps.size())).click();

		driver.findElement(subjectName).sendKeys(fake.company().name());		

		if (subject.contains("ЮЛ")) {
			Thread.sleep(500);
			driver.findElement(legalForm).sendKeys("ОАО");
		}

		if (!subject.contains("Иностранная")) {
			driver.findElement(subjectInn).sendKeys(Generator.fakeInn());
		}

		driver.findElement(kadastrNumber).clear();
		driver.findElement(kadastrNumber).sendKeys(Generator.fakeKadastr());
		driver.findElement(usePurpose).sendKeys("Сбор пушнины, мёда, рыболовство.");
		driver.findElement(contractSaveBtn).click();
		driver.findElement(toInspBtn).click();
	}
	public void fillIn(String contractRight, String kadastrNum) throws InterruptedException {		
		String contrDate = Generator.fakeDatePast();

		if (driver.findElement(btiAddrText).getAttribute("innerHTML").length() == 1) {
			driver.findElement(bldAddress).click();
			Thread.sleep(300);
			driver.findElement(autogenInput).sendKeys("Ста");
			Thread.sleep(3000);
			List<WebElement> results = driver.findElements(select2drop);
			results.get(1).click();
			Thread.sleep(3000);
			List<WebElement> newResults = driver.findElements(select2drop);
			newResults.get(1).click();
		}
		driver.findElement(contractNum).sendKeys(Generator.fakeContractNum());
		driver.findElement(contractDate).sendKeys(contrDate);
		driver.findElement(contractNum).click();
		driver.findElement(subjectType).click();
		List<WebElement> sbjs = driver.findElements(select2drop);		
		sbjs.get(Generator.getRandomUpTo(sbjs.size())).click();

		String subject = driver.findElement(subjectType).getText();

		driver.findElement(rightType).click();
		List<WebElement> rights = driver.findElements(select2drop);
		for (int i = 0; i < rights.size(); i++) {
			String right = rights.get(i).getText();
			if (right.contains(contractRight)) {
				rights.get(i).click();
				break;
			}
		}		

		driver.findElement(contractValidity).click();
		List<WebElement> vals = driver.findElements(select2drop);		
		vals.get(Generator.getRandomUpTo(vals.size())).click();

		driver.findElement(startDate).sendKeys(contrDate);
		driver.findElement(endDate).sendKeys(Generator.fakeDateFuture());
		driver.findElement(contractNum).click();
		driver.findElement(isPerpetual).click();
		List<WebElement> perps = driver.findElements(select2drop);		
		perps.get(Generator.getRandomUpTo(perps.size())).click();

		driver.findElement(subjectName).sendKeys(fake.company().name());		

		if (subject.contains("ЮЛ")) {
			Thread.sleep(500);
			driver.findElement(legalForm).sendKeys("ОАО");
		}

		if (!subject.contains("Иностранная")) {
			driver.findElement(subjectInn).sendKeys(Generator.fakeInn());
		}

		driver.findElement(kadastrNumber).clear();
		driver.findElement(kadastrNumber).sendKeys(kadastrNum);
		driver.findElement(usePurpose).sendKeys("Сбор пушнины, мёда, рыболовство.");
		driver.findElement(contractSaveBtn).click();
		driver.findElement(toInspBtn).click();
	}
	
	public void fillIn(String contractRight, String kadastrNum, String ddMMyyyy) throws InterruptedException {
		String contrDate = ddMMyyyy;

		if (driver.findElement(btiAddrText).getAttribute("innerHTML").length() == 1) {
			driver.findElement(bldAddress).click();
			Thread.sleep(300);
			driver.findElement(autogenInput).sendKeys("Ста");
			Thread.sleep(3000);
			List<WebElement> results = driver.findElements(select2drop);
			results.get(1).click();
			Thread.sleep(3000);
			List<WebElement> newResults = driver.findElements(select2drop);
			newResults.get(1).click();
		}
		driver.findElement(contractNum).sendKeys(Generator.fakeContractNum());
		driver.findElement(contractDate).sendKeys(contrDate);
		driver.findElement(contractNum).click();
		driver.findElement(subjectType).click();
		List<WebElement> sbjs = driver.findElements(select2drop);		
		sbjs.get(Generator.getRandomUpTo(sbjs.size())).click();

		String subject = driver.findElement(subjectType).getText();

		driver.findElement(rightType).click();
		List<WebElement> rights = driver.findElements(select2drop);
		for (int i = 0; i < rights.size(); i++) {
			String right = rights.get(i).getText();
			if (right.contains(contractRight)) {
				rights.get(i).click();
				break;
			}
		}		

		driver.findElement(contractValidity).click();
		List<WebElement> vals = driver.findElements(select2drop);		
		vals.get(Generator.getRandomUpTo(vals.size())).click();

		driver.findElement(startDate).sendKeys(contrDate);
		driver.findElement(endDate).sendKeys(Generator.fakeDateFuture());
		driver.findElement(contractNum).click();
		driver.findElement(isPerpetual).click();
		List<WebElement> perps = driver.findElements(select2drop);		
		perps.get(Generator.getRandomUpTo(perps.size())).click();

		driver.findElement(subjectName).sendKeys(fake.company().name());		

		if (subject.contains("ЮЛ")) {
			Thread.sleep(500);
			driver.findElement(legalForm).sendKeys("ОАО");
		}

		if (!subject.contains("Иностранная")) {
			driver.findElement(subjectInn).sendKeys(Generator.fakeInn());
		}

		driver.findElement(kadastrNumber).clear();
		driver.findElement(kadastrNumber).sendKeys(kadastrNum);
		driver.findElement(usePurpose).sendKeys("Сбор пушнины, мёда, рыболовство.");
		driver.findElement(contractSaveBtn).click();
		driver.findElement(toInspBtn).click();
	}

}
