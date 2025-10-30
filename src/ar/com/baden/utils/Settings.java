package ar.com.baden.utils;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Settings extends Properties {

    public static final String K_CONFIRM_EXIT = "settings.confirmToExit";
    public static final String K_LOOK_AND_FEEL = "settings.lookAndFeel";
    public static final String K_SWING_BOLD_METAL = "settings.swingBoldMetal";
    public static final String K_METAL_THEME = "settings.metalTheme";
    public static final String K_WINDOWS_DECORATIONS = "settings.lafWindowsDecorations";
    static final String APP_FOLDER = ".baden";

    private final Properties buffer;
    private final PropertyChangeSupport changeSupport;

    public Settings() {
        super(new Properties());
        setDefaults();
        buffer = new Properties();
        changeSupport = new PropertyChangeSupport(this);
    }

    private void setDefaults() {
        defaults.put(K_CONFIRM_EXIT, String.valueOf(true));
        String className = UIManager.getCrossPlatformLookAndFeelClassName();
        Object swingBoldMetal = UIManager.get("swingBoldMetal");
        defaults.put(K_LOOK_AND_FEEL, className);
        if (swingBoldMetal != null) {
            defaults.put(K_SWING_BOLD_METAL, swingBoldMetal.toString());
        } else {
            defaults.put(K_SWING_BOLD_METAL, String.valueOf(true));
        }
        MetalTheme metalTheme = MetalLookAndFeel.getCurrentTheme();
        defaults.put(K_METAL_THEME, metalTheme.getName());
        boolean decorations = JFrame.isDefaultLookAndFeelDecorated() && JDialog.isDefaultLookAndFeelDecorated();
        defaults.put(K_WINDOWS_DECORATIONS, String.valueOf(decorations));
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
