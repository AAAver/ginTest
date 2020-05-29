package pagerepository.inspectionTask;

import oracle.sql.CharacterSetWithConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

public class InspectionTaskList extends CorePage {
    public InspectionTaskList(WebDriver driver) {
        super(driver);
    }

    By raidBtn = By.xpath("//*[@title='Список рейдов']");

    public void toRaidList(){
        while(!driver.getCurrentUrl().contains("RaidPlanTask")) {
            click(raidBtn);
        }
    }
}
