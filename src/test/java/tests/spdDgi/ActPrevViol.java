package tests.spdDgi;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import inspection.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import common.DisposalPage;
import common.LoginPage;
import common.Save;
import common.Upload;
import inspection.InspectionPage;
import utilities.Catalog;
import utilities.Generator;
import utilities.Props;


public class ActPrevViol  {
	private Faker fake = new Faker(new Locale("ru"));
	
	private String baseUrl = Props.BASE_URL;
	private String disposalUrl = Props.DISPOSAL_URL_NF;
	private String ultLogin = Props.ULT_LOGIN;
	private String ultPassword = Props.ULT_PASSWORD;
	
	private String address = fake.address().streetAddress();
	private String companyName = "Альянс Девелопмент";	
	private String objSquare = Integer.toString(Generator.getRandomUpTo(5000));
	private String inspTheme = Catalog.inspection.theme.ONF;
	private String inspResult = Catalog.inspection.result.VIOL_SIGNS_IDENT;
	private String rightType = Catalog.useRight.RENT;
	private String docCategory = Catalog.docs.category.ACT_NF;
	private String docPath = (new File(Catalog.docs.path.ACT_NF)).getAbsolutePath();

	@Test
	public void prevViol() throws IOException, InterruptedException {
		WebDriver driver = Props.initChromeDriver();		
		driver.get(baseUrl);
		
		LoginPage l = new LoginPage(driver);
		l.loginAs(ultLogin, ultPassword);

		driver.get(disposalUrl);
		DisposalPage d = new DisposalPage(driver);
		d.addInspection();

		InspectionPage i = new InspectionPage(driver);
		InspectionObjectTab obj = new InspectionObjectTab(driver);
		InspSubj subj = new InspSubj(driver);
		InspectionActNF act = new InspectionActNF(driver);
		InspViol viol = new InspViol(driver);
		
		//==== ТЕМАТИКА/РЕЗУЛЬТАТ ====//	
		i.setInspectionTheme(inspTheme);
		i.setInspectionResult(inspResult);
		//==== ОБЩАЯ ИНФА ====//
		i.populateCommonInformation();
		i.isRepresentativeRefusedToSign(false);
		act.populateCommonInformation();
		//==== АКТ НФ (НАРУШЕНИЯ) ====//	
		act.isActualUsage(false);
		act.isReplanned(false);
		act.isPremicyUsed(true);
		act.isThirdParty(false);
		//==== АКТ НФ (НАРУШЕНИЯ В ПРОШЛОМ) ====//
		act.previousViolations(true);
		act.previousViolationDate();
		act.previousReplanViolationFixed(false);
		act.previousReplanViolation(true);		
		act.previousNonPurposeUsage(false);
		act.previousNonPurposeUsageFixed(false);
		act.previousThirdPartyViolation(true);
		act.previousThirdPartyViolationFixed(true);	
		//==================//
		Upload.file(driver, docCategory, docPath);
		//==== ДАННЫЕ ОБЪЕКТА ====//	
		i.objectTabSwitch();	
		obj.setAddress(address);
		obj.setObjSquare(objSquare);
		obj.pickKadNumExist(true);
		Save.saveThis(driver);
		//==== ДАННЫЕ СУБЪЕКТА ====//		
		i.subjectTabSwitch();
		while(!subj.isShdPresented()) {			
			subj.peekShd(companyName);
			Save.saveThis(driver);
			i.subjectTabSwitch();
		}		
		//==== ИНФОРМАЦИЯ О ЗДАНИИ ====//
		i.objectTabSwitch();
		obj.addEgrnTable();
		obj.addRoomTable();
		Save.saveThis(driver);		
		//==== ДОГОВОР НА ПОМЕЩЕНИЕ ====//
		i.objectTabSwitch();
		obj.addContractTable(rightType);		
		//==== НАРУШЕНИЯ ====//
		i.violTabSwitch();
		viol.addWarning();		
		//==== ВЕРИФИКАЦИЯ ====//
		i.verify();
		
		//==== ЗАПИСЫВАЕМ ИНФУ ====//
		String inspId = i.getInspectionId();
		Props.setProperty("inspIdPrevViol", inspId);
		System.out.println(i.getInspectionId() + " - ПОВТОРНЫЕ НАРУШЕНИЯ");
		
		driver.close();
	}	
}
