package ar.com.baden.util;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AppProperties extends Properties {

    public AppProperties() {
        super(new Properties());
        setDefaultValues();
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
            putAll(defaults);
        }
    }

}
