package pagerepository.utilities;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Save {

	public static void saveThis(WebDriver driver) throws InterruptedException {
		Thread.sleep(500);
		Actions a = new Actions(driver);
		a.keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).build().perform();
		Thread.sleep(2000);
	}

}
