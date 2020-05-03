package common;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import misc.Generator;

public class Upload {

	@SuppressWarnings("unused")
	private static final By btnAttachFile = By.xpath("//a[@title='Добавить документ (Ctrl+D)']");
	private static final By inputFileField = By.xpath("//div[contains(text(), 'Выберите файл')]/following-sibling::input");
	private static final By dropCategory = By.id("dumpName");
	private static final By commentaries = By.xpath("//div[@aria-describedby='ui-id-1'] //div[@class='comment-block'] //textarea");
	private static final By btnConfirmAttachement = By
			.xpath("//div[contains(@class,'ui-dialog-buttonset')]/button[@class = 'btn btn-primary btn-xs']");
	private static final By documentDate = By.xpath("//*[contains(@class,'ui-widget')] //input[contains(@name, 'DocumentDate')]");

	public static void file(WebDriver driver, String docCategory, String docPath) {

		Actions a = new Actions(driver);
		a.keyDown(Keys.CONTROL).sendKeys("d").keyUp(Keys.CONTROL).build().perform();
		driver.findElement(inputFileField).sendKeys(docPath);
		Select s = new Select(driver.findElement(dropCategory));
		s.selectByVisibleText(docCategory);
		
		try {
			driver.findElement(documentDate).sendKeys(Generator.getCurrentDate());
		} catch (NoSuchElementException e) {
			
		}
		
		driver.findElement(btnConfirmAttachement).click();

	}

	public static void file(WebDriver driver, String docCategory, String docPath, String comments) {
		Actions a = new Actions(driver);
		a.keyDown(Keys.CONTROL).sendKeys("d").keyUp(Keys.CONTROL).release().build().perform();
		driver.findElement(inputFileField).sendKeys(docPath);
		Select s = new Select(driver.findElement(dropCategory));
		s.selectByVisibleText(docCategory);
		driver.findElement(commentaries).sendKeys(comments);
		driver.findElement(btnConfirmAttachement).click();
	}
	
	public static void file(WebDriver driver, String[] docCategory, String[] docPath) {
		Actions a = new Actions(driver);
		int i = 0;
		while(i < docCategory.length) {
		a.keyDown(Keys.CONTROL).sendKeys("d").keyUp(Keys.CONTROL).release().build().perform();
		String absPath = (new File(docPath[i])).getAbsolutePath();
		driver.findElement(inputFileField).sendKeys(absPath);
		Select s = new Select(driver.findElement(dropCategory));		
		s.selectByVisibleText(docCategory[i]);
		
		try {
			driver.findElement(documentDate).sendKeys(Generator.getCurrentDate());
		} catch (NoSuchElementException e) {
			
		}
		
		driver.findElement(btnConfirmAttachement).click();
		i++;
		}
	}
}
