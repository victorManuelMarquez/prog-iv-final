package ar.com.baden.main;

import ar.com.baden.gui.BootFrame;

import javax.swing.*;

public class App {

    static void main() {
        SwingUtilities.invokeLater(new BootFrame("Cargando..."));
    }

}
