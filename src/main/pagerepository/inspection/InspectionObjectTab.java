package inspection;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import misc.Generator;

public class InspectionObjectTab extends InspectionPage {

	public InspectionObjectTab(WebDriver driver) {
		super(driver);
	}

	String owner = fake.name().fullName();
	String kadastrNumberNf = Generator.fakeKadastr();	
	String unom = fake.number().digits(5);
	

	// Все(ну почти все) справочники в дропдаунах
	By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");

	// Данные объекта
	By okrug = By.id("s2id_AoIdM");
	By okrugRemove = By.xpath("//*[@id='s2id_AoIdM'] //abbr");
	By district = By.id("s2id_DistrictIdM");
	By addressKIM = By.id("AddressM_Address");
	By roadAccess = By.id("IsAvailableFromRoadway");
	By objSquare = By.id("SquareM");
	By explotationRight = By.id("s2id_ExploitationRightCtId");
	By kadastrYN = By.id("s2id_HasKadastrZuRegistration");
	By kadastrNum = By.id("KadastrZu");
	By objTypeDrop = By.id("s2id_ObjectTypeCtIds");
	By clearTypeBtn = By.xpath("//*[@id='s2id_ObjectTypeCtIds'] //a");


	// Таблица 1. Иформация о здании из ЕГРН
	By egrnTableAdd = By.id("add-ctrl-bld-btn");
	By buildingEgrnAddres = By.xpath("//table[@id='table-ctrl-building-info'] //input[contains(@id, 'Address')]");
	By egrnInfoAddBld = By.id("add-ctrl-bld-btn");
	By egrnTblAddress = By
			.xpath("//table[@id='table-ctrl-building-info'] //tbody/tr[last()] //input[contains(@id, '_Address')]");
	By egrnTblOwner = By
			.xpath("//table[@id='table-ctrl-building-info'] //tbody/tr[last()] //input[contains(@id, '_Owner')]");
	By egrnTblKadastr = By
			.xpath("//table[@id='table-ctrl-building-info'] //tbody/tr[last()] //input[contains(@id, '_KadastrBld')]");
	By egrnTblUnom = By
			.xpath("//table[@id='table-ctrl-building-info'] //tbody/tr[last()] //input[contains(@id, '_Unom')]");
	By egrnTblConfirm = By
			.xpath("//table[@id='table-ctrl-building-info'] //tbody/tr[last()] //a[contains(@class, 'btn-success')]");

	// Таблица 2. Информация о договорах
	By BtnContract = By.xpath("//div[@name='nf-info-container'] //a[@title='Добавить договор']");

