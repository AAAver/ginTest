package pagerepository.ubs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import pagerepository.common.Save;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pagerepository.utilities.Catalog;
import pagerepository.utilities.CorePage;
import pagerepository.utilities.Generator;
import pagerepository.utilities.Props;

public class UbsScratch extends CorePage {

    public UbsScratch(WebDriver driver) {
        super(driver);
    }

    private String dopComms = fake.book().title();
    private String fakeAddress = fake.address().streetAddress();
    private String ubsSquare = Integer.toString(Generator.getRandomUpTo(5000));
    private String objSquare = Integer.toString(Integer.parseInt(ubsSquare) + Generator.getRandomUpTo(2000));
    private String photo1Path = (new File(Props.PHOTO_PATH_A)).getAbsolutePath();
    private String photo2Path = (new File(Props.PHOTO_PATH_U)).getAbsolutePath();
    private String photo3Path = (new File(Props.PHOTO_PATH_T)).getAbsolutePath();
    private String photo4Path = (new File(Props.PHOTO_PATH_O)).getAbsolutePath();
    private String pp819 = Catalog.ubs.resolution.PP_819;


    // Кнопка сохранить
    By save = By.xpath("//a[@title='Сохранить (Ctrl+S)']");
    // Фотографии
    By photos = By.xpath("//ul/li[last()] //input[contains(@id, 'UploadPhotos')]");
    // Округ Район
    By dropAo = By.xpath("//*[@id='s2id_AoId']");
    By dropAoCross = By.xpath("//*[@id='s2id_AoId'] //abbr[@class='select2-search-choice-close']");
    By dropDistrict = By.xpath("//*[@id='s2id_DistrictId']");
    // Проверяющее подразделение
    By chkDepartment = By.xpath("//*[@id='s2id_DepartmentIdM']");
    // ++++ Ручная корректировка состояния* ++++//
    By dropManualCorrection = By.xpath("//*[@id='s2id_IsStateHandControlM']");
    By fieldManualCorrectionComments = By.xpath("//*[@id='StateHandControlCommentsM']");
    // ++++ Состояние ОСС* ++++//
    By dropState = By.xpath("//*[@id = 's2id_StateM']");
    By dropStateActualState = By.xpath("//*[@id='s2id_StateM'] //span");
    // ++++ Рассматривается в рамках ++++//
    By resol = By.xpath("//*[@id='s2id_ResolutionNumberId']");
    // Адрес
    By addressZU = By.xpath("//*[@id='LandAddressM_Address']");
    By address = By.xpath("//*[@id='ObjectAddressM_Address']");
    // Тип объекта
    By objType = By.xpath("//*[@id='s2id_ObjectTypeCtId']");
    // Рассматривается в рамках

    // Площадь самостроя/объекта
    By fieldUbsSquare = By.xpath("//*[@id='Square']");
    By fieldObjSquare = By.xpath("//*[@id='ObjectSquare']");

    // Материал стен/конструктив
    By wallMat = By.xpath("//*[@id='s2id_MtrlWallCtId']");
    By constructiv = By.xpath("//*[@id='s2id_ConstructiveCtId']");

    // Фактическое использование/пользователь
    By factUsage = By.xpath("//*[@id='s2id_ActivityTypeCtIds']");
    By actualUser = By.xpath("//*[@id='ActualUserName']");

    // ++++ Информация о здании ++++//
    By collapseBldInfo = By.xpath("//*[@id='KadastrBld']/../../../preceding-sibling::div");
    By fieldBldInfoKadastr = By.xpath("//*[@id='KadastrBld']");

    // Дополнительные комментарии
    By addComments = By.xpath("//*[@id='AdditionalComments']");

    By validationErrors = By.xpath("//div[@class='validation-summary-errors'] //li");

    By zpoTrue = By.xpath("//*[@id = 'Zpo_ZpoExist' and @value = 'True']/parent::label");
    By zpoFalse = By.xpath("//*[@id = 'Zpo_ZpoExist' and @value = 'False']/parent::label");
    By zpoNoData = By.xpath("//*[@id = 'Zpo_ZpoExist' and @value = '']/parent::label");
    By rentContractZPO = By.xpath("//*[@id='Zpo_ZpoRentContract']");
    By contractStartDateZPO = By.xpath("//*[@id='Zpo_ZpoContractStartDate']");
    By contractEndDateZPO = By.xpath("//*[@id='Zpo_ZpoContractEndDate']");
    By usageTypeZPO = By.xpath("//*[@id='Zpo_ZpoUsageType']");

