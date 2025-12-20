package ar.com.baden.main;

import ar.com.baden.main.ar.com.baden.gui.MainFrame;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame("Bienvenido");
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }

}
