package ar.com.baden.gui;

import ar.com.baden.gui.component.ThemesPanel;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private SettingsDialog(Window owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        // componentes
        ThemesPanel themesPanel = new ThemesPanel();

        // instalando componentes
        getContentPane().add(themesPanel);
    }

    public static void createAndShow(MainFrame mainFrame) {
        try {
            SettingsDialog dialog = new SettingsDialog(mainFrame, "Configuración");
            dialog.pack();
            dialog.setLocationRelativeTo(mainFrame);
            dialog.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
