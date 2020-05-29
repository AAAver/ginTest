package pagerepository.dismantle;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pagerepository.utilities.Catalog;
import pagerepository.utilities.CorePage;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;

import java.io.File;

public class DismantlePage extends CorePage {

    public DismantlePage(WebDriver driver) {
        super(driver);
    }

    By i_objectStatus = By.xpath("//*[text()='Статус объекта:']/parent::div");
    By f_notificationDate = By.xpath("//*[@id='NotificationDate']");
    By initialReviewPhoto = By.xpath("//*[contains(@id,'UploadPhotos_InitialReview')]");
    By voluntaryDismantlePhoto = By.xpath("//*[contains(@id,'UploadPhotos_VoluntaryDismantle')]");

    By b_Save = By.xpath("//*[@title='Сохранить']");
    By b_finishInspection = By.xpath("//*[@title='Завершить обследование']");
    By b_confirm = By.xpath("//*[contains(@style,'display: block')] //button[contains(@class,'btn-success')]");
    By b_voluntaryDismantleTimeEnded = By.xpath("//*[@title='Срок добровольного демонтажа истёк']");

    By b_voluntaryDismantleConfirmed = By.xpath("//*[@title='Демонтаж подтверждён']");
    By b_voluntaryDismantleNotConfirmed = By.xpath("//*[@title='Демонтаж не подтверждён']");

    By f_dismantleCost = By.xpath("//*[@id='Cost']");
    By d_contractor = By.xpath("//*[@id='s2id_ContractorId']");
    By d_dismantleComplexity = By.xpath("//*[@id='s2id_complexityDropDown']");
    By f_projectDesignCost = By.xpath("//*[@id = 'DesignCost']");
    By b_formDesignTask = By.xpath("//*[@title='Сформировать заявку на проектирование']");
    By b_formDismantleTask = By.xpath("//*[@title='Сформировать заявку на демонтаж']");


    // ====== Блок [Демонтаж] ====== //
    By dismantleBlockCollapse = By.xpath("//*[@id='tabDismantle']/parent::div/preceding-sibling::div");
    By dismantleSection = By.xpath("//*[@id='tabDismantle']/parent::div");
    By objectRevealTabParent = By.xpath("//a[text()='Выявление объекта']/parent::li");
    By objectRevealTab = By.xpath("//a[text()='Выявление объекта']");
    By dismantleProcessTabParent = By.xpath("//a[text()='Ход демонтажа']/parent::li");
    By dismantleProcessTab = By.xpath("//a[text()='Ход демонтажа']");
    By f_dismantleProcessPhoto = By.xpath("//*[contains(@id,'UploadPhotos_DismantleProgress')]");
    By dismantleConfirmTabParent = By.xpath("//a[text()='Подтверждение демонтажа']/parent::li");
    By dismantleConfirmTab = By.xpath("//a[text()='Подтверждение демонтажа']");
    By f_dismantleConfirmPhoto = By.xpath("//*[contains(@id,'UploadPhotos_DismantleApproval')]");

    // ====== Блок [Документация подрядчика] ====== //
    By contractorDocumentsCollapse = By.xpath("//*[contains(@id,'DismanteledDoc_Contractor')]/parent::div/preceding-sibling::div");
    By contractorDocumentsSection = By.xpath("//*[contains(@id,'DismanteledDoc_Contractor')]/parent::div");
    By b_finishDismantle = By.xpath("//*[@title='Завершить демонтаж']");

    // ====== Блок [Документация подрядчика(ГБУ)] ====== //
    By b_attachGbuDocs = By.xpath("//*[contains(@id,'Contractor_FHU_GBU')] //a[text()='Добавить документ']");
    By f_inputGbuDocs = By.xpath("//*[contains(@style,'display: block')]  //input[contains(@name,'File')]");
    By d_gbuDocCategory = By.xpath("//*[contains(@style,'display: block')]  //select[contains(@name,'CategoryId')]");
    By f_gbuDocDate = By.xpath("//*[contains(@style,'display: block')]  //input[contains(@name,'DocumentDate')]");
    By b_confirmDocAttachment = By.xpath("//*[contains(@style,'display: block')]  //button[contains(@class,'btn-primary')]");
    By b_acceptDismantleGbu = By.xpath("//*[contains(@id,'Contractor_FHU_GBU')]/following-sibling::div[1] //a[@title='Принять']");


