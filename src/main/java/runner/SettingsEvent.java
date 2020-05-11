package runner;

import java.util.EventObject;

public class SettingsEvent extends EventObject {
    private String disposalUrlZu;
    private String disposalUrlNf;
    private int testToRun;

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

    public void setTestToRun(int testToRun){this.testToRun = testToRun;}

    public int getTestToRun(){return testToRun;}
}
