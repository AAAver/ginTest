package pagerepository.inspection;

import org.openqa.selenium.WebDriver;

public class InspectionSubjectTab extends InspectionPage {


	public InspectionSubjectTab(WebDriver driver) {
		super(driver);
	}

	String hPostTitle = "Руководитель высшей категории";
	String locationType = "Местонахождения";

	public void peekShd(String companyName) throws InterruptedException {
		click(addShdBtn);
		writeText(searchField, companyName);
		click(searchBtn);
		var shdNamesList = getElementList(shdNames);
		for (int i = 0; i < shdNamesList.size(); i++) {
			if (getText(shdNamesList.get(i)).contains(companyName)) {
				click(getElementList(addButtons).get(i));
				break;
			}
		}
		clearField(headPostTitle);
		writeText(headPostTitle, hPostTitle);
		clearField(headLastName);
		writeText(headLastName, fake.name().lastName());
		clearField(headFirstName);
		writeText(headFirstName, fake.name().firstName());
		click(addressTab);
		var addressTypes = getElementList(shdAddressTypes);
		for (int i = 0; i < addressTypes.size(); i++) {
			if (getText(addressTypes.get(i)).contains(locationType)) {
				if (!getElementList(shdAddressChkbox).get(i).isSelected()) {
					click(getElementList(shdAddressChkbox).get(i));
				}
			}
		}

		click(employeeTab);
		writeText(emplLastName, fake.name().lastName());
		writeText(emplFirstName, fake.name().firstName());
		click(emplPosition);
		var positions = getElementList(select2drop);
		click(positions.get(random.nextInt(positions.size())));
		click(accept);
	}
	
	public boolean isShdPresented() {
		if(!isDisplayed(snapshot)) {
			return false;
		}
		else {
		return true;
		}
	}

}
