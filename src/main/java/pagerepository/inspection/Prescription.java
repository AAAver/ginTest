package pagerepository.inspection;

import java.util.List;
import pagerepository.utilities.CorePage;
import pagerepository.utilities.Generator;

import org.openqa.selenium.*;

public class Prescription extends CorePage {

	public Prescription(WebDriver driver) {
		super(driver);
	}

	By subNumber = By.id("RequirementSubNumber");
	By issueDate = By.id("IssueDate");
	By deadlineDate = By.id("DeadLineDateM");
	By type = By.id("s2id_RequirementTypeM");
	By responsibleInspector = By.id("s2id_InspectorUserId");
	By deliveryType = By.id("s2id_DeliveryTypeCtId");
	By content = By.id("Contents");
	By saveBtn = By.id("submit-form-btn");
	By toInspBtn =By.xpath("//a[@title='Карточка проверки']");
	
	public void fillPrescription() {
		writeText(subNumber, Integer.toString(random.nextInt(1000)));
		writeText(issueDate, Generator.getCurrentDate());
		writeText(issueDate, Keys.chord(Keys.ENTER));
		if(!getAttribute(deadlineDate, "class").contains("dirty-input")) {
		writeText(deadlineDate, Generator.getCurrentDatePlus5());
		writeText(deadlineDate, Keys.chord(Keys.ENTER));
		}
		click(type);
		for (WebElement type : getElementList(select2drop)) {
			if (getText(type).contains("Предписание")) {
				click(type);
				break;
			}
		}
		click(responsibleInspector);
		List<WebElement> inspectors = getElementList(select2drop);
		click(inspectors.get(random.nextInt(inspectors.size())));
		writeText(content, fake.artist().name());
		click(saveBtn);
	    click(toInspBtn);
	}
	
}
