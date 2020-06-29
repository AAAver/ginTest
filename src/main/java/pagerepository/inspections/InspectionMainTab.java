package pagerepository.inspections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import miscelaneous.Generator;
import org.openqa.selenium.WebDriver;

public class InspectionMainTab extends InspectionPage {

    public InspectionMainTab(WebDriver driver) {
        super(driver);
    }

    By representativeExistTrue =  By.xpath("//*[@id='HasRepresentative' and @value='True']");



    public void setInspectionTheme(String theme) {
        scrollIntoViewBy(inspectionTheme);
        click(inspectionTheme);
        var inspectionThemes = getElementList(select2drop);
        for (WebElement i : inspectionThemes) {
            if (getText(i).contains(theme)) {
                click(i);
                break;
            }
        }
    }

    public void setInspectionResult(String result) {
        scrollIntoViewBy(inspectionResult);
        click(inspectionResult);
        var results = getElementList(select2drop);
        for (WebElement webElement : results) {
            if (getText(webElement).contains(result)) {
                click(webElement);
                break;
            }
        }
    }

    //=================== СВЯЗКА С ОСС ================//

    public void connectUbs(String address) {
        scrollIntoViewBy(ubsCollapse);
        click(ubsCollapse);
        click(bindUbsBtn);
        writeText(ubsAddress, address);
        click(searchUbsBtn);
        click(addExactUbsBtn);
        click(acceptUbsModal);
        scrollIntoViewBy(ubsCollapse);
    }

    //=================== ОБЩАЯ ИНФОРМАЦИЯ ================//

    public void addInspectors() {
        scrollIntoViewBy(addInspectorBtn);
        click(addInspectorBtn);
        click(addInspectorBtn);

        click(firstInspector);
        var inspectors = getElementList(select2drop);
        click(inspectors.get(1));

        click(isResponsibleInsp);
        var isResponsible = getElementList(select2drop);
        click(isResponsible.get(0));
    }

    public void objectConstructionStage(String constructionStage) {
        scrollIntoViewBy(objectConstructionStage);
        click(objectConstructionStage);
        var stages = getElementList(select2drop);
        for (WebElement stage : stages) {
            if (getText(stage).contains(constructionStage)) {
                click(stage);
                break;
            }
        }
    }

    public void objectConstructionStage() {
        scrollIntoViewBy(objectConstructionStage);
        click(objectConstructionStage);
        var stages = getElementList(select2drop);
        click(stages.get(random.nextInt(stages.size())));

    }

    public void setFactUsageRandom() {
       chooseFromDropDownRandom(factUsage);
    }

    //=================== ПРЕДСТАВИТЕЛЬ ПРОВЕРЯЕМОГО ЛИЦА ================//

    public void addRepresentative(boolean refusedToSign) {
        if(!isDisplayed(representativeLastName)){
            clickJS(representativeExistTrue);
        }

        writeText(representativeLastName, fake.name().firstName());
        writeText(representativeFirstName, fake.name().lastName());
        chooseFromDropDownRandom(representativePosition);

        if (refusedToSign) {
            chooseFromDropDown(representativeRefuse, "Да");
        } else {
            chooseFromDropDown(representativeRefuse, "Нет");
        }
    }

    public void addRepresentative(String lastName, String firstName, String position, boolean refusedToSign) {
        scrollIntoViewBy(representativeLastName);
        writeText(representativeLastName, lastName);
        writeText(representativeFirstName, firstName);
        click(representativePosition);
        var positions = getElementList(select2drop);
        for (WebElement webElement : positions) {
            if (getText(webElement).contains(position)) {
                click(webElement);
                break;
            }
        }
        click(representativeRefuse);
        var decisions = getElementList(select2drop);
        if (refusedToSign) {
            click(decisions.get(0));
        } else {
            click(decisions.get(1));
        }
    }

    //=================== ПРИЗНАКИ ОПАСНОСТИ ================//

    public void dangerSignal(boolean signal) {
        click(dangerSignal);
        var decisions = getElementList(select2drop);
        if (signal) {
            click(decisions.get(0));
        } else {
            click(decisions.get(1));
        }
    }

    //=================== ГЕНЕРАЦИЯ РАНДОМНОЙ ИНФЫ ================//

    public void populateCommonInformation() {
        scrollIntoViewBy(controlSubNumber);
        writeText(controlSubNumber, fake.number().digits(4));
        while(!getAttribute(controlDate, "class").contains("dirty-input")) {
            setDate(controlDate, currentDate);
            setDate(actDate, currentDate);
        }
        click(controlSubNumber);
        writeText(durationHours, Integer.toString(Generator.getRandomUpTo(4)));
        writeText(controlTime, Generator.randomInspTime());
        addInspectors();
        objectConstructionStage();
        setFactUsageRandom();
        addRepresentative(false);
        dangerSignal(false);
    }


}
