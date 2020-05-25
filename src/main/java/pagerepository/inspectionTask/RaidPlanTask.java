package pagerepository.inspectionTask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;
import pagerepository.utilities.Generator;

public class RaidPlanTask extends CorePage {
    public RaidPlanTask(WebDriver driver) {
        super(driver);
    }

    By createRaidTaskBtn = By.xpath("//*[@title='Добавить']");
    By isOpenForAdd = By.xpath("//*[@id='s2id_IsOpenedForAdding']");
    By startDate = By.xpath("//*[@id='RaidStartDate']");
    By endDate = By.xpath("//*[@id='RaidEndDate']");
    By signer = By.xpath("//*[@id='s2id_SigningDlId']");
    By save = By.xpath("//*[@title='Сохранить']");
    By toInspectionTask = By.xpath("//*[@title='Список задач']");

    public void createRaidTask(){
        int j = 0;
        while(!driver.getCurrentUrl().contains("Add") && j < 3) {
            click(createRaidTaskBtn);
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
            chooseFromDropDown(signer, "Горбунов");
            j++;
        }
        j = 0;
        click(save);
        while(!driver.getCurrentUrl().contains("InspectionTask") && j < 3) {
            click(toInspectionTask);
            j++;
        }
    }
}
