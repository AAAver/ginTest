package runner;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class LogPanel extends JPanel {
    private JTextArea textArea;
    private PrintStream standardOut;

    public LogPanel() throws UnsupportedEncodingException {
        textArea = new JTextArea();
        textArea.setEditable(false);

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        standardOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);

        setLayout(new BorderLayout());
        add(new JScrollPane(textArea),BorderLayout.CENTER);
    }
    public void appendText(String text){
        textArea.append(text);
    }
}
