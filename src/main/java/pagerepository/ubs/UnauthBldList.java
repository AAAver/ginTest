package pagerepository.ubs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

public class UnauthBldList extends CorePage {

		public UnauthBldList(WebDriver driver) {
				super(driver);
			}
	
	By addUnauthBld = By.xpath("//a[text()='Добавить карточку объекта']");
	
	public void addUnauthBld() {
		do {
			click(addUnauthBld);
		} while (!driver.getCurrentUrl().contains("Add"));

	}

}
