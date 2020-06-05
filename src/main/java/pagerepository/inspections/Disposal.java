package pagerepository.inspections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pagerepository.utilities.CorePage;

import java.util.ArrayList;
import java.util.List;

public class Disposal extends CorePage {

    public Disposal(WebDriver driver) {
        super(driver);
    }

    By addInspection = By.xpath("//a[@title='Добавить Обследование' or @title='Добавить Проверку']");
    By reason = By.xpath("//*[@id='s2id_ReasonCtId']");
    By SubjectName = By.xpath("//*[@id='SubjectName']");
    public void addInspection() {
        while (!driver.getCurrentUrl().contains("Controls/AddInsp")) {
            click(addInspection);
        }
    }

    public void selectReason(String reason){
        chooseFromDropDown(this.reason, reason);
    }

    public void testAllReasons(){
        List<String> s = getAllPossibleValues(reason);
        for (int i = 0; i < s.size(); i++) {
            chooseFromDropDown(reason,s.get(i));
        }
    }

    public void printAllReasons(){
        List<String> s = getAllPossibleValues(reason);
        for (String s1 : s) {
            System.out.println(s1);
        }
    }

}
