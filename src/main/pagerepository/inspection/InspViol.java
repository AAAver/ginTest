package inspection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import common.Upload;
import misc.Catalog;
import misc.Generator;

public class InspViol {
	WebDriver driver;
	WebDriverWait wait;
	
	public InspViol(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));	
	}
	
	Faker fake = new Faker(new Locale("ru"));
	String warnContent = "Здесь могло быть ваше нарушение";
	String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	String warnCategory = Catalog.docs.category.WARNING;
	String warnPath = (new File(Catalog.docs.path.WARNING)).getAbsolutePath();
	// Все(ну почти все) справочники в дропдаунах
	private By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");
	
	// Сохранить
	By saveBtn = By.xpath("//a[@title='Сохранить']");
	
	// К проверке
	private By toInsp = By.xpath("//a[@title='Проверка']");
	private By warningBtn = By.id("add-perimeter-control-btn");
	private By subNumber = By.id("SubNumber");
	@SuppressWarnings("unused")
	private By calendar = By.id("ui-datepicker-div");
	private By date = By.id("Date");
	@SuppressWarnings("unused")
	private By currentDate = By.cssSelector("a.ui-state-default.ui-state-highlight");
	private By koapArticle = By.id("s2id_KoApArticleCtId");
	private By inspector = By.id("s2id_InspectorId");
	private By deliveryType = By.id("s2id_DeliveryTypeCtId");
	private By violatorType = By.id("s2id_ViolatorTypeCtId");
	private By content = By.id("Content");	
	
	// Добавить нарушение
	private By addViolBtn = By.id("add-violation-btn");
	private By violTypeDrop = By.xpath("//*[@id='table-violation-info'] //tbody/tr[last()] //div[contains(@id, '_ViolationCtId')]");
	private By apArticleDrop = By.xpath("//*[@id='table-violation-info'] //tbody/tr[last()] //div[contains(@id, '_ApArticleCtId')]");
	private By violSuccessBtn = By.xpath("//*[@id='table-violation-info'] //tbody/tr[last()] //a[contains(@class, 'btn-success')]");
	
	// Таблица с нарушениями	
	private By tableViolPlus = By.xpath("//*[@id='table-violation-info'] //tbody/tr //a[contains(@class, 'bigger')]");
	private By addProtocol = By.xpath("//*[@id='table-violation-info'] //tbody/tr //a[@title='Добавить протокол']");	
	private By addPrescription = By.xpath("//*[@id='table-violation-info'] //tbody/tr //a[@title='Добавить предписание/уведомление']");
	private By violationLabel = By.xpath("//label[contains(@for, '_ViolationCtId')]");	
	
	public void addViolation(String violation) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(addViolBtn));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		driver.findElement(addViolBtn).click();
		Thread.sleep(1000);
		driver.findElement(violTypeDrop).click();
		Thread.sleep(300);
		List<WebElement> viols = driver.findElements(select2drop);
		for(int i = 0; i < viols.size(); i++) {
			if(viols.get(i).getText().contains(violation)) {
				viols.get(i).click();
				break;
			}
		}		
		driver.findElement(apArticleDrop).click();
		Thread.sleep(300);
		List<WebElement> articles = driver.findElements(select2drop);		
		articles.get(Generator.getRandomUpTo(articles.size())).click();		
		driver.findElement(violSuccessBtn).click();	
		
		
	}
	
	public void addProtocol(String violation) throws InterruptedException {
		Actions a = new Actions(driver);
		Thread.sleep(500);
		List<WebElement> violations = driver.findElements(violationLabel);
		List<WebElement> pluses = driver.findElements(tableViolPlus);
		List<WebElement> protocols = driver.findElements(addProtocol);
		WebElement protocolToAdd = null;
		WebElement plusToAdd = null;
		for(int i = 0; i < violations.size(); i++) {
			if(violations.get(i).getText().contains(violation)) {
			plusToAdd = pluses.get(i);
			protocolToAdd = protocols.get(i);
			}
		}
		a.moveToElement(plusToAdd).build().perform();		
		a.moveToElement(protocolToAdd).click().build().perform();
		Protocol p = new Protocol(driver);
		p.fillCommonProtocol();
		
		
	}
	
	public void addPrescription(String violation) throws InterruptedException {
		Actions a = new Actions(driver);
		Thread.sleep(500);
		List<WebElement> violations = driver.findElements(violationLabel);
		List<WebElement> pluses = driver.findElements(tableViolPlus);
		List<WebElement> prescs = driver.findElements(addPrescription);
		WebElement prescToAdd = null;
		WebElement plusToAdd = null;
		for(int i = 0; i < violations.size(); i++) {
			if(violations.get(i).getText().contains(violation)) {
			plusToAdd = pluses.get(i);
			prescToAdd = prescs.get(i);
			}
		}
		a.moveToElement(plusToAdd).build().perform();		
		a.moveToElement(prescToAdd).click().build().perform();
		Prescription p = new Prescription(driver);
		p.fillPrescription();
	}
	
	
	
	//================== ПРЕДОСТЕРЕЖЕНИЕ ================//
	public void addWarning() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(warningBtn));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		driver.findElement(warningBtn).click();
		driver.findElement(subNumber).sendKeys(fake.number().digits(5));
		driver.findElement(date).sendKeys(today);
		driver.findElement(subNumber).click();
		
		driver.findElement(koapArticle).click();	
		List<WebElement> articles = driver.findElements(select2drop);
		articles.get(Generator.getRandomUpTo(articles.size())).click();
		driver.findElement(inspector).click();		
		List<WebElement> inspectors = driver.findElements(select2drop);
		inspectors.get(Generator.getRandomUpTo(inspectors.size())).click();
		driver.findElement(deliveryType).click();		
		List<WebElement> deliveryTypes = driver.findElements(select2drop);
		deliveryTypes.get(Generator.getRandomUpTo(deliveryTypes.size())).click();
		driver.findElement(violatorType).click();
		List<WebElement> violatorTypes = driver.findElements(select2drop);
		violatorTypes.get(Generator.getRandomUpTo(violatorTypes.size())).click();
		driver.findElement(content).sendKeys(warnContent);
		driver.findElement(saveBtn).click();	
		Upload.file(driver, warnCategory, warnPath);
		driver.findElement(saveBtn).click();
		Thread.sleep(300);
		driver.findElement(toInsp).click();		
	}
	
	

}
