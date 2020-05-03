package unauthBuilding;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import common.Save;
import misc.Catalog.ubs.resolution;
import misc.Generator;
import misc.Props;

public class UbsScratch {

	WebDriver driver;
	WebDriverWait wait;

	public UbsScratch(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	Logger log = LogManager.getLogger(UbsScratch.class.getName());
	private Faker fake = new Faker(new Locale("ru"));
	private String dopComms = fake.book().title();
	private String fakeAddress = fake.address().streetAddress();
	private String ubsSquare = Integer.toString(Generator.getRandomUpTo(5000));
	private String objSquare = Integer.toString(Integer.parseInt(ubsSquare) + Generator.getRandomUpTo(2000));
	private String photo1Path = (new File(Props.PHOTO_PATH_A)).getAbsolutePath();
	private String photo2Path = (new File(Props.PHOTO_PATH_U)).getAbsolutePath();
	private String photo3Path = (new File(Props.PHOTO_PATH_T)).getAbsolutePath();
	private String photo4Path = (new File(Props.PHOTO_PATH_O)).getAbsolutePath();
	private String pp819 = resolution.PP_819;

	// Все(ну почти все) справочники в дропдаунах
	private By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");
	// Кнопка сохранить
	private By save = By.xpath("//a[@title='Сохранить (Ctrl+S)']");

	// Фотографии
	private By photos = By.xpath("//ul/li[last()] //input[contains(@id, 'UploadPhotos')]");

	// Округ Район
	private By dropAo = By.id("s2id_AoId");
	private By dropAoCross = By.xpath("//*[@id='s2id_AoId'] //abbr[@class='select2-search-choice-close']");
	private By dropDistrict = By.id("s2id_DistrictId");

	// Проверяющее подразделение
	private By chkDepartment = By.id("s2id_DepartmentIdM");

	// ++++ Ручная корректировка состояния* ++++//
	private By dropManualCorrection = By.id("s2id_IsStateHandControlM");
	private By fieldManualCorrectionComments = By.id("StateHandControlCommentsM");
	// ++++ Состояние ОСС* ++++//
	private By dropState = By.id("s2id_StateM");
	private By dropStateActualState = By.xpath("//*[@id='s2id_StateM'] //span");
	// ++++ Рассматривается в рамках ++++//
	private By resol = By.id("s2id_ResolutionNumberId");

	// Адрес
	private By addressZU = By.id("LandAddressM_Address");
	private By address = By.id("ObjectAddressM_Address");

	// Тип объекта
	private By objType = By.id("s2id_ObjectTypeCtId");

	// Рассматривается в рамках

	// Площадь самостроя/объекта
	private By fieldUbsSquare = By.id("Square");
	private By fieldObjSquare = By.id("ObjectSquare");

	// Материал стен/конструктив
	private By wallMat = By.id("s2id_MtrlWallCtId");
	private By constructiv = By.id("s2id_ConstructiveCtId");

	// Фактическое использование/пользователь
	private By factUsage = By.id("s2id_ActivityTypeCtIds");
	private By actualUser = By.id("ActualUserName");

	// ++++ Информация о здании ++++//
	private By collapseBldInfo = By.xpath("//*[@id='documentsTableContainer']/following-sibling::div[17]");
	private By fieldBldInfoKadastr = By.id("KadastrBld");

	// Дополнительные комментарии
	private By addComments = By.id("AdditionalComments");

	private By validationErrors = By.xpath("//div[@class='validation-summary-errors'] //li");

	private By radioZPO = By.id("Zpo_ZpoExist");
	private By rentContractZPO = By.id("Zpo_ZpoRentContract");
	private By contractStartDateZPO = By.id("Zpo_ZpoContractStartDate");
	private By contractEndDateZPO = By.id("Zpo_ZpoContractEndDate");
	private By usageTypeZPO = By.id("Zpo_ZpoUsageType");

	private By btnAttachDocs = By.xpath("//*[@title='Добавить документ (Ctrl+D)']");
	private By inputFileField = By.xpath(
			"//div[@class!='hdn']/div/div/*[contains(text(), 'Выберите файл')]/following-sibling::input[contains(@name, 'UploadDocuments')]");
	private By dropCategory = By.id("dumpName");
	@SuppressWarnings("unused")
	private By commentaries = By.xpath("//div[@class='comment-block'] //textarea");
	private By btnConfirmAttachement = By
			.xpath("//div[contains(@class,'ui-dialog-buttonset')]/button[@class = 'btn btn-primary btn-xs']");
	private By documentDate = By.xpath("//*[contains(@class,'ui-widget')] //input[contains(@name, 'DocumentDate')]");

	private By dropVerification = By.xpath("//*[@class='btn-group']/*[@data-toggle='dropdown']");
	private By btnToVerification = By.xpath("//a[contains(text(),'Отправить на верификацию')]");
	private By btnAcceptInSendToVerDialog = By.xpath(
			"//div[contains(@class, 'ui-dialog')]//*[@id='send-to-verification-dialog']/following-sibling::div //button[@class='btn btn-xs btn-success']");
	private By btnVerifyUKON = By.id("verify-btn");
	private By btnAcceptInVerUkonDialog = By
			.xpath("//*[@id='verify-dialog']/following-sibling::div //button[@class='btn btn-xs btn-success']");

	private By btnActualize = By.xpath("//a[text()='Добавить в актуализацию']");
	private By actualize819Pril = By.id("s2id_PP819CtId");
	private By btnSaveActualization819Pril = By
			.xpath("//form[contains(@action, 'EditUbActualization')] //button[@title='Сохранить']");	
	private By btnActualizationToUbs = By.xpath("//a[@title='Карточка объекта']");
	private By PP819NumberM = By.id("PP819NumberM");
	private By btnSavePP819Number = By
			.xpath("//form[contains(@action, 'UpdateActualizationNumber')] //button[@title='Сохранить']");
	private By toActualizationCard = By.xpath("//*[@id='ireon-map-container']/following-sibling::div[3] //a");
	public void actualizePril(String pril) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(btnActualize));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(btnActualize).click();
		driver.findElement(actualize819Pril).click();
		List<WebElement> prils = driver.findElements(select2drop);
		for (int i = 0; i < prils.size(); i++) {
			String prilText = prils.get(i).getText();
			if (prilText.contains(pril)) {
				prils.get(i).click();
				break;
			}
		}
		driver.findElement(btnSaveActualization819Pril).click();
		Thread.sleep(1000);
		driver.findElement(PP819NumberM).sendKeys(Integer.toString(Generator.getRandomUpTo(100)));
		driver.findElement(btnSavePP819Number).click();
		Thread.sleep(1000);
		driver.findElement(btnSaveActualization819Pril).click();
		Thread.sleep(1000);
		driver.findElement(btnActualizationToUbs).click();
		
	
	}

	public void verify() throws InterruptedException {
		log.info("ENTER verify()");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		Actions a = new Actions(driver);

		while (!driver.findElement(btnToVerification).isDisplayed()) {
			driver.findElement(dropVerification).click();
			
		}
		driver.findElement(btnToVerification).click();
		a.moveToElement(driver.findElement(btnAcceptInSendToVerDialog)).click().build().perform();
		log.debug("Sent to verification");

		while (!driver.findElement(btnVerifyUKON).isDisplayed()) {
			driver.findElement(dropVerification).click();
			
		}
		driver.findElement(btnVerifyUKON).click();
		a.moveToElement(driver.findElement(btnAcceptInVerUkonDialog)).click().build().perform();
		log.debug("Verified by UKON");

		while (!driver.findElement(btnVerifyUKON).isDisplayed()) {
			driver.findElement(dropVerification).click();
					
		}
		driver.findElement(btnVerifyUKON).click();
		a.moveToElement(driver.findElement(btnAcceptInVerUkonDialog)).click().build().perform();
		log.debug("Verified");
		log.info("EXIT verify()");
	}

	private WebElement attachPhotoField() {
		return driver.findElement(photos);
	}

	private WebElement dropAo() {
		return driver.findElement(dropAo);
	}

	private WebElement dropDistrict() {
		return driver.findElement(dropDistrict);
	}

	private WebElement aoClear() {
		return driver.findElement(dropAoCross);
	}

	private WebElement fieldAddrZU() {
		return driver.findElement(addressZU);
	}

	private WebElement fieldAddr() {
		return driver.findElement(address);
	}

	private WebElement dropObjType() {
		return driver.findElement(objType);
	}

	private WebElement fieldUbsSquare() {
		return driver.findElement(fieldUbsSquare);
	}

	private WebElement fieldObjSquare() {
		return driver.findElement(fieldObjSquare);
	}

	private WebElement dropFactUsage() {
		return driver.findElement(factUsage);
	}

	private WebElement fieldDopComms() {
		return driver.findElement(addComments);
	}

	private WebElement dropResol() {
		return driver.findElement(resol);
	}

	private WebElement dropWallMat() {
		return driver.findElement(wallMat);
	}

	private WebElement dropConstructiv() {
		return driver.findElement(constructiv);
	}

	private WebElement dropChkDepartment() {
		return driver.findElement(chkDepartment);
	}

	private WebElement fieldActualUser() {
		return driver.findElement(actualUser);
	}

	private WebElement btnSave() {
		return driver.findElement(save);
	}

	public void setObjectSqr(String square) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(fieldObjSquare));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(fieldObjSquare).sendKeys(square);
	}

	public void setUbsSqr(String square) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(fieldUbsSquare));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(fieldUbsSquare).sendKeys(square);
	}

	public void dropObjectType(String type) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(objType));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(objType).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(select2drop));
		List<WebElement> types = driver.findElements(select2drop);
		for (int i = 0; i < types.size(); i++) {
			String stype = types.get(i).getText();
			if (stype.contains(type)) {
				types.get(i).click();
				break;
			}
		}
	}

	public void setObjectTypeRandom() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(objType));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(objType).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(select2drop));
		List<WebElement> types = driver.findElements(select2drop);
		types.get(Generator.getRandomUpTo(types.size())).click();
	}

	public void setDistrictRandom() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(dropDistrict));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		driver.findElement(dropDistrict).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(select2drop));
		List<WebElement> types = driver.findElements(select2drop);
		types.get(Generator.getRandomUpTo(types.size())).click();
	}

	public void zpo(boolean exist) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(radioZPO));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		List<WebElement> radios = driver.findElements(radioZPO);
		if (exist) {
			radios.get(0).click();
			driver.findElement(rentContractZPO).sendKeys(Generator.fakeContractNum());
			driver.findElement(contractStartDateZPO).sendKeys(Generator.fakeDatePast());
			driver.findElement(contractEndDateZPO).sendKeys(Generator.fakeDateFuture());
			driver.findElement(usageTypeZPO).sendKeys("готовка пищи");
		}

	}

	public ArrayList<String> validationErrors() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(validationErrors));
		List<WebElement> errorsEl = driver.findElements(validationErrors);
		ArrayList<String> errors = new ArrayList<String>();
		for (int i = 0; i < errorsEl.size(); i++) {
			String error = errorsEl.get(i).getText();
			errors.add(error);
		}
		return errors;
	}

	public void attachPhotos() {
		UbsScratch ubs = new UbsScratch(driver);
		ubs.attachPhotoField().sendKeys(photo1Path);
		ubs.attachPhotoField().sendKeys(photo2Path);
		ubs.attachPhotoField().sendKeys(photo3Path);
		ubs.attachPhotoField().sendKeys(photo4Path);
	}

	public void fillCommonInfo() throws InterruptedException {
		UbsScratch ubs = new UbsScratch(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.dropObjType());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.dropObjType().click();
		Thread.sleep(300);
		List<WebElement> types = driver.findElements(select2drop);
		int pick = Generator.getRandomUpTo(types.size());
		types.get(pick).click();

		ubs.fieldUbsSquare().sendKeys(ubsSquare);
		ubs.fieldObjSquare().sendKeys(objSquare);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.dropWallMat());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.dropWallMat().click();
		List<WebElement> mats = driver.findElements(select2drop);
		int pick2 = Generator.getRandomUpTo(mats.size());
		mats.get(pick2).click();

		ubs.dropConstructiv().click();
		List<WebElement> cons = driver.findElements(select2drop);
		int pick3 = Generator.getRandomUpTo(cons.size());
		cons.get(pick3).click();

		ubs.dropFactUsage().click();
		Thread.sleep(300);
		List<WebElement> usages = driver.findElements(select2drop);
		int pick4 = Generator.getRandomUpTo(usages.size());
		usages.get(pick4).click();
		ubs.fieldDopComms().sendKeys(dopComms);

		ubs.fieldActualUser().sendKeys(fake.name().fullName());

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
		UbsScratch ubs = new UbsScratch(driver);
		int objSquareRaw = (int) Float.parseFloat(ubs.fieldObjSquare().getAttribute("value"));
		String objSquare = Integer.toString(objSquareRaw);
		return objSquare;
	}

	public String getUbsSquare() {
		UbsScratch ubs = new UbsScratch(driver);
		int ubsSquareRaw = (int) Float.parseFloat(ubs.fieldUbsSquare().getAttribute("value"));
		String ubsSquare = Integer.toString(ubsSquareRaw);
		return ubsSquare;
	}

	public void setAddress() {
		UbsScratch ubs = new UbsScratch(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.fieldAddrZU());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.fieldAddrZU().click();
		ubs.fieldAddrZU().sendKeys(fakeAddress);
		ubs.fieldAddr().sendKeys(fakeAddress);
	}

	public void setAddress(String address) {
		UbsScratch ubs = new UbsScratch(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.fieldAddrZU());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.fieldAddrZU().click();
		ubs.fieldAddrZU().sendKeys(address);
		ubs.fieldAddr().sendKeys(address);
	}

	public void setAo(String setAo) throws InterruptedException {
		UbsScratch ubs = new UbsScratch(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.dropAo());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.dropAo().click();
		Thread.sleep(1000);
		List<WebElement> aos = driver.findElements(select2drop);
		for (int i = 0; i < aos.size(); i++) {
			String aoName = aos.get(i).getText();
			if (aoName.contains(setAo)) {

				aos.get(i).click();

				ubs.dropDistrict().click();
				Thread.sleep(500);
				List<WebElement> districts = driver.findElements(select2drop);
				int pick = Generator.getRandomUpTo(districts.size());
				districts.get(pick).click();
				break;
			}
		}
	}

	public void setDepartment(String department) {
		UbsScratch ubs = new UbsScratch(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.dropChkDepartment());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.dropChkDepartment().click();
		List<WebElement> chkrs = driver.findElements(select2drop);
		for (int i = 0; i < chkrs.size(); i++) {
			if (chkrs.get(i).getText().contains(department)) {
				chkrs.get(i).click();
				break;
			}
		}
	}

	public void setResolution(String resolution) throws InterruptedException {
		UbsScratch ubs = new UbsScratch(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ubs.dropResol());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		ubs.dropResol().click();
		Thread.sleep(300);
		List<WebElement> resols = driver.findElements(select2drop);
		for (int i = 0; i < resols.size(); i++) {
			String resolText = resols.get(i).getText();
			if (resolText.contains(resolution)) {
				resols.get(i).click();
				break;
			}
		}
	}

	public void isManualCorrection(boolean bool) throws InterruptedException {
		log.debug("ENTER UbsScratch.isManualCorrection(), going to change to " + bool);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(dropManualCorrection));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		log.debug("Element 'dropManualCorrection' found. Scrolled to element");
		driver.findElement(dropManualCorrection).click();
		log.debug("Element 'dropManualCorrection' clicked");
		Thread.sleep(300);
		List<WebElement> b = driver.findElements(select2drop);
		if (bool) {
			b.get(0).click();
			log.debug("Found and clicked on decision");
			driver.findElement(fieldManualCorrectionComments).sendKeys("Необходима ручная корректировка");
			log.debug("Comments to manual corection are populated. EXIT isManualCorrection()");
		} else {
			b.get(1).click();
			log.debug("Found and clicked on decision " + b.get(1).getText()
					+ " No comments needed. EXIT UbsScratch.isManualCorrection()");
		}
	}

	public void setUbsState(String state) throws InterruptedException {
		log.info("ENTER UbsScratch.setUbsState(), going to change to " + state);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(dropState));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		log.debug("Element 'dropState' found. Scrolled to element");
		driver.findElement(dropState).click();
		log.debug("Element 'dropState' clicked");
		Thread.sleep(300);
		List<WebElement> s = driver.findElements(select2drop);
		boolean stateChanged = false;
		for (int i = 0; i < s.size(); i++) {
			if (s.get(i).getText().equals(state)) {
				s.get(i).click();
				stateChanged = true;
				log.debug("State found and clicked");
				break;
			}
		}
		if (stateChanged) {
			log.debug("State changed");
			log.info("EXIT setUbsState()");
		} else {
			log.error("UBS State did not changed");
		}

	}

	public void setBuildingKadastr(String kadastr) {
		log.debug("ENTER UbsScratch.setBuildingKadastr(), going to set " + kadastr);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(collapseBldInfo));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		log.debug("Element 'collapseBldInfo' found. Scrolled to element");
		if (!driver.findElement(fieldBldInfoKadastr).isDisplayed()) {
			log.debug("Element 'fieldBldInfoKadastr' is not displayed. Trying to expand block");
			driver.findElement(collapseBldInfo).click();
			log.debug("Block should be expanded");
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(fieldBldInfoKadastr));
		log.debug("Block is visible");
		driver.findElement(fieldBldInfoKadastr).sendKeys(kadastr);
		log.debug("Kadastr populated");
		log.info("EXIT setBuildingKadastr()");
	}

	public void uploadFile(WebDriver driver, String[] docCategory, String[] docPath) {
		int i = 0;
		log.info("ENTER uploadFile()");
		while (i < docCategory.length) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					driver.findElement(btnAttachDocs));
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
			driver.findElement(btnAttachDocs).click();
			log.debug("Modal opened");
			String p = (new File(docPath[i])).getAbsolutePath();
			driver.findElement(inputFileField).sendKeys(p);
			log.debug("File " + i + "attached");
			Select s = new Select(driver.findElement(dropCategory));
			s.selectByVisibleText(docCategory[i]);
			log.debug("Category " + i + "choosen");

			try {
				driver.findElement(documentDate).sendKeys(Generator.getCurrentDate());
				log.debug("Date picked");
			} catch (NoSuchElementException e) {
				log.debug("Field date is absent");
			}

			driver.findElement(btnConfirmAttachement).click();
			log.debug("Modal closed");
			i++;
		}
		log.info("EXIT uploadFile()");
	}

	public String getUbsId() {
		log.info("Getting UBS Id");
		String[] urlParts = driver.getCurrentUrl().split("/");
		String ubsId = urlParts[urlParts.length - 1];
		log.debug("Ubs ID = " + ubsId);
		return ubsId;
	}
}
