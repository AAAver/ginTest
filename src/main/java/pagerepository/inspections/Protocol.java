package pagerepository.inspections;

import java.io.File;

import pagerepository.utilities.Save;
import pagerepository.utilities.Upload;
import miscelaneous.Catalog;
import pagerepository.utilities.CorePage;
import miscelaneous.Generator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Protocol extends CorePage {

    public Protocol(WebDriver driver) {
        super(driver);
    }

    String protocolCategory = Catalog.docs.category.PROTOCOL;
    String protocolFile = (new File(Catalog.docs.path.PROTOCOL)).getAbsolutePath();

    By subNumber = By.xpath("//*[@id='ProtocolSubnumber']");
    By date = By.xpath("//*[@id='ProtocolDate']");
    By responsiblePerson = By.xpath("//*[@id='s2id_InspectorUserId']");
    By deliveryType = By.xpath("//*[@id='s2id_DeliveryTypeCtId']");
    By violatorType = By.xpath("//*[@id='s2id_ViolatorTypeCtId']");
    By protocolCollapse = By.xpath("//*[@id='ProtocolContent']/../../../preceding-sibling::div[contains(@class,'collapse')]");
    By protocolContent = By.xpath("//*[@id='ProtocolContent']");
    By toInspBtn = By.xpath("//a[@title='Карточка проверки']");

    public void populateProtocol() throws InterruptedException {
        do {
            writeText(subNumber, Integer.toString(random.nextInt(300)));
            click(date);
        } while (!getAttribute(subNumber, "class").contains("valid"));

        setDate(date, Generator.getCurrentDate());
        chooseFromDropDownRandom(responsiblePerson);
        chooseFromDropDownRandom(deliveryType);
        chooseFromDropDown(violatorType,"ЮЛ");

        if (!isDisplayed(protocolContent)) {
            click(protocolCollapse);
        }

        writeText(protocolContent, fake.animal().name());

        Save.saveThis(driver);
        Upload.file(driver, protocolCategory, protocolFile);
        Save.saveThis(driver);

        click(toInspBtn);
    }

}