	// Таблица 3. Информация о помещениях в здании
	By premicyCollapse = By.xpath("//div[@name='nf-info-container']/div[3]");
	By addRoomBtn = By.id("add-room-btn");
	By roomKadastrNumber = By
			.xpath("//table[@id='table-room-info'] //tbody/tr[last()] //input[contains(@id, '_KadastrRoom')]");
	By parkingPlace = By
			.xpath("//table[@id='table-room-info'] //tbody/tr[last()] //input[contains(@id, '_ParkingPlace')]");
	By confirmPremicyAdd = By
			.xpath("//table[@id='table-room-info'] //tbody/tr[last()] //a[contains(@class, 'btn-success')]");

	
	//===============ДАННЫЕ ОБЪЕКТА===============//
	private WebElement fieldAddressKIM() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addressKIM));
		return driver.findElement(addressKIM);
	}
	private WebElement fieldObjSqr() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(objSquare));
		return driver.findElement(objSquare);
	}
	private WebElement fieldKadNum() {
		return driver.findElement(kadastrNum);
	}
	public void setAddress(String address) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				fieldAddressKIM());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		fieldAddressKIM().clear();
		fieldAddressKIM().sendKeys(address);
	}
	public void setObjSquare(String objSquare) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				fieldObjSqr());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		fieldObjSqr().clear();
		fieldObjSqr().sendKeys(objSquare);
	}
	public void pickKadNumExist(boolean kadNumExist) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(kadastrYN));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(kadastrYN));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		driver.findElement(kadastrYN).click();
		List<WebElement> decisions = driver.findElements(select2drop);
		if (kadNumExist) {
			decisions.get(0).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(kadastrNum));	
			fieldKadNum().clear();
			fieldKadNum().sendKeys(Generator.fakeKadastr());
		} else {
			decisions.get(1).click();
		}

	}
		
	//============= ТАБЛИЦА ИНФОРМАЦИЯ О ЗДАНИИ ИЗ ЕГРН ===========//
	private WebElement egrnBldInfoAddBtn() {
		return driver.findElement(egrnInfoAddBld);
	}
	public void addEgrnTable() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(addressKIM));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		String address = driver.findElement(addressKIM).getAttribute("value");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", egrnBldInfoAddBtn());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		egrnBldInfoAddBtn().click();
		driver.findElement(egrnTblAddress).sendKeys(address);
		driver.findElement(egrnTblOwner).sendKeys(owner);
		driver.findElement(egrnTblKadastr).sendKeys(kadastrNumberNf);
		driver.findElement(egrnTblUnom).sendKeys(unom);
		driver.findElement(egrnTblConfirm).click();
	}
	
	//============= ТАБЛИЦА ИНФОРМАЦИЯ О ПОМЕЩЕНИИ В ЗДАНИИ ===========//
	public void addRoomTable() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(premicyCollapse));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		boolean blockVis = driver.findElement(addRoomBtn).isDisplayed();
		if (!blockVis) {
			driver.findElement(premicyCollapse).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(addRoomBtn));
		}
		driver.findElement(addRoomBtn).click();
		driver.findElement(roomKadastrNumber).sendKeys(kadastrNumberNf);
		driver.findElement(confirmPremicyAdd).click();

	}
	
	//============= ТАБЛИЦА ИНФОРМАЦИЯ О ДОГОВОРЕ ===========//
	public void addContractTable(String contractRight) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(BtnContract));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		driver.findElement(BtnContract).click();
		ContractBld cbld = new ContractBld(driver);
		cbld.fillIn(contractRight, kadastrNumberNf);
	}
	public void addContractTable(String contractRigth, String ddMMyyyy) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(BtnContract));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		driver.findElement(BtnContract).click();
		ContractBld cbld = new ContractBld(driver);
		cbld.fillIn(contractRigth, kadastrNumberNf, ddMMyyyy);
	}
	
	//================= НУЖНО РАЗГРЕСТИ ===============//
	private WebElement okrug() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(okrug));
		return driver.findElement(okrug);
	}
	private WebElement district() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(district));
		return driver.findElement(district);
	}	
	public void setAoDistrict(String AO, String district) throws InterruptedException {
		okrug().click();
		Thread.sleep(500);
		List<WebElement> okrugs = driver.findElements(select2drop);
		for (int i = 0; i < okrugs.size(); i++) {
			String okrugName = okrugs.get(i).getText();
			if (okrugName.contains(AO)) {
				okrugs.get(i).click();
				break;
			}

		}

		district().click();
		Thread.sleep(500);
		List<WebElement> districts = driver.findElements(select2drop);
		for (int i = 0; i < districts.size(); i++) {

			String districtName = districts.get(i).getText();
			if (districtName.contains(district)) {
				districts.get(i).click();
				break;
			}

		}
	}	
	public void pickRoadAccess(boolean isAvailableFromRoad) {
		Select s = new Select(driver.findElement(roadAccess));
		if (isAvailableFromRoad) {
			s.selectByValue("True");
		} else {
			s.selectByValue("False");
		}
	}
	private WebElement explotationRigth() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(explotationRight));
		return driver.findElement(explotationRight);
	}
	public void setExplRigth(String right) {
		explotationRigth().click();

		List<WebElement> explotationRigths = driver.findElements(select2drop);

		for (int i = 0; i < explotationRigths.size(); i++) {

			String explotationRigthName = explotationRigths.get(i).getText();
			if (explotationRigthName.contains(right)) {
				explotationRigths.get(i).click();
				break;

			}
		}

	}
	public void fillCommonInfo() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				explotationRigth());
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		explotationRigth().click();
		List<WebElement> rights = driver.findElements(select2drop);
		rights.get(Generator.getRandomUpTo(rights.size())).click();
		fieldAddressKIM().clear();
		fieldAddressKIM().sendKeys(fake.address().streetAddress());
	}
	public void setObjType(String objType) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(objTypeDrop));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		boolean present = true;
		int i = 0;
		while(present && i < 2) {
			try {
				driver.findElement(clearTypeBtn).click();				
			} catch (NoSuchElementException e) {
				present = false;
				
			}
		}
		
		driver.findElement(objTypeDrop).click();
		List<WebElement> types = driver.findElements(select2drop);
		for(int j = 0; j < types.size(); j++) {
			if(types.get(j).getText().equals(objType)) {
				types.get(j).click();
			}
		}
	}
	public void setDistrict(String districtName) {
		try {
			driver.findElement(okrugRemove).click();
		} catch (Exception e) {
			
		}
		
		driver.findElement(district).click();
		List<WebElement> districts = driver.findElements(select2drop);
		for(int i = 0; i < districts.size(); i++) {
			String dist = districts.get(i).getText();
			if(dist.equals(districtName)) {
				districts.get(i).click();
			}
			else System.out.println(dist);
		}
		
	}
	public void setDistrict() {		
		String districtName = Generator.randomCAOdistrict();
		try {
			driver.findElement(okrugRemove).click();
		} catch (Exception e) {
			
		}
		
		driver.findElement(district).click();
		List<WebElement> districts = driver.findElements(select2drop);
		for(int i = 0; i < districts.size(); i++) {
			if(districts.get(i).getText().contains(districtName)) {
				districts.get(i).click();
			}
		}
		
	}
	public void setAo(String Ao) throws InterruptedException {
		okrug().click();
		Thread.sleep(500);
		List<WebElement> okrugs = driver.findElements(select2drop);
		for (int i = 0; i < okrugs.size(); i++) {
			String okrugName = okrugs.get(i).getText();
			if (okrugName.contains(Ao)) {
				okrugs.get(i).click();
				break;
			}

		}

		district().click();
		Thread.sleep(500);
		List<WebElement> districts = driver.findElements(select2drop);
		districts.get(Generator.getRandomUpTo(districts.size())).click();
		
	}
	
}