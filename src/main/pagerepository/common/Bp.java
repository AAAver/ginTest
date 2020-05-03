package common;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Bp {

	WebDriver driver;

	public Bp(WebDriver driver) {
		this.driver = driver;
	}

	By pageSelector = By.xpath("//select[@title='Записей на стр.']");
	By bpEntityInfo = By.xpath("//*[@aria-describedby='gridTable_BpEntityInfo']");
	By toBpBtn = By.xpath(".//*[@title='Перейти к исполнению']");
	By btnDiv = By.xpath("//*[@aria-describedby='gridTable_ViewEditBtn']");

	public List<WebElement> BpEntityInfoRaw() {
		return driver.findElements(bpEntityInfo);
	}

	public Select elementsPerPage() {
		Select pages = new Select(driver.findElement(pageSelector));
		return pages;
	};

	public void findBpByAdress(String validProcessAdress) throws InterruptedException {
		List<WebElement> processAddresses = driver.findElements(bpEntityInfo); // Все ячейки с адресами БП

		for (int i = 0; i < processAddresses.size(); i++) {
			String processAddress = processAddresses.get(i).getText();

			if (processAddress.equals(validProcessAdress)) {

				System.out.println("Адрес найден");
				try {
					Thread.sleep(2000);
					WebElement td = driver.findElements(btnDiv).get(i);
					td.findElement(toBpBtn).click();
				} catch (org.openqa.selenium.StaleElementReferenceException ex) {
					Thread.sleep(2000);
					WebElement td = driver.findElements(btnDiv).get(i);
					td.findElement(toBpBtn).click();

				}

				break;
			}
		}

	}

}
