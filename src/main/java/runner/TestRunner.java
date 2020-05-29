package runner;

import javax.swing.*;
import java.io.UnsupportedEncodingException;

public class TestRunner {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new MainFrame("GIN Automation");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});


	}

}
