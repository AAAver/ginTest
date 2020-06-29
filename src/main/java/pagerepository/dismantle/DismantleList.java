package pagerepository.dismantle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pagerepository.utilities.CorePage;

import java.util.List;

public class DismantleList extends CorePage {
    public DismantleList(WebDriver driver) {
        super(driver);
    }

    By b_filterBtn = By.xpath("//a[text()='Фильтр']");
    By b_submitFilter = By.xpath("//button[text()='Поиск']");


    By b_filterMainTab = By.xpath("//a[text()='Основное']");
    By b_filterObjectTab = By.xpath("//a[text()='Объект']");
    By s_filterObjectTabParent = By.xpath("//a[text()='Объект']/parent::li");

    By f_objectAddress = By.xpath("//*[@id='AddressActual']");

    By td_address = By.xpath("//table[not(@id)] //td[2]");
    By b_toDismantleCard = By.xpath("//table[not(@id)]/tbody //a");
    By filterBlock = By.xpath("//*[@id='searchFormDiv']");


    public void openDismantle(String address) {
        while (!getAttribute(filterBlock,"style").contains("display: block")) {
            click(b_filterBtn);
        }

        while (!getAttribute(s_filterObjectTabParent, "class").contains("active")) {
            click(b_filterObjectTab);
        }

        writeText(f_objectAddress, address);
        click(b_submitFilter);

        List<WebElement> addresses = getElementList(td_address);
        for (int i = 0; i < addresses.size(); i++) {
            if (getText(addresses.get(i)).contains(address)) {
                while (!driver.getCurrentUrl().contains("UnauthBuildingDismantle/Edit")) {
                    click(getElementList(b_toDismantleCard).get(i));
                }
            }
        }
    }
}
