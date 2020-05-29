package pagerepository.inspection;

import pagerepository.utilities.CorePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectionPage extends CorePage {

	public InspectionPage(WebDriver driver) {super(driver);	}

	String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

	// Верхние кнопки
	By toDisposal = By.xpath("//a[@title='Поручение']");
	By toInspectionList = By.xpath("//a[@title='Список']");

	// ======== ВЕРИФИКАЦИЯ ====== //
	By toVer = By.xpath("//button[contains(.,'Верификация')]");
	By sendVerBtn = By.xpath("//*[@id='send-to-verification-btn']");
	By sendVerModal = By
			.xpath("//div[@aria-describedby='send-to-verification-dialog'] //button[contains(@class, 'btn-success')]");
	By verify = By.xpath("//*[@id='verify-btn']");
	By verModal = By.xpath("//div[@aria-describedby='verify-dialog'] //button[contains(@class, 'btn-success')]");

	// Переход по вкладкам
	By eventTab = By.xpath("//a[@href='#eventTab']");
	By eventTabParent = By.xpath("//a[@href='#eventTab']/parent::li");
	By objectTab = By.xpath("//a[@href='#objectTab']");
	By objectTabParent = By.xpath("//a[@href='#objectTab']/parent::li");
	By subjectTab = By.xpath("//a[@href='#subjectTab']");
	By subjectTabParent = By.xpath("//a[@href='#subjectTab']/parent::li");
	By violationTab = By.xpath("//a[@href='#violationTab']");
	By violationTabParent = By.xpath("//a[@href='#violationTab']/parent::li");

	// Кнопка СОХРАНИТЬ
	By saveBtn = By.xpath("//a[@title='Сохранить (Ctrl+S)']");
	By saveWarning = By.xpath("//a[@title='Сохранить']");

	// Все(ну почти все) справочники в дропдаунах


	// ======== ВКЛАДКА [Мероприятия] ========= //
	public static By inspectionTheme = By.xpath("//*[@id='s2id_InspThemeCtIdM']");
	By controlSubNumber = By.xpath("//*[@id='ControlSubnumber']");
	By controlDate = By.xpath("//*[@id='ControlDate']");
	By actDate = By.xpath("//*[@id='ActDate']");
	By durationHours = By.xpath("//*[@id='DurationHours']");
	By controlTime = By.xpath("//*[@id='ControlTime']");
	By addInspectorBtn = By.xpath("//*[@id='add-inspector-btn']"); // Кнопка [Добавить инспектора]
	By firstInspector = By.xpath("//table[@id='table-inspector-info'] //tr[1] //div[contains(@id, 'UserId')]"); 
	By isResponsibleInsp = By.xpath("//table[@id='table-inspector-info'] //tr[1] //div[contains(@id, 'IsResponsibleEnum')]");
	By inspectionResult = By.xpath("//*[@id='s2id_ResultCtIdM']");
	By objectConstructionStage = By.xpath("//*[@id='s2id_ObjectConstructionStageCtId']");
	By factUsage = By.xpath("//*[@id = 's2id_ActualUsageCtIds']");
	By representativeLastName = By.xpath("//*[@id='RepresentativeLastName']");
	By representativeFirstName = By.xpath("//*[@id='RepresentativeFirstName']");
	By representativePosition = By.xpath("//*[@id='s2id_RepresentativePositionCtId']");
	By representativeRefuse = By.xpath("//*[@id='s2id_IsRefusalExploreAct']");
	By dangerSignal = By.xpath("//*[@id='s2id_IsDangerSignal']");
	By attachFile = By.xpath("//a[@title='Добавить документ (Ctrl+D)']");
	By ubsCollapse = By.xpath("//div[@id='eventTab'] //*[contains(., 'Объект самостроя')]");
	By bindUbsBtn = By.xpath("//*[@id='bind-to-unauth-btn']");
	By ubsAddress = By.xpath("//*[@id='LandAddress']");
	By searchUbsBtn = By.xpath("//div[@id='bind-to-unauth-dlg'] //button[text()='Поиск']");
	By addExactUbsBtn = By.xpath("//*[@id='unauth-table-container'] //a[contains(@class,'choice-btn')]");
	By acceptUbsModal = By.xpath("//*[@id='bind-to-unauth-dlg']/following-sibling::div //button[contains(@class,'btn-success')]");
	By kadastrNum = By.xpath("//*[@id='KadastrZu']");

	// Данные объекта
	By okrug = By.xpath("//*[@id='s2id_AoIdM']");
	By okrugRemove = By.xpath("//*[@id='s2id_AoIdM'] //abbr");
	By district = By.xpath("//*[@id='s2id_DistrictIdM']");
	By addressKIM = By.xpath("//*[@id='AddressM_Address']");
	By roadAccess = By.xpath("//*[@id='IsAvailableFromRoadway']");
	By objSquare = By.xpath("//*[@id='SquareM']");
	By explotationRight = By.xpath("//*[@id='s2id_ExploitationRightCtId']");
	By kadastrYN = By.xpath("//*[@id='s2id_HasKadastrZuRegistration']");

	By objTypeDrop = By.xpath("//*[@id='s2id_ObjectTypeCtIds']");
	By clearTypeBtn = By.xpath("//*[@id='s2id_ObjectTypeCtIds'] //a");


	// Таблица 1. Иформация о здании из ЕГРН
	By egrnTableAdd = By.xpath("//*[@id='add-ctrl-bld-btn']");
	By buildingEgrnAddres = By.xpath("//table[@id='table-ctrl-building-info'] //input[contains(@id, 'Address')]");
	By egrnInfoAddBld = By.xpath("//*[@id='add-ctrl-bld-btn']");
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
	By addRoomBtn = By.xpath("//*[@id='add-room-btn']");
	By roomKadastrNumber = By
			.xpath("//table[@id='table-room-info'] //tbody/tr[last()] //input[contains(@id, '_KadastrRoom')]");
	By parkingPlace = By
			.xpath("//table[@id='table-room-info'] //tbody/tr[last()] //input[contains(@id, '_ParkingPlace')]");
	By confirmPremicyAdd = By
			.xpath("//table[@id='table-room-info'] //tbody/tr[last()] //a[contains(@class, 'btn-success')]");


	//============== АКТ НФ =================//
	// 1. Характеристики помещения
	By premicyLocationTable = By.xpath("//*[@id='premicy-location-table']");
	By addPremicyLocationBtn = By.xpath("//*[@id='add-ctrl-premicy-location-btn']");
	By floors = By.xpath("//tr[last()] //div[contains(@id, 'FloorCtIds')]");
	By facilityNumbers = By.xpath("//tr[last()] //input[contains(@id, 'FacilityNumber')]");
	By roomNumbers = By.xpath("//tr[last()] //input[contains(@id, 'RoomsNumber')]");
	By separateEntranceTrue = By.xpath("//*[@id = 'ControlPremicyInfo_HasSeparateEntrance' and @value ='True']/parent::label");
	By separateEntranceFalse = By.xpath("//*[@id = 'ControlPremicyInfo_HasSeparateEntrance' and @value ='False']/parent::label");
	By sepEntrDescrip = By.xpath("//*[@id='ControlPremicyInfo_SeparateEntranceThrough']");
	By techCond = By.xpath("//*[@id='s2id_ControlPremicyInfo_FacilityTechnicalConditionCtId']");
	By techCondFacade = By.xpath("//*[@id='s2id_ControlPremicyInfo_FacadeTechnicalConditionCtId']");
	By graffitiTrue = By.xpath("//*[@id = 'ControlPremicyInfo_FacadeHasPictures' and @value ='True']/parent::label");
	By graffitiFalse = By.xpath("//*[@id = 'ControlPremicyInfo_FacadeHasPictures' and @value ='False']/parent::label");
	By territoryUsableTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsAdjacentTerritoryUsable' and @value ='True']/parent::label");
	By territoryUsableFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsAdjacentTerritoryUsable' and @value ='False']/parent::label");
	// 2. Соблюдение условий договора
	By actualUsageTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsActualUsage' and @value ='True']/parent::label");
	By actualUsageFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsActualUsage' and @value ='False']/parent::label");
	By actualUsageOther = By.xpath("//*[@id = 'ControlPremicyInfo_IsActualUsage' and @value ='']/parent::label");
	By actualUsageDescription = By.xpath("//*[@id='ControlPremicyInfo_ActualUsageDescription']");
	By replanTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsReplanned' and @value ='True']/parent::label");
	By replanFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsReplanned' and @value ='False']/parent::label");
	By replanDescription = By.xpath("//*[@id='ControlPremicyInfo_ReplanningComment']");
	By premicyUsedTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsUsed' and @value ='True']/parent::label");
	By premicyUsedFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsUsed' and @value ='False']/parent::label");
	By premicyUsedPartially = By.xpath("//*[@id = 'ControlPremicyInfo_IsUsed' and @value ='']/parent::label");
	By premicyUsedPercent = By.xpath("//*[@id='ControlPremicyInfo_UsagePercent']");
	By thirdPartyTrue = By.xpath("//*[@id = 'ControlPremicyInfo_HasThirdPartyOrganizations' and @value ='True']/parent::label");
	By thirdPartyFalse = By.xpath("//*[@id = 'ControlPremicyInfo_HasThirdPartyOrganizations' and @value ='False']/parent::label");
	By thirdPartyAddTableBtn = By.xpath("//*[@id='add-thrd-party-org-btn']");
	By thirdPartyName = By.xpath("//input[contains(@id, '_OrganizationName')]");
	By thirdPartyAddress = By.xpath("//input[contains(@id, '_Address')]");
	By thirdPartyINN = By.xpath("//input[contains(@id, '_Inn')]");
	By thirdPartySquare = By.xpath("//input[contains(@id, '_Square')]");
	By thirdPartyPlacementCause = By.xpath("//input[contains(@id, '_PlacementCause')]");
	By thirdPartyUsagePurpose = By.xpath("//input[contains(@id, '_UsagePurpose')]");
	By thirdPartyTableConfirmBtn = By.xpath("//*[@id='third-party-orgs-table'] //a[1]");
	// 3. Заключение
	By bannerTrue = By.xpath("//*[@id = 'ControlPremicyInfo_HasBanner' and @value ='True']/parent::label");
	By bannerFalse = By.xpath("//*[@id = 'ControlPremicyInfo_HasBanner' and @value ='False']/parent::label");
	By accessibleTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsAccessible' and @value ='True']/parent::label");
	By accessibleFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsAccessible' and @value ='False']/parent::label");
	By accessibleOther = By.xpath("//*[@id = 'ControlPremicyInfo_IsAccessible' and @value ='']/parent::label");
	By accessDescription = By.xpath("//*[@id='ControlPremicyInfo_MiscellaneousDescription']");
	By previousViolationsTrue = By.xpath("//*[@id = 'ControlPremicyInfo_HasPreviouslyIdentifiedViolations' and @value ='True']/parent::label");
	By previousViolationsFalse = By.xpath("//*[@id = 'ControlPremicyInfo_HasPreviouslyIdentifiedViolations' and @value ='False']/parent::label");
	By previousViolationDate = By.xpath("//*[@id='ControlPremicyInfo_PreviouslyIdentifiedViolationDate']");
	By previousReplanTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsReplanningViolation' and @value ='True']/parent::label");
	By previousReplanFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsReplanningViolation' and @value ='False']/parent::label");
	By previousReplanFixedTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsReplanningViolationFixed' and @value ='True']/parent::label");
	By previousReplanFixedFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsReplanningViolationFixed' and @value ='False']/parent::label");
	By previousThirdPartyTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsThirdPartyUserViolation' and @value ='True']/parent::label");
	By previousThirdPartyFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsThirdPartyUserViolation' and @value ='False']/parent::label");
	By previousThirdPartyFixedTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsThirdPartyUserViolationFixed' and @value ='True']/parent::label");
	By previousThirdPartyFixedFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsThirdPartyUserViolationFixed' and @value ='False']/parent::label");
	By previousNonPurposeUsageTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsNonPurposeUsageViolation' and @value ='True']/parent::label");
	By previousNonPurposeUsageFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsNonPurposeUsageViolation' and @value ='False']/parent::label");
	By previousNonPurposeUsageFixedTrue = By.xpath("//*[@id = 'ControlPremicyInfo_IsNonPurposeUsageViolationFixed' and @value ='True']/parent::label");
	By previousNonPurposeUsageFixedFalse = By.xpath("//*[@id = 'ControlPremicyInfo_IsNonPurposeUsageViolationFixed' and @value ='False']/parent::label");

	// ================== ВКЛАДКА СУБЪЕКТ ================ //
	// Вкладка [Субъект]
	By addShdBtn = By.xpath("//*[@id='shd-catalog-btn']");
	// Таблица с СХД
	By shdTable = By.xpath("//div[@id='shd-calatogs-search-result']/table");
	By shdNames = By.xpath("//*[@id='shd-calatogs-search-result']/table/tbody/tr/td[4]");
	By addButtons = By.xpath("//*[@id='shd-calatogs-search-result']/table/tbody/tr/td[8] //a");
	By accept = By.xpath(
			"//*[@aria-describedby='modalDialog6'] //div[@class='ui-dialog-buttonpane ui-widget-content ui-helper-clearfix'] //button[@class='btn btn-xs btn-success']");
	By searchField = By.xpath("//*[@id='FullName']");
	By searchBtn = By.xpath("//div[@id='searchFormDiv'] //button[text()='Поиск']");
	// Вкладки СХД
	By mainTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Основные')]");
	By addressTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Адреса')]");
	By employeeTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Данные сотрудника')]");
	By bankTab = By.xpath("//*[@id='tabShdSnapshot'] //a[contains(.,'Банковские')]");
	// Вкладка [Основные]
	By headPostTitle = By.xpath("//*[@id='HeadPostTitle']");
	By headLastName = By.xpath("//*[@id='HeadLastName']");
	By headFirstName = By.xpath("//*[@id='HeadFirstName']");
	// Вкладка [Адреса]
	By shdAddressTypes = By.xpath("//*[@id='address-table'] //td[8]");
	By shdAddressChkbox = By.xpath("//*[@id='address-table'] //td[9] //input[@type='checkbox']");
	// Вкладка [Данные сотрудника]
	By emplLastName = By.xpath("//*[@id='ShdEmployeeM_Surname']");
	By emplFirstName = By.xpath("//*[@id='ShdEmployeeM_FirstName']");
	By emplPosition = By.xpath("//*[@id='s2id_ShdEmployeeM_PostCtId']");
	By snapshot = By.xpath("//*[@id='shd-snapshot-info-container'] //label[@for='ShortName']");

	// ============= ВКЛАДКА НАРУШЕНИЯ ============= //
	// К проверке
	By toInsp = By.xpath("//a[@title='Проверка']");
	By warningBtn = By.xpath("//*[@id='add-perimeter-control-btn']");
	By subNumber = By.xpath("//*[@id='SubNumber']");
	By calendar = By.xpath("//*[@id='ui-datepicker-div']");
	By date = By.xpath("//*[@id='Date']");
	By koapArticle = By.xpath("//*[@id='s2id_KoApArticleCtId']");
	By inspector = By.xpath("//*[@id='s2id_InspectorId']");
	By deliveryType = By.xpath("//*[@id='s2id_DeliveryTypeCtId']");
	By violatorType = By.xpath("//*[@id='s2id_ViolatorTypeCtId']");
	By content = By.xpath("//*[@id='Content']");
	// Добавить нарушение
	By addViolBtn = By.xpath("//*[@id='add-violation-btn']");
	By violTypeDrop = By.xpath("//*[@id='table-violation-info'] //tbody/tr[last()] //div[contains(@id, '_ViolationCtId')]");
	By apArticleDrop = By.xpath("//*[@id='table-violation-info'] //tbody/tr[last()] //div[contains(@id, '_ApArticleCtId')]");
	By violSuccessBtn = By.xpath("//*[@id='table-violation-info'] //tbody/tr[last()] //a[contains(@class, 'btn-success')]");
	// Таблица с нарушениями
	By tableViolPlus = By.xpath("//*[@id='table-violation-info'] //tbody/tr //a[contains(@class, 'bigger')]");
	By addProtocol = By.xpath("//*[@id='table-violation-info'] //tbody/tr //a[@title='Добавить протокол']");
	By addPrescription = By.xpath("//*[@id='table-violation-info'] //tbody/tr //a[@title='Добавить предписание/уведомление']");
	By violationLabel = By.xpath("//label[contains(@for, '_ViolationCtId')]");

	public void verify() throws InterruptedException {
		scrollToTop();
		click(toVer);
		click(sendVerBtn);
		click(sendVerModal);
		Thread.sleep(3000);
		click(toVer);
		click(verify);
		click(verModal);
	}

	public void mainTabSwitch(){
		scrollToTop();
		while(!getAttribute(eventTabParent, "class").contains("active")) {
			click(eventTab);
		}
	}

	public void objectTabSwitch(){
		scrollToTop();
		while(!getAttribute(objectTabParent, "class").contains("active")) {
			click(objectTab);
		}
	}

	public void subjectTabSwitch(){
		scrollToTop();
		while(!getAttribute(subjectTabParent, "class").contains("active")) {
			click(subjectTab);
		}
	}

	public void violTabSwitch(){
		scrollToTop();
		while(!getAttribute(violationTabParent, "class").contains("active")) {
			click(violationTab);
		}
	}

	public void saveInspection(){
		scrollToBottom();
		click(saveBtn);
	}

	public By getByByName (String name) throws NoSuchFieldException, IllegalAccessException {
		return (By) InspectionPage.class.getDeclaredField(name).get(this);
	}

}
