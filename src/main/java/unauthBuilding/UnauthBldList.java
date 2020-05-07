package java.unauthBuilding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.utilities.CorePage;

public class UnauthBldList extends CorePage {

		public UnauthBldList(WebDriver driver) {
				super(driver);
			}
	
	By addUnauthBld = By.xpath("//a[text()='Добавить карточку объекта']");
	
	public void addUnauthBld() {
		click(addUnauthBld);
		
	}

}
