package pagerepository.legalcase;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pagerepository.utilities.CorePage;
import miscelaneous.Generator;

import java.util.ArrayList;
import java.util.List;

public class DgiLegalCase extends CorePage {
    public DgiLegalCase(WebDriver driver) {
        super(driver);
    }

    By searchFilterBtn = By.xpath("//*[@title='Фильтр']");
    By div_searchForm = By.xpath("//*[@id='searchFormDiv']");
    By d_ubsBinding = By.xpath("//*[@id='s2id_IsBinding']");
    By d_courtDecision = By.xpath("//*[@id='s2id_SudResultClass']");
    By d_impossibleToBind = By.xpath("//*[@id='s2id_IsNoBindingM']");
    By b_searchSubmit = By.xpath("//*[text()='Поиск']");
    By searchTableRows = By.xpath("//table[@id='gridTable']/tbody/tr");

    By gridTablePagesInput = By.xpath("//*[@id='input_gridTable-pager']/input");
    By gridTablePagesNumber = By.xpath("//*[@id='input_gridTable-pager']/span");
    By goToCaseBtn = By.xpath("//table[@id='gridTable'] //a");

    By legalCaseForceDate = By.xpath("//*[@id='LegalForceDate']");
    By toListBtn = By.xpath("//*[@title='Список']");

    By bindToUnauthBtn = By.xpath("//*[@id='bind-to-unauth-btn']");
    By unauthAddress = By.xpath("//*[@id='LandAddress']");
    By searchBtn = By.xpath("//*[text()='Поиск']");
    By addExactUbs = By.xpath("//*[@id='unauth-table-container'] //a[contains(@class,'choice-btn')]");
    By acceptAddedUbs = By.xpath("//*[@id='bind-to-unauth-dlg']/following-sibling::div //button[contains(@class,'btn-success')]");
    By saveBtn = By.xpath("//*[@title='Сохранить']");

    By spanSaved = By.xpath("//span[text()='Данные сохранены']");

    private void connectUbs(String ubsAddress) {
        while (!isDisplayed(unauthAddress)) {
            click(bindToUnauthBtn);
        }
        writeText(unauthAddress, ubsAddress);
        click(searchBtn);
        click(addExactUbs);
        click(acceptAddedUbs);
    }

    public void filter(String filter) {
        while (!isDisplayed(div_searchForm)) {
            click(searchFilterBtn);
        }
        chooseFromDropDown(d_ubsBinding, "Нет");
        chooseFromDropDown(d_courtDecision, filter);
        chooseFromDropDown(d_impossibleToBind, "Нет");
        clickJS(b_searchSubmit);
        waitVisibilityMultipleElements(searchTableRows);
    }

    public void findLegalCaseForUbs(String ubsAddress) throws InterruptedException {
        filter("УДОВЛЕТВОРЕНО В ТРЕБОВАНИЯХ");
        int pagesCount = Integer.parseInt(getAttribute(gridTablePagesNumber, "innerHTML"));
        List<String> pages = new ArrayList<>();
        for (int i = 1; i < pagesCount + 1; i++) {
            pages.add(Integer.toString(i));
        }
        boolean caseFound = false;

        do {
            for (int i = 0; i < pages.size(); i++) {
                String pageToShow = pages.get(random.nextInt(pages.size()));
                pages.remove(pageToShow);
                goToPage(pageToShow);

                if(isStale(goToCaseBtn)){driver.get(driver.getCurrentUrl());}

                int caseNumber = getElementList(goToCaseBtn).size();
                for (int j = 0; j < caseNumber; j++) {
                    List<WebElement> cases = getElementList(goToCaseBtn);
                    while (!driver.getCurrentUrl().contains("Edit")) {
                        clickJS(cases.get(j));
                    }
                    if (getAttribute(legalCaseForceDate, "value").isEmpty() && getAttribute(legalCaseForceDate, "readonly") == null) {
                        caseFound = true;
                        connectUbs(ubsAddress);
                        break;
                    } else {
                        clickJS(toListBtn);
                    }
                }
                if (caseFound) {
                    break;
                }
            }
        } while (!caseFound);

    }


    private void goToPage(String pageToJump) {
        clearField(gridTablePagesInput);
        writeText(gridTablePagesInput, pageToJump);
        driver.findElement(gridTablePagesInput).sendKeys(Keys.chord(Keys.ENTER));
    }


    public void setLegalForceDate() {
        setDate(legalCaseForceDate, Generator.getCurrentDatePlus5());
    }

    public void setLegalCaseForceDate(String date) {
        setDate(legalCaseForceDate, date);
    }

    public void save() {
        click(saveBtn);
    }
}
