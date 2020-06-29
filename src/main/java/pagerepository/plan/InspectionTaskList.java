package pagerepository.plan;

import oracle.sql.CharacterSetWithConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pagerepository.utilities.CorePage;
import tests.utils.Listeners;

import java.util.List;

public class InspectionTaskList extends CorePage {
    public InspectionTaskList(WebDriver driver) {
        super(driver);
    }

    By raidBtn = By.xpath("//*[@title='Список рейдов']");

    By addTaskDropdownButton = By.xpath("//button[contains(.,'Добавить задачу')]");
    By addTaskDropdownMenu = By.xpath("//button[contains(.,'Добавить задачу')]/following-sibling::ul");
    By addTaskZu = By.xpath("//a[@title='Ручной ввод ЗУ']");
    By addTaskNf = By.xpath("//a[@title='Ручной ввод НФ']");
    By addToRaidPlan = By.xpath("//a[@title='Добавить в ПРЗ']");

    By checkboxesTdCol = By.xpath("//div[@id='searchFormDiv']/following-sibling::table //td[1]");
    By addressCol = By.xpath("//div[@id='searchFormDiv']/following-sibling::table //td[7]");
    By checkbox = By.xpath("//input[@type='checkbox']");

    By raidNumbersCol = By.xpath("//div[@id='raids-container'] //table //td[1]");
    By addToRaidPlusesCol = By.xpath("//div[@id='raids-container'] //table //td[7]");
    By confirmRaidDialog = By.xpath("//*[@id='add-addresses-to-raid-dialog']/following-sibling::div //button[contains(@class, 'btn-success')]");
    By toInspectionTaskAddressButton = By.xpath("//*[@title='Редактирование адреса']");



    public RaidPlanTask toRaidList(){
        while(!driver.getCurrentUrl().contains("RaidPlanTask")) {
            click(raidBtn);
        }
        return new RaidPlanTask(driver);
    }

    public InspectionTask addTaskZu(){
        while(!isDisplayed(addTaskDropdownMenu)){
            clickJS(addTaskDropdownButton);
        }
        clickJS(addTaskZu);

        if(driver.getCurrentUrl().contains("InspectionTask/Add?")){
            log.info("On add InspectionTask page");
        }else{
            log.fatal("Inspection task adding was not open. Wrong url");
        }

        return new InspectionTask(driver);
    }

    public void addTaskNf(){
        while(!isDisplayed(addTaskDropdownMenu)){
            clickJS(addTaskDropdownButton);
        }
        clickJS(addTaskNf);
    }

    public void addTaskToRaid(String taskAddress, String raidNumber){
        List<WebElement> addresses = getElementList(addressCol);
        List<WebElement> checkboxes = getElementList(checkboxesTdCol);
        for (int i = 0; i < addresses.size(); i++) {
            if(addresses.get(i).getText().contains(taskAddress)){
                WebElement checkbox = checkboxes.get(i).findElement(this.checkbox);
                clickJS(checkbox);
                break;
            }
        }
        clickJS(addToRaidPlan);

        List<WebElement> raidNumbers = getElementList(raidNumbersCol);
        List<WebElement> pluses = getElementList(addToRaidPlusesCol);
        for (int i = 0; i < raidNumbers.size(); i++) {
            if(raidNumbers.get(i).getText().contains(raidNumber)){
                WebElement plus = pluses.get(i);
                click(plus);
                break;
            }
        }

        clickJS(confirmRaidDialog);



    }

    public void openInspectionTaskAddress(String fakeAddress) {
        List<WebElement> addresses = getElementList(addressCol);
        List<WebElement> buttons = getElementList(toInspectionTaskAddressButton);
        for (int i = 0; i < addresses.size(); i++) {
            if(getText(addresses.get(i)).contains(fakeAddress)){
                clickJS(buttons.get(i-1));
                break;
            }
        }

        if(!driver.getCurrentUrl().contains("InspectionTaskAddress/Edit")){
            log.fatal("Inspection task address was not open");
        } else{
            log.info("Inspection task address opened");
        }
    }
}
