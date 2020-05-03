package inspection;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import misc.Generator;

public class InspectionActNF extends InspectionPage {

	public InspectionActNF(WebDriver driver) {
		super(driver);
	}

	Random r = new Random();

	private By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");

	// 1. Характеристики помещения@SuppressWarnings("unused")
	@SuppressWarnings("unused")
	private By premicyLocationTable = By.id("premicy-location-table");	
	private By addPremicyLocationBtn = By.id("add-ctrl-premicy-location-btn");	
	private By floors = By.xpath("//tr[last()] //div[contains(@id, 'FloorCtIds')]");
	private By facilityNumbers = By.xpath("//tr[last()] //input[contains(@id, 'FacilityNumber')]");
	private By roomNumbers = By.xpath("//tr[last()] //input[contains(@id, 'RoomsNumber')]");
	private By sepEntr = By.id("ControlPremicyInfo_HasSeparateEntrance");
	private By sepEntrDescrip = By.id("ControlPremicyInfo_SeparateEntranceThrough");
	private By techCond = By.id("s2id_ControlPremicyInfo_FacilityTechnicalConditionCtId");
	private By techCondFacade = By.id("s2id_ControlPremicyInfo_FacadeTechnicalConditionCtId");
	private By graffiti = By.id("ControlPremicyInfo_FacadeHasPictures");
	private By surroundUsage = By.id("ControlPremicyInfo_IsAdjacentTerritoryUsable");

	// 2. Соблюдение условий договора
	private By actualUsage = By.id("ControlPremicyInfo_IsActualUsage");
	private By actualUsageDescription = By.id("ControlPremicyInfo_ActualUsageDescription");
	private By replan = By.id("ControlPremicyInfo_IsReplanned");
	private By replanDescription = By.id("ControlPremicyInfo_ReplanningComment");
	private By premicyUsed = By.id("ControlPremicyInfo_IsUsed");
	private By premicyUsagePercent = By.id("ControlPremicyInfo_UsagePercent");
	private By thirdParty = By.id("ControlPremicyInfo_HasThirdPartyOrganizations");
	private By addThirdPartyBtn = By.id("add-thrd-party-org-btn");
	private By thirdPartyName = By.xpath("//input[contains(@id, '_OrganizationName')]");
	private By thirdPartyAddress = By.xpath("//input[contains(@id, '_Address')]");
	private By thirdPartyINN = By.xpath("//input[contains(@id, '_Inn')]");
	private By thirdPartySquare = By.xpath("//input[contains(@id, '_Square')]");
	private By thirdPartyPlacementCause = By.xpath("//input[contains(@id, '_PlacementCause')]");
	private By thirdPartyUsagePurpose = By.xpath("//input[contains(@id, '_UsagePurpose')]");
	private By thirdPartyTableConfirmBtn = By.xpath("//*[@id='third-party-orgs-table'] //a[1]");

	// 3. Заключение
	private By hasBanner = By.id("ControlPremicyInfo_HasBanner");
	private By isAccessible = By.id("ControlPremicyInfo_IsAccessible");
	private By accessDescription = By.id("ControlPremicyInfo_MiscellaneousDescription");
	private By previousViolations = By.id("ControlPremicyInfo_HasPreviouslyIdentifiedViolations");
	private By previousViolationDate = By.id("ControlPremicyInfo_PreviouslyIdentifiedViolationDate");
	private By previousReplanViolation = By.id("ControlPremicyInfo_IsReplanningViolation");
	private By previousReplanViolationFixed = By.id("ControlPremicyInfo_IsReplanningViolationFixed");
	private By previousThirdPartyViolation = By.id("ControlPremicyInfo_IsThirdPartyUserViolation");
	private By previousThirdPartyViolationFixed = By.id("ControlPremicyInfo_IsThirdPartyUserViolationFixed");
	private By previousNonPurposeUsage = By.id("ControlPremicyInfo_IsNonPurposeUsageViolation");
	private By previousNonPurposeUsageFixed = By.id("ControlPremicyInfo_IsNonPurposeUsageViolationFixed");
	
	
	
	private WebElement addPremicyLocationBtn() {
		return driver.findElement(addPremicyLocationBtn);
	}

	private WebElement floors() {
		return driver.findElement(floors);
	}

