package runner;

import org.testng.TestNG;
import pagerepository.utilities.Props;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private TextPanel textPanel;
    private JTextField textField;
    private JButton btn;
    private JLabel label;
    private JCheckBox chk;
    private Toolbar toolbar;
    private SettingsPanel settingsPanel;
    private SettingsListener formListener;
    private TestNG test;

    public MainFrame(String title){
        super(title);
        setLayout(new BorderLayout());

        toolbar = new Toolbar();
        textPanel = new TextPanel();
        textField = new JTextField();
        settingsPanel = new SettingsPanel();
        chk = new JCheckBox();

        add(textPanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.WEST);


        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //		// TODO Auto-generated method stub

        toolbar.setComponentCommunicator(new ToolbarListener() {
            @Override
            public void textEmitted(String text) {
                textPanel.appendText(text);
            }

        });

        settingsPanel.setSettingsListener(new SettingsListener(){
            public void urlSettingsChanged(SettingsEvent e){
                String disposalUrlZu = e.getDisposalUrlZu();
                String disposalUrlNf = e.getDisposalUrlNf();

                Props.setProperty("disposalUrlZu", disposalUrlZu);
                Props.setProperty("disposalUrlNf", disposalUrlNf);
            }

            @Override
            public void launchTest(SettingsEvent e) {
                int testId = e.getTestToRun();
                switch (testId){
                    case 0:
                        test = new TestNG();
                        test.setTestClasses(new Class[]{tests.runnertest.CreateUBS819Pril2.class});
                        test.run();
                        break;
                    case 1:
                        test = new TestNG();
                        test.setTestClasses(new Class[]{tests.runnertest.CreateUBS819Pril3With2Violations.class});
                        test.run();
                        break;
                    case 2:
                        test = new TestNG();
                        test.setTestClasses(new Class[]{tests.runnertest.Ubs234PPSignsConfirmed.class});
                        test.run();
                        break;
                    case 3:
                        test = new TestNG();
                        test.setTestClasses(new Class[]{tests.runnertest.ActNoPrevViol.class});
                        test.run();
                        break;
                }
            }
        });
        settingsPanel.appendDisposalZuText(Props.getProperty("disposalUrlZu"));
        settingsPanel.appendDisposalNfText(Props.getProperty("disposalUrlNf"));
    }


}
