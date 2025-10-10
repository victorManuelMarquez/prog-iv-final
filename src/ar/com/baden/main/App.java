package ar.com.baden.main;

import ar.com.baden.gui.MainFrame;
import ar.com.baden.utils.UserPreferences;

import javax.swing.*;

public class App {

    static void main() {
        SwingUtilities.invokeLater(() -> {
            String lafClassName = UserPreferences.getUserTheme();
            LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
            if (lookAndFeel == null || !lookAndFeel.getClass().getName().equals(lafClassName)) {
                try {
                    UIManager.setLookAndFeel(lafClassName);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
            MainFrame frame = new MainFrame("Bienvenido");
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
