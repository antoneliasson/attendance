package se.antoneliasson.attendance.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.prefs.Preferences;

/*
 * The methods in Preferences are all thread safe, so let's simply instantiate
 * this class when we need access to the configuration and not worry about
 * thread safety in this implementation.
 */
public class Config {
    private final Preferences preferences;
    private final Logger log;

    public Config() {
        preferences = Preferences.userRoot().node("se").node("antoneliasson").node("attendance");
        log = LogManager.getLogger();
    }

    public String getLastDbPath() {
        String path = preferences.get("lastDbPath", System.getProperty("user.dir"));
        log.debug("Loaded last db path: {}", path);
        return path;
    }

    public void setLastDbPath(String path) {
        preferences.put("lastDbPath", path);
        log.debug("Saved last db path: {}", path);
    }

    public String getLastImportPath() {
        String path = preferences.get("lastImportPath", System.getProperty("user.dir"));
        log.debug("Loaded last import path: {}", path);
        return path;
    }

    public void setLastImportPath(String path) {
        preferences.put("lastImportPath", path);
        log.debug("Saved last import path: {}", path);
    }
}