    By btnAttachDocs = By.xpath("//*[@title='Добавить документ (Ctrl+D)']");
    By inputFileField = By.xpath(
            "//div[@class!='hdn']/div/div/*[contains(text(), 'Выберите файл')]/following-sibling::input[contains(@name, 'UploadDocuments')]");
    By dropCategory = By.xpath("//*[@id='dumpName']");
    @SuppressWarnings("unused")
    By commentaries = By.xpath("//div[@class='comment-block'] //textarea");
    By btnConfirmAttachement = By
            .xpath("//div[contains(@class,'ui-dialog-buttonset')]/button[@class = 'btn btn-primary btn-xs']");
    By documentDate = By.xpath("//*[contains(@class,'ui-widget')] //input[contains(@name, 'DocumentDate')]");

    By dropVerification = By.xpath("//*[@class='btn-group']/*[@data-toggle='dropdown']");
    By btnToVerification = By.xpath("//a[contains(text(),'Отправить на верификацию')]");
    By btnAcceptInSendToVerDialog = By.xpath(
            "//div[contains(@class, 'ui-dialog')]//*[@id='send-to-verification-dialog']/following-sibling::div //button[contains(@class, 'btn-success')]");
    By btnVerifyUKON = By.xpath("//*[@id='verify-btn']");
    By btnAcceptInVerUkonDialog = By
            .xpath("//*[@id='verify-dialog']/following-sibling::div //button[@class='btn btn-xs btn-success']");

    By btnActualize = By.xpath("//a[text()='Добавить в актуализацию']");
    By actualize819Pril = By.xpath("//*[@id='s2id_PP819CtId']");
    By btnSaveActualization819Pril = By
            .xpath("//form[contains(@action, 'EditUbActualization')] //button[@title='Сохранить']");
    By btnActualizationToUbs = By.xpath("//a[@title='Карточка объекта']");
    By PP819NumberM = By.xpath("//*[@id='PP819NumberM']");
    By btnSavePP819Number = By
            .xpath("//form[contains(@action, 'UpdateActualizationNumber')] //button[@title='Сохранить']");
    By toActualizationCard = By.xpath("//*[@id='ireon-map-container']/following-sibling::div[3] //a");

    // === КОРРЕКТИРОВКА ОСС ПО РЕШЕНИЮ СУДА === //
    By f_courtDecisionCorrectionAddress = By.xpath("//*[@id='CourtDecisionCorrection_ObjectAddressM_Address']");
    By div_courtDecisionCorrection = By.xpath("//*[@id='CourtDecisionCorrection_ObjectAddressM_Address']/../../..");
    By courtDecisionCorrectionCollapse = By.xpath("//*[@id='CourtDecisionCorrection_ObjectAddressM_Address']/../../../../preceding-sibling::div[1]");
    By f_courtDecisionCorrectionUbsSquare = By.xpath("//*[@id='CourtDecisionCorrection_Square']");
    By d_courtDecisionCorrectionWallMaterial = By.xpath("//*[@id='s2id_CourtDecisionCorrection_MtrlWallCtId']");
    By f_courtDecisionCorrectionDismantleSquare = By.xpath("//*[@id='CourtDecisionCorrection_DismantleCourtDecisionSquare']");
    By f_courtDecisionCorrectionVoluntaryDismantleDate = By.xpath("//*[@id='CourtDecisionCorrection_VoluntaryDismantleDate']");
    By b_finishCorrection = By.xpath("//*[@title='Завершить редактирование']");
    By b_modalConfirm = By.xpath("//*[contains(@style,'display: block')] //button[contains(@class,'btn-success')]");

    public void courtDecisionCorrection() {
        if (!isDisplayed(div_courtDecisionCorrection)) {
            click(courtDecisionCorrectionCollapse);
        }
        writeText(f_courtDecisionCorrectionUbsSquare, ubsSquare);
        chooseFromDropDownRandom(d_courtDecisionCorrectionWallMaterial);
        writeText(f_courtDecisionCorrectionDismantleSquare, ubsSquare);
        setDate(f_courtDecisionCorrectionVoluntaryDismantleDate, Generator.getCurrentDatePlus5());
    }

    public void finishCourtDecisionCorrection(){
        click(b_finishCorrection);
        click(b_modalConfirm);
    }


