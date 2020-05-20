package runner;

import java.util.EventListener;
import java.util.Set;

public interface SettingsListener extends EventListener {
    void urlSettingsChanged(SettingsEvent e);
    void launchTest(SettingsEvent e);
    void launchSuite(SettingsEvent e);
    void suiteClicked(SettingsEvent e);
}
