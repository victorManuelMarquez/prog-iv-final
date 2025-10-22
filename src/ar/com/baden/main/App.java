package ar.com.baden.main;

import ar.com.baden.gui.MainFrame;
import ar.com.baden.utils.Settings;

import javax.swing.*;

public class App {

    public static Settings settings;

    static {
        settings = new Settings();
        settings.load();
    }

    static void main() {
        String lafClassName = settings.getProperty("settings.lookAndFeel");
        if (lafClassName != null) {
            try {
                UIManager.setLookAndFeel(lafClassName);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame("Bienvenido");
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
