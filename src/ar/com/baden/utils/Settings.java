package ar.com.baden.utils;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import java.util.Properties;

public class Settings extends Properties {

    private final Properties buffer;
    private final PropertyChangeSupport changeSupport;

    public Settings() {
        super(new Properties());
        setDefaults();
        buffer = new Properties();
        changeSupport = new PropertyChangeSupport(this);
    }

    private void setDefaults() {
        if (defaults != null) {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            defaults.put("settings.lookAndFeel", className);
            defaults.put("settings.confirmToExit", String.valueOf(true));
        }
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

    public boolean hasChanges() {
        return !buffer.isEmpty();
    }

}
