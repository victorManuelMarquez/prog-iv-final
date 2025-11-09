package ar.com.baden.main;

import ar.com.baden.gui.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class App {

    public static Properties properties;

    static {
        properties = new Properties();
        File appFolder = new File(System.getProperty("user.home"), ".baden");
        File propertiesFile = new File(appFolder, "settings.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    static void main() {
        String userLafClassName = properties.getProperty("settings.lookAndFeel");
        String lafDecorations = properties.getProperty("settings.lafWindowsDecorations");
        String swingBold = properties.getProperty("settings.swingBoldMetal");
        if (Boolean.parseBoolean(lafDecorations)) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }
        if (swingBold != null) {
            UIManager.put("swing.boldMetal", Boolean.parseBoolean(swingBold));
        }
        if (userLafClassName != null) {
            try {
                UIManager.setLookAndFeel(userLafClassName);
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
