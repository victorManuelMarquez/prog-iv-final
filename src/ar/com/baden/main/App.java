package ar.com.baden.main;

import ar.com.baden.gui.LauncherFrame;

import javax.swing.*;
import java.util.Set;
import java.util.TreeSet;

public class App {

    public static Set<String> availableFontFamilyNames;

    static {
        availableFontFamilyNames = new TreeSet<>();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LauncherFrame::createAndShow);
    }

}
