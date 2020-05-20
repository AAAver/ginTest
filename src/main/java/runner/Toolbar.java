package runner;

import org.testng.TestNG;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Toolbar extends JPanel {

    private JButton killChromeDriver;
    private JButton ubsPril2;
    private JButton ubsPril3;
    private JButton ubs234;


    private ToolbarListener toolbarListener;


    public Toolbar(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        killChromeDriver = new JButton("Убить ХромДрайвер");
        ubsPril2 = new JButton("ОСС Прил.2");
        ubsPril3 = new JButton("ОСС Прил.3");
        ubs234 = new JButton("ОСС 234-ПП");


        killChromeDriver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(toolbarListener != null){
                    try {
                        toolbarListener.killChrome();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });



        add(killChromeDriver);


    }

    public void setToolbarListener(ToolbarListener communicator) {
        this.toolbarListener = communicator;
    }




}
