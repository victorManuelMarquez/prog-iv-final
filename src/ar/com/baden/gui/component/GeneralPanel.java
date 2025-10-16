package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;

public class GeneralPanel extends JPanel {

    public GeneralPanel() {
        // variables
        String confirmToExitKey = "settings.confirmToExit";
        String property = App.settings.getProperty(confirmToExitKey);

        // componentes
        JCheckBox confirmToExitBtn = new JCheckBox("Confirmar para salir");
        confirmToExitBtn.setSelected(Boolean.parseBoolean(property));

        // instalando componentes
        add(confirmToExitBtn);

        // eventos
        confirmToExitBtn.addActionListener(_ -> {
            boolean status = confirmToExitBtn.isSelected();
            App.settings.setProperty(confirmToExitKey, String.valueOf(status));
        });
    }

}
