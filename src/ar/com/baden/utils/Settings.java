package ar.com.baden.utils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Settings extends Properties {

    public static final String BASE_KEY = "settings";
    private final List<PropertyChangeListener> listeners;
    private final Map<Object, Object> buffer;

    public Settings() {
        super(new Properties());
        setDefaults();
        listeners = new ArrayList<>();
        buffer = new HashMap<>();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        Object bufferValue = buffer.put(key, value);
        boolean isUpdate = containsKey(key) && !get(key).equals(value);
        if (!isUpdate) {
            bufferValue = buffer.remove(key);
        }
        String propertyName = isUpdate ? "updatedValue" : "restoredValue";
        propertyName = createKey(propertyName);
        notifyListeners(new PropertyChangeEvent(this, propertyName, bufferValue, value));
        return bufferValue;
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
        Enumeration<Object> keys = lookAndFeel.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Font font = lookAndFeel.getDefaults().getFont(key);
            if (font != null) {
                defaults.setProperty(createKey(key.toString()).concat(".family"), font.getFamily());
                defaults.setProperty(createKey(key.toString()).concat(".style"), String.valueOf(font.getStyle()));
                defaults.setProperty(createKey(key.toString()).concat(".size"), String.valueOf(font.getSize()));
            }
        }
    }

    public void loadDefaults() {
        buffer.clear();
        super.putAll(defaults);
        String propertyName = createKey("defaultsApplied");
        notifyListeners(new PropertyChangeEvent(this, propertyName, false, true));
    }

    public void applyChanges() {
        if (!buffer.isEmpty()) {
            super.putAll(buffer);
            buffer.clear();
            String propertyName = createKey("changesApplied");
            notifyListeners(new PropertyChangeEvent(this, propertyName, true, false));
        }
    }

    public void clearChanges() {
        buffer.clear();
        String propertyName = createKey("changesDiscarded");
        notifyListeners(new PropertyChangeEvent(this, propertyName, false, true));
    }

    public boolean hasChanges() {
        return !buffer.isEmpty();
    }

    @Deprecated
    public void clearChanges(String suffix) {
        Stream<Object> keys = buffer.keySet().stream().filter(k -> k.toString().startsWith(suffix));
        List<Object> copy = List.copyOf(keys.toList());
        copy.forEach(buffer::remove);
        String propertyName = createKey(suffix + "...");
        notifyListeners(new PropertyChangeEvent(this, propertyName, false, true));
    }

    protected void notifyListeners(PropertyChangeEvent event) {
        listeners.forEach(l -> l.propertyChange(event));
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        listeners.add(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.remove(l);
    }

}