    public void stageGbuInitial() {
        setDate(f_notificationDate, Generator.getCurrentDate());
        driver.findElement(initialReviewPhoto).sendKeys(new File(Props.PHOTO_PATH_A).getAbsolutePath());
        save();
        while (!isDisplayed(b_confirm)) {
            click(b_finishInspection);
        }
        click(b_confirm);
    }

    public void stageVoluntaryDismantle(boolean voluntarily) throws InterruptedException {
        driver.findElement(voluntaryDismantlePhoto).sendKeys(new File(Props.PHOTO_PATH_O).getAbsolutePath());
        save();
        if(driver.findElement(b_voluntaryDismantleConfirmed).isEnabled()){
            log.fatal("Button 'Demontazh Podtverzhden' enabled, but should be disabled");
        }
        driver.findElement(voluntaryDismantlePhoto).sendKeys(new File(Props.PHOTO_PATH_T).getAbsolutePath());
        save();
        scrollToTop();
        Thread.sleep(1000);
        click(b_voluntaryDismantleTimeEnded);
        Thread.sleep(1000);
        click(b_confirm);
        while (!isDisplayed(b_confirm)) {
            if (voluntarily) {
                click(b_voluntaryDismantleConfirmed);
            } else {
                click(b_voluntaryDismantleNotConfirmed);
            }
        }
        click(b_confirm);
    }

    public void stageDismantleComplexity(boolean isComplex) {
        if (!isComplex) {
//            clearField(f_dismantleCost);
//            writeText(f_dismantleCost, Integer.toString(random.nextInt(80000)));
            while (!getActualValueFromDrop(d_dismantleComplexity).contains("Простой")) {
                chooseFromDropDown(d_dismantleComplexity, "Простой");
            }
            while (getActualValueFromDrop(d_contractor).isBlank()) {
                chooseFromDropDownRandom(d_contractor);
            }
            save();
            while (!isDisplayed(b_confirm)) {
                click(b_formDismantleTask);
            }
        } else {
            chooseFromDropDown(d_dismantleComplexity, "Сложный");
            writeText(f_dismantleCost, Integer.toString(random.nextInt(800000)));
            chooseFromDropDownRandom(d_contractor);
            writeText(f_projectDesignCost, Integer.toString(random.nextInt(800000)));
            save();
            while (!isDisplayed(b_confirm)) {
                click(b_formDesignTask);
            }
        }
        click(b_confirm);
    }

    public void dismantleByContractor() {
        click(dismantleProcessTab);
        driver.findElement(f_dismantleProcessPhoto).sendKeys(new File(Props.PHOTO_PATH_O).getAbsolutePath());
        while (!isDisplayed(contractorDocumentsSection)) {
            click(contractorDocumentsCollapse);
        }
        while(!isDisplayed(b_confirm)) {
            click(b_finishDismantle);
        }
        click(b_confirm);
    }

    public void attachGbuDocs(String[] docCategory, String[] docPath) {
        int i = 0;
        while (i < docCategory.length) {
            while(!isDisplayed(f_inputGbuDocs)) {
                click(b_attachGbuDocs);
            }
            String path = (new File(docPath[i])).getAbsolutePath();
            writeText(f_inputGbuDocs, path);

            Select s = new Select(castToWebElement(d_gbuDocCategory));
            s.selectByVisibleText(docCategory[i]);


            try {
                setDate(f_gbuDocDate, Generator.getCurrentDate());
            } catch (NoSuchElementException e) {

            } catch (TimeoutException e) {

            }

            click(b_confirmDocAttachment);
            log.info("Modal closed");
            i++;
        }
    }

    public void stageGbuAcceptance() {
        attachGbuDocs(Catalog.docs.category.GBU_DISMANTLE_DOC_PACK, Catalog.docs.path.GBU_DISMANTLE_DOC_PACK);
        while(!isDisplayed(dismantleSection)) {
            click(dismantleBlockCollapse);
        }
        click(dismantleConfirmTab);
        driver.findElement(f_dismantleConfirmPhoto).sendKeys(new File(Props.PHOTO_PATH_U).getAbsolutePath());
        save();
        while(!isDisplayed(b_confirm)) {
            click(b_acceptDismantleGbu);
        }
        click(b_confirm);
    }


    public void save() {
        scrollToBottom();
        click(b_Save);
    }


    public String getStatus() {
        return getText(i_objectStatus);
    }


}
