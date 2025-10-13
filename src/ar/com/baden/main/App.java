package ar.com.baden.main;

import ar.com.baden.gui.MainFrame;
import ar.com.baden.utils.Settings;

import javax.swing.*;

public class App {

    public static Settings settings;

    static {
        settings = new Settings();
    }

    static void main() {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame("Bienvenido");
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
