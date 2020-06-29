package pagerepository.inspections;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import pagerepository.utilities.Upload;
import miscelaneous.Catalog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class InspectionViolationTab extends InspectionPage {

    public InspectionViolationTab(WebDriver driver) {
        super(driver);
    }

    By warningBtn = By.xpath("//*[@id='add-perimeter-control-btn']");

    public void addViolation(String violation) throws InterruptedException {
        scrollIntoViewBy(addViolBtn);
        click(addViolBtn);
        click(violTypeDrop);
        var  viols = getElementList(select2drop);
        for (WebElement viol : viols) {
            if (viol.getText().contains(violation)) {
                click(viol);
                break;
            }
        }
        click(apArticleDrop);
        var articles = getElementList(select2drop);
        click(articles.get(random.nextInt(articles.size())));
        click(violSuccessBtn);
    }

    public void addProtocol(String violation) throws InterruptedException {
        Actions a = new Actions(driver);
        List<WebElement> violations = getElementList(violationLabel);
        List<WebElement> pluses = getElementList(tableViolPlus);
        List<WebElement> protocols = getElementList(addProtocol);
        WebElement protocolToAdd = null;
        WebElement plusToAdd = null;
        for (int i = 0; i < violations.size(); i++) {
            if (getText(violations.get(i)).contains(violation)) {
                plusToAdd = pluses.get(i);
                protocolToAdd = protocols.get(i);
            }
        }
        a.moveToElement(plusToAdd).build().perform();
        a.moveToElement(protocolToAdd).click().build().perform();
        Protocol p = new Protocol(driver);
        p.populateProtocol();
    }

    public void addPrescription(String violation) throws InterruptedException {
        Actions a = new Actions(driver);
        List<WebElement> violations = getElementList(violationLabel);
        List<WebElement> pluses = getElementList(tableViolPlus);
        List<WebElement> prescriptions = getElementList(addPrescription);
        WebElement prescriptionToAdd = null;
        WebElement plusToAdd = null;
        for (int i = 0; i < violations.size(); i++) {
            if (getText(violations.get(i)).contains(violation)) {
                plusToAdd = pluses.get(i);
                prescriptionToAdd = prescriptions.get(i);
            }
        }
        a.moveToElement(plusToAdd).build().perform();
        a.moveToElement(prescriptionToAdd).click().build().perform();
        Prescription p = new Prescription(driver);
        p.fillPrescription();
    }


    //================== ПРЕДОСТЕРЕЖЕНИЕ ================//
    public void addWarning() throws InterruptedException {
        scrollIntoViewBy(warningBtn);
        clickJS(warningBtn);
        Warning w = new Warning(driver);
        w.populate();
        }

    }



