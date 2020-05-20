package runner;

import org.checkerframework.checker.units.qual.C;
import org.testng.TestNG;
import tests.runnertest.CreateUBS819Pril2;
import tests.runnertest.CreateUBS819Pril3With2Violations;
import tests.spdDgi.*;

import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

public class SettingsEvent extends EventObject {
    private String disposalUrlZu;
    private String disposalUrlNf;
    private Class[] testToRun;
    private String suiteInfo;
    private String suiteToRun;

    public SettingsEvent(Object source) {
        super(source);
    }

    public SettingsEvent(Object source, String disposalUrlZu, String disposalUrlNf) {
        super(source);
        this.disposalUrlZu = disposalUrlZu;
        this.disposalUrlNf = disposalUrlNf;
    }

    public String getDisposalUrlZu() {
        return disposalUrlZu;
    }

    public void setDisposalUrlZu(String disposalUrlZu) {
        this.disposalUrlZu = disposalUrlZu;
    }

    public String getDisposalUrlNf() {
        return disposalUrlNf;
    }

    public void setDisposalUrlNf(String disposalUrlNf) {
        this.disposalUrlNf = disposalUrlNf;
    }

    public void setTestToRun(int testToRun) {
        switch (testToRun) {
            case 0:
                this.testToRun = new Class[]{CreateUBS819Pril2.class};
            case 1:
                this.testToRun = new Class[]{CreateUBS819Pril3With2Violations.class};
            case 2:
                this.testToRun = new Class[]{tests.runnertest.Ubs234PPSignsConfirmed.class};
            case 3:
                this.testToRun = new Class[]{tests.runnertest.ActNoPrevViol.class};
            case 101:
                this.testToRun = new Class[]{ActMissAct.class, ActMissContract.class, ActNoPrevViol.class, ActNoViol.class, ActPrevViol.class, ActWrongRightFirst.class, ActWrongRightLast.class,
                        Ubs234PPSignsConfirmed.class, Ubs234PPIncluded.class, Ubs819Pril2TakenIntoAccount.class, Ubs819Pril2TakenIntoAccount.class};
        }
    }

    public void setSuiteToRun(int suiteToRun){
        switch (suiteToRun){
            case 101:
                this.suiteToRun = "./suites/spdDgiSuite.xml";

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
