package runner;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

    private JLabel disposalLabelZu1;
    private JLabel disposalLabelNf;
    private JLabel testPickerLabel;
    private JTextField disposalFieldZu;
    private JTextField disposalFieldNf;
    private JButton saveBtn;
    private JButton launchTestBtn;
    private JButton launchSuiteBtn;
    private SettingsListener settingsListener;
    private JList testList;
    private JList testSuites;
    private JComboBox comboBox;


    public SettingsPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 500;
        setPreferredSize(dim);
        Border innerBorder = BorderFactory.createTitledBorder("Настройки");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        disposalLabelZu1 = new JLabel("Url Поручение ЗУ: ");
        disposalFieldZu = new JTextField(25);
        disposalLabelNf = new JLabel("Url Поручение НФ: ");
        disposalFieldNf = new JTextField(25);
        saveBtn = new JButton("Сохранить");


        //============== СПИСОК ТЕСТОВ ===========//
        testPickerLabel = new JLabel("Карточка для генерации:");

        testList = new JList();
        DefaultListModel testListModel = new DefaultListModel();
        testListModel.addElement(new TestCategory(0, "ОСС по 819-ПП прил.2"));
        testListModel.addElement(new TestCategory(1, "ОСС по 819-ПП прил.3"));
        testListModel.addElement(new TestCategory(2, "ОСС по 234-ПП"));
        testListModel.addElement(new TestCategory(3, "Проверка НФ"));
        testList.setModel(testListModel);
        testList.setBorder(BorderFactory.createEtchedBorder());
        testList.setSelectedIndex(0);

        launchTestBtn = new JButton("Запустить");

        //=============== ДРОПДАУН ==============//
        comboBox = new JComboBox();
        comboBox.setPreferredSize(new Dimension(137, 20));

        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addElement("Первый");
        comboBoxModel.addElement("Второй");
        comboBoxModel.addElement("Третий");
        comboBox.setModel(comboBoxModel);

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
                String disposalUrlZu = disposalFieldZu.getText();
                String disposalUrlNf = disposalFieldNf.getText();

                SettingsEvent event = new SettingsEvent(this);
                event.setDisposalUrlNf(disposalUrlNf);
                event.setDisposalUrlZu(disposalUrlZu);

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
        disposalFieldZu.setText(text);
    }

    public void appendDisposalNfText(String text) {
        disposalFieldNf.setText(text);
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
        add(disposalLabelZu1, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(disposalFieldZu, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(disposalLabelNf, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(disposalFieldNf, gc);

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
        add(testList, gc);

        //============ NEXT ROW ============//
        gc.gridy++;

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(comboBox, gc);

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