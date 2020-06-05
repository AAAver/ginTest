package tests.spdDgi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.testng.Assert;
import org.testng.annotations.Test;

import miscelaneous.Props;

public class DbConnect {

	String inspIdNoPrevViol = Props.getProperty("inspIdNoPrevViol");
	String inspIdNoViol = Props.getProperty("inspIdNoViol");
	String inspIdPrevViol = Props.getProperty("inspIdPrevViol");
	String inspIdMissAct = Props.getProperty("inspIdMissAct");
	String inspIdWrongRightEarly = Props.getProperty("inspIdWrongRightEarly");
	String inspIdMissContract = Props.getProperty("inspIdMissContract");
	String inspIdWrongRightLast = Props.getProperty("inspIdWrongRightLast");
	
	

	private ResultSet getResponse(String query) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.4.29:1521:gin", "GINU", "u123");
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(query);
		return rs;
	}

	@Test
	public void noPrevVioldDb() throws IOException, ClassNotFoundException, SQLException {

		String statement = "select * from export_queues where entity_id = " + inspIdNoPrevViol;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		int count1010 = 0;
		int count1011 = 0;

		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String reglamentid = rs.getString("reglamentid");

			Assert.assertTrue(message.equals("Успешно отправлено."));
			Assert.assertTrue(is_done.equals("1"));

			if (reglamentid.equals("1010")) {
				count1010++;
			}
			if (reglamentid.equals("1011")) {
				count1011++;
			}
			rows++;

		}

		Assert.assertTrue(rows == 5);
		Assert.assertTrue(count1010 == 1);
		Assert.assertTrue(count1011 == 4);

	}

	@Test
	public void noViolDb() throws IOException, ClassNotFoundException, SQLException {

		String statement = "select * from export_queues where entity_id =" + inspIdNoViol;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String delete_comments = rs.getString("delete_comments");

			Assert.assertTrue(message == null, "no message should be here but found - " + message);
			Assert.assertTrue(is_done.equals("0"), "is_done expected to be 0, but it is " + is_done);

			Assert.assertTrue(delete_comments.equals("no violations"),
					"delete comments are not as expected. Theese comments are found " + delete_comments);

			rows++;

		}

		Assert.assertTrue(rows == 1);

	}

	@Test
	public void prevVioldDb() throws IOException, ClassNotFoundException, SQLException {

		String statement = "select * from export_queues where entity_id = " + inspIdPrevViol;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		int count1012 = 0;

		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String reglamentid = rs.getString("reglamentid");

			Assert.assertTrue(message.equals("Успешно отправлено."));
			Assert.assertTrue(is_done.equals("1"));

			if (reglamentid.equals("1012")) {
				count1012++;
			}

			rows++;

		}

		Assert.assertTrue(rows == 1, "Should be only one row but got" + rows);
		Assert.assertTrue(count1012 == 1, "Should be one row with reglamentid 1012 but got" + count1012);

	}

	@Test
	public void noActDb() throws IOException, ClassNotFoundException, SQLException {
		String statement = "select * from export_queues where entity_id = " + inspIdMissAct;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		int count1010 = 0;
		int count1011 = 0;

		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String reglamentid = rs.getString("reglamentid");

			Assert.assertTrue(message.contains("не имеет прикреплённого акта осмотра"));
			Assert.assertTrue(is_done.equals("0"));

			if (reglamentid.equals("1010")) {
				count1010++;
			}
			if (reglamentid.equals("1011")) {
				count1011++;
			}
			rows++;

		}

		Assert.assertTrue(rows == 4, "Всего " + rows + "записи");
		Assert.assertTrue(count1010 == 1);
		Assert.assertTrue(count1011 == 3);

	}

	@Test
	public void wrongRightDb() throws IOException, ClassNotFoundException, SQLException {
		String statement = "select * from export_queues where entity_id = " + inspIdWrongRightEarly;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		int count1010 = 0;
		int count1011 = 0;

		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String reglamentid = rs.getString("reglamentid");

			Assert.assertTrue(message.contains("указано иное право собственност"), "Сообщение: " + message);
			Assert.assertTrue(is_done.equals("0"));

			if (reglamentid.equals("1010")) {
				count1010++;
			}
			if (reglamentid.equals("1011")) {
				count1011++;
			}
			rows++;

		}

		Assert.assertTrue(rows == 4);
		Assert.assertTrue(count1010 == 1);
		Assert.assertTrue(count1011 == 3);

	}
	
	@Test
	public void wrongRightLastDb() throws IOException, ClassNotFoundException, SQLException {
		String statement = "select * from export_queues where entity_id = " + inspIdWrongRightLast;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		int count1010 = 0;
		int count1011 = 0;

		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String reglamentid = rs.getString("reglamentid");

			Assert.assertTrue(message.contains("Успешно отправлено"), "Сообщение: " + message);
			Assert.assertTrue(is_done.equals("1"));

			if (reglamentid.equals("1010")) {
				count1010++;
			}
			if (reglamentid.equals("1011")) {
				count1011++;
			}
			rows++;

		}

		Assert.assertTrue(rows == 4);
		Assert.assertTrue(count1010 == 1);
		Assert.assertTrue(count1011 == 3);

	}

	@Test
	public void noContractDb() throws IOException, ClassNotFoundException, SQLException {
		String statement = "select * from export_queues where entity_id = " + inspIdMissContract;
		ResultSet rs = getResponse(statement);

		int rows = 0;
		int count1010 = 0;
		int count1011 = 0;

		while (rs.next()) {
			String message = rs.getString("message");

			String is_done = rs.getString("is_done");
			String reglamentid = rs.getString("reglamentid");

			Assert.assertTrue(message.contains("нет информации о договорах."));
			Assert.assertTrue(is_done.equals("0"));

			if (reglamentid.equals("1010")) {
				count1010++;
			}
			if (reglamentid.equals("1011")) {
				count1011++;
			}
			rows++;

		}

		Assert.assertTrue(rows == 3, "Всего " + rows + " записи");
		Assert.assertTrue(count1010 == 1);
		Assert.assertTrue(count1011 == 2);

	}
}
