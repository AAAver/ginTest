package tests.spdDgi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import inspection.InspectionPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import common.Save;
import common.Upload;
import inspection.InspectionObjectTab;
import utilities.Catalog;
import utilities.Props;

public class FixWrongs {

	private String inspIdMissAct = Props.getProperty("inspIdMissAct");
	private String inspIdWrongRightEarly = Props.getProperty("inspIdWrongRightEarly");
	private String inspIdMissContract = Props.getProperty("inspIdMissContract");

	private String actCategory = Catalog.docs.category.ACT_NF;
	private String actPath = Catalog.docs.path.ACT_NF;
	private String contractRight = Catalog.useRight.RENT;
	private String baseUrl = Props.BASE_URL;
	

	@Test
	public void fixMissAct() throws InterruptedException {
		WebDriver driver = Props.initChromeDriver();

		driver.get(baseUrl + "/Controls/" + inspIdMissAct);
		Upload.file(driver, actCategory, actPath);
		Save.saveThis(driver);
		driver.close();
	}

	public void fixMissContract() throws InterruptedException {
		WebDriver driver = Props.initChromeDriver();

		driver.get(baseUrl + "/Controls/" + inspIdMissContract);
		InspectionPage i = new InspectionPage(driver);
		i.objectTabSwitch();
		InspectionObjectTab obj = new InspectionObjectTab(driver);
		obj.addContractTable(contractRight);
		Save.saveThis(driver);
	}

	public void fixWrongRight() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.4.29:1521:gin", "GINU", "u123");
		Statement s = con.createStatement();		
		
		String wrongRightDateToLaterDate = "UPDATE building_contract SET start_date = TO_DATE('23/05/2019', 'DD/MM/YYYY') WHERE id IN (SELECT building_contract_id FROM control_bld_contract WHERE control_id = " +inspIdWrongRightEarly+") AND right_type_ctid != 340001";
		
		String rentRightDateToEarlierDate = "UPDATE building_contract SET start_date = TO_DATE('23/05/2014', 'DD/MM/YYYY') WHERE id IN (SELECT building_contract_id FROM control_bld_contract WHERE control_id = " +inspIdWrongRightEarly+") AND right_type_ctid = 340001";
		
		int up1 = s.executeUpdate(wrongRightDateToLaterDate);
		int up2 = s.executeUpdate(rentRightDateToEarlierDate);
		s.execute("commit");
		
		Assert.assertFalse(up1 != 1 && up2 != 1);
				
		
	}

}
