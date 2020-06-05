package pagerepository.inspections;

import org.openqa.selenium.*;

import miscelaneous.Generator;

public class InspectionObjectTab extends InspectionPage {

    public InspectionObjectTab(WebDriver driver) {
        super(driver);
    }
    //===============ДАННЫЕ ОБЪЕКТА===============//

    public void setAddress(String address) {
        scrollIntoViewBy(addressKIM);
        if (!getAttribute(addressKIM, "value").isBlank()) {
            clearField(addressKIM);
        }
        writeText(addressKIM, address);
    }

    public void setObjSquare(String objectSquare) {
        scrollIntoViewBy(objSquare);
        if (!getAttribute(objSquare, "value").isBlank()) {
            clearField(objSquare);
        }
        writeText(objSquare, objectSquare);
    }

    public void pickKadNumExist(boolean kadNumExist) {
        scrollIntoViewBy(kadastrYN);
        click(kadastrYN);
        var decisions = getElementList(select2drop);
        if (kadNumExist) {
            click(decisions.get(0));
            waitVisibility(kadastrNum);
            clearField(kadastrNum);
            writeText(kadastrNum, Generator.fakeKadastr());
        } else {
            click(decisions.get(1));
        }
    }

    public void pickKadNumExist(boolean kadNumExist, String number) {
        scrollIntoViewBy(kadastrYN);
        click(kadastrYN);

        var decisions = getElementList(select2drop);
        if (kadNumExist) {
            click(decisions.get(0));
            waitVisibility(kadastrNum);
            clearField(kadastrNum);
            writeText(kadastrNum, number);
        } else {
            click(decisions.get(1));
        }
    }

    //============= ТАБЛИЦА ИНФОРМАЦИЯ О ЗДАНИИ ИЗ ЕГРН ===========//

    public void createEgrnTable() {
        scrollIntoViewBy(addressKIM);
        String address = getAttribute(addressKIM, "value");
        scrollIntoViewBy(egrnInfoAddBld);
        click(egrnInfoAddBld);
        if (!address.isBlank()) {
            writeText(egrnTblAddress, address);
        } else {
            writeText(egrnTblAddress, fake.address().streetAddress());
        }
        writeText(egrnTblOwner, fake.name().fullName());
        writeText(egrnTblKadastr, Generator.fakeKadastr());
        writeText(egrnTblUnom, fake.number().digits(5));
        click(egrnTblConfirm);
    }

    //============= ТАБЛИЦА ИНФОРМАЦИЯ О ПОМЕЩЕНИИ В ЗДАНИИ ===========//

    public void createRoomInfoTable() {
        scrollIntoViewBy(premicyCollapse);
        if (!isDisplayed(addRoomBtn)) {
            click(premicyCollapse);
        }
        click(addRoomBtn);
        writeText(roomKadastrNumber, Generator.fakeKadastr());
        click(confirmPremicyAdd);
    }

    //============= ТАБЛИЦА ИНФОРМАЦИЯ О ДОГОВОРЕ ===========//

    public void createContractTable(String contractRight) throws InterruptedException {
        scrollIntoViewBy(BtnContract);
        click(BtnContract);
        BuildingContract bcp = new BuildingContract(driver);
        bcp.fillIn(contractRight);
    }

    public void createContractTable(String contractRight, String date) throws InterruptedException {
        scrollIntoViewBy(BtnContract);
        click(BtnContract);
        BuildingContract bcp = new BuildingContract(driver);
        bcp.fillIn(contractRight, date);
    }


}
