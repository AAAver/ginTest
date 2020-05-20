package runner;

import okhttp3.internal.concurrent.Task;
import org.testng.TestNG;
import pagerepository.utilities.Props;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private LogPanel logPanel;
    private JTextField textField;
    private JButton btn;
    private JLabel label;
    private JCheckBox chk;
    private Toolbar toolbar;
    private SettingsPanel settingsPanel;
    private SettingsListener formListener;
    private TestNG test;
    private Thread testThread;

    public MainFrame(String title) throws UnsupportedEncodingException {
        super(title);
        setLayout(new BorderLayout());

        toolbar = new Toolbar();
        logPanel = new LogPanel();
        textField = new JTextField();
        settingsPanel = new SettingsPanel();
        chk = new JCheckBox();

        add(logPanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.WEST);


        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        toolbar.setToolbarListener(new ToolbarListener() {

            @Override
            public void killChrome() throws Exception {
                String chromedriver = "chromedriver.exe";
                if(TaskKiller.isProcessRunning(chromedriver)){
                    TaskKiller.killProcess(chromedriver);
                }
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
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Class[] testId = e.getTestToRun();
                        test = new TestNG();
                        test.setTestClasses(testId);
                        test.run();

                    }
                });
                t.start();

            }

            @Override
            public void launchSuite(SettingsEvent e) {
                testThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        test = new TestNG();
                        List<String> suite= new ArrayList<>();
                        suite.add(e.getSuiteToRun());
                        test.setTestSuites(suite);
                        test.run();
                    }
                });
                testThread.start();
            }

            @Override
            public void suiteClicked(SettingsEvent e) {

             logPanel.appendText(e.getSuiteInfo());
            }
        });
        settingsPanel.appendDisposalZuText(Props.getProperty("disposalUrlZu"));
        settingsPanel.appendDisposalNfText(Props.getProperty("disposalUrlNf"));
        logPanel.appendText("HELLO\n");
    }
}
