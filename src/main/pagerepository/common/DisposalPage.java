package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DisposalPage {

	WebDriver driver;

	public DisposalPage(WebDriver driver) {
		this.driver = driver;
	}

	By addInspection = By.xpath("//a[@title='Добавить Обследование']");

	public WebElement btnAddInspection() {
		return driver.findElement(addInspection);
	}

	public void addInspection() {
		btnAddInspection().click();
	}

}
