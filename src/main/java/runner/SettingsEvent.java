package runner;

import tests.runnertest.*;

import java.util.EventObject;

public class SettingsEvent extends EventObject {
    private String baseUrl;
    private Class[] testToRun;
    private String suiteInfo;
    private String suiteToRun;

    public SettingsEvent(Object source) {
        super(source);
    }

    public SettingsEvent(Object source, String baseUrl) {
        super(source);
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setTestToRun(int testToRun) {
        switch (testToRun) {
            case 0:
                this.testToRun = new Class[]{BakeUbsPril2.class};
                break;
            case 1:
                this.testToRun = new Class[]{BakeUbsPril3.class};
                break;
            case 2:
                this.testToRun = new Class[]{BakeUbs234pp.class};
                break;
            case 3:
                this.testToRun = new Class[]{BakeControlNf.class};
                break;
            case 4:
                this.testToRun = new Class[]{DismantleP3Easy.class};
                break;
            case 5:
                this.testToRun = new Class[]{DismantleP3Voluntary.class};
                break;

        }
    }

    public void setSuiteToRun(int suiteToRun){
        switch (suiteToRun){
            case 101:
                this.suiteToRun = "./suites/spdDgiSuite.xml";
                break;

        }
    }
    public String getSuiteToRun(){return suiteToRun;}

    public Class[] getTestToRun() {
        return testToRun;
    }

    public void setSuiteInfo(int testSuiteToDisplay) {
        switch (testSuiteToDisplay) {
            case 101:
                suiteInfo = "12 тест кейсов по СПД ДГИ\n";
        }
    }

    public String getSuiteInfo() {
        return suiteInfo;
    }

}
