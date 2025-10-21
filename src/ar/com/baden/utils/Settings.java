package ar.com.baden.utils;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class Settings extends Properties {

    public static final String APP_FOLDER = ".baden";

    private final Properties buffer;
    private final PropertyChangeSupport changeSupport;

    public Settings() {
        super(new Properties());
        setDefaults();
        buffer = new Properties();
        changeSupport = new PropertyChangeSupport(this);
    }

    private void setDefaults() {
        String className = UIManager.getCrossPlatformLookAndFeelClassName();
        defaults.put("settings.lookAndFeel", className);
        defaults.put("settings.confirmToExit", String.valueOf(true));
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        String keyString = key.toString();
        String currentValue = getProperty(keyString);
        boolean isUpdate = currentValue != value;
        boolean isDefault = Objects.equals(defaults.getProperty(keyString), currentValue);
        String propertyName = isUpdate ? "update" : isDefault ? "default" : "current";
        Object oldValue;
        if (isUpdate) {
            oldValue = buffer.setProperty(keyString, value.toString());
        } else {
            oldValue = buffer.remove(keyString);
        }
        changeSupport.firePropertyChange(propertyName, oldValue, value);
        return oldValue;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public void applyChanges() {
        super.putAll(buffer);
        buffer.clear();
        changeSupport.firePropertyChange("changesApplied", false, true);
    }

    public void discardChanges() {
        buffer.clear();
        changeSupport.firePropertyChange("changesDiscarded", false, true);
    }

    public void restoreDefaults() {
        buffer.clear();
        buffer.putAll(defaults);
        changeSupport.firePropertyChange("defaultsRestored", false, true);
    }

    public String getDefault(String key) {
        return defaults.getProperty(key);
    }

    public boolean hasChanges() {
        return !buffer.isEmpty();
    }

    public void load() {
        String userHome = System.getProperty("user.home");
        File appFolder = new File(userHome, APP_FOLDER);
        File propertiesFile = new File(appFolder, "settings.properties");
        try (FileReader reader = new FileReader(propertiesFile)) {
            super.load(reader);
            if (buffer.isEmpty()) {
                restoreDefaults();
            } else {
                applyChanges();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void save() {
        String userHome = System.getProperty("user.home");
        File appFolder = new File(userHome, APP_FOLDER);
        if (!appFolder.exists()) {
            boolean ignore = appFolder.mkdir();
        }
        File propertiesFile = new File(appFolder, "settings.properties");
        try (FileWriter writer = new FileWriter(propertiesFile)) {
            super.store(writer, "Archivo de configuración");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

}
