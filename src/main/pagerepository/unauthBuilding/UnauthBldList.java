package unauthBuilding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UnauthBldList {

	WebDriver driver;	

	public UnauthBldList(WebDriver driver) {
				this.driver = driver;
			}
	
	By addUnauthBld = By.xpath("//a[text()='Добавить карточку объекта']");
	
	
	
	public void addUnauthBld() {
		driver.findElement(addUnauthBld).click();		
		
	}

}