    public void actualizePril(String pril) {
        scrollIntoViewBy(btnActualize);
        click(btnActualize);
        click(actualize819Pril);
        List<WebElement> prils = getElementList(select2drop);
        for (WebElement webElement : prils) {
            if (getText(webElement).contains(pril)) {
                click(webElement);
                break;
            }
        }
        click(btnSaveActualization819Pril);
        writeText(PP819NumberM, Integer.toString(random.nextInt(100)));
        click(btnSavePP819Number);
        click(btnSaveActualization819Pril);
        click(btnActualizationToUbs);
    }

    public void verify() throws InterruptedException {
        log.info("ENTER verify()");
        scrollToTop();
        Actions a = new Actions(driver);

        while (!isDisplayed(btnToVerification)) {
            click(dropVerification);
        }
        click(btnToVerification);
        a.moveToElement(castToWebElement(btnAcceptInSendToVerDialog)).click().build().perform();
        log.info("Sent to verification");

        while (!isDisplayed(btnVerifyUKON)) {
            click(dropVerification);
        }
        click(btnVerifyUKON);
        a.moveToElement(castToWebElement(btnAcceptInVerUkonDialog)).click().build().perform();
        log.info("Verified by UKON");

        while (!isDisplayed(btnVerifyUKON)) {
            click(dropVerification);
        }
        click(btnVerifyUKON);
        a.moveToElement(castToWebElement(btnAcceptInVerUkonDialog)).click().build().perform();
        log.info("Verified");
        log.info("EXIT verify()");
    }

    public void setObjectSqr(String square) {
        scrollIntoViewBy(fieldObjSquare);
        writeText(fieldObjSquare, square);
    }

    public void setUbsSqr(String square) {
        scrollIntoViewBy(fieldUbsSquare);
        writeText(fieldUbsSquare, square);
    }

    public void dropObjectType(String type) {
        scrollIntoViewBy(objType);
        click(objType);
        List<WebElement> types = getElementList(select2drop);
        for (WebElement webElement : types) {
            if (getText(webElement).contains(type)) {
                click(webElement);
                break;
            }
        }
    }

    public void setObjectTypeRandom() {
        scrollIntoViewBy(objType);
        click(objType);
        List<WebElement> types = getElementList(select2drop);
        click(types.get(random.nextInt(types.size())));
    }

    public void setDistrictRandom() {
        scrollIntoViewBy(dropDistrict);
        click(dropDistrict);
        List<WebElement> types = getElementList(select2drop);
        click(types.get(random.nextInt(types.size())));
    }

    public void zpo(boolean exist) {
        scrollIntoViewBy(zpoTrue);
        if (exist) {
            click(zpoTrue);
            writeText(rentContractZPO, Generator.fakeContractNum());
            setDate(contractStartDateZPO, Generator.fakeDatePast());
            setDate(contractEndDateZPO, Generator.fakeDateFuture());
            writeText(usageTypeZPO, "готовка пищи");
        } else {
            click(zpoFalse);
        }
    }

    public ArrayList<String> validationErrors() {
        scrollToTop();
        List<WebElement> errorsEl = getElementList(validationErrors);
        ArrayList<String> errors = new ArrayList<String>();
        for (WebElement webElement : errorsEl) {
            String error = getText(webElement);
            errors.add(error);
        }
        return errors;
    }

    public void attachPhotos() {
        writeText(photos, photo1Path);
        writeText(photos, photo2Path);
        writeText(photos, photo3Path);
        writeText(photos, photo4Path);
    }

    public void fillCommonInfo() throws InterruptedException {
        scrollIntoViewBy(objType);
        click(objType);
        List<WebElement> types = getElementList(select2drop);
        click(types.get(random.nextInt(types.size())));

        writeText(fieldUbsSquare, ubsSquare);
        writeText(fieldObjSquare, objSquare);

        scrollIntoViewBy(wallMat);
        click(wallMat);
        List<WebElement> materials = getElementList(select2drop);
        click(materials.get(random.nextInt(materials.size())));

        click(constructiv);
        List<WebElement> constructivs = getElementList(select2drop);
        click(constructivs.get(random.nextInt(constructivs.size())));

        click(factUsage);
        List<WebElement> usages = getElementList(select2drop);
        click(usages.get(random.nextInt(usages.size())));
        writeText(addComments, dopComms);
        writeText(actualUser, fake.name().fullName());
    }

    public void generateUBS(String address, String ao, String resol) throws InterruptedException {
        attachPhotos();
        setAo(ao);
        setDepartment(ao);
        setAddress(address);
        fillCommonInfo();
        setResolution(resol);
        Save.saveThis(driver);
    }

