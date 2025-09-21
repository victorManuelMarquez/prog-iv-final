package ar.com.baden.util;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AppProperties extends Properties {

    private final PropertyChangeSupport propertyChangeSupport;
    private final Map<Object, Object> buffer;

    public AppProperties() {
        super(new Properties());
        propertyChangeSupport = new PropertyChangeSupport(this);
        buffer = new HashMap<>();
        setDefaultValues();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        if (containsKey(key)) {
            boolean equals = getProperty(key.toString()).equals(value);
            String propertyName = equals ? "restore" : "change";
            Object previous;
            if (equals) {
                previous = buffer.remove(key);
            } else {
                buffer.put(key, value);
                previous = get(key);
            }
            propertyChangeSupport.firePropertyChange(propertyName, previous, value);
        }
        return null;
    }

    private String createKey(String suffix) {
        return "settings." + suffix;
    }

    protected void setDefaultValues() {
        if (defaults != null) {
            defaults.setProperty(createKey("showClosingDialog"), String.valueOf(true));
            LookAndFeel laf = UIManager.getLookAndFeel();
            defaults.setProperty(createKey("lookAndFeel.id"), laf.getID());
            defaults.setProperty(createKey("lookAndFeel.className"), laf.getClass().getName());
            UIDefaults lafDefaults = laf.getDefaults();
            if ("Metal".equals(laf.getID())) {
                String key = "swing.boldMetal";
                boolean boldMetal = !lafDefaults.containsKey(key) || lafDefaults.getBoolean(key);
                defaults.setProperty(createKey(key), String.valueOf(boldMetal));
            }
            Predicate<Object> predicate = v -> v.toString().endsWith("font") || v.toString().endsWith("Font");
            Stream<Object> keys = lafDefaults.keySet().stream().filter(predicate);
            keys.forEach(k -> {
                Font font = lafDefaults.getFont(k);
                defaults.setProperty(createKey(k + ".family"), font.getFamily());
                defaults.setProperty(createKey(k + ".style"), String.valueOf(font.getStyle()));
                defaults.setProperty(createKey(k + ".size"), String.valueOf(font.getSize()));
            });
            super.putAll(defaults);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public void applyChanges() {
        if (!buffer.isEmpty()) {
            super.putAll(buffer);
            buffer.clear();
            propertyChangeSupport.firePropertyChange("changesApplied", false, true);
        }
    }

    public void clearChanges() {
        buffer.clear();
        propertyChangeSupport.firePropertyChange("changesCleared", false, true);
    }

    public boolean hasChanges() {
        return !buffer.isEmpty();
    }

}
