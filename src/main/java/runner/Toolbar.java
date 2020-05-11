package runner;

import org.testng.TestNG;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Toolbar extends JPanel implements ActionListener {

    private JButton inspectionNf;
    private JButton ubsPril2;
    private JButton ubsPril3;
    private JButton ubs234;
    private TestNG test;

    private ToolbarListener toolbarListener;
    private String wait;

    public Toolbar(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        inspectionNf = new JButton("Проверка НФ");
        ubsPril2 = new JButton("ОСС Прил.2");
        ubsPril3 = new JButton("ОСС Прил.3");
        ubs234 = new JButton("ОСС 234-ПП");

        wait = "\n Дождитесь автоматического закрытия браузера\n";

        inspectionNf.addActionListener(this);
        ubsPril2.addActionListener(this);
        ubsPril3.addActionListener(this);
        ubs234.addActionListener(this);



        add(inspectionNf);
        add(ubsPril2);
        add(ubsPril3);
        add(ubs234);

    }

    public void setToolbarListener(ToolbarListener communicator) {
        this.toolbarListener = communicator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked  = (JButton) e.getSource();
    }
}
