package runner;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

    private JLabel baseUrl;
    private JTextField baseUrlField;

    private JLabel testPickerLabel;
    private JButton saveBtn;
    private JButton launchTestBtn;
    private JButton launchSuiteBtn;
    private SettingsListener settingsListener;
    private JList testList;
    private JScrollPane testScroll;
    private JList testSuites;
    private JComboBox comboBox;



    public SettingsPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 500;
        setPreferredSize(dim);
        Border innerBorder = BorderFactory.createTitledBorder("Настройки");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        baseUrl = new JLabel("BaseURL: ");
        baseUrlField = new JTextField(25);

        saveBtn = new JButton("Сохранить");


        //============== СПИСОК ТЕСТОВ ===========//
        testPickerLabel = new JLabel("Что будем выпекать:");

        testList = new JList();
        testScroll = new JScrollPane(testList);
        testScroll.setPreferredSize(new Dimension(250,130));

        DefaultListModel testListModel = new DefaultListModel();
        testListModel.addElement(new TestCategory(0, "ОСС по прил.2"));
        testListModel.addElement(new TestCategory(1, "ОСС по прил.3"));
        testListModel.addElement(new TestCategory(2, "ОСС по 234-ПП"));
        testListModel.addElement(new TestCategory(3, "Проверка НФ"));
        testListModel.addElement(new TestCategory(4, "Демонтаж прил.3(простой)"));
        testListModel.addElement(new TestCategory(5, "Демонтаж прил.3(добровольный)"));
        testList.setModel(testListModel);
        testList.setBorder(BorderFactory.createEtchedBorder());
        testList.setSelectedIndex(0);

        launchTestBtn = new JButton("Запустить");

        //=============== ДРОПДАУН ==============//
        comboBox = new JComboBox();
        comboBox.setPreferredSize(new Dimension(137, 20));

//        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
//        comboBoxModel.addElement("Первый");
//        comboBoxModel.addElement("Второй");
//        comboBoxModel.addElement("Третий");
//        comboBox.setModel(comboBoxModel);

        // ============== СЬЮТЫ =================== //
        testSuites = new JList();
        DefaultListModel ds = new DefaultListModel();
        ds.addElement(new TestCategory(101, "СПД ДГИ сьют"));
        testSuites.setBorder(BorderFactory.createEtchedBorder());
        testSuites.setPreferredSize(new Dimension(137, 20));
        testSuites.setModel(ds);

        launchSuiteBtn = new JButton("Запустить");


        layoutComponents();

        //============ ACTIONS =============//

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String disposalUrlZu = baseUrlField.getText();

                SettingsEvent event = new SettingsEvent(this);

                event.setBaseUrl(disposalUrlZu);
                if (settingsListener != null) {
                    settingsListener.urlSettingsChanged(event);
                }
            }
        });

        launchTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestCategory testToRun = (TestCategory) testList.getSelectedValue();
                SettingsEvent event = new SettingsEvent(this);
                event.setTestToRun(testToRun.getId());

                if (settingsListener != null) {
                    settingsListener.launchTest(event);
                }

            }
        });

        launchSuiteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestCategory suiteToRun = (TestCategory) testSuites.getSelectedValue();

                SettingsEvent event = new SettingsEvent(this);
                event.setSuiteToRun(suiteToRun.getId());

                if (settingsListener != null) {
                    settingsListener.launchSuite(event);
                }
            }
        });

        testSuites.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                TestCategory cat = (TestCategory) list.getSelectedValue();
                SettingsEvent event = new SettingsEvent(this);
                event.setSuiteInfo(cat.getId());

                if (settingsListener != null) {
                    settingsListener.suiteClicked(event);
                }

            }
        });


    }

    public void setSettingsListener(SettingsListener settingsListener) {
        this.settingsListener = settingsListener;
    }

    public void appendDisposalZuText(String text) {
        baseUrlField.setText(text);
    }

    public void layoutComponents() {

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //============ РАСПОЛОЖЕНИЕ КОМПОНЕНТОВ ==========//
        //============ FIRST ROW ============//
        gc.gridy = 0;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(baseUrl, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(baseUrlField, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.3;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(saveBtn, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(testPickerLabel, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(testScroll, gc);

        //============ NEXT ROW ============//
//        gc.gridy++;
//
//        gc.gridx = 1;
//        gc.weightx = 1;
//        gc.weighty = 0.1;
//        gc.insets = new Insets(0, 0, 0, 0);
//        gc.anchor = GridBagConstraints.FIRST_LINE_START;
//        add(comboBox, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.3;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(launchTestBtn, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0, 0, 0, 5);
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Сьюты:"), gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(testSuites, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 2;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(launchSuiteBtn, gc);

    }
}

class TestCategory {
    private int id;
    private String testName;

    public TestCategory(int id, String testName) {
        this.id = id;
        this.testName = testName;
    }

    public String toString() {
        return testName;
    }

    public int getId() {
        return id;
    }
}