package ar.com.baden.utils;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Settings extends Properties {

    public static final String BASE_KEY = "settings";
    private final List<PropertyChangeListener> listeners;

    public Settings() {
        super(new Properties());
        setDefaults();
        listeners = new ArrayList<>();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        Object old = super.put(key, value);
        boolean isDefault = defaults.containsKey(key) && defaults.get(key).equals(value);
        String propertyName = isDefault ? "default" : (old != null) ? "update" : "new";
        propertyName = createKey(propertyName);
        firePropertyChangeListeners(new PropertyChangeEvent(this, propertyName, old, value));
        return old;
    }

    private String createKey(String value) {
        return BASE_KEY + "." + value;
    }

    protected void setDefaults() {
        // mostrar el diálogo de cierre
        defaults.setProperty(createKey("showClosingDialog"), Boolean.toString(true));
        // "tema" por defecto
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        String lafClassName = lookAndFeel.getClass().getName();
        String lafID = lookAndFeel.getID();
        defaults.setProperty(createKey("lookAndFeel.className"), lafClassName);
        defaults.setProperty(createKey("lookAndFeel.id"), lafID);
        String boldMetalKey = "swing.boldMetal";
        if ("Metal".equals(lafID)) {
            UIDefaults uiDefaults = lookAndFeel.getDefaults();
            boolean boldMetal = !uiDefaults.containsKey(boldMetalKey) || uiDefaults.getBoolean(boldMetalKey);
            defaults.setProperty(createKey(boldMetalKey), String.valueOf(boldMetal));
        }
    }

    public void loadDefaults() {
        putAll(defaults);
    }

    protected void firePropertyChangeListeners(PropertyChangeEvent event) {
        listeners.forEach(l -> l.propertyChange(event));
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        listeners.add(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.remove(l);
    }

}
