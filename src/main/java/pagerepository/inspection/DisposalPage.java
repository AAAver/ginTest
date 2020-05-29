package pagerepository.inspection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

public class DisposalPage extends CorePage {

    public DisposalPage(WebDriver driver) {
        super(driver);
    }

    By addInspection = By.xpath("//a[@title='Добавить Обследование' or @title='Добавить Проверку']");

    public void addInspection() {
        while (!driver.getCurrentUrl().contains("Controls/AddInsp")) {
            click(addInspection);
        }
    }

}
