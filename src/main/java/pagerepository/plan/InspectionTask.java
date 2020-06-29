package pagerepository.plan;

import com.google.inject.internal.cglib.core.$Constants;
import miscelaneous.Generator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

public class InspectionTask extends CorePage {

    public InspectionTask(WebDriver driver) {
        super(driver);
    }

    By docNumber = By.xpath("//*[@id='GinDocNumber']");
    By registrationDate = By.xpath("//*[@id='RegistrationDate']");
    By addressee = By.xpath("//*[@id='Addressee']");
    By summary = By.xpath("//*[@id='SummaryM']");
    By taskSource = By.xpath("//*[@id='s2id_SourceCtIdM']");
    By taskSourceComments = By.xpath("//*[@id='SourceM']");
    By inspectionTheme = By.xpath("//*[@id='s2id_ThemeCtIdM']");
    By inspectionDepartment = By.xpath("//*[@id='s2id_DepartmentIds']");
    By objectType = By.xpath("//*[@id='s2id_ObjectTypeCtIdM']");
    By startDate = By.xpath("//*[@id='StartDateM']");
    By endDate = By.xpath("//*[@id='EndDateM']");
    By comments = By.xpath("//*[@id='Comment']");
    By saveButton = By.xpath("//*[@id='save-btn']");
    By addAddressButton = By.xpath("//a[text()='Добавить адрес']");

    public void setInspectionTheme(String theme){
        chooseFromDropDown(inspectionTheme,theme);

        if(getActualValueFromDrop(inspectionTheme).contains(theme)){
            log.info("Inspection theme properly set");
        } else {
            log.error("Inspection theme has not been set properly");
        }
    }

    public void setTaskSource(String source){
        chooseFromDropDown(taskSource,source);

        if(getActualValueFromDrop(taskSource).contains(source)){
            log.info("Task source  properly set");
        } else {
            log.error("Task source has not been set properly");
        }
    }

    public void setInspectionDepartment(String department){
        chooseFromDropDown(inspectionDepartment,department);

        if(getActualValuesFromField(inspectionDepartment).get(0).contains(department)){
            log.info("Inspection department properly set");
        }else {
            log.error("Inspection department has not been set properly");
            log.info(getActualValuesFromField(inspectionDepartment).get(0));
        }
    }

    public void setObjectType(String type){
        chooseFromDropDown(objectType,type);

        if(getActualValueFromDrop(objectType).contains(type)){
            log.info("Object type  properly set");
        } else {
            log.error("Object type has not been set properly");
        }
    }

    public void setStartDate(String date){
        setDate(startDate,date);
    }

    public void setEndDate(String date){
        setDate(endDate,date);
        log.info("Date: "+date+" set." );
    }

    public void save(){
        clickJS(saveButton);
    }

    public InspectionTaskAddress addAddress (){
        clickJS(addAddressButton);
        if(driver.getCurrentUrl().contains("InspectionTaskAddress/Add")){
            log.info("On adding InspectionTaskAddress page");
        }else{
            log.error("InspectionTaskAddress page was not opened");
        }
        return  new InspectionTaskAddress(driver);
    }

    public void create(String taskSource, String inspectionTheme, String department){
        setTaskSource(taskSource);
        setInspectionTheme(inspectionTheme);
        setInspectionDepartment(department);
        setEndDate(Generator.getCurrentDatePlus5());
        save();
    }


}
