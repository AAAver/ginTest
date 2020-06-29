package pagerepository.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pagerepository.utilities.CorePage;

import java.util.List;

public class DocumentSigning extends CorePage {
    public DocumentSigning(WebDriver driver) {
        super(driver);
    }

    By checkBoxesCol = By.xpath("//*[@id='searchFormDiv']/following-sibling::table //td[1] //input");
    By docTypesCol = By.xpath("//*[@id='searchFormDiv']/following-sibling::table //td[5]");
    By signButton = By.xpath("//*[@title='Подписать']");
    By cert = By.xpath("//li/div[contains(@class, 'content')]");
    By confirmSign = By.xpath("//div[contains(@aria-describedby, 'modal')] //button[contains(@class, 'btn-success')]");
    By searchButton = By.xpath("//*[text()='Поиск']");


    public void sign(String documentNumber) throws InterruptedException {
        List<WebElement> checkboxes = getElementList(checkBoxesCol);
        List<WebElement> types = getElementList(docTypesCol);
        for (int i = 0; i < types.size(); i++) {
            if(getText(types.get(i)).contains(documentNumber)){
                checkboxes.get(i).click();
                break;
            }
        }

        clickJS(signButton);
        Thread.sleep(1500);
        new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOfElementLocated(cert));
        click(cert);
        Thread.sleep(1500);
        click(confirmSign);

        new WebDriverWait(driver, 40).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(), '"+documentNumber+"')]")));
        log.info("Document signed");




    }
}
