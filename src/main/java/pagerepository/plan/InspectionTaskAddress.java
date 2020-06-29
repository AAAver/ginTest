package pagerepository.plan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.inspections.Disposal;
import pagerepository.utilities.CorePage;

import java.util.List;
import java.util.regex.Pattern;

public class InspectionTaskAddress extends CorePage {




    public InspectionTaskAddress(WebDriver driver) {
        super(driver);
    }

    By address = By.xpath("//*[@id='AddressM_Address']");
    By ao = By.xpath("//*[@id='s2id_AoIdM']");
    By district = By.xpath("//*[@id='s2id_DistrictIdM']");
    By inspectionDepartment = By.xpath("//*[@id='s2id_DepartmentIdM']");
    By inspectors = By.xpath("//*[@id='s2id_InspectorIds']");
    By saveButton = By.xpath("//*[@title='Сохранить (Ctrl+S)']");
    By addDisposalButton = By.xpath("//*[@title='Добавить']");
    By finishAddressCorrection = By.xpath("//*[@title='Завершить работу с адресом']");
    By confirmFinishCorrectionDialog = By.xpath("//*[@aria-describedby='change-close-addr-bp-dlg'] //button[contains(@class,'success')]");
    By toInspectionTaskList = By.xpath("//*[@title='Список']");

    public void setAddress(String address){
        writeText(this.address,address);
        log.info("Address set");
    }
    public void setAo(String ao){
        chooseFromDropDown(this.ao,ao);
        String name = getActualValueFromDrop(this.ao);
        log.info("Ao set on "+name);
    }
    public void setDistrictRandom(){
        chooseFromDropDownRandom(district);
        String name = getActualValueFromDrop(district);
        log.info("District set on "+name);
    }
    public void setInspectionDepartment(String inspectionDepartment){
        chooseFromDropDown(this.inspectionDepartment,inspectionDepartment);
        String name = getActualValueFromDrop(this.inspectionDepartment);
        log.info("Inspection department set on "+name);
    }
    public void setInspectorsRandom(int inspectorsCount){
        List<String> possibleValues = getAllPossibleValues(inspectors);
        System.out.println(possibleValues.size());
        for (int i = 0; i < possibleValues.size(); i++) {
            if(!Pattern.matches("^[а-яА-Я]+ [А-Я]\\.[А-Я]\\.$", possibleValues.get(i))){
                System.out.println(possibleValues.get(i));
                possibleValues.remove(i);
            }
        }
        System.out.println(possibleValues.size());
        for (int i = 0; i < inspectorsCount; i++) {
            int inspToGet = random.nextInt(possibleValues.size());
            chooseFromDropDown(inspectors, possibleValues.get(inspToGet));
            possibleValues.remove(inspToGet);
        }
    }
    public void save(){
        clickJS(saveButton);
    }
    public Disposal addDisposal(){
        clickJS(addDisposalButton);
        Disposal d = new Disposal(driver);
        return d;
    }

    public List<String> getInspectorsList(){
        return getActualValuesFromField(inspectors);
    }

    public void create(String address, String department, int inspectorsCount){
        setAddress(address);
        setAo(department);
        setDistrictRandom();
        setInspectionDepartment(department);
        setInspectorsRandom(inspectorsCount);
        save();
        if(!getAttribute(this.address,"value").contains(address)){
            setAddress(address);
            log.info("Address was reset");
            save();
        }
    }

    public void finishAddressCorrection() {
        if(isDisplayed(finishAddressCorrection)){
            clickJS(finishAddressCorrection);
            if(!isDisplayed(confirmFinishCorrectionDialog)){
                clickJS(finishAddressCorrection);
            }
            clickJS(confirmFinishCorrectionDialog);
            if(isDisplayed(confirmFinishCorrectionDialog)){
                clickJS(confirmFinishCorrectionDialog);
            }
            if(!isDisplayed(confirmFinishCorrectionDialog)){
                log.info("Address correction complete");
            }
        }

    }

    public void toInspectionTaskList() {
        clickJS(toInspectionTaskList);
    }
}
