package common;

import java.util.List;
import utilities.CorePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BusinessProcessListPage extends CorePage {

	public BusinessProcessListPage(WebDriver driver) {
		super(driver);
	}

	By pageSelector = By.xpath("//select[@title='Записей на стр.']");
	By bpEntityInfo = By.xpath("//*[@aria-describedby='gridTable_BpEntityInfo']");
	By toBpBtn = By.xpath(".//*[@title='Перейти к исполнению']");
	By btnDiv = By.xpath("//*[@aria-describedby='gridTable_ViewEditBtn']");

	public void findBpByAddress(String processAddress) throws InterruptedException {
		List<WebElement> processAddresses = getElementList(bpEntityInfo); // Все ячейки с адресами БП
		for (int i = 0; i < processAddresses.size(); i++) {
			String processAddressC = getText(processAddresses.get(i));
			if (processAddressC.equals(processAddress)) {
				try {
					click(getElementList(toBpBtn).get(i));
				} catch (org.openqa.selenium.StaleElementReferenceException ex) {
					click(getElementList(toBpBtn).get(i));
				}
				break;
			}
		}

	}

}
