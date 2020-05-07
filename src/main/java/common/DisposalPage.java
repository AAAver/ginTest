package java.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.utilities.CorePage;

public class DisposalPage extends CorePage {

	public DisposalPage(WebDriver driver) {
		super(driver);
	}

	By addInspection = By.xpath("//a[@title='Добавить Обследование']");

	public void addInspection() {
		click(addInspection);
	}

}
