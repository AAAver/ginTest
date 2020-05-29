package runner;

import pagerepository.utilities.Props;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class MainFrame extends JFrame {

    private LogPanel logPanel;
    private JTextField textField;
    private JButton btn;
    private JLabel label;
    private JCheckBox chk;
    private Toolbar toolbar;
    private SettingsPanel settingsPanel;
    private SettingsListener formListener;
    private SuiteThread suiteThread;
    private TestThread testThread;

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


        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        toolbar.setToolbarListener(new ToolbarListener() {

            @Override
            public void killChrome() throws Exception {
                String chromedriver = "chromedriver.exe";
                if (TaskKiller.isProcessRunning(chromedriver)) {
                    TaskKiller.killProcess(chromedriver);
                    System.out.println("ChromeDriver is dead");
                } else {
                    System.out.println("No ChromeDriver running");
                }
            }

        });

        settingsPanel.setSettingsListener(new SettingsListener() {
            public void urlSettingsChanged(SettingsEvent e) {
                String baseUrl = e.getBaseUrl();
                Props.setProperty("baseUrl", baseUrl);
            }

            @Override
            public void launchTest(SettingsEvent e) {
                testThread = new TestThread(e.getTestToRun());
                testThread.start();
            }

            @Override
            public void launchSuite(SettingsEvent e) {
                suiteThread = new SuiteThread(e.getSuiteToRun());
                suiteThread.start();
            }

            @Override
            public void suiteClicked(SettingsEvent e) {
                logPanel.appendText(e.getSuiteInfo());
            }
        });
        settingsPanel.appendDisposalZuText(Props.getProperty("baseUrl"));
        logPanel.appendText("HELLO\n");
    }
}
