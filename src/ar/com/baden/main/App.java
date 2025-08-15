package ar.com.baden.main;

import ar.com.baden.gui.LauncherFrame;
import ar.com.baden.utils.Settings;

import javax.swing.*;

public class App {

    public static Settings settings;

    static {
        settings = new Settings();
        settings.loadDefaults();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LauncherFrame::createAndShow);
    }

}
