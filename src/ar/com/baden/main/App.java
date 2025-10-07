package ar.com.baden.main;

import ar.com.baden.gui.MainFrame;

import javax.swing.*;

public class App {

    static void main() {
        SwingUtilities.invokeLater(MainFrame::createAndShow);
    }

}
