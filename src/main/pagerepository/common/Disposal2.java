package common;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import inspection.InspectionPage;

import java.time.Duration;

public class Disposal2 {
	
	private WebDriver driver;	
	private WebDriverWait wait;
	
	public Disposal2(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	By addInspection = By.xpath("//a[@title='Добавить Обследование']");
	
	
	public WebElement addInspection () {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addInspection));
		return driver.findElement(addInspection);
	}
	
	public InspectionPage addInsp () {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addInspection));
		driver.findElement(addInspection).click();	
		String url = driver.getCurrentUrl();
		if(url.contains("Disposals")) {
			driver.findElement(addInspection).click();
		}
		return new InspectionPage(this.driver);
	}

}
