package pagerepository.plan;

import miscelaneous.Props;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pagerepository.main.DocumentSigning;
import pagerepository.main.LoginPage;
import pagerepository.main.MainPage;
import pagerepository.utilities.CorePage;
import miscelaneous.Generator;

import java.util.List;

public class RaidPlanTask extends CorePage {
    private String raidPlanTaskURL = Props.BASE_URL + "/RaidPlanTask";

    public RaidPlanTask(WebDriver driver) {
        super(driver);
    }

    By createRaidTaskBtn = By.xpath("//*[@title='Добавить']");
    By isOpenForAdd = By.xpath("//*[@id='s2id_IsOpenedForAdding']");
    By startDate = By.xpath("//*[@id='RaidStartDate']");
    By endDate = By.xpath("//*[@id='RaidEndDate']");
    By signer = By.xpath("//*[@id='s2id_SigningDlId']");
    By save = By.xpath("//*[@title='Сохранить']");
    By toInspectionTaskListButton = By.xpath("//*[@title='Список задач']");

    By raidNumbersCol = By.xpath("//*[@id='searchFormDiv']/following-sibling::table //td[1]");
    By editRaidButtonsCol = By.xpath("//*[@id='searchFormDiv']/following-sibling::table //td[7]/a");

    By sendToSignButton = By.xpath("//*[@title='Отправить на подписание']");
    By sendToSignConfirmationButton = By.xpath("//*[@id='send-to-signing-dialog']/following-sibling::div //*[contains(@class, 'btn-success')]");

    public void createRaidTask(String responsibleSigner){
        int j = 0;
        while(!driver.getCurrentUrl().contains("Add") && j < 3) {
            if(isDisplayed(createRaidTaskBtn)){
            clickJS(createRaidTaskBtn);
            } else {
                log.fatal("Кнопки Добавить нет");
                Assert.assertTrue(false,"Отсутствует кнопка Добавить рейд");
            }
            j++;
        }
        j = 0;
        while(!getActualValueFromDrop(isOpenForAdd).contains("Да") && j < 3){
            chooseFromDropDown(isOpenForAdd, "Да");
            j++;
        }
        j = 0;
        while(!getAttribute(startDate,"class").contains("dirty-input") && j < 3) {
            setDate(startDate, Generator.getCurrentDate());
            j++;
        }
        j = 0;
        while(!getAttribute(endDate,"class").contains("dirty-input") && j < 3) {
            setDate(endDate, Generator.getCurrentDate());
            j++;
        }
        j = 0;
        while(getActualValueFromDrop(signer).isBlank() && j < 3) {
            chooseFromDropDown(signer, responsibleSigner);
            j++;
        }
        j = 0;
        click(save);
    }

   public void toInspectionTaskList(){
       while(!driver.getCurrentUrl().contains("InspectionTask")) {
           click(toInspectionTaskListButton);
       }
   }

   public String getRaidNumber(){
        return getUrlTail();
   }

    public void open(String raidNumber) {
        List<WebElement> numbers = getElementList(raidNumbersCol);
        List<WebElement> buttons = getElementList(editRaidButtonsCol);
        for (int i = 0; i < numbers.size(); i++) {
            if(getText(numbers.get(i)).contains(raidNumber)){
                clickJS(buttons.get(i));
                break;
            }
        }
    }

    public void sendForSign() {
        while(!isDisplayed(sendToSignConfirmationButton)){
        clickJS(sendToSignButton);
        }
        while(isDisplayed(sendToSignConfirmationButton)){
            clickJS(sendToSignConfirmationButton);
        }
    }

    public void sign(String raidNumber, String signerLogin) throws InterruptedException {
        if(!driver.getCurrentUrl().equals(raidPlanTaskURL)){
            driver.get(raidPlanTaskURL);
        }
        open(raidNumber);
        sendForSign();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.logout();
        loginPage.loginAs(signerLogin);
        MainPage mainPage = new MainPage(driver);
        mainPage.toDocumentsForSigning();
        DocumentSigning documentSigning = new DocumentSigning(driver);
        documentSigning.sign(raidNumber);
        loginPage.logout();
    }
}
