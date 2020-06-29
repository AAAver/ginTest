package pagerepository.inspections;

import miscelaneous.Catalog;
import miscelaneous.Generator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pagerepository.utilities.CorePage;
import pagerepository.utilities.Upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Warning extends CorePage {
    public Warning(WebDriver driver) {
        super(driver);
    }

    String warnCategory = Catalog.docs.category.WARNING;
    String warnPath = (new File(Catalog.docs.path.WARNING)).getAbsolutePath();
    String warnContent = "Здесь могло быть ваше нарушение";
    String today = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    // К проверке
    By toInsp = By.xpath("//a[@title='Проверка']");
    By subNumber = By.xpath("//*[@id='SubNumber']");
    By calendar = By.xpath("//*[@id='ui-datepicker-div']");
    By date = By.xpath("//*[@id='Date']");
    By koapArticle = By.xpath("//*[@id='s2id_KoApArticleCtId']");
    By inspector = By.xpath("//*[@id='s2id_InspectorId']");
    By deliveryType = By.xpath("//*[@id='s2id_DeliveryTypeCtId']");
    By violatorType = By.xpath("//*[@id='s2id_ViolatorTypeCtId']");
    By content = By.xpath("//*[@id='Content']");
    By saveWarning = By.xpath("//a[@title='Сохранить']");
    By addDocumentsButton = By.xpath("//*[@title='Добавить документ (Ctrl+D)']");
    By inputFileField = By.xpath("//div[contains(text(), 'Выберите файл')]/following-sibling::input");
    By dropCategory = By.xpath("//*[@id='dumpName']");
    By commentaries = By.xpath("//div[@aria-describedby='ui-id-1'] //div[@class='comment-block'] //textarea");
    By btnConfirmAttachement = By
            .xpath("//div[contains(@class,'ui-dialog-buttonset')]/button[@class = 'btn btn-primary btn-xs']");
    By documentDate = By.xpath("//*[contains(@class,'ui-widget')] //input[contains(@name, 'DocumentDate')]");
    By documentType = By.xpath("//*[@id='documentsTableContainer'] //tr/td[6]");

    public void populate() {

        chooseFromDropDownRandom(koapArticle);
        if(!getActualValueFromDrop(koapArticle).isBlank()) {
            log.info("Koap article selected: " + getActualValueFromDrop(koapArticle));
        }else{
            log.error("Koap article is not selected");
        }

        setDate(date, today);

        chooseFromDropDownRandom(inspector);
        if(!getActualValueFromDrop(inspector).isBlank()) {
            log.info("Inspector selected: " + getActualValueFromDrop(inspector));
        }else{
            log.error("Inspector is not selected");
        }

        chooseFromDropDownRandom(deliveryType);
        if(!getActualValueFromDrop(deliveryType).isBlank()) {
            log.info("Delivery type: " + getActualValueFromDrop(deliveryType));
        }else{
            log.error("Delivery type is not selected");
        }

        chooseFromDropDownRandom(violatorType);
        if(!getActualValueFromDrop(violatorType).isBlank()) {
            log.info("Violator type: " + getActualValueFromDrop(violatorType));
        }else{
            log.error("Violator type is not selected");
        }

        writeText(content, warnContent);
        click(saveWarning);
        if(!getAttribute(date,"value").isBlank()){
            log.info("Violation date set to: " + getAttribute(date,"value") );
        } else{
            log.error("Violation date is empty");
        }


        upload(warnCategory, warnPath);
        click(saveWarning);
        if(isDisplayed(documentType) && !getText(documentType).isBlank()){
            log.info("Warning document type: " + getText(documentType) +" attached");
        } else {
            log.error("Warning document not attached");
        }

        while (driver.getCurrentUrl().contains("EditWarning")) {
            click(toInsp);
        }

        if(driver.getCurrentUrl().contains("Controls/EditInsp")){
            log.info("Moved to inspection");
        } else {
            log.error("Wrong redirection");
        }

    }

    public void upload(String category, String path){
        clickJS(addDocumentsButton);
        writeText(inputFileField, path);
        Select s = new Select(driver.findElement(dropCategory));
        s.selectByVisibleText(category);

        try {
            driver.findElement(documentDate).sendKeys(Generator.getCurrentDate());
        } catch (NoSuchElementException e) {

        }
        clickJS(btnConfirmAttachement);
    }
}
