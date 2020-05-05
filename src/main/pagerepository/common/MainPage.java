package common;

import org.apache.logging.log4j.core.Core;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.CorePage;

public class MainPage extends CorePage {

	public MainPage(WebDriver driver) {
		super(driver);
	}
	
	By bell = By.xpath("//li[@id='navbar-notification'] //a[@class='dropdown-toggle']");
	By notificationBp = By.xpath("//li[@id='navbar-notification']/ul/li[2]");
	
	public void goToActiveBp() {
		click(bell);
		click(notificationBp);
	}
	
	
}
