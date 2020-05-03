package inspection;

import misc.Generator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class InspectionMainTab extends InspectionPage {

    public InspectionMainTab(WebDriver driver) {
        super(driver);
    }

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

    public void setInspectionTheme(String theme) {
        scrollIntoViewBy(inspectionTheme);
        click(inspectionTheme);
        var inspectionThemes = getElementList(select2drop);
        for (int i = 0; i < inspectionThemes.size(); i++) {
            String inspectionThemeText = inspectionThemes.get(i).getText();
            if (inspectionThemeText.contains(theme)) {
                click(inspectionThemes.get(i));
                break;
            }
        }
    }

    public void setInspectionResult(String result) {
        scrollIntoViewBy(inspectionResult);
        click(inspectionResult);
        var results = getElementList(select2drop);
        for (int i = 0; i < results.size(); i++) {
            String resultText = results.get(i).getText();
            if (resultText.contains(result)) {
                results.get(i).click();
                break;
            }
        }
    }

    public void objectConstructionStage(String constructionStage) {
        scrollIntoViewBy(objectConstructionStage);
        click(objectConstructionStage);
        var stages = getElementList(select2drop);
        for (int i = 0; i < stages.size(); i++) {
            String resultText = stages.get(i).getText();
            if (resultText.contains(constructionStage)) {
                stages.get(i).click();
                break;
            }
        }
    }

    public void objectConstructionStage() {
        scrollIntoViewBy(objectConstructionStage);
        click(objectConstructionStage);
        var stages = getElementList(select2drop);
        click(stages.get(Generator.getRandomUpTo(stages.size())));

    }

    public void factUsage() {
        scrollIntoViewBy(factUsage);
        click(factUsage);
        var usages = getElementList(select2drop);
        click(usages.get(Generator.getRandomUpTo(usages.size())));
    }


    //=================== ПРЕДСТАВИТЕЛЬ ПРОВЕРЯЕМОГО ЛИЦА ================//

    public void addRepresentative(boolean refusedToSign) {
        scrollIntoViewBy(representativeLastName);
        writeText(representativeLastName, fake.name().firstName());
        writeText(representativeFirstName, fake.name().lastName());
        click(representativePosition);
        var positions = getElementList(select2drop);
        positions.get(Generator.getRandomUpTo(positions.size())).click();
        click(representativeRefuse);
        var decisions = getElementList(select2drop);
        if (refusedToSign) {
            decisions.get(0).click();
        } else {
            decisions.get(1).click();
        }
    }

    public void addRepresentative(String lastName, String firstName, String position, boolean refusedToSign) {
        scrollIntoViewBy(representativeLastName);
        writeText(representativeLastName, lastName);
        writeText(representativeFirstName, firstName);
        click(representativePosition);
        var positions = getElementList(select2drop);
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getText().contains(position)) {
                positions.get(i).click();
                break;
            }
        }
        click(representativeRefuse);
        var decisions = getElementList(select2drop);
        if (refusedToSign) {
            decisions.get(0).click();
        } else {
            decisions.get(1).click();
        }
    }


    //=================== ПРИЗНАКИ ОПАСНОСТИ ================//
    public void dangerSignal(boolean signal) {
        click(dangerSignal);
        var decisions = getElementList(select2drop);
        if (signal) {
            decisions.get(0).click();
        } else {
            decisions.get(1).click();
        }
    }

    //=================== ГЕНЕРАЦИЯ РАНДОМНОЙ ИНФЫ ================//
    public void populateCommonInformation() {
        scrollIntoViewBy(controlSubNumber);
        writeText(controlSubNumber, fake.number().digits(4));
        writeText(controlDate, currentDate);
        writeText(actDate, currentDate);
        writeText(durationHours, Integer.toString(Generator.getRandomUpTo(4)));
        writeText(controlTime, Generator.randomInspTime());
        addInspectors();
        objectConstructionStage();
        factUsage();
        addRepresentative(false);
        dangerSignal(false);
    }


}
