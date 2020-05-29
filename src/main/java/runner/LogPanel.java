package runner;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


public class LogPanel extends JPanel {
    private JTextArea textArea;
    private PrintStream standardOut;

    public LogPanel() throws UnsupportedEncodingException {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Courier", Font.PLAIN, 12));

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea),true,"UTF-8");
        standardOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);

        setLayout(new BorderLayout());
        add(new JScrollPane(textArea),BorderLayout.CENTER);
    }
    public void appendText(String text){
        textArea.append(text);
    }

    public String getDisplayedText(){
       return textArea.getText();
    }
}
