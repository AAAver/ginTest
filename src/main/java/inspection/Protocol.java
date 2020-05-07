package inspection;

import java.io.File;
import java.util.List;
import utilities.Catalog;
import utilities.CorePage;
import utilities.Generator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.Save;
import common.Upload;

public class Protocol extends CorePage {

    public Protocol(WebDriver driver) {
        super(driver);
    }

    String protocolCategory = Catalog.docs.category.PROTOCOL;
    String protocolFile = (new File(Catalog.docs.path.PROTOCOL)).getAbsolutePath();

    By subNumber = By.id("ProtocolSubnumber");
    By date = By.id("ProtocolDate");
    By responsiblePerson = By.id("s2id_InspectorUserId");
    By deliveryType = By.id("s2id_DeliveryTypeCtId");
    By violatorType = By.id("s2id_ViolatorTypeCtId");
    By collapse = By.cssSelector(".collapse-head-line");
    By protocolContent = By.id("ProtocolContent");
    By toInspBtn = By.xpath("//a[@title='Карточка проверки']");

    public void fillCommonProtocol() throws InterruptedException {
        writeText(subNumber, Integer.toString(random.nextInt(300)));
        writeText(date, Generator.getCurrentDate());
        writeText(date, Keys.chord(Keys.ENTER));
        click(responsiblePerson);
        List<WebElement> persons = getElementList(select2drop);
        click(persons.get(random.nextInt(persons.size())));
        click(deliveryType);
        List<WebElement> dtypes = getElementList(select2drop);
        click(dtypes.get(random.nextInt(dtypes.size())));
        click(violatorType);
        List<WebElement> vtypes = getElementList(select2drop);
        for (WebElement webElement : vtypes) {
            if (getText(webElement).contains("ЮЛ")) {
                click(webElement);
                break;
            }
        }
        if (!isDisplayed(protocolContent)) {
            click(collapse);
        }
        writeText(protocolContent, fake.animal().name());

        Save.saveThis(driver);
        Upload.file(driver, protocolCategory, protocolFile);
        Save.saveThis(driver);

        click(toInspBtn);
    }

}
