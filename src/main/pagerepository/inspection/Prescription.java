package inspection;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import utilities.Generator;

public class Prescription {
	WebDriver driver;
	WebDriverWait wait;

	public Prescription(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	private By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");	
	private By subNumber = By.id("RequirementSubNumber");
	private By issueDate = By.id("IssueDate");
	private By deadlineDate = By.id("DeadLineDateM");
	private By type = By.id("s2id_RequirementTypeM");
	private By responsibleInspector = By.id("s2id_InspectorUserId");
	@SuppressWarnings("unused")
	private By deliveryType = By.id("s2id_DeliveryTypeCtId");
	private By content = By.id("Contents");
	private By saveBtn = By.id("submit-form-btn");
	private By toInspBtn =By.xpath("//a[@title='Карточка проверки']");
	Faker fake = new Faker(new Locale("ru"));
	
	public void fillPrescription() throws InterruptedException {
		Thread.sleep(500);
		driver.findElement(subNumber).sendKeys(Integer.toString(Generator.getRandomUpTo(1000)));
		driver.findElement(issueDate).sendKeys(Generator.getCurrentDate());
		driver.findElement(subNumber).click();		
		if(!driver.findElement(deadlineDate).getAttribute("class").contains("dirty-input")) {		
		driver.findElement(deadlineDate).sendKeys(Generator.getCurrentDatePlus5());		
		driver.findElement(subNumber).click();
		}
		driver.findElement(type).click();
		Thread.sleep(500);
		List<WebElement> types = driver.findElements(select2drop);
		for(int i =0; i < types.size(); i++) {
			String typeName = types.get(i).getText();
			if(typeName.contains("Предписание")) {
				types.get(i).click();
				break;
			}
		}
		
		driver.findElement(responsibleInspector).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(select2drop));
		List<WebElement> insps = driver.findElements(select2drop);
		insps.get(Generator.getRandomUpTo(insps.size())).click();
		driver.findElement(content).sendKeys(fake.artist().name());
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(saveBtn));
		driver.findElement(saveBtn).click();
	    Thread.sleep(1000);
	    driver.findElement(toInspBtn).click();
	}
	
}
