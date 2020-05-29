package runner;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
    private final ByteArrayOutputStream buf = new ByteArrayOutputStream();

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;

    }
    @Override public void flush() throws IOException {
        textArea.append(buf.toString("UTF-8"));
        textArea.setCaretPosition(textArea.getDocument().getLength());
        buf.reset();
    }

    @Override
    public void write(int b) {
        buf.write(b);
    }
}