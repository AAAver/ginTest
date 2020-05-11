package runner;

import java.util.EventListener;

public interface SettingsListener extends EventListener {
    void urlSettingsChanged(SettingsEvent e);
    void launchTest(SettingsEvent e);
}