	private WebElement facilityNumbers() {
		return driver.findElement(facilityNumbers);
	}

	private WebElement roomNumbers() {
		return driver.findElement(roomNumbers);
	}
	
	private WebElement addThirdPartyBtn() {
		return driver.findElement(addThirdPartyBtn);
	}

	private WebElement thirdPartyName() {
		return driver.findElement(thirdPartyName);
	}

	private WebElement thirdPartyAddress() {
		return driver.findElement(thirdPartyAddress);
	}

	private WebElement thirdPartyINN() {
		return driver.findElement(thirdPartyINN);
	}

	private WebElement thirdPartySquare() {
		return driver.findElement(thirdPartySquare);
	}

	private WebElement thirdPartyPlacementCause() {
		return driver.findElement(thirdPartyPlacementCause);
	}

	private WebElement thirdPartyUsagePurpose() {
		return driver.findElement(thirdPartyUsagePurpose);
	}

	private WebElement thirdPartyTableConfirmBtn() {
		return driver.findElement(thirdPartyTableConfirmBtn);
	}

	//==================ХАРАКТЕРИСТИКИ ПОМЕЩЕНИЯ===================//
	public void hasSeparateEntr(boolean ent) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(sepEntr));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		List<WebElement> decisions = driver.findElements(sepEntr);
		if (ent) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
			driver.findElement(sepEntrDescrip).sendKeys("Проход перегорожен мусором");
		}
	}

	public void techCond(int techCondition) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(techCond));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(techCond).click();
		List<WebElement> conditions = driver.findElements(select2drop);
		if (techCondition == 1) {
			conditions.get(2).click();
		} else if (techCondition == 2) {
			conditions.get(1).click();
		} else {
			conditions.get(0).click();
		}
	}

	public void techCondFacade(int facadeCondition) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(techCondFacade));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		driver.findElement(techCondFacade).click();
		List<WebElement> conditions = driver.findElements(select2drop);
		if (facadeCondition == 1) {
			conditions.get(2).click();
		} else if (facadeCondition == 2) {
			conditions.get(1).click();
		} else {
			conditions.get(0).click();
		}

	}

	public void hasGraffity(boolean graffity) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(graffiti));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		
		List<WebElement> answers = driver.findElements(graffiti);
		if (graffity) {
			answers.get(0).click();
		} else {
			answers.get(1).click();
		}
	}

	public void isAdditionalUsagePossible(boolean addUsePossibility) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(surroundUsage));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> answers = driver.findElements(surroundUsage);
		if(answers == null || answers.size() < 1){
			Thread.sleep(1000);
			answers = driver.findElements(surroundUsage);
		}
		if (addUsePossibility) {
			answers.get(0).click();
		} else {
			answers.get(1).click();
		}
	}
	//==================СОБЛЮДЕНИЕ УСЛОВИЙ ДОГОВОРА===================//
	public void isActualUsage(boolean usage) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(actualUsage));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(actualUsage);
		if (usage) {
			decisions.get(0).click();
		} else
			decisions.get(1).click();
	}

	public void isActualUsage(String usageDescrip) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(actualUsage));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(actualUsage);
		decisions.get(2).click();
		driver.findElement(actualUsageDescription).sendKeys("Дополнительная информация");
	}

	public void isReplanned(boolean isReplanned) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(replan));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(replan);
		if (isReplanned) {
			decisions.get(0).click();
			driver.findElement(replanDescription).sendKeys("Произведена перепланировка помещения без согласования");
		} else {
			decisions.get(1).click();
		}
	}
	
	public void isPremicyUsed(boolean premicyIsUsed) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(premicyUsed));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(premicyUsed);
		if (premicyIsUsed) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void premicyUsedPartially(int usagePercent) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(premicyUsed));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(premicyUsed);
		if (usagePercent > 0 && usagePercent < 100) {
			decisions.get(2).click();
			driver.findElement(premicyUsagePercent).clear();
			driver.findElement(premicyUsagePercent).sendKeys(Integer.toString(usagePercent));
		} else {
			decisions.get(1).click();
			System.out.println("Процент использования должен быть в диапазоне от 1 до 99");
		}
	}

	public void isThirdParty(boolean thirdPartyUsesTheBuilding) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(thirdParty));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(thirdParty);
		if (thirdPartyUsesTheBuilding) {
			decisions.get(0).click();	
			wait.until(ExpectedConditions.visibilityOfElementLocated(addThirdPartyBtn));
			driver.findElement(addThirdPartyBtn).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(thirdPartyName));
			driver.findElement(thirdPartyName).sendKeys(fake.company().name());
			driver.findElement(thirdPartyAddress).sendKeys(fake.address().streetAddress());
			driver.findElement(thirdPartyINN).sendKeys(Long.toString(fake.number().randomNumber(12, true)));
			driver.findElement(thirdPartySquare).clear();
			driver.findElement(thirdPartySquare).sendKeys(Integer.toString(fake.number().numberBetween(1, 600)));
			driver.findElement(thirdPartyPlacementCause).sendKeys("Неустановленные причины");
			driver.findElement(thirdPartyUsagePurpose).sendKeys("Цели не выяснены");
			driver.findElement(thirdPartyTableConfirmBtn).click();
		} else {
			decisions.get(1).click();
		}
	}
	
	//==================ЗАКЛЮЧЕНИЕ===================//	
	public void hasBanner(boolean banner) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(hasBanner));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(hasBanner);
		if (banner) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void isAccessible(boolean accessible) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(isAccessible));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(isAccessible);
		if (accessible) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void isAccessible(String description) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(isAccessible));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(isAccessible);
		decisions.get(2).click();
		driver.findElement(accessDescription).sendKeys(description);
	}

	//==================ВЫЯВЛЕННЫЕ РАНЕЕ НАРУШЕНИЯ===================//	
	public void previousViolations(boolean violations) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousViolations));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousViolations);
		if (violations) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void previousViolationDate() {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousViolationDate));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		driver.findElement(previousViolationDate).sendKeys(Generator.fakeDatePast());
	}
	
	public void previousReplanViolation(boolean prevReplanViol) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousReplanViolation));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousReplanViolation);
		if (prevReplanViol) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void previousReplanViolationFixed(boolean prevReplanViolFixed) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousReplanViolationFixed));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousReplanViolationFixed);
		if (prevReplanViolFixed) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void previousThirdPartyViolation(boolean prevThirdPartyViol) {		;
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousThirdPartyViolation));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousThirdPartyViolation);
		if (prevThirdPartyViol) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void previousThirdPartyViolationFixed(boolean prevThirdPartyViolFixed) {	
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousThirdPartyViolationFixed));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousThirdPartyViolationFixed);
		if (prevThirdPartyViolFixed) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void previousNonPurposeUsage(boolean prevNonPurpUsage) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousNonPurposeUsage));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousNonPurposeUsage);
		if (prevNonPurpUsage) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}

	public void previousNonPurposeUsageFixed(boolean prevNonPurpUsageFixed) {		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(previousNonPurposeUsageFixed));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		
		List<WebElement> decisions = driver.findElements(previousNonPurposeUsageFixed);
		if (prevNonPurpUsageFixed) {
			decisions.get(0).click();
		} else {
			decisions.get(1).click();
		}
	}
	//==================ЗАПОЛНЕНИЕ НЕВАЖНОЙ ИНФОРМАЦИИ===================//
	public void populateCommonInformation() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(addPremicyLocationBtn));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(addPremicyLocationBtn));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");

		Actions a = new Actions(driver);
		addPremicyLocationBtn().click();
		a.moveToElement(floors()).click().sendKeys(Integer.toString(Generator.getRandomUpTo(100))).sendKeys(Keys.ENTER)
				.build().perform();
		facilityNumbers().sendKeys(Integer.toString(Generator.getRandomUpTo(200)));
		roomNumbers().sendKeys(Integer.toString(Generator.getRandomUpTo(999)));
		hasSeparateEntr(r.nextBoolean());
//		techCond((int)Math.floor(Math.random()*3));
//		techCondFacade((int)Math.floor(Math.random()*3));
		hasGraffity(r.nextBoolean());
		isAdditionalUsagePossible(r.nextBoolean());

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(hasBanner));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)");
		hasBanner(r.nextBoolean());
		isAccessible(r.nextBoolean());
	}

}
