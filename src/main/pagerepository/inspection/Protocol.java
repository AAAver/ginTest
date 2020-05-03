package inspection;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import common.Save;
import common.Upload;
import misc.Catalog;
import misc.Generator;

public class Protocol {

	WebDriver driver;
	WebDriverWait wait;
	public Protocol(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}
	
	String protocolCategory = Catalog.docs.category.PROTOCOL;
	String protocolFile = (new File(Catalog.docs.path.PROTOCOL)).getAbsolutePath();

	Faker fake = new Faker(new Locale("ru"));

	private By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");

	private By subNumber = By.id("ProtocolSubnumber");
	private By date = By.id("ProtocolDate");	
	private By responsiblePerson = By.id("s2id_InspectorUserId");
	private By deliveryType = By.id("s2id_DeliveryTypeCtId");
	private By violatorType = By.id("s2id_ViolatorTypeCtId");
	private By collapse = By.cssSelector(".collapse-head-line");
	private By protocolContent = By.id("ProtocolContent");
	private By toInspBtn = By.xpath("//a[@title='Карточка проверки']");

	private WebElement subNumber() {
		return driver.findElement(subNumber);
	}

	private WebElement date() {
		return driver.findElement(date);
	}

	private WebElement responsiblePerson() {
		return driver.findElement(responsiblePerson);
	}

	private WebElement deliveryType() {
		return driver.findElement(deliveryType);
	}

	private WebElement violatorType() {
		return driver.findElement(violatorType);
	}

	private WebElement collapse() {
		return driver.findElement(collapse);
	}

	private WebElement protocolContent() {
		return driver.findElement(protocolContent);
	}
	private WebElement toInspBtn() {
		return driver.findElement(toInspBtn);
	}

	public void fillCommonProtocol() throws InterruptedException {
		Thread.sleep(1000);
		subNumber().sendKeys(Integer.toString(Generator.getRandomUpTo(300)));
		date().sendKeys(Generator.getCurrentDate());
		subNumber().click();

		responsiblePerson().click();
		List<WebElement> pers = driver.findElements(select2drop);
		pers.get(Generator.getRandomUpTo(pers.size())).click();

		deliveryType().click();
		List<WebElement> dtypes = driver.findElements(select2drop);
		dtypes.get(Generator.getRandomUpTo(dtypes.size())).click();

		violatorType().click();
		List<WebElement> vtypes = driver.findElements(select2drop);
		for(int i = 0; i < vtypes.size(); i++) {
			String vtype = vtypes.get(i).getText();
			if (vtype.contains("ЮЛ")) {
				vtypes.get(i).click();
				break;
			}
		}

		collapse().click();
		protocolContent().sendKeys(fake.animal().name());

		Save.saveThis(driver);
		Upload.file(driver, protocolCategory, protocolFile);
		Save.saveThis(driver);		
		
		toInspBtn().click();
	}

}
