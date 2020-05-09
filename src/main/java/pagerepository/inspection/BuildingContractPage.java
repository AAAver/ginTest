package pagerepository.inspection;

import pagerepository.utilities.CorePage;
import pagerepository.utilities.Generator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BuildingContractPage extends CorePage {

    public BuildingContractPage(WebDriver driver) {
        super(driver);
    }

    // Кнопка Проверка
    By toInspBtn = By.xpath("//a[@title='Проверка']");

    // Информация о договорах
    By bldAddress = By.id("s2id_BtiAddressStr");
    By btiAddrText = By.xpath("//*[@id='s2id_BtiAddressStr'] //span");
    By autogenInput = By.id("s2id_autogen5_search");
    By contractNum = By.id("Number");
    By contractDate = By.id("ContractDate");
    By subjectType = By.id("s2id_SubjectTypeCtId");
    By rightType = By.id("s2id_RightTypeCtId");
    By contractValidity = By.id("s2id_ContractValidityCtId");
    By startDate = By.id("StartDate");
    By endDate = By.id("EndDate");
    By isPerpetual = By.id("s2id_IsPerpetual");
    By subjectName = By.id("SubjectName");
    By legalForm = By.xpath("//input[@id='LegalForm']");
    By subjectInn = By.id("SubjectInn");
    By square = By.id("Square");
    By kadastrNumber = By.id("KadastrBld");
    By usePurpose = By.id("UsagePurposes");
    By contractSaveBtn = By.xpath("//a[@title='Сохранить']");

    public void fillIn(String contractRight) throws InterruptedException {
        if (getAttribute(btiAddrText, "innerHTML").length() == 1) {
            click(bldAddress);
            Thread.sleep(300);
            writeText(autogenInput, "Ста");
            Thread.sleep(2000);
            var results = getElementList(select2drop);
            click(results.get(1));
            Thread.sleep(2000);
            var newResults = getElementList(select2drop);
            click(newResults.get(1));
        }
        writeText(contractNum, Generator.fakeContractNum());
        writeText(contractDate, Generator.fakeDatePast());
        click(contractNum);
        click(subjectType);
        var subjects = getElementList(select2drop);
        click(subjects.get(random.nextInt(subjects.size())));
        var subjectTypeName = getText(subjectType);
        click(rightType);
        var rights = getElementList(select2drop);
        for (WebElement webElement : rights) {
            if (getText(webElement).contains(contractRight)) {
                webElement.click();
                break;
            }
        }
        click(contractValidity);
        var vals = getElementList(select2drop);
        click(vals.get(random.nextInt(vals.size())));
        writeText(startDate, Generator.fakeDatePast());
        writeText(endDate, Generator.fakeDateFuture());
        click(contractNum);
        click(isPerpetual);
        var perps = getElementList(select2drop);
        click(perps.get(random.nextInt(perps.size())));
        writeText(subjectName, fake.company().name());
        if (subjectTypeName.contains("ЮЛ")) {
            writeText(legalForm, "ОАО");
        }
        if (!subjectTypeName.contains("Иностранная")) {
            writeText(subjectInn, Generator.fakeInn());
        }
        if (getAttribute(square, "value").isBlank()) {
            writeText(square, Integer.toString(random.nextInt(40000)));
        }
        if (getAttribute(kadastrNumber, "value").isBlank()) {
            writeText(kadastrNumber, Generator.fakeKadastr());
        }
        writeText(usePurpose, "Сбор пушнины, мёда, рыболовство.");
        click(contractSaveBtn);
        click(toInspBtn);
    }

    public void fillIn(String contractRight, String date) throws InterruptedException {
        if (getAttribute(btiAddrText, "innerHTML").length() == 1) {
            click(bldAddress);
            Thread.sleep(300);
            writeText(autogenInput, "Ста");
            Thread.sleep(2000);
            var results = getElementList(select2drop);
            click(results.get(1));
            Thread.sleep(2000);
            var newResults = getElementList(select2drop);
            click(newResults.get(1));
        }
        writeText(contractNum, Generator.fakeContractNum());
        writeText(contractDate, date);
        click(contractNum);
        click(subjectType);
        var subjects = getElementList(select2drop);
        click(subjects.get(random.nextInt(subjects.size())));
        var subjectTypeName = getText(subjectType);
        click(rightType);
        var rights = getElementList(select2drop);
        for (WebElement webElement : rights) {
            if (getText(webElement).contains(contractRight)) {
                webElement.click();
                break;
            }
        }
        click(contractValidity);
        var vals = getElementList(select2drop);
        click(vals.get(random.nextInt(vals.size())));
        writeText(startDate, Generator.fakeDatePast());
        writeText(endDate, Generator.fakeDateFuture());
        click(contractNum);
        click(isPerpetual);
        var perps = getElementList(select2drop);
        click(perps.get(random.nextInt(perps.size())));
        writeText(subjectName, fake.company().name());
        if (subjectTypeName.contains("ЮЛ")) {
            writeText(legalForm, "ОАО");
        }
        if (!subjectTypeName.contains("Иностранная")) {
            writeText(subjectInn, Generator.fakeInn());
        }
        if (getAttribute(square, "value").isBlank()) {
            writeText(square, Integer.toString(random.nextInt(40000)));
        }
        if (getAttribute(kadastrNumber, "value").isBlank()) {
            writeText(kadastrNumber, Generator.fakeKadastr());
        }
        writeText(usePurpose, "Сбор пушнины, мёда, рыболовство.");
        click(contractSaveBtn);
        click(toInspBtn);
    }

}
