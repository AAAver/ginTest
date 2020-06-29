package pagerepository.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pagerepository.plan.InspectionTaskList;
import pagerepository.utilities.CorePage;

public class MainPage extends CorePage {

	public MainPage(WebDriver driver) {
		super(driver);
	}
	

	By notificationBp = By.xpath("//li[@id='navbar-notification']/ul/li[2]");

	// ===== ССЫЛКИ ====== //
	By dismantleLink = By.xpath("//*[contains(@href, 'UnauthBuildingDismantle') and text()='Список объектов']");
	By disposalLink = By.xpath("//*[contains(@href,'Disposals')]");
	By ubsListLink = By.xpath("//*[contains(@href,'UnauthorizedBuilding')]");
	By inspectionTaskList = By.xpath("//li/*[contains(@href,'InspectionTask')]");
	By casesDgi = By.xpath("//*[contains(@href, 'DgiLegalCase')]");

	By bell = By.xpath("//*[@id='navbar-notification']/a");
	By docToSign = By.xpath("//a[contains(@href, 'DocumentSigning')]");

	
	public void goToActiveBp() {
		click(bell);
		click(notificationBp);
	}

	public void toDismantle(){
		while(!driver.getCurrentUrl().contains("UnauthBuildingDismantle")) {
			clickJS(dismantleLink);
		}
	}

	public void toDisposals(){
		while(!driver.getCurrentUrl().contains("Disposals")) {
			clickJS(disposalLink);
		}
	}

	public void toUbsList(){
		while(!driver.getCurrentUrl().contains("UnauthorizedBuilding")) {
			clickJS(ubsListLink);
		}
	}

	public InspectionTaskList toInspectionTaskList(){
		while(!driver.getCurrentUrl().contains("InspectionTask")) {
			clickJS(inspectionTaskList);
		}
		log.info("On InspectionTaskList page");
		return new InspectionTaskList(driver);
	}

	public void toDgiLegalCase(){
		while (!driver.getCurrentUrl().contains("DgiLegalCase")){
			clickJS(casesDgi);
		}
	}


    public void toDocumentsForSigning() {
		click(bell);
		click(docToSign);
    }
}
