package pagerepository.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.utilities.CorePage;

public class MainPage extends CorePage {

	public MainPage(WebDriver driver) {
		super(driver);
	}
	
	By bell = By.xpath("//li[@id='navbar-notification'] //a[@class='dropdown-toggle']");
	By notificationBp = By.xpath("//li[@id='navbar-notification']/ul/li[2]");

	// ===== ССЫЛКИ ====== //
	By dismantleLink = By.xpath("//*[contains(@href, 'UnauthBuildingDismantle') and text()='Список объектов']");
	By disposalLink = By.xpath("//*[contains(@href,'Disposals')]");
	By ubsListLink = By.xpath("//*[contains(@href,'UnauthorizedBuilding')]");
	By inspectionTaskList = By.xpath("//*[contains(@href,'InspectionTask')]");

	
	public void goToActiveBp() {
		click(bell);
		click(notificationBp);
	}

	public void toDismantle(){
		while(!driver.getCurrentUrl().contains("UnauthBuildingDismantle")) {
			click(dismantleLink);
		}
	}

	public void toDisposals(){
		while(!driver.getCurrentUrl().contains("Disposals")) {
			click(disposalLink);
		}
	}

	public void toUbsList(){
		while(!driver.getCurrentUrl().contains("UnauthorizedBuilding")) {
			click(ubsListLink);
		}
	}

	public void toInspectionTaskList(){
		while(!driver.getCurrentUrl().contains("InspectionTask")) {
			click(inspectionTaskList);
		}
	}


}
