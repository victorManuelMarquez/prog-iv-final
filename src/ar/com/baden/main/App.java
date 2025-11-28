package ar.com.baden.main;

import ar.com.baden.main.ar.com.baden.gui.MainFrame;

import javax.swing.*;

public class App {

    static void main() {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame("Bienvenido");
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }

}