    public String getObjSquare() {
        return Integer.toString((int) Float.parseFloat(getAttribute(fieldObjSquare, "value")));

    }

    public String getUbsSquare() {
        return Integer.toString((int) Float.parseFloat(getAttribute(fieldUbsSquare, "value")));
    }

    public void setAddress(String address) {
        scrollIntoViewBy(addressZU);
        writeText(addressZU, address);
        writeText(this.address, address);
    }

    public void setAo(String setAo) throws InterruptedException {
        scrollIntoViewBy(dropAo);
        click(dropAo);
        List<WebElement> aos = getElementList(select2drop);
        for (WebElement ao : aos) {
            if (getText(ao).contains(setAo)) {
                ao.click();
                click(dropDistrict);
                List<WebElement> districts = getElementList(select2drop);
                click(districts.get(random.nextInt(districts.size())));
                break;
            }
        }
    }

    public void setDepartment(String department) {
        scrollIntoViewBy(chkDepartment);
        click(chkDepartment);
        List<WebElement> departments = getElementList(select2drop);
        for (WebElement webElement : departments) {
            if (getText(webElement).contains(department)) {
                click(webElement);
                break;
            }
        }
    }

    public void setResolution(String resolution) {
        scrollIntoViewBy(resol);
        click(resol);
        List<WebElement> resolutions = getElementList(select2drop);
        for (WebElement webElement : resolutions) {
            if (getText(webElement).contains(resolution)) {
                click(webElement);
                break;
            }
        }
    }

    public void isManualCorrection(boolean bool) {
        log.info("ENTER isManualCorrection(), going to change to " + bool);
        scrollIntoViewBy(dropManualCorrection);
        log.info("Element 'dropManualCorrection' found. Scrolled to element");
        click(dropManualCorrection);
        log.info("Element 'dropManualCorrection' clicked");
        List<WebElement> b = getElementList(select2drop);
        if (bool) {
            click(b.get(0));
            log.info("Manual correction is picked");
            writeText(fieldManualCorrectionComments, "Необходима ручная корректировка");
            log.info("Comments to manual correction are populated. EXIT isManualCorrection()");
        } else {
            click(b.get(1));
            log.info("No manual correction");
            log.info("EXIT isManualCorrection()");
        }
    }

    public void setUbsState(String state) {
        log.info("ENTER UbsScratch.setUbsState(), going to change to " + state);
        scrollIntoViewBy(dropState);
        log.info("Element 'dropState' found. Scrolled to element");
        click(dropState);
        log.info("Element 'dropState' clicked");
        List<WebElement> states = getElementList(select2drop);
        for (WebElement webElement : states) {
            if (getText(webElement).equals(state)) {
                click(webElement);
                break;
            }
        }
        if (!getActualValueFromDrop(dropState).equals(state)) {
            log.error("State did not change correctly. Expected: " + state + ". Actual: " + getActualValueFromDrop(dropState));
        } else {
            log.info("State changed to " + state);
            log.info("EXIT setUbsState()");
        }

    }

    public void setBuildingKadastr(String kadastr) {
        log.info("ENTER UbsScratch.setBuildingKadastr(), going to set " + kadastr);
        scrollIntoViewBy(collapseBldInfo);
        log.info("Element 'collapseBldInfo' found. Scrolled to element");
        if (!isDisplayed(fieldBldInfoKadastr)) {
            log.info("Element 'fieldBldInfoKadastr' is not displayed. Trying to expand block");
            click(collapseBldInfo);
            log.info("Block should be expanded");
        }
        writeText(fieldBldInfoKadastr, kadastr);
        log.info("Kadastr populated");
        log.info("EXIT setBuildingKadastr()");
    }

    public void uploadFile(String[] docCategory, String[] docPath) {
        int i = 0;
        log.info("ENTER uploadFile()");
        while (i < docCategory.length) {
            scrollIntoViewBy(btnAttachDocs);
            click(btnAttachDocs);
            log.info("Modal opened");
            String path = (new File(docPath[i])).getAbsolutePath();
            writeText(inputFileField, path);
            log.info("File " + (i + 1) + " attached");
            Select s = new Select(castToWebElement(dropCategory));
            s.selectByVisibleText(docCategory[i]);
            log.info("Category " + (i + 1) + " chosen");

            click(btnConfirmAttachement);
            log.info("Modal closed");
            i++;
        }
        log.info("EXIT uploadFile()");
    }

}
