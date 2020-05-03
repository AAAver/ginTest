package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {


	WebDriver driver;

	public MainPage(WebDriver driver) {
		this.driver = driver;
	}
	
	By bell = By.xpath("//li[@id='navbar-notification'] //a[@class='dropdown-toggle']");
	By notificationBp = By.xpath("//li[@id='navbar-notification']/ul/li[2]");
	
	public void goToActiveBp() {
		driver.findElement(bell).click();
		driver.findElement(notificationBp).click();
	}
	
	
}
