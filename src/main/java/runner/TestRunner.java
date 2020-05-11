package runner;

import org.testng.TestNG;
import tests.training.TestFake;

import javax.swing.*;

public class TestRunner {
	static TestNG test;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame("GIN Automation");
			}
		});


	}

}
