package pagerepository.inspections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

public class DisposalsList extends CorePage {
    public DisposalsList(WebDriver driver) {
        super(driver);
    }

    By b_filter = By.xpath("//a[text()='Фильтр']");
    By d_objectTypes = By.xpath("//*[@id='s2id_ObjectTypesCtId']");
    By d_inspectDepartment = By.xpath("//*[@id='s2id_DepartmentId']");
    By b_submitFilter = By.xpath("//button[text()='Поиск']");
    By b_toDisposal = By.xpath("//tbody //td[7]/a");
    By div_searchForm = By.xpath("//*[@id='searchFormDiv']");


    public void toInspectionZuDisposal() {
        while (!getAttribute(div_searchForm, "style").contains("display: block")) {
            click(b_filter);
        }
        chooseFromDropDown(d_objectTypes, "ЗУ");
        chooseFromDropDown(d_inspectDepartment, "ЦАО");
        click(b_submitFilter);
        while (!driver.getCurrentUrl().contains("Disposals/Edit")) {
            click(b_toDisposal);
        }
    }

    public void toInspectionNfDisposal() {
        while (!getAttribute(div_searchForm, "style").contains("display: block")) {
            click(b_filter);
        }
        chooseFromDropDown(d_objectTypes, "НФ");
        chooseFromDropDown(d_inspectDepartment, "ЦАО");
        click(b_submitFilter);
        while (!driver.getCurrentUrl().contains("Disposals/Edit")) {
            click(b_toDisposal);
        }
    }


}
