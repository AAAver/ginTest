package pagerepository.legalcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

import java.util.List;

public class LegalCase extends CorePage {
    public LegalCase(WebDriver driver) {
        super(driver);
    }

    By validationError = By.xpath("//*[contains(@class, 'validation')]/ul/li");
    // ======= Шапка судебного дела ===== //
    By i_lastDateSendCaseToCourt = By.xpath("//span[contains(.,'срок подачи заявления')]/following-sibling::span");
    By i_caseStatus = By.xpath("//span[contains(.,'Статус дела')]/following-sibling::span[1]");

    // ======= Вкладки ====== //
    By mainTab = By.xpath("//a[@href='#main']");
    By mainTabParent = By.xpath("//a[@href='#main']/parent::li");
    By bindingsTab = By.xpath("//a[@href='#bindings']");
    By bindingsTabParent = By.xpath("//a[@href='#bindings']/parent::li");
    By documentsTab = By.xpath("//a[@href='#documents']");
    By documentsTabParent = By.xpath("//a[@href='#documents']/parent::li");

    // ======= Данные дела ======= //
    By f_CaseNumber = By.xpath("//*[@id='LcNumber']");
    By d_CaseResult = By.xpath("//*[@id='s2id_ResultCtId']");
    By d_ResponsibleLawyer = By.xpath("//*[@id='s2id_EmployeeId']");
    By d_LawyerAssistant = By.xpath("//*[@id='s2id_AssistantIds']");
    By d_AppealClaims = By.xpath("//*[@id='s2id_AssistantIds']");
    By f_RentViolation = By.xpath("//*[@id='RentPointViolaton']");
    By ta_Comments = By.xpath("//*[@id='Comments']");

    // ======== Документы ============//
    By outgoingDocument = By.xpath("//a[@title='Добавить исходящий документ']");
    By incomingDocument = By.xpath("//a[@title='Добавить входящий документ']");

    public boolean isStatusEquals(String expected){
        return getAttribute(i_caseStatus,"innerHTML").equals(expected);
    }

    public boolean isAppealClaimsEquals(String expected){
        return getActualValuesFromField(d_AppealClaims).equals(expected);
    }

    public List<String> getValidationErrors(){
        return getActualValuesFromField(validationError);
    }



}
