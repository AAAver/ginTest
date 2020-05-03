package inspection;

import com.github.javafaker.Faker;
import common.CommonPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InspectionPage extends CommonPage {

	WebDriver driver;
	WebDriverWait wait;
	Faker fake = new Faker(new Locale("ru"));


	public InspectionPage(WebDriver driver) {
		super(driver);
	}

	String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

	// Верхние кнопки
	By toDisposal = By.xpath("//a[@title='Поручение']");
	By toInspectionList = By.xpath("//a[@title='Список']");

	// ======== ВЕРИФИКАЦИЯ ====== //
	By toVer = By.xpath("//button[contains(.,'Верификация')]");
	By sendVerBtn = By.id("send-to-verification-btn");
	By sendVerModal = By
			.xpath("//div[@aria-describedby='send-to-verification-dialog'] //button[contains(@class, 'btn-success')]");
	By verify = By.id("verify-btn");
	By verModal = By.xpath("//div[@aria-describedby='verify-dialog'] //button[contains(@class, 'btn-success')]");

	// Переход по вкладкам
	By eventTab = By.xpath("//a[@href='#eventTab']");
	By objectTab = By.xpath("//a[@href='#objectTab']");
	By subjectTab = By.xpath("//a[@href='#subjectTab']");
	By violationTab = By.xpath("//a[@href='#violationTab']");

	// Кнопка СОХРАНИТЬ
	By saveBtn = By.xpath("//a[@title='Сохранить (Ctrl+S)']");

	// Все(ну почти все) справочники в дропдаунах
	By select2drop = By.xpath("//div[@id='select2-drop'] //ul/li");

	// ======== ВКЛАДКА [Мероприятия] ========= //
	By inspectionTheme = By.id("s2id_InspThemeCtIdM");
	By controlSubNumber = By.id("ControlSubnumber");
	By controlDate = By.id("ControlDate");
	By actDate = By.id("ActDate");
	By durationHours = By.id("DurationHours");
	By controlTime = By.id("ControlTime");
	By addInspectorBtn = By.id("add-inspector-btn"); // Кнопка [Добавить инспектора]
	By firstInspector = By.xpath("//table[@id='table-inspector-info'] //tr[1] //div[contains(@id, 'UserId')]"); 
	By isResponsibleInsp = By.xpath("//table[@id='table-inspector-info'] //tr[1] //div[contains(@id, 'IsResponsibleEnum')]");
	By inspectionResult = By.id("s2id_ResultCtIdM");
	By objectConstructionStage = By.id("s2id_ObjectConstructionStageCtId");
	By factUsage = By.id("s2id_ActualUsageCtIds");
	By representativeLastName = By.id("RepresentativeLastName");
	By representativeFirstName = By.id("RepresentativeFirstName");
	By representativePosition = By.id("s2id_RepresentativePositionCtId");
	By representativeRefuse = By.id("s2id_IsRefusalExploreAct");
	By dangerSignal = By.id("s2id_IsDangerSignal");

	// ======== ВКЛАДКА [Субъект] ========= //
	By addShdBtn = By.id("shd-catalog-btn");

	By attachFile = By.xpath("//a[@title='Добавить документ (Ctrl+D)']");

	By ubsCollapse = By.xpath("//div[@id='eventTab'] //*[contains(., 'Объект самостроя')]");
	By bindUbsBtn = By.id("bind-to-unauth-btn");
	By ubsAddress = By.id("LandAddress");
	By searchUbsBtn = By.xpath("//div[@id='bind-to-unauth-dlg'] //button[text()='Поиск']");
	By addExactUbsBtn = By.xpath("//*[@id='unauth-table-container'] //a[contains(@class,'choice-btn')]");
	By acceptUbsModal = By.xpath("//*[@id='bind-to-unauth-dlg']/following-sibling::div //button[contains(@class,'btn-success')]");


	public void verify() throws InterruptedException {
		scrollToTop();
		driver.findElement(toVer).click();
		driver.findElement(sendVerBtn).click();
		driver.findElement(sendVerModal).click();
		Thread.sleep(3000);
		driver.findElement(toVer).click();
		driver.findElement(verify).click();
		driver.findElement(verModal).click();
	}

	public String getInspectionId() {
		String[] urlParts = driver.getCurrentUrl().split("/");
		String inspId = urlParts[urlParts.length - 1];
		return inspId;
	}

	public void mainTabSwitch() throws InterruptedException {
		Thread.sleep(300);
		scrollToTop();
		click(eventTab);
	}

	public void objectTabSwitch() throws InterruptedException {
		Thread.sleep(300);
		scrollToTop();
		click(objectTab);
	}

	public void subjectTabSwitch() throws InterruptedException {
		Thread.sleep(300);
		scrollToTop();
		click(subjectTab);
	}

	public void violTabSwitch() throws InterruptedException {
		Thread.sleep(300);
		scrollToTop();
		click(violationTab);
	}

	public void saveInspection() throws InterruptedException {
		Thread.sleep(300);
		scrollToBottom();
		click(saveBtn);
	}


}
