package pagerepository.inspections;

import miscelaneous.Generator;
import miscelaneous.Props;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pagerepository.main.DocumentSigning;
import pagerepository.main.LoginPage;
import pagerepository.main.MainPage;
import pagerepository.utilities.CorePage;

import java.util.ArrayList;
import java.util.List;

public class Disposal extends CorePage {
    private String disposalEditUrlBase = Props.BASE_URL + "/Disposals/Edit/";

    public Disposal(WebDriver driver) {
        super(driver);
    }

    By addInspection = By.xpath("//a[@title='Добавить Обследование' or @title='Добавить Проверку']");
    By reason = By.xpath("//*[@id='s2id_ReasonCtId']");
    By SubjectName = By.xpath("//*[@id='SubjectName']");
    By issueDate = By.xpath("//*[@id='IssueDate']");
    By saveButton = By.xpath("//*[@title='Сохранить (Ctrl+S)']");
    By sendForSign = By.xpath("//*[@title='Отправить на подписание']");
    By confirmSendForSign = By.xpath("//*[@aria-describedby='send-to-signing-dialog'] //button[contains(@class, 'success')]");
    By disposalNumber = By.xpath("//*[@id='DisposalNumberM']");


    public void addInspection() {
        while (!driver.getCurrentUrl().contains("Controls/AddInsp")) {
            click(addInspection);
        }
    }

    public void selectReason(String reason) {
        chooseFromDropDown(this.reason, reason);
    }

    public void selectReasonRandom() {
        chooseFromDropDownRandom(this.reason);
    }

    public void testAllReasons() {
        List<String> s = getAllPossibleValues(reason);
        for (int i = 0; i < s.size(); i++) {
            chooseFromDropDown(reason, s.get(i));
        }
    }

    public void printAllReasons() {
        List<String> s = getAllPossibleValues(reason);
        for (String s1 : s) {
            System.out.println(s1);
        }
    }

    public void setIssueDate(String date) {
        setDate(issueDate, date);
    }

    public void setIssueDate() {
        setDate(issueDate, Generator.getCurrentDate());
    }

    public void setReason(String reason){
        chooseFromDropDown(this.reason,reason);
    }
    public void setReasonRandom(){
        chooseFromDropDownRandom(this.reason);
    }

    public void save(){
        clickJS(saveButton);
    }

    public void sendForSign() {
        clickJS(sendForSign);
        clickJS(confirmSendForSign);
    }

    public String getDisposalNumber() {
        return getAttribute(disposalNumber,"value");
    }

    public void populate(){
        setIssueDate();
        setReasonRandom();
        save();
    }

    public void sign(String disposalUrl, String signerLogin) throws InterruptedException {
        if(!driver.getCurrentUrl().equals(disposalUrl)){
            driver.get(disposalUrl);
        }
        String disposalNumber = getDisposalNumber();
        sendForSign();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.logout();
        loginPage.loginAs(signerLogin);

        MainPage mainPage = new MainPage(driver);
        mainPage.toDocumentsForSigning();
        DocumentSigning documentSigning = new DocumentSigning(driver);
        documentSigning.sign(disposalNumber);
        loginPage.logout();
    }
}
