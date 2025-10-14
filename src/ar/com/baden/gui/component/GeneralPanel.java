package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;

public class GeneralPanel extends JPanel {

    public GeneralPanel() {
        // variables
        String property = App.settings.getProperty("settings.confirmToExit");

        // componentes
        JCheckBox confirmToExitBtn = new JCheckBox("Confirmar para salir");
        confirmToExitBtn.setSelected(Boolean.parseBoolean(property));

        // instalando componentes
        add(confirmToExitBtn);

        // eventos
        confirmToExitBtn.addActionListener(_ -> {
            boolean status = confirmToExitBtn.isSelected();
            App.settings.setProperty("setting.confirmToExit", String.valueOf(status));
        });
    }

}
