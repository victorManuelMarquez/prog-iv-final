package ar.com.baden.main;

import ar.com.baden.gui.LauncherFrame;
import ar.com.baden.util.AppProperties;

import javax.swing.*;
import java.util.Set;
import java.util.TreeSet;

public class App {

    public static Set<String> availableFontFamilyNames;
    public static AppProperties properties;

    static {
        availableFontFamilyNames = new TreeSet<>();
        properties = new AppProperties();
    }

    static void main() {
        SwingUtilities.invokeLater(LauncherFrame::createAndShow);
    }

}
