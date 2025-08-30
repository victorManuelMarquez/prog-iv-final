package ar.com.baden.main;

import ar.com.baden.gui.LauncherFrame;
import ar.com.baden.utils.Settings;

import javax.swing.*;
import java.util.Set;
import java.util.TreeSet;

public class App {

    public static Settings settings;
    public static Set<String> availableFontFamilyNames;

    static {
        settings = new Settings();
        settings.loadDefaults();
        availableFontFamilyNames = new TreeSet<>();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LauncherFrame::createAndShow);
    }

}
