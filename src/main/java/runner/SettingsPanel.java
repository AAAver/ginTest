package runner;



import javax.swing.*;
import javax.swing.border.Border;
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
    private SettingsListener settingsListener;
    private JList testList;
    private JComboBox comboBox;


    public SettingsPanel(){
        Dimension dim = getPreferredSize();
        dim.width = 500;
        setPreferredSize(dim);
        Border innerBorder = BorderFactory.createTitledBorder("Настройки");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        disposalLabelZu1 = new JLabel("Url Поручение ЗУ: ");
        disposalFieldZu = new JTextField(25);
        disposalLabelNf = new JLabel("Url Поручение НФ: ");
        disposalFieldNf = new JTextField(25);
        saveBtn = new JButton("Сохранить");



        //============== СПИСОК ТЕСТОВ ===========//
        testPickerLabel = new JLabel("Карточка для генерации:");

        testList = new JList();
        DefaultListModel testListModel = new DefaultListModel();
        testListModel.addElement(new TestCategory(0,"ОСС по 819-ПП прил.2"));
        testListModel.addElement(new TestCategory(1,"ОСС по 819-ПП прил.3"));
        testListModel.addElement(new TestCategory(2,"ОСС по 234-ПП"));
        testListModel.addElement(new TestCategory(3,"Проверка НФ"));
        testList.setModel(testListModel);
        testList.setBorder(BorderFactory.createEtchedBorder());
        testList.setSelectedIndex(0);

        launchTestBtn = new JButton("Запустить");

        //=============== ДРОПДАУН ==============//
        comboBox = new JComboBox();

        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addElement("Первый");
        comboBoxModel.addElement("Второй");
        comboBoxModel.addElement("Третий");
        comboBox.setModel(comboBoxModel);

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

                if(settingsListener != null){
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

                if(settingsListener != null){
                    settingsListener.launchTest(event);
                }

            }
        });
    }
    public void setSettingsListener(SettingsListener settingsListener){
        this.settingsListener = settingsListener;
    }
    public void appendDisposalZuText(String text){
        disposalFieldZu.setText(text);
    }
    public void appendDisposalNfText(String text){
        disposalFieldNf.setText(text);
    }

    public void layoutComponents(){

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
        gc.insets = new Insets(0,0,0,5);
        add(disposalLabelZu1, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(disposalFieldZu, gc);

        //============ NEXT ROW ============//
        gc.gridy ++;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(disposalLabelNf,gc);

        gc.gridx = 1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(disposalFieldNf,gc);

        //============ NEXT ROW ============//
        gc.gridy ++;

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(saveBtn, gc);

        //============ NEXT ROW ============//
        gc.gridy ++;

        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(testPickerLabel,gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(testList, gc);

        //============ NEXT ROW ============//
//        gc.gridy ++;
//
//        gc.gridx = 1;
//        gc.weightx = 1;
//        gc.weighty = 0.1;
//        gc.insets = new Insets(0,0,0,0);
//        gc.anchor = GridBagConstraints.FIRST_LINE_START;
//        add(comboBox, gc);

        //============ NEXT ROW ============//
        gc.gridy ++;

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 2;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(launchTestBtn, gc);
    }
}

class TestCategory{
    private int id;
    private String testName;
    public TestCategory(int id, String testName){
        this.id = id;
        this.testName = testName;
    }
    public String toString(){return testName;}
    public int getId(){return id;}
}